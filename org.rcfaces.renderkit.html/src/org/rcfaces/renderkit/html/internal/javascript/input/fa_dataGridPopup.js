/*
 * $Id$
 */

/**
 * 
 * @aspect public fa_dataGridPopup extends f_object, fa_filterProperties
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/** 
	 * @field private static final String 
	 */
	_DATAGRID_POPUP_KEY_SCOPE_ID: "#dataGridPopup",
	
	/** 
	 * @field private static final boolean 
	 */
	_CACHE_IE_POPUP: false,
	
	/** 
	 * @field private static final String 
	 */
	_DEFAULT_VALUE_FORMAT: "{0}",
	
	/**
	 * @method private static
	 */
	_OpenPopup: function(dataGridPopup, position, offsetX, offsetY) {
		f_core.Debug(fa_dataGridPopup, "_OpenPopup: Open popup for dataGridPopup '"+dataGridPopup.id+"'. (popupOpened='"+dataGridPopup._popupOpened+"' popup='"+dataGridPopup._popup+"')");
				
		var popup=dataGridPopup._popup;
		if (!popup) {
			var body=null;
			
			if (dataGridPopup._iePopup) {
				var doc=dataGridPopup.ownerDocument;
				
				if (fa_dataGridPopup._CACHE_IE_POPUP) {
					popup=f_popup.Ie_GetCurrentPopupByKey(doc, dataGridPopup.id);
				}
				
				if (!popup) {
					if (fa_dataGridPopup._CACHE_IE_POPUP) {				
						popup=f_popup.Ie_GetPopup(doc, dataGridPopup.id, function() {
							dataGridPopup.f_destroyComponent();		
						});
					} else {
						popup=f_popup.Ie_GetPopup(doc);
					}
				
					var pdoc=popup.document;
					
					pdoc.body.innerHTML="";
					
					body=pdoc.createElement("div");
					body.className="f_dataGridPopup_popup";
					body.style.visibility="inherit";
					body.style.position="relative";
					body.style.width=dataGridPopup._popupWidth+"px";
					body.style.height=dataGridPopup._popupHeight+"px";
	
					pdoc.body.appendChild(body);
				}
								
			} else {
				popup=dataGridPopup.ownerDocument.createElement("div");
				popup.className="f_dataGridPopup_popup";
	
				popup.onclick=f_core.CancelJsEventHandlerTrue;
				popup.onmousedown=f_core.CancelJsEventHandlerTrue;
	
				body=popup;
				
				var parent=dataGridPopup;
				for(;;parent=parent.parentNode) {
					var tagName=parent.tagName.toLowerCase();
					
					if (tagName=="input" || tagName=="span" || tagName=="table") {
						continue;
					}
					break;
				}
				
				parent.appendChild(popup);
			}
			dataGridPopup._popup=popup;
		
			if (body) {
				body._popupObject=true;
				dataGridPopup.f_constructDataGrid(body);
				
			} else {
				//calendar.f_refreshComponent();
			}
		}

		if (f_popup.RegisterWindowClick({
				/**
				 * @method public
				 */
				exit: function(jsEvent) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.exit: Click outside "+jsEvent);

					return dataGridPopup._clickOutside(jsEvent);
				},
				/**
				 * @method public
				 */
				keyDown: function(jsEvent) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.keyDown: popup keyDown: "+jsEvent.keyCode);

					switch(jsEvent.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 			
						dataGridPopup._rowSelection(dataGridPopup._dataGrid, jsEvent);
				 		return false;

				 	case f_key.VK_TAB:
						dataGridPopup._rowSelection(dataGridPopup._dataGrid, jsEvent);
				 		return true; // On accepte le TAB

					case f_key.VK_ESCAPE:
							
						dataGridPopup.f_closeDataGridPopup(jsEvent);
				 		return false;
				 		
					case f_key.VK_DOWN:
					case f_key.VK_UP:
					case f_key.VK_PAGE_DOWN:
					case f_key.VK_PAGE_UP:
//					case f_key.VK_END:
//					case f_key.VK_HOME:
						return dataGridPopup._dataGrid.f_performKeyDown(jsEvent);
					}

					return true;
				},
				/**
				 * @method public
				 */
				keyUp: function(jsEvent) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.keyUp: popup keyUp: "+jsEvent.keyCode);
					/*return menu._filterKey("up", evt);*/
					
					switch(jsEvent) {
					case f_key.VK_DOWN:
					case f_key.VK_UP:
					case f_key.VK_PAGE_DOWN:
					case f_key.VK_PAGE_UP:
					case f_key.VK_END:
					case f_key.VK_HOME:
						//return dataGridPopup._dataGrid.f_onKeyUp(jsEvent);
					}
					
					return true;
				},
				/**
				 * @method public
				 */
				keyPress: function(evt) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.keyPress: popup keyPress: "+evt.keyCode);
					/*switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return fa_menuCore.OnKeyDown(menu, evt);
					}
					*/
					return true;
				}
			}, dataGridPopup, popup)==false) {
			
			f_core.Debug(fa_dataGridPopup, "_OpenPopup: Register refused to open the popup of dataGridPopup='"+dataGridPopup.id+"'.");
			return;
		}
		
		f_core.Debug(fa_dataGridPopup, "_OpenPopup: Open popup "+popup+" of dataGridPopup='"+dataGridPopup.id+"'.");
		if (popup) {
			f_key.EnterScope(fa_dataGridPopup._DATAGRID_POPUP_KEY_SCOPE_ID);

			var positionParameters = {
				component: position, 
				position: f_popup.BOTTOM_LEFT_COMPONENT 
			};

			if (dataGridPopup._iePopup) {
				f_popup.Ie_openPopup(popup, positionParameters);
			
			} else {
				f_popup.Gecko_openPopup(popup, positionParameters);
			}			
		}
	
		dataGridPopup._popupOpened=true;
	},

	/**
	 * @method private static
	 */
	_ClosePopup: function(dataGridPopup, jsEvt) {	
		f_core.Debug(fa_dataGridPopup, "_ClosePopup: Close the popup of dataGridPopup='"+dataGridPopup.id+"' opened="+dataGridPopup._popupOpened+" popup="+dataGridPopup._popup);

		if (!dataGridPopup._popupOpened) {
			return;
		}
		dataGridPopup._popupOpened=undefined;

		f_popup.UnregisterWindowClick(dataGridPopup);		
					
		f_key.ExitScope(fa_dataGridPopup._DATAGRID_POPUP_KEY_SCOPE_ID);
		
		var popup=dataGridPopup._popup;		
		if (!popup) {
			return; 
		}
	
		dataGridPopup._popup=undefined;
		
		if (!dataGridPopup._iePopup) {
			f_popup.Gecko_closePopup(popup);

//			f_popup.Gecko_releasePopup(popup);
			return;
		}	
		
		f_popup.Ie_closePopup(popup);

		f_popup.Ie_releasePopup(popup);
	}
	
}

var __members = {
		
	/**
	 * @field private String
	 */
	_dataGridInnerHtml: undefined,
	
	/**
	 * @field private String
	 */
	_valueColumnId: undefined,
	
	/**
	 * @field private String
	 */
	_labelColumnId: undefined,
	
	/**
	 * @field private String
	 */
	_valueFormat: undefined,
	
	/**
	 * @field private Object[]
	 */
	_columns: undefined,
	
	fa_dataGridPopup: function() {		
		this._valueColumnId=f_core.GetAttribute(this, "v:valueColumnId");
		
		var labelColumnId=f_core.GetAttribute(this, "v:labelColumnId");
		if (labelColumnId) {
			this._labelColumnId=labelColumnId;
		}
		
		var valueFormat=f_core.GetAttribute(this, "v:valueFormat");
		if (!valueFormat) {
			if (labelColumnId) {
				valueFormat="{"+labelColumnId+"}";

			} else {			
				valueFormat=fa_dataGridPopup._DEFAULT_VALUE_FORMAT;
			}
		}
		
		
		this._popupWidth=f_core.GetNumberAttribute(this, "v:popupWidth", 320);
		this._popupHeight=f_core.GetNumberAttribute(this, "v:popupHeight", 200);
		
		this._valueFormat=valueFormat;
		
		this._iePopup=f_popup.Ie_enablePopup();
	},
	f_finalize: function() {
		
		// this._dataGridHtml=undefined; // String
		// this._autoSelect=undefined; // number
		// this._valueColumnId=undefined; // String
		// this._labelColumnId=undefined; // String
		// this._valueFormat=undefined; // String
		// this._iePopup=undefined; // boolean
		// this._popupWidth=undefined; // number
		// this._popupHeight=undefined; // number
				
		this._popup=undefined; // ? HtmlDivElement
		this._columns=undefined;  // Object[]
		
		this.f_destroyDataGrid();
		this.f_destroyPager();
	},
	/**
	 * @method protected
	 * @param HTMLElement parent
	 * @return f_dataGrid
	 */
	f_constructDataGrid: function(parent) {
		f_core.Debug(fa_dataGridPopup, "f_constructDataGrid: construct components parent="+parent);
		
		var dataGrid=this._dataGrid;
		if (dataGrid && dataGrid.parentNode && dataGrid.ownerDocument==parent.ownerDocument) {
		//	return dataGrid;
		// Pas de cache pour l'instant !
		}
		
		if (dataGrid) {
			this.f_destroyDataGrid(dataGrid);
		}
		var pager=this._pager;
		if (pager) {
			this.f_destroyPager(dataGrid);	
		}

		var rows=f_core.GetNumberAttribute(this, "v:rows");
		var paged=f_core.GetBooleanAttribute(this, "v:paged", true);
		
		var hasPager=(rows>0 && paged);
		
		var width=this._popupWidth;
		var height=this._popupHeight;
		
		var dataGridContainer=f_core.CreateElement(parent, "table", {cellSpacing: 0, cellPadding: 0, "style": "width:"+width+"px;height:"+height+"px" });
		
		var tBodyContainer=f_core.CreateElement(dataGridContainer, "tbody");		
		
		var td=f_core.CreateElement(tBodyContainer, "tr", null, "td", {align: "left", valign: "middle" });									
		
		var pagerHeight=30;
		
		dataGrid=f_dataGridPopup.Create(td, 
			this, 
			width, 
			(hasPager)?(height-pagerHeight):height, 
			f_core.GetAttribute(this, "v:gridStyleClass"));
		
		this._dataGrid=dataGrid;
		
		if (hasPager) {
			if (!f_core.GetAttribute(this, "v:message")) {
				var resourceBundle=f_resourceBundle.Get(fa_dataGridPopup);
				
				this.setAttribute("v:message", resourceBundle.f_get("MESSAGE"));
				this.setAttribute("v:zeroResultMessage", resourceBundle.f_get("ZERO_RESULT_MESSAGE"));			
				this.setAttribute("v:oneResultMessage", resourceBundle.f_get("ONE_RESULT_MESSAGE"));			
				this.setAttribute("v:manyResultMessage", resourceBundle.f_get("MANY_RESULTS_MESSAGE"));			
				this.setAttribute("v:manyResultMessage2", resourceBundle.f_get("MANY_RESULTS_MESSAGE2"));			
			}
			
			
			td=f_core.CreateElement(tBodyContainer, "tr", null, "td", {align: "center", valign: "middle" });
			pager=f_pager.Create(td, 
				this, 
				":"+dataGrid.id,
				f_core.GetAttribute(this, "v:pagerStyleClass"));
			this._pager=pager;
			
			pager.style.height=pagerHeight+"px";
		}
				
		var self=this;
		dataGrid.f_addEventListener(f_event.SELECTION, function(event) {
			f_core.Debug(fa_dataGridPopup, "_rowSelectionEvent: selection detail="+event.f_getDetail());

			if (!(event.f_getDetail() & f_event.ACTIVATE_DETAIL)) {
				return;
			}
			
			return self._rowSelection(event.f_getSelectionProvider(), event.f_getJsEvent());			
		});
		
		return dataGrid;
	},
	f_destroyDataGrid: function() {
		var dataGrid=this._dataGrid;
		if (!dataGrid) {
			return;
		}
		this._dataGrid=undefined;
		
		var parent=dataGrid.parentNode;
		if (parent) {
			parent.removeChild(dataGrid);
		}
		
		f_classLoader.Destroy(dataGrid);
	},
	f_destroyPager: function() {
		var pager=this._pager;
		if (!pager) {
			return;
		}
		this._pager=undefined;
		
		var parent=pager.parentNode;
		if (parent) {
			parent.removeChild(pager);
		}
		
		f_classLoader.Destroy(pager);
	},
	/**
	 * @method hidden
	 * @param String html
	 * @return void
	 */
	f_setGridInnerHTML: function(html) {
		this._dataGridInnerHtml=html;
	},
	/**
	 * @method hidden
	 * @param Object... columns
	 * @return void
	 */
	f_setColumns2: function(columns) {
		this._columns=f_core.PushArguments(null, arguments);
	},
	/**
	 * @method hidden
	 * @param optional Event jsEvent
	 * @return boolean
	 */
	f_closeDataGridPopup: function(jsEvent) {
		fa_dataGridPopup._ClosePopup(this, jsEvent);
	},
	/**
	 * @method hidden
	 * @param optional Event jsEvent
	 * @param optional String text
	 * @param optional number autoSelect
	 * @return boolean
	 */
	f_openDataGridPopup: function(jsEvent, text, autoSelect) {
		f_core.Debug(fa_dataGridPopup, "f_openDataGridPopup: jsEvent="+jsEvent+" text='"+text+"' autoSelect="+autoSelect);

		var popupOpened=this._popupOpened
		if (!popupOpened) {
			if (this.f_fireEvent(f_event.MENU, jsEvent)===false) {
				return false;
			}
			
			var offsetX=0;
			var offsetY=this.offsetHeight;
			
			fa_dataGridPopup._OpenPopup(this, this, 0, this.offsetHeight, false); 
		}

		var dataGrid=this._dataGrid;
		if (!dataGrid) {
			return false;
		}
		
		var selection=dataGrid.f_getSelection();
		if (autoSelect && !selection.length) {
			dataGrid.f_setAutoSelection(autoSelect);
		}
		
		var filterProperties=this.f_getFilterProperties();
		filterProperties.text=text;
		
		dataGrid.f_setFilterProperties(filterProperties);
		
		return true;
	},
	/**
	 * @method private
	 * @return boolean
	 */
	_clickOutside: function(jsEvent) {
		f_core.Debug(fa_dataGridPopup, "_clickOutside: popup click outside");
		
		this.f_closeDataGridPopup(jsEvent);
		return false;	
	},
	/**
	 * @method protected
	 * @return boolean
	 */
	f_isDataGridPopupOpened: function() {
		return this._popupOpened;
	},
	/**
	 * @method hidden
	 * @return f_dataGrid
	 */
	f_getDataGrid: function() {
		return this._dataGrid;
	},
	
	/**
	 * @method hidden
	 * @param number autoSelection
	 * @return void
	 */
	f_changeSelection: function(autoSelection) {
		f_core.Assert(typeof(autoSelection)=="number", "fa_dataGridPopup.f_changeSelection: Invalid autoSelection parameter ("+autoSelection+")");		

		var dataGrid=this.f_getDataGrid();
		if (!dataGrid) {
			return;
		}
		dataGrid.f_performAutoSelection(autoSelection);
	},
	f_clearAllGridRows: function() {

		var dataGrid=this.f_getDataGrid();
		if (!dataGrid) {
			return;
		}
		dataGrid.f_clearAll();
	},
	/**
	 * @method private
	 * @param f_dataGrid dataGrid
	 * @param Event jsEvent
	 * @return void
	 */
	_rowSelection: function(dataGrid, jsEvent) {
		var selection=dataGrid.f_getSelection();
		
		if (!selection.length) {
			return;
		}
		
		this.f_closeDataGridPopup(jsEvent);

		var first=selection[0];
		
		var array=dataGrid.f_getRowValues(first);
		var values=dataGrid.f_getRowValuesSet(first);
		
		if (values) {
			for(var name in values) {
				array[name]=values[name];
			}
		}
		
		var message=f_core.FormatMessage(this._valueFormat, array);
		
		var valueColumnId=this._valueColumnId;
		if (!valueColumnId) {
			valueColumnId=0;
		}
		
		var value=array[valueColumnId];
		
		// A cause de IE !!!! ARG !
		var self=this;
		window.setTimeout(function() {
			self.fa_valueSelected(value, message);
		}, 0);
	},

	fa_cancelFilterRequest: function() {
	},
	fa_updateFilterProperties: function() {		
	},

	/**
	 * @method protected abstract
	 * @param String value
	 * @param String label
	 * @param optional Event jsEvent
	 * @return void
	 */	
	fa_valueSelected: f_class.ABSTRACT
}

new f_aspect("fa_dataGridPopup", __statics, __members, fa_filterProperties);
