/*
 * $Id$
 */
 
/**
 * class f_hyperLink
 *
 * @class f_hyperLink extends f_input
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {

	f_hyperLink: function() {
		this.f_super(arguments);

		this.f_setForcedEventReturn(f_event.SELECTION, false);
		
		var input=this._input;
		if (input.tagName.toUpperCase()=="A") {
			if (!input.href) {
				input.href=f_core.JAVASCRIPT_VOID;
			}
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
	}
}
new f_class("f_hyperLink", null, null, __prototype, f_input, fa_immediate);
