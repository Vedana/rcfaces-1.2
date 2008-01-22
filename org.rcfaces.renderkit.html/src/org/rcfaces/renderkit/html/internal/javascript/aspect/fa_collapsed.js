/*
 * $Id$
 */
 
/**
 * Aspect Collapsed
 *
 * @aspect abstract fa_collapsed
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._collapsed=undefined; // boolean
	},
	*/
	/**
	 * @method public
	 * @return boolean
	 */
	f_isCollapsed: function() {
		if (this._collapsed===undefined) {
			// Appel depuis le constructor de l'objet !
			this._collapsed =f_core.GetBooleanAttribute(this, "v:collapsed", false);
		}

		return this._collapsed;
	},
	/**
	 * @method public
	 * @param optional boolean set
	 * @return void
	 */
	f_setCollapsed: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isCollapsed()==set) {
			return;
		}
		
		this._collapsed = set;
	
		this.fa_updateCollapsed(set);
		
		this.f_setProperty(f_prop.COLLAPSED, set);
	},

	/**
	 * @method public abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateCollapsed: f_class.ABSTRACT
}

new f_aspect("fa_collapsed", {
	members: __members
});
