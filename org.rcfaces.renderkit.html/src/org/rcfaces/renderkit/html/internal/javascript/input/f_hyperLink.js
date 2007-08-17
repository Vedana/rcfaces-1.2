/*
 * $Id$
 */
 
/**
 * class f_hyperLink
 *
 * @class f_hyperLink extends f_input, fa_immediate
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {

	f_hyperLink: function() {
		this.f_super(arguments);

		this.f_setForcedEventReturn(f_event.SELECTION, false);
		
		var input=this._input;
		if (input.tagName.toLowerCase()=="a") {
			if (!input.href) {
				input.href=f_core.JAVASCRIPT_VOID;
			}
		}
		
		var d=f_core.GetAttribute(this, "DISABLED");
		if (d!==undefined && d!==false) {
			this.f_setDisabled(true);
		}
	},
	f_initializeInput: function() {
		return this;
	},
	/**
	 * Returns the text of the link.
	 *
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return f_core.GetTextNode(this, true);
	},
	/**
	 * Set the text of the link.
	 *
	 * @method public
	 * @param String text
	 * @return void
	 */
	f_setText: function(text) {
		f_core.SetTextNode(this, text, this._accessKey);
		
		this.f_setProperty(f_prop.TEXT,text);
	},
	f_fireEvent: function(type, evt, item, value, selectionProvider, detail) {
		if (type==f_event.SELECTION) {			
			if (this.f_isReadOnly() || this.f_isDisabled()) {
				return false;
			}
			
			if (!value) {
				value=this.f_getValue();
			}
		}	
		
		return this.f_super(arguments, type, evt, item, value, selectionProvider, detail);
	}
}
new f_class("f_hyperLink", null, null, __members, f_input, fa_immediate);
