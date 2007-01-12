/*
 * $Id$
 */
 
/**
 * Aspect Items
 *
 * @aspect public fa_items extends fa_itemClientDatas
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

	fa_items: function() {
		this._items=new Array;
		this._itemByValues=new Object;
	},

	f_finalize: function() {
		this._checkedValues=undefined;
		this._uncheckedValues=undefined;
		this._disabledItems=undefined;
		this._enabledItems=undefined;
		this._itemByValues=undefined;
		
		var items=this._items;
		if (items) {
			this._items=undefined;

			this.fa_destroyItems(items);
		}
	},
	/*   ** PAS DE SENS ***
	 * 
	 * @method public
	 * @param Object item
	 * @param any value
	 * @return void
	f_setItemValue: function(item, value) {
		f_core.Assert(typeof(item)=="object", "Item parameter must be an object !");
		f_core.Assert(typeof(value)!="string", "Value parameter must be a String !");

		item._value=value;
	},
	*/
	/**
	 * @method public
	 * @param Object item
	 * @return any
	 */
	f_getItemValue: function(item) {
		return item._value;
	},
	/**
	 * @method hidden
	 */
	f_addItem: function(parent, item) {
		var items=parent._items;
		if (!items) {
			items=new Array;
			parent._items=items;
		}
		
		items.push(item);
		
		var value=item._value;
		f_core.Debug(fa_items, "Add item '"+value+"' to parent '"+parent+"'.");
		if (value) {
			this._itemByValues[value]=item;
		}
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_setItemImages: function(item, imageURL, disabledImageURL, hoverImageURL, selectedImageURL) {
		if (imageURL) {
			this.f_setItemImageURL(item, imageURL);
		}
		if (disabledImageURL) {
			this.f_setItemDisabledImageURL(item, disabledImageURL);
		}
		if (hoverImageURL) {
			this.f_setItemHoverImageURL(item, hoverImageURL);
		}
		if (selectedImageURL) {
			this.f_setItemSelectedImageURL(item, selectedImageURL);
		}
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String message
	 * @return void
	 */
	f_setItemToolTip: function(item, message) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		item.title=message;

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemToolTip: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item.title;
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String imageURL
	 * @return void
	 */
	f_setItemImageURL: function(item, imageURL) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		item._imageURL=imageURL;

		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemImageURL: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._imageURL;
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String imageURL
	 * @return void
	 */
	f_setItemDisabledImageURL: function(item, imageURL) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		item._disabledImageURL=imageURL;

		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemDisabledImageURL: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._disabledImageURL;
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String imageURL
	 * @return void
	 */
	f_setItemHoverImageURL: function(item, imageURL) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		item._hoverImageURL=imageURL;

		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemHoverImageURL: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._hoverImageURL;
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String imageURL
	 * @return void
	 */
	f_setItemSelectedImageURL: function(item, imageURL) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		item._selectedImageURL=imageURL;

		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemSelectedImageURL: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._selectedImageURL;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return boolean
	 */
	f_isItemDisabled: function(item) {		
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return (item._disabled)?true:false;
	},
	/**
	 * Returns the disable state of the item.
	 * 
	 * @method public
	 * @param String item Value of the item, or the item object.
	 * @param optional boolean disabled Disable state to set.
	 * @return void
	 */
	f_setItemDisabled: function(item, disabled) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		if (disabled===undefined) {
			disabled=true;
			
		} else {
			disabled=(disabled)?true:false;
		}
		
		var old=(item._disabled)?true:false;
		item._disabled=(disabled)?true:false;
		
		if (old==item._disabled) {
			return;
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this.fa_updateItemStyle(item);

		if (item.id) {
			if (item._disabled) {
			
				if (this._enabledItems) {
					delete this._enabledItems[item.id];
				}
				
				if (!this._disabledItems) {
					this._disabledItems=new Object;
				}
				
				this._disabledItems[item.id]=true;
				
			} else {
				if (this._disabledItems) {
					delete this._disabledItems[item.id];
				}
				
				if (!this._enabledItems) {
					this._enabledItems=new Object;
				}
				
				this._enabledItems[item.id]=true;
			}
		}
		
	},
	/**
	 * Returns the visibility state of the item.
	 *
	 * @method public
	 * @param String item Value of the item or the item object.
	 * @return boolean The visibility state of the item.
	 */
	f_isItemVisible: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}
		
		return (item._visible===false)?false:true;
	},
	/**
	 * Change the visibility state of the item.
	 * 
	 * @method public
	 * @param String item Value of the item, or the item object.
	 * @param boolean visible Visibility state.
	 * @return void
	 */
	f_setItemVisible: function(item, visible) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}
		
		if (visible===undefined) {
			visible=true;
			
		} else {
			visible=(visible===false)?false:true;
		}
		
		var old=(item._visible===false)?false:true;
		item._visible=visible;

		if (visible==old) {
			return;
		}
				
		if (!this.fa_componentUpdated) {
			return;
		}

		this.fa_updateItemStyle(item);
	
		this.f_performPropertyChange("visible", visible, old);
	},
	/**
	 * Returns the check state of the item.
	 *
	 * @method public
	 * @param String item Value of the item, or the item object.
	 * @return boolean The check state.
	 */
	f_isItemChecked: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}
		
		return (item._checked)?true:false;
	},
	/**
	 * @method public
	 * @param String item Value of the item, or the item object.
	 * @param boolean checked
	 * @return void
	 */
	f_setItemChecked: function(item, checked) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		if (checked===undefined) {
			checked=true;
			
		} else {
			checked=(checked)?true:false;
		}

		if (item._groupName && checked) {
			var selected=this.f_getCheckedItemInGroup(item);
			if (selected && this.f_isItemChecked(selected)) {
				this.f_setItemChecked(selected, false);
			}		
		}		
		
		var old=(item._checked)?true:false;

		if (checked==old) {
			return;
		}

		item._checked=checked;
				
		if (!this.fa_componentUpdated) {
			return;
		}

		if (item.id) {
			if (checked) {
				if (!this._checkedValues) {
					this._checkedValues=new Object;
				}
				this._checkedValues[item.id]=true;

				if (this._uncheckedValues) {
					delete this._uncheckedValues[item.id];
				}
				
			} else {
				if (this._checkedValues) {
					delete this._checkedValues[item.id];
				}

				if (!this._uncheckedValues) {
					this._uncheckedValues=new Object;
				}
				this._uncheckedValues[item.id]=true;
			}
		}

		this.fa_updateItemStyle(item);
		
		this.f_fireEvent(f_event.CHECK, null, item, item._checked);
	},
	/**
	 * Returns the item associated to the specified value.
	 * 
	 * @method public
	 * @param String value Value of an item.
	 * @param hidden boolean assertIfNotFound 
	 * @return Object Item associated with the value.
	 */
	f_getItemByValue: function(value, assertIfNotFound) {
		f_core.Assert(typeof(value)=="string", "Value parameter must be a string.");
		
		var itemValues=this._itemByValues;
		if (!itemValues) {
			if (assertIfNotFound) {
				f_core.Assert(item, "fa_items.f_getItemByValue: Item parameter must be defined !");
			}
			return null;
		}
			
		var item=itemValues[value];
		if (item) {
			return item;
		}
		
		if (assertIfNotFound) {
			f_core.Assert(item, "fa_items.f_getItemByValue: Item parameter must be defined !");
		}
		
		return null;
	},
	/**
	 * @method public
	 * @param String item
	 * @return any
	 */
	f_getItemValue: function(item) {
		return item._value;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String
	 */
	f_getItemAccessKey: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._accessKey;
	},
	/**
	 * @method hidden
	 */
	f_setItemAccessKey: function(item, accessKey) {
		item._accessKey=accessKey;
	},
	/**
	 * Returns the group name of the item.
	 *
	 * @method public
	 * @param Object item
	 * @return String Group name
	 */
	f_getItemGroupName: function(item) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		return item._groupName;
	},
	/**
	 * Set group of an item.
	 *
	 * @method public
	 * @param Object item
	 * @param String groupName Name of group.
	 * @return void
	 */
	f_setItemGroupName: function(item, groupName) {
		if (typeof(item)!="object") {
			item=this.f_getItemByValue(item, true);
		}

		this.f_changeGroup(item._groupName, groupName, item);
		item._groupName = groupName;
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_serializeItems: function() {
		if (this._checkedValues) {
			this.f_setProperty(f_prop.CHECKED_ITEMS, this._checkedValues, true);
		}
		if (this._uncheckedValues) {
			this.f_setProperty(f_prop.UNCHECKED_ITEMS, this._uncheckedValues, true);
		}
		if (this._disabledItems) {
			this.f_setProperty(f_prop.DISABLED_ITEMS, this._disabledItems, true);
		}
		if (this._enabledItems) {
			this.f_setProperty(f_prop.ENABLED_ITEMS, this._enabledItems, true);
		}
	},
	/**
	 * @method public
	 * @param Object item
	 * @return Object[]
	 */
	f_listItemChildren: function(item) {
		f_core.Assert(typeof(item)=="object", "fa_items.f_listItemChildren: Invalid item object. ("+item+")");

		return item._items;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return Object[]
	 */
	f_listVisibleItemChildren: function(item) {
		f_core.Assert(typeof(item)=="object", "fa_items.f_listItemChildren: Invalid item object. ("+item+")");

		var array=new Array;

		var items=item._items;
		if (!items || !items.length) {
			return array;
		}
		
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			if (!this.f_isItemVisible(item)) {
				continue;
			}
			
			array.push(item);
		}
		
		return array;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return boolean
	 */
	f_hasItemChildren: function(item) {
		f_core.Assert(typeof(item)=="object", "fa_items.f_hasItemChildren: Invalid item object. ("+item+")");

		var items=item._items;
		if (!items || !items.length) {
			return false;
		}
		
		return true;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return boolean
	 */
	f_hasVisibleItemChildren: function(item) {
		f_core.Assert(typeof(item)=="object", "fa_items.f_hasItemChildren: Invalid item object. ("+item+")");

		var items=item._items;
		if (!items || !items.length) {
			return false;
		}
		
		for(var i=0;i<items.length;i++) {
			if (this.f_isItemVisible(items[i])) {
				return true;
			}
		}
		
		return false;
	},
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_updateItemStyle: f_class.ABSTRACT,
	
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_destroyItems: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	fa_componentUpdated: f_class.OPTIONAL_ABSTRACT
}

var fa_items=new f_aspect("fa_items", null, __prototype, fa_itemClientDatas);
