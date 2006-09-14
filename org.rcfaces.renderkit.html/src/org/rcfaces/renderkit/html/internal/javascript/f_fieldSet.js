/*
 * $Id$
 */

/**
 * class f_fieldSet
 *
 * @class public f_fieldSet extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {
	f_fieldSet: function() {
		this.f_super(arguments);
		
		this._text = f_core.GetFirstElementByTagName(this, "LABEL");
		// Le premier LABEL est forcement notre titre !
	},
	f_finalize: function() {
		if (this._text) {
			this._text=undefined; // HtmlElement
		}
		
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getText: function() {
		if (!this._text) {
			return "";
		}
		
		return f_core.GetTextNode(this._text);
	},
	/**
	 * @method public
	 * @param string text
	 * @return void
	 */
	f_setText: function(text) {
		var cp=this._text;

		if (!cp) {
			return;
		}
		
		if (typeof(text)!="string") {
			text="";
		}
				
		f_core.SetTextNode(cp, text);
		if (text.length) {
			if (cp.style.display=="none") {
				cp.style.display="inherit";
			}
			
		// Titre pas visible !
		} else if (cp.style.display!="none") {
			cp.style.display="none";
		}

		this.f_setProperty(f_prop.TEXT, text);		
	}
}
var f_fieldSet=new f_class("f_fieldSet", null, null, __prototype, f_component);
