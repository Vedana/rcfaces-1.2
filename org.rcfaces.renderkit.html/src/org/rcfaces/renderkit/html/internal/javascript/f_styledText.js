/*
 * $Id$
 */

/**
 * @class public f_styledText extends f_text
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

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
		f_core.Assert(typeof(text)=="string", "Invalid text parameter ! ('"+text+"')");

		if (this.f_getText() == text) {
			return;
		}
		this.innerHTML=text;
		
		this.f_setProperty(f_prop.TEXT,text);
	}
}

var f_styledText=new f_class("f_styledText", null, null, __prototype, f_text);
