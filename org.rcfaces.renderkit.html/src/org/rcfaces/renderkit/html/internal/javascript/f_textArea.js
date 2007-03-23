/*
 * $Id$
 */

/**
 * f_textArea class
 *
 * @class public f_textArea extends f_input, fa_required, fa_selectionProvider, fa_subMenu, fa_focusStyleClass
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	/**
	 * @field private static final String
	 */
	_TEXT_MENU_ID: "#text"
}

var __prototype = {
	f_textArea: function() {
		this.f_super(arguments);
		
		this._emptyMessage=f_core.GetAttribute(this, "v:emptyMessage");
		
		if (this._emptyMessage) {
			this.f_insertEventListenerFirst(f_event.FOCUS, this._messageFocusEvent);
			this.f_insertEventListenerFirst(f_event.BLUR, this._messageBlurEvent);
		}

		// On peut pas le mêtre dans le f_setDomEvent, la profondeur de la pile ne le permet pas !
		this.f_insertEventListenerFirst(f_event.KEYPRESS, this.f_performSelectionEvent);
	},
	/*
	f_finalize: function() {
		// this._emptyMessage=undefined; // string
		// this._showEmptyMessage=undefined; // string
		// this._requiredInstalled=undefined; // boolean
		
		this.f_super(arguments);
	},
	*/
	f_update: function() {

		var menu=this.f_getSubMenuById(f_textArea._TEXT_MENU_ID);
		if (menu) {
			this.f_insertEventListenerFirst(f_event.MOUSEDOWN, this._performMenuMouseDown);
		}
		
		if (this.f_isRequired()) {
			this.fa_updateRequired();
		}
		
		this.f_super(arguments);
	},
	f_serialize: function() {
		if (this.f_isDisabled()) {
			this.f_setProperty(f_prop.TEXT, this.f_getText());

		} else {
			// Le probleme est que le TEXT peut persister ... 
			// et que la valeur soit modifiée par l'utilisateur ...
			this.f_setProperty(f_prop.TEXT);
		}
		
		if (this._showEmptyMessage) {
			// On ne sérialise pas le message !
			this._showEmptyMessage=undefined;
			this.value="";
		}
		
		return this.f_super(arguments);
	},
	/**
	 * 
	 * @method protected
	 * @return String
	 */
	f_getInputTagName: function() {
		return "TEXTAREA";
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			return;
			
		case f_event.KEYPRESS:
		case f_event.KEYDOWN:
		case f_event.KEYUP:
		case f_event.FOCUS:
		case f_event.BLUR:
			// Car _input est peut etre pas encore initialisé !
			target=this.f_getInput();
			break;
		}
		
		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.KEYPRESS:
		case f_event.KEYDOWN:
		case f_event.KEYUP:
		case f_event.FOCUS:
		case f_event.BLUR:
			target=this.f_getInput();
			break;
		}
		
		this.f_super(arguments, type, target);
	},
	/**
	 * @method protected
	 */
	f_performSelectionEvent: function(evt) {
		if (this.f_isDisabled()) {
			return;
		}
		
		if (this.f_isActionListEmpty(f_event.SELECTION)) {
			return;
		}
		
		var jsEvent=evt.f_getJsEvent();
		if (!jsEvent) {
			return;
		}
		
		if (!jsEvent.ctrlKey) {
			return;
		}
			
		var code=jsEvent.keyCode;
	
		if (code!=f_key.VK_RETURN && code!=f_key.VK_ENTER) { // RETURN ou ENTER
			return;
		}

		evt.f_preventDefault();

		this.f_fireEvent(f_event.SELECTION, jsEvent);
		
		return false;
	},
	/**
	 * @method private
	 */
	_performMenuMouseDown: function(event) {		
		var evt=event.f_getJsEvent();
		
		var sub=f_core.IsPopupButton(evt);
		if (!sub) {
			return;
		}
		
		var menu=this.f_getSubMenuById(f_textArea._TEXT_MENU_ID);
		if (menu) {
			menu.f_open(this, {
				position: f_menu.MOUSE_POSITION
				}, this, evt);
		
			return event.f_preventDefault();
		}
	},
	/**
	 * @method private
	 */
	_messageFocusEvent: function() {
	},
	/**
	 * @method private
	 */
	_messageBlurEvent: function() {
	},
	/**
	 * @method public
	 * @return Object  An object which defines fields 'text', 'start' and 'end'
	 */
	f_getSelection: function() {
		// Retourne deux entiers qui positionnent le debut et la fin de la selection
		var pos=f_core.GetTextSelection(this);
		
		var value=this.f_getText();
		if (!pos) {
			return {
				text: value,
				start: 0,
				end: value.length
			};
		}
		
		return {
			start: pos[0],
			end: pos[1],
			text: value.substring(pos[0], pos[1])
		};
	},
	/**
	 * @method public
	 * @param Object selection An object which defines fields 'start' and 'end'
	 * @param optional boolean show If possible, show the selection.
	 * @return void
	 */
	f_setSelection: function(selection, show) {
		// C'est un object avec un champ "start" et eventuellement un champ "end" >= "start"
		f_core.Assert(typeof(selection)=="object"
			&& typeof(selection.start)=="number"
			&& selection.start>=0, "Selection must be an object which defines 'start' and 'end' field with positive numbers.");
		
		var start=selection.start;
		var end=selection.end;
		if (!end || end<start) {
			end=start;
		}
		
		f_core.Debug(f_textArea, "SetSelection start="+start+" end="+end+".");
		
		f_core.SelectText(this.f_getInput(), start, end);
		
		if (show) {
			this.scrollIntoView();
		}
	},
	/**
	 * @method public
	 * @param boolean show If possible, show the selection.
	 * @return void
	 */
	f_selectAll: function(show) {
		this.f_getInput().select();
	},
	/**
	 * @method protected
	 * @return void
	 */
	fa_componentCaptureMenuEvent: function() {
		return null;
	},
	/**
	 * @method protected
	 * @return void
	 */
	fa_updateRequired: function() {
		// XXXXXX @TODO this._installRequiredValidator();
		this.f_updateStyleClass();
	}
}

new f_class("f_textArea", null, __static, __prototype, f_input, fa_required, fa_selectionProvider, fa_subMenu);

