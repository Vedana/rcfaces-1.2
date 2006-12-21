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
	 */
	Get: function() {
		return f_focusManager._Instance;
	},
	/**
	 * @method public static
	 */
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
		
		if (f_core.IsGecko()) {
			var focusManager=this;
			this._onFocus=function(event) {
			
				try {
					var source=event.target;
					if (source.f_link) {
						source=source.f_link;
						
					} else if (!f_class.IsObjectInitialized(source) && source.nodeType==1) {
						var containerId=f_core.GetAttribute(source, "v:container");
						
						if (containerId) {
							source=document.getElementById(containerId);
							if (!source) {
								f_core.Error(f_focusManager, "Can not find containerId '"+containerId+"'.");
							}
						}
					}
	
					f_core.Debug(f_focusManager, "OnFocus: target="+event.target+" currentTarget="+event.currentTarget+" source="+source+" ");
					
					var id=source.id;
					if (!id) {
						id=null;
					}
					
					focusManager._recordFocus(id);
				} catch (x) {
					f_core.Error(f_focusManager, "Exception on onFocus()",x);
				}
			}
		
			this._onBlur=function(event) {
				try {
					focusManager._recordFocus(null);
					
				} catch (x) {
					f_core.Error(f_focusManager, "Exception on onBlur()", x);
				}
			}
			
		
			f_core.AddEventListener(document, "focus", this._onFocus, true);
			f_core.AddEventListener(document, "blur", this._onBlur, true);
		}
	},
	f_finalize: function() {
		if (f_core.IsGecko()) {
			f_core.RemoveEventListener(document, "focus", this._onFocus, true);
			this._onFocus=undefined;

			f_core.RemoveEventListener(document, "blur", this._onBlur, true);
			this._onBlur=undefined;
		}

		this._initFocusId=undefined;
		this._focusId=undefined;

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
		if (f_core.IsInternetExplorer()) {
			var activeElement=document.activeElement;
			if (activeElement) {
				return activeElement.id;
			}
			return null;
		}
		
		return this._focusId;
	},
	/**
	 * @method public
	 * @return HTMLElement
	 */
	f_getFocusComponent: function() {
		var focusId=this.f_getFocusId();
		if (!focusId) {
			return null;
		}
		
		return f_core.GetElementById(focusId, document);
	},
	
	/**
	 * @method public
	 * @param String focus Focus identifier, or a component.
	 * @param optional boolean async Set focus in async mode.
	 * @return boolean
	 */
	f_setFocus: function(focus, async) {
		f_core.Assert(focus.nodeType==1, "Focus component parameter is not defined.");
		
		f_core.Debug(f_focusManager, "f_setFocus to '"+focus.id+"' async='"+async+"'.");
		var component;
		
		if (typeof(focus)=="string") {
			try {
				component=f_core.GetElementById(focus, document);
			} catch (x)  {
				// Si le composant n'est pas Camelia, ca pete !
				
				component=document.getElementById(focus);
			}
			
			if (!component) {
				f_core.Info(f_focusManager, "Can not find component '"+focus+"' to set focus !");
				return false;
			}
			
		} else if (focus.tagName) {
			component=focus;
			
		} else {
			f_core.Error(f_focusManager, "Focus parameter is invalid '"+focus+"'.");
			return false;
		}

		f_core.Debug(f_focusManager, "Set focus to component '"+component.id+"'.");
		
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
		if (focusId=="") {
			focusId=null;
		}
		
		f_core.Debug(f_focusManager, "Focus changed to component '"+focusId+"'.");

		this._focusId=focusId;
		this.f_setProperty(f_prop.FOCUS_ID, focusId);
	},
	f_serialize: function() {
		var focusId=this.f_getFocusId();
		
		this.f_setProperty(f_prop.FOCUS_ID, focusId);
	}
}
 
var f_focusManager = new f_class("f_focusManager", null, __static, __prototype, f_object, fa_serializable);
