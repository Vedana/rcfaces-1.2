/*
 * $Id$
 */

/**
 * @class public f_message extends f_component, fa_message
 *
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	FillComponent: function(className, component, summaryLabel, detailLabel, message, styleMessage) {
		var summary=undefined;
		var detail=undefined;
		
		if (message) {
			summary=message.f_getSummary();
			detail=message.f_getDetail();

			if (styleMessage) {
				className=styleMessage;
			}
		}
		
		if (!summary) {
			summary="";
		}
		
		if (!detail) {
			detail="";
		}
		
		component.className=className;
		
		if (summaryLabel) {
			f_core.SetTextNode(summaryLabel, summary);
			summaryLabel.className=className+"_summary";
		}
		
		if (detailLabel) {
			f_core.SetTextNode(detailLabel, detail);
			detailLabel.className=className+"_detail";
		}
	}
}
var __prototype = {
	f_message: function() {
		this.f_super(arguments);

		this._className=this.className;		
	},
	
	/*
	f_finalize: function() {
		this._for=undefined; // string
		this._summaryLabel=undefined; // string 
		this._detailLabel=undefined; // string
		this._currentMessage=undefined; // string
		this._className=undefined; // string

		this.f_super(arguments);
	},
	*/
	
	f_update: function() {
		var messageContext=f_messageContext.Get(this);

		var messages=messageContext.f_listMessages(this.f_getFor());
		if (messages && messages.length>0) {
			this._currentMessage=messages[0];
		}
	
		this._a_updateMessages();

		return this.f_super(arguments);	
	},
	/**
	 * @method public 
	 * @return f_component
	 */
	f_getFor: function() {
		if (this._for===undefined) {
			var s=f_core.GetAttribute(this, "v:for");		
			this._for=(s)?s:null;
		}
		
		return this._for;
	},
	_addMessageObject: function(componentId, messageObject) {
		var ctx=f_messageContext.Get(this);
		
		return ctx.f_addMessageObject(componentId, messageObject, false);
	},
	f_performMessageChanges: function(messageContext) {
		var forComponent=this.f_getFor();
		
		forComponent=fa_namingContainer.ComputeComponentId(this, forComponent);
		
		f_core.Assert(forComponent, "Component id associated to this message is not defined !");
		
		var messages=messageContext.f_listMessages(forComponent);
		var msg;
		if (!messages || messages.length<1) {
			msg=undefined;
			
		} else {
			msg=messages[0];
		}
		
		if (msg==this._currentMessage) {
			f_core.Debug("f_message", "Message changes notification: no modifications !");
			return;
		}

		f_core.Debug("f_message", "Message changes notification: change message !");
		
		this._currentMessage=msg;
		
		this._a_updateMessages();
	},
	_a_updateMessages: function() {
		var message=this._currentMessage;
		var summaryLabel=this._summaryLabel;
		var detailLabel=this._detailLabel;
		
		if (!message) {
			if (summaryLabel) {
				this.removeChild(summaryLabel);
				this._summaryLabel=undefined;
			}
			if (detailLabel) {
				this.removeChild(detailLabel);
				this._detailLabel=undefined;
			}

			this.className=this._className;
			
			return;
		}
		
		var styleMessage=this.f_getStyleClassFromSeverity(message.f_getSeverity());

		if (!summaryLabel && this.f_isShowSummary()) {
			summaryLabel=document.createElement("LABEL");
		}
		
		if (!detailLabel && this.f_isShowDetail()) {
			detailLabel=document.createElement("LABEL");
		}
	
		f_message.FillComponent(this._className, 
			this, 
			summaryLabel, 
			detailLabel, 
			message, 
			styleMessage);
			
		if (summaryLabel && !this._summaryLabel) {
			this._summaryLabel=summaryLabel;
			this.appendChild(summaryLabel);
		}
			
		if (detailLabel && !this._detailLabel) {
			this._detailLabel=detailLabel;
			this.appendChild(detailLabel);
		}
	}
}

var f_message=new f_class("f_message", null, __static, __prototype, f_component, fa_message);
