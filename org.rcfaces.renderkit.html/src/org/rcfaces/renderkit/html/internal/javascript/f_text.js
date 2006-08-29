/*
 * $Id$
 *
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */

/**
 * @class f_text extends f_component
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __prototype = {

	/**
	 * @method public
	 * @return string
	 */
	f_getText: function() {
		return f_core.GetTextNode(this);
	},
	/**
	 * @method public
	 * @param string text The text.
	 * @return void
	 */
	f_setText: function(text) {
		if (this.f_getText() == text) {
			return;
		}
		f_core.SetTextNode(this, text);
		
		this.f_setProperty(f_prop.TEXT,text);
	}
}

var f_text=new f_class("f_text", null, null, __prototype, f_component);
