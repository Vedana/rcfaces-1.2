/*
 * $Id$
 *
 */

/**
 * @class hidden F_NumberFormat
 */
function F_NumberFormat() {
	this._init(arguments);
}

/*------------------------------------------------------------------------------
 * Class private constants
 */
F_NumberFormat._F_NUMBERSTYLE = 0;
F_NumberFormat._F_CURRENCYSTYLE = 1;
F_NumberFormat._F_PERCENTSTYLE = 2;
F_NumberFormat._F_SCIENTIFICSTYLE = 3;
F_NumberFormat._F_INTEGERSTYLE = 4;

F_NumberFormat.F_getInstance = F_NumberFormat_F_getInstance;

/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_NumberFormat.prototype._groupingUsed = true;
F_NumberFormat.prototype._maxIntegerDigits = 40;
F_NumberFormat.prototype._minIntegerDigits = 1;
F_NumberFormat.prototype._maxFractionDigits = 3;    // invariant, >= minFractionDigits
F_NumberFormat.prototype._minFractionDigits = 0;
F_NumberFormat.prototype._parseIntegerOnly = false;
F_NumberFormat.prototype._maximumIntegerDigits = 40;
F_NumberFormat.prototype._minimumIntegerDigits = 1;
F_NumberFormat.prototype._maximumFractionDigits = 3;    // invariant, >= minFractionDigits
F_NumberFormat.prototype._minimumFractionDigits = 0;

F_NumberFormat.prototype._init = F_NumberFormat__init;

F_NumberFormat.prototype.f_isGroupingUsed = F_NumberFormat_isGroupingUsed;
F_NumberFormat.prototype.f_setGroupingUsed = F_NumberFormat_setGroupingUsed;
F_NumberFormat.prototype.f_getMaximumIntegerDigits = F_NumberFormat_getMaximumIntegerDigits;
F_NumberFormat.prototype.f_setMaximumIntegerDigits = F_NumberFormat_setMaximumIntegerDigits;
F_NumberFormat.prototype.f_getMinimumIntegerDigits = F_NumberFormat_getMinimumIntegerDigits;
F_NumberFormat.prototype.f_setMinimumIntegerDigits = F_NumberFormat_setMinimumIntegerDigits;
F_NumberFormat.prototype.f_getMaximumFractionDigits = F_NumberFormat_getMaximumFractionDigits;
F_NumberFormat.prototype.f_setMaximumFractionDigits = F_NumberFormat_setMaximumFractionDigits;
F_NumberFormat.prototype.f_getMinimumFractionDigits = F_NumberFormat_getMinimumFractionDigits;
F_NumberFormat.prototype.f_setMinimumFractionDigits = F_NumberFormat_setMinimumFractionDigits;
F_NumberFormat.prototype.f_isParseIntegerOnly = F_NumberFormat_isParseIntegerOnly;
F_NumberFormat.prototype.f_setParseIntegerOnly = F_NumberFormat_setParseIntegerOnly;

function F_NumberFormat__init(args) {
	//alert("Static class!!!");
}

function F_NumberFormat_F_getInstance(varargs) {
	var locale = (arguments.length > 0)? arguments[0]:F_Locale.F_getDefault();
	var choice = (arguments.length > 1)? arguments[1]:F_NumberFormat._F_NUMBERSTYLE;
	var symbols = new F_DecimalFormatSymbols(locale);
	var entry = (choice == F_NumberFormat._F_INTEGERSTYLE) ? 
		F_NumberFormat._F_NUMBERSTYLE : choice;
	var resource = F_LocaleData.F_getLocaleElements(locale);
	var numberPatterns = resource.f_getStringArray("NumberPatterns");
	var format = new F_DecimalFormat(numberPatterns[entry], symbols);

	if (choice == F_NumberFormat._F_INTEGERSTYLE) {
		format.f_setMaximumFractionDigits(0);
		format.f_setDecimalSeparatorAlwaysShown(false);
		format.f_setParseIntegerOnly(true);
	} else if (choice == F_NumberFormat._F_CURRENCYSTYLE) {
		format.f_adjustForCurrencyDefaultFractionDigits();
	}
	return format;
}

function F_NumberFormat_F_getNumberInstance(args) {
	var locale = (args.length > 0)? args[0]:F_Locale.F_getDefault();
	F_NumberFormat.F_getInstance(locale, F_NumberFormat._F_NUMBERSTYLE);
}

function F_NumberFormat_F_getIntegerInstance(args) {
	var locale = (args.length > 0)? args[0]:F_Locale.F_getDefault();
	F_NumberFormat.F_getInstance(locale, F_NumberFormat._F_INTEGERSTYLE);
}

function F_NumberFormat_F_getCurrencyInstance(args) {
	var locale = (args.length > 0)? args[0]:F_Locale.F_getDefault();
	F_NumberFormat.F_getInstance(locale, F_NumberFormat._F_CURRENCYSTYLE);
}

function F_NumberFormat_F_getPercentInstance(args) {
	var locale = (args.length > 0)? args[0]:F_Locale.F_getDefault();
	F_NumberFormat.F_getInstance(locale, F_NumberFormat._F_PERCENTSTYLE);
}

function F_NumberFormat_F_getScientificInstance(args) {
	var locale = (args.length > 0)? args[0]:F_Locale.F_getDefault();
	F_NumberFormat.F_getInstance(locale, F_NumberFormat._F_SCIENTIFICSTYLE);
}

function F_NumberFormat_isGroupingUsed() {
	return this._groupingUsed;
}

function F_NumberFormat_setGroupingUsed(newValue) {
	this._groupingUsed = newValue;
}

function F_NumberFormat_getMaximumIntegerDigits() {
	return this._maximumIntegerDigits;
}

function F_NumberFormat_setMaximumIntegerDigits(newValue) {
	this._maximumIntegerDigits = Math.max(0,newValue);
	if (this._minimumIntegerDigits > this._maximumIntegerDigits) {
		this._minimumIntegerDigits = this._maximumIntegerDigits;
	}
}

function F_NumberFormat_getMinimumIntegerDigits() {
	return this._minimumIntegerDigits;
}

function F_NumberFormat_setMinimumIntegerDigits(newValue) {
	this._minimumIntegerDigits = Math.max(0,newValue);
	if (this._minimumIntegerDigits > this._maximumIntegerDigits) {
		this._maximumIntegerDigits = this._minimumIntegerDigits;
	}
}

function F_NumberFormat_getMaximumFractionDigits() {
	return this._maximumFractionDigits;
}

function F_NumberFormat_setMaximumFractionDigits(newValue) {
	this._maximumFractionDigits = Math.max(0,newValue);
	if (this._maximumFractionDigits < this._minimumFractionDigits) {
		this._minimumFractionDigits = this._maximumFractionDigits;
    }
}

function F_NumberFormat_getMinimumFractionDigits() {
	return this._minimumFractionDigits;
}

function F_NumberFormat_setMinimumFractionDigits(newValue) {
	this._minimumFractionDigits = Math.max(0,newValue);
	if (this._maximumFractionDigits < this._minimumFractionDigits) {
		this._maximumFractionDigits = this._minimumFractionDigits;
	}
}

function F_NumberFormat_isParseIntegerOnly() {
	return this._parseIntegerOnly;
}

function F_NumberFormat_setParseIntegerOnly(value) {
	this._parseIntegerOnly = value;
}



