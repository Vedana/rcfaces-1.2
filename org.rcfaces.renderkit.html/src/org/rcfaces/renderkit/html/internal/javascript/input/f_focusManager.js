/*
 * $Id$
 */
 
/**
 * Focus manager class.
 *
 * @class public f_focusManager extends f_object, fa_serializable
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics={

	/**
	 * @field private static
	 */
	_Instance: undefined,
	
	/**
	 * @method public static
	 * @return f_focusManager
	 */
	Get: function() {
		return f_focusManager._Instance;
	},
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		f_focusManager._Instance=undefined; // f_focusManager
	}
}

var __members={

	f_focusManager: function(focusId) {
		this.f_super(arguments);
		
		if (!f_focusManager._Instance) {
			f_focusManager._Instance=this;
		}
	
		if (this.nodeType==f_core.ELEMENT_NODE) {	
			focusId=f_core.GetAttribute(this, "v:focusId");
		}		
		this._initFocusId =  focusId;
	},
	f_finalize: function() {

		// this._initFocusId=undefined; // String
		// this._focusId=undefined; // String

		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_documentComplete: function() {
		var focusId=this._initFocusId;
		if (!focusId) {
			return;
		}
		this._initFocusId=undefined;
		
		this.f_setFocus(focusId);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getFocusId: function() {
		var activeElement;
		
		if (f_core.IsInternetExplorer()) {
			activeElement=document.activeElement;

		} else if (f_core.IsGecko()) {
			activeElement=window.getSelection().focusNode;
		}

		if (activeElement) {
			return activeElement.id;
		}
		
		return this._initFocusId;
	},
	/**
	 * @method public
	 * @return HTMLElement
	 */
	f_getFocusComponent: function() {
		var activeElement;
		
		if (f_core.IsInternetExplorer()) {
			activeElement=document.activeElement;

		} else if (f_core.IsGecko()) {
			activeElement=window.getSelection().focusNode;
		}

		if (activeElement) {
			return this.f_getClassLoader().f_init(activeElement, true);
		}
		 
		return null;
	},
	
	/**
	 * @method public
	 * @param String focus Focus clientId, or a component. 
	 * @param optional boolean async Set focus in async mode.
	 * @return boolean
	 */
	f_setFocus: function(focus, async) {
		f_core.Assert(typeof(focus)=="string" || (focus && focus.nodeType==f_core.ELEMENT_NODE), "f_focusManager.f_setFocus: Focus component parameter is not invalid ("+focus+").");
		
		var component=focus;
		
		if (typeof(focus)=="string") {
			f_core.Debug(f_focusManager, "f_setFocus: search component id='"+focus+"' async='"+async+"'.");
		
			try {
				component=f_core.GetElementByClientId(focus);
				
			} catch (x)  {
				// Si le composant n'est pas Camelia, ca pete !
				
				component=document.getElementById(focus);
			}
		}
			
		if (!component) {
			f_core.Info(f_focusManager, "f_setFocus: Can not find component '"+focus+"' to set focus !");
			return false;
		}

		f_core.Debug(f_focusManager, "f_setFocus: Set focus to component '"+component.id+"'.");
		
		if (!f_core.SetFocus(component, async)) {
			return false;
		}
		
		return true;
	},
	/**
	 * @method private
	 * @return void
	 */
	_recordFocus: function(focusId) {
		f_core.Assert(focusId===null || typeof(focusId)=="string", "f_focusManager._recordFocus: Invalid focusId parameter ! ("+typeof(focusId)+":"+focusId+")");
		if (!focusId) {
			focusId=null;
		}
		
		f_core.Debug(f_focusManager, "_recordFocus: Focus changed to component '"+focusId+"'.");

		this._focusId=focusId;
		this.f_setProperty(f_prop.FOCUS_ID, focusId);
	},
	f_serialize: function() {
		var focusId=this.f_getFocusId();
		
		this.f_setProperty(f_prop.FOCUS_ID, focusId);
	}
}
 
new f_class("f_focusManager", {
	extend: f_object,
	aspects: [ fa_serializable ],
	statics: __statics, 
	members: __members
});
