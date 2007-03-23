/*
 * $Id$
 */

/**
 * 
 *
 * @class public final f_env extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

var __static = {
	/**
	 * @field private static final String 
	 */
	_STYLESHEET_BASE: "styleSheet.base",
	
	/**
	 * @field private static final String 
	 */
	_CANCEL_EXTERNAL_SUBMIT: "cancel.external.submit",

	/**
	 * @field private static final String 
	 */
	_DOMAIN_NAME: "camelia.domain",
	
	/**
	 * @field private static String
	 */
	 _StyleSheetBase: undefined,
	
	/**
	 * @field private static String
	 */
	_BaseURI: undefined,
	
	/**
	 * @field private static String
	 */
	_JsBaseURI: undefined,
	
	/**
	 * @field private static String
	 */
	_ViewURI: undefined,

	/**
	 * @field private static String
	 */
	_LocaleName: undefined,

	/**
	 * @field private static boolean
	 */
	_LockSubmitUntilPageComplete: undefined,
		
	/**
	 * @field hidden static boolean
	 */
	_SensitiveCaseTagName: undefined,
		
	/**
	 * @field private static boolean
	 */
	_ClientValidationDisabled: undefined,
	
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
	 * @return String The url of the rcfaces resources, or null if it is not defined !
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
		
		f_core.Debug(f_env, "GetStyleSheetBase: not yet initialized !");
		return null;
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
		
		if (viewURI.charAt(0)=="/") {
			// On ajoute le protocole, le host et le port
			
			var url=window.location.toString();
			var idx=url.indexOf("//");
			if (idx>0) {
				idx=url.indexOf("/", idx+2);
				if (idx>0) {
					viewURI=url.substring(0, idx)+viewURI;
				}
			}
		}
		
		f_env._ViewURI=viewURI;
		
		f_core.Debug(f_env, "Set Page base URI to '"+baseURI+"'.");
		f_core.Debug(f_env, "Set View base URI to '"+viewURI+"'.");
		f_core.Debug(f_env, "Set Javascript base URI to '"+jsBaseURI+"'.");
		f_core.Debug(f_env, "Set Css base URI to '"+styleSheetBaseURI+"'.");
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
		return (f_env._ClientValidationDisabled!==true);
	},
	
	/**
	 * @method public static final
	 */
	EnableSensitiveCaseTagName: function() {
		if (f_core.IsGecko()) { // Gecko n'est pas sensitive case !
			return;
		}
		
		f_core.Debug(f_env, "Enable sensitiveCaseTagName");
		f_env._SensitiveCaseTagName=true;
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
	/**
	 * @method hidden static final
	 * @return String the domain
	 */
	GetDomain: function() {
		var domain=f_env.Get(f_env._DOMAIN_NAME);
		if (domain) {
			return domain;
		}
			
		var url=window.location.toString();
		var idx=url.indexOf("//");
		if (idx<0) {
			f_env.Set(f_env._DOMAIN_NAME, "");

			f_core.Debug(f_env, "Invalid domain from url '"+url+"'.");
			return "";
		}
		
		var domain;
		
		var idx2=url.indexOf("/", idx+2);
		if (idx2>idx) {
			domain=url.substring(idx+2, idx2);
		} else {
			domain=url.substring(idx+2);
		}
		
		var idx2=domain.indexOf(":");
		if (idx2>0) {
			domain=domain.subtring(0, idx2);
		}
		
		var ds=domain.split(".");
		var dsl=ds.length;
		if (dsl>1) {
			domain=ds[dsl-2]+"."+ds[dsl-1];
		}
		
		f_env.Set(f_env._DOMAIN_NAME, domain);
		
		f_core.Debug(f_env, "Set domain to '"+domain+"' (url='"+url+"')");
		return domain;
	},
	/** 
	 * @method hidden static
	 * @return void
	 */	
	DisableClientValidation: function() {
		f_env._ClientValidationDisabled=true;
	}
}

new f_class("f_env", null, __static);
