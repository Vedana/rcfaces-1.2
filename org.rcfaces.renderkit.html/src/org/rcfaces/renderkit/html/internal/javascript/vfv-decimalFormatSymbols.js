/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */
 
/**
 * @class hidden F_DecimalFormatSymbols
 * @company Vedana
 * @authors J.Merlin
 * @version $Revision$ $Date$
 *
 *
 *
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * NumberFortmat, DecimalFormat, DecimalFormatSymbols
 * java classes.
 * The following class is a JavaScript adaptation
 */


function F_DecimalFormatSymbols() {
	this._init(arguments);
}

/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_DecimalFormatSymbols.prototype._locale = null;
F_DecimalFormatSymbols.prototype._zeroDigit = null;
F_DecimalFormatSymbols.prototype._groupingSeparator = null;
F_DecimalFormatSymbols.prototype._decimalSeparator = null;
F_DecimalFormatSymbols.prototype._perMill = null;
F_DecimalFormatSymbols.prototype._percent = null;
F_DecimalFormatSymbols.prototype._digit = null;
F_DecimalFormatSymbols.prototype._patternSeparator = null;
F_DecimalFormatSymbols.prototype._infinity = null;
F_DecimalFormatSymbols.prototype._NaN = null;
F_DecimalFormatSymbols.prototype._minusSign = null;
F_DecimalFormatSymbols.prototype._currencySymbol = null;
F_DecimalFormatSymbols.prototype._intlCurrencySymbol = null;
F_DecimalFormatSymbols.prototype._monetarySeparator = null;
F_DecimalFormatSymbols.prototype._exponential = null;

/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_DecimalFormatSymbols.prototype._init = F_DecimalFormatSymbols__init;
F_DecimalFormatSymbols.prototype._initialize = F_DecimalFormatSymbols__initialize;

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_DecimalFormatSymbols.prototype.f_getZeroDigit = F_DecimalFormatSymbols_getZeroDigit;
F_DecimalFormatSymbols.prototype.f_setZeroDigit = F_DecimalFormatSymbols_setZeroDigit;
F_DecimalFormatSymbols.prototype.f_getGroupingSeparator = F_DecimalFormatSymbols_getGroupingSeparator;
F_DecimalFormatSymbols.prototype.f_setGroupingSeparator = F_DecimalFormatSymbols_setGroupingSeparator;
F_DecimalFormatSymbols.prototype.f_getDecimalSeparator = F_DecimalFormatSymbols_getDecimalSeparator;
F_DecimalFormatSymbols.prototype.f_setDecimalSeparator = F_DecimalFormatSymbols_setDecimalSeparator;
F_DecimalFormatSymbols.prototype.f_getPerMill = F_DecimalFormatSymbols_getPerMill;
F_DecimalFormatSymbols.prototype.f_setPerMill = F_DecimalFormatSymbols_setPerMill;
F_DecimalFormatSymbols.prototype.f_getPercent = F_DecimalFormatSymbols_getPercent;
F_DecimalFormatSymbols.prototype.f_setPercent = F_DecimalFormatSymbols_setPercent;
F_DecimalFormatSymbols.prototype.f_getDigit = F_DecimalFormatSymbols_getDigit;
F_DecimalFormatSymbols.prototype.f_setDigit = F_DecimalFormatSymbols_setDigit;
F_DecimalFormatSymbols.prototype.f_getPatternSeparator = F_DecimalFormatSymbols_getPatternSeparator;
F_DecimalFormatSymbols.prototype.f_setPatternSeparator = F_DecimalFormatSymbols_setPatternSeparator;
F_DecimalFormatSymbols.prototype.f_getInfinity = F_DecimalFormatSymbols_getInfinity;
F_DecimalFormatSymbols.prototype.f_setInfinity = F_DecimalFormatSymbols_setInfinity;
F_DecimalFormatSymbols.prototype.f_getNaN = F_DecimalFormatSymbols_getNaN;
F_DecimalFormatSymbols.prototype.f_setNaN = F_DecimalFormatSymbols_setNaN;
F_DecimalFormatSymbols.prototype.f_getMinusSign = F_DecimalFormatSymbols_getMinusSign;
F_DecimalFormatSymbols.prototype.f_setMinusSign = F_DecimalFormatSymbols_setMinusSign;
F_DecimalFormatSymbols.prototype.f_getCurrencySymbol = F_DecimalFormatSymbols_getCurrencySymbol;
F_DecimalFormatSymbols.prototype.f_setCurrencySymbol = F_DecimalFormatSymbols_setCurrencySymbol;
F_DecimalFormatSymbols.prototype.f_getInternationalCurrencySymbol = F_DecimalFormatSymbols_getInternationalCurrencySymbol;
F_DecimalFormatSymbols.prototype.f_setInternationalCurrencySymbol = F_DecimalFormatSymbols_setInternationalCurrencySymbol;
F_DecimalFormatSymbols.prototype.f_getMonetaryDecimalSeparator = F_DecimalFormatSymbols_getMonetaryDecimalSeparator;
F_DecimalFormatSymbols.prototype.f_setMonetaryDecimalSeparator = F_DecimalFormatSymbols_setMonetaryDecimalSeparator;
F_DecimalFormatSymbols.prototype.f_getExponentialSymbol = F_DecimalFormatSymbols_getExponentialSymbol;
F_DecimalFormatSymbols.prototype.f_setExponentialSymbol = F_DecimalFormatSymbols_setExponentialSymbol;

/**
 * @class F_DecimalFormat
 * @method 
 *
 *
 *
 *
 *
 *
 *
 */
function F_DecimalFormatSymbols__init(args) {
	var locale = (args.length == 0)? F_Locale.F_getDefault():args[0];
	this._initialize(locale);
}

function F_DecimalFormatSymbols__initialize(locale) {
	this._locale = locale;
	this._decimalSeparator = ",";
	this._groupingSeparator = " ";
	this._patternSeparator = ";";
	this._percent = "%";
	this._zeroDigit = "0";
	this._digit = "#";
	this._minusSign = "-";
	this._exponential = "E";
	this._perMill = "\u2030"; // Per mille sign unicode
	this._infinity  = "?";
	this._NaN = "?";
	this._currencySymbol = "\u20AC"; // Euro currency symbol unicode
	this._intlCurrencySymbol = "EUR";
	this._monetarySeparator = this._decimalSeparator;
}

function F_DecimalFormatSymbols_getZeroDigit() {
	return this._zeroDigit;
}
function F_DecimalFormatSymbols_setZeroDigit(zeroDigit) {
	this._zeroDigit = zeroDigit;
}

function F_DecimalFormatSymbols_getGroupingSeparator() {
	return this._groupingSeparator;
}

function F_DecimalFormatSymbols_setGroupingSeparator(groupingSeparator) {
	this._groupingSeparator = groupingSeparator;
}

function F_DecimalFormatSymbols_getDecimalSeparator() {
	return this._decimalSeparator;
}

function F_DecimalFormatSymbols_setDecimalSeparator(decimalSeparator) {
	this._decimalSeparator = decimalSeparator;
}

function F_DecimalFormatSymbols_getPerMill() {
	return this._perMill;
}

function F_DecimalFormatSymbols_setPerMill(perMill) {
	this._perMill = perMill;
}

function F_DecimalFormatSymbols_getPercent() {
	return this._percent;
}

function F_DecimalFormatSymbols_setPercent(percent) {
	this._percent = percent;
}

function F_DecimalFormatSymbols_getDigit() {
	return this._digit;
}

function F_DecimalFormatSymbols_setDigit(digit) {
	this._digit = digit;
}

function F_DecimalFormatSymbols_getPatternSeparator() {
	return this._patternSeparator;
}

function F_DecimalFormatSymbols_setPatternSeparator(patternSeparator) {
	this._patternSeparator = patternSeparator;
}

function F_DecimalFormatSymbols_getInfinity() {
	return this._infinity;
}

function F_DecimalFormatSymbols_setInfinity(infinity) {
	this._infinity = infinity;
}

function F_DecimalFormatSymbols_getNaN() {
	return this._NaN;
}

function F_DecimalFormatSymbols_setNaN(NaN) {
	this._NaN = NaN;
}

function F_DecimalFormatSymbols_getMinusSign() {
	return this._minusSign;
}

function F_DecimalFormatSymbols_setMinusSign(minusSign) {
	this._minusSign = minusSign;
}

function F_DecimalFormatSymbols_getCurrencySymbol() {
	return this._currencySymbol;
}

function F_DecimalFormatSymbols_setCurrencySymbol(currency) {
	this._currencySymbol = currency;
}

function F_DecimalFormatSymbols_getInternationalCurrencySymbol() {
	return this._intlCurrencySymbol;
}

function F_DecimalFormatSymbols_setInternationalCurrencySymbol(currencyCode) {
	this._intlCurrencySymbol = currencyCode;
}

function F_DecimalFormatSymbols_getMonetaryDecimalSeparator() {
	return this._monetarySeparator;
}

function F_DecimalFormatSymbols_setMonetaryDecimalSeparator(sep) {
	this._monetarySeparator = sep;
}

function F_DecimalFormatSymbols_getExponentialSymbol() {
	return this._exponential;
}

function F_DecimalFormatSymbols_setExponentialSymbol(exp) {
	this._exponential = exp;
}
