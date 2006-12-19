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
		// this._text=undefined;  // String
		// this._accessKey=undefined; // string
		
		// this._disabled=undefined; // boolean
		// this._imageURL=undefined; // string
		// this._hoverImageURL=undefined; // string
		// this._disabledImageURL=undefined; // string
		// this._selectedImageURL=undefined; // string
		
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return this._text;
	},
	/**
	 * @method public
	 * @param String text The text.
	 * @return void
	 */
	f_setText: function(text) {
		if (text == this._text) {
			return;
		}
		this._text=text;
		this.f_setProperty(f_prop.TEXT,text);
		
		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabText(this, text);
		}
	},
	/**
	 * @method public
	 * @param String imageURL
	 * @return void
	 */
	f_setImageURL: function(imageURL) {
		this._imageURL=imageURL;

		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param String imageURL
	 * @return void
	 */
	f_setDisabledImageURL: function(imageURL) {
		this._disabledImageURL=imageURL;

		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabDisabledImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param String imageURL
	 * @return void
	 */
	f_setHoverImageURL: function(imageURL) {
		this._hoverImageURL=imageURL;

		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabHoverImageURL(this, imageURL);
		}
	},
	/**
	 * @method public
	 * @param String imageURL
	 * @return void
	 */
	f_setSelectedImageURL: function(imageURL) {
		this._selectedImageURL=imageURL;

		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabSelectedImageURL(this, imageURL);
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
		
		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setTabDisabled(this, disabled);
		}
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isDisabled: function() {
		return this._disabled;
	},
	f_declareTab: function(tabbedPane, text, accessKey, disabled, imageURL, disabledImageURL, selectedImageURL, hoverImageURL) {
		this.f_declareCard(tabbedPane);

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
		
		var mask=document.createElement("DIV");
		this._mask=mask;
		mask.className=tabbedPane.className+"_tab_mask";
		
		this.insertBefore(mask, this.firstChild);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getAccessKey: function() {
		return this._accessKey;
	},
	f_performAccessKey: function(evt) {
		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_performTabAccessKey(this, evt);
		}
	},
	/**
	 * @method protected
	 * @return void
	 */	 
	f_parentShow: function() {
		if (this.f_isDisabled()) {
			return false;
		}
		
		this._cardBox.f_selectCard(this, false);
		
		return this.f_super(arguments);		
	},
	/**
	 * @method public
	 * @return void
	 */
	f_forceChildVisibility: function(component) {
		//alert("Test: "+component);
		return false;
	}
}

var f_tab=new f_class("f_tab", null, null, __prototype, f_card);
