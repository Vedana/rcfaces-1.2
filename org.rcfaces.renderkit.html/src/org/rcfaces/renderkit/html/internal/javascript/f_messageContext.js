/*
 * $Id$
 */

/**
 * Class Message Context
 *
 * @class public final f_messageContext extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


function f_messageContext(form) {

	this._messages=new Object;

	if (!arguments.length) {
		this._listeners=new Array;
		f_core.Assert(f_messageContext._Root===undefined, "Root messageContext is already defined !");
		return;
	}

	f_core.Assert(form && form.tagName && form.tagName.toUpperCase()=="FORM", "Form parameter is not a form ! ("+form+")");

	this._form=form;
	var parent=f_messageContext.Get();
	this._parent=parent;
	this._listeners=parent._listeners;
	
	// Il faut recuperer les messages du root !
	var rootMessages=parent._messages;
	if (rootMessages) {
		var formClientId=form.id;
		
		var messages=this._messages;
	
		for(var i in rootMessages) {
			if (i.indexOf(formClientId)) {
				 // Commence pas par notre clientId !
				continue;
			}
			
			messages[i]=rootMessages[i];
			rootMessages[i]=undefined;
		}	
	}	
	
	f_core.AddCheckListener(form, this);
}
	
f_messageContext.prototype = {
	f_finalize: function() {
		var parent=this._parent;
		if (parent) {
			this._parent=undefined; // f_messageContext
			this._form._messageContext=undefined; // f_messageContext
			this._form=undefined; // HTMLFormElement
			
		} else {
			this._listeners=undefined; // function[]
		}
		
		// this._messages=undefined;  // f_messageObject[]	
	},

	/**
	 * @method hidden
	 * @param HTMLElement form
	 * @return void
	 */
	f_performCheckPre: function(form) {
		this._clearMessages(false);
	},

	/**
	 * @method hidden
	 * @param boolean result
	 * @param HTMLElement form
	 * @return boolean 
	 */
	f_performCheckPost: function(result, form) {
		// Methode appelée lors du check de la form !
		
		this._fireMessageEvent();
		
		if (!result) {
			// C'est déjà bloqué !
			return result;
		}
		
		var messages=this._messages;
		
		// On bloque si il y a des erreurs !
		for(var clientId in messages) {
			var ms=messages[clientId];
			
			for(var i=0;i<ms.length;i++) {
				var message=ms[i];
				
				if (message.f_getSeverity()<f_messageObject.SEVERITY_ERROR) {
					continue;
				}
				
				// Positionne le focus ?
				
				var component=f_core.GetElementById(clientId);
				if (component) {
					f_core.SetFocus(component, true);
				}
				
				return false;
			}
		}
		
		return true;
	},
	
	/**
	 * @method hidden
	 * @param Function listener
	 * @return boolean 
	 */
	f_addMessageListener: function(listener) {
		var l=this._listeners;
	
		f_core.Debug("f_messageContext", "Add a new message event listener !");
		
		return l.f_addElement(listener);
	},
	
	/**
	 * @method hidden
	 * @param Function listener
	 * @return boolean 
	 */
	f_removeMessageListener: function(listener) {
		var l=this._listeners;
		
		return l.f_removeElement(listener);
	},
	
	/**
	 * @method public
	 * @param String componentId Identifiant of component, or an array of identifiants.  (<code>null</code> specified ALL messages)
	 * @param optional boolean globalOnly
	 * @return f_messageObject[]
	 */
	f_listMessages: function(componentId, globalOnly) {
	
		if (globalOnly) {
			if (this._parent) {
				return this._parent.f_listMessages(null, true);
			}
		
			componentId=f_messageContext._GLOBAL_COMPONENT_ID;
		}
		
		var messages=this._messages;
	
		if (typeof(componentId)=="string") {
			if (!messages) {
				return [];
			}
	
			var l=messages[componentId];
			return (l)?l:[];
		}
	
		// Tous les messages
	
		var l=new Array;
		
		for(var clientId in messages) {
			l.push.apply(l, messages[clientId]);
		}
			
		if (this._parent) {
			l.push.apply(l, this._parent.f_listMessages(null, true));
		}
	
		return l;
	},
	
	/**
	 * @method public
	 *
	 * @param HTMLElement component componentOrId Component to add the message.
	 * @param f_messageObject message
	 * @param hidden boolean performEvent
	 * @return void
	 */
	f_addMessageObject: function(component, message, performEvent) {	
		f_core.Assert(component===null || component===false || component.id || typeof(component)=="string", "f_messageContext.f_addMessageObject: Component parameter must be a component or an id !");
		f_core.Assert(typeof(component)!="string" || component.length, "f_messageContext.f_addMessageObject: Parameter componentId is invalid ! ('"+component+"')"); 
	
		var id=component;
		if (component && component.id) {
			id=component.id;
		}
		
		if (id===null && this._parent) {
			this._parent.f_addMessageObject(null, message, performEvent);
			return;
		}
		
		if (id===false) {
			id=f_messageContext._UNKNOWN_COMPONENT_ID;
				
		} else if (id===null) {
			id=f_messageContext._GLOBAL_COMPONENT_ID;
		}
	
		var messages=this._messages;
		var l2=messages[id];
		if (!l2) {
			l2=new Array;
			messages[id]=l2;
		}
	
		f_core.Info(f_messageContext, "Add message object to component '"+id
			+"'.\nseverity="+message.f_getSeverity()
			+"\nsummary="+message.f_getSummary()
			+"\ndetail="+message.f_getDetail());
	
		l2.push(message);
		
		if (performEvent!==false) {
			this._fireMessageEvent();
		}
	},
	
	/**
	 * @method public
	 *
	 * @param HTMLElement component Component to add the message, or an array of components.
	 * @param number severity
	 * @param String summary
	 * @param optional String detail
	 * @return f_messageObject
	 */
	f_addMessage: function(component, severity, summary, detail) {
		f_core.Assert(typeof(severity)=="number", "f_messageContext.f_addMessage: Bad type of severity !");
		f_core.Assert(summary, "f_messageContext.f_addMessage: Summary is null !");
		f_core.Assert(component===null || (component instanceof Array) || component.id, "f_messageContext.f_addMessage: Component parameter must be a component or an id or null !");
		
		var message=new f_messageObject(severity, summary, detail);
		
		if (component instanceof Array) {
			for(var i=0;i<component.length;i++) {
				var cmp=component[i];
				
				f_core.Assert(cmp===null || cmp.id, "Component parameter #"+i+" must be a component or null !");
	
				this.f_addMessageObject(cmp, message);
			}
			
			return message;
		}
		
		this.f_addMessageObject(component, message);
	
		return message;
	},
	
	/**
	 * @method public
	 * @param optional HTMLElement... component (or an id)
	 * @return boolean Returns <code>true</code> if some messages have been removed.
	 */
	f_clearMessages: function(component) {
		return this._clearMessages(true, arguments);
	},
	
	/**
	 * @method private
	 * @param boolean performEvent 
	 * @param optional HTMLElement[] components (or an id)
	 * @return boolean Returns <code>true</code> if some messages have been removed.
	 */
	_clearMessages: function(performEvent, components) {
	
		var parent=this._parent;
		if (!components || !components.length) {
			// Aucun arguments !
			
			var changed=false;
			
			if (parent) {
				changed=parent._clearMessages(false);
			}
			
			for(var i in this._messages) {
				this._messages=new Object;
				changed=true;
				break;
			}
		
			if (!changed) {
				f_core.Info(f_messageContext, "["+this._form+"] No messages to clear.");
				return false;
			}	
	
			f_core.Info(f_messageContext,  "["+this._form+"] Clear all messages.");
			
			if (performEvent) {
				this._fireMessageEvent();
			}
			return true;
		}
		
		var messages=this._messages;
		
		var changed=false;
		for(var i=0;i<components.length;i++) {
			var componentId=components[i];
			if (componentId===null) {
				if (parent) {
					if (parent._clearMessages(false)) {
						changed=true;
					}
					continue;
				}
				
				componentId=f_messageContext._GLOBAL_COMPONENT_ID;
	
			} else if (typeof(componentId)!="string") {
				componentId=componentId.id;			
			}
			
			f_core.Assert(typeof(componentId)=="string", "f_messageContext.f_clearMessages: Invalid parameter #"+i+" ("+arguments[i]+").");
	
			var l2=messages[componentId];
			if (!l2) {
				f_core.Debug(f_messageContext,  "["+this._form+"] Nothing to clear for component '"+componentId+"'.");
				continue;
			}
			
			delete messages[componentId];
			changed=true;
		
			f_core.Info(f_messageContext,  "["+this._form+"] Clear "+(l2.length)+" messages associated to the component '"+componentId+"'.");
		}
		
		if (!changed) {
			return false;
		}
		
		if (performEvent) {
			this._fireMessageEvent();
		}
		
		return true;
	},
	
	/**
	 * @method private
	 * @return void
	 */
	_fireMessageEvent: function() {
	
		var l=this._listeners;
		if (!l) {
			f_core.Debug("f_messageContext", "["+this._forms+"] No listeners...");
			return;
		}
		
		f_core.Debug("f_messageContext", "Fire event message modifications to "+l.length+" listeners...");
	
		for(var i=0;i<l.length;i++) {
			var listener=l[i];
			
			listener.f_performMessageChanges(this);
		}
	}
}

var __static = {

	/**
	 * @field private static final
	 */
	_GLOBAL_COMPONENT_ID: "",
	
	/**
	 * @field private static final
	 */
	_UNKNOWN_COMPONENT_ID: "?",
	
	/**
	 * @field private static 
	 */
	_Root: undefined,
	
	/**
	 * @method public static
	 * @param HTMLElement component
	 * @return f_messageContext
	 */
	Get: function(component) {
		if (!component) {
			var root=f_messageContext._Root;
			if (root) {
				return root;
			}
			
			root=new f_messageContext();	
			f_messageContext._Root=root;
			
			return root;	
		}
	
		f_core.Assert(component && component.nodeType, "f_messageContext.Get: Bad component parameter ! ("+component+")");
		
		var form=f_core.GetParentForm(component);
		f_core.Assert(form, "f_messageContext.Get: Can not find form associated to component ! (id="+component.id+")");
	
		var ctx=form._messageContext;
		if (ctx!=null) {
			return ctx;
		}
	
		f_core.Info(f_messageContext, "Create a messageContext associated to form '"+form.id+"'.");
	
		ctx=new f_messageContext(form);
		form._messageContext=ctx;
		
		return ctx;	
	},
	
	/**
	 * @method hidden static
	 */
	AppendMessages: function(clientId, messages) {
		var root=f_messageContext.Get();
		
		for(var i=1;i<arguments.length;i++) {
			root.f_addMessageObject(clientId, arguments[i]);
		}
	},
	 
	/**
	 * @method public static
	 * @return String
	 */
	f_getName: function() {
		return "f_messageContext";
	}
}


for(var p in __static) {
	f_messageContext[p]=__static[p];
}

