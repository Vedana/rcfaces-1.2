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
	/** 
	 * @field private static String
	 * @this Object
	 */
	_ItemToString: function() {
		return this._id;
	}
}

var __prototype = {

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
				var item=new Object();
				
				item._inputType=inputType;
				item._id="#SEP#"+(this._sepId++);
								
				if (f_core.IsDebugEnabled(f_itemsToolFolder)) {
					item.toString=function() {
						return "[toolItemSeparator id='"+item._id+"']";
					}				
				} else {
					item.toString=f_itemsToolFolder._ItemToString;
				}

				this.f_addItem(this, item);

				f_core.Debug(f_itemsToolFolder, "f_appendToolItems: append a separator !");
			
				continue;
			}
			
			var component=this.f_findComponent(id);
			f_core.Assert(component, "f_itemsToolFolder.f_appendToolItems: Can not find component associated to id '"+id+"'.");
			
			var item=new Object();
			item._id=component.id;
			item._inputType=inputType;
			item._value=value;
			item._disabled=disabled;
			
			if (f_core.IsDebugEnabled(f_itemsToolFolder)) {
				item.toString=function() {
					return "[toolItem id='"+this._id+"' value='"+this._value+"' inputType='"+this._inputType+"' disabled="+this._disabled+"]";
				}				
			} else {
				item.toString=f_itemsToolFolder._ItemToString;
			}
			this.f_addItem(this, item);
			
			f_core.Assert(!this._uiItems[value], "f_itemsToolFolder.f_appendToolItems: Value is already used ! ('"+value+"')");
			
			this._uiItems[value]=component;
			
			f_core.Debug(f_itemsToolFolder, "f_appendToolItems: append item: "+item);
			
			switch(inputType) {
			case fa_items.AS_PUSH_BUTTON:
			case fa_items.AS_CHECK_BUTTON:
			case fa_items.AS_RADIO_BUTTON:
			// case fa_items.AS_SUBMIT_BUTTON: // trop tard !?
			// case fa_items.AS_RESET_BUTTON: // trop tard !?
			
				component.f_addEventListener(f_event.SELECTION, selectionCallback);
				break;

			case fa_items.AS_SUBMIT_BUTTON: // trop tard !?
			case fa_items.AS_RESET_BUTTON: // trop tard !?
				break;

			default:
				f_core.Debug(f_itemsToolFolder, "f_appendToolItems: unknown input type of item='"+item+"'.");
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
		var component=this._getItemComponent(item);
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
	},
	/**
	 * @method private
	 * @return f_component
	 */
	_getItemComponent: function(item) {
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
 
new f_class("f_itemsToolFolder", null, __static, __prototype, f_component,
	fa_readOnly, fa_disabled, fa_items, fa_subMenu);
