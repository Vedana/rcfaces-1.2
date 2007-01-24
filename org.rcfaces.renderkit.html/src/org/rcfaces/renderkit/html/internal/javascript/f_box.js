/*
 * $Id$
 */
 
/**
 * Box class.
 *
 * @class public f_box extends f_component, fa_asyncRender, fa_subMenu
 */
 
var __static = {
	/**
	 * @field private static final String
	 */
	_BODY_MENU_ID: "#body"
}

var __prototype = {
/*
	f_finalize: function() {
		this._defaultMenuId=undefined; // string
		
		this.f_super(arguments);
	},
	*/
	f_update: function() {
		this.f_super(arguments);
		
		var menu=this.f_getSubMenuById(f_box._BODY_MENU_ID);
		if (menu) {
			this.f_insertEventListenerFirst(f_event.MOUSEDOWN, this._performMenuMouseDown);
		}
	},
	/**
	 * Specify the default menu identifier.
	 *
	 * @method public
	 * @param String menuId Identifier of the menu.
	 * @return void
	 */
	f_setDefaultMenuId: function(menuId) {
		this._defaultMenuId=menuId;
	},
	
	/**
	 * Returns the current menu identifier.
	 *
	 * @method public
	 * @return Identifier of the menu.
	 */
	f_getDefaultMenuId: function() {
		var d=this._defaultMenuId;
		
		return (d)?d:null;
	},
	_performMenuMouseDown: function(event) {		
		var jsEvent=event.f_getJsEvent();
		
		var sub=f_core.IsPopupButton(jsEvent);
		if (!sub) {
			return;
		}
		
		var menuId=this.f_getDefaultMenuId();
		if (!menuId) {
			menuId=f_box._BODY_MENU_ID;
		}
		
		var menu=this.f_getSubMenuById(menuId);
		if (menu) {
			menu.f_open(jsEvent);
				
			return event.f_preventDefault();
		}
	},
	fa_componentCaptureMenuEvent: function() {
		return null;
	}
}
 
new f_class("f_box", null, __static, __prototype, f_component, fa_asyncRender, fa_subMenu);
