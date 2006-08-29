/*
 * $Id$
 */

/**
 * class f_imageSubmitButton
 *
 * @class f_imageSubmitButton extends f_imageButton
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __prototype = {
	/**
	 * 
	 * @method protected
	 */
	f_disableSubmitEvent: function() {
		// Rien: le fonctionement standard
	}
}

var f_imageSubmitButton=new f_class("f_imageSubmitButton", null, null, __prototype, f_imageButton);
