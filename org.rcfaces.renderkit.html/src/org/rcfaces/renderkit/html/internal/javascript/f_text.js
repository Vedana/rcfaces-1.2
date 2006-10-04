/*
 * $Id$
 */

/**
 * @class f_text extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
		f_core.Assert(typeof(text)=="string", "Invalid text parameter ! ('"+text+"')");
		if (this.f_getText() == text) {
			return;
		}
		f_core.SetTextNode(this, text);
		
		this.f_setProperty(f_prop.TEXT,text);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getValue: function() {
		return this.f_getText();
	},
	/**
	 * @method public
	 * @param string text The text.
	 * @return void
	 */
	f_setValue: function(text) {
		this.f_setText(text);
	}	
}

var f_text=new f_class("f_text", null, null, __prototype, f_component);
