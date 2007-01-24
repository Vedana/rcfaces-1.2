/*
 * $Id$
 */

/**
 * Aspect Menu
 *
 * @aspect public fa_menuCore extends fa_groupName, fa_items
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {

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
	 * @field hidden static final number
	 */
	_AS_SEPARATOR: 8,
	
	/** 
	 * @field private static final number
	 */
	_ITEM_IMAGE_WIDTH: 18,
	
	/** 
	 * @field private static final number
	 */
	_ITEM_IMAGE_HEIGHT: 18,
	
	/** 
	 * @field private static final String
	 */
	_BLANK_IMAGE_URL: "/menu/blank.gif",
	
	/** 
	 * @field private static number
	 */
	_ItemIds: 0,
	
	/**
	 * @method private static 
	 */
	_MenuItem_mouseOver: function(evt) {
		var item=this._item;
		var menu=item._menu;
		f_core.Debug(fa_menuCore, "_MenuItem_mouseOver: menu="+menu+" item="+item);
		
		if (menu.f_getEventLocked(false, f_event.POPUP_LOCK)) {
			return false;
		}

		if (!evt) {
			evt=f_core.GetEvent(this);
		}
		
		menu.f_menuItem_over(item, true, evt);

		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_MenuItem_mouseOut: function(evt) {
// Pas bloqué !			if (f_core.GetEventLocked(false)) return false;

		var item=this._item;
		var menu=item._menu;

		f_core.Debug(fa_menuCore, "_MenuItem_mouseOut: menu="+menu+" item="+item);

		menu._menuItem_out(item);
	
		if (!evt) {
			evt=f_core.GetEvent(this);
		}
		
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static 
	 * @document this.ownerDocument
	 */
	_MenuItem_mouseDown: function(evt) {
		var item=this._item;
		var menu=item._menu;
		if (menu.f_getEventLocked(true, f_event.POPUP_LOCK)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetEvent(this);
		}
	
		menu.f_menuItem_select(item, evt);
			
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
	 * @method hidden static final hidden
	 */
	OnKeyDown: function(menu, menuItem, evt) {
		var cancel;

		f_core.Assert(evt, "Event is null !");
		var code=evt.keyCode;

		switch(code) {
		case f_key.VK_CONTEXTMENU:
			cancel=true;
			break;

		case f_key.VK_DOWN: // FLECHE VERS LE BAS
			menu._nextMenuItem(menuItem, evt);
			cancel=true;
			break;
			
		case f_key.VK_UP: // FLECHE VERS LE HAUT
			menu._previousMenuItem(menuItem, evt);
			cancel=true;
			break;
			
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
			menu._nextMenuItemLevel(menuItem, evt);
			cancel=true;
			break;
			
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
			menu._previousMenuItemLevel(menuItem, evt);
			cancel=true;
			break;

		case f_key.VK_HOME: // HOME
// @TODO		menu._nextMenuItemLevel(menuItem, evt);
			cancel=true;
			break;
			
		case f_key.VK_END: // END
// @TODO			menu._previousMenuItemLevel(menuItem, evt);
			cancel=true;
			break;


		case f_key.VK_TAB:
			// Rien ....
			if (!menu.fa_tabKeySelection || !menu.fa_tabKeySelection()) {
				break;
			}
			
			// C'est un RETURN alors !
	
		case f_key.VK_RETURN:
	 	case f_key.VK_ENTER:
			menu._keySelectMenuItem(menuItem, evt);
			cancel=true;
			break;

		case f_key.VK_ESCAPE:
			menu._keyCloseMenuItem(menuItem, evt);
			cancel=true;
			break;
		
		default:
			f_core.Debug(fa_menuCore, "OnKeyDown: menu="+menu+" menuItem="+menuItem);
			
			if (menu.f_uiIsPopupOpened(menuItem)) {
				cancel=true;
				
				if (!evt.altKey) {
					menu.fa_keySearchAccessKey(menuItem, code, evt);
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
	}
}

var __prototype = {
	fa_menuCore: function() {

		this._uiMenuItems=new Object;
		this._uiMenuPopups=new Object;
		this._menu=this;

		if (this.tagName) {
			var itemImageWidth=f_core.GetAttribute(this, "v:itemImageWidth");
			if (itemImageWidth) {
				this._itemImageWidth=parseInt(itemImageWidth);
			}
	
			var itemImageHeight=f_core.GetAttribute(this, "v:itemImageHeight");
			if (itemImageHeight) {
				this._itemImageHeight=parseInt(itemImageHeight);
			}
			
			if (itemImageWidth || itemImageHeight) {
				f_core.Debug(fa_menuCore, "Set item image width/height by tag attributes width="+this._itemImageWidth+" height="+this._itemImageHeight+".");
			}
		}
		
		this._blankMenuImageURL=f_env.GetStyleSheetBase()+fa_menuCore._BLANK_IMAGE_URL;
		
		f_imageRepository.PrepareImage(this._blankMenuImageURL);
	},
	f_finalize:  function() {
		this._menu=undefined; // fa_menuCore
		
		// this._blankMenuImageURL=undefined; // string
		// this._itemImageWidth=undefined; // number
		// this._itemImageHeight=undefined; // number

		this._uiMenuItems=undefined; // Map<Object, HTMLElement>
		this._uiMenuPopups=undefined; // Map<Object, HTMLElement>
	},
	/**
	 * @method public
	 * @param Object parentItem Parent object or <code>null</code>
	 * @param String id Identifier of the item. (can be <code>null</code>)
	 * @param String groupName Group name of the item.
	 * @param String label Label of the item.
	 * @param optional String value
	 * @param optional boolean checked
	 * @param optional String accessKey
	 * @param optional String tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @param optional String acceleratorKey
	 * @return Object
	 */
	f_appendRadioItem: function(parentItem, id, groupName, label, value, checked, accessKey, tooltip, disabled, visible, acceleratorKey) {
		f_core.Assert(typeof(groupName)=="string", "groupName parameter is invalid. ("+groupName+")");

		var item=this.f_appendItem(parentItem, id, label, value, accessKey, tooltip, disabled, visible, acceleratorKey);
		
		item._style=fa_menuCore._AS_RADIO_BUTTON;
		if (groupName) {
			this.f_setItemGroupName(item, groupName);
		}		
		if (checked) {
			this.f_setItemChecked(item, checked);
		}
		
		return item;
	},
	/**
	 * @method public
	 * @param Object parentItem Parent object or <code>null</code>
	 * @param String id Identifier of the item. (can be <code>null</code>)
	 * @param String label Label of the item.
	 * @param optional String value
	 * @param optional boolean checked
	 * @param optional String accessKey
	 * @param optional String tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @param optional String acceleratorKey
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
	 * @param String id Identifier of the item. (can be <code>null</code>)
	 * @param String label Label of the item.
	 * @param optional String value Value of the item.
	 * @param optional String accessKey Access key of the item.
	 * @param optional String tooltip
	 * @param optional boolean disabled
	 * @param optional boolean visible
	 * @param optional String acceleratorKey
	 * @return Object
	 */
	f_appendItem: function(parentItem, id, label, value, accessKey, tooltip, disabled, visible, acceleratorKey) {
		f_core.Assert(parentItem===null || typeof(parentItem)=="object", "parentItem parameter is invalid. ("+parentItem+")");
		f_core.Assert(id===null || typeof(id)=="string", "id parameter is invalid. ("+id+")");
		f_core.Assert(typeof(label)=="string", "label parameter is invalid. ("+label+")");
		
		if (!parentItem) {
			parentItem=this;
		}
		
		var menuItem=new Object;
		menuItem._style=fa_menuCore._AS_PUSH_BUTTON;
		menuItem._accessKey=accessKey;
		menuItem._value=value;
		menuItem._acceleratorKey=acceleratorKey;
		menuItem._parentItem=parentItem;
		menuItem._menu=this;
		menuItem._label=label;
		menuItem._acceleratorKey=acceleratorKey;
		menuItem.toString=function() {
			return "[Item "+id+"]";
		}
		
		if (!id) {
			id=this.id+"::"+(fa_menuCore._ItemIds++);
		}
		menuItem._id=id;

		this.f_addItem(parentItem, menuItem);
			
		if (disabled) {
			this.f_setItemDisabled(menuItem, disabled);
		}
		if (tooltip) {
			this.f_setItemToolTip(menuItem, tooltip);
		}
		if (visible===false) {
			this.f_setItemVisible(menuItem, visible);
		}
		
		return menuItem;
	},
	/**
	 * @method public 
	 * @param Object parentItem Parent object or <code>null</code>
	 * @return Object
	 */
	f_appendSeparatorItem: function(parentItem) {
		if (!parentItem) {
			parentItem=this;
		}

		var item=new Object;
		item._style=fa_menuCore._AS_SEPARATOR;
		item._disabled=true;
		item._parentItem=parentItem;
		item._menu=this;
		item.toString=function() {
			return "[Separator]";
		}
		
		this.f_addItem(parentItem, item);		
		
		return item;
	},
	/**
	 * @method protected
	 */
	f_setItemImageSize: function(itemImageWidth, itemImageHeight) {
		f_core.Assert(typeof(itemImageWidth)=="number" && itemImageWidth, "fa_menuCore.f_setItemImageSize: Invalid itemImageWidth parameter ("+itemImageWidth+").");
		f_core.Assert(typeof(itemImageHeight)=="number" && itemImageHeight, "fa_menuCore.f_setItemImageSize: Invalid itemImageHeight parameter ("+itemImageHeight+").");

		this._itemImageWidth=itemImageWidth;
		this._itemImageHeight=itemImageHeight;

		f_core.Debug(fa_menuCore, "Set item image size to width="+this._itemImageWidth+" height="+this._itemImageHeight+".");
	},
	/**
	 * @method protected 
	 */
	f_createPopup: function(container, parentItem) {
		var popupObject;
		var doc;

		if (f_popup.Ie_enablePopup()) {
			// container = popup object
			
			popupObject=container;
			doc=container.document;
			container=doc.body;
			
		} else {
			doc=container.ownerDocument;		
		}
	
		var uiPopup=doc.createElement("UL");		
		uiPopup.className="f_menu_popup";

		if (!popupObject) {
			uiPopup.style.visibility="hidden";
			popupObject=uiPopup;
		}

		uiPopup._popupObject=popupObject;
	
		container.appendChild(uiPopup);
		
		this._uiMenuPopups[parentItem]=uiPopup;
		
		uiPopup.id=this.fa_getMenuScopeName(parentItem);
		uiPopup._item=parentItem;
		
		if (!this._itemImageWidth) {
			this._itemImageWidth=fa_menuCore._ITEM_IMAGE_WIDTH;
		}
		
		if (!this._itemImageHeight) {
			this._itemImageHeight=fa_menuCore._ITEM_IMAGE_HEIGHT;

			f_core.Debug(fa_menuCore, "Use default size for item image width/height, width="+this._itemImageWidth+" height="+this._itemImageHeight+".");
		}
		
		var sep=true;
		
		var items=this.f_listVisibleItemChildren(parentItem);
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			if (sep && item._style==fa_menuCore._AS_SEPARATOR) {
				continue;
			}
			
			var uiItem=doc.createElement("LI");
			var itemId=item._id;
			if (itemId) {
				uiItem.id=itemId;
			}
			uiPopup.appendChild(uiItem);
			
			this._uiMenuItems[item]=uiItem;
			uiItem._item=item;
			sep=false;
			
			switch(item._style) {				
			case fa_menuCore._AS_CHECK_BUTTON:
				uiItem.role="menuitemcheckbox";
				break;
			
			case fa_menuCore._AS_RADIO_BUTTON:
				uiItem.role="menuitemradio";
				break;
			
			case fa_menuCore._AS_SEPARATOR:
				uiItem.role="menuitemradio";
				uiItem.className="f_menu_item_sep";
				uiItem.onmousedown=fa_menuCore._SeparatorItem_click;
				sep=true;
							
			default:
				uiItem.role="menuitem";
				break;
			}
			
			if (sep) {
				continue;
			}

			uiItem.className="f_menu_item";
			
			var image=doc.createElement("IMG");
			image.align="middle";
			image.valign="middle";
			image.border=0;
			image.width=this._itemImageWidth;
			image.height=this._itemImageHeight;
			image.src=this._blankMenuImageURL;
			image.className="f_menu_item_image";
			uiItem._icon=image;
			
			uiItem.appendChild(image);
			
			var div=doc.createElement("LABEL");
			div.className="f_menu_item_text";
			
			var accessKey=item._accessKey;
			if (accessKey) {
				uiItem._accessKey=accessKey;
				uiItem.accessKey=accessKey;
			}
	
			var label=this.f_getItemLabel(item);
			f_component.AddLabelWithAccessKey(div, label, accessKey);
			
			uiItem.appendChild(div);
	
			var acceleratorKey=this._acceleratorKey;
			if (acceleratorKey) {
				var htmlAcceleratorKey=f_core.EncodeHtml(acceleratorKey);
				var accelV=doc.createElement("LABEL");
				accelV.className="f_menu_item_accelV";
				accelV.innerHTML=htmlAcceleratorKey;
				uiItem.appendChild(accelV);
		
				var accel=doc.createElement("LABEL");
				accel.className="f_menu_item_accel";
				accel.innerHTML=htmlAcceleratorKey;
				uiItem.appendChild(accel);
			}
			
			uiItem.onmouseover=fa_menuCore._MenuItem_mouseOver;
			uiItem.onmouseout=fa_menuCore._MenuItem_mouseOut;
			uiItem.onmousedown=fa_menuCore._MenuItem_mouseDown;
			uiItem.onclick=fa_menuCore._MenuItem_click;
			
			this.f_uiUpdateItemStyle(item, uiItem);
		}
		
		return uiPopup;
	},
	/**
	 * @method protected final
	 */
	f_getUIItem: function(menuItem) {
		f_core.Assert(typeof(menuItem)=="object" && (!menuItem.nodeType || menuItem==this) && menuItem._menu, "fa_menuCore.f_getUIItem: Invalid menuItem parameter ("+menuItem+")");

		var mi=this._uiMenuItems[menuItem];
		f_core.Assert(mi, "fa_menuCore.f_getUIItem: No uiMenuItem for '"+menuItem+"'.");
		return mi;
	},
	/**
	 * @method protected final
	 */
	f_getUIPopup: function(menuItem) {
		f_core.Assert(typeof(menuItem)=="object" && (!menuItem.nodeType || menuItem==this) && menuItem._menu, "fa_menuCore.f_getUIPopup: Invalid menuItem parameter ("+menuItem+")");

		var mi=this._uiMenuPopups[menuItem];
			
//		f_core.Debug(fa_menuCore, "fa_menuCore.f_getUIPopup: For popup '"+menuItem+"' => "+mi);
		return mi;
	},
	/**
	 * @method protected final
	 * @return boolean
	 */
	f_uiIsPopupOpened: function(menuItem) {
		f_core.Assert(typeof(menuItem)=="object" && !menuItem.nodeType && menuItem._menu, "fa_menuCore.f_uiIsPopupOpened: Invalid menuItem parameter ("+menuItem+")");

		return this.f_getUIPopup(menuItem)!=null;
	},	 
	/**
	 * @method protected 
	 * @return boolean
	 */
	f_uiGetSelectedItem: function(menuItem) {
		f_core.Assert(typeof(menuItem)=="object" && !menuItem.nodeType && menuItem._menu, "fa_menuCore.f_uiGetSelectedItem: Invalid menuItem parameter ("+menuItem+")");

		var popup=this.f_getUIPopup(menuItem);
		if (!popup) {
			return null;
		}
		
		return popup._selectedMenuItem;
	},
	/**
	 * @method protected final
	 * @return void 
	 */	 
	f_uiSelectItem: function(menuItemParent, menuItem) {
		f_core.Assert(typeof(menuItemParent)=="object" && (!menuItemParent.nodeType || menuItemParent==this ) && menuItemParent._menu, "fa_menuCore.f_uiSelectItem: Invalid menuItemParent parameter ("+menuItemParent+")");
		f_core.Assert(!menuItem || (typeof(menuItem)=="object" && !menuItem.nodeType && menuItem._menu), "fa_menuCore.f_uiSelectItem: Invalid menuItem parameter ("+menuItem+")");
		f_core.Assert(menuItemParent!=menuItem, "Invalid menuItem, same as parent. (parent="+menuItemParent+")");
		 
		var popup=this.f_getUIPopup(menuItemParent, true);
		f_core.Assert(popup, "fa_menuCore.f_uiSelectItem: Invalid popup for item="+menuItemParent);

		popup._selectedMenuItem=menuItem;
	},
	/**
	 * @method protected final
	 * @return boolean
	 */
	f_uiIsItemOver: function(menuItem) {
		f_core.Assert(typeof(menuItem)=="object" && !menuItem.nodeType && menuItem._menu, "fa_menuCore.f_uiIsItemOver: Invalid menuItem parameter ("+menuItem+")");

		return this.f_getUIItem(menuItem)._over;
	},
	/**
	 * @method protected final
	 * @return boolean
	 */	 
	f_uiSetItemOver: function(menuItem, over) {
		f_core.Assert(typeof(menuItem)=="object" && !menuItem.nodeType && menuItem._menu, "fa_menuCore.f_uiSetItemOver: Invalid menuItem parameter ("+menuItem+")");
		f_core.Assert(over===undefined || typeof(over)=="boolean", "fa_menuCore.f_uiSetItemOver: Invalid over parameter '"+over+"'.");
		
		this.f_getUIItem(menuItem)._over=over;
	},
	/**
	 * @method private
	 * @param Object menuItem
	 * @param Event evt
	 * @return void
	 */
	_nextMenuItem: function(menuItem, evt) {
	
		// Par defaut le parent est le menuBarItem
		var parent=menuItem;
		
		if (!this.fa_isSameMenuBase(menuItem)) {
			var selectedMenuItem=this.f_uiGetSelectedItem(this);
			if (selectedMenuItem) {
				this._closeUIPopup(selectedMenuItem);
			}
			
		} else {
			// On recherche le sous-menu (enfant) ouvert ...
			for(;;) {
				var pi=this.f_uiGetSelectedItem(parent);
				if (!pi || !this.f_uiIsPopupOpened(pi)) {
					break;
				}
				
				parent=pi;
			}			
		}
		
		this.f_openUIPopup(menuItem, evt);
		
		// En verifie bien que nous sommes sur le bon menuBarItem !
		this.fa_focusMenuItem(menuItem);
		
		var menuItems=this.f_listItemChildren(parent);
		if (!menuItems || !menuItems.length) {
			return;
		}

		var menuItem=undefined;
		if (this.f_uiGetSelectedItem(parent)) {
			var i=0;
			// On recherche le premier qui est over
			for(;i<menuItems.length;i++) {
				var mi=menuItems[i];
				
				if (!this.f_uiIsItemOver(mi)) {
					continue;
				}
				
				i++;
				break;
			}

			// Recherche le suivant mais on évite les séparateurs !
			for(;i<menuItems.length;i++) {
				var m=menuItems[i];
				if (m._style==fa_menuCore._AS_SEPARATOR || !this.f_isItemVisible(m)) {
					// C'est un séparateur ou il n'est pas visible
					continue;
				}
								
				menuItem=m;
				break;
			}
			
		}

		if (!menuItem) {
			// Toujours pas ! On prend le premier de la liste !
			
			for(var i=0;i<menuItems.length;i++) {
				var m=menuItems[i];
				if (m._style==fa_menuCore._AS_SEPARATOR || !this.f_isItemVisible(m)) {
					continue;
				}
				
				menuItem=m;
				break;
			}
		}
		
		if (menuItem) {
			this.f_menuItem_over(menuItem, false, evt);
		}		
	},
	/**
	 * @method private
	 * @param Object menuItem
	 * @param Event evt
	 * @return void
	 */
	_previousMenuItem: function(menuItem, evt) {
		var parent=menuItem;

		if (!this.fa_isSameMenuBase(menuItem)) {
			var selectedMenuItem=this.f_uiGetSelectedItem(this);
			if (selectedMenuItem) {
				this._closeUIPopup(selectedMenuItem);
			}

		} else {
			
			// Par defaut le parent est le menuItem
			for(;;) {
				var pi=this.f_uiGetSelectedItem(parent);
				
				if (!pi || !this.f_uiIsPopupOpened(pi)) {
					break;
				}			
				parent=pi;
			}			
	
			
			if (!this.f_uiGetSelectedItem(parent)) {
				return;
			}
		}
	
		// En verifie bien que nous sommes sur le bon menuItem !
		this.fa_focusMenuItem(menuItem);
				
		var menuItems=this.f_listItemChildren(parent);
		if (!menuItems || !menuItems.length) {
			return;
		}

		var menuItem;
		if (this.f_uiGetSelectedItem(parent)) {
			var i=0;
			
			// On recherche l'item OVER
			for(;i<menuItems.length;i++) {
				var mi=menuItems[i];
				
				if (!this.f_uiIsItemOver(mi)) {
					continue;
				}
				
				break;
			}
			
			if (i<1) {
				// Pas trouvé !
				i=menuItems.length;
			}
			
			// On part du bas en remontant ...
			for(i--;i>=0;i--) {
				var m=menuItems[i];
				if (m._style==fa_menuCore._AS_SEPARATOR || !this.f_isItemVisible(m)) {
					continue;
				}
				
				menuItem=m;
				break;				
			}
		}
		
		if (!menuItem) {
			for(var i=menuItems.length-1;i>=0;i--) {
				var m=menuItems[i];
				if (m._style==fa_menuCore._AS_SEPARATOR || !this.f_isItemVisible(m)) {
					continue;
				}
				
				menuItem=m;
				break;				
			}
		}

		if (menuItem) {
			this.f_menuItem_over(menuItem, false, evt);		
		}
	},
	/**
	 * @method private
	 * @param Object menuItem
	 * @param Event evt
	 * @return void
	 */
	_nextMenuItemLevel: function(menuItem, evt) {

		if (!this.fa_isSameMenuBase(menuItem)) {
			var smi=this.f_uiGetSelectedItem(this);
			if (smi) {
				this._closeUIPopup(smi);
			}
			return;
		}
		
		if (this.f_uiIsPopupOpened(menuItem)) {
			// Recherche le popup enfant le plus ouvert
			
			var parent=menuItem;
			for(;;) {
				var pi=this.f_uiGetSelectedItem(parent);
				if (!pi || !this.f_uiIsPopupOpened(pi)) {
					break;
				}
				parent=pi;
			}

			var selectedItem=this.f_uiGetSelectedItem(parent);

			if (selectedItem 
					&& !this.f_isItemDisabled(selectedItem) 
					&& this.f_hasVisibleItemChildren(selectedItem)) {
				// On ouvre le popup !
				
				this.f_openUIPopup(selectedItem, evt, true);
				
				return;
			}
		}
		
		// Passe au popup suivant !
		var menuItems=this.f_listItemChildren(this);
		
		var mbi;
		for(var i=0;i<menuItems.length;i++) {
			var mi=menuItems[i];
		
			// On recherche notre menu !
			if (mi!=menuItem) {
				continue;
			}
			
			// On en cherche un autre en avancant
			for(var j=0;j<menuItems.length;j++) {
				i++;
				if (i==menuItems.length) {
					i=0;
				}
				
				if (this.f_isItemDisabled(menuItems[i])) {
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

		var smi=this.f_uiGetSelectedItem(this);
		if (smi) {
			this._closeUIPopup(smi);
		}

		this.fa_focusMenuItem(mbi);

		this.f_openUIPopup(mbi, evt, true);
	},
	/**
	 * @method private
	 * @param Object menuItem
	 * @param Event evt
	 * @return void
	 */
	_previousMenuItemLevel: function(menuItem, evt) {
		if (!this.fa_isSameMenuBase(menuItem)) {
			var selectedMenuItem=this.f_uiGetSelectedItem(this);
		
			if (selectedMenuItem) {
				this._closeUIPopup(selectedMenuItem);
			}
			return;
		}
		
		if (this.f_uiIsPopupOpened(menuItem)) {
			// Recherche l'enfant ouvert le plus profond
			
			var parent=menuItem;
			for(;;) {
				var pi=this.f_uiGetSelectedItem(parent);
				if (!pi || !this.f_uiIsPopupOpened(pi)) {
					break;
				}
				parent=pi;
			}

			if (parent!=menuItem) {
				// On ferme le popup !
					
				this._closeUIPopup(parent);
			
				return;
			}
		}

		// Passe au popup precedent !
		var menuItems=this.f_listItemChildren(this);
		
		var mbi;
		for(var i=0;i<menuItems.length;i++) {
			var mi=menuItems[i];
			
			// On recherche notre menu
			if (mi!=menuItem) {
				continue;
			}
			
			// On en recherche un autre en reculant
			
			for(var j=0;j<menuItems.length;j++) {
				i--;
				if (i<0) {
					i=menuItems.length-1;
				}
				
				var mip=menuItems[i];
				if (this.f_isItemDisabled(mip)) {
					continue;
				}
				
				mbi=mip;
				break;
			}
			break;
		}
		
		var smi=this.f_uiGetSelectedItem(this);
		if (smi) {
			this._closeUIPopup(smi);
		}
		
		if (!mbi) {
			return;
		}

		this.fa_focusMenuItem(mbi);

		this.f_openUIPopup(mbi, evt, true);
	},
	/**
	 * Selection de l'item par une touche !  (item root)
	 * 
	 * @method private
	 * @param Object menuItem
	 * @param Event jsEvent
	 * @return void
	 */
	_keySelectMenuItem: function(menuItem, jsEvent) {
			
		if (!this.fa_isSameMenuBase(menuItem)) {
			var selectedMenuItem=this.f_uiGetSelectedItem(this);
		
			if (selectedMenuItem) {
				this._closeUIPopup(selectedMenuItem);
			}
			return true;
		}

		// Le menu est en mode ReadOnly ?
		if (this.f_isReadOnly()) {
			return true;
		}
		
		// Notre menuItem root est-il desactivé ?
		if (this.f_isItemDisabled(menuItem)) {
			return true;
		}

		// a t-il  un popup ?
		// (cas d'un menubar)
		if (!this.f_hasVisibleItemChildren(menuItem)) {
			// Non ...
			
	
			// Appel de la callback de selection
			var value=this.f_getItemValue(menuItem);
			this.f_performItemSelect(menuItem, value, jsEvent);
			return;			
		}
			
		// Aucune selection ... on passe au menu suivant
		// (Cas d'un menubar)
		if (!this.f_uiGetSelectedItem(menuItem)) {
			// Il n'est pas selectionné ?
			this._nextMenuItem(menuItem, jsEvent);		
			return;
		}
	
		// Recherche le popup enfant le plus profond
		var parent=menuItem;
		for(;;) {
			var pi=this.f_uiGetSelectedItem(parent);
			if (!pi || !this.f_uiIsPopupOpened(pi)) {
				break;
			}
			
			parent=pi;
		}

		var item=this.f_uiGetSelectedItem(parent);
		
		if (this.f_isItemDisabled(item)) {
			return;
		}
		
		// Notre vrai item selectionné a t-il des enfants ?
		if (this.f_hasVisibleItemChildren(item)) {
			this._nextMenuItemLevel(menuItem, jsEvent);
			return;
		}
		
		// pas d'enfants		
		this._closeUIPopup(menuItem);

		// Appel de la callback de selection
		var value=this.f_getItemValue(item);
		this.f_performItemSelect(item, value, jsEvent);
	},
	/**
	 * Fermeture du menu par une touche !
	 * 
	 * @method private
	 * @param Object menuItem
	 * @param Event evt
	 * @return void
	 */
	_keyCloseMenuItem: function(menuItem, evt) {	
		this._closeUIPopup(menuItem);
	},
	/**
	 * Returns parent of item.
	 * 
	 * @method protected final
	 * @param Object menuItem
	 * @return Object its parent.
	 */
	f_getParentItem: function(item) {
		f_core.Assert(typeof(item)=="object", "Item parameter must be an object !");
		return item._parentItem;
	},
	/**
	 * Gestion du OVER d'un menuItem
	 * 
	 * @method protected final
	 * @param Object menuItem
	 * @param boolean open
	 * @param Event evt
	 * @param boolean autoSelect
	 * @return void
	 */
	f_menuItem_over: function(menuItem, open, evt, autoSelect) {
		var parent=this.f_getParentItem(menuItem);

		var parentPopup=this.f_getUIPopup(parent);
		if (!parentPopup) {
			return;
		}
		
		var oldMenuItem=this.f_uiGetSelectedItem(parent);
//		f_core.Debug(fa_menuCore, "f_menuItem_over: "+menuItem+" parent="+parent+" old="+oldMenuItem+" open="+open);
		
		// Un autre était déjà over ???
		if (oldMenuItem && oldMenuItem!=menuItem) {
			this.f_uiSetItemOver(oldMenuItem, false);
			
			// Eventuellement on ferme le popup !
			this._closeUIPopup(oldMenuItem);

			this.f_uiSelectItem(parent); // deselectionne !
			this.f_uiUpdateItemStyle(oldMenuItem);
		}
				
		this.f_uiSetItemOver(menuItem, true);
		this.f_uiSelectItem(parent, menuItem);

/* On accepte les disabled over !
		if (open && menuItem._disabled ) {
			return;
		}
	*/	
		this.f_uiUpdateItemStyle(menuItem);

		if (this.f_isItemDisabled(menuItem)) {
			return;
		}
		
		if (this.f_hasVisibleItemChildren(menuItem) && open) {
			this.f_openUIPopup(menuItem, evt, autoSelect);
		}
	},
	/**
	 * Gestion du OUT d'un menuItem
	 * 
	 * @method private
	 * @param Object menuItem
	 * @return void
	 */
	_menuItem_out: function(menuItem) {
		if (!this.f_uiIsItemOver(menuItem)) {
//			f_core.Debug(fa_menuCore, "_menuItem_out: "+menuItem+" no over !");

			return;
		}

//		f_core.Debug(fa_menuCore, "_menuItem_out: "+menuItem);
		
		this.f_uiSetItemOver(menuItem, false);
	
		this.f_uiUpdateItemStyle(menuItem);
	},
	/**
	 * Gestion du MOUSE BUTTON d'un menuItem
	 * 
	 * @method protected final
	 * @param Object menuItem
	 * @param Event jsEvent
	 * @return void
	 */
	f_menuItem_select: function(menuItem, jsEvent) {
	
		// Il a un popup ?
		if (this.f_hasVisibleItemChildren(menuItem)) {
			// On ignore
			return;
		}
		
		if (this.f_isItemDisabled(menuItem)) {
			return;
		}
				
		this._closeUIPopup(menuItem);
					
		if (this.f_isReadOnly()) {
			return;
		}
			
		var value=this.f_getItemValue(menuItem);
		
		this.f_performItemSelect(menuItem, value, jsEvent);
	},
	/**
	 * @method protected
	 */
	fa_updateItemStyle: function(item) {
		// On s'enfiche car le style est créé a chaque ouverture de popup !
	},
	
	f_uiUpdateItemStyle: function(item, uiItem) {	
		if (item._style==fa_menuCore._AS_SEPARATOR) {
			return;
		}
		
		if (!uiItem) {
			uiItem=this.f_getUIItem(item);
		}
		
	
		var disabled=this.f_isItemDisabled(item);

		// f_core.Debug(fa_menuCore, "f_uiUpdateItemStyle: item="+item+" uiItem="+uiItem.id+" over="+uiItem._over+" disabled="+disabled);

		var suffix="";
		var imageURL=item._imageURL;

		if (this.f_hasVisibleItemChildren(item)) {
			var popupOpened=this.f_uiIsPopupOpened(item);

		//	f_core.Debug(fa_menuCore, "f_uiUpdateItemStyle: popupOpened="+popupOpened+" over="+uiItem._over);
			
			if (popupOpened || uiItem._over) {
				suffix+="_selected";
	
			} else {
				suffix+="_popup";
			}

			if (disabled) {
				suffix+="_disabled";
			
				var disabledImageURL=item._disabledImageURL;
				if (disabledImageURL) {
					imageURL=disabledImageURL;
				}
			} else {
				var expandedImageURL=item._expandedImageURL;
				if (expandedImageURL) {
					imageURL=expandedImageURL;
				}
			}
				
		} else if (disabled) {
			suffix+="_disabled";
			
			if (uiItem._over) {
				suffix+="_hover";			
			}
			
			var disabledImageURL=item._disabledImageURL;
			if (disabledImageURL) {
				imageURL=disabledImageURL;
			}
	
		} else if (uiItem._over) {
			suffix+="_hover";
			
			var hoverImageURL=item._hoverImageURL;
			if (hoverImageURL) {
				imageURL=hoverImageURL;
			}

		} else if (item._checked) {
			var selectedImageURL=item._selectedImageURL;
			if (selectedImageURL) {
				imageURL=selectedImageURL;
			}
		} 
		
		var className="f_menu_item";
		if (suffix) {
			className+=" "+className+suffix;			
		}
		
		if (uiItem.className!=className) {
			uiItem.className=className;	
		}
				
		var icon=uiItem._icon;
		if (icon) {
			var iconClassName="f_menu_item_image";
			
			var suffix="";
			if (!imageURL) {
				if (item._checked) {
					var style=item._style;
					
					if (style==fa_menuCore._AS_CHECK_BOX) {
						suffix="_check";

					} else if (style==fa_menuCore._AS_RADIO_BUTTON) {
						suffix="_radio";
					}
				}

				if (uiItem._over) {
					suffix+="_hover";
				}
				
				if (suffix) {
					iconClassName+=" "+iconClassName+suffix;
				}
			}
			
			imageURL=(typeof(imageURL)=="string")?"url('"+imageURL+"')":"";
			
			var iconStyle=icon.style;
			if (iconStyle.backgroundImage!=imageURL) {
				iconStyle.backgroundImage=imageURL;
			}
			if (icon.className!=iconClassName) {
				icon.className=iconClassName;
			}
		}
	},
	/**
	 * @method protected
	 * @param Object item
	 * @param any value
	 * @param Event jsEvent
	 * @return void
	 */
	f_performItemSelect: function(item, value, jsEvent) {		

		this.f_closeAllPopups();

		switch(item._style) {
		case fa_menuCore._AS_CHECK_BOX:
			var state=this.f_isItemChecked(item);
			
			this.f_setItemChecked(item, !state);
			
			// Dans ce cas un event CHECK est envoyé !
			return;
		
		case fa_menuCore._AS_RADIO_BUTTON:
			this.f_setItemChecked(item, true);

			// Dans ce cas un event CHECK est envoyé !
			return;
		}

		var selectionProvider=this.fa_getSelectionProvider();

		this.f_fireEvent(f_event.SELECTION, jsEvent, item, value, selectionProvider);
	},
	/**
	 * Returns the label of the item.
	 * 
	 * @method public
	 * @param any item The value of the item, or the item object.
	 * @return String The label.
	 */
	f_getItemLabel: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._label;
	},
	/**
	 * Set the label of the item.
	 *
	 * @method public
	 * @param any item The value of the item, or the item object.
	 * @param String label Label of the item.
	 * @return void
	 */
	f_setItemLabel: function(item, label) {
		f_core.Assert(typeof(label)=="string", "f_setItemLabel: Label parameter is not a string !");
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}
		
		item._label=label;
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

		return this.f_findIntoGroup(this.f_getItemGroupName(item), search);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return Object[]
	 */
	f_listAllInGroup: function(item) {
		return this.f_listGroup(this.f_getItemGroupName(item));
	},
	/**
	 * Remove all items
	 *
	 * @method public
	 * @param Object menuItem
	 * @return void
	 */
	f_removeAllItems: function(menuItem) {
		var items=menuItem._items;
		if (!items) {
			return;
		}
	
		menuItem._items=new Array;
	},
	fa_getRadioScope: function() {
		return this;
	},
	fa_destroyItems: function(items) {
	},	
	/**
	 * @method private 
	 * @return boolean
	 */
	_preparePopup: function(menuItem) {
		f_core.Debug(fa_menuCore, "_preparePopup: Popup menu '"+menuItem+"'");
	
		if (this.f_getUIPopup(menuItem)) {
			f_core.Debug(fa_menuCore, "_preparePopup: Popup menu '"+menuItem+"' is already opened !");
			return false;
		}
			
		// Efface tout si necessaire !
		if (menuItem._removeAllWhenShow) {
			// Effaces tous les items !
			f_core.Debug(fa_menuCore, "_preparePopup: Remove all items of '"+menuItem+"'.");

			this.f_removeAllItems(menuItem);
		}
		
		var menuActionList=menuItem._menuActionList;
		if (menuActionList) {
			f_core.Debug(fa_menuCore, "_preparePopup: Call menu callbacks for menuItem '"+menuItem+"'.");
			
			// Appel les callbacks !
			var evt=new f_event(this, f_event.MENU, null, menuItem, null, this);
			try {
				if (menuActionList.f_callActions(evt)===false) {
					// Refuse l'affichage !

					f_core.Debug(fa_menuCore, "_preparePopup: One callback refuse to open the menu.");
					return false;
				}
	
			} finally {
				f_classLoader.Destroy(evt);
			}
		}
		
		return this.f_hasVisibleItemChildren(menuItem);
	},
	/**
	 * @method private
	 * @param Object menuItem
	 * @return HTMLElement 
	 */
	_getPopupContainer: function(menuItem) {
		var parent=this.f_getParentItem(menuItem);
	
		if (f_popup.Ie_enablePopup()) {
		
			f_core.Debug(fa_menuCore, "_getPopupContainer: parent="+parent);
			if (!parent) {
				return f_popup.Ie_GetPopup(document);
			}			
			
			var parentPopup=this.f_getUIPopup(parent);
			f_core.Debug(fa_menuCore, "_getPopupContainer: parentPopup="+parentPopup);
			
			return f_popup.Ie_GetPopup(parentPopup.ownerDocument);
		}
	
		if (!parent) {
			return document.body;
		}			
		
		var parentPopup=this.f_getUIPopup(parent);
		if (!parentPopup) {
			return document.body;
		}
		
		return parentPopup.ownerDocument.body;
	},
	/**
	 * @method protected
	 */
	_showPositionedMenuPopup: function(popup, positionInfos) {
	},
	/**
	 * @method hidden
	 * @return boolean
	 */
	f_open: function(jsEvent, positionInfos, autoSelect) {
		f_core.Debug(fa_menuCore, "f_open: Open menu "+this+".");
		
		if (!positionInfos) {
			positionInfos={				
				position: f_popup.MOUSE_POSITION
			};	
		}
		if (jsEvent) {
			positionInfos.jsEvent=jsEvent;
		}
		
		return this.f_openUIPopup(this, jsEvent, autoSelect, positionInfos);
	},
	/**
	 * @method protected
	 * @return boolean
	 */
	f_openUIPopup: function(menuItem, jsEvent, autoSelect, positionInfos) {

		// On verifie l'AJAX !

		if (!this._preparePopup(menuItem)) {
			f_core.Debug(fa_menuCore, "Prepare popup of '"+menuItem.id+"' returns false !");
			return false;
		}
		
		var parentItem=this.f_getParentItem(menuItem);

		f_core.Debug(fa_menuCore, "2 "+parentItem);

		var selectionProvider=this.fa_getSelectionProvider();

		f_core.Debug(fa_menuCore, "3 "+selectionProvider);
				
		var container=this._getPopupContainer(menuItem);
		f_core.Assert(container, "fa_menuCore.f_openUIPopup: Invalid popup container !");
		f_core.Debug(fa_menuCore, "Get container of '"+menuItem+"' returns '"+container+"'.");
		
		var popup=this.f_createPopup(container, menuItem);
		f_core.Assert(popup, "fa_menuCore.f_openUIPopup: Invalid popup object (container="+container+")");

		if (!parentItem) {
			f_core.Debug(fa_menuCore, "f_openUIPopup: Register windows this="+this+" menuItem="+menuItem+" popup="+popup);
	
			if (f_popup.RegisterWindowClick(this.fa_getPopupCallbacks(), this, popup, this.fa_getKeyProvider())==false) {
				return;
			}
		} else {
			f_core.Debug(fa_menuCore, "f_openUIPopup: Child popup parentItem="+parentItem+" this="+this+" menuItem="+menuItem+" popup="+popup);
		}
		
		try {
			if (menuItem!=this) {
				this.f_uiUpdateItemStyle(menuItem);
			}
	
			var scopeName=this.fa_getMenuScopeName(menuItem);
			f_key.EnterScope(scopeName);
	
			if (!positionInfos) {			
				positionInfos={
					component: this.f_getUIItem(menuItem),
					position: f_popup.RIGHT_COMPONENT					
				};
			}
											
			if (f_popup.Ie_enablePopup()) {		
				f_popup.Ie_openPopup(popup._popupObject, positionInfos);

			} else {
				f_popup.Gecko_openPopup(popup._popupObject, positionInfos);
			}
			
		} catch(x) {
			if (!parentItem) {
				f_popup.UnregisterWindowClick(menuItem);
			}
				
			throw x;
		}
				
		if (autoSelect) {
			var menuItems=this.f_listVisibleItemChildren(menuItem);
			
			if (menuItems && menuItems.length) {
				this.f_menuItem_over(menuItems[0], false, jsEvent);
			}
		}
	},
	/**
	 * @method private static 
	 * @param Object menuItem
	 * @param optional Object popup
	 * @return void
	 */
	_closeUIPopup: function(menuItem, popup) {
		f_core.Assert(!popup || this._uiMenuPopups[menuItem]==popup, "Invalid popup or menuItem. menuItem="+menuItem+" popup="+popup);

		if (!popup) {
			popup=this.f_getUIPopup(menuItem);
	
			if (!popup) {
				f_core.Debug(fa_menuCore, "Popup menu '"+menuItem+"' is already closed !");
				return;
			}
		}
		
		if (popup._item===undefined) {
			// Déjà fermé !
			
			f_core.Debug(fa_menuCore, "Popup menu '"+menuItem+"' is already closed !");
			return;
		}

		f_core.Debug(fa_menuCore, "_closeUIPopup: menuItem="+menuItem+" popup="+popup);
		var popupObject=popup._popupObject;
		
		popup._item=undefined; // Object
		popup._popupObject=undefined; // Object

		var selectedItem=this.f_uiGetSelectedItem(menuItem);
		f_core.Debug(fa_menuCore, "_closeUIPopup: child selected="+selectedItem);
		
		if (selectedItem) {
			var childPopup=this.f_getUIPopup(selectedItem);
			if (childPopup) {
				this._closeUIPopup(selectedItem);
			}
		}
		
		var scopeName=this.fa_getMenuScopeName(menuItem);
		f_key.ExitScope(scopeName);
		
		if (menuItem!=this) {
			this.f_uiUpdateItemStyle(menuItem);
		}

		if (f_popup.Ie_enablePopup()) {		
			f_popup.Ie_closePopup(popupObject);

		} else {
			f_popup.Gecko_closePopup(popupObject);
		}			

		var uiMenuItems=this._uiMenuItems;
		var items=this.f_listItemChildren(menuItem);
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			var uiItem=uiMenuItems[item];
			if (!uiItem) {
				continue;
			}
			
			delete uiMenuItems[item];
			
			uiItem._item=undefined; // Object
			uiItem._icon=undefined; // HTMLImageElement
			uiItem.onclick=null;
			uiItem.onmousedown=null;
			uiItem.onmouseover=null;
			uiItem.onmouseout=null;
				
			f_core.VerifyProperties(uiItem);
		}
		
		delete this._uiMenuPopups[menuItem];
		f_core.Debug(fa_menuCore, "_closeUIPopup: remove one popup="+popup+" menuItem="+menuItem);

		if (f_popup.Ie_enablePopup()) {		
			f_popup.Ie_releasePopup(popupObject);

		} else {
			f_popup.Gecko_releasePopup(popupObject);
		}			
		
		if (menuItem==this) {
			f_popup.UnregisterWindowClick(menuItem);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_closeAllPopups: function() {
		f_core.Debug(fa_menuCore, "f_closeAllPopups: close all popups");
		this._closeUIPopup(this);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_clickOutside: function() {
		f_core.Debug(fa_menuCore, "f_clickOutside: click outside !");
		
		this.f_closeAllPopups();
	},
	/**
	 * @method abstract
	 * @return void
	 */
	fa_getPopupContainer: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	fa_focusMenuItem: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	fa_getSelectionProvider:  f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_keySearchAccessKey: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return boolean
	 */
	fa_isSameMenuPoup: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_tabKeySelection: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getMenuScopeName: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getKeyProvider: f_class.ABSTRACT
}

var fa_menuCore=new f_aspect("fa_menuCore", __static, __prototype, fa_groupName, fa_items);
