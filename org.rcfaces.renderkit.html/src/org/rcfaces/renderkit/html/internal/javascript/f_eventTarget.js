/*
 * $Id$
 */

/**
 * f_eventTarget class
 *
 * @class public f_eventTarget extends f_object, HTMLElement, fa_eventTarget
 * @author Joel Merlin & Olivier Oeuillot
 * @version $Revision$ $Date$
 */
var __static = {

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
			user: f_event.USER
	},
	
	/**
	 * @field private static
	 */
	_Callbacks: undefined,
	
	
	/**
	 * @method hidden static
	 * @return void
	 */
	Initializer: function() {
		var cb={};
		this._Callbacks=cb;
		
		cb[f_event.BLUR]={_dom: "onblur", _mtd: __SYMBOL("f_onBlur") }; 
		cb[f_event.CHANGE]={_dom: "onchange", _mtd: __SYMBOL("f_onChange") }; 
		cb[f_event.CHECK]=null;
		cb[f_event.CLOSE]=null;
		cb[f_event.DBLCLICK]={_dom: "ondblclick", _mtd: __SYMBOL("f_onDblClick") }; 
		cb[f_event.ERROR]=null;
		cb[f_event.FOCUS]={_dom: "onfocus", _mtd: __SYMBOL("f_onFocus") }; 
		cb[f_event.INIT]=null;
		cb[f_event.KEYDOWN]={_dom: "onkeydown", _mtd: __SYMBOL("f_onKeyDown") }; 
		cb[f_event.KEYPRESS]={_dom: "onkeypress", _mtd: __SYMBOL("f_onKeyPress") }; 
		cb[f_event.KEYUP]={_dom: "onkeyup", _mtd: __SYMBOL("f_onKeyUp") }; 
		cb[f_event.LOAD]=null;
		cb[f_event.MENU]=null;
		cb[f_event.MOUSEDOWN]={_dom: "onmousedown", _mtd: __SYMBOL("f_onMouseDown") }; 
		cb[f_event.MOUSEOUT]={_dom: "onmouseout", _mtd: __SYMBOL("f_onMouseOut") }; 
		cb[f_event.MOUSEOVER]={_dom: "onmouseover", _mtd: __SYMBOL("f_onMouseOver") }; 
		cb[f_event.MOUSEUP]={_dom: "onmouseup", _mtd: __SYMBOL("f_onMouseUp") }; 
		cb[f_event.PROPERTY_CHANGE]=null;
		// A PART cb[f_event.RESET]=null;
		cb[f_event.SELECTION]={_dom: "onclick", _mtd: __SYMBOL("f_onSelect") }; 
		cb[f_event.SUBMIT]=null;
		cb[f_event.SUGGESTION]=null;
		cb[f_event.USER]=null;
	},
	
	/**
	 * @method private static
	 * @return boolean
	 */
	_Generic: function(elt, jsEvent, type, lock, custom) {
		
		if (!jsEvent && elt) {
			jsEvent = f_core.GetEvent(elt);
		}
		
		var comp = (elt.f_link)? elt.f_link:elt;

		if (lock && comp.f_getEventLocked()) {
			f_core.Info(f_eventTarget, "Event has been locked !");
			
			return false;
		}

		var ret = comp.f_fireEvent(type, jsEvent);

		if (custom !== undefined) {
			ret = comp._returnOnSelect;
			if (ret===undefined) {
				ret=custom;
				if (jsEvent && custom===false) {
//		alert("Type:"+type+"/"+custom+"/"+ret);

					f_core.CancelEvent(jsEvent);				
				}
			}
		}

		f_core.Debug(f_eventTarget, "Generic call type '"+type+"' elt='"+((elt)?elt.id:"?")+"' comp='"+comp+"' returns '"+ret+"'.");
		return ret;
	}
}

var __prototype = {
	
	f_eventTarget: function() {
		this.f_super(arguments);
		
		if (this.nodeType==1) {
			this.f_initEventAtts(f_eventTarget._EVENTS);
		}
	},	
	/**
	 * 
	 * @method protected
	 */
	/*
	f_finalize: function() {
		this._returnOnSelect=undefined; // boolean
		
		this.f_super(arguments);
	},
	*/
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onCheck: function(evt) {
		return f_eventTarget._Generic(this,evt,f_event.CHECK,true,false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onSelect: function(evt) {
		return f_eventTarget._Generic(this, evt, f_event.SELECTION, true, false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onFocus: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.FOCUS,false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onBlur: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.BLUR,false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onChange: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.CHANGE,true); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onDblClick: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.DBLCLICK,true); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onError: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.ERROR,false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onKeyUp: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.KEYUP,true); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onKeyDown: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.KEYDOWN,true); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onKeyPress: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.KEYPRESS,true); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onMouseOver: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.MOUSEOVER,false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onMouseOut: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.MOUSEOUT,false); 
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onMouseDown: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.MOUSEDOWN,true);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onMouseUp: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.MOUSEUP,false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onPrepareSuggestion: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.PREPAGESUGGESTION,false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onPropertyChange: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.PROPERTY_CHANGE,false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onMenu: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.MENU,false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onReset: function(evt) { 
		return f_eventTarget._Generic(this, evt, f_event.RESET, false);
	},
	/**
	 * 
	 * @method protected
	 * @param optional Event evt
	 * @return boolean
	 */
	f_onUserEvent: function(evt) { 
		return f_eventTarget._Generic(this,evt,f_event.USER,false);
	},
	/**
	 * 
	 * @method protected
	 * @return boolean
	 */
	f_onInitEvent: function() { 
		return this.f_fireEvent(f_event.INIT);
	},
	/**
	 * 
	 * @method protected
	 * @param String type
	 * @param HTMLElement target
	 * @return boolean
	 */
	f_setDomEvent: function(type, target) {
		f_core.Assert(typeof(type)=="string", "f_eventTarget.f_setDomEvent: Type of event is incorrect ! ("+type+")");
		f_core.Assert(target && target.tagName, "f_eventTarget.f_setDomEvent: Type of target is incorrect ! ("+target+")");
		
		var cb=f_eventTarget._Callbacks[type];
		
//		alert("["+this.id+"] Type="+type+" => "+cb);
		if (cb) {
			target[cb._dom]=this[cb._mtd];
			return;
			
		} else if (cb===null) {
			return;
		}
		
		switch(type) {			
		case f_event.RESET: 
			f_core.AddResetListener(this);
			return;					
		}
	},
	/**
	 * 
	 * @method hidden
	 * @return boolean
	 */
	f_callOnInit: function() {
		return this.f_fireEvent(f_event.INIT,null);
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
			target[cb._dom]=null;
			return;
			
		} else if (cb===null) {
			return;
		}
		
		switch(type) {
		case f_event.RESET: 
			f_core.RemoveResetListener(this);
			return;					
		}
	}
}

new f_class("f_eventTarget", null, __static, __prototype, f_object, fa_eventTarget);
