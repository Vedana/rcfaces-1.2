/*
 * $Id$
 */

/**
 * f_textArea class
 *
 * @class public f_textArea extends f_textEntry
 * @author Olivier Oeuillot
 * @version $Revision$
 */
 
var __prototype= {
	f_initializeInput: function() {
		var tagName=this.tagName;
		if (tagName && tagName.toUpperCase()=="TEXTAREA") {
			return null;
		}
		
		var inputs=this.getElementsByTagName("TEXTAREA");
		if (inputs.length==0) {
			f_core.Error(f_input, "No TextArea tag into this component !");
			return null;
		}
		
		f_core.Assert(inputs.length==1, "f_textArea.f_initializeInput: More than one TextArea tag into this button component !");
		return inputs[0];
	}
}

var f_textArea = new f_class("f_textArea", null, null, __prototype, f_textEntry);
