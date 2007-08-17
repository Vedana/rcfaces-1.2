/*
 * $Id$
 */

/**
 * fa_eventTarget class
 *
 * @aspect public abstract fa_eventTarget
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
	/**
	 * Last event identifier (for debug)
	 * 
	 * @field private static number
	 */
	 _EventId: undefined,
	/**
	 * 
	 * @method private static
	 * @param f_event event 
	 * @return boolean
	 */
	 _Submit: function(event) {
	 	f_core.Submit(event);
	 	return false;
	 }
}

var __members = {
	
	f_finalize: function() {
		var actionLists=this._actionLists;
		if (!actionLists) {
			return;
		}
		
		this._actionLists=undefined;
//		this._hasInitListeners=undefined; // boolean
//		this._forcedEventReturns=undefined; // Map<String, boolean>
		
		var toDestroy=new Array;
		
		var clearDomEvent=this.f_clearDomEvent;
		for(var type in actionLists) {
			if (clearDomEvent) {
				clearDomEvent.call(this, type, this);
			}
			toDestroy.push(actionLists[type]);
		}
					
		f_classLoader.Destroy.apply(f_classLoader, toDestroy);
	},
	/**
	 * 
	 * @method hidden
	 * @param Object eventTypes 
	 */
	f_initEventAtts: function(eventTypes) {
		var value=f_core.GetAttribute(this, "v:events");
		if (!value) {
			return;
		}
		
		var evts=value.split("|");
		for(var j=0;j<evts.length;j++) {
			var commands=f_core.ParseParameters(evts[j], new Array);
			
			f_core.Assert(commands.length>1, "fa_eventTarget.f_initEventAtts: Invalid events command '"+commands+"' for component '"+this.id+"'.");
			
			var eventName=commands[0];
			var eventType=eventTypes[eventName];
			f_core.Assert(eventType, "fa_eventTarget.f_initEventAtts: Unknown eventType '"+eventName+"' for component '"+this.id+"'.");
			
			f_core.Debug(fa_eventTarget, "f_initEventAtts: Register event '"+eventName+"' on component '"+this.id+"' => "+commands);

			if (commands.length==2 && commands[1]=="submit") {
				this.f_addEventListener(eventType, fa_eventTarget._Submit);
				continue;
			}

			for(var i=1;i<commands.length;i++) {
				var command=commands[i];
				
				this.f_addEventListener(eventType, command);
			}
		}
	},	
	/**
	 * 
	 * @method protected
	 * @param Event jsEvent
	 * @param optional boolean showAlert
	 * @param optional number event type mask
	 * @return boolean
	 */
	f_getEventLocked: function(jsEvent, showAlert, mask) {

		if (window._rcfacesExiting) {
			return true;
		}

		// f_core.Assert(jsEvent===null || (jsEvent instanceof Event), "fa_eventTarget.f_getEventLocked: Invalid jsEvent parameter ("+jsEvent+").");
		// Le type  Event n'existe pas sous IE
		f_core.Assert(showAlert===undefined || typeof(showAlert)=="boolean", "fa_eventTarget.f_getEventLocked: Invalid showAlert parameter ("+showAlert+").");
		f_core.Assert(mask===undefined || typeof(mask)=="number", "fa_eventTarget.f_getEventLocked: Invalid mask parameter ("+mask+").");

		return f_event.GetEventLocked(jsEvent, showAlert, mask);
	},
	/**
	 * 
	 * @method public
	 * @param String type Type of event or an f_event object
	 * @param optional Event jsEvt
	 * @param optional Object item
	 * @param optional any value
	 * @param optional fa_selectionProvider selectionProvider
	 * @param optional any detail
	 * @return boolean
	 */
	f_fireEvent: function(type, jsEvt, item, value, selectionProvider, detail) {
		var eventId=undefined;
		if (f_core.IsDebugEnabled(fa_eventTarget)) {
			if (!fa_eventTarget._EventId) {
				fa_eventTarget._EventId=0;
			}
			eventId=fa_eventTarget._EventId++;
		}
		
		var event;
		if (type instanceof f_event) {
			event=type;
			type=event._type;
			jsEvt=event._jsEvent;
			event._eventId=eventId;
		}

//		f_core.Profile(false, "fa_eventTarget.fireEvent(#"+eventId+","+type+","+this.id+")");

		try {				
			var al=this.f_getActionList(type);
			if (!al) {
				f_core.Debug(fa_eventTarget, "f_fireEvent: No listeners for event '"+type+"' on '"+this.id+"'"+((event)?("item='"+event._item+"' value='"+event._value+"' selectionProvider='"+event._selectionProvider+"'"):"")+".");
				
				// On retourne TRUE par défaut.
				return true;
			}		
			
			var disposeEvent;	
			if (!event) {
				// On créée l'event que si il y a une liste !
				event=new f_event(this, type, jsEvt, item, value, selectionProvider, detail);
				event._eventId=eventId;
				
				disposeEvent=true;
			}
			
			var ret=true;
			
			try {
				f_core.Debug(fa_eventTarget, "f_fireEvent: Fire event '"+event._type+"' on '"+this.id+"' item='"+event._item+"' value='"+event._value+"' selectionProvider='"+event._selectionProvider+"' detail='"+event._detail+"'.");
				
				ret = al.f_callActions(event);
								
			} finally {
				if (disposeEvent) {
					// Nous sommes pas forcement dans un contexte initialisé. (aprés un submit)
					// f_finalize existe peut-etre plus !
					if (window.f_classLoader) {
						f_classLoader.Destroy(event);
					}
				}
			}
	
			if (event && event._eventReturn!==undefined) {
				ret=event._eventReturn;
				f_core.Debug(fa_eventTarget, "f_fireEvent: Call actions: eventReturn is forced (by _eventReturn) to "+ret);
			}
			
			var fer=this._forcedEventReturns;
			if (fer) {
				var forcedReturn=fer[type];
				
				if (forcedReturn!==undefined) {
					ret=forcedReturn;
					f_core.Debug(fa_eventTarget, "f_fireEvent: Call actions: eventReturn is forced (by forcedValue) to "+ret);
				}
			}

			if (jsEvt) {			
				if (ret===false) {
					f_core.CancelJsEvent(jsEvt);
					//alert("Cancel event !");
				}
	
				jsEvt.returnValue = ret;
			}
			
			return ret;
		} finally {
//			f_core.Profile(true, "fa_eventTarget.fireEvent(#"+eventId+")");
		}
	},
	/**
	 * 
	 * @method hidden
	 * @param String type Type of event.
	 * @return f_actionList
	 */
	f_getActionList: function(type) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_getActionList: Bad type of event name '"+type+"'");

		var al=this._actionLists;
		if (!al) {
			return null;
		}
		
		return al[type];
	},
	/**
	 * 
	 * @method protected
	 * @param String type Type of event.
	 * @return boolean
	 */
	f_isActionListEmpty: function(type) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_isActionListEmpty: Bad type of event name '"+type+"'");

		var als=this._actionLists;
		if (!als) {
			return true;
		}
		
		var al=als[type];
		if (!al) {
			return true;
		}
		
		return al.f_isEmpty();
	},
	
	/**
	 * 
	 * @method public
	 * @param String type Type of event.
	 * @param Function... listener An object of type string or function.
	 * @return void
	 */
	f_addEventListener: function(type, listener) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_addEventListener: Bad type of event '"+type+"'");
	
		var al=this.f_openActionList(type);
		
		f_core.Debug(fa_eventTarget, "f_addEventListener: Add actions '"+type+"' => "+arguments);
		
		for (var i=1;i<arguments.length;i++) {
			al.f_addAction(arguments[i]);
		}
	},
	/**
	 * 
	 * @method hidden
	 * @param String type Type of event.
	 * @param Function... listener An object of type string or function.
	 * @return void
	 */
	f_insertEventListenerFirst: function(type, listener) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_insertEventListenerFirst: Bad type of event '"+type+"'");
	
		var al=this.f_openActionList(type);
		
		f_core.Debug(fa_eventTarget, "f_insertEventListenerFirst: Insert actions '"+type+"' => "+arguments);
		
		for (var i=1;i<arguments.length;i++) {
			al.f_addActionFirst(arguments[i]);
		}
	},
	/**
	 * 
	 * @method public
	 * @param String type Type of event.
	 * @param Function... listener An object of type string or function.
	 * @return void
	 */
	f_removeEventListener: function(type, listener) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_removeEventListener: Bad type of event '"+type+"'");

		var al=this.f_getActionList(type);
		if (!al) {
			return;
		}
	
		for (var i=1;i<arguments.length;i++) {
			al.f_removeAction(arguments[i]);
		}
	},
	/**
	 * 
	 * @method hidden
	 * @param String Type of the event.
	 * @return f_actionList
	 */
	f_openActionList: function(type) {
		f_core.Assert(typeof(type)=="string", "fa_eventTarget.f_openActionList: Bad type of event '"+type+"'");

		var al=this.f_getActionList(type);
		if (al) {
			return al;
		}

		if (type==f_event.INIT) {
			// Optimisation car c'est couteux de faire ca systématiquement !
			this._hasInitListeners=true;
		}

		var actionLists=this._actionLists;
		if (!actionLists) {
			actionLists=new Object;
			this._actionLists = actionLists;
		}

		al = new f_actionList(this, type);
		actionLists[type] = al;
		
		f_core.Debug(fa_eventTarget, "Create actionList '"+type+"' for component '"+this.id+"'.");
		
		if (this.f_setDomEvent) {
			this.f_setDomEvent(type, this);
		}
			
		return al;
	},
	/**
	 * @method protected final
	 */
	f_performPropertyChange: function(name, value, oldValue) {
		if (this.f_isActionListEmpty(f_event.PROPERTY_CHANGE)) {
			return;
		}
		
		// Gros problèmes de stack overflow avec MSIE >=6.0.29
		var component=this;
		
		window.setTimeout(function() {
			component.f_fireEvent(f_event.PROPERTY_CHANGE, null, null, {
				name: name,
				value: value, 
				oldValue: oldValue });
		}, 10);
	},
	/**
	 * @method protected final
	 * @param String type
	 * @param boolean value
	 * @return void
	 */
	f_setForcedEventReturn: function(type, value) {
		var fer=this._forcedEventReturns;
		if (!fer) {
			fer=new Object();
			this._forcedEventReturns=fer;
		}		
		fer[type]=value;
	},
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setDomEvent: f_class.OPTIONAL_ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_clearDomEvent: f_class.OPTIONAL_ABSTRACT
}

new f_aspect("fa_eventTarget", {
	statics: __statics,
	members: __members
});
