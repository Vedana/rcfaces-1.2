/*
 * $Id$
 */

/**
 * Aspect Menu
 *
 * @aspect hidden fa_menuCore extends fa_groupName, fa_items
 * @author Olivier Oeuillot
 * @version $Revision$
 */

var __static = {

	/** 
	 * @field private static final string
	 */
	_MENU_CLASSNAME: "f_menu",
	
	/** 
	 * @field hidden static final number
	 */
	_AS_PUSH_BUTTON: 1,

	/** 
	 * @field hidden static final number
	 */
	_AS_CHECK_BOX: 2,

	/** 
	 * @field hidden static final number
	 */
	_AS_RADIO_BUTTON: 4,
	
	/** 
	 * @field private static final string
	 */
	_BLANK_IMAGE_URL: "menu/blank.gif",
	
	/** 
	 * @field hidden static final number
	 */
	_LABEL_CHANGED: 1,
	
	/** 
	 * @field private static number
	 */
	_ItemIds: 0,
	
	/**
	 * @method private static 
	 */	
	_DestroyMenuItem: function(menuItem) {
		menuItem._menuBar=undefined;
		menuItem._parentItem=undefined;
		// menuItem._disabled=undefined; // boolean
		// menuItem._removeAllWhenShow=undefined; // boolean
		
		var menuActionList=menuItem._menuActionList;
		if (menuActionList) {
			menuItem._menuActionList=undefined;
			
			f_classLoader.Destroy(menuActionList);
		}

		if (menuItem._separator) {
			menuItem.onmousedown=null;		
			// menuItem._separator=undefined; // boolean
			return;
		}
		
		var items=menuItem._items;
		if (items) {
			menuItem._items=undefined;

			for(var i=0;i<items.length;i++) {
				fa_menuCore._DestroyMenuItem(items[i]);
			}			
		}
	
		var icon=menuItem._icon; // HtmlImageElement
		if (icon) {
			menuItem._icon=undefined;
			// icon._menuClassName=undefined; // string

			f_core.VerifyProperties(icon);
		}
		
		var text=menuItem._text;
		if (text) {
			menuItem._text=undefined; // HtmlDivElement
						
			f_core.VerifyProperties(text);
		}

		menuItem.onmouseover=null;
		menuItem.onmouseout=null;		
		menuItem.onmousedown=null;		
		menuItem.onclick=null;		
		
		// menuItem._menuClassName=undefined; // string
		menuItem._menuPopup=undefined;
		// menuItem._attachedTable=undefined; // boolean
		// menuItem._checked=undefined; // boolean
		menuItem._value=undefined; // any
		// menuItem._label=undefined; // string
		// menuItem._groupName=undefined; // string
		// menuItem._over=undefined; // boolean
		// menuItem._style=undefined; // number
		// menuItem._changes=undefined; // boolean

		// menuItem._imageURL=undefined; // string
		// menuItem._hoverImageURL=undefined; // string
		// menuItem._selectedImageURL=undefined; // string
		// menuItem._disabledImageURL=undefined; // string
		// menuItem._expandedImageURL=undefined; // string

		menuItem._selectedMenuItem=undefined;
		// menuItem._popupOpened=undefined; // boolean
		// menuItem._accessKey=undefined; // string

		menuItem._openPopup=undefined;
		menuItem._closePopup=undefined;
		
		var iePopup=menuItem._iePopup;
		if (iePopup) {
			menuItem._iePopup=undefined;
			f_core.VerifyProperties(iePopup);
		}
		menuItem._rlink=undefined;
		menuItem._ricon=undefined;
		
		f_core.VerifyProperties(menuItem);
	},
	/**
	 * @method private static 
	 */
	_MenuItem_mouseOver: function(evt) {
		var menuBar=this._menuBar;
		if (menuBar.f_getEventLocked(false)) {
			return false;
		}
		
		menuBar._menuItem_over(this, true);

		if (!evt) {
			evt=window.event;
		}
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_MenuItem_mouseOut: function(evt) {
// Pas bloqué !			if (f_core.GetEventLocked(false)) return false;

		var menuBar=this._menuBar;

		menuBar._menuItem_out(this);
	
		if (!evt) {
			evt=window.event;
		}
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_MenuItem_mouseDown: function(evt) {
		var menuBar=this._menuBar;
		if (menuBar.f_getEventLocked()) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}
	
		menuBar._menuItem_select(this, evt);
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_SeparatorItem_click: f_core.CancelEventHandler,
	/**
	 * @method private static 
	 */
	_MenuItem_click: f_core.CancelEventHandler,
	/**
	 * @method private static 
	 */
	_MenuItem_openPopup: function(menuItem, autoSelect) {
		if (menuItem._popupOpened) {
			return;
		}

		var popup=menuItem._menuPopup;
		if (!popup) {
			return;
		}
		
		var menuBar=menuItem._menuBar;
		
		// Efface tout si necessaire !
		if (menuItem._removeAllWhenShow) {
			// Effaces tous les items !
			menuBar.f_removeAllItems(menuItem);
		}
		
		var menuActionList=menuItem._menuActionList;
		if (menuActionList) {
			// Appel les callbacks !
			var evt=new f_event(menuBar, f_event.MENU, null, menuItem, null, menuBar);
			try {
				if (!menuActionList.f_callActions(evt)) {
					// Refuse l'affichage !
					return;
				}
	
			} finally {
				f_classLoader.Destroy(evt);
			}
		}
		
		if (menuItem._items.length<1) {
			// Pas d'items !
			return;
		}
		
		f_key.EnterScope(menuItem.id);
		
		menuItem._popupOpened=true;
		menuBar._a_updateItemStyle(menuItem);

		// Il faut filtrer les separators ...
		fa_menuCore.HideSeparators(menuItem);

		if (menuItem._iePopup) {
			fa_menuCore._Ie_openPopup(menuItem, menuItem, menuItem._rlink.offsetWidth+1, 0);

		} else {
			var p1=f_core.GetAbsolutePos(menuItem);
			var p2=f_core.GetAbsolutePos(popup);
		
			var x=p1.x+menuItem.offsetWidth;
			var y=p1.y;
			
			x-=p2.x-popup.offsetLeft;
			y-=p2.y-popup.offsetTop;
				
			x+=2;
			
			var pos={ x: x, y: y };
			
			f_core.ComputePopupPosition(popup, pos);
			
			if (!popup.style.zIndex && menuItem._parentItem) {
				var ppop=menuItem._parentItem._menuPopup;
				
				var zbuf=0;
				if (ppop) {
					zbuf=ppop.style.zIndex;
				}
				
				if (!zbuf) {
					ppop=1000;
					
				} else {
					ppop=parseInt(zbuf, 10);
				}
				
				popup.style.zIndex=String(ppop+1);
			}
	
			popup.style.left=pos.x+"px";
			popup.style.top=pos.y+"px";
		
			popup.style.visibility="inherit";
		}
		
		if (autoSelect) {
			if (menuItem._items && menuItem._items.length>0) {
				menuBar._menuItem_over(menuItem._items[0], false);
			}
		}
	},
	/**
	 * @method private static 
	 */
	_MenuItem_closePopup: function(menuItem) {

		if (!menuItem._popupOpened) {
			return;
		}

		menuItem._popupOpened=false;

		var selectedMenuItem=menuItem._selectedMenuItem;
		if (selectedMenuItem) {
			fa_menuCore._MenuItem_closePopup(selectedMenuItem);
			
			menuItem._selectedMenuItem=undefined;

			selectedMenuItem._over=false;			
			selectedMenuItem._menuBar._a_updateItemStyle(selectedMenuItem);			
		}

		var menuBar=menuItem._menuBar;
		
		f_key.ExitScope(menuItem.id);
		
		menuBar._a_updateItemStyle(menuItem);

		if (menuItem._menuPopup) {
			if (menuItem._iePopup) {
				fa_menuCore._Ie_closePopup(menuItem);
				
			} else {
				menuItem._menuPopup.style.visibility="hidden";
			}
		}
	},
	/**
	 * @method static final hidden
	 */
	HideSeparators: function(menuItem) {
		var popup=menuItem._menuPopup;
		f_core.Assert(popup, "No popup for item '"+menuItem+"'.");
		
		var items=menuItem._items;
		
		var first=true;
		var lastSep;
		for(var i=0;i<items.length;i++) {
			var li=items[i];
			if (li._separator) {

				// Un séparateur !
				if (first || lastSep) {
					li.style.display="none";
					continue;
				}
				lastSep=li;
				
				continue;
			}
			
			if (li._visible===false) {
				continue;
			}
			
			first=false;
			
			// Pas un separateur !
			if (lastSep) {
				lastSep.style.display="";
				lastSep=undefined;
			}			
		}
		
		if (lastSep) {
			lastSep.style.display="none";
		}
	},
	/**
	 * @method static final hidden
	 */
	OnKeyDown: function(menu, menuPopup, evt) {
		var cancel;

		f_core.Assert(evt, "Event is null !");
		var code=evt.keyCode;

		switch(code) {
		case f_key.VK_DOWN: // FLECHE VERS LE BAS
			menu._nextMenuItem(menuPopup, evt);
			cancel=true;
			break;
			
		case f_key.VK_UP: // FLECHE VERS LE HAUT
			menu._previousMenuItem(menuPopup, evt);
			cancel=true;
			break;
			
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
			menu._nextMenuItemLevel(menuPopup, evt);
			cancel=true;
			break;
			
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
			menu._previousMenuItemLevel(menuPopup, evt);
			cancel=true;
			break;

		case f_key.VK_HOME: // HOME
// @TODO		menu._nextMenuItemLevel(menuPopup, evt);
			cancel=true;
			break;
			
		case f_key.VK_END: // END
// @TODO			menu._previousMenuItemLevel(menuPopup, evt);
			cancel=true;
			break;


		case f_key.VK_TAB:
			// Rien ....
			if (!menu._a_tabKeySelection || !menu._a_tabKeySelection()) {
				break;
			}
			
			// C'est un RETURN alors !
	
		case f_key.VK_RETURN:
	 	case f_key.VK_ENTER:
			menu._keySelectMenuItem(menuPopup, evt);
			cancel=true;
			break;

		case f_key.VK_ESCAPE:
			menu._keyCloseMenuItem(menuPopup, evt);
			cancel=true;
			break;
		
		default: 
			if (menuPopup && menuPopup._popupOpened) {
				cancel=true;
				
				if (!evt.altKey) {
					menu._a_keySearchAccessKey(menuPopup, code, evt);
					cancel=true;
				}
				
			} else if (!evt.altKey) {
				cancel=true;
			}
		}

		if (cancel) {
			return f_core.CancelEvent(evt);		
		}
		
		return true;
	},
	
	/* ******************************************************************* */
	/* Popup IE */
	_Ie_getPopup: function(menuItem) {
	
		var parentItem=menuItem._parentItem
	
		if (!parentItem) {
			// un menuBar
			
			return f_popup.Ie_GetPopup(document);
		}
		
		var parentPopup=fa_menuCore._Ie_getPopup(parentItem);
		
		return f_popup.Ie_GetPopup(parentPopup.document);
	},
	_Ie_onclick: function() {
		return fa_menuCore._MenuItem_click.call(this._link, f_core.IeGetEvent(this));
	},
	_Ie_onmouseover: function() {
		return fa_menuCore._MenuItem_mouseOver.call(this._link, f_core.IeGetEvent(this));
	},
	_Ie_onmouseout: function() {
		return fa_menuCore._MenuItem_mouseOut.call(this._link, f_core.IeGetEvent(this));
	},
	_Ie_onmousedown: function() {
		return fa_menuCore._MenuItem_mouseDown.call(this._link, f_core.IeGetEvent(this));
	},
	_Ie_onkeydown: function() {
		var evt=f_core.IeGetEvent(this);

		try {
			var callbacks=f_popup.Callbacks;
			if (!callbacks) {
				return;
			}
			
			var keyDown=callbacks.keyDown;
			if (keyDown) {
				return keyDown.call(f_popup.Component, evt, f_popup.Popup);
			}
			
		} catch (x) {
			f_core.Error(fa_menuCore, "keyDown callback throws exception", x);
		}
	},
	_Ie_onkeyup: function() {
		var evt=f_core.IeGetEvent(this);

		try {
			var callbacks=f_popup.Callbacks;
			if (!callbacks) {
				return;
			}
			
			var keyUp=callbacks.keyUp;
			if (keyUp) {
				return keyUp.call(f_popup.Component, evt, f_popup.Popup);
			}
			
		} catch (x) {
			f_core.Error(fa_menuCore, "keyUp callback throws exception", x);
		}
	},
	_Ie_onkeypress: function() {
		var evt=f_core.IeGetEvent(this);

		try {
			var callbacks=f_popup.Callbacks;
			if (!callbacks) {
				return;
			}
			
			var keyPress=callbacks.keyPress;
			if (keyPress) {
				return keyPress.call(f_popup.Component, evt, f_popup.Popup);
			}
			
		} catch (x) {
			f_core.Error(fa_menuCore, "keyPress callback throws exception", x);
		}
	},
	_Ie_openPopup: function(menuItem, component, popupX, popupY, popupWidth) {
		var popup=menuItem._iePopup;
	
		var popupDocument=popup.document;
		
		f_key.UpdateAccessKeyRule(document.parentWindow, popupDocument.parentWindow);
				
		popupDocument.hideFocus=true;

		var code=menuItem._menuPopup.outerHTML;
	
		var pbody=popupDocument.body;
		pbody._menuItem=menuItem;
		pbody.onunload=fa_menuCore._Ie_unload;
		pbody.onkeydown=fa_menuCore._Ie_onkeydown;
		pbody.onkeyup=fa_menuCore._Ie_onkeyup;
		pbody.onkeypress=fa_menuCore._Ie_onkeypress;
		pbody.innerHTML=code;
		
		var seps=new Array;
		
		var menuPopup=pbody.firstChild;
		if (menuPopup) {
			menuPopup.style.visibility="inherit";
	
			var items=menuItem._menuPopup.getElementsByTagName("LI");
			var pitems=popupDocument.getElementsByTagName("LI");

			for(var i=0;i<items.length;i++) {
				var item=items[i];
				var pitem=pitems[i];

				seps.push(pitem);

				
				// Il semble que le LAYOUT soit mauvais ... 
				// il faut forcer le display BLOCK pour avoir un bon resultat 
				if (item._separator) {
					continue;
				}
					
				pitem._link=item;
				item._rlink=pitem;
				
				pitem.onclick=fa_menuCore._Ie_onclick;
				pitem.onmousedown=fa_menuCore._Ie_onmousedown;
				pitem.onmouseover=fa_menuCore._Ie_onmouseover;
				pitem.onmouseout=fa_menuCore._Ie_onmouseout;
				
				if (!item._icon) {
					continue;
				}
				
				var picon=f_core.GetFirstElementByTagName(pitem, "IMG");
				if (picon) {
					item._ricon=picon;
					//picon.style.position="relative";
					//picon.style.top="-1px";
				}
			}
		}
		
		popup.show(0, 0, 0, 0);
		var popupW = menuPopup.offsetWidth;
		var popupH = menuPopup.offsetHeight;
		
		if (popupWidth) {
			popupW=popupWidth;
		}
	
		if (component._rlink) {
			component=component._rlink;
		}

		popup.show(popupX, popupY, popupW, popupH, component);
		
		// Il faut motiver les composants ?????
		// Merci IE .... au moins il y a une solution !
		for(var i=0;i<seps.length;i++) {
			if (seps[i].style.display=="none") {
				continue;
			}
			seps[i].style.visibility="inherit";
		}
	},
	_Ie_unload: function(evt) {
		var body=this.document.body;

		var menuItem=body._menuItem;
		if (!menuItem) {
			return;
		}
		
		body.onunload=null;
		body.onkeydown=null;
		body.onkeyup=null;
		body.onkeypress=null;
		body._menuItem=undefined;
				
		var pitems=body.getElementsByTagName("LI");
		for(var i=0;i<pitems.length;i++) {
			var pmenuItem=pitems[i];
			
			if (!pmenuItem._link) {
				continue;
			}

			pmenuItem._link._rlink=undefined;
			pmenuItem._link._ricon=undefined;
			pmenuItem._link=undefined;
			// pmenuItem._accessKey=undefined; // string
			// pmenuItem._value=undefined; // string
			// pmenuItem._menuClassName=undefined; // string
			// pmenuItem._disabled=undefined; // boolean
			// pmenuItem._over=undefined; // boolean
			
			pmenuItem.onclick=null;
			pmenuItem.onmousedown=null;
			pmenuItem.onmouseover=null;
			pmenuItem.onmouseout=null;

	//		f_core.VerifyProperties(pmenuItem);
		}

		var menuBar=menuItem._menuBar;
		if (menuItem._popupOpened) {
			menuBar._a_clickOutside(evt);
			return;
		}
		
		if (menuItem._parentItem) {
			return;
		}
		f_popup.UnregisterWindowClick(menuBar);
	},
	_Ie_closePopup: function(menuItem) {
		var popup=menuItem._iePopup;
		if (!popup.isOpen) {
			return;
		}

		popup.hide();
	}	
}

var __prototype = {
	fa_menuCore: function() {
		if (this.tagName) {
			this._menuClassName=f_core.GetAttribute(this, "v:menuClassName");
		}
		if (!this._menuClassName) {
			this._menuClassName=fa_menuCore._MENU_CLASSNAME;
		}
		
		this._blankMenuImageURL=f_env.GetStyleSheetBase()+fa_menuCore._BLANK_IMAGE_URL;
		
		f_imageRepository.PrepareImage(this._blankMenuImageURL);
	},
	f_finalize:  function() {
		// this._popupOpened=undefined;  // boolean
		this._selectedMenuItem=undefined;
		// this._menuClassName=undefined; // string
		// this._blankMenuImageURL=undefined; // string
		// this._menuItemsChanged=undefined; // boolean
	},
	/**
	 * @method public
	 * @param Object parentItem Parent object or <code>null</code>
	 * @param string id Identifier of the item. (can be <code>null</code>)
	 * @param string groupName Group name of the item.
	 * @param string label Label of the item.
	 * @param optional string value
	 * @param optional boolean checked
	 * @param optional string accessKey
	 * @param optional string tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @param optional string acceleratorKey
	 * @return Object
	 */
	f_appendRadioItem: function(parentItem, id, groupName, label, value, checked, accessKey, tooltip, disabled, visible, acceleratorKey) {
		f_core.Assert(typeof(groupName)=="string", "groupName parameter is invalid. ("+groupName+")");

		var item=this.f_appendItem(parentItem, id, label, value, accessKey, tooltip, disabled, visible, acceleratorKey);
		
		item._style=fa_menuCore._AS_RADIO_BUTTON;
		if (groupName) {
			this._setItemGroupName(item, groupName);
		}		
		if (checked) {
			this.f_setItemChecked(item, checked);
		}
		
		return item;
	},
	/**
	 * @method public
	 * @param Object parentItem Parent object or <code>null</code>
	 * @param string id Identifier of the item. (can be <code>null</code>)
	 * @param string label Label of the item.
	 * @param optional string value
	 * @param optional boolean checked
	 * @param optional string accessKey
	 * @param optional string tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @param optional string acceleratorKey
	 * @return Object
	 */
	f_appendCheckItem: function(parentItem, id, label, value, checked, accessKey, tooltip, disabled, visible, acceleratorKey) {
		var item=this.f_appendItem(parentItem, id, label, value, accessKey, tooltip, disabled, visible, acceleratorKey);

		item._style=fa_menuCore._AS_CHECK_BOX;
		if (checked) {
			this.f_setItemChecked(item, checked);
		}
				
		return item;
	},
	/**
	 * Add an item to a component.
	 * 
	 * @method public
	 * @param Object parentItem Parent object or <code>null</code>
	 * @param string id Identifier of the item. (can be <code>null</code>)
	 * @param string label Label of the item.
	 * @param optional string value Value of the item.
	 * @param optional string accessKey Access key of the item.
	 * @param optional string tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @return Object
	 */
	f_appendItem: function(parentItem, id, label, value, accessKey, tooltip, disabled, visible, acceleratorKey) {
		f_core.Assert(parentItem===null || typeof(parentItem)=="object", "parentItem parameter is invalid. ("+parentItem+")");
		f_core.Assert(id===null || typeof(id)=="string", "id parameter is invalid. ("+id+")");
		f_core.Assert(typeof(label)=="string", "label parameter is invalid. ("+label+")");
		
		if (!parentItem) {
			parentItem=this;
		}
		
		if (!id) {
			id=this.id+"__"+(fa_menuCore._ItemIds++);
		}
		
		var table=parentItem._menuPopup;
		if (!table) {
			table=this.f_createPopup();
			
			parentItem._menuPopup=table;
		}
		
		var menuItem=document.createElement("LI");
		menuItem.id=id;
		menuItem.className=this._menuClassName+"_item";
		menuItem._style=fa_menuCore._AS_PUSH_BUTTON;
		
		table.appendChild(menuItem);

		var image=document.createElement("IMG");
		image.className=this._menuClassName+"_item_image";
		image.align="middle";
		image.border=0;
		image.width=18;
		image.height=18;
		image.src=this._blankMenuImageURL;
		image._menuClassName=image.className;
		menuItem._icon=image;
		
		menuItem.appendChild(image);
		
		var div=document.createElement("LABEL");
		div.className=this._menuClassName+"_item_text";
		if (accessKey) {
			menuItem._accessKey=accessKey;
			menuItem.accessKey=accessKey;
		}

		f_component.AddLabelWithAccessKey(div, label, accessKey);
		
		menuItem._label=label;
		menuItem._text=div;
		menuItem.appendChild(div);

		if (acceleratorKey) {
			var htmlAcceleratorKey=f_core.EncodeHtml(acceleratorKey);
			var accelV=document.createElement("LABEL");
			accelV.className=this._menuClassName+"_item_accelV";
			accelV.innerHTML=htmlAcceleratorKey;
			menuItem.appendChild(accelV);
	
			var accel=document.createElement("LABEL");
			accel.className=this._menuClassName+"_item_accel";
			accel.innerHTML=htmlAcceleratorKey;
			menuItem.appendChild(accel);
		}
		
		menuItem.onmouseover=fa_menuCore._MenuItem_mouseOver;
		menuItem.onmouseout=fa_menuCore._MenuItem_mouseOut;
		menuItem.onmousedown=fa_menuCore._MenuItem_mouseDown;
		menuItem.onclick=fa_menuCore._MenuItem_click;
		
		menuItem._menuClassName=menuItem.className;
		menuItem._menuBar=this;
		menuItem._value=value;
		menuItem._parentItem=parentItem;
		menuItem._openPopup=fa_menuCore._MenuItem_openPopup;
		menuItem._closePopup=fa_menuCore._MenuItem_closePopup;
		
		this._addItem(parentItem, menuItem);

		if (f_popup.Ie_enablePopup()) {
			// On associe le POPUP 
			
			menuItem._iePopup=fa_menuCore._Ie_getPopup(menuItem);
		}
		
		if (disabled) {
			this.f_setItemDisabled(menuItem, disabled);
		}
		if (tooltip) {
			this.f_setItemToolTip(menuItem, tooltip);
		}
		if (visible===false) {
			this.f_setItemVisible(menuItem, visible);
		}
	
		this._menuItemsChanged=true;
		
		return menuItem;
	},
	/**
	 * @method hidden
	 */
	_addMenuItemListeners: function(menuItem, removeAllWhenShow, listeners) {
		menuItem._removeAllWhenShow=true;
		
		if (arguments.length<3) {
			return;
		}
		
		var l=menuItem._menuActionList;
		if (!l) {
			l=new f_actionList(this, f_event.MENU);
			menuItem._menuActionList=l;
		}
		
		for(var i=2;i<arguments.length;i++) {
			l.f_addAction(arguments[i]);
		}
	},
	/**
	 * @method protected 
	 */
	f_createPopup: function() {
		var table=document.createElement("UL");
		table.className=this._menuClassName+"_popup";
		table.style.visibility="hidden";
		
		return table;
	},
	_appendSeparatorItem: function(parentItem) {
		var document=this.ownerDocument;

		var table=parentItem._menuPopup;
		if (!table) {
			table=this.f_createPopup();
			
			parentItem._menuPopup=table;
		}
		
		var item=document.createElement("LI");
		item.className=this._menuClassName+"_item_sep";
		item.onmousedown=fa_menuCore._SeparatorItem_click;
		item._separator=true;
		item._disabled=true;
		item._menuBar=this;
		item._parentItem=parentItem;
		
		table.appendChild(item);
		
		this._addItem(parentItem, item);
		
		this._menuItemsChanged=true;
	},
	_updateItem: function(menuItem) {
		var items=menuItem._items;
		if (items) {
			for(var i=0;i<items.length;i++) {
				this._updateItem(items[i]);
			}
		}
		
		var table=menuItem._menuPopup;
		if (table && !menuItem._attachedTable) {
			menuItem._attachedTable=true;
			if (menuItem._iePopup) {
				menuItem.appendChild(table);
				
			} else {
				this.ownerDocument.body.appendChild(table);
			}
		}
		
		var changes=menuItem._changes;
		if (changes) {
			menuItem._changes=undefined;

			if (changes & fa_menuCore._LABEL_CHANGED) {
				f_component.AddLabelWithAccessKey(menuItem._text, menuItem._label, menuItem._accessKey, true);
			}
		}
		
		this._a_updateItemStyle(menuItem);		
	},
	_nextMenuItem: function(menuBarItem, evt) {
	
		// Par defaut le parent est le menuBarItem
		var parent=menuBarItem;
		
		if (!this._a_isSameMenuBase(menuBarItem)) {
			var selectedMenuItem=this._selectedMenuItem;
			if (selectedMenuItem) {
				selectedMenuItem._closePopup(selectedMenuItem);
			}
//			alert("Diff !");
			
		} else {
			// On recherche le sous-menu ouvert ...
			for(;;) {
				var pi=parent._selectedMenuItem;
				if (!pi || !pi._popupOpened) {
					break;
				}
				
				parent=pi;
			}			
		}
		
		menuBarItem._openPopup(menuBarItem, false);
		
		// En verifie bien que nous sommes sur le bon menuBarItem !
		this._a_focusMenuItem(menuBarItem);
		
		var menuItems=parent._items;
		if (!menuItems || menuItems.length<1) {
			return;
		}

		var menuItem=undefined;
		if (parent._selectedMenuItem) {
			var i=0;
			for(;i<menuItems.length;i++) {
				var mi=menuItems[i];
				
				if (!mi._over) {
					continue;
				}
				
				i++;
				break;
			}

			// Recherche le suivant mais on évite les séparateurs !
			for(;i<menuItems.length;i++) {
				var m=menuItems[i];
				if (m._separator || m._visible===false) {
					continue;
				}
				
				menuItem=m;
				break;
			}
			
		}

		if (!menuItem) {
			// Toujours pas ! On prend le premier qui vient !
			
			for(var i=0;i<menuItems.length;i++) {
				var m=menuItems[i];
				if (m._separator || m._visible===false) {
					continue;
				}
				
				menuItem=m;
				break;
			}
		}
		
		if (menuItem) {
			this._menuItem_over(menuItem, false);
		}		
	},
	_previousMenuItem: function(menuBarItem, evt) {
		var parent=menuBarItem;

		if (!this._a_isSameMenuBase(menuBarItem)) {
			if (this._selectedMenuItem) {
				this._selectedMenuItem._closePopup(this._selectedMenuItem);
			}

		} else {
			
			// Par defaut le parent est le menuBarItem
			for(;;) {
				var pi=parent._selectedMenuItem;
				if (!pi || !pi._popupOpened) {
					break;
				}			
				parent=pi;
			}			
	
			
			if (!parent._selectedMenuItem) {
				return;
			}
		}
	
		// En verifie bien que nous sommes sur le bon menuBarItem !
		this._a_focusMenuItem(menuBarItem);
				
		var menuItems=parent._items;
		if (!menuItems || menuItems.length<1) {
			return;
		}

		var menuItem;
		if (parent._selectedMenuItem) {
			var i=0;
			for(;i<menuItems.length;i++) {
				var mi=menuItems[i];
				
				if (!mi._over) {
					continue;
				}
				
				i--;
				break;
			}
			if (i<0) {
				i=menuItems.length-1;
			}
			
			for(;i>=0;i--) {
				var m=menuItems[i];
				if (m._separator || m._visible===false) {
					continue;
				}
				
				menuItem=m;
				break;				
			}
		}
		
		if (!menuItem) {
			for(var i=menuItems.length-1;i>=0;i--) {
				var m=menuItems[i];
				if (m._separator || m._visible===false) {
					continue;
				}
				
				menuItem=m;
				break;				
			}
		}

		if (menuItem) {
			this._menuItem_over(menuItem, false);		
		}
	},
	_nextMenuItemLevel: function(menuBarItem, evt) {
		if (!this._a_isSameMenuBase(menuBarItem)) {
			var smi=this._selectedMenuItem;
			if (smi) {
				smi._closePopup(smi);
			}
			return true;
		}
		
		if (menuBarItem._popupOpened) {
			// Par defaut le parent est le menuBarItem
			var parent=menuBarItem;
			for(;;) {
				var pi=parent._selectedMenuItem;
				if (!pi || !pi._popupOpened) {
					break;
				}
				parent=pi;
			}

			var selected=parent._selectedMenuItem;

			if (selected && selected._menuPopup && !this.f_isItemDisabled(selected)) {
				// On ouvre le popup !
				
				selected._openPopup(selected, true);
				
				return;
			}
		}
		// Passe au popup suivant !
		var menuBarItems=this._items;
		
		var mbi;
		for(var i=0;i<menuBarItems.length;i++) {
			var mi=menuBarItems[i];
		
			if (mi!=menuBarItem) {
				continue;
			}
			
			for(var j=0;j<menuBarItems.length;j++) {
				i++;
				if (i==menuBarItems.length) {
					i=0;
				}
				
				if (this.f_isItemDisabled(menuBarItems[i])) {
					continue;
				}
				
				mbi=menuBarItems[i];
				break;
			}
			
			break;
		}
		
		if (!mbi) {
			return;
		}

		var smi=this._selectedMenuItem;
		if (smi) {
			smi._closePopup(smi);
		}

		this._a_focusMenuItem(mbi);

		mbi._openPopup(mbi, true);
	},
	_previousMenuItemLevel: function(menuBarItem, evt) {
		if (!this._a_isSameMenuBase(menuBarItem)) {
			if (this._selectedMenuItem) {
				this._selectedMenuItem._closePopup(this._selectedMenuItem);
			}
			return true;
		}
		
		if (menuBarItem._popupOpened) {
			// Par defaut le parent est le menuBarItem
			var parent=menuBarItem;
			for(;;) {
				var pi=parent._selectedMenuItem;
				if (!pi || !pi._popupOpened) {
					break;
				}
				parent=pi;
			}

			if (parent!=menuBarItem) {
				// On ferme le popup !
					
				parent._closePopup(parent);
			
				return;
			}
		}
		
		var mbi;
		for(var i=0;i<this._items.length;i++) {
			var mi=this._items[i];
			
			if (mi!=menuBarItem) {
				continue;
			}
			
			for(var j=0;j<this._items.length;j++) {
				i--;
				if (i<0) {
					i=this._items.length-1;
				}
				
				var mip=this._items[i];
				if (this.f_isItemDisabled(mip)) {
					continue;
				}
				
				mbi=mip;
				break;
			}
			break;
		}
		
		if (this._selectedMenuItem) {
			this._selectedMenuItem._closePopup(this._selectedMenuItem);
		}
		
		if (!mbi) {
			return;
		}

		this._a_focusMenuItem(mbi);

		mbi._openPopup(mbi, true);
	},
	_keySelectMenuItem: function(menuBarItem, jsEvent) {
			
		if (!this._a_isSameMenuBase(menuBarItem)) {
			if (this._selectedMenuItem) {
				this._selectedMenuItem._closePopup(this._selectedMenuItem);
			}
			return true;
		}
		if (this.f_isItemDisabled(menuBarItem)) {
			return true;
		}

		if (this.f_isReadOnly()) {
			return true;
		}

// Deja selectionné !
//		this._selectedMenuItem=menuBarItem;
//		this._a_updateItemStyle(menuBarItem);		

		if (!menuBarItem._menuPopup) {
			var value=menuBarItem._value;
			this._performItemSelect(menuBarItem, value, jsEvent);

			return;			
		}
			
		if (!menuBarItem._selectedMenuItem) {
			this._nextMenuItem(menuBarItem, jsEvent);		
			return;
		}
	
		var parent=menuBarItem;
		for(;;) {
			var pi=parent._selectedMenuItem;
			if (!pi || !pi._popupOpened) {
				break;
			}
			
			parent=pi;
		}

		var item=parent._selectedMenuItem;
		
		if (this.f_isItemDisabled(item)) {
			return;
		}
		
		if (item._menuPopup) {
			this._nextMenuItemLevel(menuBarItem, jsEvent);
			return;
		}
		
		this._a_closeMenu(menuBarItem, jsEvent);

		var value=item._value;
		this._performItemSelect(item, value, jsEvent);
	},
	_keyCloseMenuItem: function(menuBarItem, evt) {	
		this._a_closeMenu(menuBarItem, evt, true);
		
		return true;
	},
	_menuItem_over: function(menuItem, open, autoSelect) {
		var parent=menuItem._parentItem;			

		menuItem._over=true;
		
		var oldMenuItem=parent._selectedMenuItem;
		if (oldMenuItem && oldMenuItem!=menuItem) {
			oldMenuItem._over=false
			
			// Eventuellement on ferme le popup !
			oldMenuItem._closePopup(oldMenuItem);

			parent._selectedMenuItem=undefined;
			this._a_updateItemStyle(oldMenuItem);
		}
				
		parent._selectedMenuItem=menuItem;

/* On accepte les disabled over !
		if (open && menuItem._disabled ) {
			return;
		}
	*/	
		this._a_updateItemStyle(menuItem);

		if (menuItem._disabled) {
			return;
		}
		
		if (menuItem._menuPopup && open) {
			fa_menuCore._MenuItem_openPopup(menuItem, autoSelect);
		}
	},
	_menuItem_out: function(menuItem) {
		if (!menuItem._over) {
			return;
		}
		
		menuItem._over=false;
	
		this._a_updateItemStyle(menuItem);
	},
	_menuItem_select: function(menuItem, jsEvent) {
	
		if (menuItem._menuPopup) {	
			return false;
		}

		// Si le composant est disabled
		if (menuItem._disabled) {
			return false;
		}
				
		this._a_closeMenu(menuItem, jsEvent);
					
		if (this.f_isReadOnly()) {
			return;
		}
			
		var value=menuItem._value;
		
		this._performItemSelect(menuItem, value, jsEvent);
	},
	_a_updateItemStyle: function(item) {	
	
		if (item._separator) {
			return;
		}
	
		var className=item._menuClassName;
		var imageURL=item._imageURL;
		var itemStyle=item.style;
		
		if (itemStyle) {
			if (item._visible===false) {
				if (itemStyle.display!="none") {
					itemStyle.display="none";
				}
			} else {
				if (itemStyle.display=="none") {
					itemStyle.display="block";
				}
			}
		}
				
		if (item._disabled) {
			className+="_disabled";
			
			if (item._over) {
				className+="_hover";			
			}
			
			var disabledImageURL=item._disabledImageURL;
			if (disabledImageURL) {
				imageURL=disabledImageURL;
			}
	
		} else if (item._menuPopup) {
			if (item._popupOpened || item._over) {
				className+="_selected";
	
			} else {
				className+="_popup";
			}

			var expandedImageURL=item._expandedImageURL;
			if (expandedImageURL) {
				imageURL=expandedImageURL;
			}
	
		} else if (item._over) {
			className+="_hover";
			
			var hoverImageURL=item._hoverImageURL;
			if (hoverImageURL) {
				imageURL=hoverImageURL;
			}
		}
		
		if (item.className!=className) {
			item.className=className;	
			if (item._rlink) {
				item._rlink.className=className;
			}
		}
				
		var icon=item._icon;
		if (icon) {
			var iconClassName=icon._menuClassName;
			
			if (!imageURL) {
				if (item._checked) {
					var style=item._style;
					
					if (style==fa_menuCore._AS_CHECK_BOX) {
						iconClassName=icon._menuClassName+"_check";

					} else if (style==fa_menuCore._AS_RADIO_BUTTON) {
						iconClassName=icon._menuClassName+"_radio";
					}
				}

				if (item._over) {
					iconClassName+="_hover";
				}
			}
			
			imageURL=(typeof(imageURL)=="string")?"url('"+imageURL+"')":"";
			
			if (icon.style.backgroundImage!=imageURL) {
				icon.style.backgroundImage=imageURL;
				if (item._ricon) {
					item._ricon.style.backgroundImage=imageURL;
				}
			}
			if (icon.className!=iconClassName) {
				icon.className=iconClassName;
				if (item._ricon) {
					item._ricon.className=iconClassName;
				}				
			}
		}
	},
	/**
	 * Returns the label of the item.
	 * 
	 * @method public
	 * @param any item The value of the item, or the item object.
	 * @return string The label.
	 */
	f_getItemLabel: function(item) {
		if (typeof(item)=="string") {
			item=this.f_getItemByValue(item);
		}
		
		f_core.Assert(item, "f_getItemLabel: Item parameter must be defined !");

		return item._label;
	},
	/**
	 * Set the label of the item.
	 *
	 * @method public
	 * @param any item The value of the item, or the item object.
	 * @param string label Label of the item.
	 * @return void
	 */
	f_setItemLabel: function(item, label) {
		f_core.Assert(typeof(label)=="string", "f_setItemLabel: Label parameter is not a string !");
		if (typeof(item)=="string") {
			item=this.f_getItemByValue(item);
		}
		
		f_core.Assert(item, "f_setItemLabel: Item parameter must be defined !");
		
		item._label=label;
		item._changes|=fa_menuCore._LABEL_CHANGED;
		this._menuItemsChanged=true;
	},
	/**
	 * Returns a list of items
	 * 
	 * @method public
	 * @param Object item
	 * @return Object
	 */
	f_getCheckedItemInGroup: function(item) {
		function search(i) {
			return this.f_isItemChecked(i)?i:null;
		}

		return this._findIntoGroup(this.f_getItemGroupName(item), search);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return Object[]
	 */
	f_listAllInGroup: function(item) {
		return this._listGroup(this.f_getItemGroupName(item));
	},
	/**
	 * Remove all items
	 *
	 * @method public
	 * @param optional Object menuItem
	 * @return void
	 */
	f_removeAllItems: function(menuItem) {
		var items=menuItem._items;
		if (!items) {
			return;
		}
		
		var table=menuItem._menuPopup;
		if (table) {
			for(var i=0;i<items.length;i++) {
				table.removeChild(items[i]);
			}
		}
	
		this._a_destroyItems(items);
		menuItem._items=new Array;
		
		this._menuItemsChanged=true;
	},
	_performItemSelect: function(item, value, jsEvent) {
		this._popupOpened=undefined;
		
		if (item._style==fa_menuCore._AS_CHECK_BOX) {
			var state=this.f_isItemChecked(item);
			
			this.f_setItemChecked(item, !state);
			
			// Dans ce cas un event CHECK est envoyé !
			return;
		}
		
		if (item._style==fa_menuCore._AS_RADIO_BUTTON) {
			this.f_setItemChecked(item, true);

			// Dans ce cas un event CHECK est envoyé !
			return;
		}

		var selectionProvider=this._a_getSelectionProvider();

		this.f_fireEvent(f_event.SELECTION, jsEvent, item, value, selectionProvider);
	},
	_a_destroyItems: function(items) {
		for(var i=0;i<items.length;i++) {
			fa_menuCore._DestroyMenuItem(items[i]);
		}
	},	
	_a_getRadioScope: function() {
		return this;
	},

	/**
	 * @method abstract
	 * @return void
	 */
	_a_focusMenuItem:  f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_closeMenu:  f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_clickOutside: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_getSelectionProvider:  f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_keySearchAccessKey: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_isSameMenuPoup: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_tabKeySelection: f_class.ABSTRACT
}

var fa_menuCore=new f_aspect("fa_menuCore", __static, __prototype, fa_groupName, fa_items);
