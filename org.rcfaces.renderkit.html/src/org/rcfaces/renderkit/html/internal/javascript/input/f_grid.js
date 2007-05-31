/*
 * $Id$
 */

/**
 * 
 * @class public f_grid extends f_component, fa_disabled, fa_pagedComponent, fa_subMenu, fa_commands, fa_selectionManager, fa_selectionManager, fa_scrollPositions
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {

	/**
	 * @field private static final String
	 */
	_DEFAULT_ALIGNMENT: "left",


	/**
	 * @field private static final String[]
	 */
	_DEFAULT_ROW_STYLE_CLASSES: [ "f_grid_row_odd", "f_grid_row_even" ],

	/** 
	 * @field protected static final number	
	 */
	IMAGE_WIDTH: 16,

	/** 
	 * @field protected static final number	
	 */
	IMAGE_HEIGHT: 16,

	/** 
	 * @field private static final number	
	 */
	_CURSOR_WIDTH: 8,

	/** 
	 * @field private static final number	
	 */
	_COLUMN_MIN_WIDTH: 4,

	/** 
	 * @field private static final number	
	 */
	_COLUMN_MAX_WIDTH: 640,

	/** 
	 * @field private static final number	
	 */
	_DRAG_TIMER: 50,

	/** 
	 * @field protected static final boolean	
	 */
	USE_BACKGROUND_IMAGE: false,
	
	/** 
	 * @field private static final String	
	 */
	_BLANK_IMAGE_URL: "/blank.gif",
	
	/** 
	 * @field private static final String	
	 */
	_ROW_MENU_ID: "#row",	

	/** 
	 * @field private static final String	
	 */
	_BODY_MENU_ID: "#body",

	/** 
	 * @field private static final String	
	 */
	_HEAD_MENU_ID: "#head",
	
	/** 
	 * @field private static final number	
	 */
	_TEXT_RIGHT_PADDING: 8,
	
	/** 
	 * @field private static final number	
	 */
	_SORT_PADDING: 18,
	
	/** 
	 * @field protected static final number	
	 */
	FULL_WAITING: 0,

	/** 
	 * @field protected static final number	
	 */
	END_WAITING: 1,

	/** 
	 * @field protected static final number	
	 */
	ROWS_WAITING: 2,	
	
	/** 
	 * @field private static 	
	 */
	_DragOldCursor: undefined,
	
	/** 
	 * @field private static	
	 */
	_DragColumn: undefined,
		
	/**
	 * @method protected static
	 */
	RowMouseDown: function(evt) {
		var dataGrid=this._dataGrid;

		f_core.Debug(f_grid, "RowMouseDown: mouse down on row of '"+dataGrid+"'");
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}
		
		if (dataGrid.f_isDisabled() || (dataGrid.f_isReadOnly && dataGrid.f_isReadOnly())) {
			return f_core.CancelJsEvent(evt);
		}
		
		dataGrid.f_forceFocus();
		
		var sub=f_core.IsPopupButton(evt);

		var selection=fa_selectionManager.ComputeMouseSelection(evt);
			
		dataGrid.f_moveCursor(this, true, evt, selection);			
		
		if (sub && this._selected) {
			var menu=dataGrid.f_getSubMenuById(f_grid._ROW_MENU_ID);
			if (menu) {
				menu.f_open(evt, {
					position: f_popup.MOUSE_POSITION
				});
			}
		}
			
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method protected static
	 */
	VerifyTarget: function(evt) {		
		if (this._dataGrid || this._row) {
			return true;
		}
			
		var target;
		if (evt.target) {
			target = evt.target;
			
		} else if (evt.srcElement) {
			target = evt.srcElement;
		}
			
		if (target && target.nodeType==f_core.ELEMENT_NODE) {
			var tagName=target.tagName;
			if (tagName && tagName.toLowerCase()=="input") {
				return false;
			}
		}
				
		return true;
	},
	/**
	 * @method protected static
	 */
	FiltredCancelJsEventHandler: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}
		
		return f_core.CancelJsEventHandler(evt);
	},
	/**
	 * @method protected static
	 */
	RowMouseDblClick: function(evt) {
		var dataGrid=this._dataGrid;
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (f_core.IsPopupButton(evt) || dataGrid.f_isDisabled() || !this._selected) {
			return f_core.CancelJsEvent(evt);
		}
		
		dataGrid.f_fireEvent(f_event.DBLCLICK, evt, this, this._index, dataGrid);
		
		return f_core.CancelJsEvent(evt);;
	},
	/**
	 * @method private static
	 */
	_BodyMouseDown: function(evt) {
		var dataGrid=this._dataGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
	
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		var sub=f_core.IsPopupButton(evt);
		if (!sub) {
			return f_core.CancelJsEvent(evt);
		}
		
		dataGrid.f_forceFocus();
		
		var menuId=f_grid._BODY_MENU_ID;
		
		// S'il y a une seule selection, on bascule en popup de ligne !
		if (this._selectable && dataGrid._currentSelection.length) {
			menuId=f_grid._ROW_MENU_ID;	
		}
		
		var menu=dataGrid.f_getSubMenuById(menuId);
		if (menu) {
			menu.f_open(evt, {
				position: f_popup.MOUSE_POSITION
			});
		}
			
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method protected
	 * @param HTMLTableElement element
	 * @return HTMLTrElement
	 */
	GetFirstRow: function(element, assertIfNotFound) {
		f_core.Assert(element && element.tagName.toLowerCase()=="table", "f_grid.GetFistRow: Invalid table parameter ("+element+")");

		var child=element.firstChild;
		for(;child;child=child.nextSibling) {
			switch(child.tagName.toLowerCase()) {
			case "thead":
			case "tbody":
				if (child.firstChild) {
					return child.rows[0];
				}
				break;
				
			case "tr": 
				return child;
			}
		}

		f_core.Assert(!assertIfNotFound, "f_grid.GetFirstRow: Component TR not found from '"+element+"' !");
		return null;
	},
	/**
	 * @method protected
	 * @param HTMLTableElement element
	 * @return HTMLTrElement
	 */
	ListRows: function(element) {
		f_core.Assert(element && 
			element.tagName.toLowerCase()=="table", "f_grid.ListRows: Invalid table parameter ("+element+")");

		var rows=null;
		var child=element.firstChild;
		for(;child;child=child.nextSibling) {
			switch(child.tagName.toLowerCase()) {
			case "thead":
			case "tbody":
				if (child.firstChild) {
					return child.rows;				
				}
				break;
				
			case "tr": 
				if (!rows) {
					rows=new Array;
				}
		
				rows.push(child);
			}
		}

		if (!rows) {
			return [];
		}
		return rows;
	},
	/**
	 * @method private static
	 */
	_Link_onfocus: function(evt) {
		try {
			var dataGrid=this._dataGrid;

			if (dataGrid._ignoreFocus) {
				return false;
			}

			f_core.Debug(f_grid, "_Link_onfocus: Get focus for '"+dataGrid+"'");
			
			if (!evt) {
				evt = f_core.GetJsEvent(this);
			}
			
			if (!dataGrid._loading && dataGrid.f_getEventLocked(evt, false)) {
				return false;
			}
			
			if (!f_grid.VerifyTarget(evt)) {
				return true;
			}
			
			if (dataGrid._focus) {
				return true;
			}
			
			dataGrid._focus=true;
		
			if (this._selectable) {
				if (!dataGrid._cursor) {
					var currentSelection=dataGrid._currentSelection;
					if (currentSelection.length) {
						dataGrid._cursor=currentSelection[0];
						dataGrid._initCursorValue=undefined;
					}
		
					if (!dataGrid._cursor && dataGrid._table) {
						var tr=f_grid.GetFirstRow(dataGrid._table); //f_core.GetFirstElementByTagName(dataGrid._table, "tr");
				
						if (tr) {
							dataGrid._cursor=tr;
							dataGrid._initCursorValue=undefined;
						}
					}
							
					dataGrid._updateCurrentSelection();
				}

			} else if (!dataGrid._cursor && dataGrid._table) {
				var tr=f_grid.GetFirstRow(dataGrid._table); //f_core.GetFirstElementByTagName(dataGrid._table, "tr");
		
				if (tr) {
					dataGrid._cursor=tr;
					dataGrid._initCursorValue=undefined;
				}			
			}
			
			if (dataGrid._cursor) {
				dataGrid.fa_updateElementStyle(dataGrid._cursor);			
			}
			
			dataGrid.f_onFocus(evt);
	
			return true;
			
		} catch (x) {
			f_core.Error(f_grid, "_Link_onfocus: throws exception.", x);
		}
	},
	/**
	 * @method private static
	 */
	_Link_onblur: function(evt) {
		try {
			var dataGrid=this._dataGrid;

			if (dataGrid._ignoreFocus) {
				return true;
			}

			f_core.Debug(f_grid, "_Link_onblur: Lost focus for '"+dataGrid+"'");

			if (!evt) {
				evt = f_core.GetJsEvent(this);
			}

	// On bloque pas le "blur" car lors d'une ouverture d'une popup, il faut le traiter !
	//		if (dataGrid.f_getEventLocked(evt, false)) {
	//			return false;
	//		}
			
			if (!dataGrid._focus) {
				return true;
			}
			
			dataGrid._focus=false;
		
			dataGrid._updateCurrentSelection();
			
			dataGrid.f_onBlur(evt);
	
			return true;
			
		} catch (x) {
			f_core.Error(f_grid, "_Link_onfocus: throws exception.", x);
		}
	},
	/**
	 * @method private static
	 */
	_Link_onkeypress: function(evt) {
		var dataGrid=this._dataGrid;
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt, false)) {
			return false;
		}

		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		var code=evt.charCode;
		if (code===undefined) {
			code=evt.keyCode;
		}
		
		if (evt.keyCode==f_key.VK_TAB) {
			return true;
			
		} else if (f_key.IsPrintable(code)) {
			return dataGrid.f_onKeyPress(evt);
//			return true;
		}

		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onkeydown: function(evt) {
		var dataGrid=this._dataGrid;
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		// On peut vouloir faire PAGE-DOWN/UP avec un repeat ! => pas de boite d'alerte !
		if (dataGrid.f_getEventLocked(evt, !dataGrid._waitingLoading)) {
			return false;
		}
	
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (!dataGrid._focus) {
			return true;
		}
	
		return dataGrid.f_onKeyDown(evt);
		//return dataGrid.f_performKeyDown(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onkeyup: function(evt) {
		var dataGrid=this._dataGrid;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		// On peut vouloir faire PAGE-DOWN/UP avec un repeat ! => pas de boite d'alerte !
		if (dataGrid.f_getEventLocked(evt, false)) {
			return false;
		}
	
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (!dataGrid._focus) {
			return true;
		}
	
		return dataGrid.f_onKeyUp(evt);
		//return dataGrid.f_performKeyDown(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onmousewheel: function(evt) {
		var dataGrid=this._dataGrid;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt, false)) {
			// Il faut bloquer ... sinon ca risque de scroller la page !
			return f_core.CancelJsEvent(evt);
		}

		if (!dataGrid._focus) {
			return true;
		}

		var wheel=evt.wheelDelta;
		
		if (f_core.IsGecko()) {
			wheel=-evt.detail;
		}
		
		if (wheel>0) {
			dataGrid._previousCursorRow(evt);

		} else if (wheel<0) {
			dataGrid._nextCursorRow(evt);
		}
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_Title_onMouseOver: function(evt) {
		var column=this._column;
		if (!column) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var dataGrid=column._dataGrid;
		if (dataGrid.f_getEventLocked(evt, false) || !dataGrid._columnCanBeSorted) {
			return false;
		}
		
		// En drag ?
		if (f_grid._DragColumn) {
			return false;
		}	

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		if (dataGrid._columnOver==column) {
			return true;
		}

		var oldColumn=dataGrid._columnOver;
		
		dataGrid._columnOver=column;

		if (oldColumn) {
			dataGrid._updateTitleStyle(oldColumn);
		}

		dataGrid._updateTitleStyle(column);
	},
	/**
	 * @method private static
	 */
	_Title_onMouseOut: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		// En drag ?
		if (f_grid._DragColumn) {
			return false;
		}	

		var column=this._column;
		if (!column) {
			return false;
		}
		
		var dataGrid=column._dataGrid;

		if (dataGrid.f_isDisabled() || !dataGrid._columnCanBeSorted || !column._method) {
			return false;
		}
		
		if (dataGrid._columnSelected==column) {
			dataGrid._columnSelected=null;
		}
		
		if (dataGrid._columnOver!=column) {
			return true;
		}

		dataGrid._columnOver=null;

		dataGrid._updateTitleStyle(column);
	},
	/**
	 * @method private static
	 */
	_Title_onMouseDown: function(evt) {

		var column=this._column;
		if (!column) {
			return false;
		}
	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
	
		var dataGrid=column._dataGrid;
		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
		
		if (dataGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		var sub=f_core.IsPopupButton(evt);
		if (sub) {
			var menu=dataGrid.f_getSubMenuById(f_grid._HEAD_MENU_ID);
			
				if (menu) {
				menu.f_open(evt, {
					position: f_popup.MOUSE_POSITION
				});
			}
			return f_core.CancelJsEvent(evt);
		}
	
//	alert("CB="+dataGrid._columnCanBeSorted);
		if (!dataGrid._columnCanBeSorted || !column._method) {
			return f_core.CancelJsEvent(evt);
		}
		
		dataGrid._columnSelected=column;
		dataGrid._updateTitleStyle(column);
		
		return true;
	},
	/**
	 * @method private static
	 */
	_Title_onMouseUp: function(evt) {
		var column=this._column;
		var dataGrid=column._dataGrid;
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt, false)) {
			return false;
		}

		if (dataGrid.f_isDisabled() || !dataGrid._columnCanBeSorted) {
			return f_core.CancelJsEvent(evt);
		}

		var oldColumn=dataGrid._columnSelected;
		if (!oldColumn) {
			return f_core.CancelJsEvent(evt);
		}
		
		dataGrid._columnSelected=undefined;
	
		if (oldColumn!=column) {
			if (oldColumn) {
				dataGrid._updateTitleStyle(oldColumn);
			}
			return true;
		}
		
		var append=(evt.shiftKey);
		
		dataGrid.f_setColumnSort(column, undefined, append); 
		
		return true;
	},
	/**
	 * @method private static
	 */
	_OnScroll: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		var dataGrid=this._dataGrid;
		var scrollBody=dataGrid._scrollBody;
		
		if (dataGrid._scrollTitle) {		
			var scrollTitleLeft=dataGrid._scrollTitle.scrollLeft;
			var scrollBodyLeft=scrollBody.scrollLeft;
			
			if (scrollTitleLeft!=scrollBodyLeft) {
				dataGrid._scrollTitle.scrollLeft=scrollBodyLeft;
			}
		}
		
		return dataGrid._verifyWaitingPosition(evt);
	},
	/**
	 * @method protected static
	 */
	GotFocus: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		var row=this._row;
		if (row) {
			row._dataGrid.f_forceFocus(row);
			return;
		}
		this._dataGrid.f_forceFocus(this._dataGrid);
	},	
	/**
	 * @method private static
	 */
	_TitleCursorMouseDown: function(evt) {
		var column=this._column;
		var dataGrid=column._dataGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
		
		var doc=dataGrid.ownerDocument;

		f_core.AddEventListener(doc, "mousemove", f_grid._TitleCursorDragMove, dataGrid);
		f_core.AddEventListener(doc, "mouseup",   f_grid._TitleCursorDragStop, dataGrid);

		if (f_core.IsInternetExplorer()) {
			if (false) {
				doc.onlosecapture=function() {
					f_core.Debug(f_grid, "Lose capture !");
				}
			}
		}

	 	f_core.CancelJsEvent(evt);

		var eventPos=f_core.GetJsEventPosition(evt, doc);
		var cursorPos=f_core.GetAbsolutePosition(this);
		dataGrid._dragDeltaX=eventPos.x-cursorPos.x+dataGrid._scrollTitle.scrollLeft;

		f_grid._DragColumn=column;
		dataGrid._dragOriginX=eventPos.x;

		var ths=dataGrid._title.getElementsByTagName("th");
		var c=this.style.cursor;
		for(var i=0;i<ths.length;i++) {
			ths[i].oldCursorStyle=ths[i].style.cursor;
			ths[i].style.cursor="e-resize";
		}
		
		f_grid._DragOldCursor=doc.body.style.cursor;
		doc.body.style.cursor="e-resize";
		
		return false;
	},
	/**
	 * @method private static
	 */
	_TitleCursorDragMove: function(evt) {
		var column=f_grid._DragColumn;
		if (!column) {
			return;
		}
		
		var dataGrid=column._dataGrid;
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var doc=dataGrid.ownerDocument;

		var eventPos=f_core.GetJsEventPosition(evt, doc);
		var cursorPos=f_core.GetAbsolutePosition(column._cursor);
		dataGrid._dragMousePosition=eventPos.x;
		
		var dw=eventPos.x-cursorPos.x+dataGrid._scrollTitle.scrollLeft-dataGrid._dragDeltaX;
		
		f_grid._DragCursorMove(dataGrid, column, dw);
						
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 * @return void
	 */
	_TimerDragMove: function() {
		var column=f_grid._DragColumn;
		if (!column) {
			return;
		}
		
		var dataGrid=column._dataGrid;

		var eventPos=dataGrid._dragMousePosition;
		var cursorPos=f_core.GetAbsolutePosition(column._cursor);
		
		var dw=eventPos-cursorPos.x+dataGrid._scrollTitle.scrollLeft-dataGrid._dragDeltaX;
		
		f_grid._DragCursorMove(dataGrid, column, dw);
	},
	/**
	 * @method private static
	 */
	_DragCursorMove: function(dataGrid, column, dw) {
		
		var doc=dataGrid.ownerDocument;
		
		if (dataGrid._dragTimerId) {
			f_core.GetWindow(doc).clearTimeout(dataGrid._dragTimerId);
			dataGrid._dragTimerId=undefined;
		}

		var w=column._col.offsetWidth+dw;
		
//		document.title="W="+w+"/"+dw;
		
		if (w<column._minWidth) {
			w=column._minWidth;
		}
		if (w>column._maxWidth) {
			w=column._maxWidth;
		}		

		dw=w-column._col.offsetWidth;
		
		if (dw==0) {
			return false;
		}
		
		/*
		var labelStyle=column._label.style;
		if (w<8) {
			if (labelStyle.display!="none") {
				labelStyle.display="none";
			}
		} else if (labelStyle.display!="block") {
			labelStyle.display="block";
		}
		
		if (w<24) {
			if (column._ascendingOrder!==undefined && !column._restoreClass) {
				column._restoreClass=column._label.className;
				column._label.className="f_grid_ttext";
			}
			
		} else if (column._restoreClass) {
			column._label.className=column._restoreClass;		
			column._restoreClass=undefined;
		}
		*/
			
		var tcol=column._tcol;
		var col=column._col;
		var col2=column._col2;
		var head=column._head;
		var tableOffsetWidth=dataGrid._table.offsetWidth;
		
		var twidth=0;
		if (column._ascendingOrder!==undefined) {
			twidth-=f_grid._SORT_PADDING;
		}
		
	 	if (f_core.IsInternetExplorer()) {
			// AVANT !
			if (tableOffsetWidth) {
				dataGrid._table.style.width=(tableOffsetWidth+dw)+"px";
			}
			
			dataGrid._title.style.width=(dataGrid._title.offsetWidth+dw)+"px";

			if (tcol) {
				tcol.style.width=w+"px";
			}
			col.style.width=w+"px";
			col2.style.width=w+"px";
			head.style.width=w+"px";
			
			var bw=w-f_grid._TEXT_RIGHT_PADDING;
			if (bw<0) {
				bw=0;
			}			
			column._box.style.width=bw+"px";
			
			var lw=w-f_grid._TEXT_RIGHT_PADDING+twidth;
			if (lw<0) {
				lw=0;
			}
			column._label.style.width=lw+"px";

		} else {
			if (tcol) {
				//tcol.style.width=w+"px";
			}
			col.style.width=w+"px";			
			col2.style.width=w+"px";
			head.style.width=w+"px";
			column._box.style.width=(w-f_grid._TEXT_RIGHT_PADDING)+"px";
			column._label.style.width=(w-f_grid._TEXT_RIGHT_PADDING+twidth)+"px";
		
			var totalCols=0;
			var columns=dataGrid._columns;
			for(var i=0;i<columns.length;i++) {
				var column=columns[i];
				
				totalCols+=parseInt(column._col.style.width, 10);
			}

			var fakeCol=dataGrid._fakeColWidth;
			if (!fakeCol) {
				fakeCol=0;
			}
			dataGrid._title.style.width=(totalCols+fakeCol)+"px";
			dataGrid._table.style.width=(totalCols)+"px";
		}
		
//		window.status="deltaTitle="+(dataGrid._title.offsetWidth-dataGrid._table.offsetWidth)+"pixels ";
		
		if (dataGrid._scrollTitle.scrollLeft>0) {
			dataGrid._dragTimerId=f_core.GetWindow(doc).setTimeout(f_grid._TimerDragMove, f_grid._DRAG_TIMER);
		}
	},
	/**
	 * @method private static
	 */
	_TitleCursorDragStop: function(evt) {
		var column=f_grid._DragColumn;
		if (!column) {
			// Cela peut survenir si les stops sont enchainés ....
			return false;
		}
		
		var dataGrid=column._dataGrid;
				
		var doc=dataGrid.ownerDocument;
		
		if (dataGrid._dragTimerId) {
			f_core.GetWindow(doc).clearTimeout(dataGrid._dragTimerId);
			dataGrid._dragTimerId=undefined;
		}

		f_core.RemoveEventListener(doc, "mousemove", f_grid._TitleCursorDragMove, dataGrid);
		f_core.RemoveEventListener(doc, "mouseup",   f_grid._TitleCursorDragStop, dataGrid);

		doc.body.style.cursor=f_grid._DragOldCursor;
		f_grid._DragOldCursor=undefined;

		var ths=dataGrid._title.getElementsByTagName("th");
		for(var i=0;i<ths.length;i++) {
			ths[i].style.cursor=ths[i].oldCursorStyle;
			ths[i].oldCursorStyle=undefined;
		}
		
		column._restoreClass=undefined;
		
		f_grid._DragColumn=undefined;
		dataGrid._dragDeltaX=undefined;
		dataGrid._dragOriginX=undefined;
		dataGrid._dragMousePosition=undefined;
	},
	/**
	 * @method hidden static
	 * @param String text1
	 * @param String text2
	 * @return number
	 */
	Sort_Server: function(text1, text2) {
		// Pas d'implementation, car la fonction est filtrée avant !
	}
}
 
var __prototype = {
	
	f_grid: function() {
		this.f_super(arguments);
		
		this._rowsPool=new Array;
		this._cellsPool=new Array;
//		this._colsPool=new Array;
		
		this._showCursor=false;
		
		this._cellStyleClass="f_grid_cell";
		this._rowStyleClass="f_grid_row";
		
		this._resizable=f_core.GetBooleanAttribute(this, "v:resizable");
		
		this._initCursorValue=f_core.GetAttribute(this, "v:cursorValue");

		this._headerVisible=f_core.GetBooleanAttribute(this, "v:headerVisible", true);
	
		var rowStyleClass=f_core.GetAttribute(this, "v:rowStyleClass");
		if (rowStyleClass) {
			this._rowStyleClasses=rowStyleClass.split(",");
			
		} else  {
			this._rowStyleClasses=this.f_getDefaultRowStyleClasses();
		}
		
		this.f_initializeTableLayout();
			
		this.f_openActionList(f_event.MOUSEDOWN);
		this.f_openActionList(f_event.MOUSEUP);
		this.f_openActionList(f_event.SELECTION);

		var focus;
		if (f_core.IsInternetExplorer()) {
			if (!this.tabIndex) {
				this.tabIndex=0;
			}
			
			this.hideFocus=true;
			this.onfocus=f_grid._Link_onfocus;
			this.onblur=f_grid._Link_onblur;
			this.onkeydown=f_grid._Link_onkeydown;
			this.onkeypress=f_grid._Link_onkeypress;
			this.onkeyup=f_grid._Link_onkeyup;
			this.onmousewheel=f_grid._Link_onmousewheel;
			this._dataGrid=this;
			
		} else if (f_core.IsGecko()) {
			focus=f_core.GetChildByCssClass(this, "f_grid_dataBody_scroll");
			
			if (focus) {
				focus.onfocus=f_grid._Link_onfocus;
				focus.onblur=f_grid._Link_onblur;
				focus.onkeydown=f_grid._Link_onkeydown;
				focus.onkeypress=f_grid._Link_onkeypress;
				focus.onkeyup=f_grid._Link_onkeyup;
				focus._dataGrid=this;
				this._cfocus=focus;
				
			} else {
				this.onfocus=f_grid._Link_onfocus;
				this.onblur=f_grid._Link_onblur;
				this._cfocus=this;
				this._dataGrid=this;
			}
			
		} else {	
			var doc=this.ownerDocument;

			focus=doc.createElement("a");
			this._cfocus=focus;
			focus.className="f_grid_focus";
			focus.onfocus=f_grid._Link_onfocus;
			focus.onblur=f_grid._Link_onblur;
			focus.onkeydown=f_grid._Link_onkeydown;
			focus.onkeypress=f_grid._Link_onkeypress;
			focus.onkeyup=f_grid._Link_onkeyup;
			focus.href=f_core.JAVASCRIPT_VOID;
			focus._dataGrid=this;
		
			if (this.tabIndex) {
				focus.tabIndex=this.tabIndex;
			}
			
			table.appendChild(focus);
		}
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._performKeyDown);		
		
		var styleSheetBase=f_env.GetStyleSheetBase();

		this._blankImageURL=styleSheetBase+f_grid._BLANK_IMAGE_URL;
		f_imageRepository.PrepareImage(this._blankImageURL);
	},
	f_finalize: function() {
	
		if (f_grid._DragColumn) {
			f_grid._TitleCursorDragStop();
		}
	
		if (this._rowsPool) {
			this.f_releaseRows();
			this._rowsPool=undefined;
		}
	
		if (this._cellsPool) {
			this.f_releaseCells();
			this._cellsPool=undefined;
		}
/*	
		if (this._colsPool) {
			this._releaseCols(this._colsPool);
			this._colsPool=null;
		}
	*/	

//		this._headerVisible=undefined; // boolean
//		this._sb=undefined; // boolean
//		this._cellStyleClass=undefined; // String
//		this._rowStyleClass=undefined; // String
	
//		this._showCursor=undefined; // boolean
	
//		this._endRowIndex=undefined; // number
		this._shadowRows=undefined; // HtmlTRElement[]
//		this._waitingMode=undefined; // number
//		this._visibleColumnsCount=undefined; // number
//		this._titleLayout=undefined; // boolean
//		this._documentComplete=undefined; // boolean

//		this._blankImageURL=undefined; // string
//		this._columnsStyleClass=undefined; // string
//		this._waitingIndex=undefined; // number
//		this._waitingSelection=undefined; // number

//		this._partialWaiting=undefined; // boolean
// 		this._loading=undefined; // boolean
		this._waiting=undefined; // 
		this._waitingRow=undefined; // HTMLTrElement
//		this._waitingLoading=undefined; // boolean
		this._pagedWaiting=undefined; // 

		this._currentSorts=undefined; // HTMLColElement
//		this._columnCanBeSorted=undefined; // boolean
		
//		this._createFakeTH=undefined; //  boolean
	
//		this._oldHeight=undefined;  // string
//		this._oldHeightStyle=undefined; // string
	
		if (this._tbody) {
			f_core.VerifyProperties(this._tbody);
			this._tbody=undefined;
		}

		var table=this._table;
		if (table) {		
			this._table=undefined;
	
			table._dataGrid=undefined;
			
			if (table!=this) {
				f_core.VerifyProperties(table);
			}
		}
		
		if (this._columns) {
			this._releaseColumns();
			this._columns=undefined;
		}
		this._columnOver=undefined; // HTMLColElement
		this._columnSelected=undefined;  // HTMLColElement
//		this._rowValueColumnIndex=undefined; // string
		
		var cfocus=this._cfocus;
		if (cfocus) {
			this._cfocus=undefined;

			cfocus.onfocus=null;
			cfocus.onblur=null;
			cfocus.onkeydown=null;
			cfocus.onkeypress=null;
			cfocus.onkeyup=null;
			cfocus.onmousewheel=null;
			cfocus._dataGrid=undefined;
	
		} else {
			this.onfocus=null;
			this.onblur=null;
			this.onkeydown=null;
			this.onkeypress=null;
			this.onkeyup=null;
			this.onmousewheel=null;
			this._dataGrid=undefined;
		}
		
		var scrollBody=this._scrollBody;
		if (scrollBody) {
			this._scrollBody=undefined;
			
			scrollBody.onscroll=null;
			scrollBody.onmousedown=null;
			scrollBody.onmouseup=null;
			scrollBody.onclick=null;
			scrollBody._dataGrid=undefined;
			
			if (scrollBody!=this) {
				f_core.VerifyProperties(scrollBody);
			}
		}

		var title=this._title;
		if (title) {
			this._title=undefined;
			
			if (title!=this) {
				f_core.VerifyProperties(title);
			}
		}
		
		var scrollTitle=this._scrollTitle;
		if (scrollTitle) {
			this._scrollTitle=undefined;
			
			scrollTitle.onscroll=null;
			scrollTitle._dataGrid=undefined;
			
			if (scrollTitle!=this) {
				f_core.VerifyProperties(scrollTitle);
			}
		}
		
		// Il faut tester le verify properties qu'a la fin des undefined !
		if (cfocus && cfocus!=this) {	
			f_core.VerifyProperties(cfocus);
		}
		
		// this._cursor=undefined; // fait dans f_releaseRows()

		
//		this._initSort=undefined;  // boolean
//		this._resizable=undefined; // boolean

		this.f_super(arguments);			
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.DBLCLICK:
		case f_event.SELECTION: 
		case f_event.BLUR:
		case f_event.FOCUS:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
			return;
		}
		
		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.DBLCLICK:
		case f_event.SELECTION: 
		case f_event.BLUR:
		case f_event.FOCUS:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
			return;
		}
		
		this.f_super(arguments, type, target);
	},
	/**
	 * @method protected
	 */
	f_getEventLocked: function(evt, showAlert) {
		if (this._loading) {
			if (showAlert!==false) {
				var resourceBundle=f_resourceBundle.Get(f_grid);
			
				alert(resourceBundle.f_get("EVENT_LOCKED"));
			}
			return true;
		}
		
		return this.f_super(arguments, evt, showAlert);
	},
	f_getMainStyleClass: function() {
		return "f_grid";
	},
	/** 
	 * @method protected
	 * @return String[]
	 */
	f_getDefaultRowStyleClasses: function() {
		return f_grid._DEFAULT_ROW_STYLE_CLASSES;
	},
	f_serialize: function() {
		
		if (this._resizable && this._titleLayout) {
			var columns=this._columns;
			var v="";
			for(var i=0;i<columns.length;i++) {
				var col=columns[i];

				if (!col._visibility || !col._resizable) { 
					continue;
				}

				if (v) {
					v+=",";
				}
				
				v+=col._col.offsetWidth;
			}
			this.f_setProperty(f_prop.COLUMN_WIDTHS, v);
		}

		var cursor=this._cursor;
		var cursorValue=null;
		if (cursor) {		
			cursorValue=this.fa_getElementValue(cursor);
			if (typeof(cursorValue)=="number") {
				cursorValue=String(cursorValue);
			}
		}
		
		this.f_setProperty(f_prop.CURSOR, cursorValue);
	
		return this.f_super(arguments);
	},

	f_update: function() {
		var rowCount=this._rowCount;
		
		if (this._rows>0 && !this._paged) {
			// Pas de mode page,
			// On affiche un wait !
			
			if (rowCount>=0) {
				// On affiche des lignes vides ....
				
				this._waitingMode=f_grid.ROWS_WAITING;
				
				this.f_addWaitingRows();
				
			} else {
				this._waitingMode=f_grid.END_WAITING;

				this.f_addPagedWait();
			}
			
		} else {
			this._waitingMode=f_grid.FULL_WAITING
		}


		f_core.Debug(f_grid, "f_update: Set waiting mode to '"+this._waitingMode+"' (rows="+this._rows+" paged="+this._paged+" rowCount="+this._rowCount+")");

		if (this._tbody && !f_core.GetParentNode(this._tbody)) {
//			f_core.Assert(this._tbody.parentNode!=this._table, "Tbody has not been detached !");
// C'est normal dans un componentsGrid

			this._table.appendChild(this._tbody);

			this.fa_initializeScrollBars();
		}
		
		this.f_super(arguments);
		
		if (this._initSort) {
			this._initSort=undefined;
			
			this._sortTable();
		}				

		var scrollBody=this._scrollBody;					
		// Des popups ?
		var menu=this.f_getSubMenuById(f_grid._BODY_MENU_ID);
		if (menu) {
			scrollBody.onmousedown=f_grid._BodyMouseDown;
			scrollBody.onmouseup=f_core.FiltredCancelJsEventHandler;
			scrollBody.onclick=f_core.FiltredCancelJsEventHandler;
		}					
	
		this.f_performPagedComponentInitialized();
		
		if (!this.f_isVisible()) {
			this.f_getClass().f_getClassLoader().addVisibleComponentListener(this);
			
		} else {
			// Visible !			
	
	/* On le fait sur le onload !
			if (!f_core.IsInternetExplorer()) {
				f_grid.UpdateTitle(this);
			}
			*/
		}
	},
	f_documentComplete: function() {
		this.f_super(arguments);

	//	this._documentComplete=true;

		if (!this.f_isVisible()) {
			return;
		}
		
		this.f_performComponentVisible();
	},
	/**
	 * @method hidden
	 */
	f_performComponentVisible: function() {
		f_core.Debug(f_grid, "f_performComponentVisible: rows="+this._rows+" paged="+this._paged+" rowCount="+this._rowCount);

		this.f_updateTitle();

		if (false && f_core.IsGecko()) {				
			if (this._table.parentNode.offsetHeight==0) {
				// BUG de layout de Firefox !
				this._table.parentNode.style.height=this._table.offsetHeight+"px";
			}
		}
		

		this.fa_initializeScrollBars();

		if (this._interactiveShow) {
			this.f_setFirst(this._first, this._cursor);			
		}
	},
	/**
	 * @method protected
	 */
	f_removePagedWait: function() {
		f_core.Assert(this._waitingMode==f_grid.END_WAITING, "f_grid.f_removePagedWait: Invalid waiting mode !");
		
		var waiting=this._pagedWaiting;
		if (waiting) {
			this._pagedWaiting=undefined;
			
			// Pas de destroy car des références peuvent trainer ...
			// f_classLoader.Destroy(waiting);
		}
							
		var waitingRow=this._waitingRow;
		if (waitingRow) {
			this._waitingRow=undefined;
			
			waitingRow.parentNode.removeChild(waitingRow);
		}
	},
	/**
	 * @method protected
	 */
	f_addWaitingRows: function() {
		f_core.Debug(f_grid, "f_addWaitingRows: rowCount="+this._rowCount+" rows="+this._rows);
		
		var doc=this.ownerDocument;
		
		if (this._rows==this._rowCount) {
//		alert("RowCount="+this._rowCount+"/"+this._rows);
			this._waitingRow=undefined;
			return;			
		}		
		
		var shadows=this._shadowRows;
		if (shadows) {
			this._waitingRow=shadows[0];
			return;
		}
		shadows=new Array;
		this._shadowRows=shadows;
				
		var body=this._tbody;
		
		var diff=this._rowCount-this._rows-shadows.length;
		for (var i=0;i<diff;i++) {
			var tr=doc.createElement("tr");
			shadows.push(tr);
			body.appendChild(tr);
			
			var td=doc.createElement("td");
			td.colSpan=this._visibleColumnsCount;
			td.className=this._cellStyleClass+"_shadow f_grid_cell_shadow";

			tr.appendChild(td);
			
			td.innerHTML="&nbsp;";
		}
				
		this._waitingRow=shadows[0];

		// On verifie qu'il n'est pas visible ?
		
		var dataGrid=this;
		f_core.GetWindow(doc).setTimeout(function() {
			dataGrid._verifyWaitingPosition();
		}, 100);
	},
	/**
	 * @method private
	 */
	f_addPagedWait: function() {
		f_core.Assert(this._waitingMode==f_grid.END_WAITING, "f_grid.f_addPagedWait: Invalid waiting mode !");
	
		f_core.Debug(f_grid, "f_addPagedWait: rowCount="+this._rowCount+" rows="+this._rows);
				
		var doc=this.ownerDocument;
		
		var poolSize=this._rowsPool.length;
		if (poolSize==this._rowCount) {
		//alert("RowCount="+this._rowCount+"/"+this._rows);
			return;
		}
		
		this.f_removePagedWait();
		
		var tbody=this._tbody;
		f_core.Assert(tbody, "f_grid.f_addPagedWait: No Tbody for dataGrid ???");
	
		var waitTR=doc.createElement("tr");
		tbody.appendChild(waitTR);
		this._waitingRow=waitTR;
		this._waitingLoading=undefined;
		
		var rowIdx=this._rowsPool.length;
		
		waitTR.className=this._rowStyleClasses[rowIdx % this._rowStyleClasses.length];
		
		var td=doc.createElement("td");
		waitTR.appendChild(td);
		td.colSpan=this._visibleColumnsCount;
	
		var waiting=f_waiting.Create(td, null, true);
 		this._pagedWaiting=waiting;
 		
 		waiting.f_show();

		// On verifie qu'il n'est pas visible ?
		
		var dataGrid=this;
		f_core.GetWindow(doc).setTimeout(function() {
			dataGrid._verifyWaitingPosition();
		}, 100);
	},
	
	/**
	 * @method private
	 */
	_performPagedLoading: function(evt, cursorIndex) {
		this._waitingLoading=true;
		
		this.f_appendCommand(function(dataGrid) {
			var poolSize=dataGrid._rowsPool.length;
		
			dataGrid.f_callServer(poolSize, 0, cursorIndex, undefined, true);
		});	
	},

	/**
	 * @method private
	 */
	_performRowsLoading: function(evt, endIndex) {
		this._waitingLoading=true;
		
		var index=this._endRowIndex;

		f_core.Debug(f_grid, "_performRowsLoading: endIndex="+endIndex+" cur="+index);

		if (index && endIndex<index) {
			return;
		}
	
		this._endRowIndex=endIndex;
	
		this.f_appendCommand(function(dataGrid) {			
			var index=this._endRowIndex;
			var poolSize=dataGrid._rowsPool.length;

			if (!index || index<poolSize) {
				f_core.Debug(f_grid, "_performRowsLoading.appendCommand: Ignore index="+index+" poolSize="+poolSize);
				
				dataGrid.f_processNextCommand();
				return;
			}
			
			this._endRowIndex=undefined;
		
			var length=index-poolSize;
			if (length<this._rows) {
				length=this._rows;
			}
			
			if (poolSize+length>=this._rowCount) {
				length=this._rowCount-poolSize;
			}
			
			f_core.Debug(f_grid, "_performRowsLoading.appendCommand: Call server poolSize="+poolSize+" length="+length+" end="+index+" rows="+this._rows);
			
			dataGrid.f_callServer(poolSize, length, index, undefined, true);
		});		
	},
	
	/* ****************************************************************** */
	
	/**
	 * @method hidden
	 */
	f_setColumns2: function() {
		var columns=new Array;
		this._columns=columns;

		var v=0;
		for(var i=0;i<arguments.length;) {
			var column=arguments[i++];			

			if (column) {				
				if (column._visibility===undefined) {
					column._visibility=true;
				}
				
				if (column._valueColumn) {
					this._rowValueColumnIndex=i-1;
				}				
				
				var defaultCellImage=column._defaultCellImageURL;
				if (defaultCellImage) {
					f_imageRepository.PrepareImage(defaultCellImage);				
				}
				
				if (!column._align) {
					column._align=f_grid._DEFAULT_ALIGNMENT;
				}
				
				if (this._resizable && column._resizable) {
					if (!column._minWidth || column._minWidth<1) {
						column._minWidth=f_grid._COLUMN_MIN_WIDTH;
					}
						
					if (!column._maxWidth || column._maxWidth<column._minWidth) {
						column._maxWidth=f_grid._COLUMN_MAX_WIDTH;
					}

					column._resizable=(column._minWidth>=f_grid._COLUMN_MIN_WIDTH && column._maxWidth>=column._minWidth);
				}
				
			} else {
				column=new Object;
				
				column._visibility=true;			
				column._align=f_grid._DEFAULT_ALIGNMENT;
			}
								
			column._index=columns.length;
			column._dataGrid=this;

			if (column._visibility) {
				v++
			}		
							
			columns.push(column);
		}

		this._visibleColumnsCount=v;
		
		this.f_updateColumnsLayout(columns);

		for(var i=0;i<columns.length;) {
			var column=columns[i++];			
			
			var sorter=column._sorter;
			if (sorter) {
				column._sorter=undefined;
				
				this._installSorter(column, sorter);
			}			
		}
		
	},
	/**
	 * @method private
	 * @return void
	 */
	_installSorter: function(column, method) {
		f_core.Assert(column, "f_grid._installSorter: Invalid column parameter '"+column+"'.");
	
		this._columnCanBeSorted=true;
		
		if (typeof(method)!="function") {
			try {
				method=eval(method);
				
			} catch (x) {
				f_core.Error(f_grid, "_installSorter: Can not eval sort method '"+method+"'.", x);
				
				throw x;
			}
			
			f_core.Assert(typeof(method)=="function", "f_grid._installSorter: Bad sort method for column '"+column._index+"' !");
		}
	
		column._method=method;
	},
	/**
	 * @method public
	 * @param Object row
	 * @return boolean
	 */
	f_isRowSelected: function(row) {
		return (row._selected)?true:false;
	},
	/**
	 * @method protected
	 */
	fa_updateElementStyle: function(row) {
		var suffix;
		if (this.f_isDisabled()) {
			suffix="_disabled"; //+"_disabled"; // La classe par du .f_grid_disabled

			if (row._selected) {	
				suffix+="_selected";
			}
			
		} else if (row._selected) {
			// Pas de classe avec parité !
			
			suffix="_selected";
			if (this._focus) {
				suffix+="_focus";
			}

		} else if (this._selectable) {
			suffix="_normal";
		}
		
		var className=this._rowStyleClass;
		var cl=className;
		var rowClassName=row._className;		
		if (rowClassName) {
			cl+=" "+rowClassName;
		}
		
		if (suffix) {
			cl+=" f_grid_row"+suffix;
		
			if (rowClassName) {
				cl+=" "+rowClassName+suffix;
			}
		}
		
		var updateFirstOnly=true;
		if (row.className!=cl) {
			row.className=cl;
			
			updateFirstOnly=false;
		}
		
		if (this._cursor==row) {
			if (!row._hasCursor) {
				row._hasCursor=true;
			}
			
		} else if (row._hasCursor) {	
			row._hasCursor=undefined;
		}
		
		this._updateCellsStyle(row, updateFirstOnly);
	},
	/**
	 * @method private
	 */
	_updateCellsStyle: function(row, firstOnly) {
		var td=row.firstChild;
		for(;td && td.tagName.toLowerCase()!="td";td=td.nextSibling);

		if (row._selected) {
			var className=this._cellStyleClass+" f_grid_cell_selected";
			
			var firstClassName=className+" f_grid_cell_left";
			if (row._hasCursor && this._focus && this._showCursor) {
				firstClassName+=" f_grid_cell_cursor";
			}
			
			for(var i=0;td;i++) {
				if (!i) {
					td.className=firstClassName;
				} else {
					td.className=className;
				}
				
				if (firstOnly) {
					break;
				}
				
				for(td=td.nextSibling;td && td.tagName.toLowerCase()!="td";td=td.nextSibling);
			}
			
			return;
		}
		
		var cols=this._columns;
		var idx=0;
		
		for(var i=0;i<cols.length && td;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}
			
			var cclassName=td._cellStyleClass;
			
			if (!cclassName) {
				var cellStyleClasses=col._cellStyleClasses;
				
				if (cellStyleClasses) {
					cclassName=cellStyleClasses[row._index % cellStyleClasses.length];
				}
			}
			
			className=this._cellStyleClass;
			if (cclassName) {
				className+=" "+cclassName;
			}

			if (!idx) {
				className+=" f_grid_cell_left";
				if (this._cursor==row && this._focus && this._showCursor) {
					className+=" f_grid_cell_cursor";
				}
			}
			
			if (td.className!=className) {
				td.className=className;
			}
			
			if (firstOnly) {
				break;
			}
			
			for(td=td.nextSibling;td && td.tagName.toLowerCase()!="td";td=td.nextSibling);			
			
			idx++;
		}
	},
	/**
	 * @method hidden
	 */
	f_setRowCount: function(rowCount) {
		this._rowCount=rowCount;
		this._maxRows=rowCount;
	},
	fa_updateDisabled: function(disabled) {
		if (!this.fa_componentUpdated) {
			return;
		}
		var suffix=(disabled)?"_disabled":null;
		
		this.className=this.f_computeStyleClass(suffix);
		
		var table=this._table;
		if (!table) {
			return;
		}
		var className="f_grid_table";
		if (disabled) {
			className+=" "+className+"_disabled";
		}
		table.className=className;
	
		var rows=this._table.rows;
		for(var i=0;i<rows.length;i++) {
			var row=rows[i];
			
			if (row._index===undefined) {
				continue;
			}
			
			var input=row._input;
			if (input) {
				input.disabled=disabled;
			}
			
			this.fa_updateElementStyle(row);
		}
		
		var cols=this._columns;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			
			if (!col._visibility) {
				continue;
			}
			
			this._updateTitleStyle(col);
		}			
	},
	f_onSelect: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	f_onMouseDown: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	f_onMouseUp: function(evt) { 
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}

		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	/**
	 * Returns the value of the row specified by its index.
	 *
	 * @method public
	 * @param any rowIndex Row object.
	 * @return String the key of the row.
	 */
	f_getRowValueAtIndex: function(rowIndex) {
		var row=this.f_getRow(rowIndex, true, true);
		if (!row) {
			return null;
		}

		return row._index;
	},
	/**
	 * Returns the value of the row.
	 *
	 * @method public
	 * @param any rowIndex Row object.
	 * @return String the key of the row.
	 */
	f_getRowValue: function(rowIndex) {
		var row=this.f_getRow(rowIndex, true);
		if (!row) {
			return null;
		}

		return row._index;
	},
	/**
	 * Returns the row associated to a value.
	 *
	 * @method public
	 * @param any value Value of the row, or a row object.
	 * @param hidden boolean Throws error if row is not found.
	 * @return Object row associated or <code>null</code>.
	 */
	f_getRowByValue: function(value, throwError) {
		f_core.Assert(value!==undefined && value!==null, "f_grid.f_getRowByValue: Invalid value '"+value+"'.");

		if (value._dataGrid) {
			return value;
		}
		
		var rows=this.fa_listVisibleElements();
		if (!rows) {
			f_core.Debug(f_grid, "f_getRowByValue: Empty rows to get row by value "+value);
			if (throwError) {
				throw new Error("Can not find row with value '"+value+"'.");
			}
			
			return null;
		}
	
		for(var i=0;i<rows.length;i++) {			
			var row=rows[i];
			
			if (row._index!=value) {
				continue;
			}
			
			return row;
		}
		
		f_core.Debug(f_grid, "f_getRowByValue: Can not find row by value "+value);
		
		if (throwError) {
			throw new Error("Can not find row with value '"+value+"'.");
		}
		return null;
	},
	/*
	 * Returns the index of the row associated to the specified value.
	 *
	 * @method public
	 * @param any rowValue Value of the row, or a row object.
	 * @return number Index of the row or <code>-1</code> if not found.
	 
	f_rowIndexOf: function(rowValue) {
		var rows=this._tbody.rows;
		if (!rows || rows.length<1) {
			return -1;
		}
	
		for(var i=0;i<rows.length;i++) {			
			var row=rows[i];
			
			if (row!=value && row._index!=value) {
				continue;
			}
			
			return i;
		}
		
		return -1;
	},
	*/
	/**
	 * @method protected
	 */
	f_getRow: function(rowIndex, throwError, indexByValue) {
		if (!this._tbody) {
			f_core.Debug(f_grid, "f_getRow: No body to get row #"+rowIndex);

			if (throwError) {
				throw new Error("Can not find row '"+rowIndex+"'.");
			}
			return null;
		}
		
		var row;
		
		if (typeof(rowIndex)!="number") {
			row=rowIndex;
			f_core.Assert(row._dataGrid, "f_grif.f_getRow: Object is not a row of a datagrid !");
			
		} else {
			var rows=this.fa_listVisibleElements(); //this._tbody.rows;
			if (!rows.length) {
				if (throwError) {
					throw new Error("Can not find row #"+rowIndex+". (no visible rows)");
				}
				return null;
			}
			
			var r;
			if (indexByValue) {
				if (rowIndex>=0 && rowIndex<rows.length) {
					r=rows[rowIndex];
				}
				
			} else {
				for(var i=0;i<rows.length;i++) {
					if (rows[i]._index!=rowIndex) {
						continue;
					}
					
					r=rows[i];
					break;
				}
			}
				
			if (r===undefined) {
				if (throwError) {
					var first=this._first;
					
					throw new Error("Row index out of ranges "+first+"<"+rowIndex+"<"+(rows.length+first)+", or row not found.");
				}
				return null;
			}
				
			row=r;
		}
		
		var cells=row._cells;
		if (!cells) {
			f_core.Debug(f_grid, "f_getRow: No cells for row #"+rowIndex);
			return null;
		}
		
		return row;
	},
	/**
	 * Specify the index of the first row which starts the grid.
	 *
	 * @method public
	 * @param number index
	 * @param number cursorIndex The cursor index. (can be undefined)
	 * @return boolean Returns <code>false</code>.
	 */
	f_setFirst: function(index, cursorIndex, selection) {
		var oldFirst=this._first;
		
		this.f_setProperty(f_prop.FIRST, index);
		if (cursorIndex) {
			this.f_setProperty(f_prop.CURSOR, cursorIndex);
		}
	
		if (this._interactive) {
			this.f_appendCommand(function(dataGrid) {			
				dataGrid.f_callServer(index, 0, cursorIndex, selection);
			});
			
			return false;
		}

		f_core._Submit(null, this, f_event.CHANGE);
			
		return false;
	},
	f_refreshContent: function() {
		if (!this._interactive) {
			return false;
		}
		
		this.f_appendCommand(function(dataGrid) {
			dataGrid.f_callServer(0);
		});

		return true;
	},
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * @method hidden
	 */
	fa_cancelFilterRequest: function() {
		// Appeler par la génération du serveur !
	},
	_releaseColumns: function() {
		var columns=this._columns;
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];
			
			var head=column._head;
			if (head) {
				column._head=undefined;

				head._column=undefined;
				head.onmouseout=null;
				head.onmouseover=null;
				head.onmousedown=null;
				head.onmouseup=null;
				
				f_core.VerifyProperties(head);
				
			}
			var cursor=column._cursor;
			if (cursor) {
				column._cursor=undefined;

				cursor._column=undefined;

				cursor.onmousedown=null;
				cursor.onclick=null;
				
				f_core.VerifyProperties(cursor);
			}
			
			var col=column._col;
			if (col) {
				column._col=undefined; // HTMLColElement

				f_core.VerifyProperties(col);
			}
			
			var col=column._col2;
			if (col) {
				column._col2=undefined; // HTMLColElement

				f_core.VerifyProperties(col);
			}
			
//			column._align=undefined; // String
			column._tcol=undefined; // HTMLThElement
			column._tcell=undefined;
			column._box=undefined; // HTMLDivElement
			column._label=undefined; // HTMLDivElement
			column._image=undefined; // HTMLImageElement
			
//			column._titleImageURL=undefined; // String
//			column._titleDisabledImageURL=undefined; // String
//			column._titleHoverImageURL=undefined; // String
//			column._titleSelectedImageURL=undefined; // String
			
			// column._index=undefined; // number
			column._dataGrid=undefined; // f_grid
			// column._id=undefined; // String
			column._method=undefined; // function
			// column._visibility=undefined; // boolean
			// column._cellStyle=undefined; // String
			// column._cellToolTipText=undefined; // String
			// column._autoFilter=undefined; // boolean
			// column._ascendingOrder=undefined; // boolean
			
//			column._cellImage=undefined; // boolean
//			column._defaultCellImageURL=undefined; // String
			
			f_core.VerifyProperties(column);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_releaseRows: function() {
		this._cursor=undefined; // HTMLRowElement

		var list=this._rowsPool;
		if (!list || !list.length) {
			return;
		}
		this._rowsPool=new Array;
		
		this._releaseRow.apply(this, list);
	},
	_releaseRow: function() {
		for(var i=0;i<arguments.length;i++) {
			var row=arguments[i];
			
			if (row._index===undefined) {
				continue;
			}

			var input=row._input;
			if (input) {
				row._input=undefined;				

				input._row=undefined;  // HtmlTRElement
				input.onmousedown=null;
				input.onmouseup=null;
				input.onclick=null;
				input.ondblclick=null;
				input.onfocus=null;
				
				f_core.VerifyProperties(input);
			}
										
//			row._className=undefined; // string
			row._cells=undefined; // HtmlElement[]
			row._label=undefined;  // HtmlLabelElement
			row._dataGrid=undefined;  // f_grid
//			row._index=undefined; // string
//			row._cellsStyleClass=undefined; // string
//			row._selected=undefined; // boolean
			row._cellImages=undefined; // HTMLImageELement[]
//			row._hasCursor=undefined; // boolean
			
			row.onmousedown=null;
			row.onmouseup=null;
			row.onclick=null;
			row.ondblclick=null;
			row.onmouseout=null;
			row.onmouseover=null;
			row.onfocus=null;

		//	f_core.VerifyProperties(row);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_releaseCells: function() {
		var list=this._cellsPool;
		if (!list || !list.length) {
			return;
		}

		var cell;
		while(cell=list.pop()) {
			cell.onmouseup=null;
			cell.onmousedown=null;
			cell.ondblclick=null;
			cell.onclick=null;
			cell.onfocus=null;
			
//			cell._className=undefined; // string
			cell._dataGrid=undefined; // f_grid
//			cell._cellStyleClasses=undefined; // string[]
//			cell._text=undefined; // string

		//	f_core.VerifyProperties(cell);
		}
	},
	/**
	 * @method private
	 * @param f_event evt
	 * @return boolean
	 */
	_performKeyDown: function(evt) {
		var jsEvent=evt.f_getJsEvent();
		
		return this.f_performKeyDown(jsEvent);
	},
	/**
	 * @method protected
	 * @param Event evt
	 * @return boolean
	 */
	f_performKeyDown: function(evt) {
		var cancel=false;

		var selection=fa_selectionManager.ComputeKeySelection(evt);
		
		var code=evt.keyCode;
		switch(code) {
		case f_key.VK_DOWN: // FLECHE VERS LE BAS
			this._nextCursorRow(evt, selection);
			cancel=true;
			break
						
		case f_key.VK_UP: // FLECHE VERS LE HAUT
			this._previousCursorRow(evt, selection);
			cancel=true;
			break;

		case f_key.VK_PAGE_DOWN: // FLECHE VERS LE BAS
			this._nextPageRow(evt, selection);
			cancel=true;
			break;
						
		case f_key.VK_PAGE_UP: // FLECHE VERS LE HAUT
			this._previousPageRow(evt, selection);
			cancel=true;
			break;

		case f_key.VK_END: // FIN
			this._selectLastRow(evt, selection);
			cancel=true;
			break;
						
		case f_key.VK_HOME: // HOME
			this._selectTopRow(evt, selection);
			cancel=true;
			break;

		case f_key.VK_SPACE:
			if (this._checkable) {
				var cursor=this._cursor;
				if (cursor) {
					this.fa_performElementCheck(cursor, true, evt, !this.fa_isElementChecked(this._cursor));
				}
				cancel=true;
				break;
			}
				
			// Continue comme une selection ....
			
		case f_key.VK_RETURN:
		case f_key.VK_ENTER:
			if (this._cursor && this._selectable) {
				this._performElementSelection(this._cursor, true, evt, selection);
			}
			cancel=true;
			break;

		case f_key.VK_CONTEXTMENU:
			this._openContextMenu(evt);
			cancel=true;
			break;
		}
	
		if (cancel) {
			return f_core.CancelJsEvent(evt);
		}
		
		return true;
	},
	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_openContextMenu: function(evt) {
		if (!this._cursor) {
			return;
		}
		
		var menu=this.f_getSubMenuById(f_grid._ROW_MENU_ID);
		if (menu) {
			menu.f_open(evt, {
				component: this._cursor,
				position: f_popup.LEFT_COMPONENT,
				deltaX: 4,
				deltaY: 4
				});
		}
	},
	_updateCurrentSelection: function() {
		var cursorRow=this._cursor;

		if (this._selectable) {
			var currentSelection=this._currentSelection;
			for(var i=0;i<currentSelection.length;i++) {
				var r=currentSelection[i];
				if (cursorRow==r) {
					cursorRow=undefined;
				}
				
				this.fa_updateElementStyle(r);
			}
		}
				
		if (cursorRow) {
			this.fa_updateElementStyle(cursorRow);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_forceFocus: function() {
		f_core.Debug(f_grid, "f_forceFocus: force focus="+this._focus);

		if (this._focus || this._ignoreFocus) {
			return;
		}
		
		var cfocus=this._cfocus;
		if (cfocus) {
			cfocus.style.top=this._table.scrollTop+"px";
			
			cfocus.focus();
			return;
		}

		this.focus();
	},
	f_setFocus: function() {
		f_core.Debug(f_grid, "f_setFocus: set focus="+this._focus);
		
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}
		if (this._focus || this.f_isDisabled()) {
			return;
		}

		var cfocus=this._cfocus;
		if (cfocus && typeof(cfocus.focus)=="function") {
			cfocus.focus();
			return true;
		}
		
		if (typeof(this.focus)=="function") {
			this.focus();
		}
		return true;
	},
	_nextCursorRow: function(evt, selection) {
		var trs=this._rowsPool;

		var tr=this._cursor;
		if (!tr) {
			// Selection du premier cursor

			for(var i=0;i<trs.length;i++) {
				var tr=trs[i];
				if (!tr._dataGrid) {
					continue;
				}
				
				this.f_moveCursor(tr, true, evt, selection);
				
				return;
			}

			return;
		}
		
		for(tr=tr.nextSibling;tr;tr=tr.nextSibling) {
			if (!tr._dataGrid) {
				continue;
			}
			
			// Si le CONTROL est appuyé on ne bouge que le curseur !
			this.f_moveCursor(tr, true, evt, selection);
			
			return;
		}
		
		// Page suivante ?
		if (!this._rows) {
			// Pas de page
			return;
		}
			
		var nextFirst=this._first+this._rows;
		
		if (!this._paged) { // Rows est défini, mais nous ne sommes pas en mode page !
			var waitingRow=this._waitingRow;
			if (waitingRow) {
				this._performPagedLoading(evt, nextFirst);

				var scrollBody=this._scrollBody;
				
				var pos=waitingRow.offsetTop+waitingRow.offsetHeight-scrollBody.offsetHeight;
				scrollBody.scrollTop=pos;
			}
			
			return;
		}
		
		if (this._rowCount>=0) {
			if (nextFirst>=this._rowCount) {
				return;
			}
		} else {
			if (nextFirst>this._maxRows) {
				return;
			}
		}
				
		this.f_setFirst(nextFirst, nextFirst, selection);
	},
	_previousCursorRow: function(evt, selection) {		
		var trs=this._rowsPool;

		if (!this._cursor) {
			// Selection du dernier

			for(var i=trs.length-1;i>=0;i--) {
				if (!trs[i]._dataGrid) {
					continue;
				}
				
				this.f_moveCursor(trs[i], true, evt, selection);
				
				return;
			}

			return;
		}
		
		for(var i=0;i<trs.length;i++) {
			if (trs[i]!=this._cursor || !trs[i]._dataGrid) {
				continue;
			}
			
			
			for(i--;i>=0;i--) {
				if (trs[i]._dataGrid) {
					break;
				}
			}
			
			if (i<0) {
				break;
			}
			
			this.f_moveCursor(trs[i], true, evt, selection);
			
			return;
		}
		
		// Page pr?cedente ?
		if (!this._rows || !this._paged) {
			// Pas de page
			return;
		}
		
		if (this._first<1) {
			return;
		}
		
		var nextFirst=this._first-this._rows;
		if (nextFirst<0) {
			nextFirst=0;
		}
		
		var nextPos=nextFirst+this._rows-1;
		if (nextPos>=this._rowCount) {
			nextPos=this._rowCount-1;
		}
		
		this.f_setFirst(nextFirst, nextPos, selection);
	},
	_nextPageRow: function(evt, selection) {
		var trs=this._rowsPool;
		if (!trs.length) {
			return;
		}

		// Il faut rechercher le dernier visible !
		var last=null;
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			if (!row._dataGrid) {
				continue;
			}
			
			if (row.offsetTop+row.offsetHeight/2-this._scrollBody.scrollTop>this._scrollBody.clientHeight) {
				// On le voit plus !
				break;
			}		
			
			last=row;
		}
		
		if (last && last!=this._cursor) {
			this.f_moveCursor(last, true, evt, selection);
			return;			
		}
		
		if (this._rows && this._paged) {
			// Table Page par Page
			var bottom;
			for(var i=0;i<trs.length;i++) {
				if (!trs[i]._dataGrid) {
					continue;
				}
				
				bottom=trs[i];
			}
			
			if (bottom) {
				if (this._cursor!=bottom) {
					this.f_moveCursor(bottom, true, evt, selection);
					return;
				}
			}
					
			// Page suivante ...
			
			if (this._rows<1) {
				return;
			}
				
			var nextFirst=this._first+this._rows;
			if (this._rowCount>=0) {
				if (nextFirst>=this._rowCount) {
					return;
				}
			} else {
				if (nextFirst>this._maxRows) {
					return;
				}
			}
						
			var nextPos=nextFirst+this._rows-1;
			if (nextPos>=this._rowCount) {
				nextPos=this._rowCount-1;
			}
			
			this.f_setFirst(nextFirst, nextPos, selection);
		
			return;	
		}
		
		// On recherche notre index, et la hauteur d'une ligne
		var trh=0;
		var idx=-1;
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			
			if (!row._dataGrid) {
				continue;
			}
			
			if (trh<1) {
				trh=row.offsetHeight;
			}

			if (row==this._cursor) {
				idx=i;
			}
			
			if (idx>=0 && trh>0) {
				break; // On a trouvé l'index et la hauteur d'une ligne
			}
		}
		if (trh<=0 || idx<0) {
			// On a pas trouvé notre index, ou la hauteur d'une ligne
			return;
		}
		
		var h=this._scrollBody.clientHeight;

		// 
		var pos=Math.floor(idx+h/trh);
		
		f_core.Debug(f_grid, "_nextPageRow: Pos="+pos+" idx="+idx+" h="+h+" trh="+trh+" rowCount="+this._rowCount+" trs="+trs.length);
		
		if (pos>=trs.length) {
			pos=trs.length-1;
		}		
//		f_core.Assert(pos>=trs.length, "Invalid position !");

		var row=null;
		
		// On cherche juste apres !
		for(var i=pos;i<trs.length;i++) {
			var r=trs[i];
			
			if (!r._dataGrid) {
				continue;
			}
			
			row=r;
			f_core.Debug(f_grid, "_nextPageRow: Found #"+i+" pos="+pos+" next row="+row);
			break;
		}
		
		f_core.Debug(f_grid, "_nextPageRow: Found next row="+row);
		
		if (!row) {
			// Pas trouvé, alors on cherche juste avant !
			for(var i=pos-1;i>=0;i--) {
				var r=trs[i];
				
				if (r._dataGrid) {
					row=r;
					break;
				}			
			}

			f_core.Debug(f_grid, "_nextPageRow: Found previous row="+row);
		}
		
		//alert("Paged="+this._paged+" pos="+pos+" trs.length="+trs.length);
		if (!this._paged && pos==trs.length-1) {		
			var waitingRow=this._waitingRow;

			//f_core.Debug(f_grid, "Waiting row="+waitingRow);
			if (waitingRow) {
				this._performPagedLoading(evt, pos);

				var scrollBody=this._scrollBody;
				
				var pos=waitingRow.offsetTop+waitingRow.offsetHeight-scrollBody.offsetHeight;
				scrollBody.scrollTop=pos;
			} else {
				// Pas de waiting row ?
			}
		}
		
		this.f_moveCursor(row, true, evt, selection);
	},
	_previousPageRow: function(evt, selection) {
		var trs= this._rowsPool;
		if (!trs.length) {
			return;
		}

		// Il faut rechercher le dernier visible !
		var last=null;
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			if (!row._dataGrid) {
				continue;
			}
			
			if (row.offsetTop+row.offsetHeight/2-this._scrollBody.scrollTop<0) {
				continue;
			}
			
			last=row;
			break;
		}
		
		if (last && last!=this._cursor) {
			this.f_moveCursor(last, true, evt, selection);
			return;			
		}

		if (this._rows && this._paged) {
			// Page pr?c?dante ...
	
			if (this._first<=0) {
				return;
			}
			
			var nextFirst=this._first-this._rows;
			if (nextFirst<0) {
				nextFirst=0;
			}
			
			this.f_setFirst(nextFirst, nextFirst, selection);
		
			return;	
		}
		
		// On recherche la position du pr?c?dant !
		var trh=0;
		var idx=-1;
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			
			if (!row._dataGrid) {
				continue;
			}
			
			if (trh<1) {
				trh=row.offsetHeight;
			}

			if (row==this._cursor) {
				idx=i;
			}
			
			if (idx>=0 && trh>0) {
				break;
			}
		}
		if (trh<=0 || idx<0) {
			return;
		}
		var h=this._scrollBody.clientHeight;

		var pos=Math.floor(idx-h/trh);
		if (pos<0) {
			pos=0;
		}		

		var row;
		for(var i=0;pos>=0;i++) {
			row=trs[i];
			
			if (!row._dataGrid) {
				continue;
			}
			
			pos--;
		}
		
		this.f_moveCursor(row, true, evt, selection);
	},
	_selectLastRow: function(evt, selection) {
		var trs= this._rowsPool;
		if (!trs.length) {
			return;
		}

		// Il faut rechercher le dernier visible !
		var last=null;
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			if (!row._dataGrid) {
				continue;
			}
			
			last=row;
		}
		
		
		if (!last) {
			return;
		}
		
		if (last!=this._cursor) {	
			this.f_moveCursor(last, true, evt, selection);
			return;
		}
		
		var rowCount=this._rowCount; // Nombre total 
		var rows=this._rows; // Nombre a afficher  (0= pas de mode page)
		
		// Le meme .... ben on va à la derniere page			
		if (rowCount<rows || rows<1 || !this._paged) {
			return;
		}
		
		var nextFirst=rowCount-((rowCount+rows-1) % rows)-1;
		if (nextFirst<=this._first) {
			return;
		}
		
		var nextPos=rowCount-1;
		
		this.f_setFirst(nextFirst, nextPos, selection);
	},
	_selectTopRow: function(evt, selection) {
		var trs= this._rowsPool;
		if (!trs.length) {
			return;
		}

		// Il faut rechercher le premier visible !
		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			if (!row._dataGrid) {
				continue;
			}
			
			if (row==this._cursor) {
				break;
			}
			
			this.f_moveCursor(row, true, evt, selection);
			return;
		}
		
		// Le meme .... ben on va ? la premiere page
			
		if (this._first<1 || !this._paged) {
			return;
		}
		
		var nextFirst=0;
		var nextPos=0;
		
		this.f_setFirst(nextFirst, nextPos, selection);
	},
	/**
	 * @method hidden
	 */
	f_enableSorters: function(sortColumnIndex1, ascending1, sortColumnIndex2, ascending2) {
		var cols=this._columns;
		var currentSorts=this._currentSorts;
		if (!currentSorts) {
			currentSorts=new Array;
			this._currentSorts=currentSorts;
		}

		for(var i=0;i<arguments.length;) {		
			var sortColumnIndex=arguments[i++];
			var ascending=(arguments[i++])?true:false;
			
			f_core.Assert(sortColumnIndex>=0 && sortColumnIndex<cols.length, "Bad sortColumnIndex !");
	
			var col=cols[sortColumnIndex];
			col._ascendingOrder=ascending;
			
			currentSorts.push(col);
			
			this._updateTitleStyle(col);
			
			if (col._method!=f_grid.Sort_Server) {
				this._initSort=true;
			}
		}
	},
	/**
	 * @method public
	 * @param Object col Column to sort
	 * @param optional boolean ascending Sort ascending.
	 * @return void
	 */
	f_setColumnSort: function(col, ascending, append) {
		var args=[false];
		
		if (ascending===undefined) {
			if (col._ascendingOrder===undefined) {
				ascending=true;
			} else {
				ascending=!col._ascendingOrder;
			}
		}
		
		var currentSorts=this._currentSorts;
		if (!currentSorts) {
			currentSorts=new Array;
			this._currentSorts=currentSorts;
		}

		f_core.Debug(f_grid, "f_setColumnSort: Sort col="+col._index+" ascending="+ascending+" append="+append);

		if (currentSorts.length) {
			if ((append || currentSorts.length==1) && currentSorts[currentSorts.length-1]==col) {
				// f_core.Debug(f_grid, "f_setColumnSort: Just inverse");
				col._ascendingOrder=ascending;
		
				this._updateTitleStyle(col);
				this._sortTable();
				return;				
			}
		}
		
		if (!append && currentSorts.length) {
		
			f_core.Debug(f_grid, "f_setColumnSort: Remove olds");
		
			for(var i=0;i<currentSorts.length;i++) {
				var old=currentSorts[i];
				
				old._ascendingOrder=undefined;
				this._updateTitleStyle(old);
			}
			
			currentSorts=new Array;
			this._currentSorts=currentSorts;
		}
		
		if (!f_core.AddElement(currentSorts, col)) {
			// Déjà connu !
	
			// f_core.Debug(f_grid, "f_setColumnSort: Already known ???");
			
			if (col._ascendingOrder==ascending) {
				// Et dans le même sens !
				return;
			}				
		}

		f_core.Debug(f_grid, "f_setColumnSort: Change order '"+ascending+"'");
		
		col._ascendingOrder=ascending;
		this._updateTitleStyle(col);
		
		this._sortTable();
	},
	/** 
	 * @method private
	 */
	_updateTitleStyle: function(column) {
		f_core.Assert(column && column._head, "f_grid._updateTitleStyle: Invalid column parameter ("+column+")");

		var suffix="";
		if (this.f_isDisabled()) {
		  // rien

		} else if (this._columnSelected==column) {
			suffix+="_selected";

		} else if (this._columnOver==column) {
			suffix+="_over";
		}

		var tcellClassName="f_grid_tcell";
		var columnStyleClass=column._styleClass;
		
		var className=tcellClassName;
		if (columnStyleClass) {
			className+=" "+columnStyleClass;
		}
		if (suffix) {
			className+=" "+tcellClassName+suffix;
			if (columnStyleClass) {
				className+=" "+columnStyleClass+suffix;
			}			
		}
		
		if (column._method) {
			className+=" "+tcellClassName+"_sorter";
		}

		var head=column._head;
		if (head.className!=className) {
			head.className=className;
		}
	
		className="f_grid_ttext";
		
		var stextClassName="f_grid_stext";
		
		var suffix="";
		var wc=className;
		if (column._ascendingOrder!==undefined) {
			if (column._ascendingOrder) {
				suffix="_ascending";

			} else {
				suffix="_descending";
			}
			className+=" "+className+suffix;
			stextClassName+=" "+stextClassName+suffix;
		}
		
		var cw=column._col.style.width;
		if (!cw) {
			cw=column._head.width;
			
			if (!cw) {
				cw=column._width;
			}
		}
					
		var swidth=parseInt(cw, 10);
		
		swidth-=f_grid._TEXT_RIGHT_PADDING;
		if (swidth<0) {
			swidth=0;
		}
		
//		document.title="swidth='"+swidth+"' cur='"+column._label.style.width+"' col="+column._col.style.width;
		
		var box=column._box;
		var label=column._label;

		var sw=swidth+"px";
		if (box.style.width!=sw) {
			box.style.width=sw;
		}			
	
		if (suffix) {
			swidth-=f_grid._SORT_PADDING;
			if (swidth<0) {
				swidth=0;
			}
		}
		
		var sw=swidth+"px";
		if (label.style.width!=sw) {
			label.style.width=sw;
		}			
			
		if (column._restoreClass) {
			column._restoreClass=className;
			className=wc;
		}
		
		if (label.className!=className) {
			label.className=className;
		}
		if (box.className!=stextClassName) {
			box.className=stextClassName;
		}
		
		var image=column._image;
		if (image) {
			var imageURL;

			if (this.f_isDisabled()) {
				imageURL=column._titleDisabledImageURL;				
			
			} else if (this._columnSelected==column) {
				imageURL=column._titleSelectedImageURL;
	
			} else if (this._columnOver==column) {
				imageURL=column._titleHoverImageURL;
			}
			
			if (!imageURL) {
				imageURL=column._titleImageURL;
			}
			
			if (image.src!=imageURL) {
				image.src=imageURL;
			}
		}
	},
	fa_updateFilterProperties: function(filterProperties) {
		if (!this._interactive) {
			return false;
		}
		
		this.f_appendCommand(function(dataGrid) {
			if (dataGrid._rows>0) {
				// Page par page !
				// On ne sait plus le nombre de lignes ...
				dataGrid._rowCount=-1;
				dataGrid._maxRows=dataGrid._rows;
			}
			
			dataGrid.f_callServer(0);
		});
		
		return false;
	},	
	/**
	 * @method protected
	 */
	fa_componentCaptureMenuEvent: function() {
		return null;
	},
	/** 
	 * @method private
	 * @return boolean
	 */
	_verifyWaitingPosition: function(evt) {
		var scrollBody=this._scrollBody;

		var waitingRow=this._waitingRow;
		if (waitingRow && f_core.GetParentNode(waitingRow)) {
			var sbTop=scrollBody.scrollTop+scrollBody.offsetHeight;
		
			var pos=waitingRow.offsetTop-sbTop;
			
			if (pos<0) {
				if (this._waitingMode==f_grid.END_WAITING) {
					if (!this._waitingLoading) {
						this._performPagedLoading(evt);
					}

				} else {				
					// Recherche le dernier index visible !
					
					var nodes=waitingRow.parentNode.childNodes;
					
					var i=0;
					for(;i<nodes.length;i++) {
						var node=nodes[i];
						
						if (node.offsetTop>sbTop) {
							break;
						}
					}
					
					if (this._waitingLoading) {
						this._performRowsLoading(evt, i);
						
					} else { // Pour eviter que l'on lance le download, on attend que ca se stabilise
						this._waitingLoading=true;
						
						i+=Math.floor(this._rows/2); // On prend une petite marge ...
						
						var dataGrid=this;
						f_core.GetWindow(dataGrid.ownerDocument).setTimeout(function() {
							dataGrid._performRowsLoading(evt, i);
						}, 100);
					}
				}
			
			}
		}
		
		return true;
	},
	/**
	 * @method private
	 * @return void
	 */
	_sortTable: function() {
		var currentSorts=this._currentSorts;
		if (!currentSorts || !currentSorts.length) {
			return;
		}

		var methods=new Array;
		var tdIndexes=new Array;
		var ascendings=new Array;
		
		var serverSort=false;
		var columns=this._columns;
		
		var serial="";	
				
		for(var i=0;i<currentSorts.length;i++) {
			var col=currentSorts[i];
			
		 	var method=col._method;
		 	methods.push(method);
		 	if (method==f_grid.Sort_Server) {
		 		serverSort=true;
		 	}
		 	
		 	ascendings.push(col._ascendingOrder);
		 	
		 	var columnIndex=col._index;
		 	var tdIndex=0;
			for(var j=0;j<columns.length;j++) {
				var col=columns[j];
				if (!col._visibility) {
					continue;
				}
	
				if (columnIndex==j) {
					break;	
				}
	
				tdIndex++;
			}
			tdIndexes.push(tdIndex);
			
			if (serial) {
				serial+=",";
			}
			serial+=col._index+","+col._ascendingOrder;
		}
		
	
		this.f_setProperty(f_prop.SORT_INDEX, serial);
			
		if (this._rowCount<0 || (this._rows && this._rows<this._rowCount) || serverSort) {
			// Plusieurs pages !
			// Il faut partir coté serveur !

			f_core.Debug(f_grid, "_sortTable: SERVER:\nserial='"+serial+"'\nrowCount="+this._rowCount+"\nrows="+this._rows);
			
			return this.f_setFirst(this._first);
		}
		
		f_core.Debug(f_grid, "_sortTable: CLIENT:\ntdIndexes="+tdIndexes+"\nascendings="+ascendings+"\nSort="+methods);
		
		this.f_sortClientSide(methods, ascendings, tdIndexes);
	},
	/**
	 * Select a row
	 *
	 * @method public
	 * @param any rowValue Value associated to the row
	 * @param optional boolean append Inclusive or Exclusive selection
	 * @param optional boolean show Show the selected row.
	 * @param optional hidden Event jsEvent
	 * @return boolean Returns <code>true</code> if success.
	 */
	f_selectRow: function(rowValue, append, show, jsEvent) {
		var row=this.f_getRowByValue(rowValue, true);
	
		var selection=(append)?fa_selectionManager.APPEND_SELECTION:0;
		
		return this._performElementSelection(row, show, jsEvent, selection);
	},
	/**
	 * Deselect a row
	 *
	 * @method public
	 * @param any rowValue Value associated to the row
	 * @param optional boolean show Show the deselected row.
	 * @param optional hidden Event jsEvent
	 * @return boolean Returns <code>true</code> if success.
	 */
	f_deselectRow: function(rowValue, show, jsEvent) {
		var row=this.f_getRowByValue(rowValue, true);
		
		if (!this.fa_isElementSelected(row)) {
			return false;
		}

		return this._performElementSelection(row, show, jsEvent, false);
	},
	/**
	 * Returns <code>true</code> if the receiver is checked, and <code>false</code> otherwise
	 *
	 * @method public
	 * @param any rowValue Value associated to the row, or a row object.
	 * @return boolean The checked state of the row
	 */
	f_isSelected: function(rowValue) {
		var row=this.f_getRowByValue(rowValue, true);

		return this._isElementValueSelected(row); 
	},

	fa_getElementItem: function(row) {
		return row;
	},

	fa_getElementValue: function(row) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_grid.fa_getElementValue: Invalid element parameter ! ("+row+")");

		return row._index;
	},

	fa_isElementDisabled: function(row) {
		return false;
	},

	fa_isElementSelected: function(row) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_grid.fa_isElementSelected: Invalid element parameter ! ("+row+")");
		
		return row._selected;
	},
	
	fa_setElementSelected: function(row, selected) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_grid.fa_setElementSelected: Invalid element parameter ! ("+row+")");
		
		row._selected=selected;
	},
	
	fa_showElement: function(row) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_grid.fa_showElement: Invalid element parameter ! ("+row+")");

		var scrollBody=this._scrollBody;
		if (row.offsetTop-scrollBody.scrollTop<0) {
			scrollBody.scrollTop=row.offsetTop;
			return;
		}
			
		if (row.offsetTop+row.offsetHeight-scrollBody.scrollTop>scrollBody.clientHeight) {
			scrollBody.scrollTop=row.offsetTop+row.offsetHeight-scrollBody.clientHeight;
		}		
	},
	
	fa_listVisibleElements: function() {
		return this._rowsPool;
	},
	fa_getScrolledComponent: function() {
		return this._scrollBody;
	},
	fa_getScrolledHorizontalTitle: function() {
		return this._scrollTitle;
	},
	fa_getScrolledVerticalTitle: function() {
		return null;
	},
	f_initializeTableLayout: function() {
		var table = f_core.GetChildByCssClass(this, "f_grid_table");
		f_core.Assert(table, "f_grid.f_initializeTableLayout: Can not find table 'f_grid_table'");
		this._table = table;
		table._dataGrid=this;	

		f_core.Assert(table.tBodies.length<=1, "f_grid.f_initializeTableLayout: Too many TBODY ! ("+table.tBodies.length+")");	
		var firstTBody=table.tBodies[0];
		
		this._tbody=firstTBody; //bodies[0];
		if (firstTBody && !firstTBody.firstChild) {			
			table.removeChild(firstTBody);
		}

		var scrollBody=this;
		var catchScrollEvent=false;
		this._title=f_core.GetChildByCssClass(this,"f_grid_fttitle");
		if (this._title) {
			this._scrollTitle=f_core.GetChildByCssClass(this, "f_grid_dataTitle_scroll");
			if (this._scrollTitle) {
				var dataBodyClassName="f_grid_dataBody_scroll";

				scrollBody=f_core.GetChildByCssClass(this, dataBodyClassName);
				
				catchScrollEvent=true;
			}			
		}
		
		if (scrollBody) {
			this._scrollBody=scrollBody;
			scrollBody._dataGrid=this;
		
			if (catchScrollEvent) {								
				scrollBody.onscroll=f_grid._OnScroll;
			}

			if (f_core.IsGecko()) {
				scrollBody.addEventListener("DOMMouseScroll", f_grid._Link_onmousewheel, false);
			}
		}		
	},
	/**
	 * @method hidden
	 */
	f_updateColumnsLayout: function(columns) {
		var heads;
		var cols;
		var cols2;
		
		if (this._title) {
			heads=this._title.getElementsByTagName("th");
			cols=this._title.getElementsByTagName("col");
			cols2=this._table.getElementsByTagName("col");			
			
		} else {
			if (this._visibleHeader) {
				heads=this._table.getElementsByTagName("th");
			}
			cols=this._table.getElementsByTagName("col");			
		}
				
		/* on part du f_grid_disabled
		if (this.f_isDisabled()) {
			var className="f_grid_tcell f_grid_tcell_disabled";
		
			for(var i=0;i<heads.length;i++) {
				heads[i].className=className;
			}
		}
		*/

		var resourceBundle=f_resourceBundle.Get(f_grid);
		var headCursorTitle=resourceBundle.f_get("COLUMN_RESIZE");

		var isInternetExplorer=f_core.IsInternetExplorer();
		
		var v=0;
		for(var i=0;i<columns.length;) {
			var column=columns[i++];			
									
			if (!column._visibility) {
				continue;
			}
				
			column._col=cols[v];
			if (cols2) {
				column._col2=cols2[v];
			}

			if (!heads) {
				continue;
			}

			var head=heads[v];
			
			head.onmouseover=f_grid._Title_onMouseOver;
			head.onmouseout=f_grid._Title_onMouseOut;
			head.onmousedown=f_grid._Title_onMouseDown;
			head.onmouseup=f_grid._Title_onMouseUp;
			
			head._column=column;
			column._head=head;

			column._box=f_core.GetFirstElementByTagName(head, "div", true);
			column._label=f_core.GetFirstElementByTagName(column._box, "div");

			var image=f_core.GetFirstElementByTagName(column._label, "img");
			if (image) {
				column._image=image;
			}

			if (column._resizable) {
				var cursor=f_core.CreateElement(column._box, "div", { title: headCursorTitle, className:  "f_grid_colCursor" });
				column._cursor=cursor;
				cursor._column=column;
				cursor.onmousedown=f_grid._TitleCursorMouseDown;
				cursor.onclick=f_core.CancelJsEventHandler;
		
				if (isInternetExplorer) {
					// Ben oui ... il faut bien !
					cursor.style.right="-8px";
				}
			}

			v++;
		}
	},
	/**
	 * @method protected
	 */
	f_updateTitle: function() {
		if (!this._title) {
			return;
		}

		var doc=this.ownerDocument;
	
		var tr=f_grid.GetFirstRow(this._table); //f_core.GetFirstElementByTagName(dataGrid._table, "tr");
		if (!tr) {
			// Le tableau est vide ?
//			dataGrid._title.style.width=dataGrid.offsetWidth+"px";
			
			f_core.Debug(f_grid, "f_updateTitle: No rows !");
			return;
		}
		
		this._titleLayout=true;		

		var ttr=f_grid.GetFirstRow(this._title); //f_core.GetFirstElementByTagName(dataGrid._title, "tr");
		var tths=ttr.getElementsByTagName("th");
		
		var ths=tr.getElementsByTagName("td");

		var body=this._scrollBody;
		var clientWidth=body.clientWidth;
		var offsetWidth=body.offsetWidth;
		var scrollBarWidth=offsetWidth-clientWidth;
		if (scrollBarWidth<=0) {
			// Ben si y a pas de scrollbar a droite, on cherche en bas !
			scrollBarWidth=body.offsetHeight-body.clientHeight;
			
			if (scrollBarWidth<=0) {
				scrollBarWidth=1;
			}
		}

		var cols=this._title.getElementsByTagName("col");
		var tcols=null;
		var tds=null;
		var columns=this._columns;
		if (!columns[0]._tcol) {
			tcols=this._table.getElementsByTagName("col");
			var tr=f_grid.GetFirstRow(this._table); //f_core.GetFirstElementByTagName(dataGrid._table, "tr", false);
			
			if (tr) {
				// Quid ?
				tds=tr.getElementsByTagName("td");
			}
		}
		
		var total=0;
		var ci=0;
		for(var i=0;i<ths.length;i++) {
			var col=cols[i];
			if (!col) {
				break;
			}
			tths[i].width="";
			
			var w=ths[i].offsetWidth;
			col.style.width=w+"px";
			total+=w;
			
			var cs;
			for(;;ci) {
				cs=columns[ci++];
				if (cs._head) {
					break;
				}
			}

			if (!cs) {
				continue;
			}

			if (tcols) {
				cs._tcol=tcols[i];
			}
			if (tds) {
				cs._tcell=tds[i];
			}			
		}
		
		if (!this._createFakeTH && (this._resizable || offsetWidth>clientWidth)) {
			this._createFakeTH=true;
			
			var col=doc.createElement("col");
			col.style.width=scrollBarWidth+"px";
		
			var colsPos=this._title.getElementsByTagName("col");
			var lastCol=colsPos[colsPos.length-1];
			if (!lastCol.nextSibling) {
				this._title.appendChild(col);
			} else {
				this._title.insertBefore(col, lastCol.nextSibling);
			}
		
			var th=doc.createElement("th");
			var thClassName="f_grid_tcell";
			if (this.f_isDisabled()) {
				//thClassName+=" "+thClassName+"_disabled"; // On part du f_grid_disabled
			}
			th.className=thClassName;
			th.innerHTML="&nbsp;";
			
			var ths0=f_grid.GetFirstRow(this._title, true); //f_core.GetFirstElementByTagName(dataGrid._title, "tr", true);
			ths0.appendChild(th);
			
			total+=scrollBarWidth;
			
			// On verifie que la scrollbar V reste bien visible
			
			if (f_core.IsInternetExplorer()) {
				body.style.overflowY="scroll";

			} else if (f_core.IsGecko()) {
				var overflow=f_core.GetCurrentStyleProperty(body, "overflow");

				if (overflow=="auto" && !this._resizable) {
					body.style.overflow="-moz-scrollbars-vertical";
				}
			}
		}
		
		if (total>clientWidth || this._resizable) {
			this._title.style.width=total+"px";
			
		} else {
			this._title.style.width=offsetWidth+"px";
		}
		
		if (scrollBarWidth>0) {
			var h=this.offsetHeight-this._title.offsetHeight-2;
			body.style.height=h+"px";
		}
		
		this._title.scrollLeft=this._scrollBody.scrollLeft;
	},	
	
	/** 
	 * @method protected abstract
	 */
	f_callServer: f_class.ABSTRACT,
	
	/** 
	 * @method protected abstract
	 */
	f_sortClientSide: f_class.ABSTRACT
}
 
new f_class("f_grid", null, __static, __prototype, f_component, fa_disabled, fa_pagedComponent, fa_subMenu, fa_commands, fa_selectionManager, fa_scrollPositions);
