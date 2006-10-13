/*
 * $Id$
 */

/**
 * class f_messageFieldSet
 *
 * @class public f_messageFieldSet extends f_fieldSet, fa_message1
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {
	f_messageFieldSet: function() {
		this.f_super(arguments);
		
		this._normalText=this.f_getText();
	},
/*
	f_finalize: function() {
		// this._normalText=undefined; // string

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
		
		var text=null;
		if (currentMessage) {
			text=currentMessage.f_getSummary();
		}
		if (!text) {
			text=this._normalText;
		}
		
		this.f_setText(text);
	}
}
var f_messageFieldSet=new f_class("f_messageFieldSet", null, null, __prototype, f_fieldSet, fa_message1);
