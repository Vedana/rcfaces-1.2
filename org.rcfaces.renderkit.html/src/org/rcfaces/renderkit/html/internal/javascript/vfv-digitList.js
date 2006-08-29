/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */

/**
 * @class hidden F_DigitList
 */
function F_DigitList() {
	this._init(arguments);
}

//F_DigitList.F_MAX_COUNT = 19;
F_DigitList.F_DBL_DIG = 17;
// This is the 2^63 representation
//F_DigitList.F_LONG_MIN_REP = [
//	'9','2','2','3','3','7','2','0','3','6','8','5','4','7','7','5','8','0','8'
//];

// JavaScript Math accuracy is limited to 16 digits...
F_DigitList.F_MAX_COUNT = 16;
F_DigitList.F_LONG_MIN_REP = [
	'9','9','9','9','9','9','9','9','9','9','9','9','9','9','9','8'
];


F_DigitList.prototype.f_decimalAt = 0;
F_DigitList.prototype.f_count = 0;
F_DigitList.prototype.f_digits = null;

F_DigitList.prototype._init = F_DigitList__init;
F_DigitList.prototype.f_clear = F_DigitList_clear;
F_DigitList.prototype.f_getDouble = F_DigitList_getDouble;
F_DigitList.prototype.f_getLong = F_DigitList_getLong;
F_DigitList.prototype.f_fitsIntoLong = F_DigitList_fitsIntoLong;
F_DigitList.prototype.f_append = F_DigitList_append;
F_DigitList.prototype._isLongMIN_VALUE = F_DigitList__isLongMIN_VALUE;
F_DigitList.prototype._shouldRoundUp = F_DigitList__shouldRoundUp;
F_DigitList.prototype._round = F_DigitList__round;
F_DigitList.prototype.f_setDouble = F_DigitList_setDouble;
F_DigitList.prototype.f_setLong = F_DigitList_setLong;
F_DigitList.prototype.f_isZero = F_DigitList_isZero;

function F_DigitList__init(args) {
	this.f_decimalAt = 0;
	this.f_count = 0;
	this.f_digits = new Array();
}

function F_DigitList_clear() {
	this.f_decimalAt = 0;
	this.f_count = 0;
}

function F_DigitList_append(digit) {
	if (this.f_count < F_DigitList.F_MAX_COUNT) {
		this.f_digits[this.f_count++] = digit;
	}
}

function F_DigitList_fitsIntoLong(isPositive, ignoreNegativeZero) {
	// Figure out if the result will fit in a long.  We have to
	// first look for nonzero digits after the decimal point;
	// then check the size.  If the digit count is 18 or less, then
	// the value can definitely be represented as a long.  If it is 19
	// then it may be too large.

	// Trim trailing zeros.  This does not change the represented value.
	while (this.f_count > 0 && this.f_digits[this.f_count - 1] == '0') --this.f_count;
	if (this.f_count == 0) {
		// Positive zero fits into a long, but negative zero can only
		// be represented as a double. - bug 4162852
		return isPositive || ignoreNegativeZero;
	}

	if (this.f_decimalAt < this.f_count ||
		this.f_decimalAt > F_DigitList.F_MAX_COUNT)
		return false;

	if (this.f_decimalAt < F_DigitList.F_MAX_COUNT)
		return true;

	// At this point we have decimalAt == count, and count == MAX_COUNT.
	// The number will overflow if it is larger than 9223372036854775807
	// or smaller than -9223372036854775808.
	for (var i=0; i<this.f_count; ++i) {
		var dig = this.f_digits[i]
		var max = F_DigitList.F_LONG_MIN_REP[i];
		if (dig > max) return false;
		if (dig < max) return true;
	}

	// At this point the first count digits match.  If decimalAt is less
	// than count, then the remaining digits are zero, and we return true.
	if (this.f_count < this.f_decimalAt) return true;

	// Now we have a representation of Long.MIN_VALUE, without the leading
	// negative sign.  If this represents a positive value, then it does
	// not fit; otherwise it fits.
	return !isPositive;
}

function F_DigitList_getDouble() {
	if (this.f_count == 0) return 0.0;
	var temp = new F_StringBuffer();
	temp.f_append('.');
	for (var i = 0; i < this.f_count; ++i) temp.f_append(this.f_digits[i]);
	temp.f_append('E');
	temp.f_append(this.f_decimalAt.toString());
	return new Number(temp.toString());//Double.valueOf(temp.toString()).doubleValue();
	// long value = Long.parseLong(temp.toString());
	// return (value * Math.pow(10, decimalAt - count));
}

function F_DigitList_getLong() {
	// for now, simple implementation; later, do proper IEEE native stuff

	if (this.f_count == 0) return 0;

	// We have to check for this, because this is the one NEGATIVE value
	// we represent.  If we tried to just pass the digits off to parseLong,
	// we'd get a parse failure.
	// @@JM JavaScript Math precision is limited to 16 digits but this is not
	// equivalent to Number.MIN_VALUE (5e-324)
	// So, just comment this out for now...
	//if (this._isLongMIN_VALUE()) return Number.MIN_VALUE;

	var temp = new F_StringBuffer();
	for (var i = 0; i < this.f_decimalAt; ++i) {
		temp.f_append((i < this.f_count) ? this.f_digits[i]:'0');
	}
	return new Number(temp.toString()); //Long.parseLong(temp.toString());

}

function F_DigitList__isLongMIN_VALUE() {
	if (this.f_decimalAt != this.f_count || this.f_count != F_DigitList.F_MAX_COUNT)
		return false;
	for (var i = 0; i < this.f_count; ++i) {
		if (this.f_digits[i] != F_DigitList.F_LONG_MIN_REP[i]) return false;
	}
	return true;
}

function F_DigitList__shouldRoundUp(maximumDigits) {
	var increment = false;
	// Implement IEEE half-even rounding
	if (maximumDigits < this.f_count) {
		if (this.f_digits[maximumDigits] > '5') {
			return true;
		} else if (this.f_digits[maximumDigits] == '5' ) {
			for (var i=maximumDigits+1; i<this.f_count; ++i) {
				if (this.f_digits[i] != '0') {
					return true;
				}
			}
			return maximumDigits > 0 && (this.f_digits.charCodeAt(maximumDigits-1) % 2 != 0);
		}
	}
	return false;
}


function F_DigitList__round(maximumDigits) {
	// Eliminate digits beyond maximum digits to be displayed.
	// Round up if appropriate.
	if (maximumDigits >= 0 && maximumDigits < this.f_count) {
		if (this._shouldRoundUp(maximumDigits)) {
			// Rounding up involved incrementing digits from LSD to MSD.
			// In most cases this is simple, but in a worst case situation
			// (9999..99) we have to adjust the decimalAt value.
			for (;;) {
				--maximumDigits;
				if (maximumDigits < 0) {
					// We have all 9's, so we increment to a single digit
					// of one and adjust the exponent.
					this.f_digits[0] = '1';
					++this.f_decimalAt;
					maximumDigits = 0; // Adjust the count
					break;
				}
				var code = this.f_digits[maximumDigits].charCodeAt(0);
				this.f_digits[maximumDigits] = String.fromCharCode(++code);
				if (this.f_digits[maximumDigits] <= '9') break;
				// digits[maximumDigits] = '0'; // Unnecessary since we'll truncate this
			}
			++maximumDigits; // Increment for use as count
		}
		this.f_count = maximumDigits;
		// Eliminate trailing zeros.
		while (this.f_count > 1 && this.f_digits[this.f_count-1] == '0') {
			--this.f_count;
		}
	}
}

function F_DigitList_setDouble(source, maximumDigits, fixedPoint) {
	if (arguments.length < 3) fixedPoint = true;

	if (source == 0) {
		source = 0;
	}
	// Generate a representation of the form DDDDD, DDDDD.DDDDD, or
	// DDDDDE+/-DDDDD.
	var rep = source.toString();

	this.f_decimalAt = -1;
	this.f_count = 0;
	var exponent = 0;
	// Number of zeros between decimal point and first non-zero digit after
	// decimal point, for numbers < 1.
	var leadingZerosAfterDecimal = 0;
	var nonZeroDigitSeen = false;
	for (var i=0; i < rep.length; ++i) {
		var c = rep.charAt(i);
		if (c == '.') {
			this.f_decimalAt = this.f_count;
		} else if (c == 'e' || c == 'E') {
			exponent = parseInt(rep.substring(i+1), 10);
            break;
		} else if (this.f_count < F_DigitList.F_MAX_COUNT) {
			if (!nonZeroDigitSeen) {
				nonZeroDigitSeen = (c != '0');
				if (!nonZeroDigitSeen && this.f_decimalAt != -1) ++leadingZerosAfterDecimal;
			}
			if (nonZeroDigitSeen) this.f_digits[this.f_count++] = c;
		}
	}
	if (this.f_decimalAt == -1) this.f_decimalAt = this.f_count;
	if (nonZeroDigitSeen) {
		this.f_decimalAt += (exponent - leadingZerosAfterDecimal);
	}
	if (fixedPoint) {
		// The negative of the exponent represents the number of leading
		// zeros between the decimal and the first non-zero digit, for
		// a value < 0.1 (e.g., for 0.00123, -decimalAt == 2).  If this
		// is more than the maximum fraction digits, then we have an underflow
		// for the printed representation.
		if (-this.f_decimalAt > maximumDigits) {
			// Handle an underflow to zero when we round something like
			// 0.0009 to 2 fractional digits.
			this.f_count = 0;
			return;
		} else if (-this.f_decimalAt == maximumDigits) {
			// If we round 0.0009 to 3 fractional digits, then we have to
			// create a new one digit in the least significant location.
			if (this._shouldRoundUp(0)) {
				this.f_count = 1;
				++this.f_decimalAt;
				this.f_digits[0] = '1';
			} else {
				this.f_count = 0;
			}
			return;
		}
		// else fall through
	}
	// Eliminate trailing zeros.
	while (this.f_count > 1 && this.f_digits[this.f_count - 1] == '0') {
		--this.f_count;
	}
	// Eliminate digits beyond maximum digits to be displayed.
	// Round up if appropriate.
	this._round(fixedPoint ? (maximumDigits + this.f_decimalAt) : maximumDigits);
}

function F_DigitList_setLong(source, maximumDigits) {
	// This method does not expect a negative number. However,
	// "source" can be a Long.MIN_VALUE (-9223372036854775808),
	// if the number being formatted is a Long.MIN_VALUE.  In that
	// case, it will be formatted as -Long.MIN_VALUE, a number
	// which is outside the legal range of a long, but which can
	// be represented by DigitList.
	if (arguments.length < 2) maximumDigits = 0;
	if (source <= 0) {
		if (source == Number.MIN_VALUE) {
			this.f_decimalAt = this.f_count = F_MAX_COUNT;
			for (var i=0;i<this.f_count;i++) {
				this.f_digits[i] = F_DigitList.F_LONG_MIN_REP[i];
			}
		} else {
			this.f_decimalAt = this.f_count = 0; // Values <= 0 format as zero
		}
	} else {
		var left = F_DigitList.F_MAX_COUNT;
		var right;
		var zero = "0";
		var code = zero.charCodeAt(0);
		while (source > 0) {
			this.f_digits[--left] = String.fromCharCode(code + (source % 10));
			source = Math.floor(source /= 10);
			
		}
		this.f_decimalAt = F_DigitList.F_MAX_COUNT - left;
		// Don't copy trailing zeros.  We are guaranteed that there is at
		// least one non-zero digit, so we don't have to check lower bounds.
		for (right = F_DigitList.F_MAX_COUNT - 1; this.f_digits[right] == '0'; --right) {}
		this.f_count = right - left + 1;
		for (var i=0; i<this.f_count; i++) {
			this.f_digits[i] = this.f_digits[left+i];
		}
	}
	if (maximumDigits > 0) {
		this._round(maximumDigits);
	}
}

function F_DigitList_isZero() {
	for (var i=0; i<this.f_count; i++) {
		if (this.f_digits[i] != '0') {
			return false;
		}
	}
	return true;
}

