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

	/**
	 * 
	 * @method protected
	 */
	f_getMainClassName: function() {
		return "f_imageResetButton";
	},
	_onSelect: function() {
		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		
		var form = f_core.GetParentForm(this);
		if (form) {
			form.reset();
			
		} else {
			f_core.Error(f_imageResetButton, "FORM component was not found !");
		}
		
		return false;
	}
}

var f_imageResetButton=new f_class("f_imageResetButton", null, null, __prototype, f_imageButton);
