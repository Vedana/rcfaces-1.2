/*
 * $Id$
 */

/**
 * 
 * @class public f_dataGrid extends f_grid, fa_readOnly, fa_checkManager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	
	/**
	 * @method private static
	 */
	_CheckMouseButtons: f_core.CancelJsEventHandler,
	
	/**
	 * @method private static
	 */
	_ReturnFalse: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (!f_grid.VerifyTarget(evt)) {
			return true;
		}
	
		if (f_core.IsPopupButton(evt)) {
			return f_core.CancelJsEvent(evt);
		}
		
		return true;
	},
	
	/**
	 * @method private static
	 */
	_CheckSelect: function(evt) {
		var row=this._row;
		var dataGrid=row._dataGrid;
		
		if (dataGrid.f_getEventLocked()) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		// Il faut bloquer le bubble !
		evt.cancelBubble = true;

		if (dataGrid.f_isReadOnly()) {
			return false;
		}
		
		if (row!=dataGrid._cursor) {
			dataGrid.f_moveCursor(row, true, evt);
		}
		
		var checked;
		if (this.type=="radio") {
			checked=true;
			
		} else {
			checked=!dataGrid.fa_isElementChecked(row);
		}
	
		dataGrid.fa_performElementCheck(row, true, evt, checked);
		
		if (f_core.IsGecko()) {
			if (dataGrid.fa_isElementChecked(row)!=checked) {
				return false;
			}
		}
		 		 
		return true;
	},
	
	/**
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	 * @method hidden static
	 * @param String text1
	 * @param String text2
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
	}
}
 
var __prototype = {
	
	f_dataGrid: function() {
		this.f_super(arguments);

		this._showCursor=true;
		this._cellStyleClass="f_dataGrid_cell";
		this._rowStyleClass="f_dataGrid_row";
	},
	/*	
	f_finalize: function() {
		this.f_super(arguments);
	},
	*/
	fa_isElementChecked: function(row) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_dataGrid.fa_isElementChecked: Invalid element parameter ! ("+row+")");

		return (row._checked)?true:false;
	},
	fa_setElementChecked: function(row, checked) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_dataGrid.fa_setElementChecked: Invalid element parameter ! ("+row+")");

		row._checked=checked;
	},
	/**
	 * @method protected
	 */
	fa_updateElementStyle: function(row) {
		this.f_super(arguments, row);	
		
		var input=row._input;
		if (input && row._cheked!=input.checked) {
			input.checked=row._checked;
			
			if (f_core.IsInternetExplorer()) {
				// Il se peut que le composant ne soit jamais affiché 
				// auquel cas il faut utiliser le defaultChecked !
				input.defaultChecked=row._checked;
			}
		}
	},
	/**
	 * 
	 * @method public
	 * @param any value The value of the new row
	 * @param String columnValue1 A parameter for each column 
	 * @param String columnValue2 A parameter for each column 
	 * @return Object
	 */
	f_addRow: function(value, columnValue1, columnValue2) {
		f_core.Assert(this._rows==0, "All rows of the DataGrid must be loaded (attribute rows=0)");
		
		var properties=new Object;
		
		var l=[value, properties];
		
		f_core.PushArguments(l, arguments, 1);
		
		var ret=this.f_addRow2.apply(this, l);
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
	f_addRow2: function() {
		f_core.Assert(this._tbody, "f_dataGrid.f_addRow2: No table body !");
		
		var doc=this._tableDocument;
		
		var row;
		var firstCell;
		var shadowRows=this._shadowRows;
		if (shadowRows && shadowRows.length) {
			row=shadowRows.shift();
			firstCell=row.firstChild;
			
			while (firstCell.hasChildNodes()) {
				firstCell.removeChild(firstCell.lastChild);
			}
			
			f_core.Assert(row.tagName.toLowerCase()=="tr", "Invalid row ! "+row);
			
		} else {
			row=doc.createElement("tr");
			this._tbody.appendChild(row);
		}
		this._rowsPool.push(row);
		row._dataGrid=this;
		
		var rowIdx=this._rowsPool.length;
		
		var idx=0;
		row._index=arguments[idx++];
		row.id=this.id+":row"+rowIdx;
		
		if (this._selectable || this._checkable) {
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
		
		var checked=undefined;
		if (this._checkable) {	
			if (!this._checkFullState && properties) {
				checked=properties._checked;
			}
		}
		
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
					if (firstCell) {
						td=firstCell;
						td.colSpan=1; // pour le shadow
						td.className="";
						firstCell=undefined;
						
					} else {
						td=doc.createElement("td");
						row.appendChild(td);
					}
					
					this._cellsPool.push(td);
					
					td.valign="top";
					td.noWrap=true;
					td._text=cellText;
					
					td.align=col._align;

					var cellImage=null;
					if (col._cellImage || col._defaultCellImageURL) {
						cellImage=doc.createElement("img");
						cellImage.className="f_grid_imageCell";
						cellImage.width=f_grid.IMAGE_WIDTH;
						cellImage.height=f_grid.IMAGE_HEIGHT;

						var imageURL=col._defaultCellImageURL;
						// L'image par cellule est spécifié par une méthode evaluée plus tard ...

						if (f_grid.USE_BACKGROUND_IMAGE) {	
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

						var cellImages=row._cellImages;
						if (!cellImages) {
							cellImages=new Array;
							row._cellImages=cellImages;
						}
						cellImages[countTd]=cellImage;
					}

					if (this._selectable) {
//						td.onmouseup=f_dataGrid._ReturnFalse;
//						td.onmousedown=f_dataGrid._ReturnFalse;
//						td.ondblclick=f_core.CancelJsEventHandler;
						td.onclick=f_dataGrid._ReturnFalse;
						
						td._dataGrid=this;
						td.onfocus=f_grid.GotFocus;
					}
								
					if (this._checkable) {
						if (!countTd) {
							var input=doc.createElement("input");
							row._input=input;
							
							input.id=this.id+"::"+rowIdx;
							
							if (this._checkCardinality==fa_cardinality.ONE_CARDINALITY) {
								input.type="radio";
								input.value="CHECKED_"+rowIdx;
								input.name=this.id+"::radio";
								
							} else {							
								input.type="checkbox";
								input.value="CHECKED";
								input.name=input.id;
							}
													
							input.onmousedown=f_dataGrid._CheckMouseButtons;
							input.onmouseup=f_dataGrid._CheckMouseButtons;
							input.onclick=f_dataGrid._CheckSelect;
							input.onfocus=f_grid.GotFocus;
							input._row=row;
							input.tabIndex=-1;
							input.className="f_grid_input";
							
							if (this.f_isDisabled()) {
								input.disabled=true;
							}
							
							td.appendChild(input);

							if (cellImage) {
								td.appendChild(cellImage);
							}

							var label=doc.createElement("label");
							row._label=label;
							if (cellText) {
								label.appendChild(doc.createTextNode(cellText));
							}
							label.className="f_grid_label";
							td.appendChild(label);
							
							this.fa_updateElementCheck(row, checked);
						}
					}
					
					if (countTd || !row._input) {
						if (cellImage) {
							td.appendChild(cellImage);
						}
						
						var span=doc.createElement("label");
						span.className="f_grid_label";
						td.appendChild(span);
						
						if (!cellText) {
							cellText=" ";
						}
						span.appendChild(doc.createTextNode(cellText));							
					}
					countTd++;
				} else {
					td=new Object;
					td._text=cellText;
				}
			}
			
			cells.push(td);
		}
		
		var initCursorValue=this._initCursorValue;
		if (!this._cursor && row._index==initCursorValue) {
			this._cursor=row;
			this._initCursorValue=undefined;
		}
		
		this.fa_updateElementStyle(row);
		
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
			this.fa_fireSelectionChangedEvent();
		}
		if (checkChanged) {
			this.fa_fireCheckChangedEvent();
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
	/**
	 * Returns an array of content of each cell of the specified row.
	 *
	 * @method public
	 * @param any rowValue Row value, a row object, or the index of row into the table.
	 * @param boolean onlyVisible Keey only visible columns.
	 * @return String[] 
	 */
	f_getRowValues: function(rowValue, onlyVisible) {
		f_core.Assert(rowValue, "Invalid rowValue parameter ! ("+rowValue+")");
		var row;
		
		if (rowValue._dataGrid) {
			row=rowValue;
			
		} else if (typeof(rowValue)=="number") {
			row=this.f_getRow(rowValue, true);
			
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
			row=this.f_getRow(rowValue, true);
			
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
		
		var columns=this._columns;
		if (typeof(columnIndex)=="number") {
			for(var i=0;i<columns.length;i++) {
				var col=columns[i];
	
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
		
		for(var i=0;i<columns.length;i++) {
			var col=columns[i];

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
	f_callServer: function(firstIndex, length, cursorIndex, selection, partialWaiting) {
//		f_core.Assert(!this._loading, "Already loading ....");
		if (!selection) {
			selection=0;
		}		
		
		var params=new Object;
		params.dataGridId=this.id;
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
		
		f_core.Debug(f_dataGrid, "Call server  firstIndex="+firstIndex+" cursorIndex="+cursorIndex+" selection="+selection);

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
			
				// Detache temporairement !
				if (tbody.parentNode) {
					
					f_core.Assert(tbody.parentNode==this._table, "CallServer: Not same parent ? ("+tbody.parentNode+")");
					
					this._table.removeChild(tbody);
				}

				if (this._waitingMode==f_grid.END_WAITING) {
					this.f_removePagedWait();
				}
				this._shadowRows=undefined;
				this._endRowIndex=undefined;
	
				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}
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
	 			f_core.Info(f_dataGrid, "Bad status: "+request.f_getStatus());
	 				 				 			
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
		request.f_setRequestHeader("X-Camelia", "dataGrid.update");
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
		
		if (false) {
			// Pas 2 fois !
			// Ca peut poser des problemes lors d'enchainement de filtres !
			
			if (tbody && tbody.parentNode) {		
				this.f_releaseRows();
				this.f_releaseCells();
				
				f_core.Assert(tbody.parentNode==this._table, "StartNewPage: Not same parent ? ("+tbody.parentNode+"/"+this._table+")");
				this._table.removeChild(tbody);
				this._tbody=undefined;	
	
				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}	
			}
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
			if (this._checkable) {
				var oldCurrentChecks=(this._currentChecks.length);
				this._currentChecks=new Array;
				
				// Reset des lignes selectionnées ...
				if (oldCurrentChecks) {
					// On avait des selections !
					
					if (!this._checkFullState) {
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
	f_updateNewPage: function() {
		// Appeler par la génération du serveur !

		f_core.Debug(f_dataGrid, "f_updateNewPage: Update new page _rowCount='"+this._rowCount+"' _maxRows="+this._maxRows+"' _rows='"+this._rows+"'.");

		if (this._rowCount<0) {
			var poolSize=this._rowsPool.length+this._first;
			if (this._maxRows<poolSize) {
				this._maxRows=poolSize;
			}
		}

		var cursorRow=undefined;
		var tbody=this._tbody;
		if (tbody && !this._partialWaiting) {
			f_core.Assert(tbody.parentNode!=this._table, "f_dataGrid.f_updateNewPage: Tbody has not been detached !");
			
			this._table.appendChild(tbody);
			
			var rows=f_grid.ListRows(this._table);
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

		f_grid.UpdateTitle(this);

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
	/**
	 * Specify the image of a cell.
	 * 
	 * @method public
	 * @param number row
	 * @param number columnIndex
	 * @param String imageURL 
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
		
		if (!col._cellImage && !col._defaultCellImageURL) {
			return;
		}
		
		var images=row._cellImages;
		if (!images || images.length<=cindex) {
			return;
		}

		var imageTag=images[cindex];
		
		imageTag._imageURL=imageURL;
		
		if (f_grid.USE_BACKGROUND_IMAGE) {
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
	 * Returns the imageURL of the cell.
	 *
	 * @method public 
	 * @return String
	 */
	f_getCellImageURL: function(row, columnIndex) {
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
			return null;
		}
		
		var images=row._cellImages;
		if (!images || images.length<=cindex) {
			return null;
		}

		var imageTag=images[cindex];
		if (!imageTag) {
			return null;
		}
		
		return imageTag._imageURL;
	},
	/**
	 * @method hidden
	 * @param HTMLRowElement row
	 * @param Object... Properties of each row
	 * @return void
	 */
	f_setCells2: function(row) {
		var tds=row.getElementsByTagName("td");
		var cols=this._columns;

		var images=row._cellImages;

		var argIdx=0;
		for(var i=0;i<cols.length;i++) {
			var col=cols[i];
			if (!col._visibility) {
				continue;
			}
			
			var td=tds[argIdx];
			var properties=arguments[argIdx+1];
			if (!properties) {
				argIdx++;
				continue;
			}
			
			var cls=properties._styleClass;
			if (cls) {
				td._cellStyleClass=cls;
				td.className="f_grid_cell "+cls;

				row._cellsStyleClass=true;
			}
			
			var toolTipText=properties._toolTipText;
			if (toolTipText) {
				td.title=toolTipText;				
			}
			
			var imageURL=properties._imageURL;
			if (imageURL!==undefined && images) {
				if (imageURL) {
					f_imageRepository.PrepareImage(imageURL);
				}				
				
				var imageTag=images[argIdx];
				
				if (f_grid.USE_BACKGROUND_IMAGE) {
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
			}
				
			argIdx++;
		}
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
			
		if (this.fa_isElementChecked(row)) {
			return false;
		}
		
		return this.fa_performElementCheck(row, show, jsEvent, true);
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
		
		if (!this.fa_isElementChecked(row)) {
			return false;
		}
		
		return this.fa_performElementCheck(row, false, jsEvent, false);
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
	
		return this.fa_isElementValueChecked(row);
	},	
	fa_isElementChecked: function(row) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_dataGrid.fa_isElementChecked: Invalid element parameter ! ("+row+")");

		return (row._checked)?true:false;
	},
	fa_setElementChecked: function(row, checked) {
		f_core.Assert(row && row.tagName.toLowerCase()=="tr", "f_dataGrid.fa_setElementChecked: Invalid element parameter ! ("+row+")");

		row._checked=checked;
	},
	fa_updateReadOnly: function() {
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_sortClientSide: function(methods, ascendings,tdIndexes) {
			
		function internalSort(obj1, obj2) {	
			for(var i=0;i<methods.length;i++) {
				var tdIndex=tdIndexes[i];
				
				var tc1 = obj1.childNodes[tdIndex];
				var tc2 = obj2.childNodes[tdIndex];

				 var ret=methods[i].call(this, tc1._text, tc2._text, tc1, tc2, tc1._index, tc2._index);
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

		var rowClasses= this._rowStyleClasses;

		for(var i=0;i<trs.length;i++) {
			var row=trs[i];
			
			row._className=rowClasses[i % rowClasses.length];
			
			this.fa_updateElementStyle(row);
		}
	
		this._table.appendChild(body);	
	}	
}
 
new f_class("f_dataGrid", null, __static, __prototype, f_grid, fa_readOnly, fa_checkManager);
