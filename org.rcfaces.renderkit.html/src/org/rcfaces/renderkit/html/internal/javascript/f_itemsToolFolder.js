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
 
var __static = {
}

var __prototype = {

	f_itemsToolFolder: function() {
		this.f_super(arguments);
		
		this._uiItems=new Object;
	},
	f_finalize: function() {
		this._uiItems=undefined; // Map<String, Object>
		
		this.f_super(arguments);
	},
	f_update: function() {
		if (this._items) {
		}
		
		return this.f_super(arguments);
	},
	f_serialize: function() {
		this.f_serializeItems();
			
		return this.f_super(arguments);
	},
	fa_destroyItems: function(items) {
	},	
	/**
	 * @method hidden
	 */
	f_appendToolItems: function() {
		
		var toolFolder=this;
		
		var selectionCallback=function(event) {
			return toolFolder._itemOnSelect(event);
		}
		
		for(var i=0;i<arguments.length;) {
			var id=arguments[i++];
			var inputType=arguments[i++];
			var value=arguments[i++];
			var disabled=arguments[i++];	
			
			if (inputType==fa_items.AS_SEPARATOR) {
				var item=this.f_appendItem(this);
				
				item._inputType=inputType;
				continue;
			}
			
			var component=this.f_findComponent(id);
			f_core.Assert(component, "f_itemsToolFolder.f_appendToolItems: Can not find component associated to id '"+id+"'.");
			
			var item=new Object;
			item._id=component.id;
			item._inputType=inputType;
			item._value=value;
			item._disabled=disabled;
			
			this.f_addItem(this, item);
			
			f_core.Assert(!this._uiItems[value], "f_itemsToolFolder.f_appendToolItems: Value is already used ! ('"+value+"')");
			
			this._uiItems[value]=component;
				
			switch(inputType) {
			case fa_items.AS_PUSH_BUTTON:
			case fa_items.AS_CHECK_BUTTON:
			case fa_items.AS_RADIO_BUTTON:
			// case fa_items.AS_SUBMIT_BUTTON: // trop tard !?
			// case fa_items.AS_RESET_BUTTON: // trop tard !?
			
				component.f_addEventListener(f_event.SELECTION, selectionCallback);
				break;
			}
			
		}			
	},
	/**
	 * @method private
	 * @param f_event event
	 * @return void
	 */
	_itemOnSelect: function(event) {
		var itemValue; // Rechercher l'item
		
		var itemComponent=event.f_getComponent();
		
		var uiItems=this._uiItems;
		for(var i in uiItems) {					
			if (uiItems[i]==itemComponent) {
				itemValue=i;
				break;
			}
		}
		
		if (itemValue===undefined) {
			f_core.Debug(f_itemsToolFolder, "_itemOnSelect: Can not find item value='"+itemValue+"'.");
			return true;
		}
		
		var item=this.f_getItemByValue(itemValue, true);

		f_core.Debug(f_itemsToolFolder, "_itemOnSelect: Call SELECTION on item='"+item+"' value='"+itemValue+"'.");
		
		return this.f_fireEvent(f_event.SELECTION, event.f_getJsEvent(), item, itemValue);
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
		var itemValue=this.f_getItemValue(item);
		
		var component=this._uiItems[itemValue];
		if (!component) {
			return;
		}
		
		component.f_setDisabled(this.f_isItemDisabled(item));
		component.f_setVisible(this.f_isItemVisible(item));
		//component.f_setReadOnly(this.f_isItemReadOnly(item));
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_hideToolItems: function() {
		for(var i=0;i<arguments.length;i++) {
			var itemValue=arguments[i];
			
			this.f_setItemVisible(itemValue, false);
		}
	}
}
 
new f_class("f_itemsToolFolder", null, __static, __prototype, f_component,
	fa_readOnly, fa_disabled, fa_items, fa_subMenu);
