/*
 * $Id$
 */
 
/**
 * Aspect Composite entry
 *
 * @aspect hidden fa_compositeEntry extends fa_disabled, fa_readOnly
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static={	

	/**
	 * @field protected static final string
	 */
	MIN_TYPE: "min",

	/**
	 * @field protected static final string
	 */
	MAX_TYPE: "max",

	/**
	 * @field protected static final string
	 */
	DEFAULT_TYPE: "default",
	
	/**
	 * @method private static
	 */
	_OnInputKeyDown: function(evt) {
		var compositeEntry=this._compositeEntry;
		if (compositeEntry.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = window.event;

		if (compositeEntry.f_isDisabled() || compositeEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return compositeEntry._onInputKeyDown(evt, this);		
	},
	
	/**
	 * @method private static
	 */
	_OnInputKeyPress: function(evt) {
		var compositeEntry=this._compositeEntry;
		if (compositeEntry.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (compositeEntry.f_isDisabled() || compositeEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return compositeEntry._onInputKeyPress(evt, this);		
	},
	
	/**
	 * @method private static
	 */
	_OnInputBlur: function(evt) {
		var compositeEntry=this._compositeEntry;
		if (compositeEntry.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (compositeEntry.f_isDisabled() || compositeEntry.f_isReadOnly()) {
			return f_core.CancelEvent(evt);
		}
	
		return compositeEntry._onInputBlur(this, evt);		
	},
	
	/**
	 * @method protected static
	 */
	FormatNumber: function(number, size) {
		if (typeof(size)=="string") {
			size=parseInt(size, 10);
		}
		var s=String(number);		
		for(size-=s.length;size;size--) {
			s="0"+s;
		}
		return s;
	}
}

var __prototype={

	fa_compositeEntry: function() {
		var inputs=this.getElementsByTagName("INPUT");
		f_core.Assert(inputs.length>0, "fa_compositeEntry(): Can not find any Input !");
		
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
			
			input._compositeEntry=this;
			input.onkeypress=fa_compositeEntry._OnInputKeyPress;
			input.onblur=fa_compositeEntry._OnInputBlur;
			input.onkeydown=fa_compositeEntry._OnInputKeyDown;
		}
	},
	f_finalize: function() {		
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
				input._compositeEntry=undefined; // fa_compositeEntry
				
				input.onkeypress=null; // function
				input.onkeydown=null; // function
				input.onblur=null; // function
				
				f_core.VerifyProperties(input);
			}
		}
	},
	_onInputKeyDown: function(jsEvent, input) {
		// permet de capter le TAB sous IE !
		var keyCode = jsEvent.keyCode;

		var sel=f_core.GetTextSelection(input);
		f_core.Debug(fa_compositeEntry, "KeyDown '"+keyCode+"' sel0="+sel[0]+" sel1="+sel[1]);

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
			
		case f_key.VK_UP:
			var inputValue=input.value;

			var fv=parseInt(inputValue, 10);
			var max=input._max;
			if (!isNaN(fv) && (max!==undefined && max>fv)) {
				input.value=fa_compositeEntry.FormatNumber(fv+1, input.maxLength);
				f_core.SelectText(input, 0, input.maxLength);
			
				return false;
			}
			break;
			
		case f_key.VK_PAGE_UP:
			var inputValue=input.value;

			var fv=parseInt(inputValue, 10);
			var max=input._max;
			if (!isNaN(fv) && max!==undefined && max>fv) {
				input.value=fa_compositeEntry.FormatNumber(max, input.maxLength);
				f_core.SelectText(input, 0, input.maxLength);
			
				return false;
			}
			break;
			
		case f_key.VK_PAGE_DOWN:
			var inputValue=input.value;

			var fv=parseInt(inputValue, 10);
			var min=input._min;
			if (!isNaN(fv) && min!==undefined && min<fv) {
				input.value=fa_compositeEntry.FormatNumber(min, input.maxLength);
				f_core.SelectText(input, 0, input.maxLength);
							
				return false;
			}
			break;

		case f_key.VK_DOWN:
			var inputValue=input.value;

			var fv=parseInt(inputValue, 10);
			var min=input._min;
			if (!isNaN(fv) && (min!==undefined && min<fv)) {
				input.value=fa_compositeEntry.FormatNumber(fv-1, input.maxLength);
				f_core.SelectText(input, 0, input.maxLength);
			
				return false;
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
				
		f_core.Debug(fa_compositeEntry, "KeyPress: keyCode="+keyCode+" charCode="+charCode+" shift="+jsEvent.shift+" ctrl="+jsEvent.ctrl+" alt="+jsEvent.alt+" keyChar="+keyChar+"("+((keyChar.length>0)?keyChar.charCodeAt(0):"")+") min="+input._min+" max="+input._max+" default="+input._default);

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
		f_core.Debug(fa_compositeEntry, "KeyPress: keyCode="+keyCode+" keyChar='"+keyChar+"' separators="+input._separators);
		
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
		
		var v=fa_compositeEntry.FormatNumber(inputValue, maxLength);
		if (v!=input.value) {
			input.value=v;
		}
		
		return true;
	},
	_onDigitPressed: function(input, keyChar, jsEvent, fill) {
		f_core.Debug(fa_compositeEntry, "_onDigitPressed on input '"+input.id+"' keyChar="+keyChar);
		
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
		if (this._autoCompletion && maxLength==2) {
			// Si on ajoute à la fin !
			var fv=parseInt(futureValue, 10);
			
			var min=input._min;
			var max=input._max;
		
			f_core.Debug(fa_compositeEntry, "Supposed value '"+futureValue+"' int="+fv+" min="+min+" max="+max);
	
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
					
					var v=fa_compositeEntry.FormatNumber(inputValue, maxLength);
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
				
				f_core.Debug(fa_compositeEntry, " Diff="+diff+" fv="+fv);
				
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
		f_core.Debug(fa_compositeEntry, "_onSeparatorPressed on input '"+input.id+"' separator="+separator);
		
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
		
		var v=fa_compositeEntry.FormatNumber(inputValue, maxLength);
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
	fa_updateReadOnly: function() {
		var inputs=this._inputs;
		var readOnly=this._readOnly;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
		
			input.readOnly=readOnly;
		}
	},
	fa_updateDisabled: function() {
		var inputs=this._inputs;
		var disabled=this._disabled;
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
		
			input.disabled=disabled;
		}
	}
}
 
var fa_compositeEntry=new f_aspect("fa_compositeEntry", __static, __prototype, fa_disabled, fa_readOnly);
