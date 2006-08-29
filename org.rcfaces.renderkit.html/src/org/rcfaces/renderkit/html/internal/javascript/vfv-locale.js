/**
 * @file _locale.js
 * @company Vedana
 * @authors J.Merlin
 * @date
 * @revision
 *
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * Locale, LocaleData, ResourceBundle java classes.
 *
 * The following class is a JavaScript adaptation
 */

//------------------------------------------------------------------------------
// Class F_ResourceBundle
//------------------------------------------------------------------------------

/**
 * @class hidden F_ResourceBundle
 * @method F_ResourceBundle
 * @decl public
 *
 *
 *
 * @param
 * @return
 * @see
 */
function F_ResourceBundle() {
	this._init(arguments);
}

/*------------------------------------------------------------------------------
 * Class public methods
 */
F_ResourceBundle.F_getBundle = F_ResourceBundle_getBundle;

/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_ResourceBundle.prototype._values = null;

/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_ResourceBundle.prototype._init = F_ResourceBundle__init;

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_ResourceBundle.prototype.f_getStringArray = F_ResourceBundle_getStringArray;


function F_ResourceBundle__init(args) {
}

function F_ResourceBundle_getBundle(varargs) {
	var basename = (arguments.length > 0)? arguments[0]:"";
	var locale = (arguments.length > 1)? arguments[1]:Locale.F_getDefault();
	var bundle = null;
	var name = basename;
	var language = locale.f_getLanguage();
	var country = locale.f_getCountry();
	var variant = locale.f_getVariant();
	if (language != "") {
		name = basename + "_" + language;
		if (country != "") {
			name += ("_" + country);
			if (variant != "") {
				name += ("_" + variant);
			}
		}
	}
	try {
		bundle = eval("new " + name + "()");
	} catch (e) {
		bundle = null;
	}
	return bundle; 
}


function F_ResourceBundle_getStringArray(resourceKey) {
	if (this._values[resourceKey] === undefined) {
		return null;
	}
	return this._values[resourceKey];
}


//------------------------------------------------------------------------------
// Class F_Bundle_fr_FR
//------------------------------------------------------------------------------
function F_Bundle_fr_FR() {
	this._init(arguments);
}
F_Bundle_fr_FR.prototype = new F_ResourceBundle();
F_Bundle_fr_FR.prototype._init = F_Bundle_fr_FR__init;

function F_Bundle_fr_FR__init(args) {
	this._values = [];
	this._values["NumberPatterns"] = [
		"#,##0.###;-#,##0.###",		// NUMBERSTYLE, INTEGERSTYLE
		"#,##0.00 ¤;-#,##0.00 ¤",	// CURRENCYSTYLE
		"#,##0%"					// PERCENTSTYLE
	];
}


//------------------------------------------------------------------------------
// Class F_LocaleData
//------------------------------------------------------------------------------
function F_LocaleData() {
	this._init(arguments);
}

F_LocaleData.prototype._init = F_LocaleDatf__init;
F_LocaleData.F_getLocaleElements = F_LocaleDatf_getLocaleElements;


function F_LocaleDatf_getLocaleElements(locale) {
	return F_ResourceBundle.F_getBundle("F_Bundle", locale);
}

function F_LocaleDatf__init(args) {
}

//------------------------------------------------------------------------------
// Class F_Locale
//------------------------------------------------------------------------------
/**
 * @class hidden F_Locale
 */
function F_Locale() {
	this._init(arguments);
}
F_Locale.prototype._init = F_Locale__init;

/*------------------------------------------------------------------------------
 * Class public constants
 */
F_Locale.F_ENGLISH = new F_Locale("en","","");
F_Locale.F_FRENCH = new F_Locale("fr","","");
F_Locale.F_GERMAN = new F_Locale("de","","");
F_Locale.F_ITALIAN = new F_Locale("it","","");
F_Locale.F_JAPANESE = new F_Locale("ja","","");
F_Locale.F_KOREAN = new F_Locale("ko","","");
F_Locale.F_CHINESE = new F_Locale("zh","","");
F_Locale.F_SIMPLIFIED_CHINESE = new F_Locale("zh","CN","");
F_Locale.F_TRADITIONAL_CHINESE = new F_Locale("zh","TW","");
F_Locale.F_FRANCE = new F_Locale("fr","FR","");
F_Locale.F_GERMANY = new F_Locale("de","DE","");
F_Locale.F_ITALY = new F_Locale("it","IT","");
F_Locale.F_JAPAN = new F_Locale("ja","JP","");
F_Locale.F_KOREA = new F_Locale("ko","KR","");
F_Locale.F_CHINA = new F_Locale("zh","CN","");
F_Locale.F_PRC = new F_Locale("zh","CN","");
F_Locale.F_TAIWAN = new F_Locale("zh","TW","");
F_Locale.F_UK = new F_Locale("en","GB","");
F_Locale.F_US = new F_Locale("en","US","");
F_Locale.F_CANADA = new F_Locale("en","CA","");
F_Locale.F_CANADF_FRENCH = new F_Locale("fr","CA","");


/*------------------------------------------------------------------------------
 * Class public methods
 */
F_Locale.F_getDefault = F_Locale_getDefault;
F_Locale.F_setDefault = F_Locale_setDefault;

/*------------------------------------------------------------------------------
 * Class private variables
 */
F_Locale._defaultLocale = null;


/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_Locale.prototype._language = "";	// ISO-2 639 Language Code
F_Locale.prototype._country = "";		// ISO-2 3166 Country Code
F_Locale.prototype._variant = "";		// Vendor or Browser specific code


/*------------------------------------------------------------------------------
 * Instance private methods
 */

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_Locale.prototype.f_getLanguage = F_Locale_getLanguage;
F_Locale.prototype.f_getCountry = F_Locale_getCountry;
F_Locale.prototype.f_getVariant = F_Locale_getVariant;


/**
 * @method _init
 * @decl private
 *
 *
 * Cette 
 *
 * @param args Arguments pass�s au constructeur
 * @return
 * @see
 */
function F_Locale__init(args) {
	this._language = (args.length > 0)? args[0]:"";
	this._country = (args.length > 1)? args[1].toUpperCase():"";
	this._variant = (args.length > 2)? args[2]:"";
}

/**
 * @method F_getDefault
 * @decl public static
 *
 * Retourne la localisation par d�faut
 *
 * @param
 * @return F_Locale Un objet de type F_Locale
 * @see
 */
function F_Locale_getDefault() {
	if (F_Locale._defaultLocale == null) {
		F_Locale._defaultLocale = F_Locale.F_FRANCE;
	}
	return F_Locale._defaultLocale;
}

/**
 * @class F_Locale
 * @method F_setDefault
 * @decl public static
 *
 *
 *
 * @param newLocale 
 * @return
 * @see
 */
function F_Locale_setDefault(newLocale) {
	F_Locale._defaultLocale = newLocale;
}

function F_Locale_getLanguage() {
	return this._language;
}

function F_Locale_getCountry() {
	return this._country;
}

function F_Locale_getVariant() {
	return this._variant;
}

