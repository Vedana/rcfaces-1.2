/*
 * $Id$
 */
 
/**
 * Aspect ReadOnly
 *
 * @aspect public abstract fa_readOnly
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._readOnly=undefined; //boolean
	},
	*/
	/**
	 * @method public
	 * @return boolean
	 */
	f_isReadOnly: function() {
		if (this._readOnly===undefined) {
			// Appel depuis le constructor de l'objet !
			this._readOnly=f_core.GetBooleanAttribute(this, "v:readOnly", false);
		}

		return this._readOnly;
	},
	/**
	 * @method public
	 * @param optional boolean set
	 * @return void
	 */
	f_setReadOnly: function(set) {
		if (set!==false) {
			set=true;
		} else {
			set=!!set;
		}
		
		if (this.f_isReadOnly()==set) {
			return;
		}
		
		this._readOnly = set;
	
		this.fa_updateReadOnly(set);
		
		this.f_setProperty(f_prop.READONLY,this._readOnly);
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
	fa_updateReadOnly: f_class.ABSTRACT
}

new f_aspect("fa_readOnly", {
	members: __members
});
	
