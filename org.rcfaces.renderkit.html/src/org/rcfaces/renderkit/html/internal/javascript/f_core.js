/*
 * $Id$
 */

/**
 * f_core class
 *
 * @class public f_core extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
var f_core = {

	/**
	 * @field hidden static final String
	 */
	SERIALIZED_DATA: 	"VFC_SERIAL",

	/**
	 * @field private static final String
	 */
	_COMPONENT:			"VFC_COMPONENT",

	/**
	 * @field private static final String
	 */
	_EVENT: 			"VFC_EVENT",

	/**
	 * @field private static final String
	 */
	_VALUE:				"VFC_VALUE",

	/**
	 * @field private static final String
	 */
	_ITEM:				"VFC_ITEM",

	/**
	 * @field private static final String
	 */
	_DETAIL:			"VFC_DETAIL",
		
	/**
	 * @field private static final number
	 */
	_FOCUS_TIMEOUT_DELAY: 50,
		
	/**
	 * @field private static final String
	 */
	_REPORT_ERROR_URL: "/frameSetAppender/reportError.html",
	
	/**
	 * @field hidden static final number
	 */
	ELEMENT_NODE: 1,
	
	/**
	 * @field hidden static final number
	 */
	TEXT_NODE: 3,
	
	/**
	 * @field hidden static final number
	 */
	CDATA_SECTION_NODE: 4,
	
	/**
	 * @field hidden static final number
	 */
	DOCUMENT_NODE: 9,
	
	/**
	 * @field private static final number
	 */
	_RCFACES_EXITING: 1,

	/**
	 * @field private static final number
	 */
	_RCFACES_EXITED: 2,

	/**
	 * @field hidden static final String
	 */
	FIREFOX_1_0: "firefox.1.0",
		
	/**
	 * @field hidden static final String
	 */
	FIREFOX_1_5: "firefox.1.5",

	/**
	 * @field hidden static final String
	 */
	FIREFOX_2_0: "firefox.2.0",

	/**
	 * @field hidden static final String
	 */
	INTERNET_EXPLORER_6: "iexplorer.6",

	/**
	 * @field hidden static final String
	 */
	INTERNET_EXPLORER_7: "iexplorer.7",

	/**
	 * @field private static final String
	 */
	_UNKNOWN_BROWER: "unknown",

	/**
	 * Numero du bouton qui déclanche les popups. (Cela dépend de l'OS !)
	 *
	 * @field private static final number
	 */
	_POPUP_BUTTON: 2,

	/**
	 * @field hidden static final String
	 */
	JAVASCRIPT_VOID: "javascript:void(0)",

	/**
	 * @field private static final number
	 */
	_CLEAN_UP_ON_SUBMIT_DELAY: 20,
	
	/**
	 * @field private static final RegExp
	 */
	_BLOCK_TAGS: new RegExp(
		"^(ADDRESS|APPLET|BLOCKQUOTE|BODY|CAPTION|CENTER|COL|COLGROUP|DD|DIR|DIV|" +
		"DL|DT|FIELDSET|FORM|FRAME|FRAMESET|H1|H2|H3|H4|H5|H6|HR|IFRAME|LI|MENU|" +
		"NOSCRIPT|NOFRAMES|OBJECT|OL|P|PRE|TABLE|TBODY|TD|TFOOT|TH|THEAD|TR|UL){1}$", 
		"i"
	),

	/**
	 * @field private static final RegExp
	 */
	_FOCUSABLE_TAGS: new RegExp(
		"^(A|AREA|BUTTON|IFRAME|INPUT|OBJECT|SELECT|TEXTAREA){1}$"
	),
	
	/**
	 * @field private static final String[]
	 */
	_OPEN_WINDOW_KEYWORDS: ["width", "height", "channelmode", "fullscreen", "resizable", "titlebar", "scrollbars", "location", "toolbar", "directories", "status", "menubar", "copyhistory" ],
	
	/**
	 * @field hidden static boolean
	 */
	DebugMode:	undefined,

	/**
	 * @field hidden static boolean
	 */
	DesignerMode:	undefined,

	/**
	 * @field private static function
	 */
	_AjaxParametersUpdater: undefined,
	
	/**
	 * @field private static boolean
	 */
	_DisabledContextMenu: undefined,
	
	/**
	 * @field private static boolean
	 */
	_Logging: undefined,
	
	/**
	 * @field private static boolean
	 */
	_LoggingProfile: undefined,
	
	/**
	 * @field private static any
	 */
	_FocusTimeoutID: undefined,
	
	/**
	 * @field private static HTMLElement
	 */
	_FocusComponent: undefined,
	
	/**
	 * @field private static function[]
	 */
	_PostSubmitListeners: undefined,
	
	/**
	 * Throws a message if the expression is true.
	 *
	 * @method public static
	 * @param boolean expr Expression.
	 * @param String message The message.
	 * @return void
	 */
	Assert: function(expr, message) {
		if (expr) {
			return;
		}
			
		message="ASSERT ERROR: "+message;
		var ex=new Error(message);
		
		f_core.Error("f_assert", message, ex);
		
		throw ex;
	},
	/**
	 * @method private static
	 * @param number level
	 * @param String name
	 * @param String message
	 * @param optional Error exception
	 * @param optional Window win
	 * @return void
	 */
	_AddLog: function(level, name, message, exception, win) {
		if (!win) {
			win=window;
		}

		if (window.rcfacesLogCB) {
			window.rcfacesLogCB.apply(window, arguments);
			return;
		}
		
		if (f_core._Logging) {
			if (exception) {
				throw exception;
			}
			if (level<2) {
				window.status="Exception: "+message;
			}
			return;
		}
			
		try {
			f_core._Logging=true;

			if (level!==undefined) {
				if (typeof(name)!="string" && name.f_getName) {
					var className=name.f_getName();
					f_core.Assert(typeof(className)=="string", "f_core._AddLog: Invalid class name of object '"+name+"'.");
					name=className;
				}
				
				f_core.Assert(typeof(name)=="string", "f_core._AddLog: Invalid name of log '"+name+"'.");
				
				if (window._ignoreLog) {
					if (level===0) {
						var msg="Error: ("+name+"): "+message;
						if (exception) {
							msg+="\nException:\n"+f_core._FormatException(exception);
						}
						
						alert(msg);
					}
		
					return true;
				}
			}
					
			if (!window._flushLogs) {
				if (level<2) {
					var msg="Error: ("+name+"): "+message;
					if (exception) {
						msg+="\nException:\n"+f_core._FormatException(exception);
					}
					
					alert(msg);
				}
				
				var l=window._coreLogs;
				if (!l) {
					l=new Array;
					window._coreLogs=l;
				}
				l.push([level, name, message, exception, win]);
				return true;
			}
			
			var l=window._coreLogs;
			if (l) {
				window._coreLogs=undefined;
				
				f_core._Logging=undefined;
				for(var i=0;i<l.length;i++) {
					f_core._AddLog.apply(f_core, l[i]);
				}
				f_core._Logging=true;
			}
	
			if (level===undefined) {
				// Grosse astuce: c'etait histoire de faire afficher les logs.
				return true;
			}
	
			var log=f_log.GetLog(name);
			var fct;
						
			if (log) { 
				switch(level) {
				case 0:
					fct=log.f_fatal;
					break;
	
				case 1:
					fct=log.f_error;
					break;
	
				case 2:
					fct=log.f_warn;
					break;
		
				case 3:
					fct=log.f_info;
					break;
					
				case 4:
					fct=log.f_debug;
					break;
					
				case 5:
					fct=log.f_trace;
					break;
					
				default:
					fct=log.f_error;
				}
			}
			
			if (!fct && window._rcfacesExiting) {
				// Ok normal ...
				if (level<2) {
					var msg="Error: ("+name+"): "+message;
					if (exception) {
						msg+="\nException:\n"+f_core._FormatException(exception);
					}
					
					alert(msg);
				}
				
				return false;
			}
	
			f_core.Assert(typeof(fct)=="function", "f_core._AddLog: Log function is invalid '"+fct+"'.");
			if (!fct) {
				return false;
			}
			
			return fct.call(log, message, exception, win);
			
		} finally {
			f_core._Logging=undefined;
		}
	},
	/**
	 * @method private static
	 * @param Error ex
	 * @return String
	 */
	_FormatException: function(ex) {
		if (ex.number) { // IE normalement ...
			var msg="0x"+(ex.number & 0xffff).toString(16)+" name='"+ex.name+"'";
			if (ex.message) {
				msg+="\nmessage='"+ex.message+"'";
			}
			if (ex.description && ex.message!=ex.description) {
				msg+="\ndescription='"+ex.description+"'";
			}
				
			return msg;
		}
		
		return ex.toString();
	},
	/**
	 * @method private static
	 * @return void
	 */
	_FlushLogs: function() {		
		if (!window.f_log) {
			window._coreLogs=undefined;
			return;
		}

		window._flushLogs=true;
		f_core._AddLog();	
	},
	/**
	 * @method public static
	 * @param String name Log name.
	 * @param String message The message.
	 * @param optional Error exception An exception if any.
	 * @param optional hidden Window win	 
	 * @return void
	 */
	Debug: function(name, message, exception, win) {
		f_core._AddLog(4, name, message, exception, win);
	},
    /**
     * <p>Is debug logging currently enabled ?</p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
	 *
	 * @method public static
	 * @param String name Log name.
	 * @return boolean <code>true</code> if debug logging  is enabled.
	 */
	IsDebugEnabled: function(name) {
		if (typeof(name)!="string" && name.f_getName) {
			var className=name.f_getName();
			f_core.Assert(typeof(className)=="string", "f_core.IsDebugEnabled: Invalid class name of object '"+name+"'.");
			name=className;
		}

		f_core.Assert(typeof(name)=="string", "f_core.IsDebugEnabled: name parameter is invalid. ('"+name+"')");
		if (!window.f_log) {
			return (f_core.DebugMode);
		}
		
		return f_log.GetLog(name).f_isDebugEnabled();
	},
	/**
	 * @method public static
	 * @param String name Log name.
	 * @param String message The message.
	 * @param optional Error exception An exception if any.
	 * @param optional hidden Window win	 
	 * @return void
	 */
	Trace: function(name, message, exception, win) {
		f_core._AddLog(5, name, message, exception, win);
	},
    /**
     * <p>Is trace logging currently enabled ?</p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace. </p>
	 *
	 * @method public static
	 * @param String name Log name.
	 * @return boolean <code>true</code> if debug logging  is enabled.
	 */
	IsTraceEnabled: function(name) {
		if (typeof(name)!="string" && name.f_getName) {
			var className=name.f_getName();
			f_core.Assert(typeof(className)=="string", "f_core.IsTraceEnabled: Invalid class name of object '"+name+"'.");
			name=className;
		}

		f_core.Assert(typeof(name)=="string", "f_core.IsTraceEnabled: name parameter is invalid. ('"+name+"')");
		if (!window.f_log) {
			return (f_core.DebugMode);
		}
		
		return f_log.GetLog(name).f_isTraceEnabled();
	},
	/**
	 * @method public static
	 * @param String name Log name.
	 * @param String message The message.
	 * @param optional Error exception An exception if any.
	 * @param optional hidden Window win	 
	 * @return void
	 */
	Info: function(name, message, exception, win) {
		f_core._AddLog(3, name, message, exception, win);
	},
	/**
     * <p> Is info logging currently enabled ? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
 	 *
	 * @method public static
	 * @param String name Log name.
	 * @return boolean <code>true</code> if info logging  is enabled.
	 */
	IsInfoEnabled: function(name) {
		if (typeof(name)!="string" && name.f_getName) {
			var className=name.f_getName();
			f_core.Assert(typeof(className)=="string", "f_core.IsInfoEnabled: Invalid class name of object '"+name+"'.");
			name=className;
		}

		f_core.Assert(typeof(name)=="string", "f_core.IsInfoEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isInfoEnabled();
	},
	/**
	 * @method public static
	 * @param String name Log name.
	 * @param String message The message.
	 * @param optional Error exception An exception if any.	 
	 * @param optional hidden Window win	 
	 * @return void
	 */
	Warn: function(name, message, exception, win) {
		f_core._AddLog(2, name, message, exception, win);
	},
	/**
     * <p> Is warning logging currently enabled ? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
 	 *
	 * @method public static
	 * @param String name Log name.
	 * @return boolean <code>true</code> if info logging  is enabled.
	 */
	IsWarnEnabled: function(name) {
		if (typeof(name)!="string" && name.f_getName) {
			var className=name.f_getName();
			f_core.Assert(typeof(className)=="string", "f_core.IsWarnEnabled: Invalid class name of object '"+name+"'.");
			name=className;
		}

		f_core.Assert(typeof(name)=="string", "f_core.IsWarnEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isWarnEnabled();
	},
	/**
	 * @method public static
	 * @param String name Log name.
	 * @param String message The message.
	 * @param optional Error exception An exception if any.
	 * @param optional hidden Window win	 
	 * @return void
	 */
	Error: function(name, message, exception, win) {
	
//		f_core.Profile(null, "f_core.Error("+name+") "+message+"\n"+exception);
	
		if (f_core.DebugMode) {
			if (!exception) {
				exception=new Error(message);	
			}			
                          
			if (window.dump) {
				var msg=message;
				if (exception) {
					msg+="\nException:\n"+f_core._FormatException(exception);
				}
				
				window.dump(msg);
			}
		}

		if (!f_core._AddLog(1, name, message, exception, win)) {
			// Rien n'a été rapporté, on passe à la console !
			
			if (!exception) {
				exception=new Error(message);
			}
			throw exception;
		}

		if (f_core.DebugMode) {
			if (!exception) {
				exception=new Error(message);
			}
			throw exception;
		}
	},
	/**
     * <p> Is error logging currently enabled ? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error. </p>
 	 *
	 * @method public static
	 * @param String name Log name.
	 * @return boolean <code>true</code> if error logging  is enabled.
	 */
	IsErrorEnabled: function(name) {
		if (typeof(name)!="string" && name.f_getName) {
			var className=name.f_getName();
			f_core.Assert(typeof(className)=="string", "f_core.IsErrorEnabled: Invalid class name of object '"+name+"'.");
			name=className;
		}

		f_core.Assert(typeof(name)=="string", "f_core.IsErrorEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isErrorEnabled();
	},
	/**
	 * @method hidden static
	 * @param optional boolean cleanUpOnSubmit
	 * @return void
	 */
	SetCleanUpOnSubmit: function(cleanUpOnSubmit) {
		f_core.Assert(cleanUpOnSubmit===undefined || typeof(cleanUpOnSubmit)=="boolean", "f_core.SetCleanUpOnSubmit invalid cleanUpOnSubmit parameter ("+cleanUpOnSubmit+")");

		if (cleanUpOnSubmit===undefined) {
			cleanUpOnSubmit=true;
		}
		f_core._rcfacesCleanUpOnSubmit=cleanUpOnSubmit;
	},
	/**
	 * @method hidden static
	 * @param optional boolean debugMode  
	 * @return void
	 */
	SetDebugMode: function(debugMode) {
		f_core.Assert(debugMode===undefined || typeof(debugMode)=="boolean", "f_core.SetDebugMode: Invalid debugMode parameter ("+debugMode+")");

		if (debugMode===undefined) {
			debugMode=true;
		}
		f_core.DebugMode=debugMode;
	},
	/**
	 * @method hidden static
	 * @param optional function profilerMode
	 * @return void
	 */
	SetProfilerMode: function(profilerMode) {
		f_core.Assert(profilerMode===undefined || typeof(profilerMode)=="function", "f_core.SetProfilerMode: Invalid profilerMode parameter ("+profilerMode+")");

		if (profilerMode===false) {			
			window.rcfacesProfilerCB=false;
			return;
		}
		
		if (profilerMode===undefined) {
			profilerMode=true;
		}
		
		if (!window.rcfacesProfilerCB) {
			window.rcfacesProfilerCB=profilerMode;
		}
	},
	/**
	 * @method hidden static
	 * @param boolean designerMode
	 * @return void
	 */
	SetDesignerMode: function(designerMode) {
		f_core.Assert(typeof(designerMode)=="function", "f_core.SetDesignerMode: Invalid designerMode parameter ("+designerMode+")");

		f_core.DesignerMode=designerMode;
	},
	/**
	 * @method hidden static
	 * @param Window win
	 * @return void
	 */
	_InitLibrary: function(win) {
		this._window=win;
						
		var initDate=win._rcfacesInitLibraryDate;
		
		f_core.DebugMode=win._rcfacesDebugMode;
		
		var profilerCB=win.rcfacesProfilerCB;
		var logCB=win.rcfacesLogCB;

		if (window._rcfacesMultiWindowMode!==false && window._RCFACES_LEVEL3 && window._rcfacesMultiWindowClassLoader===true && !f_core._multiWindowCore) { 
				// Il faut copier les members de CORE pour les retrouver !
								
			var methods=new Object;
			for(var memberName in f_core) {
				methods[memberName]=f_core[memberName];
			}

			var kmethods=function() {};
			f_core._kmethodsPrototype=kmethods;
			kmethods.prototype=methods;
		}


		try {
			for(var w=win;w && w.parent!=w;w=w.parent) {
				var f=w.parent.rcfacesProfilerCB
				if (!f) {
					f=w.parent.f_profilerCB; // Legacy !
				}
				
				if (f) {
					profilerCB=f;
					win.rcfacesProfilerCB=profilerCB;
				}
				
				f=w.parent.rcfacesLogCB;
				if (f) {
					logCB=f;
					win.rcfacesLogCB=logCB;
				}
			}
		} catch (x) {
			// Il y aura peut etre des problemes de sécurité ... on laisse tomber !
		}
		
				
		f_core.Info(f_core, "_InitLibrary: start date="+initDate);
		
		if (profilerCB) {
			f_core.Info(f_core, "_InitLibrary: Enable profiler mode");
		
			var pageInit=win._rcfacesInitTimer;
			if (pageInit) {
				f_core.Profile(null, "page.init", pageInit);
			}
			
			f_core.Profile(null, "f_core.initializing", initDate);
		}

		if (win.rcfacesBuildId) {
			f_core.Info(f_core, "_InitLibrary: RCFaces buildId: "+rcfacesBuildId);
		}
	
		// Bug sous IE en LEVEL3 ... on ne peut pas compter sur le this !
		f_core._OnExit=function() {
			f_core.ExitWindow(win);
		}
	
		f_core.AddEventListener(win, "load", f_core._OnInit);
		f_core.AddEventListener(win, "unload", f_core._OnExit);

		if (f_core.IsInternetExplorer()) {
			f_core.AddEventListener(win.document, "selectstart", f_core._IeOnSelectStart);
		}
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @param String name Event name
	 * @param function Called callback.
	 * @param HTMLElement capture Component which will capture events.
	 * @return void
	 */
	AddEventListener: function(component, name, fct, capture) {
		f_core.Assert(component && 
			(component.nodeType==f_core.ELEMENT_NODE || 
				component.nodeType==f_core.DOCUMENT_NODE || 
				component.screen /* window ? */), "f_core.AddEventListener: Invalid component parameter ("+component+")");
		f_core.Assert(typeof(name)=="string", "f_core.AddEventListener: Invalid name parameter ("+name+")");
		f_core.Assert(typeof(fct)=="function", "f_core.AddEventListener: Invalid function parameter ("+fct+")");
		f_core.Assert(capture===undefined || 		
			(capture.nodeType==f_core.ELEMENT_NODE || 
				capture.nodeType==f_core.DOCUMENT_NODE || 
				capture.screen /* window ? */), "f_core.AddEventListener: Invalid capture parameter ("+capture+")");

		f_core.Debug(f_core, "AddEventListener: entering with component='"+component+"' name='"+name+"' fct=<not printed> capture='"+capture+"'");
	 	if (f_core.IsInternetExplorer()) {
	 		if (component.nodeType==f_core.DOCUMENT_NODE) {
	 			switch (name) {
	 			case "focus":
	 				component = component.body;
	 				name = "focusin";
	 				break;
	 			case "blur":
	 				component = component.body;
	 				name = "focusout";
	 				break;
	 			}
	 		}
	 		
		    component.attachEvent("on"+name, fct);
			    
		    if (capture) {
		 		if (capture.nodeType==f_core.DOCUMENT_NODE) {
		 			capture = capture.body;
		 		}
		    	if (capture.setCapture) {
				    capture.setCapture();
				} else {
					f_core.Debug(f_core, "AddEventListener: IE : setCapture not available on "+capture+" for "+name+" on "+component+" (id = "+component.id+", tagName="+component.tagName+", nodeType="+component.nodeType+")");
				}
			}
		    return;
		}

	    component.addEventListener(name, fct, !!capture);
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component[]
	 * @param String name Event name
	 * @param function Called callback.
	 * @return void
	 */
	RemoveEventListeners: function(components, name, fct) {
		
	 	if (f_core.IsInternetExplorer()) {
	 		name="on"+name;
	 		
			for(var i=0;i<components.length;i++) {
				components[i].detachEvent(name, fct);	
			}
			    
		    return;
		}

		
		for(var i=0;i<components.length;i++) {
		    components[i].removeEventListener(name, fct, false);		
		}
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @param String name Event name
	 * @param function Called callback.
	 * @param HTMLElement capture Component which captured events.
	 * @return void
	 */
	RemoveEventListener: function(component, name, fct, capture) {
		f_core.Assert(component && 
			(component.nodeType==f_core.ELEMENT_NODE || 
				component.nodeType==f_core.DOCUMENT_NODE || 
				component.screen /* window ? */), "f_core.RemoveEventListener: Invalid component parameter ("+component+")");
		f_core.Assert(typeof(name)=="string", "f_core.RemoveEventListener: Invalid name parameter ("+name+")");
		f_core.Assert(typeof(fct)=="function", "f_core.RemoveEventListener: Invalid function parameter ("+fct+") type='"+name+"' component={"+component.nodeType+"}"+component.tagName+"."+component.className+"#"+component.id);
		f_core.Assert(capture===undefined || 		
			(capture.nodeType==f_core.ELEMENT_NODE || 
				capture.nodeType==f_core.DOCUMENT_NODE || 
				capture.screen /* window ? */), "f_core.RemoveEventListener: Invalid capture parameter ("+capture+")");

	 	if (f_core.IsInternetExplorer()) {
		    if (capture) {
		 		if (capture.nodeType==f_core.DOCUMENT_NODE) {
		 			capture = capture.body;
		 		}
				capture.releaseCapture();
			}

	 		if (component.nodeType==f_core.DOCUMENT_NODE) {
	 			switch (name) {
	 			case "focus":
	 				component = component.body;
	 				name = "focusin";
	 				break;
	 				
	 			case "blur":
	 				component = component.body;
	 				name = "focusout";
	 				break;
	 			}
	 		}
			component.detachEvent("on"+name, fct);
			    
		    return;
		}

		if (capture) {
			capture=true;
		}

	    component.removeEventListener(name, fct, capture);
	},
	/**
	 * @method private static
	 * @return void
	 */
	_NoProfile: function() {		
	},
	/**
	 * @method hidden static
	 * @param boolean timeEnd
	 * @param String name of profile point.
	 * @param optional any Date of profile point. (Can be 'Date' or numer)
	 * @return void
	 */
	Profile: function(timeEnd, name, date, win) {
		if (f_core._LoggingProfile) {
			return;
		}
		if (!win) {
			win=window;
		}
		
		try {
			f_core._LoggingProfile=true;	
	
			var profiler=window.rcfacesProfilerCB;
			if (profiler===undefined) {
				return;
			}
			
			if (typeof(profiler)=="function") {
				try {
					profiler.call(window, timeEnd, name, date, win);
					
				} catch (x) {
					f_core.Error(f_core, "While calling external profiler.", x);
				}
				
				return;
			}
			
			if (profiler!==true || window._rcfacesExiting) {
				return;
			}
		
			if (!date) {
				date=new Date().getTime();
	
			} else if (date instanceof Date) {
				date=date.getTime();
			}
		
			if (timeEnd===false) {
				name="ENTER: "+name;
				
			} else if (timeEnd===true) {
				name="EXIT: "+name;
			}
		
			var diff=date-window._rcfacesInitLibraryDate;
			if (diff<1) {
				f_core.Debug("f_core.profile", "Profiler: "+name+"  "+date);
				return;
			}
	
			f_core.Debug("f_core.profile", "Profiler: "+name+"  +"+diff+"ms.");
			
		} finally {
			f_core._LoggingProfile=undefined;
		}
	},
	/**
	 * @method private static
	 * @context window:win
	 */
	_OnInit: function() {
		var now=new Date();
		var win=this;
		
		if (win.document.readyState) {
			// Sous IE;
			
			// Bug du frameSet
			if (!win.event) {
				// Une des frames doit positionner 'event'
				var frames=win.frames;
				for(var i=0;i<frames.length;i++) {
					if (frames[i].event) {
						win=frames[i];
						break;
					}
				}
			}
		}
		
		f_core.Profile(false, "f_core.onInit", now);
		try {		
			f_core._FlushLogs();	
			
			f_core.Info("f_core", "Install library (onload) on "+now);
			
			if (f_core.DebugMode) {
				var title=["DEBUG"];
				f_core.Info("f_core", "Enable f_core.DEBUG mode");
			
				var profiler=win.rcfacesProfilerCB;
				if (profiler) {
					title.push("PROFILER");
				}
				
				var version=win.rcfacesBuildId;
				if (version) {
					var compiled=false;					
					var compiledVersion=version.indexOf('c');
					if (compiledVersion>=0) {
						compiled=true;
						version=version.substring(0, compiledVersion);
					}
					
					if (!version.indexOf("0.")) {
						var d=new Date();
						d.setTime(parseInt(version.substring(2)));
						version=d;
					}
					title.push("version="+version);					
											
					if (compiled) {
						title.push("COMPILED");
					}
				}
				
				win.document.title+="  [Camelia: "+title.join(",")+"]";
			}
			
			if (f_core._DisabledContextMenu) {
				f_core._DisabledContextMenu=undefined;
				
				f_core.DisableContextMenu();
			}
							
			// Hook the forms
			var forms = win.document.forms;
			for (var i=0; i<forms.length; i++) {
				var f = forms[i];
	
				f_core.InitializeForm(f);
			}
			
			// Les objets non encore initializés
			var classLoader=f_classLoader.Get(win);	
			classLoader.f_initializeObjects();
	
			f_core.Profile(null, "f_core.onInit.objects");

			// Initialize packages here
			classLoader.f_onDocumentComplete();
	
		} finally {
			f_core.Profile(true, "f_core.onInit");
		}
	},
	/** 
	 * @method hidden static
	 */
	InitializeForm: function(f) {
		if (f._rcfacesInitialized) {
			return;
		}

		f._rcfacesInitialized=true;
		
		f_core.AddEventListener(f, "submit", f_core._OnSubmit);
		f_core.AddEventListener(f, "reset", f_core._OnReset);

		f_core.f_findComponent=f_core._FormFindComponent;


		// Pas forcement, si on ne veut pas que ca soit trop intrusif !
		// Obligatoire pour la validation !
		if (true) {
			try {
				var old=f.submit;
				
				f.submit = f_core._SystemSubmit;
				
				f._rcfacesOldSubmit = old;
			} catch (x) {
				// Dans certaines versions de IE, il n'est pas possible de changer le submit !
			}
		}
		
		f_core.Debug(f_core, "InitializeForm: Hook Html FORM tag id=\""+f.id+"\".");
	},
	/**
	 * @method private static
	 */
	_FormFindComponent: function() {
		// Nous sommes dans le scope d'un formulaire !
		
		return fa_namingContainer.FindComponents(this, arguments);
	},
	/**
	 * @method private static
	 * @return void
	 * @context window:win
	 */
	_OnExit: function() {
		var win=this;
		
		if (win.document.readyState && !win.event) {
			// Sous IE il peut y avoir un bug de positionnement de l'évènement

			function f(win) {
				try {
					if (win.event) {
						return win;
					}
					
					var frames=win.frames;
					for(var i=0;i<frames.length;i++) {
						win=f(frames[i]);
						if (win) {
							return win;
						}
					}
				} catch (x) {
					// Probleme de sécurité peut subvenir !
				}				
				return null;
			}			

			win=f(win);
	
			if (!win) {
				alert("RCFACES: PANIC can not identify unloaded window ! (root="+this.location+")");
				return;
			}
		}

		if (win._rcfacesExiting) {
			return;
		}
	
		f_core.ExitWindow(win);
	},
	/**
	 * @method hidden static
	 * @param Window win
	 * @param optional boolean asyncExit
	 * @return void
	 * @context window:win
	 */
	ExitWindow: function(win, asyncExit) {
		if (win._rcfacesExiting) {
			return;
		}
		
		win._rcfacesExiting=f_core._RCFACES_EXITING;
		
		var exitedState=f_core._RCFACES_EXITED;
		try {		
			var doc=win.document;
	
			f_core.Profile(false, "f_core.onExit("+((asyncExit)?true:false)+")");
			try {				
				f_core.RemoveEventListener(win, "load", f_core._OnInit);
				f_core.RemoveEventListener(win, "unload", f_core._OnExit);
				f_core._DesinstallModalWindow();
				
				if (f_core.IsInternetExplorer()) {
					f_core.RemoveEventListener(doc, "selectstart", f_core._IeOnSelectStart);
				}
				
				var timeoutID=f_core._FocusTimeoutID;
				if (timeoutID) {
					f_core._FocusTimeoutID=undefined;
					win.clearTimeout(timeoutID);
				}
		
				var forms = doc.forms;
				for (var i=0; i<forms.length; i++) {
					var form = forms[i];
					
					if (!form._rcfacesInitialized) {
						continue;
					}
					form._rcfacesInitialized=undefined;
		
					f_core.RemoveEventListener(form, "submit", f_core._OnSubmit);
					f_core.RemoveEventListener(form, "reset", f_core._OnReset);
		
					form._checkListeners=undefined; // List<method>
					form._resetListeners=undefined; // List<method>
					form._messageContext=undefined; // f_messageContext
					form.f_findComponent=undefined; // function
					form._serializedInputs=undefined; // Map<String, String>			
					
					if (form._rcfacesOldSubmit) {
						try {
							form.submit = form._rcfacesOldSubmit;
							
						} catch (x) {
							// Dans certaines versions de IE, il n'est pas possible de changer le submit !
						}
						
						form._rcfacesOldSubmit = undefined;
					}
				}
				
				if (win.rcfacesGarbageDisabled!==true) {
		
					// Terminate packages here
					var classLoader=f_classLoader.Get(win);

					classLoader.f_onExit();
					f_core.Finalizer();
				}
			
				if (win._rcfacesCloseWindow) {		
					win._rcfacesCloseWindow=undefined;
					win.close();
				}
				
			} finally {
				f_core.Profile(true, "f_core.onExit");
			}
		} finally {
			win._rcfacesExiting=exitedState;
		}
		
		// Ultime finalize
		f_core._window=undefined;
	},
	/**
	 * @method protected static
	 */
	Finalizer: function() {
		//	document._lazyIndex=undefined; // number
		f_core._AjaxParametersUpdater=undefined; // function
		f_core._FocusComponent=undefined; // HTMLElement
		f_core._PostSubmitListeners=undefined; // List<function>
		f_core._FocusTimeoutID=undefined; // any ???
		f_core._kmethodsPrototype=undefined; // Map<name, object>
		f_core._CachedForm=undefined; // HTMLFormElement
	},
	/**
	 * @method hidden static
	 */
	SetInputHidden: function(form, name, val) {
		f_core.Assert(form && form.tagName.toLowerCase()=="form", "f_core.SetInputHidden: Invalid form component ! "+form);
		f_core.Assert(name, "f_core.SetInputHidden: Invalid name of hidden input ! ("+name+")");
		
		if (val) {
			switch (typeof(val)) {
			case "string":
				break;

			case "boolean":
			case "number":
				val=String(val);
				break;
			
			default:
				f_core.Error(f_core, "SetInputHidden: Can not set a input hidden '"+name+"' with value '"+val+"'.");
			}
		}
		
		var inputs=f_core.GetElementsByTagName(form, "input");
				
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			
			if (input.name!=name) {
				continue;
			}
			
			f_core.Assert(input.type && input.type.toUpperCase()=="HIDDEN", "f_core.SetInputHidden: Input type is not hidden !");
			
			if (val===null) {
				input.parentNode.removeChild(input);
				return;
			}
			
			input.value=val;

			return;
		}
		
		if (!val) {
			// Il est vide : on laisse tomber ...
			return;
		}
		
		var input = form.ownerDocument.createElement("input");
		
		// Il faut specifier les caracteristiques avant le appendChild !
		input.type = "hidden";
		input.value = val;
		input.name = name;

		form.appendChild(input);

		return input;

	},
	/**
	 * @method hidden static
	 * @param HTMLElement elt
	 * @return HTMLFormElement
	 */
	GetParentForm: function(elt) {
		f_core.Assert(elt.ownerDocument, "f_core.GetParentForm: Invalid parameter element ("+elt+")");
	
		var form=f_core._CachedForm;
		if (form!==undefined) {
			return form;
		}
	
		// Optimisation s'il n'y a qu'une seule form !
		var forms=elt.ownerDocument.forms;
		switch(forms.length) {
		case 0:
			f_core.Debug(f_core, "GetParentForm: No form into document !");
			f_core._CachedForm=null;
			return null;

		case 1:
//			f_core.Debug(f_core, "GetParentForm: Only one form into document, returns "+forms[0].id);

			form=forms[0];
			f_core._CachedForm=form;
			return form;
		}
	
		for(form=elt;form;form=form.parentNode) {
			var tagName=form.tagName;
			if (form.nodeType!=f_core.ELEMENT_NODE || !tagName) {
				continue;
			}
			
			if (tagName.toLowerCase()!="form") {
				continue;
			}
			
			f_core.Debug(f_core, "GetParentForm: Parent form of '"+elt.id+"': "+form.id);
			
			return form;
		}

		f_core.Debug(f_core, "GetParentForm: Can not find any parent form for component '"+elt.id+"'.");
		return null;
	},
	/**
	 * @method static hidden
	 * @return f_component
	 */
	GetParentComponent: function(comp) {
		for(comp=comp.f_getParent();comp;comp=comp.parentNode) {
			if (!f_class.IsObjectInitialized(comp)) {
				continue;
			}
			
			return comp;
		}
		
		return null;
	},
	/**
	 * @method static hidden
	 * @param HTMLElement parent
	 * @param String tagName
	 * @param Object properties
	 * @return HTMLElement
	 */
	CreateElement: function(parent, tagName, properties) {
		f_core.Assert(parent && parent.nodeType==f_core.ELEMENT_NODE, "f_core.CreateElement: Invalid component ! ("+parent+")");		
		
		var doc=parent.ownerDocument;
		
		var ie=f_core.IsInternetExplorer();
		
		for(var i=1;i<arguments.length;) {
			tagName=arguments[i++];
			properties=arguments[i++];
			
			f_core.Assert(typeof(tagName)=="string", "f_core.CreateElement: Invalid tagName parameter ("+tagName+")");
			f_core.Assert(properties===undefined || typeof(properties)=="object", "f_core.CreateElement: Invalid properties parameter ("+properties+")");
			
			var element;
			
			if (ie && tagName.toLowerCase()=="input" && properties.type && properties.name) {
				element=doc.createElement("<input name=\""+properties.name+"\" type=\""+properties.type+"\">");
				delete properties.name;
				delete properties.type;
				
			} else {
				element=doc.createElement(tagName);
			}
			
			var textNode=null;
			var innerHtml=null;
			if (properties) {
				for(var name in properties) {
					var value=properties[name];
				
					switch(name.toLowerCase()) {
					case "classname":
					case "class":
						element.className=value;
						break;
						
					case "textnode":
						textNode=value;
						break;
						
					case "innerhtml":
						innerHtml=value;
						break;
					
					case "style":
						var rs=value.split(';');
						for(var j=0;j<rs.length;j++) {
							var rs2=rs[j].split(':');
							
							var rname=f_core.Trim(rs2[0]);
							var rvalue=f_core.Trim(rs2[1]);
							
							element.style[rname]=rvalue;
						}
						break;
					
					default:	
						if (!name.indexOf("css")) { /* ==0 !!! */
							element.style[name.substring(3, 4).toLowerCase()+name.substring(4)]=value;
							break;
							
						}
						
						switch(typeof(value)) {
						case "function":
						case "object":
							element[name]=value;
							break;
							
						default:
							element.setAttribute(name, value);
						}
									
					}
				}
			}
			
			parent.appendChild(element);
			
			if (textNode) {
				element.appendChild(doc.createTextNode(textNode));
			}
			
			if (innerHtml) {
				element.innerHTML=innerHtml;
			}
			
			parent=element;
		}
				
		return element;
	},
	/**
	 * @method static hidden
	 * @return void
	 */
	SetTextNode: function(component, text, accessKey) {
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.SetTextNode: Invalid component ! ("+component+")");
		f_core.Assert(text===null || typeof(text)=="string", "f_core.SetTextNode: Invalid text parameter ("+text+")");
		f_core.Assert(accessKey===undefined || accessKey===null || typeof(accessKey)=="string", "f_core.SetTextNode: Invalid accessKey parameter ("+accessKey+")");
		
		var doc=component.ownerDocument;

/*		
		if (text && f_core.IsGecko()) {
			text=f_core.EncodeHtml(text);			
		}
	*/	
		if (text && accessKey && accessKey.length) {
			var idx=text.toLowerCase().indexOf(accessKey.toLowerCase());
			if (idx>=0) {
				for(;component.firstChild;) {
					component.removeChild(component.firstChild);
				}

				if (idx) {
					component.appendChild(doc.createTextNode(text.substring(0, idx)));
				}

				var ul=doc.createElement("u");
				component.appendChild(ul);
				ul.className="f_accessKey";
				
				ul.appendChild(doc.createTextNode(text.charAt(idx)));
			
				if (idx+1<text.length) {
					component.appendChild(doc.createTextNode(text.substring(idx+1)));
				}
				
				return;
			}
		}

		var children=component.childNodes;
		for(var i=0;i<children.length;) {
			var child=children[i];
			if (child.nodeType!=f_core.TEXT_NODE) {
				//i++;
				component.removeChild(child);
				continue;
			}
			
			if (text!==undefined) {
				child.data=text;
				text=undefined;
				i++;
				continue;
			}
			
			component.removeChild(child);
		}
		
		if (text) {
			component.appendChild(doc.createTextNode(text));
		}
	},
	/**
	 * @method static hidden
	 */
	GetTextNode: function(component, concatChildren) {
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.GetTextNode: Invalid component ! ("+component+")");

		var children=component.childNodes;

		var text="";
		for(var i=0;i<children.length;i++) {
			var child=children[i];
			
			switch(child.nodeType) {
			case f_core.TEXT_NODE:
			case f_core.CDATA_SECTION_NODE:
				text+=child.data;
				break;
				
			case f_core.ELEMENT_NODE:
				if (concatChildren) {
					text+=f_core.GetTextNode(child, true);
				}
				break;
			}
			
		}
		
		return text;
	},
	/**
	 * @method private static
	 * @return boolean
	 * @context event:evt
	 */
	_OnReset: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var win;
		var form;
		
		var tagName=this.tagName;
		if (!tagName || tagName.toLowerCase()!="form") {
			// C'est une window ?

			if (evt.relatedTarget) {
				form = evt.relatedTarget;
				
			} else if (evt.srcElement) {
				form = evt.srcElement;
			}
			win=window;
			
		} else {		
			form=this;	
			win=f_core.GetWindow(form);
		}
		
		f_core.Assert(form && form.tagName.toLowerCase()=="form", "f_core._OnReset: Can not identify form ! ("+form+")");
		f_core.Assert(win, "f_core._OnReset: Can not identify window !");

		f_core.Info("f_core", "_OnReset: Catch reset event from form '"+form.id+"'.");

		if (win.f_event) {
			if (win.f_event.GetEventLocked(evt, true)) {
				return false;
			}
		}
		
		f_classLoader.Get(win).f_verifyOnSubmit();
		
		f_core._CallFormResetListeners(form, evt);
		
		// Appel de la validation ?
		if (f_env.GetCheckValidation()) {
			f_core._CallFormCheckListeners(form, true);
		}
		
		return true;
	},
	/**
	 * @method private static
	 * @context event:evt
	 */
	_OnSubmit: function(evt) {
		f_core.Profile(false, "f_core.SubmitEvent");
		try {
			if (!evt) {
				evt = f_core.GetJsEvent(this); //window.event;
			}
	
			// f_core.Assert(evt, "f_core._OnSubmit: Event is not known ?");
			// evt peut être null !
		
			var win;
			var form;
			
			if (!this.tagName || this.tagName.toLowerCase()!="form") {
				// this est une window ?
	
				if (evt.relatedTarget) {
					form = evt.relatedTarget;
					
				} else if (evt.srcElement) {
					form = evt.srcElement;
				}
				win=window;
				
			} else {		
				form=this;	
				win=f_core.GetWindow(form);
			}


			f_core.Assert(win, "f_core._OnSubmit: Can not identify window !");
	
			if (!win._rcfacesSubmitting && f_env.GetCancelExternalSubmit()) {
				return f_core.CancelJsEvent(evt);
			}
			
			f_core.Assert(form && form.tagName.toLowerCase()=="form", "f_core._OnSubmit: Can not identify form ! ("+form+")");
	
			f_core.Info(f_core, "_OnSubmit: Catch submit event from form '"+form.id+"'.");
	
			if (win.f_event) {
				if (win.f_event.GetEventLocked(evt, true)) {
					return f_core.CancelJsEvent(evt);
				}
			}
	
			var classLoader=f_classLoader.Get(win);
			if (!form._rcfacesInitialized) {
				//f_core.Assert(form._rcfacesInitialized, "f_core._OnSubmit: Not initialized form '"+form.id+"'.");
	
				// Cas ou l'utilisateur va plus vite que la musique ! (avant le onload de la page)
				
				if (f_env.IsSubmitUntilPageCompleteLocked()) {
					return f_core.CancelJsEvent(evt);
				}
				
				// On essaye d'initialiser les objets qui ne sont pas encore initializés
				classLoader.f_initializeObjects();
				
				// XXX Il faut peut etre attendre QUE TOUTS LES OBJETS soient initialisés ?
				
				// On initialize la form !				
				f_core.InitializeForm(form); 
			}
			
			var component=win.f_event.GetComponent();
			
			var immediate;
			if (win.f_event.GetType()==f_event.ERROR) {
				f_core.Debug(f_core, "_OnSubmit: Event is an Error, bypass check validation !");

				immediate=true;
				
			} else if (component) {			
				f_core.Debug(f_core, "_OnSubmit: Component which performs submit event is '"+((component)?component.id:"**UNKNOWN**")+"', call checkListeners="+ f_env.GetCheckValidation());
		
				var immediateFunction=component.f_isImmediate;
				if (typeof(immediateFunction)=="function") {
					immediate=immediateFunction.call(component);
		
					f_core.Debug(f_core, "_OnSubmit: Test immediate property of '"+component.id+"' = "+immediate);
				}
			}
			
					
			f_classLoader.Get(win).f_verifyOnSubmit();		
			
			if (immediate!==true && f_env.GetCheckValidation()) {
				var valid;
				try {
					valid = f_core._CallFormCheckListeners(form);
					
				} catch (x) {
					f_core.Error(f_core, "_OnSubmit: Error when checking listeners", x);
				}
				
				f_core.Profile(null, "f_core.SubmitEvent.checkListeners");
				
				f_core.Debug(f_core, "_OnSubmit: Validation of checkers returns: "+valid);
				if (!valid) {
					return f_core.CancelJsEvent(evt);
				}
			} else {
				f_core.Debug(f_core, "_OnSubmit: Bypass validation immediate="+immediate+" or checkValidation="+f_env.GetCheckValidation());
			}

			if (classLoader) {
				classLoader.f_serialize(form);
				f_classLoader.SerializeInputsIntoForm(form);
				
				f_core.Profile(null, "f_core.SubmitEvent.serialized");
			
			}
			if (!win._rcfacesSubmitting) {
				f_core._PostSubmit(form);

				if (win._rcfacesCleanUpOnSubmit!==false) {
					win.setTimeout(function () {
						if (win.f_core && win.f_core.ExitWindow) {
							win.f_core.ExitWindow(win, true);
						}
					}, f_core._CLEAN_UP_ON_SUBMIT_DELAY);
				}
			}
				
			return true;
		} finally {
			f_core.Profile(true, "f_core.SubmitEvent");
		}
	},
	/**
	 * @method private static
	 * @param Event jsEvent
	 * @return boolean
	 * @context event:jsEvent
	 */
	_SystemSubmit: function(jsEvent) {
		return f_core._Submit(this, this, jsEvent);
	},
	/**
	 * @method private static
	 * @return boolean
	 */
	_Submit: function(form, elt, event, url, target, createWindowParameters, closeWindow, modal) {
		f_core.Assert(createWindowParameters===undefined || createWindowParameters===null || typeof(createWindowParameters)=="object", "f_core._Submit: createWindowParameters parameter must be undefined or an object.");
		f_core.Assert(closeWindow===undefined || closeWindow===null || typeof(closeWindow)=="boolean", "f_core._Submit: closeWindow parameter must be undefined or a boolean.");
		f_core.Assert(modal===undefined || modal===null || typeof(modal)=="boolean", "f_core._Submit: modal parameter must be undefined or a boolean.");

		f_core.Profile(false, "f_core._submit("+url+")");

		try {
			// Check if we get called from the form itself and use it if none specified
			if (!form && (this != f_core)) {
				event = form;
				form = this;
			}
			
			// Intialize defaul return value
			var ret = false;
	
			var type;
			var jsEvt;
			if (typeof(event)=="string") {
				type=event;	
				event=f_event.GetEvent();
	
			} else if (!event) {
				event=f_event.GetEvent();
	
			} else if (event.cancelBubble!==undefined) { // Un event ... on peut pas faire instanceof Event 
				jsEvt=event;
			}
			
			if (!jsEvt) {
				jsEvt=(event)?event.f_getJsEvent():null;
			}
	
			// Get element from event info if given
			if (!elt && event) {
				elt = event.f_getComponent();
			}
	
			// Get form form element parent if not given
			if (!form && elt) {
				form = f_core.GetParentForm(elt);
			}
			if (!form) {
				var ex=new Error("Can not find form !");
				f_core.Error(f_core, "_Submit: Can not find form !", ex);
				throw ex;
				//return false;
			}

			var doc=form.ownerDocument;
			var win=f_core.GetWindow(doc);
			
			if (win.f_event.GetEventLocked(jsEvt, true)) {
				return false;
			}
	
			// Double check this is a real FORM
			f_core.Assert((form.tagName.toLowerCase()=="form"),"f_core._Submit: Invalid form '"+form.tagName+"'.");
	
			// Call onsubmit hook
			try {
				win._rcfacesSubmitting=true;
				
				var ret = f_core._OnSubmit.call(form, jsEvt);
				if (!ret) {
					return ret;
				}
				
			} finally {
				win._rcfacesSubmitting=undefined;
			}

			f_core.Profile(null, "f_core._submit.called");
			
			// Serialize element if found
			var id=null;
			if (elt) {
				id=elt.id;
				if (!id) {
					// Sous IE le changement de la propriété ID est Read-only !
					id=elt.name;
				}
			}
			
			// Serialize event name from event info if given
			if (!type && event) {
				type = event.f_getType();
			}
	
			// On les effectent quand meme, car cela peut etre le 2eme submit !
			f_core.SetInputHidden(form, f_core._COMPONENT, id);
			f_core.SetInputHidden(form, f_core._EVENT, type);
			
			var eventValue=(event)?event.f_getValue():null;
			f_core.SetInputHidden(form, f_core._VALUE, eventValue);
		
			var eventItem=(event)?event.f_getItem():null;
			if (eventItem) {
				eventItem=f_core._ConvertItem(eventItem);
			}
			f_core.SetInputHidden(form, f_core._ITEM, eventItem);

			var eventDetail=(event)?event.f_getDetail():null;
			f_core.SetInputHidden(form, f_core._DETAIL, eventDetail);
	
			// Keep the previous for further restore
			if (url) {
				if (!form._rcfacesOldAction) {
					form._rcfacesOldAction = form.action;
				}
				
				form.action = url;
				
			} else if (form._rcfacesOldAction) {
				form.action=form._rcfacesOldAction;
			}
	
			// Keep the previous for further restore
			if (target) {
				if (form._rcfacesOldTarget===undefined) {
					form._rcfacesOldTarget = form.target;
				}
				
				form.target = target;
				
			} else if (form._rcfacesOldTarget!==undefined) {
				form.target=form._rcfacesOldTarget;
			}
	
			win.f_event.EnterEventLock(f_event.SUBMIT_LOCK);
			var unlockEvents=false;
			try {
				if (createWindowParameters) {
					if (!createWindowParameters.target) {
						createWindowParameters.target=target;
					}
					var newWindow=f_core.OpenWindow(win, createWindowParameters, modal);
					
					if (newWindow) {
						f_core._FocusWindow(newWindow);
					}
				}
	
				f_core.Profile(null, "f_core._submit.preSubmit");
				
				// Don't replace the current handler form.submit() and call the previous
				if (win._rcfacesNoSubmit!==true) {
					form._rcfacesOldSubmit();
				}
	
				f_core.Profile(null, "f_core._submit.postSubmit");
		
				if (closeWindow) {
					win._rcfacesCloseWindow=true;
					return true;
				}
				
				if (createWindowParameters) {
					unlockEvents=true;

				} else {
					f_core._PostSubmit(form);
								
					if (win._rcfacesCleanUpOnSubmit!==false) {
						win.setTimeout(function () {
							if (win.f_core && win.f_core.ExitWindow) {
								win.f_core.ExitWindow(win, true);
							}
						}, f_core._CLEAN_UP_ON_SUBMIT_DELAY);
					}
				}
								
			} catch (ex) {
				// Dans le cas d'une exception, on libere les evenements, mais on renvoie l'exception ...
				unlockEvents=true;
				
				throw ex;
			
			} finally {
				if (unlockEvents) {

		//			var dlg = A_CORE._A_getDialogLock();
		//			if (dlg != null) {
		//				dlg.focus();
		//			}
					
					win.f_event.ExitEventLock(f_event.SUBMIT_LOCK);
				}
			}

			return true;
			
		} finally {		
			f_core.Profile(true, "f_core._submit("+url+")");
		}
	},
	/**
	 * @method private static
	 * @param Object item
	 * @return String
	 */
	_ConvertItem: function(item) {
		if (!item || typeof(item)=="string") {
			return item;
		}
		
		if (item.value) {
			return item.value;
		}
		
		if (item._value) {
			return item._value;
		}
		
		return String(item);
	},
	/**
	 * @method private static
	 * @return void
	 */
	_PostSubmit: function(form) {
		var postSubmitListeners=f_core._PostSubmitListeners;
		if (postSubmitListeners) {
			for(var i=0;i<postSubmitListeners.length;i++) {
				var postSubmitListener=postSubmitListeners[i];
				
				try {
					postSubmitListener.call(f_core, form);
					
				} catch (x) {
					f_core.Error(f_core, "_Submit: PostSubmitListener ("+postSubmitListener+") threw an exception.", x);
				}
			}
	
			f_core.Profile(null, "f_core._submit.postSubmitListeners("+postSubmitListeners.length+")");
		}
	
		// IE Progress bar bug only
		if (f_core.IsInternetExplorer()) {
			if (document.readyState=="loading") {
				var msg=f_env.Get("F_SUBMIT_PROGRESS_MESSAGE");
				if (msg) {
					window.defaultStatus = msg;
					document.body.style.cursor = "wait";
				}
			}
		}
	},
	/**
	 * 
	 * @method hidden static
	 * @param function listener
	 * @return void
	 */
	AddPostSubmitListener: function(listener) {
		f_core.Assert(typeof(listener)=="function", "f_core.AddPostSubmitListener: invalid listener parameter ("+listener+")");
		
		var postSubmitListeners=f_core._PostSubmitListeners;		
		if (!postSubmitListeners) {
			postSubmitListeners=new Array;
			f_core._PostSubmitListeners=postSubmitListeners;
		}
		
		postSubmitListeners.push(listener);
	},
	/**
	 * @method private static
	 */
	_FocusWindow: function(win) {
		f_core.Debug(f_core, "_FocusWindow: focus window "+win);
		
		try {
			win.focus();

		} catch (x) {
			// On est dans une situation trés strange !
			//f_core.Error(f_core, "Can not focus window "+win, x);
		}

		try {
			if (win.GetAttention) {
				win.GetAttention();
			}
		} catch (x) {
		}
	},
	/**
	 * @method private static
	 */
	_InstallModalWindow: function(modalWindow) {
		f_core._cameliaModalWindow=modalWindow;
				
		f_core.Debug("f_core", "Install modal window !");
		
		f_event.EnterEventLock(f_event.MODAL_LOCK);
		
		f_core.AddEventListener(document.body, "focus", f_core._ModalWindowFocus); 
		if (f_core.IsInternetExplorer()) {
			f_core.AddEventListener(document, "selectstart", f_core._ModalWindowFocus);
		}

/*
		try {
			modalWindow.onunload=f_core._CloseModalChildWindow;
			
		} catch (x) {
			f_core.Error("f_core", "Can not set onclose callback.", x);
		}
		*/
	},
	/**
	 * @method private static
	 */
	_DesinstallModalWindow: function() {
		var modalWindow=f_core._cameliaModalWindow;
		if (!modalWindow) {
			return;
		}
		
		try {
			modalWindow.onclose=null;
			
		} catch (x) {
			// Des problemes de sécurité peuvent survenir !
		}
		
		f_core.Debug("f_core", "Desinstall modal window !");
	
		f_core._cameliaModalWindow=undefined;

		f_event.ExitEventLock(f_event.MODAL_LOCK);

		f_core.RemoveEventListener(document.body, "focus", f_core._ModalWindowFocus);
		if (f_core.IsInternetExplorer()) {
			f_core.RemoveEventListener(document, "selectstart", f_core._ModalWindowFocus);
		}
	},
	/**
	 * @method private static
	 */
	_CloseModalChildWindow: function(evt) {
		if (!this.closed) {
			return;
		}
				
		if (!evt) {
			evt = this.event;
		}

		var win;
		if (evt.relatedTarget) {
			win = evt.relatedTarget;
			
		} else if (evt.srcElement) {
			win= evt.srcElement;
		}

		f_core.Debug("f_core", "CloseChildWindow: "+this+" window="+win+" event="+evt);
		
		if (f_core._cameliaModalWindow!=win) {
			return;
		}
		
		f_core._DesinstallModalWindow(win);
	},
	/**
	 * @method hidden static
	 * @return boolean <code>true</code> if the child window lock the current window.
	 */
	VerifyModalWindow: function() {
		var modalWindow=f_core._cameliaModalWindow;
		if (!modalWindow) {
			return false;
		}

		var closed;
		try {
			closed=modalWindow.closed;
			
		} catch (x) {
			// Sous IE on peut avoir un probleme de sécurité !
			closed=true; // On considere que la fenetre est detachée
		}
		
		if (closed) {
			f_core._DesinstallModalWindow();
			return false;
		}

		f_core._FocusWindow(modalWindow);
		
		return true;
	},
	/**
	 * @method private static
	 * @context event:event
	 */
	_ModalWindowFocus: function(event) {
		var modalWindow=f_core._cameliaModalWindow;
		if (!modalWindow) {
			return;
		}

		var closed;		
		try {
			closed=modalWindow.closed;
			
		} catch (x) {
			// Sous IE on peut avoir un probleme de sécurité !
		}
		
		if (closed) {
			f_core._DesinstallModalWindow();
			return;
		}
			
		f_core.Debug(f_core, "Catch focus of parent of a modal window !");

		f_core._FocusWindow(modalWindow);
		
		return true;
	},
	/**
	 * @method hidden static
	 * @param Window win
	 * @param Object parameters
	 * @param boolean modal
	 * @return Window
	 */
	OpenWindow: function(win, parameters, modal) {
		if (!win) {
			win=window;
		}
		var url=parameters.url;
		if (!url) {
			url = "about:blank";
		}
		var target=parameters.target;
		if (!target) {
			target = "T"+(new Date().getTime());
		}
		
		var features = new Object;
		features.left=parameters.x;
		features.top=parameters.y;

		if (parameters.dialog) {
			parameters.scrollbars=false;
			parameters.location=false;
			parameters.toolbar=false;
			parameters.directories=false;
			parameters.status=false;
			parameters.menubar=false;
			parameters.copyhistory=false;
		}
		
		var keywords=f_core._OPEN_WINDOW_KEYWORDS;
		for(var i=0;i<keywords.length;i++) {
			var name=keywords[i];
			
			var v=parameters[name];
			if (v===undefined) {
				continue;
			}
			if (!v) {
				v="no";
					
			} else if (v===true) {
				v="yes";
			}
			
			features[name]=v;
		}

		var deco=parameters.extra;
		if (deco) {
			for (var name in deco) {
				features[name]=deco[name];
			}
		}
		
		var s="";
		for(var name in features) {
			if (s.length) {
				s+=",";
			}
			s+=name+"="+features[name];
		}
		
		f_core.Debug(f_core, "OpenWindow: url="+url+" target="+target+" features="+s);
		
		var newWindow;
		try {
			newWindow=win.open(url, target, s);

		} catch (x) {
			f_core.Debug(f_core, "OpenWindow: while opening new window, exception.", x);
			newWindow=null;
		}
			
		if (!newWindow || newWindow.closed) { // Déjà fermé: c'est un popup blocker
			// Popup Blocker
			var s=f_env.GetOpenWindowErrorMessage();
			
			if (s) {
				alert(s);
				return null;
			}
			
			f_core.Error(f_core, "OpenWindow: Can not open window url='"+url+"' target='"+target+"' features='"+s+"'.", x);
			return null;
		}
		
		if (modal) {
			f_core._InstallModalWindow(newWindow);
		}
	
		return newWindow;
	},
	/**
	 * @method private static
	 * @param HTMLFormElement form
	 * @return boolean
	 */
	_CallFormCheckListeners: function(form, afterReset) {
		var checkListeners=form._checkListeners;
		if (!checkListeners || !checkListeners.length) {
		
			f_core.Debug(f_core, "_CallFormCheckListeners: No check listeners to call ...");
			return true;
		}
		
		var cfp=undefined;
		var cfs=undefined;
		var ces=undefined;
		
		// var messageContext=f_messageContext.Get(form); // Pas utilisé !
		
		for(var i=0;i<checkListeners.length;) {
			var component= checkListeners[i++];
			var checkListener=checkListeners[i++];
			
			if (checkListener) {
				var checkPre=checkListener.f_performCheckPre;
				if (checkPre && typeof(checkPre)=="function") {
					if (!cfp) {
						cfp=new Array;
					}
					cfp.push(checkListener);
				}
							
				var checkEvent=checkListener.f_performCheckValue;
				if (checkEvent && typeof(checkEvent)=="function") {
					if (!ces) {
						ces=new Array;
					}
					ces.push(checkListener);
				}
				
				var checkPost=checkListener.f_performCheckPost;
				if (checkPost && typeof(checkPost)=="function") {
					if (!cfs) {
						cfs=new Array;
					}
					cfs.push(checkListener);
				}
				continue;
			}

			if (!ces) {
				ces=new Array;
			}
			
			ces.push({
				_component: component,
				
				/**
				 * @method hidden
				 * @param f_event event
				 * @return boolean 
				 */
				f_performCheckValue: function(event) {
					var detail=event.f_getDetail();
					if (!detail) {
						detail=0;
					}
					if (afterReset) {
						detail|=f_event.RESET_DETAIL;
					}
					
					return this._component.f_fireEvent(f_event.VALIDATION, null, form, event.f_getValue(), null, detail);
				}
			});			
		}

		f_core.Debug(f_core, "_CallFormCheckListeners: "+checkListeners.length+" listeners"+" PreCheck="+(cfp?cfp.length:0)+" Check="+(ces?ces.length:0)+" PostCheck="+(cfs?cfs.length:0)+".");
		
		var event=new f_event(form, f_event.VALIDATION, null, form, true);
		
		var ret=true;
		try {
			if (cfp) {
				for(var i=0;i<cfp.length;i++) {
					var checkPre=cfp[i];
					
					try {
						checkPre.f_performCheckPre(event);
						
					} catch (x) {
						f_core.Error(f_core, "_CallFormCheckListeners: PreCheck value throws an exception : "+checkPre, x);
					}
				}
			}
			
			if (ces) {
				for(var i=0;i<ces.length;i++) {
					var checkEvent=ces[i];
					
					try {
						if (checkEvent.f_performCheckValue(event)===false) {
							ret=false;
				
							f_classLoader.Destroy(event);
							event=new f_event(form, f_event.VALIDATION, null, form, ret);
						}
						
					} catch (x) {
						f_core.Error(f_core, "_CallFormCheckListeners: Check value throws an exception : "+checkEvent, x);
					}
				}
			}
						
		} finally {
			try {
				if (cfs) {
					for(var i=0;i<cfs.length;i++) {
						var checkPost=cfs[i];
						
						try {
							checkPost.f_performCheckPost(event);
	
						} catch (x) {
							f_core.Error(f_core, "_CallFormCheckListeners: Post check value throws an exception : "+checkPost, x);
						}
					}
				}
			} finally {
				f_classLoader.Destroy(event);					
			}
		}
				
		return ret;
	},
	/**
	 * @method private static
	 * @param HTMLFormElement form
	 * @param Event event
	 * @return boolean
	 */
	_CallFormResetListeners: function(form, event) {
		var resetListeners=form._resetListeners;
		if (!resetListeners || !resetListeners.length) {
			return true;
		}
		
		var ret=true;
		for(var i=0;i<resetListeners.length && ret;i++) {
			var resetListener=resetListeners[i];
			
			if (resetListener.call(form, event)===false) {
				ret=false;
			}
		}
				
		return ret;
	},
	/**
	 * @method hidden static hidden
	 * @param HTMLElement component 
	 * @param optional Object listener
	 * @param optional boolean first
	 * @return void
	 */
	AddCheckListener: function(component, listener, first) {
		f_core.Assert(listener===undefined || typeof(listener)=="object", "f_core.AddCheckListener: Listener must be an object ! ("+listener+")");
		f_core.Assert(component.nodeType, "f_core.AddCheckListener: Invalid component parameter ("+component+")");

		var form=f_core.GetParentForm(component);
		f_core.Assert(form, "f_core.AddCheckListener: Can not get form of component '"+component.id+"'.");
		
		var checkListeners=form._checkListeners;
		if (!checkListeners) {
			checkListeners=new Array;
			form._checkListeners=checkListeners;
		}
		
		if (first) {
			checkListeners.unshift(component, listener);
			return;
		}
		
		checkListeners.push(component, listener);
	},
	/**
	 * @method hidden static hidden
	 * @param HTMLElement component 
	 * @param Object listener
	 * @return boolean
	 */
	RemoveCheckListener: function(component, listener) {
		var form=f_core.GetParentForm(component);

		var checkListeners=form._checkListeners;
		if (!checkListeners) {
			return false;
		}
		
		return checkListeners.f_removeElement(listener);
	},
	/**
	 * @method hidden static hidden
	 */
	AddResetListener: function(component, callback) {
		f_core.Assert(component.nodeType==f_core.ELEMENT_NODE, "f_core.AddResetListener: Component is invalid  ("+component+")");
		f_core.Assert(typeof(callback)=="function", "f_core.AddResetListener: Listener is invalid ("+callback+")");

		var form=f_core.GetParentForm(component);
		f_core.Assert(form, "f_core.AddResetListener: Can not get form of component '"+component.id+"'.");
		
		var resetListeners=form._resetListeners;
		if (!resetListeners) {
			resetListeners=new Array;
			form._resetListeners=resetListeners;
		}
		
		resetListeners.f_addElement(callback);
	},
	/**
	 * @method public static hidden
	 */
	RemoveResetListener: function(component, callback) {
		f_core.Assert(component.nodeType==f_core.ELEMENT_NODE, "f_core.RemoveResetListener: Component is invalid  ("+component+")");
		f_core.Assert(typeof(callback)=="function", "f_core.RemoveResetListener: Listener is invalid ("+callback+")");

		var form=f_core.GetParentForm(component);
		f_core.Assert(form, "f_core.RemoveResetListener: Can not get form of component '"+component.id+"'.");
		
		var resetListeners=form._resetListeners;
		if (!resetListeners) {
			return false;
		}	
		
		return resetListeners.f_removeElement(callback);
	},
	/**
	 * @method public static
	 * @param f_event event
	 * @return boolean <code>true</code> if success.
	 */
	SubmitEvent: function(event) {
		return f_core._Submit(null, null, event);
	},
	/**
	 * @method public static
	 * @param optional String url
	 * @param optional String dest Window name.
	 * @param optional HTMLElement elt
	 * @param optional f_event event 
	 * @param optional Object createWindowParameters
	 * @param optional boolean closeWindow
	 * @param optional boolean modal
	 * @return boolean <code>true</code> if success.
	 */
	Submit: function(url, dest, elt, event, createWindowParameters, closeWindow, modal) {
		if (!event && (url instanceof f_event)) {
			event=url;
			url=null;
		}
		return f_core._Submit(null, elt, event, url, dest, createWindowParameters, closeWindow, modal);
	},
	/**
	 * Submit the page, and open a new window to show the response.
	 * 
	 * @method public static
	 * @param optional String dest Window name.
	 * @param optional Object createWindowParameters
	 * @param optional boolean modal
	 * @param optional f_event event Event if any.
	 * @return boolean <code>true</code> if success.
	 */
	SubmitOpenWindow: function(dest, createWindowParameters, modal, event) {
		return f_core._Submit(null, null, event, null, dest, createWindowParameters, null, modal);
	},
	/**
	 * Submit the page, and open a new window to show the response.
	 * 
	 * @method public static
	 * @param optional String dest Window name.
	 * @param optional Object createWindowParameters
	 * @param optional f_event event Event if any.
	 * @return boolean <code>true</code> if success.
	 */
	SubmitModalDialog: function(dest, createWindowParameters, event) {
		if (!createWindowParameters) {
			createWindowParameters=new Object;
		}
		createWindowParameters.dialog=true;
	
		return f_core._Submit(null, null, event, null, dest, createWindowParameters, null, true);
	},
	/**
	 * Submit the page, and close the window.
	 *
	 * @method public static
	 * @param optional f_event event 
	 * @return boolean <code>true</code> if success.
	 */
	SubmitCloseWindow: function(event) {
		return f_core._Submit(null, null, event, null, null, null, true);
	},
	/**
	 * Returns the window associated to the specified element.
	 *
	 * @method hidden static
	 * @param HTMLElement elt HTML element.
	 * @return Window Window associated to the element.
	 */
	GetWindow: function(elt) {		
		f_core.Assert(elt && (elt.nodeType==f_core.ELEMENT_NODE || elt.nodeType==f_core.DOCUMENT_NODE), "f_core.GetWindow: Invalid elt parameter ("+elt+")");

		// Cas de IE, si elt est déjà un Document !
		var view=elt.window;
		if (view) {
			return view;
		}
		
		var doc;
		if (elt.nodeType==f_core.DOCUMENT_NODE) { // nodeType=9 => Document
			doc=elt;
	
		} else { // nodeType=f_core.ELEMENT_NODE => Element
			doc=elt.ownerDocument;
		}
		
		f_core.Assert(doc, "f_core.GetWindow: Can not find window of component '"+elt+"'.");
		
		view=doc.defaultView; // DOM Level 2  (Firefox)
		if (view) { 
			return view;
		}
		
		f_core.Assert(doc.parentWindow, "f_core.GetWindow: Invalid document: "+doc);
		
		return doc.parentWindow; // (IE)
	},
	/**
	 * @method private static
	 * @return boolean
	 */
	_InstanceOf: function(elt, claz, css) {
		if (css) {
			var cs=elt.className;
			if (!cs) {
				return null;
			}
			
			// Pas de classes composite ?
			if (cs.indexOf(' ')<0) {
				return (cs==claz)?elt:null;
			}
			
			var classes=cs.split(' ');
			for(var i=0;i<classes.length;i++) {
				if (classes[i]==claz) {
					return elt;
				}
			}
			
			return null;
		}
		var kclass=elt._kclass;
		if (kclass && kclass._name==claz) {
			return elt;
		}
		if (elt.nodeType==f_core.ELEMENT_NODE && f_core.GetAttribute(elt, "v:class")==claz) {
			return f_classLoader.Get(window).f_init(elt);
		}
		return null;
	},
	/**
	 * Find a child with a specified css class.
	 *
	 * @method hidden static
	 * @param HTMLElement elt Start node.
	 * @param String claz Css class name. 
	 * @return HTMLElement
	 */
	GetChildByCssClass: function(elt,claz) {
		return f_core.GetChildByClass(elt,claz,true);
	},
	/**
	 * @method hidden static
	 * @param HTMLElement elt Root of the search.
	 * @param String tagName Tag name.
	 * @return HTMLElement[]
	 */
	GetElementsByTagName: function(elt, tagName) {
		f_core.Assert(typeof(elt)=="object" && elt.nodeType, "f_core.GetElementsByTagName: Invalid element parameter ("+elt+").");
		f_core.Assert(typeof(tagName)=="string" && tagName.length, "f_core.GetElementsByTagName: Invalid tagName parameter ("+tagName+").");

		if (!f_env._SensitiveCaseTagName) {
			return elt.getElementsByTagName(tagName);
		}

		var tags=elt.getElementsByTagName("*");
		if (tagName=="*") {
			return tags;
		}

		var ret=new Array;
		tagName=tagName.toLowerCase();
	
		for(var i=0;i<tags.length;i++) {
			var tag=tags[i];
			
			if (tag.tagName.toLowerCase()!=tagName) {
				continue;
			}	
			
			ret.push(tag);
		}
		
		return ret;
	},
	
	/**
	 * Find a child with a specified class.
	 *
	 * @method hidden static
	 * @param HTMLElement elt Start node.
	 * @param String claz Class name.
	 * @param hidden boolean css Search Css class.
	 * @return HTMLElement
	 */
	GetChildByClass: function(elt,claz,css) {
		var stack=[elt];		
		for(;stack.length;) {
			var n=stack.pop();
			
			var comp = f_core._InstanceOf(n, claz, css);
			if (comp) {
				return comp;
			}
			
			var cn=n.childNodes;
			if (!cn.length) {
				continue;
			}
			
			for(var i=0;i<cn.length;i++) {
				n=cn[i];
				if (n.nodeType!=f_core.ELEMENT_NODE) {
					continue;
				}
				
				stack.push(n);
			}
		}
		
		return null;
	},
	/**
	 * Find a child by its identifier.  <b>(The naming separator IS ':')</b>
	 *
	 * @method public static
	 * @param String id The identifier of the component. (naming separator is ':')
	 * @param optional HTMLDocument doc The document.
	 * @return HTMLElement
	 */
	FindComponent: function(id, doc) {
		f_core.Assert(typeof(id)=="string", "f_core.FindComponent: Invalid id parameter '"+id+"'.");
		f_core.Assert(doc===undefined || (doc && doc.nodeType==f_core.DOCUMENT_NODE), "f_core.FindComponent: Invalid document parameter '"+doc+"'.");

		if (!doc) {
			doc=document;
		}
		
		return fa_namingContainer.FindComponent(doc.body, id);
	},
	/**
	 * Find a child by its identifier. <b>(The naming separator might not be ':')</b>
	 *
	 * @method public static
	 * @param String id  The identifier of the component. (naming separator might not be is ':')
	 * @param optional HTMLDocument document
	 * @param hidden optional boolean noCompleteComponent
	 * @deprecated Replaced by f_core.GetElementByClientId() .
	 * @return HTMLElement
	 */
	GetElementById: function(id, document, noCompleteComponent) {
		return f_core.GetElementByClientId.apply(f_core, arguments);
	},
	/**
	 * Find a child by its identifier. <b>(The naming separator might not be ':')</b>
	 *
	 * @method public static
	 * @param String id  The identifier of the component. (naming separator might not be is ':')
	 * @param optional HTMLDocument doc Document.
	 * @param hidden optional boolean noCompleteComponent Dont complete component !
	 * @return HTMLElement
	 */
	GetElementByClientId: function(id, doc, noCompleteComponent) {
		f_core.Assert(typeof(id)=="string", "f_core.GetElementByClientId: Invalid id parameter '"+id+"'.");
		f_core.Assert(doc===undefined || (doc && doc.nodeType==f_core.DOCUMENT_NODE), "f_core.GetElementByClientId: Invalid document parameter '"+doc+"'.");
		
		if (!doc) {
			doc=document;
		}
		var obj = doc.getElementById(id);
		var found=obj;
		
		if (!obj) {
			// On peut toujours chercher dans les forms du document ....
			// s'il n'y a pas de séparateurs !
			obj=fa_namingContainer.SearchElementById(doc, id);
		}
		
		if (obj && f_class.IsObjectInitialized(obj)) {
			return obj;
		}
		
		var win=f_core.GetWindow(doc);
		var classLoader=f_classLoader.Get(win);
		if (!obj) {
			// Objet pas trouvé, on passe l'ID à la methode _init !
			obj = classLoader.f_init(id, true);
			
		} else {		
			obj = classLoader.f_init(obj, true);
		}
		
		if (!obj) {
			return found;
		}
		
		// Notre composant est trouvé mais il n'était pas initialisé !
		if (noCompleteComponent!==true) {
			if (typeof(obj.f_completeComponent)=="function") {
				obj.f_completeComponent();
			}
		}
		return obj;
	},
	/** 
	 * @method hidden static
	 * @param Element element
	 * @param String attributeName
	 * @param optional any defaultValue
	 * @return any
	 */
	GetAttribute: function(element, attributeName, defaultValue) {
		f_core.Assert(element && element.nodeType==f_core.ELEMENT_NODE, "f_core.GetAttribute: Object parameter is not a valid node ! ("+element+")");
		f_core.Assert(typeof(attributeName)=="string" && attributeName.length, "f_core.GetAttribute: attributeName parameter is invalid. ("+attributeName+")");

		try {
			var value=element.getAttribute(attributeName);
			if (value!==undefined && value!==null) {
				// Transforme les &quote; =>  "
				// et pour finir transforme les &amp; => &

/*
				if (value.length>4 && value.indexOf('&')>=0 && f_core.IsGecko()) {
					value=value.replace(/&quote;/g, '"').replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, "&");
				}
				*/

				return value;
			}
			
		} catch (x) {
			/* ignore, in IE6 calling on a table results in an exception */
		}

		return defaultValue;
	},	
	/** 
	 * @method hidden static
	 * @param Element element
	 * @param String attributeName
	 * @param optional number defaultValue
	 * @return number
	 */
	GetNumberAttribute: function(element, attributeName, defaultValue) {
		f_core.Assert(defaultValue===undefined || typeof(defaultValue)=="number", "f_core.GetNumberAttribute: defaultValue parameter is invalid. ("+defaultValue+")");

		var value=f_core.GetAttribute(element, attributeName)
		if (value) {
			return parseInt(value, 10);
		}

		return defaultValue;
	},	
	/** 
	 * @method hidden static
	 * @param Element element
	 * @param String attributeName
	 * @param optional boolean defaultValue
	 * @return boolean
	 */
	GetBooleanAttribute: function(element, attributeName, defaultValue) {
		f_core.Assert(defaultValue===undefined || typeof(defaultValue)=="boolean", "f_core.GetBooleanAttribute: defaultValue parameter is invalid. ("+defaultValue+")");

		var value=f_core.GetAttribute(element, attributeName)
		if (value) {
			return (value.toLowerCase()!="false");
		}

		return defaultValue;
	},	
	/** 
	 * Returns true if component (and its ancestors) is visible.
	 *
	 * @method hidden static
	 * @param f_component component
	 * @return boolean
	 */
	IsComponentVisible: function(component) {
		f_core.Assert(component, "f_core.IsComponentVisible: Component is null !");
		f_core.Assert(component.nodeType==f_core.ELEMENT_NODE, "f_core.IsComponentVisible: Component parameter is invalid ("+component+")");

		// En visibility="hidden"  il y a peut être 
		if (component.clientWidth || component.clientHeight) {
			return true;
		}

		for(;component;component=component.parentNode) {
			var style=component.style;
			
			if (!style) {
				continue;
			}
			
			if (style.visibility=="hidden") {
				return false;
			}
			
			if (style.display=="none") {
				return false;
			}
		}
		
		return true;
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @return boolean
	 */
	ForceComponentVisibility: function(component) {
		if (f_core.IsComponentVisible(component)) {
			return true;
		}
		
		var parents=new Array;
		for(;component;component=component.parentNode) {
			var style=component.style;
			
			if (!style) {
				continue;
			}
			
			parents.unshift(component);
		}

		if (!parents) {
			return false;
		}

		for(var i=0;i<parents.length;i++) {
			component=parents[i];
			
			var style=component.style;

			if (style.visibility!="hidden" && style.display!="none") {
				return false;
			}
			
			if (typeof(component.f_setVisible)!="function") {
				// Ce n'est pas un composant camelia qui est capable de se rendre visible tout seul !
				
				if (i<1) {
					// Pas de parent !
					return false;
				}
				
				// Le parent est peut etre capable de forcer l'affichage de ce composant !
				var parent=parents[i-1];
				if (typeof(parent.f_forceChildVisibility)!="function") {
					// non ? c'est perdu !
					return false;
				}
				
				if (parent.f_forceChildVisibility(component)!==true) {
					// Ne sait pas le faire .... perdu !
					
					return false;
				}
				
				// Ok on passe au suivant !
				continue;
			}
			
			component.f_setVisible(true);
		}
		
		return true;		
	},
	/**
	 * Returns absolute position.
	 *
	 * @method hidden static
	 * @param HTMLElement component
	 * @return Object 
	 */
	GetAbsolutePosition: function(component) {
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.GetAbsolutePosition: Invalid component parameter '"+component+"'.");

		var curTop = 0;
		var curLeft= 0;
		
		var gecko=f_core.IsGecko();
		
	//	f_core.Debug(f_core, "Get absolutePos of '"+component.id+"'.");
		var offsetParent=component.offsetParent;
		if (offsetParent) {
			var doc=component.ownerDocument;
			
			var body=doc.body;
			var documentElement=doc.documentElement;
		
			for (;;) {
				curTop += component.offsetTop 
				curLeft += component.offsetLeft;
				
				if (!offsetParent) {
					break;
				}

				// On cherche le scroll intermédiaire !				
				for(component=component.parentNode;component!=offsetParent;component=component.parentNode) {
					curTop -= component.scrollTop;
					curLeft -= component.scrollLeft;
				}
	
				if (component!=body && component!=documentElement) {
					curTop -= component.scrollTop;
					curLeft -= component.scrollLeft;
			
					//if (gecko) {
						// C'est dans l'offsetTop
						// curTop+=f_core.ComputeBorderLength(component, "top")		
						// curLeft+=f_core.ComputeBorderLength(component, "left")
					//}		
				}
								
			//	f_core.Debug(f_core, " Sub absolutePos of '"+component.id+"' x="+component.offsetLeft+" y="+component.offsetTop+"  totX="+curLeft+" totY="+curTop);
		
				offsetParent = component.offsetParent;
			}
		} else {
			// ???
			if (component.x) {
				curLeft+=component.x;
			}
			if (component.y) {
				curTop += component.y;
			}
		}
		
		//f_core.Debug(f_core, "  End absolutePos x="+curLeft+" y="+curTop);
		return { x: curLeft, y: curTop };
	},
	/**
	 * @method hidden static
	 * @param optional String version
	 * @return boolean 
	 */
	IsGecko: function(version) {
		if (!f_core._browser) {
			f_core._SearchBrowser();
		}
		
		switch(f_core._browser) {
		case f_core.FIREFOX_2_0:
			return (!version || version==f_core.FIREFOX_2_0);
			
		case f_core.FIREFOX_1_5:
			return (!version || version==f_core.FIREFOX_1_5);
				
		case f_core.FIREFOX_1_0:
			return true;
		}
		
		return false;
	},
	/**
	 * @method hidden static
	 * @return boolean
	 */
	IsGeckoDisableDispatchKeyEvent: function() {
		if (!f_core.IsGecko()) {
			return false;
		}
		
		if (f_core._browser_major>1 || f_core._browser_release>1) {
			return true;
		}
		if (f_core._browser_minor>=5) {
			return true;
		}
		
		return false;
	},
	/**
	 * @method hidden static
	 * @param optional String version
	 * @return boolean
	 */
	IsInternetExplorer: function(version) {
		if (!f_core._browser) {
			f_core._SearchBrowser();
		}
		
		switch(f_core._browser) {
		case f_core.INTERNET_EXPLORER_7:
			return (!version || version==f_core.INTERNET_EXPLORER_7);
				
		case f_core.INTERNET_EXPLORER_6:
			return (!version || version==f_core.INTERNET_EXPLORER_6);
		}
		
		return false;
	},
	/**
	 * @method private static
	 * @return void
	 */
	_ReturnsAlwaysFalse: function () {
		return false;
	},
	/**
	 * @method private static
	 * @return boolean
	 */
	_SearchBrowser: function() {
		var agt=window.navigator.userAgent.toLowerCase();

		f_core.Info(f_core, "_SearchBrowser: Navigator agent: "+agt);
		f_core._browser=f_core._UNKNOWN_BROWER;

		var idx=agt.indexOf("msie ");
		if (idx>=0) {
			var idx2=agt.indexOf(';', idx);
			
			var version=agt;
			if (idx2>idx) {
				version=agt.substring(idx+5, idx2);
			}
			
			var vs=version.split(".");
			try {
				f_core._browser_major=parseInt(vs[0], 10);
				f_core._browser_release=parseInt(vs[1], 10);
				
			} catch (ex) {
				f_core.Error(f_core, "_SearchBrowser: Can not parse msie version '"+version+"'.", ex);
				version=-1;
			}
			
			var scriptEngine=ScriptEngine();
			var scriptEngineMajorVersion=ScriptEngineMajorVersion();
			var scriptEngineMinorVersion=ScriptEngineMinorVersion();
			
			f_core._ieScriptEngine57=(scriptEngineMajorVersion>5) || (scriptEngineMajorVersion==5 && scriptEngineMinorVersion>=7);
			f_core.IsGecko=f_core._ReturnsAlwaysFalse;
			
			if (f_core._browser_major>=7) {
				f_core._browser=f_core.INTERNET_EXPLORER_7;

				f_core.Info(f_core, "Microsoft Internet Explorer 7 detected ! (script engine '"+scriptEngine+"' "+scriptEngineMajorVersion+"."+scriptEngineMinorVersion+")");
				return true;
			}
			
			if (f_core._browser_major>=6) {
				f_core._browser=f_core.INTERNET_EXPLORER_6;

				f_core.Info(f_core, "Microsoft Internet Explorer 6 detected ! (script engine '"+scriptEngine+"' "+scriptEngineMajorVersion+"."+scriptEngineMinorVersion+")");
				return true;
			}
			
			f_core.Info(f_core, "_SearchBrowser: Invalid version of Microsoft Internet Explorer !");
			
			return false;
		}
		
		var firefox=agt.indexOf("firefox");
		if (firefox<0) {
			firefox=agt.indexOf("mozilla");
		}
		if (firefox>=0) {			
			// On coupe aprés le premier espace, ou la fin de la chaine !
			var p1=agt.indexOf(" ",firefox);
			if (p1<0) {
				p1=agt.length
			}
			
			var version=agt.substring(firefox, p1);
			var vs=version.split("/");
			if (vs.length>1) {
				vs=vs[1].split(".");
				if (vs.length>0) {
					try {
						f_core._browser_major=parseInt(vs[0], 10);

					} catch (ex) {
						f_core.Error(f_core, "_SearchBrowser: Can not parse firefox version '"+version+"'.", ex);
						return false;
					}
				}
				
				if (vs.length>1) {	
					try {
						f_core._browser_release=parseInt(vs[1], 10);
						
					} catch (ex) {
						f_core.Debug(f_core, "_SearchBrowser: Can not parse release version ! (release="+vs[1]+")");
					}
				}
				if (vs.length>2) {	
					try {
						f_core._browser_minor=parseInt(vs[2], 10);
						
					} catch (ex) {
						f_core.Debug(f_core, "Can not parse minor version ! (minor="+vs[2]+")");
					}
				}
			}

			f_core.Debug(f_core, "_SearchBrowser: Firefox version: major="+f_core._browser_major+" release="+f_core._browser_release+" minor="+f_core._browser_minor);
			f_core.IsInternetExplorer=f_core._ReturnsAlwaysFalse;

			if (f_core._browser_major>=2) {			
				f_core._browser=f_core.FIREFOX_2_0;
				
				f_core.Info(f_core, "Firefox 2.0 detected !");
				return true;
			}

			if (f_core._browser_major==1 && f_core._browser_release>=5) {			
				f_core._browser=f_core.FIREFOX_1_5;
				
				f_core.Info(f_core, "Firefox 1.5 detected !");
				return true;
			}
			
			if (f_core._browser_major>=1) {
				f_core._browser=f_core.FIREFOX_1_0;

				f_core.Info(f_core, "Firefox 1.0 detected !");
				return true;
			}
			
			f_core.Info(f_core, "_SearchBrowser: Invalid version of Firefox !");
			
			return false;
		}

		f_core.Assert(false, "f_core._SearchBrowser: Unknown browser '"+agt+"'.");
		return false;
	},
	/**
	 * @method static hidden
	 * @pararm Object target
	 * @return void
	 */
	CopyCoreFields: function(target) {
		target._browser=f_core._browser;
		target._browser_major=f_core._browser_major;
		target._browser_release=f_core._browser_release;
		target._browser_minor=f_core._browser_minor;		
	},
	/**
	 * @method hidden static
	 * @param Event evt Javascript event
	 * @return boolean
	 * @context event:evt
	 */
	CancelJsEventHandler: function(evt) {
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
	
		if (f_event.GetEventLocked(evt, false)) {
			return false;
		}
	
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method hidden static 
	 * @param Event evt Javascript event
	 * @return boolean
	 * @context event:evt
	 */
	CancelJsEventHandlerTrue: function(evt) {
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (f_event.GetEventLocked(evt, false)) {
			return false;
		}
			
		f_core.CancelJsEvent(evt);
		
		return true;
	},
	/**
	 * @method hidden static
	 * @param Event evt
	 * @return boolean
	 * @context event:evt
	 */
	CancelJsEvent: function(evt) {
		if (!evt) {
			evt=window.event;

			// Lorsque l'évenement est "USER" il n'y a pas d'evt !
			// f_core.Assert(evt, "f_core.CancelJsEvent: Event is not known ?");
			if (!evt) {
				// On peut rien faire, sinon de retourner false
				return false;		
			}
		}		

		evt.cancelBubble = true;

		if (evt.preventDefault) {
			evt.preventDefault();

		} else {
			evt.returnValue = false;	
		}
		
		return false;
	},
	/**
	 * Returns the size of the View.
	 *
	 * @method public static 
	 * @param optional Object values Object which will contain the result or <code>null</code>.
	 * @param optional HTMLDocument doc
	 * @return Object Object which defines 2 fields: width and height 
	 */
	GetViewSize: function(values, doc) {
		f_core.Assert(values===undefined || values===null || typeof(values)=="object", "f_core.GetViewSize: Invalid values parameter '"+values+"'.");

		if (!values) {
			values=new Object;
		}
		
		var win;
		if (!doc) {
			doc=document;
			win=window;
			
		} else {
			win=f_core.GetWindow(doc);
		}
		
		if (f_core.IsInternetExplorer()) {
			var docElement=doc.documentElement;
			
			if (docElement && docElement.clientWidth) {
				values.width=docElement.clientWidth; 
				values.height=docElement.clientHeight; 
			
				return values;
			}
			
		} else if (f_core.IsGecko()) {
			// Firefox !

			var docElement=doc.documentElement;
			var body=doc.body;
			
			if (docElement.clientWidth<docElement.scrollWidth) {
				values.width=docElement.clientWidth;
		
			} else if (body.clientWidth<body.scrollWidth) {
				// On retire la taille de la scrollbar
				values.width=body.clientWidth;
			
			} else if (win.scrollMaxY) {
				values.width=body.clientWidth;
					
			} else {
				values.width=win.innerWidth;
			}
		
			if (docElement.clientHeight<docElement.scrollHeight) {
				values.height=docElement.clientHeight;
		
			} else if (body.clientHeight<body.scrollHeight) {
				// On retire la taille de la scrollbar
				values.height=body.clientHeight;
			
			} else if (win.scrollMaxX) {
				values.height=body.clientHeight;
				
			} else {
				values.height=win.innerHeight;
			}
			
//			values.width-=f_core.ComputeBorderLength(body, "left")+f_core.ComputeBorderLength(body, "right");
//			values.height-=f_core.ComputeBorderLength(body, "top")+f_core.ComputeBorderLength(body, "bottom");
			
			return values;
		}
					
		var body=doc.body;
		values.width=body.clientWidth;
		values.height=body.clientHeight;
		
		return values;
	},
	/**
	 * @method private static
	 * @param HTMLElement component
	 * @param String... side
	 * @return number
	 */
	ComputeBorderLength: function(component, side) {
		var length=0;

		var ie=f_core.IsInternetExplorer();		
		
		var width=(ie)?"Width":"-width";
		
		for(var i=1;i<arguments.length;i++) {
			side=arguments[i];

			if (ie) {
				side=side.substring(0, 1).toUpperCase()+side.substring(1);
				
			} else {
				side="-"+side;
			}
			
			var margin=f_core.GetCurrentStyleProperty(component, "margin"+side);
			if (margin && margin.indexOf("px")>0) {
				length+=parseInt(margin);
			}
		
			var padding=f_core.GetCurrentStyleProperty(component, "padding"+side);
			if (padding && padding.indexOf("px")>0) {
				length+=parseInt(padding);
			}
		
			var border=f_core.GetCurrentStyleProperty(component, "border"+side+width);
			if (border && border.indexOf("px")>0) {
				length+=parseInt(border);
			}
		}		
		return length;
	},
	/**
	 * @method hidden static
 	 * @param optional Object values
	 * @param optional Document doc
	 * @return Object  which defines 2 fields: x and y 
	 */
	GetScrollOffsets: function(values, doc) {
		f_core.Assert(values===undefined || values===null || typeof(values)=="object", "f_core.GetScrollOffsets: Invalid values parameter '"+values+"'.");
		
		if (!values) {
			values=new Object;
		}
		
		var win;
		if (!doc) {
			doc=document;
			win=window;
			
		} else {
			win=f_core.GetWindow(doc);
		}
					
		if (typeof(win.pageYOffset)=="number") {
			// Netscape !
			values.x=win.pageXOffset;
			values.y=win.pageYOffset;
			
			return values;
		}

		var docElement=doc.documentElement;
		if (docElement && (typeof(doc.compatMode) == "string") &&
               (doc.compatMode.indexOf("CSS") >= 0) &&
               (typeof(docElement.scrollTop) == 'number')) {

			values.x=docElement.scrollLeft; 
			values.y=docElement.scrollTop;
			return values;
        }

		var body=doc.body;
		if (body && (typeof(body.scrollTop) == "number")) {
			values.x=body.scrollLeft; 
			values.y=body.scrollTop; 
			return values;
 		}
      
		return null;
	},
	/**
	 * Returns the size of the document.
	 *
	 * @method public static 
	 * @param optional Object values
	 * @param optional Document doc
	 * @return Object Object which defines 2 fields: width and height 
	 */
	GetDocumentSize: function(values, doc) {
		f_core.Assert(values===undefined || values===null || typeof(values)=="object", "f_core.GetDocumentSize: Invalid values parameter '"+values+"'.");

		if (!values) {
			values=new Object;
		}

		if (!doc) {
			doc=document;
		}
		
		var docElement=doc.documentElement;
		if (docElement && docElement.clientWidth) {
			// Firefox et IE en mode docType strict

			values.width=docElement.scrollWidth;
			values.height=docElement.scrollHeight;
			
			return values;
		}
		
		// IE et le reste ...		
		var body=doc.body;
		values.width=body.scrollWidth;
		values.height=body.scrollHeight;
		
		return values;
	},
	/**
	 * Copy the styleSheets from a document source to a document destination.
	 *
	 * @method hidden static 
	 * @param Document targetDocument
	 * @param Document sourceDocument
	 * @param optional number startIndex Index of the first styleSheets
	 * @param optional number length
	 * @return void
	 */
	CopyStyleSheets: function(targetDocument, sourceDocument, startIndex, length) {
		f_core.Assert(targetDocument && targetDocument.nodeType==f_core.DOCUMENT_NODE, "f_core.CopyStyleSheets: Invalid targetDocument parameter '"+targetDocument+"'.");
		f_core.Assert(sourceDocument && sourceDocument.nodeType==f_core.DOCUMENT_NODE, "f_core.CopyStyleSheets: Invalid sourceDocument parameter '"+sourceDocument+"'.");
		f_core.Assert(startIndex===undefined || typeof(startIndex)=="number", "f_core.CopyStyleSheets: Invalid startIndex parameter '"+startIndex+"'.");
		f_core.Assert(length===undefined || typeof(length)=="number", "f_core.CopyStyleSheets: Invalid length parameter '"+length+"'.");

		var links=sourceDocument.styleSheets;

		if (startIndex===undefined) {
			startIndex=0;
		}
		if (length===undefined) {
			length=links.length;
		}

		if (startIndex>=length) {
			return;
		}

		if (f_core.IsInternetExplorer()) {
			for(var i=startIndex;i<length;i++) {
				var link=links[i];
				
				var href=link.href;
				if (href) {
					targetDocument.createStyleSheet(href);
					continue;
				}
				
				var cssText=link.cssText;
				if (cssText) {
					// C'est du texte
					targetDocument.createStyleSheet().cssText=cssText;
					continue;	
				}
			}	
			
			return;
		}
		
		var head=f_core.GetFirstElementByTagName(targetDocument, "head");
		if (!head) {
			head=targetDocument.createElement("head");
			targetDocument.appendChild(head);
		}
		
		if (f_core.IsGecko()) {
			for(var i=startIndex;i<length;i++) {
				var link=links[i];
	
				var newLink=targetDocument.createElement("link");
				newLink.rel="stylesheet";
				newLink.type="text/css";
	
				var content=link.ownerNode.textContent;
				if (link.href && !content) {
					// C'est un lien
					newLink.href=link.href;
					
					head.appendChild(newLink);
					continue;
				}
	
				newLink.textContent=content;
				
				head.appendChild(newLink);
			}
			
			return;
		}
	},
	/*
	 * Returns the position of the Window.
	 *
	 * @method public static 
	 * @param optional HTMLDocument doc
	 * @return Object Object which defines 2 fields: x and y 
	 */
	GetViewPosition: function(doc) {
		if (!doc) {
			doc=document;
		}
		  
		var window=f_core.GetWindow(doc);

		if (f_core.IsInternetExplorer()) {
			return {
				x: window.screenLeft,
				y: window.screenTop
			};
		}
				 
		return { 
			x: window.screenX,
			y: window.screenY
		};
	},
	/**
	 * Returns the position of event
	 *
	 * @method hidden static 
	 * @param Event event
	 * @param optional HTMLDocument doc
	 * @return number[]
	 */
	GetJsEventPosition: function(event, doc) {
		f_core.Assert(event && event.type, "f_core.GetJsEventPosition: Invalid event parameter '"+event+"'.");
	
		if (!doc) {
			var target=event.relatedTarget;
			if (!target) {
				target=event.scrElement;
			}
			if (target) {
				if (target.nodeType==f_core.DOCUMENT_NODE) {
					doc=target;
					
				} else if (target.nodeType==f_core.ELEMENT_NODE) {
					doc=target.ownerDocument;
	//				alert("Get doc: "+doc);
				}
			}
			
			if (!doc) {
				doc=document;
			}
		} 
 		if (f_core.IsInternetExplorer()) {
			return { 
				x: event.clientX + doc.documentElement.scrollLeft + doc.body.scrollLeft,
			 	y: event.clientY + doc.documentElement.scrollTop + doc.body.scrollTop
			};
		}
 	 
 		var window=f_core.GetWindow(doc);
		return { 
		    x: window.scrollX + event.clientX,
			y: window.scrollY + event.clientY
		};
	},
	/**
	 * Returns if the event has been performed into a component.
	 *
	 * @method hidden static 
	 * @param HTMLElement component
	 * @param f_event event
	 * @return boolean
	 */
	IsComponentInside: function(component, event) {			
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.IsComponentInside: Invalid component parameter '"+component+"'.");
		f_core.Assert(event && event.type, "f_core.IsComponentInside: Invalid event parameter '"+event+"'.");
	
		var p=f_core.GetAbsolutePosition(component);
	
		if (event.clientX<p.x || 
			event.clientY<p.y || 
			event.clientX>p.x+component.offsetWidth || 
			event.clientY>p.y+component.offsetHeight) {
			
			return false;
		}
		
		return true;
	},
	/**
	 * @method hidden static 
	 */
	VerifyProperties: function(object) {
		if (!f_core.DebugMode) {
			return;
		}
		
		var s="";
		
		for (var p in object) {
/*			if (p.length<2) {
				continue;
			}
			*/
			if (p.indexOf("f_")!=0 && p.indexOf("_")!=0 && p.indexOf("on")!=0) {
				continue;
			}
			// on ne garde que  f_*  _*  on*
			if (p=="_kclassName") {
				continue;
			}
			
			var value=object[p];
			
			var typeOfValue=typeof(value);
			
			if (value===null || 
				value===undefined || 
				typeOfValue=="number" || 
				typeOfValue=="string" || 
				typeOfValue=="boolean") {
				continue;
			}
			
			if (value instanceof RegExp) {
				continue;

			} else if (value instanceof Date) {
				continue;

			} else if (value instanceof f_messageObject) {
				continue;

			} else if (f_class.IsClassDefined("f_time") && (value instanceof f_time)) {
				continue;

			} else if (value instanceof Array) {
				var ok=true;
				for(var i=0;i<value.length;i++) {
					var v=value[i];
					var vt=typeof(v);
					
					if (v===null || 
						v===undefined || 
						vt=="number" || 
						vt=="string" || 
						vt=="boolean") {
						continue;
					}
					ok=false;
					break;
				}
				
				if (ok) {
					continue;
				}
			} else if (typeof(value)=="object" && !(value.nodeType)) {
				var ok=true;
				for(var i in value) {
					var v=value[i];
					var vt=typeof(v);
					
					if (v===null || 
						v===undefined || 
						vt=="number" || 
						vt=="string" || 
						vt=="boolean") {
						continue;
					}
					ok=false;
					break;
				}
				
				if (ok) {
					continue;
				}
			}

			if (s.length>0) {
				s+=",";
			}
			
			s+=p;
			if (typeof(value)=="function") {
				s+="[*function*]";
				continue;

			} else if (value.nodeType==f_core.ELEMENT_NODE) {
				s+="[Tag "+value.tagName+"."+value.className+"#"+value.id+"]";
				continue;
					
			} else if (value.nodeType==f_core.DOCUMENT_NODE) {
				s+="[Document "+value.location.toString()+"*]";
				continue;
			}
			
			s+="["+value+":"+typeOfValue+"]";
		}
		
		if (s.length) {
			if (object.nodeType==f_core.ELEMENT_NODE) {
				s="[Tag "+object.tagName+"."+object.className+"#"+object.id+"]\n"+s;
					
			} else if (object.nodeType==f_core.DOCUMENT_NODE) {
				s+="[Document "+object.location.toString()+"]\n"+s;
			}
			if (object._kclassName) {
				s="_KClassName: "+object._kclassName+"\n"+s;
			}
			
			s="Not garbaged properties : "+s;
			
			var ex=new Error(s);
			f_core.Error(f_core, s, ex);
			
			throw ex;
		}
	},
	/**
	 * @method public static 
	 * @param HTMLElement component
	 * @param boolean asyncMode
	 * @return boolean <code>true</code> is success !
	 */
	SetFocus: function(component, asyncMode) {
		f_core.Assert(component, "f_core.SetFocus: Component is NULL");
		f_core.Assert(component.nodeType==f_core.ELEMENT_NODE, "f_core.SetFocus: Parameter is not a component.");

		f_core.Debug(f_core, "SetFocus: component="+component.id+" asyncMode="+asyncMode);

		if (f_core._FocusTimeoutID) {
			f_core._FocusComponent=component;
			return true;
		}
		
		if (asyncMode) {
			f_core._FocusComponent=component;
			
			f_core._FocusTimeoutID=window.setTimeout(function() {
				// On sait jamais !
				if (window._rcfacesExiting) {
					return;
				}
				f_core._FocusTimeoutID=undefined;
				
				var component=f_core._FocusComponent;
				if (!component) {
					return;
				}
				f_core._FocusComponent=undefined;
				f_core.SetFocus(component, false);
				
			}, f_core._FOCUS_TIMEOUT_DELAY);
			return true;
		}

		if (typeof(component.f_show)=="function") {
			try {
				if (!component.f_show()) {
					f_core.Info(f_core, "SetFocus: Can not set focus to a not visible component");
					return false;
				}
				
			} catch (ex) {
				f_core.Error(f_core, "SetFocus: Exception while calling f_show() of '"+component.id+"' [camelia method].", ex);
				
				return false;
			}
		}

		if (typeof(component.f_setFocus)=="function") {
			f_core.Debug(f_core, "SetFocus: Try to call f_setFocus() method to set the focus. (componentId="+component.id+"/tagName="+component.tagName+")");
			try {
				component.f_setFocus();
				return true;
				
			} catch (ex) {
				f_core.Error(f_core, "SetFocus: Exception while setting focus of '"+component.id+"' [camelia method].", ex);
			}
			
			return false;
		}
		
		if (f_core.IsInternetExplorer()) {
//			component.setActive();
		}

		f_core.Debug(f_core, "SetFocus: Try to call focus() method to set the focus. (componentId="+component.id+"/tagName="+component.tagName+")");

		try {
			component.focus();
			return true;
			
		} catch (ex) {
			if (f_core.IsGecko()) {
				// Le moteur GECKO peut generer une exception dans certains cas
				return true;
			}

			f_core.Error(f_core, "SetFocus: Exception while setting focus of '"+component.id+"'.", ex);
		}
		
		return false;
	},
	/**
	 * @method hidden static
	 */
	GetFirstElementByTagName: function(parent, tagName, assertIfNotFound) {
		f_core.Assert(parent && parent.nodeType, "f_core.GetFirstElementByTagName: Parent '"+parent+"' is not a Dom node !");
		
		var components=parent.getElementsByTagName(tagName);
		if (!components || !components.length) {
			f_core.Assert(!assertIfNotFound, "f_core.GetFirstElementByTagName: Component '"+tagName+"' not found !");
			return null;
		}
		
		return components[0];
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @return String
	 */	
	GetDefaultDisplayMode: function(component) {
		var tagName=component.tagName;
		if (!tagName) {
			return null;
		}
		if (f_core._BLOCK_TAGS.test(tagName)) {
			return "block";
		}
		return "inline";
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @param Function listener
	 * @return boolean
	 */
	AddResizeEventListener: function(component, listener) {
		if (f_core.IsInternetExplorer()) {
			component.onresize=listener;
			return true;
		}

		if (f_core.IsGecko()) {
			listener._mainResizeCallback=function() {
				return listener.call(component);
			}
		
			window.addEventListener("resize", listener._mainResizeCallback, false);
			
			return true;
		}
		
		return false;
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @param Function listener
	 * @return void
	 */
	RemoveResizeEventListener: function(component, listener) {
		if (f_core.IsInternetExplorer()) {
			component.onresize=null;
			return;
		}

		if (f_core.IsGecko()) {
			window.removeEventListener("resize", listener._mainResizeCallback, false);
			
			listener._mainResizeCallback=undefined;
			return true;
		}
		
		return;
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component
	 * @param String attributeId
	 * @return String
	 */
	GetCurrentStyleProperty: function(component, attributeId) {
		if (f_core.IsInternetExplorer()) {
			return component.currentStyle[attributeId];
		}
		
		if (f_core.IsGecko()) {	
			return component.ownerDocument.defaultView.getComputedStyle(component, '').getPropertyValue(attributeId);
		}
		
		f_core.Assert(false, "f_core.GetCurrentStyleProperty: Browser not supported !");
		return null;
	},
	/**
	 * @method hidden static 
	 * @param Object p
	 * @param optional String sep
	 * @param hidden Array d
	 * @return String
	 */
	EncodeObject: function(p, sep) {
		if (!sep) {
			sep="&";
		}
		
		var d = new Array;
		
		function f(d, v) {
			if (v===null || v===undefined) {
				d.push("L");
				return;
			}

			if (v===true) {
				d.push("T");
				return;
			}			

			if (v===false) {
				d.push("F");
				return;
			}
			
			if (v==="") {
				// Vide !
				return;
			}
			
			if (v instanceof Array) {
				var l=v.length;
				
				if (!l) {
					d.push("[0]");
					return;
				}
				
				d.push("[", String(l), ":");

				for(var i=0;i<l;i++) {
					if (i) {
						d.push(',');
					}
						
					arguments.callee(d, v[i]);
				}
				
				d.push("]");

				return;
			}
			
			if (typeof(v)=="number") {
				if (!v) {
					d.push("0");
					return;
				}
				
//				d+="N";
				
				var fixed=v.toFixed();
				if (fixed==v) {
					v=fixed;
				}
				
				if (v<0.0) {
					d.push("-");
					v=-v;
				}
				
			} else if (typeof(v)=="string") {
				d.push("S");
				
			} else if (v instanceof Date) {
				d.push("D");
				v=f_core.SerializeDate(v);
				
			} else if (v instanceof Document) {
				d.push("X");
				v=f_xml.Serialize(v);
				
			} else if (f_class.IsClassDefined("f_time") && (v instanceof f_time)) {
				d.push("M");
				v=f_time.Serialize(v);
				
			} else if (f_class.IsClassDefined("f_period") && (v instanceof f_period)) {
				d.push("P");
				v=f_period.Serialize(v);

			} else {
				f_core.Error(f_core, "EncodeObject: Can not serialize '"+v+"'.");
				return;
			}
			
			d.push(encodeURIComponent(v));
		}
		
		for (var i in p) {
			if (d.length) { 
				d.push(sep);
			}
			
			d.push(i, "=");
			
			f(d, p[i]);
		}
	
		return d.join('');
	},
	/**
	 * @method hidden static
	 */
	DecodeObject: function(string, emptyReturnsNull, sep) {
		var obj=new Object;
		if (!string) {
			if (emptyReturnsNull) {
				return null;
			}
			return obj;
		}

		if (!sep) {
			sep="&";
		}
				
		var ss=string.split(sep);
		for(var i=0;i<ss.length;i++) {
			var s=ss[i];
			var idx=s.indexOf('=');
			f_core.Assert(idx>0, "f_core.DecodeObject: Bad format ! '"+s+"'.");
			
			var name=s.substring(0, idx);
			name=name.replace(/\+/g, ' ');
			name=decodeURIComponent(name);

			idx++; // le =
			var type=s.charAt(idx++); // le type
			
			var data=s.substring(idx);
			switch(type) {
			case 'S':
				if (data.length) {
					data=decodeURIComponent(data.replace(/\+/g, ' '));
				}
				break;

			case 'D':
				data=f_core.DeserializeDate(data);
				break;

			case 'M':
				f_class.IsClassDefined("f_time", "f_time class is required to deserialize a time object !");
				
				data=f_time.Deserialize(data);
				break;

			case 'P':
				f_class.IsClassDefined("f_period", "f_period class is required to deserialize a period object !");
				
				data=f_period.Deserialize(data);
				break;

			case 'X':
				f_class.IsClassDefined("f_xml", "Xml class is required to deserialize a xml document !");

				data=data.replace(/\+/g, ' ');
				data=decodeURIComponent(data);
				data=f_xml.FromString(data);
				break;

			case 'L':
				data=null;
				break;

			case 'T':
				data=true;
				break;

			case 'F':
				data=false;
				break;
				
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (!data) {
					data=parseFloat(type);
					break;
				}
			
			case '-':
				data=-parseFloat(data);
				break;

				
			default:
				f_core.Error(f_core, "DecodeObject: Unknown type '"+type+"' !");
				data=undefined;
			}
			
			f_core.Debug(f_core, "DecodeObject: Deserialize attribute '"+name+"' = '"+data+"'");
			obj[name]=data;
		}

		return obj;
	},
	/**
	 * @method private hidden static
	 * @param Date date
	 * @return String
	 */
	SerializeDate: function(date) {
		var year=date.getFullYear();
		var month=date.getMonth();
		var day=date.getDate()-1;
		var hours=date.getHours();
		var minutes=date.getMinutes();
		var seconds=date.getSeconds();
		var millis=date.getMilliseconds();
		
		if (!millis) {
			if (!seconds) {
				if (!minutes) {
					if (!hours) {
						if (!day) {
							if (!month) {
								return "Y"+year.toString(32);
							}							
							month+=year*12;							
							return "M"+month.toString(32);
						}
						day+=(year*12+month)*31;
						return "d"+day.toString(32);
					}
					hours+=((year*12+month)*31+day)*24;
					return "H"+hours.toString(32);
				}
				minutes+=(((year*12+month)*31+day)*24+hours)*60;
				return "m"+minutes.toString(32);
			}
			seconds+=((((year*12+month)*31+day)*24+hours)*60+minutes)*60;
			return "s"+seconds.toString(32);
		}
		
		millis+=(((((year*12+month)*31+day)*24+hours)*60+minutes)*60+seconds)*1000;
		return "S"+millis.toString(32);
	},

	/**
	 * @method hidden static
	 * @param String date
	 * @return Date
	 */
	DeserializeDate: function(date) {
		if (!date.length) {
			return null;
		}
		
		var unit=date.charAt(0);
		var value=parseInt(date.substring(1), 32);

		switch(unit) {
		case 'Y':		
			return new Date(value, 0, 1);

		case 'M':
			var m=value % 12;
			var y=Math.floor(value/12);
			return new Date(y, m, 1);
		
		case 'd':
			var d=value;
			var m=Math.floor(value/31);
			var y=Math.floor(m/12);
			return new Date(y, m % 12, (d % 31)+1);
	
		case 'H':
			var h=value;
			var d=Math.floor(h/24);
			var m=Math.floor(d/31);
			var y=Math.floor(m/12);
			return new Date(y, m % 12, (d % 31)+1, h % 24, 0, 0);
		
		case 'm':
			var mn=value;
			var h=Math.floor(mn/60);
			var d=Math.floor(h/24);
			var m=Math.floor(d/31);
			var y=Math.floor(m/12);
			return new Date(y, m % 12, (d % 31)+1, h % 24, mn % 60, 0);
		
		case 's':			
			var s=value;
			var mn=Math.floor(s/60);
			var h=Math.floor(mn/60);
			var d=Math.floor(h/24);
			var m=Math.floor(d/31);
			var y=Math.floor(m/12);
			
			return new Date(y, m % 12, (d % 31)+1, h % 24, mn % 60, s % 60);

		case 'S':			
			var ms=value;
			var s=Math.floor(ms/1000);
			var mn=Math.floor(s/60);
			var h=Math.floor(mn/60);
			var d=Math.floor(h/24);
			var m=Math.floor(d/31);
			var y=Math.floor(m/12);
			
			return new Date(y, m % 12, (d % 31)+1, h % 24, mn % 60, s % 60, ms % 1000);
		}
		
		f_core.Error(f_core, "DeserializeDate: Invalid date format ! ("+date+")");
		return null;
	},
	/**
	 * @method private static
	 */
	_IeBlockSelectStart: function(evt) {
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * Returns the parent of a HTML element.
	 *
	 * @method hidden static
	 * @param HTMLElement A HTML element
	 * @return HTMLElement Parent of the element  (or <code>null</code> if no parent)
	 */
	GetParentNode: function(component) {
		if (!component) {
			return null;
		}
		
		var parent=component.parentNode;
		if (!parent) {
			return null;
		}
		
		switch(parent.nodeType) {
		case f_core.ELEMENT_NODE:
		case f_core.DOCUMENT_NODE:
			return parent;
		}
		
		return null;
	},
	/**
	 * Returns an Event attached to the window of the component. (if known)
	 * 
	 * @method hidden static
	 * @param HTMLElement component
	 * @return Event Returns event if known.
	 */
	GetJsEvent: function(component) {
		if (!f_core.IsInternetExplorer()) {
			f_core.Debug(f_core, "GetJsEvent: Can not get event from component ! (no IE browser)");
			return null;
		}
		
		if (component.nodeType==f_core.DOCUMENT_NODE) {
			// component=document
			return component.parentWindow.event;
		}
		
		if (component.ownerDocument) {
			return component.ownerDocument.parentWindow.event;
		}
		
		// Component est une window ?
		return component.event;
	},
	/**
	 * @method private static
	 * @context event:evt
	 */
	_IeOnSelectStop: function(evt) {
		// TODO Il faut résoudre le WINDOW
				//document.title="STOP bookmark ! "+window._acceptedSelection;
		//window._acceptedSelection=undefined;
		f_core.RemoveEventListener(document.body, "losecapture", f_core._IeOnSelectStop);
		f_core.RemoveEventListener(document.body, "mouseover", f_core._IeOnSelectOver);
		f_core.RemoveEventListener(document.body, "mouseout", f_core._IeOnSelectOver);
	},
	/**
	 * @method private static
	 * @context event:evt
	 */
	_IeOnSelectOver: function(evt) {
		// TODO Il faut résoudre le WINDOW

		evt = f_core.GetJsEvent(this);
		var component=evt.srcElement;
		
		var selection=document.selection;
		var textRanges=selection.createRangeCollection();
		if (textRanges.length<1) {
			return true;
		}
		var textRange=textRanges[0];
		
		for(;component && component.nodeType==f_core.ELEMENT_NODE;component=component.parentNode) {
			var style=component.currentStyle;
			if (!style) {
				continue;
			}
			
			if (style["user-select"]) {
				var oldBookmark=window._acceptedSelection;
				if (oldBookmark) {
					if (textRange.moveToBookmark(oldBookmark)) {
					
						textRange.select();
						return false;
					}
					
					
					// Sinon on efface tout !
					window._acceptedSelection=undefined;
				}
				//document.title="No bookmark ! "+window._acceptedSelection;

					
				selection.empty();
				return false;
			}
		}
		
		window._acceptedSelection=textRange.getBookmark();
		document.title="Set bookmark ! "+window._acceptedSelection;	
		return true;
	},
	/**
	 * @method private static
	 * @context event:evt
	 */
	_IeOnSelectStart: function(evt) {

		if (true) {
			return;
		}

		f_core.AddEventListener(document.body, "losecapture", f_core._IeOnSelectStop);
		f_core.AddEventListener(document.body, "mouseover", f_core._IeOnSelectOver);
		f_core.AddEventListener(document.body, "mouseout", f_core._IeOnSelectOver);
		
		f_core._IeOnSelectOver();
		
		return true;
	},
	/**
	 * @method public static 
	 * @param String cookieName
	 * @param optional HTMLDocument doc Html document.
	 * @return String value associated to the cookie, or <code>null</code>.
	 */
	GetCookieValue: function(cookieName, doc) {
		f_core.Assert(typeof(cookieName)=="string", "f_core.GetCookieValue: Bad cookieName ! ("+cookieName+")");
		
		if (!doc) {
			doc=document;
		}
		
		var cookies;
		try {
			cookies=doc.cookie;
			
		} catch (x) {
			f_core.Error(f_core, "GetCookieValue: Can not list cookies of document.", x);
		
			return null;			
		}
		
		if (!cookies) {
			return null;
		}
		
        var start = cookies.indexOf("; "+cookieName+"=");
		if (start<0) {
			start = cookies.indexOf(cookieName+"=");
			// C'est forcement le premier !
			if (start!=0) {
				return null;
			}
		}
		
		// On regarde derriere !
		// Deuxieme verification, non obligatoire !
		if (start>0 && cookies.charAt(start-1)>64) {
			// On a trouvé un sous-ensemble !
			return null;
		}
		
        start = cookies.indexOf("=", start) + 1;
		var end = cookies.indexOf(";", start);
		if (end<0) {
			end=cookies.length;
		}
		
		var value=cookies.substring(start, end);
        return unescape(value);
	},
	/**
	 * @method public static 
	 * @param String cookieName
	 * @param optional String cookieValue Value to associate with cookie, or <code>null</code> to delete cookie !
	 * @param optional HTMLDocument doc Html document
	 * @return boolean Returns <code>true</code> if success.
	 */
	SetCookieValue: function(cookieName, cookieValue, doc) {
		f_core.Assert(typeof(cookieName)=="string", "f_core.SetCookieValue: Bad cookieName ! ("+cookieName+")");

		if (!doc) {
			doc=document;
		}

		try {
			if (!cookieValue || !cookieValue.length) {
				doc.cookie=cookieName+"=; expires=Thu, 01-Jan-70 00:00:01 GMT";
				return true;
			}
			
			doc.cookie=cookieName+"="+escape(cookieValue);
			return true;
			
		} catch (x) {
			f_core.Error(f_core, "SetCookieValue: Can not set cookie '"+cookieName+"', value='"+cookieValue+"'.", x);
	
			if (f_core.DebugMode) {
				throw x;
			}
			
			return false;
		}
	},
	/** 
	 * @method hidden static
	 */
	IsPopupButton: function(evt) {
		return f_core.GetEvtButton(evt)==f_core._POPUP_BUTTON;
	},
	/** 
	 * @method hidden static
	 */
	IsAppendMode: function(evt) {
		return evt.ctrlKey;
	},
	/** 
	 * @method hidden static
	 */
	IsAppendRangeMode: function(evt) {
		return evt.shiftKey;
	},
	/** 
	 * @method hidden static
	 */
	GetEvtButton: function(evt) {
		if (evt.button!==undefined) {
			return evt.button;
		}
		if (evt.which!==undefined) {
			return evt.which;
		}
		
		return 0;
	},
	/**
	 * Returns an effect specified by its name.
	 *
	 * @method hidden static 
	 * @param String effectName Name of effect
	 * @param HTMLElement body Component which be applied the effect.
	 * @param optional boolean reverse Inverse of the effect
	 * @return f_effect An f_effect object. 
	 */
	CreateEffectByName: function(effectName, body, callback) {
		f_core.Assert(typeof(effectName)=="string", "f_core.CreateEffectByName: The name of the effect is not a string !");
		f_core.Assert(body && body.nodeType!==undefined, "f_core.CreateEffectByName: Body parameter is not a HTMLElement");
	
		var effectClass=f_classLoader.Get(window).f_getClass("f_effect");
		if (!effectClass) {
			f_core.Error(f_core, "CreateEffectByName: Effect class has not been loaded. (name="+effectName+")");
			return null;
		}
		
		var effect=effectClass.Create(effectName, body, callback);
		if (!effect) {
			return null;
		}
		
		return effect;
	},
	/**
	 * Returns selection of a TextEntry or a TextArea.
	 *
	 * @method hidden static 
	 * @param HTMLElement component
	 * @return number[] Can return <code>null</ocde> if the current selected component is not the same as the parameter component.
	 */
	GetTextSelection: function(component) {
		f_core.Assert(component && component.tagName, "f_core.GetTextSelection: Invalid component !");

		if (f_core.IsInternetExplorer()) {
			var caret = component.ownerDocument.selection.createRange();
			if (caret.parentElement()!=component) {
				// Le composant actuellement sélectionné, n'est pas notre composant !
				return null;
			}

			caret=caret.duplicate();

			var isCollapsed = (caret.compareEndPoints("StartToEnd", caret) == 0);
			var bookmark;
			if (!isCollapsed) {
				bookmark=caret.getBookmark();
			}
			
			var value=component.value;
			
			var i=0;
			for(;caret.parentElement() == component;) {
				if (caret.move("character", -1)!=-1) {
					break;
				}
				
				i++;
			}
			
			f_core.Debug(f_core, "GetTextSelection: Caret position: "+i+" collapse="+isCollapsed);
			
			if (isCollapsed) {
				delete caret;
				return [ i, i ];
			}
			
			caret.moveToBookmark(bookmark);
			delete bookmark;
			
			var j=0;
			for(;caret.parentElement() == component;) {
				if (caret.moveStart("character", 1)!=1) {
					break;
				}
				
				j++;

				if (caret.compareEndPoints("StartToEnd", caret) >= 0) {
					break;
				}
			}
			
			f_core.Debug(f_core, "GetTextSelection: Caret position: "+i+" to "+(i+j)+".");
			
			delete caret;
			return [ i, i+j ];
		}
						
		if (f_core.IsGecko()) {
			f_core.Debug(f_core, "GetTextSelection: Caret position: "+component.selectionStart+" to "+component.selectionEnd+".");

			return [ component.selectionStart, component.selectionEnd ];
		}
		
		f_core.Error(f_core, "GetTextSelection: Unsupported browser for GetTextSelection() !");
		return null;
	},
	/**
	 * Select a text into a TextEntry or a TextArea
	 *
	 * @method hidden static 
	 * @param HTMLElement component
	 * @param number start
	 * @param optional number end
	 * @return void
	 */
	SelectText: function(component, start, end) {		
		f_core.Assert(component && component.tagName, "f_core.SelectText: Invalid component !");

		if ((start instanceof Array) && start.length==2) {
			f_core.Assert(start.length==2, "f_core.SelectText: Invalid start parameter [length of array!=2] ("+start+")");

			end=start[1];
			start=start[0];
			
		} else if (end===undefined) {
			end=start;
		}
		
		f_core.Assert(typeof(start)=="number" && start>=0, "f_core.SelectText: Invalid start parameter '"+start+"'.");
		f_core.Assert(typeof(end)=="number" && end>=start, "f_core.SelectText: Invalid end parameter '"+end+"'. (start="+start+")");
	
		f_core.Debug(f_core, "SelectText: select text from '"+start+"' to '"+end+"'.");
	
		if (f_core.IsInternetExplorer()) {
			var tr=component.createTextRange();
			tr.moveStart("character", start, end-start);
			tr.select();
			return;
		}
		
		if (f_core.IsGecko()) {
			component.setSelectionRange(start, end);
			
			return;
		}
		
		f_core.Error(f_core, "SelectText: Unsupported browser for SelectText() !");
	},
	/**
	 * List all components of a document.
	 *
	 * @method hidden static 
	 * @param Document doc
	 * @return HTMLElement[] A list of HTMLElements
	 */
	ListAllHtmlComponents: function(doc) {
		f_core.Assert(doc && doc.nodeType==f_core.DOCUMENT_NODE, "f_core.ListAllHtmlComponents: Doc parameter must be a document object.");
	
		// On peut toujours essayer ;-)
		var elts=doc.all;
		if (elts!==undefined) {
			return elts;
		}

		// Check view elements
		elts=new Array;

		var e=[ doc.documentElement ];
		
		for(;e.length;) {
			var p=e.pop();
		
			if (p.nodeType==f_core.ELEMENT_NODE) {
				elts.push(p);
			}
			
			var nextSibling=p.nextSibling;
			if (nextSibling) {
				e.push(nextSibling);
			}
			
			var firstChild=p.firstChild;
			if (firstChild) {
				e.push(firstChild);
			}
		}
		
		return elts;
	},
	
	/**
	 * Recherche du prochain élément vers lequel on peut tabuler
	 * depuis l'élément courant. Cette recherche s'effectue sur tous les éléments
	 * du document de type suivant:<br>
	 *		<b>A,AREA,BUTTON,IFRAME,INPUT,OBJECT,SELECT,TEXTAREA</b>
	 * <br>
	 * La tabulation HTML s'effectue dans l'ordre suivant:<br>
	 * <ol>
	 *		<li>Elements ayant un tabIndex > 0 dans l'ordre croissant</li>
	 *		<li>Elements ayant un tabIndex <= 0 dans l'ordre de déclaration
	 *		<ul><li>a) Sauf pour IE où un tabIndex < 0 est non tabulable</li></ul>
	 *		</li>
	 * </ol>
	 * <br>
	 * Par ailleurs les particularités suivantes sont à signaler:<br>
	 * <ul>
	 *		<li>Sous IE, un composant possède par défaut un tabIndex à 0</li>
	 *		<li>Sous NS, un composant tabulable a un tabIndex de valeur -1</li>
	 *		<li>Sous NS, un composant non tabulable a un tabIndex undefined</li>
	 * </ul>
	 * <br>
	 * Deux tableaux sont donc créés pour accueillir les éléments non ordonnés et
	 * ceux qui le sont. La recherche du suivant rejette les éléments qui ont les
	 * caractéristiques suivantes:<br>
	 *		<b>NOT VISIBLE, DISABLED, HIDDEN TYPE, NO FOCUS METHOD</b>
	 *
	 * @method hidden static 
	 * @param HTMLElement component composant précédent dans l'ordre de tabulation
	 * @return HTMLElement composant suivant dans l'ordre de tabulation
	 */
	GetNextFocusableComponent: function(component) {
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.GetNextFocusableComponent: bad parameter type "+component);
		f_core.Debug(f_core, "GetNextFocusableComponent: entering ("+component+") "+component.nodeType);
		
		var focusableElementFunction=component.f_getFocusableElement;
		if (typeof(focusableElementFunction)=="function") {
			component=focusableElementFunction.call(component);
		}
		
		// Check view elements
		var elts = f_core.ListAllHtmlComponents(component.ownerDocument);
		
		var len = elts.length;
		if (!len) {
			f_core.Debug(f_core, "GetNextFocusableComponent: No elements into document !");
			return null;
		}

		function getNextAvailable(tabs, offset) {
			for(offset++;offset<tabs.length;offset++) {
				var elt = tabs[offset];
				var style = elt.style;
				
				if (style) {
					if (style.visibility == "hidden" || style.display == "none") {
						continue;
					}
				}
				if (elt.disabled || elt.type == "hidden" || !elt.focus) {
					continue;
				}
				
				return elt;
			}
			
			return null;
		}

		// Initialize prev and next
		var prev = component.tabIndex;
		var next = null;
		var offset = -1;

		// Build tabulation list
		var itabs = new Array;
		var utabs = new Array;

		var isIE = f_core.IsInternetExplorer();

		var focusableTags=f_core._FOCUSABLE_TAGS;
		// Get thru form elements
		for (var i=0; i<len; i++) {
			var elt = elts[i];
			var tagName=elt.tagName;
			
			if (!tagName) {
				continue;
			}
			
			if (!focusableTags.test(tagName)) {
//				f_core.Debug("f_core", "Refuse element: id="+elt.id + " tagName="+ elt.tagName +" className="+elt.className);
				continue;
			}	
				
//			f_core.Debug("f_core", "Focusable element: id="+elt.id + " tagName="+ elt.tagName +" type="+ elt.type + " className="+elt.className);
			
			var idx = elt.tabIndex;
			// Non tab components
			if (idx === undefined || idx == null || (idx<0 && isIE)) {
				continue;
			}

			// Unordered tab components
			if (idx <= 0) {
				if (elt == component) {
					offset = utabs.length;
				}
				utabs.push(elt);
				continue;
			}
			
			var ts=itabs[idx];
			// Ordered tab components
			// @TODO Ne pas utiliser l'indice du tableau en tant que TAB INDEX
			if (!ts) {
				ts=new Array;
				itabs[idx] = ts;
			}
			ts.push(elt);
		}

		f_core.Debug(f_core, "GetNextFocusableComponent: utabs.length="+utabs.length+" itabs.length="+itabs.length);

		// Get next form unordered
		if (prev == undefined || prev == null || (prev<0 && isIE)) {
		
			f_core.Debug(f_core, "GetNextFocusableComponent: Search next unordered component. (prev="+prev+")");
		
			return getNextAvailable(utabs, -1);
		}
		
		// Get next from unordered starting at offset
		if (prev <= 0) {
			// Get first accessible unordered

			f_core.Debug(f_core, "GetNextFocusableComponent: Get next from unordered starting at offset. (offset="+offset+")");
			return getNextAvailable(utabs, offset);
		}
		
		// Get next from ordered starting at offset or
		// get next from unordered starting at offset -1
		var otabs = new Array;
		for (var i=0; i<32768; i++) {
			var ar = itabs[i];
			if (!ar) {
				continue;
			}
			
			for (var p in ar) {
				var elt = ar[p];
				if (elt == component) {
					offset = otabs.length;
				}
				otabs.push(elt);
			}
		}

		f_core.Debug(f_core, "GetNextFocusableComponent: Get next from ordered starting at offset. (offset="+offset+")");

		next = getNextAvailable(otabs, offset);
		if (next) {
			return next;
		}
	
		f_core.Debug(f_core, "GetNextFocusableComponent: Get next from unordered");
		
		return getNextAvailable(utabs, -1);
	},

	/**
	 *  NE FONCTIONNE pas avec IE
	 * 
	 * @method hidden static
	 */
	ComputePopupPosition: function(popup, positions) {
		var body=popup.ownerDocument.body;
			
		// Ne fonctionne PAS sous IE !
		
		var viewSize=f_core.GetViewSize(null, popup.ownerDocument);
		
		var bw=viewSize.width;
		var bh=viewSize.height;
/*		
		document.title="bw="+body.clientWidth+" bh="+body.clientHeight+" ww="+window.innerWidth+" wh="+window.innerHeight+" sx="+window.scrollX+" sy="+window.scrollY;
	*/	
		var scrollPosition=f_core.GetScrollOffsets(popup.ownerDocument);
		bw+=scrollPosition.x;
		bh+=scrollPosition.y;

		var absPos=f_core.GetAbsolutePosition(popup.offsetParent);

		f_core.Debug(f_core, "ComputePopupPosition: bw="+bw+" bh="+bh+" absPos.x="+absPos.x+" absPos.y="+absPos.y+" positions.x="+positions.x+" positions.y="+positions.y+" popupWidth="+popup.offsetWidth+" popupHeight="+popup.offsetHeight);

		if (popup.offsetWidth+positions.x+absPos.x>bw) {
			positions.x=bw-popup.offsetWidth-absPos.x;

			f_core.Debug(f_core, "ComputePopupPosition: change x position to "+positions.x);
		}
		
		if (popup.offsetHeight+positions.y+absPos.y>bh) {
			positions.y=bh-popup.offsetHeight-absPos.y;

			f_core.Debug(f_core, "ComputePopupPosition: change y position to "+positions.y);
		}
	},
	/**
	 * Compute the popup position. (centred horizontaly and verticaly)
	 *
	 * @method public static
	 * @param Object parameters
	 * @return void Fill fields "x" and "y" into the "parameters" object.
	 */
	ComputeDialogPosition: function(parameters) {
		var x=0;
		var y=0;

		var body=document.body;
		
		if (window.screenX!==undefined) {
		 	x=window.screenX;
		 	y=window.screenY;
		 	
		} else if (window.screenLeft!==undefined) {
			x=window.screenLeft;
			y=window.screenTop;
		}
		
		var width=0;
		var height=0;
		
		if (window.innerWidth) {
		 	width=window.outerWidth;
		 	height=window.outerHeight;
		 
		} else if (document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight )) {
			width = document.documentElement.clientWidth;
	    	height = document.documentElement.clientHeight;	
		
		} else if (body && body.offsetWidth) {
			width = body.clientWidth;
	    	height = body.clientHeight;
		}
		
		var dialogWidth=parameters.width;
		f_core.Assert(typeof(dialogWidth)=="number", "f_core.ComputeDialogPosition: width must be specified into parameters object.");

		var dialogHeight=parameters.height;
		f_core.Assert(typeof(dialogHeight)=="number", "f_core.ComputeDialogPosition: height must be specified into parameters object.");
		
		var posX=Math.floor(x+(width-dialogWidth)/2);
		var posY=Math.floor(y+(height-dialogHeight)/2);
		
		// document.title="posX="+posX+" posY="+posY+" x="+x+" y="+y+" width="+width+" height="+height+" dialogWidth="+dialogWidth+" dialogHeight="+dialogHeight;
		
		if (posX<0) {
			posX=0;
		}
		
		if (posY<0) {
			posY=0;
		}
		
		parameters.x=posX;
		parameters.screenX=posX;

		parameters.y=posY;
		parameters.screenY=posY;
	},
	/**
	 * @method hidden static
	 * @return String
	 */
	FormatMessage: function(message, parameters) {
		f_core.Assert(typeof(message)=="string", "f_core.FormatMessage: Message parameter is invalid '"+message+"'.");
//		f_core.Assert(parameters instanceof Array, "f_core.FormatMessage: parameters parameter is invalid '"+parameters+"'.");
		
		var ret="";
		var pos=0;
		for(;pos<message.length;) {
			var idx=message.indexOf('{', pos);
			var idx2=message.indexOf('\'', pos);
			
			if (idx2<0 && idx<0) {
				return ret+message.substring(pos);
			}
			
			if (idx2<0 || (idx>=0 && idx<idx2)) {	
				idx2=message.indexOf('}', idx);
				if (idx2<0) {
					throw new Error("Invalid expression \""+message+"\".");
				}
				
				ret+=message.substring(pos, idx);
				
				if (parameters) {
					var p=message.substring(idx+1, idx2);
					var num=parseInt(p, 10);
					if (!isNaN(num)) {
						if (num>=0 && num<parameters.length) {
							ret+=parameters[num];
						}
					} else if (parameters[p]) {
						ret+=parameters[p];
					}
				}
								
				pos=idx2+1
				continue;
			}
			
			ret+=message.substring(pos, idx2);
			
			idx=message.indexOf('\'', idx2+1);
			if (idx<0) {
				throw new Error("Invalid expression \""+message+"\".");
			}
			pos=idx+1;

			if (idx==idx2+1) {
				ret+='\'';
				
			} else {
				ret+=message.substring(idx2+1, idx);

				if (message.charAt(pos)=='\'') {
					ret+='\'';
				}
			}
		}
		
		return ret;
	},
	/**
	 * @method hidden static 
	 * @param String params 
	 * @param optional Object object Map or Array
	 * @return Object Map or Array
	 */
	ParseParameters: function(params, object) {
		params=params.split(":");
		
		var key=undefined;
		for(var i=0;i<params.length;i++) {
			var param=params[i];
			
			if (param=="%") {
				param=null;
				
			} else if (param.indexOf('%')>=0) {
				param=param.replace(/%7C/g, "|");
				param=param.replace(/%3A/g, ":");
				param=param.replace(/%25/g, "%");
			}
			
			if (object instanceof Array) {
				object.push(param);
				continue;
			}
			
			if (key===undefined) {
				key=param;
				continue;
			}
			
			if (object===undefined) {
				object=new Object;
			}
			object[key]=param;
			key=undefined;
		}
		
		return object;
	},	 
	/**
	 * @method hidden static 
	 * @param String url 
	 * @param any data
	 * @return any Data
	 * @dontInline f_popup
	 */
	UpdateAjaxParameters: function(component, url, data) {
		var forms=document.forms;
		var form=component;
		
		if (forms.length==1 || !component || component.nodeType==f_core.DOCUMENT_NODE) {
			form=forms[0];

		} else if (component.tagName.toLowerCase()!="form") {
			
			if (window.f_popup) { // Une popup ?
				var popupComponent=f_popup.GetComponent();
				if (popupComponent) {
					component=popupComponent;
				}
			}

			form=f_core.GetParentForm(component);			
		}
		
		if (!form) {
			f_core.Error(f_core, "UpdateAjaxParameters: can not copy jsf marker"); 
			return;
		}
		
		var ajaxParametersUpdater=f_core._AjaxParametersUpdater;
		if (ajaxParametersUpdater) {
			return ajaxParametersUpdater.call(this, form, component, url, data);
		}
		
		f_core.Debug(f_core, "UpdateAjaxParameters: Use default faces hidden input search !");
		
		return f_core.AddFacesHiddenInputParameters(form, function(input) {
			return !f_core.GetAttribute(input, "v:class");
		}, data);
	},
	/**
	 * @method hidden static 
	 * @param Function callback
	 * @return Function
	 */
	SetAjaxParametersUpdater: function(callback) {
		var old=f_core._AjaxParametersUpdater;
		f_core._AjaxParametersUpdater=callback;
		return old;
	},
	/**
	 * @method hidden static 
	 * @param HTMLFormElement form
	 * @param String pattern
	 * @param any data
	 * @param boolean onlyOne
	 * @return any
	 */
	AddFacesHiddenInputParameters: function(form, acceptFunction, data, onlyOne) {
		var inputs=f_core.GetElementsByTagName(form, "input");
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			
			var type=input.type;
			if (!type || type.toLowerCase()!="hidden") {
				continue;
			}
		
			if (!acceptFunction(input)) {
				continue;
			}
			
			var name=input.name;
			var value=input.value;
			f_core.Debug("f_core", "AddFacesHiddenInputParameters: Add parameter name='"+name+"' value='"+value+"'.");
		
			if (typeof(data)=="string") {
				if (data) {
					data+="&";
				}
				
				data+=encodeURIComponent(name)+"="+encodeURIComponent(value);

			} else {
				if (data===undefined) {
					data=new Object;
				}	
				
				data[name]=value;
			}
			
			if (onlyOne) {
				break;
			}
		}
		
		return data;
	},
	/**
	 * @method hidden static 
	 * @param String text Text to encode to HTML form
	 * @return String Html form of text.
	 */
	EncodeHtml: function(text) {
		f_core.Assert(typeof(text)=="string", "f_core.EncodeHtml: Invalid text parameter ("+text+").");

		return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	},
	/**
	 * @method hidden static 
	 * @param String text Text to decode from HTML form
	 * @return String UTF8 text.
	 */
	DecodeHtml: function(text) {
		f_core.Assert(typeof(text)=="string", "f_core.DecodeHtml: Invalid text parameter ("+text+").");

		return text.replace(/&gt;/gi, ">").replace(/&lt;/gi, "<").replace(/&amp;/gi, "&");
	},
	/** 
	 * @method hidden static 
	 * @param Array dest
	 * @param Array args
	 * @param optional number index
	 * @param optional number length
	 * @return Array
	 */
	PushArguments: function(dest, args, index, length) {
		f_core.Assert(dest===undefined || dest===null || (dest instanceof Array), "f_core.PushArguments: Invalid dest parameter ("+dest+").");
		f_core.Assert(typeof(args)=="object", "f_core.PushArguments: Invalid args parameter ("+args+").");
		f_core.Assert(index===undefined || typeof(index)=="number", "f_core.PushArguments: Invalid index parameter ("+index+").");
		f_core.Assert(length===undefined || typeof(length)=="number", "f_core.PushArguments: Invalid length parameter ("+length+").");
		
		if (index===undefined) {
			index=0;
		}
		// length devient last !
		if (length!==undefined) {
			length+=index;
			
		} else {
			length=args.length;
		}
		
		if (!dest) {
			dest=new Array();
		}
		
		for(;index<length;index++) {
			dest.push(args[index]);
		}
		
		return dest;
	},
	/** 
	 * @method hidden static
	 * @return void
	 */
	DisableContextMenu: function() {
		if (!document.body) {
			f_core._DisabledContextMenu=true;
			return;
		}
		document.body.oncontextmenu=f_core.CancelJsEventHandler;
	},
	/** 
	 * @method hidden static
	 * @param String text
	 * @return String
	 */
	Trim: function(text) {
		f_core.Assert(typeof(text)=="string", "f_core.Trim: Invalid text parameter ("+text+").");

		text = text.replace(/^\s\s*/, '');
		
		var ws = /\s/;
		var i;
		for(i = text.length;ws.test(text.charAt(--i)););
		
		return text.slice(0, i + 1);
	},
	/** 
	 * @method hidden static
	 * @param String url
	 * @return void
	 */
	VerifyBrowserCompatibility: function(url) {
		f_core.Assert(typeof(url)=="string", "f_core.VerifyBrowserCompatibility: Invalid url parameter ("+url+").");

		if (f_core.IsGecko() || f_core.IsInternetExplorer()) {
			return;
		}
		
		document.location=url;
	},
	
	/** 
	 * @method hidden static
	 * @param String text
	 * @return String
	 */
	UpperCaseFirstChar: function(text) {
		f_core.Assert(typeof(text)=="string", "f_core.UpperCaseFirstChar: Invalid text parameter ("+text+").");

		if (!text) {
			return text;
		}
		
		return text.charAt(0).toUpperCase()+text.substring(1);
	},
	/**
	 * @method hidden static
	 * @param HTMLElement component Html component.
	 * @param number opacity Value between 0 (hidden) and 1 (visible)
	 * @return void
	 */
	SetOpacity: function(component, opacity) {
		f_core.Assert(component && component.nodeType==f_core.ELEMENT_NODE, "f_core.SetOpacity: Invalid component parameter ("+component+")");
		f_core.Assert(typeof(opacity)=="number" && opacity>=0 && opacity<=1, "f_core.SetOpacity: Invalid opacity parameter ("+opacity+")");

		if (component.style.opacity!==undefined) {
			// CSS 3  on peut toujours réver !
			component.style.opacity = opacity;
			return;
		}
		
		if (f_core.IsInternetExplorer()) {
			if (opacity==1) {
				component.style.filter = "";
				
			} else {
				component.style.filter = "alpha(opacity="+Math.floor(opacity*100)+")";
			}
			
			return;
		}
		
		if (f_core.IsGecko()) {
			component.style.MozOpacity = opacity;
			return;
		}
	},
	/**
	 * @method static hidden
	 * @param Object object
	 * @return Object
	 */
	CopyObject: function(object) {
		f_core.Assert(object===null || object===undefined || typeof(object)=="object", "f_core.CopyObject: Invalid object parameter ("+object+")");

		if (object===null || object===undefined) {
			return object;
		}
				
		var obj=new Object;
		for (var p in object) {
			obj[p]=object[p];
		}
		
		return obj;
	},
	/**
	 * @method static hidden
	 * @param String componentClientId 
	 * @param number messageCode
	 * @param String message
	 * @param String messageDetail
	 * @return boolean
	 */
	PerformErrorEvent: function(componentClientId, messageCode, message, messageDetail) {
		if (!componentClientId) {
			f_core.Error(f_core, "Error event code='"+messageCode+"' message='"+message+"' messageDetail='"+messageDetail+"'.");
			return false;
		}
		
		var component=f_core.GetElementByClientId(componentClientId);
		if (!component || typeof(component.f_fireEvent)!="function") {
			f_core.Error(f_core, "Error event componentClientId='"+componentClientId+"' code='"+messageCode+"' message='"+message+"' messageDetail='"+messageDetail+"'.");
			return false;
		}
		
		var event=new f_event(component, f_event.ERROR, null, message, messageCode, null, messageDetail);
		try {
			return component.f_fireEvent(event);
			
		} finally {
			event.f_finalize();
		}
	},
	/**
	 * @method hidden static
	 * @param String code
	 * @return any
	 */
	WindowScopeEval: function(code) {

		if (false && window.execScript) { // IE only	
		    window.execScript(code); // eval in global scope for IE
		    return;
		}
		  
		if (window.eval) { // Firefox
			return window.eval(code);
		}

		// Pour IE, ca a l'air de marcher ...
		var f=new window.Function(code);
		return f.call(window);
	},

	/**
	 * @method hidden static 
	 * @param HTMLElement Node which defines v:data attribute.
	 * @return Object
	 */
	ParseDataAttribute: function(node) {
		var clientData=new Object;
		
		var att=f_core.GetAttribute(node, "v:data");
		if (!att) {
			return clientData;
		}
		
		var ds=att.split(",");
		for(var i=0;i<ds.length;i++) {
			var d=ds[i];
			var vname=d;
			var value="";
			
			var p=d.indexOf("=");
			if (p>=0) {
				vname=d.substring(0, p).replace(/\+/g, " ");
				vname=decodeURIComponent(vname);
				
				value=d.substring(p+1);
				value=value.replace(/\+/g," ");
				value=decodeURIComponent(value);
			}
								
			clientData[vname]=value;
		}

		return clientData;
	},
	
	/**
	 * @method public static
	 * @param HTMLElement component
	 * @return void
	 */
	ShowComponent: function(component) {
		try {	
			var position=f_core.GetAbsolutePosition(component);
			var size= { width: component.offsetWidth, height: component.offsetHeight };
						
			if (f_core.IsGecko()) {
				size.width+=f_core.ComputeBorderLength(component, "left", "right");
				size.height+=f_core.ComputeBorderLength(component, "top", "bottom");
			}		
			
			for(;;) {			
				var doc=component.ownerDocument;
				var scroll=f_core.GetScrollOffsets(null, doc);
				var viewSize=f_core.GetViewSize(null, doc);		
				
				f_core.Debug(f_core, "ShowComponent: position x="+position.x+" y="+position.y+"  scroll.x="+scroll.x+" scroll.y="+scroll.y+"  view.width="+viewSize.width+" view.height="+viewSize.height+"  document='"+doc.location+"'");
				
				var newScroll= { x: scroll.x, y: scroll.y };
				
				if (position.y+size.height>newScroll.y+viewSize.height) {
					newScroll.y=position.y+size.height-viewSize.height;			
				}
				if (position.y<newScroll.y) {
					newScroll.y=position.y;
				}
			
				if (position.x+size.width>newScroll.x+viewSize.width) {
					newScroll.x=position.x+size.width-viewSize.width;
				}
				if (position.x<newScroll.x) {
					newScroll.x=position.x;
				} 
				
				var win=f_core.GetWindow(doc);
				if (newScroll.x!=scroll.x || newScroll.y!=scroll.y) {
				
					f_core.Debug(f_core, "ShowComponent: move scroll x="+newScroll.x+" y="+newScroll.y+"  oldX="+scroll.x+" oldY="+scroll.y);
								
					win.scroll(newScroll.x, newScroll.y);
				}
				
				component=win.frameElement
				if (!component) {
					return;
				}
				
				var iframePosition=f_core.GetAbsolutePosition(component);
				position.x+=iframePosition.x;
				position.y+=iframePosition.y;
			}
		} catch (x) {
			// Un probleme de sécurité ?
			f_core.Info(f_core, "ShowComponent: can not change scroll position !", x);
		}
	},
	/**
	 * @method hidden static
	 * @param HTMLElement parent
	 * @param HTMLElement child
	 * @return void
	 */
	AppendChild: function(parent, child) {
		f_core.Assert(parent.ownerDocument==child.ownerDocument, "f_core.AppendChild: Different owner document. (parent, child)");
		f_core.Assert(parent.ownerDocument.location, "f_core.AppendChild: Invalid parent. (invalid document)");
		
		parent.appendChild(child);
	},
	/**
	 * @method hidden static
	 * @param HTMLElement parent
	 * @param HTMLElement child
	 * @param HTMLElement childBefore
	 * @return void
	 */
	InsertBefore: function(parent, child, childBefore) {
		f_core.Assert(parent.ownerDocument==child.ownerDocument, "f_core.InsertBefore: Different owner document. (parent, child)");
		f_core.Assert(parent.ownerDocument.location, "f_core.InsertBefore: Invalid parent. (invalid document)");
		f_core.Assert(!childBefore || child.ownerDocument==childBefore.ownerDocument, "f_core.InsertBefore: Different owner document. (child, childBefore)");
		f_core.Assert(!childBefore || childBefore.parentNode==parent, "f_core.InsertBefore: ChildBefore has not the same parent");
		
		parent.insertBefore(child, childBefore);
	},
	/**
	 * @method hidden static
	 * @param String... urls
	 * @return void
	 */
	IncludesScript: function(urls) {
		for(var i=0;i<arguments.length;) { 
			var url=arguments[i++];
			var charSet=arguments[i++];
			
			if (!charSet) {
				charSet="UTF-8";
				
			} else if (typeof(charSet)=="number") {
				charSet="ISO-8859-"+charSet;
			}
		
			document.write("<SCRIPT type=\"text/javascript\" charset=\""+charSet+"\" src=\""+url+"\"></SCRIPT>");
		}		
	},
	/**
	 * @method public static
	 * @return void
	 */
	ShowVersion: function() {
		var msg="RCFaces:\nbuildId="+window.rcfacesBuildId;
		
		if (window.RCFACES_JS_VERSION) {
			msg+="\njs.version="+window.RCFACES_JS_VERSION;
		}
		
		if (window.frames.length) {
			function f(win,index) {				
				try {
					var msg;
					
					if (!win._rcfacesClassLoader) {
						msg="\n"+index+": "+win.location;

					} else {					
						msg="\n"+index+": "+win._rcfacesClassLoader;
					}

					if (index=="Root") {
						index="";

					} else {
						index+=".";
					}
			
					var frames=win.frames;		
					for(var i=0;i<frames.length;i++) {
						msg+=arguments.callee(frames[i], index+(i+1));
					}
					
					return msg;					
				} catch (x) {
					// Exception de sécurité !
				}
				
				return "";
			}
			
			msg+=f(window, "Root");
		}
				
		alert(msg);
	},
	/**
	 * @method public static
	 * @param String tokenId
	 * @return void
	 */
	ReportError: function(tokenId) {
		if (!tokenId) {
			return;
		}
		var url=f_env.GetStyleSheetBase()+f_core._REPORT_ERROR_URL+"?tokenId="+encodeURIComponent(tokenId);
				
		window.open(url, "rcfacesReport"+new Date().getTime());
	},
	/**
	 * @method public static
	 * @param Window win
	 * @return void
	 */
	ProfileExit: function(win) {
		win._rcfacesNoSubmit=true;
		
		win.document.forms[0].submit();
		f_core._OnExit.call(win);		
	},
	/**
	 * @method public static
	 * @return String
	 */
	f_getName: function() {
		return "f_core";
	},
	/**
	 * @method public static
	 * @return String
	 */
	toString: function() {
		return "[class f_core]";
	}
}


f_core._InitLibrary(window);


