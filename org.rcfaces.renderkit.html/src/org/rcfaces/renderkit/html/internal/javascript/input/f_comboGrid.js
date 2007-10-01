/* 
 * $Id$
 */


/**
 * 
 * @class f_comboGrid extends f_textEntry, fa_dataGridPopup
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __statics = {

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
		
		this.f_super(arguments);	
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
		if (menuOpened) {
			// Aie aie aie
			this.f_closePopup(jsEvt);
			return true;
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
	fa_valueSelected: function(value, label, rowValues) {
		f_core.Debug(f_comboGrid, "fa_valueSelected: value='"+value+"' label='"+label+"'");
		
		if (this.f_fireEvent(f_event.SELECTION, null, rowValues, value)===false) {
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
		
		// Il faut tout selectionner car sous IE le focus se repositionne au début		
		input.select();		
	},
	_onBlur: function(event) {
		f_core.Debug(f_comboGrid, "_onBlur: formattedValue='"+this._formattedValue+"' (inputValue='"+this._inputValue+"')");
		this._focus=undefined;

		var menuOpened=this.f_isDataGridPopupOpened();
		if (menuOpened) {
			return;
		}

		// On affiche la zone formatée

		var input=this.f_getInput();
		
		this._inputSelection=f_core.GetTextSelection(input);
		this._inputValue=input.value;
		
		input.value=this._formattedValue;
		
		this.f_closePopup(event.f_getJsEvent());
	},
	f_performSelectionEvent: function() {
		// On traite pas le RETURN !
	},
	f_getSuggestionMinChars: function() {
		return this._suggestionMinChars;
	},
	f_getSuggestionDelayMs: function() {
		return this._suggestionDelayMs;
	}
}

new f_class("f_comboGrid", null, __statics, __members, f_textEntry, fa_dataGridPopup);
