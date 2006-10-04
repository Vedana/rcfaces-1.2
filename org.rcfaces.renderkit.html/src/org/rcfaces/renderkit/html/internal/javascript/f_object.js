/*
 * $Id$
 */

/**
 * class f_object
 *
 * @class public f_object extends Object
 * @author Joel Merlin & Olivier Oeuillot
 * @version $Revision$ $Date$
 */
var __prototype = {

	/**
	 * @method public
	 */
	f_object: function() {
	},

	/**
	 * @method protected
	 */
	f_finalize: function() {
	},

	/**
	 * Returns the class of the component.
	 * 
	 * @method public
	 * @return f_class Class of the component.
	 */
	f_getClass: function() {
		return this._kclass;
	}
}

var f_object=new f_class("f_object", null, null, __prototype);
