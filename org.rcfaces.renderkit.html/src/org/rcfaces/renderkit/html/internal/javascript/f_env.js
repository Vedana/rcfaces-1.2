/*
 * $Id$
 */

/**
 * 
 *
 * @class public f_env extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */
 
function f_env() {
}

var __static = {
	/**
	 * @field private static final string 
	 */
	_STYLESHEET_BASE: "styleSheet.base",
	/**
	 * @field private static final string 
	 */
	_DEFAULT_STYLESHEET_BASE: "stylesheets",
	
	/**
	 * @field private static final string 
	 */
	_CANCEL_EXTERNAL_SUBMIT: "cancel.external.submit",
	
	/**
	 * @field private static string
	 */
	 _StyleSheetBase: undefined,
	
	/**
	 * @field private static string
	 */
	_BaseURI: undefined,
	
	/**
	 * @field private static string
	 */
	_JsBaseURI: undefined,
	
	/**
	 * @field private static string
	 */
	_ViewURI: undefined,

	/**
	 * @field private static string
	 */
	_LocaleName: undefined,

	/**
	 * @field private static boolean
	 */
	_LockSubmitUntilPageComplete: undefined,
	
	/*
	Initializer: function() {
		// Defaults are in f_helpButton, change these for customization
		// HELPBUTTON_IMAGE_URL = "stylesheets/help/helpButton.gif";
		// HELPBUTTON_HOVER_URL = "stylesheets/help/helpButtonHover.gif";

		// Defaults are in F_HELP, change these for customization
		// WINHELP_ID = "VFCHelpWindow
		// WINHELP_X = 100;
		// WINHELP_Y = 100;
		// WINHELP_W = 600;
		// WINHELP_H = 450;
		// WINHELP_FEATURES = "scrollbars,resizable,status=no";

		// CORE_LOCK_MESSAGE = "Window has been locked...";
	},
	*/
	
	/**
	 * @method public static final 
	 * @param String name Name of the key.
	 * @param any val Value to associate to the specified key
	 * @return any Previous value.
	 */
	Set: function(name, val) {
		f_core.Assert(typeof(name)=="string", "f_env.Set: Invalid name '"+name+"'.");

		var ret = f_env[name];
		f_env[name] = val;
		return ret;
	},
	
	/**
	 * @method public static final 
	 * @param String name
	 * @param any defaultValue
	 * @return any Value associated to name, or specified default value.
	 */
	Get: function(name, defaultValue) {
		f_core.Assert(typeof(name)=="string", "f_env.Get: Invalid name '"+name+"'.");
		
		var v=f_env[name];
		if (v===undefined) {
			return defaultValue;
		}
		
		return v;
	},
	
	/**
	 * @method public static final 
	 * @param String name
	 * @return any Previous value associated to specified name.
	 */
	Delete: function(name) {
		f_core.Assert(typeof(name)=="string", "f_env.Delete: Invalid name '"+name+"'.");

		var old=f_env[name];
		if (old !== undefined) {
			delete f_env[name];
		}
		
		return old;
	},

	/**
	 * @method hidden static final 
	 */
	GetBaseURI: function() {
		return f_env._BaseURI;
	},
	
	/**
	 * @method hidden static final 
	 */
	ComputeJavaScriptURI: function(uri) {
		f_core.Assert(typeof(uri)=="string", "f_env.ComputeJavaScriptURI: invalid uri parameter '"+uri+"'.");

		var baseURI=f_env._JsBaseURI;
		if (!baseURI) {
			return uri;
		}
		return baseURI+"/"+uri;
	},
	/**
	 * @method hidden static final 
	 */
	GetStyleSheetBase: function() {
		if (f_env._StyleSheetBase) {
			return f_env._StyleSheetBase;
		}
		
		var sb=f_env.Get(f_env._STYLESHEET_BASE);
		if (sb) {
			f_env._StyleSheetBase=sb;
			return sb;
		}
		
		return f_env._DEFAULT_STYLESHEET_BASE;
	},
	/**
	 * @method hidden static final 
	 */
	GetLocaleName: function() {
		return f_env._LocaleName;
	},
	
	/**
	 * @method hidden static final
	 */
	Initialize: function(baseURI, viewURI, localeName, jsBaseURI, styleSheetBaseURI) {
		f_env._BaseURI=baseURI;		
		f_env._JsBaseURI=jsBaseURI;
		f_env._LocaleName=localeName;
		if (!styleSheetBaseURI) {
			styleSheetBaseURI=jsBaseURI;
		}
		f_env._StyleSheetBase=styleSheetBaseURI;
		f_env._ViewURI=viewURI;
		
		f_core.Debug("f_env", "Set Page base URI to '"+baseURI+"'.");
		f_core.Debug("f_env", "Set View base URI to '"+viewURI+"'.");
		f_core.Debug("f_env", "Set Javascript base URI to '"+jsBaseURI+"'.");
		f_core.Debug("f_env", "Set Css base URI to '"+styleSheetBaseURI+"'.");
	},
	
	/**
	 * @method hidden static final
	 */
	GetCancelExternalSubmit: function() {
		return f_env.Get(f_env._CANCEL_EXTERNAL_SUBMIT, false);
	},
	
	/**
	 * @method hidden static final
	 */
	EnableSubmitLockUntilPageComplete: function() {
		f_env._LockSubmitUntilPageComplete=true;
	},
	
	/**
	 * @method hidden static final
	 */
	IsSubmitUntilPageCompleteLocked: function() {
		return f_env._LockSubmitUntilPageComplete;
	},
		
	/**
	 * @method hidden static final
	 */
	GetViewURI: function() {
		var uri=f_env._ViewURI;
		if (uri) {
			return uri;
		}
		
		return window.location.toString();
	},
	
	/**
	 * @method hidden static final
	 */
	GetCheckValidation: function() {
		return true;
	},
	
	/**
	 * @method hidden static final
	 */
	GetOpenWindowErrorMessage: function() {
		var bundle=f_resourceBundle.Get(f_env);
		if (!bundle) {
			return null;
		}
			
		return bundle.f_get("OPEN_WINDOW_ERROR_MESSAGE");
	},
	
	f_getName: function() {
		return "f_env";
	}
}

for(var p in __static) {
	f_env[p]=__static[p];
}
