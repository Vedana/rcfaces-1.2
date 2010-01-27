/* 
 * $Id$
 */


/**
 * 
 * @class f_comboGrid extends f_textEntry, fa_dataGridPopup, fa_commands, fa_readOnly, fa_editable, fa_clientValidatorParameters
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __statics = {

	/**
	 * @field private static final number
	 */
	_UNLIMITED_TEXT_SIZE: 999999,
	
	/**
	 * @field private static final String
	 */
	_BUTTON_ID_SUFFIX: "::button",

	/**
	 * @field private static final String
	 */
	_INPUT_ID_SUFFIX: "::input",

	/**
	 * @field private static final number
	 */
	_DEFAULT_SUGGESTION_DELAY_MS: 300,

	/**
	 * @field private static final number
	 */
	_DEFAULT_SUGGESTION_MIN_CHARS: 0,

	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:comboGrid
	 */
	_OnButtonMouseDown: function(evt) {
		var comboGrid=this._comboGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		if (comboGrid.f_getEventLocked(evt)) {
			return false;
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseDown(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:comboGrid
	 */
	_OnButtonMouseUp: function(evt) {
		var comboGrid=this._comboGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		comboGrid._onButtonMouseUp(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:comboGrid
	 */
	_OnButtonMouseOver: function(evt) {
		var comboGrid=this._comboGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (comboGrid.f_getEventLocked(evt, false)) {
			return false;
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseOver(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:comboGrid
	 */
	_OnButtonMouseOut: function(evt) {
		var comboGrid=this._comboGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		comboGrid._onButtonMouseOut(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	/**
	 * @method private static
	 * @return boolean
	 * @context event:evt
	 */
	_OnBeforeDeactivate: function(evt) {
		// Que sur IE ... donc ...
		evt=f_core.GetJsEvent(this);
		
		var toElement=evt.toElement;
		if (!toElement) {
			return true;
		}

		var comboGrid=evt.srcElement.f_link;
		
		for(;toElement.parentNode;toElement=toElement.parentNode) {
			if (toElement!=comboGrid) {
				continue;
			}
			
			return f_core.CancelJsEvent(evt);
		}
		
		return true;
	}
}

var __members = {
	
	/**
	 * @field private boolean
	 */
	_buttonOver: undefined,
	
	/**
	 * @field private boolean
	 */
	_buttonDown: undefined,

	f_comboGrid: function() {
		this.f_super(arguments);
			
		this._suggestionDelayMs=f_core.GetNumberAttribute(this, "v:suggestionDelayMs", f_comboGrid._DEFAULT_SUGGESTION_DELAY_MS);
		
		this._suggestionMinChars=f_core.GetNumberAttribute(this, "v:suggestionMinChars", f_comboGrid._DEFAULT_SUGGESTION_MIN_CHARS);
		
		this._valueFormat=f_core.GetAttribute(this, "v:valueFormat");
		
		this._filtred=true;
		
		var button=this.ownerDocument.getElementById(this.id+f_comboGrid._BUTTON_ID_SUFFIX);
		this._button=button;
		
		button._comboGrid=this;
		button.onmousedown=f_comboGrid._OnButtonMouseDown;
		button.onmouseup=f_comboGrid._OnButtonMouseUp;
		button.onmouseover=f_comboGrid._OnButtonMouseOver;
		button.onmouseout=f_comboGrid._OnButtonMouseOut;
		
		var input=this.f_getInput();
		
		this._emptyMessage=f_core.GetAttribute(this, "v:emptyMessage");
		if (this._emptyMessage && f_core.GetAttribute(input, "v:emptyMessage")) {
			this._formattedValue="";
			this._emptyMessageShown=true;
			
		} else {		
			this._formattedValue=input.value;
		}
			
		this._selectedValue=f_core.GetAttribute(this, "v:selectedValue", "");
		this._inputValue=this._selectedValue;
		if (this._selectedValue) {
			this._keyErrored=f_core.GetAttribute(this, "v:invalidKey", false);
		}
		
		this._maxTextLength=f_core.GetNumberAttribute(this, "v:maxTextLength", 0);

		this._gridStyleClass=f_core.GetNumberAttribute(this, "v:gridStyleClass", 0);

		
		this.f_getInput().onbeforedeactivate=f_comboGrid._OnBeforeDeactivate;
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._onCancelDown);
		this.f_insertEventListenerFirst(f_event.KEYUP, this._onSuggest);
		this.f_insertEventListenerFirst(f_event.FOCUS, this._onFocus);
		this.f_insertEventListenerFirst(f_event.BLUR, this._onBlur);
	},

	f_finalize: function() {
		
		var checkListeners=this._checkListeners;
		if (checkListeners) {
			this._checkListeners=undefined;
			
			f_core.RemoveCheckListener(this, checkListeners);			
		}

		this.f_getInput().onbeforedeactivate=null;
	
		// this._buttonOver=undefined; // boolean
		// this._buttonDown=undefined; // boolean
		// this._suggestionDelayMs=undefined;  // number
		// this._suggestionMinChars=undefined; // number
		// this._valueFormat=undefined; // String
		// this._formattedValue=undefined; // String
		// this._inputValue=undefined; // String
		// this._focus=undefined; // boolean
		// this._selectedValue=undefined; // String
		// this._verifyingKey=undefined; // boolean
		// this._editable=undefined; // boolean
		// this._readOnly=undefined; // boolean 
		// this._maxTextLength=undefined; // number
		// this._emptyMessageShown=undefined; boolean
	
		var button=this._button;
		if (button) {
			this._button=undefined; // HTMLImageElement
			
			button._comboGrid=undefined; // f_comboGrid
			
			button.onmousedown=null;
			button.onmouseup=null;
			button.onmouseover=null;
			button.onmouseout=null;

			f_core.VerifyProperties(button);
		}
		
		var request=this._verifyRequest;
		if (request) {
			this._verifyRequest=undefined;
			
			request.f_cancelRequest();
		}		
		
		this.f_super(arguments);
	},
	f_serialize: function() {
		this.f_setProperty(f_prop.SELECTED, this._selectedValue);
		
		if (this._emptyMessageShown) {	
			input=this.f_getInput();
			
			input.value="";
		}
		
		this.f_super(arguments);	
	},
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return void
	 */
	_onButtonMouseDown: function(jsEvent) {
		this._buttonDown=true;
		this.f_updateButtonStyle();		
	
		var menuOpened=this.f_isDataGridPopupOpened();
		
		f_core.Debug(f_comboGrid, "_onButtonMouseDown: menuOpened="+menuOpened);
		
		if (menuOpened) {
			this.f_closePopup(jsEvent);
			return
		}
		
		this.f_setFocus();
		this.f_openPopup(jsEvent);
	},
	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_onButtonMouseUp: function(evt) {
		if (!this._buttonDown) {
			return;
		}
		this._buttonDown=false;
		this.f_updateButtonStyle();	
	},
	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_onButtonMouseOver: function(evt) {
		this._buttonOver=true;
		this.f_updateButtonStyle();
	},
	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_onButtonMouseOut: function(evt) {
		if (!this._buttonOver) {
			return;
		}
		this._buttonDown=false; // Obligé sinon sous IE on sait plus quand c'est relaché
		this._buttonOver=false;
		this.f_updateButtonStyle();	
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateButtonStyle: function() {
		var button=this._button;
		if (!button) {
			return;
		}
		
		var className="f_comboGrid_button";
		
		if (this.f_isDisabled()) {
		//	className+=" "+className+"_disabled";
		// porté par la classe principale 

		} else if (this._buttonDown) {
			className+=" "+className+"_selected";

		} else if (this._buttonOver) {
			className+=" "+className+"_hover";
		}
		
		if (button.className!=className) {
			button.className=className;
		}
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
		if (this.className!=componentClassName) {
			this.className=componentClassName;
		}

		// Le champ INPUT

		var mainClassName="f_comboGrid_input";
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
		if (!input) {
			return;
		}
		
		if (input.className!=className) {
			input.className=className;
		}		
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @param optional boolean autoSelect
	 * @return void
	 */
	f_openPopup: function(jsEvent, autoSelect) {
		f_core.Debug(f_comboGrid, "f_openPopup: open popup");
		
		this.f_openDataGridPopup(jsEvent, null, autoSelect);
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @return void
	 */
	f_closePopup: function(jsEvent) {
		f_core.Debug(f_comboGrid, "f_closePopup: close popup");
			
		this.f_closeDataGridPopup(jsEvent);
	},
	/**
	 * @method protected
	 */
	f_getEventLocked: function(evt, showAlert, mask) {
		if (this.f_isDataGridPopupOpened()) {
			if (!mask) {
				mask=0;
			}	
			
			mask|=f_event.POPUP_LOCK;
		}

		f_core.Debug(f_comboGrid, "f_getEventLocked: menuOpened="+this.f_isDataGridPopupOpened()+" mask="+mask+" showAlert="+showAlert);

		return this.f_super(arguments, evt, showAlert, mask);
	},
	/**
	 * @method private
	 * @param f_event evt
	 * @return boolean
	 */
	f_initializeInput: function() {
		return this.ownerDocument.getElementById(this.id+f_comboGrid._INPUT_ID_SUFFIX);
	},
	/**
	 * @method private
	 * @param f_event evt
	 * @return boolean
	 */
	_onCancelDown: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble) {
			f_core.Debug(f_comboGrid, "_onCancelDown: Event has been canceled !");
			return true;
		}

		f_core.Debug(f_comboGrid, "_onCancelDown: Event keyCode="+jsEvt.keyCode);

		var menuOpened=this.f_isDataGridPopupOpened();

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_ESPACE:
			if (menuOpened) {
				return f_core.CancelJsEvent(jsEvt);
			}
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
	 * @method private
	 * @param f_event evt
	 * @return boolean
	 */
	_onSuggest: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble || this.f_isDisabled()) {
			f_core.Debug(f_comboGrid, "_onSuggest: Event has been canceled !");
			return true;
		}
		
		var menuOpened=this.f_isDataGridPopupOpened();
		if (menuOpened) {
			// Aie aie aie
			this.f_closePopup(jsEvt);
			return true;
		}
		
		var newInput=this.f_getInput().value;
		if (this._inputValue!=newInput) {
			f_core.Debug(f_comboGrid, "_onSuggest: Different values  newInput='"+newInput+
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
				
			if (newInput!=this._selectedValue && this._selectedValue) {
				this._selectedValue=null;
	
				this.f_fireEvent(f_event.SELECTION, jsEvt, null, null);
			}
		}
		
		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			this.f_openPopup(jsEvt);

			return f_core.CancelJsEvent(jsEvt);
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
		f_core.Assert(value===null || typeof(value)=="string", "f_comboGrid.f_setValue: Invalid value parameter ("+value+").");
		
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
		f_core.Debug(f_comboGrid, "fa_valueSelected: value='"+value+"' label='"+label+"'");

		if (this.f_isReadOnly()) {
			return;
		}
		
		if (this.f_fireEvent(f_event.SELECTION, null, rowValues, value)===false) {
			return;
		}
		
		if (value===undefined) {
			this._keyErrored=true;
			this._formattedValue=this.f_getInput().value;
			this._selectedValue="";
			this._cancelVerification();
			return;
		}
		
		this._keyErrored=undefined;
		this._cancelVerification();
		
		this._formattedValue=(label)?label:"";
		this._selectedValue=value;
		this._inputValue=value;
		
		var input=this.f_getInput();
		
		if (this._focus && this.f_isEditable() && !this.f_isReadOnly()) {
			input.value=value;

		} else {
			input.value=this._formattedValue;
		}
		
		if (focusNext===false) {		
			/* Ca redonne le focus sous IE !!! (donc il doit être egal à undefined pour lors de l'appel ajax de vérification) */	
			f_core.SelectText(input, value.length);

		} else if (focusNext===true) {
			var comp=f_core.GetNextFocusableComponent(this);
			
			if (comp) {
				f_core.SetFocus(comp, true);
			}
		}
	},
	/**
	 * @method private
	 * @param f_event event
	 * @return void
	 */
	_onFocus: function(event) {
		f_core.Debug(f_comboGrid, "_onFocus: inputValue='"+this._inputValue+"'  (formattedValue='"+this._formattedValue+"')");

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
			
			this.setAttribute("v:notFocusedValue", input.value);
			
			input.value=this._inputValue;
		}
		
		// Il faut tout selectionner car sous IE le focus se repositionne au début		
		input.select();		
	},
	/**
	 * @method private
	 * @param f_event event
	 * @return void
	 */
	_onBlur: function(event) {
		f_core.Debug(f_comboGrid, "_onBlur: formattedValue='"+this._formattedValue+"' (inputValue='"+this._inputValue+"')");
		
		if (!this._focus) {
			return;
		}

		var menuOpened=this.f_isDataGridPopupOpened();
		if (menuOpened) {
			return;
		}

		this._focus=undefined;
	
		var input=this.f_getInput();
	
		if (this._maxTextLength) {
			input.maxLength=f_comboGrid._UNLIMITED_TEXT_SIZE;
		}	
	
		// On affiche la zone formatée
		
		var inputValue=input.value;
		
		this._inputValue=inputValue;
		if (inputValue && !this._selectedValue && this.f_isEditable() && !this.f_isReadOnly()) {
			this._verifyKey(inputValue);
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
		f_core.Debug(f_comboGrid, "_verifyKey: value="+value);
		
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
				
		this.f_appendCommand(function(comboGrid) {
			comboGrid.f_callServer(this._verifyingKey);
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
		var comboGrid=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_dataGrid, "f_callServer.onError: Bad status: "+status);
	 			
	 			var continueProcess;
	 			
	 			try {
	 				continueProcess=comboGrid.f_performErrorEvent(request, f_error.HTTP_ERROR, text);
	 				
	 			} catch (x) {
	 				f_core.Error(f_dataGrid, "f_callServer.onError: fire event throws exception ", x);
	 			
	 				// On continue coute que coute !
	 				continueProcess=true;
	 			}	 				
 			 			
 			 	f_core.Debug(f_dataGrid, "f_callServer.onError: continueProcess="+continueProcess); 			 	
 			 			
		 		if (continueProcess===false) {
					comboGrid._loading=undefined;
					comboGrid._verifyingKey=undefined;		
					comboGrid.f_updateInputStyle();		
			 		return;
		 		}
	 			
				if (comboGrid.f_processNextCommand()) {
					return;
				}
			 			
 			 	f_core.Debug(f_dataGrid, "f_callServer.onError: no more commands"); 			 	
 	 		
				comboGrid._loading=undefined;		
	 			 			
				comboGrid._verifyingKey=undefined;		
				comboGrid.f_updateInputStyle();		
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (comboGrid.f_processNextCommand()) {
					return;
				}
			 			
 			 	f_core.Debug(f_dataGrid, "f_callServer.onLoad: no more commands"); 			 	
	 				
				comboGrid._verifyingKey=undefined;		
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						comboGrid.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}

					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						comboGrid.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
	
					var responseContentType=request.f_getResponseContentType().toLowerCase();
					if (responseContentType.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE)>=0) {
						var code=f_error.ComputeApplicationErrorCode(request);
				
				 		comboGrid.f_performErrorEvent(request, code, content);
						return;
					}
		
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
				 		comboGrid.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);
						return;
					}
					
					var ret=request.f_getResponse();
										
					//alert("ret="+ret);
					try {
						f_core.WindowScopeEval(ret);
						
					} catch (x) {
			 			comboGrid.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
					}

				} finally {
					comboGrid._loading=undefined;
					comboGrid.f_updateInputStyle();		
				}
	 		},
	 		onAbort: function() {
				if (comboGrid.f_processNextCommand()) {
					return;
				}
			 	f_core.Debug(f_dataGrid, "f_callServer.onAbort: no more commands"); 			 	

				comboGrid._loading=undefined;		
		 			
				comboGrid._verifyingKey=undefined;		
				comboGrid.f_updateInputStyle();		
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
		f_core.Debug(f_comboGrid, "_cancelVerification: updateInputStyle="+updateInputStyle);
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
	 * @return number
	 */
	f_getSuggestionMinChars: function() {
		return this._suggestionMinChars;
	},
	/**
	 * @method protected
	 * @return number
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

		if (this._checkListeners) {
			return;
		}

		var comboGrid=this;
		var checkListeners={
			f_performCheckValue: function(event) {
				if (comboGrid._inputValue) {
					return;
				}
				
				var summary=comboGrid.f_getClientValidatorParameter("REQUIRED_ERROR_SUMMARY");
				var detail=comboGrid.f_getClientValidatorParameter("REQUIRED_ERROR_DETAIL");
				
				if (!summary) {
					var resourceBundle=f_resourceBundle.Get(f_comboGrid);
					summary=resourceBundle.f_formatParams("REQUIRED_ERROR_SUMMARY");
					//detail=resourceBundle.f_formatParams("REQUIRED_ERROR_DETAIL");
					
					if (!summary) {	
						summary=f_locale.Get().f_formatMessageParams("javax_faces_component_UIInput_REQUIRED", null, "A value is required.");
					}
				}
					
				var messageContext=f_messageContext.Get(comboGrid);	
				messageContext.f_addMessage(comboGrid, f_messageObject.SEVERITY_ERROR, summary, detail);
				
				return false;
			}		
		};

		this._checkListeners=checkListeners;
		f_core.AddCheckListener(this, checkListeners);			
	},
	/**
	 * @method hidden
	 */
	f_setInteractiveShow: function(interactiveComponentId) {		
	}
}

new f_class("f_comboGrid", {
	extend: f_textEntry,
	aspects: [ fa_dataGridPopup, fa_commands, fa_readOnly, fa_editable, fa_clientValidatorParameters ],
	statics: __statics,
	members: __members
});
