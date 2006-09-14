/*
 * $Id$
 */

/**
 * class f_imageCheckButton
 *
 * @class f_imageCheckButton extends f_imageButton, fa_selected
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

	_onSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		
		this.f_setSelected(!this.f_isSelected());
		
		return false;
	},
	_a_updateSelected: function() {
		this._updateImage();
	}
}

var f_imageCheckButton=new f_class("f_imageCheckButton", null, null, __prototype, f_imageButton, fa_selected);
