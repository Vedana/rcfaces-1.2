/*
 * $Id$
 */

/**
 * f_suggestTextEntry class
 *
 * @class public f_suggestTextEntry extends f_textEntry, fa_filterProperties, fa_commands
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field private static final number
	 */
	_DEFAULT_ROWS_NUMBER: 5,

	/**
	 * @field private static final number
	 */
	_DEFAULT_SUGGESTION_DELAY_MS: 300,

	/**
	 * @field private static final number
	 */
	_DEFAULT_SUGGESTION_MIN_CHARS: 0,
	
	/**
	 * @field private static final String
	 */
	_SUGGESTION_MENU_ID: "#suggestion"
}

var __members = {
	f_suggestTextEntry: function() {
		this.f_super(arguments);
		
		this.setAttribute("autocomplete", "off");
		
		this._filtred=true;
		this._rowCount=0;
		
		this._maxResultNumber=f_core.GetNumberAttribute(this, "v:maxResultNumber", f_suggestTextEntry._DEFAULT_ROWS_NUMBER);
		
		this._suggestionDelayMs=f_core.GetNumberAttribute(this, "v:suggestionDelayMs", f_suggestTextEntry._DEFAULT_SUGGESTION_DELAY_MS);
		
		this._suggestionMinChars=f_core.GetNumberAttribute(this, "v:suggestionMinChars", f_suggestTextEntry._DEFAULT_SUGGESTION_MIN_CHARS);
		
		this._caseSensitive=f_core.GetBooleanAttribute(this, "v:caseSensitive", false);
		
		this._forceProposal=f_core.GetBooleanAttribute(this, "v:forceProposal", false);
		
		// Permet d'optimiser les propositions !
		this._orderedResult=f_core.GetAttribute(this, "v:orderedResult");
		this._orderedResult=true;

		this._suggestionValue=f_core.GetAttribute(this, "v:suggestionValue");
		
		this._moreResultsMessage=f_core.GetAttribute(this, "v:moreResultsMessage");
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._onCancelDown);
		this.f_insertEventListenerFirst(f_event.KEYUP, this._onSuggest);
		
		var suggestTextEntry=this;
		var menu=this.f_newSubMenu(f_suggestTextEntry._SUGGESTION_MENU_ID);
		menu.f_setCatchOnlyPopupKeys(true);
		menu.f_insertEventListenerFirst(f_event.SELECTION, function(evt) {
			var jsEvt=evt.f_getJsEvent();
			//var menu=evt.f_getComponent();
			//var evtItem=evt.f_getItem();
			var item=evt.f_getValue();
	
			suggestTextEntry._setSuggestion(item._label, item._value, item, jsEvt);
		});
	},
	f_finalize: function() {
		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			window.clearTimeout(timerId);
		}

		this._results=undefined; //map[]  de string, number

		// this._requestedText=undefined; // string
		// this._suggestionDelayMs=undefined;  // number
		// this._maxResultNumber=undefined; // number
		// this._rowCount=undefined; // number
		// this._caseSensitive=undefined; // boolean
		// this._suggestionMinChars=undefined; // number
		// this._canSuggest=undefined; // boolean
		// this._forceProposal=undefined; // boolean
		// this._suggestionValue=undefined; // string
		// this._orderedResult=undefined; // boolean
		// this._loading=undefined; // boolean
		// this._moreResultsMessage=undefined; // String

		// this._oldClassName=undefined; // string
		// this._canSuggest=undefined; // boolean
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_documentComplete: function() {
		this.f_super(arguments);
		
		this._canSuggest=true;
		
		//this._showProposal();
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getMaxResultNumber: function() {
		return this._maxResultNumber;
	},
	/**
	 * @method public
	 * @param number maxResultNumber
	 * @return void
	 */
	f_setMaxResultNumber: function(maxResultNumber) {
		if (this._maxResultNumber==maxResultNumber) {
			return;
		}
		this._maxResultNumber=maxResultNumber;
		this.f_setProperty(f_prop.ROWS, maxResultNumber);
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getSuggestionDelayMs: function() {
		return this._suggestionDelayMs;
	},
	/**
	 * @method public
	 * @param number suggestionDelayMs
	 * @return void
	 */
	f_setSuggestionDelayMs: function(suggestionDelayMs) {
		if (this._suggestionDelayMs==suggestionDelayMs) {
			return;
		}
		this._suggestionDelayMs=suggestionDelayMs;
		this.f_setProperty(f_prop.DELAY_MS, suggestionDelayMs);
	},
	/**
	 * @method private
	 */
	_onCancelDown: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble) {
			f_core.Debug(f_suggestTextEntry, "_onCancelDown: Event has been canceled !");
			return true;
		}

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			return f_core.CancelJsEvent(jsEvt);
		}
		
		return true;
	},
	/**
	 * @method private
	 */
	_onSuggest: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble) {
			f_core.Debug(f_suggestTextEntry, "_onSuggest: Event has been canceled !");
			return true;
		}
		
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
		var menuOpened=(menu && menu.f_isOpened());

		var cancel=false;
		var value=this.f_getValue();
		var showPopup=false;

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			if (menuOpened) {
				return f_core.CancelJsEvent(jsEvt);
			}

			if (value==this._lastValue) {
				this._showPopup(jsEvt, (jsEvt.keyCode==f_key.VK_DOWN)?1:-1);

				return f_core.CancelJsEvent(jsEvt);
			}
			
			showPopup=true;
			break;

		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_TAB:
			// Le KeyUp vient du popup !
			return true;
		}

		f_core.Debug(f_suggestTextEntry, "_onSuggest: Charcode ("+jsEvt.keyCode+").");
		
		var value=this.f_getValue();
		if (value==this._lastValue) {
			f_core.Debug(f_suggestTextEntry, "_onSuggest: Same value ! (value="+value+" / last="+this._lastValue+")");
			return true;
		}
		
		if (menuOpened) {
			menu.f_closeAllPopups();
		}

		// On retire la value !
		this._setSuggestionValue(null, null, jsEvt);
		
		var keyCode=jsEvt.keyCode;
		if (!showPopup) {
			if (keyCode<32) {
				// On affiche le POPUP que si c'est une touche normale !
		
//				return;
			}
		}
			
		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			window.clearTimeout(timerId);
		}
		
		var suggestionDelayMs=this._suggestionDelayMs;		
		if (suggestionDelayMs<1) {
			return;
		}
		
		f_core.Debug(f_suggestTextEntry, "_onSuggest: Set timeout to "+suggestionDelayMs);
		
		var delay=suggestionDelayMs;
		if (menuOpened) {
			delay/=3.0;
			if (delay<1) {
				delay=1;
			}
		} else if (!showPopup && keyCode<32) {
			delay*=2.0;
		}
		
		if (showPopup) {
			this._lastValue=value;
			this._onSuggestTimeOut();
			
		} else {
			this._lastValue=value;

			var suggestTextEntry=this;
			this._timerId=window.setTimeout(function() {
				if (window._rcfacesExiting) {
					return;
				}

				try {
					suggestTextEntry._onSuggestTimeOut();
					
				} catch (x) {
					f_core.Error(f_suggestTextEntry, "_onSuggest.timer: Timeout processing error !", x);
				}
			}, delay);
		}		
		
		if (cancel) {
			return f_core.CancelJsEvent(jsEvt);
		}
		
		return true;
	},
	/**
	 * @method private
	 */
	_onSuggestTimeOut: function(text) {
		if (!text) {
			text=this.f_getText();
		}
		
		var minChars=this._suggestionMinChars;
		f_core.Debug(f_suggestTextEntry, "_onSuggestTimeOut: text='"+text+"'. (minChars="+minChars+")");

		if (minChars>0 && text.length<minChars) {
			return;
		}
		
		// Peut-on se limiter à ce que l'on a en mémoire ?
		
		var results=this._results;

		if (this._requestedText==text) {
			this._showProposal();
			return;
		}

		if (results && results.length && !text.indexOf(this._requestedText)) {
			if (this._filterProposals(new Array(), text)) {
				this._showProposal();
				return;
			}
		}

		
		var p=this.f_getFilterProperties();
		
		p.text=text;
		p.caseSensitive=this._caseSensitive;
		
		this.f_setFilterProperties(p);
	},
	fa_updateFilterProperties: function() {
		if (this._calling) {
			return;
		}
		
		var params=new Object;
		params.componentId=this.id;
		
		var text=null;
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
			
			text=this.f_getFilterProperties().text;
		}
		var maxResultNumber=this._maxResultNumber;
		if (maxResultNumber>0) {
			params.maxResultNumber=maxResultNumber;
		}
		
		this.f_appendCommand(function(suggestTextEntry) {
			suggestTextEntry._callServer(params, text);
		});
	},
	/**
	 * @method private
	 */
	_callServer: function(params, text) {
		this._calling=true;
		
		try {
			if (this.f_fireEvent(f_event.MENU)==false) {
				return;
			}	
		
			this.f_setLoading(true);
			
			f_core.Debug(f_suggestTextEntry, "Call server text='"+text+"' maxResultNumber="+params.maxResultNumber);
		
			var url=f_env.GetViewURI();	
			var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
			var suggestTextEntry=this;
			request.f_setListener({
				/**
				 * @method public
				 */
		 		onError: function(request, status, text) {
		 			f_core.Info(f_suggestTextEntry, "Bad status: "+status);

		 			var continueProcess;
		 			
		 			try {
		 				continueProcess=suggestTextEntry.f_performErrorEvent(request, f_error.HTTP_ERROR, text);
		 				
		 			} catch (x) {
		 				// On continue coute que coute !
		 				continueProcess=false;
		 			}	 				
		 				 				 			 			
			 		if (continueProcess===false) {
						suggestTextEntry.f_setLoading(false);
						return;
					}
		 			
		 			
					if (suggestTextEntry.f_processNextCommand()) {
						return;
					}

					suggestTextEntry.f_setLoading(false);
		 		},
				/**
				 * @method public
				 */
		 		onProgress: function(request, content, length, contentType) {
				 	// f_core.SetTextNode(waitingNode._label, f_waiting.GetReceivingMessage());
		 		},
				/**
				 * @method public
				 */
		 		onLoad: function(request, content, contentType) {
					if (suggestTextEntry.f_processNextCommand()) {
						return;
					}
					try {
						if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
							suggestTextEntry.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
							return;
						}
	
						var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
						if (!cameliaServiceVersion) {
							suggestTextEntry.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
							return;					
						}
		
						var responseContentType=request.f_getResponseContentType();
						
						if (responseContentType.indexOf(f_error.ERROR_MIME_TYPE)>=0) {
					 		suggestTextEntry.f_performErrorEvent(request, f_error.APPLICATION_ERROR, content);
							return;
						}
	
						if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)) {
				 			suggestTextEntry.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);

							return;
						}
	
						//alert("Req='"+text+"'");
						var ret=request.f_getResponse();
	
						suggestTextEntry._results=undefined;
						suggestTextEntry._requestedText=text;
						
						try {
							eval(ret);
							
						} catch (x) {
				 			suggestTextEntry.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
						}
	
					} finally {
						suggestTextEntry.f_setLoading(false);
					}
		 		}
			});
	
			this.f_setLoading(true);
	
			request.f_setRequestHeader("X-Camelia", "items.request");
			request.f_doFormRequest(params);
			
		} finally {
			this._calling=undefined;
		}
	},
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {

		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * @method hidden
	 */
	f_setLoading: function(state) {
		if (state==this._loading) {
			return;
		}
		
		this._loading=state;
		
		var cls=this.f_computeStyleClass((state)?"_waiting":null);
		
		if (this.className!=cls) {	
			this.className=cls;
		}
	},
	/**
	 * @method public
	 * @param String label
	 * @param any value
	 * @param optional String description
	 * @param optional String imageURL
	 * @param optional String clientDataName1
	 * @param optional String clientDataValue1
	 * @param optional String... clientDataName2
	 * @return Object New item.
	 */
	f_appendItem: function(label, value, description, imageURL, clientDataName1, clientDataValue1, clientDataName2) {
		var results=this._results;
		if (!results) {
			results=new Array;
			this._results=results;
		}
		
		var clientDatas;
		if (arguments.length>4) {
			clientDatas=new Object;
			
			for(var i=4;i<arguments.length-1;) {
				var key=arguments[i++];
				var value=arguments[i++]

				clientDatas[key]=value;
			}
		}
		
		var item={
			_label: label,
			_value: value,
			_description: description,
			_imageURL: imageURL,
			_clientDatas: clientDatas
		};
		
		results.push(item);
		
		return item;
	},
	/**
	 * @method hidden
	 */
	f_setRowCount: function(rows) {
		f_core.Debug(f_suggestTextEntry, "f_setRowCount rows="+rows+" canSuggest="+this._canSuggest);
		
		this._rowCount=rows;
		
		if (!this._canSuggest) {
			return;
		}

		this._showProposal();
	},
	/**
	 * @method private
	 */
	_showProposal: function(jsEvt) {	
		var results=this._results;
		if (!results) {
			return;
		}
			
		var rs=new Array;
		this._filterProposals(rs);
	
		if (!rs.length) {
			return;
		}
		
		f_core.Debug(f_suggestTextEntry, "_showProposal: result="+rs+" forceProposal="+this._forceProposal);
		
		if (!this._forceProposal && rs.length>1) {	
			this._showPopup(jsEvt);
			return;
		}
		
		this.f_showProposal(rs[0]._label, rs[0]._value, rs[0], jsEvt);
		
		if (rs.length>1) {	
			this._showPopup(jsEvt);
		}
	},
	/**
	 * @method private
	 * @param Event jsEvt
	 * @param optional number autoSelect
	 * @return void
	 */
	_showPopup: function(jsEvt, autoSelect) {
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
		if (!menu) {
			f_core.Debug(f_suggestTextEntry, "_showPopup: no menu !");
			return;
		}
		
		if (menu.f_isOpened()) {
			f_core.Debug(f_suggestTextEntry, "_showPopup: already opened, close all");

			menu.f_closeAllPopups();
		}
		
		menu.f_removeAllItems(menu);
		
		var results=new Array;
		var complete=this._filterProposals(results);
		if (!results.length) {
			return;
		}
		
		var i;
		for(i=0;i<results.length;i++) {
			var result=results[i];

			var label=result._label;
			var description=result._description;
			if (description) {
				label+=description;
			}
	
			var item=menu.f_appendItem(menu, "_result"+i, label, result);
			
			var imageURL=result._imageURL;
			if (imageURL) {
				menu.f_setItemImages(item, imageURL);
			}
		}
		
		var message=this._moreResultsMessage;
		if (!complete && message!="") {
			if (!message) {
				message=f_resourceBundle.Get(f_suggestTextEntry).f_get("MORE_RELEVANT_RESULTS");
			}
		
			var item=menu.f_appendItem(menu, "_result"+i, message);			
			menu.f_setItemDisabled(item, true);
		}
	
		var params={
			component: this.f_getInput(),
			position: f_popup.BOTTOM_COMPONENT
		};
	
		if (!f_core.IsInternetExplorer()) {
			// Probleme de box modele !
//			params.deltaX=-1;
			params.deltaY=-1;
			params.deltaWidth=-4;
		}
	
		f_core.Debug(f_suggestTextEntry, "_showPopup: open menu :"+menu);
	
		menu.f_open(jsEvt, params, autoSelect);
//		this.focus();
	},
	/** 
	 * @method public
	 * @param String proposalLabel
	 * @param String proposalValue
	 * @param hidden Object proposalItem
	 * @param hidden Event jsEvt
	 * @return void
	 */
	f_showProposal: function(proposalLabel, proposalValue, proposalItem, jsEvt) {
		var label=this.f_getText();
		
		var labelCS=(this._caseSensitive)?label:label.toLowerCase();
		var proposalLabelCS=(this._caseSensitive)?proposalLabel:proposalLabel.toLowerCase();
		
		if (proposalLabelCS.indexOf(labelCS)!=0) {
			return;
		}

		if (this._forceProposal) {
			var results=this._results;
			if (results && results.length && results.length==this._rowCount ) {
				// Recherche les caracteres supplementaires !
				for(var i=label.length+1;i>=0 && i<=proposalLabelCS.length;i++) {
					var l=proposalLabelCS.substring(0, i);
					
					for(var j=0;j<results.length;j++) {
						var result=results[j]._label;
						var resultCS=(this._caseSensitive)?result:result.toLowerCase();
						
						if (resultCS.indexOf(labelCS)<0) {
							continue;
						}
						
						if (!resultCS.indexOf(l)) {
							continue;
						}
					
						i=-1;
						break;
					}
					
					if (i<1) {
						break;
					}

					label=proposalLabel.substring(0, i);
				}
			}
		}
		
		this._setSuggestion(proposalLabel, proposalValue, proposalItem, jsEvt);
		
		this.f_setSelection({
			start: label.length,
			end: proposalLabel.length-label.length
		});
	},
	/**
	 * @method private
	 */
	_setSuggestion: function(label, value, item, jsEvt) {
		f_core.Debug(f_suggestTextEntry, "_setSuggestion: label='"+label+"' value='"+value+"' item='"+item+"'.");

		this.f_setText(label, true);
		this._setSuggestionValue(value, item, jsEvt);
		this._lastValue=this.f_getValue();
	},
	/**
	 * @method public
	 * @param String text
	 * @param hidden boolean keepSuggestion
	 * @return void
	 */
	f_setText: function(text, keepSuggestion) {
		this.f_super(arguments, text);	
		
		if (!keepSuggestion) {
			this._setSuggestionValue(null);
		}
	},
	/**
	 * @method private
	 * @param Array ret
	 * @return boolean
	 */
	_filterProposals: function(ret, text) {
	
		var results=this._results;
		if (!results || !results.length) {
			return true;
		}

		if (text===undefined) {
			var d=this.f_getSelection();
			if (!d) {
				f_core.Debug(f_suggestTextEntry, "_filterProposals: No selection to change proposal !");
				return true;
			}
	
			text=this.f_getValue().substring(0, d.start);
		}
				
		if (!this._caseSensitive) {
			text=text.toLowerCase();
		}
		
		var state=0;
		
		for(var i=0;i<results.length;i++) {
			var r=results[i]._label;
			if (!this._caseSensitive) {
				r=r.toLowerCase();
			}
			
			if (r.indexOf(text)) { // Ca ne commence pas !
				if (state==1) {
					// On avait trouvé, puis c'est la fin
					state=2;
				}
				
				continue;
			}

			if (state==0) { 
				state=1;
			}
			
			ret.push(results[i]);
		}

		var complete=false;
	
//	alert("State="+state+" resultsLength="+results.length+" rowCount="+this._rowCount+" ordered="+this._orderedResult);
	
		switch(state) {
		case 0:
			if (this._orderedResult) {
				var requestedText=this._requestedText;
				if (requestedText && requestedText.length) {
					// on a une petite chance car on a recherché A 
					// et on tombe sur AE
					// on peut en déduire qu'il n'y a pas de AA,AB,AC,AD et c'est donc complet
					
					if (!this._caseSensitive) {
						requestedText=requestedText.toLowerCase();
					}
					
					complete=(text.indexOf(requestedText)==0);
				}
			}
			break;
		
		case 1:
			complete=(results.length==this._rowCount);
			break;

		case 2:
			complete=(this._orderedResult==true);
			break;
		} 
		
		f_core.Debug(f_suggestTextEntry, "_filterProposals: Results="+results.length+" rowCount="+this._rowCount+" Post filter="+ret.length+" complete="+complete+" state="+state);
			
		return complete;
	},
	/** 
	 * @method public
	 * @return String
	 */
	f_getSuggestionValue: function() {
		return this._suggestionValue;
	},
	/** 
	 * @method private
	 * @param String value
	 * @param Object item
	 * @param Event jsEvt
	 * @return void
	 */
	_setSuggestionValue: function(value, item, jsEvt) {
		f_core.Assert(value===null || typeof(value)=="string", "Suggestion value must be a string or null !");
		
		if (this._suggestionValue==value) {
			return;
		}
		
		this._suggestionValue=value;

		this.f_fireEvent(f_event.SUGGESTION, jsEvt, item, value);
	},
	/**
	 * @method public
	 * @param Object item
	 * @param String key
	 * @return String Named data associated to the item.
	 */
	f_getItemClientData: function(item, key) {
		var clientDatas=item._clientDatas;
		if (!clientDatas) {
			return null;
		}
		
		return clientDatas[key];
	},
	/**
	 * @method public
	 * @param Object item
	 * @return String Text associated to the item.
	 */
	f_getItemLabel: function(item) {
		return item._label;
	},
	/**
	 * @method public
	 * @param Object item
	 * @return any Value associated to the item.
	 */
	f_getItemValue: function(item) {
		return item._value;
	},
	/**
	 * @method protected
	 */
	f_getEventLocked: function(evt, showAlert, mask) {
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
					
		var menuOpened=(menu && menu.f_isOpened());
		if (menuOpened) {
			if (!mask) {
				mask=0;
			}	
			
			mask|=f_event.POPUP_LOCK;
		}

		f_core.Debug(f_suggestTextEntry, "f_getEventLocked: menu="+menu+" menuOpened="+menuOpened+" mask="+mask+" showAlert="+showAlert);

		return this.f_super(arguments, evt, showAlert, mask);
	},
	/**
	 * @method hidden
	 */
	fa_cancelFilterRequest: function() {
	}
}

new f_class("f_suggestTextEntry", null, __statics, __members, f_textEntry, fa_filterProperties, fa_commands);
