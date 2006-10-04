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
	_DestroyMenuBarItem: function(menuBar, menuBarItem) {
		var items=menuBarItem._items;
		if (items) {
			menuBarItem._items=undefined;

			for(var i=0;i<items.length;i++) {
				fa_menuCore.DestroyMenuItem(items[i]);
			}			
		}

		menuBarItem.onmouseover=null;
		menuBarItem.onmouseout=null;
		menuBarItem.onmousedown=null;
		menuBarItem.onclick=null;
		menuBarItem.onselectstart=null;
		menuBarItem.onblur=null;
		menuBarItem.onfocus=null;
		menuBarItem.onkeypress=null;
		menuBarItem.onkeydown=null;
		
		menuBarItem._className=undefined;
		menuBarItem._menuBar=undefined;
		menuBarItem._menuPopup=undefined;
		menuBarItem._value=undefined;
		menuBarItem._disabled=undefined;
		menuBarItem._accessKey=undefined;
		menuBarItem._hasFocus=undefined;
		menuBarItem._popupOpened=undefined;
		menuBarItem._selectedMenuItem=undefined;

		menuBarItem._openPopup=undefined;
		menuBarItem._closePopup=undefined;
		
		menuBarItem._iePopup=undefined;
		
		f_core.VerifyProperties(menuBarItem);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_mouseOver: function(evt) {
		var menuBar=this._menuBar;
		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}
		
		if (!evt) evt = window.event;

		menuBar._menuBarItem_over(this);
		
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_mouseOut: function(evt) {
// Pas bloqué !		if (f_core.GetEventLocked(false)) return false;
		if (!evt) evt = window.event;

		var menuBar=this._menuBar;

		menuBar._menuBarItem_out(this);

		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_mouseDown: function(evt) {
		var menuBar=this._menuBar;
		if (menuBar.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}
		if (!evt) evt = window.event;

		menuBar._menuBarItem_select(this, false, evt);
		
		f_menuBar._MenuBarItem_setFocus(this);
		
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_click: f_core.CancelEventHandler,
	/**
	 * @method private static
	 */
	_MenuBarItemInput_click: function(evt) {
		var item=this;
		var menuBar=item._menuBar;
		if (menuBar.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}
		if (!evt) evt = window.event;

		menuBar._menuBarItem_select(item, false, evt);
		
		f_menuBar._MenuBarItem_setFocus(item);
		
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_setFocus: function(menuBarItem) {
		if (menuBarItem._hasFocus) {
			return;
		}

		menuBarItem.focus();		
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_keyDown: function(evt) {
		var item=this;
		var menuBar=item._menuBar;
		if (menuBar.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}
		
		if (!evt) evt = window.event;

		return fa_menuCore.OnKeyDown(menuBar, item, evt);
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_focus: function(evt) {
		var menuItem=this; //._link;
		var menuBar=menuItem._menuBar;
		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}
		if (!evt) evt = window.event;

		menuItem._hasFocus=true;
		
		var old=menuBar._selectedMenuItem;
		
//		f_core.Info("f_menuBar", "Focus: old="+old+" param="+menuItem);
		
		if (old==menuItem) {
			return true;
		}
		
		if (old) {
			old._closePopup(old);
			menuBar._selectedMenuItem=undefined;
			
			menuBar.fa_updateItemStyle(old);
		}
			
		menuBar._selectedMenuItem=menuItem;
		menuBar.fa_updateItemStyle(menuItem);

		if (menuBar.f_isItemDisabled(menuItem)) {
//			f_core.Info("f_menuBar", "Focus-DISABLED cur="+menuBar._selectedMenuItem);
			return true;
		}
		
		if (menuBar._openMode) {
			menuItem._openPopup(menuItem, false);
		}
				
		return true;
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_blur: function(evt) {
		var menuItem=this; //._link;
		var menuBar=menuItem._menuBar;
		if (menuBar.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}
		if (!evt) evt = window.event;

		menuItem._hasFocus=undefined;
	
/*		if (menuBar._ignoreBlurOpenMode) {
			menuBar._ignoreBlurOpenMode=undefined;
			alert("IGNORE !");
			
		} else {
		*/
			menuBar._openMode=undefined;
			
			f_core.Info(f_menuBar, "Blur clear openMode");
			
/*		} */

		var old=menuBar._selectedMenuItem;
		if (old!=menuItem) {
			return true;
		}

		old._closePopup(old);
		menuBar._selectedMenuItem=undefined;
		
		menuBar.fa_updateItemStyle(old);

		return true;
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_closePopup: function(menuBarItem) {
		if (!menuBarItem._popupOpened || !menuBarItem._menuPopup) {
			return;
		}

//		f_core.Info("f_menuBar", "Close popup cur="+menuBarItem._selectedMenuItem+"/ param="+menuBarItem);		

		menuBarItem._popupOpened=undefined;
		
		var menuBar=menuBarItem._menuBar;
		
		f_popup.UnregisterWindowClick(menuBar);
		
		var old=menuBarItem._selectedMenuItem;
		if (old) {
			old._closePopup(old);
			old._over=undefined;
			
			menuBarItem._selectedMenuItem=undefined;
			menuBar.fa_updateItemStyle(old);
		}
				
		f_key.ExitScope(menuBarItem.id);
		
		var popup=menuBarItem._menuPopup;
		if (popup) {
			if (menuBarItem._iePopup) {
				fa_menuCore._Ie_closePopup(menuBarItem);
				
			} else {
				popup.style.visibility="hidden";
			}
					
			var old=popup._menuItemSelected;
			if (old) {
				old._closePopup(old);
				
				popup._menuItemSelected=null;
			}			
		}
		
		menuBar.fa_updateItemStyle(menuBarItem);			
	},
	/**
	 * @method private static
	 */
	_MenuBarItem_openPopup: function(menuBarItem, autoSelect) {
//		f_core.Info("f_menuBar", "OpenPopup: "+menuBarItem._popupOpened+"/"+this._openMode);

		if (menuBarItem._popupOpened) {
			return;
		}
	
		var menuBar=menuBarItem._menuBar;

		if (menuBar.f_isItemDisabled(menuBarItem)) {
			return;
		}

		var old=menuBar._selectedMenuItem;
		if (old) {
			menuBar._selectedMenuItem=undefined;
			menuBar.fa_updateItemStyle(old);
		}

		if (!menuBarItem._menuPopup) {
			menuBar._selectedMenuItem=menuBarItem;
			menuBar.fa_updateItemStyle(menuBarItem);
		
			return;
		}
	
		fa_menuCore.HideSeparators(menuBarItem);
	
		var popup=menuBarItem._menuPopup;
										
		if (f_popup.RegisterWindowClick({
				exit: menuBar._a_clickOutside,
				keyDown: function(evt) {
					return fa_menuCore.OnKeyDown(menuBar, menuBarItem, evt);
				}
			}, menuBar, menuBar)==false) {
			return;
		}

		if (popup) {
			f_key.EnterScope(menuBarItem.id);

			if (menuBarItem._iePopup) {
				fa_menuCore._Ie_openPopup(menuBarItem, menuBarItem, 0, menuBarItem.offsetHeight);
			
			} else {
				var p1=f_core.GetAbsolutePos(menuBarItem);
			
				var x=p1.x;
				var y=p1.y+menuBarItem.offsetHeight;

				x+=1; // Les bordures ....
				y+=1;

				var pos={ x: x, y: y };
				
				f_core.ComputePopupPosition(popup, pos);

				popup.style.left=pos.x+"px";
				popup.style.top=pos.y+"px";
			
				popup.style.visibility="inherit";
			}			
			popup._menuItemSelected=null;
		}
	
		menuBarItem._popupOpened=true;
		menuBar._selectedMenuItem=menuBarItem;
		menuBar.fa_updateItemStyle(menuBarItem);

		if (autoSelect) {
			if (menuBarItem._items && menuBarItem._items.length>0) {
				menuBar._menuItem_over(menuBarItem._items[0], false);
			}
		}
	}
}
 
var __prototype = {
	f_menuBar: function() {
		this.f_super(arguments);

		this._className=f_core.GetAttribute(this, "v:className");
		if (!this._className) {
			this._className=this.className;
		}
		
//		this._menuClassName=this._className;
	},
	/*
	f_finalize: function() {
		this._className=undefined; // string
		
		this.f_super(arguments);
	},
	*/
	f_serialize: function() {
		this.f_serializeItems();
			
		return this.f_super(arguments);
	},
	f_update: function() {
		if (this._items) {
			var l=this._items;
			
			var dummies=this.getElementsByTagName("A");
			
			for(var i=0;i<dummies.length;i++) {
				this.removeChild(dummies[i]);
			}
			
			for(var i=0;i<l.length;i++) {
				var menuBarItem=l[i];
				
// XXXX On est pas pressé !
				this._updateBarItem(menuBarItem);
//				this.fa_updateItemStyle(menuBarItem);
				
				this.appendChild(menuBarItem);
			}
		}
		
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
		for(var i=0;i<items.length;i++) {
			f_menuBar._DestroyMenuBarItem(this, items[i]);
		}
	},	
	_updateBarItem: function(menuBarItem) {
		var items=menuBarItem._items;
		if (items) {
			for(var i=0;i<items.length;i++) {
				this._updateItem(items[i]);
			}
		}
		
		var table=menuBarItem._menuPopup;
		if (table) {
			if (menuBarItem._iePopup) {
				menuBarItem.appendChild(table);
				
			} else {
				this.ownerDocument.body.appendChild(table);
			}
		}
						
		this.fa_updateItemStyle(menuBarItem);
	},
	_menuBarItem_over: function(menuBarItem) {
		var old=this._selectedMenuItem;
		var oldOpenMode=this._openMode;
		
//		f_core.Info("f_menuBar", "OVER: cur="+old+" param="+menuBarItem+" openMode="+this._openMode);
		
		if (old==menuBarItem) {
			this.fa_updateItemStyle(menuBarItem);
			
			if (this._openMode) {
				menuBarItem._openPopup.call(this, menuBarItem, false);
			}
			
			return true;
		}
			
		if (old) {
			old._closePopup(old);
			this._selectedMenuItem=undefined;
			
			this.fa_updateItemStyle(old);
		}

		this._selectedMenuItem=menuBarItem;
		this.fa_updateItemStyle(menuBarItem);
				
		if (this.f_isItemDisabled(menuBarItem)) {	
			this._openMode=oldOpenMode;

//			f_core.Info("f_menuBar", "OVER DISABLED: cur="+this._selectedMenuItem+" openMode="+this._openMode);
			return;
		}

		f_menuBar._MenuBarItem_setFocus(menuBarItem);
		
		this._openMode=oldOpenMode;
		if (this._openMode) {
			menuBarItem._openPopup.call(this, menuBarItem, false);
		}
		
/*			
		if (old) {
			this._ignoreBlurOpenMode=true;
		}		
		*/
	},
	_menuBarItem_out: function(menuBarItem) {
		var old=this._selectedMenuItem;
		if (old!=menuBarItem) {
			return true;
		}

		if (this._openMode) { // old._popupOpened
			return true;
		}

		this._selectedMenuItem=undefined;
		old._closePopup(old);
			
		this.fa_updateItemStyle(old);
	},
	_menuBarItem_select: function(menuBarItem, autoSelect, jsEvent) {
		var old=this._selectedMenuItem;
		if (old && old._popupOpened) {
			// Un popup deja ouvert ?
			this._openMode=undefined;
			
			old._closePopup(old);

			this.fa_updateItemStyle(old);

			return;			
		}
	
		if (this.f_isItemDisabled(menuBarItem) || this.f_isDisabled()) {
			return;
		}
		
		if (!menuBarItem._menuPopup) {
			// Pas de popup !
			
			if (this.f_isReadOnly()) {
				return;
			}
			
			var value=menuBarItem._value;
			this._performItemSelect(menuBarItem, value, jsEvent);

			return;
		}
		this._openMode=true;
		menuBarItem._openPopup(menuBarItem, autoSelect);
	},
	fa_updateItemStyle: function(item) {
		if (!item._parentItem && !item._separator) {
			// MenuBarItem 
			var className=item._className;
			
			if (item._disabled || this.f_isDisabled()) {
				className+="_disabled";
		
				if (this._selectedMenuItem==item) {
					className+="_hover";
				}
				
			} else if (this._selectedMenuItem==item) {
				if (item._popupOpened) {
					className+="_selected";
				}
				
				className+="_hover";
			}
			
			item.className=className;
		
			return;	
		}
		
		this.f_super(arguments, item);
	},
	_accessKeyBarItem: function(evt, menuBarItem) {
		var menuItem=menuBarItem._link;
		
		this._menuBarItem_select(menuItem, true, evt);
		
		f_menuBar._MenuBarItem_setFocus(menuItem);
	},
	_declareBarItem: function(id, label, value, accessKey, disabled) {
		var document=this.ownerDocument;
		
		var menuBarItem;
		
		menuBarItem=document.createElement("BUTTON");
		
		menuBarItem.id=id;
		menuBarItem.tabIndex=-1;
		menuBarItem.className=this.className+"_bitem";
		menuBarItem._className=menuBarItem.className;
		
		menuBarItem._accessKey=accessKey;
		f_component.AddLabelWithAccessKey(menuBarItem, label, accessKey);

		menuBarItem.onmouseover=f_menuBar._MenuBarItem_mouseOver;
		menuBarItem.onmouseout=f_menuBar._MenuBarItem_mouseOut;
		
		/*
		if (f_core.IsGecko()) {
			menuBarItem.onmousedown=f_menuBar._MenuBarItem_mouseDown;
			
		} else {
			menuBarItem.onmouseup=f_menuBar._MenuBarItem_mouseDown;
		}
		*/
		
		menuBarItem.onselectstart=f_core._IeOnSelectStart;
				
		if (accessKey) {
			menuBarItem.accessKey=accessKey;
			
			f_key.AddKeyHandler(null, accessKey, this, null, menuBarItem);
		}			
		menuBarItem.tabIndex=this._tabIndex;
		menuBarItem.onkeydown=f_menuBar._MenuBarItem_keyDown;
		menuBarItem.onclick=f_menuBar._MenuBarItemInput_click;
		menuBarItem.onfocus=f_menuBar._MenuBarItem_focus;
		menuBarItem.onblur=f_menuBar._MenuBarItem_blur;
		menuBarItem.hideFocus=true;
			
			
		menuBarItem._menuBar=this;
		menuBarItem._value=value;
		menuBarItem._openPopup=f_menuBar._MenuBarItem_openPopup;
		menuBarItem._closePopup=f_menuBar._MenuBarItem_closePopup;
	
		this.f_addItem(this, menuBarItem);

		if (f_popup.Ie_enablePopup()) {
			// On associe le POPUP 
			
			menuBarItem._iePopup=fa_menuCore._Ie_getPopup(menuBarItem);
		}

		if (disabled) {
			this.f_setItemDisabled(menuBarItem, disabled);
		}

		return menuBarItem;
	},
	_a_keySearchAccessKey: function(menuBarItem, code, jsEvent) {
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
	_a_focusMenuItem: function(menuBarItem) {
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
	_a_closeMenu: function(menuBarItem, jsEvent, definitive) {
		// On verifie si c'est un menuBar !
		for(;menuBarItem._parentItem;) {
			menuBarItem=menuBarItem._parentItem;
		}
		
		if (definitive) {
			this._openMode=undefined;
		}
		
		f_menuBar._MenuBarItem_closePopup(menuBarItem);
	},
	_a_clickOutside: function(jsEvt) {
		f_core.Info(f_menuBar, "Click outside !");
		this._openMode=undefined;

		var menuBarItem=this._selectedMenuItem;
		if (!menuBarItem) {
			return;
		}
		menuBarItem._closePopup(menuBarItem, jsEvt);
		
		this.fa_updateItemStyle(menuBarItem);		
	},
	fa_updateDisabled: function() {
		if (!this.fa_componentUpdated) {
			return;
		}
		
		if (this._items) {
			var l=this._items;
			for(var i=0;i<l.length;i++) {
				this.fa_updateItemStyle(l[i]);
			}
		}		
	},
	fa_updateReadOnly: function() {
	},
	_a_getSelectionProvider: function() {
		return null;
	},
	_a_isSameMenuBase: function(menuBarItem) {
		return this._selectedMenuItem==menuBarItem;
	},
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this.f_isDisabled()) {
			return false;
		}
		
		var items=this._items;
		if (!items || items.length<1) {
			return false;
		}
		for(var i=0;i<items.length;i++) {
			var menuBarItem=items[i];
			
			/* On accepte le focus sur un item disabled !
			if (menuBarItem._disabled) {
				continue;
			}
			*/
			
			menuBarItem.focus();
			return true;
		}
		
		return false;
	}
}

var f_menuBar=new f_class("f_menuBar", null, __static, __prototype, f_menuBase,
	fa_readOnly, fa_disabled, fa_immediate);
