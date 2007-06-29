/*
 * $Id$
 */
 
/**
 * Aspect Selected
 *
 * @aspect public fa_selected
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._selected=undefined; // boolean
	},
	*/
	/**
	 * Retourne <code>true</code> si le composant est désactivé.
	 *
	 * @method public
	 * @return boolean
	 */
	f_isSelected: function() {
		if (this._selected===undefined) {
			// Appel depuis le constructor de l'objet !
			this._selected=f_core.GetBooleanAttribute(this, "v:selected", false);
		}
		
		return this._selected;
	},
	/**
	 * Spécifie si le composant est selectionné.
	 *
	 * @method public
	 * @param optional boolean set <code>true</code> pour selectionner le composant.
	 * @return void
	 */
	f_setSelected: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isSelected()==set) {
			return;
		}
		
		this._selected = set;
	
		// On le met avant l'update, car des fois que la valeur rechange ...
		this.f_setProperty(f_prop.SELECTED, set);
	
		this.fa_updateSelected(set);
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateSelected: f_class.ABSTRACT,


	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

new f_aspect("fa_selected", {
	members: __members
});
	
