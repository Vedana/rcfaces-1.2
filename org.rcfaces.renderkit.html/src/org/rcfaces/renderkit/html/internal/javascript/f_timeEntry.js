/*
 * $Id$
 */

/**
 * 
 * @class public f_timeEntry extends f_component, fa_compositeEntry, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype={

	f_timeEntry: function() {
		this.f_super(arguments);

		this._timeFormat=f_core.GetAttribute(this, "v:timeFormat");
		
		var validatorParams=f_core.GetAttribute(this, "v:clientValidatorParams");
		if (validatorParams) {
			this._validatorParams=f_core.ParseParameters(validatorParams);
		}
				
		f_core.AddCheckListener(this, this);	
	},
	/*
	f_finalize: function() {
		// this._timeFormat=undefined; // String
		// this._validatorParams=undefined; // Map<String, String>
		
		this.f_super(arguments);
	},
	*/

	/**
	 * @method public
	 * @param optional hidden number timeType Type of time. (min, max, default)
	 * @return number
	 */
	f_getTime: function(timeType) {
		var hour=-1;
		var minute=0;
		var second=0;
		var millis=0;
		
		var inputs=this._inputs;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			var type=input._type;
			
			var min=input._min;
			var max=input._max;
			
			var v;
			switch(dateType) {
			case fa_compositeEntry.MIN_TYPE:
				v=min;
				break;
				
			case fa_compositeEntry.MAX_TYPE:
				v=max;
				break;
				
			case fa_compositeEntry.DEFAULT_TYPE:
				v=input._default;
				break;

			default:
				v=parseInt(input.value, 10);
				if (isNaN(v) || (min!==undefined && v<min) || (max!==undefined && v>max)) {
					v=-1;
				}
			}
			if (v===undefined) {
				v=-1;
			}
						
			switch(type) {
			case "H":
				hour=v
				break;
				
			case "m":
				minute=v;
				break;
				
			case "s":
				second=v;
				break;

			case "S":
				millis=v;
				break;
			}
		}
		
		if (hour<0) {
			return null;
		}
		
		var t=((((hour*60)+minute)*60)+seconde)*1000+millis;
		
		return t;
	},
	/**
	 * @method public
	 * @param number time
	 * @return void
	 */
	f_setDate: function(time) {
		var inputs=this._inputs;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			var type=input._type;
			var maxLength=parseInt(input.maxLength);
			
			var v=-1;
			switch(type) {
			case "H":
				v=Math.floor(time/(60*60*1000));
				break;
				
			case "m":
				v=(Math.floor(time/(60*1000)) % 60);
				break;
				
			case "s":
				v=(Math.floor(time/1000) % 60);				
				break;

			case "S":
				v=time % 1000;				
				break;
			}
			if (v<0) {
				continue;
			}
			
			v=fa_compositeEntry.FormatNumber(v, maxLength);
			if (v!=input.value) {
				input.value=v;
			}
		}
	},
	f_serialize: function() {
		var time=this.f_getTime();
		
		this.f_setProperty(f_prop.VALUE, time);

		this.f_super(arguments);
	},
	f_performCheckValue: function() {		
		var errorMessage=null;
		
		return true;
	}
}
 
var f_timeEntry=new f_class("f_timeEntry", null, null, __prototype, f_component, fa_compositeEntry, fa_required);
