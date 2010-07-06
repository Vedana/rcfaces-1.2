/*
 * $Id$
 */

/**
 * f_dndEvent class
 *
 * @class public f_dndEvent extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {


	/**
	 * @field public static Number
	 */
    NONE_DND_EFFECT: 0x00,

	/**
	 * @field public static Number
	 */
    DEFAULT_DND_EFFECT: 0x01,

	/**
	 * @field public static Number
	 */
    COPY_DND_EFFECT: 0x02,

	/**
	 * @field public static Number
	 */
	LINK_DND_EFFECT: 0x04,

	/**
	 * @field public static Number
	 */
    MOVE_DND_EFFECT: 0x08,

	/**
	 * @field public static String
	 */
	DRAG_INIT_STAGE: "dragInit",
	
	/**
	 * @field public static String
	 */
	DRAG_START_STAGE: "dragStart",

	/**
	 * @field public static String
	 */
	DRAG_ABORTED_STAGE: "dragAborted",
	
	/**
	 * @field public static String
	 */
	DROP_REQUEST_STAGE: "dropRequest",
	

	/**
	 * @field public static String
	 */
	DRAG_REQUEST_STAGE: "dragRequest",

	/**
	 * @field public static String
	 */
	DROP_CANCELED_STAGE: "dropCanceled",
	

	/**
	 * @field public static String
	 */
	DRAG_CANCELED_STAGE: "dragCanceled",
	

	/**
	 * @field public static String
	 */
	DROP_COMPLETE_STAGE: "dropComplete",
	

	/**
	 * @field public static String
	 */
	DRAG_COMPLETE_STAGE: "dragComplete",
	
	/**
	 * @field public static String
	 */
	DRAG_OVER_STAGE: "dragOver",

	/**
	 * @field public static String
	 */
	DROP_OVER_STAGE: "dropOver",

	/**
	 * @field public static String
	 */
	DRAG_OVER_CANCELED_STAGE: "dragOverCanceled",

	/**
	 * @field public static String
	 */
	DROP_OVER_CANCELED_STAGE: "dropOverCanceled",
	
	/**
	 * Convert a f_event event to a f_dndEvent event to get more informations.
	 * 
	 * @method public static
	 * @param f_event event;
	 * @return f_dndEvent
	 */
	As: function(event) {
		return new f_dndEvent(event);
	}
};

var __members = {
		
	/**
	 * @field private Object
	 */
	_detail: undefined,
	
	/**
	 * @field private f_dragAndDropEngine
	 */
	_engine: undefined,
	
	/**
	 * @field private String
	 */
	_stage: undefined,
	
	/**
	 * @method private
	 * @param f_event event
	 */
	f_dndEvent: function(event) {
		this._detail=event.f_getValue();
		this._engine=event.f_getItem();
		this._stage=event.f_getDetail();
	},

	/**
	 * @method public
	 * @return f_component
	 */
	f_getTargetComponent: function() {
		return this._detail._targetComponent;
	},

	/**
	 * @method public
	 * @return any
	 */
	f_getTargetItem: function() {
		return this._detail._targetItem;
	},

	/**
	 * @method public
	 * @return String
	 */
	f_getTargetItemValue: function() {
		return this._detail._targetItemValue;
	},

	/**
	 * @method public
	 * @return f_component
	 */
	f_getSourceComponent: function() {
		return this._detail._sourceComponent;
	},

	/**
	 * @method public
	 * @return any
	 */
	f_getSourceItem: function() {
		return this._detail._sourceItem;
	},

	/**
	 * @method public
	 * @return String
	 */
	f_getSourceItemValue: function() {
		return this._detail._sourceItemValue;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getStage: function() {
		return this._stage;
	},
	/**
	 * @method public
	 * @return f_dragAndDropEngine
	 */
	f_getDragAndDropEngine: function() {
		return this._engine;
	},
	/**
	 * @method public
	 * @return Number
	 */
	f_getEffect: function() {
		return this._detail._effect;
	},
	/**
	 * @method public
	 * @return String[]
	 */
	f_getTypes: function() {
		return this._detail._types;
	}
};

new f_class("f_dndEvent", {
	statics: __statics,
	members: __members
});
