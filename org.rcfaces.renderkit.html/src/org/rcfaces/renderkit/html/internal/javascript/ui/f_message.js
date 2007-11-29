/*
 * $Id$
 */

/**
 * @class public f_message extends f_component, fa_message1
 *
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
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
var __members = {
	/**
	 * @field private function
	 */
	_onFocusCb: undefined,
	/**
	 * @field private function
	 */
	_onBlurCb: undefined,
	
	f_message: function() {
		this.f_super(arguments);
		
		this._textLabel=f_core.GetFirstElementByTagName(this, "label");

		var image=f_core.GetFirstElementByTagName(this, "img");
		if (image) {
			this._image=image;
			this._imageURL=image.src;
			this._infoImageURL=f_core.GetAttribute(this, "v:infoImageURL");
			this._warnImageURL=f_core.GetAttribute(this, "v:warnImageURL");
			this._errorImageURL=f_core.GetAttribute(this, "v:errorImageURL");
			this._fatalImageURL=f_core.GetAttribute(this, "v:fatalImageURL");
		}

		this._showIfMessage=f_core.GetBooleanAttribute(this, "v:showIfMessage", false);
		this._showActiveComponentMessage=f_core.GetBooleanAttribute(this, "v:showActiveComponentMessage", false);
		if (this._showActiveComponentMessage && !this.f_getFor()) {
			this.f_addFocusAndBlurListener();
		}
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
		// this._showActiveComponentMessage=undefined; //boolean
		
		this.f_removeFocusAndBlurListener();
		
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
			summaryLabel=document.createElement("label");
		}
		
		if (!detailLabel && this.f_isShowDetail()) {
			detailLabel=document.createElement("label");
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
			f_core.AppendChild(this, summaryLabel);
		}
			
		if (detailLabel && !this._detailLabel) {
			this._detailLabel=detailLabel;
			f_core.AppendChild(this, detailLabel);
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
	 * @method protected
	 * @return void
	 */
	f_removeFocusAndBlurListener: function() {
     	f_core.Debug(f_message, "f_removeFocusAndBlurListener: remove focus & blur hooks");
		
		if (this._onFocusCb) {
			var capture = undefined;
			if (!f_core.IsInternetExplorer()) {
				capture = document;
			}
			f_core.RemoveEventListener(document, "focus", this._onFocusCb, capture);
			this._onFocusCb=undefined; // function
		}
	
		if (this._onBlurCb) {
			f_core.RemoveEventListener(document, "blur", this._onBlurCb);
			this._onBlurCb=undefined; // function
		}
	},

	/**
	 * @method protected
	 * @return void
	 */
	f_addFocusAndBlurListener: function() {
     	f_core.Debug(f_message, "f_addFocusAndBlurListener: Install focus eblur hooks");
		
		var message=this;
		
		this._onFocusCb=function(evt) {
	    	if (window._rcfacesExiting) {
	     		// On sait jamais, nous sommes peut etre dans un context foireux ...
	     		return;
	     	}
	 
	 		if (!evt) {
				evt = f_core.GetJsEvent(this);
			}
  			
  			f_core.Debug(f_message, "anonymous this._onFocusCb on :"+this.id);
  			message._performOnFocus(evt);
		}
		
		var capture = undefined;
		if (!f_core.IsInternetExplorer()) {
			capture = document;
		}
		f_core.AddEventListener(document, "focus", this._onFocusCb, capture);

/*
		this._onBlurCb=function(evt) {
	    	if (window._rcfacesExiting) {
	     		// On sait jamais, nous sommes peut etre dans un context foireux ...
	     		return;
	     	}
	 
	 		if (!evt) {
				evt = f_core.GetJsEvent(this);
			}
  			
  			message._performOnBlur(evt);
		}
		
		f_core.AddEventListener(document, "blur", this._onBlurCb);
*/
	},

	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_performOnFocus: function(evt) {
     	f_core.Debug(f_message, "_performOnFocus: entering ");

		if (!evt) {
				evt = f_core.GetJsEvent(this);
		}

		var target;
		if (evt.target) {
			target = evt.target;
			
		} else if (evt.srcElement) {
			target = evt.srcElement;
		}
     	f_core.Debug(f_message, "_performOnFocus: target : "+target.id+"\ntarget "+target.tagName);
		var compId = target.id;
		if (compId) {
			var msgCtx=f_messageContext.Get(target);
			var listMsg=msgCtx.f_listMessages(compId);
			if (listMsg.length == 0) {
				compId=null;
			}
		}		
		this._fors=this._forsTranslated=[compId];
		this.f_performMessageChanges();
		
	},
	/**
	 * @method private
	 * @param Event evt
	 * @return void
	 */
	_performOnBlur: function(evt) {
     	f_core.Debug(f_message, "_performOnBlur: entering");

		this._fors=this._forsTranslated=[null];
		this.f_performMessageChanges();
		
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
			textLabel=document.createElement("label");
			
			var message=this._currentMessage;
			if (!message) {
				textLabel.style.display="none";
			}
			
			f_core.AppendChild(this, textLabel);
		
			this._textLabel=textLabel;
		}
		
		f_core.SetTextNode(textLabel, text);

		this.f_setProperty(f_prop.TEXT, text);
	}
}

new f_class("f_message", {
	extend: f_component, 
	aspects: [ fa_message1 ],
	statics: __statics,
	members: __members
});