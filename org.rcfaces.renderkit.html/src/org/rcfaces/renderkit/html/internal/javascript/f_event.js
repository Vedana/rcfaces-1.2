/*
 * $Id$
 */

/**
 * f_event class
 *
 * @class public final f_event extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

/**
 * @method public
 * @param f_object component
 * @param String type
 * @param optional Event jsEvent
 * @param optional Object item
 * @param optional any value
 * @param optional fa_selectionProvider selectionProvider
 * @param optional any detail
 */
function f_event(component, type, jsEvent, item, value, selectionProvider, detail) {
	f_core.Assert(typeof(type)=="string", "Bad type of event '"+type+"'");
	f_core.Assert(component.tagName || component._kclass, "Bad component '"+component+"'.");

	this._type = type;
	this._component = component;
	this._jsEvent = jsEvent;
	this._item = item;
	this._value = value;
	this._selectionProvider = selectionProvider;
	this._detail = detail;
}

f_event.prototype= {
	/**
	 * @method public
	 * @return String
	 */
	f_getType: function() {
		return this._type;
	},
	
	/**
	 * @method public
	 * @return Event
	 */
	f_getJsEvent: function() {
		return this._jsEvent;
	},
	
	/**
	 * @method public
	 * @return Object
	 */
	f_getItem: function() {
		return this._item;
	},
	
	/**
	 * @method public
	 * @return f_object
	 */
	f_getComponent: function() {
		return this._component;
	},
	
	/**
	 * @method public
	 * @return any
	 */
	f_getValue: function() {
		return this._value;
	},
	
	/**
	 * @method public
	 * @return fa_selectionProvider
	 */
	f_getSelectionProvider: function() {
		return this._selectionProvider;
	},
	
	/**
	 * @method public
	 * @return any
	 */
	f_getDetail: function() {
		return this._detail;
	},
	
	/**
	 * @method public
	 * @param String... id Identifier of component.
	 * @return f_component
	 * @see f_component#f_findComponent f_component.f_findComponent()
	 */
	f_findComponent: function(id) {
		var component=this._component;
		
		f_core.Assert(component.f_findComponent, "f_event.f_findComponent: Component '"+component+"' has no f_findComponent method.");
		
		return component.f_findComponent.apply(component, arguments);
	},
	
	/**
	 * @method public
	 * @return boolean <code>false</code> value.
	 */
	f_preventDefault: function() {
		var evt=this._jsEvent;
		f_core.Assert(evt, "Javascript Event is null !");
	
		f_core.CancelEvent(evt);
		
		return false;
	},
	
	/**
	 * @method hidden
	 */
	f_finalize: function() {
	//	this._type = undefined;  // string
		this._component = undefined; // component 
		this._jsEvent = undefined; // jsEvent
		this._item = undefined; // any
		this._value = undefined; // any
		this._detail = undefined; // any
		this._selectionProvider = undefined; // fa_selectionProvider
	},
	 
	toString: function() {
		return "[f_event type='"+this._type+"' component='"+this._component+"' value='"+this._value+"' item='"+this._item+"' jsEvent='"+this._jsEvent+"']";
	}
}
 
var __static = {
	/**
	 * @field private static final String
	 */
	_LOCK_MESSAGE: "Window has been locked.",

	/**
	 * @field hidden static final number
	 */
	SUBMIT_LOCK: 1,

	/**
	 * @field hidden static final number
	 */
	MODAL_LOCK: 2,

	/**
	 * @field hidden static final number
	 */
	POPUP_LOCK: 4,

	/* 
	 * @field private static number
	 */
	_EvtLock: 0,

	
	/*
	 * @field private static boolean
	 */
	_EvtLockMode: true,
	
	
	/**
	 * @field private static f_event
	 */
	_Event: undefined,


	// Class public constants

	/**
	 * Blur event name.
	 *
	 * @field public static final String
	 */
	BLUR:		"blur",

	/**
	 * Change event name.
	 *
	 * @field public static final String
	 */
	CHANGE:		"change",

	/**
	 * Check event name.
	 *
	 * @field public static final String
	 */
	CHECK:		"check",

	/**
	 * Close event name.
	 *
	 * @field public static final String
	 */
	CLOSE:		"close",

	/**
	 * Double-click event name.
	 *
	 * @field public static final String
	 */
	DBLCLICK:	"dblclick",

	/**
 	 * Focus event name.
 	 *
	 * @field public static final String
	 */
	FOCUS:		"focus",

	/**
	 * Initialize event name.
	 *
	 * @field public static final String
	 */
	INIT:		"init",

	/**
	 * Key down event name.
	 *
	 * @field public static final String
	 */
	KEYDOWN:	"keydown",

	/**
	 * Key press event name.
	 *
	 * @field public static final String
	 */
	KEYPRESS:	"keypress",

	/**
	 * Key up event name.
	 *
	 * @field public static final String
	 */
	KEYUP:		"keyup",

	/**
	 * Load event name.
	 *
	 * @field public static final String
	 */
	LOAD:	 	"load",

	/**
	 * Menu event name.
	 *
	 * @field public static final String
	 */
	MENU:	 	"menu",

	/**
	 * Mouse button down event name.
	 *
	 * @field public static final String
	 */
	MOUSEDOWN:	"mousedown",

	/**
	 * Mouse out event name.
	 *
	 * @field public static final String
	 */
	MOUSEOUT:	"mouseout",

	/**
	 * Mouse over event name.
	 *
	 * @field public static final String
	 */
	MOUSEOVER:	"mouseover",
	
	/**
	 * Mouse button up event name.
	 *
	 * @field public static final String
	 */
	MOUSEUP:	"mouseup",
	
	/**
	 * Property Change event name.
	 *
	 * @field public static final String
	 */
	PROPERTY_CHANGE:	"propertyChange",

	/**
	 * Reset event name.
	 *
	 * @field public static final String
	 */
	RESET:	"reset",

	/**
	 * Selection event name.
	 *
	 * @field public static final String
	 */
	SELECTION:	"selection",

	/**
	 * Submit event name.
	 *
	 * @field public static final String
	 */
	SUBMIT:		"submit",
	
	/**
	 * Suggestion event name.
	 *
	 * @field public static final String
	 */
	SUGGESTION:	"suggestion",

	/**
	 * User event name.
	 *
	 * @field public static final String
	 */
	USER:	"user",

	/**
	 * @field public static final number
	 */
	ACTIVATE_DETAIL: 0x100,

	/**
	 * @method public static
	 * @return f_object
	 */
	GetComponent: function() { 
		var event=f_event.GetEvent();
		if(!event) {
			return null;
		}
		return event.f_getComponent();
	},
	/**
	 * @method public static
	 * @return Object
	 */
	GetItem: function() { 
		return f_event.GetEvent().f_getItem();
	},
	/**
	 * @method public static
	 * @return any
	 */
	GetValue: function() { 
		return f_event.GetEvent().f_getValue();
	},
	/**
	 * @method public static
	 * @return any
	 */
	GetSelectionProvider: function() { 
		return f_event.GetEvent().f_getSelectionProvider();
	},
	/**
	 * @method public static
	 * @return Event
	 */
	GetJsEvent: function() { 
		var event=f_event.GetEvent();
		if(!event) {
			return null;
		}
		return f_event.GetEvent().f_getJsEvent();
	},
	/**
	 * @method public static
	 * @return any
	 */
	GetDetail: function() { 
		return f_event.GetEvent().f_getDetail();
	},
	/**
	 * @method public static
	 * @return f_event
	 */
	GetEvent: function() { 
		return f_event._Event;
	},
	/**
	 * @method hidden static
	 */
	SetEvent: function(event) {
		var old=f_event._Event;
		
		f_event._Event=event;
		
		return old;
	},
	/**
	 * @method hidden static
	 */
	GetEventLockedMode: function() {
		return f_event._EvtLockMode;
	},
	/**
	 * @method hidden static
	 */
	SetEventLockedMode: function(set) {
		f_core.Assert(set===undefined || typeof(set)=="boolean", "f_event.SetEventLockedMode: invalid set parameter ("+set+").");
		if (set===undefined) {
			set=true;
		}
		
		f_event._EvtLockMode = (set)?true:false;
	},
	/**
	 * @method hidden static
	 * @return boolean Returns <code>true</code> if lock is setted !
	 */
	GetEventLocked: function(showAlert, mask) {
	
		if (!window.f_event) {
			return true;
		}

		var currentLock=f_event._EvtLock;

		if (mask) {
			currentLock &= ~mask;
		}
		if (!currentLock) {
			return false;
		}

		if (currentLock==f_event.MODAL_LOCK) {
			if (!f_core.VerifyModalWindow()) {
				return false; // finalement c'est pas verouillé !
			}
		
			// On passe le focus dessus normalement, donc pas de boite d'alerte !
			return true;
		}
	
		if (currentLock==f_event.POPUP_LOCK) {
			f_core.Debug("f_event", "Catch popup lock (mask="+mask+" show="+showAlert+")");
			return true;
		}
	
		var currentMode=f_event._EvtLockMode;
		if (currentLock==f_event.SUBMIT_LOCK && currentMode===false) {
			return false;
		}
		
		f_core.Debug("f_event", "Events are locked, break current process ! (mode="+currentMode+" state="+currentLock+" show="+showAlert+")");

		if (showAlert===false) {
			return true;
		}
		
		var s=f_env.Get("CORE_LOCK_MESSAGE");
		if (!s) {
			var bundle=f_resourceBundle.Get(f_event);
			if (bundle) {
				s=bundle.f_get("LOCK_MESSAGE");
			}
			
			if (!s) {
				s=f_event._LOCK_MESSAGE;
			}
		}
		
		if (window.console && console.trace) {
			console.trace();
		}
		
		alert(s);

		return true;
	},
	/**
	 * @method hidden static
	 */
	EnterEventLock: function(set) {
		var currentLock=f_event._EvtLock;

		f_core.Assert(typeof(set)=="number", "f_event.EnterEventLock: Lock type is invalid ! ("+set+")");
		
		if (currentLock & set) {
			f_core.Error("f_event", "Several same lock of type "+set+" !");
			return;
		}
		
		f_event._EvtLock |= set;
		f_core.Debug("f_event", "Enter event lock (new state="+f_event._EvtLock+" old="+currentLock+")");
	},
	/**
	 * @method hidden static
	 */
	ExitEventLock: function(set) {
		var currentLock=f_event._EvtLock;
	
		f_core.Assert(typeof(set)=="number", "f_event.ExitEventLock: Lock type is invalid ! ("+set+")");
	
		if (!(currentLock & set)) {
			f_core.Error("f_event", "No lock for type "+set+" !");
			return;
		}
		
		f_event._EvtLock &= (~set);
		f_core.Debug("f_event", "Exit event lock (new state="+f_event._EvtLock+" old="+currentLock+")");
	},
	toString: function() {
		return "[class f_event]";
	},
	/**
	 * Returns the name of the class.
	 *
	 * @method public static
	 * @return String
	 */
	f_getName: function() {
		return "f_event";
	}
}

for(var p in __static) {
	f_event[p]=__static[p];
}
