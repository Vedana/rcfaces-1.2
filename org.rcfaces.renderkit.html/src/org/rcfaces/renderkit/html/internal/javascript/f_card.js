/*
 * $Id$
 */

/**
 * class Card
 *
 * @class f_card extends f_component
 * @author olivier Oeuillot
 * @version $REVISION: $
 */
var __prototype = {
	f_card: function() {
		this.f_super(arguments);

		this.f_setHiddenMode(f_component.HIDDEN_MODE_PHANTOM);
	},
	f_finalize: function() {
		var cardBox=this._cardBox;
		if (cardBox) {
			this._cardBox=undefined;

			if (cardBox._destroyCard) {
				cardBox._destroyCard(this);
			}
		}

		this._vcard=undefined;
		
		this.f_super(arguments);
	},
	/**
	 * @method hidden
	 * @return void
	 */
	_declareCard: function(cardBox) {
		this._cardBox=cardBox;
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

		if (this._cardBox) {
			this._cardBox._setCardFocus(this, evt);
		}
	},
	/**
	 * @method hidden
	 * @return void
	 */
	_a_getInteractiveParent: function() {
		return this; //._tabbedPane;
	}
}

var f_card=new f_class("f_card", null, null, __prototype, f_component, fa_asyncRender);
