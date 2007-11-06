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
 
var __statics = {
	/**
	 * @field private static final String
	 */
	_LABEL_ID_SUFFIX: "::label"
	
}
 
var __members = {
	f_finalize: function() {
		var text=this._titleLabel;
		if (text) {
			this._titleLabel=undefined; // HtmlElement
			f_core.VerifyProperties(text);
		}
		
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		var titleLabel=this._titleLabel;
		if (titleLabel===undefined) {		
			// Le premier LABEL est forcement notre titre !
			titleLabel = this.ownerDocument.getElementById(this.id+f_fieldSet._LABEL_ID_SUFFIX);
			this._titleLabel = titleLabel;		
		}

		if (!titleLabel) {
			return "";
		}
		
		return f_core.GetTextNode(titleLabel);
	},
	/**
	 * @method public
	 * @param String text
	 * @param hidden boolean noSerialize
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(text===null || typeof(text)=="string", "f_fieldSet.f_setText: Invalid text parameter ('"+text+"')");

		if (text==this.f_getText()) {
			return;
		}
	
		var titleLabel=this._titleLabel;

		f_core.Debug(f_fieldSet, "f_setText: Change Label ("+titleLabel+") to text '"+text+"'");

		if (!titleLabel) {
			return;
		}
				
		if (!text) {
			text="";
		}	
			
		f_core.SetTextNode(titleLabel, text);
		
		var style=titleLabel.style;
		if (text.length) {
			if (style.display=="none") {
				style.display="inherit";
			}
			
		// Titre pas visible !
		} else if (style.display!="none") {
			style.display="none";
		}

		this.f_setProperty(f_prop.TEXT, text);
	}
}
new f_class("f_fieldSet", {
	extend: f_component,
	members: __members,
	statics: __statics
});
