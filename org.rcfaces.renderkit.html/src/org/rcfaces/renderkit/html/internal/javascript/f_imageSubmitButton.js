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
	 * 
	 * @method protected
	 */
	f_getMainClassName: function() {
		return "f_imageSubmitButton";
	},
	/**
	 * 
	 * @method protected
	 */
	f_disableSubmitEvent: function() {
		// Rien: le fonctionement standard
	}
}

var f_imageSubmitButton=new f_class("f_imageSubmitButton", null, null, __prototype, f_imageButton);
