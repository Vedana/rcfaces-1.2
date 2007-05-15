/*
 * $Id$
 */

/**
 * 
 * @aspect public fa_dataGridPopup extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	/** 
	 * @field private static final String 
	 */
	_DATAGRID_POPUP_KEY_SCOPE_ID: "#dataGridPopup",
	
	/** 
	 * @field private static final boolean 
	 */
	_CACHE_IE_POPUP: false,
	
	/**
	 * @method private static
	 */
	_OpenPopup: function(dataGridPopup, position, offsetX, offsetY, offsetWidth) {
		f_core.Debug(fa_dataGridPopup, "_OpenPopup: Open popup for dataGridPopup '"+dataGridPopup.id+"'. (popupOpened='"+dataGridPopup._popupOpened+"')");
		
		
		var dataGridFilter=	dataGridPopup.fa_getDataGridFilter();
		
		if (dataGridPopup._popupOpened) {
			dataGridPopup._dataGrid.f_setFilterProperty("text", dataGridFilter);
			return;
		}
		
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
				exit: dataGridPopup._clickOutside,
				/**
				 * @method public
				 */
				keyDown: function(evt) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.keyDown: popup keyDown: "+evt.keyCode);
					/*if (menu._filterKey("down", evt)===true) {
						return true;
					}
				
					switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return true;
					}
					
					return fa_menuCore.OnKeyDown(menu, evt);
					*/
					return true;
				},
				/**
				 * @method public
				 */
				keyUp: function(evt) {
					f_core.Debug(fa_dataGridPopup, "_OpenPopup.keyUp: popup keyUp: "+evt.keyCode);
					/*return menu._filterKey("up", evt);*/
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

			if (dataGridPopup._iePopup) {
				f_popup.Ie_openPopup(popup, {
					component: dataGridPopup, 
					position: f_popup.BOTTOM_LEFT_COMPONENT });
			
			} else {
				var p1=f_core.GetAbsolutePosition(position);
				var parentPos=f_core.GetAbsolutePosition(popup.offsetParent);
			
				f_core.Debug(fa_dataGridPopup, "_OpenPopup: Popup absolute pos x="+p1.x+" y="+p1.y+" offsetX="+offsetX+" offsetY="+offsetY+" parentX="+parentPos.x+" parentY="+parentPos.y);
			
				var x=p1.x+offsetX-parentPos.x;
				var y=p1.y+offsetY-parentPos.y;

				x+=0; // Les bordures ....
				//y+=3;
			
				var pos={ x: x, y: y };
				
				f_core.ComputePopupPosition(popup, pos);

				f_core.Debug(fa_dataGridPopup, "_OpenPopup: Computed pos x="+p1.x+" y="+p1.y+" offsetX="+offsetX+" offsetY="+offsetY);
					
				popup.style.left=pos.x+"px";
				popup.style.top=pos.y+"px";
			
				if (offsetWidth) {
					popup.style.width=offsetWidth+"px";
					
				} else if (offsetWidth!==false) {
					popup.style.width="auto";
				}
			
				popup.style.visibility="inherit";
			}			
		}
	
		dataGridPopup._popupOpened=true;
		dataGridPopup._dataGrid.f_setFilterProperty("text", dataGridFilter);
	},

	/**
	 * @method private static
	 */
	_ClosePopup: function(dataGridPopup, jsEvt) {	
		f_core.Debug(fa_dataGridPopup, "_ClosePopup: Close the popup of dataGridPopup='"+dataGridPopup.id+"'.");

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
		
		if (!dataGridPopup._iePopup) {
			popup.style.visibility="hidden";
			return;
		}	
		
		f_popup.Ie_closePopup(popup);
		dataGridPopup._popup=undefined;
	}
	
}

var __prototype = {
		
	/**
	 * @field private String
	 */
	_dataGridInnerHtml: undefined,
	
	/**
	 * @field private Object[]
	 */
	_columns: undefined,
	
	fa_dataGridPopup: function() {
	},
	f_finalize: function() {
		
		// this._dataGridHtml=undefined; // String
		this._columns=undefined;  // Object[]
		
		this.f_destroyDataGrid();
		this.f_destroyPager();
	},
	f_constructDataGrid: function(parent) {
		var dataGrid=this._dataGrid;
		if (dataGrid && dataGrid.parentNode && dataGrid.ownerDocument==parent.ownerDocument) {
			return dataGrid;
		}
		
		if (dataGrid) {
			this.f_destroyDataGrid(dataGrid);
		}
		var pager=this._pager;
		if (pager) {
			this.f_destroyPager(dataGrid);	
		}
		
	//	dataGrid=f_dataGrid.Create(parent, this._columns, this._rows, );

		
		var dataGridContainer=f_core.CreateElement(parent, "table", {cellSpacing: 0, cellPadding: 0 });
		
		var tBodyContainer=f_core.CreateElement(dataGridContainer, "tbody");
		
		var width=f_core.GetNumberAttribute(this, "v:popupWidth", 320);
		var height=f_core.GetNumberAttribute(this, "v:popupHeight", 200);

		var rows=f_core.GetNumberAttribute(this, "v:rows");

		var td=f_core.CreateElement(tBodyContainer, "tr", null, "td");									
		dataGrid=f_dataGridPopup.Create(td, this, width, (rows)?(height-26):height);
		this._dataGrid=dataGrid;
		
		if (rows) {
			td=f_core.CreateElement(tBodyContainer, "tr", null, "td");
			pager=f_pager.Create(td, this, ":"+dataGrid.id);
			this._pager=pager;
		}
				
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
	
	f_openDataGridPopup: function(jsEvent) {
		f_core.Debug(fa_dataGridPopup, "f_openDataGridPopup: jsEvent="+jsEvent);
				
		if (this.f_fireEvent(f_event.MENU, jsEvent)===false) {
			return false;
		}
		
		var offsetX=0;
		var offsetY=this.offsetHeight;
		
		fa_dataGridPopup._OpenPopup(this, this, 0, this.offsetHeight, false, false);
		
		return false;
	},
	_clickOutside: function(jsEvent) {
		f_core.Debug(fa_dataGridPopup, "_clickOutside: popup click outside");
		
		fa_dataGridPopup._ClosePopup(this, jsEvent);
		return false;
	
	},
	/**
	 * @method hidden
	 * @return f_dataGrid
	 */
	f_getDataGrid: function() {
		return this._dataGrid;
	},
	
	fa_getDataGridFilter: f_class.ABSTRACT
}

new f_aspect("fa_dataGridPopup", __static, __prototype);
