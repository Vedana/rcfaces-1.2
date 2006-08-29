/*
 * $Id$
 *
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 *
 */

/**
 * class f_button
 *
 * @class public f_button extends f_input, fa_immediate
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __prototype = {

	f_button: function() {
		this.f_super(arguments);

		this._returnOnSelect = true;
	}
}

var f_button=new f_class("f_button", null, null, __prototype, f_input, fa_immediate);

