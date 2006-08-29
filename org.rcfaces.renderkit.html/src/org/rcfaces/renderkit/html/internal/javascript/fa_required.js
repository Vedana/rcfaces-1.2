/*
 * $Id$
 *
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */
 
/**
 * Aspect Required. 
 *
 * @aspect public fa_required
 * @author Olivier Oeuillot
 * @version $Revision$
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
			var b=f_core.GetAttribute(this, "v:required");
			
			this._required=(b)?true:false;
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
	
		this._a_updateRequired(set);
	},

	/**
	 * @method abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_updateRequired: f_class.ABSTRACT
}

var fa_required=new f_aspect("fa_required", null, __prototype);
