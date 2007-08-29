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

var __members= {
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
	 f_event: function(component, type, jsEvent, item, value, selectionProvider, detail) {
		f_core.Assert(typeof(type)=="string", "Bad type of event '"+type+"'");
		f_core.Assert(component && (component.tagName || component._kclass), "Bad component '"+component+"'.");
	
		this._type = type;
		this._component = component;
		this._jsEvent = jsEvent;
		this._item = item;
		this._value = value;
		this._selectionProvider = selectionProvider;
		this._detail = detail;
	},
	
	/**
	 * Returns the type of event.
	 * @method public
	 * @return String The type of event.
	 */
	f_getType: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getType: Invalid number of parameter");
		
		return this._type;
	},
	
	/**
	 * Returns the Javascript event if any.
	 *
	 * @method public
	 * @return Event The Javascript event.
	 */
	f_getJsEvent: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getJsEvent: Invalid number of parameter");
		
		return this._jsEvent;
	},
	
	/**
	 * Returns the item associated to the event.
	 *
	 * @method public
	 * @return Object An item.
	 */
	f_getItem: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getItem: Invalid number of parameter");
		
		return this._item;
	},
	
	/**
	 * Returns the component associated to the event.
	 *
	 * @method public
	 * @return f_object The component associated to the event.
	 */
	f_getComponent: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getComponent: Invalid number of parameter");
		
		return this._component;
	},
	
	/**
	 * Returns the value of the item associated to the event.
	 *
	 * @method public
	 * @return any The value of the item associated to the event.
	 */
	f_getValue: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getValue: Invalid number of parameter");

		return this._value;
	},
	
	/**
	 * Returns the selectionProvider wich contains the item associated to the event.
	 *
	 * @method public
	 * @return fa_selectionProvider Returns the selectionProvider associated to the item.
	 */
	f_getSelectionProvider: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getSelectionProvider: Invalid number of parameter");

		return this._selectionProvider;
	},
	
	/**
	 * Returns a detail about the event.
	 *
	 * @method public
	 * @return any A detail value.
	 */
	f_getDetail: function() {
		f_core.Assert(arguments.length==0, "f_event.f_getDetail: Invalid number of parameter");

		return this._detail;
	},
	
	/**
	 * <p>Search for and return the {@link f_component} with an <code>id</code>
     * that matches the specified search expression (if any).
	 *
	 * @method public
	 * @param String... id Identifier of component.
	 * @return HTMLElement the found {@link f_component}, or <code>null</code>
     *  if the component was not found.
	 * @see f_component#f_findComponent f_component.f_findComponent()
	 */
	f_findComponent: function(id) {
		f_core.Assert(arguments.length, "f_event.f_findComponent: Invalid number of parameter");

		var component=this._component;
		
		f_core.Assert(component.f_findComponent, "f_event.f_findComponent: Component '"+component+"' has no f_findComponent method.");
		
		return component.f_findComponent.apply(component, arguments);
	},
	/**
	 * <p>Search for and return the sibling {@link f_component} with an <code>id</code>
     * that matches the specified search expression (if any).
	 *
	 * @method public
	 * @param String... id Identifier of component.
	 * @return HTMLElement the found {@link f_component}, or <code>null</code>
     *  if the component was not found.
	 * @see f_component#f_findComponent f_component.f_findComponent()
	 */
	f_findSiblingComponent: function(id) {
		f_core.Assert(arguments.length, "f_event.f_findSiblingComponent: Invalid number of parameter");

		var component=this._component;
		
		f_core.Assert(component.f_findSiblingComponent, "f_event.f_findSiblingComponent: Component '"+component+"' has no f_findSiblingComponent method.");
		
		return component.f_findSiblingComponent.apply(component, arguments);
	},
	
	/**
	 * Prevent the default process of the event.
	 *
	 * @method public
	 * @return boolean <code>false</code> value.
	 */
	f_preventDefault: function() {
		var evt=this._jsEvent;
		f_core.Assert(evt, "Javascript Event is null !");
	
		f_core.CancelJsEvent(evt);
		
		return false;
	},
	
	/**
	 * @method hidden
	 * @return void
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
	 
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_event type='"+this._type+"' component='"+this._component+"' value='"+this._value+"' item='"+this._item+"' jsEvent='"+this._jsEvent+"']";
	}
}
 
var __statics = {
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

	/**
	 * @field private static number
	 */
	_EvtLock: 0,

	
	/**
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
	ADDITIONAL_INFORMATION:	"additionalInformation",

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
	 * Validation event name.
	 *
	 * @field public static final String
	 */
	VALIDATION:	"validation",

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
 	 * Error event name.
 	 *
	 * @field public static final String
	 */
	ERROR:		"error",

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
	 * @field public static final number
	 */
	RESET_DETAIL: 0x200,
	
	/**
	 * @method public static
	 * @return f_object
	 */
	GetComponent: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetComponent: Invalid number of parameter");
		
		var event=f_event.GetEvent();
		if(!event) {
			return null;
		}
		return event.f_getComponent();
	},
	/**
	 * @method public static
	 * @return f_object
	 */
	GetType: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetType: Invalid number of parameter");

		var event=f_event.GetEvent();
		if(!event) {
			return null;
		}
		return event.f_getType();
	},
	/**
	 * @method public static
	 * @return Object
	 */
	GetItem: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetItem: Invalid number of parameter");

		return f_event.GetEvent().f_getItem();
	},
	/**
	 * @method public static
	 * @return any
	 */
	GetValue: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetValue: Invalid number of parameter");

		return f_event.GetEvent().f_getValue();
	},
	/**
	 * @method public static
	 * @return any
	 */
	GetSelectionProvider: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetSelectionProvider: Invalid number of parameter");

		return f_event.GetEvent().f_getSelectionProvider();
	},
	/**
	 * @method public static
	 * @return Event
	 */
	GetJsEvent: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetJsEvent: Invalid number of parameter");

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
		f_core.Assert(arguments.length==0, "f_event.GetDetail: Invalid number of parameter");

		return f_event.GetEvent().f_getDetail();
	},
	/**
	 * @method public static
	 * @return f_event
	 */
	GetEvent: function() { 
		f_core.Assert(arguments.length==0, "f_event.GetEvent: Invalid number of parameter");

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
			
		} else {
			set=!!set;
		}
		
		f_event._EvtLockMode = set;
	},
	/**
	 * @method hidden static
	 * @param Event jsEvent
	 * @param optional boolean showAlert
	 * @param optional number mask
	 * @return boolean Returns <code>true</code> if lock is setted !
	 * @dontInline f_popup
	 */
	GetEventLocked: function(jsEvent, showAlert, mask) {
	
		if (window._rcfacesExiting) {
			return true;
		}
	
		//f_core.Assert(jsEvent===null || (jsEvent instanceof  Event), "f_event.GetEventLocked: Invalid jsEvent parameter ("+jsEvent+").");
		// Le type  Event n'existe pas sous IE
		f_core.Assert(showAlert===undefined || typeof(showAlert)=="boolean", "f_event.GetEventLocked: Invalid showAlert parameter ("+showAlert+").");
		f_core.Assert(mask===undefined || typeof(mask)=="number", "f_event.GetEventLocked: Invalid mask parameter ("+mask+").");

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

		if (f_event._EvtLock & f_event.POPUP_LOCK) {
			if (f_popup.VerifyLock()===false) {
				// FInalement nous ne sommes plus en LOCK ...
				// 					
				currentLock=f_event._EvtLock;
				if (mask) {
					currentLock &= ~mask;
				}
				if (!currentLock) {
					return false;
				}
				
			} else if (jsEvent) {
				var target=jsEvent.target;
				if (!target) {
					target = jsEvent.srcElement;
				}
			
				var ret;
				if (target) {
					ret=f_popup.IsChildOfDocument(target);
				}
				
				f_core.Debug(f_event, "GetEventLocked: Search popup child: target="+target+" return="+ret);

				if (ret) {
					// C'est un composant dans la popup !
					return false;
				}

			} else {
				f_core.Debug(f_event, "GetEventLocked: Can not test popup case !");
			}
		}
		
		/*
		if (currentock==f_event.POPUP_LOCK) {
			var ret=f_popup.VerifyLock();
			
			if (ret!==undefined) {
				f_core.Debug(f_event, "GetEventLocked: Catch popup lock (mask="+mask+" show="+showAlert+") returns "+ret);
				return ret;
			}

			currentLock=f_event._EvtLock;
			if (mask) {
				currentLock &= ~mask;
			}
			if (!currentLock) {
				return false;
			}
		}
		*/
	
		var currentMode=f_event._EvtLockMode;
		if (currentLock==f_event.SUBMIT_LOCK && currentMode===false) {
			return false;
		}
		
		f_core.Debug(f_event, "GetEventLocked: Events are locked, break current process ! (mode="+currentMode+" state="+currentLock+" show="+showAlert+")");

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
			
		/*
		if (window.console && console.trace) {
			
		}
		*/
		
		if (f_core.IsDebugEnabled(f_event)) {
			if (jsEvent) {
				s+="\nJSEVENT informations: type="+jsEvent.type;
				
				if (jsEvent.target) {
					s+="\ntarget=["+jsEvent.target.nodeType+"]"+jsEvent.target.tagName+"#"+jsEvent.target.id+"."+jsEvent.target.className
				}				
				if (jsEvent.currentTarget) {
					s+="\ncurrentTarget=["+jsEvent.currentTarget.nodeType+"]"+jsEvent.currentTarget.tagName+"#"+jsEvent.currentTarget.id+"."+jsEvent.currentTarget.className
				}
				if (jsEvent.fromElement) {
					s+="\nfromElement=["+jsEvent.fromElement.nodeType+"]"+jsEvent.fromElement.tagName+"#"+jsEvent.fromElement.id+"."+jsEvent.fromElement.className;
				}
				if (jsEvent.srcElement) {
					s+="\nsrcElement=["+jsEvent.srcElement.nodeType+"]"+jsEvent.srcElement.tagName+"#"+jsEvent.srcElement.id+"."+jsEvent.srcElement.className;
				}
				if (jsEvent.toElement) {
					s+="\ntoElement=["+jsEvent.toElement.nodeType+"]"+jsEvent.toElement.tagName+"#"+jsEvent.toElement.id+"."+jsEvent.toElement.className;
				}
			}
		
			f_core.Debug(f_event, "Popup event ALERT:\n"+s);
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
		
		return f_event._EvtLock;
	}
}
new f_class("f_event", {
	statics: __statics,
	members: __members,
	systemClass: true
});
