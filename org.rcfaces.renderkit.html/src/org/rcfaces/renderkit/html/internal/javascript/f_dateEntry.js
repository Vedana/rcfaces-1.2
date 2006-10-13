/*
 * $Id$
 */

/**
 * 
 * @class public f_dateEntry extends f_component, fa_calendarPopup, fa_compositeEntry, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype={

	f_dateEntry: function() {
		this.f_super(arguments);

		this._dateFormat=f_core.GetAttribute(this, "v:dateFormat");

		this._showOnFocus=f_core.GetAttribute(this, "v:showOnFocus");

		this._autoCompletion=f_core.GetAttribute(this, "v:autoCompletion");
		
		var twoDigitYearStart=f_core.GetAttribute(this, "v:twoDigitYearStart");
		if (twoDigitYearStart) {
			this._twoDigitYearStart=f_dateFormat.ParseStringDate(twoDigitYearStart);
		}
		
		var validatorParams=f_core.GetAttribute(this, "v:clientValidatorParams");
		if (validatorParams) {
			this._validatorParams=f_core.ParseParameters(validatorParams);
		}
		
		if (window.f_messageContext) {
			f_core.AddCheckListener(this, this);	
		}
	},
	/*
	f_finalize: function() {
		// this._twoDigitYearStart=undefined;  // Date
		// this._dateFormat=undefined; // String
		// this._showOnFocus=undefined; // boolean
		// this._validatorParams=undefined; // Map<String, String>
		
		this.f_super(arguments);
	},
	*/

	/**
	 * @method public
	 * @return Date
	 */
	f_getMinDate: function() {
		return this.f_getDate(fa_compositeEntry.MIN_TYPE);
	},

	/**
	 * @method public
	 * @return Date
	 */
	f_getMaxDate: function() {
		return this.f_getDate(fa_compositeEntry.MAX_TYPE);
	},
	/**
	 * @method public
	 * @return any
	 */
	f_getValue: function() {
		return this.f_getDate();
	},
	/**
	 * @method public
	 * @param optional hidden number dateType Type of date. (min, max, default)
	 * @return Date
	 */
	f_getDate: function(dateType) {		
		var year=-1;
		var month=1;
		var date=1;
		
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
				if (isNaN(v)) {
					v=-1;
				}
			}
			if (v===undefined) {
				v=-1;
			}
						
			switch(type) {
			case "d":
				date=v
				break;
				
			case "M":
				month=v;
				break;
				
			case "y":
				year=v;
				break;
			}
		}
		
		f_core.Debug(f_dateEntry, "Parse Date: year="+year+" month="+month+" date="+date);
		
		if (year<0 || month<1 || date<1) {
			return null;
		}
			
		if (year<100) {
			year=f_dateFormat.ResolveYear(year, month, date, this._twoDigitYearStart);
		}
		
		var d=new Date(year, month-1, date);
		
		if (d.getDate()!=date || d.getMonth()!=month-1  || d.getFullYear()!=year) {
			return null;
		}
		
		return d;
	},
	/**
	 * @method public
	 * @param Object value
	 * @return void
	 */
	f_setValue: function(value) {
		this.f_setDate(value);
	},
	/**
	 * @method public
	 * @param Date date
	 * @return void
	 */
	f_setDate: function(date) {
		f_core.Assert(date===nulll || (date instanceof Date), "Invalid date parameter '"+date+"'");
		
		var inputs=this._inputs;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			
			if (!date) {
				input.value="";
				continue;
			}
			
			var type=input._type;
			var maxLength=parseInt(input.maxLength);
			
			var v=-1;
			switch(type) {
			case "d":
				v=date.getDate();
				break;
				
			case "M":
				v=date.getMonth()+1;
				break;
				
			case "y":
				v=date.getFullYear();
				
				if (maxLength==2) {
					v%=100;
				}
				
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
		var date=this.f_getDate();
		
		var d=(date instanceof Date)?f_dateFormat.FormatStringDate(date):null;
		
		this.f_setProperty(f_prop.VALUE, d);

		this.f_super(arguments);
	},
	f_performCheckValue: function() {		
		var messageContext=f_messageContext.Get(this);
		if (!messageContext) {
			return;
		}

		var errorMessage=null;
			
		var date=this.f_getDate();
		
		f_core.Debug(f_dateEntry, "Date: "+date);
		if (!date) {
			if (!this.f_isRequired()) {
				// Si c'est pas requis, on ne rale que si un des champs est rempli
				var empty=true;
				var inputs=this._inputs;
				for(var i=0;i<inputs.length;i++) {
					if (inputs[i].value.length<1) {
						continue;
					}
					
					empty=false;
					break;
				}
				
				if (empty) {
					// Tous les champs sont vides
					return;
				}

				errorMessage="invalidDate.error";
				
			} else {
				errorMessage="required.error";
			}
			
			// Le champ n'est pas requis, mais un des champs n'est pas vide !
			// ou le champ est requis et la date est invalide 

		} else {
			// La date est valide !
			var t=date.getTime();
			
			var d2=new Date(t);
			
			if (d2.getDate()!=date.getDate() || d2.getMonth()!=date.getMonth() || d2.getFullYear()!=date.getFullYear()) {
				errorMessage="invalidDate.error";
				
			} else {			
				var minDate=this.f_getMinDate();
				var maxDate=this.f_getMaxDate();
				
				if (minDate && t<minDate.getTime()) {
					errorMessage="minDate.error";
	
				} else if (maxDate && t>maxDate.getTime()) {
					errorMessage="maxDate.error";
				}

				f_core.Debug(f_dateEntry, "Text Min/max : Error Message: "+errorMessage+" date="+date+" dateMin="+minDate+" dateMax="+maxDate);
			}
		}
		
		f_core.Debug(f_dateEntry, "Error Message: "+errorMessage+" date="+date);
		
		if (!errorMessage) {
			return;
		}
		
		var summary=null;
		var detail=null;
		var severity=f_messageObject.SEVERITY_ERROR;
		
		var validatorParams=this._validatorParams;
		if (validatorParams) {
			summary=validatorParams[errorMessage+".summary"];
			if (summary) {
				detail=validatorParams[errorMessage+".detail"];
			} else {
				summary=validatorParams[errorMessage];
			}			
		}
		
		if (!summary) {
			var resourceBundle=f_resourceBundle.Get(f_dateEntry);
			
			summary=resourceBundle.f_get(errorMessage.toUpperCase().replace(/\./g, "_")+"_SUMMARY");
		}

		var message=new f_messageObject(severity, summary, detail);
		messageContext.f_addMessageObject(this, message);
		
		return false;
	}
}
 
var f_dateEntry=new f_class("f_dateEntry", null, null, __prototype, f_component, fa_calendarPopup, fa_compositeEntry, fa_required);
