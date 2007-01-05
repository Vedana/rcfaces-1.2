/*
 * $Id$
 */

/**
 * f_fireBug appender
 *
 * @class hidden f_fireBugAppender extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static={
	
	/**
	 * @method private static
	 * @return void
	 */
	_Profile: function() {
	},
	
	/**
	 * @method hidden static
	 * @return void
	 */
	Initializer: function() {	
	
		try {
			if (!window.console || !window.console.firebug) {
				return;
			}
		} catch (x) {
			return;
		}
			
 		// this est la classe !
		this.f_newInstance();

		if (!window.f_profilerCB) {
			f_core.SetProfilerMode(f_fireBugAppender._Profile);
		}
		
		f_core.Assert=console.assert;
	}
}
var __prototype = {
	f_fireBugAppender: function() {
		this.f_super(arguments);
		
		f_log.AddAppenders(this);
	},
	f_doAppend: function(event) {

		if (!window.console) {
			return;
		}

		var param=[];
		var message="";
	
		if (event.name ) {
			message+="%s:";
			param.push(event.name);
		}
		
		if (event.message) {
			message+=" %s";
			param.push(event.message);
		}
		
		if (event.exception) {
			message+=" %o";
			param.push(event.exception);
		}
		
		
		var method=console.debug;
		
		switch(event.level) {
		case f_log.FATAL:
			message="[FATAL] "+message;
		
		case f_log.ERROR:
			method=console.error;
			break;
			
		case f_log.WARN:
			method=console.warn;
			break;

		case f_log.INFO:
			method=console.info;
			break;

		case f_log.DEBUG:
			break;

		default: 
			message="[UNKNOWN LEVEL] "+message;
		}
		
		param.unshift(message);
		method.apply(console, param);
	}
}

var f_fireBugAppender=new f_class("f_fireBugAppender", null, __static, __prototype);
