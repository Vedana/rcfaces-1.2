/*
 * $Id$
 */

/**
 * class f_imageCombo
 *
 * @class f_imageCombo extends f_imageButton
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field private static final string
	 */
	_MENU_ID: "#popup"
}

var __prototype = {

	f_imageCombo: function() {
		this.f_super(arguments);
		
		this.f_addEventListener(f_event.KEYDOWN, this._onKeyDown);		
	},
	f_update: function(set) {
		this.f_super(arguments);
		
	},
	_onKeyDown: function(event) {
		var code=event.f_getJsEvent().keyCode;

		if (code!=f_key.VK_DOWN) {
			return true;
		}

		event.f_preventDefault();

		var menu=this.f_getSubMenuById(f_imageCombo._MENU_ID);
		if (menu) {
			menu.f_open(this, {
				position: f_menu.BOTTOM_COMPONENT
				}, null, event.f_getJsEvent());
		}
		
		return false;
	},
	_onSelect: function(evt) {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		
		if (f_popup.VerifyMouseDown(this, evt._jsEvent)==false) {
			return false;
		}
	
		var menu=this.f_getSubMenuById(f_imageCombo._MENU_ID);
		if (!menu) {
			return true;
		}
		
		menu.f_open(this, {
			position: f_menu.BOTTOM_COMPONENT
			}, null, evt);
		
		return false;
	},
	
	_a_componentCaptureMenuEvent: function() {
		return this;
	},
	
	_a_getItemsWrapper: function() {
		return this.f_getSubMenuById(f_imageCombo._MENU_ID);
	}
}

var f_imageCombo=new f_class("f_imageCombo", null, __static, __prototype, f_imageButton, fa_subMenu, fa_itemsWrapper);
