/*
 * $Id$
 */
 
/**
 * Accelerator class.
 *
 * @class public f_accelerator extends f_object, fa_immediate, fa_eventTarget
 */

var __statics={

	/**
	 * @field private static final
	 */
	_EVENTS: {
			init: f_event.INIT,
			keyPress: f_event.KEYPRESS,
			propertyChange: f_event.PROPERTY_CHANGE,
			user: f_event.USER
	},
	
	/**
	 * @field private static
	 */
	 _AcceleratorsByFor: undefined,
	
	/*
	 * @field hidden static final
	 * @return Object
	 *
	GetAcceleratorByForComponent: function(forComponentId, forItemValue) {
		var abf=f_accelerator._AcceleratorsByFor;
		if (!abf) {
			return null;
		}
		
		var key=forComponentId;
		if (forItemValue) {
			key+=" "+
		}
		
		return abf[];
	},
	*/
	
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		f_accelerator._AcceleratorsByFor=undefined;
	}
}

var __members={

	f_accelerator: function() {
		this.f_super(arguments);

		this._character=f_core.GetAttribute(this, "v:character", undefined);

		var vk=f_core.GetAttribute(this, "v:virtualKey");
		if (vk) {
			this._virtualKeys = [ parseInt(vk, 10) ];
		}

		this._keyFlags = f_core.GetNumberAttribute(this, "v:keyFlags");

		f_key.AddAccelerator(this._character, this._virtualKeys, this._keyFlags, this, this._performKeyEvent);

		var events=f_core.GetAttribute(this, "v:events");
		if (events) {
			this.f_initEventAtts(f_accelerator._EVENTS, events);
		}
		
		var forComponent=f_core.GetAttribute(this, "v:for");
		
		if (forComponent) {
			this._forComponentId=fa_namingContainer.ComputeComponentId(this, forComponent);
			this._forItemValue=f_core.GetAttribute(this, "v:forItemValue");
				
			this.f_insertEventListenerFirst(f_event.KEYPRESS, this._forListener);
		}
	},
	/*
	f_finalize: function() {
		this._character=undefined; // string
		this._virtualKeys=undefined; // string
		this._keyFlags=undefined; // string
		this._forComponentId=undefined; // string
		this._forItemValue=undefined; // string

		this.f_super(arguments);
	},
	*/
	/**
	 * @method private
	 * @return boolean
	 */
	_performKeyEvent: function(jsEvt) {
		return this.f_fireEvent(f_event.KEYPRESS, jsEvt);
	},
	
	/**
	 * @method private
	 * @param f_event event
	 * @return boolean
	 */
	_forListener: function(event) {
		var forComponent=this._forComponentId;
		
		var component=f_core.GetElementByClientId(forComponent, this.ownerDocument);
		
		if (!component) {
			f_core.Debug(f_accelerator, "Can not find component '"+forComponent+"'.");
			return false;
		}
		
		var f=component.f_onSelect;
		if (!f) {				
			f_core.Debug(f_accelerator, "No callback for component '"+forComponent+"'.");
			return false;
		}

		f_core.Debug(f_accelerator, "Call onSelect on component '"+forComponent+"'.");
		
		try {
			return f.call(component);

		} catch (ex) {
			f_core.Error(f_accelerator, "Call onSelect on component '"+forComponent+"' throws exception.", ex);
			
			throw ex;
		}
	},
	
	/**
	 * @method hidden
	 * @return String
	 */
	f_getCharacter: function() {
		return this._character;
	},
	/**
	 * @method hidden
	 * @return number
	 */
	f_getVirtualKeys: function() {
		return this._virtualKeys;
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getKeyFlags: function() {
		return this._keyFlags;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getFor: function() {
		return this._forComponentId;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getForItemValue: function() {
		return this._forItemValue;
	}
}
 
new f_class("f_accelerator", null, __statics, __members, f_object, fa_immediate, fa_eventTarget);
