/*
 * $Id$
 */

/**
 * Class MenuBar
 *
 * @class f_menuBar extends f_menuBase, fa_immediate, fa_readOnly, fa_disabled
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {

	/**
	 * @method private static
	 */
	_MenuBarItem_mouseOver: function(evt) {
		var item=this._item;
		var menuBar=item._menu;
		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetEvent(this);
		}

		menuBar._menuBarItem_over(item, evt);
		
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_mouseOut: function(evt) {
		var item=this._item;
		var menuBar=item._menu;

// Pas bloqué !		if (f_core.GetEventLocked(false)) return false;
		
		if (!evt) {
			evt = f_core.GetEvent(this);
		}

		menuBar._menuBarItem_out(item);

		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItemInput_click: function(evt) {
		var menuItem=this._item;
		var menuBar=menuItem._menu;
	
		f_core.Debug(f_menuBar, "_MenuBarItemInput_click: click on item='"+menuItem+"' menuBar='"+menuBar+"'.");

		if (menuBar.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetEvent(this);
		}

		try {
			menuBar._menuBarItem_select(menuItem, false, evt);
			
			f_menuBar._MenuBarItem_setFocus(menuItem);
		
		} catch (x) {
			f_core.Error(f_menuBar, "Click exception", x);
		}
				
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_setFocus: function(menuBarItem) {
		if (menuBarItem._hasFocus) {
			return;
		}

		var uiItem=menuBarItem._menu.f_getUIItem(menuBarItem);
		if (!uiItem) {
			return;
		}

		uiItem.focus();		
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_keyDown: function(evt) {
		var menuItem=this._item;
		var menuBar=menuItem._menu;
	
		f_core.Debug(f_menuBar, "_MenuBarItem_keyDown: key item='"+menuItem+"' menuBar='"+menuBar+"'.");

		if (menuBar.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetEvent(this);
		}

		return fa_menuCore.OnKeyDown(menuBar, menuItem, evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_focus: function(evt) {
		var menuItem=this._item;
		var menuBar=menuItem._menu;

		f_core.Debug(f_menuBar, "_MenuBarItem_focus: focus item='"+menuItem+"' menuBar='"+menuBar+"'.");

		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}

		menuBar._hasFocus=true;

		if (!evt) {
			evt = f_core.GetEvent(this);
		}
		
		var old=menuBar._selectedMenuItem;
		
//		f_core.Info("f_menuBar", "Focus: old="+old+" param="+menuItem);
		
		if (old==menuItem) {
			return true;
		}
		
		if (old) {
			menuBar._selectedMenuItem=undefined;
			menuBar.f_closeAllPopups();
				
			menuBar.f_updateMenuBarItemStyle(old);
		}
			
		menuBar._selectedMenuItem=menuItem;
		menuBar.f_updateMenuBarItemStyle(menuItem);

		if (menuBar.f_isItemDisabled(menuItem)) {
//			f_core.Info("f_menuBar", "Focus-DISABLED cur="+menuBar._selectedMenuItem);
			return true;
		}
		
		if (menuBar._openMode) {
		//	menuItem._openPopup(menuItem, false);
		}
				
		return true;
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_blur: function(evt) {
		var menuItem=this._item;
		var menuBar=menuItem._menu;
	
		f_core.Debug(f_menuBar, "_MenuBarItem_blur: blur item='"+menuItem+"' menuBar='"+menuBar+"'.");
		
		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetEvent(this);
		}
	
		menuBar._openMode=undefined;
			
		f_core.Info(f_menuBar, "Blur clear openMode");
		
		var old=menuBar._selectedMenuItem;
		if (old!=menuItem) {
			return true;
		}

		menuBar._selectedMenuItem=undefined;
		menuBar.f_closeAllPopups();
		
		menuBar.f_updateMenuBarItemStyle(menuItem);

		return true;
	}
}
 
var __prototype = {
	/*
	f_menuBar: function() {
		this.f_super(arguments);
		
	},
	*/
	/*
	f_finalize: function() {
		
		this.f_super(arguments);
	},
	*/
	f_serialize: function() {
		this.f_serializeItems();
			
		return this.f_super(arguments);
	},
	f_setDomEvent: function(type, target) {
		if (type==f_event.SELECTION) {
			return;
		}
		return this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		if (type==f_event.SELECTION) {
			return;
		}
		return this.f_super(arguments, type, target);
	},
	
	/* ********************************************************************
	
		MenuBarItem
		
	 * ********************************************************************/
	fa_destroyItems: function(items) {
		var uiMenuItems=this._uiMenuItems;
		var items=this.f_listItemChildren(this);
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			var uiItem=uiMenuItems[item];
			if (!uiItem) {
				continue;
			}
			
			delete uiMenuItems[item];
		
			this.f_destroyMenuBarItem(uiItem);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_destroyMenuBarItem: function(uiItem) {
		uiItem.onmouseover=null;
		uiItem.onmouseout=null;
		uiItem.onmousedown=null;
		uiItem.onclick=null;
		uiItem.onblur=null;
		uiItem.onfocus=null;
		uiItem.onkeypress=null;
		uiItem.onkeydown=null;
		
		uiItem._item=undefined;

		f_core.VerifyProperties(uiItem);
	},
	/**
	 * @method private
	 * @return void
	 */
	_menuBarItem_over: function(menuBarItem, jsEvt) {
		var old=this._selectedMenuItem;
				
		var oldOpenMode=this._openMode;
		
		// f_core.Info("f_menuBar", "OVER: cur="+old+" param="+menuBarItem+" openMode="+this._openMode);
		
		if (old==menuBarItem) {
			// Le selectionné est le méme que l'ancien selectionné !
			
			this.f_updateMenuBarItemStyle(menuBarItem);
			
			if (this._openMode) {
				this.f_openUIPopup(menuBarItem, jsEvt, false, {
					position: f_popup.BOTTOM_LEFT_COMPONENT,
					component: this.f_getUIItem(menuBarItem),
					deltaX: -1,
					deltaY: 1
				});
			}
			
			return;
		}
			
		if (old) {
			this._selectedMenuItem=undefined;
	
			this.f_closeAllPopups();
				
			this.f_updateMenuBarItemStyle(old);
		}

		this._selectedMenuItem=menuBarItem;
		this.f_updateMenuBarItemStyle(menuBarItem);
				
		if (this.f_isItemDisabled(menuBarItem)) {	
			this._openMode=oldOpenMode;

//			f_core.Info("f_menuBar", "OVER DISABLED: cur="+this._selectedMenuItem+" openMode="+this._openMode);
			return;
		}
		
		this._openMode=oldOpenMode;
		if (oldOpenMode) {
			f_menuBar._MenuBarItem_setFocus(menuBarItem);

			this.f_openUIPopup(menuBarItem, jsEvt, false, {
				position: f_popup.BOTTOM_LEFT_COMPONENT,
				component: this.f_getUIItem(menuBarItem),
				deltaX: -1,
				deltaY: 1
			});
		}
	},
	/**
	 * @method private
	 * @return void
	 */
	_menuBarItem_out: function(menuBarItem) {
		var old=this._selectedMenuItem;
		if (old!=menuBarItem) {
			return;
		}

		if (this._openMode) { // old._popupOpened
			return;
		}

		this._selectedMenuItem=undefined;
		this.f_closeAllPopups();
			
		this.f_updateMenuBarItemStyle(old);
	},
	/**
	 * @method private
	 * @return void
	 */
	_menuBarItem_select: function(menuBarItem, autoSelect, jsEvent) {
		var old=this._selectedMenuItem;
		if (old && this.f_uiIsPopupOpened(old)) {
			// Un popup deja ouvert ?
			this._openMode=undefined;
			
			this.f_closeAllPopups();

			this.f_updateMenuBarItemStyle(old);

			return;			
		}
	
		if (this.f_isDisabled() || this.f_isItemDisabled(menuBarItem)) {
			return;
		}
		
		if (!this.f_hasVisibleItemChildren(menuBarItem)) {
			// Pas de popup !
			
			if (this.f_isReadOnly()) {
				return;
			}
			
			var value=this.f_getItemValue(menuBarItem);
			
			this.f_performItemSelect(menuBarItem, value, jsEvent);

			return;
		}
		this._openMode=true;

		this.f_openUIPopup(menuBarItem, jsEvent, autoSelect, {
			position: f_popup.BOTTOM_LEFT_COMPONENT,
			component: this.f_getUIItem(menuBarItem),
			deltaX: -1,
			deltaY: 1
		});
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateMenuBarItemStyle: function(menuBarItem) {
		var component=this.f_getUIItem(menuBarItem);
		// MenuBarItem 
		var className="f_menuBar_bitem";
		
		var suffix="";
		if (this.f_isDisabled() || this.f_isItemDisabled(menuBarItem)) {
			suffix+="_disabled";
	
			if (this._selectedMenuItem==menuBarItem) {
				suffix+="_hover";
			}
			
		} else if (this._selectedMenuItem==menuBarItem) {
			if (this.f_uiIsPopupOpened(menuBarItem)) {
				suffix+="_selected";
			}
			
			suffix+="_hover";
		}
		
		if (suffix) {
			className+=" "+className+suffix;
		}
		
		if (component.className!=className) {
			component.className=className;
		}
	},
	/**
	 * @method hidden
	 * @return Object item
	 */
	f_declareBarItem: function(id, label, value, accessKey, disabled) {
		var menuBarItem=this.f_appendItem(this, id, label, value, accessKey, null, disabled);
		
		var uiItem=document.createElement("BUTTON");
		this._uiMenuItems[menuBarItem]=uiItem; // Ben oui 
		uiItem._item=menuBarItem;
		
		uiItem.id=id;
		uiItem.tabIndex=-1;
		uiItem.className="f_menuBar_bitem";
		
		f_component.AddLabelWithAccessKey(uiItem, label, accessKey);

		uiItem.onmouseover=f_menuBar._MenuBarItem_mouseOver;
		uiItem.onmouseout=f_menuBar._MenuBarItem_mouseOut;
				
		if (accessKey) {
			uiItem.accessKey=accessKey;
			
			f_key.AddKeyHandler(null, accessKey, this, null, uiItem);
		}			
		
		uiItem.tabIndex=this._tabIndex;
		uiItem.onkeydown=f_menuBar._MenuBarItem_keyDown;
		uiItem.onclick=f_menuBar._MenuBarItemInput_click;
		uiItem.onfocus=f_menuBar._MenuBarItem_focus;
		uiItem.onblur=f_menuBar._MenuBarItem_blur;
		uiItem.hideFocus=true;

		this.f_updateMenuBarItemStyle(menuBarItem);

		if (this._items.length==1) {
			var dummies=this.getElementsByTagName("A");
			
			for(var i=0;i<dummies.length;i++) {
				this.removeChild(dummies[i]);
			}
		}
		
		this.appendChild(uiItem);

		return menuBarItem;
	},
	fa_keySearchAccessKey: function(menuBarItem, code, jsEvent) {
		if (this._selectedMenuItem!=menuBarItem) {
			if (this._selectedMenuItem) {
				this._selectedMenuItem._closePopup(this._selectedMenuItem);
			}
			return false;
		}
		
		if (this.f_isItemDisabled(menuBarItem) || this.f_isReadOnly()) {
			return false;
		}

		if (!menuBarItem._popupOpened) {
			return false;
		}

		// Par defaut le parent est le menuBarItem
		// On recherche le popup le plus "ouvert"
		var parent=menuBarItem;
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
				menuBarItem._closePopup(menuBarItem);
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
	fa_focusMenuItem: function(menuBarItem) {
			// On verifie si c'est un menuBar !
		for(;menuBarItem._parentItem;) {
			menuBarItem=menuBarItem._parentItem;
		}
	
//		f_core.Info("f_menuBar", "Focus menuItem "+this._selectedMenuItem+"/"+menuBarItem);
	
		if (this._selectedMenuItem==menuBarItem) {
			return;
		}
	
		f_menuBar._MenuBarItem_setFocus(menuBarItem);
		
		var old=this._selectedMenuItem;
		if (old) {
			this._selectedMenuItem=undefined;
			this.fa_updateItemStyle(old);
		}
		
		this._selectedMenuItem=menuBarItem;
	},
	fa_updateDisabled: function() {
		if (!this.fa_componentUpdated) {
			return;
		}
		
		var l=this.f_listVisibleItemChildren(this);
		for(var i=0;i<l.length;i++) {
			this.fa_updateItemStyle(l[i]);
		}
	},
	fa_updateReadOnly: function() {
	},
	fa_getSelectionProvider: function() {
		return null;
	},
	fa_isSameMenuBase: function(menuBarItem) {
		return this._selectedMenuItem==menuBarItem;
	},
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this.f_isDisabled()) {
			return false;
		}
		
		var items=this.f_listVisibleItemChildren(this);
		if (!items.length) {
			return false;
		}
		
		for(var i=0;i<items.length;i++) {
			var menuBarItem=items[i];
			
			/* On accepte le focus sur un item disabled !
			if (menuBarItem._disabled) {
				continue;
			}
			*/
			
			var uiItem=this.f_getUIItem(menuBarItem);
			if (!uiItem) {
				continue;
			}

			uiItem.focus();
			return true;
		}
		
		return false;
	},
	f_closeAllPopups: function() {
		f_core.Debug(fa_menuCore, "menuBar.f_closeAllPopups: close all popups");
		
		var items=this.f_listVisibleItemChildren(this);
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			this._closeUIPopup(item);
		}
	},
	fa_updateItemStyle: function(menuItem) {
		if (this.f_getParentItem(menuItem)==this) {
			this.f_updateMenuBarItemStyle(menuItem);
			return;
		}
		
		this.f_super(arguments, menuItem);		
	},
	
	fa_getMenuScopeName: function(menuItem) {
		var cid=this.id;
		if (menuItem==this) {
			return cid;
		}
		
		return cid+"::"+menuItem._id;
	},
	f_uiUpdateItemStyle: function(menuItem) {
		if (this.f_getParentItem(menuItem)==this) {
			this.f_updateMenuBarItemStyle(menuItem);
			return;
		}
		
		this.f_super(arguments, menuItem);		
	}
	/*,
	f_uiGetSelectedItem: function(menuItem) {
		if (this.f_getParentItem(menuItem)==this) {
			return this._selectedMenuItem;
		}
		
		return this.f_super(arguments, menuItem);
	},
	f_uiSelectItem: function(menuItemParent, menuItem) {
		f_core.Debug(f_menuBar, "f_uiSelectItem: parent="+menuItemParent+" item="+menuItem);
		if (menuItemParent==this) {
			this._selectedMenuItem=menuItem;
			return;
		}
		
		return this.f_super(arguments, menuItemParent, menuItem);
	}
	*/
}

new f_class("f_menuBar", null, __static, __prototype, f_menuBase,
	fa_readOnly, fa_disabled, fa_immediate);
