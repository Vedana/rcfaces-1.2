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
			form.submit();
			
		} else {
			f_core.Error(f_imageSubmitButton, "FORM component was not found !");
		}
				
		return true;
	}
}

new f_class("f_imageSubmitButton", null, null, __prototype, f_imageButton);
