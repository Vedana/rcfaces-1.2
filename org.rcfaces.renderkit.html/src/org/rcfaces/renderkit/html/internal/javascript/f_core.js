/*
 * $Id$
 */

if (window.f_core) {
	var m="PANIC: Vedana Faces Library is already loaded !";
	alert(m);
	throw new Error(m);
} 

// For profiling ....
window._f_core_initLibraryDate=new Date();

		
var __SYMBOL=function(x) { return x };

/**
 * f_core class
 *
 * @class public f_core extends Object
 * @author Olivier Oeuillot + Joel Merlin
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
	_COMPONENT:	"VFC_COMPONENT",

	/**
	 * @field private static final String
	 */
	_EVENT: 	"VFC_EVENT",

	/**
	 * @field private static final String
	 */
	_VALUE:		"VFC_VALUE",

	/**
	 * @field private static final String
	 */
	_ITEM:		"VFC_ITEM",

	/**
	 * @field private static final String
	 */
	_DETAIL:	"VFC_DETAIL",
		
	/**
	 * @field private static final number
	 */
	_FOCUS_TIMEOUT_DELAY: 100,
	
	/**
	 * @field private static final number
	 */
	_ELEMENT_NODE: 1,
	
	/**
	 * @field private static final number
	 */
	_DOCUMENT_NODE: 9,

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
	 * @field private static
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
	 * @field private static HTMLComponent
	 */
	_FocusComponent: undefined,
	
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
	 */
	_AddLog: function(level, name, message, exception, win) {
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
					f_core.Assert(typeof(className)=="string", "Invalid class name of object '"+name+"'.");
					name=className;
				}
				
				f_core.Assert(typeof(name)=="string", "Invalid name of log '"+name+"'.");
				
				if (window._ignoreLog) {
					if (level===0) {
						var msg="Error: ("+name+"): "+message;
						if (exception) {
							msg+="\nException:\n"+exception;
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
						msg+="\nException:\n"+exception;
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
				for(var i=0;i<l.length;i++) {
					f_core._AddLog.apply(f_core, l[i]);
				}
			}
	
			if (level===undefined) {
				// Grosse astuce: c'etait histoire de faire afficher les logs.
				return true;
			}
	
			var log=f_log.GetLog(name);
			var fct;
			
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
			f_core.Assert(typeof(className)=="string", "Invalid class name of object '"+name+"'.");
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
					msg+="\n"+exception;
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
		f_core.Assert(typeof(name)=="string", "f_core.IsErrorEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isErrorEnabled();
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	SetDebugMode: function(debugMode) {
		if (debugMode===undefined) {
			debugMode=true;
		}
		f_core.DebugMode=debugMode;
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	SetProfilerMode: function(profilerMode) {
		if (profilerMode===undefined) {
			profilerMode=true;
		}
		if (!window.f_profilerCB) {
			window.f_profilerCB=profilerMode;
		}
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	SetDesignerMode: function(designerMode) {
		f_core.DesignerMode=designerMode;
	},
	/**
	 * @method hidden static
	 * @return void
	 */
	_InitLibrary: function(window) {
		var initDate=window._f_core_initLibraryDate;
		
		f_core.Info("f_core", "InitLibrary: "+initDate);
		
		var profilerCB=window.f_profilerCB;
		if (!profilerCB) {
			try {
				for(var w=window;w && w.parent!=w;w=w.parent) {
					if (!w.parent.f_profilerCB) {
						continue;
					}
					
					profilerCB=w.parent.f_profilerCB;
					window.f_profilerCB=profilerCB;
				}
			} catch (x) {
				// Il y aura peut etre des problemes de sécurité ... on laisse tomber !
			}
		}
		
		if (profilerCB) {
			f_core.Info("f_core", "Enable profiler mode");
			
			f_core.Profile(null, "f_core.initializing", initDate);
		}
			
		if (window.cameliaVersion) {
			f_core.Info("f_core", "Camelia version: "+cameliaVersion);
		}
	
		f_core.AddEventListener(window, "load", f_core._OnInit);
		f_core.AddEventListener(window, "unload", f_core._OnExit);

		if (f_core.IsInternetExplorer()) {
			f_core.AddEventListener(document, "selectstart", f_core._IeOnSelectStart);
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
	 	if (f_core.IsInternetExplorer()) {
		    component.attachEvent("on"+name, fct);
			    
		    if (capture) {
			    capture.setCapture();
			}
		    return;
		}

		if (capture) {
			capture=true;
		}
	    component.addEventListener(name, fct, capture);
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
	 	if (f_core.IsInternetExplorer()) {
		    if (capture) {
				capture.releaseCapture();
			}

			document.detachEvent("on"+name, fct);
			    
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
	Profile: function(timeEnd, name, date) {
		if (f_core._LoggingProfile) {
			return;
		}
		try {
			f_core._LoggingProfile=true;	
	
			var profiler=window.f_profilerCB;
			if (profiler===undefined) {
				return;
			}
			
			if (typeof(profiler)=="function") {
				try {
					profiler.apply(window, arguments);
					
				} catch (x) {
					f_core.Error(f_core, "While calling external profiler.", x);
				}
				
				return;
			}
			
			if (profiler!==true || window._f_exiting) {
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
		
			var diff=date-window._f_core_initLibraryDate;
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
	 */
	_OnInit: function() {
		var now=new Date();
		f_core.Profile(false, "f_core.onInit", now);
		try {		
			f_core._FlushLogs();	
	
			var window=this;
		
			f_core.Info("f_core", "Install library (onload) on "+now);
			
			if (f_core.DebugMode) {
				var title=["DEBUG"];
				f_core.Info("f_core", "Enable f_core.DEBUG mode");
			
				var profiler=window.f_profilerCB;
				if (profiler) {
					title.push("PROFILER");
				}
				
				if (title.length>0) {
					window.document.title+="  [Camelia: "+title.join(",")+"]";
				}
			}
			
			if (f_core._DisabledContextMenu) {
				f_core._DisabledContextMenu=undefined;
				
				f_core.DisableContextMenu();
			}
							
			// Hook the forms
			var forms = window.document.forms;
			for (var i=0; i<forms.length; i++) {
				var f = forms[i];
	
				f_core.InitializeForm(f);
			}
			
			// Les objets non encore initializés		
			window._classLoader._initializeObjects();
	
			f_core.Profile(null, "f_core.onInit.objects");

			// Initialize packages here
			window._classLoader._onDocumentComplete();
	
		} finally {
			f_core.Profile(true, "f_core.onInit");
		}
	},
	/** 
	 * @method hidden static
	 */
	InitializeForm: function(f) {
		if (f._initialized) {
			return;
		}

		f._initialized=true;
		
		f_core.AddEventListener(f, "submit", f_core._OnSubmit);
		f_core.AddEventListener(f, "reset", f_core._OnReset);

		f_core.f_findComponent=f_core._FormFindComponent;


		// Pas forcement, si on ne veut pas que ca soit trop intrusif !
		if (true) {
			try {
				var old=f.submit;
				
				f.submit = f_core._Submit;
				
				f._oldSubmit = old;
			} catch (x) {
				// Dans certaines versions de IE, il n'est pas possible de changer le submit !
			}
		}
		
		f_core.Debug("f_core", "Hook Html FORM tag id=\""+f.id+"\".");
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
	 */
	_OnExit: function() {
		var win=this;
		
		win._f_exiting=true;
		try {		
			var document=win.document;
	
			f_core.Profile(false, "f_core.onExit");
			try {
				
				f_core.RemoveEventListener(win, "load", f_core._OnInit);
				f_core.RemoveEventListener(win, "unload", f_core._OnExit);
				f_core._DesinstallModalWindow();
				
				if (f_core.IsInternetExplorer()) {
					f_core.RemoveEventListener(document, "selectstart", f_core._IeOnSelectStart);
				}
				
				var timeoutID=f_core._FocusTimeoutID;
				if (timeoutID) {
					f_core._FocusTimeoutID=undefined;
					window.clearTimeout(timeoutID);
				}
				f_core._FocusComponent=undefined;
		
				var forms = document.forms;
				for (var i=0; i<forms.length; i++) {
					var f = forms[i];
					
					if (!f._initialized) {
						continue;
					}
					f._initialized=undefined;
		
					f_core.RemoveEventListener(f, "submit", f_core._OnSubmit);
					f_core.RemoveEventListener(f, "reset", f_core._OnReset);
		
					f._checkListeners=undefined;
					f._resetListeners=undefined;
					f._messageContext=undefined;
					f.f_findComponent=undefined;
					
					if (f._oldSubmit) {
						try {
							f.submit = f._oldSubmit;
							
						} catch (x) {
							// Dans certaines versions de IE, il n'est pas possible de changer le submit !
						}
						
						f._oldSubmit = undefined;
					}
				}
		
				document._lazyIndex=undefined;
		
				// Terminate packages here
				win._classLoader._onExit();
				
				if (win._f_closeWindow) {		
					win._f_closeWindow=undefined;
					win.close();
				}
				
			} finally {
				f_core.Profile(true, "f_core.onExit");
			}
		} finally {
			win._f_exiting=undefined;
		}
	},
	/**
	 * @method static hidden
	 */
	SetInputHidden: function(form, name, val) {
		f_core.Assert(form && form.tagName.toUpperCase()=="FORM", "f_core.SetInputHidden: Invalid form component ! "+form);
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
				f_core.Error(f_core, "Can not set a input hidden '"+name+"' with value '"+val+"'.");
			}
		}
		
		var inputs=f_core.GetElementsByTagName(form, "INPUT");
				
		for(var i=0;i<inputs.length;i++) {
			var input=inputs[i];
			
			if (input.name!=name) {
				continue;
			}
			
			f_core.Assert(input.type && input.type.toUpperCase()=="HIDDEN", "Input type is not hidden !");
			
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
		
		var input = form.ownerDocument.createElement("INPUT");
		
		// Il faut specifier les caracteristiques avant le appendChild !
		input.type = "hidden";
		input.value = val;
		input.name = name;

		form.appendChild(input);

		return input;

	},
	/**
	 * @method public static
	 * @param HTMLElement elt
	 * @return HTMLFormElement
	 */
	GetParentForm: function(elt) {
		f_core.Assert(elt.ownerDocument, "f_core.GetParentForm: Invalid parameter element ("+elt+")");
	
		// Optimisation s'il n'y a qu'une seule form !
		var forms=elt.ownerDocument.forms;
		if (forms.length==1) {
			f_core.Debug(f_core, "Only one form into document, returns "+forms[0].id);
			return forms[0];
		}
	
		for(var f=elt;f;f=f.parentNode) {
			var tagName=f.tagName;
			if (!tagName || f.nodeType!=1) {
				continue;
			}
			
			if (tagName.toUpperCase()!="FORM") {
				continue;
			}
			
			f_core.Debug(f_core, "Parent form of '"+elt.id+"': "+f.id);
			
			return f;
		}

		f_core.Debug(f_core, "Can not find any parent form for component '"+elt.id+"'.");
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
	 * @return void
	 */
	SetTextNode: function(component, text, accessKey) {
		f_core.Assert(component && component.nodeType==f_core._ELEMENT_NODE, "f_core.SetTextNode: Invalid component ! ("+component+")");
		f_core.Assert(text===null || typeof(text)=="string", "f_core.SetTextNode: Invalid text parameter ("+text+")");
		f_core.Assert(accessKey===undefined || accessKey===null || typeof(accessKey)=="string", "f_core.SetTextNode: Invalid accessKey parameter ("+accessKey+")");
		
		var doc=component.ownerDocument;
		
		if (text && accessKey && accessKey.length) {
			var idx=text.toLowerCase().indexOf(accessKey.toLowerCase());
			if (idx>=0) {
				for(;component.firstChild;) {
					component.removeChild(component.firstChild);
				}

				if (idx) {
					component.appendChild(doc.createTextNode(text.substring(0, idx)));
				}

				var ul=doc.createElement("U");
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
			if (child.nodeType!=3) {
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
		f_core.Assert(component && component.nodeType==f_core._ELEMENT_NODE, "f_core.GetTextNode: Invalid component ! ("+component+")");

		var children=component.childNodes;

		var text="";
		for(var i=0;i<children.length;i++) {
			var child=children[i];
			
			switch(child.nodeType) {
			case 3:
			case 4:
				text+=child.data;
				break;
				
			case 1:
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
	 */
	_OnReset: function(evt) {
		if (!evt) {
			evt = f_core.GetEvent(this);
		}
		
		var win;
		var form;
		
		var tagName=this.tagName;
		if (!tagName || tagName.toUpperCase()!="FORM") {
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
		
		f_core.Assert(form && form.tagName.toUpperCase()=="FORM", "Can not identify form ! ("+form+")");
		f_core.Assert(win, "Can not identify window !");

		f_core.Info("f_core", "Catch reset event from form '"+form.id+"'.");

		if (win.f_event) {
			if (win.f_event.GetEventLocked(true)) {
				return false;
			}
		}
		
		f_core._CallFormResetListeners(form, evt);
		
		// Appel de la validation ?
		if (f_env.GetCheckValidation()) {
			f_core._CallFormCheckListeners(form);
		}
	},
	/**
	 * @method private static
	 */
	_OnSubmit: function(evt) {
		f_core.Profile(false, "f_core.SubmitEvent");
		try {
			if (!evt) {
				evt = window.event;
			}
	
			// f_core.Assert(evt, "f_core._OnSubmit: Event is not known ?");
			// evt peut être null !
	
			if (!window._submitting && f_env.GetCancelExternalSubmit()) {
				return f_core.CancelEvent(evt);
			}
		
			var win;
			var form;
			
			if (!this.tagName || this.tagName.toUpperCase()!="FORM") {
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
			
			f_core.Assert(form && form.tagName.toUpperCase()=="FORM", "Can not identify form ! ("+form+")");
			f_core.Assert(win, "Can not identify window !");
	
			f_core.Info("f_core", "Catch submit event from form '"+form.id+"'.");
	
			if (win.f_event) {
				if (win.f_event.GetEventLocked(true)) {
					return f_core.CancelEvent(evt);
				}
			}
	
			if (!form._initialized) {
				//f_core.Assert(form._initialized, "Not initialized form '"+form.id+"'.");
	
				// Cas ou l'utilisateur va plus vite que la musique ! (avant le onload de la page)
				
				if (f_env.IsSubmitUntilPageCompleteLocked()) {
					return f_core.CancelEvent(evt);
				}
				
				// On essaye d'initialiser les objets qui ne sont pas encore initializés
				window._classLoader._initializeObjects();
				
				// XXX Il faut peut etre attendre QUE TOUTS LES OBJETS soient initialisés ?
				
				// On initialize la form !				
				f_core.InitializeForm(form); 
			}
			
			var component=win.f_event.GetComponent();
			
			var immediate;
			if (win.f_event.GetType()==f_event.ERROR) {
				f_core.Debug("f_core", "Event is an Error, bypass check validation !");

				immediate=true;
				
			} else if (component) {			
				f_core.Debug("f_core", "Component which performs submit event is '"+((component)?component.id:"**UNKNOWN**")+"', call checkListeners="+ f_env.GetCheckValidation());
		
				if (typeof(component.f_isImmediate)=="function") {
					immediate=component.f_isImmediate();
		
					f_core.Debug("f_core", "Test immediate property of '"+component.id+"' = "+immediate);
				}
			}
						
			if (immediate!==true && f_env.GetCheckValidation()) {
				var valid=f_core._CallFormCheckListeners(form);
				
				f_core.Profile(null, "f_core.SubmitEvent.checkListeners");
				
				f_core.Debug(f_core, "Validation of checkers returns: "+valid);
				if (!valid) {
					return f_core.CancelEvent(evt);
				}
			}
			
			var classLoader=win._classLoader;
			if (classLoader) {
				classLoader.serialize(form);
				
				f_core.Profile(null, "f_core.SubmitEvent.serialized");
			}
				
			return true;
		} finally {
			f_core.Profile(true, "f_core.SubmitEvent");
		}
	},
	/**
	 * @method private static
	 */
	_Submit: function(form, elt, event, url, target, createWindowParameters, closeWindow, modal) {
		f_core.Assert(createWindowParameters===undefined || createWindowParameters===null || typeof(createWindowParameters)=="object", "Submit: createWindowParameters parameter must be undefined or an object.");
		f_core.Assert(closeWindow===undefined || closeWindow===null || typeof(closeWindow)=="boolean", "Submit: closeWindow parameter must be undefined or a boolean.");
		f_core.Assert(modal===undefined || modal===null || typeof(modal)=="boolean", "Submit: modal parameter must be undefined or a boolean.");
		
		
		f_core.Profile(false, "f_core._submit("+url+")");

		try {
			// Check if we get called from the form itself and use it if none specified
			if (!form && (this != f_core)) {
				form = this;
			}
			
			// Intialize defaul return value
			var ret = false;
	
			var type;
			if (typeof(event)=="string") {
				type=event;	
				event=f_event.GetEvent(form);
	
			} else if (!event) {
				event=f_event.GetEvent(form);
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
				f_core.Error(f_core, "Can not find form !", ex);
				throw ex;
				//return false;
			}

			var document=form.ownerDocument;
			var win=f_core.GetWindow(document);
			
			if (win.f_event.GetEventLocked(true)) {
				return false;
			}
	
			// Double check this is a real FORM
			f_core.Assert((form.tagName.toUpperCase()=="FORM"),"f_core._Submit: Invalid form '"+form.tagName+"'.");
	
			// Call onsubmit hook
			try {
				win._submitting=true;
				
				var ret = f_core._OnSubmit.call(form);
				if (!ret) {
					return ret;
				}
			} finally {
				win._submitting=undefined;
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
			f_core.SetInputHidden(form, f_core._ITEM, eventValue);

			var eventDetail=(event)?event.f_getDetail():null;
			f_core.SetInputHidden(form, f_core._DETAIL, eventDetail);
	
			// Keep the previous for further restore
			if (url) {
				if (!form._old_action) {
					form._old_action = form.action;
				}
				
				form.action = url;
				
			} else if (form._old_action) {
				form.action=form._old_action;
			}
	
			// Keep the previous for further restore
			if (target) {
				if (form._old_target===undefined) {
					form._old_target = form.target;
				}
				
				form.target = target;
				
			} else if (form._old_target!==undefined) {
				form.target=form._old_target;
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
				form._oldSubmit();
	
				f_core.Profile(null, "f_core._submit.postSubmit");
		
				if (closeWindow) {
					win._f_closeWindow=true;
					return true;
				}
				
				if (createWindowParameters) {
					unlockEvents=true;
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
	
			// IE Progress bar bug only
			if (f_core.IsInternetExplorer()) {
				switch (document.readyState) {
				case "loading":
					var msg=f_env.Get("F_SUBMIT_PROGRESS_MESSAGE");
					if (msg) {
						win.defaultStatus = msg;
						document.body.style.cursor = "wait";
					}
					break;
				
				// Target is another window
				case "complete":
				default: 
					break;
				}
			}

			return true;
		} finally {		
			f_core.Profile(true, "f_core._submit("+url+")");
		}
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
	 */
	OpenWindow: function(window, parameters, modal) {
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
		
		f_core.Debug("f_core", "Open window, url="+url+" target="+target+" features="+s);
		
		var newWindow;
		try {
			newWindow=window.open(url, target, s);

		} catch (x) {
			f_core.Debug("f_core", "Open window exception.", x);
			newWindow=null;
		}
			
		if (!newWindow) {
			// Popup Blocker
			var s=f_env.GetOpenWindowErrorMessage();
			
			if (s) {
				alert(s);
				return null;
			}
			
			f_core.Error(f_core, "Can not open window url='"+url+"' target='"+target+"' features='"+s+"'.", x);
			return null;
		}
		
		if (modal) {
			f_core._InstallModalWindow(newWindow);
		}
	
		return newWindow;
	},
	/**
	 * @method private static
	 */
	_CallFormCheckListeners: function(form) {
		var checkListeners=form._checkListeners;
		if (!checkListeners || checkListeners.length<1) {
		
			f_core.Debug(f_core, "No check listeners to call ...");
			return true;
		}
		
		var cfp=undefined;
		var cfs=undefined;
		var ces=undefined;
		
		for(var i=0;i<checkListeners.length;i++) {
			var checkListener=checkListeners[i];
			
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
		}

		f_core.Debug(f_core, "PreCheck="+(cfp?cfp.length:0)+" Check="+(ces?ces.length:0)+" PostCheck="+(cfs?cfs.length:0)+".");
		
		var ret=true;
		try {
			if (cfp) {
				for(var i=0;i<cfp.length;i++) {
					var checkPre=cfp[i];
					
					try {
						checkPre.f_performCheckPre(form);
						
					} catch (x) {
						f_core.Error(f_core, "PreCheck value throws an exception : "+checkPre, x);
					}
				}
			}
			
			if (ces) {
				for(var i=0;i<ces.length && ret;i++) {
					var checkEvent=ces[i];
					
					try {
						if (checkEvent.f_performCheckValue(form)===false) {
							ret=false;
						}
						
					} catch (x) {
						f_core.Error(f_core, "Check value throws an exception : "+checkEvent, x);
					}
				}
			}
						
		} finally {		
			if (cfs) {
				for(var i=0;i<cfs.length;i++) {
					var checkPost=cfs[i];
					
					try {
						checkPost.f_performCheckPost(ret, form);

					} catch (x) {
						f_core.Error(f_core, "Post check value throws an exception : "+checkPost, x);
					}
				}
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
		if (!resetListeners || resetListeners.length<1) {
			return true;
		}
		
		var ret=true;
		for(var i=0;i<resetListeners.length && ret;i++) {
			var resetListener=resetListeners[i];
							
			var resetFunction=resetListener.f_onReset;
			f_core.Assert(typeof(resetFunction)=="function", "f_core._CallFormResetListeners: Invalid reset function '"+resetFunction+"'.");
			
			if (resetFunction.call(resetListener, event)===false) {
				ret=false;
			}
		}
				
		return ret;
	},
	/**
	 * @method public static hidden
	 */
	AddCheckListener: function(component, listener) {
		f_core.Assert(typeof(listener)=="object", "Listener must be an object ! ("+listener+")");
		f_core.Assert(component.nodeType, "f_core.AddCheckListener: Invalid component parameter ("+component+")");

		var form=this.GetParentForm(component);
		f_core.Assert(form, "Can not get form of component '"+component.id+"'.");
		
		var checkListeners=form._checkListeners;
		if (!checkListeners) {
			checkListeners=new Array;
			form._checkListeners=checkListeners;
		}
		
		checkListeners.push(listener);
	},
	/**
	 * @method public static hidden
	 */
	AddResetListener: function(component) {
		f_core.Assert(typeof(component)=="object", "Listener is invalid !");

		var form=this.GetParentForm(component);
		f_core.Assert(form, "Can not get form of component '"+component.id+"'.");
		
		var resetListeners=form._resetListeners;
		if (!resetListeners) {
			resetListeners=new Array;
			form._resetListeners=resetListeners;
		}
		
		resetListeners.f_addElement(component);
	},
	/**
	 * @method public static hidden
	 */
	RemoveResetListener: function(component) {
		f_core.Assert(typeof(component)=="object", "Listener is invalid !");

		var form=this.GetParentForm(component);
		f_core.Assert(form, "Can not get form of component '"+component.id+"'.");
		
		var resetListeners=form._resetListeners;
		if (!resetListeners) {
			return false;
		}	
		
		return resetListeners.f_removeElement(component);
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
		f_core.Assert(elt && (elt.nodeType==f_core._ELEMENT_NODE || elt.nodeType==f_core._DOCUMENT_NODE), "f_core.GetWindow: Invalid elt parameter ("+elt+")");

		// Cas de IE, si elt est déjà un Document !
		var view=elt.window;
		if (view) {
			return view;
		}
		
		var doc;
		if (elt.nodeType==f_core._DOCUMENT_NODE) { // nodeType=9 => Document
			doc=elt;
	
		} else { // nodeType=1 => Element
			doc=elt.ownerDocument;
		}
		
		f_core.Assert(doc, "f_core.GetWindow: Can not find window of component '"+elt+"'.");
		
		view=doc.defaultView; // DOM Level 2
		if (view) { 
			return view;
		}
		
		f_core.Assert(doc.parentWindow, "f_core.GetWindow: Invalid document: "+doc);
		
		return doc.parentWindow;
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
			
			var classes=cs.split(" ");
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
		if (elt.nodeType==f_core._ELEMENT_NODE && f_core.GetAttribute(elt, "v:class")==claz) {
			return f_core.GetWindow(elt)._classLoader._init(elt);
		}
		return null;
	},
	/**
	 * Find a child with a specified css class.
	 *
	 * @method public static
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
	 * @method public static
	 * @param HTMLElement elt Start node.
	 * @param String claz Class name.
	 * @param hidden boolean css Search Css class.
	 * @return HTMLElement
	 */
	GetChildByClass: function(elt,claz,css) {
		var comp = f_core._InstanceOf(elt,claz,css);
		if (comp) {
			return comp;
		}
		var ns=elt.childNodes
		if (!ns) {
			return null;
		}
		for(var i=0;i<ns.length;i++) {
			var n=ns[i];
			comp =  f_core._InstanceOf(n,claz,css);
			if (comp) {
				return comp;
			}
		}
		for(var i=0;i<ns.length;i++) {
			var n= f_core.GetChildByClass(ns[i],claz,css);
			if (n) {
				return n;
			}
		}
		
		return null;
	},
	/**
	 * Find component
	 *
	 * @method public static
	 * @param String... id Identifier
	 * @return HTMLElement
	 */
	FindComponent: function(id) {
		var component=document.body;
		f_core.Assert(component && component.tagName, "f_core.FindComponent: Invalid body component !");
		
		for(var i=0;component && i<arguments.length;i++) {
			component=fa_namingContainer.SearchElementById(component, arguments[i]);
		}
		
		return component;
	},
	/**
	 * Find a child by its identifier.
	 *
	 * @method public static
	 * @param String id Identifier
	 * @param optional Document doc Document.
	 * @param hidden boolean noCompleteComponent Dont complete component !
	 * @return HTMLElement
	 */
	GetElementById: function(id, doc, noCompleteComponent) {
		f_core.Assert(typeof(id)=="string", "f_core.GetElementById: Invalid id parameter '"+id+"'.");
		f_core.Assert(doc===undefined || (doc && doc.nodeType==f_core._DOCUMENT_NODE), "f_core.GetElementById: Invalid document parameter '"+doc+"'.");
		
		if (!doc) {
			doc=document;
		}
		var obj = doc.getElementById(id);
	
		if (!obj) {
			// On peut toujours chercher dans les forms du document ....
			obj=fa_namingContainer.SearchElementById(doc, id);
		}
		
		if (obj && f_class.IsObjectInitialized(obj)) {
			return obj;
		}
		
		if (!obj) {
			// Objet pas trouvé, on passe l'ID à la methode _init !
			obj=id;
		}
		
		obj = f_core.GetWindow(doc)._classLoader._init(obj, true);
		if (!obj) {
			return null;
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
	 * @param Element 
	 */
	GetAttribute: function(object, attributeName, defaultValue) {
		f_core.Assert(object && object.nodeType==f_core._ELEMENT_NODE, "Object parameter is node a valid node ! ("+object+")");
		f_core.Assert(typeof(attributeName)=="string", "attributeName parameter is invalid.");

		try {
			var value=object.getAttribute(attributeName);
			if (defaultValue===undefined || (value!==undefined && value!==null)) {
				return value;
			}
			
		} catch (x) {
			/* ignore, in IE6 calling on a table results in an exception */
		}

		return defaultValue;
	},	
	/** 
	 * Returns true if component (and its ancestors) is visible.
	 *
	 * @method hidden static
	 */
	IsComponentVisible: function(component) {
		f_core.Assert(component, "Component is null !");

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
			var component=parents[i];
			
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
		f_core.Assert(component && component.nodeType==f_core._ELEMENT_NODE, "Invalid component parameter '"+component+"'.");

		var curTop = 0;
		var curLeft= 0;
		
	//	f_core.Debug(f_core, "Get absolutePos of '"+component.id+"'.");
		if (component.offsetParent) {
			for (;component.offsetParent;component = component.offsetParent) {
				curTop += component.offsetTop;
				curLeft += component.offsetLeft;

		//		f_core.Debug(f_core, " Sub absolutePos of '"+component.id+"' x="+component.offsetLeft+" y="+component.offsetTop+"  totX="+curLeft+" totY="+curTop);
			}
		} else {
			if (component.x) {
				curLeft+=component.x;
			}
			if (component.y) {
				curTop += component.y;
			}
		}
		
	//	f_core.Debug(f_core, "  End absolutePos x="+curLeft+" y="+curTop);
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
	 */
	_SearchBrowser: function() {
		var agt=window.navigator.userAgent.toLowerCase();

		f_core.Info("f_core", "Navigator agent: "+agt);
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
				f_core.Error(f_core, "Can not parse msie version '"+version+"'.", ex);
				version=-1;
			}
			
			if (f_core._browser_major>=7) {
				f_core._browser=f_core.INTERNET_EXPLORER_7;

				f_core.Info(f_core, "Microsoft Internet Explorer 7 detected !");
				return true;
			}
			
			if (f_core._browser_major>=6) {
				f_core._browser=f_core.INTERNET_EXPLORER_6;

				f_core.Info(f_core, "Microsoft Internet Explorer 6 detected !");
				return true;
			}
			
			f_core.Info(f_core, "Invalid version of Microsoft Internet Explorer !");
			
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
						f_core.Error(f_core, "Can not parse firefox version '"+version+"'.", ex);
						return false;
					}
				}
				
				if (vs.length>1) {	
					try {
						f_core._browser_release=parseInt(vs[1], 10);
						
					} catch (ex) {
						f_core.Debug(f_core, "Can not parse release version ! (release="+vs[1]+")");
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

			f_core.Debug("f_core", "Browser version: major="+f_core._browser_major+" release="+f_core._browser_release+" minor="+f_core._browser_minor);

			if (f_core._browser_major==2) {			
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
			
			f_core.Info(f_core, "Invalid version of Firefox !");
			
			return false;
		}

		f_core.Assert(false, "Unknown browser '"+agt+"'.");
	},
	/**
	 * @method hidden static
	 */
	RemoveElement: function(list, value) {
		if (!list || !list.length) {
			return false;
		}
		for(var i=0;i<list.length;i++) {
			if (list[i]!==value) {
				continue;
			}
			
			list.splice(i, 1);
			return true;
		}
		return false;
	},
	/**
	 * @method hidden static
	 */
	AddElement: function(list, value) {
		for(var i=0;i<list.length;i++) {
			if (list[i]!==value) {
				continue;
			}
			
			return false;
		}
	
		list.push(value);
		return true;
	},
	/**
	 * @method hidden static
	 */
	CancelEventHandler: function(evt) {
		if (f_event.GetEventLocked(false)) {
			return false;
		}
	
		if (!evt) {
			evt=f_core.GetEvent(this);
		}
	
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method hidden static
	 */
	CancelEvent: function(evt) {
		if (!evt) {
			evt=window.event;

			// Lorsque l'évenement est "USER" il n'y a pas d'evt !
			// f_core.Assert(evt, "f_core.CancelEvent: Event is not known ?");
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
	 * @method hidden static 
	 */
	CancelEventHandlerTrue: function(evt) {
		if (f_event.GetEventLocked(false)) {
			return false;
		}
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * Returns the size of the View.
	 *
	 * @method public static 
	 * @param optional Document doc
	 * @return Object Object which defines 2 fields: width and height 
	 */
	GetViewSize: function(doc) {
		if (!doc) {
			doc=document;
		}
		if (f_core.IsInternetExplorer()) {
			return { 
		 		width: Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft) +
		     		 (doc.documentElement.clientWidth != 0 ? doc.documentElement.clientWidth : doc.body.clientWidth),
		   		height: Math.max(doc.documentElement.scrollTop, doc.body.scrollTop) +
		      		(doc.documentElement.clientHeight != 0 ? doc.documentElement.clientHeight : doc.body.clientHeight) 
		      };
		}
		  
		var window=f_core.GetWindow(doc);
		 
		return { 
			width: window.scrollX + window.innerWidth,
			height: window.scrollY + window.innerHeight
		};
	},
	/**
	 * Returns the position of the Window.
	 *
	 * @method public static 
	 * @param optional Document doc
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
	 * @param optional Document doc
	 * @return number[]
	 */
	GetEventPosition: function(event, doc) {
		f_core.Assert(event && event.type, "Invalid event parameter '"+event+"'.");
	
		if (!doc) {
			var target=event.relatedTarget;
			if (!target) {
				target=event.scrElement;
			}
			if (target) {
				if (target.nodeType==f_core._DOCUMENT_NODE) {
					doc=target;
					
				} else if (target.nodeType==f_core._ELEMENT_NODE) {
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
		f_core.Assert(component && component.nodeType==f_core._ELEMENT_NODE, "Invalid component parameter '"+component+"'.");
		f_core.Assert(event && event.type, "Invalid event parameter '"+event+"'.");
	
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

			} else if (window.f_time && (value instanceof f_time)) {
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
			}
			s+="["+value+":"+typeOfValue+"]";
		}
		
		if (s.length>0) {
			if (object.tagName) {
				s="TagName: "+object.tagName+"{"+object.className+"}\n"+s;
			}
			if (object._kclassName) {
				s="_KClassName: "+object._kclassName+"\n"+s;
			}
			
			s="Oubli de propriétés :\n"+s;
			
			var ex=new Error(s);
			f_core.Error(f_core, s, ex);
			
			throw ex;
		}
	},
	/**
	 * @method public static 
	 * @param HTMLElement component
	 * @param hidden boolean asyncMode
	 * @return boolean <code>true</code> is success !
	 */
	SetFocus: function(component, asyncMode) {
		f_core.Assert(component, "Component is NULL");
		f_core.Assert(component.nodeType==f_core._ELEMENT_NODE, "Parameter is not a component.");

		f_core.Debug(f_core, "SetFocus: component="+component.id+" asyncMode="+asyncMode);

		if (f_core._FocusTimeoutID) {
			f_core._FocusComponent=component;
			return;
		}
		
		if (asyncMode) {
			f_core._FocusComponent=component;
			
			f_core._FocusTimeoutID=window.setTimeout(f_core._FocusTimeout, f_core._FOCUS_TIMEOUT_DELAY);
			return;
		}

		if (typeof(component.f_show)=="function") {
			try {
				if (!component.f_show()) {
					f_core.Info("f_core", "Can not set focus to a not visible component");
					return;
				}
				
			} catch (ex) {
				f_core.Error(f_core, "Exception while calling f_show() of '"+component.id+"' [camelia method].", ex);
				
				return false;
			}
		}

		if (typeof(component.f_setFocus)=="function") {
			f_core.Debug(f_core, "Try to call f_setFocus() method to set the focus. (componentId="+component.id+"/tagName="+component.tagName+")");
			try {
				component.f_setFocus();
				return true;
				
			} catch (ex) {
				f_core.Error(f_core, "Exception while setting focus of '"+component.id+"' [camelia method].", ex);
			}
			
			return false;
		}
		
		if (f_core.IsInternetExplorer()) {
//			component.setActive();
		}

		f_core.Debug(f_core, "Try to call focus() method to set the focus. (componentId="+component.id+"/tagName="+component.tagName+")");

		try {
			component.focus();
			return true;
			
		} catch (ex) {
			if (f_core.IsGecko()) {
				// Le moteur GECKO peut generer une exception dans certains cas
				return true;
			}

			f_core.Error(f_core, "Exception while setting focus of '"+component.id+"'.", ex);
		}
		
		return false;
	},
	/**
	 * @method private static
	 * @return void
	 */
	_FocusTimeout: function() {
		// On sait jamais !
		if (!window.f_core) {
			return;
		}
		f_core._FocusTimeoutID=undefined;
		
		var component=f_core._FocusComponent;
		if (!component) {
			return;
		}
		f_core._FocusComponent=undefined;
		f_core.SetFocus(component, false);
	},	
	/**
	 * @method hidden static
	 */
	GetFirstElementByTagName: function(parent, tagName, assertIfNotFound) {
		f_core.Assert(parent && parent.nodeType, "Parent '"+parent+"' is not a Dom node !");
		
		var components=parent.getElementsByTagName(tagName);
		if (!components || !components.length) {
			if (assertIfNotFound) {
				f_core.Assert(false, "Component '"+tagName+"' not found !");
			}
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
			window.addEventListener("resize", function() {
				return listener.call(component);
				
			}, false);
			
			return true;
		}
		
		return false;
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
		
		f_core.Assert(false, "Browser not supported !");
		return null;
	},
	/**
	 * @method hidden static 
	 */
	EncodeObject: function(p, sep) {
		if (!sep) {
			sep="&";
		}
		
		var d = "";
		for (var i in p) {
			if (d) { 
				d+=sep;
			}
			
			d+=i+"=";
			
			var v=p[i];
			if (v===null || v===undefined) {
				d+="L";
				continue;
			}

			if (v===true) {
				d+="T";
				continue;
			}			

			if (v===false) {
				d+="F";
				continue;
			}
			
			if (v==="") {
				// Vide !
				continue;
			}
			
			if (typeof(v)=="number") {
				if (v==0) {
					d+="0";
					continue;
				}
				
//				d+="N";
				
				var fixed=v.toFixed();
				if (fixed==v) {
					v=fixed;
				}
				
				if (v<0.0) {
					d+="-";
					v=-v;
				}
				
			} else if (typeof(v)=="string") {
				d+="S";
				
			} else if (v instanceof Date) {
				if (!f_core.f_dateFormat) {
					f_core.Error(f_core, "Can not serialize a Date object without f_dateFormat class.");
					continue;
				}
				
				d+="D"+f_dateFormat.FormatStringDate(v);

			} else {
				f_core.Error(f_core, "Can not serialize '"+v+"'.");
				continue;
			}
			
			d+=encodeURIComponent(v);
		}
	
		return d;
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
			f_core.Assert(idx>0, "Bad format ! '"+s+"'.");
			
			var name=s.substring(0, idx);
			name=name.replace(/\+/g, ' ');
			name=decodeURIComponent(name);

			idx++; // le =
			var type=s.charAt(idx++); // le type
			
			var data=s.substring(idx);
			switch(type) {
			case 'S':
				if (data) {
					data=data.replace(/\+/g, ' ');
					data=decodeURIComponent(data);
				}
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
				f_core.Error(f_core, "Unknown type '"+type+"' !");
				data=undefined;
			}
			
			f_core.Debug(f_core, "Deserialize attribute '"+name+"' = '"+data+"'");
			obj[name]=data;
		}

		return obj;
	},
	/**
	 * @method private static
	 */
	_IeBlockSelectStart: function(evt) {
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method hidden static
	 */
	GetEvent: function(component) {
		if (!f_core.IsInternetExplorer()) {
			return null;
		}
		
		if (component.nodeType==f_core._DOCUMENT_NODE) {
			// component=document
			return component.parentWindow.event;
		}
		
		return component.ownerDocument.parentWindow.event;
	},
	/**
	 * @method private static
	 */
	_IeOnSelectStop: function() {
				//document.title="STOP bookmark ! "+window._acceptedSelection;
		//window._acceptedSelection=undefined;
		f_core.RemoveEventListener(document.body, "losecapture", f_core._IeOnSelectStop);
		f_core.RemoveEventListener(document.body, "mouseover", f_core._IeOnSelectOver);
		f_core.RemoveEventListener(document.body, "mouseout", f_core._IeOnSelectOver);
	},
	/**
	 * @method private static
	 */
	_IeOnSelectOver: function() {
		var component=window.event.srcElement;
		
		var selection=document.selection;
		var textRanges=selection.createRangeCollection();
		if (textRanges.length<1) {
			return true;
		}
		var textRange=textRanges[0];
		
		for(;component && component.nodeType==f_core._ELEMENT_NODE;component=component.parentNode) {
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
			
	},
	/**
	 * @method private static
	 */
	_IeOnSelectStart: function() {
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
		f_core.Assert(typeof(cookieName)=="string", "Bad cookieName ! ("+cookieName+")");
		
		if (!doc) {
			doc=document;
		}
		
		var cookies;
		try {
			cookies=doc.cookie;
			
		} catch (x) {
			f_core.Error(f_core, "Can not list cookies of document.", x);
		
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
		f_core.Assert(typeof(cookieName)=="string", "Bad cookieName ! ("+cookieName+")");

		if (!doc) {
			doc=document;
		}

		try {
			if (!cookieValue || cookieValue.length<1) {
				doc.cookie=cookieName+"=; expires=Thu, 01-Jan-70 00:00:01 GMT";
				return true;
			}
			
			doc.cookie=cookieName+"="+escape(cookieValue);
			return true;
			
		} catch (x) {
			f_core.Error(f_core, "Can not set cookie '"+cookieName+"', value='"+cookieValue+"'.", x);
	
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
		f_core.Assert(typeof(effectName)=="string", "The name of the effect is not a string !");
		f_core.Assert(body && body.nodeType!==undefined, "Body parameter is not a HTMLElement");
	
		var effectClass=window._classLoader.f_getClass("f_effect");
		if (!effectClass) {
			f_core.Error(f_core, "Effect class has not been loaded. (name="+effectName+")");
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
			var caret = component.ownerDocument.selection.createRange()
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
			
			f_core.Debug("f_core", "Caret position: "+i+" collapse="+isCollapsed);
			
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
			
			f_core.Debug("f_core", "Caret position: "+i+" to "+(i+j)+".");
			
			delete caret;
			return [ i, i+j ];
		}
						
		if (f_core.IsGecko()) {
			return [ component.selectionStart, component.selectionEnd ];
		}
		
		f_core.Error(f_core, "Unsupported browser for GetTextSelection() !");
	},
	/**
	 * Select a text into a TextEntry or a TextArea
	 *
	 * @method hidden static 
	 * @param HTMLElement component
	 * @param number index
	 * @param number length
	 * @return void
	 */
	SelectText: function(component, index, length) {		
		f_core.Assert(component && component.tagName, "f_core.SelectText: Invalid component !");
		f_core.Assert(typeof(index)=="number" && index>=0, "f_core.SelectText: Invalid index '"+index+"'.");
		f_core.Assert(typeof(length)=="number" && length>=0, "f_core.SelectText: Invalid length '"+length+"'.");
	
		if (f_core.IsInternetExplorer()) {
			var tr=component.createTextRange();
			tr.moveStart("character", index, length);
			tr.select();
			return;
		}
		
		if (f_core.IsGecko()) {
			component.setSelectionRange(index, index+length);
			
			return;
		}
		
		f_core.Error(f_core, "Unsupported browser for SelectText() !");
	},
	/**
	 * List all components of a document.
	 *
	 * @method hidden static 
	 * @param Document doc
	 * @return HTMLElement[] A list of HTMLElements
	 */
	ListAllHtmlComponents: function(doc) {
		f_core.Assert(doc && doc.nodeType==f_core._DOCUMENT_NODE, "f_core.ListAllHtmlComponents: Doc parameter must be a document object.");
	
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
		
			if (p.nodeType==f_core._ELEMENT_NODE) {
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
	 * @param HTMLElement component composant précédant dans l'ordre de tabulation
	 * @return HTMLElement composant suivant dans l'ordre de tabulation
	 */
	GetNextFocusableComponent: function(component) {
		// Check view elements
		var elts = f_core.ListAllHtmlComponents(component.ownerDocument);
		
		var len = elts.length;
		if (!len) {
			f_core.Debug("f_core", "No elements into document !");
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

		f_core.Debug("f_core", "utabs.length="+utabs.length+" itabs.length="+itabs.length);

		// Get next form unordered
		if (prev == undefined || prev == null || (prev<0 && isIE)) {
		
			f_core.Debug("f_core", "Search next unordered component. (prev="+prev+")");
		
			return getNextAvailable(utabs, -1);
		}
		
		// Get next from unordered starting at offset
		if (prev <= 0) {
			// Get first accessible unordered

			f_core.Debug("f_core", "Get next from unordered starting at offset. (offset="+offset+")");
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

		f_core.Debug("f_core", "Get next from ordered starting at offset. (offset="+offset+")");

		next = getNextAvailable(otabs, offset);
		if (next) {
			return next;
		}
	
		f_core.Debug("f_core", "Get next from unordered");
		
		return getNextAvailable(utabs, -1);
	},
	/**
	 *  NE FONCTIONNE pas avec IE
	 * 
	 * @method hidden static
	 */
	ComputePopupPosition: function(popup, positions) {
		var body=popup.ownerDocument.body;
		
		var bw=body.clientWidth+window.scrollX;
		var bh=body.clientHeight+window.scrollY;

		var absPos=f_core.GetAbsolutePosition(popup.offsetParent);

		if (popup.offsetWidth+positions.x+absPos.x>bw) {
			positions.x=bw-popup.offsetWidth-absPos.x;
		}
		
		if (popup.offsetHeight+positions.y+absPos.y>bh) {
			positions.y=bh-popup.offsetHeight-absPos.y;
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
		f_core.Assert(typeof(message)=="string", "Message parameter is invalid '"+message+"'.");
//		f_core.Assert(parameters instanceof Array, "parameters parameter is invalid '"+parameters+"'.");
		
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
					throw new Error("Invalid expression \""+parameters+"\".");
				}
				
				ret+=message.substring(pos, idx);
				
				var num=parseInt(message.substring(idx+1, idx2));
				if (parameters && num<parameters.length) {
					ret+=parameters[num];
				}
				
				pos=idx2+1
				continue;
			}
			
			ret+=message.substring(pos, idx2);
			
			idx=message.indexOf('\'', idx2+1);
			if (idx<0) {
				throw new Error("Invalid expression \""+parameters+"\".");
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
	 */
	UpdateAjaxParameters: function(component, url, data) {
		var forms=document.forms;
		var form=component;
		if (forms.length==1 || !component || component.nodeType==f_core._DOCUMENT_NODE) {
			form=forms[0];

		} else if (component.tagName.toUpperCase()!="FORM") {
			form=f_core.GetParentForm(component);
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
	 * @param String url 
	 * @param any data
	 * @return any Data
	 */
	SetAjaxParametersUpdater: function(callback) {
		f_core._AjaxParametersUpdater=callback;
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
		var inputs=f_core.GetElementsByTagName(form, "INPUT");
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
		return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
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
		document.body.oncontextmenu=f_core.CancelEventHandler;
	},
	/** 
	 * @method hidden static
	 * @param String text
	 * @return String
	 */
	Trim: function(text) {
		return text.replace(/^\s*|\s*$/g, "");
	},
	/** 
	 * @method hidden static
	 * @param String url
	 * @return void
	 */
	VerifyBrowserCompatibility: function(url) {
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
		if (text.length<1) {
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
		f_core.Assert(component && component.tagName, "Invalid component parameter ("+component+")");

		if (component.style.opacity!==undefined) {
			// CSS 3  on peut toujours réver !
			component.style.opacity = cur;
			return;
		}
		
		if (f_core.IsInternetExplorer()) {
			if (cur==1) {
				component.style.filter = "";
				
			} else {
				component.style.filter = "alpha(opacity="+Math.floor(cur*100)+")";
			}
			
			return;
		}
		
		if (f_core.IsGecko()) {
			component.style.MozOpacity = cur;
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
		
		var component=f_core.GetElementById(componentClientId);
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
	 * @method public static
	 * @return String
	 */
	f_getName: function() {
		return "f_core";
	},
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[class f_core]";
	}
}

/**
 * Removes the first occurrence in this list of the specified element.
 *
 * @class hidden Array
 * @method hidden f_removeElement
 * @param Object element Object to be removed.
 * @return boolean <code>true</code> if success.
 */
Array.prototype.f_removeElement=function(element) {
	for(var i=0;i<this.length;i++) {
		if (this[i]!=element) {
			continue;
		}
		
		this.splice(i, 1);
		return true;
	}
	return false;
}
/**
 * Removes the first occurrence in this list of the specified elements.
 *
 * @class hidden Array
 * @method hidden f_removeElements
 * @return number Number of removed element.
 */
Array.prototype.f_removeElements=function() {
	var cnt=0;
	for(var j=0;j<arguments.length && this.length>0;j++) {
		var element=arguments[j];
		
		for(var i=0;i<this.length;i++) {
			if (this[i]!=element) {
				continue;
			}
			
			this.splice(i, 1);
			cnt++;
			break;
		}
	}
	
	return cnt;
}
/**
 * Adds the specified element to the list if the list does not contain the element.
 *
 * @class hidden Array
 * @method hidden f_addElement
 * @param Object element element to be added.
 */
Array.prototype.f_addElement=function(element) {
	if (this.f_contains(element)) {
		return false;
	}

	this.push(element);
	return true;
}
/**
 * Adds the specified element to the list if the list does not contain the element.
 *
 * @class hidden Array
 * @method hidden f_addElements
 * @return number Number of added elements.
 */
Array.prototype.f_addElements=function() {
	var cnt=0;
	for(var j=0;j<arguments.length;j++) {
		var element=arguments[j];
		
		if (!this.f_addElement(element)) {
			continue;
		}
		
		cnt++;
	}
	
	return cnt;		
}
/**
 * Returns <tt>true</tt> if this array contains the specified element.
 *
 * @class hidden Array
 * @method hidden f_contains
 * @param any element Element whose presence in this array is to be tested.
 * @return boolean <tt>true</tt> if this collection contains the specified element
 */
Array.prototype.f_contains=function(element) {
	for(var i=0;i<this.length;i++) {
		if (this[i]!=element) {
			continue;
		}
		
		return true;
	}
	
	return false;
}


f_core._InitLibrary(window);


