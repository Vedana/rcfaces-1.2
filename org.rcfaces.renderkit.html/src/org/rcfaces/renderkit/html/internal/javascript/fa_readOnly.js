/*
 * $Id$
 */
 
/**
 * Aspect ReadOnly
 *
 * @aspect public fa_readOnly
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
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
			var b=f_core.GetAttribute(this, "v:readOnly");
			
			this._readOnly = (b)?true:false;
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

var fa_readOnly=new f_aspect("fa_readOnly", null, __prototype);
	
