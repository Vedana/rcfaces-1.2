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
		var instance=f_focusManager._Instance;
		if (!instance) {
			instance=f_focusManager.f_newInstance();
		}
		
		return instance;
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

	f_focusManager: function(focusId, setFocusIfMessage) {
		this.f_super(arguments);
		
		if (!f_focusManager._Instance) {
			f_focusManager._Instance=this;
		}
	
		if (this.nodeType==f_core.ELEMENT_NODE) {	
			focusId=f_core.GetAttribute(this, "v:focusId");
			
			setFocusIfMessage=f_core.GetBooleanAttribute(this, "v:setFocusIfMessage", true);
		}
		
		this._initFocusId =  focusId;
		this._setFocusIfMessage=setFocusIfMessage;

		if (setFocusIfMessage!==false) {
			var messageContext=f_messageContext.Get();
			if (messageContext) {
				this._messageContext=messageContext;
			
				messageContext.f_addMessageListener(this);
			}		
		}
	},
	f_finalize: function() {
		
		var messageContext=this._messageContext;
		if (messageContext) {
			this._messageContext=undefined;
			
			messageContext.f_removeMessageListener(this);
		}

		// this._setFocusIfMessage=undefined; // boolean
		// this._initFocusId=undefined; // String
		// this._focusId=undefined; // String
		// this._documentComplete=undefined; // boolean

		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_documentComplete: function() {
		this._documentComplete=true;
	
		var focusId=this._initFocusId;
		this._initFocusId=undefined;
			
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
		
		his.f_setFocus(focusId, true);
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
			activeElement=window.getSelection().focusNode;
		}

		return activeElement;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getFocusId: function() {
		if (!this._documentComplete) {
			return this._initFocusId;
		}

		var activeElement=this._getActiveElement();

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
			if (!this._documentComplete) {
				this._initFocusId=focus;
				return undefined;
			}
			
			f_core.Debug(f_focusManager, "f_setFocus: search component id='"+focus+"' async='"+async+"'.");

			try {
				component=f_core.GetElementByClientId(focus);
				
			} catch (x)  {
				// Si le composant n'est pas Camelia, ca pete !
				
				component=document.getElementById(focus);
			}
		} else if (!this._documentComplete) {
			// C'est déjà positionné !
			this._initFocusId=undefined;
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
