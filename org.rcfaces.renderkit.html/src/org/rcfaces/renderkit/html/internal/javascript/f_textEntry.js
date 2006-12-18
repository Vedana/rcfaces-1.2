/*
 * $Id$
 */

/**
 * f_textEntry class
 *
 * @class f_textEntry extends f_input, fa_required, fa_selectionProvider, fa_subMenu, fa_focusStyleClass, fa_message
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	/**
	 * @field private static final string
	 */
	_TEXT_MENU_ID: "#text"
}

var __prototype = {
	f_textEntry: function() {
		this.f_super(arguments);
		
		var autoTab=f_core.GetAttribute(this, "v:autoTab");
		if (autoTab) {
			this._autoTab=true;
		}
		
		this._emptyMessage=f_core.GetAttribute(this, "v:emptyMessage");
		
		var focusStyleClass=this.f_getFocusStyleClass();
		if (focusStyleClass || this._emptyMessage) {
			this._oldStyleClass=this.className;
			
			this.f_addEventListener(f_event.FOCUS, this._performFocusEvent);
			this.f_addEventListener(f_event.BLUR, this._performBlurEvent);
		}
		
		if (window.f_clientValidator) {
			f_clientValidator.InstallValidator(this);
		}
	},
	/*
	f_finalize: function() {
		// this._autoTab=undefined;  // boolean
		// this._oldStyleClass=undefined;  // string
		// this._emptyMessage=undefined; // string
		// this._showEmptyMessage=undefined; // string
		// this._requiredInstalled=undefined // boolean
		
		this.f_super(arguments);
	},
	*/
	f_update: function() {

		var menu=this.f_getSubMenuById(f_textEntry._TEXT_MENU_ID);
		if (menu) {
			this.f_addEventListener(f_event.MOUSEDOWN, this._performMenuMouseDown);
		}
		
		if (this.f_isAutoTab()) {
			this._installAutoTab();
		}
		
		if (this.f_isRequired()) {
			this._installRequiredValidator();
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
	 * @method public
	 * @return number
	 */
	f_getMaxLength: function() {
		return this._input.maxLength;
	},
	/**
	 * @method public
	 * @return boolean Returns <code>true</code> if auto tab facility is enabled.
	 */
	f_isAutoTab: function() {
		return this._autoTab?true:false;
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			this.f_addEventListener(f_event.KEYPRESS, this._performSelectionEvent);
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
		case f_event.SELECTION: 
			this.f_removeEventListener(f_event.KEYPRESS, this._performSelectionEvent);
			return;
	
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
	 * @method private
	 */
	_performSelectionEvent: function(evt) {
		if (this.f_isDisabled()) {
			return;
		}
		
		var jsEvent=evt.f_getJsEvent();
		if (!jsEvent) {
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
		
		var menu=this.f_getSubMenuById(f_textEntry._TEXT_MENU_ID);
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
	_performFocusEvent: function() {
		var focusStyleClass=this.f_getFocusStyleClass();
		if (focusStyleClass!=this.className) {
			return;
		}
		
		this.className=focusStyleClass;
	},
	/**
	 * @method private
	 */
	_performBlurEvent: function() {
		var focusStyleClass=this._oldStyleClass;
		if (focusStyleClass==this.className) {
			return;
		}
		
		this.className=focusStyleClass;
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
	 * @param boolean show If possible, show the selection.
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
		
		f_core.Debug(f_textEntry, "SetSelection start="+start+" end="+end+".");
		
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
	 * @method public
	 * @return boolean
	 */
	f_isAutoCompletion: function() {
		var input=this.f_getInput();
		
		return f_core.GetAttribute(input, "autoComplete")!="off";	
	},
	/**
	 * @method public
	 * @param boolean complete
	 * @return void
	 */
	f_setAutoCompletion: function(complete) {
		if (complete===undefined) {
			complete=true;
		}
		
		if (complete==this.f_isAutoCompletion()) {
			return;
		}
		
		var input=this.f_getInput();
	
		input.setAttribute("autoComplete", (complete)?"on":"off");
		
		this.f_setProperty(f_prop.AUTO_COMPLETION, complete);
	},
	/**
	 * @method private
	 * @return void
	 */
	_installAutoTab: function() {
		if (!window.f_clientValidator) {
			f_core.Error(f_textEntry, "Validator class is not known !");
			return;
		}
		
		f_core.Debug(f_textEntry, "Install autotab processor to component '"+this.id+"' !");
		
		var validator = f_clientValidator.GetValidator(this);
		if (!validator) {
			validator = f_clientValidator.f_newInstance(this);
		}
		
		validator.f_addProcessor(f_vb.Processor_autoTab);
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
		this._installRequiredValidator();
	},
	/**
	 * @method private
	 * @return void
	 */
	_installRequiredValidator: function() {
		if (this._requiredInstalled) {
			return;
		}
		
		this._requiredInstalled=true;

		if (!window.f_clientValidator) {
			f_core.Error(f_textEntry, "Validator class is not known !");
			return;
		}
	
		f_core.Debug(f_textEntry, "Install required behavior to component '"+this.id+"' !");
		
		var validator = f_clientValidator.GetValidator(this);
		if (!validator) {
			validator = f_clientValidator.f_newInstance(this);
		}
		
		validator.f_addBehavior(f_vb.Behavior_required);		
	},
	
	f_performMessageChanges: function(messageContext) {	
	}
}

var f_textEntry=new f_class("f_textEntry", null, __static, __prototype, f_input, fa_required, fa_selectionProvider, fa_subMenu, fa_focusStyleClass, fa_message);
