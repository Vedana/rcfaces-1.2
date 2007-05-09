/*
 * $Id$
 */

/**
 * @class f_comboEx extends f_component, fa_readOnly, fa_disabled
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {

	/**
	 * @field private static final String
	 */
	_BLANK_IMAGE_URL: "/comboEx/blank.gif",
	
	/**
	 * @field private static final number
	 */
	_DEFAULT_ROW_NUMBER: 8,

	/**
	 * @method private static
	 */
	_KeyUp: function(evt) {
		var comboEx=this._comboEx;		
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		var code=evt.keyCode;

		if (code==f_key.VK_DOWN || code==f_key.VK_UP) {
			return f_core.CancelJsEvent(evt);
		}
				
		comboEx._updateInputStyle();
		
		return true;
	},
	/**
	 * @method private static
	 */
	_KeyPress: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (comboEx.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}

		var code=evt.keyCode;
		if (code==f_key.VK_DOWN || code==f_key.VK_UP || 
			code==f_key.VK_PAGE_DOWN || code==f_key.VK_PAGE_UP ||
				code==f_key.VK_ENTER || code==f_key.VK_RETURN) {
			return f_core.CancelJsEvent(evt);
		}
				
		if (comboEx._isPopupOpened()) {
			if (code==f_key.VK_HOME || code==f_key.VK_END) {
				return f_core.CancelJsEvent(evt);
			}
		}				

		return true;
	},
	/**
	 * @method private static
	 */
	_KeyDown: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked()) {
			return false;
		}
		
		if (!evt) {
			evt = window.event;
		}

		if (comboEx.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}

		if (evt.ctrlKey || evt.altKey) {
			return true;
		}

		var ignoreKey=false;
		
		var code=evt.keyCode;
		
		if (code==f_key.VK_DOWN) { // FLECHE VERS LE BAS
			comboEx._goNextItem(evt);
			ignoreKey=true;
			
		} else if (code==f_key.VK_UP) { // FLECHE VERS LE HAUT
			comboEx._goPreviousItem(evt);
			ignoreKey=true;
			
		} else if (code==f_key.VK_PAGE_DOWN) {
			comboEx._goNextPage(evt);
			ignoreKey=true;
		
		} else if (code==f_key.VK_PAGE_UP) {
			comboEx._goPreviousPage(evt);
			ignoreKey=true;
		
		} else if (code==f_key.VK_ESCAPE) { // ESCAPE
			comboEx._closePopup(evt);
			
			if (comboEx._oldValue!=null) {
				comboEx._input.value=comboEx._oldValue;
				comboEx._oldValue=null;
			}
			
			ignoreKey=true;

		} else if (code==f_key.VK_TAB || code==f_key.VK_RETURN || code==f_key.VK_ENTER) { // ENTER/RETURN
			if (comboEx._isPopupOpened()) {

				if (comboEx._hoverItem && !comboEx.f_isReadOnly()) {
					comboEx.f_selectItem(comboEx._hoverItem);
				}			
			}
			
			if (code==f_key.VK_TAB) {
				comboEx._closePopup(evt);
				
			} else {			
				ignoreKey=true;
			}
		} else if (comboEx._isPopupOpened()) {
			if (code==f_key.VK_HOME) {
				comboEx._goFirstItem(evt);
				ignoreKey=true;
			
			} else if (code==f_key.VK_END) {
				comboEx._goLastItem(evt);
				ignoreKey=true;
			}
		}		
		
		var clearTimeout=true;
		
	 	if (comboEx.f_isAutoCompletion()) {
			var completion=false;
			var	proposal=true;
		
			if (f_key.IsPrintable(code))	{	// keyboard letters
				completion=true;
			}
			
			if (code==f_key.VK_BACK_SPACE || code==f_key.VK_CLEAR || code==f_key.VK_DELETE) {
				completion=true;
				proposal=false;
			}
			
			if (completion && !comboEx._timeoutid) {
				clearTimeout=false;
				comboEx._timeoutid=window.setTimeout(f_comboEx._KeyUpTimeout, 150);
				f_comboEx._timeoutComponent=comboEx;
				f_comboEx._timeoutProposalMode=proposal;
			}
		}
		
		if (clearTimeout && comboEx._timeoutid>0) {
			window.clearTimeout(comboEx._timeoutid);
			comboEx._timeoutid=window.setTimeout(f_comboEx._KeyUpTimeout, 150);
		}

		if (ignoreKey) {
			return f_core.CancelJsEvent(evt);
		}
				
		return true;
	},
	/**
	 * @method private static
	 */
	_KeyUpTimeout: function() {
		var comboEx=f_comboEx._timeoutComponent;
		var proposal=f_comboEx._timeoutProposalMode;
		
		comboEx._timeoutid=null;
	
		comboEx._updateInputCompletion(comboEx._input.value, proposal, null);		
		comboEx._updateInputStyle();
	},
	/**
	 * @method private static
	 */
	_InputChange: f_core.CancelJsEventHandler,
	/**
	 * @method private static
	 */
	_InputClick: f_core.CancelJsEventHandler,
	/**
	 * @method private static
	 */
	_InputFocus: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}

		if (!evt) {
			evt = window.event;
		}

		if (comboEx.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		if (!comboEx.f_isReadOnly()) {
			this.select();
		}
		
		return true;
	},
	/**
	 * @method private static
	 */
	_InputBlur: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;
		
		if (comboEx._isPopupOpened()) {
	
			if (f_core.IsGecko()) {
				// Dans Firefox le click dans la scrollbar du popup genere un event
				// dans le composant qui a le focus ....
				// Autant dire, rien avoir avec le composant INPUT concerné !
				
				if (f_core.IsComponentInside(this, evt)==false) {
					return true;
				}
			}
		
			comboEx._closePopup(evt);
		}

		return true;
	},
	/**
	 * @method private static
	 */
	_InputMouseDown: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked()) {
			return false;
		}
		
		if (!evt) evt = window.event;

		if (comboEx.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}

		
/*
		if (!this._hover) {
			// C'est une blaque ?
			// Cas de FireFox qui click dans une scrollbar !
			evt.cancelBubble = true;
			return false;
		}
*/

		var editable=comboEx.f_isEditable();
		var readOnly=comboEx.f_isReadOnly();
		if (editable && !readOnly) {
			return true;
		}
		
		if (f_core.IsGecko()) {
			// Dans Firefox le click dans la scrollbar du popup genere un event
			// dans le composant qui a le focus ....
			// Autant dire, rien avoir avec le composant INPUT concerné !
			
			if (f_core.IsComponentInside(this, evt)==false) {
				return true;
			}
		}
			
		return f_comboEx._ButtonDown.call(comboEx._button, evt);
	},
	/**
	 * @method private static
	 */
	_InputMouseUp: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		if (comboEx._button._pushed) {
			return f_comboEx._ButtonUp.call(comboEx._button, evt);
		}
		
		return true;
	},
	/**
	 * @method private static
	 */
	_InputMouseOver: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}

		if (!evt) evt = window.event;

		if (comboEx.f_isDisabled()) {
			return false;
		}

		this._hover=true;

		var editable=comboEx.f_isEditable();
		var readOnly=comboEx.f_isReadOnly();
		if (editable && !readOnly) {
			return true;
		}
		
		comboEx._buttonOver(evt);
		
		return true;
	},
	/**
	 * @method private static
	 */
	_InputMouseOut: function(evt) {
		if (!evt) evt = window.event;

		this._hover=false;

		var comboEx=this._comboEx;
		var editable=comboEx.f_isEditable();
		var readOnly=comboEx.f_isReadOnly();
		if (editable && !readOnly) {
			return true;
		}
		
		comboEx._buttonOut(evt);
		
		return true;
	},
	/**
	 * @method private static
	 */
	_ButtonDown: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked()) {
			return false;
		}

		if (!evt) {
			evt = window.event;
		}

		if (comboEx.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}

		var opened=comboEx._isPopupOpened();

		if (opened) {
			comboEx._closePopup(evt);
			
		} else {
			if (comboEx._iePopup && comboEx._ieLastCloseTime) {
				// C'est franchement limite ... 
				// mais a t-on le choix ...
				
				// On calcule le temps de la fermeture de la popup
				// et le click, s'il est moins de 100ms 
				// on considere que c'est le click qui a fermé la popup !
				var delta=(new Date().getTime())-comboEx._ieLastCloseTime;
		
				comboEx._ieLastCloseTime=0;
				
				if (delta<100) {
					return true;
				}
			}
		
			if (comboEx._filter) {
				comboEx._filter=false;
				comboEx._hoverItem=null;
				comboEx._filtredItems=null;
			}
					
			if (comboEx._showPopup(evt)) {
				this._pushed=true;
			}
		}
		
		comboEx._updateButtonStyle(comboEx);
				
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_ButtonClick: f_core.CancelJsEventHandler,
	/**
	 * @method private static
	 */
	_ButtonUp: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}

		this._pushed=false;

		comboEx._updateButtonStyle();

		if (!evt) {
			evt=window.event;
		}
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_ButtonMouseOver: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		comboEx._buttonOver(evt);
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_ButtonMouseOut: function(evt) {
		if (!evt) evt = window.event;

		this._pushed=false;

		this._comboEx._buttonOut(evt);
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_UpdateText: function(input, value, found) {
		input.value=found;
	
		if (f_core.IsInternetExplorer()) {
			var tr=input.createTextRange();
			tr.moveStart("character", value.length);
			tr.select();
			return;
		}
		
		if (f_core.IsGecko()) {
			input.setSelectionRange(value.length, found.length);
			
			return;
		}
	},
	/**
	 * @method private static
	 */
	_ShowCursor: function(input, position) {
		if (position<0 || position===undefined) {
			position=input.value.length;
		}
		
		if (f_core.IsInternetExplorer()) {
			var tr=input.createTextRange();
			tr.moveStart("character", position);
			tr.select();
			return;
		}
		
		if (f_core.IsGecko()) {
			input.setSelectionRange(position, position);
			
			return;
		}		
	},
	/**
	 * @method private static
	 */
	_Ie_popupKeyUp: function(evt) {
		var comboEx=this.document.body._comboEx;
		if (comboEx.f_getEventLocked()) {
			return false;
		}
		if (!evt) evt = this.document.parentWindow.event;

		if (evt.ctrlKey || evt.altKey) {
			return true;
		}
	
		var code=evt.keyCode;
		var stopKey=false;
		
		if (code==f_key.VK_DOWN) {
			comboEx._goNextItem(evt);
			stopKey=true;
			
		} else if (code==f_key.VK_UP) { // FLECHE VERS LE HAUT
			comboEx._goPreviousItem(evt);
			stopKey=true;

		} else if (code==f_key.VK_ESCAPE) { // ESCAPE
			comboEx._closePopup(evt);
			stopKey=true;

		} else if (code==f_key.VK_RETURN || code==f_key.VK_ENTER) { // ENTER/RETURN
			if (comboEx._isPopupOpened()) {

				if (comboEx._hoverItem && !comboEx.f_isReadOnly()) {
					comboEx.f_selectItem(comboEx._hoverItem);
				}			
			}
			
			stopKey=true;
		}
		
		if (stopKey) {
			return f_core.CancelJsEvent(evt);
		}

		return true;
	},
	/**
	 * @method private static
	 */
	_Ie_showPopup: function(combo) {
		var popup=combo._iePopup;		
		var popupDocument=popup.document;
	
		var code=combo._menuPopup.outerHTML;
		popupDocument.body.innerHTML=code;
		popupDocument.body._comboEx=combo;

		var menuPopup=f_core.GetFirstElementByTagName(popupDocument, "ul", true);

		var items=popupDocument.getElementsByTagName("li");
		var itemsOrigin=combo._menuPopup.getElementsByTagName("li");
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			var c=itemsOrigin[i];
			
			item._link=c;
			item._rlink=null; // Bizarre, sinon la propriété existe encore, mais cette fois-ci en attribut !
			item._className=null;
			c._rlink=item;
			
			item.onmousedown=f_comboEx._Ie_onmousedown;
			item.onmouseover=f_comboEx._Ie_onmouseover;
		}
				
		menuPopup.style.visibility="inherit";

		menuPopup.style.width=combo.offsetWidth+"px";
		menuPopup.document.body.onkeydown=f_comboEx._Ie_popupKeyUp;
		menuPopup.document.body.onunload=f_comboEx._Ie_onunload;
		
		popup.show(0, 0, 0, 0);

		var popupW = menuPopup.offsetWidth;
		if (popupW<combo.offsetWidth) {
			popupW=combo.offsetWidth;
		}
		
		var popupH = menuPopup.offsetHeight;
		if (items.length>combo._popupRowNumber) {
			var border=popupH-items[0].offsetHeight*items.length+1;
		
			popupH=items[0].offsetHeight*combo._popupRowNumber+border;	
			menuPopup.style.height=popupH;		
		}

		popup.show(0, combo.offsetHeight, popupW, popupH, combo);
	},
	/**
	 * @method private static
	 */
	_Ie_onmousedown: function() {
		return f_comboEx._Item_mouseDown.call(this._link, this.ownerDocument.parentWindow.event);
	},
	/**
	 * @method private static
	 */
	_Ie_onmouseover: function() {
		return f_comboEx._Item_mouseOver.call(this._link, this.ownerDocument.parentWindow.event);
	},
	/**
	 * @method private static
	 */
	_Ie_onunload: function() {
		var body=this.document.body;
		
		body.onkeyup=null;
		body.onunload=null;
		
		var items=body.getElementsByTagName("li");
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			if (item._link) {
				item._link._rlink=undefined;
				item._link=undefined;
			}
			item.onclick=null;
			item.onmouseover=null;
			item.onmousedown=null;

			f_core.VerifyProperties(item);
		}	

		var comboEx=body._comboEx;
		body._comboEx=undefined;
		
		if (!comboEx._popupOpened) {
			return;
		}
		
		comboEx._ieLastCloseTime=new Date().getTime();
		
		comboEx._closePopup();
	},
	/**
	 * @method private static
	 */
	_Ie_closePopup: function(combo) {
		var popup=combo._iePopup;		
		if (!popup.isOpen) {
			return;
		}
		popup.hide();
	},
	/**
	 * @method private static
	 */
	_Item_mouseOver: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked(false)) {
			return false;
		}
		if (!evt) evt = window.event;

		comboEx._setHoverItem(this, evt);
		
		return f_core.CancelJsEvent(evt);
	}, 
	/**
	 * @method private static
	 */
	_Item_mouseDown: function(evt) {
		var comboEx=this._comboEx;
		if (comboEx.f_getEventLocked()) {
			return false;
		}

		if (comboEx.f_isReadOnly()) {
			comboEx._closePopup();

		} else {		
			comboEx.f_selectItem(this);
		}
			
		if (!evt) {
			evt=window.event;
		}
		return f_core.CancelJsEvent(evt);
	}
}

/* -------------------------------------------------------------------------------- */

var __prototype = {
	
	f_comboEx: function() {
		this.f_super(arguments);
				
		this._className=f_core.GetAttribute(this, "v:className");
		if (!this._className) {
			this._className=this.className;
		}
		
		this._input = f_core.GetFirstElementByTagName(this, "input", true);
		this._input._comboEx = this;
		this._input.onkeydown=f_comboEx._KeyDown;
		this._input.onkeypress=f_comboEx._KeyPress;
		this._input.onkeyup=f_comboEx._KeyUp;
		this._input.onmousedown=f_comboEx._InputMouseDown;
		this._input.onclick=f_comboEx._InputClick;
		this._input.onmouseup=f_comboEx._InputMouseUp;
		this._input.onmouseover=f_comboEx._InputMouseOver;
		this._input.onmouseout=f_comboEx._InputMouseOut;
//		this._input.onfocus=f_comboEx._InputFocus;
		this._input.onblur=f_comboEx._InputBlur;

		// He oui ! cela semble marcher sur tous les browsers ! (meme Gecko !?)		
		this._input.setAttribute("autocomplete", "off");
		
		var buttonClassName=this.className+"_image";
		var cl=buttonClassName;
		if (f_core.GetBooleanAttribute(this, "v:disabled")) {
			cl+="_disabled";
		}

		var button=	f_core.GetChildByCssClass(this,cl);
		this._button = button;
		button._comboEx = this;

		this._itemImage = f_core.GetChildByCssClass(this,this.className+"_itemImage");
		if (this._itemImage) {
			this._itemImage._comboEx=this;
			
			this._itemImage.onmouseover=f_comboEx._InputMouseOver;
			this._itemImage.onmouseout=f_comboEx._InputMouseOut;
			this._itemImage.onmouseup=f_comboEx._InputMouseUp;
			this._itemImage.onmousedown=f_comboEx._InputMouseDown;
			this._itemImage.onclick=f_comboEx._InputClick;
		}
		
		var cl=f_core.GetAttribute(button, "v:className");
		if (cl) {
			button._className=cl;
		} else {
			button._className=buttonClassName;
		}

		if (!f_core.GetBooleanAttribute(this, "v:editable", true)) {
			this.f_setEditable(false);
		}

		if (f_core.GetBooleanAttribute(this, "v:autoCompletion")) {
			this.f_setAutoCompletion(true);
		}
		
		this._popupRowNumber=f_core.GetNumberAttribute(this, "v:popupRowNumber");
		
		if (!this._popupRowNumber || this._popupRowNumber<1) {
			this._popupRowNumber=f_comboEx._DEFAULT_ROW_NUMBER;
		}
		
		this._items=new Array;
		
		button.onmousedown=f_comboEx._ButtonDown;
		button.onclick=f_comboEx._ButtonClick;
		button.onmouseup=f_comboEx._ButtonUp;
		button.onmouseover=f_comboEx._ButtonMouseOver;
		button.onmouseout=f_comboEx._ButtonMouseOut;
		
		if (f_popup.Ie_enablePopup()) {
			this._iePopup=f_popup.Ie_GetPopup(document);
		}
	},
	f_finalize: function() {
		var button=this._button;
		if (button) {
			this._button=undefined;

			button.onmousedown=null;
			button.onclick=null;
			button.onmouseup=null;
			button.onmouseover=null;
			button.onmouseout=null;
			
			button._pushed=undefined;
			button._className=undefined; // string
			button._comboEx=undefined;
			button._hover=undefined;
			
			f_core.VerifyProperties(button);			
		}

		var input=this._input;
		if (input) {
			this._input=undefined;
			
			input.onkeydown=null;
			input.onkeyup=null;
			input.onkeypress=null;
			input.onmouseup=null;
			input.onmousedown=null;
			input.onmouseover=null;
			input.onmouseout=null;
			input.onclick=null;
			input.onfocus=null;
			input.onblur=null;
			input._hover=undefined;

			input._comboEx=undefined;

			f_core.VerifyProperties(input);			
		}
		
		var itemImage=this._itemImage;
		if (itemImage) {			
			this._itemImage=undefined;

			itemImage.onmouseover=null;
			itemImage.onmouseout=null;
			itemImage.onmouseup=null;
			itemImage.onmousedown=null;
			itemImage.onclick=null;
			itemImage._hover=undefined;

			itemImage._comboEx=undefined;
			f_core.VerifyProperties(itemImage);
		}

		this._hoverItem=undefined;
		this._oldValue=undefined;
		this._popupRowNumber=undefined;
		this._editable=undefined;
		this._autoCompletion=undefined;
		this._hover=undefined;
		this._className=undefined;
				
		this._items=undefined;
		this._filter=undefined;
		this._filtredItems=undefined;

		this._menuPopup=undefined;
		this._popupOpened=undefined;
		this._scroll=undefined;
		this._popupFilter=undefined;
		this._iePopup=undefined;
		this._ieLastCloseTime=undefined;
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 */
	f_update: function() {
		if (!this._menuPopup) { 
			var popup=document.createElement("ul");
			popup.className=this.className+"_popup";
				
			this._menuPopup=popup;
			
			var parent=this;
			if (f_core.IsGecko()) {
				// Gecko ajoute une marge d'environ 2->3 pixels ???
				// Alors on le met sur l'input : il ne gene plus personne !
				parent=this._input;
			}
			parent.appendChild(this._menuPopup);
			
			// Pas de filtre ... il faut regenerer ...
			this._filter=false;
		}
		
		this._updateInputStyle();
					
		return this.f_super(arguments);
	},
	/**
	 * @method protected
	 */
	f_serialize: function() {
		
		if (this.f_isDisabled()) {
			var text=this.f_getText();
			
			this.f_setProperty(f_prop.TEXT, text);
		}
		
		return this.f_super(arguments);
	},

	/* ****************************************************** */
	_goNextItem: function(evt) {
		var items=this._menuPopup.getElementsByTagName("li");

		if (!this._isPopupOpened()) {
			this._showPopup(evt);

			return;			
		}
		
		// Deja ouvert ... on scroll la selection vers la bas !
		var i=0;
		if (this._hoverItem) {
			for(;i<items.length;i++) {
				if (items[i]!=this._hoverItem) {
					continue;
				}
				i++; // On se positionne au prochain ...
				break;
			}
		}		
	
		if (i>=items.length) {
			return;
		}
		
		this._showItem(items, items[i]);
	},
	_goPreviousItem: function(evt) {
		if (!this._isPopupOpened()) {
			return;
		}

		var items=this._menuPopup.getElementsByTagName("li");

		// Deja ouvert ... on scroll la selection vers la bas !
		var i=0;
		for(;i<items.length;i++) {
			if (items[i]==this._hoverItem) {
				i--;
				break;
			}
		}
		if (i<0 || i>=items.length) {
			return;
		}

		this._showItem(items, items[i]);
	},
	_goFirstItem: function(evt) {
		if (!this._isPopupOpened()) {
			return;
		}

		var items=this._menuPopup.getElementsByTagName("li");
		if (items.length<1) {
			return;
		}

		var first=items[0];
		if (first==this._hoverItem) {
			return;
		}
		
		this._showItem(items, first);
	},
	_goLastItem: function(evt) {
		if (!this._isPopupOpened()) {
			return;
		}

		var items=this._menuPopup.getElementsByTagName("li");
		if (items.length<1) {
			return;
		}

		var last=items[items.length-1];		
		if (last==this._hoverItem) {
			return;
		}
		
		this._showItem(items, last);
	},
	_goNextPage: function(evt) {
		if (!this._isPopupOpened()) {
			return;
		}

		var items=this._menuPopup.getElementsByTagName("li");
	
		var last=null;
		var i;
		var parent;
		for(i=0;i<items.length;i++) {
			var li=items[i];

			if (li._rlink) {
				li=li._rlink;
			}
			parent=li.parentNode;
			
			if (li.offsetTop+li.offsetHeight/2-parent.scrollTop>parent.clientHeight) {
				// On le voit plus !
				break;
			}		
			
			last=items[i];
		}
		
		if (last==null) {
			return;
		}
		
		if (last!=this._hoverItem) {
			this._showItem(items, last);
			return;
		}
		
		var next=i+Math.floor(parent.scrollHeight/last.offsetHeight);
		if (next>=items.length) {
			next=items.length-1;
		}
		
		var item=items[next];
		if (item==this._hoverItem) {
			return;
		}
	
		this._showItem(items, item);
	},
	_goPreviousPage: function(evt) {
		if (!this._isPopupOpened()) {
			return;
		}

		var items=this._menuPopup.getElementsByTagName("li");
	
		var last=null;
		var i;
		var parent;
		for(i=0;i<items.length;i++) {
			var li=items[i];

			if (li._rlink) {
				li=li._rlink;
			}
			parent=li.parentNode;
			
			if (li.offsetTop+li.offsetHeight/2-parent.scrollTop>0) {
				last=items[i];
				// On le voit !
				break;
			}		
		}
		
		if (last==null) {
			return;
		}
		
		if (last!=this._hoverItem) {
			this._showItem(items, last);
			return;
		}
		
		var next=i-Math.floor(parent.scrollHeight/last.offsetHeight);
		if (next<0) {
			next=0;
		}
		
		var item=items[next];
		if (item==this._hoverItem) {
			return;
		}
	
		this._showItem(items, item);
		
	},
	_showItem: function(items, item) {
		this._setHoverItem(item);
		
		var label=item._item.label;
		
		if (this._popupFilter) {
			f_comboEx._UpdateText(this._input, this._oldValue, label);
			
		} else {
			this._input.value=label;
			this._input.select();
		}
		this._updateInputStyle();
	},
	_showPopup: function(evt) {
		if (this._isPopupOpened()) {
			return false;
		}

		var popup=this._menuPopup;
		
		if (f_popup.RegisterWindowClick( {
				exit: this._clickOutside
			}, this, popup)==false) {
			return false;
		}

		this._popupOpened=true;

		if (this._popupFilter===undefined || this._popupFilter!=this._filter) {
			// Reconstruit !
			
			if (this._filter) {
				this._constructPopupMenu(this._filtredItems);
				
			} else {
				this._constructPopupMenu(this._items);
			}
		}
	
		if (this._iePopup) {
			f_comboEx._Ie_showPopup(this);
			
		} else {
			var p2=f_core.GetAbsolutePosition(this);
			popup.style.left=p2.x;
			popup.style.top=p2.y+this.offsetHeight;
			popup.style.width=this.offsetWidth-2;
			popup.style.visibility="inherit";

			try {
				this._input.focus();
				
			} catch (dummy) {
				// Ce gros naze de firefox qui nous pete les couilles avec une exception !
			}
		}
		
		this._oldValue=this._input.value;
		
		return true;
	},
	_closePopup: function() {
		if (!this._popupOpened) {
			return;
		}
		this._popupOpened=false;

	
		f_popup.UnregisterWindowClick(this);
	
		// ferme le popup !
		if (this._iePopup) {
			f_comboEx._Ie_closePopup(this);
	
		} else {
			var popup=this._menuPopup;
			popup.style.visibility="hidden";
		}
	},
	_clickOutside: function() {
		this._closePopup();
	},
	_isPopupOpened: function() {
		var opened=this._popupOpened;
		if (!opened) {
			return false;
		}
		if (!this._iePopup) {
			return true;
		}
		if (this._iePopup.isOpen) {
			return true;
		}
		
		this._closePopup();
		
		return false;
	},
	_buttonOver: function(evt) {
		this._hover=true;
		this._updateButtonStyle();
	},
	_buttonOut: function(evt) {
		this._hover=false;
		this._updateButtonStyle();
	},
	_updateButtonStyle: function() {
		var cname=this._button._className;

		if (this.f_isDisabled()) {
			cname+="_disabled";

		} else if (this._button._pushed) {
			cname+="_selected";

		} else if (this._hover && !this._popupOpened) {
			cname+="_hover";
		}
		
		if (this._button.className!=cname) {
			this._button.className=cname;
		}
	},
	/**
	 * @method hidden
	 */
	f_addItem: function(label, value, imageURL, disabled) {
		return this._addSubItem(this, label, value, imageURL, disabled);
	},
	_addSubItem: function(parent, label, value, imageURL, disabled) {

		var item=new Object;

		if (!parent._items) {
			parent._items=new Array;
		}
		
		parent._items.push(item);
				
		item.parent=parent;
		item.label=label;
		item.value=value;
		item.imageURL=imageURL;
		item.disabled=disabled;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
		
		return item;
	},
	_constructPopupMenu: function(items) {
		var tbody=this._menuPopup;

		var children=tbody.childNodes;
		if (children) {
			while (tbody.hasChildNodes()) {
				tbody.removeChild(tbody.lastChild);
			}
		}

		var filter=this._filter;
		var value=this._input.value;
		this._hoverItem=null;
	
		if (filter) {
			var ritems=new Array;
			
			this._filterItem(items, filter, ritems);
			items=ritems;			
		}
		
		var blankImageURL=f_env.GetStyleSheetBase()+f_comboEx._BLANK_IMAGE_URL;
		f_imageRepository.PrepareImage(blankImageURL);
				
		this._constructItem(items, filter, tbody, value, 0, blankImageURL);
		var firstItem=null;
		if (tbody.hasChildNodes()) {
			firstItem=tbody.childNodes[0];
		}
			
		this._popupFilter=filter;		
		
		if (!this._hoverItem && firstItem) {
			this._setHoverItem(firstItem);
		}

		var clsName=this._className+"_popup";
		
		if (this._itemImage && !filter) {
			clsName+="_image";
		}		
		
		var flatItems=tbody.getElementsByTagName("li");
		if (flatItems.length > this._popupRowNumber) {
			clsName+="_scroll";
			this._scroll=true;

			if (f_core.IsInternetExplorer()) {
				// C'est le popup menu qui regle ca ...

			} else {
				tbody.className=clsName;			
				
				window.getComputedStyle(flatItems[0], "");
				var itemHeight=flatItems[0].offsetHeight;
				
				var h=itemHeight*this._popupRowNumber;
				
				tbody.style.height=h+"px";
			}
			
			if (f_core.IsGecko()) {
				 tbody.style.overflow="-moz-scrollbars-vertical";
			}
		} else {
			this._scroll=false;
		}

		tbody.className=clsName;			
	},
	_filterItem: function(items, filter, ritems) {
		for(var i=0;i<items.length;i++) {		
			var it=items[i];
			
			var label=it.label;
			
			if (label.length>=filter.length) {
				if (label.substring(0, filter.length)==filter) {
					ritems.push(it);
				}
			}
			
			if (it._items && it._items.length>0) {
				this._filter(it._items, filter, ritems);
			}
		}
	},
	_constructItem: function(items, filter, container, value, depth, blankImageURL) {
		for(var i=0;i<items.length;i++) {		
			var it=items[i];
			
			var item=document.createElement("li");
			if (!filter && this._itemImage) {
				item._className=this.className+"_item_image";
				
			} else {
				item._className=this.className+"_item";
			}
			if (depth>0) {
				item.style.paddingLeft=(depth*16)+"px";
			}
			
			container.appendChild(item);
				
			if (this._itemImage && !filter) {
				var image=document.createElement("img");
				image.className=this.className+"_item_image";
				image.align="center";
				image.valign="center";
				image.border=0;
				image._className=image.className;
				item.f_icon=image;
				
				var imageURL=it.imageURL;
				
				if (!imageURL) {
					imageURL=blankImageURL;
				}
				image.src=imageURL;
				
				item.appendChild(image);
					
				item.appendChild(document.createTextNode(" "));
			}
			
			var div=document.createElement("span");
			div.className=this.className+"_item_text";
			div.appendChild(document.createTextNode(it.label));
	
			item.className=item._className;
			item.f_text=div;
			item.href=f_core.JAVASCRIPT_VOID;
			item.tabIndex=-1;
			item.appendChild(div);
	
			item.onmouseover=f_comboEx._Item_mouseOver;
			item.onmousedown=f_comboEx._Item_mouseDown;
			
			item._comboEx=this;
			item._item=it;
			item.hideFocus=true;
			
			if (value==it.label) {
				this._setHoverItem(item);
			}
			
			if (filter) {
				continue;
			}
			if (it._items && it._items.length>0) {
				this._constructItem(it._items, filter, container, value, depth+1, blankImageURL);
			}
		}
	},
	/**
	 * @method public
	 */
	f_selectItem: function(item) {
		f_core.Assert(item._item, "No item ! for : "+item);
	
		this._closePopup();		

		this._input.value=item._item.label;

		this._updateInputStyle();
				
		this._input.focus();
		
		if (this.f_isReadOnly() || this.f_isEditable()==false) {
			this._input.select();
			
		} else {
			f_comboEx._ShowCursor(this._input);
		}
	},
	_updateInputStyle: function() {		
		if (!this._itemImage) {
			return;
		}
		
		var value=this._input.value;
		
		var imageURL=null;
		var items=this._items;
		if (items) {
			var it=this._searchInputStyle(items, value);
			if (it) {
				imageURL=it.imageURL;
			}
		}
		
		if (!imageURL) {
			imageURL=f_env.GetStyleSheetBase()+f_comboEx._BLANK_IMAGE_URL;
		}
		
		if (this._itemImage.src!=imageURL) {
			this._itemImage.src=imageURL;
		}
	},
	_searchInputStyle: function(items, value) {
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			if (item.label==value) {
				return item;
			}
			
			if (item._items && item._items.length>0) {
				var ret=this._searchInputStyle(item._items, value);
				if (ret) {
					return ret;
				}
			}
		}
		
		return false;
	},
	_updateInputCompletion: function(value, showProposal, evt) {

		var items=this._items;
		if (!items || items.length<1) {
			this._filter=null;
			this._closePopup();
			return;
		}

		if (value.length<1) {
			this._filter=null;
			this._closePopup();
			return;
		}
		
		var found=null;
		var filtredItems=new Array;
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			var label=item.label;
			if (!label || label.length<value.length) {
				continue;
			}
			
			if (label.substring(0, value.length)!=value) {
				continue;
			}

			filtredItems.push(item);
			
			if (!found || found>label) {
				found=label;
			}
		}
		
		if (!found) {
			this._filter=null;
			this._closePopup();
			return;
		}
		
		if (filtredItems.length<1) {
			this._closePopup();
			return;
		}

		var opened=this._isPopupOpened();

		if (!opened && !showProposal) {
			return;
		}

		function alphabeticOrder(item1, item2) {
			if (item1.label<item2.label) {
				return -1;
				
			} else if (item1.label>item2.label) {
				return 1;
			}
			
			return 0;
		}
		filtredItems.sort(alphabeticOrder);
		
		if (opened) {
			if (this._filter) {
				var fis=this._filtredItems;
				if (fis && fis.length==filtredItems.length) {
					var same=true;
					
					for(var i=0;i<filtredItems.length;i++) {
						if (fis[i].label==filtredItems[i].label) {
							continue;
						}
						
						same=false;
						break;
					}
					
					if (same) {
						if (showProposal) {
							if (!this._hoverItem) {
								var it=this._getComponentFromItem(fis[0]);
								
								this._setHoverItem(it);
							}
							f_comboEx._UpdateText(this._input, value, found);
						} else {
							if (this._hoverItem) {
								this._setHoverItem(null);
							}
						}
					
						return;
					}
				}
			}	
			this._closePopup();
		}		
		
		this._filter=value;
		this._filtredItems=filtredItems;
		this._setHoverItem(null);
		this._showPopup(evt);

		if (showProposal) {
			f_comboEx._UpdateText(this._input, value, found);
		}
	},
	_setHoverItem: function(item) {
		if (this._hoverItem==item) {
			if (item) {
				this._updateItemStyle(this._hoverItem);
			}
			return false;
		}

		var old=this._hoverItem;
		this._hoverItem=item;
		
		if (old) {
			this._updateItemStyle(old);
		}
		
		if (!this._hoverItem) {
			return;
		}
		
		this._updateItemStyle(this._hoverItem);
		
		if (!this._scroll) {
			return;
		}

		var item=this._hoverItem;
		
		if (item._rlink) {
			item=item._rlink;
		}
		
		var parent=item.parentNode;
	
		if (item.offsetTop-parent.scrollTop<0) {
			parent.scrollTop=item.offsetTop;
			
		} else if (item.offsetTop+item.offsetHeight-parent.scrollTop>parent.clientHeight) {			
			parent.scrollTop=item.offsetTop+item.offsetHeight-parent.clientHeight;
		}
	},
	_updateItemStyle: function(item) {
		var className=item._className;
		
		if (item.f_disabled) {
			className+="_disabled";
	
		} else if (item==this._hoverItem) {
			className+="_hover";
		}
		
		if (item.className!=className) {
			item.className=className;
			
			if (item._rlink) {
				item._rlink.className=className;
			}
		}
	},
	_getComponentFromItem: function(item) {
		var items=this._menuPopup.getElementsByTagName("li");
		for(var i=0;i<items.length;i++) {
			var it=items[i];
			
			if (it._item==item) {
				return it;
			}
		}
		
		return null;
	},
	fa_updateReadOnly: function(readOnly) {
		if (!this.fa_componentUpdated) {
			return;
		}
	
		var ro=readOnly | !this.f_isEditable();
	
		this._input.readOnly=ro;
	},
	/**
	 * @method protected
	 */
	fa_updateDisabled: function(disabled) {		
		if (!this.fa_componentUpdated) {
			return;
		}	
		this._input.disabled=disabled;
		this._updateButtonStyle();
	},
	/**
	 * Retourne <code>true</code> si le composant est éditable.
	 *
	 * @method public
	 */
	f_isEditable: function() {
		if (this._editable===undefined) {
			this._editable=true;
		}
		return this._editable;
	},
	/**
	 * Spécifie si le composant est éditable.
	 *
	 * @param boolean set <code>true</code> si le composant doit être éditable.
	 * @method public
	 */
	f_setEditable: function(set) {
		if (set===undefined) {
			set=true;
		}
		if (this._editable==set) {
			return;
		}
		
		this._editable = (set)?true:false;
		
		if (!this.fa_componentUpdated) {
			return;
		}
	
		var ro=(!set) | this.f_isReadOnly();
		this._input.readOnly=ro;
		
		this.f_setProperty(f_prop.EDITABLE,this._editable);
	},
	/**
	 * Retourne <code>true</code> si la fonctionnalité de complétion automatique est activée.
	 * 
	 * @method public
	 */
	f_isAutoCompletion: function() {
		return this._autoCompletion;
	},
	/**
	 * Spécifie l'activation de la fonctionnalité de complétion automatique.
	 * 
	 * @method public
	 * @param boolean set <code>true</code> si la fonctionnalité doit être activée.
	 */
	f_setAutoCompletion: function(set) {
		if (set===undefined) {
			set=true;
		}
		if (this._autoCompletion==set) {
			return;
		}
		
		this._autoCompletion = (set)?true:false;
				
		this.f_setProperty(f_prop.AUTO_COMPLETION, this._autoCompletion);
	},
	/**
	 * @method public
	 */
	f_getText: function() {
		if (!this._input) {
			return null;
		}
		
		return this._input.value;
	},
	/**
	 * @method public
	 */
	f_setText: function(text) {
		if (!this._input) {
			return;
		}
		
		this._input.value=text;		
	}
}

new f_class("f_comboEx", null, __static, __prototype, f_component, fa_readOnly, fa_disabled);
