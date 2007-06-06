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

var __static={

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
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		f_focusManager._Instance=undefined; // f_focusManager
	}
}

var __prototype={

	f_focusManager: function() {
		this.f_super(arguments);
		
		if (!f_focusManager._Instance) {
			f_focusManager._Instance=this;
		}
	
		var ch=f_core.GetAttribute(this, "v:focusId");
		if (ch) {
			this._initFocusId =  ch;
		}
		
		if (false && f_core.IsGecko()) {
			var focusManager=this;
			this._onFocus=function(event) {
			
				try {
					var source=event.target;
					if (source.f_link) {
						source=source.f_link;
						
					} else if (!f_class.IsObjectInitialized(source) && source.nodeType==f_core.ELEMENT_NODE) {
						var containerId=f_core.GetAttribute(source, "v:container");
						
						if (containerId) {
							source=document.getElementById(containerId);
							if (!source) {
								f_core.Error(f_focusManager, "_onfocus: Can not find containerId '"+containerId+"'.");
							}
						}
					}
	
					f_core.Debug(f_focusManager, "_onfocus: target="+event.target+" currentTarget="+event.currentTarget+" source="+source+" ");
					
					var id=source.id;
					if (!id) {
						id=null;
					}
					
					focusManager._recordFocus(id);
					
				} catch (x) {
					f_core.Error(f_focusManager, "_onfocus: Exception on onFocus()",x);
				}
			}
		
			this._onBlur=function(event) {
				try {
					focusManager._recordFocus(null);
					
				} catch (x) {
					f_core.Error(f_focusManager, "f_focusManager: Exception on onBlur()", x);
				}
			}
			
		
			f_core.AddEventListener(document, "focus", this._onFocus, document);
			f_core.AddEventListener(document, "blur", this._onBlur, document);
		}
	},
	f_finalize: function() {
		if (this._onFocus) {
			f_core.RemoveEventListener(document, "focus", this._onFocus, document);
			this._onFocus=undefined;

			f_core.RemoveEventListener(document, "blur", this._onBlur, document);
			this._onBlur=undefined;
		}

		// this._initFocusId=undefined; // String
		// this._focusId=undefined; // String

		this.f_super(arguments);
	},
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
		return null;
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
			return f_core.GetWindow(activeElement)._classLoader._init(activeElement, true);
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
		f_core.Assert(typeof(focus)=="string" || (focus && focus.nodeType==f_core.ELEMENT_NODE), "Focus component parameter is not invalid ("+focus+").");
		
		var component=focus;
		
		if (typeof(focus)=="string") {
			f_core.Debug(f_focusManager, "f_setFocus: search component id='"+focus+"' async='"+async+"'.");
		
			try {
				component=f_core.GetElementByClientId(focus, document);
				
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
 
new f_class("f_focusManager", null, __static, __prototype, f_object, fa_serializable);
