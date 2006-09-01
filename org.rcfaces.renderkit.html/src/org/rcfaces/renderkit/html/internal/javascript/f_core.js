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
 * @author Joel Merlin + Olivier Oeuillot
 * @version $Revision$
 */
var f_core = {

	/**
	 * @field hidden static final string
	 */
	SERIALIZED_DATA: 	"VFC_SERIAL",

	/**
	 * @field private static final string
	 */
	_COMPONENT:	"VFC_COMPONENT",

	/**
	 * @field private static final string
	 */
	_EVENT: 	"VFC_EVENT",

	/**
	 * @field private static final string
	 */
	_VALUE:		"VFC_VALUE",

	/**
	 * @field private static final string
	 */
	_ITEM:		"VFC_ITEM",

	/**
	 * @field private static final string
	 */
	_DETAIL:	"VFC_DETAIL",
		
	/**
	 * @field hidden static final string
	 */
	FIREFOX_1_0: "firefox.1.0",
		
	/**
	 * @field hidden static final string
	 */
	FIREFOX_1_5: "firefox.1.5",

	/**
	 * @field hidden static final string
	 */
	FIREFOX_2_0: "firefox.2.0",

	/**
	 * @field hidden static final string
	 */
	INTERNET_EXPLORER_6: "iexplorer.6",

	/**
	 * @field hidden static final string
	 */
	INTERNET_EXPLORER_7: "iexplorer.7",

	/**
	 * @field private static final string
	 */
	_UNKNOWN_BROWER: "unknown",

	/**
	 * Numero du bouton qui déclanche les popups. (Cela dépend de l'OS !)
	 *
	 * @field private static final number
	 */
	_POPUP_BUTTON: 2,

	/**
	 * @field hidden static final string
	 */
	JAVASCRIPT_VOID: "javascript:void(0)",

	/**
	 * @field private static final RegExp
	 */
	_BLOCK_TAGS: new RegExp(
		"^(ADDRESS|APPLET|BLOCKQUOTE|BODY|CAPTION|CENTER|COL|COLGROUP|DD|DIR|DIV|" +
		"DL|DT|FIELDSET|FORM|FRAME|FRAMESET|H1|H2|H3|H4|H5|H6|HR|IFRAME|LI|MENU|" +
		"NOSCRIPT|NOFRAMES|OBJECT|OL|P|PRE|TABLE|TBODY|TD|TFOOT|TH|THEAD|TR|UL){1}$"
	),

	/**
	 * @field private static final RegExp
	 */
	_FOCUSABLE_TAGS: new RegExp(
		"^(A|AREA|BUTTON|IFRAME|INPUT|OBJECT|SELECT|TEXTAREA){1}$"
	),

	/**
	 * @field hidden static boolean
	 */
	Debug_Mode:	false,

	/**
	 * @field private static
	 */
	_AjaxParametersUpdater: undefined,
	
	/**
	 * Throws a message if the expression is true.
	 *
	 * @method public static final
	 * @param boolean expr Expression.
	 * @param string message The message.
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
	 * @method private static final
	 */
	_AddLog: function (level, name, message, exception, win) {
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
			if (level===0) {
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
		case 1:
			fct=log.f_warn;
			break;

		case 2:
			fct=log.f_info;
			break;
			
		case 3:
			fct=log.f_debug;
			break;
			
		default:
			fct=log.f_error;
		}
		
		f_core.Assert(typeof(fct)=="function", "f_core._AddLog: Log function is invalid '"+fct+"'.");
		if (!fct) {
			return false;
		}
		
		return fct.call(log, message, exception, win);
	},
	/**
	 * @method private static final
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
	 * @method public static final
	 * @param string name Log name.
	 * @param string message The message.
	 * @param Error exception An exception if any.	 
	 * @return void
	 */
	Debug: function(name, message, exception, win) {
		f_core._AddLog(3, name, message, exception, win);
	},
    /**
     * <p>Is debug logging currently enabled ?</p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug. </p>
	 *
	 * @method public static final
	 * @param string name Log name.
	 * @return boolean <code>true</code> if debug logging  is enabled.
	 */
	IsDebugEnabled: function(name) {
		f_core.Assert(typeof(name)=="string", "f_core.IsDebugEnabled: name parameter is invalid. ('"+name+"')");
		if (!window.f_log) {
			return (f_core.Debug_Mode);
		}
		
		return f_log.GetLog(name).f_isDebugEnabled();
	},
	/**
	 * @method public static final
	 * @param string name Log name.
	 * @param string message The message.
	 * @param Error exception An exception if any.	 
	 * @return void
	 */
	Info: function(name, message, exception, win) {
		f_core._AddLog(2, name, message, exception, win);
	},
	/**
     * <p> Is info logging currently enabled ? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
 	 *
	 * @method public static final
	 * @param string name Log name.
	 * @return boolean <code>true</code> if info logging  is enabled.
	 */
	IsInfoEnabled: function(name) {
		f_core.Assert(typeof(name)=="string", "f_core.IsInfoEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isInfoEnabled();
	},
	/**
	/**
	 * @method public static final
	 * @param string name Log name.
	 * @param string message The message.
	 * @param Error exception An exception if any.	 
	 * @return void
	 */
	Warn: function(name, message, exception, win) {
		f_core._AddLog(1, name, message, exception, win);
	},
	/**
     * <p> Is warning logging currently enabled ? </p>
     *
     * <p> Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info. </p>
 	 *
	 * @method public static final
	 * @param string name Log name.
	 * @return boolean <code>true</code> if info logging  is enabled.
	 */
	IsWarnEnabled: function(name) {
		f_core.Assert(typeof(name)=="string", "f_core.IsWarnEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isWarnEnabled();
	},
	/**
	 * @method public static final
	 * @param string name Log name.
	 * @param string message The message.
	 * @param Error exception An exception if any.	 
	 * @return void
	 */
	Error: function(name, message, exception, win) {
	
		f_core.Profile("f_core.Error("+name+") "+message+"\n"+exception);
	
		if (f_core.Debug_Mode) {
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

		if (!f_core._AddLog(0, name, message, exception, win)) {
			// Rien n'a été rapporté, on passe à la console !
			
			if (!exception) {
				exception=new Error(message);
			}
			throw exception;
		}

		if (f_core.Debug_Mode) {
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
	 * @method public static final
	 * @param string name Log name.
	 * @return boolean <code>true</code> if error logging  is enabled.
	 */
	IsErrorEnabled: function(name) {
		f_core.Assert(typeof(name)=="string", "f_core.IsErrorEnabled: name parameter is invalid. ('"+name+"')");
		
		return f_log.GetLog(name).f_isErrorEnabled();
	},
	/**
	 * @method hidden static final
	 * @return void
	 */
	SetDebugMode: function(debugMode) {
		if (debugMode===undefined) {
			debugMode=true;
		}
		f_core.Debug_Mode=debugMode;
	},
	/**
	 * @method hidden static final
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
	 * @method hidden static final
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
			
			f_core.Profile("f_core.loading", initDate);
			f_core.Profile("f_core.initializing");
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
	 * @method hidden static final
	 * @param HTMLElement component
	 * @param string name Event name
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
	 * @method hidden static final
	 * @param HTMLElement component
	 * @param string name Event name
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
	 * @method hidden static final
	 * @param string name of profile point.
	 * @param optional any Date of profile point. (Can be 'Date' or numer)
	 * @return void
	 */
	Profile: function(name, date) {
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
	
		var diff=date-window._f_core_initLibraryDate;
		if (diff<1) {
			f_core.Debug("f_core.profile", "Profiler: "+name+"  "+date);
			return;
		}

		f_core.Debug("f_core.profile", "Profiler: "+name+"  +"+diff+"ms.");
	},
	_OnInit: function() {
		var now=new Date();
		f_core.Profile("f_core.onInit.enter", now);
		try {		
			f_core._FlushLogs();	
	
			var window=this;
		
			f_core.Info("f_core", "Install library (onload) on "+now);
			
			if (f_core.Debug_Mode) {
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
							
			// Hook the forms
			var forms = window.document.forms;
			for (var i=0; i<forms.length; i++) {
				var f = forms[i];
	
				f_core._InitializeForm(f);
			}
			
			// Les objets non encore initializés		
			window._classLoader._initializeObjects();
	
			f_core.Profile("f_core.onInit.objects");

			// Initialize packages here
			window._classLoader._onDocumentComplete();
	
		} finally {
			f_core.Profile("f_core.onInit.exit");
		}
	},
	_InitializeForm: function(f) {
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
	 * @method private static final
	 */
	_FormFindComponent: function() {
		// Nous sommes dans le scope d'un formulaire !
		
		return fa_namingContainer.FindComponents(this, arguments);
	},
	/**
	 * @method private static final
	 */
	_OnExit: function() {
		var win=this;
		
		win._f_exiting=true;
		try {		
			var document=win.document;
	
			f_core.Profile("f_core.onExit.enter");
			try {
				
				f_core.RemoveEventListener(win, "load", f_core._OnInit);
				f_core.RemoveEventListener(win, "unload", f_core._OnExit);
				
				if (f_core.IsInternetExplorer()) {
					f_core.RemoveEventListener(document, "selectstart", f_core._IeOnSelectStart);
				}
		
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
				f_core.Profile("f_core.onExit.exit");
			}
		} finally {
			win._f_exiting=undefined;
		}
	},
	/**
	 * @method final static hidden
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
		
		var inputs=form.getElementsByTagName("INPUT");
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
	 * @method static hidden
	 */
	_GetParentForm: function(elt) {
		f_core.Assert(elt.ownerDocument, "f_core._GetParentForm: Invalid parameter element ("+elt+")");
	
		// Optimisation s'il n'y a qu'une seule form !
		var forms=elt.ownerDocument.forms;
		if (forms.length==1) {
			return forms[0];
		}
	
		for(;elt;elt=elt.parentNode) {
			var tagName=elt.tagName;
			if (!tagName) {
				continue;
			}
			
			if (tagName.toUpperCase()!="FORM") {
				continue;
			}
			
			return elt;
		}

		return null;
	},
	/**
	 * @method static hidden
	 */
	GetParentComponent: function(comp) {
		for(var comp=comp.f_getParent();comp;comp=comp.parentNode) {
			if (!f_class.IsObjectInitialized(comp)) {
				continue;
			}
			
			return comp;
		}
		
		return null;
	},
	/**
	 * @method static hidden
	 */
	SetTextNode: function(component, text) {
		f_core.Assert(component && component.nodeType==1, "f_core.SetTextNode: Invalid component ! ("+component+")");
		var children=component.childNodes;

		for(var i=0;i<children.length;i++) {
			var child=children[i];
			if (child.nodeType!=3) {
				continue;
			}
			
			if (text!==undefined) {
				child.data=text;
				text=null;
				continue;
			}
			
			component.removeChild(child);
		}
		
		if (text) {
			component.appendChild(component.ownerDocument.createTextNode(text));
		}
	},
	/**
	 * @method static hidden
	 */
	GetTextNode: function(component) {
		f_core.Assert(component && component.nodeType==1, "f_core.GetTextNode: Invalid component ! ("+component+")");

		var children=component.childNodes;

		var text="";
		for(var i=0;i<children.length;i++) {
			var child=children[i];
			if (child.nodeType!=3) {
				continue;
			}
			text+=child.data;
		}
		
		return text;
	},
	_OnReset: function(evt) {
		if (!evt) {
			evt = window.event;
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
			if (win.f_event.GetEventLocked(true, win)) {
				return false;
			}
		}
		
		f_core._CallFormResetListeners(form, evt);
		
		// Appel de la validation ?
		if (f_env.GetCheckValidation()) {
			f_core._CallFormCheckListeners(form);
		}
	},
	_OnSubmit: function(evt) {
		f_core.Profile("f_core.SubmitEvent");

		if (!evt) {
			evt = window.event;
		}

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
			if (win.f_event.GetEventLocked(true, win)) {
				return false;
			}
		}

		if (!form._initialized) {
			//f_core.Assert(form._initialized, "Not initialized form '"+form.id+"'.");

			// Cas ou l'utilisateur va plus vite que la musique ! (avant le onload de la page)
			
			if (f_env.IsLockSubmitUntilPageCompleteEnable()) {
				return false;
			}
			
			// On essaye d'initialiser les objets qui ne sont pas encore initializés
			window._classLoader._initializeObjects();
			
			// XXX Il faut peut etre attendre QUE TOUTS LES OBJETS soient initialisés ?
			
			// On initialize la form !				
			f_core._InitializeForm(form); 
		}
		
		var immediate;
		var component=win.f_event.GetComponent();

		f_core.Debug("f_core", "Component which performs submit event is '"+((component)?component.id:"**UNKNOWN**")+"'");
		if (component && component.f_isImmediate) {
			immediate=component.f_isImmediate();

			f_core.Debug("f_core", "Test immediate property of '"+component.id+"' = "+immediate);
		}
		
		if (immediate!==true && f_env.GetCheckValidation()) {
			if (!f_core._CallFormCheckListeners(form)) {
				return false;
			}
			
			f_core.Profile("f_core.SubmitEvent.checkListeners");
		}
		
		var classLoader=win._classLoader;
		if (classLoader) {
			classLoader.serialize(form);
			
			f_core.Profile("f_core.SubmitEvent.serialized");
		}
			
		return true;
	},
	_Submit: function(form, elt, event, url, target, createWindow, closeWindow, nomodal) {
		f_core.Profile("f_core.submit.enter("+url+")");

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
				event=f_event.GetEvent();
	
			} else if (!event) {
				event=f_event.GetEvent();
			}
	
			// Get element from event info if given
			if (!elt && event) {
				elt = event.f_getComponent();
			}
	
			// Get form form element parent if not given
			if (!form && elt) {
				form = f_core._GetParentForm(elt);
			}
			if (!form) {
				var ex=new Error("Can not find form !");
				f_core.Error(f_core, "Can not find form !", ex);
				throw ex;
				//return false;
			}

			var document=form.ownerDocument;
			var win=f_core.GetWindow(document);
	
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

			f_core.Profile("f_core.submit.onSubmit.called");
			
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
				if (!form._old_target) {
					form._old_target = form.target;
				}
				
				form.target = target;
				
			} else if (form._old_target) {
				form.target=form._old_target;
			}
	
			win.f_event.SetEventLocked(win, true);
			var unlockEvents=false;
			try {
				if (createWindow) {
					if (!createWindow.target) {
						createWindow.target=target;
					}
					var newWindow=f_core.OpenWindow(win, createWindow);
					
					if (newWindow) {
						try {
							newWindow.focus();
							
							if (newWindow.GetAttention) {
								newWindow.GetAttention();
							}
						} catch (x) {
							f_core.Error(f_core, "Can not focus window "+newWindow, x);
						}
					}
				}
	
				f_core.Profile("f_core.submit.preSubmit");
					
				// Don't replace the current handler form.submit() and call the previous
				form._oldSubmit();
	
				f_core.Profile("f_core.submit.postSubmit");
		
				if (closeWindow) {
					win._f_closeWindow=true;
					return true;
				}
				
				if (createWindow) {
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
					
					win.f_event.SetEventLocked(win, false);
				}
			}
	
			// IE Progress bar bug only
			if (document.readyState) {
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
			f_core.Profile("f_core.submit.exit("+url+")");
		}
	},
	OpenWindow: function(window, parameters) {
		var url=parameters.url;
		if (!url) {
			url = "about:blank";
		}
		var target=parameters.target;
		if (!target) {
			target = "";
		}
		var features = new Object;
		features.left=parameters.x;
		features.top=parameters.y;
		features.width=parameters.width;
		features.height=parameters.height;

		if (parameters.dialog) {
			features.scrollbars="no";
			features.location="no";
			features.toolbar="no";
			features.directories="no";
			features.status="no";
			features.menubar="no";
			features.copyhistory="no";
		}

		var deco=parameters.extra;
		if (deco) {
			for (var name in deco) {
				features[name]=deco[name];
			}
		}
		
		var s="";
		for(var name in features) {
			if (s.length>0) {
				s+=",";
			}
			s+=name+"="+features[name];
		}
		
		f_core.Debug("f_core", "Open window, url="+url+" target="+target+" features="+s);
		
		try {
			var newWindow=window.open(url, target, s);
		
			return newWindow;

		} catch (x) {
			var s=f_env.GetOpenWindowErrorMessage();
			if (s) {
				alert(s);
				return null;
			}
			
			f_core.Error(f_core, "Can not open window url='"+url+"' target='"+target+"' features='"+s+"'.", x);
			
			throw x;
		}
	},
	/**
	 * @method private static final
	 */
	_CallFormCheckListeners: function(form) {
		var checkListeners=form._checkListeners;
		if (!checkListeners || checkListeners.length<1) {
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
		
		var ret=true;
		try {
			if (cfp) {
				for(var i=0;i<cfp.length;i++) {
					var checkPre=cfp[i];
					
					checkPre.f_performCheckPre(form);
				}
			}
			
			if (ces) {
				for(var i=0;i<ces.length && ret;i++) {
					var checkEvent=ces[i];
					
					if (checkEvent.f_performCheckValue(form)===false) {
						ret=false;
					}
				}
			}
						
		} finally {		
			if (cfs) {
				for(var i=0;i<cfs.length;i++) {
					var checkPost=cfs[i];
					
					checkPost.f_performCheckPost(ret, form);
				}
			}
		}
				
		return ret;
	},
	/**
	 * @method private static final
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

		var form=this._GetParentForm(component);
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

		var form=this._GetParentForm(component);
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

		var form=this._GetParentForm(component);
		f_core.Assert(form, "Can not get form of component '"+component.id+"'.");
		
		var resetListeners=form._resetListeners;
		if (!resetListeners) {
			return false;
		}	
		
		return resetListeners.f_removeElement(component);
	},
	/**
	 * @method public static final
	 * @param optional string url
	 * @param optional string dest Window name.
	 * @param optional HTMLElement elt
	 * @param optional f_event event 
	 * @param optional boolean createWindow
	 * @param optional boolean closeWindow
	 * @param optional boolean nomodal
	 * @return boolean <code>true</code> if success.
	 */
	Submit: function(url, dest, elt, event, createWindow, closeWindow, nomodal) {
		return f_core._Submit(null, elt, event, url, dest, createWindow, closeWindow, nomodal);
	},
	/**
	 * Submit the page, and open a new window to show the response.
	 * 
	 * @method public static final
	 * @param optional string dest Window name.
	 * @param optional boolean createWindow
	 * @param optional boolean nomodal
	 * @param optional f_event event 
	 * @return boolean <code>true</code> if success.
	 */
	SubmitOpenWindow: function(dest, createWindow, nomodal, event) {
		return f_core._Submit(null, null, event, null, dest, createWindow, null, nomodal);
	},
	/**
	 * Submit the page, and close the window.
	 *
	 * @method public static final
	 * @param optional f_event event 
	 * @return boolean <code>true</code> if success.
	 */
	SubmitCloseWindow: function(event) {
		return f_core._Submit(null, null, event, null, null, null, true);
	},
	/**
	 * Returns the window associated to the specified element.
	 *
	 * @method hidden static final
	 * @param HTMLElement elt HTML element.
	 * @return Window Window associated to the element.
	 */
	GetWindow: function(elt) {		
		// Cas de IE, si elt est déjà un Document !
		if (elt.window) {
			return elt.window;
		}
		
		var doc;
		if (elt.nodeType && elt.nodeType==9) { // 9=document
			doc=elt;
	
		} else {
			doc=elt.ownerDocument;
		}
		
		f_core.Assert(doc, "Can not find window of component '"+elt+"'.");
		
		if (doc.defaultView) { // DOM Level 2
			return doc.defaultView;
		}
		
		return doc.parentWindow;
	},
	_InstanceOf: function(elt,claz,css) {
		if (css) {
			return (elt.className && elt.className==claz)? elt:null;
		}
		if (elt._kclass && elt._kclass._name==claz) {
			return elt;
		}
		if (elt.nodeType==1 && f_core.GetAttribute(elt, "v:class")==claz) {
			return f_core.GetWindow(elt)._classLoader._init(elt);
		}
		return null;
	},
	/**
	 * Find a child with a specified css class.
	 *
	 * @method public static final
	 * @param HTMLElement elt Start node.
	 * @param string claz Css class name. 
	 * @return HTMLElement
	 */
	GetChildByCssClass: function(elt,claz) {
		return f_core.GetChildByClass(elt,claz,true);
	},
	/**
	 * Find a child with a specified class.
	 *
	 * @method public static final
	 * @param HTMLElement elt Start node.
	 * @param string claz Class name.
	 * @param boolean css Search Css class.
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
	 * @method public static final
	 * @param string id1 Identifier
	 * @param optional string id2 Identifier
	 * @return HTMLElement
	 */
	FindComponent: function(id1, id2) {
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
	 * @method public static final
	 * @param string id Identifier
	 * @param Document doc Document.
	 * @param hidden boolean noCompleteComponent Dont complete component !
	 * @return HTMLElement
	 */
	GetElementById: function(id, doc, noCompleteComponent) {
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
			if (typeof(obj._completeComponent)=="function") {
				obj._completeComponent();
			}
		}
		return obj;
	},
	/** 
	 * @method hidden static final
	 * @param Element 
	 */
	GetAttribute: function(object, attributeName) {
		f_core.Assert(object && object.nodeType==1, "Object parameter is node a valid node ! ("+object+")");
		f_core.Assert(typeof(attributeName)=="string", "attributeName parameter is invalid.");

		try {
			return object.getAttribute(attributeName);
			
		} catch (x) {
			/* ignore, in IE6 calling on a table results in an exception */
			return null;
		}
	},	
	/** 
	 * Returns true if component (and its ancestors) is visible.
	 *
	 * @method hidden static final
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
	 * @method hidden static final
	 * @param HTMLElement obj
	 * @return Object 
	 */
	GetAbsolutePos: function(obj) {
		var curtop = 0;
		var curleft= 0;
		if (obj.offsetParent) {
			for (;obj.offsetParent;obj = obj.offsetParent) {
				curtop += obj.offsetTop;
				curleft += obj.offsetLeft;
			}
		} else {
			if (obj.x) {
				curleft+=obj.x;
			}
			if (obj.y) {
				curtop += obj.y;
			}
		}
		
		return { x: curleft, y: curtop };
	},
	/**
	 * @method hidden static final
	 * @param optional string version
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
	 * @method hidden static final
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
	 * @method hidden static final
	 * @param optional string version
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
	 * @method private static final
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
	 * @method hidden static final
	 */
	RemoveElement: function(list, index) {
		if (list==null || list.length<1) {
			return false;
		}
		for(var i=0;i<list.length;i++) {
			if (list[i]!=index) {
				continue;
			}
			
			list.splice(i, 1);
			return true;
		}
		return false;
	},
	/**
	 * @method hidden static final
	 */
	AddElement: function(list, index) {
		for(var i=0;i<list.length;i++) {
			if (list[i]!=index) {
				continue;
			}
			
			return false;
		}
	
		list.push(index);
		return true;
	},
	/**
	 * @method hidden static final
	 */
	CancelEventHandler: function(evt) {
		if (f_event.GetEventLocked(false, window)) {
			return false;
		}
	
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method hidden static final
	 */
	CancelEvent: function(evt) {
		if (!evt) {
			evt=window.event;
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
	 * @method hidden static final
	 */
	CancelEventHandlerTrue: function(evt) {
		if (f_event.GetEventLocked(false, window)) {
			return false;
		}
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * Returns the size of the View.
	 *
	 * @method public static final
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
	 * @method public static final
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
	 * @method hidden static final
	 * @param Event event
	 * @param optional Document doc
	 * @return number[]
	 */
	GetEventPosition: function(event, doc) {
		if (!doc) {
			doc=document;
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
	 * @method hidden static final
	 * @param HTMLElement component
	 * @param f_event event
	 * @return boolean
	 */
	IsComponentInside: function(component, event) {			
		var p=f_core.GetAbsolutePos(component);
	
		if (event.clientX<p.x || 
			event.clientY<p.y || 
			event.clientX>p.x+component.offsetWidth || 
			event.clientY>p.y+component.offsetHeight) {
			
			return false;
		}
		
		return true;
	},
	/**
	 * @method hidden static final
	 */
	VerifyProperties: function(object) {
		if (!f_core.Debug_Mode) {
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
	 * @method public static final
	 * @param HTMLElement component
	 * @return boolean <code>true</code> is success !
	 */
	SetFocus: function(component) {
		f_core.Assert(component, "Component is NULL");
		f_core.Assert(component.tagName, "Parameter is not a component.");

		if (!f_core.ForceComponentVisibility(component)) {
			f_core.Info("f_core", "Can not set focus to a not visible component");
			return;
		}

		if (component.f_setFocus) {
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
	 * @method hidden static final
	 */
	GetFirstElementByTagName: function(parent, tagName, assertIfNotFound) {
		f_core.Assert(parent && parent.nodeType, "Parent '"+parent+"' is not a Dom node !");
		
		var components=parent.getElementsByTagName(tagName);
		if (!components || components.length<1) {
			if (assertIfNotFound) {
				f_core.Assert(false, "Component '"+tagName+"' not found !");
			}
			return null;
		}
		
		return components[0];
	},
	/**
	 * @method hidden static final
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
	 * @method hidden
	 */
	GetCurrentStyleProperty: function(component, attributeId) {
		if (f_core.IsInternetExplorer()) {
			return component.currentStyle[attributeId];
		}
		
		if (f_core.IsGecko()) {	
			return component.ownerDocument.defaultView.getComputedStyle(component, '').getPropertyValue(attributeId);
		}
		
		f_core.Assert(false, "Browser not supported !");
	},
	/**
	 * @method hidden
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
	 * @method hidden
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

			idx++;
			var type=s.charAt(idx++);
			
			var data=s.substring(idx);
			if (data.length>0) {
				switch(type) {
				case 'S':
					data=data.replace(/\+/g, ' ');
					data=decodeURIComponent(data);
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
					data=parseFloat(type+data);
					break;
	
					
				default:
					f_core.Error(f_core, "Unknown type '"+type+"' !");
					data=undefined;
				}
			}
						
			obj[name]=data;
		}

		return obj;
	},
	/**
	 * @method hidden static final
	 */
	IeBlockSelectStart: function(evt) {
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method hidden static final
	 */
	IeGetEvent: function(component) {		
		return component.ownerDocument.parentWindow.event;
	},
	_IeOnSelectStop: function() {
				//document.title="STOP bookmark ! "+window._acceptedSelection;
		//window._acceptedSelection=undefined;
		f_core.RemoveEventListener(document.body, "losecapture", f_core._IeOnSelectStop);
		f_core.RemoveEventListener(document.body, "mouseover", f_core._IeOnSelectOver);
		f_core.RemoveEventListener(document.body, "mouseout", f_core._IeOnSelectOver);
	},
	_IeOnSelectOver: function() {
		var component=window.event.srcElement;
		
		var selection=document.selection;
		var textRanges=selection.createRangeCollection();
		if (textRanges.length<1) {
			return true;
		}
		var textRange=textRanges[0];
		
		for(;component && component.nodeType==1;component=component.parentNode) {
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
	 * @method public static final
	 * @param string cookieName
	 * @param optional HTMLDocument doc Html document.
	 * @return string value associated to the cookie, or <code>null</code>.
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
	 * @method public static final
	 * @param string cookieName
	 * @param optional string cookieValue Value to associate with cookie, or <code>null</code> to delete cookie !
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
	
			if (f_core.Debug_Mode) {
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
		if (evt.button) {
			return evt.button;
		}
		if (evt.which) {
			return evt.which;
		}
		
		return 1;
	},
	/**
	 * Returns an effect specified by its name.
	 *
	 * @method hidden static 
	 * @param string effectName Name of effect
	 * @param HTMLElement body Component which be applied the effect.
	 * @param optional Function callback Callback which be called when the effect changes properties.
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
	 * @method hidden static final
	 * @param Document doc
	 * @return HTMLElement[] A list of HTMLElements
	 */
	ListAllHtmlComponents: function(doc) {
		f_core.Assert(doc && doc.nodeType==9, "f_core.ListAllHtmlComponents: Doc parameter must be a document object.");
	
		// On peut toujours essayer ;-)
		var elts=doc.all;
		if (elts!==undefined) {
			return elts;
		}

		// Check view elements
		elts=new Array;

		function getAllElements(elements, node) {
			if (node.nodeType==1) {
				elements.push(node);
			}
			if (node.firstChild) {
				getAllElements(elements, node.firstChild);
			}
			if (node.nextSibling) {
				getAllElements(elements, node.nextSibling);
			}
		}

		getAllElements(elts, doc.documentElement);
		
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
	 * @method hidden static final
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
	 * @method hidden static final 
	 */
	ComputePopupPosition: function(popup, positions) {
		var body=popup.ownerDocument.body;
		var bw=body.clientWidth+window.scrollX;
		var bh=body.clientHeight+window.scrollY;

		if (popup.offsetWidth+positions.x>bw) {
			positions.x=bw-popup.offsetWidth;
		}
		
		if (popup.offsetHeight+positions.y>bh) {
			positions.y=bh-popup.offsetHeight;
		}
	},
	/**
	 * @method hidden static final
	 */
	FormatMessage: function(message, parameters) {
		f_core.Assert(typeof(message)=="string", "Message parameter is invalid '"+message+"'.");
		f_core.Assert(parameters instanceof Array, "parameters parameter is invalid '"+parameters+"'.");
		
		for(var i=0;i<parameters.length;i++) {
			var mk="{"+i+"}";
			
			var idx=message.indexOf(mk);
			if (idx<0) {
				continue;
			}
			
			message=message.substring(0, idx)+parameters[i]+message.substring(idx+mk.length);
		}
		
		return message;
	},
	/**
	 * @method hidden static final
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
	 * @method hidden static final
	 * @param String url 
	 * @param any data
	 * @return any Data
	 */
	UpdateAjaxParameters:function(component, url, data) {
		var forms=document.forms;
		var form=component;
		if (forms.length==1 || !component || component.nodeType==9) {
			form=forms[0];

		} else if (component.tagName!="FORM") {
			form=f_core._GetParentForm(component);
		}
		
		var ajaxParametersUpdater=f_core._AjaxParametersUpdater;
		if (ajaxParametersUpdater) {
			return ajaxParametersUpdater.call(this, form, component, url, data);
		}
		
		f_core.Debug("f_core", "UpdateAjaxParameters: Use default faces hidden input search !");
		
		return f_core.AddFacesHiddenInputParameters(form, function(input) {
			return !f_core.GetAttribute(input, "v:class");
		}, data);
	},
	/**
	 * @method hidden static final
	 * @param String url 
	 * @param any data
	 * @return any Data
	 */
	SetAjaxParametersUpdater: function(callback) {
		f_core._AjaxParametersUpdater=callback;
	},
	/**
	 * @method hidden static final
	 * @param HtmlFormElement form
	 * @param String pattern
	 * @param any data
	 * @param boolean onlyOne
	 * @return any
	 */
	AddFacesHiddenInputParameters: function(form, acceptFunction, data, onlyOne) {
		var inputs=form.getElementsByTagName("INPUT");
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
	 * @method hidden static final
	 * @param string text Text to encode to HTML form
	 * @return string Html form of text.
	 */
	EncodeHtml: function(text) {
		return text.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	},
	/** 
	 * @method hidden static final
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
		
		for(;index<length;index++) {
			dest.push(args[index]);
		}
		
		return dest;
	},
	
	/**
	 * @method public static final
	 * @return string
	 */
	f_getName: function() {
		return "f_core";
	},
	/**
	 * @method public string
	 * @return string
	 */
	toString: function() {
		return "[f_core]";
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


/** 
 * @class hidden String
 */
String.prototype._upperCaseFirstChar=function() {
	if (this.length<1) {
		return "";
	}
	
	return this.charAt(0).toUpperCase()+this.substring(1);
}

f_core._InitLibrary(window);


