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
var __prototype = {
	f_menu: function(parentComponent, selectionProvider, redirectEvents, id, menuId, itemImageWidth, itemImageHeight, removeAllWhenShown) {
		f_core.Assert(!this.ownerDocument, "f_menu object can not be a tag !");
		
		this.f_super(arguments);

		this._parentComponent=parentComponent;
		this._selectionProvider=selectionProvider;
		this._redirectEvents=redirectEvents;

		if (!id) {
			id="";
		}
		this.id=id;
		this._menuId=menuId;
		this._component=parentComponent;
		this._menu=this;
		this.ownerDocument=parentComponent.ownerDocument;
		this._removeAllWhenShown=removeAllWhenShown;
		
		if (itemImageWidth && itemImageHeight) {
			this.f_setItemImageSize(itemImageWidth, itemImageHeight);
		}
	},
	f_finalize: function() {
		this._redirectEvents=undefined; // fa_targetEvent
		// this.id=undefined; // string
 
		this._selectionProvider=undefined; // f_selectionProvider
		this._component=undefined; // f_component
		this._parentComponent=undefined; // f_component
		this._selectedMenuItem=undefined;
		// this.fa_componentUpdated=undefined; // boolean
		this.ownerDocument=undefined; // Document

		this._attachedTable=undefined;
		this._iePopup=undefined;

		this.f_super(arguments);
	},
	f_update: function(set) {
		if (f_popup.Ie_enablePopup()) {
			// On associe le POPUP 
			
			this._iePopup=fa_menuCore._Ie_getPopup(this);
		}	
		
		this.fa_componentUpdated = (set===undefined)? true:set;		
	},
	fa_focusMenuItem: function(item) {
		// Ca sert au changement de menuBarItem !
	},
	f_fireEvent: function(type, jsEvt, item, value, selectionProvider) {
		var redir=this._redirectEvents;
		if (redir) {
			f_core.Assert(typeof(redir.f_fireEvent)=="function", "f_menu.f_fireEvent: Invalid redir object ("+redir+")");
			
			f_core.Debug(f_menu, "f_fireEvent: redirect event '"+type+"' to '"+redir+"'.");
			
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
	fa_getSelectionProvider: function() {
		return this._selectionProvider;
	},
	fa_keySearchAccessKey: function(item, code, jsEvent) {
			
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
	fa_isSameMenuBase: function(menuBarItem) {
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
	
	/**
	 * @method private
	 * @return boolean
	 */
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

	fa_tabKeySelection: function() {
		return true;
	},
	
	/**
	 * @method public
	 * @param String id Identifier of component.
	 * @return f_component
	 * @see f_component#f_findComponent
	 */
	f_findComponent: function(id) {
		return fa_namingContainer.FindComponents(this._parentComponent, arguments);
	},
	
	fa_getPopupCallbacks: function() {
		var menu=this;
		
		return {
			exit: menu.f_clickOutside,
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
		}
	},
	
	fa_getMenuScopeName: function(menuItem) {
		var cid=this._parentComponent.id+"::"+this.id;
		if (menuItem==this) {
			return cid;
		}
		
		return cid+"::"+menuItem._id;
	}
}


var f_menu=new f_class("f_menu", null, null, __prototype, f_eventTarget, fa_menuCore);
 