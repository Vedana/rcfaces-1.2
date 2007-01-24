/*
 * $Id$
 */

/**
 * f_submitButton class
 *
 * @class f_submitButton extends f_button
 */
var __prototype = {

	f_submitButton: function() {
		this.f_super(arguments);

		this._returnOnSelect = false;
	}
	 /*,
	f_updateDisabled: function(disabled) {
		this.f_super(arguments, disabled);
		
		if (f_core.IsGecko()) {
			var input=this._input;
			if (!input) {
				input=this;
			}
				
			var t="SUBMIT";
			if (disabled) {
				t="BUTTON";
			}
			
			f_core.Info("f_submitButton", "Change button '"+this.id+"' to type '"+t+"'.");
			
			if (t!=this.type) {
				this.type=t;
			}
		}
	}
	*/
}

new f_class("f_submitButton", null, null, __prototype, f_button);
