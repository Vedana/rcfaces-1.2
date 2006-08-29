/*
 * $Id$
 */
 
/**
 * Aspect Collapsed
 *
 * @aspect fa_collapsed
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __prototype = {
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
			var b=f_core.GetAttribute(this, "v:collapsed");
			
			this._collapsed = (b)?true:false;
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
	
		this._a_updateCollapsed(set);
		
		this.f_setProperty(f_prop.COLLAPSED,this._collapsed);
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
	_a_updateCollapsed: f_class.ABSTRACT
}

var fa_collapsed=new f_aspect("fa_collapsed", null, __prototype);
	
