/*
 * $Id$
 */

/**
 * f_textEntry class
 *
 * @class f_textEntry extends f_textArea
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {
	f_textEntry: function() {
		this.f_super(arguments);
		
		var autoTab=f_core.GetAttribute(this, "v:autoTab");
		if (autoTab) {
			this._autoTab=true;
		}
				
		if (window.f_clientValidator) {
			f_clientValidator.InstallValidator(this);
		}
	},
	/*
	f_finalize: function() {
		// this._autoTab=undefined;  // boolean
		
		this.f_super(arguments);
	},
	*/
	f_update: function() {
		
		if (this.f_isAutoTab()) {
			this._installAutoTab();
		}
		
		this.f_super(arguments);
	},
	/**
	 * 
	 * @method protected
	 * @return String
	 */
	f_getInputTagName: function() {
		return "INPUT";
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getMaxLength: function() {
		return this.f_getInput().maxLength;
	},
	/**
	 * @method public
	 * @return boolean Returns <code>true</code> if auto tab facility is enabled.
	 */
	f_isAutoTab: function() {
		return this._autoTab?true:false;
	},
	/**
	 * @method protected
	 */
	f_performSelectionEvent: function(evt) {
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
	 * @method public
	 * @return boolean
	 */
	f_isAutoCompletion: function() {
		var input=this.f_getInput();
		
		return f_core.GetAttribute(input, "autoComplete")!="off";	
	},
	/**
	 * @method public
	 * @param optional boolean complete
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
	fa_updateRequired: function() {
		this.f_updateStyleClass();

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
	}
}

new f_class("f_textEntry", null, null, __prototype, f_textArea);
