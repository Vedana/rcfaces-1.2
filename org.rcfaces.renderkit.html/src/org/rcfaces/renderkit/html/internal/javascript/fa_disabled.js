/*
 * $Id$
 */
 
/**
 * Aspect Disabled
 *
 * @aspect fa_disabled
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
/*
	f_finalize: function() {
		// this._disabled=undefined;  // boolean
	},
	*/
	/**
	 * Returns <code>true</code> if the component is disabled
	 *
	 * @method public
	 * @return boolean
	 */
	f_isDisabled: function() {
		if (this._disabled===undefined) {
			// Appel depuis le constructor de l'objet !
		  	var b=f_core.GetAttribute(this, "v:disabled");
			
			this._disabled=(b)?true:false;
		}
		
		return this._disabled;
	},
	/**
	 * Set disabled state.
	 *
	 * @method public
	 * @param optional boolean set <code>true</code> to disable the component
	 * @return void
	 */
	f_setDisabled: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isDisabled()==set) {
			return;
		}
		
		this._disabled = set;
		
		// On le met avant l'update, car des fois que la valeur rechange ...
		this.f_setProperty(f_prop.DISABLED, set);

		this.fa_updateDisabled(set);
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateDisabled: f_class.ABSTRACT,


	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

var fa_disabled=new f_aspect("fa_disabled", null, __prototype);
	
