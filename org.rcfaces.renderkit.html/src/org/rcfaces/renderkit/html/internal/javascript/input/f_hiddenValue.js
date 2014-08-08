/*
 * $Id$
 */
 
/**
 * Hidden value component.
 *
 * @class public f_hiddenValue extends f_eventTarget, fa_serializable, fa_clientData
 */
 
 var __members = {
		 
	 /**
	 * Returns the idenfiant of the component.
	 * 
	 * @method public
	 * @return String Identifier
	 */
	f_getId: function() {
		return this.id;
	},
		 
	/**
	 * Returns the value.
	 *
	 * @method public
	 * @return String Or <code>null</code> if not defined !
	 */
	f_getValue: function() {
		return this.value;
	},
	/**
	 * Set the value.
	 *
	 * @method public
	 * @param optional String value
	 * @return void
	 */
	f_setValue: function(value) {
		if (value===undefined) {
			value="";
		}
		
		if (value!==null && value!==undefined && typeof(value)!="string") {
			value=String(value);
		}
	
		var oldValue=this.value;	
		this.value=value;
		
		if (this.f_performPropertyChange) {
			this.f_performPropertyChange(f_prop.VALUE, value, oldValue);
		}
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_completeComponent: function() {
		
		if (f_class.PROFILE_COMPONENT) {
			f_core.Profile(undefined, "f_component.f_completeComponent("+this.id+" / "+this._kclass._name+")");
		}

		if (!this._hasInitListeners) {
			return;
		}
		this._hasInitListeners=undefined;
		
		//this.f_fireEvent(f_event.INIT);
		
		this.f_getClass().f_getClassLoader().f_fireInitListener(this);
	},
	f_findComponent: function(id) {
		return fa_namingContainer.FindComponents(this, arguments);
	},
	f_findSiblingComponent: function(id) {
		return fa_namingContainer.FindSiblingComponents(this, arguments);
	}
};

new f_class("f_hiddenValue", {
	extend: f_eventTarget,
	aspects: [fa_serializable, fa_clientData],
	members: __members
} );