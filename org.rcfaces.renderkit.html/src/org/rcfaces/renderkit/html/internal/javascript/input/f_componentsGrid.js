/*
 * $Id$
 */

/**
 * 
 * @class public f_componentsGrid extends f_grid
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
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
	
	f_componentsGrid: function() {
		this.f_super(arguments);
		
		this._cellStyleClass="f_cGrid_cell";
		this._rowStyleClass="f_cGrid_row";
	},
	/*
	f_finalize: function() {
		this.f_super(arguments);
	},
	*/
	f_callServer: function(firstIndex, length, cursorIndex, selection, partialWaiting) {
//		f_core.Assert(!this._loading, "Already loading ....");
		if (!selection) {
			selection=0;
		}		
		
		var params=new Object;
		params.componentsGridId=this.id;
		params.index=firstIndex;
		if (length>0) {
			params.rows=length;
		}

		var orderColumnIndex=this.f_getProperty(f_prop.SORT_INDEX);
		if (orderColumnIndex) {
			params.sortIndex=orderColumnIndex;
		}
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}

		this._waitingIndex=cursorIndex;
		this._waitingSelection=selection;
		this._partialWaiting=partialWaiting;
		
		f_core.Debug(f_componentsGrid, "f_callServer: Call server  firstIndex="+firstIndex+" cursorIndex="+cursorIndex+" selection="+selection);

		if (!partialWaiting) {
			var tbody=this._tbody;
			
			var scrollBody=this._scrollBody;
			if (!this._oldHeight) {
				this._oldHeight=true;
				this._oldHeightStyle=scrollBody.style.height;
				scrollBody.style.height=scrollBody.offsetHeight+"px";
			}
						
			if (tbody) {
				this.f_releaseRows();
				this.f_releaseCells();
				
//				this._table.style.display="none";

				if (this._waitingMode==f_grid.END_WAITING) {
					this.f_removePagedWait();
				}
				this._shadowRows=undefined;
				this._endRowIndex=undefined;

				f_classLoader.SerializeInputs(params, tbody);

				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}
			
				var serializedForm=this.f_getClass().f_getClassLoader().f_garbageObjects(true);
				f_core.Debug(f_componentsGrid, "f_callServer: serializedForm="+serializedForm);
				if (serializedForm) {
					params[f_core.SERIALIZED_DATA]=serializedForm;
				}
				
				params.serializedFirst=this._first;
				params.serializedRows=this._rows;
			}
		}
		
		var waitingObject=(partialWaiting && !length)?this._pagedWaiting:this._waiting;

		var url=f_env.GetViewURI();
		var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var dataGrid=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 			if (!waitingObject) {
	 				waitingObject=f_waiting.Create(dataGrid, null, false);
	 				dataGrid._waiting=waitingObject;
	 			}
	 			
	 			if (waitingObject) {
		 			waitingObject.f_setText(f_waiting.GetLoadingMessage());
		 			waitingObject.f_show();
			 	}
		 	},
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_componentsGrid, "Bad status: "+request.f_getStatus());
	 				 				 			
		 		if (dataGrid.f_performErrorEvent(request, f_error.HTTP_ERROR, text)===false) {
					dataGrid._loading=undefined;		
	
					if (waitingObject) {
						waitingObject.f_hide();
					}
			 		return;
		 		}
	 			
				if (dataGrid.f_processNextCommand()) {
					return;
				}
	 		
				dataGrid._loading=undefined;		

				if (waitingObject) {
					waitingObject.f_hide();
				}
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
				if (waitingObject) {
	 				waitingObject.f_setText(f_waiting.GetReceivingMessage());
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (dataGrid.f_processNextCommand()) {
					return;
				}
	 				
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						dataGrid.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}

					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						dataGrid.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
	
					var responseContentType=request.f_getResponseContentType();
					
					if (responseContentType.indexOf(f_error.ERROR_MIME_TYPE)>=0) {
				 		dataGrid.f_performErrorEvent(request, f_error.APPLICATION_ERROR, content);
						return;
					}

					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
				 		dataGrid.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);
						return;
					}
					
					var ret=request.f_getResponse();
					
					if (dataGrid._waitingLoading) {
						if (dataGrid._waitingMode==f_grid.END_WAITING) {
							dataGrid.f_removePagedWait();
						}
					}
					
					//alert("ret="+ret);
					try {
						eval(ret);
						
					} catch (x) {
			 			dataGrid.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
					}

				} finally {
					dataGrid._loading=undefined;
					
					if (waitingObject) {
						waitingObject.f_hide(true);					
					}					
					
					dataGrid._waitingLoading=undefined;

					if (dataGrid._waitingMode==f_grid.END_WAITING) {						
						dataGrid.f_addPagedWait();
						
					} else if (dataGrid._waitingMode==f_grid.ROWS_WAITING) {
						dataGrid.f_addWaitingRows();
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
		request.f_setRequestHeader("X-Camelia", "componentsGrid.update");
		request.f_doFormRequest(params);
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_startNewPage: function(rowIndex) {
		// Appeler par la génération du serveur !

		var tbody=this._tbody;
		
		var scrollBody=this._scrollBody;
		if (this._oldHeight) {
			scrollBody.style.height=this._oldHeightStyle;
			this._oldHeight=undefined;
			this._oldHeightStyle=undefined;
		}		

		if (!this._waitingLoading) {
			this._first=rowIndex;

			if (this._selectable) {
				var oldCurrentSelection=(this._currentSelection.length);
				this._currentSelection=new Array;
				this._lastSelectedElement=undefined;
				
				// Reset des lignes selectionnées ...
				if (oldCurrentSelection) {
					// On avait des selections !
					
					if (!this._selectionFullState) {
						// Pas de fullstate: elles sont perdues !
						this.fa_fireSelectionChangedEvent();
					}
				}
			}
		}		
		this.fa_componentUpdated=false;
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_updateNewPage: function(rowCount) {
		// Appeler par la génération du serveur !

		f_core.Debug(f_componentsGrid, "f_updateNewPage: Update new page _rowCount='"+this._rowCount+"' _maxRows="+this._maxRows+"' _rows='"+this._rows+"'.");

		var tbody=this._tbody;
		try {
			this.f_getClass().f_getClassLoader().f_processScripts(this, tbody);
			
		} catch (x) {
 			f_core.Error(f_componentsGrid, "f_updateNewPage: Can not load content of componentsGrid cell '"+content+"'", x);
		}

		if (this._rowCount<0) {
			var poolSize=this._rowsPool.length+this._first;
			if (this._maxRows<poolSize) {
				this._maxRows=poolSize;
			}
		}

		var cursorRow=undefined;
		if (!this._partialWaiting) {
			var newDisplay="table";
			if (f_core.IsInternetExplorer()) {
				newDisplay="block";
			}
			this._table.style.display=newDisplay;
			
			var rows=tbody.childNodes;				
			for(var i=0;i<rows.length;i++) {
				var row=rows[i];
				var index=row._index;
				if (index===undefined) {
					continue;
				}
				if (this._first+i==this._waitingIndex) {
					cursorRow=row;
					this._waitingIndex=undefined;
					break;
				}
			}
		
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
			
		
			switch(this._waitingMode) {
			case f_grid.ROWS_WAITING:
				this.f_addWaitingRows();
				break;
				
			case f_grid.END_WAITING:
				this.f_addPagedWait();
				break;
			}
		}
	
		this.fa_componentUpdated=true;

		this.f_updateTitle();

		if (this._interactiveShow || !this._titleLayout ) {
			this._interactiveShow=undefined;
		}

		if (cursorRow) {
			this._lastSelectedElement=cursorRow;
			var selection=this._waitingSelection;
			this._waitingSelection=undefined;
			
			if (selection & fa_selectionManager.RANGE_SELECTION) {
				selection|=fa_selectionManager.APPEND_SELECTION;
			}

			this.f_moveCursor(cursorRow, true, null, selection);
		}

		this.f_performPagedComponentInitialized();
	},
	f_update: function() {
		var rows=f_grid.ListRows(this._table);
	
		var rowClasses= this._rowStyleClasses;
	
		var cellStyleClassSetted=null;
		var columns=this._columns;
		
		var cellIdx=0;
		for(var i=0;i<columns.length;i++) {
			var col=columns[i];
			if (!col._visibility) {
				continue;
			}	
			cellIdx++;
			
			if (!col._cellStyleClassSetted) {			
				continue;
			}
			
			if (!cellStyleClassSetted) {
				cellStyleClassSetted=new Array;
			}
			cellStyleClassSetted.push(cellIdx-1);
		}
	
		for(var i=0;i<rows.length;i++) {
			var row=rows[i];
			
			this._rowsPool.push(row);
			row._dataGrid=this;
			
			var rowIdx=this._rowsPool.length;
			
			row._index=f_core.GetAttribute(row, "v:index");
			row.id=this.id+":row"+i;

			row._className=rowClasses[i % rowClasses.length];
			
			if (cellStyleClassSetted) {
				var cells=row.cells;
				
				for(var j=0;j<cellStyleClassSetted.length;j++) {
					var cellIdx=cellStyleClassSetted[j];
					
					var cell=cells[cellIdx];
					
					cell._cellStyleClass=f_core.GetAttribute(cell, "v:class");
				}
			}
			
			if (this._selectable) {
				row.onmousedown=f_grid.RowMouseDown;
				row.onmouseup=f_grid.FiltredCancelJsEventHandler;
				row.onclick=f_grid.FiltredCancelJsEventHandler;
				row.ondblclick=f_grid.RowMouseDblClick;
				row.onfocus=f_grid.GotFocus;
			
				// La ligne peut être sélectionnée	
				
				// Nous sommes en fullstate ?
				if (!this._selectionFullState && f_core.GetBooleanAttribute(row, "v:selected")) {
					this.f_updateElementSelection(row, true);	
				}
			}
		}
		
		this.f_super(arguments);		
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_sortClientSide: function(methods, ascendings, tdIndexes) {
			
		function internalSort(obj1, obj2) {	
			for(var i=0;i<methods.length;i++) {
				var tdIndex=tdIndexes[i];
				
				var tc1 = obj1.childNodes[tdIndex];
				var tc2 = obj2.childNodes[tdIndex];

				 var ret=methods[i].call(this, tc1, tc2, tc1._index, tc2._index);
				 if (!ret) {
					continue;
				 }
				 
				return (ascendings[i])? ret:-ret;
			}
			
			return 0;
		}
		
		var body=this._table.tBodies[0];
		f_core.Assert(body, "f_grid._sortTable: No body for data table of dataGrid !");
		
		var trs=new Array;
		var childNodes=body.rows;
		var idx=0;
		for(var i=0;i<childNodes.length;i++) {
			var row=childNodes[i];
			if (row._index===undefined) {
				continue;
			}
			
			trs.push(row);
		}
		
		trs.sort(internalSort);

		this._table.removeChild(body);
		
		while(body.firstChild) {
			body.removeChild(body.firstChild);
		}

		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			row._curIndex=null;
			
			body.appendChild(row);
		}

		var rowClasses= this._rowStyleClass;

		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			
			row._className=rowClasses[i % rowClasses.length];
			
			this.fa_updateElementStyle(row);
		}
	
		this._table.appendChild(body);	
	},
	
	/**
	 * @method protected
	 */
	f_initializeTableLayout0: function() {
		var table = f_core.GetChildByCssClass(this, "f_grid_table");
		f_core.Assert(table, "f_componentsGrid.f_initializeTableLayout: Can not find table 'f_grid_table'");
		this._table = table;
		table._dataGrid=this;	

		f_core.Assert(table.tBodies.length<=1, "f_componentsGrid.f_initializeTableLayout: Too many TBODY ! ("+table.tBodies.length+")");	
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
	f_updateColumnsLayout0: function(columns) {
		var heads;
		var cols;
		var cols2;
		
		if (this._title) {
			heads=this._title.getElementsByTagName("th");
			cols=this._title.getElementsByTagName("col");
			cols2=this._table.getElementsByTagName("col");			
			
		} else {
			heads=this._table.getElementsByTagName("th");
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
									
			if (column._visibility) {
				var head=heads[v];

				head.onmouseover=f_grid._Title_onMouseOver;
				head.onmouseout=f_grid._Title_onMouseOut;
				head.onmousedown=f_grid._Title_onMouseDown;
				head.onmouseup=f_grid._Title_onMouseUp;
				
				column._head=head;
				column._col=cols[v];
				if (cols2) {
					column._col2=cols2[v];
				}
				head._column=column;
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
		}
	},
	
	/**
	 * @method hidden
	 * @param String rowId
	 * @param Object rowProperties
	 * @return Object
	 */
	f_addRow2: function(rowId, rowProperties, cell1Properties, cell1Content) {
		f_core.Assert(this._tbody, "f_dataGrid.f_addRow2: No table body !");
		
		var row;
		var firstCell;
		var shadowRows=this._shadowRows;
		if (shadowRows && shadowRows.length) {
			row=shadowRows.shift();
			firstCell=row.firstChild;
			
			while (firstCell.hasChildNodes()) {
				firstCell.removeChild(firstCell.lastChild);
			}
			
			f_core.Assert(row.tagName.toLowerCase()=="tr", "f_componentsGrid.f_addRow2: Invalid row ! "+row);
			
		} else {
			row=document.createElement("tr");
			this._tbody.appendChild(row);
		}
		this._rowsPool.push(row);
		row._dataGrid=this;
		
		var rowIdx=this._rowsPool.length;
		
		var idx=0;
		row._index=arguments[idx++];
		row.id=rowId;
		
		if (this._selectable) {
			row.onmousedown=f_grid.RowMouseDown;
			row.onmouseup=f_core.CancelJsEventHandler;
			row.onclick=f_core.CancelJsEventHandler;
			row.ondblclick=f_grid.RowMouseDblClick;
			row.onfocus=f_grid.GotFocus;
		}
		
		var properties=arguments[idx++];
		
		var className=null;
		
		if (properties) {
			className=properties._styleClass;
		}
		if (!className) {
			className=this._rowStyleClasses[rowIdx % this._rowStyleClasses.length];
		}
		row.className=className;
		row._className=className;
				
		if (this._selectable) {
			var selected=false;
			
			if (!this._selectionFullState && properties) {
				selected=properties._selected;
			}
			
			this.f_updateElementSelection(row, selected);
		}
		
		var cells=new Array;
		row._cells=cells;
		var rowValueColumnIndex=this._rowValueColumnIndex;
		var columns=this._columns;
		for(var i=0;i<columns.length;i++) {
			var col=columns[i];

			var td;
			if (col._visibility===null) {
				cells.push(null);
				continue;
			}
				
			properties=arguments[idx++];
			
			if (!col._visibility) {
				if (!properties) {
					properties=new Object;
				}
				
				td=properties;
				//td._text=cellText;
				cells.push(td);
				continue;
			}

			var content=arguments[idx++];
			
			if (firstCell) {
				td=firstCell;
				td.colSpan=1; // pour le shadow
				td.className="";
				firstCell=undefined;
				
			} else {
				td=document.createElement("td");
				row.appendChild(td);
			}
			
			this._cellsPool.push(td);
					
			td.valign="top";
			td.noWrap=true;
			
			var align=col._align;
			if (properties) {
				if (properties._styleClass) {
					row._cellsStyleClass=true;
					td._cellStyleClass=properties._styleClass;
				}
				if (properties._toolTipText) {
					td.tooltip=properties._toolTipText;
				}
				if (properties._align) {
					align=properties._align;
				}
			}
			td.align=align;
				
			try {
				this.f_getClass().f_getClassLoader().f_loadContent(this, td, content, false);
				
			} catch (x) {
	 			f_core.Error(f_componentsGrid, "f_addRow2: Can not load content of componentsGrid cell '"+content+"'");
			}
		}
		
		var initCursorValue=this._initCursorValue;
		if (!this._cursor && row._index==initCursorValue) {
			this._cursor=row;
			this._initCursorValue=undefined;
		}
		
		this.fa_updateElementStyle(row);
		
		return row;
	}
}
 
new f_class("f_componentsGrid", null, __static, __prototype, f_grid);
