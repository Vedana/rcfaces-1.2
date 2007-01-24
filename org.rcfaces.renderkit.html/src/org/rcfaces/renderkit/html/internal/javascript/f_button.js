/*
 * $Id$
 */

/**
 * class f_button
 *
 * @class public f_button extends f_input, fa_immediate
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

	f_button: function() {
		this.f_super(arguments);

		this._returnOnSelect = true;
	}
}

new f_class("f_button", null, null, __prototype, f_input, fa_immediate);

