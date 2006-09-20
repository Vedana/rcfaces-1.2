/*
 * $Id$
 */

/**
 * f_textArea class
 *
 * @class public f_textArea extends f_textEntry
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype= {
	/**
	 * 
	 * @method protected
	 * @return string
	 */
	f_getInputTagName: function() {
		return "TEXTAREA";
	}
}

var f_textArea = new f_class("f_textArea", null, null, __prototype, f_textEntry);
