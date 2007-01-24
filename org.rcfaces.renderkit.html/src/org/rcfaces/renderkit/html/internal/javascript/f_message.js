/*
 * $Id$
 */

/**
 * @class public f_message extends f_component, fa_message1
 *
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @method hidden static
	 */
	FillComponent: function(className, component, textLabel, summaryLabel, detailLabel, message, styleMessage) {
		var summary=undefined;
		var detail=undefined;
		
		if (message) {
			summary=message.f_getSummary();
			detail=message.f_getDetail();

			if (styleMessage) {
				className=" "+styleMessage;
			}
		}
		
		if (!summary) {
			summary="";
		}
		
		if (!detail) {
			detail="";
		}
		
		if (component.className!=className) {
			component.className=className;
		}
		
		if (textLabel) {
			textLabel.style.display="none";
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
		
		var labels=this.getElementsByTagName("LABEL");
		if (labels.length) {
			this._textLabel=labels[0];
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

		this._showIfMessage=f_core.GetAttribute(this, "v:showIfMessage");
	},
	
	f_finalize: function() {
		this._summaryLabel=undefined; // HTMLLabelElement 
		this._detailLabel=undefined; // HTMLLabelElement
 		this._textLabel=undefined; // HTMLLabelElement
 		this._image=undefined; // HTMLImageElement
 
		// MESSAGE1: this._currentMessage=undefined; // f_messageObject
		// MESSAGE1: this._for=undefined; // string
		
		// this._imageURL=undefined; // string
		// this._infoImageURL=undefined; // string
		// this._warnImageURL=undefined; // string
		// this._errorImageURL=undefined; // string
		// this._fatalImageURL=undefined; // string
		// this._showIfMessage=undefined; // boolean

		this.f_super(arguments);
	},
	fa_updateMessages: function() {
		var summaryLabel=this._summaryLabel;
		var detailLabel=this._detailLabel;
		var textLabel=this._textLabel;
		var image=this._image;
		
		var message=this._currentMessage;
		if (!message) {
			var cls=this.f_computeStyleClass();
			if (this.className!=cls) {
				this.className=cls;
			}

			if (summaryLabel && summaryLabel.style.display!="none") {
				summaryLabel.style.display="none";
			}
			if (detailLabel && detailLabel.style.display!="none") {
				detailLabel.style.display="none";
			}
			if (textLabel && textLabel.style.display!="inline") {
				textLabel.style.display="inline";
			}
			
			if (image) {
				this._changeImageURL(image, this._imageURL);
			}
							
			if (this._showIfMessage) {
				this.f_setVisible(false);
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
	
		f_message.FillComponent(this.f_computeStyleClass(), 
			this, 
			textLabel,
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
							
			if (this._showIfMessage) {
				this.f_setVisible(true);
			}
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
	},
	/**
	 * @method public
	 * @return String Text if no message is shown.
	 */
	f_getText: function() {
		var text=this._textLabel;
		if (!text) {
			return "";
		}
		
		return f_core.GetTextNode(text);
	},
	/**
	 * @method public
	 * @param String text Text if no message is shown.
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(typeof(text)=="string", "f_message.f_setText: Invalid text parameter ('"+text+"').");
		
		var textLabel=this._textLabel;
		if (!textLabel) {
			textLabel=document.createElement("LABEL");
			
			var message=this._currentMessage;
			if (!message) {
				textLabel.style.display="none";
			}
			
			this.appendChild(textLabel);
		
			this._textLabel=textLabel;
		}
		
		f_core.SetTextNode(textLabel, text);

		this.f_setProperty(f_prop.TEXT, text);
	}
}

new f_class("f_message", null, __static, __prototype, f_component, fa_message1);
