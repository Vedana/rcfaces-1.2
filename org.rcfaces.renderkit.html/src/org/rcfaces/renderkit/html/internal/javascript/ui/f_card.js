/*
 * $Id$
 */

/**
 * class Card
 *
 * @class f_card extends f_component, fa_asyncRender
 * @author olivier Oeuillot
 * @version $REVISION: $
 */
var __members = {
	f_card: function() {
		this.f_super(arguments);

		this.f_setHiddenMode(f_component.HIDDEN_MODE_IGNORE);
	},
	f_finalize: function() {
		var cardBox=this._cardBox;
		this._cardBox=undefined; // f_cardBox

//		this._value=undefined; // String

		var vcard=this._vcard;
		if (vcard) {
			// On efface la trace de cette carte .. 
			// Pour que le VerifyComponent soit correcte
			if (cardBox.f_destroyCard) {
				cardBox.f_destroyCard(vcard);
			}

			// On verifie tout de meme ....
			this._vcard=undefined;
		}
		
		this.f_super(arguments);
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_declareCard: function(cardBox, value) {
		this._cardBox=cardBox;
		this._value=value;
	},
	/**
	 * @method public
	 * @param optional Event evt Optional javascript event.
	 * @return void
	 */
	f_setFocus: function(evt) {
		if (this.f_isDisabled()) {
			return;
		}

		var cardBox=this._cardBox;
		if (cardBox) {
			cardBox.f_setCardFocus(this, evt);
		}
	},
	/**
	 * @method hidden
	 * @return HTMLElement
	 */
	fa_getInteractiveParent: function() {
		return this; //._tabbedPane;
	},
	/**
	 * @method public
	 * @return String value of the card.
	 */
	f_getValue: function() {
		return this._value;
	}
}

new f_class("f_card", null, null, __members, f_component, fa_asyncRender);
