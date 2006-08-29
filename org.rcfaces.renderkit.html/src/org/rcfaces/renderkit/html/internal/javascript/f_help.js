/*
 * $Id$
 *
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */

/**
 * f_help package
 *
 * @class hidden f_help extends f_object
 * @author Joel Merlin
 * @version $Revision$
 */
var __static = {

	/**
	 * @field private static final string
	 */
	_ID: "VFCHelpWindow",
	
	// Default is 800x600 resolution, 4/3 screen, centered
	/**
	 * @field private static final number
	 */
	_X: 100,

	/**
	 * @field private static final number
	 */
	_Y: 100,

	/**
	 * @field private static final number
	 */
	_W: 600,

	/**
	 * @field private static final number
	 */
	_H: 450,

	/**
	 * @field private static final string
	 */
	_F: "scrollbars,resizable,status=no",

	/**
	 * @field private boolean
	 */
	_Installed: undefined,

	/**
	 * @field private static f_component
	 */
	_FocusElement: undefined,

	/**
	 * @field private static f_component
	 */
	_HelpZone: undefined,

	/**
	 * @field private static boolean
	 */
	_FindZone: undefined, 
	
	/**
	 * @field private static window
	 */
	_Window: undefined,

	/**
	 * @method hidden static final
	 */
	SetHelpMessageZone: function(elt) {
		if (!f_help._HelpZone && !elt) {
			f_help._FindZone = true;
			return;
		}

		if (elt) {
			f_help._HelpZone = elt;
		}
	},
	/**
	 * @method hidden static final
	 */
	GetHelpMessageZone: function() {
		return f_help._HelpZone;
	},
	/**
	 * @method private static final
	 */
	_Install: function() {
		if (f_help._Installed) {
			return;
		}	
		f_help._Installed = true;
		
		if (f_core.IsInternetExplorer()) {
			window.onhelp = f_help._IE_open;
			return;	
		}

		document.onkeydown = f_help._NS_open;
	},
	_IE_open: function() {
		if (f_help._FocusElement) {
			return f_help._Open(f_help._FocusElement);
		}

		return false;
	},
	_NS_open: function(js) {
		if (f_help._FocusElement && js.keyCode==f_key.VK_F1) {
			return f_help._Open(f_help._FocusElement);
		}

		return true;
	},
	_OnFocus: function(evt) {
		f_help._FocusElement = evt.f_getComponent();
	},
	_OnBlur: function(evt) {
		if (f_help._FocusElement == evt.f_getComponent()) {
			f_help._FocusElement = undefined;
		}
	},
	_OnShowHelpMessage: function(evt) {
		var component=evt.f_getComponent();
		
		var zone = f_help._HelpZone;
		if (!zone) {
			window.status = component.f_getHelpMessage();
			
		} else {
			zone.f_showMessage(component);
		}
		return true;
	},
	_OnHideHelpMessage: function(evt) {
		var component=evt.f_getComponent();
		var zone = f_help._HelpZone;
		if (!zone) {
			window.status = "";

		} else {
			zone.f_hideMessage(component);
		}
		
		return true;
	},
	_Open: function(elt) {
		var url=null;
		if (elt && typeof(elt.f_getHelpURL)=="function") {
			url = elt.f_getHelpURL();
		}
		
		if (!url) {
			return false;
		}
		
		var win=f_help._Window;
		if (!win || win.closed) {
			var id = f_env.Get("WINHELP_ID", f_help._ID);
			var w = f_env.Get("WINHELP_W", f_help._W);
			var h = f_env.Get("WINHELP_H", f_help._H);
			var x = f_env.Get("WINHELP_W", f_help._X);
			var y = f_env.Get("WINHELP_W", f_help._Y);
			var f = f_env.Get("WINHELP_FEATURES", f_help._F);
			win = f_core._OpenWindow(url,id,f,x,y,w,h);
			
			f_help._Window = win;
		}
			
		win.location.href = url;
		win.focus();
		
		return false;
	},
	DocumentComplete: function() {
		if (!f_help._FindZone) {
			return;
		}
		f_help._HelpZone = f_core.GetChildByClass(document,f_helpMessageZone.f_getName());
	},
	Finalizer: function() {
		f_help._FocusElement=undefined;
		f_help._HelpZone=undefined;
		f_help._Window=undefined;
	}
}

var f_help=new f_class("f_help", null, __static);
