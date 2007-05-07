/*
 * $Id$
 */

/**
 * f_input class.
 *
 * @class f_input extends f_component, fa_message, fa_focusStyleClass
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */
var __prototype = {

	f_input: function() {
		this.f_super(arguments);

		var input=this.f_getInput();
		if (input!=this) {
			input.f_link=this;
		}
			
		var focusStyleClass=this.f_getFocusStyleClass();
		if (focusStyleClass) {
			this.f_insertEventListenerFirst(f_event.FOCUS, this._focusFocusEvent);
			this.f_insertEventListenerFirst(f_event.BLUR, this._focusBlurEvent);
		}
		
		f_core.Debug(f_input, "f_input: Input associated to component '"+this.id+"' is id='"+input.id+"', tagName="+input.tagName+", name='"+input.name+"'.");
	},
	f_finalize: function() {
//		this._hasFocus=undefined; // boolean
 		this._currentMessage=undefined; // f_message
		
		this._validator=undefined;
		
		this.f_super(arguments);

		// On efface l'INPUT aprés car le _input peut être reinitialisé par les classes parentes !
		var input=this._input;
		if (input) {
			this._input=undefined;
			input.f_link=undefined;
		}

		if (input && input!=this) {
			f_core.VerifyProperties(input);
		}
	},
	/**
	 * 
	 * @method protected final
	 * @return HTMLElement
	 */
	f_getInput: function() {
		var input=this._input;
		if (input) {
			return input;
		}
		
		input=this.f_initializeInput();
		if (!input) {
			throw new Error("Can not find input associated to component '"+this.id+"'.");
		}
		this._input=input;
		
		return input;
	},
	/**
	 * 
	 * @method protected
	 * @return HTMLElement
	 */
	f_initializeInput: function() {
		var inputTagName=this.f_getInputTagName();
		
		var tagName=this.tagName;
		if (tagName && tagName.toLowerCase()==inputTagName) {
			return this;
		}
		
		var input=f_core.GetFirstElementByTagName(this, inputTagName, true);

		return input;
	},
	/**
	 * 
	 * @method protected
	 * @return String
	 */
	f_getInputTagName: function() {
		return "input";
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
		
		var input=this.f_getInput();
	
		f_core.Debug(f_input, "f_setFocus: Input '"+this.id+"' component="+input+" componentId="+input.id);
		
		try {
			input.focus();
			
		} catch (x) {
			f_core.Error(f_input, "Error while setting focus to '"+input.id+"'.", x);
		}
		
		if (input.tagName.toLowerCase()=="textarea") {
			input.select();
			
		} else {  		
			var type=input.type;
			if (type) {
				switch(type.toUpperCase()) {
				case "TEXT":
					input.select();
					break;					
				}
			}
		}
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		var validator=this._validator;
		if (validator) {
			return validator.f_getValue();
		}
	
		return this.f_getInput().value;
	},
	/**
	 * Returns the text associated to the input.
	 * 
	 * @method public
	 * @param String text The text of the input.
	 * @return void
	 */
	f_setText: function(text) {
		var input=this.f_getInput();
		
		if (text == input.value) {
			return;
		}
		
		input.value = text;
		this.f_setProperty(f_prop.TEXT, text);

		var validator=this._validator;
		if (validator) {
			validator.f_updateValue(text);
		}		
	},
	/**
	 * Returns the disabled state. 
	 *
	 * @method public
	 * @return boolean Returns <code>true</code> if the input is disabled.
	 */
	f_isDisabled: function() {
		return this.f_getInput().disabled;
	},
	/**
	 * Set the disabled state of this component.
	 *
	 * @method public
	 * @param boolean disabled Set disabled state.
	 * @return void
	 */
	f_setDisabled: function(disabled) {
		this.f_getInput().disabled = disabled;
		this.disabled = disabled;
		this.f_updateDisabled(disabled);
		this.f_setProperty(f_prop.DISABLED, disabled);
	},
	/**
	 * @method protected
	 */
	f_updateDisabled: function(disabled) {
		this.f_updateStyleClass();
	},
	/**
	 * Returns the read only state.
	 * 
	 * @method public
	 * @return boolean Returns <code>true</code> if the component is in read only mode.
	 */
	f_isReadOnly: function() {
		return (this.f_getInput().readOnly == true);
	},
	/**
	 * @method public
	 * @param boolean set Set Read-only.
	 * @return void
	 */
	f_setReadOnly: function(set) {
		var input=this.f_getInput();
		if (input.readOnly == set) {
			return;
		}
		input.readOnly = set;
		this.f_setProperty(f_prop.READONLY,set);
		
		this.f_updateStyleClass();
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateStyleClass: function(postSuffix) {
		var suffix="";

		if (this.f_isDisabled()) {
			suffix+="_disabled";

		} else if (this.f_isReadOnly()) {
			suffix+="_readOnly";
		}
		
		if (postSuffix) {
			suffix+=postSuffix;
		}
	
		var claz=this.f_computeStyleClass(suffix);
		if (this.className!=claz) {
			this.className=claz;
		}
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
		//this.f_clientValidatorEx.fa_componentUpdatedValue(this.value);
		//}

		this.f_super(arguments);
	},
	*/
	
	f_update: function() {
		this.f_performMessageChanges();	
				
		return this.f_super(arguments);	
	},
	/**
	 * Returns the value associated to the input component.
	 *
	 * @method public
	 * @return String The value associated.
	 */
	f_getValue: function() {
		var validator=this._validator;
		if (validator) {
			return validator.f_getConvertedValue();
		}		
		
		return this.f_getInput().value;
	},
	/**
	 * Returns the value associated to the input component.
	 *
	 * @method public
	 * @return boolean If value is recognized.
	 */
	f_setValue: function(value) {
		var validator=this._validator;
		if (validator) {
			if (validator.f_setConvertedValue(value)) {
				return true;
			}
		}		

		if (typeof(value)=="number") {
			value=String(value);
		}

		if (typeof(value)!="string") {
			f_core.Debug(f_input, "Invalid value: "+value);
			return false;
		}
		
		this.f_getInput().value=value;
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
			f_core.Debug(f_input, "f_isValid: No message context !", x);
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
	},
	/**
	 * @method protected
	 * @return String
	 */
	f_computeStyleClass: function(suffix) {	
		var styleClass=this.f_super(arguments, suffix);

		if (this._hasFocus && !this.f_isDisabled()) {
			var focusStyleClass=this.f_getFocusStyleClass();

			if (focusStyleClass) {
				styleClass+=" "+focusStyleClass;
			}
		}
		
		var msg=this._currentMessage;
		if (msg) {
			var severity=msg.f_getSeverity();
			
			var severityClass=this.f_getStyleClassFromSeverity(severity);
			
			if (!severityClass && severity>=f_messageObject.SEVERITY_ERROR) {
				severityClass=this.f_getMainStyleClass()+"_error";
			}
			
			if (severityClass) {
				styleClass+=" "+severityClass;
			}
		}

		return styleClass;
	},
	f_performMessageChanges: function() {	
		var messages=f_messageContext.ListMessages(this);
		
		var msg;
		for(var j=0;j<messages.length;j++) {
			var m=messages[j];
			
			if (!msg || msg.f_getSeverity()<m.f_getSeverity()) {
				msg=m;
			}
		}
		
		f_core.Debug(f_input, "f_performMessageChanges: Change message to '"+msg+"' for component "+this.id+".");
						
		if (msg==this._currentMessage) {
			return;
		}
		
		this._currentMessage=msg;
		
		this.f_updateStyleClass();
	},
	/**
	 * @method private
	 */
	_focusFocusEvent: function() {
		this._hasFocus=true;
		
		this.f_updateStyleClass();
	},
	/**
	 * @method private
	 */
	_focusBlurEvent: function() {
		this._hasFocus=undefined;
		
		this.f_updateStyleClass();
	}
}

new f_class("f_input", null, null, __prototype, f_component, fa_message, fa_focusStyleClass);
