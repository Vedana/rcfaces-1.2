/*
 * $Id$
 */

/**
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * DecimalFormat, NumberFormat java classes.
 *
 * The following class is a JavaScript adaptation
 *
 * @class hidden F_DecimalFormat
 */

/**
 * Class constructor
 *
 * @method hidden
 * @param pattern Cha�ne de caract�res facultatif
 * @param symbols F_DecimalFormatSymbols facultatif
 * @return
 * @see
 */
function F_DecimalFormat() {
	this._init(arguments);
};
F_DecimalFormat.prototype = new F_NumberFormat();

F_DecimalFormat.NO_ERROR						= 0;
F_DecimalFormat.MALFORMED_PATTERN				= 1;
F_DecimalFormat.UNQUOTED_SPECIAL_CHAR			= 2;
F_DecimalFormat.TOO_MANY_PERCENT				= 3;
F_DecimalFormat.UNEXPECTED_0					= 4
F_DecimalFormat.MULTIPLE_DECIMAL_SEPARATOR		= 5;
F_DecimalFormat.MULTIPLE_EXPONENT				= 6;
F_DecimalFormat.MALFORMED_EXPONENT				= 7; 
F_DecimalFormat.PARSE_ERROR						= 8;
F_DecimalFormat.PARSE_AMBIG						= 9;
F_DecimalFormat.PARSE_NO_DIGIT					= 10;
F_DecimalFormat.PARSE_UNCOMPLETE				= 11;
 
F_DecimalFormat._PATTERN_ZERO_DIGIT			= '0';
F_DecimalFormat._PATTERN_GROUPING_SEPARATOR	= ',';
F_DecimalFormat._PATTERN_DECIMAL_SEPARATOR	= '.';
F_DecimalFormat._PATTERN_PER_MILLE			= '\u2030';
F_DecimalFormat._PATTERN_PERCENT				= '%';
F_DecimalFormat._PATTERN_DIGIT				= '#';
F_DecimalFormat._PATTERN_SEPARATOR			= ';';
F_DecimalFormat._PATTERN_EXPONENT				= 'E';
F_DecimalFormat._PATTERN_MINUS				= '-';
F_DecimalFormat._CURRENCY_SIGN				= '\u00A4'; // �
F_DecimalFormat._QUOTE						= '\'';
    // Upper limit on integer and fraction digits for a Java double
F_DecimalFormat._DOUBLE_INTEGER_DIGITS		= 309;
F_DecimalFormat._DOUBLE_FRACTION_DIGITS		= 340;

F_DecimalFormat._STATUS_INFINITE = 0;
F_DecimalFormat._STATUS_POSITIVE = 1;

/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_DecimalFormat.prototype._init = F_DecimalFormat__init;
F_DecimalFormat.prototype._expandAffixes = F_DecimalFormat__expandAffixes;
F_DecimalFormat.prototype._expandAffix = F_DecimalFormat__expandAffix;
F_DecimalFormat.prototype._subparse = F_DecimalFormat__subparse;
F_DecimalFormat.prototype._subformat = F_DecimalFormat__subformat;

F_DecimalFormat.prototype._status = F_DecimalFormat.NO_ERROR;
F_DecimalFormat.prototype._digitList = new F_DigitList();
F_DecimalFormat.prototype._positivePrefix = "";
F_DecimalFormat.prototype._positiveSuffix = "";
F_DecimalFormat.prototype._negativePrefix = "-";
F_DecimalFormat.prototype._negativeSuffix = "";
F_DecimalFormat.prototype._posPrefixPattern = "";
F_DecimalFormat.prototype._posSuffixPattern = "";
F_DecimalFormat.prototype._negPrefixPattern = "";
F_DecimalFormat.prototype._negSuffixPattern = "";
F_DecimalFormat.prototype._multiplier = 1;
F_DecimalFormat.prototype._groupingSize = 3;  // invariant, > 0 if useThousands
F_DecimalFormat.prototype._decimalSeparatorAlwaysShown = false;
F_DecimalFormat.prototype._isCurrencyFormat = false;
F_DecimalFormat.prototype._symbols = null; // LIU new DecimalFormatSymbols();
F_DecimalFormat.prototype._useExponentialNotation = false;  // Newly persistent in the Java 2 platform
F_DecimalFormat.prototype._minExponentDigits = 0;       // Newly persistent in the Java 2 platform

F_DecimalFormat.prototype.f_getStatus = F_DecimalFormat_getStatus;
F_DecimalFormat.prototype.f_setStatus = F_DecimalFormat_setStatus;
F_DecimalFormat.prototype.f_isDecimalSeparatorAlwaysShown = F_DecimalFormat_isDecimalSeparatorAlwaysShown;
F_DecimalFormat.prototype.f_setDecimalSeparatorAlwaysShown = F_DecimalFormat_setDecimalSeparatorAlwaysShown;
F_DecimalFormat.prototype.f_getGroupingSize = F_DecimalFormat_getGroupingSize;
F_DecimalFormat.prototype.f_setGroupingSize = F_DecimalFormat_setGroupingSize;
F_DecimalFormat.prototype.f_getMultiplier = F_DecimalFormat_getMultiplier;
F_DecimalFormat.prototype.f_setMultiplier = F_DecimalFormat_setMultiplier;
F_DecimalFormat.prototype.f_setNegativeSuffix = F_DecimalFormat_setNegativeSuffix;
F_DecimalFormat.prototype.f_getNegativeSuffix = F_DecimalFormat_getNegativeSuffix;
F_DecimalFormat.prototype.f_getPositiveSuffix = F_DecimalFormat_getPositiveSuffix;
F_DecimalFormat.prototype.f_setPositiveSuffix = F_DecimalFormat_setPositiveSuffix;
F_DecimalFormat.prototype.f_parse = F_DecimalFormat_parse;
F_DecimalFormat.prototype.f_applyPattern = F_DecimalFormat_applyPattern;
F_DecimalFormat.prototype.f_applyLocalizedPattern = F_DecimalFormat_applyLocalizedPattern;
//F_DecimalFormat.prototype.f_format = F_DecimalFormat_format;
F_DecimalFormat.prototype.f_formatLong = F_DecimalFormat_formatLong;
F_DecimalFormat.prototype.f_formatDouble = F_DecimalFormat_formatDouble;


/**
 * @class F_DecimalFormat
 * @method _init
 * @decl private
 *
 * Ceci est le constructeur g�rant la surcharge
 *
 * @param args Le tableau d'arguments pass�s au constructeur
 * @return
 * @see F_DecimalFormat()
 */
function F_DecimalFormat__init(args) {
	var pattern = (args.length > 0)? args[0]:"";
	var symbols = (args.length > 1)? args[1]: new F_DecimalFormatSymbols();
	this._symbols = symbols;
	this.f_applyPattern(pattern, false);
}

function F_DecimalFormat_setStatus(status) {
	this._status = status;
}

function F_DecimalFormat_getStatus() {
	return this._status;
}

function F_DecimalFormat_applyLocalizedPattern(pattern) {
	this.f_applyPattern(pattern, true);
}

function F_DecimalFormat_isDecimalSeparatorAlwaysShown() {
	return this._decimalSeparatorAlwaysShown;
}

function F_DecimalFormat_setDecimalSeparatorAlwaysShown(newValue) {
	this._decimalSeparatorAlwaysShown = newValue;
}

function F_DecimalFormat_getGroupingSize() {
	return this._groupingSize;
}

function F_DecimalFormat_setGroupingSize(newValue) {
	this._groupingSize = newValue;
}

function F_DecimalFormat_getMultiplier() {
	return this._multiplier;
}

function F_DecimalFormat_setMultiplier(newValue) {
	this._multiplier = newValue;
}

function F_DecimalFormat_setNegativeSuffix(newValue) {
	this._negativeSuffix = newValue;
	this._negSuffixPattern = null;
}

function F_DecimalFormat_getNegativeSuffix() {
	return this._negativeSuffix;
}

function F_DecimalFormat_getPositiveSuffix() {
	return this._positiveSuffix;
}

function F_DecimalFormat_setPositiveSuffix(newValue) {
	this._positiveSuffix = newValue;
	this._posSuffixPattern = null;
}

// String text
// ParsePosition pos
function F_DecimalFormat_parse(text,pos) {
	// special case NaN
	if (text.regionMatches(false, pos.f_index, this._symbols.f_getNaN(),
		0, this._symbols.f_getNaN().length)) {
		pos.f_index = pos.f_index + this._symbols.f_getNaN().length;
		return Number.NaN; //new Double(Double.NaN);
	}

	//boolean[] status = new boolean[STATUS_LENGTH];
	var status = new Object();
	status.boole = [false, false];

	if (!this._subparse(text, pos, this._digitList, false, status))
		return null;

	// Check if parse complete for strict parsing
	if (pos.f_index != text.length) {
		this.f_setStatus(F_DecimalFormat.PARSE_UNCOMPLETE);
		return null;
	}
	var doubleResult = 0.0; // double
	var longResult = 0; // long
	var gotDouble = true; // boolean

	// Finally, have DigitList parse the digits into a value.
	if (status.boole[F_DecimalFormat._STATUS_INFINITE]) {
		doubleResult = Number.POSITIVE_INFINITY;
	} else if (this._digitList.f_fitsIntoLong(status.boole[F_DecimalFormat._STATUS_POSITIVE], this.f_isParseIntegerOnly())) {
		gotDouble = false;
		longResult = this._digitList.f_getLong();
	} else {
		doubleResult = this._digitList.f_getDouble();
	}
	// Divide by multiplier. We have to be careful here not to do unneeded
	// conversions between double and long.
	if (this._multiplier != 1) {
		if (gotDouble) {
			doubleResult /= this._multiplier;
		} else {
			// Avoid converting to double if we can
			if (longResult % this._multiplier == 0) {
				longResult /= this._multiplier;
			} else {
				doubleResult = longResult / this._multiplier;
				if (doubleResult < 0) doubleResult = -doubleResult;
				gotDouble = true;
			}
		}
	}
	if (!status.boole[F_DecimalFormat._STATUS_POSITIVE]) {
		doubleResult = -doubleResult;
		// If longResult was Long.MIN_VALUE or a divisor of it (if
		// multiplier != 1) then don't negate it.
		if (longResult > 0) {
			longResult = -longResult;
		}
	}
	// At this point, if we divided the result by the multiplier, the result may
	// fit into a long.  We check for this case and return a long if possible.
	// We must do this AFTER applying the negative (if appropriate) in order to
	// handle the case of LONG_MIN; otherwise, if we do this with a positive value
	// -LONG_MIN, the double is > 0, but the long is < 0.  This is a C++-specific
	// situation.  We also must retain a double in the case of -0.0, which will
	// compare as == to a long 0 cast to a double (bug 4162852).
	if (this._multiplier != 1 && gotDouble) {
		longResult = Math.floor(doubleResult); //(long)doubleResult;
		gotDouble = (doubleResult != longResult) || 
		(doubleResult == 0.0 &&
		!status.boole[F_DecimalFormat._STATUS_POSITIVE] &&
		!this.f_isParseIntegerOnly());
	}
	this.f_setStatus(F_DecimalFormat.NO_ERROR);
	var wrapper = new Object();
	wrapper.f_type = (gotDouble)? "double":"long";
	wrapper.f_value = (gotDouble) ? new Number(doubleResult):new Number(longResult);
	return wrapper;
}

function F_DecimalFormat__subparse(text,parsePosition,digits,isExponent,status) {
	var position = parsePosition.f_index;
	var oldStart = parsePosition.f_index;
	var backup;

	// check for positivePrefix; take longest
	var gotPositive = text.regionMatches(false,position,this._positivePrefix,0,
		this._positivePrefix.length);
	var gotNegative = text.regionMatches(false,position,this._negativePrefix,0,
		this._negativePrefix.length);
	if (gotPositive && gotNegative) {
		if (this._positivePrefix.length > this._negativePrefix.length)
			gotNegative = false;
		else if (this._positivePrefix.length < this._negativePrefix.length)
			gotPositive = false;
	}
	if (gotPositive) {
		position += this._positivePrefix.length;
	} else if (gotNegative) {
		position += this._negativePrefix.length;
	} else {
		parsePosition.f_errorIndex = position;
		this.f_setStatus(F_DecimalFormat.PARSE_AMBIG);
		return false;
	}
	// process digits or Inf, find decimal position
	status.boole[F_DecimalFormat._STATUS_INFINITE] = false;
	if (!isExponent && text.regionMatches(false,position,this._symbols.f_getInfinity(),0,
		this._symbols.f_getInfinity().length)) {
			position += this._symbols.getInfinity().length;
			status.boole[F_DecimalFormat._STATUS_INFINITE] = true;
	} else {
		// We now have a string of digits, possibly with grouping symbols,
		// and decimal points.  We want to process these into a DigitList.
		// We don't want to put a bunch of leading zeros into the DigitList
		// though, so we keep track of the location of the decimal point,
		// put only significant digits into the DigitList, and adjust the
		// exponent as needed.

		digits.f_decimalAt = digits.f_count = 0;
		var zero = this._symbols.f_getZeroDigit();
		var decimal = this._isCurrencyFormat ?
		this._symbols.f_getMonetaryDecimalSeparator() : this._symbols.f_getDecimalSeparator();
		var grouping = this._symbols.f_getGroupingSeparator();
		var exponentChar = this._symbols.f_getExponentialSymbol();
		var sawDecimal = false;
		var sawExponent = false;
		var sawDigit = false;
		var exponent = 0; // Set to the exponent value, if any

		// We have to track digitCount ourselves, because digits.count will
		// pin when the maximum allowable digits is reached.
		var digitCount = 0;

		backup = -1;
		for (; position < text.length; ++position) {
			var ch = text.charAt(position);

			/* We recognize all digit ranges, not only the Latin digit range
			* '0'..'9'.  We do so by using the Character.digit() method,
			* which converts a valid Unicode digit to the range 0..9.
			*
			* The character 'ch' may be a digit.  If so, place its value
			* from 0 to 9 in 'digit'.  First try using the locale digit,
			* which may or MAY NOT be a standard Unicode digit range.  If
			* this fails, try using the standard Unicode digit ranges by
			* calling Character.digit().  If this also fails, digit will
			* have a value outside the range 0..9.
			*/
			var digit = ch.charCodeAt(0) - zero.charCodeAt(0);
			//if (digit < 0 || digit > 9) digit = Character.digit(ch, 10);

			if (digit == 0) {
				// Cancel out backup setting (see grouping handler below)
				backup = -1; // Do this BEFORE continue statement below!!!
				sawDigit = true;

				// Handle leading zeros
				if (digits.f_count == 0) {
					// Ignore leading zeros in integer part of number.
					if (!sawDecimal) continue;

					// If we have seen the decimal, but no significant digits yet,
					// then we account for leading zeros by decrementing the
					// digits.decimalAt into negative values.
					--digits.f_decimalAt;
				} else {
					++digitCount;
					digits.f_append(String.fromCharCode(digit + zero.charCodeAt(0)));
				}
			} else if (digit > 0 && digit <= 9) { // [sic] digit==0 handled above
				sawDigit = true;
				++digitCount;
				digits.f_append(String.fromCharCode(digit + zero.charCodeAt(0)));

				// Cancel out backup setting (see grouping handler below)
				backup = -1;
			} else if (!isExponent && ch == decimal) {
				// If we're only parsing integers, or if we ALREADY saw the
				// decimal, then don't parse this one.
				if (this.f_isParseIntegerOnly() || sawDecimal) break;
				digits.f_decimalAt = digitCount; // Not digits.count!
				sawDecimal = true;
			} else if (!isExponent && ch == grouping && this.f_isGroupingUsed()) {
				if (sawDecimal) {
					break;
				}
				// Ignore grouping characters, if we are using them, but require
				// that they be followed by a digit.  Otherwise we backup and
				// reprocess them.
				backup = position;
			} else if (!isExponent && ch == exponentChar && !sawExponent) {
				// Process the exponent by recursively calling this method.
				var pos = new F_ParsePosition(position + 1);
				//boolean[] stat = new boolean[STATUS_LENGTH];
				var stat = new Object();
				stat.boole = [false, false];
				var exponentDigits = new F_DigitList();
				if (this._subparse(text, pos, exponentDigits, true, stat) &&
					exponentDigits.f_fitsIntoLong(stat[F_DecimalFormat._STATUS_POSITIVE], true)) {
					position = pos.f_index; // Advance past the exponent
					exponent = exponentDigits.f_getLong();
					if (!stat.boole[F_DecimalFormat._STATUS_POSITIVE]) exponent = -exponent;
					sawExponent = true;
				}
				break; // Whether we fail or succeed, we exit this loop
			}
			else break;
		}

		if (backup != -1) position = backup;

		// If there was no decimal point we have an integer
		if (!sawDecimal) digits.f_decimalAt = digitCount; // Not digits.count!

		// Adjust for exponent, if any
		digits.f_decimalAt += exponent;

		// If none of the text string was recognized.  For example, parse
		// "x" with pattern "#0.00" (return index and error index both 0)
		// parse "$" with pattern "$#0.00". (return index 0 and error index
		// 1).
		if (!sawDigit && digitCount == 0) {
			parsePosition.f_index = oldStart;
			parsePosition.f_errorIndex = oldStart;
			this.f_setStatus(F_DecimalFormat.PARSE_NO_DIGIT);
			return false;
		}
	}

	// check for positiveSuffix
	if (gotPositive)
		gotPositive = text.regionMatches(false, position,this._positiveSuffix,0,
			this._positiveSuffix.length);
	if (gotNegative)
		gotNegative = text.regionMatches(false,position,this._negativeSuffix,0,
			this._negativeSuffix.length);

	// if both match, take longest
	if (gotPositive && gotNegative) {
		if (this._positiveSuffix.length > this._negativeSuffix.length)
			gotNegative = false;
		else if (this._positiveSuffix.length < this._negativeSuffix.length)
			gotPositive = false;
	}

	// fail if neither or both
	if (gotPositive == gotNegative) {
		parsePosition.f_errorIndex = position;
		this.f_setStatus(F_DecimalFormat.PARSE_AMBIG);
		return false;
	}

	parsePosition.f_index = position +
		(gotPositive ? this._positiveSuffix.length : this._negativeSuffix.length); // mark success!

	status.boole[F_DecimalFormat._STATUS_POSITIVE] = gotPositive;
	if (parsePosition.f_index == oldStart) {
		parsePosition.f_errorIndex = position;
		this.f_setStatus(F_DecimalFormat.PARSE_ERROR);
		return false;
	}
	return true;
}

function F_DecimalFormat_applyPattern(varargs) {
	var pattern = arguments[0];
	var localized = (arguments.length > 1)? arguments[1]:false;

	var zeroDigit         = F_DecimalFormat._PATTERN_ZERO_DIGIT;
	var groupingSeparator = F_DecimalFormat._PATTERN_GROUPING_SEPARATOR;
	var decimalSeparator  = F_DecimalFormat._PATTERN_DECIMAL_SEPARATOR;
	var percent           = F_DecimalFormat._PATTERN_PERCENT;
	var perMill           = F_DecimalFormat._PATTERN_PER_MILLE;
	var digit             = F_DecimalFormat._PATTERN_DIGIT;
	var separator         = F_DecimalFormat._PATTERN_SEPARATOR;
	var exponent          = F_DecimalFormat._PATTERN_EXPONENT;
	var minus             = F_DecimalFormat._PATTERN_MINUS;
	if (localized) {
		zeroDigit         = this._symbols.f_getZeroDigit();
		groupingSeparator = this._symbols.f_getGroupingSeparator();
		decimalSeparator  = this._symbols.f_getDecimalSeparator();
		percent           = this._symbols.f_getPercent();
		perMill           = this._symbols.f_getPerMill();
		digit             = this._symbols.f_getDigit();
		separator         = this._symbols.f_getPatternSeparator();
		exponent          = this._symbols.f_getExponentialSymbol();
		minus             = this._symbols.f_getMinusSign();
	}
	var gotNegative = false;

	this._decimalSeparatorAlwaysShown = false;
	this._isCurrencyFormat = false;
	this._useExponentialNotation = false;

	// Two variables are used to record the subrange of the pattern
	// occupied by phase 1.  This is used during the processing of the
	// second pattern (the one representing negative numbers) to ensure
	// that no deviation exists in phase 1 between the two patterns.
	var phaseOneStart = 0;
	var phaseOneLength = 0;
	/** Back-out comment : HShih
	* boolean phaseTwo = false;
	*/

	var start = 0;
	for (var j = 1; j >= 0 && start < pattern.length; --j) {
		var inQuote = false;
		var prefix = new F_StringBuffer();
		var suffix = new F_StringBuffer();
		var decimalPos = -1;
		var multiplier = 1;
		var digitLeftCount = 0;
		var zeroDigitCount = 0;
		var digitRightCount = 0;
		var groupingCount = -1;

            // The phase ranges from 0 to 2.  Phase 0 is the prefix.  Phase 1 is
            // the section of the pattern with digits, decimal separator,
            // grouping characters.  Phase 2 is the suffix.  In phases 0 and 2,
            // percent, permille, and currency symbols are recognized and
            // translated.  The separation of the characters into phases is
            // strictly enforced; if phase 1 characters are to appear in the
            // suffix, for example, they must be quoted.
		var phase = 0;

            // The affix is either the prefix or the suffix.
		var affix = prefix; // StringBuffer

		for (var pos = start; pos < pattern.length; ++pos) {
			var ch = pattern.charAt(pos);
			switch (phase) {
				case 0:
				case 2:
					// Process the prefix / suffix characters
					if (inQuote) {
						// A quote within quotes indicates either the closing
						// quote or two quotes, which is a quote literal.  That is,
						// we have the second quote in 'do' or 'don''t'.
						if (ch == F_DecimalFormat._QUOTE) {
							if ((pos+1) < pattern.length &&
								pattern.charAt(pos+1) == F_DecimalFormat._QUOTE) {
								++pos;
								affix.f_append("''"); // 'don''t'
							} else {
								inQuote = false; // 'do'
							}
							continue;
						}
					} else {
						// Process unquoted characters seen in prefix or suffix
						// phase.
						if (ch == digit || ch == zeroDigit ||
							ch == groupingSeparator || ch == decimalSeparator) {
							// Any of these characters implicitly begins the next
							// phase.  If we are in phase 2, there is no next phase,
							// so these characters are illegal.
							/**
							* 1.2 Back-out comment : HShih
							* Can't throw exception here.
							* if (phase == 2)
							*    throw new IllegalArgumentException("Unquoted special character '" +
							*                   ch + "' in pattern \"" +
							*                   pattern + '"');
							*/
							phase = 1;
							if (j == 1) phaseOneStart = pos;
							--pos; // Reprocess this character
							continue;
						} else if (ch == F_DecimalFormat._CURRENCY_SIGN) {
							// Use lookahead to determine if the currency sign is
							// doubled or not.
							var doubled = (pos + 1) < pattern.length &&
								pattern.charAt(pos + 1) == F_DecimalFormat._CURRENCY_SIGN;
							if (doubled) ++pos; // Skip over the doubled character
							this._isCurrencyFormat = true;
							affix.f_append(doubled ? "'\u00A4\u00A4" : "'\u00A4");
							continue;
						} else if (ch == F_DecimalFormat._QUOTE) {
							// A quote outside quotes indicates either the opening
							// quote or two quotes, which is a quote literal.  That is,
							// we have the first quote in 'do' or o''clock.
							if (ch == F_DecimalFormat._QUOTE) {
								if ((pos+1) < pattern.length &&
									pattern.charAt(pos+1) == F_DecimalFormat._QUOTE) {
									++pos;
									affix.concat("''"); // o''clock
								} else {
									inQuote = true; // 'do'
								}
								continue;
							}
						} else if (ch == separator) {
							// Don't allow separators before we see digit characters of phase
							// 1, and don't allow separators in the second pattern (j == 0).
							if (phase == 0 || j == 0) {
								//throw new IllegalArgumentException("Unquoted special character '" +
								//	ch + "' in pattern \"" + pattern + '"');
								this.f_setStatus(F_DecimalFormat.UNQUOTED_SPECIAL_CHAR);
								return;
							}
							start = pos + 1;
							pos = pattern.length;
							continue;
						} else if (ch == percent) {
							// Next handle characters which are appended directly.
							if (multiplier != 1) {
								//throw new IllegalArgumentException("Too many percent/permille characters in pattern \"" +
								//	pattern + '"');
								this._status = F_DecimalFormat.TOO_MANY_PERCENT;
								return;
							}
							multiplier = 100;
							affix.concat("'%");
							continue;
						} else if (ch == perMill) {
							if (multiplier != 1) {
								//throw new IllegalArgumentException("Too many percent/permille characters in pattern \"" +
								//pattern + '"');
								this.f_setStatus(F_DecimalFormat.TOO_MANY_PERCENT);
								return;
							}
							multiplier = 1000;
							affix.concat("'\u2030");
							continue;
						} else if (ch == minus) {
							affix.f_append("'-");
							continue;
						}
					}
					// Note that if we are within quotes, or if this is an unquoted,
					// non-special character, then we usually fall through to here.
					affix.f_append(ch);
					break;
				case 1:
					// Phase one must be identical in the two sub-patterns.  We
					// enforce this by doing a direct comparison.  While
					// processing the first sub-pattern, we just record its
					// length.  While processing the second, we compare
					// characters.
					if (j == 1) ++phaseOneLength;
					else {
						/**
						* 1.2 Back-out comment : HShih
						* if (ch != pattern.charAt(phaseOneStart++))
						*    throw new IllegalArgumentException("Subpattern mismatch in \"" +
						*                       pattern + '"');
						* phaseTwo = true;
						*/
						if (--phaseOneLength == 0) {
							phase = 2;
							affix = suffix;
						}
						continue;
					}

					// Process the digits, decimal, and grouping characters.  We
					// record five pieces of information.  We expect the digits
					// to occur in the pattern ####0000.####, and we record the
					// number of left digits, zero (central) digits, and right
					// digits.  The position of the last grouping character is
					// recorded (should be somewhere within the first two blocks
					// of characters), as is the position of the decimal point,
					// if any (should be in the zero digits).  If there is no
					// decimal point, then there should be no right digits.
					if (ch == digit) {
						if (zeroDigitCount > 0) ++digitRightCount; else ++digitLeftCount;
						if (groupingCount >= 0 && decimalPos < 0) ++groupingCount;
					} else if (ch == zeroDigit) {
						if (digitRightCount > 0) {
							//throw new IllegalArgumentException("Unexpected '0' in pattern \"" +
							//pattern + '"');
							this.f_setStatus(F_DecimalFormat.UNEXPECTED_0);
							return;
						}
						++zeroDigitCount;
						if (groupingCount >= 0 && decimalPos < 0) ++groupingCount;
					} else if (ch == groupingSeparator) {
						groupingCount = 0;
					} else if (ch == decimalSeparator) {
						if (decimalPos >= 0) {
							//throw new IllegalArgumentException("Multiple decimal separators in pattern \"" +
							//pattern + '"');
							this.f_setStatus(F_DecimalFormat.MULTIPLE_DECIMAL_SEPARATOR);
							return;
						}
						decimalPos = digitLeftCount + zeroDigitCount + digitRightCount;
					} else if (ch == exponent) {
						if (this._useExponentialNotation) {
							//throw new IllegalArgumentException("Multiple exponential " +
							//"symbols in pattern \"" +
							//pattern + '"');
							this.f_setStatus(F_DecimalFormat.MULTIPLE_EXPONENT);
							return;
						}
						this._useExponentialNotation = true;
						this._minExponentDigits = 0;

						// Use lookahead to parse out the exponential part of the
						// pattern, then jump into phase 2.
						while (++pos < pattern.length &&
							pattern.charAt(pos) == zeroDigit) {
							++this._minExponentDigits;
							++phaseOneLength;
						}

						if ((digitLeftCount + zeroDigitCount) < 1 ||
							this._minExponentDigits < 1) {
							//throw new IllegalArgumentException("Malformed exponential " +
							//"pattern \"" +
							//pattern + '"');
							this.f_setStatus(F_DecimalFormat.MALFORMED_EXPONENT);
							return;
						}

						// Transition to phase 2
						phase = 2;
						affix = suffix;
						--pos;
						continue;
					} else {
						phase = 2;
						affix = suffix;
						--pos;
						--phaseOneLength;
						continue;
					}
					break;
			}
		} // switch
		/*
		* 1.2 Back-out comment : HShih
		* if (phaseTwo && phaseOneLength > 0)
		*      throw new IllegalArgumentException("Subpattern mismatch in \"" +
		*                                   pattern + '"');
		*/
		// Handle patterns with no '0' pattern character.  These patterns
		// are legal, but must be interpreted.  "##.###" -> "#0.###".
		// ".###" -> ".0##".
		/* We allow patterns of the form "####" to produce a zeroDigitCount of
		* zero (got that?); although this seems like it might make it possible
		* for format() to produce empty strings, format() checks for this
		* condition and outputs a zero digit in this situation.  Having a
		* zeroDigitCount of zero yields a minimum integer digits of zero, which
		* allows proper round-trip patterns.  That is, we don't want "#" to
		* become "#0" when toPattern() is called (even though that's what it
		* really is, semantically).  */
		if (zeroDigitCount == 0 && digitLeftCount > 0 &&
			decimalPos >= 0) {
			// Handle "###.###" and "###." and ".###"
			var n =  decimalPos;
			if (n == 0) ++n; // Handle ".###"
			digitRightCount = digitLeftCount - n;
			digitLeftCount = n - 1;
			zeroDigitCount = 1;
		}
			// Do syntax checking on the digits.
		if ((decimalPos < 0 && digitRightCount > 0) ||
			(decimalPos >= 0 &&
			(decimalPos < digitLeftCount ||
			decimalPos > (digitLeftCount + zeroDigitCount))) ||
			groupingCount == 0 ||
			inQuote) {
			//throw new IllegalArgumentException("Malformed pattern \"" +
			//	pattern + '"');
			this.f_setStatus(F_DecimalFormat.MALFORMED_PATTERN);
			return;
		}
		if (j == 1) {
			this._posPrefixPattern = prefix.toString();
			this._posSuffixPattern = suffix.toString();
			this._negPrefixPattern = this._posPrefixPattern;   // assume these for now
			this._negSuffixPattern = this._posSuffixPattern;
			var digitTotalCount = digitLeftCount + zeroDigitCount + digitRightCount;
			/* The effectiveDecimalPos is the position the decimal is at or
			* would be at if there is no decimal.  Note that if decimalPos<0,
			* then digitTotalCount == digitLeftCount + zeroDigitCount.  */
			var effectiveDecimalPos = decimalPos >= 0 ? decimalPos : digitTotalCount;
			this.f_setMinimumIntegerDigits(effectiveDecimalPos - digitLeftCount);
			this.f_setMaximumIntegerDigits(this._useExponentialNotation ?
				digitLeftCount + this.f_getMinimumIntegerDigits() : F_DecimalFormat._DOUBLE_INTEGER_DIGITS);
			this.f_setMaximumFractionDigits(decimalPos >= 0 ? (digitTotalCount - decimalPos) : 0);
			this.f_setMinimumFractionDigits(decimalPos >= 0 ?
				(digitLeftCount + zeroDigitCount - decimalPos) : 0);
			this.f_setGroupingUsed(groupingCount > 0);
			this._groupingSize = (groupingCount > 0) ? groupingCount : 0;
			this._multiplier = multiplier;
			this.f_setDecimalSeparatorAlwaysShown(decimalPos == 0 || decimalPos == digitTotalCount);
		} else {
			this._negPrefixPattern = prefix.toString();
			this._negSuffixPattern = suffix.toString();
			gotNegative = true;
		}
	}
	if (pattern.length == 0) {
		this._posPrefixPattern = this._posSuffixPattern = "";
		this.f_setMinimumIntegerDigits(0);
		this.f_setMaximumIntegerDigits(F_DecimalFormat._DOUBLE_INTEGER_DIGITS);
		this.f_setMinimumFractionDigits(0);
		this.f_setMaximumFractionDigits(F_DecimalFormat._DOUBLE_FRACTION_DIGITS);
	}
	// If there was no negative pattern, or if the negative pattern is identical
	// to the positive pattern, then prepend the minus sign to the positive
	// pattern to form the negative pattern.
	if ((!gotNegative) ||
		(this._negPrefixPattern == this._posPrefixPattern &&
		this._negSuffixPattern == this._posSuffixPattern)) {
		this._negSuffixPattern = this._posSuffixPattern;
		this._negPrefixPattern = "'-" + this._posPrefixPattern;
	}

	this._expandAffixes();
	this.f_setStatus(F_DecimalFormat.NO_ERROR);
}

function F_DecimalFormat__expandAffixes() {
	var buffer = new F_StringBuffer();

	if (this._posPrefixPattern != null) {
		this._positivePrefix = this._expandAffix(this._posPrefixPattern, buffer);
		this._positivePrefixFieldPositions = null;
	}
	if (this._posSuffixPattern != null) {
		this._positiveSuffix = this._expandAffix(this._posSuffixPattern, buffer);
		this._positiveSuffixFieldPositions = null;
	}
	if (this._negPrefixPattern != null) {
		this._negativePrefix = this._expandAffix(this._negPrefixPattern, buffer);
		this._negativePrefixFieldPositions = null;
	}
	if (this._negSuffixPattern != null) {
		this._negativeSuffix = this._expandAffix(this._negSuffixPattern, buffer);
		this._negativeSuffixFieldPositions = null;
	}
}

function F_DecimalFormat__expandAffix(pattern, buffer) {
	buffer.f_setLength(0);
	for (var i=0; i<pattern.length;) {
		var c = pattern.charAt(i++);
		if (c == F_DecimalFormat._QUOTE) {
			c = pattern.charAt(i++);
			switch (c) {
				case F_DecimalFormat._CURRENCY_SIGN:
					if (i<pattern.length &&
						pattern.charAt(i) == F_DecimalFormat._CURRENCY_SIGN) {
						++i;
						buffer.f_append(this._symbols.f_getInternationalCurrencySymbol());
					} else {
						buffer.f_append(this._symbols.f_getCurrencySymbol());
					}
					continue;
				case F_DecimalFormat._PATTERN_PERCENT:
					c = this._symbols.f_getPercent();
					break;
				case F_DecimalFormat._PATTERN_PER_MILLE:
					c = this._symbols.f_getPerMill();
					break;
				case F_DecimalFormat._PATTERN_MINUS:
					c = this._symbols.f_getMinusSign();
					break;
			}
		}
		buffer.f_append(c);
	}
	return buffer.toString();
}

function F_DecimalFormat_formatDouble(number, result) {
	if (number == Number.NaN) {
		result.f_append(this._symbols.f_getNaN());
		return result;
	}

	/* Detecting whether a double is negative is easy with the exception of
	* the value -0.0.  This is a double which has a zero mantissa (and
	* exponent), but a negative sign bit.  It is semantically distinct from
	* a zero with a positive sign bit, and this distinction is important
	* to certain kinds of computations.  However, it's a little tricky to
	* detect, since (-0.0 == 0.0) and !(-0.0 < 0.0).  How then, you may
	* ask, does it behave distinctly from +0.0?  Well, 1/(-0.0) ==
	* -Infinity.  Proper detection of -0.0 is needed to deal with the
	* issues raised by bugs 4106658, 4106667, and 4147706.  Liu 7/6/98.
	*/
	var isNegative = (number < 0.0) || (number == 0.0 && 1/number < 0.0);
	if (isNegative) number = -number;

	// Do this BEFORE checking to see if value is infinite!
	if (this._multiplier != 1) number *= this._multiplier;

	if (number == Number.NEGATIVE_INFINITY || number == Number.POSITIVE_INFINITY) {
		if (isNegative) {
			result.f_append(this._negativePrefix);
		} else {
			result.f_append(this._positivePrefix);
		}
		result.f_append(this._symbols.f_getInfinity());
		if (isNegative) {
			result.f_append(this._negativeSuffix);
		} else {
			result.f_append(this._positiveSuffix);
		}
		return result;
	}

	// At this point we are guaranteed a nonnegative finite
	// number.
	this._digitList.f_setDouble(number, this._useExponentialNotation ?
		this.f_getMaximumIntegerDigits() + this.f_getMaximumFractionDigits() :
		this.f_getMaximumFractionDigits(), !this._useExponentialNotation);

	return this._subformat(result, isNegative, false);
}

function F_DecimalFormat_formatLong(number,result) {
	var isNegative = (number < 0);
	if (isNegative) number = -number;

	// In general, long values always represent real finite numbers, so
	// we don't have to check for +/- Infinity or NaN.  However, there
	// is one case we have to be careful of:  The multiplier can push
	// a number near MIN_VALUE or MAX_VALUE outside the legal range.  We
	// check for this before multiplying, and if it happens we use doubles
	// instead, trading off accuracy for range.
	if (this._multiplier != 1 && this._multiplier != 0) {
		var useDouble = false;
		// This can only happen if number == Long.MIN_VALUE
		if (number < 0) {
			var cutoff = Number.MIN_VALUE / this._multiplier;
			useDouble = (number < cutoff);
		} else {
			var cutoff = Number.MAX_VALUE / this._multiplier;
			useDouble = (number > cutoff);
		}
		if (useDouble) {
			var dnumber = (isNegative ? -number : number);
			return this.f_formatDouble(dnumber, result);
		}
	}
	number *= this._multiplier;
	this._digitList.f_setLong(number, this._useExponentialNotation ?
		this.f_getMaximumIntegerDigits() + this.f_getMaximumFractionDigits() : 0);

	return this._subformat(result, isNegative, true);
}


function F_DecimalFormat__subformat(result,isNegative,isInteger) {
	var zero = this._symbols.f_getZeroDigit();
	//var zeroDelta = zero.charCodeAt(0) - "0".charCodeAt(0); 
	var grouping = this._symbols.f_getGroupingSeparator();
	var decimal = this._isCurrencyFormat ?
		this._symbols.f_getMonetaryDecimalSeparator() :
		this._symbols.f_getDecimalSeparator();

	if (this._digitList.f_isZero()) {
		this._digitList.f_decimalAt = 0; // Normalize
	}
	//var fieldStart = result.length();

	if (isNegative) {
		result.f_append(this._negativePrefix);
	} else {
		result.f_append(this._positivePrefix);
	}
	if (this._useExponentialNotation) {
		//var iFieldStart = result.f_length();
		//var iFieldEnd = -1;
		//var fFieldStart = -1;

		// Minimum integer digits are handled in exponential format by
		// adjusting the exponent.  For example, 0.01234 with 3 minimum
		// integer digits is "123.4E-4".

		// Maximum integer digits are interpreted as indicating the
		// repeating range.  This is useful for engineering notation, in
		// which the exponent is restricted to a multiple of 3.  For
		// example, 0.01234 with 3 maximum integer digits is "12.34e-3".
		// If maximum integer digits are > 1 and are larger than
		// minimum integer digits, then minimum integer digits are
		// ignored.
		var exponent = this._digitList.f_decimalAt;
		var repeat = this.f_getMaximumIntegerDigits();
		var minimumIntegerDigits = this.f_getMinimumIntegerDigits();
		if (repeat > 1 && repeat > minimumIntegerDigits) {
			// A repeating range is defined; adjust to it as follows.
			// If repeat == 3, we have 6,5,4=>3; 3,2,1=>0; 0,-1,-2=>-3;
			// -3,-4,-5=>-6, etc. This takes into account that the
			// exponent we have here is off by one from what we expect;
			// it is for the format 0.MMMMMx10^n.
			if (exponent >= 1) {
				exponent = ((exponent - 1) / repeat) * repeat;
			} else {
				// integer division rounds towards 0
				exponent = ((exponent - repeat) / repeat) * repeat;
			}
			minimumIntegerDigits = 1;
		} else {
			// No repeating range is defined; use minimum integer digits.
			exponent -= minimumIntegerDigits;
		}
		// We now output a minimum number of digits, and more if there
		// are more digits, up to the maximum number of digits.  We
		// place the decimal point after the "integer" digits, which
		// are the first (decimalAt - exponent) digits.
		var minimumDigits = this.f_getMinimumIntegerDigits()
			+ this.f_getMinimumFractionDigits();
		// The number of integer digits is handled specially if the number
		// is zero, since then there may be no digits.
		var integerDigits = this._digitList.f_isZero() ? minimumIntegerDigits :
			this._digitList.f_decimalAt - exponent;
		if (minimumDigits < integerDigits) {
			minimumDigits = integerDigits;
		}
		var totalDigits = this._digitList.f_count;
		if (minimumDigits > totalDigits) totalDigits = minimumDigits;
		var addedDecimalSeparator = false;

		for (var i=0; i<totalDigits; ++i) {
			if (i == integerDigits) {
				result.f_append(decimal);
				addedDecimalSeparator = true;
			}
			result.f_append((i < this._digitList.f_count) ?
				this._digitList.f_digits[i]:zero);
		}

		// The exponent is output using the pattern-specified minimum
		// exponent digits.  There is no maximum limit to the exponent
		// digits, since truncating the exponent would result in an
		// unacceptable inaccuracy.
		result.f_append(this._symbols.f_getExponentialSymbol());
		// For zero values, we force the exponent to zero.  We
		// must do this here, and not earlier, because the value
		// is used to determine integer digit count above.
		if (this._digitList.f_isZero()) exponent = 0;

		var negativeExponent = (exponent < 0);
		if (negativeExponent) {
			exponent = -exponent;
			result.f_append(this._negativePrefix);
		} else {
			result.f_append(this._positivePrefix);
		}
		this._digitList.f_setLong(exponent);

		for (var i=this._digitList.f_decimalAt; i<this._minExponentDigits; ++i) 
			result.f_append(zero);
		for (var i=0; i<this._digitList.f_decimalAt; ++i) {
			result.f_append((i < this._digitList.f_count) ?
				this._digitList.f_digits[i]: zero);
		}
		if (negativeExponent) {
			result.f_append(this._negativeSuffix);
		} else {
			result.f_append(this._positiveSuffix);
		}
	} else {
		// No exponential notation
		// Output the integer portion.  Here 'count' is the total
		// number of integer digits we will display, including both
		// leading zeros required to satisfy getMinimumIntegerDigits,
		// and actual digits present in the number.
		var count = this.f_getMinimumIntegerDigits();
		var digitIndex = 0; // Index into digitList.fDigits[]
		if (this._digitList.f_decimalAt > 0 && count < this._digitList.f_decimalAt)
			count = this._digitList.f_decimalAt;

		// Handle the case where getMaximumIntegerDigits() is smaller
		// than the real number of integer digits.  If this is so, we
		// output the least significant max integer digits.  For example,
		// the value 1997 printed with 2 max integer digits is just "97".

		if (count > this.f_getMaximumIntegerDigits()) {
			count = this.f_getMaximumIntegerDigits();
			digitIndex = this._digitList.f_decimalAt - count;
		}

		var sizeBeforeIntegerPart = result.f_length();
		for (var i=count-1; i>=0; --i) {
			if (i < this._digitList.f_decimalAt && digitIndex < this._digitList.f_count) {
				// Output a real digit
				result.f_append(this._digitList.f_digits[digitIndex++]);
			} else {
				// Output a leading zero
				result.f_append(zero);
			}

			// Output grouping separator if necessary.  Don't output a
			// grouping separator if i==0 though; that's at the end of
			// the integer part.
			if (this.f_isGroupingUsed() && i>0 && (this._groupingSize != 0) && (i % this._groupingSize == 0)) {
				result.f_append(grouping);
			}
		}

		// Determine whether or not there are any printable fractional
		// digits.  If we've used up the digits we know there aren't.
		var fractionPresent = (this.f_getMinimumFractionDigits() > 0) ||
			(!isInteger && digitIndex < this._digitList.f_count);

		// If there is no fraction present, and we haven't printed any
		// integer digits, then print a zero.  Otherwise we won't print
		// _any_ digits, and we won't be able to parse this string.
		if (!fractionPresent && result.f_length() == sizeBeforeIntegerPart) {
			result.f_append(zero);
		}

		// Output the decimal separator if we always do so.
		var sStart = result.f_length();
		if (this._decimalSeparatorAlwaysShown || fractionPresent)
			result.f_append(decimal);

		for (var i=0; i < this.f_getMaximumFractionDigits(); ++i) {
			// Here is where we escape from the loop.  We escape if we've output
			// the maximum fraction digits (specified in the for expression above).
			// We also stop when we've output the minimum digits and either:
			// we have an integer, so there is no fractional stuff to display,
			// or we're out of significant digits.
			if (i >= this.f_getMinimumFractionDigits() &&
				(isInteger || digitIndex >= this._digitList.f_count))
				break;

			// Output leading fractional zeros.  These are zeros that come after
			// the decimal but before any significant digits.  These are only
			// output if abs(number being formatted) < 1.0.
			if (-1-i > (this._digitList.f_decimalAt-1)) {
				result.f_append(zero);
				continue;
			}

			// Output a digit, if we have any precision left, or a
			// zero if we don't.  We don't want to output noise digits.
			if (!isInteger && digitIndex < this._digitList.f_count) {
				result.f_append(this._digitList.f_digits[digitIndex++]);
			} else {
				result.f_append(zero);
			}
		}
	}

	if (isNegative) {
		result.f_append(this._negativeSuffix);
	} else {
		result.f_append(this._positiveSuffix);
	}

	return result;
}
