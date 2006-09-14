/*
 * $Id$
 */
 
/**
 * A reset button, resets the form which contains itself.
 *
 * @class f_imageResetButton extends f_imageButton
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {

	_onSelect: function() {
		if (this.f_isReadOnly()) {
			return false;
		}
		
		var form = f_core._GetParentForm(this);
		if (form) {
			form.reset();
		}
		return false;
	}
}

var f_imageResetButton=new f_class("f_imageResetButton", null, null, __prototype, f_imageButton);
