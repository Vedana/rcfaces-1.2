/*
 * $Id$
 */

/**
 * Class Menu
 *
 * @class public f_menu extends f_eventTarget, fa_menuCore
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	/**
	 * @field hidden static final number
	 */
	MOUSE_POSITION:0, 

	/**
	 * @field hidden static final number
	 */
	MIDDLE_COMPONENT: 1,

	/**
	 * @field hidden static final number
	 */
	BOTTOM_COMPONENT: 2,

	/**
	 * @field hidden static final number
	 */
	WIDTH_COMPONENT: 4,

	/**
	 * @field hidden static final number
	 */
	HEIGHT_COMPONENT: 8,

	_ClosePopup: function(menu, jsEvt) {	
		if (!menu._popupOpened || !menu._menuPopup) {
			return;
		}
		menu._popupOpened=undefined;

		f_popup.UnregisterWindowClick(menu);	
		
		var old=menu._selectedMenuItem;
		if (old) {
			menu._selectedMenuItem=undefined;

			old._closePopup(old);
			old._over=undefined;
			
			menu.fa_updateItemStyle(old);
		}
				
		f_key.ExitScope(menu.id);
		
		var popup=menu._menuPopup;
		
		if (popup) {
			if (menu._iePopup) {
				fa_menuCore._Ie_closePopup(menu);
				
			} else {
				popup.style.visibility="hidden";
			}
					
			var old=popup._menuItemSelected;
			if (old) {
				old._closePopup(old);
				
				popup._menuItemSelected=undefined;
			}			
		}
	},
	_OpenPopup: function(menu, position, offsetX, offsetY, offsetWidth, autoSelect) {
		if (menu._popupOpened) {
			return;
		}

		var popup=menu._menuPopup;

		if (!popup) {
			return;
		}
										
		if (f_popup.RegisterWindowClick({
				exit: menu._a_clickOutside,
				keyDown: function(evt) {
					if (menu._filterKey("down", evt)===true) {
						return true;
					}
				
					switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return true;
					}
					
					return fa_menuCore.OnKeyDown(menu, menu, evt);
				},
				keyUp: function(evt) {
					return menu._filterKey("up", evt);
				},
				keyPress: function(evt) {
					switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return fa_menuCore.OnKeyDown(menu, menu, evt);
					}
					
					return true;
				}
			}, menu, popup)==false) {
			return;
		}

		if (popup) {
			f_key.EnterScope(menu.id);

			if (menu._iePopup) {
				fa_menuCore._Ie_openPopup(menu, position, offsetX, offsetY, offsetWidth);
			
			} else {
				var p1=f_core.GetAbsolutePosition(position);
			
				var x=p1.x+offsetX;
				var y=p1.y+offsetY;

				x+=1; // Les bordures ....
				y+=1;
			
				var pos={ x: x, y: y };
				
				f_core.ComputePopupPosition(popup, pos);
					
				popup.style.left=pos.x+"px";
				popup.style.top=pos.y+"px";
			
				if (offsetWidth) {
					popup.style.width=offsetWidth+"px";
				} else {
					popup.style.width="auto";
				}
			
				popup.style.visibility="inherit";
			}			
			popup._menuItemSelected=null;
		}
	
		menu._popupOpened=true;

		if (autoSelect) {
			if (menu._items && menu._items.length>0) {
				menu._menuItem_over(menu._items[0], false);
			}
		}
	}
}

var __prototype = {
	f_menu: function(parentComponent, redirectEvents, id, menuId, itemImageWidth, itemImageHeight) {
		this.f_super(arguments);

		this._parentComponent=parentComponent;
		this._redirectEvents=redirectEvents;

		this.id=id;
		this._menuId=menuId;
		this._component=parentComponent;
		this._openPopup=f_menu._OpenPopup;
		this._closePopup=f_menu._ClosePopup;
		this._menuBar=this;
		this.ownerDocument=parentComponent.ownerDocument;
		
		if (itemImageWidth && itemImageHeight) {
			this.f_setItemImageSize(itemImageWidth, itemImageHeight);
		}
	},
	f_finalize: function() {
		this._redirectEvents=undefined; // fa_targetEvent
		// this.id=undefined; // string
 
		this._component=undefined; // f_component
		this._openPopup=undefined; // function 
		this._closePopup=undefined; // function
		this._menuBar=undefined; // f_menu
		this._parentComponent=undefined; // f_component
		this._selectedMenuItem=undefined;
		// this.fa_componentUpdated=undefined; // boolean

		this._selectionProvider=undefined;
		this._attachedTable=undefined;
		this._iePopup=undefined;

		this.ownerDocument=undefined; // Document
		this.f_super(arguments);
	},
	f_update: function(set) {
		if (f_popup.Ie_enablePopup()) {
			// On associe le POPUP 
			
			this._iePopup=fa_menuCore._Ie_getPopup(this);
		}	
		
		this._menuItemsChanged=true;
		
		this.fa_componentUpdated = (set===undefined)? true:set;		
	},
	/**
	 * @method hidden
	 *
	 * @return boolean Open has been performed !
	 */
	f_close: function(jsEvt) {
		this._closePopup(this, jsEvt);
	},
	/**
	 * @method hidden
	 *
	 * @return boolean Open has been performed !
	 */
	f_open: function(component, componentPosition, selectionProvider, jsEvt, autoSelect) {
		this._selectionProvider=selectionProvider;
		
		if (this.f_fireEvent(f_event.MENU, jsEvt, null, null, selectionProvider)===false) {
			return false;
		}
		
		if (this._menuItemsChanged) {
			this._menuItemsChanged=undefined;
	
			var items=this._items;
			if (items) {
				for(var i=0;i<items.length;i++) {
					this._updateItem(items[i]);
				}
			}
			
			var table=this._menuPopup;
			if (table && !this._attachedTable) {
				this._attachedTable=true;
			
				var parent=this._parentComponent;
				if (this._iePopup) {
					if (parent.tagName=="INPUT") {
						parent=parent.parentNode;
					}
					
					parent.appendChild(table);
					
				} else {
					parent.ownerDocument.body.appendChild(table);
				}
			}
		}

		if (!this._menuPopup) {
			f_core.Info(f_menu, "No popup associated to the menu !");
			return false;
		}
		
		
		fa_menuCore.HideSeparators(this);
		
		var offsetX=0;
		var offsetY=0;
		var offsetWidth;
		
		var position=(componentPosition)?componentPosition.position:0;
		switch(position) {
		case f_menu.BOTTOM_COMPONENT:
			offsetY=component.offsetHeight;
			break;

		case f_menu.MIDDLE_COMPONENT:
			offsetY=component.offsetHeight/2;
			offsetX=offsetY;
			break;
			
		default:
			// Calcule la position de la souris 
			var eventPos=f_core.GetEventPosition(jsEvt, component.ownerDocument);
			var cursorPos=f_core.GetAbsolutePosition(component);
			
			offsetX=eventPos.x-cursorPos.x;
			offsetY=eventPos.y-cursorPos.y;
			break;
		}
	
		var size=(componentPosition)?componentPosition.size:0;
		switch(size) {
		case f_menu.WIDTH_COMPONENT:
			offsetWidth=component.offsetWidth;
		}
		
		if (componentPosition.deltaX) {
			offsetX+=componentPosition.deltaX;
		}
		
		if (componentPosition.deltaY) {
			offsetY+=componentPosition.deltaY;
		}
		
		if (componentPosition.deltaWidth) {
			offsetWidth+=componentPosition.deltaWidth;
		}
		
		this._openPopup(this, component, offsetX, offsetY, offsetWidth, autoSelect);
		
		return true;
	},
	_a_clickOutside: function(jsEvt) {
		this._openMode=undefined;
	
		this._closePopup(this, jsEvt);
	},
	_a_focusMenuItem: function(item) {
		// Ca sert au changement de menuBarItem !
	},
	_a_closeMenu: function(item, jsEvt) {
		f_menu._ClosePopup(this, jsEvt);
	},
	f_fireEvent: function(type, jsEvt, item, value, selectionProvider) {
		var redir=this._redirectEvents;
		if (redir) {
			return redir.f_fireEvent.apply(redir, arguments);
		}
		
		return this.f_super(arguments, type, jsEvt, item, value, selectionProvider);
	},
	/** 
	 * @method public
	 * @return boolean
	 */
	f_isReadOnly: function() {
		var component=this._parentComponent;
		
		if (component.f_isReadOnly && component.f_isReadOnly()) {
			return true;
		}
		
		return false;
	},
	/** 
	 * @method public
	 * @return f_component
	 */
	f_getOwnerComponent: function() {
		return this._parentComponent;
	},
	/** 
	 * @method public
	 * @return boolean
	 */
	f_isOpened: function() {
		return this._popupOpened;
	},
	_a_getSelectionProvider: function() {
		return this._selectionProvider;
	},
	_a_keySearchAccessKey: function(item, code, jsEvent) {
			
		if (this.f_isReadOnly()) {
			return false;
		}

		// Par defaut le parent est le menuBarItem
		// On recherche le popup le plus "ouvert"
		var parent=item;
		for(;;) {
			var pi=parent._selectedMenuItem;
			if (!pi || !pi._popupOpened) {
				break;
			}
			parent=pi;
		}
		
		var key=String.fromCharCode(code).toUpperCase();

		var items=parent._items;
		for(var i=0;i<items.length;i++) {
			var it=items[i];

			if (!it._accessKey) {
				continue;
			}

			if (it._accessKey!=key) {
				continue;
			}

 			if (this.f_isItemDisabled(it)) {
				item._closePopup(item);
				return true;
 			}
 
			this._menuItem_over(it, true, true);			
			if (it._menuPopup) {
				return true;
			}

			this._menuItem_select(it, jsEvent);
			return true;
		}
		
		return false;
	},
	_a_isSameMenuBase: function(menuBarItem) {
		return true;
	},	
	f_setDomEvent: function(type, target) {
		// On positionne pas de Handler !
		return;
	},
	f_clearDomEvent: function(type, target) {
		// On positionne pas de Handler !
		return;
	},
	/**
	 * @method hidden
	 * @param boolean enable
	 * @return void
	 */
	f_setCatchOnlyPopupKeys: function(enable) {
		this._catchOnlyPopupKeys=true;
	},
	
	_filterKey: function(phase, evt) {
		f_core.Debug(f_menu, "Filter key '"+evt.keyCode+"'.");
		if (!this._catchOnlyPopupKeys) {
			return false;
		}
		
		var code=evt.keyCode;
	
		switch(code) {
		case f_key.VK_DOWN: // FLECHE VERS LE BAS
		case f_key.VK_UP: // FLECHE VERS LE HAUT
	 	case f_key.VK_ESCAPE:
	 	case f_key.VK_ENTER:
		case f_key.VK_RETURN:
			return false;
			
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
		case f_key.VK_HOME: // HOME
		case f_key.VK_END: // END
		case f_key.VK_TAB: 
	 		// En cas de selection !
	 		if (this._selectedMenuItem) {
				return false;
			}
		}
		
		return true;
	},

	_a_tabKeySelection: function() {
		return true;
	},
	
	/**
	 * @method public
	 * @param string id Identifier of component.
	 * @return f_component
	 * @see f_component.f_findComponent
	 */
	f_findComponent: function(id) {
		return fa_namingContainer.FindComponents(this._parentComponent, arguments);
	}
	
}


var f_menu=new f_class("f_menu", null, __static, __prototype, f_eventTarget, fa_menuCore);
 