/*
 * $Id$
 */
 
/**
 * Aspect Required. 
 *
 * @aspect public fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
/*
	f_finalize: function() {
		// this._required=undefined; //boolean
	},
	*/
	/**
	 * @method public
	 * @return boolean
	 */
	f_isRequired: function() {
		if (this._required===undefined) {
			// Appel depuis le constructor de l'objet !
			this._required=f_core.GetBooleanAttribute(this, "v:required", false);
		}

		return this._required;
	},
	/**
	 * @method public
	 * @param boolean set
	 * @return void
	 */
	f_setRequired: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isRequired()==set) {
			return;
		}
		
		this._required = set;
		
		// On le met avant l'update, car des fois que la valeur rechange ...
		this.f_setProperty(f_prop.REQUIRED,this._required);
	
		this.fa_updateRequired(set);
	},

	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateRequired: f_class.ABSTRACT
}

new f_aspect("fa_required", null, __prototype);
