/*
 * $Id$
 */

/**
 * class f_imageSubmitButton
 *
 * @class f_imageSubmitButton extends f_imageButton
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
		/*	
		var form = f_core.GetParentForm(this);
		if (form) {
			f_core.Debug(f_imageSubmitButton, "f_performImageSelection: Submit form !");
			
		} else {
			f_core.Error(f_imageSubmitButton, "f_performImageSelection: FORM component was not found !");
		}
	*/
		f_core.SubmitEvent(event);
				
		return false;
	}
}

new f_class("f_imageSubmitButton", {
	extend: f_imageButton,
	members: __members
});
