/*
 * $Id$
 */

/**
 * @class public f_messages extends f_component, fa_message
 */
 
var __static = {
	/**
	 * @method private static
	 */
	_IsEquals: function(array1, array2) {
		
		if (array1===undefined) {
			return (array2===array1);
		}
		
		if (!(array1 instanceof Array) 
				|| !(array2 instanceof Array) 
				|| (array1.length!=array2.length)) {
			return false;
		}
		
		for(var i=0;i<array1.length;i++) {
			if (array1[i]!=array2[i]) {
				return false;
			}
		}
		
		return true;
	}
}
var __prototype = {
/*
	f_messages: function() {
		this.f_super(arguments);
	},
	*/
	f_finalize: function() {
//		this._globalOnly=undefined;  // boolean
		this._currentMessages=undefined; // object[]
		this._tbody=undefined; // HTMLElement

		this.f_super(arguments);
	},
	
	f_update: function() {
		var messageContext=f_messageContext.Get(this);

		this._currentMessages=messageContext.f_listMessages(null, this.f_isGlobalOnly());

		this.fa_updateMessages();		
				
		return this.f_super(arguments);	
	},
	/**
	 * @method public 
	 * @return f_component
	 */
	f_isGlobalOnly: function() {
		if (this._globalOnly!==undefined) {
			return this._globalOnly;
		}
		
		var b=f_core.GetAttribute(this, "v:globalOnly")?true:false;
		this._globalOnly=b;
		return b;
	},
	_addMessageObject: function(global, messageObject) {			
		var ctx=f_messageContext.Get(this);
		
		return ctx.f_addMessageObject(global, messageObject, false);
	},
	f_performMessageChanges: function(messageContext) {	
		var messages=messageContext.f_listMessages(null, this.f_isGlobalOnly());
		
		if (f_messages._IsEquals(messages, this._currentMessages)) {
			f_core.Debug("f_messages", "Messages change events: no changes !");
			return;
		}
		
		f_core.Debug("f_messages", "Messages change events: update "+(messages?messages.length:0)+" messages !");
		
		if (!messages) {
			this._currentMessages=null;
			
		} else {
			var currentMessages=new Array;
			currentMessages.push.apply(currentMessages, messages);
			
			this._currentMessages=currentMessages;
		}

		this.fa_updateMessages();
	},
	fa_updateMessages: function() {
		var messages=this._currentMessages;

		var tbody=this._tbody;
		if (!tbody) {
			if (!messages) {
				f_core.Debug("f_messages", "No body and no messages to update !");
				return;
			}

			tbody=document.createElement("TBODY");

		} else  {
			while (tbody.hasChildNodes()) {
				tbody.removeChild(tbody.lastChild);
			}	

			if (!messages) {
				f_core.Debug("f_messages", "Body cleared and no messages to update !");
				return;
			}
		}
	
		f_core.Debug("f_messages", "Update "+messages.length+" messages.");
		
		for(var i=0;i<messages.length;i++) {
			var message=messages[i];
			
			f_core.Assert(message, "Null message into list of message !");
			
			var styleMessage=this.f_getStyleClassFromSeverity(message.f_getSeverity());
			
			var tr=document.createElement("TR");
			
			var summaryLabel;
			if (this.f_isShowSummary()) {
				summaryLabel=document.createElement("TD");
				tr.appendChild(summaryLabel);
			}
			
			var detailLabel;
			if (this.f_isShowDetail()) {		
				detailLabel=document.createElement("TD");
				tr.appendChild(detailLabel);
			}
			
			f_message.FillComponent(this.className, tr, null, summaryLabel, detailLabel, message, styleMessage);
			
			tbody.appendChild(tr);
		}
		
		if (!this._tbody) {
			this._tbody=tbody;
			this.appendChild(tbody);
		}
	}
}

var f_messages=new f_class("f_messages", null, __static, __prototype, f_component, fa_message);
