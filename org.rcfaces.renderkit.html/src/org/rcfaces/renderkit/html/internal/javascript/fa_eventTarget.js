/*
 * $Id$
 */

/**
 * fa_eventTarget class
 *
 * @aspect public fa_eventTarget
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	/**
	 * @field private static number
	 */
	 _EventId: undefined
}

var __prototype = {
	
	f_finalize: function() {
		var actionLists=this._actionLists;
		if (!actionLists) {
			return;
		}
		
		this._actionLists=undefined;
//		this._hasInitListeners=undefined; // boolean
		
		var clearDomEvent=this.f_clearDomEvent;
		for(var type in actionLists) {
			if (clearDomEvent) {
				clearDomEvent.call(this, type, this);
			}
				
			// Pour les subviews !
			var al=actionLists[type];
			
			f_classLoader.Destroy(al);
		}
	},
	/**
	 * 
	 * @method hidden
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
			
			f_core.Debug("f_eventTarget", "Register event '"+eventName+"' on component '"+this.id+"' => "+commands);

			if (commands.length==2 && commands[1]=="submit") {
				this.f_addEventListener(eventType, f_core.Submit);
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
	 */
	f_getEventLocked: function(showAlert, mask) {
		if (!window.f_event) {
			return true;
		}
		return f_event.GetEventLocked(showAlert, mask);
	},
	/**
	 * 
	 * @method public
	 */
	f_fireEvent: function(type, jsEvt, item, value, selectionProvider, detail) {
		if (!fa_eventTarget._EventId) {
			fa_eventTarget._EventId=0;
		}
		var eventId=fa_eventTarget._EventId++;

		var event;
		if (type instanceof f_event) {
			event=type;
			type=event._type;
			jsEvt=event._jsEvent;
			event._eventId=eventId;
		}

//		f_core.Profile("fa_eventTarget.fireEvent.enter(#"+eventId+","+type+","+this.id+")");

		try {				
			var al=this.f_getActionList(type);
			if (!al) {
				f_core.Debug("f_eventTarget", "No listeners for event '"+type+"' on '"+this.id+"'"+((event)?("item='"+event._item+"' value='"+event._value+"' selectionProvider='"+event._selectionProvider+"'"):"")+".");
				
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
				f_core.Debug("f_eventTarget", "Fire event '"+event._type+"' on '"+this.id+"' item='"+event._item+"' value='"+event._value+"' selectionProvider='"+event._selectionProvider+"' detail='"+event._detail+"'.");
				
				ret = al.f_callActions(event);
					
				if (jsEvt && ret===false) {
					f_core.CancelEvent(jsEvt);
					//alert("Cancel event !");
				}
				
			} finally {
				if (disposeEvent) {
					// Nous sommes pas forcement dans un contexte initialisé. (aprés un submit)
					// f_finalize existe peut-etre plus !
					if (window.f_classLoader) {
						f_classLoader.Destroy(event);
					}
				}
			}
			
			if (jsEvt) {
				jsEvt.returnValue = ret;
			}
			
			return ret;
		} finally {
//			f_core.Profile("fa_eventTarget.fireEvent.exit(#"+eventId+")");
		}
	},
	/**
	 * 
	 * @method hidden
	 */
	f_getActionList: function(type) {
		f_core.Assert(typeof(type)=="string", "Bad type of event name '"+type+"'");

		var al=this._actionLists;
		if (!al) {
			return null;
		}
		
		return al[type];
	},
	/**
	 * 
	 * @method hidden
	 * @return boolean
	 */
	f_isActionListEmpty: function(type) {
		f_core.Assert(typeof(type)=="string", "Bad type of event name '"+type+"'");

		var als=this._actionLists;
		if (!als) {
			return false;
		}
		
		var al=als[type];
		if (!al) {
			return false;
		}
		
		return al.f_isEmpty();
	},
	
	/**
	 * 
	 * @method public
	 * @param String type Type of event.
	 * @param any action An object of type string or function.
	 * @return void
	 */
	f_addEventListener: function(type, action) {
		f_core.Assert(type && typeof(type)=="string", "Bad type of event '"+type+"'");
	
		var al=this.f_openActionList(type);
		
		for (var i=1;i<arguments.length;i++) {
			al.f_addAction(arguments[i]);
		}
	},
	/**
	 * 
	 * @method hidden
	 * @param String type Type of event.
	 * @param any action An object of type string or function.
	 * @return void
	 */
	f_insertEventListenerFirst: function(type, action) {
		f_core.Assert(type && typeof(type)=="string", "Bad type of event '"+type+"'");
	
		var al=this.f_openActionList(type);
		
		for (var i=1;i<arguments.length;i++) {
			al.f_addActionFirst(arguments[i]);
		}
	},
	/**
	 * 
	 * @method public
	 * @param String type Type of event.
	 * @param any action An object of type string or function.
	 * @return void
	 */
	f_removeEventListener: function(type, action) {
		f_core.Assert(type && typeof(type)=="string", "Bad type of event '"+type+"'");

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
	 */
	f_openActionList: function(type) {
		f_core.Assert(typeof(type)=="string", "Bad type of event '"+type+"'");

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
		
		f_core.Debug("f_eventTarget", "Create actionList '"+type+"' for component '"+this.id+"'.");
		
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
	 * @method protected
	 * @return void
	 */
	f_setDomEvent: f_class.OPTIONAL_ABSTRACT,
	
	/**
	 * @method protected
	 * @return void
	 */
	f_clearDomEvent: f_class.OPTIONAL_ABSTRACT
}

var fa_eventTarget=new f_aspect("fa_eventTarget", __static, __prototype);
