/*
 * $Id$
 */
 
/**
 * Draggable .
 *
 * @aspect public abstract fa_draggable
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._draggable=undefined;  // Boolean
	},
	*/
	/**
	 * Returns the disable state.
	 *
	 * @method public
	 * @return Boolean <code>true</code> if the component is disabled.
	 */
	f_isDraggable: function() {
		if (this._draggable===undefined) {
			// Appel depuis le constructor de l'objet !
			var dragEffects=f_core.GetNumberAttribute(this, "v:dragEffects");
			
		  	this._draggable=(dragEffects!==undefined);
		  	if (this._draggable) {
		  		this._dragEffects=dragEffects;
		  		var ds=f_core.GetAttribute(this, "v:dragTypes");
		  		if (ds) {
		  			this._dragTypes=ds.split(",");
		  		}		  		
		  	}
		}
		
		return this._draggable;
	}
}

new f_aspect("fa_draggable", {
	members: __members
});	
