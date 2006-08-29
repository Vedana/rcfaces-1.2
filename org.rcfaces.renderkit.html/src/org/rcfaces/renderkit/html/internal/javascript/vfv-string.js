/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 *
 * @file adonis_string.js
 * @company Vedana
 * @authors J.Merlin
 * @date
 * @revision
 *
 *
 *
 * Most of  the  original  code  is  from Sun Microsystems, Inc and part of the
 * String, Character java class
 * The following class is a JavaScript adaptation
 */

/** 
 * Character class
 * @class hidden F_Character
 */
function F_Character() {
}

F_Character.prototype.f_isDigit = function(c) {
	var d = c.charCodeAt(0);
	return (d > 47 && d < 58);
}

/*------------------------------------------------------------------------------
 * String class
 */

String.prototype.regionMatches = function(ignoreCase, toffset, other, ooffset, len) {
	var ta = this.valueOf();
	var to = toffset;
	var pa = other.valueOf();
	var po = ooffset;
	// Note: toffset, ooffset, or len might be near -1>>>1.
	if ((ooffset < 0) || (toffset < 0) || (toffset > this.length - len) ||
		(ooffset > other.length - len)) {
		return false;
	}
	while (len-- > 0) {
		var c1 = ta.charAt(to++);
		var c2 = pa.charAt(po++);
		if (c1 == c2) {
			continue;
		}
		if (ignoreCase) {
			// If characters don't match but case may be ignored,
			// try converting both characters to uppercase.
			// If the results match, then the comparison scan should
			// continue.
			var u1 = c1.toUpperCase();
			var u2 = c2.toUpperCase();
			if (u1 == u2) {
				continue;
			}
		}
		return false;
	}
	return true;
}

/**
 * @class hidden F_StringBuffer
 * @method F_StringBuffer
 * @decl public
 *
 * Constructeur de la classe F_StringBuffer
 *
 * @param
 * @return
 * @see
 */
function F_StringBuffer() {
	this._init(arguments);
}
F_StringBuffer.prototype._length = 0;
F_StringBuffer.prototype._value = null;

F_StringBuffer.prototype._init = function(args) {
	this._value = (args.length > 0)? args[0]:"";
	this._count = this._value.length;
}

F_StringBuffer.prototype.f_append = function(str) {
	this._value += str;
	this._count = this._value.length;
}

F_StringBuffer.prototype.f_length = function() {
	return this._value.length;
}

F_StringBuffer.prototype.f_setLength = function(newLength) {
	if (newLength <= 0) {
		this._value = "";
		this._count = 0;
	} else {
		// TODO
	}
}

F_StringBuffer.prototype.toString = function() {
	return this._value;
}
