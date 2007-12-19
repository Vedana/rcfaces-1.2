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

var __members = {
	
	f_tab: function() {
		this.f_super(arguments);
		
		var tabbedPaneClientId=f_core.GetAttribute(this, "v:tabbedPaneId");
		if (tabbedPaneClientId) {
			var properties= {
				_id:				this.id,
				_titleGenerated:	true,
				_value: 			f_core.GetAttribute(this, "v:value"),
				_selected: 			f_core.GetBooleanAttribute(this, "v:selected", false),
				_disabled: 			f_core.GetBooleanAttribute(this, "v:disabled", false),
				_text: 				f_core.GetAttribute(this, "v:text"),
				_accessKey: 		f_core.GetAttribute(this, "v:accessKey"),
				_imageURL: 			f_core.GetAttribute(this, "v:imageURL"),
				_selectedImageURL: 	f_core.GetAttribute(this, "v:selectedImageURL"),
				_hoverImageURL: 	f_core.GetAttribute(this, "v:hoverImageURL"),
				_disabledImageURL: 	f_core.GetAttribute(this, "v:disabledImageURL")
			};
			
			var tabbedPane=f_core.GetElementByClientId(tabbedPaneClientId, this.ownerDocument, true);
			
			tabbedPane.f_declareCard(properties);			
		}
	},
	
	f_finalize: function() {		
		this._mask=undefined;  // HTMLElement
		// this._text=undefined;  // String
		// this._accessKey=undefined; // String
		// this._value=undefined; // String
		
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
		
		this.f_setProperty(f_prop.DISABLED, disabled);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isDisabled: function() {
		return this._disabled;
	},
	/**
	 * @method hidden
	 */
	f_declareTab: function(tabbedPane, value, text, accessKey, disabled, imageURL, disabledImageURL, selectedImageURL, hoverImageURL) {
		this.f_declareCard(tabbedPane, value);

		this._text=text;
		this._accessKey=accessKey;
		this._disabled=disabled;
		this._imageURL=imageURL;
		this._disabledImageURL=disabledImageURL;
		this._hoverImageURL=hoverImageURL;
		this._selectedImageURL=selectedImageURL;
		
		var mask=document.createElement("div");
		this._mask=mask;
		mask.className="f_tabbedPane_tab_mask";
		
		f_core.InsertBefore(this, mask, this.firstChild);
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
	 * @return boolean
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
	 * @return boolean
	 */
	f_forceChildVisibility: function(component) {
		return false;
	}
}

new f_class("f_tab", null, null, __members, f_card);
