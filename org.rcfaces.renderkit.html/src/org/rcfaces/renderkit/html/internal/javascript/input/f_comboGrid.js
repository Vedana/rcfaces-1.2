/* 
 * $Id$
 */


/**
 * 
 * @class f_comboGrid extends f_combo, fa_disabled, fa_required, fa_readOnly
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __static = {
	_OnButtonMouseDown: function(evt) {
		var comboGrid=this._comboGrid;
		if (comboGrid.f_getEventLocked()) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseDown(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	_OnButtonMouseUp: function(evt) {
		var comboGrid=this._comboGrid;
		if (comboGrid.f_getEventLocked(false)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseUp(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	_OnButtonMouseOver: function(evt) {
		var comboGrid=this._comboGrid;
		if (comboGrid.f_getEventLocked(false)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseOver(evt);
		
		return f_core.CancelJsEvent(evt);		
	},
	_OnButtonMouseOut: function(evt) {
		var comboGrid=this._comboGrid;
		if (comboGrid.f_getEventLocked(false)) {
			return false;
		}
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (comboGrid.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		comboGrid._onButtonMouseOut(evt);
		
		return f_core.CancelJsEvent(evt);		
	}
}

var __prototype = {

	f_comboGrid: function() {
		this.f_super(arguments);
		
		var button=f_core.GetFirstElementByTagName(this, "img", true);
		this._button=button;
		
		button._comboGrid=this;
		button.onmousedown=f_comboGrid._OnButtonMouseDown;
		button.onmouseup=f_comboGrid._OnButtonMouseUp;
		button.onmouseover=f_comboGrid._OnButtonMouseOver;
		button.onmouseout=f_comboGrid._OnButtonMouseOut;
		
		var input=f_core.GetFirstElementByTagName(this, "input", true);
		this._input=input;
		input._comboGrid=this;
		
	},

	f_finalize: function() {
	
		// this._buttonOver=undefined; // boolean
		// this._buttonDown=undefined; // boolean
	
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
		
		var input=this._input;
		if (input) {
			this._input=undefined; // HTMLInputElement
			
			input._comboGrid=undefined; // f_comboGrid

			f_core.VerifyProperties(input);
		}	
		
		this.f_super(arguments);
	},
	_onButtonMouseDown: function(evt) {
		this._buttonDown=true;
		this.f_updateButtonStyle();
		
		this.f_setFocus();
		this.f_openPopup(evt);
	},
	_onButtonMouseUp: function(evt) {
		this._buttonDown=false;
		this.f_updateButtonStyle();	
	},
	_onButtonMouseOver: function(evt) {
		this._buttonOver=true;
		this.f_updateButtonStyle();
	},
	_onButtonMouseOut: function(evt) {
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
	 * @return void
	 */
	f_updateStyleClass: function(postSuffix) {
		var suffix="";

		if (this.f_isDisabled()) {
			suffix+="_disabled";

		} else if (this.f_isReadOnly()) {
			suffix+="_readOnly";
		}
		
		if (postSuffix) {
			suffix+=postSuffix;
		}
	
		var claz=this.f_computeStyleClass(suffix);
		if (this.className!=claz) {
			this.className=claz;
		}
	},
	fa_updateDisabled: function() {
		this.f_updateButtonStyle();
		
		var input=this._input;
		if (input) {
			input.disabled=this.f_isDisabled();
		}
	},
	fa_updateReadOnly: function() {
		var input=this._input;
		if (input) {
			input.readOnly=this.f_isReadOnly();
		}
	},
	/**
	 * @method protected
	 */
	f_openPopup: function(jsEvent) {
		if (this.f_isReadOnly()) {
			return;
		}
	},
	/**
	 * Set the focus to this component.
	 *
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this.f_isDisabled()) {
			return false;
		}

		f_core.Debug(f_comboGrid, "f_setFocus: Set focus on comboGrid '"+this.id+"'.");
		
		var cmp=this._input;
		if (!cmp) {
			cmp=this;
		}
		
		cmp.focus();
	}
}

new f_class("f_comboGrid", null, __static, __prototype, f_component, fa_disabled, fa_required, fa_readOnly);
