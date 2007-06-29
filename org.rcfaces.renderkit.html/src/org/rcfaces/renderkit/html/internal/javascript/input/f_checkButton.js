/*
 * $Id$
 */
 
/**
 * class f_checkButton
 *
 * @class public f_checkButton extends f_button
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {

	/**
	 * @method hidden
	 */
	f_checkButton: function() {
		this.f_super(arguments);
		
		this._label=f_core.GetFirstElementByTagName(this, "label");
	},
	
	/**
	 * @method protected
	 */
	f_initializeInput: function() {
		var inputTagName=this.f_getInputTagName();
		
		var input=f_core.GetFirstElementByTagName(this, inputTagName);
		if (input) {
			return input;
		}
		input=f_core.GetChildByCssClass(this, this.f_getMainStyleClass()+"_input");
		if (input) {
			return input;
		}
		
		return this.f_super(arguments);
	},
	f_finalize: function() {
		var label=this._label;
		if (label) {
			this._label=undefined;
			
			f_core.VerifyProperties(label);			
		}
		this.f_super(arguments);
	},

	/**
	 * @method public
	 * @return boolean
	 */
	f_isSelected: function() {
		f_core.Assert(this._input, "f_checkButton.f_isSelected: Input is not found for selected property !");

		return (this._input.checked==true);
	},

	/**
	 * @method public
	 * @param boolean set
	 * @return void
	 */
	f_setSelected: function(set) {
		f_core.Assert(this._input, "f_checkButton.f_setSelected: Input is not found for selected property !");

		if (this._input.checked==set) {
			return;
		}
		
		this._input.checked = set;
	},

	/**
	 * @method protected
	 */
	f_setDomEvent: function(type, target) {
	
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
			target=this.f_getInput();
			break;
		}
		
		this.f_super(arguments, type, target);
	},
	
	/**
	 * @method protected
	 */
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
			target=this.f_getInput();
			break;
		}
		
		this.f_super(arguments, type, target);
	},

	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		if (!this._label) {
			return this.f_super(arguments);
		}

		return f_core.GetTextNode(this._label);
	},

	/**
	 * @method public
	 * @param String text
	 * @return void
	 */
	f_setText: function(text) {
		if (!this._label) {
			return this.f_super(arguments, text);
		}
		if (text==this.f_getText()) {
			return;
		}
		f_core.SetTextNode(this._label, text);
		this.f_setProperty(f_prop.TEXT,text);
	},
	/**
	 * @method protected
	 */
	f_serialize: function() {
		if (this.f_isDisabled()) {
			if (this.f_isSelected()) {			
				this.f_setProperty(f_prop.SELECTED, this._input.value);
			}
		}
		return this.f_super(arguments);
	}
}

new f_class("f_checkButton", {
	extend: f_button,
	members: __members
});
