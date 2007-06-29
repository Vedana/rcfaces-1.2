/*
 * $Id$
 */

/**
 *
 * @class f_json extends Object
 * @version $Revision$ $Date$
 */

if (!Object.prototype.toJSONString) {

    Array.prototype.toJSONString = function () {
        var a = [],     // The array holding the member texts.
            i,          // Loop counter.
            l = this.length,
            v;          // The value to be stringified.


// For each value in this array...

        for (i = 0; i < l; i += 1) {
            v = this[i];
            switch (typeof v) {
            case 'object':

// Serialize a JavaScript object value. Ignore objects thats lack the
// toJSONString method. Due to a specification error in ECMAScript,
// typeof null is 'object', so watch out for that case.

                if (v) {
                    if (typeof v.toJSONString === 'function') {
                        a.push(v.toJSONString());
                    }
                } else {
                    a.push('null');
                }
                break;

            case 'string':
            case 'number':
            case 'boolean':
                a.push(v.toJSONString());

// Values without a JSON representation are ignored.

            }
        }

// Join all of the member texts together and wrap them in brackets.

        return '[' + a.join(',') + ']';
    };


    Boolean.prototype.toJSONString = function () {
        return String(this);
    };


    Date.prototype.toJSONString = function () {

// Ultimately, this method will be equivalent to the date.toISOString method.

        function f(n) {

// Format integers to have at least two digits.

            return n < 10 ? '0' + n : n;
        }

        return '"' + this.getFullYear() + '-' +
                f(this.getMonth() + 1) + '-' +
                f(this.getDate()) + 'T' +
                f(this.getHours()) + ':' +
                f(this.getMinutes()) + ':' +
                f(this.getSeconds()) + '"';
    };


    Number.prototype.toJSONString = function () {

// JSON numbers must be finite. Encode non-finite numbers as null.

        return isFinite(this) ? String(this) : 'null';
    };


    Object.prototype.toJSONString = function () {
        var a = [],     // The array holding the member texts.
            k,          // The current key.
            v;          // The current value.

// Iterate through all of the keys in the object, ignoring the proto chain.

        for (k in this) {
            if (this.hasOwnProperty(k)) {
                v = this[k];
                switch (typeof v) {
                case 'object':

// Serialize a JavaScript object value. Ignore objects that lack the
// toJSONString method. Due to a specification error in ECMAScript,
// typeof null is 'object', so watch out for that case.

                    if (v) {
                        if (typeof v.toJSONString === 'function') {
                            a.push(k.toJSONString() + ':' + v.toJSONString());
                        }
                    } else {
                        a.push(k.toJSONString() + ':null');
                    }
                    break;

                case 'string':
                case 'number':
                case 'boolean':
                    a.push(k.toJSONString() + ':' + v.toJSONString());

// Values without a JSON representation are ignored.

                }
            }
        }

// Join all of the member texts together and wrap them in braces.

        return '{' + a.join(',') + '}';
    };


    (function (s) {

// Augment String.prototype. We do this in an immediate anonymous function to
// avoid defining global variables.

// m is a table of character substitutions.

        var m = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        };


        s.parseJSON = function (filter) {
            var j;

            function walk(k, v) {
                var i;
                if (v && typeof v === 'object') {
                    for (i in v) {
                        if (v.hasOwnProperty(i)) {
                            v[i] = walk(i, v[i]);
                        }
                    }
                }
                return filter(k, v);
            }

            if (/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/.test(this.
                    replace(/\\./g, '@').
                    replace(/"[^"\\\n\r]*"/g, ''))) {

                j = eval('(' + this + ')');

                if (typeof filter === 'function') {
                    j = walk('', j);
                }
                return j;
            }

            throw new SyntaxError('parseJSON');
        };


        s.toJSONString = function () {

            if (/["\\\x00-\x1f]/.test(this)) {
                return '"' + this.replace(/([\x00-\x1f\\"])/g, function (a, b) {
                    var c = m[b];
                    if (c) {
                        return c;
                    }
                    c = b.charCodeAt();
                    return '\\u00' +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                }) + '"';
            }
            return '"' + this + '"';
        };
    })(String.prototype);
}

var __statics = {
	/**
	 * @method public static
	 * @param String formattedObject A formatted format of a JSON object(s)
	 * @return any
	 */
	Parse: function(formattedObject) {
		f_core.Assert(typeof(formattedObject), "f_json.Parse: Invalid formattedObject parameter ("+formattedObject+")");

		return eval(formattedObject);
	},
	/**
	 * @method public static
	 * @param Object objectToFormatToString The object which will be formatted
	 * @param optional function filter Filter function
	 * @return String
	 */
	Format: function(objectToFormatToString, filter) {
		f_core.Assert(filter && typeof(filter)!="function", "f_json.Format: Invalid filter parameter ("+filter+")");

		return objectToFormatToString.toJSONString(filter);
	}
}

new f_class("f_json", {
	statics: __statics
});
