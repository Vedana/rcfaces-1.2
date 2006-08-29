/*
 * $Id$
 */

/**
 * 
 * @class public f_dateEntry extends f_component, fa_calendarPopup
 * @author Olivier Oeuillot
 * @version $Revision$
 */
 
var __static={

	/**
	 * @field private static final string
	 */
	_MIN_DATE_TYPE: "min",

	/**
	 * @field private static final string
	 */
	_MAX_DATE_TYPE: "max",

	/**
	 * @field private static final string
	 */
	_DEFAULT_DATE_TYPE: "default",
	
	
	/**
	 * @method private static
	 */
	_OnInputKeyDown: function(evt) {
		var dateEntry=this._dateEntry;
		if (dateEntry.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		if (dateEntry.f_isDisabled() || dateEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return dateEntry._onInputKeyDown(evt, this);		
	},
	
	/**
	 * @method private static
	 */
	_OnInputKeyPress: function(evt) {
		var dateEntry=this._dateEntry;
		if (dateEntry.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (dateEntry.f_isDisabled() || dateEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return dateEntry._onInputKeyPress(evt, this);		
	},
	
	/**
	 * @method private static
	 */
	_OnInputBlur: function(evt) {
		var dateEntry=this._dateEntry;
		if (dateEntry.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (dateEntry.f_isDisabled() || dateEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return dateEntry._onInputBlur(this, evt);		
	},
	
	/**
	 * @method private static
	 */
	_FormatNumber: function(number, size) {
		var s=String(number);		
		for(size-=s.length;size;size--) {
			s="0"+s;
		}
		return s;
	}
}

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
		
		
		var inputs=this.getElementsByTagName("INPUT");
		f_core.Assert(inputs.length>0, "f_dateEntry(): Can not find any Input !");
		
		this._inputs=inputs;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			
			var min=f_core.GetAttribute(input, "v:min");
			if (min) {
				input._min=parseInt(min);
			}
			var max=f_core.GetAttribute(input, "v:max");
			if (max) {
				input._max=parseInt(max);
			}
			var defaultValue=f_core.GetAttribute(input, "v:default");
			if (defaultValue) {
				input._default=parseInt(defaultValue);
			}
			
			var separators=f_core.GetAttribute(input, "v:separators");
			if (separators) {
				// Ce sont les separateurs du precedant !
				inputs[i-1]._separators=separators;
			}
			
			input._type=f_core.GetAttribute(input, "v:type");
			
			if (i+1<inputs.length) {
				input._nextInput=inputs[i+1];
				inputs[i+1]._predInput=input;
			}
			
			input._dateEntry=this;
			input.onkeypress=f_dateEntry._OnInputKeyPress;
			input.onblur=f_dateEntry._OnInputBlur;
			input.onkeydown=f_dateEntry._OnInputKeyDown;
		}
				
		f_core.AddCheckListener(this, this);	
	},
	f_finalize: function() {
		// this._twoDigitYearStart=undefined;  // Date
		// this._dateFormat=undefined; // String
		// this._showOnFocus=undefined; // boolean
		// this._validatorParams=undefined; // Map<String, String>
		
		var inputs=this._inputs;
		if (inputs) {
			this._inputs=undefined; // HtmlInputElement[]
			
			for(var i=0;i<inputs.length;i++) {
				var input=inputs[i];
				
				// input._min=undefined; // number
				// input._max=undefined; // number
				// input._default=undefined; // number
				// input._separators=undefined; // string
				// input._type=undefined; // string
				
				input._nextInput=undefined; // HtmlInputElement
				input._predInput=undefined; // HtmlInputElement
				input._dateEntry=undefined; // f_dateEntry
				
				input.onkeypress=null;
				input.onkeydown=null;
				input.onblur=null;
				
				f_core.VerifyProperties(input);
			}
		}
		
		this.f_super(arguments);
	},
	_onInputKeyDown: function(jsEvent, input) {
		// permet de capter le TAB sous IE !
		var keyCode = jsEvent.keyCode;

		var sel=f_core.GetTextSelection(input);
		f_core.Debug(f_dateEntry, "KeyDown '"+keyCode+"' sel0="+sel[0]+" sel1="+sel[1]);


		switch(keyCode) {
		case f_key.VK_LEFT:
		case f_key.VK_HOME:
		case f_key.VK_BACK_SPACE:
			if (!sel[0] && !sel[1]) {
				var predInput=input._predInput;
				if (predInput) {
					if (keyCode==f_key.VK_HOME) {
						for(;predInput._predInput;predInput=predInput._predInput);
						
						f_core.SelectText(predInput, 0, 0);
					} else if (keyCode==f_key.VK_LEFT && jsEvent.ctrlKey) {
						f_core.SelectText(predInput, 0, 0);
					}

					predInput.focus();
				}
				
				return  false;				
			}
			break;
			
		case f_key.VK_END:
		case f_key.VK_RIGHT:
			if (sel[0]==sel[1] && sel[0]==input.value.length) {
				var nextInput=input._nextInput;
				
				if (nextInput) {
					if (keyCode==f_key.VK_END) {
						for(;nextInput._nextInput;nextInput=nextInput._nextInput);

						var vnextInputLength=nextInput.value.length;
						f_core.SelectText(nextInput, vnextInputLength, 0);

					} else if (keyCode==f_key.VK_RIGHT && jsEvent.ctrlKey) {
						var vnextInputLength=nextInput.value.length;
						f_core.SelectText(nextInput, vnextInputLength, 0);
					}
					
					nextInput.focus();
				}
				
				return  false;				
			}
			break;
		}		
		
		return true;
	},
	_onInputKeyPress: function(jsEvent, input) {
		var keyCode = jsEvent.keyCode;
		var charCode = jsEvent.charCode;
		
		var keyChar;
		
		if (!charCode) {
			keyChar = String.fromCharCode(keyCode);

		} else {
			keyChar = String.fromCharCode(charCode);
		}
				
		f_core.Debug(f_dateEntry, "KeyPress: keyCode="+keyCode+" charCode="+charCode+" shift="+jsEvent.shift+" ctrl="+jsEvent.ctrl+" alt="+jsEvent.alt+" keyChar="+keyChar+"("+((keyChar.length>0)?keyChar.charCodeAt(0):"")+") min="+input._min+" max="+input._max+" default="+input._default);

		if (keyCode==f_key.VK_TAB) {
			// Deja traité .. normalement !
			return true;
		}
	
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
		
		// charCode=String.fromCharCode(keyCode);
		f_core.Debug(f_dateEntry, "KeyPress: keyCode="+keyCode+" keyChar='"+keyChar+"' separators="+input._separators);
		
		var ret=false;
		if (keyChar>='0' && keyChar<='9') {
			// Un nombre
			ret=this._onDigitPressed(input, keyChar, jsEvent);

		} else {
			// Un separateur ou TAB ?
			var separators=input._separators;
			if (separators && separators.indexOf(keyChar)>=0) {
				// Un séparateur ou TAB !
				ret=this._onSeparatorPressed(input, true, jsEvent);
			}
		}
		
		if (ret) {
			return true;
		}
		return f_core.CancelEvent(jsEvent);
	},
	_onInputBlur: function(input, jsEvent) {
		var inputValue=input.value;
		var maxLength=parseInt(input.maxLength);
		
		if (inputValue.length==maxLength || !inputValue.length) {
			return true;
		}
		
		var fv=parseInt(inputValue, 10);
		var min=input._min;
		var max=input._max;

		if ((min!==undefined && min>fv) || (max!==undefined && max<fv)) {
			return true;
		}
		
		var v=f_dateEntry._FormatNumber(inputValue, maxLength);
		if (v!=input.value) {
			input.value=v;
		}
		
		return true;
	},
	_onDigitPressed: function(input, keyChar, jsEvent, fill) {
		f_core.Debug(f_dateEntry, "_onDigitPressed on input '"+input.id+"' keyChar="+keyChar);
		
		var sel=f_core.GetTextSelection(input);

		var inputValue=input.value;
		var maxLength=parseInt(input.maxLength);
		if (inputValue.length==maxLength) {
			if (sel[0]==maxLength) {
				// Balance le chiffre sur l'autre champ !
				
				var nextInput=input._nextInput;
				if (nextInput) {
					// On efface le champ suivant
					nextInput.value="";			
					
					// On lui donne le focus		
					nextInput.focus();
					
					// On simule l'appuie de la touche
					this._onDigitPressed(nextInput, keyChar, jsEvent, true);
					
					return false;
				}
				
				// On est à la fin ... y a plus rien à saisir
				return false;
			}
			
			if (sel[0]!=0 || sel[1]!=maxLength) {
				// On est au milieu du champ: Normalement ca bloque !
				return true;
			}
		}

		var futureValue=inputValue.substring(0, sel[0])+keyChar+inputValue.substring(sel[1]);
		if (this._autoCompletion) {
			// Si on ajoute à la fin !
			var fv=parseInt(futureValue, 10);
			
			var min=input._min;
			var max=input._max;
		
			f_core.Debug(f_dateEntry, "Supposed value '"+futureValue+"' int="+fv+" min="+min+" max="+max);
	
			if ((fv || futureValue.length==maxLength) 
					&& ((min!==undefined && min>fv && futureValue.length==maxLength)  // On ne peut pas determiner le min si le champ n'est pas complet !
						|| (max!==undefined && max<fv))) {
				if (sel[1]!=inputValue.length) {
					// On insere au milieu et y a un probleme: on refuse la touche
					return false;
				}
				
				// probleme !
				// Si le champ est vide : on prend le defaut !
				if (inputValue.length==0) {
					var defaultValue=input._default;
					if (defaultValue===undefined) {
						// meme pas de valeur par defaut
						
						return false; // on refuse
					}
				
					futureValue=defaultValue;
					fill=true;
					
				} else if (!fv) {
					// Le champ est rempli de zero !
					return false;
					
				} else if (min===undefined || min<fv) {
					// Le champ n'est pas vide .. mais la valeur précédente etait bonne !
					// On la conserve ... (on formate au passage)
					
					var v=f_dateEntry._FormatNumber(inputValue, maxLength);
					if (v!=input.value) {
						input.value=v;
					}
					
					// ... et on passe la touche à l'input suivant !
					
					var nextInput=input._nextInput;
					if (nextInput) {
						// On efface le champ suivant
						nextInput.value="";			
						
						// On lui donne le focus		
						nextInput.focus();
						
						// On simule l'appuie de la touche
						this._onDigitPressed(nextInput, keyChar, jsEvent, true);
						
						return false;
					}
					
					// On est à la fin ... y a plus rien à saisir
					return false;
							
				} else {
					// Le nombre précédent n'est pas acceptable !
					return false;
				}
			}
			
			// Maintenant on recherche si on peut predire les valeurs suivantes !
			// On ne fait ca que si le curseur est à la fin !
			if (sel[1]==inputValue.length) {
				fv=parseInt(futureValue, 10); // On recalcule, car futureValue a pu changer !
				
				var diff=maxLength-futureValue.length;
				
				if (min!==undefined && max!==undefined) {
					for(;diff;diff--) {
						if (max>=(fv*10) && min<=(fv*10+9)) {
							// Le chiffre suivant est possible !		
							break;
						}
						
						fv*=10;
						futureValue="0"+futureValue;
						fill=true;
					}
				}
				
				f_core.Debug(f_dateEntry, " Diff="+diff+" fv="+fv);
				
				if (!diff) {
					// Il est complet !
					// On passe au suivant si possible !
					input.value=futureValue;
	
					var nextInput=input._nextInput;
					if (nextInput) {
						// On lui donne le focus		
						nextInput.focus();
						f_core.SelectText(nextInput, 0, nextInput.value.length);
							
						return false;
					}
					
					// On est à la fin ... y a plus rien à saisir
					return false;				
				}
			}
		}
		
		if (fill) {
			input.value=futureValue;
		}
		
		return true;
	},
	_onSeparatorPressed: function(input, separator, jsEvent) {
		f_core.Debug(f_dateEntry, "_onSeparatorPressed on input '"+input.id+"' separator="+separator);
		
		var inputValue=input.value;
		if (inputValue.length==0) {
			// Aucune saisie
			
			var defaultValue=input._default;
			if (defaultValue===undefined) {
				// meme pas de valeur par defaut
				
				return false; // on refuse
			}
			
			inputValue=defaultValue;
		}
		
		var maxLength=parseInt(input.maxLength);
		
		var v=f_dateEntry._FormatNumber(inputValue, maxLength);
		if (v!=input.value) {
			input.value=v;
		}
		
		if (separator) {
			var nextInput=input._nextInput;
			if (nextInput) {
				nextInput.focus();
			}

			// De toute facon on refuse la saisie du séparateur
			return false;
		}
		
		// C'est un TAB, on laisse faire ....
		return true;
	},
	_a_updateReadOnly: function() {
		var inputs=this._inputs;
		var readOnly=this._readOnly;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
		
			input.readOnly=readOnly;
		}
	},
	_a_updateDisabled: function() {
		var inputs=this._inputs;
		var disabled=this._disabled;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
		
			input.disabled=disabled;
		}
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
			case f_dateEntry._MIN_DATE_TYPE:
				v=min;
				break;
				
			case f_dateEntry._MAX_DATE_TYPE:
				v=max;
				break;
				
			case f_dateEntry._DEFAULT_DATE_TYPE:
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
	 * @param Date date
	 * @return void
	 */
	f_setDate: function(date) {
		var inputs=this._inputs;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
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
			
			v=f_dateEntry._FormatNumber(v, maxLength);
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
		var errorMessage=null;
		
		var date=this.f_getDate();
		if (!date) {
			if (!this.f_isRequired()) {
				// Si c'est pas requis, on ne rale que si un des champs en rempli
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
			}
			
			// Le champ n'est pas requis, mais un des champs n'est pas vide !
			// ou le champ est requis et la date est invalide 

			errorMessage="required.error";

		} else {
			// La date est valide !
			var t=date.getTime();
					
			var minDate=this.f_getMinDate(); // XXX poursuivre ...
			var maxDate=this.f_getMaxDate();
			
			if (minDate && t<minDate.getTime()) {
				errorMessage="minDate.error";

			} else if (maxDate && t>maxDate.getTime()) {
				errorMessage="maxDate.error";
				
			}
		}
		
		alert("ErrorMessage="+errorMessage);
		
		if (!errorMessage) {
			return;
		}
		
		alert("Error: "+errorMessage);

		var validatorParams=this._validatorParams;
		if (validatorParams) {
//			validatorParams[errorMessage+".error"
		}		
		
		
	}
}
 
var f_dateEntry=new f_class("f_dateEntry", null, __static, __prototype, f_component, fa_calendarPopup, fa_disabled, fa_readOnly);
