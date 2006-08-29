/**
 * @class hidden F_Calendar
 * @authors J.Merlin
 * @date
 * @revision
 *
 *
 *
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * GregorianCalendar, Calendar java classes.
 * The following class is a JavaScript adaptation
 */


/**
 * @method F_Calendar
 * @decl public
 *
 * F_Calendar constructor, mostly Gregorian calendar
 *
 * @param
 * @param
 * @return
 * @see
 */
function F_Calendar(args) {
	this._init(arguments);
}

/*------------------------------------------------------------------------------
 * Class private constants
 */


F_Calendar._EPOCH_YEAR = 1970; // Reference is January 1, 1970

F_Calendar._ONE_SECOND = 1000;
F_Calendar._ONE_MINUTE = 60*F_Calendar._ONE_SECOND;
F_Calendar._ONE_HOUR   = 60*F_Calendar._ONE_MINUTE;
F_Calendar._ONE_DAY    = 24*F_Calendar._ONE_HOUR;
F_Calendar._ONE_WEEK   = 7*F_Calendar._ONE_DAY;
F_Calendar._MIN_VALUES = [
	0,1,0,1,0,1,1,1,-1,0,0,0,0,0,0,-12*F_Calendar._ONE_HOUR,0
];
F_Calendar._LEAST_MAX_VALUES = [
	1,292269054,11,52,4,28,365,7,4,1,11,23,59,59,999,
	12*F_Calendar._ONE_HOUR,1*F_Calendar._ONE_HOUR
];
F_Calendar._MAX_VALUES = [
	1,292278994,11,53,6,31,366,7,6,1,11,23,59,59,999,
	12*F_Calendar._ONE_HOUR,1*F_Calendar._ONE_HOUR
];

/*------------------------------------------------------------------------------
 * Class public constants
 */
F_Calendar.ERA				= 0;
F_Calendar.YEAR				= 1;
F_Calendar.MONTH			= 2;
F_Calendar.WEEK_OF_YEAR		= 3;
F_Calendar.WEEK_OF_MONTH	= 4;
F_Calendar.DATE				= 5;
F_Calendar.DAY_OF_MONTH		= 5;
F_Calendar.DAY_OF_YEAR		= 6;
F_Calendar.DAY_OF_WEEK		= 7;
F_Calendar.DAY_OF_WEEK_IN_MONTH = 8;
F_Calendar.AM_PM			= 9;
F_Calendar.HOUR				= 10;
F_Calendar.HOUR_OF_DAY		= 11;
F_Calendar.MINUTE			= 12;
F_Calendar.SECOND			= 13;
F_Calendar.MILLISECOND		= 14;
F_Calendar.ZONE_OFFSET		= 15;
F_Calendar.DST_OFFSET		= 16;
F_Calendar.FIELD_COUNT		= 17;

F_Calendar.BC				= 0;
F_Calendar.AD				= 1;
F_Calendar.AM				= 0;
F_Calendar.PM				= 1;

/*------------------------------------------------------------------------------
 * Instance private variables
 */
F_Calendar.prototype._fields = null;
F_Calendar.prototype._areFieldsSet = false;
F_Calendar.prototype._isTimeSet = false;
F_Calendar.prototype._time = 0;
F_Calendar.prototype._areAllFieldsSet = false;

/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_Calendar.prototype._init = F_Calendar__init;
F_Calendar.prototype._internalSet = F_Calendar__internalSet;
F_Calendar.prototype._internalGet = F_Calendar__internalGet;
F_Calendar.prototype._computeFields = F_Calendar__computeFields;
F_Calendar.prototype._complete = F_Calendar__complete;
F_Calendar.prototype._updateTime = F_Calendar__updateTime;
F_Calendar.prototype._computeTime = F_Calendar__computeTime;

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_Calendar.prototype.f_get = F_Calendar_get;
F_Calendar.prototype.f_set = F_Calendar_set;
F_Calendar.prototype.f_getMaximum = F_Calendar_getMaximum;
F_Calendar.prototype.f_getMinimum = F_Calendar_getMinimum;
F_Calendar.prototype.f_getGreatestMinimum = F_Calendar_getGreatestMinimum;
F_Calendar.prototype.f_getLeastMaximum = F_Calendar_getLeastMaximum;
F_Calendar.prototype.f_clear = F_Calendar_clear;
F_Calendar.prototype.f_clone = F_Calendar_clone;
F_Calendar.prototype.f_setTime = F_Calendar_setTime;
F_Calendar.prototype.f_getTime = F_Calendar_getTime;
F_Calendar.prototype.f_setTimeInMillis = F_Calendar_setTimeInMillis;
F_Calendar.prototype.f_getTimeInMillis = F_Calendar_getTimeInMillis;
F_Calendar.prototype.f_before = F_Calendar_before;
F_Calendar.prototype.f_after = F_Calendar_after;
F_Calendar.prototype.f_equals = F_Calendar_equals;
F_Calendar.prototype.f_add = F_Calendar_add;

/*------------------------------------------------------------------------------
 * Class F_Calendar implementation
 */
function F_Calendar__init(args) {
	// Fields already have default values during instantiation
	this._fields = new Array();
	for (var i=0; i<F_Calendar.FIELD_COUNT; i++) {
		this._fields[i] = null; // UNSET
	}
}

function F_Calendar_get(field) {
	this._complete();
	return this._internalGet(field);
}

function F_Calendar__internalGet(field) {
	return this._fields[field];
}

function F_Calendar_set(field, value) {
	this._isTimeSet = false;
	this._internalSet(field, value);
	//stamp[field] = nextStamp++;
	//if (nextStamp == Integer.MAX_VALUE) {
		//adjustStamp();
	//}
	this._areFieldsSet = false;
	//isSet[field] = true;
}

function F_Calendar__internalSet(field, value) {
	this._fields[field] = value;
}

function F_Calendar_getMinimum(field) {
	return F_Calendar._MIN_VALUES[field];
}

function F_Calendar_getMaximum(field) {
	return F_Calendar._MAX_VALUES[field];
}

function F_Calendar_getGreatestMinimum(field) {
	return F_Calendar._MIN_VALUES[field];
}

function F_Calendar_getLeastMaximum(field) {
	return F_Calendar._LEAST_MAX_VALUES[field];
}

function F_Calendar_getTime() {
	var d = new Date();
	d.setTime(this.f_getTimeInMillis());
	return d;
}

function F_Calendar_setTime(date) {
	this.f_setTimeInMillis(date.getTime());
}

function F_Calendar_setTimeInMillis(millis) {
	this._isTimeSet = true;
	this._time = millis;
	this._areFieldsSet = false;
	if (!this._areFieldsSet) {
		this._computeFields();
		this._areFieldsSet = true;
	}
}

function F_Calendar_getTimeInMillis() {
	if (!this._isTimeSet) {
		this._updateTime();
	}
	return this._time;
}

function F_Calendar_clear() {
	this._fields = new Array();
	for (var i=0; i<F_Calendar.FIELD_COUNT; i++) {
		this._fields[i] = null; // UNSET
	}
    this._areFieldsSet = false;
    this._isTimeSet = false;
}

function F_Calendar_clone() {
	var other = new F_Calendar();
	other._isTimeSet = this._isTimeSet;
	other._areFieldsSet = this._areFieldsSet;
	other._time = this._time;
	other._fields = new Array();
	for (var i=0; i<this._fields.length; i++) {
		other._fields[i] = this._fields[i];
	}
}

function F_Calendar_before(when) {
	return (this.f_getTimeInMillis < when.f_getTimeInMillis());
}

function F_Calendar_after(when) {
	return (this.f_getTimeInMillis() > when.f_getTimeInMillis());
}

function F_Calendar_equals(obj) {
	return (this.f_getTimeInMillis() == obj.f_getTimeInMillis());
}

function F_Calendar__computeFields() {
	var d = new Date();
	var index;
	d.setTime(this._time);
	this._fields = new Array();
	//var days = 
	var year = d.getFullYear();
	if (year <= 0) {
		year = 1 - year;
		index = F_Calendar.BC;
	} else {
		index = F_Calendar.AD;
	}
	this._fields[F_Calendar.ERA] = index;
	this._fields[F_Calendar.YEAR] = year;
	this._fields[F_Calendar.MONTH] = d.getMonth();
	this._fields[F_Calendar.WEEK_OF_YEAR] = 0;
	this._fields[F_Calendar.WEEK_OF_MONTH] = 0; //TODO
	this._fields[F_Calendar.DATE] = d.getDate();
	this._fields[F_Calendar.DAY_OF_MONTH] = d.getDate();
	this._fields[F_Calendar.DAY_OF_YEAR] = 0; //TODO
	this._fields[F_Calendar.DAY_OF_WEEK] = d.getDay();
	this._fields[F_Calendar.DAY_OF_WEEK_IN_MONTH] = 0; //TODO
	var hour = d.getHours();
	if (hour > 12) {
		hour -= 12;
		index = F_Calendar.PM;
	} else {
		index = F_Calendar.AM;
	}
	this._fields[F_Calendar.AM_PM] = index;
	this._fields[F_Calendar.HOUR] = hour;
	this._fields[F_Calendar.HOUR_OF_DAY] = d.getHours();
	this._fields[F_Calendar.MINUTE] = d.getMinutes();
	this._fields[F_Calendar.SECOND] = d.getSeconds();
	this._fields[F_Calendar.MILLISECOND] = d.getMilliseconds();
	this._fields[F_Calendar.ZONE_OFFSET] = d.getTimezoneOffset();
	this._fields[F_Calendar.DST_OFFSET] = 0; //TODO

//	for (var i=0; i<F_Calendar.FIELD_COUNT; i++) {
//		alert("field: " + i + " " + this._fields[i]);
//	}
}

function F_Calendar__complete() {
	if (!this._isTimeSet) {
		this._updateTime();
	}
	if (!this._areFieldsSet) {
		this._computeFields();
		this._areFieldsSet = true;
		this._areAllFieldsSet = true;
	}
}

function F_Calendar__updateTime() {
	this._computeTime();
	this._isTimeSet = true;
}

function F_Calendar__computeTime() {
	var y,M,d,h,m,s,ms;
	var date = null;
	
	y = this._fields[F_Calendar.YEAR];
	if (y == null) y = F_Calendar._EPOCH_YEAR;
	M = this._fields[F_Calendar.MONTH];
	if (M == null) {
		M = 0;
		d = 1;
	}
	else {
		d = this._fields[F_Calendar.DATE];
		if (d == null) d = 1;
	}
	h = this._fields[F_Calendar.HOUR_OF_DAY];
	if (h == null) h = 0;
	m = this._fields[F_Calendar.MINUTE];
	if (m == null) m = 0;
	s = this._fields[F_Calendar.SECOND];
	if (s == null) s = 0;
	ms = this._fields[F_Calendar.MILLISECOND];
	if (ms == null) ms = 0;

	// Check maximum values...
	if ((h > this.f_getMaximum(F_Calendar.HOUR_OF_DAY)) ||
		(m > this.f_getMaximum(F_Calendar.MINUTE)) ||
		(s > this.f_getMaximum(F_Calendar.SECOND)) ||
		(ms > this.f_getMaximum(F_Calendar.MILLISECOND))) {
		date = null;
	} else {
		date = new Date(y,M,d,h,m,s,ms);
	}
	// JavaScript handles invalid dates by shifting days over months and months over years...
	if ((date.getFullYear() != y) || (date.getDate() != d) || (date.getMonth() != M)) {
		// Throw an exception
		date = null;
	}
	this._time = date.getTime();
}

function F_Calendar_add(field, amount) {
	if (amount == 0) {
		return;   // Do nothing!
	}
	this._complete();
	if (field == F_Calendar.YEAR) {
		var year = this._internalGet(F_Calendar.YEAR);
		if (this._internalGet(F_Calendar.ERA) == F_Calendar.AD) {
			year += amount;
			if (year > 0) {
				this.f_set(F_Calendar.YEAR, year);
			} else { // year <= 0
				this.f_set(F_Calendar.YEAR, 1 - year);
				// if year == 0, you get 1 BC
				this.set(F_Calendar.ERA, F_Calendar.BC);
			}
		} else { // era == BC
			year -= amount;
			if (year > 0) {
				this.f_set(F_Calendar.YEAR, year);
			} else { // year <= 0
				this.f_set(F_Calendar.YEAR, 1 - year);
				// if year == 0, you get 1 AD
				this.f_set(F_Calendar.ERA, F_Calendar.AD);
			}
		}
		// TODO
		//pinDayOfMonth();
	} else if (field == MONTH) {
	}
}
