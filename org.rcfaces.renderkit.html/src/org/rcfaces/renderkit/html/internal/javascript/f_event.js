/*
 * $Id$
 */

/**
 * f_event class extends Object
 *
 * @class public f_event extends Object
 * @author Olivier Oeuillot & Joel Merlin
 * @version $Revision$
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
 * @return string
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
 * @param string id Identifier of component.
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
 
f_event.f_getName=function() {
	return "f_event";
}
 
var __static = {
	
	/**
	 * @field private static final string
	 */
	_LOCK_MESSAGE: "Window has been locked.",

	/* C'est sur la WINDOW !!!!!!
	 * @field private static boolean
	 *
	_EvtLock: undefined,
	*/
	
	/* C'est sur la WINDOW !!!!
	 * @field private static boolean
	 *
	_EvtLockMode: true,
	*/
	
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
		return window._EvtLockMode;
	},
	/**
	 * @method hidden static
	 */
	SetEventLockedMode: function(set) {
		f_core.Assert(set===undefined || typeof(set)=="boolean", "f_event.SetEventLockedMode: invalid set parameter ("+set+").");
		if (set===undefined) {
			set=true;
		}
		
		window._EvtLockMode = (set)?true:false;
	},
	/**
	 * @method hidden static
	 * @return boolean Returns <code>true</code> if lock is setted !
	 */
	GetEventLocked: function(showAlert, win) {
		if (!win) {
			win=window;
		}
		
		if (win._EvtLockMode===false || !win._EvtLock) {
			return false;
		}
		
		f_core.Debug("f_event", "Events are Locked, stop action ! (mode="+win._EvtLockMode+" state="+win._EvtLock+" show="+showAlert+")");

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
	SetEventLocked: function(win, set) {
		if (!win) {
			win=window;
		}
				
		if (set===undefined) {
			set=true;
		}

		
		f_core.Debug("f_core", "Change lock event state (new state="+set+" old="+win._EvtLock+")");
	
		if (set) {
			if (win._EvtLock) {
				alert("Several locks !!!!");
			}
		}
		
		win._EvtLock = (set)?true:undefined;
	}
}

for(var p in __static) {
	f_event[p]=__static[p];
}
