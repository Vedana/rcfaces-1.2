/*
 * $Id$
 */

/**
 * f_time class
 *
 * @class public f_time extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

/**
 * @method public
 */
function f_time(hours, minutes, seconds, millis) {
	if (arguments.length==1) {
		if (hours instanceof Date) {	
			var date=hours;
			
			this._hours=date.getHours();
			this._minutes=date.getMinutes();
			this._seconds=date.getSeconds();
			this._millis=date.getMilliseconds();
			return;
		}

		if (typeof(hours)=="number") {
			this._hours=Math.floor((hours/(60*60*1000)));
			this._minutes=Math.floor((hours/(60*1000)) % 60);
			this._seconds=Math.floor((hours/1000) % 60);
			this._millis=Math.floor(hours % 1000);
			return;
		}
	}

	f_core.Assert(typeof(hours)=="number", "Invalid hours parameters ("+hours+")");
	this._hours=hours;

	this._minutes=0;
	this._seconds=0;
	this._millis=0;
	
	if (minutes) {
		f_core.Assert(typeof(minutes)=="number", "Invalid minutes parameters ("+minutes+")");
		this._minutes=minutes;
	
		if (seconds) {
			f_core.Assert(typeof(seconds)=="number", "Invalid seconds parameters ("+seconds+")");
			this._secondes=seconds;

			if (millis) {
				f_core.Assert(typeof(millis)=="number", "Invalid millis parameters ("+minutes+")");
				this._millis=millis;
			}
		}
	}
}

/**
 * @method public
 * @return number
 */
f_time.prototype.f_getHours=function() {
	return this._hours;
}

/**
 * @method public
 * @return number
 */
f_time.prototype.f_getMinutes=function() {
	return this._minutes;
}

/**
 * @method public
 * @return number
 */
f_time.prototype.f_getSeconds=function() {
	return this._seconds;
}

/**
 * @method public
 * @return number
 */
f_time.prototype.f_getMilliseconds=function() {
	return this._millis;
}
 
/**
 * @method public
 * @return String
 */
f_time.prototype.toString=function() {
	return "[f_time "+this._hours+":"+this._minutes+":"+this._seconds+"."+this._millis+"]";
}

/**
 * @method public
 * @return Date
 */
f_time.prototype.f_toDate=function() {
	return new Date(1, 0, 2000, this._hour, this._minute, this._second, this._millis);
}

/**
 * @method public
 * @return number
 */
f_time.prototype.f_getTime=function() {
	return ((((this._hours*60)+ this._minutes)*60)+ this._seconds)*1000+ this._millis;
}

/**
 * @method public static
 * @return String
 */
f_time.toString=function() {
	return "[class f_time]";
}

/**
 * @method public static
 * @return String
 */
f_time.f_getName=function() {
	return "f_time";
}

