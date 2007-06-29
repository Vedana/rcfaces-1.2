/*
 * $Id$
 */

/**
 * @class public f_styledText extends f_text
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {

	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return this.innerHTML;
	},
	/**
	 * @method public
	 * @param String text The text.
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(typeof(text)=="string", "f_styledText.f_setText: Invalid text parameter ! ('"+text+"')");

		if (this.f_getText() == text) {
			return;
		}
		this.innerHTML=text;
		
		this.f_setProperty(f_prop.TEXT,text);
	}
}

new f_class("f_styledText", {
	extend: f_text,
	members: __members
});
