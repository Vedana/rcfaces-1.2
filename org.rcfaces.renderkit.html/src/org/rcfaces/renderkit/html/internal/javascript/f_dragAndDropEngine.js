/*
 * $Id$
 */

/**
 * f_dragAndDropEngine class
 *
 * @class public f_dragAndDropEngine extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
		
	/**
	 * @field private static final f_dragAndDropEngine
	 */	
	_Current: undefined,
	
	/**
	 * 
	 * @method hidden static
	 * @param f_component component 
	 * @param Object item
	 * @param any itemValue
	 * @param HTMLElement itemComponent
	 * @param int dragEffects
	 * @param Array dragTypes
	 * @param optional jsEvent
	 * @return boolean
	 */
	 Create: function(component, item, itemValue, itemComponent, dragEffects, dragTypes, jsEvent ) {

		var current=f_dragAndDropEngine._Current;
		if (current) {
			f_dragAndDropEngine._Current=undefined;
			
			current.f_release();
		}
		
		var engine = f_dragAndDropEngine.f_newInstance(component, item, itemValue, itemComponent, dragEffects, dragTypes);
		
		engine.f_fireEventToSource(f_event.DRAG, jsEvt, item, value, selectionProvider, detail, stage);
		
		return false;
	 }
}

var __members = {
		
	/**
	 * @field private f_component
	 */
	_component: undefined,
		
	f_dragAndDropEngine: function(component, item, itemValue, itemComponent, dragEffects, dragTypes) {
		this._component=component;
		this._item=item;
		this._itemValue=itemValue;
		this._itemComponent = itemComponent;
		this._dragEffects=dragEffects;
		this._dragTypes=dragTypes;			
	},
	
	f_fireEventToSource: function(type, jsEvent, value, detail, stage) {
		return this._component.f_fireEvent(type, jsEvent, this, value, null, detail, stage);
	}
	
	f_finalize: function() {
		
	},
	
	/**
	 * @method public
	 */
	f_release: function() {
		// Arret définitif ... on rollback les actions eventuelles
	}
}

new f_aspect("f_dragAndDropEngine", {
	statics: __statics,
	members: __members
});