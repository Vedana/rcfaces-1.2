/*
 * $Id$
 */

/**
 * @class f_text extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
		
	/**
	 * @field private String
	 */
	_forComponentId: undefined,

	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return f_core.GetTextNode(this, true);
	},
	/**
	 * @method public
	 * @param String text The text.
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(typeof(text)=="string", "f_text.f_setText: Invalid text parameter ! ('"+text+"')");
		if (this.f_getText() == text) {
			return;
		}
		
		f_core.SetTextNode(this, text, this._accessKey);
		
		this.f_setProperty(f_prop.TEXT,text);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getValue: function() {
		return this.f_getText();
	},
	/**
	 * @method public
	 * @param String text The text.
	 * @return Boolean
	 */
	f_setValue: function(text) {
		if (typeof(text)=="string") {
			this.f_setText(text);
			
			return true;
		}
		
		return false;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getForClientId: function() {
		var forComponentId=this._forComponentId;
		if (forComponentId!==undefined) {
			return forComponentId;
		}
		
		forComponentId=f_core.GetAttributeNS(this, "for", null);
		if (!forComponentId) {
			forComponentId=f_core.GetAttribute(this, "for", null);
		}
		
		this._forComponentId=forComponentId;
		
		return forComponentId;
	}
};

new f_class("f_text", {
	extend: f_component,
	members: __members
});
