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
		
		// Le premier LABEL est forcement notre titre !
		this._titleLabel = f_core.GetFirstElementByTagName(this, "LABEL");		
	},
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
	 * @return string
	 */
	f_getText: function() {
		var titleLabel=this._titleLabel;

		if (!titleLabel) {
			return "";
		}
		
		return f_core.GetTextNode(titleLabel);
	},
	/**
	 * @method public
	 * @param string text
	 * @param hidden boolean noSerialize
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(text===null || typeof(text)=="string", "f_fieldSet.f_setText: Invalid text parameter ('"+text+"')");

		var titleLabel=this._titleLabel;

		f_core.Debug(f_fieldSet, "Change Label ("+titleLabel+") to text '"+text+"'");

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
var f_fieldSet=new f_class("f_fieldSet", null, null, __prototype, f_component);
