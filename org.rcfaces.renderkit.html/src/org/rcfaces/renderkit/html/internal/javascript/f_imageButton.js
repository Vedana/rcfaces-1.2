/*
 * $Id$
 */
 
/**
 * f_imageButton class
 *
 * @class f_imageButton extends f_component, fa_readOnly, fa_disabled, fa_borderType, fa_images
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */ 
var __static = {

	/**
	 * @field private static final String
	 */
	_EMPTY_IMAGE_URL: "/imageButton/blank.gif"

}
 
var __prototype = {

	f_imageButton: function() {
		this.f_super(arguments);

		var tabIndex=this.tabIndex;
		var link=null;
		var tagName=this.tagName.toLowerCase();
		
		if (tagName=="input") {
			// Il faut recuperer le click pour empecher le submit !
			this._image=this;
			
		} else 	if (tagName=="a") {
			// Il faut recuperer le click pour empecher le submit !
			this._image=f_core.GetFirstElementByTagName(this, "img", true);
						
		} else {
			link=f_core.GetFirstElementByTagName(this, "input", false);
			if (!link) {
				link=f_core.GetFirstElementByTagName(this, "a", true);
			}
			
			if (link) {
				tabIndex=f_core.GetAttribute(this, "tabIndex");
				if (!tabIndex) {
					tabIndex=0;
				}
			
				if (link.tagName.toLowerCase()=="input") {				
					this._image=link;
				} else {
					this._image=f_core.GetFirstElementByTagName(link, "img", false);
					
					f_core.Assert(this._image, "Can not find Image of component '"+this.id+"'.");
				}
				
				this._link=link;
				link.f_link=this;
				
				if (tabIndex>=0) {
					link.tabIndex=tabIndex;
				}

				this.f_openActionList(f_event.SELECTION);
			}
		}
		
		if (!this._image) {
			var images=this.getElementsByTagName("img");
			if (images.length) {
				var cl=this.f_getMainStyleClass()+"_image";
				var image;
				for(var i=0;i<images.length;i++) {
					var img=images[i];
					
					if (img.className!=cl) {
						continue;
					}
					
					image=img;
					break;
				}
				this._image = image;

				f_core.Assert(image, "Can not find IMAGE tag into ImageButton ! (classname='"+cl+"', id="+this.id+")");
			}
		}
		
		var texts=this.getElementsByTagName("span");
		if (texts.length) {
			var cl=this.f_getMainStyleClass()+"_text";
			var text;
			for(var i=0;i<texts.length;i++) {
				var txt=texts[i];
				
				if (txt.className!=cl) {
					continue;
				}
				
				text=txt;
				break;
			}

			f_core.Assert(text, "Can not find SPAN tag into ImageButton ! (classname='"+cl+"', id="+this.id+")");
			
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
			var cmp=(link)?link:this;
			
			cmp.tabIndex=-1;
			cmp.hideFocus=true;
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
		this._link=undefined;
		
		if (text && text!=this) {
			f_core.VerifyProperties(text);
		}
		if (image && image!=this) {
			f_core.VerifyProperties(image);
		}		
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
	_onMouseOver: function() {
		if (this.f_getEventLocked(false)) {
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
			suffix="_pushed";
			
		} else if (this.f_isSelected && this.f_isSelected()) {
			url = this.f_getSelectedImageURL();
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
		
		if (this.className!=className) {
			this.className=className;
		}


		f_core.Debug(f_imageButton, "Update class: "+className+" mouseDown="+this._mouseDown+" selected="+this._selected+" focus="+this._focus);
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
		if (url && image.src!=url) {
			image.src = url;
		}		
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
		var link=this._link;
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
		var link=this._link;
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
		var cmp=(this._link)?this._link:this;

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
			return false;
		}

		f_core.Debug("f_imageButton", "f_setFocus of imageButton '"+this.id+"'.");
		
		var cmp=this._link;
		if (!cmp) {
			cmp=this;
		}
		
		cmp.focus();
	}
}

new f_class("f_imageButton", null, __static, __prototype, f_component, fa_readOnly, fa_disabled, fa_borderType, fa_images, fa_immediate);
