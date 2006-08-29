/*
 * $Id$
 */
 
/**
 * class f_hyperLink
 *
 * @class f_hyperLink extends f_input
 * @author Olivier Oeuillot
 * @version $Revision$
 */
 
var __prototype = {

	f_hyperLink: function() {
		this.f_super(arguments);
		this._returnOnSelect = false;
		
		if (this.tagName.toUpperCase()=="A") {
			if (!this.href) {
				this.href=f_core.JAVASCRIPT_VOID;
			}
		}
	},	
	f_initializeInput: function() {
		return null;
	},
	/**
	 * Returns the text of the link.
	 *
	 * @method public
	 * @return string
	 */
	f_getText: function() {
		return f_core.GetTextNode(this);
	},
	/**
	 * Set the text of the link.
	 *
	 * @method public
	 * @param string text
	 * @return void
	 */
	f_setText: function(text) {
		f_core.SetTextNode(this, text);
		
		this.f_setProperty(f_prop.TEXT,text);
	}
}

var f_hyperLink=new f_class("f_hyperLink", null, null, __prototype, f_input, fa_immediate);
