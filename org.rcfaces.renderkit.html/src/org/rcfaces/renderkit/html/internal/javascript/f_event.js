/*
 * $Id$
 */

/**
 * f_event class
 *
 * @class public f_event extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

/**
 * @method hidden
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

/**
 * @method public
 * @return String
 */
f_event.prototype.f_getType=function() {
	return this._type;
}

/**
 * @method public
 * @return Event
 */
f_event.prototype.f_getJsEvent=function() {
	return this._jsEvent;
}

/**
 * @method public
 * @return Object
 */
f_event.prototype.f_getItem=function() {
	return this._item;
}

/**
 * @method public
 * @return f_component
 */
f_event.prototype.f_getComponent=function() {
	return this._component;
}

/**
 * @method public
 * @return any
 */
f_event.prototype.f_getValue=function() {
	return this._value;
}

/**
 * @method public
 * @return any
 */
f_event.prototype.f_getSelectionProvider=function() {
	return this._selectionProvider;
}

/**
 * @method public
 * @return any
 */
f_event.prototype.f_getDetail=function() {
	return this._detail;
}

/**
 * @method public
 * @param String id Identifier of component.
 * @return f_component
 * @see f_component#f_findComponent
 */
f_event.prototype.f_findComponent=function(id) {
	var component=this._component;
	
	f_core.Assert(component.f_findComponent, "f_event.f_findComponent: Component '"+component+"' has no f_findComponent method.");
	
	return component.f_findComponent.apply(component, arguments);
}

/**
 * @method public
 * @return boolean <code>false</code> value.
 */
f_event.prototype.f_preventDefault=function() {
	var evt=this._jsEvent;
	f_core.Assert(evt, "Javascript Event is null !");

	f_core.CancelEvent(evt);
	
	return false;
}

/**
 * @method hidden
 */
f_event.prototype.f_finalize=function() {
//	this._type = undefined;  // string
	this._component = undefined; // component 
	this._jsEvent = undefined; // jsEvent
	this._item = undefined; // any
	this._value = undefined; // any
	this._detail = undefined; // any
	this._selectionProvider = undefined; // fa_selectionProvider
}
 
f_event.prototype.toString=function() {
	return "[f_event type='"+this._type+"' component='"+this._component+"' value='"+this._value+"' item='"+this._item+"' jsEvent='"+this._jsEvent+"']";
}
 
var __static = {
	
	/**
	 * @field private static final string
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
	 * @field public static final string
	 */
	BLUR:		"blur",

	/**
	 * Change event name.
	 *
	 * @field public static final string
	 */
	CHANGE:		"change",

	/**
	 * Check event name.
	 *
	 * @field public static final string
	 */
	CHECK:		"check",

	/**
	 * Close event name.
	 *
	 * @field public static final string
	 */
	CLOSE:		"close",

	/**
	 * Double-click event name.
	 *
	 * @field public static final string
	 */
	DBLCLICK:	"dblclick",

	/**
 	 * Focus event name.
 	 *
	 * @field public static final string
	 */
	FOCUS:		"focus",

	/**
	 * Initialize event name.
	 *
	 * @field public static final string
	 */
	INIT:		"init",

	/**
	 * Key down event name.
	 *
	 * @field public static final string
	 */
	KEYDOWN:	"keydown",

	/**
	 * Key press event name.
	 *
	 * @field public static final string
	 */
	KEYPRESS:	"keypress",

	/**
	 * Key up event name.
	 *
	 * @field public static final string
	 */
	KEYUP:		"keyup",

	/**
	 * Load event name.
	 *
	 * @field public static final string
	 */
	LOAD:	 	"load",

	/**
	 * Menu event name.
	 *
	 * @field public static final string
	 */
	MENU:	 	"menu",

	/**
	 * Mouse button down event name.
	 *
	 * @field public static final string
	 */
	MOUSEDOWN:	"mousedown",

	/**
	 * Mouse out event name.
	 *
	 * @field public static final string
	 */
	MOUSEOUT:	"mouseout",

	/**
	 * Mouse over event name.
	 *
	 * @field public static final string
	 */
	MOUSEOVER:	"mouseover",
	
	/**
	 * Mouse button up event name.
	 *
	 * @field public static final string
	 */
	MOUSEUP:	"mouseup",
	
	/**
	 * Property Change event name.
	 *
	 * @field public static final string
	 */
	PROPERTY_CHANGE:	"propertyChange",

	/**
	 * Reset event name.
	 *
	 * @field public static final string
	 */
	RESET:	"reset",

	/**
	 * Selection event name.
	 *
	 * @field public static final string
	 */
	SELECTION:	"selection",

	/**
	 * Submit event name.
	 *
	 * @field public static final string
	 */
	SUBMIT:		"submit",
	
	/**
	 * Suggestion event name.
	 *
	 * @field public static final string
	 */
	SUGGESTION:	"suggestion",

	/**
	 * User event name.
	 *
	 * @field public static final string
	 */
	USER:	"user",

	/**
	 * @field public static final number
	 */
	ACTIVATE_DETAIL: 0x100,

	/**
	 * @method public static
	 * @return f_component
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
	f_getName: function() {
		return "f_event";
	}
}

for(var p in __static) {
	f_event[p]=__static[p];
}
