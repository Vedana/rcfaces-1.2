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
var __prototype = {
	/**
	 * @method protected
	 * @param f_event event
	 * @return boolean
	 */
	f_performImageSelection: function(event) {		
		var form = f_core.GetParentForm(this);
		if (form) {
			f_core.Debug(f_imageSubmitButton, "f_performImageSelection: Submit form !");
			form.submit();
			
		} else {
			f_core.Error(f_imageSubmitButton, "f_performImageSelection: FORM component was not found !");
		}
				
		return false;
	}
}

new f_class("f_imageSubmitButton", null, null, __prototype, f_imageButton);
