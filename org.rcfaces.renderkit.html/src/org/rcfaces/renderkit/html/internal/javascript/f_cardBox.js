/*
 * $Id$
 */

/**
 * @class public f_cardBox extends f_component
 *
 * @author olivier Oeuillot
 * @version $REVISION: $
 */

var __prototype={

	f_cardBox: function() {
		this.f_super(arguments);

		this._cards=new Array;
	},
	f_finalize: function() {
		this._selectedCard=undefined;

		var cards=this._cards;
		if (cards) {
			this._cards=undefined;

			for(var i=0;i<cards.length;i++) {
				var card=cards[i];
				
				// On peut le faire de ce destructeur, ou celui de la card !
				this.f_destroyCard(card);
			}
		}

		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateCards: function() {
		var cards=this._cards;
		for(var i=0;i<cards.length;i++) {
			var card=cards[i];
		
			var ccard=f_core.GetElementById(card._id, this.ownerDocument);
			f_core.Assert(ccard, "Can not find card component of card '"+card._id+"'.");

			f_core.Debug(f_cardBox, "Update card#"+i+" card="+card+" ccard="+ccard);
			card._ccard=ccard;
			ccard._vcard=card;			
			ccard.f_declareCard(this);	
		}
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_destroyCard: function(card) {
		var cards=this._cards;
		if (cards) {
			cards.f_removeElement(card);
		}

		if (!card._cardBox) {
			return;
		}		
		
		// card._id=undefined; // string
		card._cardBox=undefined;

		var ccard=card._ccard;
		f_core.Debug(f_cardBox, "Destroy card: "+card+"  comp="+ccard);
		if (ccard) {
			card._ccard=undefined;
		
			ccard._vcard=undefined;
		}
				
		// Pas forcement les cartes peuvent etre effacées aprés !
		//f_core.VerifyProperties(card);		
	},

	/**
	 * @method hidden
	 * @return void
	 */
	f_setCardFocus: function(card) {
	},

	/**
	 * @method hidden
	 * @return void
	 */
	f_updateCardStyle: function(card) {
	},

	/**
	 * @method hidden
	 * @return void
	 */
	f_declareCard: function(cardBodyId, selected) {
		var card=new Object;

		var cards=this._cards;
		if (cards.length) {
			var prev=cards[cards.length-1];
			prev._next=card;
			card._prev=prev;
		}
		cards.push(card);		
		card._cardBox=this;

		card._id=cardBodyId;
			
		if (selected) {
			this._selectedCard=card;
		}
		
		return card;
	},

	/**
	 * @method public
	 * @param f_card cardComponent Card to select. (or a string as the identifier of the card)
	 * @param boolean setFocus Set focus if possible !
	 * @return boolean
	 */
	f_selectCard: function(cardComponent, setFocus) {
		if (typeof(cardComponent)=="string") {
			var id=cardComponent;
			cardComponent=f_core.GetElementById(id, this.ownerDocument);
	
			f_core.Assert(cardComponent, "Can not find card '"+id+"'.");
		}

		var card=cardComponent._vcard;
		f_core.Assert(card && card._cardBox==this , "Invalid card object ("+card+")");
		
		if (!card) {
			return false;
		}
		
		if (this._selectedCard==card) {
			return false;
		}
		
		var old=this._selectedCard;
		this._selectedCard=null;
		if (old) {
			this.f_updateCardStyle(old);
		}
		
		if (old) {
			old._ccard.f_setVisible(false);
		}
		card._ccard.f_setVisible(true);
		
		this._selectedCard=card;
		this.f_updateCardStyle(card);
		
		this.f_setProperty(f_prop.SELECTED, card._id);
		
		return true;
	},
	
	/**
	 * @method public
	 * @return f_card
	 */
	f_getSelectedCard: function() {
		var sc=this._selectedCard;
		if (!sc) {
			return null;
		}
		
		return sc._ccard;
	}
}
 
var f_cardBox=new f_class("f_cardBox", null, null, __prototype, f_component);
