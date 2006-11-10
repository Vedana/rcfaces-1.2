/*
 * $Id$
 */

/**
 * @class hidden F_ParsePosition
 * @method F_ParsePosition
 * @decl public
 *
 * Parser indexing class contructor
 *
 * @param index Initialized to 0 if not specified
 * @return
 * @see
 */
function F_ParsePosition(pos) {
	this._init(arguments);
}
/*------------------------------------------------------------------------------
 * Instance private methods
 */
F_ParsePosition.prototype._init = function(args) {
	this._index = (args.length > 0)? args[0]:0;
	this._errorIndex = -1;
}

/*------------------------------------------------------------------------------
 * Instance public methods
 */
F_ParsePosition.prototype.f_setIndex = function(index) {
	this._index = index;
}
F_ParsePosition.prototype.f_getIndex = function() {
	return this._index;
}
F_ParsePosition.prototype.f_setErrorIndex = function(ei) {
	this._errorIndex = ei;
}
F_ParsePosition.prototype.f_getErrorIndex = function() {
	return this._errorIndex;
}
