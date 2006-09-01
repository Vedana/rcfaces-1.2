/*
 * $Id$
 */

/**
 * 
 * @class public f_dataGrid extends f_component, fa_readOnly, fa_disabled, fa_pagedComponent, fa_subMenu, fa_commands, fa_checkManager
 * @author Olivier Oeuillot
 * @version $Revision$
 */

var __static = {

	/** 
	 * @field private static final number	
	 */
	_IMAGE_WIDTH: 16,

	/** 
	 * @field private static final number	
	 */
	_IMAGE_HEIGHT: 16,

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
	 * @field private static final boolean	
	 */
	_USE_BACKGROUND_IMAGE: false,
	
	/** 
	 * @field private static final string	
	 */
	_BLANK_IMAGE_URL: "/blank.gif",
	
	/** 
	 * @field private static final string	
	 */
	_ROW_MENU_ID: "#row",	

	/** 
	 * @field private static final string	
	 */
	_BODY_MENU_ID: "#body",

	/** 
	 * @field private static final string	
	 */
	_HEAD_MENU_ID: "#head",
	
	/**
	 * @method private static
	 */
	_RowMouseDown: function(evt) {
		var dataGrid=this._dataGrid;
		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		if (dataGrid.f_isDisabled() || dataGrid.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
		
		dataGrid._forceFocus();
		
		var sub=f_core.IsPopupButton(evt);

		var selection=fa_selectionManager.ComputeMouseSelection(evt);
			
		dataGrid._moveCursor(this, true, evt, selection);			
		
		if (sub && this._selected) {
			var menu=dataGrid.f_getSubMenuById(f_dataGrid._ROW_MENU_ID);
			if (menu) {
				menu.f_open(this, {
					position: f_menu.MOUSE_POSITION
					}, dataGrid, evt);
			}
		}
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_RowMouseDblClick: function(evt) {
		var dataGrid=this._dataGrid;
		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}

		if (f_core.IsPopupButton(evt)) {
			return f_core.CancelEvent(evt);
		}
	
		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
		}
		
		if (!this._selected) {
			return f_core.CancelEvent(evt);
		}
		
		dataGrid.f_fireEvent(f_event.DBLCLICK, evt, this, this._index);
		
		return f_core.CancelEvent(evt);;
	},
	/**
	 * @method private static
	 */
	_BodyMouseDown: function(evt) {
		var dataGrid=this._dataGrid;
		
		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
		}
		
		var sub=f_core.IsPopupButton(evt);
		if (!sub) {
			return f_core.CancelEvent(evt);
		}
		
		dataGrid._forceFocus();
		
		var menuId=f_dataGrid._BODY_MENU_ID;
		
		// S'il y a une seule selection, on bascule en popup de ligne !
		if (this._selectable && dataGrid._currentSelection.length>0) {
			menuId=f_dataGrid._ROW_MENU_ID;	
		}
		
		var menu=dataGrid.f_getSubMenuById(menuId);
		if (menu) {
			menu.f_open(this, {
				position: f_menu.MOUSE_POSITION
				}, dataGrid, evt);
		}
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_ReturnFalse: function(evt) {
		if (!evt) evt = window.event;
	
		if (f_core.IsPopupButton(evt)) {
			return f_core.CancelEvent(evt);
		}
		
		return true;
	},
	/**
	 * @method private static
	 */
	_UpdateTitle: function(dataGrid) {
		if (!dataGrid._title) {
			return;
		}
	
		var tr=f_core.GetFirstElementByTagName(dataGrid._table, "TR");
		if (!tr) {
			// Le tableau est vide ?
			dataGrid._title.style.width=dataGrid.offsetWidth+"px";
			return;
		}
		
		var ths=tr.getElementsByTagName("TD");

		var body=dataGrid._scrollBody;
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

		var cols=dataGrid._title.getElementsByTagName("COL");
		var tcols=null;
		var tds=null;
		var columns=dataGrid._columns;
		var isInternetExplorer;
		if (!columns[0]._tcol) {
			tcols=dataGrid._table.getElementsByTagName("COL");
			var tr=f_core.GetFirstElementByTagName(dataGrid._table, "TR", false);
			
			if (tr) {
				// Quid ?
				tds=tr.getElementsByTagName("TD");
			}
		}
		
		var total=0;
		var ci=0;
		for(var i=0;i<ths.length;i++) {
			var col=cols[i];
			if (!col) {
				break;
			}
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
		
		if (!dataGrid._createFakeTH && (dataGrid._resizable || offsetWidth>clientWidth)) {
			dataGrid._createFakeTH=true;
			
			var col=document.createElement("COL");
			col.style.width=scrollBarWidth+"px";
		
			var colsPos=dataGrid._title.getElementsByTagName("COL");
			var lastCol=colsPos[colsPos.length-1];
			if (!lastCol.nextSibling) {
				dataGrid._title.appendChild(col);
			} else {
				dataGrid._title.insertBefore(col, lastCol.nextSibling);
			}
		
			var th=document.createElement("TH");
			var thClassName=dataGrid._className+"_tcell";
			if (dataGrid.f_isDisabled()) {
				thClassName+="_disabled";
			}
			th.className=thClassName;
			th.innerHTML="&nbsp;";
			
			var ths0=f_core.GetFirstElementByTagName(dataGrid._title, "TR", true);
			ths0.appendChild(th);
			
			total+=scrollBarWidth;
			
			// On verifie que la scrollbar V reste bien visible
			
			if (f_core.IsInternetExplorer()) {
				body.style.overflowY="scroll";

			} else if (f_core.IsGecko()) {
				var overflow=f_core.GetCurrentStyleProperty(body, "overflow");

				if (overflow=="auto" && !dataGrid._resizable) {
					body.style.overflow="-moz-scrollbars-vertical";
				}
			}
		}
		
		if (total>clientWidth || dataGrid._resizable) {
			dataGrid._title.style.width=total+"px";
			
		} else {
			dataGrid._title.style.width=offsetWidth+"px";
		}
		
		if (scrollBarWidth>0) {
			var h=dataGrid.offsetHeight-dataGrid._title.offsetHeight-2;
			body.style.height=h+"px";
		}
		
		dataGrid._title.scrollLeft=dataGrid._scrollBody.scrollLeft;
	},
	/**
	 * @method private static
	 */
	_CheckMouseButtons: f_core.CancelEventHandler,
	/**
	 * @method private static
	 */
	_CheckSelect: function(evt) {
		var row=this._row;
		var dataGrid=row._dataGrid;
		
		if (dataGrid.f_getEventLocked()) {
			return false;
		}

		if (!evt) evt = window.event;

		// Il faut bloquer le bubble !
		evt.cancelBubble = true;

		if (dataGrid.f_isReadOnly()) {
			return false;
		}
		
		if (row!=dataGrid._cursor) {
			dataGrid._moveCursor(row, true, evt);
		}
		
		var checked;
		if (this.type=="radio") {
			checked=true;
		} else {
			checked=!dataGrid._isElementChecked(row);
		}
	
		dataGrid._performElementCheck(row, true, evt, checked);
		
		if (f_core.IsGecko()) {
			if (dataGrid._isElementChecked(row)!=checked) {
				return false;
			}
		}
		 		 
		return true;
	},
	/**
	 * @method private static
	 */
	_Link_onfocus: function(evt) {
		try {
			var dataGrid=this._dataGrid;
			
			if (!dataGrid._loading && dataGrid.f_getEventLocked(false)) {
				return false;
			}
			
			if (!evt) evt = window.event;
			
			if (dataGrid._focus) {
				return true;
			}
			dataGrid._focus=true;
		
			if (!dataGrid._cursor && this._selectable) {
				var currentSelection=dataGrid._currentSelection;
				if (currentSelection.length>0) {
					dataGrid._cursor=currentSelection[0];
				}
	
				if (!dataGrid._cursor && dataGrid._table) {
					var tr=f_core.GetFirstElementByTagName(dataGrid._table, "TR");
			
					if (tr) {
						dataGrid._cursor=tr;
					}
				}
			}
				
			dataGrid._updateCurrentSelection();
			
			dataGrid.f_onFocus(evt);
	
			return true;		
		} catch (x) {
			f_core.Error(f_dataGrid, "_Link_onfocus throws exception.", x);
		}
	},
	/**
	 * @method private static
	 */
	_Link_onblur: function(evt) {
		try {
			var dataGrid=this._dataGrid;
	// On bloque pas le "blur" car lors d'une ouverture d'une popup, il faut le traiter !
	//		if (dataGrid.f_getEventLocked(false)) {
	//			return false;
	//		}
			if (!evt) evt = window.event;
	
			if (!dataGrid._focus) {
				return true;
			}
			dataGrid._focus=false;
		
			dataGrid._updateCurrentSelection();
			
			dataGrid.f_onBlur(evt);
	
			return true;
		} catch (x) {
			f_core.Error(f_dataGrid, "_Link_onfocus throws exception.", x);
		}
	},
	/**
	 * @method private static
	 */
	_Link_onkeypress: function(evt) {
		var dataGrid=this._dataGrid;
		if (dataGrid.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

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

		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onkeydown: function(evt) {
		var dataGrid=this._dataGrid;
		// On peut vouloir faire PAGE-DOWN/UP avec un repeat ! => pas de boite d'alerte !
		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		if (!dataGrid._focus) {
			return true;
		}
	
		return dataGrid.f_onKeyDown(evt);
		//return dataGrid._performKeyDown(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onkeyup: function(evt) {
		var dataGrid=this._dataGrid;
		// On peut vouloir faire PAGE-DOWN/UP avec un repeat ! => pas de boite d'alerte !
		if (dataGrid.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (!dataGrid._focus) {
			return true;
		}
	
		return dataGrid.f_onKeyUp(evt);
		//return dataGrid._performKeyDown(evt);
	},
	/**
	 * @method private static
	 */
	_Link_onmousewheel: function(evt) {
		var dataGrid=this._dataGrid;
		if (dataGrid.f_getEventLocked(false)) {
			// Il faut bloquer ... sinon ca risque de scroller la page !
			return f_core.CancelEvent(evt);
		}
		if (!evt) evt = window.event;

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
		
		return f_core.CancelEvent(evt);;
	},
	/**
	 * @method private static
	 */
	_Title_onMouseOver: function(evt) {
		var column=this._column;
		var dataGrid=column._dataGrid;
		if (dataGrid.f_getEventLocked(false)) {
			return false;
		}
		
		if (!evt) {
			evt = window.event;
		}
		
		// En drag ?
		if (window._dragColumn) {
			return false;
		}	

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
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
			evt = window.event;
		}

		// En drag ?
		if (window._dragColumn) {
			return false;
		}	

		var column=this._column;
		var dataGrid=column._dataGrid;

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
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
		var dataGrid=column._dataGrid;
		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}
	
		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
		}
		
		var sub=f_core.IsPopupButton(evt);
		if (sub) {
			var menu=dataGrid.f_getSubMenuById(f_dataGrid._HEAD_MENU_ID);
			if (menu) {
				menu.f_open(this, {
					position: f_menu.MOUSE_POSITION
					}, dataGrid, evt);
			}
			return f_core.CancelEvent(evt);
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
		if (dataGrid.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}

		if (dataGrid.f_isDisabled()) {
			return f_core.CancelEvent(evt);
		}

		var oldColumn=dataGrid._columnSelected;
		if (!oldColumn) {
			return f_core.CancelEvent(evt);
		}
		
		dataGrid._columnSelected=undefined;
	
		if (oldColumn!=column) {
			if (oldColumn) {
				dataGrid._updateTitleStyle(oldColumn);
			}
			return true;
		}
		
		dataGrid.f_sortColumn(column);
		
		return true;
	},
	/**
	 * @method private static
	 */
	_OnScroll: function(evt) {
		if (!evt) evt = window.event;

		var dataGrid=this._dataGrid;
		
		var scrollTitle=dataGrid._scrollTitle.scrollLeft;
		var scrollBody=dataGrid._scrollBody.scrollLeft;
		
		if (scrollTitle!=scrollBody) {
			dataGrid._scrollTitle.scrollLeft=scrollBody;
		}

		return true;
	},
	/**
	 * @method private static
	 */
	_GotFocus: function() {
		var row=this._row;
		if (row) {
			row._dataGrid._forceFocus(row);
			return;
		}
		this._dataGrid._forceFocus(this._dataGrid);
	},	
	/**
	 * @method private static
	 */
	_TitleCursorMouseDown: function(evt) {
		var column=this._column;
		var dataGrid=column._dataGrid;

		if (dataGrid.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		f_core.AddEventListener(document, "mousemove", f_dataGrid._TitleCursorDragMove, dataGrid);
		f_core.AddEventListener(document, "mouseup",   f_dataGrid._TitleCursorDragStop, dataGrid);

		if (f_core.IsInternetExplorer()) {
			if (false) {
				document.onlosecapture=function() {
					alert("Lose capture !");
				}
			}
		}

	 	f_core.CancelEvent(evt);

		var eventPos=f_core.GetEventPosition(evt, document);
		var cursorPos=f_core.GetAbsolutePos(this);
		dataGrid._dragDeltaX=eventPos.x-cursorPos.x+dataGrid._scrollTitle.scrollLeft;

		window._dragColumn=column;
		dataGrid._dragOriginX=eventPos.x;

		var ths=dataGrid._title.getElementsByTagName("TH");
		var c=this.style.cursor;
		for(var i=0;i<ths.length;i++) {
			ths[i].oldCursorStyle=ths[i].style.cursor;
			ths[i].style.cursor="e-resize";
		}
		
		window._dragOldCursor=document.body.style.cursor;
		document.body.style.cursor="e-resize";
		
		return false;
	},
	/**
	 * @method private static
	 */
	_TitleCursorDragMove: function(evt) {
		var column=window._dragColumn;
		if (!column) {
			return;
		}
		
		var dataGrid=column._dataGrid;
		if (!evt) evt = window.event;

		var eventPos=f_core.GetEventPosition(evt, document);
		var cursorPos=f_core.GetAbsolutePos(column._cursor);
		dataGrid._dragMousePosition=eventPos.x;
		
		var dw=eventPos.x-cursorPos.x+dataGrid._scrollTitle.scrollLeft-dataGrid._dragDeltaX;
		
		f_dataGrid._DragCursorMove(dataGrid, column, dw);
						
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_TimerDragMove: function() {
		var column=window._dragColumn;
		if (!column) {
			return;
		}
		
		var dataGrid=column._dataGrid;

		var eventPos=dataGrid._dragMousePosition;
		var cursorPos=f_core.GetAbsolutePos(column._cursor);
		
		var dw=eventPos-cursorPos.x+dataGrid._scrollTitle.scrollLeft-dataGrid._dragDeltaX;
		
		f_dataGrid._DragCursorMove(dataGrid, column, dw);
	},
	/**
	 * @method private static
	 */
	_DragCursorMove: function(dataGrid, column, dw) {
		
		if (dataGrid._dragTimerId) {
			window.clearTimeout(dataGrid._dragTimerId);
			dataGrid._dragTimerId=null;
		}

		var w=column._col.offsetWidth+dw;
		
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
		
		if (w<8) {
			if (column._label.style.display!="none") {
				column._label.style.display="none";
			}
		} else if (column._label.style.display!="block") {
			column._label.style.display="block";
		}
		
		if (w<24) {
			if (dataGrid._currentSort==column && !column._restoreClass) {
				column._restoreClass=column._label.className;
				column._label.className=dataGrid._className+"_ttext";
			}
			
		} else if (column._restoreClass) {
			column._label.className=column._restoreClass;
			column._restoreClass=null;
		}
			
	 	if (f_core.IsInternetExplorer()) {
			// AVANT !
			dataGrid._table.style.width=(dataGrid._table.offsetWidth+dw)+"px";
			dataGrid._title.style.width=(dataGrid._title.offsetWidth+dw)+"px";

			column._tcol.style.width=w+"px";
			column._col.style.width=w+"px";
		} else {
			
			column._tcol.style.width=w+"px";
			column._col.style.width=w+"px";

			// APRES !
			var tw=dataGrid._table.offsetWidth+dw;
			dataGrid._table.style.width=tw+"px";
			dataGrid._title.style.width=(dataGrid._title.offsetWidth+dw)+"px";
		}
		
//		window.status="deltaTitle="+(dataGrid._title.offsetWidth-dataGrid._table.offsetWidth)+"pixels ";
		
		if (dataGrid._scrollTitle.scrollLeft>0) {
			dataGrid._dragTimerId=window.setTimeout(f_dataGrid._TimerDragMove, f_dataGrid._DRAG_TIMER);
		}
	},
	/**
	 * @method private static
	 */
	_TitleCursorDragStop: function(evt) {
		var column=window._dragColumn;
		if (!column) {
			// Cela peut survenir si les stops sont enchainés ....
			return false;
		}
		
		var dataGrid=column._dataGrid;
		
		if (dataGrid._dragTimerId) {
			window.clearTimeout(dataGrid._dragTimerId);
			dataGrid._dragTimerId=undefined;
		}

		f_core.RemoveEventListener(document, "mousemove", f_dataGrid._TitleCursorDragMove, dataGrid);
		f_core.RemoveEventListener(document, "mouseup",   f_dataGrid._TitleCursorDragStop, dataGrid);

		document.body.style.cursor=window._dragOldCursor;
		window._dragOldCursor=undefined;

		var ths=dataGrid._title.getElementsByTagName("TH");
		for(var i=0;i<ths.length;i++) {
			ths[i].style.cursor=ths[i].oldCursorStyle;
			ths[i].oldCursorStyle=undefined;
		}
		
		column._restoreClass=undefined;
		
		window._dragColumn=undefined;
		dataGrid._dragDeltaX=undefined;
		dataGrid._dragOriginX=undefined;
		dataGrid._dragMousePosition=undefined;
	},
	/**
	 * @method private static
	 */
	_InitializeScrollbars: function(dataGrid) {
		if (!dataGrid._scrollBody) {
			return;
		}
		
		var pos=dataGrid._initialHorizontalScrollPosition;
		if (pos) {
			dataGrid._scrollBody.scrollLeft=pos;
			if (dataGrid._scrollTitle) {
				dataGrid._scrollTitle.scrollLeft=pos;
			}
		}
		
		pos=dataGrid._initialVerticalScrollPosition;
		if (pos) {
			dataGrid._scrollBody.scrollTop=pos;
			if (dataGrid._scrollTitle) {
				dataGrid._scrollTitle.scrollTop=pos;
			}
		}
	},
	/**
	 * @method private static
	 */
	_Sort_Index: function(text1, text2, tr1, tr2) {
		return tr1._index-tr2._index;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Alpha: function(text1, text2) {
		text1=(text1)?text1:"";
		text2=(text2)?text2:"";
		
		if (text1 == text2) {
			return 0;
		}
		return (text1 > text2)? 1:-1;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_AlphaIgnoreCase: function(text1, text2) {
		text1=(text1)?text1.toLowerCase():"";
		text2=(text2)?text2.toLowerCase():"";
		
		if (text1 == text2) {
			return 0;
		}
		return (text1 > text2)? 1:-1;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Integer: function(text1, text2) {
		var val1 = parseInt(text1, 10);
		var val2 = parseInt(text2, 10);
		if (val1 == val2) {
			return 0;
		}
		return (val1 > val2)? 1:-1;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Number: function(text1, text2) {
		var val1 = parseFloat(text1);
		var val2 = parseFloat(text2);
		if (val1 == val2) {
			return 0;
		}
		return (val1 > val2)? 1:-1;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Date: function(text1, text2) {
		if (text1=="") {
			return -1;
			
		} else if (text2=="") {
			return 1;
		}
		var val1 = text1.split("/");
		var val2 = text2.split("/");
		if (val1.length!=val2.length) {
			return val1.length-val2.length;
		}
		
		for(var i=val1.length-1;i>=0;i--) {
			if (val1[i]==val2[i]) {
				continue;
			}
			
			return (val1[i] > val2[i])? 1:-1;
		}
				
		return 0;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Time: function(text1, text2) {
		if (text1=="") {
			return -1;
			
		} else if (text2=="") {
			return 1;
		}
		var val1 = text1.split(":");
		var val2 = text2.split(":");
		if (val1.length!=val2.length) {
			return val1.length-val2.length;
		}
		
		for(var i=0;i<val1.length;i++) {
			if (val1[i]==val2[i]) {
				continue;
			}
			
			return (val1[i] > val2[i])? 1:-1;
		}
				
		return 0;
	},
	/**
	 * @method public static
	 * @param string text1
	 * @param string text2
	 * @return number
	 */
	Sort_Server: function(text1, text2) {
		// Pas d'implementation, car la fonction est filtrée avant !
	}
}
 
var __prototype = {
	
	f_dataGrid: function() {
		this.f_super(arguments);
				
		this._rowsPool=new Array;
		this._cellsPool=new Array;
//		this._colsPool=new Array;
		
		if (f_core.GetAttribute(this, "v:resizable")) {
			this._resizable=true;
		}
			
		var hsp=f_core.GetAttribute(this, "v:hsp");
		if (hsp) {
			this._initialHorizontalScrollPosition=hsp;
		}
			
		var vsp=f_core.GetAttribute(this, "v:vsp");
		if (vsp) {
			this._initialVerticalScrollPosition=vsp;
		}

		this._className=f_core.GetAttribute(this, "v:className");
		if (!this._className) {
			this._className=this.className;
		}

		var tableClass=this._className;
		tableClass+="_table";
		// C'est un Aspect ! aussi la variable est initializée aprés le constructeur !
		if (f_core.GetAttribute(this, "v:disabled")) {
			tableClass+="_disabled";
		}
		this._table = f_core.GetChildByCssClass(this, tableClass);
		if (this._table) {
			this._table._dataGrid=this;	

			var bodies=this._table.getElementsByTagName("TBODY");
			f_core.Assert(bodies.length==1, "Too many TBODY !");
			this._tbody=bodies[0];
			
			this._table.removeChild(this._tbody);
		}
		
		this.f_openActionList(f_event.MOUSEDOWN);
		this.f_openActionList(f_event.MOUSEUP);
		this.f_openActionList(f_event.SELECTION);

		var focus;
		if (f_core.IsInternetExplorer()) {
			if (!this.tabIndex) {
				this.tabIndex=0;
			}
			
			this.hideFocus=true;
			this.onfocus=f_dataGrid._Link_onfocus;
			this.onblur=f_dataGrid._Link_onblur;
			this.onkeydown=f_dataGrid._Link_onkeydown;
			this.onkeypress=f_dataGrid._Link_onkeypress;
			this.onkeyup=f_dataGrid._Link_onkeyup;
			this.onmousewheel=f_dataGrid._Link_onmousewheel;
			this._dataGrid=this;
			
		} else if (f_core.IsGecko()) {
			focus=f_core.GetChildByCssClass(this,this._className+"_dataBody_scroll");
			if (focus) {
				focus.onfocus=f_dataGrid._Link_onfocus;
				focus.onblur=f_dataGrid._Link_onblur;
				focus.onkeydown=f_dataGrid._Link_onkeydown;
				focus.onkeypress=f_dataGrid._Link_onkeypress;
				focus.onkeyup=f_dataGrid._Link_onkeyup;
				focus._dataGrid=this;
				this._cfocus=focus;
			}			
		} else {
			focus=document.createElement("A");
			this._cfocus=focus;
			focus.className=this._className+"_focus";
			focus.onfocus=f_dataGrid._Link_onfocus;
			focus.onblur=f_dataGrid._Link_onblur;
			focus.onkeydown=f_dataGrid._Link_onkeydown;
			focus.onkeypress=f_dataGrid._Link_onkeypress;
			focus.onkeyup=f_dataGrid._Link_onkeyup;
			focus.href=f_core.JAVASCRIPT_VOID;
			focus._dataGrid=this;
		
			if (this.tabIndex) {
				focus.tabIndex=this.tabIndex;
			}
			
			this._table.appendChild(focus);
		}

		this._title=f_core.GetChildByCssClass(this,this._className+"_fttitle");
		if (this._title) {
			this._scrollTitle=f_core.GetChildByCssClass(this,this._className+"_dataTitle_scroll");
			if (!this._scrollTitle) {
				this._scrollBody=this;
				
			} else {
				var dataBodyClassName=this._className+"_dataBody_scroll";
				
				var scrollBody=f_core.GetChildByCssClass(this, dataBodyClassName);
				if (scrollBody) {
					this._scrollBody=scrollBody;
					scrollBody.onscroll=f_dataGrid._OnScroll;
					scrollBody._dataGrid=this;
				}
				
				if (!f_core.IsGecko()) {
					// Sous GECKO on peut avoir un probleme de layout !
					//f_dataGrid._InitializeScrollbars(this);
				}
			}			
			
		} else {
			this._scrollBody=this;
		}	
		
		this.f_addEventListener(f_event.KEYDOWN, this._performKeyDown);
		
		if (f_core.IsGecko()) {
			this._scrollBody.addEventListener("DOMMouseScroll", f_dataGrid._Link_onmousewheel, false);
		}
		
		var styleSheetBase=f_env.GetStyleSheetBase();

		this._blankImageURL=styleSheetBase+f_dataGrid._BLANK_IMAGE_URL;
		f_imageRepository.PrepareImage(this._blankImageURL);
	},
	f_finalize: function() {
	
		if (window._dragColumn) {
			f_dataGrid._TitleCursorDragStop();
		}
	
		if (this._rowsPool) {
			this._releaseRows();
			this._rowsPool=undefined;
		}
	
		if (this._cellsPool) {
			this._releaseCells();
			this._cellsPool=undefined;
		}
/*	
		if (this._colsPool) {
			this._releaseCols(this._colsPool);
			this._colsPool=null;
		}
	*/	
	
//		this._documentComplete=undefined;

//		this._blankImageURL=undefined; // string
//		this._columnsStyleClass=undefined; // string
//		this._waitingIndex=undefined; // number
//		this._waitingSelection=undefined; // number

// 		this._loading=undefined; // boolean
		this._waiting=undefined;

		this._currentSort=undefined; // HTMLColElement
//		this._ascendingOrder=undefined; // boolean
		
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
			
			f_core.VerifyProperties(table);			
		}
		
		if (this._columns) {
			this._releaseColumns();
			this._columns=undefined;
		}
//		this._className=undefined;  // string
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
			
			f_core.VerifyProperties(scrollBody);
		}

		if (this._title) {
			f_core.VerifyProperties(this._title);
			this._title=undefined;
		}
		
		var scrollTitle=this._scrollTitle;
		if (scrollTitle) {
			this._scrollTitle=undefined;
			
			scrollTitle.onscroll=null;
			scrollTitle._dataGrid=undefined;
			
			f_core.VerifyProperties(scrollTitle);
		}
		
		// Il faut tester le verify properties qu'a la fin des undefined !
		if (cfocus) {	
			f_core.VerifyProperties(cfocus);
		}
		
		this._cursor=undefined;

		
//		this._initialHorizontalScrollPosition=undefined; // string
//		this._initialVerticalScrollPosition=undefined; // string
		
		this._initSortColumn=undefined;  // HTMLColElement
//		this._initSortAscending=undefined;	 // boolean
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
	f_getEventLocked: function(showAlert) {
		if (this._loading) {
			if (showAlert!==false) {
				var resourceBundle=f_resourceBundle.Get(f_dataGrid);
			
				alert(resourceBundle.f_get("EVENT_LOCKED"));
			}
			return true;
		}
		
		return this.f_super(arguments, showAlert);
	},
	f_serialize: function() {
		
		if (this._resizable) {
			var columns=this._columns;
			var v="";
			for(var i=0;i<columns.length;i++) {
				var col=columns[i];

				if (!col._resizable) {
					continue;
				}

				if (v.length>0) {
					v+=",";
				}
				
				v+=col._col.offsetWidth;
			}
			this.f_setProperty(f_prop.COLUMN_WIDTHS, v);
		}
		
		if (this._scrollBody) {
			var body=this._scrollBody;
			
			this.f_setProperty(f_prop.HORZSCROLLPOS, body.scrollLeft);
			this.f_setProperty(f_prop.VERTSCROLLPOS, body.scrollTop);
		}

		return this.f_super(arguments);
	},

	f_update: function() {
	
		if (this._tbody) {
			f_core.Assert(this._tbody.parentNode!=this._table, "Tbody has not been detached !");
			this._table.appendChild(this._tbody);			
		}
		
		this.f_super(arguments);
		
		if (this._initSortColumn) {
			var sortColumn=this._initSortColumn;
			var ascending=this._initSortAscending;
			
			this._initSortColumn=undefined;
			this._initSortAscending=undefined;
		
			var noSort=(sortColumn._method==f_dataGrid.Sort_Server);
			
			//alert("Nosort="+noSort+"/"+sortColumn._method);
			
			this.f_sortColumn(sortColumn, ascending?true:false, noSort);
		}				

		var scrollBody=this._scrollBody;					
		// Des popups ?
		var menu=this.f_getSubMenuById(f_dataGrid._BODY_MENU_ID);
		if (menu) {
			scrollBody.onmousedown=f_dataGrid._BodyMouseDown;
			scrollBody.onmouseup=f_core.CancelEventHandler;
			scrollBody.onclick=f_core.CancelEventHandler;
		}					
	
		this.f_performPagedComponentInitialized();
		
		if (!this.f_isVisible()) {
			this.f_getClass().f_getClassLoader().addVisibleComponentListener(this);
			
		} else {
			// Visible !
	
	/* On le fait sur le onload !
			if (!f_core.IsInternetExplorer()) {
				f_dataGrid._UpdateTitle(this);
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
//		if (f_core.IsInternetExplorer()) {
		f_dataGrid._UpdateTitle(this);

		if (f_core.IsGecko()) {				
			if (this._table.parentNode.offsetHeight==0) {
				// BUG de layout de Firefox !
				this._table.parentNode.style.height=this._table.offsetHeight+"px";
			}
		}

		f_dataGrid._InitializeScrollbars(this);		

		if (this._interactiveShow) {
			this.f_setFirst(this._first, this._cursor);			
		}
	},
	
	/* ****************************************************************** */
	
	/**
	 * @method hidden
	 */
	f_setColumns: function() {
		this._columns=new Array;
		
		var cols=null;
		var heads=null;
		if (this._title) {
			heads=this._title.getElementsByTagName("TH");
			cols=this._title.getElementsByTagName("COL");
			
		} else {
			heads=this._table.getElementsByTagName("TH");
			cols=this._table.getElementsByTagName("COL");
		}
		
		if (this.f_isDisabled()) {
			var className=this._className+"_tcell_disabled";
		
			for(var i=0;i<heads.length;i++) {
				heads[i].className=className;
			}
		}

		var isInternetExplorer=f_core.IsInternetExplorer();
		var v=0;
		for(var i=0;i<arguments.length;) {
			var column=new Object;
	
			var arg=arguments[i++];
			if (arg && typeof(arg)=="string") {
				column._id=arg;
				arg=arguments[i++];
			}
	
			column._index=this._columns.length;
			column._visibility=arg;
			column._dataGrid=this;
			
			if (column._visibility) {
				if (heads) {
					var head=heads[v];
					
					column._head=head;
					column._col=cols[v];
					head._column=column;
					column._box=f_core.GetFirstElementByTagName(head, "DIV", true);
					column._label=f_core.GetFirstElementByTagName(column._box, "DIV");

					if (this._resizable && f_core.GetAttribute(head, "resizable")) {
					  column._minWidth=f_core.GetAttribute(head, "minWidth");						
						if (!column._minWidth || column._minWidth<1) {
							column._minWidth=f_dataGrid._COLUMN_MIN_WIDTH;
						}
						
						column._maxWidth=f_core.GetAttribute(head, "maxWidth");
						if (!column._maxWidth || column._maxWidth<column._minWidth) {
							column._maxWidth=f_dataGrid._COLUMN_MAX_WIDTH;
						}

						if (column._minWidth>=f_dataGrid._COLUMN_MIN_WIDTH && column._maxWidth>=column._minWidth) {
							column._resizable=true;
							
							var cursor=document.createElement("DIV");
							cursor.title="Maintenez appuyer pour redimensionner la colonne";
							cursor.className=this._className+"_colCursor";
							column._box.appendChild(cursor);
							column._cursor=cursor;
							cursor._column=column;
							cursor.onmousedown=f_dataGrid._TitleCursorMouseDown;
							cursor.onclick=f_core.CancelEventHandler;
					
							if (isInternetExplorer) {
								// Ben oui ... il faut bien !
								cursor.style.right="-4px";
							}
						}
					}
				}
				v++;
			}
			
			this._columns.push(column);
			
		}
	},
	/**
	 * @method public
	 * @param Object row
	 * @return boolean
	 */
	f_isRowSelected: function(row) {
		return (row._selected)?true:false;
	},
	_updateElementStyle: function(row) {
		var className;
		if (this.f_isDisabled()) {
			className=row._className+"_disabled";

			if (row._selected) {	
				className+="_selected";
			}
			
		} else if (row._selected) {
			// Pas de classe avec parité !
			
			className=this._className+"_row_selected";
			if (this._focus) {
				className+="_focus";
			}

		} else if (this._selectable) {
			className=row._className+"_normal";
			
		} else {
			className=row._className;
		}
		
		if (row.className!=className) {
			row.className=className;
			
			if (this._columnsStyleClass || row._cellsStyleClass) {
				this._updateColumnsStyle(row);
			}
		}

		
		var input=row._input;
		if (input && row._cheked!=input.checked) {
			input.checked=row._checked;
			
			if (f_core.IsInternetExplorer()) {
				// Il se peut que le composant ne soit jamais affiché 
				// auquel cas il faut utiliser le defaultChecked !
				input.defaultChecked=row._checked;
			}
		}
		
		var cursorStyle;
		if (this._cursor==row) {
			if (!row._hasCursor) {
				row._hasCursor=true;
				cursorStyle="_cell_cursor";
			}
		} else if (row._hasCursor) {			
			row._hasCursor=undefined;
			cursorStyle="_cell";
		}
		
		
		if (cursorStyle) {
			var td=f_core.GetFirstElementByTagName(row, "TD");
			if (td) {
				td.id=this._className+cursorStyle;
			}
		}
	},
	_updateColumnsStyle: function(row) {
		var cols=this._columns;
		var tds=row.getElementsByTagName("TD");

		if (!row._selected) {
			var idx=0;
			
			for(var i=0;i<cols.length;i++) {
				var col=cols[i];
				if (!col._visibility) {
					continue;
				}

				var td=tds[idx++];
				var className=td._cellStyleClass;
				
				if (!className) {
					if (row._index % 2) {
						className=col._userClassOdd;
	
					} else {
						className=col._userClassEven;
					}
				}
								
				if (!className) {
					continue;
				}
				
				if (td.className==className) {
					continue;
				}
				
				td.className=className;
			}

			return;			
		}
		
		for(var i=0;i<tds.length;i++) {
			tds[i].className=null;
		}
	},
	/**
	 * @method hidden
	 */
	f_setRowCount: function(rowCount) {
		this._rowCount=rowCount;
		this._maxRows=rowCount;
	},
	/**
	 * 
	 * @method public
	 * @param any value The value of the new row
	 * @param string columnValue1 A parameter for each column 
	 * @param string columnValue2 A parameter for each column 
	 * @return Object
	 */
	f_addRow: function(value, columnValue1, columnValue2) {
		f_core.Assert(this._rows==0, "All rows of the DataGrid must be loaded (attribute rows=0)");
		
		var l=[value];
		
		if (this._selectable && !this._selectionFullState) {
			l.push(false);
		}	
		if (this._checkable && !this._checkFullState) {
			l.push(false);
		}
		
		f_core.PushArguments(l, arguments, 1);
		
		var ret=this._addRow.apply(this, l);
		if (!ret) {
			return ret;
		}
		
		if (this._rowCount>=0) {
			this._rowCount++;
		}

		if (ret>0) {
			this.f_performPagedComponentInitialized();
		}
			
		return ret;
	},
	/**
	 * @method hidden
	 */
	_addRow: function() {
		var idx=0;
		
		f_core.Assert(this._tbody, "No table body !");
		
		var row=document.createElement("TR");
		this._tbody.appendChild(row);
		this._rowsPool.push(row);
		row._dataGrid=this;
		
		var rowIdx=this._tbody.childNodes.length;
		
		row._index=arguments[idx++];
		row.id="row"+row._index;
		if (rowIdx % 2) {
			row.className=this._className+"_row_odd";
		} else {
			row.className=this._className+"_row_even";
		}
		row._className=row.className;
		
		if (this._selectable || this._checkable) {
			row.onmousedown=f_dataGrid._RowMouseDown;
			row.onmouseup=f_core.CancelEventHandler;
			row.onclick=f_core.CancelEventHandler;
//						td.onmouseup=f_dataGrid._ReturnFalse;
			row.ondblclick=f_dataGrid._RowMouseDblClick;
			row.onfocus=f_dataGrid._GotFocus;
		}
		
		if (this._selectable) {
			var selected=false;
			
			if (!this._selectionFullState) {
				selected=arguments[idx++];
			}
			
			this._updateElementSelection(row, selected);
		}
		
		var checked=undefined;
		if (this._checkable) {	
			if (!this._checkFullState) {
				checked=arguments[idx++];
			}
		}
		
		var cols=this._table.getElementsByTagName("COL");
		
		var cells=new Array;
		row._cells=cells;
		var countTd=0;
		var rowValueColumnIndex=this._rowValueColumnIndex;
		var columns=this._columns;
		for(var i=0;i<columns.length;i++) {
			var col=columns[i];

			var td;
			if (col._visibility===null) {
				td=null;
				
			} else {
				var cellText;
				
				if (i===rowValueColumnIndex) {
					cellText=row._index;
					
				} else {
					cellText=arguments[idx++];
				}
		
				if (col._visibility) {
					td=document.createElement("TD");
					row.appendChild(td);
					this._cellsPool.push(td);
					
					var className=this._className+"_cell";
					if (countTd===0) {
						className+="_left";
	
						td._className=className;
					}
					
					td.id=className;
					td.valign="top";
					td.noWrap=true;
					td._text=cellText;
					
					if (cols) {
						var tcol=cols[countTd];
						if (tcol) {
							td.align=tcol.align;
						}
					}

					var cellImage=null;
					if (col._cellImage || col._defaultCellImage) {
						cellImage=document.createElement("IMG");
						cellImage.className=this._className+"_imageCell";
						cellImage.width=f_dataGrid._IMAGE_WIDTH;
						cellImage.height=f_dataGrid._IMAGE_HEIGHT;

						var imageURL=col._defaultCellImage;

						if (f_dataGrid._USE_BACKGROUND_IMAGE) {	
							cellImage.src=this._blankImageURL;
							if (imageURL) {
								cellImage.style.backgroundImage="url("+imageURL+")";
							}
							
						} else {
							if (!imageURL) {
								imageURL=this._blankImageURL;
							}
							
							cellImage.src=imageURL;
						}

						cellImage.border=0;

						if (!row._cellImages) {
							row._cellImages=new Array;
						}
						row._cellImages.push(cellImage);
					}

					if (this._selectable) {
//						td.onmouseup=f_dataGrid._ReturnFalse;
//						td.onmousedown=f_dataGrid._ReturnFalse;
//						td.ondblclick=f_core.CancelEventHandler;
						td.onclick=f_dataGrid._ReturnFalse;
						
						td._dataGrid=this;
						td.onfocus=f_dataGrid._GotFocus;
					}
								
					if (this._checkable) {
						if (countTd==0) {
							var input=document.createElement("INPUT");
							row._input=input;
							
							input.id=this.id+fa_namingContainer.SeparatorChar+rowIdx;
							
							if (this._checkCardinality==fa_cardinality.ONE_CARDINALITY) {
								input.type="radio";
								input.value="CHECKED_"+rowIdx;
								input.name=this.id+fa_namingContainer.SeparatorChar+"radio";
								
							} else {							
								input.type="checkbox";
								input.value="CHECKED";
								input.name=input.id;
							}
													
							input.onmousedown=f_dataGrid._CheckMouseButtons;
							input.onmouseup=f_dataGrid._CheckMouseButtons;
							input.onclick=f_dataGrid._CheckSelect;
							input.onfocus=f_dataGrid._GotFocus;
							input._row=row;
							input.tabIndex=-1;
							input.className=this._className+"_input";
							
							if (this.f_isDisabled()) {
								input.disabled=true;
							}
							
							td.appendChild(input);

							if (cellImage) {
								td.appendChild(cellImage);
							}

							row._label=document.createElement("LABEL");
							if (cellText && cellText.length>0) {
								row._label.appendChild(document.createTextNode(cellText));
							}
							row._label.className=this._className+"_label";
							td.appendChild(row._label);
							
							this._updateElementCheck(row, checked);
						}
					}
					
					if (countTd>0 || !row._input) {
						if (cellImage) {
							td.appendChild(cellImage);
						}
						
						var span=document.createElement("LABEL");
						span.className=this._className+"_label";
						td.appendChild(span);
						
						if (!cellText) {
							cellText=" ";
						}
						span.appendChild(document.createTextNode(cellText));							
					}
					countTd++;
				} else {
					td=new Object;
					td._text=cellText;
				}
			}
			
			cells.push(td);
		}
			
		this._updateElementStyle(row);
		
		return row;
	},
	/**
	 * 
	 * @method public
	 * @param any rowValue1 The value of the row to remove
	 * @param optional any rowValue2 The value of the next row to remove
	 * @return number of removed rows.
	 */
	f_removeRow: function(rowValue1, rowValue2) {
		f_core.Assert(this._rows==0, "All rows of the DataGrid must be loaded (attribute rows=0)");
		
		var ret=0;
		var tbody=this._tbody;
		var rowsPool=this._rowsPool;
		
		var selectionChanged=false;
		var checkChanged=false;
		for(var i=0;i<arguments.length;i++) {
			var rowValue=arguments[i];
			
			var row=this.f_getRowByValue(rowValue, false);
			if (!row) {
				continue;
			}
			
			if (this._deselectElement(row)) {
				selectionChanged=true;
			}
			if (this._uncheckElement(row)) {
				checkChanged=true;
			}
			
			if (row==this._cursor) {
				this._cursor=undefined;
			}
			
			tbody.removeChild(row);
			rowsPool.f_removeElement(row);
		
			this._releaseRow(row);			

			f_core.VerifyProperties(row);
			
			ret++;
			if (this._rowCount>=0) {
				this._rowCount--;
			}
		}

		if (ret<1) {
			return 0;
		}

		this.f_performPagedComponentInitialized();

		if (selectionChanged) {
			this._fireSelectionChangedEvent();
		}
		if (checkChanged) {
			this._fireCheckChangedEvent();
		}
					
		return ret;
	},
	/**
	 * 
	 * @method public
	 * @param any[] rowValues List of values whose specified rows.
	 * @return number of removed rows.
	 */
	f_removeRows: function(rowValues) {
		return this.f_removeRow.apply(this, rowValues);
	},
	_a_updateDisabled: function(disabled) {
		if (!this._componentUpdated) {
			return;
		}
		var suffix=(disabled)?"_disabled":"";
		
		this.className=this._className+suffix;
		if (!this._table) {
			return;
		}
		this._table.className=this._className+"_table"+suffix;
	
		var rows=this._table.rows;
		for(var i=0;i<rows.length;i++) {
			var row=rows[i];
			
			if (row._index===undefined) {
				continue;
			}
			
			if (row._input) {
				row._input.disabled=disabled;
			}
			
			this._updateElementStyle(row);
		}

		var ths=this._listTitleCells();
		if (ths && ths.length>0) {
			for(var i=0;i<ths.length;i++) {
				var th=ths[i];
				
				th.className=this._className+"_tcell"+suffix;
			}
		}			
	},
	_a_updateReadOnly: function() {
	},
	_listTitleCells: function() {
		var list=new Array;
		var table=this._title;
		var tableChildren=table.childNodes;
		if (tableChildren==null || tableChildren.length<1) {
			return null;
		}
		for(var i=0;i<tableChildren.length;i++) {
			var tableChild=tableChildren[i];
			if (tableChild.tagName!="THEAD") { 
				continue;
			}
			
			var theadChildren=tableChild.childNodes;
			if (theadChildren==null || theadChildren.length<1) {
				continue;
			}
			for(var j=0;j<theadChildren.length;j++) {
				var theadChild=theadChildren[j];
				if (theadChild.tagName!="TR") {
					continue;
				}

				var trChildren=theadChild.childNodes;
				if (trChildren==null || trChildren.length<1) {
					continue;
				}
				for(var k=0;k<trChildren.length;k++) {
					var trChild=trChildren[k];
					if (trChild.tagName!="TH") {
						continue;
					}
					list.push(trChild);
				}
			}
			
			break;
		}
		
		return list;
	},
	f_onSelect: function(evt) {
		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	f_onMouseDown: function(evt) { 
		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	f_onMouseUp: function(evt) { 
		if (this._selectable) {
			return false;
		}
		return this.f_super(arguments, evt);
	},
	/**
	 * Returns an array of content of each cell of the specified row.
	 *
	 * @method public
	 * @param any rowValue Row value, a row object, or the index of row into the table.
	 * @param boolean onlyVisible Keey only visible columns.
	 * @return string[] 
	 */
	f_getRowValues: function(rowValue, onlyVisible) {
		var row;
		
		if (rowValue._dataGrid) {
			row=rowValue;
			
		} else if (typeof(rowValue)=="number") {
			row=this._getRow(rowValue, true);
			
		} else {
			row=this.f_getRowByValue(rowValue, true);
		}
		
		var cells=row._cells;
		var array=new Array;
		
		array.index=row._index;
		
		var index=0;
		for(var i=0;i<this._columns.length;i++) {
			var col=this._columns[i];

			if (col._visibility===null) { // HiddenMode cot? serveur !
				if (onlyVisible) {
					continue;
				}
				
				array.push(null);
				continue;
			}
	
			var cell=cells[index++];

			if (!col._visibility && onlyVisible) { // HiddenMode cot? client !
				continue;
			}

			array.push(cell._text);
		}
		
		return array;
	},
	/**
	 * Returns into an object, contents of each cell of the specified row.
	 *
	 * @method public
	 * @param any rowValue Row value, a row object, or the index of the row into the table.
	 * @param boolean onlyVisible Keey only visible columns.
	 * @return Object
	 */
	f_getRowValuesSet: function(rowValue, onlyVisible) {
		var row;
		
		if (rowValue._dataGrid) {
			row=rowValue;
			
		} else if (typeof(rowValue)=="number") {
			row=this._getRow(rowValue, true);
			
		} else {
			row=this.f_getRowByValue(rowValue, true);
		}
		
		var cells=row._cells;
		var set=new Object;
		
		if (row._index) {
			set.id=row._index;
		}
		
		var index=0;
		for(var i=0;i<this._columns.length;i++) {
			var col=this._columns[i];

			if (col._visibility===null) { // HiddenMode coté serveur !
				continue;
			}
	
			var cell=cells[index++];

			if (!col._id) {
				continue;
			}

			if (!col._visibility && onlyVisible) { // HiddenMode cot? client !
				continue;
			}

			set[col._id]=cell._text;
		}
		
		return set;
	},
	/**
	 * Returns the value of the row specified by its index.
	 *
	 * @method public
	 * @param any rowIndex Row object.
	 * @return string the key of the row.
	 */
	f_getRowValueAtIndex: function(rowIndex) {
		var row=this._getRow(rowIndex, true, true);
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
	 * @return string the key of the row.
	 */
	f_getRowValue: function(rowIndex) {
		var row=this._getRow(rowIndex, true);
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
		f_core.Assert(value!==undefined, "f_dataGrid.f_getRowByValue: Invalid value '"+value+"'.");

		if (value._dataGrid) {
			return value;
		}
		
		var rows=this._listVisibleElements();
		if (!rows) {
			f_core.Debug(f_dataGrid, "Empty rows to get row by value "+value);
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
		
		f_core.Debug(f_dataGrid, "Can not find row by value "+value);
		
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
	 * Returns the content of the cell specified by row and column.
	 *
	 * @method public
	 * @param any rowValue Row value, row object or the index of row the into table.
	 * @param number columnIndex Index of the column.
	 * @return Object
	 */
	f_getCellValue: function(rowValue, columnIndex) {	
		var row=this.f_getRowByValue(rowValue, true);
		
		var cells=row._cells;
		var index=0;
		
		if (typeof(columnIndex)=="number") {
			for(var i=0;i<this._columns.length;i++) {
				var col=this._columns[i];
	
				if (col._visibility===null) {
					if (columnIndex==i) {
						return null;
					}
					continue;
				}

				if (columnIndex==i) {
					return cells[index];
				}
		
				index++;
			}
			
			return null;
		}
		
		for(var i=0;i<this._columns.length;i++) {
			var col=this._columns[i];

			if (col._visibility===null) {
				if (col._id==columnIndex) {
					return null;
				}
				continue;
			}

			if (col._id==columnIndex) {
				return cells[index];
			}
	
			index++;
		}
		
		return null;
	},
	_getRow: function(rowIndex, throwError, indexByValue) {
		if (!this._tbody) {
			f_core.Debug(f_dataGrid, "No body to get row #"+rowIndex);

			if (throwError) {
				throw new Error("Can not find row '"+rowIndex+"'.");
			}
			return null;
		}
		
		var row;
		
		if (typeof(rowIndex)!="number") {
			row=rowIndex;
			f_core.Assert(row._dataGrid, "Object is not a row of a datagrid !");
			
		} else {
			var rows=this._tbody.rows;
			if (!rows || rows.length<1) {
				if (throwError) {
					throw new Error("Can not find row #"+rowIndex+".");
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
			f_core.Debug(f_dataGrid, "No cells for row #"+rowIndex);
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
				dataGrid._callServer(index, cursorIndex, selection);
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
			dataGrid._callServer(0);
		});

		return true;
	},
	_callServer: function(firstIndex, cursorIndex, selection) {
//		f_core.Assert(!this._loading, "Already loading ....");
		if (!selection) {
			selection=0;
		}		
		
		var params=new Object;
		params.dataGridId=this.id;
		params.index=firstIndex;

		var orderColumnIndex=this.f_getProperty(f_prop.SORT_INDEX);
		
		if (typeof(orderColumnIndex)=="number" && orderColumnIndex>=0) {
			params.sortIndex=orderColumnIndex;
			
			if (this.f_getProperty(f_prop.SORT_ORDER)) {
				params.sortOrder=true;
			}
		}
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}

		this._waitingIndex=cursorIndex;
		this._waitingSelection=selection;
		
		f_core.Debug(f_dataGrid, "Call server  firstIndex="+firstIndex+" cursorIndex="+cursorIndex+" selection="+selection);

		if (true) {
			var tbody=this._tbody;
			
			var scrollBody=this._scrollBody;
			if (!this._oldHeight) {
				this._oldHeight=true;
				this._oldHeightStyle=scrollBody.style.height;
				scrollBody.style.height=scrollBody.offsetHeight+"px";
			}
						
			if (tbody) {
				this._releaseRows();
				this._releaseCells();
			
				// Detache temporairement !
				if (tbody.parentNode) {
					
					f_core.Assert(tbody.parentNode==this._table, "CallServer: Not same parent ? ("+tbody.parentNode+")");
					
					this._table.removeChild(tbody);
				}
	
				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}
			}
		}		

		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var dataGrid=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 			var waiting=dataGrid._waiting;
	 			if (!waiting) {	
		 			waiting=f_waiting.Create(dataGrid);
	 				dataGrid._waiting=waiting
	 			}
	 			
	 			waiting.f_setText(f_waiting.GetLoadingMessage());
	 			waiting.f_show();
	 		},
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_dataGrid, "Bad status: "+request.f_getStatus());
	 			
				if (dataGrid.f_processNextCommand()) {
					return;
				}
	 		
				dataGrid._loading=false;		
				
				var waiting=dataGrid._waiting;
				if (waiting) {
					waiting.f_hide();
				}
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 			var waiting=dataGrid._waiting;
				if (waiting) {
	 				waiting.f_setText(f_waiting.GetReceivingMessage());
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (dataGrid.f_processNextCommand()) {
					return;
				}
	 				
				var waiting=dataGrid._waiting;
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_dataGrid, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
	
					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
						f_core.Error(f_dataGrid, "Unsupported content type: "+responseContentType);
						return;
					}
				
					var ret=request.f_getResponse();
					
					//alert("ret="+ret);
					eval(ret);

				} finally {
					dataGrid._loading=undefined;
	
					if (waiting) {
						waiting.f_hide(true);
					}
				}
	
				var event=new f_event(dataGrid, f_event.CHANGE);
				try {
					dataGrid.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
	 		}
		});

		this._loading=true;
		request.f_setRequestHeader("X-Camelia", "dataGrid.update");
		request.f_doFormRequest(params);
	},
	_startNewPage: function(rowIndex) {
		// Appeler par la génération du serveur !

		var tbody=this._tbody;
		
		var scrollBody=this._scrollBody;
		if (this._oldHeight) {
			scrollBody.style.height=this._oldHeightStyle;
			this._oldHeight=undefined;
			this._oldHeightStyle=undefined;
		}
		
		if (false) {
			// Pas 2 fois !
			// Ca peut poser des problemes lors d'enchainement de filtres !
			
			if (tbody && tbody.parentNode) {		
				this._releaseRows();
				this._releaseCells();
				
				f_core.Assert(tbody.parentNode==this._table, "StartNewPage: Not same parent ? ("+tbody.parentNode+"/"+this._table+")");
				this._table.removeChild(tbody);
				this._tbody=undefined;	
	
				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}	
			}
		}
				
		this._first=rowIndex;

		if (this._selectable) {
			var oldCurrentSelection=(this._currentSelection.length>0);
			this._currentSelection=new Array;
			this._lastSelectedElement=undefined;
			
			// Reset des lignes selectionnées ...
			if (oldCurrentSelection) {
				// On avait des selections !
				
				if (!this._selectionFullState) {
					// Pas de fullstate: elles sont perdues !
					this._fireSelectionChangedEvent();
				}
			}
		}
		if (this._checkable) {
			var oldCurrentChecks=(this._currentChecks.length>0);
			this._currentChecks=new Array;
			
			// Reset des lignes selectionnées ...
			if (oldCurrentChecks) {
				// On avait des selections !
				
				if (!this._checkFullState) {
					// Pas de fullstate: elles sont perdues !
					this._fireSelectionChangedEvent();
				}
			}			
		}
		
		this._componentUpdated=false;
	},
	_updateNewPage: function() {
		// Appeler par la génération du serveur !

		f_core.Debug(f_dataGrid, "Update new page _rowCount='"+this._rowCount+"' _maxRows="+this._maxRows+"' _rows='"+this._rows+"'.");

		if (this._rowCount<0) {
			if (this._maxRows<this._first+this._rows) {
				this._maxRows=this._first+this._rows;
			}
		}

		var cursorRow=undefined;
		if (this._tbody) {
			f_core.Assert(this._tbody.parentNode!=this._table, "Tbody has not been detached !");
			
			this._table.appendChild(this._tbody);
			
			var rows=this._tbody.childNodes;
			//alert("rows="+rows.length);
					
			for(var i=0;i<rows.length;i++) {
				var row=rows[i];
				var index=row._index;
				if (index===undefined) {
					continue;
				}
				if (this._first+i==this._waitingIndex) {
					cursorRow=row;
					this._waitingIndex=undefined;
				}
			}
			
			this._table.appendChild(this._tbody);
		
			if (f_core.IsGecko()) {
				// On a un probleme de layout avec le DIV ! arg !
				
				if (this._rows>0 && !this.style.height) {
					var h=this._table.offsetHeight;
					
					var body=this._scrollBody;
					if (body) {
						var dh=body.offsetHeight-body.clientHeight;
						
						h+=dh;
					}
					
					this._table.parentNode.style.height=h+"px";
				}
			}
		}
	
		this._componentUpdated=true;

		if (this._interactiveShow) {
			this._interactiveShow=undefined;
			f_dataGrid._UpdateTitle(this);
		}

		if (cursorRow) {
			this._lastSelectedElement=cursorRow;
			var selection=this._waitingSelection;
			this._waitingSelection=undefined;
			
			if (selection & fa_selectionManager.RANGE_SELECTION) {
				selection|=fa_selectionManager.APPEND_SELECTION;
			}

			this._moveCursor(cursorRow, true, null, selection);
		}

		this.f_performPagedComponentInitialized();
	},
	_cancelServerRequest: function() {
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

				cursor.onmousedown=null;
				cursor.onclick=null;
				cursor._column=undefined;
				
				f_core.VerifyProperties(cursor);
			}
			
			var col=column._col;
			if (col) {
				column._col=undefined;

				f_core.VerifyProperties(col);
			}
			
			column._tcol=undefined;
			column._tcell=undefined;
			column._box=undefined;
			column._label=undefined;
			column._index=undefined;
			column._sorter=undefined;
			column._dataGrid=undefined;
			column._id=undefined;
			column._method=undefined;
			column._visibility=undefined;
			column._cellStyle=undefined;
			column._autoFilter=undefined;
			
			f_core.VerifyProperties(column);
		}
	},
	_releaseRows: function() {
		this._cursor=undefined; // HTMLRowElement

		var list=this._rowsPool;
		if (!list || list.length<1) {
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
			row._dataGrid=undefined;  // f_dataGrid
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
	_releaseCells: function() {
		var list=this._cellsPool;
		if (!list || list.length<1) {
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
			cell._dataGrid=undefined; // f_dataGrid
//			cell._cellStyleClass=undefined; // string
//			cell._text=undefined; // string

		//	f_core.VerifyProperties(cell);
		}
	},
	_performKeyDown: function(cevt) {
		var evt=cevt.f_getJsEvent();
	
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
					this._performElementCheck(cursor, true, evt, !this._isElementChecked(this._cursor));
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
			return f_core.CancelEvent(evt);;		
		}
		
		return true;
	},
	_openContextMenu: function(evt) {
		if (!this._cursor) {
			return;
		}
		
		var menu=this.f_getSubMenuById(f_dataGrid._ROW_MENU_ID);
		if (menu) {
			menu.f_open(this._cursor, {
				position: f_menu.MIDDLE_COMPONENT
				}, this, evt);
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
				
				this._updateElementStyle(r);
			}
		}
				
		if (cursorRow) {
			this._updateElementStyle(cursorRow);
		}
	},
	_forceFocus: function() {
		if (this._focus) {
			return;
		}
		if (this._cfocus) {
			this._cfocus.style.top=this._table.scrollTop+"px";
			
			f_core.SetFocus(this._cfocus);
			return;
		}

		f_core.SetFocus(this);
	},
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}
		if (this._focus || this.f_isDisabled()) {
			return;
		}

		if (this._cfocus) {
			this._cfocus.focus();
			return true;
		}
		
		this.focus();
		return true;
	},
	_nextCursorRow: function(evt, selection) {
		var trs=this._table.getElementsByTagName("TR");

		var tr=this._cursor;
		if (!tr) {
			// Selection du premier cursor

			for(var i=0;i<trs.length;i++) {
				var tr=trs[i];
				if (!tr._dataGrid) {
					continue;
				}
				
				this._moveCursor(tr, true, evt, selection);
				
				return;
			}

			return;
		}
		
		for(tr=tr.nextSibling;tr;tr=tr.nextSibling) {
			if (!tr._dataGrid) {
				continue;
			}
			
			// Si le CONTROL est appuyé on ne bouge que le curseur !
			this._moveCursor(tr, true, evt, selection);
			
			return;
		}
		
		// Page suivante ?
		if (!this._rows) {
			// Pas de page
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
				
		this.f_setFirst(nextFirst, nextFirst, selection);
	},
	_previousCursorRow: function(evt, selection) {		
		var trs=this._table.getElementsByTagName("TR");

		if (!this._cursor) {
			// Selection du dernier

			for(var i=trs.length-1;i>=0;i--) {
				if (!trs[i]._dataGrid) {
					continue;
				}
				
				this._moveCursor(trs[i], true, evt, selection);
				
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
			
			this._moveCursor(trs[i], true, evt, selection);
			
			return;
		}
		
		// Page pr?cedente ?
		if (!this._rows) {
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
		var trs=this.getElementsByTagName("TR");
		if (trs.length<1) {
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
			this._moveCursor(last, true, evt, selection);
			return;			
		}
		
		if (this._rows) {
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
					this._moveCursor(bottom, true, evt, selection);
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
		
		// On recherche la position du suivant !
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

		var pos=Math.floor(idx+h/trh);
		if (pos>=this._rowCount) {
			pos=this._rowCount-1;
		}		

		var row;
		for(var i=0;pos>=0;i++) {
			row=trs[i];
			
			if (!row._dataGrid) {
				continue;
			}
			
			pos--;
		}
		
		this._moveCursor(row, true, evt, selection);
	},
	_previousPageRow: function(evt, selection) {
		var trs=this.getElementsByTagName("TR");
		if (trs.length<1) {
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
			this._moveCursor(last, true, evt, selection);
			return;			
		}

		if (this._rows) {
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
		
		this._moveCursor(row, true, evt, selection);
	},
	_selectLastRow: function(evt, selection) {
		var trs=this.getElementsByTagName("TR");
		if (trs.length<1) {
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
			this._moveCursor(last, true, evt, selection);
			return;
		}
		// Le meme .... ben on va ? la derniere page
			
		if (this._rowCount<this._rows || this._rows<1) {
			return;
		}
		
		var nextFirst=this._rowCount-((this._rowCount+this._rows-1) % this._rows)-1;
		if (nextFirst<=this._first) {
			return;
		}
		
		var nextPos=this._rowCount-1;
		
		this.f_setFirst(nextFirst, nextPos, selection);
	},
	_selectTopRow: function(evt, selection) {
		var trs=this.getElementsByTagName("TR");
		if (trs.length<1) {
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
			
			this._moveCursor(row, true, evt, selection);
			return;
		}
		
		// Le meme .... ben on va ? la premiere page
			
		if (this._first<1) {
			return;
		}
		
		var nextFirst=0;
		var nextPos=0;
		
		this.f_setFirst(nextFirst, nextPos, selection);
	},
	/**
	 * @method hidden
	 */
	f_setRowValueColumn: function(idx) {
		this._rowValueColumnIndex=idx;
	},
	/**
	 * @method hidden
	 */
	f_setColumnsImages: function(images) {
		var j=0;
		for(var i=0;i<arguments.length;) {
			var col;

			for(;;) {
				col=this._columns[j++];
				if (col._visibility==true) {
					break;
				}
			}
		
			col._cellImage=arguments[i++];
			// col._cellImage à TRUE signie qu'il y a une image par cellule pour cette colonne !

			col._defaultCellImage=arguments[i++];
			if (col._defaultCellImage) {
				f_imageRepository.PrepareImage(col._defaultCellImage);				
			}
		}
	},
	/**
	 * @method hidden
	 */
	f_setColumnsCellStyle: function() {
		var cols=this._columns;
		var a=0;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}
			
			col._cellStyle=arguments[a++];
		}
	},
	/**
	 * @method hidden
	 */
	f_setCellImages: function(row) {
		// Les arguments
		var images=row._cellImages;
		if (!images) {
			return;
		}
		
		var cols=this._columns;
		var idx=0;
		var a=1;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			if (!col._visibility || (!col._cellImage && !col._defaultCellImage)) {
				continue;
			}
			
			var imageURL=arguments[a++];
			if (imageURL) {
				f_imageRepository.PrepareImage(imageURL);
			}
			
			var imageTag=images[idx++];
			
			if (f_dataGrid._USE_BACKGROUND_IMAGE) {
				if (imageURL) {
					imageURL="url("+imageURL+")";
					
				} else {
					imageURL="none";
				}
							
				imageTag.style.backgroundImage=imageURL;
				
			} else {
				if (!imageURL) {
					imageURL=this._blankImageURL;
				}
			
				imageTag.src=imageURL;			
			}

			if (idx>=images.length || a==arguments.length) {
				break;
			}
		}
	},
	/**
	 * Specify the image of a cell.
	 * 
	 * @method public
	 * @param number row
	 * @param number columnIndex
	 * @param string imageURL 
	 * @return void
	 */
	f_setCellImageURL: function(row, columnIndex, imageURL) {
		var cols=this._columns;
		var col=null;
		var cindex=0;
		for(var i=0;i<cols.length;i++) {
			col=cols[i];
			if (col._visibility===null) {
				continue;
			}
			if (columnIndex==cindex) {
				break;
			}
			cindex++;
		}
		if (cindex==cols.length) {
			return;
		}
		
		if (!col._cellImage && !col._defaultCellImage) {
			return;
		}
		
		var images=row._cellImages;
		if (!images || images.length<=cindex) {
			return;
		}

		var imageTag=images[cindex]
		
		if (f_dataGrid._USE_BACKGROUND_IMAGE) {
			if (imageURL) {
				imageURL="url("+imageURL+")";
				
			} else {
				imageURL="none";
			}
						
			imageTag.style.backgroundImage=imageURL;
			
		} else {
			if (!imageURL) {
				imageURL=this._blankImageURL;
			}
		
			imageTag.src=imageURL;			
		}
	},
	/**
	 * @XXX
	 * @return string
	 */
	f_getCellImageURL: function(row, columnIndex) {
	},
	/**
	 * @method hidden
	 */
	f_setColumnsStyleClass: function() {
		this._columnsStyleClass=true;
		
		var cols=this._columns;
		var a=0;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}
			
			col._userClassOdd=arguments[a++];
			if (!col._userClassOdd) {
				continue;
			}
			
			var next=arguments[a++];
			if (next) {
				col._userClassEven=next;
				continue;
			}

			col._userClassEven=col._userClassOdd;
		}
	},
	/**
	 * @method hidden
	 */
	f_setCellStyleClass: function(row) {
		var tds=row.getElementsByTagName("TD");
		var cols=this._columns;
		row._cellsStyleClass=true;
		
		var argIdx=1;
		var tdIdx=0;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}

			if (!col._cellStyle) {
				tdIdx++;
				continue;
			}

			var td=tds[tdIdx++];
					
			td._cellStyleClass=arguments[argIdx++];
			td.className=td._cellStyleClass;
		}
	},
	/**
	 * @method hidden
	 */
	f_setColumnSorters: function(sorters) {
		var cols=this._columns;

		var a=0;
		for(var i=0;i<cols.length && a<arguments.length;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}
			
			var sorter=arguments[a++];
			if (!sorter) {
				continue;
			}
				
			this._installSorter(col, sorter);
		}
	},
	/**
	 * @method hidden
	 */
	f_setAutoFilters: function() {
		var cols=this._columns;

		for(var i=0;i<arguments.length;i++) {
			var index=arguments[i];
			
			f_core.Assert(index>=0 && index<cols.length, "Bad autoFilterColumnIndex !");
	
			var col=cols[index];
			col._autoFilter=i;
		}	
	},
	/**
	 * @method hidden
	 */
	f_enableSorters: function(sortColumnIndex, ascending) {
		var cols=this._columns;

		f_core.Assert(sortColumnIndex>=0 && sortColumnIndex<cols.length, "Bad sortColumnIndex !");

		var col=cols[sortColumnIndex];
		if (col!=f_dataGrid.Sort_Server) {
			this._ascendingOrder=ascending;
			this._currentSort=col;
			
			this._updateTitleStyle(col);
			return;
		}

		this._initSortColumn=col;
		this._initSortAscending=(ascending===true);
	},
	_installSorter: function(column, method) {
		f_core.Assert(column._head, "No Title for column '"+column._index+"'.");
	
		if (typeof(method)!="function") {
			method=eval(method);
			
			f_core.Assert(typeof(method)=="function", "Bad sort method for column '"+column._index+"' !");
		}
	
		column._method=method;
	
		var th=column._head;
		
		th._column=column;
		th.onmouseover=f_dataGrid._Title_onMouseOver;
		th.onmouseout=f_dataGrid._Title_onMouseOut;
		th.onmousedown=f_dataGrid._Title_onMouseDown;
		th.onmouseup=f_dataGrid._Title_onMouseUp;
		th.style.cursor="pointer";
		th.style.cursor="hand";		
	},
	/**
	 * @method public
	 * @param Object col Column to sort
	 * @param optional boolean ascending Sort ascending.
	 * @param optional hidden boolean init Sort is already made !
	 * @return void
	 */
	f_sortColumn: function(col, ascending, init) {
		if (this._currentSort && this._currentSort!=col) {
			var old=this._currentSort;
			this._currentSort=null;
			
			this._updateTitleStyle(old);
		}
	
	
		if (!col._method) {
			if (!init) {
				this._sortTable(null);
			}
			return;
		}
	
		if (ascending===undefined) {
			if (this._currentSort==col) {
				this._ascendingOrder=!this._ascendingOrder;
				
			} else {
				this._ascendingOrder=true;
				this._currentSort=col;
			}
		} else {
			this._ascendingOrder=ascending;
			this._currentSort=col;
		}
	
		this._updateTitleStyle(col);
		
		if (!init) {
			this._sortTable(col._method, col._index, this._ascendingOrder);
		}
	},
	_updateTitleStyle: function(column) {
		var className=this._className+"_tcell";

		if (this._columnSelected==column) {
			className+="_selected";

		} else if (this._columnOver==column) {
			className+="_over";
		}

		if (column._head.className!=className) {
			column._head.className=className;
		}
	
		className=this._className+"_ttext";
		
		var wc=className;
		if (this._currentSort==column) {
			if (this._ascendingOrder) {
				className+="_ascending";
				
			} else {
				className+="_descending";
			}
		}
		
		if (column._restoreClass) {
			column._restoreClass=className;
			className=wc;
		}
		
		if (column._label.className!=className) {
			column._label.className=className;
		}
	},
	_a_updateFilterProperties: function(filterProperties) {
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
			
			dataGrid._callServer(0);
		});
		
		return false;
	},	
	_a_componentCaptureMenuEvent: function() {
		return null;
	},
	_sortTable: function(method, columnIndex, ascending) {
		if (columnIndex===undefined) {
			columnIndex=-1;
			ascending=true;
		}

		this.f_setProperty(f_prop.SORT_INDEX, columnIndex);
		this.f_setProperty(f_prop.SORT_ORDER, ascending);
			
		if (this._rowCount<0 || (this._rows && this._rows<this._rowCount) || method==f_dataGrid.Sort_Server) {
			// Plusieurs pages !
			// Il faut partir cot? serveur !

			f_core.Debug(f_dataGrid, "_sortTable SERVER:\nColumnIndex="+columnIndex+"\nrowCount="+this._rowCount+"\nrows="+this._rows+"\nxascending="+ascending+"\nSort="+method);
			
			return this.f_setFirst(this._first);
		}
	
		if (!method) {
			method=f_dataGrid._Sort_Index;
		}
		
		var columns=this._columns;
		var tdIndex=0;
		for(var i=0;i<columns.length;i++) {
			var col=columns[i];
			if (!col._visibility) {
				continue;
			}

			if (columnIndex==i) {
				break;	
			}

			tdIndex++;
		}
		
		var up=(ascending!==false);
		
		f_core.Debug("f_dataGrid", "_sortTable CLIENT:\nColumnIndex="+columnIndex+"\ntdIndex="+tdIndex+"\nascending="+ascending+"\nSort="+method);
		
		function _internalSort(obj1, obj2) {
			var tc1 = obj1.childNodes[tdIndex];
			var tc2 = obj2.childNodes[tdIndex];
			var ret = method.call(this, tc1._text, tc2._text, tc1, tc2);

			return (up)? ret:-ret;
		}
		
		var body=f_core.GetFirstElementByTagName(this._table, "TBODY", true);
		f_core.Assert(body, "No body for data table of dataGrid !");
		var trs=new Array;
		var childNodes=body.childNodes;
		var idx=0;
		for(var i=0;i<childNodes.length;i++) {
			var row=childNodes[i];
			if (row._index===undefined) {
				continue;
			}
			
			trs.push(row);
		}
		
		trs.sort(_internalSort);

		var internetExplorer=f_core.IsInternetExplorer();
		this._table.removeChild(body);
		
		while(body.firstChild) {
			body.removeChild(body.firstChild);
		}

		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			row._curIndex=null;
			
			body.appendChild(row);
		}

		var odd=this._className+"_row_odd";
		var even=this._className+"_row_even";

		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			
			if (i % 2) {
				row._className=odd;
				
			} else {
				row._className=even;
			}
			
			this._updateElementStyle(row);
		}
	
		this._table.appendChild(body);	
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
		
		if (!this._isElementSelected(row)) {
			return false;
		}

		return this._performElementSelection(row, show, jsEvent, false);
	},
	/**
	 * Check a row.
	 *
	 * @method public
	 * @param any rowValue Value associated to the row
	 * @param boolean show Show the checked row.
	 * @param hidden optional Event jsEvent
	 * @return boolean Returns <code>true</code> if check has successed.
	 */
	f_checkRow: function(rowValue, show, jsEvent) {
		var row=this.f_getRowByValue(rowValue, true);
			
		if (this._isElementChecked(row)) {
			return false;
		}
		
		return this._performElementCheck(row, show, jsEvent, true);
	},
	/**
	 * Uncheck a row.
	 *
	 * @method public
	 * @param any rowValue Value associated to the row
	 * @param hidden optional Event jsEvent
	 * @return boolean Returns <code>true</code> if uncheck has successed.
	 */
	f_uncheckRow: function(rowValue, jsEvent) {
		var row=this.f_getRowByValue(rowValue, true);
		
		if (!this._isElementChecked(row)) {
			return false;
		}
		
		return this._performElementCheck(row, false, jsEvent, false);
	},
	
	/**
	 * Returns <code>true</code> if the receiver is checked, and <code>false</code> otherwise
	 *
	 * @method public
	 * @param any rowValue Value associated to the row, or a row object.
	 * @return boolean The checked state of the row
	 */
	f_getChecked: function(rowValue) {
		var row=this.f_getRowByValue(rowValue, true);
	
		return this._isElementValueChecked(row);
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

	_getElementValue: function(row) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._getElementValue: Invalid element parameter ! ("+row+")");

		return row._index;
	},

	_isElementDisabled: function(row) {
		return false;
	},

	_isElementSelected: function(row) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._isElementSelected: Invalid element parameter ! ("+row+")");
		
		return row._selected;
	},
	
	_setElementSelected: function(row, selected) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._setElementSelected: Invalid element parameter ! ("+row+")");
		
		row._selected=selected;
	},
	
	_showElement: function(row) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._showElement: Invalid element parameter ! ("+row+")");

		var scrollBody=this._scrollBody;
		if (row.offsetTop-scrollBody.scrollTop<0) {
			scrollBody.scrollTop=row.offsetTop;
			return;
		}
			
		if (row.offsetTop+row.offsetHeight-scrollBody.scrollTop>scrollBody.clientHeight) {
			scrollBody.scrollTop=row.offsetTop+row.offsetHeight-scrollBody.clientHeight;
		}		
	},
	
	_listVisibleElements: function() {
		return this._rowsPool;
	},
	_isElementChecked: function(row) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._isElementChecked: Invalid element parameter ! ("+row+")");

		return (row._checked)?true:false;
	},
	_setElementChecked: function(row, checked) {
		f_core.Assert(row && row.tagName=="TR", "f_dataGrid._setElementChecked: Invalid element parameter ! ("+row+")");

		row._checked=checked;
	}	
}
 
var f_dataGrid=new f_class("f_dataGrid", null, __static, __prototype, f_component, fa_readOnly, fa_disabled, fa_pagedComponent, fa_subMenu, fa_commands, fa_checkManager);
