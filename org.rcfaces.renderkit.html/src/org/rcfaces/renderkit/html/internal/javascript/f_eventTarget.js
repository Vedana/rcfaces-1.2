/*
 * $Id$
 */

/**
 * f_eventTarget class
 *
 * @class public f_eventTarget extends f_object, HTMLElement, fa_eventTarget
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
var __statics = {

	/**
	 * @field private static final
	 */
	_EVENTS: {
			change: f_event.CHANGE,
			check: f_event.CHECK,
			blur: f_event.BLUR,
			dblClick: f_event.DBLCLICK,
			error: f_event.ERROR,
			focus: f_event.FOCUS,
			init: f_event.INIT,
			keyUp: f_event.KEYUP,
			keyDown: f_event.KEYDOWN,
			keyPress: f_event.KEYPRESS,
			load: f_event.LOAD,
			menu: f_event.MENU,
			mouseDown: f_event.MOUSEDOWN,
			mouseOver: f_event.MOUSEOVER,
			mouseOut: f_event.MOUSEOUT,
			mouseUp: f_event.MOUSEUP,
			propertyChange: f_event.PROPERTY_CHANGE,
			suggestion: f_event.SUGGESTION,
			selection: f_event.SELECTION,
			user: f_event.USER,
			validation: f_event.VALIDATION
	},
	
	/**
	 * @field private static
	 */
	_Callbacks: undefined,
	
	
	/**
	 * @method protected static
	 * @return void
	 */
	Initializer: function() {
		var cb={};
		this._Callbacks=cb;
		
		var noLock={ _lock: false };
		
		cb[f_event.BLUR]={_dom: "onblur", _lock: false  }; 
		cb[f_event.CHANGE]={_dom: "onchange" }; 
		cb[f_event.CHECK]=null;
		cb[f_event.CLOSE]=null;
		cb[f_event.DBLCLICK]={_dom: "ondblclick" }; 
		cb[f_event.ERROR]=noLock;
		cb[f_event.FOCUS]={_dom: "onfocus", _lock: false }; 
		cb[f_event.INIT]=noLock;
		cb[f_event.KEYDOWN]={_dom: "onkeydown" }; 
		cb[f_event.KEYPRESS]={_dom: "onkeypress" }; 
		cb[f_event.KEYUP]={_dom: "onkeyup" }; 
		cb[f_event.LOAD]=null;
		cb[f_event.MENU]=noLock;
		cb[f_event.MOUSEDOWN]={_dom: "onmousedown"}; 
		cb[f_event.MOUSEOUT]={_dom: "onmouseout", _lock: false  }; 
		cb[f_event.MOUSEOVER]={_dom: "onmouseover", _lock: false  }; 
		cb[f_event.MOUSEUP]={_dom: "onmouseup", _lock: false  }; 
		cb[f_event.PROPERTY_CHANGE]=noLock;
		cb[f_event.RESET]="reset";
		cb[f_event.SELECTION]={_dom: "onclick" };
		cb[f_event.SUBMIT]=null;
		cb[f_event.SUGGESTION]=null;
		cb[f_event.USER]=noLock;
	}
}

var __members = {
	
	f_eventTarget: function() {
		this.f_super(arguments); // On appelle pas le super à cause d'un problème de profondeur de pile IE
		
		if (this.nodeType==f_core.ELEMENT_NODE) {
			var events=f_core.GetAttribute(this, "v:events");
			if (events) {
				this.f_initEventAtts(f_eventTarget._EVENTS, events);
			}
		}
	},	
	/**
	 * @method protected
	 */
	f_finalize: function() {
		this.f_super(arguments);

		this._resetCallback=undefined;
		//f_core.RemoveResetListener(this);  ???
		//f_core.RemoveCheckListener(this);  ???
	},
	
	/**
	 * 
	 * @method protected
	 * @param String type
	 * @param HTMLElement target
	 * @return void
	 */
	f_setDomEvent: function(type, target) {
		f_core.Assert(typeof(type)=="string", "f_eventTarget.f_setDomEvent: Type of event is incorrect ! ("+type+")");
		f_core.Assert(target && target.tagName, "f_eventTarget.f_setDomEvent: Type of target is incorrect ! ("+target+")");
		
		f_core.Debug(f_eventTarget, "f_setDomEvent: component '"+this.tagName+"#"+this.id+"' add '"+type+"' callbak (target="+target.tagName+"#"+target.id+")");
		
		var cb=f_eventTarget._Callbacks[type];

		if (cb) {			
			var lock=cb._lock;
			var self=this;
			var func=function(jsEvent) {
				if (window._rcfacesExiting) {
					return false;
				}
		
				if (!jsEvent) {
					jsEvent = f_core.GetJsEvent(this);
				}
				
				if (lock!==false && self.f_getEventLocked(jsEvent)) {
					f_core.Info(f_eventTarget, "f_setDomEvent.handler: Event has been locked ! (type="+type+" comp='"+self+"' ["+self.tagName+"#"+self.id+"."+self.className+"])");
					
					return false;
				}
		
				var ret = self.f_fireEvent(type, jsEvent);
		
				f_core.Debug(f_eventTarget, "f_setDomEvent.handler: Generic call type '"+type+"' comp='"+self+"' ["+self.tagName+"#"+self.id+"."+self.className+"] returns '"+ret+"'.");
				return ret;
			};

			if (cb=="reset") {
				if (!this._resetCallback) {
					this._resetCallback=func;
					f_core.AddResetListener(this, func);
				}
				return;
			}
			
//		alert("["+this.id+"] Type="+type+" => "+cb);
			target[cb._dom]=func;
			return;
			
		} else if (cb===null) {
			return;
		}
		
		switch(type) {			
		case f_event.VALIDATION: 
			f_core.AddCheckListener(this);
			return;					
		}
	},
	/**
	 * 
	 * @method protected
	 * @param String type
	 * @param HTMLElement target
	 * @return void
	 */
	f_clearDomEvent: function(type, target) {
		f_core.Assert(typeof(type)=="string", "f_eventTarget.f_clearDomEvent: Type of event is incorrect ! ("+type+")");
		f_core.Assert(target && target.tagName, "f_eventTarget.f_clearDomEvent: Type of target is incorrect ! ("+target+")");

		var cb=f_eventTarget._Callbacks[type];
		if (cb) {
			if (cb=="reset") {
				var resetCallback=this._resetCallback;
				if (resetCallback) {
					this._resetCallback=undefined;
					f_core.RemoveResetListener(this, resetCallback);
				}			
				return;
			} 
			target[cb._dom]=null;
			return;
			
		} else if (cb===null) {
			return;
		}
		
		switch(type) {
		case f_event.VALIDATION: 
			f_core.RemoveCheckListener(this);
			return;					
		}
	}
}

new f_class("f_eventTarget", {
	extend: f_object,
	aspects: [fa_eventTarget],
	statics: __statics,
	members: __members
});
