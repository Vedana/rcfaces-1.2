/*
 * $Id$
 */
 
/**
 * Aspect Focus style class.
 *
 * @aspect abstract fa_focusStyleClass
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._focusStyleClass=undefined; // string
	},
	*/
	/**
	 * @method public
	 * @return String
	 */
	f_getFocusStyleClass: function() {
		if (this._focusStyleClass===undefined) {
			// Appel depuis le constructor de l'objet !
			this._focusStyleClass=f_core.GetAttribute(this, "v:focusStyleClass");
		}
		
		return this._focusStyleClass;
	},

	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

new f_aspect("fa_focusStyleClass", {
	members: __members
});

