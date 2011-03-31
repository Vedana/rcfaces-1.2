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
		// this._droppable=undefined;  // Boolean
	},
	*/
	/**
	 * Returns the disable state.
	 *
	 * @method public
	 * @return Boolean <code>true</code> if the component is disabled.
	 */
	f_isDroppable: function() {
		if (this._droppable===undefined) {
			// Appel depuis le constructor de l'objet !
			var dropEffects=f_core.GetNumberAttribute(this, f_core._VNS+":dropEffects");
			
		  	this._droppable=(dropEffects!==undefined);
		  	if (this._droppable) {
		  		this._dropEffects=dropEffects;
		  		var ds=f_core.GetAttribute(this, f_core._VNS+":dropTypes");
		  		if (ds) {
		  			this._dropTypes=ds.split(",");
		  		}		  		
		  	}
		}
		
		return this._droppable;
	},
	
	/**
	 * @method public abstract
	 * @param f_dragAndDropEngine dragAndDropEngine
	 * @param Event jsEvent
	 * @param HTMLElement child element
	 * @return Object
	 */
	f_queryDropInfos: f_class.ABSTRACT,
	
	/**
	 * @method public abstract
	 * @param f_dragAndDropEngine dragAndDropEngine
	 * @return void
	 */
	f_releaseDropInfos: f_class.ABSTRACT,
	
	/**
	 * @method public abstract
	 * @param f_dragAndDropEngine dragAndDropEngine
	 * @param Object item
	 * @return void
	 */
	f_overDropInfos:  f_class.ABSTRACT,	
	
	/**
	 * @method public abstract
	 * @param f_dragAndDropEngine dragAndDropEngine
	 * @param Object item
	 * @return void
	 */
	f_outDropInfos:  f_class.ABSTRACT
};

new f_aspect("fa_droppable", {
	members: __members
});	
