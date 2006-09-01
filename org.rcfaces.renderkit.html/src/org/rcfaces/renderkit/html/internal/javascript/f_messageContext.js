/*
 * $Id$
 */

/**
 * Class Message Context
 *
 * @class public f_messageContext extends Object
 * @author Olivier Oeuillot
 * @version $Revision$
 */

function f_messageContext(form) {
	this._form=form;
	
	f_core.AddCheckListener(form, this);
}
	
f_messageContext.prototype.f_finalize=function() {
	this._listeners=undefined; // function[]
	this._messages=undefined;  // f_messageObject[]
	this._form=undefined; // HTMLFormElement
}

/**
 * @method hidden
 * @param HTMLElement form
 * @return void
 */
f_messageContext.prototype.f_performCheckPre=function(form) {
	this._blockEvent=true;
	this.f_clearMessages();
}

/**
 * @method hidden
 * @param boolean result
 * @param HTMLElement form
 * @return boolean 
 */
f_messageContext.prototype.f_performCheckPost=function(result, form) {
	// Methode appelée lors du check de la form !
	this._blockEvent=undefined;
	
	this._fireMessageEvent();
	
	if (!result) {
		// C'est déjà bloqué !
		return result;
	}
	
	var messages=this._messages;
	if (!messages) {
		return true;
	}
	
	// On bloque si il y a des erreurs !
	for(var i=0;i<messages.length;i++) {
		var message=messages[i];
		
		if (message.f_getSeverity()<f_messageObject.SEVERITY_ERROR) {
			continue;
		}
		
		return false;
	}
	
	return true;
}

/**
 * @method hidden
 * @param HTMLElement component 
 * @param Function listener
 * @return boolean 
 */
f_messageContext.prototype.f_addMessageListener=function(listener) {
	var l=this._listeners;
	if (!l) {
		l=new Array;
		this._listeners=l;
	}

	f_core.Debug("f_messageContext", "Add a new message event listener !");
	
	return l.f_addElement(listener);
}

/**
 * @method hidden
 * @param HTMLElement component 
 * @param Function listener
 * @return boolean 
 */
f_messageContext.prototype.f_removeMessageListener=function(listener) {
	var l=this._listeners;
	if (!l) {
		return false;
	}
	
	return l.f_removeElement(listener);
}

/**
 * 
 */
f_messageContext.prototype.f_listMessages=function(componentId, globalOnly) {
	var msgs=this._messages;
	if (!msgs) {
		return null;
	}
	
	if (globalOnly) {
		componentId=f_messageContext._GLOBAL_COMPONENT_ID;	
	}
	
	if (typeof(componentId)=="string") {
		var l=msgs[componentId];
		return (l)?l:null;
	}
	
	var l=new Array;
	for(var cid in msgs) {		
		l.push.apply(l, msgs[cid]);
	}
	
	return l;
}

/**
 * @method public
 *
 * @param HTMLElement componentOrId Component to add the message, or its id.
 * @param f_messageObject message
 * @param hidden boolean performEvent
 * @return void
 */
f_messageContext.prototype.f_addMessageObject=function(componentOrId, message, performEvent) {	
	f_core.Assert(componentOrId===null || componentOrId===false || typeof(componentOrId)=="string" || componentOrId.id, "Component parameter must be a component or an id !");

	var id;
	if (componentOrId && componentOrId.id) {
		id=componentOrId.id;
		
	} else if (typeof(componentOrId)=="string") {
		id=fa_namingContainer.ComputeComponentId(this._form, componentOrId);

	} else {
		id=componentOrId;
	}

	var l=this._messages;
	if (!l) {
		l=new Object;
		this._messages=l;
	}
	
	if (id===false) {
		id=f_messageContext._UNKNOWN_COMPONENT_ID;
			
	} else if (id===null) {
		id=f_messageContext._GLOBAL_COMPONENT_ID;
	}

	var l2=l[id];
	if (!l2) {
		l2=new Array;
		l[id]=l2;
	}

	f_core.Info(f_messageContext, "Add message object to component '"+id
		+"'.\nseverity="+message.f_getSeverity()
		+"\nsummary="+message.f_getSummary()
		+"\ndetail="+message.f_getDetail());

	l2.push(message);
	
	if (performEvent!==false) {
		this._fireMessageEvent();
	}
}

/**
 * @method public
 *
 * @param HTMLElement component Component to add the message, or its id.
 * @param number severity
 * @param string summary
 * @param string detail
 * @return f_messageObject
 */
f_messageContext.prototype.f_addMessage=function(component, severity, summary, detail) {
	f_core.Assert(typeof(severity)=="number", "Bad type of severity !");
	f_core.Assert(summary, "Summary is null !");
	f_core.Assert(component===null || typeof(component)=="string" || component.id, "Component parameter must be a component or an id or null !");
	
	var message=new f_messageObject(severity, summary, detail);
	
	this.f_addMessageObject(component, message);
	
	return message;
}

/**
 * @method public
 * @param HTMLElement component (or an id)
 * @return boolean 
 */
f_messageContext.prototype.f_clearMessages=function(component) {
	var l=this._messages;
	if (!l) {
		return false;
	}
	
	if (component===undefined) {
		this._messages=undefined;

		f_core.Info(f_messageContext, "Clear all messages.");
		
		this._fireMessageEvent();
		return true;
	}
	
	var componentId=component;
	if (component && component.id) {
		componentId=component.id;
		
	} else if (component===null) {
		componentId=f_messageContext._GLOBAL_COMPONENT_ID;
	}
	
	var l2=l[componentId];
	if (!l2) {
		return false;
	}
	
	l[componentId]=undefined;

	f_core.Info(f_messageContext, "Clear message of component '"+componentId+"'.");

	this._fireMessageEvent();
	return true;
}

/**
 * @method private
 * @return void
 */
f_messageContext.prototype._fireMessageEvent=function() {
	var l=this._listeners;
	if (!l) {
		return;
	}
	
	if (this._blockEvent) {
		return;
	}

	f_core.Debug("f_messageContext", "Fire event message modifications to "+l.length+" listeners...");

	for(var i=0;i<l.length;i++) {
		var listener=l[i];
		
		listener.f_performMessageChanges(this);
	}
}

/**
 * @field private static final
 */
f_messageContext._GLOBAL_COMPONENT_ID="";

/**
 * @field private static final
 */
f_messageContext._UNKNOWN_COMPONENT_ID="?";


/**
 * @method public static final
 */
f_messageContext.Get=function(component) {
	f_core.Assert(component && component.nodeType, "f_messageContext.Get: Bad component parameter ! ("+component+")");
	
	var form=f_core._GetParentForm(component);
	f_core.Assert(form, "f_messageContext.Get: Can not find form associated to component ! (id="+component.id+")");

	var ctx=form._messageContext;
	if (ctx!=null) {
		return ctx;
	}

	f_core.Info(f_messageContext, "Create a messageContext associated to form '"+form.id+"'.");

	ctx=new f_messageContext(form);
	form._messageContext=ctx;
	
	return ctx;	
}

/**
 * @method public static final
 */
f_messageContext.f_getName=function() {
	return "f_messageContext";
}
