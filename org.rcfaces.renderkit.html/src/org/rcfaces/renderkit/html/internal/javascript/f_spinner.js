/*
 * $Id$
 */
 
/**
 * f_spinner class
 *
 * @class f_spinner extends f_textEntry, fa_spinner
 */
var __prototype = {
	f_spinner: function() {
		this.f_super(arguments);
		
		this._className=this.className;
	},
	/*
	f_finalize: function() {
		
		// this._className=undefined; // string
		
		this.f_super(arguments);
	},
	*/
	/**
	 * @method protected
	 */
	f_performStep: function(scale, jsEvent) {
		if (this.f_isReadOnly()) {
			return;
		}
		var value=this.f_getValue();
		
		if (typeof(value)!="number") {
			value=parseFloat(this.f_getText());
		}

		if (isNaN(value)) {
			return;
		}

		var newValue=value;
		
		var step=this.f_getStep();
		if (!step) {
			newValue+=scale;

		} else if (step.charAt(0)=="%") {
			newValue=Math.floor(newValue);
			var s=parseFloat(step.substring(1));
			
			if ((newValue % s)==0) {
				newValue+=scale*s;
				
			} else {
				newValue+=((scale>0)?s:0)-(newValue % s);
			}
		
		} else {
			var s=parseFloat(step);
			if (!isNan(s)) {
				newValue+=scale*s;
			}
		}
		
		var max=this.f_getMaximum();
		var min=this.f_getMinimum();

		if (typeof(max)=="number" && max<newValue) {
			if (this._cycleValue && typeof(min)=="number") {
				newValue=min;
			} else {
				newValue=max;
			}	
		}
		if (typeof(min)=="number" && min>newValue) {
			if (this._cycleValue && typeof(max)=="number") {
				newValue=max;
			} else {
				newValue=min;
			}
		}
				
		this.f_setValue(newValue);
	}
}

var f_spinner = new f_class("f_spinner", null, null, __prototype, f_textEntry, fa_spinner);