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
				this._destroyCard(card);
			}
		}

		this.f_super(arguments);
	},
	/**
	 * @method hidden
	 * @return void
	 */
	_updateCards: function() {
		var cards=this._cards;
		for(var i=0;i<cards.length;i++) {
			var card=cards[i];
		
			var body=f_core.GetElementById(card._id, this.ownerDocument);
			f_core.Assert(body, "Can not find body of card '"+card._id+"'.");

			card._body=body;
			body._vcard=card;			
			body._declareCard(this);	
		}
	},
	/**
	 * @method hidden
	 * @return void
	 */
	_destroyCard: function(card) {
		card._id=undefined;
		card._cardBox=undefined;

		var vcard=card._vcard;
		if (vcard) {
			card._vcard=undefined;
			vcard._vcard=undefined;
		}
				
		// Pas forcement les cartes peuvent etre effacées aprés !
		//f_core.VerifyProperties(card);		
	},

	/**
	 * @method hidden
	 * @return void
	 */
	_setCardFocus: function(card) {
	},

	/**
	 * @method hidden
	 * @return void
	 */
	_updateCardStyle: function(card) {
	},

	/**
	 * @method hidden
	 * @return void
	 */
	_declareCard: function(cardBodyId, selected) {
		var card=new Object;

		if (this._cards.length>0) {
			var prev=this._cards[this._cards.length-1];
			prev._next=card;
			card._prev=prev;
		}
		this._cards.push(card);		
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
	
			f_core.Assert(cardComponent, "Can not find car '"+id+"'.");
		}
	
		var card;
		for(var i=0;i<this._cards.length;i++) {
			var c=this._cards[i];
			
			if (c._body!=cardComponent) {
				continue;
			}
			
			card=c;
			break
		}
		
		if (!card) {
			return false;
		}
		
		if (this._selectedCard==card) {
			return false;
		}
		
		var old=this._selectedCard;
		this._selectedCard=null;
		if (old) {
			this._updateCardStyle(old);
		}
			
		card._body.f_setVisible(true);
		if (old) {
			old._body.f_setVisible(false);
		}
		
		this._selectedCard=card;
		this._updateCardStyle(card);
		
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
		
		return sc._body;
	}
}
 
var f_cardBox=new f_class("f_cardBox", null, null, __prototype, f_component);
