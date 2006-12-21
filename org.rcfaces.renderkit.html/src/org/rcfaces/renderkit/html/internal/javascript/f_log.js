/* 
 * $Id$
 */

/**
 * f_log
 *
 * @class public final f_log extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {

	/**
	 * @field private static final number
	 */	
	_DEFAULT: 0,

 	/**
 	 * Fatal level.
 	 *
	 * @field public static final number
	 */
 	FATAL: 0,

 	/**
 	 * Error level.
 	 *
	 * @field public static final number
	 */
	ERROR: 1,

 	/**
 	 * War, level.
 	 *
	 * @field public static final number
	 */
	WARN: 2,

	
 	/**
 	 * Info level.
 	 *
	 * @field public static final number
	 */
  	INFO: 3,

	
 	/**
 	 * Debug level.
 	 *
	 * @field public static final number
	 */
	DEBUG: 4,

	
 	/**
 	 * Trace level.
 	 *
	 * @field public static final number
	 */
	TRACE: 5,

	/**
	 * @field private static
	 */
	_Levels: undefined,

	/**
	 * @field private static
	 */
	_Appenders: undefined,
	
	/**
	 * @method hidden static
	 * @return void
	 */
	Initializer: function() {
		var cb=window.f_logCB;
		if (!cb) {
			return;
		}
		
		f_log.AddAppenders({
			f_doAppend: cb
		});
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		f_log._Levels=undefined;
		f_log._Appenders=undefined;
	},
	/**
	 * @method public static
	 * @param String logName Name of the log.
	 * @return f_log Log object.
	 */
	GetLog: function(logName) {
		f_core.Assert(typeof(logName)=="string", "f_log.GetLog: logname parameter is invalid. ('"+logName+"')");

		var logs=f_log._logs;
		if (!logs) {
			logs=new Object;
			f_log._logs=logs;
		}
		
		var log=logs[logName];
		if (log) {
			return log;
		}
		
		var level=f_log._DEFAULT;
		if (f_core && f_core.DebugMode) {
			level=f_log.DEBUG;
		}
		
		var levels=f_log._Levels;
		if (levels) {
			var last;
			
			for(var name in levels) {
				if (name.length>0 && logName.indexOf(name)!=0) {
					continue;
				}
				
				if (last && last.length>name) {
					continue;
				}
				
				last=name;
				level=levels[name];
			}
		}
		
		log=f_log.f_newSystemInstance(logName, level);
		
		logs[logName]=log;
		
		return log;
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	AddLevels: function() {
		var levels=f_log._Levels;
		if (!levels) {
			levels=new Object;
			f_log._Levels=levels;
		}
		
		for(var i=0;i<arguments.length;) {	
			var name=arguments[i++];
			var level=arguments[i++];
			
			levels[name]=level;
		}
	},
	/**
	 * @method private static
	 * @return boolean
	 */
	_CallAppenders: function(event) {
		var appenders=f_log._Appenders;
		if (!appenders || !appenders.length) {
			return false;
		}
		
		try {
			window._ignoreLog=true;

			for(var i=0;i<appenders.length;i++) {
				var appender=appenders[i];
				
				if (typeof(appender.f_doAppend)!="function") {
					continue;
				}
				try {
					appender.f_doAppend(event);
					
				} catch (x) {
					alert("Append log event exception:\n"+x);
				}
			}
			
		} finally {
			window._ignoreLog=undefined;
		}
		
		return true;
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	AddAppenders: function() {
		var appenders=f_log._Appenders;
		if (!appenders) {
			appenders=new Array;
			f_log._Appenders=appenders;
		}
		
		f_core.PushArguments(appenders, arguments);
	}
}

var __prototype = {
	f_log: function(name, level) {
		this.f_super(arguments);
	
		if (typeof(level)!="number") {
			level=f_log.ERROR;
		}
		
		this._level=level;
		this._name=name;
	},
	/*
	f_finalize: function() {
		this._level=undefined; // number
		this._name=undefined; // string

		this.f_super(arguments);
	},
	*/
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_fatal: function(message, exception, window) {
		return this._log(f_log.FATAL, message, exception, window);
	},
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_error: function(message, exception, window) {
		return this._log(f_log.ERROR, message, exception, window);
	},
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_warn: function(message, exception, window) {
		return this._log(f_log.WARN, message, exception, window);
	},
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_info: function(message, exception, window) {
		return this._log(f_log.INFO, message, exception, window);
	},
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_debug: function(message, exception, window) {
		return this._log(f_log.DEBUG, message, exception, window);
	},
	/**
	 * @method public
	 * @param String message The message.
	 * @param Error exception
	 * @param Window window Window which was performed the log.
	 * @return boolean <code>true</code> if log has been processed.
	 */
	f_trace: function(message, exception, window) {
		return this._log(f_log.TRACE, message, exception, window);
	},
	_log: function(level, message, exception, win) {
		if (!win) {
			win=window;
		}
	
		if (this._level<level) {
			return false;
		}
		
		var event = {
			name: this._name,
			level: level,
			message: message,
			exception: exception,
			window: win,
			date: new Date
		};
		
		return f_log._CallAppenders(event);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isFatalEnabled: function() {
		return (this._level>=f_log.FATAL);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isErrorEnabled: function() {
		return (this._level>=f_log.ERROR);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isWarnEnabled: function() {
		return (this._level>=f_log.WARN);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isInfoEnabled: function() {
		return (this._level>=f_log.INFO);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isDebugEnabled: function() {
		return (this._level>=f_log.DEBUG);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isTraceEnabled: function() {
		return (this._level>=f_log.TRACE);
	}
}

var f_log=new f_class("f_log", null, __static, __prototype);
