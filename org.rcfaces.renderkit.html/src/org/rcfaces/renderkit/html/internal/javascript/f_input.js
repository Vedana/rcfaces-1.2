/*
 * $Id$
 *
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */

/**
 * f_input class.
 *
 * @class f_input extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */
var __prototype = {

	f_input: function() {
		this.f_super(arguments);

		var input=this.f_initializeInput();
		if (input) {
			f_core.Assert(input.tagName, "Input component is not a TAG !");
			
			input.f_link=this;
			
			// Trop tard ... ca sert à rien !
			//if (this._accessKey) {
				// Il faut positionner l'accessKey !
			//	input.accessKey=this._accessKey;
			//}
			
			this._input=input;
		}
	},
	f_finalize: function() {
		var input=this._input;
		if (input) {
			this._input=undefined;

			input.f_link=undefined;
			f_core.VerifyProperties(input);			
		}
		this._validator=undefined;
		
		this.f_super(arguments);
	},
	/**
	 * 
	 * @method protected
	 */
	f_initializeInput: function() {
		var tagName=this.tagName;
		if (tagName && tagName.toUpperCase()=="INPUT") {
			return null;
		}
		
		var inputs=this.getElementsByTagName("INPUT");
		if (inputs.length==0) {
			f_core.Error(f_input, "No Input tag into this component !");
			return null;
		}
		
		f_core.Assert(inputs.length==1, "f_input.f_initializeInput: More than one input tag into this button component !");
		return inputs[0];
	},
	/**
	 * 
	 * @method protected
	 */
	f_getInput: function() {
		return this._input;
	},
	/**
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this.f_isDisabled && this.f_isDisabled()) {
			return;
		}
		if (!this.tabIndex) {
			this.tabIndex = 0;	// Default tabbing
		}

		f_core.Debug("f_imageButton", "f_setFocus of input");
		
		var component=this;
		if (this._input) {
			component=this._input;
		}
		
		try {
			component.focus();
			
		} catch (x) {
			f_core.Error(f_input, "Error while setting focus to '"+component.id+"'.", x);
		}
		
		var typ=component.type;
		if (typ) {
			typ = typ.toUpperCase();
			if ((typ == "TEXT") || (typ == "TEXTAREA")) {
				component.select();
			}
		}
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getText: function() {
		var validator=this._validator;
		if (validator) {
			return validator.f_getValue();
		}
	
		return this.value;
	},
	/**
	 * Returns the text associated to the input.
	 * 
	 * @method public
	 * @param string text The text of the input.
	 * @return void
	 */
	f_setText: function(text) {
		if (text == this.value) {
			return;
		}
		
		this.value = text;
		this.f_setProperty(f_prop.TEXT, text);

		var validator=this._validator;
		if (validator) {
			validator.f_updateValue(true);
		}		
	},
	/**
	 * Returns the disabled state. 
	 *
	 * @method public
	 * @return boolean Returns <code>true</code> if the input is disabled.
	 */
	f_isDisabled: function() {
		if (this._input) {
			return (this._input.disabled);
		}
		
		return this.disabled;
	},
	/**
	 * Set the disabled state of this component.
	 *
	 * @method public
	 * @param boolean disabled Set disabled state.
	 * @return void
	 */
	f_setDisabled: function(disabled) {
		if (this._input) {
			this._input.disabled = disabled;
		}
		this.disabled = disabled;
		this._updateDisabled(disabled);
		this.f_setProperty(f_prop.DISABLED, disabled);
	},
	/**
	 * @method protected
	 */
	_updateDisabled: function(disabled) {
	},
	/**
	 * Returns the read only state.
	 * 
	 * @method public
	 * @return boolean Returns <code>true</code> if the component is in read only mode.
	 */
	f_isReadOnly: function() {
		return (this.readOnly == true);
	},
	/**
	 * @method public
	 * @param boolean set Set Read-only.
	 * @return void
	 */
	f_setReadOnly: function(set) {
		if (this.readOnly == set) {
			return;
		}
		this.readOnly = set;
		this.f_setProperty(f_prop.READONLY,set);
	},
	/*
	f_update: function() {
		// Check for an extended validator
		//if (this.f_clientValidatorEx) {
		//var view = this.f_getView();
		//view.f_addCheckElement(this);
		//}
		// Update value if necessary
		//if (this.value && this.f_clientValidatorEx) {
		//this.f_clientValidatorEx._componentUpdatedValue(this.value);
		//}

		this.f_super(arguments);
	},
	*/
	/**
	 * Returns the value associated to the input component.
	 *
	 * @method public
	 * @return string The value associated.
	 */
	f_getValue: function() {
		var input=this._input;
		if (input) {
			return input.value;
		}
		
		return this.value;
	},
	f_fireEvent: function(type, evt, item, value) {
		if (type==f_event.CHANGE) {			
			if (this.f_isReadOnly() || this.f_isDisabled()) {
				return false;
			}
			
			if (!value) {
				value=this.f_getValue();
			}
		}	
		
		return this.f_super(arguments, type, evt, item, value);
	},
	/**
	 * @method public
	 * @return boolean Returns <code>true</code> if value of the component has been validated.
	 */
	f_isValid: function() {
		try {
			var messageContext=f_messageContext.Get(this);

			if (messageContext) {
				messageContext.f_clearMessages(this);
			}
		} catch (x) {
			// f_messageContext n'existe peut etre pas !
			f_core.Debug("f_input", "No message context !", x);
		}
		
		return this.f_validValue();
	},
	/**
	 * @method protected
	 * @return boolean Returns <code>true</code> if value of the component has been validated.
	 */
	f_validValue: function() {
		var validator=this._validator;
		if (!validator) {
			return true;
		}
		
		return validator.f_isValidValue();		
	}
	
}

var f_input=new f_class("f_input", null, null, __prototype, f_component);
