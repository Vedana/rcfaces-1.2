/*
 * $Id$
 */

/**
 * class Tab.
 *
 * @class f_tab extends f_card
 * @author olivier Oeuillot
 * @version $REVISION: $
 */

var __prototype = {
	f_finalize: function() {		
		this._mask=undefined;  // HTMLElement
		this._text=undefined;  // String
		this._accessKey=undefined; // string
		
		this._disabled=undefined; // boolean
		this._imageURL=undefined; // string
		this._hoverImageURL=undefined; // string
		this._disabledImageURL=undefined; // string
		this._selectedImageURL=undefined; // string
		
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getText: function() {
		return this._text;
	},
	/**
	 * @method public
	 * @param string text The text.
	 * @return void
	 */
	f_setText: function(text) {
		if (text == this._text) {
			return;
		}
		this._text=text;
		this.f_setProperty(f_prop.TEXT,text);
		
		if (this._cardBox) {
			this._cardBox._setTabText(this, text);
		}
	},
	/**
	 * @method public
	 * @param string imageURL
	 * @return void
	 */
	f_setImageURL: function(imageURL) {
		this._imageURL=imageURL;
		if (this._cardBox) {
			this._cardBox._setTabImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param string imageURL
	 * @return void
	 */
	f_setDisabledImageURL: function(imageURL) {
		this._disabledImageURL=imageURL;
		if (this._cardBox) {
			this._cardBox._setTabDisabledImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param string imageURL
	 * @return void
	 */
	f_setHoverImageURL: function(imageURL) {
		this._hoverImageURL=imageURL;
		if (this._cardBox) {
			this._cardBox._setTabHoverImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param string imageURL
	 * @return void
	 */
	f_setSelectedImageURL: function(imageURL) {
		this._selectedImageURL=imageURL;
		if (this._cardBox) {
			this._cardBox._setTabSelectedImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param boolean disabled
	 * @return void
	 */
	f_setDisabled: function(disabled) {
		if (disabled!==false) {
			disabled=true;
		}
		
		if (this._disabled==disabled) {
			return;
		}
		
		this._disabled=disabled;
		
		if (this._cardBox) {
			this._cardBox._setTabDisabled(this, disabled);
		}
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isDisabled: function() {
		return this._disabled;
	},
	_declareTab: function(tabbedPane, text, accessKey, disabled, imageURL, disabledImageURL, selectedImageURL, hoverImageURL) {
		this._declareCard(tabbedPane);

		this._text=text;
		this._accessKey=accessKey;
		if (disabled===undefined) {
			disabled=true;
		}
		this._disabled=disabled;
		this._imageURL=imageURL;
		this._disabledImageURL=disabledImageURL;
		this._hoverImageURL=hoverImageURL;
		this._selectedImageURL=selectedImageURL;
		
		this._mask=document.createElement("DIV");
		this._mask.className=tabbedPane.className+"_tab_mask";
		
		if (this.hasChildNodes()) {
			this.insertBefore(this._mask, this.childNodes[0]);
		} else {
			this.appendChild(this._mask);		
		}
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getAccessKey: function() {
		return this._accessKey;
	},
	f_performAccessKey: function(evt) {
		if (this._cardBox) {
			this._cardBox._performTabAccessKey(this, evt);
		}
	},
	f_parentShow: function() {
		if (this.f_isDisabled()) {
			return false;
		}
		
		this._cardBox.f_selectCard(this, false);
		
		this.f_super(arguments);		
	},
	f_forceChildVisibility: function(component) {
		//alert("Test: "+component);
		return false;
	}
}

var f_tab=new f_class("f_tab", null, null, __prototype, f_card);
