/*
 * $Id$
 */

/**
 * f_dragAndDropInfo class
 *
 * @class public abstract f_dragAndDropInfo extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	
	/**
	 * @field private f_dragAndDropInfo
	 */
	_dragAndDropInfoParent: undefined,
	
	/**
	 * @field private f_dragAndDropEngine
	 */
	_dragAndDropEngine: undefined,
	
	
	f_dragAndDropInfo: function(dragAndDropEngine) {
		this.f_super(arguments);
		
		this._dragAndDropEngine=dragAndDropEngine;
		
	},
	f_finalize: function() {
		this.f_super(arguments);
		
		this._dragAndDropEngine=undefined;		
		this._dragAndDropInfoParent=undefined;
	},
	/**
	 * @method public
	 * @return void
	 */
	f_start: function() {
		f_core.Debug(f_dragAndDropInfo, "f_start: calling ...");
		
		if (this._dragAndDropInfoParent) {
			this._dragAndDropInfoParent.f_start();
		}
	},
	/**
	 * @method public
	 * @param f_dragAndDropInfo dragAndDropInfoParent
	 * @return void
	 */
	f_setParent: function(dragAndDropInfoParent) {
		this._dragAndDropInfoParent=dragAndDropInfoParent;
	},
	/**
	 * @method public
	 * @param String[] types
	 * @param Number effect
	 * @param f_component targetComponent
	 * @param any targetItem
	 * @param Object targetItemValue
	 * @param Map<String, Object> additionnal informations from target
	 * @return void
	 */
	f_updateTarget: function(types, effect, targetComponent, targetItem, targetItemValue, infos) {
		f_core.Debug(f_dragAndDropInfo, "f_updateTarget: types="+types+" effect="+effect+" targetComponent='"+targetComponent+"' targetItem='"+targetItem+"' targetItemValue='"+targetItemValue+"'");

		if (this._dragAndDropInfoParent) {
			this._dragAndDropInfoParent.f_updateTarget(types, effect, targetComponent, targetItem, targetItemValue, infos);
		}
	},

	/**
	 * @method public
	 * @param Number newPositionX
	 * @param Number newPositionY
	 * @return void
	 */
	f_move: function(newPositionX, newPositionY) {
		f_core.Debug(f_dragAndDropInfo, "f_move: newPositionX="+newPositionX+" newPositionY="+newPositionY);

		if (this._dragAndDropInfoParent) {
			this._dragAndDropInfoParent.f_move(newPositionX, newPositionY);
		}
	},

	/**
	 * @method public
	 * @return void
	 */
	f_end: function() {
		f_core.Debug(f_dragAndDropInfo, "f_end: calling ...");
		
		if (this._dragAndDropInfoParent) {
			this._dragAndDropInfoParent.f_end();
		}
	},
	/**
	 * @method protected
	 * @return f_dragAndDropEngine
	 */
	f_getDragAndDropEngine: function() {
		return this._dragAndDropEngine;
	}
};
		
new f_class("f_dragAndDropInfo", {
	extend: f_object,
	members: __members
});