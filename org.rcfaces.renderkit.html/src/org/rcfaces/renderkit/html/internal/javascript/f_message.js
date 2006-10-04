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
	/**
	 * @method hidden static
	 */
	FillComponent: function(className, component, noMessageLabel, summaryLabel, detailLabel, message, styleMessage) {
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
		
		if (noMessageLabel) {
			noMessageLabel.style.display="none";
		}
		
		if (summaryLabel) {
			f_core.SetTextNode(summaryLabel, summary);
			summaryLabel.className=className+"_summary";
			summaryLabel.style.display="inline";
		}
		
		if (detailLabel) {
			f_core.SetTextNode(detailLabel, detail);
			detailLabel.className=className+"_detail";
			detailLabel.style.display="inline";
		}
	}
}
var __prototype = {
	f_message: function() {
		this.f_super(arguments);

		this._className=this.className;	
		
		var labels=this.getElementsByTagName("LABEL");
		if (labels.length) {
			this._noMessageLabel=labels[0];
		}

		var images=this.getElementsByTagName("IMG");
		if (images.length) {
			this._image=images[0];
			
			this._imageURL=this._image.src;
			this._infoImageURL=f_core.GetAttribute(this, "v:infoImageURL");
			this._warnImageURL=f_core.GetAttribute(this, "v:warnImageURL");
			this._errorImageURL=f_core.GetAttribute(this, "v:errorImageURL");
			this._fatalImageURL=f_core.GetAttribute(this, "v:fatalImageURL");
		}
	},
	
	f_finalize: function() {
		this._summaryLabel=undefined; // HTMLLabelElement 
		this._detailLabel=undefined; // HTMLLabelElement
		this._currentMessage=undefined; // f_messageObject
 		this._noMessageLabel=undefined; // HTMLLabelElement
 		this._image=undefined; // HTMLImageElement
 
		// this._for=undefined; // string
		// this._className=undefined; // string
		// this._imageURL=undefined; // string
		// this._infoImageURL=undefined; // string
		// this._warnImageURL=undefined; // string
		// this._errorImageURL=undefined; // string
		// this._fatalImageURL=undefined; // string

		this.f_super(arguments);
	},
	
	f_update: function() {
		var messageContext=f_messageContext.Get(this);

		var messages=messageContext.f_listMessages(this.f_getFor());
		if (messages && messages.length>0) {
			this._currentMessage=messages[0];
		}
	
		this.fa_updateMessages();

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
	/**
	 * @method private
	 */
	_addMessageObject: function(componentId, messageObject) {
		var ctx=f_messageContext.Get(this);
		
		return ctx.f_addMessageObject(componentId, messageObject, false);
	},
	f_performMessageChanges: function(messageContext) {
		var forComponent=this.f_getFor();
		
		if (forComponent) {
			forComponent=fa_namingContainer.ComputeComponentId(this, forComponent);
			
			f_core.Assert(forComponent, "Component id associated to this message is not defined !");

		} else {
			forComponent=null; // Normalize :-)
		}
				
		var messages=messageContext.f_listMessages(forComponent);
		var msg=messages[0];
		
		if (msg==this._currentMessage) {
			f_core.Debug("f_message", "Message changes notification: no modifications !");
			return;
		}

		f_core.Debug("f_message", "Message changes notification: change message !");
		
		this._currentMessage=msg;
		
		this.fa_updateMessages();
	},
	fa_updateMessages: function() {
		var message=this._currentMessage;
		var summaryLabel=this._summaryLabel;
		var detailLabel=this._detailLabel;
		var noMessageLabel=this._noMessageLabel;
		var image=this._image;
		
		if (!message) {
			this.className=this._className;

			if (summaryLabel && summaryLabel.style.display!="none") {
				summaryLabel.style.display="none";
			}
			if (detailLabel && detailLabel.style.display!="none") {
				detailLabel.style.display="none";
			}
			if (noMessageLabel && noMessageLabel.style.display!="inline") {
				noMessageLabel.style.display="inline";
			}
			
			if (image) {
				this._changeImageURL(image, this._imageURL);
			}
			
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
			noMessageLabel,
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
		
		if (image) {
			var imageURL=null;
			
			switch(message.f_getSeverity()) {
			case f_messageObject.SEVERITY_FATAL:
				imageURL=this._fatalImageURL;
				if (imageURL) {
					break;
				}
				
			case f_messageObject.SEVERITY_ERROR:
				imageURL=this._errorImageURL;
				if (imageURL) {
					break;
				}
				
			case f_messageObject.SEVERITY_WARN:
				imageURL=this._warnImageURL;
				if (imageURL) {
					break;
				}
				
			case f_messageObject.SEVERITY_INFO:
				imageURL=this._infoImageURL;
				if (imageURL) {
					break;
				}
			
			default:
				imageURL=this._imageURL;
			}
			
			this._changeImageURL(image, imageURL);
		}
	},
	/**
	 * @method private
	 */
	_changeImageURL: function(image, imageURL) {
		var style=image.style;
		if (imageURL) {
			if (style.display=="none") {
				style.display="inline";
			}

			// On teste pas avant, car il peut y avoir des animations !
			image.src=imageURL;
	
			return;
		}

		if (style.display!="none") {
			style.display="none";
		}
	}
}

var f_message=new f_class("f_message", null, __static, __prototype, f_component, fa_message);
