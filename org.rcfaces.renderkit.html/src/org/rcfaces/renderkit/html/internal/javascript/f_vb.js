/*
 * $Id$
 */

/*
 * F_VALID_BASIC package
 *
 * @class f_vb
 */
/*=============================================================================
	FILTERS in alphabetic order...please
=============================================================================*/


var __static = {
	/**
	 * @field private static final string
	 */
	_LATIN_ACCENT_FR: "àäâéèëêïîöôùüûÿçÀÄÂÉÈËÊÏÎÖÔÙÜÛÇ",

	/**
	 * @field private static final string
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
 * There are a few reserved chars in regular expressions.
 * Handle string encoding with a powerfull regular expression.
 * Reserved chars are the following set:
 *	\/.*+?|()[]{}-^
 */
  _BuildEscaped: function(str) {
	if ((str == null) || (str == "")) {
		return str;
	}
	return str.replace(/([\\\/\.\*\+\?\|\(\)\[\]\{\}\-\^])/g, "\\$1");
  },

/**
 * @method public
 */
  Filter_alpha: function(validator, keyCode, keyChar) {
   var exp = "[a-zA-Z";
   var sup = validator.f_getParameter("alpha.otherChars");
   exp += (f_vb._BuildEscaped(sup) + "]");
   return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_alpha_fr: function(validator, keyCode, keyChar) {
   var exp = "[a-zA-Z" + f_vb._LATIN_ACCENT_FR;
   var sup = validator.f_getParameter("alpha.otherChars");
   exp += (f_vb._BuildEscaped(sup) + "]");
   return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_alphanum: function(validator, keyCode, keyChar) {
	var exp = "[0-9a-zA-Z";
	var sup = validator.f_getParameter("alpha.otherChars");
	exp += (f_vb._BuildEscaped(sup) + "]");
	return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_card: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\.]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_code: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9a-zA-Z]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_dat: function(validator, keyCode, keyChar) {
	var exp = "[0-9";
	var sup = validator.f_getParameter("date.sepSign");
	exp += (f_vb._BuildEscaped(sup) + "]");
	return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_date: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\/]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_digit: function(validator, keyCode, keyChar) {
   return f_clientValidator.Filter_generic(validator, /[0-9]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_dps: function(validator, keyCode, keyChar) {
   return f_clientValidator.Filter_generic(validator, /[\040-\177]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_hour: function(validator, keyCode, keyChar) {
	var exp = "[0-9";
	var sup = validator.f_getParameter("hour.sepSign");
	exp += (f_vb._BuildEscaped(sup) + "]");
	return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_insee: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9aAbB]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_integer: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_money: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\.\+\-]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_name: function(validator, keyCode, keyChar) {
 	// Un espace dans l'expression reguliere: il faut passer par le constructeur,
 	// a cause du filtre Javascript
	return f_clientValidator.Filter_generic(validator, new RegExp("[ a-zA-Z0-9\*\.\-]"), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_noblank: function(validator, keyCode, keyChar) {
 	// Un espace dans l'expression reguliere: il faut passer par le constructeur,
 	// a cause du filtre Javascript
	return f_clientValidator.Filter_generic(validator, new RegExp("[^ ]"), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_num: function(validator, keyCode, keyChar) {
	var exp = "[0-9";
	var sup = "";
	if (validator.f_getBoolParameter("num.signed", false)) {
		sup = validator.f_getParameter("num.negSign");
		exp += f_vb._BuildEscaped(sup);
	}
	if (validator.f_getIntParameter("num.decimal", 0) != 0) {
		sup = validator.f_getParameter("num.decSign");
		exp += f_vb._BuildEscaped(sup);
	}
   	sup = validator.f_getParameter("num.sepSign");
	exp += (f_vb._BuildEscaped(sup) + "]");
	return f_clientValidator.Filter_generic(validator, new RegExp(exp), keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_number: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\,\-]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_signed: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\-]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_scientific: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\.\-eE]/, keyCode, keyChar);
  },

/**
 * @method public
 */
  Filter_time: function(validator, keyCode, keyChar) {
	return f_clientValidator.Filter_generic(validator, /[0-9\:]/, keyCode, keyChar);
  },

/*=============================================================================
	TRANSLATORS in alphabetic order...please
=============================================================================*/

/**
 * @method public
 */
  Translator_date: function(validator, keyCode, keyChar) {
	var set = validator.f_getParameter("date.sepSign");
	if (set && (set.length > 1) && (set.indexOf(keyChar) != -1)) {
		return set.charCodeAt(0);
	}
	return keyCode;
  },

/**
 * @method public
 */
  Translator_hour: function(validator, keyCode, keyChar) {
	var set = validator.f_getParameter("hour.sepSign");
	if (set && (set.length > 1) && (set.indexOf(keyChar) != -1)) {
		return set.charCodeAt(0);
	}
	return keyCode;
  },

/**
 * @method public
 */
  Translator_num: function(validator, keyCode, keyChar) {
	var set = validator.f_getParameter("num.decSign");
	if (set && (set.length > 1) && (set.indexOf(keyChar) != -1)) {
		return set.charCodeAt(0);
	}
	return keyCode;
  },

/**
 * @method public
 */
  Translator_removeaccent: function(validator, keyCode, keyChar) {
	var expr,code;
	var mapper=f_clientValidator._ACCENTS_MAPPER;
	for (var i=0; i<mapper.length;) {
		expr = mapper[i++];
		code = mapper[i++];
		if (expr.test(keyChar)) {
			keyCode = code;
			break;
		}
	}
	return keyCode;
  },

/**
 * @method public
 */
  Translator_uppercase: function(validator, keyCode, keyChar) {
	if (keyCode >= 97 && keyCode <= 122) {
		return (keyCode - 32);
	} else if (keyCode > 127) {
		if (/[áãàâäåéèêëíìîïñóõòôöúùûüçý]/.test(keyChar)) {
			return (keyCode - 32);
		}
	}
	return keyCode;
  },
  Translator_lowercase: function(validator, keyCode, keyChar) {
	if (keyCode >= 65 && keyCode <= 90) {
		return (keyCode + 32);
	} else if (keyCode > 127) {
		if (/[ÀÁÂÃÄÅÈÉÊËÌÍÎÏÑÓÔÕÖÒÙÚÛÜÝÇ]/.test(keyChar)) {
			return (keyCode + 32);
		}
	}
	return keyCode;
  },

/*=============================================================================
	CHECKERS in alphabetic order...please
=============================================================================*/

/**
 * @method public
 */
  Checker_dat: function(validator, inVal) {
	var min = validator.f_getIntParameter("date.min", 1850);
	var max = validator.f_getIntParameter("date.max", 2100);
	var pivot = validator.f_getIntParameter("date.pivot", 0);
	var sep = validator.f_getParameter("date.sepSign");
		var set = "["+f_vb._BuildEscaped(sep)+"]";
	var s = sep.charAt(0);
	var	sTmp = inVal;
	var d,m,y,p,dd;
	var res = "";

	// Deal with empty string and required attribute
	if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
		return inVal;
	}

	// Get the day date
	dd = new Date();
	d = m = y = null;

	// Check if digits only
	var r = sTmp.match(/^\d*$/);
	if (r) {
		switch (sTmp.length) {
			case 8:
			case 6: y = sTmp.substr(4);
			case 4: m = sTmp.substr(2,2);
			case 2: d = sTmp.substr(0,2); break;
			case 1: d = sTmp;
			case 0: break;
			default: sTmp = null;
		}
	// Otherwise we have separators
	} else {
		var exp = "^(\\d{1,2})?"+set+"(\\d{1,2})?"+set+"?(\\d{2}|\\d{4})?$";
		r = sTmp.match(new RegExp(exp));
		if (r == null) {
			sTmp = null;
		} else {
			d = r[1]; m = r[2]; y = r[3];
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
 * @method public
 */
  Checker_dat_msa: function(validator, inVal) {
	var pivot = validator.f_getIntParameter("date.pivot", 0);
	var sep = validator.f_getParameter("date.sepSign");
	var set = "["+f_vb._BuildEscaped(sep)+"]";
	var s = sep.charAt(0);
	var	sTmp = inVal;
	var m,y,p,dd,l,r;

	// Deal with empty string and required attribute
	if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
		return inVal;
	}

	// Get the day date
	dd = new Date();
	m = y = null;

	// Check if digits only
	if (r = sTmp.match(/^\d*$/)) {
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
 * @method public
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
			case 5: y = sTmp.substr(4);
			case 4:
			case 3: m = sTmp.substr(2,2);
			case 2: d = sTmp.substr(0,2); break;
			case 1: d = sTmp;
			case 0: break;
			default: sTmp = null;
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
	if (mm == 0) dd = 0;
	// Force month 0 if day 0 and no month specified
	if (dd == 0 && !m) mm = 0;

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
			if (err && alt) yyyy=alt;
			err = (yyyy>YYYY);
			if (err) msg = "Année invalide";
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
		if (err) msg = "Date supérieure à la date courante";
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
 * @method public
 */
  Checker_hour: function(validator, inVal) {
	var sep = validator.f_getParameter("hour.sepSign");
	var set = "["+f_vb._BuildEscaped(sep)+"]";
	var s = sep.charAt(0);
	var	sTmp = inVal;
	var h,m,l,r;

	// Deal with empty string and required attribute
	if (inVal == "" && !(validator.f_getComponent().f_isRequired())) {
		return inVal;
	}

	// Get the day date
	h = m = null;

	// Check if digits only
	if (r = sTmp.match(/^\d*$/)) {
		switch (l = sTmp.length) {
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
		var exp = "^(\\d{1,2})?"+set+"(\\d{1,2})?$";
		r = sTmp.match(new RegExp(exp));
		if (r == null) {
			sTmp = null;
		} else {
			h = r[1]; m = r[2];
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
		validator.f_setLastError("VALIDATION HEURE","Heure invalide");
		return null;
	}

	// Valid hour
	sTmp = ((h<10)? "0":"")+h+s+((m<10)? "0":"")+m;
	validator.f_setObject(sTmp);
	return sTmp;
},

/**
 * @method public
 *
 * @comment
 * Rules are the following:
 * Position 1 has to be 125678
 * Position 67 has to be d{2},|2A|2B
 * Position 1 has to be 1|2 when Position 67 are 2A|2B
 */
  Checker_insee: function(validator, inVal) {
	// Handle empty string
	if (inVal == "") return inVal;
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
 * @method public
 */
  Checker_num: function(validator, inVal) {
	// Handle empty string
	if (inVal == "") return inVal;

	var sTmp = inVal;
	var signed = validator.f_getBoolParameter("num.signed");
	var decimal = validator.f_getIntParameter("num.decimal", 0);
	var dec = validator.f_getParameter("num.decSign");
	var neg = validator.f_getParameter("num.negSign");
	var n,ip,d,dp,r,v;
	var exp = "^("+ f_vb._BuildEscaped(neg) +"?)(\\d*)([" + f_vb._BuildEscaped(dec)+ "]?)(\\d*)$";

	// Normalize string, low cost and user friendly
	if (sTmp.lastIndexOf(neg) > 0) {
		sTmp = neg + sTmp.split(neg).join("");
	}

	// Check expression
	r = sTmp.match(new RegExp(exp));

	// No match
	if (r == null) {
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
	n = r[1];
	ip = (r[2])? r[2]:"0";
	d = (r[3])? dec.charAt(0):"";
	dp = (r[4])? r[4]:"";
	dp = (dp.length > decimal)? dp.substr(0,decimal):dp;
	
	// Rebuild string
	sTmp = n+ip+d+dp;
	// Math runtime uses dot as decimal separator
	v = parseFloat(n+ip+"."+dp);
	validator.f_setObject(v);
	//alert(v);
	return sTmp;
  },
  
	Checker_trim: function(validator, inVal) {
		// Handle empty string
		if (inVal == "") {
			return inVal;
		}
	
		var text = inVal.replace(/^\s*(.*?)\s*$/, "$1");
		validator.a_setObject(text);
		return text;
	},


/*=============================================================================
	FORMATTERS in alphabetic order...please
=============================================================================*/


/**
 * @method public
 */
  Formatter_insee: function(validator, inVal) {
	var l = inVal.length;
	var re = /^(\d{1})(\d{2})(\d{2})(\d{1}[0-9AB]{1})(\d{3})(\d{3})(\d{2})?$/;
	return inVal.replace(re, (l==15)? "$1 $2 $3 $4 $5 $6 $7":"$1 $2 $3 $4 $5 $6");
  },

/**
 * @method public
 */
  Formatter_num: function(validator, inVal) {
	var sTmp = inVal;
	var dec = validator.f_getParameter("num.decSign");
	var neg = validator.f_getParameter("num.negSign");
	var sep = validator.f_getParameter("num.sepSign");
	var n,ip,d,dp,r,v;
	var exp = "^("+ f_vb._BuildEscaped(neg) +"?)(\\d*)([" + f_vb._BuildEscaped(dec)+ "]?)(\\d*)$";

	// Check expression
	r = sTmp.match(new RegExp(exp));
	n = r[1];ip = r[2];d = r[3];dp = r[4];

	for(;ip.length>1 && ip.charAt(0)=="0";ip=ip.substring(1));

	// Get generic decimal separator
	if (d) {
		d = dec.charAt(0);
	}
	sTmp = n+ip+d+dp;

	// Check if no need
	if (!sep || (ip.length < 4)) {
		return sTmp;
	}

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


/*=============================================================================
	BEHAVIORS in alphabetic order...please
=============================================================================*/
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
		detail=resourceBundle.f_getParams(resourcePrefix+"_DETAIL", null, null);
	}
	if (detail && params) {
		detail=f_core.FormatMessage(detail, params);
	}
	
	var severity=validator.f_getStringParameter(parameterPrefix+".severity");

	validator.f_setLastError(summary, detail, severity);  	
  },

  Behavior_forcefill: function(validator, inVal) {
	var bRet = true;

	// Get input length
	var len = validator.f_getComponent().f_getMaxLength();

	// Silly
	if (len == 0) {
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
		f_core.SetFocus(next);

		return false;
	}
}
 
var f_vb=new f_class("f_vb", null, __static);
