/*
 * $Id$
 */
 
/**
 * Aspect Value
 *
 * @aspect public fa_value
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._value=undefined; // string
	},
	*/
	/**
	 * Returns <code>true</code> if the component is value
	 *
	 * @method public
	 * @return String or <code>null</code> if not defined !
	 */
	f_getValue: function() {
		if (this._value===undefined) {
			// Appel depuis le constructor de l'objet !
			this._value=f_core.GetAttribute(this, "v:value", null);
		}
		
		return this._value;
	},
	/**
	 * Set value state.
	 *
	 * @method public
	 * @param String value
	 * @return void
	 */
	f_setValue: function(value) {
		f_core.Assert(value===null || typeof(value)=="string", "Value parameter must be a string or null ! ("+value+")");

		if (this.f_getValue()==value) {
			return;
		}
		
		this._value = value;
	
		if (this.fa_updateValue) {
			this.fa_updateValue(value);
		}
	
		this.f_setProperty(f_prop.VALUE, value);
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateValue: f_class.OPTIONAL_ABSTRACT,

	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

new f_aspect("fa_value", {
	members: __members
});
