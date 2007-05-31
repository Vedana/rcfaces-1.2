/* 
 * $Id$
 */


/**
 * 
 * @class f_comboGrid extends f_textEntry, fa_dataGridPopup
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __static = {

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
	 */
	_OnButtonMouseOut: function(evt) {
		var comboGrid=this._comboGrid;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		comboGrid._onButtonMouseOut(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	_OnBeforeDeactivate: function() {
		var evt = f_core.GetJsEvent(this);
		
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

var __prototype = {
	
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
		
		var button=f_core.GetFirstElementByTagName(this, "img", true);
		this._button=button;
		
		this._formattedValue=this.f_getInput().value;
		this._selectedValue=f_core.GetAttribute(this, "v:selectedValue", "");
		this._inputValue=this._selectedValue;
		
		button._comboGrid=this;
		button.onmousedown=f_comboGrid._OnButtonMouseDown;
		button.onmouseup=f_comboGrid._OnButtonMouseUp;
		button.onmouseover=f_comboGrid._OnButtonMouseOver;
		button.onmouseout=f_comboGrid._OnButtonMouseOut;
		
		this.f_getInput().onbeforedeactivate=f_comboGrid._OnBeforeDeactivate;
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._onCancelDown);
		this.f_insertEventListenerFirst(f_event.KEYUP, this._onSuggest);
		this.f_insertEventListenerFirst(f_event.FOCUS, this._onFocus);
		this.f_insertEventListenerFirst(f_event.BLUR, this._onBlur);
	},

	f_finalize: function() {

		this.f_getInput().onbeforedeactivate=null;
	
		// this._buttonOver=undefined; // boolean
		// this._buttonDown=undefined; // boolean
		// this._suggestionDelayMs=undefined;  // number
		// this._suggestionMinChars=undefined; // number
		// this._valueFormat=undefined; // String
		// this._formattedValue=undefined; // String
		// this._inputValue=undefined; // String
		// this._inputSelection=undefined; // int[]
		// this._focus=undefined; // boolean
		// this._selectedValue=undefined; // String

		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			window.clearTimeout(timerId);
		}
	
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
		
		this.f_super(arguments);
	},
	f_serialize: function() {
		this.f_setProperty(f_prop.SELECTED, this._selectedValue);
		
		return this.f_super(arguments);	
	},
	/**
	 * @method private
	 * @param Event evt
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
	 *  @method protected
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
	 * @param Event jsEvent
	 * @param optional boolean autoSelect
	 * @return void
	 */
	f_openPopup: function(jsEvent, autoSelect) {
		if (this.f_isReadOnly()) {
			return;
		}
		
		var text;
		if (this._focus) {
			text=this.f_getText();
		} else {
			text=this._selectedValue;
		}
		
		this.f_openDataGridPopup(jsEvent, text, autoSelect);
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @return void
	 */
	f_closePopup: function(jsEvent) {		
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
		if (jsEvt.cancelBubble) {
			f_core.Debug(f_comboGrid, "_onSuggest: Event has been canceled !");
			return true;
		}
		
		var menuOpened=this.f_isDataGridPopupOpened();

		f_core.Debug(f_comboGrid, "_onSuggest: Charcode ("+jsEvt.keyCode+") menuOpened="+menuOpened);

		var cancel=false;
		var value=this.f_getValue();
		var showPopup=false;
		
		var newInput=this.f_getInput().value;
		if (this._inputValue!=newInput) {
			f_core.Debug(f_comboGrid, "_onSuggest: Different values  newInput='"+newInput+
				"' inputValue='"+this._inputValue+
				"' formattedValue='"+this._formattedValue+
				"' selectedValue='"+this._selectedValue+"'.");
			
			this._formattedValue="";
			this._inputValue=newInput;
			this._inputSelection=undefined;
			
			if (newInput!=this._selectedValue && this._selectedValue) {
				this._selectedValue=null;
	
				this.f_fireEvent(f_event.SELECTION, jsEvt, null, null);
			}
		}

		switch(jsEvt.keyCode) {
		case f_key.VK_DOWN:
		case f_key.VK_UP:
			var direction=(jsEvt.keyCode==f_key.VK_DOWN)?1:-1;
			
			if (menuOpened) {
				//this.f_changeSelection(direction);
				return true;// f_core.CancelJsEvent(jsEvt);
			}

			if (value==this._lastValue) {
				this.f_openPopup(jsEvt, direction);

				return f_core.CancelJsEvent(jsEvt);
			}
			
			showPopup=true;
			break;

		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_TAB:
			// Le KeyUp vient du popup !
			if (menuOpened) {
				// Selection !
				return f_core.CancelJsEvent(jsEvt);
			}
			
			this.f_closePopup(jsEvt);
			return true;
		}
		
		var value=this.f_getValue();
		if (value==this._lastValue) {
			f_core.Debug(f_comboGrid, "_onSuggest: Same value ! (value='"+value+"' / last='"+this._lastValue+"')");
			return true;
		}
		
		if (menuOpened) {
			//this.f_clearAllGridRows(); // ???
		}
		
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
		
		f_core.Debug(f_comboGrid, "_onSuggest: Set timeout to "+suggestionDelayMs);
		
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

			var self=this;
			this._timerId=window.setTimeout(function() {
				if (window._f_exiting) {
					return;
				}
							
				try {
					self._onSuggestTimeOut();
					
				} catch (x) {
					f_core.Error(f_comboGrid, "_onSuggest.timer: Timeout processing error !", x);
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
		if (!this._focus) {
			return;
		}
		
		if (!text) {
			text=this.f_getText();
		}
		
		var minChars=this._suggestionMinChars;
		f_core.Debug(f_comboGrid, "_onSuggestTimeOut: text='"+text+"'. (minChars="+minChars+")");

		if (minChars>0 && text.length<minChars) {
			return;
		}

		this.f_openDataGridPopup(null, text);
	},
	/**
	 * Returns the selected value.
	 * 
	 * @method public
	 * @return String 
	 */
	f_getSelectedValue: function() {
		return this._selectedValue;
	},
	/**
	 * @method public
	 * @param String value
	 * @return void
	 */
	f_setSelectedValue: function(value) {
		f_core.Assert(value===null || typeof(value)=="string", "f_comboGrid.f_setSelectedValue: Invalid value parameter ("+value+").");
		
		if (value!==null) {
			f_core.Error(f_comboGrid, "f_setSelectedValue: A value different of NULL is not supported yet.");
		}
		
		this._selectedValue=value;		
		this._inputValue="";
		this._inputSelection=undefined;
		this._formattedValue=""; // ???
		
		this.f_getInput().value="";
	},
	/**
	 * @method protected
	 */
	fa_valueSelected: function(value, label) {
		f_core.Debug(f_comboGrid, "fa_valueSelected: value='"+value+"' label='"+label+"'");
		
		if (this.f_fireEvent(f_event.SELECTION, null, null, value)===false) {
			return;
		}
		
		this._formattedValue=(label)?label:"";
		this._selectedValue=value;
		this._inputValue=value;
		this._inputSelection=undefined;
		this._lastValue=value;
		
		var input=this.f_getInput();
		input.value=value;
		f_core.SelectText(input, value.length);
	},
	_onFocus: function() {
		f_core.Debug(f_comboGrid, "_onFocus: inputValue='"+this._inputValue+"'  (formattedValue='"+this._formattedValue+"')");

		this._focus=true;
		
		// On affiche la clef, ou la valeur saisie
		var input=this.f_getInput();
		
		input.value=this._inputValue;
		
		var selection=this._inputSelection;
		if (selection) {
			this._inputSelection=undefined;
			
			f_core.SelectText(input, selection);
		}
		
	},
	_onBlur: function(event) {
		f_core.Debug(f_comboGrid, "_onBlur: formattedValue='"+this._formattedValue+"' (inputValue='"+this._inputValue+"')");
		this._focus=undefined;

		// On affiche la zone formatée

		var input=this.f_getInput();
		
		this._inputSelection=f_core.GetTextSelection(input);
		this._inputValue=input.value;
		
		input.value=this._formattedValue;
		
		this.f_closePopup(event.f_getJsEvent());
	},
	f_performSelectionEvent: function() {
		// On traite pas le RETURN !
	}
}

new f_class("f_comboGrid", null, __static, __prototype, f_textEntry, fa_dataGridPopup);
