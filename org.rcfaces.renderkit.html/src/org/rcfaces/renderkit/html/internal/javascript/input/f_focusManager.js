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
	 * @param hidden boolean create
	 * @return f_focusManager
	 */
	Get: function(create) {
		var instance=f_focusManager._Instance;
		if (!instance && create!==false) {
			instance=f_focusManager.f_newInstance();
			
			f_focusManager.Set(instance);
		}
		
		return instance;
	},
	/**
	 * @method public hidden
	 * @param f_focusManager focusManager
	 * @return void
	 */
	Set: function(focusManager) {
		f_focusManager._Instance=focusManager;
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

	f_focusManager: function() {
		this.f_super(arguments);
	
		if (f_focusManager._Instance) {
			throw new Error("FocusManager is already defined !");
		}
			
		if (this.nodeType==f_core.ELEMENT_NODE) {			
			var setFocusIfMessage=f_core.GetBooleanAttribute(this, "v:setFocusIfMessage", true);
			if (setFocusIfMessage) {
				instance.f_setFocusIfMessage(true);
			}
			
			var focusId=f_core.GetAttribute(this, "v:focusId");
			if (focusId) {
				instance.f_setFocus(focusId, true);
			}
			
			if (instance!=this)  {
				return;
			}
		}
		
		if (f_core.IsGecko()) {
			var self=this;

			this._firefoxFocusListener=function(event) {
				var element=event.target;
				self._activeElement=element;
				
				return true;
			};
			
			window.addEventListener("focus", this._firefoxFocusListener, true);
		}
	},
	f_finalize: function() {		
		var messageContext=this._messageContext;
		if (messageContext) {
			this._messageContext=undefined;
			
			messageContext.f_removeMessageListener(this);
		}
		
		var firefoxFocusListener=this._firefoxFocusListener;
		if (firefoxFocusListener) {
			this._firefoxFocusListener=undefined;
			
			window.removeEventListener("focus", firefoxFocusListener, true);
		}

		// this._setFocusIfMessage=undefined; // boolean
		// this._initFocusId=undefined; // String
		// this._focusId=undefined; // String
		// this._documentCompleted=undefined; // boolean
		// this._activeElement=undefined;

		this.f_super(arguments);
	},
	/**
	 * @method hidden
	 * @param String id
	 * @param optional String focusId
	 * @param optional boolean setFocusIfMessage
	 * @return void
	 */
	f_initialize: function(id, focusId, setFocusIfMessage) {
		f_core.Assert(typeof(id)=="string", "f_focusManager.f_setId: Invalid id parameter '"+id+"'.");
		this.id=id;

		if (focusId) {
			this.f_setFocus(focusId, true);
		}

		if (setFocusIfMessage!==false) {
			this._setFocusIfMessage=true;		
			
			var messageContext=f_messageContext.Get();
			if (messageContext) {
				messageContext.f_addMessageListener(this);
				this._messageContext=messageContext;
			}
		}		
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_documentComplete: function() {
		this._documentCompleted=true;
	
		var focusId=this._initFocusId;
		this._initFocusId=undefined;

		f_core.Debug(f_focusManager, "f_documentComplete: focus='"+focusId+"'.");
			
		if (!focusId) {
			return;
		}		
	
		var activeElement=this._getActiveElement();
		if (activeElement) {
			var tagName=activeElement.tagName;
			if (tagName && tagName.toUpperCase()!="BODY") {
				// Le focus est déjà déplacé !
				return;
			}
			if (activeElement.id==focusId) {
				// Déjà positionné !
				return;
			}
		}
		
		this.f_setFocus(focusId, true);
	},
	/**
	 * @method private
	 * @return HTMLElement
	 */
	_getActiveElement: function() {
		var activeElement;
		
		if (f_core.IsInternetExplorer()) {
			activeElement=document.activeElement;

		} else if (f_core.IsGecko()) {
			/*var selection=window.getSelection();
			if (selection) {
				activeElement=selection.focusNode;
			}*/
			activeElement=this._activeElement;
		}

		f_core.Debug(f_focusManager, "_getActiveElement: current active element="+activeElement+" id="+(activeElement?activeElement.id:"**null**"));

		return activeElement;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getFocusId: function() {
		if (!this._documentCompleted) {
			return this._initFocusId;
		}

		var activeElement=this._getActiveElement();

		if (activeElement) {
			var id=activeElement.id;
			if (!id) {
				return id;
			} 
			
			var idx=id.lastIndexOf("::");
			if (idx>0) {
				id=id.substring(0, idx);
			}
			
			return id;
		}
		
		return this._initFocusId;
	},
	/**
	 * @method public
	 * @return HTMLElement
	 */
	f_getFocusComponent: function() {
		var activeElement=this._getActiveElement();
		
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
			if (!this._documentCompleted) {
				this._initFocusId=focus;

				f_core.Debug(f_focusManager, "f_setFocus: documentComplete!=true, wait for focus (component="+focus+".)");
				return undefined;
			}
			
			f_core.Debug(f_focusManager, "f_setFocus: search component id='"+focus+"' async='"+async+"'.");

			try {
				component=f_core.GetElementByClientId(focus);
				
			} catch (x)  {
				// Si le composant n'est pas Camelia, ca pete !
				
				component=document.getElementById(focus);
			}
		} else if (!this._documentCompleted) {
			// C'est déjà positionné !
			this._initFocusId=undefined;
		}
			
		if (!component) {
			f_core.Info(f_focusManager, "f_setFocus: Can not find component '"+focus+"' to set focus !");
			return false;
		}

		if (component==this._getActiveElement()) {
			f_core.Debug(f_focusManager, "f_setFocus: Focus is already setted to the component "+component.id);
			return true;
		}

		f_core.Debug(f_focusManager, "f_setFocus: Set focus to component '"+component.id+"'.");
		
		if (!f_core.SetFocus(component, async)) {
			return false;
		}
		
		return true;
	},
	f_serialize: function() {
		if (this.id) {
			var focusId=this.f_getFocusId();
			
			this.f_setProperty(f_prop.FOCUS_ID, focusId);
		}
	},
	f_performMessageChanges: function(messageContext, messageEvent) {
		f_core.Debug(f_focusManager, "f_performMessageChanges: MessageEvent.type="+messageEvent.type);
		
		switch(messageEvent.type) {
		case f_messageContext.POST_CHECK_EVENT_TYPE:
		case f_messageContext.ADD_MESSAGE_EVENT_TYPE:
			break;
			
		default:
			return;
		}
		
		var selectedComponentClientId;
		var selectedSeverity=-1;
		
		var clientIds=messageContext.f_listComponentIdsWithMessages(true);
		for(var i=0;i<clientIds.length;i++) {
			var clientId=clientIds[i];
			
			if (!clientId) { // On traite pas les globaux
				continue;
			}
			
			var messages=messageContext.f_listMessages(clientId);
			if (!messages || !messages.length) {
				continue;
			}
			
			for(var j=0;j<messages.length;j++) {
				var message=messages[j];
				var severity=message.f_getSeverity();
				
				if (selectedComponentClientId && severity<=selectedSeverity) {
					continue;
				}
				
				selectedComponentClientId=clientId;
				selectedSeverity=severity;
			}
		}
		
		f_core.Debug(f_focusManager, "f_performMessageChanges: focus component="+selectedComponentClientId+" severity="+severity);
		
		if (selectedComponentClientId) {
			this.f_setFocus(selectedComponentClientId);
		}
	}
}
 
new f_class("f_focusManager", {
	extend: f_object,
	aspects: [ fa_serializable ],
	statics: __statics, 
	members: __members
});
