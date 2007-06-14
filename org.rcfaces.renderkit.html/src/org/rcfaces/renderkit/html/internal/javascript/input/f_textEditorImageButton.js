/*
 * $Id$
 */

/**
 * class f_textEditorImageButton
 *
 * @class f_textEditorImageButton extends f_imageButton
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
	f_textEditorImageButton: function() {
		this.f_super(arguments);
		
		this._type=f_core.GetAttribute(this, "v:type");
		this._for=f_core.GetAttribute(this, "v:for");
	},
	f_finalize: function() {
		// this._for=undefined; // string
		// this._type=undefined; // string
		
		this.f_super(arguments);
	},
	f_imageButtonSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}

		var type=this._type;
		if (!type) {
			return false;
		}
				
		switch(type.toLowerCase()) {
		case "first":
			break;
		
		case "prev":
			break;
		
		case "next":
			break;
		
		case "last":
			break;
		}

		return false;
	}
}

new f_class("f_textEditorImageButton", null, null, __prototype, f_imageButton);
