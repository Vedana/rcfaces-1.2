/*
 * $Id$
 */
 
/**
 * Box class.
 *
 * @class public f_styledMessage extends f_component, fa_message1
 */

var __members = {
	f_styledMessage: function() {		
		this.f_super(arguments);

		this._showIfMessage=f_core.GetAttribute(this, "v:showIfMessage");
	},
	
	/*
	f_finalize: function() {
		// this._className=undefined; // string
		// this._showIfMessage=undefined; // boolean
		
		this.f_super(arguments);
	},
	*/
	/**
	 * @method protected
	 */
	fa_updateMessages: function() {
		var className;
		var currentMessage=this._currentMessage;
		if (currentMessage) {
			className=this.f_getStyleClassFromSeverity(currentMessage.f_getSeverity());	
		}
		
		if (!className) {
			className=this._className;
		}
		
		if (this.className!=className) {
			this.className=className;
		}
		
		if (this._showIfMessage) {
			this.f_setVisible(currentMessage!=null);
		}
	}
}
 
new f_class("f_styledMessage", null, __statics, __members, f_component, fa_message1);
