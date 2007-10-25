/*
 * $Id$
 */

/**
 * F_VALID_BASIC package
 *
 * @class f_vb extends Object
 */
/*=============================================================================
	FILTERS in alphabetic order...please
=============================================================================*/


var __statics = {
	/**
	 * @field private static final String
	 */
	_LATIN_ACCENT_FR: "àäâéèëêïîöôùüûÿçÀÄÂÉÈËÊÏÎÖÔÙÜÛÇ",

	/**
	 * @field private static final String
	 */
	_LATIN_ACCENT: "áãàâäåéèêëíìîïóõòôöúùûüµýÿçñÀÁÂÃÄÅÈÉÊËÌÍÎÏÓÔÕÖÒÙÚÛÜÝÇÑ",

	/**
	 * @field private static final Array
	 */
	_ACCENTS_MAPPER: [
		/[áãàâäå]/,97,/[éèêë]/,101,/[íìîï]/,105,/[ñ]/,110,/[óõòôö]/,
		111,/[úùûüµ]/,117,/[ç]/, 99,/[ýÿ]/,121,/[ÀÁÂÃÄÅ]/,65,/[ÈÉÊË]/,
		69,/[ÌÍÎÏ]/,73,/[Ñ]/,78,/[ÓÔÕÖÒ]/,79,/[ÙÚÛÜ]/,85,/[Ç]/,67,/[Ý]/,89
		],

	/**
	 * @field private static final RegExp
	 */
	_TRANSLATOR_UPPERCASE: /[áãàâäåçéèêëíìîïñóõòôöúùûüý]/,

	/**
	 * @field private static final RegExp
	 */
	_TRANSLATOR_LOWERCASE: /[ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÑÓÔÕÖÒÙÚÛÜÝ]/,

	/**
	 * @field private static final RegExp
	 */
	_ESCAPE_REGEXP: /([\\\/\.\*\+\?\|\(\)\[\]\{\}\-\^])/g,
	
	/**
	 * @method private static
	 *
	 * There are a few reserved chars in regular expressions.
	 * Handle string encoding with a powerfull regular expression.
	 * Reserved chars are the following set:
	 *	\/.*+?|()[]{}-^
	 */
	_BuildEscaped: function(str) {
		if (!str) {
			return "";
		}
		return str.replace(f_vb._ESCAPE_REGEXP, "\\$1");
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_alpha: function(validator, keyCode, keyChar) {
		var exp = "[a-zA-Z";
		var sup = validator.f_getParameter("alpha.otherChars");
		exp += f_vb._BuildEscaped(sup)+"]";
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_alpha_fr: function(validator, keyCode, keyChar) {
		var exp = "[a-zA-Z" + f_vb._LATIN_ACCENT_FR;
		var sup = validator.f_getParameter("alpha.otherChars");
		exp += f_vb._BuildEscaped(sup)+"]";
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_alphanum: function(validator, keyCode, keyChar) {
		var exp = "[0-9a-zA-Z";
		var sup = validator.f_getParameter("alpha.otherChars");
		exp += f_vb._BuildEscaped(sup)+"]";
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_card: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\.]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_code: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9a-zA-Z]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_dat: function(validator, keyCode, keyChar) {
		var exp = "[0-9";
		var sup = validator.f_getParameter("date.sepSign");
		exp += (f_vb._BuildEscaped(sup) + "]");
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_date: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\/]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_digit: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_dps: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[\040-\177]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_hour: function(validator, keyCode, keyChar) {
		var exp = "[0-9";
		var sup = validator.f_getParameter("hour.sepSign");
		exp += (f_vb._BuildEscaped(sup) + "]");
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_insee: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9aAbB]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_integer: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_money: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\.\+\-]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_name: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[ a-zA-Z0-9\*\.\-]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_noblank: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[^ ]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_num: function(validator, keyCode, keyChar) {
		var exp = "[0-9";
		var sup = "";
		if (validator.f_getBoolParameter("num.signed", false)) {
			sup = validator.f_getParameter("num.negSign", "-");
			exp += f_vb._BuildEscaped(sup);
		}
		if (validator.f_getIntParameter("num.decimal", -1) !== 0) {
			sup = validator.f_getParameter("num.decSign");
			exp += f_vb._BuildEscaped(sup);
		}
	   	sup = validator.f_getParameter("num.sepSign");
		exp += (f_vb._BuildEscaped(sup) + "]");
		return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_number: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\,\-]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_signed: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\-]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_scientific: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\.\-eE]/, keyCode, keyChar);
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Filter_time: function(validator, keyCode, keyChar) {
		return f_clientValidator.Filter_generic(validator, /[0-9\:]/, keyCode, keyChar);
	},

/*=============================================================================
	TRANSLATORS in alphabetic order...please
=============================================================================*/

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_date: function(validator, keyCode, keyChar) {
		var set = validator.f_getParameter("date.sepSign");
		if (set && (set.length > 1) && (set.indexOf(keyChar) != -1)) {
			return set.charCodeAt(0);
		}
		return keyCode;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_hour: function(validator, keyCode, keyChar) {
		var set = validator.f_getParameter("hour.sepSign");
		if (set && (set.length > 1) && (set.indexOf(keyChar) >= 0)) {
			return set.charCodeAt(0);
		}
		return keyCode;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_num: function(validator, keyCode, keyChar) {
		var set = validator.f_getParameter("num.decSign");
		if (set && (set.length > 1) && (set.indexOf(keyChar) >= 0)) {
			return set.charCodeAt(0);
		}
		return keyCode;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_removeaccent: function(validator, keyCode, keyChar) {
		var mapper=f_vb._ACCENTS_MAPPER;
		for (var i=0; i<mapper.length;) {
			var expr = mapper[i++];
			var code = mapper[i++];
			if (expr.test(keyChar)) {
				return code;
			}
		}
		return keyCode;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_uppercase: function(validator, keyCode, keyChar) {
		if (keyCode >= 97 && keyCode <= 122) {
			return (keyCode - 32);
		}
		if (keyCode > 127 && f_vb._TRANSLATOR_UPPERCASE.test(keyChar)) {
			return (keyCode - 32);
		}
		return keyCode;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Translator_lowercase: function(validator, keyCode, keyChar) {
		if (keyCode >= 65 && keyCode <= 90) {
			return (keyCode + 32);
		}
		if (keyCode > 127 && f_vb._TRANSLATOR_LOWERCASE.test(keyChar)) {
			return (keyCode + 32);
		}
		return keyCode;
	},

/*=============================================================================
	CHECKERS in alphabetic order...please
=============================================================================*/

	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_dat: function(validator, inVal) {
		var min = validator.f_getIntParameter("date.min", 1850);
		var max = validator.f_getIntParameter("date.max", 2100);
		var pivot = validator.f_getIntParameter("date.pivot", 0);
		var sep = validator.f_getParameter("date.sepSign");
		var set = "["+f_vb._BuildEscaped(sep)+"]";
		var s = sep.charAt(0);
		var	sTmp = inVal;
		var d,m,y; //,p;
		//var res = "";
	
		// Deal with empty string and required attribute
		if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
			return inVal;
		}
	
		// Get the day date
		var dd = new Date();
		d = m = y = null;
	
		// Check if digits only
		var r = sTmp.match(/^\d*$/);
		if (r) {
			switch (sTmp.length) {
			case 8:
			case 6: 
				y = sTmp.substr(4);
			case 4: 
				m = sTmp.substr(2,2);
			case 2: 
				d = sTmp.substr(0,2);
				break;
			case 1: 
				d = sTmp;
			case 0: 
				break;
			default: 
				sTmp = null;
			}
		// Otherwise we have separators
		} else {
			var exp = "^(\\d{1,2})?"+set+"(\\d{1,2})?"+set+"?(\\d{2}|\\d{4})?$";
			r = sTmp.match(new RegExp(exp));
			if (r == null) {
				sTmp = null;
			} else {
				d = r[1]; 
				m = r[2]; 
				y = r[3];
			}
		}
		// Check valid string
		if (sTmp == null) {
			sTmp = inVal.replace(new RegExp("("+set+")","g"),s);
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setObject(null);
			validator.f_setLastError(
				"VALIDATION DATE",
				"Le format de saisie de date est invalide"
			);
			return null;
		}
		// Compute year, month ,day
		y = (y)? parseInt(y, 10):dd.getFullYear();
		y += (y >= 100)? 0:((y > pivot)? 1900:2000);
		m = (m)? parseInt(m, 10):(dd.getMonth() +1);
		d = (d)? parseInt(d, 10):dd.getDate();
	
		// Build input and output value
		sTmp = ((d<10)? "0":"")+d+s+((m<10)? "0":"")+m+s+y;
		validator.f_setInputValue(sTmp);
		validator.f_setOutputValue(sTmp);
	
		// Check valid year boundaries
		if (y<min || y>max) {
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION DATE",
				"L'année se trouve en dehors de la période de référence"
			);
			return null;
		}
	
		// Check valid date
		var test = new Date(y,m-1,d,12,0,0,0);
		if (test.getDate() == d && test.getMonth() == (m-1) && test.getFullYear() == y) {
			validator.f_setObject(test);
		} else {
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION DATE",
				"Le format de saisie de date est invalide"
			);
			sTmp = null;
		}
		validator.f_setObject(sTmp);
		return sTmp;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_dat_msa: function(validator, inVal) {
		var pivot = validator.f_getIntParameter("date.pivot", 0);
		var sep = validator.f_getParameter("date.sepSign");
		var set = "["+f_vb._BuildEscaped(sep)+"]";
		var s = sep.charAt(0);
		var	sTmp = inVal;
		var l; //,p
	
		// Deal with empty string and required attribute
		if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
			return inVal;
		}
	
		// Get the day date
		var dd = new Date();
		var m = null;
		var y = null;
	
		// Check if digits only
		var r = sTmp.match(/^\d*$/);
		if (r) {
			switch (l = sTmp.length) {
				case 6:
				case 4: y = sTmp.substr(2);
				case 2: m = sTmp.substr(0,2); break;
				case 1: m = sTmp;
				case 0: break;
				default: sTmp = null;
			}
		// Otherwise we have separators
		} else {
			var exp = "^(\\d{1,2})?"+set+"(\\d{2}|\\d{4})?$";
			r = sTmp.match(new RegExp(exp));
			if (r == null) {
				sTmp = null;
			} else {
				m = r[1]; y = r[2];
			}
		}
		// Check valid string
		if (sTmp == null) {
			sTmp = inVal.replace(new RegExp("("+set+")","g"),s);
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setObject(null);
			validator.f_setLastError(
				"VALIDATION DATE",
				"Le format de saisie de date est invalide"
			);
			return null;
		}
		// Compute year, month
		y = (y)? parseInt(y, 10):dd.getFullYear();
		y += (y >= 100)? 0:((y > pivot)? 1900:2000);
		m = (m)? parseInt(m, 10):(dd.getMonth() +1);
	
		// Allow whatever month
		sTmp = ((m<10)? "0":"")+m+s+y;
		validator.f_setObject(sTmp);
		return sTmp;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_dat_nai: function(validator, inVal) {
		// Handle empty string
		if (inVal == "") return inVal;
	
		var sep = validator.f_getParameter("date.sepSign");
		var set = "["+f_vb._BuildEscaped(sep)+"]";
		var s = sep.charAt(0);
		var	sTmp = inVal;
		var d,m,y;
		var dd,mm,yy;
		var DD,MM,YYYY;
		var aa,alt,ssss,mmmm,yyyy;
		var err = false;
		var msg = "";
	
		// Initialize fields
		d = m = y = null;
	
		// Check if digits only
		var r = sTmp.match(/^\d*$/);
		if (r) {
			var l = sTmp.length;
			switch (l) {
			case 8:
			case 7:
			case 6:
			case 5: 
				y = sTmp.substr(4);
			case 4:
			case 3: 
				m = sTmp.substr(2,2);
			case 2: 
				d = sTmp.substr(0,2); 
				break;
			case 1: 
				d = sTmp;
			case 0: 
				break;
			default: 
				sTmp = null;
			}
		// Otherwise we have separators
		} else {
			var exp = "^(\\d{1,2})?"+set+"(\\d{1,2})?"+set+"?(\\d{1,4})?$";
			r = sTmp.match(new RegExp(exp));
			if (r == null) {
				sTmp = null;
			} else {
				d = r[1]; m = r[2]; y = r[3];
			}
		}
		// Check valid string
		if (sTmp == null) {
			msg = "Format de saisie invalide";
			sTmp = inVal.replace(new RegExp("("+set+")","g"),s);
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION DATE",msg);
			return null;
		}
	
		// Get some default
		aa = new Date();
		DD = aa.getDate();
		MM = aa.getMonth()+1;
		YYYY = aa.getFullYear();
	
		// Parse values
		dd = d? parseInt(d,10):DD;
		mm = m? parseInt(m,10):MM;
		yy = y? parseInt(y,10):YYYY;
	
		// Special rules
		// Force day 0 for month 0
		if (mm == 0) {
			dd = 0;
		}
		// Force month 0 if day 0 and no month specified
		if (dd == 0 && !m) {
			mm = 0;
		}
	
		// Compute year between two limits
		// Compute century
		ssss = (Math.floor(YYYY/100))*100;
		// Compute millenary
		mmmm = (Math.floor(YYYY/1000))*1000;
	
		// Special date
		if (mm<=0 || mm>12) {
			// Year is one or two digits
			if (yy<100) {
				yyyy = yy+ssss;
				alt = yyyy-100;
				yyyy = (yyyy<=YYYY)? yyyy:alt;
			// Year is three digits
			} else if (yy==100) {
				yyyy = yy+mmmm;
			} else if (yy<850) {
				yyyy = yy+mmmm;
				err = true;
				msg = "Année invalide";
			} else if (yy<1000) {
				yyyy = yy+mmmm-1000;
			} else {
				yyyy = yy;
				if (yyyy<1850 || yyyy>2100) {
					err = true;
					msg = "Année invalide";
				}
			}
			// Check year against pivot year
			if (!err) {
				err = (yyyy>YYYY);
				if (err && alt) {
					yyyy=alt;
				}
				err = (yyyy>YYYY);
				if (err) {
					msg = "Année invalide";
				}
			}
			// Check month
			if (!err) {
				if (mm>12 && mm<20) {
					err = true;
					msg = "Mois invalide";
				}
			}
			// Build string
			sTmp = ((dd<10)?"0"+dd:""+dd)+s+((mm<10)?"0"+mm:mm)+s+yyyy;
			if (err) {
				validator.f_setInputValue(sTmp);
				validator.f_setOutputValue(sTmp);
				validator.f_setObject(null);
				validator.f_setLastError("VALIDATION DATE",msg);
				return null;
			}
			validator.f_setObject(sTmp);
			return sTmp;
		}
	
		// Normal date
		alt = undefined;
		if (yy<100) {
			yyyy = yy+ssss;
			alt = yyyy-100;
		} else if (yy==100) {
			yyyy = yy+mmmm;
			err = true;
			msg = "Année invalide";
		} else if (yy<850) {
			yyyy = yy+mmmm;
			err = true;
			msg = "Année invalide";
		} else if (yy<1000) {
			yyyy = yy+mmmm-1000;
		} else {
			yyyy = yy;
		}
	
		// At this point check valid date and alternate
		if (!err) {
			// Check if valid alternate date
			var t = new Date(alt,mm-1,dd,12,0,0,0);
			alt = (t.getFullYear()!=alt || t.getMonth()!=(mm-1) || t.getDate()!=dd)?
				undefined:alt;
	
			// Check date
			var t = new Date(yyyy,mm-1,dd,12,0,0,0);
			err = (t.getFullYear()!=yyyy || t.getMonth()!=(mm-1) || t.getDate()!=dd);
	
			// Get alternate date if invalid date
			if (err && alt) {
				err = false;
				yyyy=alt;
			} else {
				msg = "Date invalide";
			}
		}
		if (!err) {
			// Check date against pivot date
			err = ((yyyy>YYYY)||(yyyy==YYYY && mm>MM)||(yyyy==YYYY && mm==MM && dd>DD));
			// We have an unused alternate
			if (err && alt && yyyy!=alt) {
				yyyy = alt;
				err = ((yyyy>YYYY)||(yyyy==YYYY && mm>MM)||(yyyy==YYYY && mm==MM && dd>DD));
			}
			if (err) {
				msg = "Date supérieure à la date courante";
			}
		}
	
		// Build output string
		sTmp = ((dd<10)?"0"+dd:""+dd)+s+((mm<10)?"0"+mm:mm)+s+yyyy;
	
		// Invalid date
		if (err) {
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION DATE",msg);
			return null;
		}
	
		// Date is elligible
		validator.f_setObject(sTmp);
		return sTmp;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_hour: function(validator, inVal) {
		var sep = validator.f_getParameter("hour.sepSign");
		var set = "["+f_vb._BuildEscaped(sep)+"]";
		var s = sep.charAt(0);
		var	sTmp = inVal;
		var h,m,l,r,sec;
	
		// Deal with empty string and required attribute
		if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
			return inVal;
		}
	
		// Get the day date
		h = m = sec=null;
	
		// Check if digits only
		if (r = sTmp.match(/^\d*$/)) {
			switch (l = sTmp.length) {
				case 6: 
					sec = sTmp.substr(4,2);					
				case 4: 
					m = sTmp.substr(2,2);
				case 2: 
					h = sTmp.substr(0,2); 
					break;
				case 1: 
					h = sTmp;
				case 0: 
					break;
				default: 
					sTmp = null;
			}
		// Otherwise we have separators
		} else {
			var exp = "^(\\d{1,2})?"+set+"(\\d{1,2})?"+set+"(\\d{1,2})?$";
			r = sTmp.match(new RegExp(exp));
			if (r == null) {
				sTmp = null;
			} else {
				h = r[1]; m = r[2]; sec=r[3];
			}
		}
		// Check valid string
		if (sTmp == null) {
			sTmp = inVal.replace(new RegExp("("+set+")","g"),s);
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setObject(null);
			validator.f_setLastError(
				"VALIDATION HEURE",
				"Le format de saisie d'heure est invalide"
			);
			return null;
		}
		// Compute hour
		h = (h)? parseInt(h, 10):0;
		if (h > 23) {
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION HEURE","Heure invalide");
			return null;
		}
		// Compute minute
		m = (m)? parseInt(m, 10):0;
		if (m > 59) {
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION HEURE","Minute invalide");
			return null;
		}
		// Compute minute
		sec = (sec)? parseInt(sec, 10):0;
		if (sec > 59) {
			validator.f_setObject(null);
			validator.f_setLastError("VALIDATION HEURE","Seconde invalide");
			return null;
		}
	
		// Valid hour
		sTmp = ((h<10)? "0":"")+h+s+((m<10)? "0":"")+m+s+((sec<10)? "0":"")+sec;
		validator.f_setObject(sTmp);
		return sTmp;
	},

	/**
	 * @method public static
	 * @context object:validator
	 *
	 * Rules are the following:
	 * Position 1 has to be 125678
	 * Position 67 has to be d{2},|2A|2B
	 * Position 1 has to be 1|2 when Position 67 are 2A|2B
	 */
	Checker_insee: function(validator, inVal) {
		// Handle empty string
		if (!inVal) {
			return inVal;
		}
		
		var l = inVal.length;
	
		var r = null;
		if (inVal != null && inVal != "" &&  ((l == 13) || (l == 15))) {
			r = inVal.match(/^([125678])(\d{2})(\d{2})(\d{2}|2A|2B)(\d{3})(\d{3})(\d{2})?$/);
		}
		if ((r == null) || (inVal.match(/^[78][1-9].*$/)) ||
			(inVal.match(/^[03456789]\d{4}(2A|2B).*$/))) {
			validator.f_setLastError(
				"VALIDATION INSEE",
				"Le format de saisie du N° INSEE est invalide"
			);
			validator.f_setObject(null);
			return null;
		}
		// Verify key if specified
		if (l==15) {
			var key = parseInt(r[7]);
			var insee = parseInt(inVal.substr(0,13).replace(/2A/,"19").replace(/2B/,"18"),10);
			if (key != (97 - (insee % 97))) {
				validator.f_setLastError(
					"VALIDATION INSEE",
					"La clé du N° INSEE est invalide"
				);
				validator.f_setObject(null);
				return null;
			}
		}
		validator.f_setObject(inVal);
		return inVal;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_num: function(validator, inVal) {
		// Handle empty string
		if (!inVal) {
			return inVal;
		}
	
		var sTmp = inVal;
		//var signed = validator.f_getBoolParameter("num.signed");
		var decimal = validator.f_getIntParameter("num.cutDecimal", -1);
		var dec = validator.f_getParameter("num.decSign");
		var neg = validator.f_getParameter("num.negSign","-");
		var sep = validator.f_getParameter("num.sepSign");
		
		if (sep) {
			sTmp=sTmp.replace(new RegExp(f_vb._BuildEscaped(sep), "g"), "");			
		}

		var exp = "^("+ f_vb._BuildEscaped(neg) +"?)(\\d*)([" + f_vb._BuildEscaped(dec)+ "]?)(\\d*)$";
	
		// Normalize string, low cost and user friendly
		if (sTmp.lastIndexOf(neg) > 0) {
			sTmp = neg + sTmp.split(neg).join("");
		}
	
		// Check expression
		var r = sTmp.match(new RegExp(exp));
	
		// No match
		if (!r) {
			validator.f_setInputValue(sTmp);
			validator.f_setOutputValue(sTmp);
			validator.f_setLastError(
				"VALIDATION NUMERIQUE",
				"Le format de saisie est invalide"
			);
			validator.f_setObject(null);
			return null;
		}
	
		// Get parts
		var n = r[1];
		var ip = (r[2])? r[2]:"0";
		var d = (r[3])? dec.charAt(0):"";
		var dp = (r[4])? r[4]:"";
		if (decimal>0) {
			dp = (dp.length > decimal)? dp.substring(0,decimal):dp;

		} else if (decimal==0) {
			d="";
			dp="";
		}
		if (ip.length>1) {
			// Retire les 0 au debut !
			r=ip.match(new RegExp("^(0+)(\\d*)$"));
			
			if (r) {
				ip=(r[2])?r[2]:"0";				
			}
		}
		// Retire les 0 à la fin !
		for(;dp.length>1;) {
			if (dp.charAt(dp.length-1)!="0") {
				break;
			}
			
			dp=dp.substring(0, dp.length-1);
		}
		if (d && !dp.length) {
			dp="0";
		}
		
		// Rebuild string
		sTmp = n+ip+d+dp;
		// Math runtime uses dot as decimal separator
		var v = parseFloat(n+ip+"."+dp);
		validator.f_setObject(v);
		//alert(v);
		return sTmp;
	},
  
	/**
	 * @method public static
	 * @context object:validator
	 */
	Checker_trim: function(validator, inVal) {
		// Handle empty string
		if (inVal == "") {
			return inVal;
		}
	
		var text = f_core.Trim(inVal);
		validator.a_setObject(text);
		return text;
	},


/*=============================================================================
	FORMATTERS in alphabetic order...please
=============================================================================*/


	/**
	 * @method public static
	 * @context object:validator
	 */
	Formatter_insee: function(validator, inVal) {
		var l = inVal.length;
		var re = /^(\d{1})(\d{2})(\d{2})(\d{1}[0-9AB]{1})(\d{3})(\d{3})(\d{2})?$/;
		return inVal.replace(re, (l==15)? "$1 $2 $3 $4 $5 $6 $7":"$1 $2 $3 $4 $5 $6");
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Formatter_num: function(validator, inVal) {
		var sTmp = inVal;
		var decimal = validator.f_getIntParameter("num.decimal", -1);
		var dec = validator.f_getParameter("num.decSign");
		var neg = validator.f_getParameter("num.negSign", "-");
		var sep = validator.f_getParameter("num.sepSign");
		
		if (sep) {
			sTmp=sTmp.replace(new RegExp(f_vb._BuildEscaped(sep), "g"), "");			
		}
		
		var exp = "^("+ f_vb._BuildEscaped(neg) +"?)(\\d*)([" + f_vb._BuildEscaped(dec)+ "]?)(\\d*)$";
	
		// Check expression
		var r = sTmp.match(new RegExp(exp));
		if (!r) {
			f_core.Debug(f_vb, "Formatter_num: Invalid num value '"+inVal+"' (sTmp='"+sTmp+"').");
			return;
		}
		
		var n = r[1];  // negSign
		var ip = r[2]; // integer
		var d = r[3]; // decimalSign
		var dp = r[4]; // decimal
	
		for(;ip.length>1 && ip.charAt(0)=="0";ip=ip.substring(1));
	
		if (decimal===0) { // Attention au false ou -1
			d="";
			dp="";
			
		} else if (decimal>0) {
			d=true;
			
			if (dp.length>decimal) {			
				dp = dp.substring(0, decimal);
				
			} else {			
				for(;dp.length<decimal;) {
					dp+="0";
				}
			}
		}
	
		// Get generic decimal separator
		if (d) {
			d = dec.charAt(0);
		}
				
		// Check if no need
		if (!sep || (ip.length < 4)) {
			return n+ip+d+dp;
		}
		
		// Traitement des milliers ...
	
		// Otherwise format integer part
		// Reverse the string by split to array of chars and join
		// Replace every occurence of 3 digits pattern with and additional trailing space
		// Reverse the string again and there it is !
		ip = ip.split("").reverse().join("").replace(/(\d{3})/g,"$1 ").split("").reverse().join("");
		// Remove leading space if any
		ip = ip.replace(/^(\s)/,"");
		// Rebuild string
		sTmp = n+ip+d+dp;
		
		return sTmp;
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Formatter_padding: function(validator, inVal) {
		var component = validator.f_getComponent();
		if (typeof(component.f_getMaxTextLength) == "function") {
			var ln = component.f_getMaxTextLength();
			// when maxTextLength is not defined it takes a very big value ...
			if (ln > 0 && ln < 100000) {
				var nbPad = ln - inVal.length;
				if (nbPad > 0) {
					var padder = validator.f_getParameter("padder.value", " ");
					var leftSide = validator.f_getBoolParameter("padder.leftSide", true);
					var modifyInput = validator.f_getBoolParameter("padder.modifyInput", true);
					
					var padd = "";					
					for (; nbPad>0; nbPad--) {
						padd += padder;
					}
					
					var outVal;
					if (leftSide) {
						outVal = padd + inVal;
					} else {
						outVal = inVal + padd;
					}
					
					if (modifyInput) {
						validator.f_setInputValue(outVal);
					}
					return outVal;
				}
			}
		}
		return inVal;
	},


	/*=============================================================================
		BEHAVIORS in alphabetic order...please
	=============================================================================*/
	
	/**
	 * @method public static
	 * @context object:validator
	 */
	Behavior_required: function(validator, inVal) {
		var bRet;
	
		// Check if input value from format
		bRet = (inVal != null && inVal != "");
	
		// Fill error status
		if (!bRet) {
			f_vb._SetLastError(validator, "required.error", "REQUIRED_ERROR");	
		}
	
		// Return boolean value
		return bRet;
	},

	/**
	 * @method private static
	 */
	_SetLastError: function(validator, parameterPrefix, resourcePrefix, params) {
		var resourceBundle=f_resourceBundle.Get(f_vb);
		
		var summary=validator.f_getStringParameter(parameterPrefix+".summary");
		if (!summary) {
			summary=resourceBundle.f_get(resourcePrefix+"_SUMMARY");
		}
		if (summary && params) {
			summary=f_core.FormatMessage(summary, params);
		}
		
		var detail=validator.f_getStringParameter(parameterPrefix+".detail");
		if (!detail) {
			detail=resourceBundle.f_formatParams(resourcePrefix+"_DETAIL", null, null);
		}
		if (detail && params) {
			detail=f_core.FormatMessage(detail, params);
		}
		
		var severity=validator.f_getStringParameter(parameterPrefix+".severity");
	
		validator.f_setLastError(summary, detail, severity);  	
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Behavior_forcefill: function(validator, inVal) {
		var bRet = true;
	
		// Get input length
		var len = validator.f_getComponent().f_getMaxLength();
	
		// Silly
		if (!len) {
			return true;
		}
	
		// Check against inVal
		// testing inVal is equivalent to (undefined, null, "")?
		bRet = (inVal && (inVal.length == len));
	
		// Fill error status
		if (!bRet) {
			f_vb._SetLastError(validator, "required.error", "REQUIRED_ERROR", [ len ]);	
		}
	
		// Return boolean value
		return bRet;
	},
  
	/**
	 * @method public static
	 * @context object:validator
	 */
	Processor_autoTab: function(validator, keyCode, shift, ctrl, alt) {
		// Handle special key codes
		if (!f_key.IsPrintable(keyCode)) {			
			return true;
		}

		// Otherwise process element
		var component = validator.f_getComponent();

		// Check for a valid max length
		var len = component.maxLength;
		if (!len) {
			return true;
		}

		// Check for a valid value length
		var val = validator.f_getInputValue();
		if (!val.length) {
			return true;
		}

		// Check if length match
		if (val.length != len) {
			return true;
		}

		// Check for a next focusable element
		var next = f_core.GetNextFocusableComponent(component);
		if (!next) {
			return true;
		}

		// Give focus activation and selection
		f_core.SetFocus(next, true);

		return false;
	},
	
	/**
	 * @method public static
	 * @context object:validator
	 */
	Converter_dat: {
		// parameter name="date.sepSign" value="/-."
		// parameter name="date.pivot" value="90"

		f_getAsObject: function(validator, text) {
		},
		f_getAsString: function(validator, object) {
			if (!(object instanceof Date)) {
				return undefined;
			}
			
			var sep = validator.f_getParameter("date.sepSign");
			
			if (sep.length>1) {
				sep=sep.charAt(0);
			}
			
			return object.getDate()+sep+(object.getMonth()+1)+sep+object.getFullYear();	
		}
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Converter_hour: {
		// Parmaters: hour.sepSign" value=":. 
		f_getAsObject: function(validator, text) {
		},
		f_getAsString: function(validator, object) {
			if (!(object instanceof Date)) {
				return undefined;
			}
			
			var sep = validator.f_getParameter("date.sepSign");
			
			if (sep.length>1) {
				sep=sep.charAt(0);
			}
			
			return object.getHours()+sep+object.getMinutes()+sep+object.getSeconds();	
		}
	},

	/**
	 * @method public static
	 * @context object:validator
	 */
	Converter_num: {
		// parameter name="num.negSign" value="-"
		// parameter name="num.decSign" value=",."
		// parameter name="num.sepSign" value=" "
	
		f_getAsObject: function(validator, text) {
			var dec = validator.f_getParameter("num.decSign");
			var neg = validator.f_getParameter("num.negSign", "-");
			var sep = validator.f_getParameter("num.sepSign");

			if (sep) {
				text=text.replace(new RegExp(f_vb._BuildEscaped(sep), "g"), "");			
			}

			var exp = "^";
			if (neg) {
				exp+="("+f_vb._BuildEscaped(neg) +"?)";
			}

			exp+="(\\d*)";
			if (dec) {
				exp+="([" + f_vb._BuildEscaped(dec)+ "]?)(\\d*)";
			}
			exp+="$";
		
			// Check expression
			var r = text.match(new RegExp(exp));
			if (!r) {
				// Invalide !
				f_core.Debug(f_vb, "Converter_num: Invalid text '"+text+"'. (regexp='"+exp+"')");
				return null;
			}
			
			var n;
			var ip;
			//var d;
			var dp;

			if (neg) {
				n = r[1];
				ip = r[2];
				dp = r[4];
			} else {
				ip = r[1];
				dp = r[3];
			}
		
			for(;ip.length>1 && ip.charAt(0)=="0";ip=ip.substring(1));
		
			var num;
			if (dp) {
				num=parseFloat(ip+"."+dp);
			} else {
				num=parseInt(ip);
			}
			
			if (n) {
				num=-num;
			}
			
			f_core.Debug(f_vb, "Converter_num: Convert text["+typeof(text)+"] '"+text+"' to number["+typeof(num)+"] '"+num+"'.");
			return num;
		},
		f_getAsString: function(validator, object) {
			var num=object;
			if (typeof(num)!="number") {
				num=parseFloat(num);
			}
			
			var ret="";
			if (num<0) {
				var sign=validator.f_getParameter("num.negSign", "-");
				if (sign) {
					ret+=sign;
				}
				
				num=-num;
			}
			
			var dec=validator.f_getIntParameter("num.decimal", -1);
			var fixed;
			if (dec>=0) {
				fixed=num.toFixed(dec);
			} else {
				fixed=String(num);
			}
			
			var sign=validator.f_getParameter("num.decSign");
			if (sign) {
				sign=sign[0];
				if (sign!=".") {
					fixed=fixed.replace("/\./g", sign);
				}
			}
						
			ret+=fixed;
			
			f_core.Debug(f_vb, "Converter_num: Convert number["+typeof(object)+"] '"+object+"' to string["+typeof(ret)+"] '"+ret+"'.");
			return ret;
		}
	}
}

new f_class("f_vb", {
	statics: __statics
});

