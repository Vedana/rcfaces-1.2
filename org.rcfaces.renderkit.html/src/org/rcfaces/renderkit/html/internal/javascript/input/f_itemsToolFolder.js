/*
 * $Id$
 */

/**
 * Classe ItemsToolFoolder.
 *
 * @class f_itemsToolFolder extends f_component, fa_readOnly, fa_disabled, fa_items, fa_subMenu
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
	/** 
	 * @method private static
	 * @return String
	 * @context none
	 */
	_ItemToString: function() {
		return this._id;
	}
}

var __members = {

	f_itemsToolFolder: function() {
		this.f_super(arguments);
		
		this._sepId=0;
		
		this._uiItems=new Object;
	},
	f_finalize: function() {
		this._uiItems=undefined; // Map<String, Object>
//		this._sepId=undefined; // number
		
		this.f_super(arguments);
	},
	f_update: function() {
		if (this._items) {
		}
		
		return this.f_super(arguments);
	},
	f_serialize: function() {
		this.f_serializeItems();
			
		this.f_super(arguments);
	},
	fa_destroyItems: function(items) {
	},	
	f_fireEvent: function(type, evt, item, value, selectionProvider, detail) {
		if (type==f_event.SELECTION) {			
			if (!value) {
    			 return false;
			}
		}	
		
		return this.f_super(arguments, type, evt, item, value, selectionProvider, detail);
	},
	/**
	 * @method hidden
	 * @param String id
	 * @param Object properties
	 * @return Object
	 */
	f_appendToolItem2: function(id, properties) {
		
		var inputType=properties._inputType;

		var item=new Object();
		
		if (inputType==fa_items.AS_SEPARATOR) {			
			item._inputType=inputType;
			item._id="#SEP#"+(this._sepId++);
			item._value=item._id;
							
			if (f_core.IsDebugEnabled(f_itemsToolFolder)) {
				item.toString=function() {
					return "[toolItemSeparator id='"+item._id+"']";
				}				
			} else {
				item.toString=f_itemsToolFolder._ItemToString;
			}

			this.f_addItem(this, item);

			f_core.Debug(f_itemsToolFolder, "f_appendToolItem2: append a separator !");
		
			return item;

		} else if (!inputType) {
			inputType=fa_items.AS_PUSH_BUTTON;
		}
		
		item._inputType=inputType;
		
		var component=this.f_findComponent(id);
		f_core.Assert(component, "f_itemsToolFolder.f_appendToolItem2: Can not find component associated to id '"+id+"'.");
		
		var item=new Object();
		item._id=component.id;
		item._value=properties._value;
		item._disabled=properties._disabled;

		if (properties._visible===false) {
			this.f_setItemVisible(item, false);
		}
		
		if (properties._clientDatas) {
			this.f_setItemClientDatas(item, properties._clientDatas);
		}
		
		if (f_core.IsDebugEnabled(f_itemsToolFolder)) {
			item.toString=function() {
				return "[toolItem id='"+this._id+"' value='"+this._value+"' inputType='"+this._inputType+"' disabled="+this._disabled+"]";
			}				
		} else {
			item.toString=f_itemsToolFolder._ItemToString;
		}
		this.f_addItem(this, item);
		
		f_core.Assert(!this._uiItems[item._value], "f_itemsToolFolder.f_appendToolItem2: Value is already used ! ('"+item._value+"')");
		
		this._uiItems[item._value]=component;
		
		f_core.Debug(f_itemsToolFolder, "f_appendToolItem2: append item: "+item);
		
		switch(inputType) {
		case fa_items.AS_PUSH_BUTTON:
		case fa_items.AS_CHECK_BUTTON:
		case fa_items.AS_RADIO_BUTTON:
			var toolFolder=this;
		
			var selectionCallback=function(event) {
				return toolFolder._itemOnSelect(event);
			}
		
			component.f_addEventListener(f_event.SELECTION, selectionCallback);
			break;
			
		case fa_items.AS_DROP_DOWN_MENU:
			var toolFolder=this;
		
			var selectionCallback=function(event) {
				return toolFolder._itemMenuOnSelect(event);
			}
		
			component.f_addEventListener(f_event.SELECTION, selectionCallback);
			break;

		case fa_items.AS_SUBMIT_BUTTON: // trop tard !?
		case fa_items.AS_RESET_BUTTON: // trop tard !?
			break;

		default:
			f_core.Debug(f_itemsToolFolder, "f_appendToolItems: unknown input type of item='"+item+"'.");
		}
		
		return item;
	},
	/**
	 * @method private
	 * @param f_event event
	 * @return boolean
	 */
	_itemOnSelect: function(event) {
		var itemValue; // Rechercher l'item
		
		var itemComponent=event.f_getComponent();
		var itemValue=this._getItemValueByComponent(itemComponent);		
		
		if (itemValue===undefined) {
			f_core.Debug(f_itemsToolFolder, "_itemOnSelect: Can not find item value='"+itemValue+"'.");
			return true;
		}
		
		var item=this.f_getItemByValue(itemValue, true);

		f_core.Debug(f_itemsToolFolder, "_itemOnSelect: Call SELECTION on item='"+item+"' value='"+itemValue+"'.");
		
		this.f_fireEvent(f_event.SELECTION, event.f_getJsEvent(), item, itemValue);
		
		return false;
	},
	/**
	 * @method private
	 * @param HTMLElement component
	 * @return String
	 */
	_getItemValueByComponent: function(component) {
		var uiItems=this._uiItems;
		
		for(var itemValue in uiItems) {					
			if (uiItems[itemValue]==component) {
				return itemValue;
			}
		}
		
		return undefined;
	},
	/**
	 * @method private
	 * @param f_event event
	 * @return boolean
	 */
	_itemMenuOnSelect: function(event) {
		var itemValue=event.f_getValue(); // Rechercher l'item
		
		var itemComponent=event.f_getComponent();
		var componentValue=this._getItemValueByComponent(itemComponent);
		
		if (componentValue===undefined) {
			f_core.Debug(f_itemsToolFolder, "_itemMenuOnSelect: Can not find item value='"+itemValue+"'.");
			return true;
		}
		
		var item=this.f_getItemByValue(componentValue, true);

		f_core.Debug(f_itemsToolFolder, "_itemMenuOnSelect: Call SELECTION on item='"+item+"' value='"+itemValue+"'.");
		
		this.f_fireEvent(f_event.SELECTION, event.f_getJsEvent(), item, itemValue);
		
		return false;
	},
	fa_updateDisabled: function() {
		var disabled=this.f_isDisabled();
		
		var items=this.f_listItemChildren();
		for(var i=0;i<items.length;i++) {
			var component=this._uiItems[items[i]];
			if (!component) {
				continue;
			}
			
			component.f_setDisabled(disabled);
		}
	},
	fa_updateReadOnly: function() {
		var readOnly=this.f_isReadOnly();
		
		var items=this.f_listItemChildren();
		for(var i=0;i<items.length;i++) {
			var component=this._uiItems[items[i]];
			if (!component) {
				continue;
			}
				
			component.f_setReadOnly(readOnly);
		}
	},
	fa_updateItemStyle: function(item) {
		var component=this.f_getItemComponent(item);
		if (!component) {
			return;
		}
		
		component.f_setDisabled(this.f_isItemDisabled(item));
		component.f_setVisible(this.f_isItemVisible(item));
		//component.f_setReadOnly(this.f_isItemReadOnly(item));
	},
	/*
	 * @method hidden
	 * @return void
	 *
	f_hideToolItems: function() {
		for(var i=0;i<arguments.length;i++) {
			var itemValue=arguments[i];
			
		}
	},
	*/
	/**
	 * @method hidden
	 * @return f_component
	 */
	f_getItemComponent: function(item) {
		var itemValue=item;
		
		if (typeof(item)=="object") {
			itemValue=this.f_getItemValue(item);
		}
		
		return this._uiItems[itemValue];		
	}
	/*,
	/ **
	 * @method public
	 * @param String value Value of the item.
	 * @param String label Label to set.
	 * @return boolean <code>true</code> if success, <code>false</code> otherwise.
	 * /
	f_setItemLabel: function(value, label) {
		f_core.Assert(typeof(label)=="string", "f_itemsToolFolder.f_setItemLabel: Invalid label parameter. ("+label+")");
		
		var component=this._getItemComponent(item);
		if (!component) {
			return false;
		}

		if (typeof(component.f_setText)!="function") {
			return false;
		}
		
		component.f_setText(label);
		return true;
	},
	/ **
	 * @method public
	 * @param String value Value of the item.
	 * @return String Label of the item.
	 * /
	f_getItemLabel: function(value) {
		var component=this._getItemComponent(item);
		if (!component) {
			return null;
		}

		if (typeof(component.f_getText)!="function") {
			return null;
		}
		
		return component.f_getText();
	}
	*/
}
 
new f_class("f_itemsToolFolder", {
	extend: f_component,
	aspects: [ fa_readOnly, fa_disabled, fa_items, fa_subMenu ],
	statics: __statics,
	members: __members
});
