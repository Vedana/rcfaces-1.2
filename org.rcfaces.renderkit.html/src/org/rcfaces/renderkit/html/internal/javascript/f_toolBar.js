/*
 * $Id$
 */

/**
 * Classe ToolBar.
 *
 * @class f_toolBar extends f_component, fa_readOnly, fa_disabled, fa_groupName, fa_items
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	_DestroyToolFolder: function(toolBar, toolFolder) {
	},
	_DestroyItemFolder: function(toolBar, item) {
	}
}

var __prototype = {

	f_toolBar: function() {
		this.f_super(arguments);

		this._className=f_core.GetAttribute(this, "v:className");
		if (!this._className) {
			this._className=this.className;
		}
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
		for(var i=0;i<items.length;i++) {
			f_toolBar._DestroyToolFolder(this, items[i]);
		}
	},	
	
	_declareToolFolder: function(id, value, disabled) {
	},
	
	f_appendItem: function(toolFolder, id, label, value, accessKey, tooltip, disabled, selected, groupName) {
	},
	
	
	/**
	 * @method hidden
	 */
	f_setItemImages: function(item) {
	},
	
	f_appendSeparatorItem: function(toolFolder) {
	},
	fa_getRadioScope: function() {
		return this;
	}
}
 
var f_toolBar=new f_class("f_toolBar", null, __static, __prototype, f_component,
	fa_readOnly, fa_disabled, fa_groupName, fa_items);
