/*
 * $Id$
 */

/**
 * Classe ItemsToolFoolder.
 *
 * @class f_itemsToolFolder extends f_component, fa_readOnly, fa_disabled, fa_groupName, fa_items, fa_subMenu
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
}

var __prototype = {

	f_itemsToolFolder: function() {
		this.f_super(arguments);
	},
	/*
	f_finalize: function() {
		this._className=undefined; // string
		
		this.f_super(arguments);
	},
	*/
	f_update: function() {
		if (this._items) {
		}
		
		return this.f_super(arguments);
	},
	f_serialize: function() {
		this._serializeItems();
			
		return this.f_super(arguments);
	},
	fa_destroyItems: function(items) {
	},	
	
	_declareToolFolder: function(id, value, disabled) {
	},
	
	f_appendItem: function(toolFolder, id, label, value, accessKey, tooltip, disabled, selected, groupName) {
	},
		
	f_appendSeparatorItem: function(toolFolder) {
	},
	fa_getRadioScope: function() {
		return this;
	}
}
 
new f_class("f_itemsToolFolder", null, __static, __prototype, f_component,
	fa_readOnly, fa_disabled, fa_groupName, fa_items, fa_subMenu);
