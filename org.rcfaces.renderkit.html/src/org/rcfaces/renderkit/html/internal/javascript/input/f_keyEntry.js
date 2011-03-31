/* 
 * $Id$
 */


/**
 * 
 * @class f_keyEntry extends f_textEntry, fa_commands, fa_readOnly, fa_editable, fa_clientValidatorParameters, fa_filterProperties
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __statics = {

	/**
	 * @field private static final Number
	 */
	_UNLIMITED_TEXT_SIZE: 999999,

	/**
	 * @field private static final Number
	 */
	_DEFAULT_SUGGESTION_DELAY_MS: 300,

	/**
	 * @field private static final Number
	 */
	_DEFAULT_SUGGESTION_MIN_CHARS: 0,

	/**
	 * @method private static
	 * @return Boolean
	 * @context event:evt
	 */
	_OnBeforeDeactivate: function(evt) {
		// Que sur IE ... donc ...
		evt=f_core.GetJsEvent(this);
		
		var toElement=evt.toElement;
		if (!toElement) {
			return true;
		}

		var keyEntry=evt.srcElement.f_link;
		
		for(;toElement.parentNode;toElement=toElement.parentNode) {
			if (toElement!=keyEntry) {
				continue;
			}
			
			return f_core.CancelJsEvent(evt);
		}
		
		return true;
	}
}

var __members = {
	
	f_keyEntry: function() {
		this.f_super(arguments);
			
		this._suggestionDelayMs=f_core.GetNumberAttribute(this, f_core._VNS+":suggestionDelayMs", f_keyEntry._DEFAULT_SUGGESTION_DELAY_MS);
		
		this._suggestionMinChars=f_core.GetNumberAttribute(this, f_core._VNS+":suggestionMinChars", f_keyEntry._DEFAULT_SUGGESTION_MIN_CHARS);
		
		this._valueFormat=f_core.GetAttribute(this, f_core._VNS+":valueFormat");
		this._forLabel=f_core.GetAttribute(this, f_core._VNS+":forLabel");
		this._valueFormatLabel=f_core.GetAttribute(this, f_core._VNS+":valueFormatLabel");
		this._noValueFormatLabel=f_core.GetAttribute(this, f_core._VNS+":noValueFormatLabel", "");
		
		this._filtred=true;
		
		var input=this.f_getInput();
		
		this._emptyMessage=f_core.GetAttribute(this, f_core._VNS+":emptyMessage");
		if (this._emptyMessage && f_core.GetAttribute(input, f_core._VNS+":emptyMessage")) {
			this._formattedValue="";
			this._emptyMessageShown=true;
			
		} else {		
			this._formattedValue=input.value;
		}
			
		this._selectedValue=f_core.GetAttribute(this, f_core._VNS+":selectedValue", "");
		this._inputValue=this._selectedValue;
		if (this._selectedValue) {
			this._keyErrored=f_core.GetAttribute(this, f_core._VNS+":invalidKey", false);
		}
		
		this._maxTextLength=f_core.GetNumberAttribute(this, f_core._VNS+":maxTextLength", 0);

		this._gridStyleClass=f_core.GetNumberAttribute(this, f_core._VNS+":gridStyleClass", 0);

		this._forceValidation=f_core.GetBooleanAttribute(this, f_core._VNS+":forceValidation", false);
		
		if(this._forceValidation) {
			this._installCheckListener();
		}
		
		this.f_getInput().onbeforedeactivate=f_keyEntry._OnBeforeDeactivate;
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this.f_onCancelDown);
		this.f_insertEventListenerFirst(f_event.KEYUP, this.f_onSuggest);
		this.f_insertEventListenerFirst(f_event.FOCUS, this.f_onFocus);
		this.f_insertEventListenerFirst(f_event.BLUR, this.f_onBlur);
	},

	f_finalize: function() {
		
		var checkListeners=this._checkListeners;
		if (checkListeners) {
			this._checkListeners=undefined;
			
			f_core.RemoveCheckListener(this, checkListeners);			
		}

		this.f_getInput().onbeforedeactivate=null;
	
		// this._suggestionDelayMs=undefined;  // number
		// this._suggestionMinChars=undefined; // number
		// this._valueFormat=undefined; // String
		// this._valueFormatLabel=undefined; // String
		// this._noValueFormatLabel=undefined; // String
		// this._forLabel=undefined; // String
		// this._formattedValue=undefined; // String
		// this._inputValue=undefined; // String
		// this._focus=undefined; // boolean
		// this._selectedValue=undefined; // String
		// this._verifyingKey=undefined; // boolean
		// this._editable=undefined; // boolean
		// this._readOnly=undefined; // boolean 
		// this._maxTextLength=undefined; // number
		// this._emptyMessageShown=undefined; boolean
		// this._forceValidation=undefined; boolean
		// this._required=undefined; boolean
		
		var request=this._verifyRequest;
		if (request) {
			this._verifyRequest=undefined;
			
			request.f_cancelRequest();
		}		
		
		this.f_super(arguments);
	},
	f_serialize: function() {
		if (!this._keyErrored) {
			this.f_setProperty(f_prop.SELECTED, this._selectedValue);
		}
		if (this._emptyMessageShown) {	
			input=this.f_getInput();
			
			input.value="";
		}
		
		this.f_super(arguments);
		
		this.f_setProperty(f_prop.TEXT, this._inputValue);	
	},
	
	
	/**
	 * @method private
	 * @return void
	 */
	_installCheckListener: function() {
		var keyEntry=this;
		var checkListeners={
				
			f_performCheckValue: function(event) {
				if (keyEntry._inputValue) {
					if(keyEntry._forceValidation && keyEntry._keyErrored) {
						var summary=keyEntry.f_getClientValidatorParameter("INVALIDKEY_ERROR_SUMMARY");
						var detail=keyEntry.f_getClientValidatorParameter("REQUIRED_ERROR_DETAIL");
						
						if (!summary) {
							var resourceBundle=f_resourceBundle.Get(f_keyEntry);
							summary=resourceBundle.f_formatParams("INVALIDKEY_ERROR_SUMMARY");
							//detail=resourceBundle.f_formatParams("REQUIRED_ERROR_DETAIL");
							
							if (!summary) {	
								summary=f_locale.Get().f_formatMessageParams("javax_faces_component_UIInput_INVALID", null, "Invalid value.");
							}
						}
							
						var messageContext=f_messageContext.Get(keyEntry);	
						messageContext.f_addMessage(keyEntry, f_messageObject.SEVERITY_ERROR, summary, detail);
						
						
						return false;
					}
					return true;
				}
				
				if (keyEntry._required) {
					var summary=keyEntry.f_getClientValidatorParameter("REQUIRED_ERROR_SUMMARY");
					var detail=keyEntry.f_getClientValidatorParameter("REQUIRED_ERROR_DETAIL");
					
					if (!summary) {
						var resourceBundle=f_resourceBundle.Get(f_keyEntry);
						summary=resourceBundle.f_formatParams("REQUIRED_ERROR_SUMMARY");
						//detail=resourceBundle.f_formatParams("REQUIRED_ERROR_DETAIL");
						
						if (!summary) {	
							summary=f_locale.Get().f_formatMessageParams("javax_faces_component_UIInput_REQUIRED", null, "A value is required.");
						}
					}
						
					var messageContext=f_messageContext.Get(keyEntry);	
					messageContext.f_addMessage(keyEntry, f_messageObject.SEVERITY_ERROR, summary, detail);
					
					return false;
				}
			},	
		
			f_performCheckPre: function(event) {
			},

		
			f_performCheckPost: function(event) {
			}
		};

		this._checkListeners=checkListeners;
		f_core.AddCheckListener(this, checkListeners);
	
	},
	
	/**
	 * @method protected
	 * @return void
	 */
	f_updateInputStyle: function() {

		// Le composant principal !
		var csuffix="";
		if (this.f_isDisabled()) {
			csuffix+="_disabled";

		} else if (this.f_isReadOnly()) {
			csuffix+="_readOnly";
		}
		if (this._keyErrored) {
			csuffix+="_error";
		}
	
		var componentClassName=this.f_computeStyleClass(csuffix);

		// Le champ INPUT

		var mainClassName=this.f_getMainStyleClass()+"_input";
		var className=mainClassName;
		
		if (this._verifyingKey) {
			className+= " "+mainClassName+"_verifying";	

		} else if (this._keyErrored) {
			className+= " "+mainClassName+"_error";
		}
		
		if (this._emptyMessageShown) {
			className+=" "+mainClassName+"_empty_message";
		}	
		
		var input=this.f_getInput();
		if (!input || this==input) {
			componentClassName+=" "+className;
			
			if (this.className!=componentClassName) {
				this.className=componentClassName;
			}
			return;
		}

		if (this.className!=componentClassName) {
			this.className=componentClassName;
		}

		if (input.className!=className) {
			input.className=className;
		}		
	},
	/**
	 * @method protected
	 * @param f_event evt
	 * @return Boolean
	 */
	f_onCancelDown: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble) {
			f_core.Debug(f_keyEntry, "_onCancelDown: Event has been canceled !");
			return true;
		}

		f_core.Debug(f_keyEntry, "_onCancelDown: Event keyCode="+jsEvt.keyCode);

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_ESPACE:
			return true;

		case f_key.VK_TAB:
			return true;		

		case f_key.VK_SPACE:
			if (jsEvt.ctrlKey) {
				var input=this.f_getInput();
				
				var inputValue=input.value;
				
				if (inputValue) {
					this._verifyKey(inputValue);
				}
				return false;
			}
		}

		this._inputValue=this.f_getInput().value;
		this._inputSelection=undefined;
		
		return true;
	},
	/**
	 * @method protected
	 * @param f_event evt
	 * @return Boolean
	 */
	f_onSuggest: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble || this.f_isDisabled()) {
			f_core.Debug(f_keyEntry, "f_onSuggest: Event has been canceled !");
			return true;
		}
		
		var newInput=this.f_getInput().value;
		if (this._inputValue!=newInput) {
			f_core.Debug(f_keyEntry, "f_onSuggest: Different values  newInput='"+newInput+
				"' inputValue='"+this._inputValue+
				"' formattedValue='"+this._formattedValue+
				"' selectedValue='"+this._selectedValue+"'.");
			
			this._formattedValue=newInput;
			this._inputValue=newInput;
		
			if (this._verifyingKey) {
				this._verifyingKey=undefined;
				this._keyErrored=undefined;
				
				this.f_updateInputStyle();
				
				// Suppression de la recherche XXX
				this._cancelVerification();

			} else if (this._keyErrored) {
				this._keyErrored=undefined;
				
				this.f_updateInputStyle();
			}
				
			if (this._selectedValue && newInput!=this._selectedValue && newInput!=this._formattedValue) {
				f_core.Debug(f_keyEntry, "f_onSuggest: value='"+this._selectedValue+"' not equals input ='"+newInput+"'");
				this._selectedValue=null;
	
				this.f_fireEvent(f_event.SELECTION, jsEvt, null, null);
			}
		}

		return true;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getFormattedValue: function() {
		return this._formattedValue;
	},
	/**
	 * @method public
	 * @param String value
	 * @return void
	 * @see #f_setValue(String)
	 */
	f_setSelectedValue: function(value) {
		this.f_setValue(value);
	},
	/**
	 * Returns the selected value.
	 * 
	 * @method public
	 * @return String 
	 * @see #f_getValue()
	 */
	f_getSelectedValue: function() {
		return this.f_getValue();
	},
	/**
	 * Returns the selected value.
	 * 
	 * @method public
	 * @return String 
	 */
	f_getValue: function() {
		return this._inputValue;
	},
	/**
	 * @method public
	 * @param String value
	 * @return void
	 */
	f_setValue: function(value) {
		f_core.Assert(value===null || typeof(value)=="string", "f_keyEntry.f_setValue: Invalid value parameter ("+value+").");
		f_core.Debug(f_keyEntry, "f_setValue: set input value ='"+value+"'");
		this._selectedValue="";		
		this._inputValue=value;
		this._formattedValue="";
		this._keyErrored=undefined;
		this._verifyingKey=undefined;

		this.f_updateInputStyle();
		
		this.f_getInput().value=value;
		
		if (!this._focus) {
			this._verifyKey(value);
		}
	},
	/**
	 * @method protected
	 */
	fa_valueSelected: function(value, label, rowValues, focusNext) {
		f_core.Debug(f_keyEntry, "fa_valueSelected: value='"+value+"' label='"+label+"'");

		if (this.f_isReadOnly()) {
			f_core.Debug(f_keyEntry, "fa_valueSelected: no modification readOnly");
			return;
		}
		
		if (this.f_fireEvent(f_event.PRE_SELECTION, null, rowValues, value)===false) {
			f_core.Debug(f_keyEntry, "fa_valueSelected: preSelection cancel event");
			return;
		}
		
		if (value===undefined) {
			this._keyErrored=true;
			this._formattedValue=this.f_getInput().value;
			this._selectedValue="";
			this._cancelVerification();

			f_core.Debug(f_keyEntry, "fa_valueSelected: value is undefined  selectedValue='"+this._selectedValue+"'");

			if(this._forLabel){
				var labelComponent = f_core.GetElementById(this._forLabel);
				if (labelComponent) {
					labelComponent.f_setText(this._noValueFormatLabel);
				}
			}
			this.f_fireEvent(f_event.SELECTION, null, null, null);		
			return;
		}
		
		this._keyErrored=undefined;
		this._cancelVerification();
		
		this._formattedValue=(label)?label:"";
		this._selectedValue=value;
		f_core.Debug(f_keyEntry, "fa_valueSelected: value is defined selectedValue='"+this._selectedValue+"'");
		this._inputValue=value;
		if(this._forLabel){
			var labelComponent = f_core.GetElementById(this._forLabel);
			if (labelComponent) {
				labelComponent.f_setText(f_core.FormatMessage(this._valueFormatLabel,rowValues));
			}
		}
		
		var input=this.f_getInput();
		
		if (this._focus && this.f_isEditable() && !this.f_isReadOnly()) {
			input.value=value;

		} else {
			input.value=this._formattedValue;
		}
		
		if (this.f_fireEvent(f_event.SELECTION, null, rowValues, value)===false) {
			f_core.Debug(f_keyEntry, "fa_valueSelected: selection event returns false");
			return;
		}
		
		f_core.Debug(f_keyEntry, "fa_valueSelected: focusNext = '"+focusNext +"'");
		if (focusNext===false) {		
			/* Ca redonne le focus sous IE !!! (donc il doit être egal à undefined pour lors de l'appel ajax de vérification) */	
			try {
				f_core.SelectText(input, value.length);
			}catch (ex){
				f_core.Debug(f_keyEntry, "fa_valueSelected: throws exception ", ex);
			}
			

		} else if (focusNext===true) {
			var comp=f_core.GetNextFocusableComponent(this);
			
			if (comp) {
				f_core.SetFocus(comp, true);
			}
		}
	},
	/**
	 * @method protected
	 * @param f_event event
	 * @return void
	 */
	f_onFocus: function(event) {
		f_core.Debug(f_keyEntry, "f_onFocus: inputValue='"+this._inputValue+"'  (formattedValue='"+this._formattedValue+"')");

		if (this._focus || this.f_isDisabled()) { // Ca peut être disabled et recevoir le focus !
			return;
		}

		this._focus=true;
		
		var input=this.f_getInput();
		
		if (this._emptyMessageShown) {	
			this._emptyMessageShown=undefined;
			
			input.value="";
			this.f_updateInputStyle();
		}
		
		if (this._maxTextLength) {
			input.maxLength=this._maxTextLength;
		}
		
		// On affiche la clef, ou la valeur saisie
		if (this.f_isEditable() && !this.f_isReadOnly()) {
			
			this.setAttribute(f_core._VNS+":notFocusedValue", input.value);
			
			input.value=this._inputValue;
		}
		
		// Il faut tout selectionner car sous IE le focus se repositionne au début		
		input.select();		
	},
	/**
	 * @method protected
	 * @param f_event event
	 * @return void
	 */
	f_onBlur: function(event) {
		f_core.Debug(f_keyEntry, "f_onBlur: formattedValue='"+this._formattedValue+"' (inputValue='"+this._inputValue+"')");
		
		if (!this._focus) {
			return;
		}

		this._focus=undefined;
	
		var input=this.f_getInput();
	
		if (this._maxTextLength) {
			input.maxLength=f_keyEntry._UNLIMITED_TEXT_SIZE;
		}	
	
		// On affiche la zone formatée
		
		var inputValue=input.value;
		
		this._inputValue=inputValue;
		if (inputValue && !this._selectedValue && this.f_isEditable() && !this.f_isReadOnly()) {
			this._verifyKey(inputValue);
		}
		
		if (!inputValue && this._forLabel){
			var labelComponent = f_core.GetElementById(this._forLabel);
			if (labelComponent) {
				labelComponent.f_setText(this._noValueFormatLabel);
			}
		}
		
		if (!inputValue && this._emptyMessage) {
			input.value=""; // Evite les effets non estetiques
			this._emptyMessageShown=true;
			this.f_updateInputStyle();
			input.value=this._emptyMessage;
			
		} else {
			input.value=this._formattedValue;
			this._emptyMessageShown=undefined;
		}
	},
	/**
	 * @method private
	 * @param String value
	 * @return void
	 */
	_verifyKey: function(value) {
		f_core.Debug(f_keyEntry, "_verifyKey: value="+value);
		
		if (this._verifyingKey) {
			if (this._verifyingKey==value) {
				return;
			}
		
			this._cancelVerification(false);
		}
		
		if (!value) {
			this._keyErrored=false; // On ne vérifie pas une clef vide !
			this.f_updateInputStyle();
			return;
		}
		
		var inputValue=this._inputValue;
		this._verifyingKey=inputValue;					
		this.f_updateInputStyle();		
				
		this.f_appendCommand(function(keyEntry) {
			keyEntry.f_callServer(this._verifyingKey);
		});
		
	},
	/**
	 * @method protected
	 */
	f_callServer: function(key) {		
		var params={
			gridId: this.id,
			key: key
		}
	
		var filterExpression=this.fa_getSerializedPropertiesExpression();
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}
		
		var url=f_env.GetViewURI();
		var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var keyEntry=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			if (window._rcfacesExiting) {
	 				return;
	 			}
	
	 			f_core.Info(f_keyEntry, "f_callServer.onError: Bad status: "+status);
	 			
	 			var continueProcess;
	 			
	 			try {
	 				continueProcess=keyEntry.f_performErrorEvent(request, f_error.HTTP_ERROR, text);
	 				
	 			} catch (x) {
	 				f_core.Error(f_keyEntry, "f_callServer.onError: fire event throws exception ", x);
	 			
	 				// On continue coute que coute !
	 				continueProcess=true;
	 			}	 				
 			 			
 			 	f_core.Debug(f_keyEntry, "f_callServer.onError: continueProcess="+continueProcess); 			 	
 			 			
		 		if (continueProcess===false) {
					keyEntry._loading=undefined;
					keyEntry._verifyingKey=undefined;		
					keyEntry.f_updateInputStyle();		
			 		return;
		 		}
	 			
				if (keyEntry.f_processNextCommand()) {
					return;
				}
			 			
 			 	f_core.Debug(f_keyEntry, "f_callServer.onError: no more commands"); 			 	
 	 		
				keyEntry._loading=undefined;		
	 			 			
				keyEntry._verifyingKey=undefined;		
				keyEntry.f_updateInputStyle();		
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
	 			if (window._rcfacesExiting) {
	 				return;
	 			}

	 			if (keyEntry.f_processNextCommand()) {
					return;
				}
			 			
 			 	f_core.Debug(f_keyEntry, "f_callServer.onLoad: no more commands"); 			 	
	 				
				keyEntry._verifyingKey=undefined;		
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						keyEntry.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}

					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						keyEntry.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
	
					var responseContentType=request.f_getResponseContentType().toLowerCase();
					if (responseContentType.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE)>=0) {
						var code=f_error.ComputeApplicationErrorCode(request);
				
				 		keyEntry.f_performErrorEvent(request, code, content);
						return;
					}
		
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
				 		keyEntry.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);
						return;
					}
					
					var ret=request.f_getResponse();
										
					//alert("ret="+ret);
					try {
						f_core.WindowScopeEval(ret);
						
					} catch (x) {
			 			keyEntry.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
					}

				} finally {
					keyEntry._loading=undefined;
					keyEntry.f_updateInputStyle();		
				}
	 		},
	 		onAbort: function() {
	 			if (window._rcfacesExiting) {
	 				return;
	 			}
	 			
				if (keyEntry.f_processNextCommand()) {
					return;
				}
			 	f_core.Debug(f_keyEntry, "f_callServer.onAbort: no more commands"); 			 	

				keyEntry._loading=undefined;		
		 			
				keyEntry._verifyingKey=undefined;		
				keyEntry.f_updateInputStyle();		
	 		}
		});

		this._loading=true;
		request.f_setRequestHeader("X-Camelia", "comboGrid.key");
		request.f_doFormRequest(params);
	},
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * @method private
	 * @return void
	 */
	_cancelVerification: function(updateInputStyle) {
		f_core.Debug(f_keyEntry, "_cancelVerification: updateInputStyle="+updateInputStyle);
		this.f_clearCommands();
		
		var request=this._verifyRequest;
		if (request) {
			this._verifyRequest=undefined;
			
			request.f_cancelRequest();
		}		
		
		if (updateInputStyle!==false) {
			this.f_updateInputStyle();			
		}
	},
	f_performSelectionEvent: function() {
		// On traite pas le RETURN !
	},
	/**
	 * @method public
	 * @return Number
	 */
	f_getSuggestionMinChars: function() {
		return this._suggestionMinChars;
	},
	/**
	 * @method protected
	 * @return Number
	 */
	f_getSuggestionDelayMs: function() {
		return this._suggestionDelayMs;
	},
	/**
	 * @method protected
	 */
	fa_updateReadOnly: function() {
		this.f_getInput().readOnly=this.f_isReadOnly() || !this.f_isEditable();
				
		this.f_updateStyleClass();
	},
	fa_updateEditable: function(set) {
		this.fa_updateReadOnly();
	},
	/**
	 * @method protected
	 * @return void
	 */
	fa_updateRequired: function() {
		this.f_updateStyleClass();
		this._required = true;

		if (this._checkListeners) {
			return;
		}
		
		this._installCheckListener();
	},
	/**
	 * @method hidden
	 */
	f_setInteractiveShow: function(interactiveComponentId) {		
	},
	
	fa_cancelFilterRequest: function() {
	},
	fa_updateFilterProperties: function() {		
	}
}

new f_class("f_keyEntry", {
	extend: f_textEntry,
	aspects: [ fa_commands, fa_readOnly, fa_editable, fa_clientValidatorParameters, fa_filterProperties ],
	statics: __statics,
	members: __members
});
