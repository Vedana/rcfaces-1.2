/*
 * $Id$
 *
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */
 
/**
 * Aspect Immediate.
 *
 * @aspect fa_immediate
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
/*
	f_finalize: function() {
		this._immediate=undefined;  // boolean
	},
	*/
	/**
	 * @method public
	 * @return boolean
	 */
	f_isImmediate: function() {
		if (this._immediate===undefined) {
			// Appel depuis le constructor de l'objet !
			var b=f_core.GetAttribute(this, "v:immediate");
			
			this._immediate=(b)?true:false;
		}
		
		return this._immediate;
	},
	/**
	 * @method public
	 * @param optional boolean set
	 * @return void
	 */
	f_setImmediate: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (this.f_isImmediate()==set) {
			return;
		}
		
		this._immediate = set;
		
		this.f_setProperty(f_prop.IMMEDIATE,this._immediate);
	},

	/**
	 * @method abstract
	 * @return void
	 */
	f_setProperty: f_class.ABSTRACT
}

var fa_immediate=new f_aspect("fa_immediate", null, __prototype);
	
