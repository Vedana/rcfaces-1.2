/*
 * $Id$
 */
 
/**
 * f_imageButton class
 *
 * @class f_imageButton extends f_component, fa_readOnly, fa_disabled, fa_borderType, fa_images, fa_immediate
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 
var __statics = {

	/**
	 * @field private static final String
	 */
	_EMPTY_IMAGE_URL: "/imageButton/blank.gif",
	
	/**
	 * @field private static final String
	 */	  
	_IMAGE_ID_SUFFIX: "::image",

	/**
	 * @field private static final String
	 */	  
	_INPUT_ID_SUFFIX: "::input",
	
	/**
	 * @field private static final String
	 */	  
	_TEXT_ID_SUFFIX: "::text"
}
 
var __members = {

	f_imageButton: function() {
		this.f_super(arguments);

		var tabIndex=this.tabIndex;
		
		var eventComponent=this.f_getEventComponent();		
		if (eventComponent!=this) {
			this.f_openActionList(f_event.SELECTION);		
		}
		
		if (!this._image) {
			this._image=this.ownerDocument.getElementById(this.id+f_imageButton._IMAGE_ID_SUFFIX);
		}
		
		var text=this.ownerDocument.getElementById(this.id+f_imageButton._TEXT_ID_SUFFIX);
		if (text) {
			this._text = text;
			text.f_link=this;
	
//			text.onmousedown=f_imageButton._MouseDown;
//			text.onmouseup=f_imageButton._MouseUp;
		}
			
		this.f_parseAttributes();
	
		var border=this.f_getBorderComponent();
		if (border) {
//			border.onmousedown=f_imageButton._MouseDown;
//			border.onmouseup=f_imageButton._MouseUp;
			this._border=border;	
		}

		var image=this._image;
		if (image) {
//			image.onmousedown=f_imageButton._MouseDown;
//			image.onmouseup=f_imageButton._MouseUp;
				
			image.f_link=this;
		}
		
		this.f_insertEventListenerFirst(f_event.SELECTION, this.f_imageButtonSelect);
		this.f_insertEventListenerFirst(f_event.MOUSEDOWN, this._onMouseDown);
		this.f_insertEventListenerFirst(f_event.MOUSEUP, this._onMouseUp);
		
		this._tabIndex=tabIndex;
		if (this.f_isDisabled()) {			
			eventComponent.tabIndex=-1;
			eventComponent.hideFocus=true;
		}
	},
	f_finalize: function() {
		// this._focus=undefined; // boolean
		// this._tabIndex=undefined; // number
		
		// this._hover=undefined; // boolean
		// this._hoverInstalled=undefined; // boolean
		// this._mouseDown = undefined; // boolean
		// this._mouseDown_out = undefined;	// boolean
		
		var image=this._image;
		if (image) {
			this._image=undefined;
			
			image.onmousedown=null;
			image.onmouseup=null;
			
			image.f_link=undefined;
		}

		var text=this._text;
		if (text) {
			this._text=undefined;
			
			text.onmousedown=null;
			text.onmouseup=null;
			text.f_link=undefined;
		}
		
		// Le border est traité par le borderFinalizer !

		this.onmousedown=null;
		this.onmouseup=null;

		this.f_super(arguments);

		// Ce n'est plus qu'un indicateur,
		// car c'est soit NULL ou _image !
		// On efface le link aprés car on en a besoin lors du clearDomEvent !
		var eventComponent=this._eventComponent;
		this._eventComponent=undefined;
		
		if (eventComponent!=this) {
			eventComponent.f_link=undefined;
			
			f_core.VerifyProperties(eventComponent);
		}
		
		if (text && text!=this) {
			f_core.VerifyProperties(text);
		}
		if (image && image!=this) {
			f_core.VerifyProperties(image);
		}		
	},
	
	/**
	 * @method protected
	 * @return HTMLElement
	 */
	f_getEventComponent: function() {
		var eventComponent=this._eventComponent;
		if (eventComponent!==undefined) {
			return eventComponent;
		}
		
		eventComponent=this;
		
		switch(this.tagName.toLowerCase()) {
		case "input":
			// Il faut recuperer le click pour empecher le submit !
			this._image=this;
			break;

		case "a":
			// Il faut recuperer le click pour empecher le submit !
			this._image=this.ownerDocument.getElementById(this.id+f_imageButton._IMAGE_ID_SUFFIX);
			break;

		default:
			var image=this.ownerDocument.getElementById(this.id+f_imageButton._IMAGE_ID_SUFFIX);
			
			if (image) {
				var tabIndex=f_core.GetAttribute(this, "tabIndex");
				if (!tabIndex) {
					tabIndex=0;
				}
			
				this._image=image;
				var link=image;
			
				if (image.tagName.toLowerCase()!="input") {				
					var input=this.ownerDocument.getElementById(this.id+f_imageButton._INPUT_ID_SUFFIX);
					if (input) {
						link=input;
					}
			//if (!link) { // OO: On est passé en ID !
				// Fred : to allow imageButton without image !
			//	link=f_core.GetFirstElementByTagName(this, "a", false);
			//}
				}
				
				eventComponent=link;
				link.f_link=this;
				
				if (tabIndex>=0) {
					link.tabIndex=tabIndex;
				}
			}
		}
		
		this._eventComponent=eventComponent;
		
		return eventComponent;
	},
	
	/**
	 * @method private
	 */
	fa_borderFinalizer: function(border) {
		border.onmousedown=null;
		border.onmouseup=null;
	},
	
	/**
	 * 
	 * @method protected
	 */
	f_parseAttributes: function() {
		if (this.f_getBorderType()!=fa_borderType.NONE_BORDER_TYPE) {			
			this._installHoverFocus();
		}

		if (this._image) {
			this._parseImageURLs(this._image.src);
		
			if (this.f_getHoverImageURL()) {
				this._installHoverFocus();
			}
		}
	},
	/**
	 * @method protected
	 */
	f_imageButtonSelect: function(event) {
		f_core.Debug(f_imageButton, "f_imageButtonSelect: focus="+this._focus);
		
		this._mouseDown_out = undefined;	
		this._mouseDown = undefined;	
		
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {		
			return false;
		}

		this.checked = false;
//		event._eventReturn=false; // Force l'arret
	
		return this.f_performImageSelection(event);
	},
	/**
	 * @method protected
	 * @param f_event event
	 * @return boolean
	 */
	f_performImageSelection: function(event) {
		return true;
	},
	/**
	 * @method private
	 */
	_onMouseDown: function() {
		f_core.Debug(f_imageButton, "onMouseDown on imageButton '"+this.id+"'.");
		
		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		this.f_updateLastFlatBorder();

		this._mouseDown = true;		
		this._mouseDown_out = false;
		this._updateImage();
		
		if (!this._focus) {
			this.f_setFocus();
		}
		
		return true;				
	},

	/**
	 * @method private
	 */
	_onMouseUp: function() {
		if (!this._mouseDown) {
			return false;
		}
		
		this._mouseDown_out = undefined;	
		this._mouseDown = undefined;	
	//	this._updateImage();
		return true;				
	},

	/**
	 * @method private
	 */
	_onMouseOver: function(event) {
		if (this.f_getEventLocked(event.f_getJsEvent(), false)) {
			return false;
		}
		
		this.f_updateLastFlatBorder();

		this._hover=true;
		
		this._updateImage();
		return true;
	},

	/**
	 * @method private
	 */
	_onMouseOut: function() {
		if (!this._hover) {
			return true;
		}
		
		this._mouseDown = undefined;		
		this._hover = undefined;
		
		this._updateImage();
		
		return true;
	},

	/**
	 * @method private
	 */
	_onFocus: function() {
		this.f_updateLastFlatBorder();
		
		this._focus=true;
		
		this._updateImage();
		return true;
	},

	/**
	 * @method private
	 */
	_onBlur: function() {
		this._focus = undefined;

		this._updateImage();
		return true;
	},

	/**
	 * @method protected
	 */
	_updateImage: function() {
		var url=null;
		
		var suffix="";
		var ignoreFlat;
		if (this.f_isDisabled()) {
			url = this.f_getDisabledImageURL();
			suffix="_disabled";

		} else if (this._mouseDown) {
			url = this.f_getSelectedImageURL();
			if (!url) {
				url = this.f_getHoverImageURL();
			}
			suffix="_pushed";
			
		} else if (this.f_hasSelectedState()) {
			url = this.f_getSelectedImageURL();
			if (!url) {
				url = this.f_getHoverImageURL();
			}
			suffix="_selected";
			ignoreFlat=suffix;

			if (this._focus) {
				suffix+="_focus";

			} else if (this._hover) {
				suffix+="_hover";	
			}
						
		} else if (this._hover) {
			url = this.f_getHoverImageURL();
			ignoreFlat=suffix;
			suffix="_hover";

		} else if (this._focus) {
//			url = this.f_getImageURL();
			ignoreFlat=suffix;
			suffix="_focus";

		} else {
//			url = this.f_getImageURL();
//			suffix="";
		}
		
		var className=this.f_computeStyleClass(suffix);
		
		f_core.Debug(f_imageButton, "_updateImage: disabled="+this.f_isDisabled()+" mouseDown="+this._mouseDown+" selected="+(this.f_isSelected?this.f_isSelected():undefined)+" hover="+this._hover+" focus="+this._focus+" => url="+url+" suffix="+suffix+" className="+className);				
		
		if (this.className!=className) {
			this.className=className;
		}


		f_core.Debug(f_imageButton, "_updateImage: Update class: "+className+" mouseDown="+this._mouseDown+" selected="+this._selected+" focus="+this._focus);
/*		
		var text=this._text;
		if (text) {
			className=this.f_getMainStyleClass()+"_text"+suffix;
		
			if (text.className!=className) {
				text.className=className;
			}			
		}		
*/		
		if (!url) {
			url=this.f_getImageURL();
			if (!url) {
				url=f_env.GetStyleSheetBase() + f_imageButton._EMPTY_IMAGE_URL;
			}
		}
		
		var border=this.f_getBorderComponent();
		if (border && border._className) {
			var borderClassName=border._className+suffix;
			
			if (this.f_isFlatTypeBorder() && ignoreFlat!==undefined && this!=fa_borderType.GetCurrentBorder()) {
				borderClassName=border._className+ignoreFlat;
			}
			
			if (border.className!=borderClassName) {
				border.className=borderClassName;
			}
		}

		var image=this._image;		
		if (image && url && image.src!=url) {
			image.src = url;
		}		
	},
	/**
	 * @method protected
	 * @return boolean
	 */
	f_hasSelectedState: function() {
		return this.f_isSelected && this.f_isSelected();		
	},
	/**
	 * @method protected
	 */
	_installHoverFocus: function() {
		if (this._hoverInstalled) {
			return;
		}

		this._hoverInstalled = true;
		this.f_insertEventListenerFirst(f_event.MOUSEOVER, this._onMouseOver);
		this.f_insertEventListenerFirst(f_event.MOUSEOUT, this._onMouseOut);
		this.f_insertEventListenerFirst(f_event.FOCUS, this._onFocus);
		this.f_insertEventListenerFirst(f_event.BLUR, this._onBlur);
	},
	f_setDomEvent: function(type, target) {
		var link=this.f_getEventComponent();
		if (link) {
			switch(type) {
/*			case f_event.SELECTION: 
*/
			case f_event.BLUR: 
			case f_event.FOCUS: 
			case f_event.KEYDOWN: 
			case f_event.KEYUP: 
				target=link;
				break;
			}
		}
						
		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		var link=this.f_getEventComponent();
		if (link) {
			switch(type) {
/*			case f_event.SELECTION: 
*/
			case f_event.BLUR: 
			case f_event.FOCUS: 
			case f_event.KEYDOWN: 
			case f_event.KEYUP: 
				target=link;
				break;
			}
		}
				
		this.f_super(arguments, type, target);
	},
	fa_updateImages: function(prop, url) {
		if (prop==f_prop.HOVER_IMAGE_URL && url) {
			this._installHoverFocus();
		}
	
		this._updateImage();
	},
	fa_updateDisabled: function(disabled) {
		var cmp=this.f_getEventComponent();

		if (disabled) {
			cmp.tabIndex=-1;
			cmp.hideFocus=true;
			
		} else {
			cmp.tabIndex=this._tabIndex;
			cmp.hideFocus=false;
		}

		if (!this.fa_componentUpdated) {
			return;
		}
		
		this._updateImage();
	},
	fa_updateReadOnly: function() {
	},
	f_update: function() {
		this._updateImage();
		
		this.f_super(arguments);
	},
	/**
	 * Set the text of the button
	 *
	 * @method public 
	 * @param String text
	 * @return void
	 */	
	f_setText: function(text) {
		if (!this._text) {
			return;
		}
		
		f_core.SetTextNode(this._text, text, this._accessKey);
		
		this.f_setProperty(f_prop.TEXT,text);
	},
	/**
	 * Returns the text of the button.
	 * 
	 * @method public 
	 * @return boolean
	 */	
	f_getText: function() {
		if (!this._text) {
			return null;
		}
		
		return f_core.GetTextNode(this._text, true);
	},
	/**
	 * Set the focus to this component.
	 *
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this.f_isDisabled()) {
			return;
		}

		f_core.Debug(f_imageButton, "f_setFocus: Set focus on imageButton '"+this.id+"'.");
		
		var cmp=this.f_getEventComponent();
		if (!cmp) {
			cmp=this;
		}
		
		cmp.focus();
	}
}

new f_class("f_imageButton", {
	extend: f_component, 
	aspects: [ fa_readOnly, fa_disabled, fa_borderType, fa_images, fa_immediate ],
	statics: __statics,
	members: __members
});