/*
 * $Id$
 */

/**
 *
 * @class public f_key extends Object
 * @author Olivier Oeuillot
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
var __statics = {

	/** @field public static final number */
	VK_CANCEL         : 0x03,
	
	/** @field public static final number */
	VK_HELP           : 0x06,
	
	/** @field public static final number */
	VK_BACK_SPACE     : 0x08,
	
	/** @field public static final number */
	VK_TAB            : 0x09,
	
	/** @field public static final number */
	VK_CLEAR          : 0x0C,

	/** @field public static final number */
	VK_RETURN         : 0x0D,

	/** @field public static final number */
	VK_ENTER          : 0x0E,

	/** @field public static final number */
	VK_SHIFT          : 0x10,

	/** @field public static final number */
	VK_CONTROL        : 0x11,
	/** @field public static final number */
	VK_ALT            : 0x12,
	/** @field public static final number */
	VK_PAUSE          : 0x13,
	/** @field public static final number */
	VK_CAPS_LOCK      : 0x14,
	/** @field public static final number */
	VK_ESCAPE         : 0x1B,
	/** @field public static final number */
	VK_SPACE          : 0x20,
	/** @field public static final number */
	VK_PAGE_UP        : 0x21,
	/** @field public static final number */
	VK_PAGE_DOWN      : 0x22,
	/** @field public static final number */
	VK_END            : 0x23,
	/** @field public static final number */
	VK_HOME           : 0x24,
	/** @field public static final number */
	VK_LEFT           : 0x25,
	/** @field public static final number */
	VK_UP             : 0x26,
	/** @field public static final number */
	VK_RIGHT          : 0x27,
	/** @field public static final number */
	VK_DOWN           : 0x28,
	/** @field public static final number */
	VK_PRINTSCREEN    : 0x2C,
	/** @field public static final number */
	VK_INSERT         : 0x2D,
	/** @field public static final number */
	VK_DELETE         : 0x2E,

	/** @field public static final number */
	VK_SEMICOLON      : 0x3B,
	/** @field public static final number */
	VK_EQUALS         : 0x3D,
	/** @field public static final number */
	VK_CONTEXTMENU    : 0x5D,
	/** @field public static final number */
	VK_NUMPAD0        : 0x60,
	/** @field public static final number */
	VK_NUMPAD1        : 0x61,
	/** @field public static final number */
	VK_NUMPAD2        : 0x62,
	/** @field public static final number */
	VK_NUMPAD3        : 0x63,
	/** @field public static final number */
	VK_NUMPAD4        : 0x64,
	/** @field public static final number */
	VK_NUMPAD5        : 0x65,
	/** @field public static final number */
	VK_NUMPAD6        : 0x66,
	/** @field public static final number */
	VK_NUMPAD7        : 0x67,
	/** @field public static final number */
	VK_NUMPAD8        : 0x68,
	/** @field public static final number */
	VK_NUMPAD9        : 0x69,
	/** @field public static final number */
	VK_MULTIPLY       : 0x6A,
	/** @field public static final number */
	VK_ADD            : 0x6B,
	/** @field public static final number */
	VK_SEPARATOR      : 0x6C,
	/** @field public static final number */
	VK_SUBTRACT       : 0x6D,
	/** @field public static final number */
	VK_DECIMAL        : 0x6E,
	/** @field public static final number */
	VK_DIVIDE         : 0x6F,
	/** @field public static final number */
	VK_F1             : 0x70,
	/** @field public static final number */
	VK_F2             : 0x71,
	/** @field public static final number */
	VK_F3             : 0x72,
	/** @field public static final number */
	VK_F4             : 0x73,
	/** @field public static final number */
	VK_F5             : 0x74,
	/** @field public static final number */
	VK_F6             : 0x75,
	/** @field public static final number */
	VK_F7             : 0x76,
	/** @field public static final number */
	VK_F8             : 0x77,
	/** @field public static final number */
	VK_F9             : 0x78,
	/** @field public static final number */
	VK_F10            : 0x79,
	/** @field public static final number */
	VK_F11            : 0x7A,
	/** @field public static final number */
	VK_F12            : 0x7B,
	/** @field public static final number 
	VK_F13            : 0x7C, */
	/** @field public static final number 
	VK_F14            : 0x7D, */
	/** @field public static final number 
	VK_F15            : 0x7E, */
	/** @field public static final number 
	VK_F16            : 0x7F, */
	/** @field public static final number 
	VK_F17            : 0x80, */
	/** @field public static final number 
	VK_F18            : 0x81, */
	/** @field public static final number 
	VK_F19            : 0x82, */
	/** @field public static final number 
	VK_F20            : 0x83, */
	/** @field public static final number 
	VK_F21            : 0x84, */
	/** @field public static final number
	VK_F22            : 0x85, */
	/** @field public static final number
	VK_F23            : 0x86, */
	/** @field public static final number 
	VK_F24            : 0x87, */

	/** @field public static final number */
	VK_NUM_LOCK       : 0x90,
	/** @field public static final number */
	VK_SCROLL_LOCK    : 0x91,

	/** @field public static final number */
	VK_COMMA          : 0xBC,
	/** @field public static final number */
	VK_PERIOD         : 0xBE,
	/** @field public static final number */
	VK_SLASH          : 0xBF,
	/** @field public static final number */
	VK_BACK_QUOTE     : 0xC0,
	/** @field public static final number */
	VK_OPEN_BRACKET   : 0xDB,
	/** @field public static final number */
	VK_BACK_SLASH     : 0xDC,
	/** @field public static final number */
	VK_CLOSE_BRACKET  : 0xDD,
	/** @field public static final number */
	VK_QUOTE          : 0xDE,


	/** @field public static final number */
	KF_SHIFT		  : 0x01,

	/** @field public static final number */
	KF_CONTROL		  : 0x02,

	/** @field public static final number */
	KF_ALT		  	  : 0x04,
	
	/** @field public static final number */
	KF_META           : 0x08,

	/**
	 * @field private static final String
	 */
	_MAIN_SCOPE_NAME: "--MainScope--",

	/**
	 * @field private static
	 */
	_Scopes			: undefined,

	/**
	 * @field private static
	 */
	_CurrentScopes	: undefined,


	/**
	 * @method public static
	 * @param number code Key code.
	 * @return boolean
	 */
	IsPrintable: function(code) {
		if (f_key.IsLetterOrDigit(code) ||
			(code==f_key.VK_SPACE) ||
			(code>=f_key.VK_MULTIPLY && code<=f_key.VK_DIVIDE) ||
			(code>=f_key.VK_NUMPAD0 && code<=f_key.VK_QUOTE) ||
			(code>=f_key.VK_SEMICOLON && code<=f_key.VK_EQUALS)
			) {
			return true;
		}
		
		return false;
	},
	/**
	 * @method public static
	 * @param number code Key code.
	 * @return boolean
	 */
	IsLetterOrDigit: function(code) {
		if ((code >= 48 && code <= 57) ||	// keyboard digits
			(code >= 65 && code <= 90))	{	// keyboard letters
			return true;
		}
		
		return false;
	},
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		//var document=this.f_getClassLoader().f_getDocument();
		
		f_key._CurrentScopes=undefined;
		f_key._Scopes=undefined;

		f_key._SetDomEvent(false);
	},
	/**
	 * @method hidden static final
	 */
	EnterScope: function(scopeName) {
		var scope=f_key._GetScopeByName(scopeName);
		
		var currentScopes=f_key._CurrentScopes;
		if (!currentScopes) {
			currentScopes=new Array;
			f_key._CurrentScopes=currentScopes;
		}
		
		currentScopes.push(scope);

		f_core.Debug(f_key, "EnterScope: Enter scope '"+scope._name+"'.");


		if (false) {		
			var s="";
			for(var i=0;i<currentScopes.length;i++) {
				s+="/"+currentScopes[i]._name;
			}
			
			document.title=s;
		}		
	},
	/**
	 * @method hidden static final
	 */
	ExitScope: function(scopeName) {
		f_core.Assert(f_key._CurrentScopes, "f_key.ExitScope: No scopes defined.");			
		f_core.Assert(f_key._CurrentScopes.length, "f_key.ExitScope: Empty scopes !");
		
		var scope=f_key._CurrentScopes.pop();
		f_core.Assert(scope._name==scopeName, "f_key.ExitScope: Bad scope ! [removed='"+scope._name+"', asked='"+scopeName+"']");

		f_core.Debug(f_key, "ExitScope: Exit scope '"+scope._name+"'.");

		if (false) {
			var s="";
			for(var i=0;i<f_key._CurrentScopes.length;i++) {
				s+="/"+f_key._CurrentScopes[i]._name;
			}
			
			document.title=s;
		}		
	},
	/**
	 * @method hidden static final
	 */
	SetAltKeyMode: function(scopeName, needAltKey) {
		f_core.Assert(f_key._Scopes, "No scopes defined.");
		
		var scope=f_key._Scopes[scopeName];
		f_core.Assert(scope, "Scope '"+scopeName+"' not found.");
			
		scope._altKey=needAltKey;
	},
	/**
	 * @method private static
	 */
	_GetScopeByName: function(scopeName) {	
		if (!scopeName) {
			scopeName=f_key._MAIN_SCOPE_NAME;
		}
		
		var scopes=f_key._Scopes;
		if (!scopes) {
			scopes=new Object;
			f_key._Scopes=scopes;
		}
		
		var scope=scopes[scopeName];
		if (scope) {
			return scope;
		}
		
		scope=new Object;
		scope._name=scopeName;
		
		scopes[scopeName]=scope;
		
		if (scopeName!=f_key._MAIN_SCOPE_NAME) {
			return scope;
		}
		
		// Le scope de base à besoin du alt pour les keyHandlers !
		scope._altKey=true;
		
		f_key.EnterScope(scopeName);

		f_key._SetDomEvent(true);
		
		return scope;
	},
	/**
	 * @method hidden static
	 * @return boolean
	 */
	AddKeyHandler: function(scopeName, keyName, component, method, parameter) {	
		var scope=f_key._GetScopeByName(scopeName);
	
		var def=new Object;
		def._component=component;
		def._method=method;
		def._parameter=parameter;
		
		keyName=keyName.toUpperCase();
		f_core.Assert(!scope[keyName], "Le scope '"+scopeName+"' contient déjà une definition pour la touche '"+keyName+"'.");
		
		var keyHandlers=scope._keyHandlers;
		if (!keyHandlers) {
			keyHandlers=new Object;
			scope._keyHandlers=keyHandlers;
		}
		
		keyHandlers[keyName]=def;
		
		f_core.Debug(f_key, "AddKeyHandler: Add key handler scope='"+scopeName+"' keyName='"+keyName+"'"+(component?(" component='"+component.id+"'"):"")+"' method='"+method+"'.");

		return true;
	},
	/** 
	 * @method hidden static
	 */
	 AddAccelerator: function(character, virtualKeys, keyFlags, component, method) {
	 	var scope=f_key._GetScopeByName();
	 	
	 	var accelerators=scope._accelerators;
	 	if (!accelerators) {
	 		accelerators=new Array;
	 		scope._accelerators=accelerators;
	 	}
	 		 	
	 	var def=new Object;
	 	def._character=character;
	 	def._virtualKeys=virtualKeys;
	 	def._keyFlags=keyFlags;
	 	def._component=component;
	 	def._method=method;
			
		f_core.Debug(f_key, "AddAccelerator: Add accelerator character='"+character+"' virtualKeys='"+virtualKeys+"' keyFlags='"+keyFlags+"'.");
	 	
	 	accelerators.push(def);
	 },
	/**
	 * @method hidden static
	 * @return void
	 */
	RemoveKeyHandler: function(scopeName, keyName) {
		f_core.Assert(f_key._Scopes, "f_key.RemoveKeyHandler: No scopes defined.");
		
		var scope=f_key._Scopes[scopeName];
		f_core.Assert(scope, "f_key.RemoveKeyHandler: Scope '"+scopeName+"' not found.");

		var keyHandlers=scope._keyHandlers;
		if (!keyHandlers) {
			return;
		}
				
		keyName=keyName.toUpperCase();
		
		keyHandlers[keyName]=undefined;
	},
	/**
	 * @method hidden static 
	 * @return boolean
	 */
	RemoveScope: function(scopeName) {
		f_core.Assert(f_key._Scopes, "f_key.RemoveScope: No scopes defined.");
		
		var scope=f_key._Scopes[scopeName];
		if (!scope) {
			return false;
		}
		f_core.Assert(scope, "f_key.RemoveScope: Scope '"+scopeName+"' not found.");
		
		f_key._Scopes[scopeName]=undefined;
		return true;
	},	
	/**
	 * @method hidden static 
	 */
	DefaultAccessKey: function() {
	},
	/**
	 * @method private static 
	 * @context event:jsEvent
	 */
	_PerformKey: function(jsEvent) {
		if (!jsEvent) {
			jsEvent = f_core.GetJsEvent(this);
		}

		var def=f_key._SearchDef(jsEvent, true);
		if (!def) {
			return true;
		}

		if (f_event.GetEventLocked(jsEvent)) {
			return f_core.CancelJsEvent(jsEvent);
		}
		
		var method=def._method;
		
		if (method==f_key.DefaultAccessKey) {
			f_core.Debug(f_key, "_PerformKey: Default accessKey comportement !");
		
			return true;
		}
		
		if (method) {
			f_core.Debug(f_key, "_PerformKey: Call specific method.");

			try {
				method.call(def._component, jsEvent, def._parameter);
				
			} catch (ex) {
				f_core.Error(f_key, "_PerformKey: Exception/keyHandler, method="+method, ex);
				
				// def._method=undefined;
			}				
		}
		
		if (f_core.IsInternetExplorer()) {
			try {
				jsEvent.keyCode=1; // Fake key !
			} catch (x) {
				// Rien, on s'en fiche !
			}
		}
				
		return f_core.CancelJsEvent(jsEvent);
	},
	/**
	 * @method private static
	 * @context event:jsEvent
	 */
	_CatchKey: function(jsEvent) {
		if (!jsEvent) {
			jsEvent = f_core.GetJsEvent(this);
		}

		var def=f_key._SearchDef(jsEvent, false);
		
		f_core.Debug(f_key, "_CatchKey: return '"+def+"'.");
		if (!def) {
			return true;
		}
		
		var method=def._method;
		if (method==f_key.DefaultAccessKey) {
			return true;
		}	
		
		return f_core.CancelJsEvent(jsEvent);
	},	
	/**
	 * @method private static
	 */
	_SearchDef: function(jsEvent, altKey) {
		var keyCode = jsEvent.keyCode;
		var charCode = jsEvent.charCode;
		
		var keyChar;
		
		if (!charCode) {
			keyChar = String.fromCharCode(keyCode);

		} else {
			keyChar = String.fromCharCode(charCode);
		}
		
		if (keyChar) {
			keyChar=keyChar.toUpperCase();
		}
		
		f_core.Debug(f_key, "_SearchDef: KeyEvent keyCode="+keyCode+" charCode="+charCode+" keyChar="+keyChar);
		
		if (f_core.IsGecko()) {
			if (keyCode<=0) {
				keyCode=charCode;
			}
		}

		if (altKey && keyCode==f_key.VK_ALT) {
			f_key.UpdateAccessKeyRule(window);
		}

		var currentScopes=f_key._CurrentScopes;
		f_core.Assert(currentScopes, "f_key._SearchDef: No scopes !");
		
		// On recherche les keyHandlers d'abord !
		if (keyChar) {
			var currentScope=currentScopes[currentScopes.length-1];

			var keyHandlers=currentScope._keyHandlers;
			if (keyHandlers){
				if (currentScope._altKey==jsEvent.altKey) {
					var def=keyHandlers[keyChar];

					f_core.Debug(f_key, "_SearchDef: KeyHandlers("+keyChar+"/alt="+jsEvent.altKey+")="+def);

					if (def) {
						return def;
					}
				}
			}
		}
		
		// On cherche les accelerators
		var scope=currentScopes[0];
		
		var def=f_key._SearchAccelerator(scope, keyChar, keyCode, jsEvent);
		
		f_core.Debug(f_key, "_SearchDef: Accelerator("+keyChar+"/"+keyCode+")="+def);
		
		return def;
	},
	
	/**
	 * @method private static
	 */
	_SearchAccelerator: function(scope, keyChar, keyCode, jsEvent) {
		var accelerators=scope._accelerators;
		if (!accelerators || accelerators.length<1) {
			return null;
		}
		
		var mask=0;
		if (jsEvent.altKey) {
			mask|=f_key.KF_ALT;
		}
		if (jsEvent.ctrlKey) {
			mask|=f_key.KF_CONTROL;
		}
		if (jsEvent.shiftKey) {
			mask|=f_key.KF_SHIFT;
		}
		if (jsEvent.metaKey) {
			mask|=f_key.KF_META;
		}
		
		for(var i=0;i<accelerators.length;i++) {
			var def=accelerators[i];
			
			var kf=def._keyFlags;
			if (kf && mask!=kf) {
				continue;
			}
			
			var ch=def._character;
			if (ch && ch==keyChar) {
				return def;
			}
			
			var vks=def._virtualKeys;
			if (vks) {
				for (var j=0;j<vks.length;j++) {
					if (vks[j]!=keyCode) {
						continue;
					}
					
					return def;
				}
			}
		}
		
		return null;
	},
	
	/**
	 * @method hidden static
	 */
	UpdateAccessKeyRule: function(window, childWindow) {
		if (!childWindow) {
			childWindow=window;
		}
		
		if (childWindow._showAlt) {
			return;
		}
		childWindow._showAlt=true;
	
		var styleSheet=childWindow.document.styleSheets[0];

/* C'est fait par le CSS propriétaire de IE  !
		if (f_core.IsInternetExplorer()) {
		//	styleSheet.addRule("SPAN.f_accessKey", "text-decoration: underline");	
			return;
		}
*/	
		if (f_core.IsGecko()) {
			styleSheet.insertRule(".f_accessKey { text-decoration: underline }", styleSheet.cssRules.length);
			return;			
		}
	},
	
	/**
	 * @method private static
	 */
	_SetDomEvent: function(set) {
		
		f_core.Debug(f_key, "_SetDomEvent: Set dom events ("+set+")");

		if (f_core.IsInternetExplorer()) {
			if (set) {
				document.onkeydown=f_key._PerformKey;
				document.onkeypress=f_key._CatchKey;
//				document.attachEvent("onkeydown", listener);
				
			} else  {
				document.onkeydown=null;
				document.onkeypress=null;
	//			document.detachEvent("onkeydown", listener);
			}
			
			return;
		}
		
		if (set) {
			document.addEventListener("keydown", f_key._PerformKey, true);
			document.addEventListener("keypress", f_key._CatchKey, true);
			//window.addEventListener("keydown", listener, false);
			
		} else {
			document.removeEventListener("keydown", f_key._PerformKey, true);
			document.removeEventListener("keypress", f_key._CatchKey, true);
			//window.removeEventListener("keydown", listener, false);
		}
	}
}

new f_class("f_key", {
	statics: __statics
});
