/*
 * $Id$
 */
 
/**
 * fa_spinner class
 *
 * @aspect fa_spinner
 */

var __static = {

	/**
	 * @field private static final number
	 */
	_DEFAULT_STEP: 1,
	
	/**
	 * @field private static final number
	 */
	_START_DELAY: 500,

	/**
	 * @field private static final number
	 */
	_REPETITION_DELAY: 100,
	
	/**
	 * @field private static 
	 */
	_IntervalId: undefined,
	
	/**
	 * @field private static 
	 */
	_LastTimerType: undefined,
	
	/**
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		// fa_spinner._LastTimerType=undefined;
		
		var id=fa_spinner._IntervalId;
		if (!id) {
			return;
		}
		fa_spinner._IntervalId=undefined;
		
		window.clearInterval(id);
	},	
	
	/**
	 * @method private static
	 */
	_OnSpinnerButtonOver: function(evt) {
		var spinner=this._spinner;
		if (spinner.f_getEventLocked(false) || spinner.f_isDisabled()) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
	
		if (!f_core.GetEvtButton(evt) && this._pushed) {
			this._pushed=false;
			fa_spinner._InstallTimer(null);
		}
				
		this._hover=true;
		spinner.f_updateSpinnerButton(this);
		
		if (this._pushed) {
			fa_spinner._InstallTimer(this);
		}
	
		return true;
	},
	
	/**
	 * @method private static
	 */
	_OnSpinnerButtonOut: function() {
		if (!this._hover) {
			return;
		}
		
		var spinner=this._spinner;

		this._hover=undefined;
		spinner.f_updateSpinnerButton(this);
		
		fa_spinner._InstallTimer(null);
		
		return true;	
	},
	
	/**
	 * @method private static
	 */
	_OnSpinnerButtonDown: function(evt) {
		var spinner=this._spinner;
		if (spinner.f_getEventLocked() || spinner.f_isDisabled()) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		this._pushed=true;
		
		// TIMER pour la répétition ! x
		
		spinner._focusInput=true;
		
		spinner.f_updateSpinnerButton(this);
		spinner.f_performStep(this._scale, evt);
		
		fa_spinner._InstallTimer(this, true);
	},
	
	/**
	 * @method private static
	 */
	_InstallTimer: function(button, start) {
		var id=fa_spinner._IntervalId;
		if (id) {
			window.clearInterval(id);
			id=undefined;
			fa_spinner._LastTimerType=undefined;
		}
		
		if (button) {
			fa_spinner._LastTimerType=start;
			
			id=window.setInterval(function() {
				fa_spinner._PerformTick(button);
			}, (start)?fa_spinner._START_DELAY:fa_spinner._REPETITION_DELAY);
		}
				
		fa_spinner._IntervalId=id;
	},
	
	/**
	 * @method private static
	 */
	_PerformTick: function(button) {
		if (!button._hover || !button._pushed) {
			return;
		}
		
		var spinner=button._spinner;
		if (spinner.f_getEventLocked() || spinner.f_isDisabled()) {
			return false;
		}
	
		spinner.f_performStep(button._scale, null);
		
		if (fa_spinner._LastTimerType) {
			fa_spinner._InstallTimer(button);
		}
	},
	
	/**
	 * @method private static
	 */
	_OnSpinnerButtonUp: function() {
		var spinner=this._spinner;
		if (!this._pushed) {
			return;
		}
		
		this._pushed=undefined;

		spinner.f_updateSpinnerButton(this);
		
		if (spinner._focusInput) {
			spinner._focusInput=undefined;
			
			spinner.f_setFocus();
		}
	}
}

var __prototype = {
	fa_spinner: function() {			
		var disabledSuffix=(this.f_isDisabled())?"_disabled":"";
		
		this._cycleValue=(f_core.GetAttribute(this, "v:cycle")=="true");
		
		this._spinnerUp=f_core.GetChildByCssClass(this, "f_spinner_up"+disabledSuffix);
		this._installSpinnerButton(this._spinnerUp, "_up",  1);
		
		this._spinnerDown=f_core.GetChildByCssClass(this, "f_spinner_down"+disabledSuffix);
		this._installSpinnerButton(this._spinnerDown, "_down", -1);
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._performSpinnerKeyDown);
		this.f_insertEventListenerFirst(f_event.KEYUP, this._performSpinnerKeyUp);
		this.f_insertEventListenerFirst(f_event.KEYPRESS, this._performSpinnerKeyPress);
	},
	
	f_finalize: function() {
		// this._minimum=undefined;  // number
		// this._maximum=undefined;  // number
		// this._step=undefined; // number
		// this._cycleValue=undefined; // boolean
		// this._focusInput=undefined; // boolean
		// this._dontUpdateSpinnerParameter=true; // boolean

		var up=this._spinnerUp;
		if (up) {
			this._spinnerUp=undefined; // HTMLImageElement
			this._destroySpinnerButton(up);
		}
		
		var down=this._spinnerDown;
		if (down) {
			this._spinnerDown=undefined; // HTMLImageElement
			this._destroySpinnerButton(down);
		}
	},
	/**
	 * @method hidden
	 */
	f_dontUpdateSpinnerParameter: function() {
		this._dontUpdateSpinnerParameter=true;
	},
	/**
	 * @method private
	 */
	_performSpinnerKeyPress: function(cevt) {
		var jsEvent=cevt.f_getJsEvent();
		var button=this._getKeyButton(cevt);
		if (button) {
			this.f_performStep(button._scale, jsEvent);
			return;
		}
		
		var keyCode = jsEvent.keyCode;
		var charCode = jsEvent.charCode;

/*		
		var keyChar;
		
		if (!charCode) {
			keyChar = String.fromCharCode(keyCode);

		} else {
			keyChar = String.fromCharCode(charCode);
		}
				
		f_core.Debug(fa_spinner, "KeyPress: keyCode="+keyCode+" charCode="+charCode+" shift="+jsEvent.shift+" ctrl="+jsEvent.ctrl+" alt="+jsEvent.alt+" keyChar="+keyChar+"("+((keyChar.length>0)?keyChar.charCodeAt(0):"")+")");
*/	
	
		if (f_core.IsInternetExplorer()) {
			if (keyCode < 32) {
				return true;
			}
		} else if (f_core.IsGecko()) {
			if (keyCode>0) {
				return true;
			}
			keyCode=charCode;
		}
		
		if (keyCode==44 || keyCode==46) {
			var v=this.f_getValue();
			if (v.indexOf('.')>=0 || v.indexOf(',')>=0) {
				return false;
			}
			
			return true;
		}
		if (keyCode==45) {
			var v=this.f_getValue();
			
			var selection=f_core.GetTextSelection(this.f_getInput());
			
			if (selection[0]>0 || v.indexOf('-')>=0) {
				return false;
			}
			
			return true;
		}
		
		if (keyCode>=48 && keyCode<=58) {
			return true;
		}
		
		return false;
	},
	/**
	 * @method private
	 */
	_performSpinnerKeyDown: function(cevt) {
		var button=this._getKeyButton(cevt);
		if (!button) {		
			return;
		}
		
		button._keyPress=true;
		this.f_updateSpinnerButton(button);
	},
	/**
	 * @method private
	 */
	_performSpinnerKeyUp: function(cevt) {
		var button=this._getKeyButton(cevt);
		if (!button) {
			return;
		}
		
		button._keyPress=undefined;
		this.f_updateSpinnerButton(button);
	},
	/**
	 * @method private
	 */
	_getKeyButton: function(cevt) {
		var evt=cevt.f_getJsEvent();
	
		switch(evt.keyCode) {
		case f_key.VK_DOWN: // FLECHE VERS LE BAS
			return this._spinnerDown;
						
		case f_key.VK_UP: // FLECHE VERS LE HAUT
			return this._spinnerUp;
		}
		
		return null;
	},
	/**
	 * @method private
	 */
	_destroySpinnerButton: function(button) {
		//button._scale=undefined; // number
		//button._hover=undefined; // boolean
		//button._pushed=undefined; // boolean
		//button._suffix=undefined; // string
		//button._keyPress=undefined; // boolean
		
		button._spinner=undefined; // f_spinner
		button.onmouseover=null; // function
		button.onmouseout=null; // function
		button.onmousedown=null; // function
		button.onmouseup=null; // function

		f_core.VerifyProperties(button);
	},
	/**
	 * @method private
	 */
	_installSpinnerButton: function(button, suffix, scale) {
	
		button._scale=scale;
		button._spinner=this;
		button._suffix=suffix;
		button.onmouseover=fa_spinner._OnSpinnerButtonOver;
		button.onmouseout=fa_spinner._OnSpinnerButtonOut;
		button.onmousedown=fa_spinner._OnSpinnerButtonDown;
		button.onmouseup=fa_spinner._OnSpinnerButtonUp;
	},
	/**
	 * @method protected
	 */
	f_updateSpinnerButton: function(button) {		
		var suffix=button._suffix;
		if (this.f_isDisabled()) {
			suffix+="_disabled";
		
		} else if (button._keyPress) {
			suffix+="_pushed";
			
		} else if (button._hover) {
			if (button._pushed) {
				suffix+="_pushed";
				
			} else {
				suffix+="_hover";
			}
		}
		
		var cname=this.f_computeStyleClass(suffix);
		
		if (button.className==cname) {
			return;
		}
		
		button.className=cname;
	},

	/**
	 * @method public
	 * @return number Minimum value or <code>null</code>.
	 */
	f_getMinimum: function() {
		var minimum=this._minimum;
		if (minimum===undefined) {
			minimum=f_core.GetAttribute(this, "v:minimum");
			
			minimum=(minimum)?parseFloat(minimum):null;
			this._minimum=minimum;
		}
		return minimum;
	},
	/*
	 * @method public
	 * @param number minimum Minimum value.
	 * @return void
	 *
	f_setMinimum: function(minimum) {
		f_core.Assert(typeof(minimum)=="number", "Invalid parameter '"+minimum+"'.");
		
		if (minimum==this.f_getMinimum()) {
			return;
		}
		
		this._minimum=minimum;
		if (!this._dontUpdateSpinnerParameter) {
			this.f_setProperty(f_prop.MIN, minimum);	
		}
	},
	*/
	/**
	 * @method public
	 * @return number Maximum value or <code>null</code>.
	 */
	f_getMaximum: function() {
		var maximum=this._maximum;
		if (maximum===undefined) {
			maximum=f_core.GetAttribute(this, "v:maximum");
			
			maximum=(maximum)?parseFloat(maximum):null;
			this._maximum=maximum;
		}
		return maximum;
	},
	/*
	 * @method public
	 * @param number maximum Maximum value.
	 * @return void
	 *
	f_setMaximum: function(maximum) {
		f_core.Assert(typeof(maximum)=="number", "Invalid parameter '"+maximum+"'.");
		
		if (maximum==this.f_getMaximum()) {
			return;
		}
		
		this._maximum=maximum;
		if (!this._dontUpdateSpinnerParameter) {
			this.f_setProperty(f_prop.MAX, maximum);
		}
	},
	*/
	/**
	 * @method public
	 * @return number Maximum value or <code>null</code>.
	 */
	f_getStep: function() {
		var step=this._step;
		if (step===undefined) {
			this._step=f_core.GetAttribute(this, "v:step");
		}
		return step;
	},
	/*
	 * @method public
	 * @param number step Step value.
	 * @return void
	 *
	f_setStep: function(step) {
		f_core.Assert(typeof(step)=="number", "Invalid parameter '"+step+"'.");
		
		if (step==this.f_getStep()) {
			return;
		}
		
		this._step=step;
		if (!this._dontUpdateSpinnerParameter) {
			this.f_setProperty(f_prop.STEP, step);
		}
	},
	*/
	/**
	 * @method protected
	 */
	f_updateDisabled: {
		before: function(disabled) {
			if (!this.fa_componentUpdated) {
				return;
			}

			var up=this._spinnerUp;
			if (up) {
				this.f_updateSpinnerButton(up);
			}
			var down=this._spinnerDown;
			if (down) {
				this.f_updateSpinnerButton(down);
			}
		}
	}
}

new f_aspect("fa_spinner", __static, __prototype);
