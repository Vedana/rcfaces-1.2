/*
 * $Id$
 */
 
/**
 * Aspect Focus style class.
 *
 * @aspect fa_focusStyleClass
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
/*
	f_finalize: function() {
		// this._focusStyleClass=undefined; // string
	},
	*/
	/**
	 * @method public
	 * @return string
	 */
	f_getFocusStyleClass: function() {
		if (this._focusStyleClass===undefined) {
			// Appel depuis le constructor de l'objet !
			this._focusStyleClass=f_core.GetAttribute(this, "v:focusStyleClass");
		}
		
		return this._focusStyleClass;
	},

	/**
	 * @method abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

var fa_focusStyleClass=new f_aspect("fa_focusStyleClass", null, __prototype);
	
