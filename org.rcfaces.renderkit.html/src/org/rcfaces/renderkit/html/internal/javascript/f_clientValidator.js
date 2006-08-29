/*
 * $Id$
 */

/**
 * f_clientValidator class
 *
 * @class hidden f_clientValidator extends f_object
 */
 
var __static = {

	/**
	 * @field hidden static final number
	 */
	SUCCESS: 0,

	/**
	 * @field hidden static final number
	 */
	FILTER: 1,

	/**
	 * @field hidden static final number
	 */
	TRANSLATOR: 2,

	/**
	 * @field hidden static final number
	 */
	CHECKER: 3,

	/**
	 * @field hidden static final number
	 */
	FORMATTER: 4,

	/**
	 * @field hidden static final number
	 */
	BEHAVIOR: 5,

	/**
	 * @field private static 
	 */
	_Expressions: undefined,
	
	Finalizer: function() {
		f_clientValidator._Expressions=undefined;
	},
	
	/**
	 * @method static hidden
	 * @return f_clientValidator Returns the validator associated to the component or <code>null</code>.
	 */
	GetValidator: function(component) {
		var validator=component._validator;
		if (!validator) {
			return null;
		}
		
		return validator;
	},
	
	/**
	 * @method static hidden
	 * @return f_clientValidator Returns the validator associated to the component or <code>null</code>.
	 */
	InstallValidator: function(component) {
		var params=f_core.GetAttribute(component, "v:clientValidator");
		if (typeof(params)!="string") {
			return null;
		}

		var parameters=undefined;
		if (params) {
			parameters=f_core.ParseParameters(params);
		}
		
		var validator=f_clientValidator.f_newInstance(component, parameters);
		
		// Parametres ....
		
		var filter=f_core.GetAttribute(component, "v:vFilter");
		if (filter) {
			filter=f_clientValidator._EvalFunction(filter);
			validator.f_addFilter(filter);
		}
		
		var translator=f_core.GetAttribute(component, "v:vTranslator");
		if (translator) {
			translator=f_clientValidator._EvalFunction(translator);
			validator.f_addTranslator(translator);
		}

		var checker=f_core.GetAttribute(component, "v:vChecker");
		if (checker) {
			checker=f_clientValidator._EvalFunction(checker);
			validator.f_addChecker(checker);
		}

		var formatter=f_core.GetAttribute(component, "v:vFormatter");
		if (formatter) {
			formatter=f_clientValidator._EvalFunction(formatter);
			validator.f_addFormatter(formatter);
		}

		var behavior=f_core.GetAttribute(component, "v:vBehavior");
		if (behavior) {
			behavior=f_clientValidator._EvalFunction(behavior);
			validator.f_addBehavior(behavior);
		}

		var error=f_core.GetAttribute(component, "v:vError");
		if (error) {
			error=f_clientValidator._EvalFunction(error);
			validator.f_addOnError(error);
		}

		var checkError=f_core.GetAttribute(component, "v:vCheckError");
		if (checkError) {
			checkError=f_clientValidator._EvalFunction(checkError);
			validator.f_addOnCheckError(checkError);
		}
		
		return validator;
	},
	/**
	 * @method private static 
	 */
	_EvalFunction: function(expr) {
		var expressions=f_clientValidator._Expressions;

		var f=undefined;
		if (!expressions) {
			expressions=new Object;
			f_clientValidator._Expressions=expressions;

		} else {
			f=expressions[expr];
			if (f) {
				return f;
			}
		}
	
		if (expr.charAt(0)=='/') {
			// Une regexp !
			var flags=expr.lastIndexOf('/');
			if (flags>0) {
				f=new RegExp(expr.substring(1, flags), expr.substring(flags+1));
				
			} else {
				f=new RegExp(expr.substring(1));
			}
			
		} else {
			try {
				f=eval(expr);
				
			} catch (x) {
				f_core.Error(f_clientValidator, "Can not eval expression '"+expr+"'.", x);
				return null;
			}
		}
		
		f_core.Assert(typeof(f)=="function" || (f instanceof RegExp), "Invalid expression for validator : '"+expr+"'='"+f+"'.");
		
		expressions[expr]=f;
		
		return f;
	},

	/**
	 * @method private static
	 */
	_OnFocus: function(evt) {
		if (this.f_isReadOnly()) {
			return true;
		}
	
		var validator=this._validator;
	
		validator._applyInputValue();
		validator._hasFocus = true;
		
		validator._input.select();
		return true;
	},
	/**
	 * @method private static
	 */
	_OnBlur: function(evt) {
		if (this.f_isReadOnly()) {
			return true;
		}

		var validator=this._validator;

		var bRet = validator._applyAutoCheck(this.value, false);
		validator._applyOutputValue();
		validator._hasFocus = undefined;
		
		return bRet;
	},
	/**
	 * @method private static
	 */
	_OnKeyPress: function(event) {
		var jsEvent = event.f_getJsEvent();
		var keyCode = jsEvent.keyCode;
		var charCode = jsEvent.charCode;

		var validator=this._validator;
		
		var keyChar;
		
		if (!charCode) {
			keyChar = String.fromCharCode(keyCode);

		} else {
			keyChar = String.fromCharCode(charCode);
		}
				
		f_core.Debug(f_clientValidator, "KeyPress: keyCode="+keyCode+" charCode="+charCode+" shift="+jsEvent.shift+" ctrl="+jsEvent.ctrl+" alt="+jsEvent.alt+" keyChar="+keyChar+"("+((keyChar.length>0)?keyChar.charCodeAt(0):"")+")");
	
		if (f_core.IsInternetExplorer()) {
			if (keyCode < 32) {
				return true;
			}
		} else if (f_core.IsGecko()) {
			if (keyCode>0) {
				return true;
			}
			keyCode=charCode;
		}
			
		// Filters
		var bRet = validator._applyFilters(keyCode, keyChar);
		if (!bRet) {
			return bRet;	
		}
	
		// Translators
		var retCode = validator._applyTranslators(keyCode, keyChar);
		if (retCode != keyCode) {
			return f_clientValidator._ChangeKeyCode(this, retCode, jsEvent);
		}
		
		validator._transient=true;
		return bRet;
	},
	/**
	 * @method private static
	 */
	_OnKeyUp: function(event) {
		var jsEvent = event.f_getJsEvent();

		var validator=this._validator;

		var keyCode = jsEvent.keyCode;
		var shift = jsEvent.shiftKey;
		var ctrl = jsEvent.ctrlKey;
		var alt = jsEvent.altKey;
				
		f_core.Debug(f_clientValidator, "KeyUp: keyCode="+keyCode+" shift="+shift+" ctrl="+ctrl+" alt="+alt);

		validator._applyProcessors(keyCode, shift, ctrl, alt);
	
		return true;
	},	
	/**
	 * @method private static
	 */
	_ChangeKeyCode: function(component, retCode, jsEvent) {
		if (f_core.IsInternetExplorer()) {
			jsEvent.keyCode=retCode;
			return true;
		}
	
		if (f_core.IsGecko()) {
			var ch=String.fromCharCode(retCode);
			
			// initKeyEvent() : Un trou de sécurité ??? ! 
			// C'était pourtant bien pratique !
			// bref, comme d'ab ... on bidouille ...
			if (f_core.IsGeckoDisableDispatchKeyEvent()) {
				var oldScrollTop=component.scrollTop;
				var oldScrollLeft=component.scrollLeft;
				var oldScrollWidth=component.scrollWidth;
				
				var selectionStart=component.selectionStart;
				var selectionEnd=component.selectionEnd;
				
				var value=component.value;
				component.value = value.substring(0, selectionStart)+ ch + value.substring(selectionEnd);
									
				component.setSelectionRange(selectionStart + ch.length, selectionStart + ch.length);
				var deltaW = component.scrollWidth - oldScrollWidth
				if (component.scrollTop==0) {
					component.scrollTop=oldScrollTop;
				}
				if (component.scrollLeft==0) {
					component.scrollLeft=oldScrollLeft+deltaW
				}
				return false;
			}

			// Justement, le fameux trou de sécurité ...
			var keyEvent=document.createEvent("KeyEvents");
			keyEvent.initKeyEvent("keypress", true, true, document.defaultView, false, false, false, false, 0, retCode) ;
			component.dispatchEvent(keyEvent);
			
			return false;
		}
		
		return true;
	},
	/**
	 * @method private hidden
	 */
	PerformMessageError: function(validator, type, lastError, lastErrorArgs) {
		f_core.Debug(f_clientValidator, "Perform message error. type='"+type+"' "+((lastError)?("severity='"+lastError.severity+"' summary='"+lastError.summary+"' detail='"+lastError.detail+"'"):("no error"))+"'.");
		if (!lastError) {
			return false;
		}
		
		var component=validator._component;
		
		var messageContext=f_messageContext.Get(component);
		if (!messageContext) {
			return;
		}
		
		var severity=lastError.severity;
		if (!severity) {
			severity=f_messageObject.SEVERITY_ERROR;
		}
		
		var message=new f_messageObject(severity, lastError.summary, lastError.detail);
		messageContext.f_addMessageObject(component, message);

		return true; // On arrete la, les messages ...
	},
	/**
	 * @method private static
	 */
	_PerformAlertError: function(validator, type, lastError, lastErrorArgs) {
		if (!lastError) {
			return false;
		}

		f_core.Debug(f_clientValidator, "PerformAlertError: Add alert error. type='"+type+"' "+((lastError)?("summary='"+lastError.summary+"' detail='"+lastError.detail+"'"):("no error"))+"'.");
		
		var message=lastError.summary;
		if (!message) {
			message=lastError.detail;
		}
		
		if (!message) {
			return;
		}
		
		alert(message);
		
		return true; // On arrete la, les messages ...
	},
	Filter_generic: function(val,expr,keyCode,keyChar) {
		f_core.Assert(expr instanceof RegExp, "Not a regular expression. '"+expr+"'.");
		
		return (expr.test(keyChar));
	},
	
	Translator_generic: function(validator, expr, keyCode, keyChar) {
		return keyCode;
	},

	/*=============================================================================
		ERROR HANDLERS
	=============================================================================*/
	Error_msg: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, false, false);
	},
	Error_msg_color: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, false);
	},
	Error_msg_color_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, true);
	},
	Error_msg_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, false, true);
	},
	Error_color: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, true, false);
	},
	Error_color_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, true, true);
	},
	Error_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, false, true);
	},
	Error_null: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, false, false);
	},
	Error_default: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, true);
	},
	Error_generic: function(validator, type, error, useMessage, useColor, useFocus) {
		var component = validator.f_getComponent();
		var setMsg = false;
		var setCol = false;
		var unsetCol = false;
		var setFoc = false;
		
		switch (type) {
		case f_clientValidator.FILTER: 
			break;
			
		case f_clientValidator.TRANSLATOR: 
			break;
			
		case f_clientValidator.CHECKER:
		case f_clientValidator.FORMATTER:
			setMsg = true;
			setCol = true;
			setFoc = true;
			break;

		case f_clientValidator.BEHAVIOR:
			setMsg = true;
			setFoc = true;
			break;

		case f_clientValidator.SUCCESS:
			unsetCol = true;
			break;

		default:
			f_core.Error(f_clientValidator, "Unknown Error Type: "+type);
			break;
		}

//		alert("GENERIC ERROR: "+validator+"/"+type+"/"+error+"/"+useMessage+"\n"+setMsg+"/"+setCol+"/"+setFoc);		

		if (useMessage && setMsg) {
			//MESSAGE(error[0], "adonis_validatorEx", error[1], error[2]);
			alert("summary="+error._summary+"\ndetail="+error._detail);
		}
		if (useColor && setCol) {
			if (this._oldComponentColor === undefined) {
				this._oldComponentColor = component.f_getForegroundColor();
			}
			component.f_setForegroundColor("red");
		}
		if (useColor && unsetCol) {
			if (this._oldComponentColor !== undefined) {
				component.f_setForegroundColor(this._oldComponentColor);
			}
		}
		if (useFocus && setFoc) {
//			alert("Set focus !");
			f_core.SetFocus(component);
		}
		return true;
	}
}

var __prototype = {
	
	f_clientValidator: function(component, parameters) {
		f_core.Assert(component.nodeType, "f_clientValidator(): Invalid component parameter ("+component+")");

		this._component = component;
		
		if (component._input) {
			this._input = component._input;
			
		} else {
			this._input = component;
		}
				
		f_core.AddCheckListener(component, this);	
		
		var validator=this;
		component.f_addEventListener(f_event.RESET, function(event) {
			return validator._onReset(event);
		});
				
		f_core.Assert(!component._validator, "f_clientValidator.constructor: Only one validator by component! (id="+ component.id+")");
		component._validator = this;
		
		var componentValue=component.value;
		if (componentValue === undefined || componentValue == null) {
			componentValue="";
		}
		
		this.f_setInputValue(componentValue);
		
		this._initialValue = componentValue;

		this._outputValue = "";
		
		this._parameters=parameters;
		
		f_core.Debug(f_clientValidator, "Construct new validator for component '"+component.id+"' with params='"+this._parameters+"' initialValue='"+componentValue+"'.");
		
		component.f_addEventListener(f_event.FOCUS, f_clientValidator._OnFocus);
		component.f_addEventListener(f_event.BLUR, f_clientValidator._OnBlur);
	},
	f_finalize: function() {
		this._input = undefined;
		this._component = undefined;
		this._parameters = undefined; // Map<string, any>

// 		this._keyPressInstalled = undefined; // boolean
//		this._keyUpInstalled = undefined; // boolean
//		this._transient = undefined; // boolean
//		this._hasFocus = undefined; // boolean
//		this._firstApply = undefined; // boolean
//		this._checked=undefined; // boolean
//		this._outputValue=undefined; // string
//		this._initialValue=undefined; // string

		this._filters = undefined;
		this._translators = undefined;
		this._processors = undefined;
		
		this._onerror=undefined;  // function
		this._onerrorArguments=undefined;  // object[]
		
		this._oncheckerror=undefined;  // function 
		this._oncheckerrorArguments=undefined; // object[]
	},
	f_performCheckPre: function() {		
		// On applique pour générer les erreurs !
		this._checked=(this._applyAutoCheck(this.f_getInputValue(), true)!==false);
		
		f_core.Debug(f_clientValidator, "Precheck of component '"+this._component.id+"' returns "+this._checked);
	},
	f_performCheckValue: function() {
		if (this._checked) {
			return true;
		}
		
		f_core.SetFocus(this._component);

		return false;
	},
	_onReset: function() {
		f_core.Debug(f_clientValidator, "Reset component '"+this._component.id+"' (value='"+this._initialValue+"')");
	
		var bRet = this._applyAutoCheck(this._initialValue, false);
		
		if (this._hasFocus) {
			this._applyInputValue();
			
		} else {
			this._applyOutputValue();
		}
	},
	/**
	 * Called while setting textEntry text !
	 * 
	 * @method hidden
	 */
	f_updateValue: function() {
		// Check and format the updated value
		var bRet = this._applyAutoCheck(this._component.value, false);
		
		if (this._hasFocus) {
			this._applyInputValue();
			
		} else {
			this._applyOutputValue();
		}
		
		return bRet;
	},
	/**
	 * 
	 * 
	 * @method hidden
	 */
	f_isValidValue: function() {
		return this._applyAutoCheck(this._component.value, true);
	},
	_getInitialValue: function() { 
		return this._initialValue; 
	},
	f_setInputValue: function(val) { 
		this._transient=undefined;
		this._inputValue = val; 
	},
	f_getValue: function() { 
		var value=this.f_getInputValue();

		this._applyAutoCheck(value, false);
		
		var v=this.f_getOutputValue();
		
		f_core.Debug(f_clientValidator, "Return internal value  input='"+value+"' output='"+v+"'.");

		return v;
	},
	f_getInputValue: function() { 
		/**
		 * @author Joel Merlin
		 * Check for an extern call that occurs before field validation. This can
		 * happen when requesting internal value while calling a_getText() during
		 * key or text changed events...
		 * This is not a recommanded practice since validation has not yet occured.
		 * But in such case, we set the input value with raw value.
		 *
		 * Some times, when user modify text fields by using backspaces or delete, inputValue is not computed !
		 * so if the component has focus, we return input's value !
		 */
		if (this._transient || this._hasFocus) {
			return this.f_getComponent().value;
		}
		
		this._verifyFirstFocus();
		
		return this._inputValue; 
	},
	f_setOutputValue: function(val) { 
		this._outputValue = val; 
	},
	f_getOutputValue: function() { 
		/**
		 * @author Joel Merlin
		 * This call is private and should NEVER be used outside validatorEx code.
		 * However, if we are in a transient state, rather send back raw text value.
		 */
		if (this._transient) {
			return this.f_getComponent().value;
		}
		return this._outputValue; 
	},
	f_getComponent: function() {
		return this._component;
	},
	_applyInputValue: function() {
		var component = this.f_getComponent();
		var inVal = this.f_getInputValue();
	
		this._verifyFirstFocus();
		
		if (component.value != inVal) {
			component.value = inVal;
		}
	},
	_verifyFirstFocus: function() {
		if (this._firstApply) {
			return;
		}
		this._firstApply=true;
		
		var component = this.f_getComponent();
		
		var componentValue=component.value;
		if (componentValue==this._initialValue) {
			return;
		}
		
		this._initialValue = componentValue;
		
		this.f_setInputValue(componentValue);
	},
	_applyOutputValue: function() {
		var component = this.f_getComponent();
		
		component.value=this.f_getOutputValue();
	},
	_applyFilters: function(keyCode,keyChar) {
		var filters=this._filters;
		if (!filters) {
			return true;
		}
		
		var bRet = true;

		for (var i=0; i<filters.length; i++) {
			var f = filters[i];
			if (f instanceof RegExp) {
				bRet = f_clientValidator.Filter_generic(this, f, keyCode, keyChar);
				
			} else if (f instanceof Function) {
				bRet = f.call(f, this, keyCode, keyChar);
			}
			
			if (!bRet) {
				break;
			}
		}
		
		return bRet;
	},
	_applyProcessors: function(keyCode, shift, ctrl, alt) {
		var processors=this._processors;
		if (!processors) {
			return;
		}
		
		var component=this.f_getComponent();
		var params=[ this, keyCode, shift, ctrl, alt ];
		
		for (var i=0; i<processors.length; i++) {
			var p = processors[i];
			if (typeof(p)!="function") {
				continue;
			}
			
			var bRet = p.apply(component, params);
			if (!bRet) { 
				break;
			}
		}
	},
	_applyTranslators: function(keyCode, keyChar) {
		var translators=this._translators;
		if (!translators) {
			return keyCode;
		}

		var component=this.f_getComponent();
		var params=[ this, keyCode, keyChar ];
		for (var i=0; i<translators.length; i++) {
			var t = translators[i];
			
			var retCode=0;
			if (t instanceof RegExp) {
				retCode = f_clientValidator.Translator_generic(this, t, keyCode, keyChar);
				
			} else if (t instanceof Function) {
				retCode = t.apply(component, params);
			}
			
			if (retCode != keyCode) {
				keyCode = retCode;
				keyChar = String.fromCharCode(retCode);
			}
		}

		return keyCode;
	},
	_applyCheckers: function() {
		var checkers=this._checkers;
		var checkVal = this.f_getInputValue();
		
		if (!checkers) {
			return checkVal;
		}
		
		var component=this.f_getComponent();
		for (var i=0; i<checkers.length; i++) {
			var c = checkers[i];
			
			if (typeof(c)!="function") {
				continue;
			}
			
			checkVal = c.call(component, this, checkVal);
			var bRet = (checkVal != null);
			if (!bRet) {
				break;
			}

			this.f_setInputValue(checkVal);
		}
		return checkVal;
	},
	_applyFormatters: function() {
		var formatters=this._formatters;
		var formatVal = this.f_getOutputValue();

		if (!formatters) {
			return formatVal;
		}
	
		var component=this.f_getComponent();
		for (var i=0; i<formatters.length; i++) {
			var f = formatters[i];
			if (typeof(f)!="function") {
				continue;
			}

			formatVal = f.call(component, this, formatVal);
			var bRet = (formatVal != null);
			if (!bRet) {
				break;
			}
			
			this.f_setOutputValue(formatVal);
		}

		return formatVal;
	},
	_applyBehaviors: function() {
		var behaviors=this._behaviors;
		if (!behaviors) {
			return null;
		}

		var component=this.f_getComponent();
		var bRet=undefined;
		for (var i=0; i<behaviors.length; i++) {
			var f = behaviors[i];
			if (typeof(f)!="function") {
				continue;
			}
			
			bRet = f.call(component, this, this.f_getOutputValue());
			if (!bRet) {
				break;
			}
		}
		return bRet;
	},
	_applyAutoCheck: function(curVal, check) {
		var bRet = true;
		var bValid;
		var fError = (check)? this._oncheckerror:this._onerror;
		var fErrorArguments = (check)? this._oncheckerrorArguments:this._onerrorArguments;
		var handled;
		
		if (!fError && check) {
			if (window.f_messageContext) {
				fError=f_clientValidator.PerformMessageError;
				
			} else {
				fError=f_clientValidator._PerformAlertError;
			}
		}
		
		this.f_setInputValue(curVal);
		this.f_setOutputValue(curVal);

		// Call filters
		var filterVal = "";
		for (var i=0; i<curVal.length; i++) {
			bValid = this._applyFilters(curVal.charCodeAt(i), curVal.charAt(i));
			if (bValid) {
				filterVal += curVal.charAt(i);
			}
		}

		// Call translators
		var transVal = "";
		for (var i=0; i<filterVal.length; i++) {
			transVal += String.fromCharCode(this._applyTranslators(filterVal.charCodeAt(i), filterVal.charAt(i)));
		}
		if (curVal != transVal) {
			this.f_setInputValue(transVal);
			this.f_setOutputValue(transVal);
		}
	
		// Call checkers
		// @JM Checker has to deal with empty string
		var checkVal = this._applyCheckers();
		if (checkVal == null) {
			f_core.Debug(f_clientValidator, "Applyed Checker returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");
			bRet = false;
			if (fError) {
				try {
					handled = fError.call(fError, this, f_clientValidator.CHECKER, this.f_getLastError(), fErrorArguments);
					
				} catch (x) {
					f_core.Error(f_clientValidator, "Call of error function for component '"+this._component.id+"' throws exception.", x);
				}
			}
		} else {
			if (fError) {
				try {
					handled = fError.call(fError, this, f_clientValidator.CHECKER);
					
				} catch (x) {
					f_core.Error(f_clientValidator, "Call of error function for component '"+this._component.id+"' throws exception.", x);
				}
			}
			this.f_setInputValue(checkVal);
			this.f_setOutputValue(checkVal);
		}
	
		// Call formatters
		if (checkVal) {
			var formatVal = this._applyFormatters();
			if (formatVal == null) {
				f_core.Debug(f_clientValidator, "Applyed formatters returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");

				bRet = false;
				if (fError) {
					try {
						handled = fError.call(fError,this,f_clientValidator.FORMATTER,this.f_getLastError(), fErrorArguments);
						
					} catch (x) {
						f_core.Error(f_clientValidator, "Call of error function for component '"+this._component.id+"' throws exception.", x);
					}
				}
			} else {
				if (fError) {
					try {
						handled = fError.call(fError, this, f_clientValidator.FORMATTER);
						
					} catch (x) {
						f_core.Error(f_clientValidator, "Call of error function for component '"+this._component.id+"' throws exception.", x);
					}
				}
				this.f_setOutputValue(formatVal);
			}
		}
	
		if (bRet) {
			// Call behaviors
			var ret = this._applyBehaviors();
			// If set, get the returned value
			if (ret!==undefined) {
				bRet = ret;
			}
	
			try {
				// Otherwise, check error
				if (bRet == false) {
					f_core.Debug(f_clientValidator, "Applyed behaviors returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");
	
					if (fError && !handled) {
						handled = fError.call(fError, this, f_clientValidator.BEHAVIOR, this.f_getLastError(), fErrorArguments);
					}
					
				} else {
					if (fError) {
						handled = fError.call(fError, this, f_clientValidator.BEHAVIOR);
					}
				}

			} catch (x) {
				f_core.Error(f_clientValidator, "Call of error function for component '"+this._component.id+"' throws exception.", x);
			}
		}
			
		// Return text entry check status
		return bRet;
	},
	/**
	 * @method hidden final
	 */
	f_addFilter: function(expr) {
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addFilter: Filter parameter must be a function or a regexp");

		f_core.Debug(f_clientValidator, "Add filter to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		if (!this._keyPressInstalled) {
			this._component.f_insertEventListenerFirst(f_event.KEYPRESS, f_clientValidator._OnKeyPress);
			this._keyPressInstalled = true;
		}
		
		var filters=this._filters;
		if (!filters) {
			filters = new Array;
			this._filters = filters;
		}
		filters.push(expr);
	},
	/**
	 * @method hidden final
	 * @param function expr
	 * @return void
	 */
	f_addProcessor: function(expr) {
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addProcessor: Processor parameter must be a function or a regexp");

		f_core.Debug(f_clientValidator, "Add processor to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));
		
		if (!this._keyUpInstalled) {
			this._component.f_insertEventListenerFirst(f_event.KEYUP, f_clientValidator._OnKeyUp);
			this._keyUpInstalled = true;
		}
		
		var processors=this._processors;
		if (!processors) {
			processors = new Array;
			this._processors=processors;
		}
		processors.push(expr);
	},
	/**
	 * @method hidden final
	 */
	f_addTranslator: function(expr) {
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addTranslator: Translator parameter must be a function or a regexp");

		f_core.Debug(f_clientValidator, "Add translator to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		if (!this._keyPressInstalled) {
			this._component.f_insertEventListenerFirst(f_event.KEYPRESS, f_clientValidator._OnKeyPress);
			this._keyPressInstalled = true;
		}
		
		var translators=this._translators;
		if (!translators) {
			translators = new Array;
			this._translators = translators
		}
		translators.push(expr);
	},
	/**
	 * @method hidden final
	 */
	f_addChecker: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addChecker: Checker parameter must be a function");

		f_core.Debug(f_clientValidator, "Add checker function to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		var checkers=this._checkers;
		if (!checkers) {
			checkers=new Array;
			this._checkers = checkers;
		}
		checkers.push(expr);
	},
	/**
	 * @method hidden final
	 */
	f_addFormatter: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addFormatter: Formatter parameter must be a function");

		f_core.Debug(f_clientValidator, "Add formatter function to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		var formatters=this._formatters;
		if (!formatters) {
			formatters = new Array;
			this._formatters = formatters;
		}
		formatters.push(expr);
	},
	/**
	 * @method hidden final
	 */
	f_addBehavior: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addBehavior: Behavior parameter must be a function");

		f_core.Debug(f_clientValidator, "Add behavior function to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		var behaviors=this._behaviors;
		if (!behaviors) {
			behaviors = new Array;
			this._behaviors=behaviors;
		}
		behaviors.push(expr);
	},
	/**
	 * @method hidden final
	 */
	f_addOnError: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addOnError: OnError parameter must be a function");

		f_core.Debug(f_clientValidator, "Add onError function to validator attached to component '"+this._component.id+"' :\n"
		+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		this._onerror = expr;
		
		if (arguments.length<2) {
			return;
		}
		
		var l=new Array;
		f_core.PushArguments(l, arguments, 1);
		
		this._onerrorArguments=l;
	},
	/**
	 * @method hidden final
	 */
	f_addOnCheckError: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addOnCheckError: OnCheckError parameter must be a function");

		f_core.Debug(f_clientValidator, "Add onCheckError function to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		this._oncheckerror = expr;
		
		if (arguments.length<2) {
			return;
		}
		
		var l=new Array();
		f_core.PushArguments(l, arguments, 1);
		
		this._oncheckErrorArguments=l;
	},

	/**
	 * @method public final
	 */
	f_setLastError: function(summary, detail, severity) {
		f_core.Debug(f_clientValidator, "SetLastError: summary='"+summary+"' detail='"+detail+"' severity='"+severity+"'.");
	
		if (typeof(severity)=="string") {
			try {
				severity=parseInt(severity, 10);
				
			} catch (x) {
				f_core.Error(f_clientValidator, "Invalid severity expression '"+severity+"'.", x);
			}
		}
	
		this._lastErrorObject={
			summary: summary,
			detail: detail,
			severity: severity
		};
	},
	/**
	 * @method public final
	 */
	f_getLastError: function() {
		return this._lastErrorObject;
	},
	/**
	 * @method public final
	 */
	f_getParameter: function(name) {
		if (!this._parameters) {
			return null;
		}
		
		return this._parameters[name];
	},
	/**
	 * @method public final
	 */
	f_getIntParameter: function(name, def) {
		var r = this.f_getParameter(name);
		if (r === undefined) {
			return def;
		}
		return parseInt(r, 10);
	},
	/**
	 * @method public final
	 */
	f_getStringParameter: function(name, def) {
		var r = this.f_getParameter(name);
		if (r === undefined) {
			return def;
		}
		return r;
	},
	/**
	 * @method public final
	 */
	f_getBoolParameter: function(name, def) {
		var r = this.f_getParameter(name);
		if (r === undefined) {
			return def;
		}
		return (r == "true");
	},
	/**
	 * @method public final
	 */
	f_getObject: function() {
		return this._object;
	},
	/**
	 * @method public final
	 */
	f_setObject: function(object) {
		this._object=object;
	}
}

var f_clientValidator=new f_class("f_clientValidator", null, __static, __prototype);
