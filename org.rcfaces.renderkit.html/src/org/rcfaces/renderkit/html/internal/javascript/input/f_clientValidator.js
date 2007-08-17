/*
 * $Id$
 */

/**
 * f_clientValidator class
 *
 * @class hidden f_clientValidator extends f_object
 */
 
var __statics = {

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
	
	/**
	 * @method protected static
	 * @return void
	 */
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
			validator.f_setOnError(error);
		}

		var checkError=f_core.GetAttribute(component, "v:vCheckError");
		if (checkError) {
			checkError=f_clientValidator._EvalFunction(checkError);
			validator.f_setOnCheckError(checkError);
		}

		var converter=f_core.GetAttribute(component, "v:converter");
		if (converter) {
			converter=f_clientValidator._EvalFunction(converter, true);
			validator.f_setConverter(converter);
		}
		
		var value=validator._input.value;
		validator._applyAutoCheck(value, false);
		validator._applyOutputValue();
	
		validator._initialFormattedValue=validator._input.value;
	
		return validator;
	},
	/**
	 * @method private static 
	 */
	_EvalFunction: function(expr, resolveObject) {
		var expressions=f_clientValidator._Expressions;

		var f;
		if (!expressions) {
			expressions=new Object;
			f_clientValidator._Expressions=expressions;

		} else {
			f=expressions[expr];
			if (f) {
				return f;
			}
		}
	
		if (resolveObject && expr.charAt(0)=='/') {
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
		
		if (resolveObject) {
			f_core.Assert(typeof(f)=="object", "Invalid expression for object : '"+expr+"'='"+f+"'.");

		} else {
			f_core.Assert(typeof(f)=="function" || (f instanceof RegExp), "Invalid expression for function : '"+expr+"'='"+f+"'.");
		}
			
		expressions[expr]=f;
		
		return f;
	},

	/**
	 * @method private static
	 */
	_OnFocus: function(evt) {
		f_core.Debug(f_clientValidator, "_OnFocus: focus on client validator ");
		if (this.f_isReadOnly()) {
			return true;
		}
	
		var validator=this._validator;
	
		validator._applyInputValue();
		validator._hasFocus = true;
		
		return true;
	},
	/**
	 * @method private static
	 */
	_OnBlur: function(evt) {
		f_core.Debug(f_clientValidator, "_OnFocus: focus on client validator ");

		if (this.f_isReadOnly()) {
			return true;
		}

		var validator=this._validator;

		var bRet = validator._applyAutoCheck(this._input.value, false);
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
		
		validator.f_setInputValue(this._input.value);
		
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
		
		validator.f_setInputValue(this._input.value);

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
				var input=component._input;
				
				var oldScrollTop=input.scrollTop;
				var oldScrollLeft=input.scrollLeft;
				var oldScrollWidth=input.scrollWidth;
				
				var selectionStart=input.selectionStart;
				var selectionEnd=input.selectionEnd;
				
				var value=input.value;
				
				input.value = value.substring(0, selectionStart)+ ch + value.substring(selectionEnd);
									
				input.setSelectionRange(selectionStart + ch.length, selectionStart + ch.length);
				var deltaW = input.scrollWidth - oldScrollWidth
				if (!input.scrollTop) {
					input.scrollTop=oldScrollTop;
				}
				if (!input.scrollLeft) {
					input.scrollLeft=oldScrollLeft+deltaW;
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
	 * @method private static hidden
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

		f_core.SetFocus(component, true);

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
	/**
	 * @method public static
	 */
	Filter_generic: function(val,expr,keyCode,keyChar) {
		f_core.Assert(expr instanceof RegExp, "f_clientValidator.Filter_generic: Not a regular expression. '"+expr+"'.");
		
		return (expr.test(keyChar));
	},
	
	/**
	 * @method public static
	 */
	Translator_generic: function(validator, expr, keyCode, keyChar) {
		return keyCode;
	},

	/*=============================================================================
		ERROR HANDLERS
	=============================================================================*/
	/**
	 * @method public static
	 */
	Error_msg: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, false, false);
	},
	/**
	 * @method public static
	 */
	Error_msg_color: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, false);
	},
	/**
	 * @method public static
	 */
	Error_msg_color_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, true);
	},
	/**
	 * @method public static
	 */
	Error_msg_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, false, true);
	},
	/**
	 * @method public static
	 */
	Error_color: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, true, false);
	},
	/**
	 * @method public static
	 */
	Error_color_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, true, true);
	},
	/**
	 * @method public static
	 */
	Error_focus: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, false, true);
	},
	/**
	 * @method public static
	 */
	Error_null: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, false, false, false);
	},
	/**
	 * @method public static
	 */
	Error_default: function(validator, type, error) {
		return f_clientValidator.Error_generic(validator, type, error, true, true, true);
	},
	/**
	 * @method hidden static 
	 * @param f_clientValidator validator
	 * @param number type
	 * @param f_messageObject error
	 * @param boolean useMessage
	 * @param boolean useColor
	 * @param boolean useFocus
	 * @return boolean
	 */
	Error_generic: function(validator, type, error, useMessage, useColor, useFocus) {
		f_core.Debug(f_clientValidator, "Error_generic: type='"+type+"' error='"+error+"' useMessage="+useMessage+" useColor="+useColor+" useFocus="+useFocus);

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
			setMsg = !!error;
			setCol = setMsg;
			setFoc = setCol;
			break;

		case f_clientValidator.BEHAVIOR:
			setMsg = !!error;;
			setFoc = setMsg;
			break;

		case f_clientValidator.SUCCESS:
			unsetCol = true;
			break;

		default:
			f_core.Error(f_clientValidator, "Error_generic: Unknown Error Type: "+type);
			break;
		}

//		alert("GENERIC ERROR: "+validator+"/"+type+"/"+error+"/"+useMessage+"\n"+setMsg+"/"+setCol+"/"+setFoc);		

		var component = validator.f_getComponent();

		if (useMessage && setMsg) {
			//MESSAGE(error[0], "adonis_validatorEx", error[1], error[2]);
			alert("summary="+error.f_getSummary()+"\ndetail="+error.f_getDetail());
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
			f_core.SetFocus(component, true);
		}
		return true;
	}
}

var __members = {
	
	f_clientValidator: function(component, parameters) {
		f_core.Assert(component.nodeType, "f_clientValidator(): Invalid component parameter ("+component+")");

		this._component = component;
		
		if (component.f_getInput) {
			this._input = component.f_getInput();
			
		} else {
			this._input = component;
		}
		
		f_core.AddCheckListener(component, this);	
		
		var validator=this;
		component.f_insertEventListenerFirst(f_event.RESET, function(event) {
			return validator._onReset(event);
		});
				
		f_core.Assert(!component._validator, "f_clientValidator.constructor: Only one validator by component! (id="+ component.id+")");
		component._validator = this;
		
		var componentValue=this._input.value;
		if (componentValue === undefined || componentValue == null) {
			componentValue="";
		}
		
		this.f_setInputValue(componentValue);
		
		this._initialValue = componentValue;

		this._outputValue = "";
		
		this._parameters=parameters;
		
		f_core.Debug(f_clientValidator, "Construct new validator for component '"+component.id+"' with params='"+this._parameters+"' initialValue='"+componentValue+"'.");
		
		component.f_insertEventListenerFirst(f_event.FOCUS, f_clientValidator._OnFocus);
		component.f_insertEventListenerFirst(f_event.BLUR, f_clientValidator._OnBlur);
	},
	f_finalize: function() {
		this._input = undefined;
		this._component = undefined;
		this._parameters = undefined; // Map<string, any>

// 		this._keyPressInstalled = undefined; // boolean
//		this._keyUpInstalled = undefined; // boolean
//		this._hasFocus = undefined; // boolean
//		this._firstApply = undefined; // boolean
//		this._checked=undefined; // boolean
//		this._outputValue=undefined; // string
//		this._initialValue=undefined; // string
//		this._initialFormattedValue=undefined; // string

		this._filters = undefined; // function[]
		this._translators = undefined; // function[]
		this._processors = undefined; // function[]
		this._converter = undefined; // object
		
		this._onError=undefined;  // function
		this._onErrorArguments=undefined;  // object[]
		
		this._onCheckError=undefined;  // function 
		this._onCheckErrorArguments=undefined; // object[]
	},
	f_performCheckPre: function(event) {		
		// On applique pour générer les erreurs !
		var value=this.f_getInputValue(true);
		
		this._checked=(this._applyAutoCheck(value, true)!==false);
		
		f_core.Debug(f_clientValidator, "f_performCheckPre: Precheck of component '"+this._component.id+"' returns "+this._checked+" value='"+value+"'.");
	},
	/**
	 * @method hidden
	 * @param f_event event
	 * @return boolean 
	 */
	f_performCheckValue: function(event) {
		if (this._checked) {
			return true;
		}
		
		//f_core.SetFocus(this._component, true);

		return false;
	},
	/**
	 * @method private
	 */
	_onReset: function() {
		f_core.Debug(f_clientValidator, "_onReset: Reset component '"+this._component.id+"' (initialValue='"+this._initialValue+"')");
	
		var self=this;
		window.setTimeout(function() {
			//var bRet = 
			self._applyAutoCheck(self._initialValue, false);
			
			if (self._hasFocus) {
				self._applyInputValue();
				
			} else {
				self._applyOutputValue();
			}
		}, 100);
	},
	/**
	 * Called while setting textEntry text !
	 * 
	 * @method hidden
	 */
	f_updateValue: function(value) {
		if (value===undefined) {
			value=this._input.value;
		}
		
		f_core.Debug(f_clientValidator, "f_updateValue: Update value '"+value+"' (hasFocus="+this._hasFocus+").");
		
		// Check and format the updated value
		var bRet = this._applyAutoCheck(value, false);
		
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
		return this._applyAutoCheck(this._input.value, true);
	},
	_getInitialValue: function() { 
		return this._initialValue; 
	},
	/**
	 * @method hidden
	 * @param String val
	 * @return void
	 */
	f_setInputValue: function(val) { 
		if (this._inputValue != val) {
			f_core.Debug(f_clientValidator, "f_setInputValue: Change internal input value '"+val+"'.");
		}
		
		this._inputValue = val; 
	},
	/**
	 * @method hidden
	 * @return String
	 */
	f_getValue: function() { 
		var value=this.f_getInputValue(true);

		this._applyAutoCheck(value, false);
		
		var v=this.f_getOutputValue();
		
		f_core.Debug(f_clientValidator, "f_getValue: Return internal value  input='"+value+"' output='"+v+"'.");

		return v;
	},
	/**
	 * @method hidden
	 * @return String
	 */
	f_serializeValue: function() {
		var value=this.f_getInputValue(true);

		this._applyAutoCheck(value, false);
		
		var v=this.f_getInputValue(false);
		
		f_core.Debug(f_clientValidator, "f_serializeValue: Return serialized value input='"+value+"' serialized='"+v+"'.");

		return v;
	},
	/**
	 * @method hidden
	 * @return String
	 */
	f_getInputValue: function(verifyFocus) { 
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
		if (verifyFocus && this._hasFocus) {
			return this._input.value;
		}
		
		this._verifyFirstFocus();
		
		return this._inputValue; 
	},
	/**
	 * @method hidden
	 * @param String val
	 * @return void
	 */
	f_setOutputValue: function(val) { 
		if (this._outputValue != val) {
			f_core.Debug(f_clientValidator, "f_setOutputValue: Change internal output value to '"+val+"'.");
		}
		
		this._outputValue = val; 
	},
	/**
	 * @method hidden
	 * @return String
	 */
	f_getOutputValue: function() { 
		/**
		 * @author Joel Merlin
		 * This call is private and should NEVER be used outside validatorEx code.
		 * However, if we are in a transient state, rather send back raw text value.
		 */
		return this._outputValue; 
	},
	/**
	 * @method hidden
	 * @return HTMLElement
	 */
	f_getComponent: function() {
		return this._component;
	},
	/**
	 * @method private
	 * @return void
	 */
	_applyInputValue: function() {
		var input = this._input;
		var inVal = this.f_getInputValue();
	
		this._verifyFirstFocus();
		
		f_core.Debug(f_clientValidator, "_applyInputValue: Set value '"+inVal+"'.");
		if (input.value != inVal) {
			input.value = inVal;
		}
	
		// On selectionne car IE remet le focus au début du champ sinon !
		input.select();
	},
	/**
	 * @method private
	 */
	_verifyFirstFocus: function() {
		if (this._firstApply) {
			return;
		}
		this._firstApply=true;
		
		var componentValue=this._input.value;
		if (componentValue==this._initialFormattedValue) {
			return;
		}
		
		f_core.Debug(f_clientValidator, "_verifyFirstFocus: Value has changed ! modify initial value ...");
		
		this._initialValue = componentValue;
		
		this.f_setInputValue(componentValue);
	},
	/**
	 * @method private
	 * @return void
	 */
	_applyOutputValue: function() {
		var value=this.f_getOutputValue();
		
		f_core.Debug(f_clientValidator, "_applyOutputValue: Set value '"+value+"'.");
		this._input.value=value;
	},
	_applyFilters: function(keyCode, keyChar) {
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
	_applyCheckers: function(checkVal) {
		var checkers=this._checkers;
		
		if (!checkers) {
			return checkVal;
		}
		
		var component=this.f_getComponent();
		for (var i=0; i<checkers.length; i++) {
			var c = checkers[i];
			f_core.Assert(typeof(c)=="function", "f_clientValidator._applyCheckers: Unknown type of checker '"+c+"'.");
			
			var newVal = c.call(component, this, checkVal);

			f_core.Debug(f_clientValidator, "_applyCheckers: Check (#"+i+") value current='"+checkVal+"' new='"+newVal+"'.");

			if (newVal === null) { // Une erreur, on arrete la ...
				return null;
			}

			if (newVal==checkVal) { // Prochain checker
				continue;
			}

			this.f_setInputValue(newVal);
			checkVal=newVal;
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
			f_core.Assert(typeof(f)=="function", "f_clientValidator._applyFormatters: Unknown type of translator '"+f+"'.");

			formatVal = f.call(component, this, formatVal);
			var bRet = (formatVal != null);
			if (!bRet) {
				break;
			}
			
			this.f_setOutputValue(formatVal);
		}

		return formatVal;
	},
	/**
	 * @method private
	 */
	_applyBehaviors: function() {
		var behaviors=this._behaviors;
		if (!behaviors) {
			return null;
		}

		var component=this.f_getComponent();
		var bRet=undefined;
		for (var i=0; i<behaviors.length; i++) {
			var f = behaviors[i];
			f_core.Assert(typeof(f)=="function", "f_clientValidator._applyBehaviors: Unknown type of behavior '"+f+"'.");
			
			bRet = f.call(component, this, this.f_getOutputValue());
			if (!bRet) {
				break;
			}
		}
		return bRet;
	},
	/**
	 * @method private
	 * @param String curVal Current value
	 * @param boolean check Check mode
	 * @return boolean
	 */
	_applyAutoCheck: function(curVal, check) {
		var bRet = true;
		var bValid;
		var fError = (check)? this._onCheckError:this._onError;
		var fErrorArguments = (check)? this._onCheckErrorArguments:this._onErrorArguments;
		var handled;
		
		if (!fError && check) {
			fError=f_clientValidator.PerformMessageError;
		}
		
		this.f_setInputValue(curVal);
		this.f_setOutputValue(curVal);

		// Call filters
		var filterVal = "";
		for (var i=0; i<curVal.length; i++) {
			var ch=curVal.charAt(i);
			
			bValid = this._applyFilters(curVal.charCodeAt(i), ch);
			if (bValid) {
				filterVal +=ch;
			}
		}

		// f_core.Debug(f_clientValidator, "Apply auto check after filters input='"+this._inputValue+"' output='"+this._outputValue+"'.");

		// Call translators
		var transVal = "";
		for (var i=0; i<filterVal.length; i++) {
			var t=this._applyTranslators(filterVal.charCodeAt(i), filterVal.charAt(i));
			transVal += String.fromCharCode(t);
		}
		if (curVal != transVal) {
			curVal=transVal;
			this.f_setInputValue(transVal);
			this.f_setOutputValue(transVal);
		}
	
		// f_core.Debug(f_clientValidator, "Apply auto check after translators input='"+this._inputValue+"' output='"+this._outputValue+"'.");
	
		// Call checkers
		// @JM Checker has to deal with empty string
		var checkVal = this._applyCheckers(curVal);
		f_core.Debug(f_clientValidator, "_applyAutoCheck: apply checkers returns '"+checkVal+"' curVal='"+curVal+"'");

		if (checkVal === null) {
			f_core.Debug(f_clientValidator, "_applyAutoCheck: Applyed Checker returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");
			bRet = false;
			if (fError) {
				try {
					handled = fError.call(fError, this, f_clientValidator.CHECKER, this.f_getLastError(), fErrorArguments);
					
				} catch (x) {
					f_core.Error(f_clientValidator, "_applyAutoCheck: Call of error function for component '"+this._component.id+"' throws exception.", x);
				}
			}
		} else {
			if (fError) {
				try {
					handled = fError.call(fError, this, f_clientValidator.CHECKER);
					
				} catch (x) {
					f_core.Error(f_clientValidator, "_applyAutoCheck: Call of error function for component '"+this._component.id+"' throws exception.", x);
				}
			}
			if (curVal!=checkVal) {
				this.f_setInputValue(checkVal);
				this.f_setOutputValue(checkVal);
			}
		}

		// f_core.Debug(f_clientValidator, "Apply auto check after checkers input='"+this._inputValue+"' output='"+this._outputValue+"'.");
	
		// Call formatters
		if (checkVal) {
			var formatVal = this._applyFormatters();
			f_core.Debug(f_clientValidator, "_applyAutoCheck: apply formatters returns '"+formatVal+"'");

			if (formatVal == null) {
				f_core.Debug(f_clientValidator, "_applyAutoCheck: Applyed formatters returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");

				bRet = false;
				if (fError) {
					try {
						handled = fError.call(fError,this,f_clientValidator.FORMATTER,this.f_getLastError(), fErrorArguments);
						
					} catch (x) {
						f_core.Error(f_clientValidator, "_applyAutoCheck: Call of error function for component '"+this._component.id+"' throws exception.", x);
					}
				}
			} else {
				if (fError) {
					try {
						handled = fError.call(fError, this, f_clientValidator.FORMATTER);
						
					} catch (x) {
						f_core.Error(f_clientValidator, "_applyAutoCheck: Call of error function for component '"+this._component.id+"' throws exception.", x);
					}
				}
				this.f_setOutputValue(formatVal);
			}
		}
	
		// f_core.Debug(f_clientValidator, "Apply auto check after formatters input='"+this._inputValue+"' output='"+this._outputValue+"'.");
	
		if (bRet) {
			// Call behaviors
			var ret = this._applyBehaviors();
			f_core.Debug(f_clientValidator, "_applyAutoCheck: apply behaviors returns '"+ret+"'");
			
			// If set, get the returned value
			if (ret!==undefined) {
				bRet = ret;
			}
	
			try {
				// Otherwise, check error
				if (bRet == false) {
					f_core.Debug(f_clientValidator, "_applyAutoCheck: Applyed behaviors returns error '"+this.f_getLastError()+"' for component '"+this._component.id+"'. (handled="+handled+")");
	
					if (fError && !handled) {
						handled = fError.call(fError, this, f_clientValidator.BEHAVIOR, this.f_getLastError(), fErrorArguments);
					}
					
				} else {
					if (fError) {
						handled = fError.call(fError, this, f_clientValidator.BEHAVIOR);
					}
				}

			} catch (x) {
				f_core.Error(f_clientValidator, "_applyAutoCheck: Call of error function for component '"+this._component.id+"' throws exception.", x);
			}
		}
		
		// f_core.Debug(f_clientValidator, "Apply auto check after behaviors input='"+this._inputValue+"' output='"+this._outputValue+"'.");
			
		// Return text entry check status
		return bRet;
	},
	/**
	 * @method hidden final
	 */
	f_addFilter: function(expr) {
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addFilter: Filter parameter must be a function or a regexp. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addFilter: Add filter to validator attached to component '"+this._component.id+"' :\n"
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
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addProcessor: Processor parameter must be a function or a regexp. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addProcessor: Add processor to validator attached to component '"+this._component.id+"' :\n"
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
		f_core.Assert(typeof(expr)=="function" || (expr instanceof RegExp), "f_clientValidator.f_addTranslator: Translator parameter must be a function or a regexp. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addTranslator: Add translator to validator attached to component '"+this._component.id+"' :\n"
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
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addChecker: Checker parameter must be a function. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addChecker: Add checker function to validator attached to component '"+this._component.id+"' :\n"
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
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addFormatter: Formatter parameter must be a function. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addFormatter: Add formatter function to validator attached to component '"+this._component.id+"' :\n"
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
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_addBehavior: Behavior parameter must be a function. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_addBehavior: Add behavior function to validator attached to component '"+this._component.id+"' :\n"
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
	f_setOnError: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_setOnError: OnError parameter must be a function. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_setOnError: Set onError function to validator attached to component '"+this._component.id+"' :\n"
		+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		this._onError = expr;
		
		if (arguments.length<2) {
			return;
		}
		
		var l=f_core.PushArguments(null, arguments, 1);
		
		this._onErrorArguments=l;
	},
	/**
	 * @method hidden final
	 */
	f_setOnCheckError: function(expr) {
		f_core.Assert(typeof(expr)=="function", "f_clientValidator.f_setOnCheckError: OnCheckError parameter must be a function. ("+expr+")");

		f_core.Debug(f_clientValidator, "f_setOnCheckError: Set onCheckError function to validator attached to component '"+this._component.id+"' :\n"
			+((String(expr).length>64)?(String(expr).substring(0, 64)+"  ..."):(String(expr))));

		this._onCheckError = expr;
		
		if (arguments.length<2) {
			return;
		}
		
		var l=f_core.PushArguments(null, arguments, 1);
		
		this._onCheckErrorArguments=l;
	},
	/**
	 * @method hidden final
	 */
	f_setConverter: function(converter) {
		f_core.Assert(typeof(converter)=="object", "f_clientValidator.f_setConverter: Converter must be an object. ("+converter+")");
		f_core.Assert(typeof(converter.f_getAsObject)=="function", "f_clientValidator.f_setConverter: f_getAsObject of Converter must be a function. ("+converter.f_getAsObject+")");
		f_core.Assert(typeof(converter.f_getAsString)=="function", "f_clientValidator.f_setConverter: f_getAsString of Converter must be a function. ("+converter.f_getAsString+")");		
		
		this._converter=converter;
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
	f_getParameter: function(name, def) {
		if (!this._parameters) {
			return def;
		}
		
		var r=this._parameters[name];
		if (r===undefined) {
			return def;
		}
		
		return r;
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
	},
	/**
	 * @method public final
	 */
	f_getConvertedValue: function() {
		var value=this.f_getValue();
		
		var converter=this._converter;
		if (!converter) {
			return value;
		}
		
		try {
			return converter.f_getAsObject(this, value);
				
		} catch (x) {
			f_core.Error(f_clientValidator, "Exception when calling converter with string '"+value+"'. (converter='"+converter+"')", x);
			
			throw x;
		}
	},
	/**
	 * @method public final
	 */
	f_setConvertedValue: function(value) {		
		var converter=this._converter;
		if (!converter) {
			f_core.Debug(f_clientValidator, "No conversion, returns false");

			return false;
		}

		try {
			value=converter.f_getAsString(this, value);
			
		} catch (x) {
			f_core.Error(f_clientValidator, "Exception when calling converter with object '"+value+"'. (converter='"+converter+"')", x);
			
			throw x;
		}

		f_core.Debug(f_clientValidator, "Update value of converted value="+value);
		
		this.f_updateValue(value);
		
		return true;
	}
}

new f_class("f_clientValidator", null, __statics, __members, f_object);
