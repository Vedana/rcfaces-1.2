/*
 * $Id$
 */
 
/**
 * Aspect Immediate.
 *
 * @aspect abstract fa_immediate
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		this._immediate=undefined;  // boolean
	},
	*/
	/**
	 * @method public
	 * @return boolean
	 */
	f_isImmediate: function() {
		if (this._immediate===undefined) {
			// Appel depuis le constructor de l'objet !
			this._immediate=f_core.GetBooleanAttribute(this, "v:immediate", false);
		}
		
		return this._immediate;
	},
	/**
	 * @method public
	 * @param optional boolean set
	 * @return void
	 */
	f_setImmediate: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isImmediate()==set) {
			return;
		}
		
		this._immediate = set;
		
		this.f_setProperty(f_prop.IMMEDIATE,this._immediate);
	},

	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

new f_aspect("fa_immediate", {
	members: __members
});	
