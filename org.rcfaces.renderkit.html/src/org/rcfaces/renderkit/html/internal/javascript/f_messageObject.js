/*
 * $Id$
 */

/**
 * Class Message Object
 *
 * @class f_messageObject extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field public static final number
	 */
   	SEVERITY_INFO:  0,

 	/**
	 * @field public static final number
	 */
    SEVERITY_WARN:  1,

	/**
	 * @field public static final number
	 */
    SEVERITY_ERROR: 2,

	/**
	 * @field public static final number
	 */
    SEVERITY_FATAL: 3
}

function f_messageObject(severity, summary, detail) {
	f_core.Assert(typeof(severity)=="number", "Bad type of severity");
	f_core.Assert(summary, "Bad summary");

	this._severity=severity;
	this._summary=summary;
	this._detail=detail;
}

/*
f_messageObject.prototype.f_finalize=function() {
	this._severity=undefined; // number
	this._summary=undefined; // string
	this._detail=undefined; // string
}
*/

/**
 * @method public 
 * @return number Severity
 */
f_messageObject.prototype.f_getSeverity=function() {
	return this._severity;
}

/**
 * @method public 
 * @return String Summary
 */
f_messageObject.prototype.f_getSummary=function() {
	return this._summary;
}

/**
 * @method public 
 * @return String Detail
 */
f_messageObject.prototype.f_getDetail=function() {
	if (this._detail) {
		return this._detail;
	}
	
	return this._summary;
}

f_messageObject.f_getName=function() {
	return "f_messageObject";
}

for(var p in __static) {
	f_messageObject[p]=__static[p];
}
