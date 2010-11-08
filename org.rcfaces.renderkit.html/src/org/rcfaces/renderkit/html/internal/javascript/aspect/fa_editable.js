/*
 * $Id$
 */
 
/**
 * Aspect Editable
 *
 * @aspect public abstract fa_editable
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	/*
	f_finalize: function() {
		// this._editable=undefined; //Boolean
	},
	*/
	/**
	 * @method public
	 * @return Boolean
	 */
	f_isEditable: function() {
		if (this._editable===undefined) {
			// Appel depuis le constructor de l'objet !
			this._editable=f_core.GetBooleanAttribute(this, "v:editable", true);
		}

		return this._editable;
	},
	/**
	 * @method public
	 * @param optional Boolean set
	 * @return void
	 */
	f_setEditable: function(set) {
		if (set!==false) {
			set=true;
		} else {
			set=!!set;
		}
		
		if (this.f_isEditable()==set) {
			return;
		}
		
		this._editable = set;
	
		this.fa_updateEditable(set);
		
		this.f_setProperty(f_prop.EDITABLE, set);
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
	fa_updateEditable: f_class.ABSTRACT
}

new f_aspect("fa_editable", {
	members: __members
});
	
