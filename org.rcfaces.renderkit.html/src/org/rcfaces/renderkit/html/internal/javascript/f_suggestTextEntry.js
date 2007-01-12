/*
 * $Id$
 */

/**
 * f_suggestTextEntry class
 *
 * @class public f_suggestTextEntry extends f_textEntry, fa_filterProperties, fa_commands, fa_subMenu
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	/**
	 * @field private static final number
	 */
	_DEFAULT_ROWS_NUMBER: 5,

	/**
	 * @field private static final number
	 */
	_DEFAULT_SUGGESTION_DELAY_MS: 300,
	
	/**
	 * @field private static final String
	 */
	_SUGGESTION_MENU_ID: "#suggestion"
}

var __prototype = {
	f_suggestTextEntry: function() {
		this.f_super(arguments);
		
		this.setAttribute("autocomplete", "off");
		
		this._filtred=true;
		this._rowCount=0;
		
		var rows=f_suggestTextEntry._DEFAULT_ROWS_NUMBER;
		var rowsAtt=f_core.GetAttribute(this, "v:maxResultNumber");
		if (rowsAtt) {
			rows=parseInt(rowsAtt);
		}
		this._maxResultNumber=rows;
		
		var suggestionDelayMs=f_suggestTextEntry._DEFAULT_SUGGESTION_DELAY_MS;
		var suggestionDelayMsAtt=f_core.GetAttribute(this, "v:suggestionDelayMs");
		if (suggestionDelayMsAtt) {
			suggestionDelayMs=parseInt(suggestionDelayMsAtt);
		}
		this._suggestionDelayMs=suggestionDelayMs;
		
		var caseSensitiveAtt=f_core.GetAttribute(this, "v:caseSensitive");
		if (caseSensitiveAtt) {
			this._caseSensitive=true;
		}
		
		var forceProposalAtt=f_core.GetAttribute(this, "v:forceProposal");
		if (forceProposalAtt) {
			this._forceProposal=true;
		}
		
		this._suggestionMinChars=0;
		var suggestionMinCharsAtt=f_core.GetAttribute(this, "v:suggestionMinChars");
		if (suggestionMinCharsAtt) {
			this._suggestionMinChars=parseInt(suggestionMinCharsAtt);
		}
		
		// Permet d'optimiser les propositions !
		this._orderedResult=f_core.GetAttribute(this, "v:orderedResult");
		this._orderedResult=true;

		this._suggestionValue=f_core.GetAttribute(this, "v:suggestionValue");
		
		this.f_addEventListener(f_event.KEYDOWN, this._onCancelDown);
		this.f_addEventListener(f_event.KEYUP, this._onSuggest);
		
		var suggestTextEntry=this;
		var menu=this.f_newSubMenu(f_suggestTextEntry._SUGGESTION_MENU_ID);
		menu.f_setCatchOnlyPopupKeys(true);
		menu.f_addEventListener(f_event.SELECTION, function(evt) {
			var jsEvt=evt.f_getJsEvent();
			var menu=evt.f_getComponent();
			var evtItem=evt.f_getItem();
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

		// this._oldClassName=undefined; // string
		// this._canSuggest=undefined; // boolean
		
		this.f_super(arguments);
	},
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
			f_core.Debug("f_suggestTextEntry", "_onSuggest: Event has been canceled !");
			return true;
		}

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			return f_core.CancelEvent(jsEvt);
		}
		
		return true;
	},
	/**
	 * @method private
	 */
	_onSuggest: function(evt) {
		var jsEvt=evt.f_getJsEvent();
		if (jsEvt.cancelBubble) {
			f_core.Debug("f_suggestTextEntry", "_onSuggest: Event has been canceled !");
			return true;
		}
		
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
		var menuOpened=(menu && menu.f_isOpened());

		var cancel=false;
		var value=this.value;
		var showPopup=false;

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			if (menuOpened) {
				return f_core.CancelEvent(jsEvt);
			}

			if (value==this._lastValue) {
				this._showPopup(jsEvt, true);

				return f_core.CancelEvent(jsEvt);
			}
			
			showPopup=true;
			break;

		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_TAB:
			// Le KeyUp vient du popup !
			return true;
		}

		f_core.Debug("f_suggestTextEntry", "_onSuggest: Charcode ("+jsEvt.keyCode+").");
		
		var value=this.value;
		if (value==this._lastValue) {
			f_core.Debug("f_suggestTextEntry", "_onSuggest: Same value ! (value="+value+" / last="+this._lastValue+")");
			return true;
		}
		
		if (menuOpened) {
			menu.f_close();
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
		
		f_core.Debug("f_suggestTextEntry", "_onSuggest: Set timeout to "+suggestionDelayMs);
		
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
				suggestTextEntry._onSuggestTimeOut();
			}, delay);
		}		
		
		if (cancel) {
			return f_core.CancelEvent(jsEvt);
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
		f_core.Debug("f_suggestTextEntry", "_onSuggestTimeOut() text='"+text+"'. (minChars="+minChars+")");

		if (minChars>0 && text.length<minChars) {
			return;
		}
		
		var results=this._results;
		if (results && results.length>0 && results.length==this._rowCount ) {
			var requestedText=this._requestedText;
			if (requestedText) {
				var rtext=text;
				if (!this._caseSensitive) {
					requestedText=requestedText.toLowerCase();
					rtext=rtext.toLowerCase();
				}
			
				if (rtext.indexOf(requestedText)==0) {
					this._showProposal();
					return;
				}
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
		
		this.f_appendCommand(function(suggestTextEntry) {
			suggestTextEntry._callServer();
		});
	},
	/**
	 * @method private
	 */
	_callServer: function() {
		this._calling=true;
		
		try {
			if (this.f_fireEvent(f_event.MENU)==false) {
				return;
			}	
		
			this.f_setLoading(true);
		
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
	
			this._results=undefined;
			
			f_core.Debug("f_suggestTextEntry", "Call server text='"+text+"' maxResultNumber="+maxResultNumber);
		
			var url=f_env.GetViewURI();	
			var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
			var suggestTextEntry=this;
			request.f_setListener({
				/**
				 * @method public
				 */
		 		onError: function(request, status, text) {
		 			f_core.Info(f_suggestTextEntry, "Bad status: "+request.f_getStatus());
		 			
					if (suggestTextEntry.f_processNextCommand()) {
						return;
					}

					suggestTextEntry.f_setLoading(false);
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
							f_core.Error(f_suggestTextEntry, "Bad Status ! ("+request.f_getStatusText()+")");
							return;
						}
		
						var responseContentType=request.f_getResponseContentType();
						if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
							f_core.Error(f_suggestTextEntry, "Unsupported content type: "+responseContentType);
							return;
						}
	
						suggestTextEntry._requestedText=text;
						//alert("Req='"+text+"'");
						var ret=request.f_getResponse();
						
						try {
							eval(ret);
							
						} catch (x) {
							f_core.Error(f_suggestTextEntry, "Can not eval response '"+ret+"'.", x);
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
	
		if (rs.length<1) {
			return;
		}
		
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
	 */
	_showPopup: function(jsEvt, autoSelect) {
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
		if (!menu) {
			return;
		}
		
		if (menu.f_isOpened()) {
			menu.f_close();
		}
		
		menu.f_removeAllItems(menu);
		
		var results=new Array;
		var complete=this._filterProposals(results);
		if (results.length<1) {
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
		
		if (!complete) {
			var message=f_resourceBundle.Get(f_suggestTextEntry).f_get("MORE_RELEVANT_RESULTS");
		
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
	
		menu.f_open(jsEvt, params, autoSelect);
//		this.focus();
	},
	/** 
	 * @method public
	 * @param String proposalLabel
	 * @param String proposalValue
	 * @param hidden Object proposalItem
	 * @param hidden Event jsEvt
	 * @return boolean
	 */
	f_showProposal: function(proposalLabel, proposalValue, proposalItem, jsEvt) {
		var label=this.f_getText();
		
		var labelCS=(this._caseSensitive)?label:label.toLowerCase();
		var proposalLabelCS=(this._caseSensitive)?proposalLabel:proposalLabel.toLowerCase();
		
		if (proposalLabelCS.indexOf(labelCS)!=0) {
			return false;
		}

		if (this._forceProposal) {
			var results=this._results;
			if (results && results.length>0 && results.length==this._rowCount ) {
				// Recherche les caracteres supplementaires !
				for(var i=label.length+1;i>=0 && i<=proposalLabelCS.length;i++) {
					var l=proposalLabelCS.substring(0, i);
					
					for(var j=0;j<results.length;j++) {
						var result=results[j]._label;
						var resultCS=(this._caseSensitive)?result:result.toLowerCase();
						
						if (resultCS.indexOf(labelCS)<0) {
							continue;
						}
						
						if (resultCS.indexOf(l)==0) {
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
		f_core.Debug("f_suggestTextEntry", "_setSuggestion: label='"+label+"' value='"+value+"' item='"+item+"'.");

		this.f_setText(label, true);
		this._setSuggestionValue(value, item, jsEvt);
		this._lastValue=this.value;
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
	 */
	_filterProposals: function(ret) {
		var d=this.f_getSelection();
		if (!d) {
			f_core.Debug("f_suggestTextEntry", "No selection to change proposal !");
			return true;
		}

		var results=this._results;
		if (!results) {
			return true;
		}

		var value=this.value;
		var text=value.substring(0, d.start);
		
		if (!this._caseSensitive) {
			text=text.toLowerCase();
		}

		var complete=true;
		
		for(var i=0;i<results.length;i++) {
			var r=results[i]._label;
			if (!this._caseSensitive) {
				r=r.toLowerCase();
			}
			if (r.indexOf(text)!=0) {
				complete=true;
				continue;
			}
			
			complete=false;
			
			ret.push(results[i]);
		}
		
		if (!this._orderedResult) {
			// Ben on en sait rien alors !
			complete=false;
		}
		
		if (results.length==this._rowCount) {
			complete=true;
		}
		
		f_core.Debug("f_suggestTextEntry", "Results="+results.length+" rowCount="+this._rowCount+" Post filter="+ret.length+" complete="+complete);
			
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
	 * @param String
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
	f_getEventLocked: function(showAlert, mask) {
		var menu=this.f_getSubMenuById(f_suggestTextEntry._SUGGESTION_MENU_ID);
		var menuOpened=(menu && menu.f_isOpened());
		if (menuOpened) {
			if (!mask) {
				mask=0;
			}	
			
			mask|=f_event.POPUP_LOCK;
		}

		return this.f_super(arguments, showAlert, mask);
	},
	/**
	 * @method hidden
	 */
	fa_cancelFilterRequest: function() {
	}
}

var f_suggestTextEntry=new f_class("f_suggestTextEntry", null, __static, __prototype, f_textEntry, fa_filterProperties, fa_commands);
