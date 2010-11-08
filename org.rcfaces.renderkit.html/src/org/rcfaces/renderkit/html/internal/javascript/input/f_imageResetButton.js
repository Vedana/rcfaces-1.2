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

var __members = {

	/**
	 * @method protected
	 * @param f_event event
	 * @return Boolean
	 */
	f_imageButtonSelectEnd: function(event) {
		var form = f_core.GetParentForm(this);
		if (form) {
			form.reset();
			
		} else {
			f_core.Error(f_imageResetButton, "f_performImageSelection: FORM component was not found !");
		}
		
		return true;
	}
}

new f_class("f_imageResetButton", {
	extend: f_imageButton,
	members: __members
});
