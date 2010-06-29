/*
 * $Id$
 */
 
/**
 * Droppable .
 *
 * @aspect abstract fa_droppable
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._droppable=undefined;  // boolean
	},
	*/
	/**
	 * Returns the disable state.
	 *
	 * @method public
	 * @return boolean <code>true</code> if the component is disabled.
	 */
	f_isDroppable: function() {
		if (this._droppable===undefined) {
			// Appel depuis le constructor de l'objet !
			var dropEffects=f_core.GetNumberAttribute(this, "v:dropEffects");
			
		  	this._droppable=(dropEffects!==undefined);
		  	if (this._droppable) {
		  		this._dropEffects=dropEffects;
		  		var ds=f_core.GetAttribute(this, "v:dropTypes");
		  		if (ds) {
		  			this._dropTypes=ds.split(",");
		  		}		  		
		  	}
		}
		
		return this._droppable;
	},
	
	/**
	 * @method public abstract
	 * @param Event jsEvent
	 * @param HTMLElement child element
	 * @return Object
	 */
	f_queryDropInfos: f_class.ABSTRACT
}

new f_aspect("fa_droppable", {
	members: __members
});	
