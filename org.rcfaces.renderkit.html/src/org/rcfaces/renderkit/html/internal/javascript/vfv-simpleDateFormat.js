/*
 * $Id$
 *
 * @authors J.Merlin
 * @date
 * @revision
 *
 *
 *
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * SimpleDateFormat, DateFormat, DateFormatSymbols java classes.
 * The following class is a JavaScript adaptation
 */




/**
 * @class hidden F_SimpleDateFormat
 * @method F_SimpleDateFormat
 * @decl public
 *
 * Constructeur par d�faut
 *
 * @param
 * @return
 * @see F_SimpleDateFormat._init
 */
function F_SimpleDateFormat(args) {
	this._init(arguments);
}

/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_SimpleDateFormat.prototype._pattern = null;
F_SimpleDateFormat.prototype._compiledPattern = null;
F_SimpleDateFormat.prototype._formatData = null;
F_SimpleDateFormat.prototype._defaultCenturyStart = null;
F_SimpleDateFormat.prototype._calendar = null;
F_SimpleDateFormat.prototype._status = F_SimpleDateFormat.NO_ERROR;
F_SimpleDateFormat.prototype._defaultCenturyStartYear = null;

/*------------------------------------------------------------------------------
 * Class private constants
 */
F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR = 100;
F_SimpleDateFormat._F_TAG_QUOTE_CHARS = 101;

/*------------------------------------------------------------------------------
 * Class public constants
 */
F_SimpleDateFormat.NO_ERROR = 0;

F_SimpleDateFormat.COMP_BASE = 100;
F_SimpleDateFormat.COMP_NULL_PATTERN = F_SimpleDateFormat.COMP_BASE + 1;
F_SimpleDateFormat.COMP_ILLEGAL_PATTERN_CHAR = F_SimpleDateFormat.COMP_BASE + 2;
F_SimpleDateFormat.COMP_UNTERMINATED_QUOTE = F_SimpleDateFormat.COMP_BASE + 3;

F_SimpleDateFormat.PARSE_BASE = 200;
F_SimpleDateFormat.PARSE_ERROR = F_SimpleDateFormat.PARSE_BASE + 0;
F_SimpleDateFormat.DATE_ERROR = F_SimpleDateFormat.PARSE_BASE + 1;

/*------------------------------------------------------------------------------
 * Class public constants
 */

/*------------------------------------------------------------------------------
 * Instance public variables
 */
F_SimpleDateFormat.prototype.f_formatData = null;

/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_SimpleDateFormat.prototype._init = F_SimpleDateFormat__init;
F_SimpleDateFormat.prototype._subFormat = F_SimpleDateFormat__subFormat;
F_SimpleDateFormat.prototype._compile = F_SimpleDateFormat__compile;
F_SimpleDateFormat.prototype._initializeDefaultCentury = F_SimpleDateFormat__initializeDefaultCentury;
F_SimpleDateFormat.prototype._parseAmbiguousDatesAsAfter = F_SimpleDateFormat__parseAmbiguousDatesAsAfter;
F_SimpleDateFormat.prototype._matchString = F_SimpleDateFormat__matchString; 
F_SimpleDateFormat.prototype._encode = F_SimpleDateFormat__encode;
F_SimpleDateFormat.prototype._zeroPaddingNumber = F_SimpleDateFormat__zeroPaddingNumber;
F_SimpleDateFormat.prototype._subParse = F_SimpleDateFormat__subParse;

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_SimpleDateFormat.prototype.f_format = F_SimpleDateFormat_format;
F_SimpleDateFormat.prototype.f_parse = F_SimpleDateFormat_parse;
F_SimpleDateFormat.prototype.f_getStatus = F_SimpleDateFormat_getStatus;
F_SimpleDateFormat.prototype.f_setStatus = F_SimpleDateFormat_setStatus;

/*------------------------------------------------------------------------------
 * Class private methods
 */


/**
 * @class F_SimpleDateFormat
 * @method _init
 * @decl private
 *
 * Cette m�thode est appel�e par le constructeur et �value les param�tres
 *
 * @param pattern La cha�ne de caract�res du format
 * @param locale La localisation � utiliser pour ce format
 * @return
 * @see F_DateFormatSymbols, F_Locale, F_Calendar
 */
function F_SimpleDateFormat__init(args) {
	this._calendar = new F_Calendar();
	this._formatData = new F_DateFormatSymbols();
	this._pattern = (args.length > 0)? args[0]:"";
	this._compile();
	this._initializeDefaultCentury();
}

function F_SimpleDateFormat__initializeDefaultCentury() {
	var d = new Date();
	this._calendar.f_setTime(new Date());
	this._calendar.f_add(F_Calendar.YEAR, -80);
	this._parseAmbiguousDatesAsAfter(this._calendar.f_getTime());
}

function F_SimpleDateFormat__parseAmbiguousDatesAsAfter(startDate) {
	this._defaultCenturyStart = startDate;
	this._calendar.f_setTime(startDate);
	this._defaultCenturyStartYear = this._calendar.f_get(F_Calendar.YEAR);
}

function F_SimpleDateFormat_setStatus(status) {
	this._status = status;
}

function F_SimpleDateFormat_getStatus(status) {
	return this._status;
}

/**
 * @function encode
 * @decl private
 *
 *
 *
 *
 *
 */
function F_SimpleDateFormat__encode(tag, length) {
	var buffer = "";
	//alert("Encoding: " + tag + " " + length);
	if (length < 255) {
		buffer += String.fromCharCode((tag << 8) | length);
	} else {
		buffer += String.fromCharCode((tag << 8) | 0xff);
		buffer += String.fromCharCode(length >>> 16);
		buffer += String.fromCharCode(length & 0xffff);
	}
	return buffer;
}

/**
 * @class hidden F_Date
 * @method _compile
 * @decl private 
 *
 * The original code is from Sun Microsystems, Inc and part of the
 * SimpleDateFormat java class.
 * The following is the JavaScript adaptation
 *
 * @param
 * @return
 * @see
 */
function F_SimpleDateFormat__compile() {
	var pattern = this._pattern;
	if (pattern == null) {
		this.f_setStatus(F_SimpleDateFormat.COMP_NULL_PATTERN);
		return;
	}
	var compiledPattern =  new String("");
	var tmpBuffer = null;
	var count = 0;
	var lastTag = -1;
	var c;
	var length = pattern.length;
	var inQuote = false;

	for (var i = 0; i < length; i++) {
		var c = pattern.charAt(i);

		if (c == '\'') {
			// '' is treated as a single quote regardless of being
			// in a quoted section.
			// Get tohen ahead
			if ((i + 1) < length) {
				c = pattern.charAt(i + 1);
				if (c == '\'') {
					i++;
					// If pending tag, close and reset
					if (count != 0) {
						compiledPattern += this._encode(lastTag, count);
						lastTag = -1;
						count = 0;
					}
					// In litteral, append to current buffer
					if (inQuote) {
						tmpBuffer += c;
					// Otherwise, store as ascii
					} else {
						compiledPattern += String.fromCharCode(
							(F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR << 8) | c.charCodeAt(0)
						);
					}
					continue;
		    	}
			}
			// Not in litteral
			if (!inQuote) {
				// Pending tag, close and reset
				if (count != 0) {
					compiledPattern += this._encode(lastTag, count);
					lastTag = -1;
					count = 0;
				}
				// Start a new buffer
				if (tmpBuffer == null) {
					tmpBuffer = new String("");
				} else {
					tmpBuffer = "";
				}
				// Start litteral
				inQuote = true;
			} else {
				// In litteral
				var len = tmpBuffer.length;
				// Single char
		    	if (len == 1) {
					var ch = tmpBuffer.charAt(0);
					var code = ch.charCodeAt(0);
					// 8 bit encoding
					if (code < 128) {
						compiledPattern += String.fromCharCode(
							(F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR << 8) | code
						);
					// 16 bit encoding is equivalent to multiple chars with
					// count set to one
					} else {
						compiledPattern += String.fromCharCode(
							(F_SimpleDateFormat._F_TAG_QUOTE_CHARS << 8) | 1
						);
			    		compiledPattern += ch;
					}
				// Multiple chars
		    	} else {
					compiledPattern += this._encode(F_SimpleDateFormat._F_TAG_QUOTE_CHARS, len);
					compiledPattern += tmpBuffer;
		    	}
		    	// Stop litteral
		    	inQuote = false;
			}
			continue;
	    }
	    // Single character other than quote
	    // In litteral ,append to current buffer
	    if (inQuote) {
			tmpBuffer += c;
			continue;
	    }
	    // Otherwise, check for a pattern
	    if (!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')) {
	    	// Close any currently opened repeatable pattern tag
			if (count != 0) {
		    	compiledPattern += this._encode(lastTag, count);
		    	lastTag = -1;
		  	  	count = 0;
			}
			// Store characters
			// 8 bit character code
			if (c < 128) {
		    	// In most cases, c would be a delimiter, such as ':'.
		    	compiledPattern += String.fromCharCode(
		    		(F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR << 8) | c
		    	);
		    // 16 bit character code
			} else {
		    	// Take any contiguous non-ASCII alphabet characters and
		    	// put them in a single TAG_QUOTE_CHARS.
		    	var j;
		    	for (j = i + 1; j < length; j++) {
					var d = pattern.charAt(j);
					// Break if we find a tag
					if (d == '\'' || (d >= 'a' && d <= 'z' || d >= 'A' && d <= 'Z')) {
			    		break;
					}
		    	}
		    	// Store char count
		    	// The original code from Sun is Bogus...(j-i) could be more
		    	// than 254 and would break the high order byte encoding
		    	//
		    	//compiledPattern += String.fromCharCode(
		    	//	(F_Date._F_TAG_QUOTE_CHARS << 8) | (j - i)
		    	//);
		    	compiledPattern += this._encode(F_SimpleDateFormat._F_TAG_QUOTE_CHARS, (j-i));
		    	// Store string
		    	for (; i < j; i++) {
					compiledPattern += pattern.charAt(i);
		    	}
		    	i--; 
			}
			continue;
	    }

		// Get current tag from pattern
		var tag;
		if ((tag = this._formatData.f_patternChars.indexOf(c)) == -1) {
			this.f_setStatus(F_SimpleDateFormat.COMP_ILLEGAL_PATTERN_CHAR);
			return;
			//throw new IllegalArgumentException("Illegal pattern character " +
			//			"'" + c + "'");
		}
		// New or repeated tag is found, just count
		if (lastTag == -1 || lastTag == tag) {
			lastTag = tag;
			count++;
			continue;
		}
		// Otherwise, new tag, encode previous
		compiledPattern += this._encode(lastTag, count);
		lastTag = tag;
		count = 1;
	}

	// Unterminated litteral...
	if (inQuote) {
		// TODO
		//throw new IllegalArgumentException("Unterminated quote");
		this.f_setStatus(F_SimpleDateFormat.COMP_UNTERMINATED_QUOTE);
		return;
	}

	// Pending tag...
	if (count != 0) {
		compiledPattern += this._encode(lastTag, count);
	}

	// Copy the compiled pattern to a char array
	//var len = compiledPattern.length;
	//char[] r = new char[len];
	//compiledPattern.getChars(0, len, r, 0);

	this._compiledPattern = compiledPattern;
	//alert("compiledPattern: " + this._compiledPattern);
	this._status = F_SimpleDateFormat.NO_ERROR;
}

function F_SimpleDateFormat_format(date) {
	if (date == null) return null;
	var toAppendTo = "";
	this._calendar.f_setTime(date);

	for (var i = 0; i < this._compiledPattern.length; ) {
		var tag = this._compiledPattern.charCodeAt(i) >>> 8;
	    var count = this._compiledPattern.charCodeAt(i++) & 0xff;
	    if (count == 255) {
			count = this._compiledPattern.charCodeAt(i++) << 16;
			count |= this._compiledPattern.charCodeAt(i++);
		}
		//alert("format tag: " + tag);
		switch (tag) {
			case F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR: {
				toAppendTo += String.fromCharCode(count);
				break;
			}
			case F_SimpleDateFormat._F_TAG_QUOTE_CHARS: {
				toAppendTo += this._compiledPattern.substr(i, count);
				i += count;
				break;
			}
			default: {
				toAppendTo += this._subFormat(tag, count);
				break;
			}
		}
		//alert("toAppendTo: " + toAppendTo);
	}
	return toAppendTo;
}


F_SimpleDateFormat._F_PATTERN_INDEX_TO_CALENDAR_FIELD = [
	F_Calendar.ERA,					// G
	F_Calendar.YEAR,					// y
	F_Calendar.MONTH,					// M
	F_Calendar.DATE,					// d
	F_Calendar.HOUR_OF_DAY,			// k
	F_Calendar.HOUR_OF_DAY,			// H
	F_Calendar.MINUTE,				// m
	F_Calendar.SECOND,				// s
	F_Calendar.MILLISECOND,			// S
	F_Calendar.DAY_OF_WEEK,			// E
	F_Calendar.DAY_OF_YEAR,			// D
	F_Calendar.DAY_OF_WEEK_IN_MONTH,	// F
	F_Calendar.WEEK_OF_YEAR,			// w
	F_Calendar.WEEK_OF_MONTH,			// W
	F_Calendar.AM_PM,					// a
	F_Calendar.HOUR,					// h
	F_Calendar.HOUR,					// K
	F_Calendar.ZONE_OFFSET,			// z
	F_Calendar.ZONE_OFFSET			// Z
];
	
function F_SimpleDateFormat__zeroPaddingNumber(value, minDigits, maxDigits) {
	// Optimization for 1, 2 and 4 digit numbers. This should
	// cover most cases of formatting date/time related items.
	// Note: This optimization code assumes that maxDigits is
	// either 2 or Integer.MAX_VALUE (maxIntCount in format()).
	
	// TODO
	//if (zeroDigit == 0) {
		//zeroDigit = ((DecimalFormat)numberFormat).getDecimalFormatSymbols().getZeroDigit();
	//}
	zeroDigit = "0";
	zeroCode = zeroDigit.charCodeAt(0);
	if (value >= 0) {
		if (value < 100 && minDigits >= 1 && minDigits <= 2) {
			var buf = "";
		    if (value < 10) {
				if (minDigits == 2) {
					buf += zeroDigit;
				}
				buf += String.fromCharCode(zeroCode + value);
			} else {
				buf += String.fromCharCode(zeroCode + (value / 10));
				buf += String.fromCharCode(zeroCode + (value % 10));
			}
			return buf;
		} else if (value >= 1000 && value < 10000) {
			if (minDigits == 4) {
				var buf = "";
				buf += String.fromCharCode(zeroCode + (value / 1000));
				value %= 1000;
				buf += String.fromCharCode(zeroCode + (value / 100));
				value %= 100;
				buf += String.fromCharCode(zeroCode + (value / 10));
				buf += String.fromCharCode(zeroCode + (value % 10));
				return buf;
			} else if (minDigits == 2 && maxDigits == 2) {
				return this._zeroPaddingNumber(value % 100, 2, 2);
			}
		}
	}
	// TODO
	//numberFormat.setMinimumIntegerDigits(minDigits);
	//numberFormat.setMaximumIntegerDigits(maxDigits);
	//return numberFormat.format((long)value);
	return "";
}


function F_SimpleDateFormat__subFormat(patternCharIndex, count) {
	var maxIntCount = Number.MAX_VALUE;
	var current = "";

	var field = F_SimpleDateFormat._F_PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
	//alert("field: " + field);
	var value = this._calendar.f_get(field);
	//alert("value: " + value);

	// Note: zeroPaddingNumber() assumes that maxDigits is either
	// 2 or maxIntCount. If we make any changes to this,
	// zeroPaddingNumber() must be fixed.

	switch (patternCharIndex) {
		case 0: // 'G' - ERA
			current = this._formatData.f_eras[value];
			break;
		case 1: // 'y' - YEAR
			if (count >= 4)
				current = this._zeroPaddingNumber(value, count, maxIntCount);
			else // count < 4
				current = this._zeroPaddingNumber(value, 2, 2); // clip 1996 to 96
			break;
		case 2: // 'M' - MONTH
			if (count >= 4)
				current = this._formatData.f_months[value];
			else if (count == 3)
				current = this._formatData.f_shortMonths[value];
			else
				current = this._zeroPaddingNumber(value+1, count, maxIntCount);
			break;
		case 4: // 'k' - HOUR_OF_DAY: 1-based.  eg, 23:59 + 1 hour =>> 24:59
			if (value == 0)
				current = this._zeroPaddingNumber(
					this._calendar.f_getMaximum(F_Calendar.HOUR_OF_DAY)+1, count, maxIntCount);
			else
				current = this._zeroPaddingNumber(value, count, maxIntCount);
			break;
		case 9: // 'E' - DAY_OF_WEEK
			if (count >= 4)
				current = this._formatData.f_weekdays[value];
			else // count < 4, use abbreviated form if exists
				current = this._formatData.f_shortWeekdays[value];
			break;
		case 14:    // 'a' - AM_PM
			current = this._formatData.f_ampms[value];
			break;
		case 15: // 'h' - HOUR:1-based.  eg, 11PM + 1 hour =>> 12 AM
			if (value == 0)
				current = this._zeroPaddingNumber(
					this._calendar.f_getLeastMaximum(F_Calendar.HOUR)+1, count, maxIntCount);
			else
				current = this._zeroPaddingNumber(value, count, maxIntCount);
			break;
		case 17: // 'z' - ZONE_OFFSET
			// TODO
			break;
		case 18: // 'Z' - ZONE_OFFSET ("-/+hhmm" form)
			// TODO
			break;
		default: {
			// case 3: // 'd' - DATE
			// case 5: // 'H' - HOUR_OF_DAY:0-based.  eg, 23:59 + 1 hour =>> 00:59
			// case 6: // 'm' - MINUTE
			// case 7: // 's' - SECOND
			// case 8: // 'S' - MILLISECOND
			// case 10: // 'D' - DAY_OF_YEAR
			// case 11: // 'F' - DAY_OF_WEEK_IN_MONTH
			// case 12: // 'w' - WEEK_OF_YEAR
			// case 13: // 'W' - WEEK_OF_MONTH
			// case 16: // 'K' - HOUR: 0-based.  eg, 11PM + 1 hour =>> 0 AM
			current = this._zeroPaddingNumber(value, count, maxIntCount);
			break;
		}
	}
	//alert("current: " + current);
	return current;
}

/**
 * @class F_SimpleDateFormat
 * @method f_parse
 * @decl public
 *
 * Analyse une cha�ne de caract�res repr�sentant une date dans le format pr�cis�
 *
 * @param strdate Une cha�ne de caract�res
 * @return
 * @see F_SimpleDateFormat constructeur avec pattern
 */
function F_SimpleDateFormat_parse(text, obey) {
	var pos = new F_ParsePosition(0);
	var start = pos.f_index;
	var oldStart = start;
	var textLength = text.length;

	var ambiguousYear = new Object();
	ambiguousYear.bool = false;
	this._calendar.f_clear(); // Clears all the time fields

	//alert("length: " + this._compiledPattern.length);
	for (var i = 0; i < this._compiledPattern.length; ) {
		var tag = this._compiledPattern.charCodeAt(i) >>> 8;
		var count = this._compiledPattern.charCodeAt(i++) & 0xff;
		if (count == 255) {
			count = this._compiledPattern.charCodeAt(i++) << 16;
			count |= this._compiledPattern.charCodeAt(i++);
		}
		//alert("parse tag: " + tag);
		switch (tag) {
			case F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR: {
				if (start >= textLength || text.charAt(start) != String.fromCharCode(count)) {
					pos.f_index = oldStart;
					pos.f_errorIndex = start;
					this.f_setStatus(F_SimpleDateFormat.PARSE_ERROR);
					return null;
				}
				start++;
				break;
			}

			case F_SimpleDateFormat._F_TAG_QUOTE_CHARS: {
				while (count-- > 0) {
					if (start >= textLength || text.charAt(start) != this._compiledPattern.charAt(i++)) {
						pos.f_index = oldStart;
						pos.f_errorIndex = start;
						this.f_setStatus(F_SimpleDateFormat.PARSE_ERROR);
						return null;
					}
					start++;
				}
				break;
			}
			default: {
				// If next pattern is not a delimiter, obey digit count
				var obeyCount = false;
				if (i < this._compiledPattern.length) {
					var nextTag = this._compiledPattern.charCodeAt(i) >>> 8;
					if (!(nextTag == F_SimpleDateFormat._F_TAG_QUOTE_ASCII_CHAR || 
						nextTag == F_SimpleDateFormat._F_TAG_QUOTE_CHARS)) {
						obeyCount = true;
					}
				}
				start = this._subParse(text,start,tag,count,obeyCount,ambiguousYear, pos);
				if (start < 0) {
					pos.f_index = oldStart;
					this.f_setStatus(F_SimpleDateFormat.PARSE_ERROR);
					return null;
				}
			}
		} // switch
	} // endfor

	// At this point the fields of Calendar have been set.  Calendar
	// will fill in default values for missing fields when the time
	// is computed.

	pos.f_index = start;
	// In case of an ambiguous year, we might have to adjust time if we get a DST onset day
	var parsedDate;
	// If this is true then the two-digit year == the default start year
	try {
		if (ambiguousYear.bool) {
			var savedCalendar = this._calendar.clone();
			parsedDate = this._calendar.f_getTime();
			if (parsedDate.getTime() < this._defaultCenturyStart.getTime()) {
				// We can't use add here because that does a complete() first.
				savedCalendar.f_set(F_Calendar.YEAR, defaultCenturyStartYear + 100);
				parsedDate = savedCalendar.f_getTime();
			}
		} else {
			parsedDate = this._calendar.f_getTime();
		}
	} catch(e) {
		// An IllegalArgumentException will be thrown by Calendar.getTime()
		// if any fields are out of range, e.g., MONTH == 17.
		pos.f_errorIndex = start;
		pos.f_index = oldStart;
		this.f_setStatus(F_SimpleDateFormat.DATE_ERROR);
		return null;
	}
	this.f_setStatus(F_SimpleDateFormat.NO_ERROR);
	return parsedDate;
}

function parseInteger(str, pos) {
	var i = 0;
	var c;
	while ((i < str.length) && F_Character.f_isDigit(c = str.charAt(i))) i++;
	pos.f_index += i;
	return (i == 0)? Number.NaN:parseInt(str, 10);
}

function F_SimpleDateFormat__subParse(text, start, patternCharIndex, count,
	obeyCount, ambiguousYear, origPos) {
	var number = null;
	var value = 0;
	var pos = new F_ParsePosition(0);
	pos.f_index = start;
	var field = F_SimpleDateFormat._F_PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];

	//alert("In subParse...");
	// If there are any spaces here, skip over them.  If we hit the end
	// of the string, then fail.
	for (;;) {
		if (pos.f_index >= text.length) {
			origPos.f_errorIndex = start;
			return -1;
		}
		var c = text.charAt(pos.f_index);
		if (c != ' ' && c != '\t') break;
		++pos.f_index;
	}

	// We handle a few special cases here where we need to parse
	// a number value.  We handle further, more generic cases below.  We need
	// to handle some of them here because some fields require extra processing on
	// the parsed value.
	if (patternCharIndex == 4 /*HOUR_OF_DAY1_FIELD*/ ||
		patternCharIndex == 15 /*HOUR1_FIELD*/ ||
		(patternCharIndex == 2 /*MONTH_FIELD*/ && count <= 2) ||
		patternCharIndex == 1) {
		// It would be good to unify this with the obeyCount logic below,
		// but that's going to be difficult.
		if (obeyCount) {
			if ((start+count) > text.length) {
				origPos.f_errorIndex = start;
				return -1;
			}
			// TODO
			//number = numberFormat.parse(text.substring(0, start+count), pos);
			var str = text.substring(pos.f_index, start+count);
			number = parseInteger(str, pos);
		} else {
			// TODO
			//number = numberFormat.parse(text, pos);
			var str = text.substr(pos.f_index, text.length);
			number = parseInteger(str, pos);
		}
		if (isNaN(number)) {
			origPos.f_errorIndex = pos.f_index;
			return -1;
		}
		// TODO
		//value = number.intValue();
		value = number;
	}
	//alert("parse value: " + value);
	var index;
	switch (patternCharIndex) {
		case 0: {// 'G' - ERA
			if ((index = this._matchString(text, start, F_Calendar.ERA, this._formatData.f_eras)) > 0) {
				return index;
			} else {
				origPos.f_errorIndex = pos.f_index;
				return -1;
			}
		}
		case 1: {// 'y' - YEAR
			// If there are 3 or more YEAR pattern characters, this indicates
			// that the year value is to be treated literally, without any
			// two-digit year adjustments (e.g., from "01" to 2001).  Otherwise
			// we made adjustments to place the 2-digit year in the proper
			// century, for parsed strings from "00" to "99".  Any other string
			// is treated literally:  "2250", "-1", "1", "002".
			if (count <= 2 && (pos.f_index - start) == 2
				&& F_Character.f_isDigit(text.charAt(start))
				&& F_Character.f_isDigit(text.charAt(start+1))) {
				// Assume for example that the defaultCenturyStart is 6/18/1903.
				// This means that two-digit years will be forced into the range
				// 6/18/1903 to 6/17/2003.  As a result, years 00, 01, and 02
				// correspond to 2000, 2001, and 2002.  Years 04, 05, etc. correspond
				// to 1904, 1905, etc.  If the year is 03, then it is 2003 if the
				// other fields specify a date before 6/18, or 1903 if they specify a
				// date afterwards.  As a result, 03 is an ambiguous year.  All other
				// two-digit years are unambiguous.
				var ambiguousTwoDigitYear = this._defaultCenturyStartYear % 100;
				ambiguousYear.bool = (value == ambiguousTwoDigitYear);
				value += (this._defaultCenturyStartYear/100)*100 +
					(value < ambiguousTwoDigitYear ? 100 : 0);
			}
			this._calendar.f_set(F_Calendar.YEAR, value);
			return pos.f_index;
		}
		case 2: {// 'M' - MONTH
			if (count <= 2) {// i.e., M or MM.
				// Don't want to parse the month if it is a string
				// while pattern uses numeric style: M or MM.
				// [We computed 'value' above.]
				this._calendar.f_set(F_Calendar.MONTH, value - 1);
				return pos.f_index;
			} else {
				// count >= 3 // i.e., MMM or MMMM
				// Want to be able to parse both short and long forms.
				// Try count == 4 first:
				var newStart = 0;
				if ((newStart=this._matchString(text, start, F_Calendar.MONTH,
					this._formatData.f_months)) > 0) {
					return newStart;
				} else {// count == 4 failed, now try count == 3
					if ((index = this._matchString(text, start, F_Calendar.MONTH,
						this._formatData.f_shortMonths)) > 0) {
						return index;
					} else {
						origPos.f_errorIndex = pos.f_index;
						return -1;
					}
				}
			}
		}
		case 4: {// 'k' - HOUR_OF_DAY: 1-based.  eg, 23:59 + 1 hour =>> 24:59
			// [We computed 'value' above.]
			if (value == this._calendar.f_getMaximum(F_Calendar.HOUR_OF_DAY)+1) value = 0;
			this._calendar.f_set(F_Calendar.HOUR_OF_DAY, value);
			return pos.f_index;
		}
		case 9: { // 'E' - DAY_OF_WEEK
			// Want to be able to parse both short and long forms.
			// Try count == 4 (DDDD) first:
			var newStart = 0;
			if ((newStart=this._matchString(text, start, F_Calendar.DAY_OF_WEEK,
				this._formatData.f_weekdays)) > 0) {
				return newStart;
			} else {// DDDD failed, now try DDD
				if ((index = this._matchString(text, start, F_Calendar.DAY_OF_WEEK,
					this._formatData.f_shortWeekdays)) > 0) {
					return index;
				} else {
					origPos.f_errorIndex = pos.f_index;
					return -1;
				}
			}
        }
		case 14: {   // 'a' - AM_PM
			if ((index = this._matchString(text, start, F_Calendar.AM_PM,
				this._formatData.f_ampms)) > 0) {
				return index;
			} else {
				origPos.f_errorIndex = pos.f_index;
				return -1;
			}
		}
        case 15: {// 'h' - HOUR:1-based.  eg, 11PM + 1 hour =>> 12 AM
			// [We computed 'value' above.]
			if (value == this._calendar.f_getLeastMaximum(F_Calendar.HOUR)+1) value = 0;
			this._calendar.f_set(F_Calendar.HOUR, value);
			return pos.f_index;
		}
        case 17: // 'z' - ZONE_OFFSET
        case 18: // 'Z' - ZONE_OFFSET
        	// TODO
        	return -1;
		default: {
			// case 3: // 'd' - DATE
			// case 5: // 'H' - HOUR_OF_DAY:0-based.  eg, 23:59 + 1 hour =>> 00:59
			// case 6: // 'm' - MINUTE
			// case 7: // 's' - SECOND
			// case 8: // 'S' - MILLISECOND
			// case 10: // 'D' - DAY_OF_YEAR
			// case 11: // 'F' - DAY_OF_WEEK_IN_MONTH
			// case 12: // 'w' - WEEK_OF_YEAR
			// case 13: // 'W' - WEEK_OF_MONTH
			// case 16: // 'K' - HOUR: 0-based.  eg, 11PM + 1 hour =>> 0 AM

			// Handle "generic" fields
			if (obeyCount) {
				if ((start+count) > text.length) {
					origPos.f_errorIndex = pos.f_index;
					return -1;
				}
				// TODO
				//number = numberFormat.parse(text.substring(0, start+count), pos);
				var str = text.substring(pos.f_index, start+count);
				number = parseInteger(str, pos);
			} else {
				// TODO
				//number = numberFormat.parse(text, pos);
				var str = text.substring(pos.f_index, text.length);
				number = parseInteger(str, pos);
			}
			//alert("number: " + number);
			if (!isNaN(number)) {
				this._calendar.f_set(field, number);
				//alert("position: " + pos.f_index);
				return pos.f_index;
			}
			origPos.f_errorIndex = pos.f_index;
			return -1;
		}
	}
}

function F_SimpleDateFormat__matchString(text, start, field, data) {
	var i = 0;
	var count = data.length;

	if (field == F_Calendar.DAY_OF_WEEK) {
		i = 1;
	}

	// There may be multiple strings in the data[] array which begin with
	// the same prefix (e.g., Cerven and Cervenec (June and July) in Czech).
	// We keep track of the longest match, and return that.  Note that this
	// unfortunately requires us to test all array elements.
	var bestMatchLength = 0;
	var bestMatch = -1;
	for (; i<count; ++i) {
		var length = data[i].length();
		// Always compare if we have no match yet; otherwise only compare
		// against potentially better matches (longer strings).
		if (length > bestMatchLength &&
			text.regionMatches(true, start, data[i], 0, length)) {
			bestMatch = i;
			bestMatchLength = length;
		}
	}
	if (bestMatch >= 0) {
		this._calendar.f_set(field, bestMatch);
		return start + bestMatchLength;
	}
	return -start;
}
