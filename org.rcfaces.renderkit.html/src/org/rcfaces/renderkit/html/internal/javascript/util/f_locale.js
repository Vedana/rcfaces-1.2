/*
 * $Id$
 */
 
/**
 * @class public final f_locale extends Object
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {
	f_locale: function() {
		var resourceBundle=f_resourceBundle.Get(f_locale);
	
		this._monthShortNames=resourceBundle.f_get("MONTH_SHORT_NAMES");
		this._monthMedNames=resourceBundle.f_get("MONTH_MED_NAMES");
		this._monthLongNames=resourceBundle.f_get("MONTH_LONG_NAMES");
	
		this._dayShortNames=resourceBundle.f_get("DAY_SHORT_NAMES");
		this._dayMedNames=resourceBundle.f_get("DAY_MED_NAMES");
		this._dayLongNames=resourceBundle.f_get("DAY_LONG_NAMES");
		
		this._firstDayOfWeek=resourceBundle.f_get("FIRST_DAY_OF_WEEK");
		this._twoDigitYearStart=resourceBundle.f_get("TWO_DIGIT_YEAR_START");
	
		this._dateFormats=resourceBundle.f_get("DATE_FORMATS");
	},
	
	/*
	f_finalize: function() {
		this._monthShortNames=undefined; // string[]
		this._monthMedNames=undefined; // string[]
		this._monthLongNames=undefined; // string[]
	
		this._dayShortNames=undefined; // string[]
		this._dayMedNames=undefined; // string[]
		this._dayLongNames=undefined; // string[]
	
		this._firstDayOfWeek=undefined; // number
	
		this._dateFormats=undefined; // string[]
	
		this.f_super(arguments);
	},
	*/
	/**
	 * @method public 
	 * @param number n Month number (0 to 11)
	 * @param number form  Form of name (SHORT, MEDIUM, LONG)
	 * @return String
	 * @see #LONG
	 * @see #SHORT
	 * @see #MEDIUM
	 */
	f_getMonthName: function(n, form) {
	
		var a=this._monthMedNames;
	
		switch(form) {
		case f_locale.LONG:
			a=this._monthLongNames;
			break;
			
		case f_locale.SHORT:
			a=this._monthShortNames;
			break;
		}
		
		if (n<0 || n>=a.length) {
			return null;
		}
		
		return a[n];
	},
	/**
	 * @method public 
	 * @param number n Day number (0 to 6)
	 * @param number form  Form of name (SHORT, MEDIUM, LONG)
	 * @return String
	 * @see #LONG
	 * @see #SHORT
	 * @see #MEDIUM
	 */
	f_getDayName: function(n, form) {
		var a=this._dayMedNames;
	
		switch(form) {
		case f_locale.LONG:
			a=this._dayLongNames;
			break;
			
		case f_locale.SHORT:
			a=this._dayShortNames;
			break;
		}
		
		if (n<0 || n>=a.length) {
			return null;
		}
		
		return a[n];
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getFirstDayOfWeek: function() {
		return this._firstDayOfWeek;
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getTwoDigitYearStart: function() {
		return this._twoDigitYearStart;
	},
	/**
	 * @method public
	 * @param number form Form of date (SHORT, MEDIUM, LONG)
	 * @return String
	 * @see #LONG
	 * @see #SHORT
	 * @see #MEDIUM
	 */
	f_getDateFormat: function(form) {
		var ds=this._dateFormats;
	
		switch(form) {
		case f_locale.LONG:
			return ds[2];
			
		case f_locale.MEDIUM:
			return ds[1];
		}
		
		return ds[0];
	},
	/**
     * Getter for the programmatic name of the entire locale,
     * with the language, country and variant separated by underbars.
     * Language is always lower case, and country is always upper case.
     */
	f_getName: function() {
		return f_env.GetLocaleName();
	},
  	/**
     * Returns the language code for this locale, which will either be the empty string
     * or a lowercase ISO 639 code.
     */
	f_getLanguage: function() {
		return f_locale._GetLocaleNamePart(0);
	},
 	/**
     * Returns the country/region code for this locale, which will either be the empty string
     * or an upercase ISO 3166 2-letter code.
     */
 	f_getCountry: function() {
		return f_locale._GetLocaleNamePart(1);
	},
   /**
     * Returns the variant code for this locale.
     */
	f_getVariant: function() {
		return f_locale._GetLocaleNamePart(2);
	}
}

var __static = {

	/**
	 * Short form.
	 *
	 * @field public static final number
	 */
	SHORT: 0,

	/**
	 * Medium form.
	 *
	 * @field public static final number
	 */
	MEDIUM: 1,

	/**
	 * Long form.
	 *
	 * @field public static final number
	 */
	LONG: 2,

	/**
	 * @field private static
	 */
	_Instance: undefined,
	 
	/**
	 * @method public static final
	 * @return f_locale
	 */
	Get: function() {
		if (!f_locale._Instance) {
			f_locale._Instance=new f_locale();
		}
		
		return f_locale._Instance;
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		f_locale._Instance=undefined; // f_locale
	},
	/**
	 * @method private static final
	 * @param number idx
	 * @return String
	 */
	_GetLocaleNamePart: function(idx) {
		var seps=f_env.GetLocaleName().split("_");
		if (seps.length<=idx) {
			return null;
		}
		
		return seps[idx];
	}
}

new f_class("f_locale", null, __static, __prototype);

