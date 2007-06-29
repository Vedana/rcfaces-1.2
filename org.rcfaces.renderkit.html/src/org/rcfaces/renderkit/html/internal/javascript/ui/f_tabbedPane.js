/*
 * $Id$
 */

/**
 *
 * @class public f_tabbedPane extends f_cardBox
 *
 * @author olivier Oeuillot
 * @version $REVISION: $
 */

var __statics = {

	/**
	 * @field private static final String
	 */
	_BLANK_IMAGE_URL: "/blank.gif",
	
	/**
	 * @field private static 
	 */
	_PreparedImages: undefined,

	/**
	 * @method private static
	 */
	_TabbedPane_onresize: function(evt) {
		var tabbedPane=this._tabbedPane;
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt, false)) {
			return false;
		}
		
		tabbedPane._resize();
		
		return true;
	},
	/**
	 * @method private static
	 */
	_TabbedPane_click: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt)) {
			return false;
		}

		var old=tabbedPane._selectedCard;

		tabbedPane._selectTab(this._tab, true, evt);
				
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_TabbedPane_focus: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt, false)) {
			return false;
		}
				
		return true;
	},
	/**
	 * @method private static
	 */
	_TabbedPane_keyPress: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt, false)) {
			return false;
		}

		switch(evt.keyCode) {
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
		case f_key.VK_HOME:
		case f_key.VK_END:
		case f_key.VK_SPACE:
			return f_core.CancelJsEvent(evt);
		}
		
		return true;	
	},
	/**
	 * @method private static
	 */
	_TabbedPane_keyDown: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt)) {
			return false;
		}
			
		switch(evt.keyCode) {
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
			var next=tab._next;
			for(;next && next._disabled;next=next._next);
			if (next) {
				tabbedPane._selectTab(next, true, evt);
			}
			break;
			
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
			var prev=tab._prev;
			for(;prev && prev._disabled;prev=prev._prev);		
			if (prev) {
				tabbedPane._selectTab(prev, true, evt);
			}
			break;
			
		case f_key.VK_HOME:
			var next=tabbedPane._cards[0];
			for(;next && next._disabled;next=next._next);
			if (next && next!=tabbedPane._selectedCard) {
				tabbedPane._selectTab(next, true, evt);
			}
			break;

		case f_key.VK_END:
			var prev=tabbedPane._cards[tabbedPane._cards.length-1];
			for(;prev && prev._disabled;prev=prev._prev);
			if (prev && prev!=tabbedPane._selectedCard) {
				tabbedPane._selectTab(prev, true, evt);
			}
			break;
			
		case f_key.VK_SPACE:
			if (tab!=tabbedPane._selectedCard) {
				tabbedPane._selectTab(tab, false, evt);
			}
			break;
			
		default:
			return true;
		}

		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_TabbedPane_mouseover: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt, false)) {
			return false;
		}

		tabbedPane._tabMouseOver(this._tab, evt);
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_TabbedPane_mouseout: function(evt) {
		var tab=this._tab;
		var tabbedPane=tab._cardBox;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tabbedPane.f_getEventLocked(evt, false)) {
			return false;
		}

		tabbedPane._tabMouseOut(this._tab, evt);
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static
	 */
	_PrepareImages: function() {
		var styleSheetBase=f_env.GetStyleSheetBase();
		if (!styleSheetBase) {
			return;
		}

		var args= [
			f_tabbedPane._BLANK_IMAGE_URL,
			"/tabbedPane/xpMid2.gif", 
			"/tabbedPane/xpT2.gif" ];

		f_tabbedPane._PreparedImages=new Object;

		for(var i=0;i<args.length;i++) {
			var filename=args[i];
			
			var url=styleSheetBase+filename;
			f_imageRepository.PrepareImage(url);
			
			f_tabbedPane._PreparedImages[filename]=url;
		}
	},
	/**
	 * @method private static
	 */
	_GetImageURL: function(filename) {
		var url=f_tabbedPane._PreparedImages[filename];
		
		f_core.Assert(url, "Unknown filename '"+filename+"'.");
		
		return url;
	},
	/**
	 * @method public static
	 * @return void
	 */
	Initializer: function() {
		f_tabbedPane._PrepareImages();
	}
}

var __members = {

	f_tabbedPane: function() {
		this.f_super(arguments);
		
		f_tabbedPane._PrepareImages();
		
		this._tabIndex=f_core.GetAttribute(this, "v:tabIndex");
	},
	f_finalize: function() {
		var title=this._title;
		if (title) {
			this._title=undefined;
			
			title.onresize=null;
			title._tabbedPane=undefined; // f_tabbedPane
			f_core.VerifyProperties(title);
		}

		// this._tabIndex=undefined; // number
		this._overTab=undefined; // f_tab
		// this._imageURL=undefined; // string
		this.onresize=null;
		// this._resizeHeight=undefined; // boolean
		// this._resizeWidth=undefined; //boolean
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateCards: function() {
		var cards=this._cards;
		for(var i=0;i<cards.length;i++) {
			var tab=cards[i];

			var ccard=f_core.GetElementByClientId(tab._id, this.ownerDocument);
			f_core.Assert(ccard, "f_tabbedPane.f_updateCards: Can not find card component of tab '"+tab._id+"'.");

			f_core.Debug(f_tabbedPane, "f_updateCards: Update tab#"+i+" tab="+tab+" ccard="+ccard);
			tab._ccard=ccard;
			ccard._vcard=tab;			
			ccard.f_declareTab(this, tab._value, tab._text, tab._accessKey, tab._disabled, tab._imageURL, tab._disabledImageURL, tab._selectedImageURL, tab._hoverImageURL);	
		}

		
		if (!this._selectedCard && cards.length) {
			this._selectTab(cards[0], false, null, false);
		}
	},
	f_documentComplete: function() {
		this.f_super(arguments);
		
		if (!this._title) {
			return;
		}
		
		if (!this.style.width) {
			for(var i=0;i<this._cards.length;i++) {
				var tab=this._cards[i];
	
				var _tab=tab._ccard;
				if (!_tab) {
					continue;
				}
		
				if (_tab.style.width) {
					continue;
				}
	
				this._resizeWidth=true;
				break;
			}
		}
				
//		if (!this.style.height) {
			this._resizeHeight=true;
//		}
		
		if (this._resizeWidth || this._resizeHeight) {
			this._resize();
			
			f_core.AddResizeEventListener(this._title, f_tabbedPane._TabbedPane_onresize);
		
			if (f_core.IsInternetExplorer()) {
				// Il faut le faire reafficher la barre, sinon il y a un probleme de position des titres
				this.f_updateCardStyle(this._selectedCard);
			}
		}
	},
	/**
	 * @method private
	 */
	_resize:function() {
		var width=undefined;
		if (this._resizeWidth) {		
			width=this.offsetWidth+"px";
		}
		
		var maxHeight=0;
		
		var cards=this._cards;
		for(var i=0;i<cards.length;i++) {
			var tab=cards[i];

			var _tab=tab._ccard;
		
		/*	
			if (this._resizeHeight) {
				_tab.style.height="";
	
				if (maxHeight<_tab.offsetHeight) {
					maxHeight=_tab.offsetHeight;
				}
			}
			*/
			
			if (width) {
				_tab.style.width=width;
			}

			var mask=_tab._mask;
			var textTitle=tab._textTitle;
			var mleft=textTitle.offsetLeft;
			var mright=textTitle.offsetLeft+textTitle.offsetWidth;
			
			var borderSize=f_core.GetCurrentStyleProperty(_tab, "border-left-width");
			if (!borderSize) {
				borderSize=f_core.GetCurrentStyleProperty(_tab, "borderLeftWidth");
			}
			
			var borderSize=parseInt(borderSize, 10);
			if (!isNaN(borderSize)) {
				mleft+=1-borderSize;
				mright+=1-borderSize;
			}
			
			var w=tab._leftTitle.offsetWidth;
			if (!tab._prev) {
				mleft-=w;
			} else {
				mleft-=w-2;
			}
			
			w=tab._rightTitle.offsetWidth;			
			if (!tab._next) {
				mright+=w-2;
			} else {
				mright+=w-4;
			}
			
			/*
			mleft-=2;
			
			if (!tab._right) {
				mright+=2;
			}
			*/
			
			f_core.Debug(f_tabbedPane, "_resize: Set mask position "+mleft+" to "+mright);
			
			mask.style.left=(mleft)+"px";
			mask.style.width=(mright-mleft)+"px";
		}
	
		if (this.style.height) {
			// Hauteur fixée !
			var p1=f_core.GetAbsolutePosition(this);
			var div0=f_core.GetFirstElementByTagName(this, "div", true);
			var p2=f_core.GetAbsolutePosition(div0);
	
			var ythis=p1.y;
			var ybody=p2.y;
			
			maxHeight=this.offsetHeight-ybody+ythis;
		}				


		if (maxHeight>0) {
			var height=maxHeight+"px";
			
			for(var i=0;i<this._cards.length;i++) {
				var tab=this._cards[i];
	
				var _tab=tab._ccard;
				if (!_tab) {
					continue;
				}
				
				_tab.style.height=height;
			}

			height=(maxHeight+2)+"px";
			var bodies=this.getElementsByTagName("div");
			for(var i=0;i<bodies.length;i++) {
				var body=bodies[i];
				
				if (body.className!="f_tabbedPane_content") {
					continue;
				}
				
				body.style.height=height;
				break;
			}
		}	
	},
	
	/**
	 * @method public
	 * @param f_tab tab Tab to select
	 * @param boolean setFocus Set focus if possible !
	 * @return boolean
	 */
	f_selectCard: function(tab, setFocus) {
		var _tab=tab._vcard;
		f_core.Assert(_tab, "f_tabbedPane.f_selectCard: L'objet n'est pas un onglet ! ("+tab+")");
		
		return this._selectTab(_tab, setFocus, null);
	},
	/**
	 * Select a tab by its value.
	 * 
	 * @method public
	 * @param String value
	 * @param optional boolean setFocus
	 * @return boolean <code>true</code> if success.
	 */
	f_setValue: function(value, setFocus) {
		var tab=this.f_getCardByValue(setFocus);
		if (!tab) {
			return false;
		}
			
		return this._selectTab(tab, false, null);			
	},
	/**
	 * @method private
	 */
	_selectTab: function(tab, setFocus, evt, sendEvent) {
		var ccard=tab._ccard;
		
		if (ccard.f_isDisabled()) {
			return false;
		}
	
		// ON verifie la selection par l'appel d'un evenement !
		if (setFocus) {
			f_core.SetFocus(tab._textLink);
		}
		
		if (tab==this._selectedCard) {		
			return true;
		}
		
		if (sendEvent!==false) {
			if (this.f_fireEvent(f_event.SELECTION, evt, ccard, ccard.f_getValue())===false) {
				return false;
			}
		}
		
		var old=this._selectedCard;
		this._selectedCard=null;
		if (old) {
			this.f_updateCardStyle(old);
		}
			
		if (old) {
			old._ccard.f_setVisible(false);
		}
		ccard.f_setVisible(true);
		
		this._selectedCard=tab;
		this.f_updateCardStyle(tab);
		
		this.f_setProperty(f_prop.SELECTED, tab._id);
		
		return true;
	},
	/**
	 * @method private
	 */
	_tabMouseOver: function(tab, evt) {
		if (tab._disabled) {
			return;
		}
		
		var old=null;
		
		if (this._overTab) {
			if (tab==this._overTab) {
				return;
			}

			old=this._overTab;			
		}

		this._overTab=tab;
		if (old) {		
			this.f_updateCardStyle(old);		
		}
		
		this.f_updateCardStyle(tab);
	},
	/**
	 * @method private
	 * @return void
	 */
	_tabMouseOut: function(tab, evt) {
		if (this._overTab!=tab) {
			return;
		}
		this._overTab=undefined;
		
		this.f_updateCardStyle(tab);
	},
	/**
	 * @method protected
	 * @param f_tab tab
	 * @return void
	 */
	f_updateCardStyle: function(tab) {
		var rightTTitleImage;
		var leftTTitleImage;
		var rightTitle;
		var leftTitle;
		var textTTitle;
		var textTitle;

		if (this._selectedCard==tab) {
			if (!tab._prev) {
				// Le plus à gauche est sélectionné !
				leftTTitleImage="_ttitleLeftA";
				leftTitle="_titleLeft_selected";

			} else if (tab._prev==this._overTab) {
				leftTTitleImage="_ttitleNextRH";
				leftTitle="_titleNext_sright";

			} else {
				leftTTitleImage="_ttitleNextR";
				leftTitle="_titleNext_sright";
			}
						
			if (!tab._next) {
				// Le plus à droite est sélectionné
				rightTTitleImage="_ttitleRightA";
				rightTitle="_titleRight_selected";

			} else if (tab._next==this._overTab) {
				rightTTitleImage="_ttitleNextLH";
				rightTitle="_titleNext_sleft";

			} else {
				rightTTitleImage="_ttitleNextL";
				rightTitle="_titleNext_sleft";
			}
			
			textTTitle="_ttitleText_selected";
			textTitle="_titleText_selected";
		} else {
			if (!tab._prev) {
				if (tab==this._overTab) {
					leftTTitleImage="_ttitleLeftH";
				} else {
					leftTTitleImage="_ttitleLeft";
				}
				leftTitle="_titleLeft";

			} else if (tab._prev==this._selectedCard) {
				// L'onglet de gauche est selectionné !
				if (tab==this._overTab) {
					leftTTitleImage="_ttitleNextLH";
					
				} else {
					leftTTitleImage="_ttitleNextL";
				}
				leftTitle="_titleNext_sleft";
							
			} else {
				if (tab==this._overTab) {
					leftTTitleImage="_ttitleNextHR";

				} else if (tab._prev && tab._prev==this._overTab) {
					leftTTitleImage="_ttitleNextHL";

				} else {
					leftTTitleImage="_ttitleNext";
				}
				leftTitle="_titleNext";
			}

			if (!tab._next) {
				if (tab==this._overTab) {
					rightTTitleImage="_ttitleRightH";
					
				} else  {
					rightTTitleImage="_ttitleRight";
				}
				rightTitle="_titleRight";

			} else if (tab._next==this._selectedCard) {
				// L'onglet de droite est selectionné !
				
				if (tab==this._overTab) {
					rightTTitleImage="_ttitleNextRH";
					
				} else {
					rightTTitleImage="_ttitleNextR";
				}
				rightTitle="_titleNext_sright";
							
			} else {
				if (tab==this._overTab) {
					rightTTitleImage="_ttitleNextHL";

				} else if (tab._next && tab._next==this._overTab) {
					rightTTitleImage="_ttitleNextHR";
					
				} else {
					rightTTitleImage="_ttitleNext";
				}
				rightTitle="_titleNext";
			}
		
			if (tab==this._overTab) {
				textTTitle="_ttitleText_over";
				
			} else {
				textTTitle="_ttitleText";
			}
			
			if (tab._disabled) {
				textTitle="_titleText_disabled";
				
			} else {
				textTitle="_titleText";
			}
		}
		
		var className="f_tabbedPane";
		tab._rightTTitleImage.className=className+rightTTitleImage;
		tab._leftTTitleImage.className=className+leftTTitleImage;
		tab._rightTitle.className=className+rightTitle;
		tab._leftTitle.className=className+leftTitle;
		tab._textTitle.className=className+textTitle;
		tab._textTTitle.className=className+textTTitle;
		
		var icon=tab._icon;
		if (icon) {
			var imageURL=null;

			if (tab._disabled) {
				imageURL=tab._disabledImageURL;
			}

			if (!imageURL && this._selectedCard==tab) {
				imageURL=tab._selectedImageURL;
			}
			
			if (!imageURL && tab==this._overTab) {
				imageURL=tab._hoverImageURL;
			}
			
			if (!imageURL) {
				imageURL=tab._imageURL;
			}
			
			if (imageURL) {
				icon.src=imageURL;
	//			icon.style.display="inherit";
				
			} else {
	//			icon.style.display="none";
			}
		}
	},
	/**
	 * @method hidden
	 */
	f_declareTab: function(tabBodyId, tabValue, selected, text, accessKey, disabled, imageURL, disabledImageURL, selectedImageURL, hoverImageURL) {
		f_core.Assert(typeof(tabBodyId)=="string", "f_tabbedPane.f_declareTab: Invalid tabBodyId parameter ("+tabBodyId+")");
		f_core.Assert(!tabValue || typeof(tabValue)=="string", "f_tabbedPane.f_declareTab: Invalid tabValue parameter ("+tabValue+")");
		f_core.Assert(!selected || typeof(selected)=="boolean", "f_tabbedPane.f_declareTab: Invalid selected parameter ("+selected+")");
		f_core.Assert(!text || typeof(text)=="string", "f_tabbedPane.f_declareTab: Invalid text parameter ("+text+")");
		
		var tab=this.f_declareCard(tabBodyId, tabValue, selected);

		f_core.Debug(f_tabbedPane, "Declare tab : "+tab);

		if (disabled===undefined) {
			disabled=false;
		}
		tab._disabled=disabled;
		
		var blankImage=f_tabbedPane._GetImageURL(f_tabbedPane._BLANK_IMAGE_URL);
		
		tab._imageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
		
		tab._disabledImageURL=disabledImageURL;
		if (disabledImageURL) {
			f_imageRepository.PrepareImage(disabledImageURL);
		}
		
		tab._hoverImageURL=hoverImageURL;
		if (hoverImageURL) {
			f_imageRepository.PrepareImage(hoverImageURL);
		}
		
		tab._selectedImageURL=selectedImageURL;
		if (selectedImageURL) {
			f_imageRepository.PrepareImage(selectedImageURL);
		}
		
		var table=this._title;
		if (!table) {
			table=f_core.GetChildByCssClass(this,"f_tabbedPane_title");
			this._title=table;
			table._tabbedPane=this;
		}
		var rows=table.rows;
		
		var trTitle=rows[0];
		var trText=rows[1];
		
		var cellsTitle=trTitle.cells;
		var cellsText=trText.cells;
		
		if (!tab._prev) {
			// Premier !
			
			var tdTitleLeft=document.createElement("td");
			trTitle.appendChild(tdTitleLeft);
			
			var leftTTitleImage=document.createElement("img");
			tab._leftTTitleImage=leftTTitleImage;
			leftTTitleImage.src=blankImage;
			leftTTitleImage.width=5;
			leftTTitleImage.height=5;
			tdTitleLeft.appendChild(leftTTitleImage);
			
			tab._leftTitle=document.createElement("td");
			trText.appendChild(tab._leftTitle);
			
		} else {
			//var tdTitleLeft=cellsTitle[cellsTitle.length-1];
			tab._leftTTitleImage=tab._prev._rightTTitleImage;
			tab._leftTTitleImage.width=7;
			tab._leftTitle=cellsText[cellsText.length-1];
		}
		
		tab._textTTitle=document.createElement("td");		
		trTitle.appendChild(tab._textTTitle);
	
		var textTitle=document.createElement("td");
		tab._textTitle=textTitle;
		trText.appendChild(textTitle);

		textTitle._tab=tab;
		textTitle.onclick=f_tabbedPane._TabbedPane_click;
		textTitle.onmouseover=f_tabbedPane._TabbedPane_mouseover;
		textTitle.onmouseout=f_tabbedPane._TabbedPane_mouseout;
		
		var textLink=document.createElement("a");
		tab._textLink=textLink;
		textTitle.appendChild(textLink);
		
		if (accessKey && f_core.IsInternetExplorer()) {
			// Il faut positionner l'accessKey !
			textLink.accessKey=accessKey;
		}

		var imageURL=tab._imageURL;
		if (imageURL) {
			var icon=document.createElement("img");
			tab._icon=icon;
			icon.src=imageURL;
			icon.align="center";
			icon.border=0;
			icon.className="f_tabbedPane_titleIcon";

			textLink.appendChild(icon);
		}
		
		if (text) {
			f_component.AddLabelWithAccessKey(textLink, text, accessKey);
		}
		
		textLink._tab=tab;
		if (this._tabIndex) {
			textLink.tabIndex=this._tabIndex;
		} else {
			textLink.tabIndex=0;
		}
		textLink._tab=tab;
		textLink.onclick=f_tabbedPane._TabbedPane_click;
		textLink.onfocus=f_tabbedPane._TabbedPane_focus;
		textLink.onkeydown=f_tabbedPane._TabbedPane_keyDown;
		textLink.onkeypress=f_tabbedPane._TabbedPane_keyPress;
		textLink.href=f_core.JAVASCRIPT_VOID;
	
		var tdTitleRight=document.createElement("td");
		trTitle.appendChild(tdTitleRight);

		var rightTTitleImage=document.createElement("img");
		tab._rightTTitleImage=rightTTitleImage;
		rightTTitleImage.src=blankImage;
		rightTTitleImage.width=5;
		rightTTitleImage.height=5;
		tdTitleRight.appendChild(rightTTitleImage);		
		
		tab._rightTitle=document.createElement("td");
		trText.appendChild(tab._rightTitle);
		
		this.f_updateCardStyle(tab);
	},
	/**
	 * @method protected
	 * @param f_tab tab
	 * @return void
	 */
	f_destroyCard: function(tab) {
		f_core.Assert(tab._cardBox, "f_tabbedPane.f_destroyCard: Invalid tab object ("+tab+")");
		
		var ccard=tab._ccard;
		
		f_core.Debug(f_tabbedPane, "f_destroyCard: Destroy tab: "+tab+"  component="+ccard);
		
		tab._next=undefined; // f_tab
		tab._prev=undefined; // f_tab
		
		f_core.VerifyProperties(tab._leftTitle);	
		tab._leftTitle=undefined; // HTMLTDElement
		
		f_core.VerifyProperties(tab._rightTitle);	
		tab._rightTitle=undefined; // HTMLTDElement
		
		f_core.VerifyProperties(tab._leftTTitleImage);	
		tab._leftTTitleImage=undefined;
		
		f_core.VerifyProperties(tab._textTTitle);	
		tab._textTTitle=undefined;
		
		f_core.VerifyProperties(tab._rightTTitleImage);	
		tab._rightTTitleImage=undefined;
		
		f_core.VerifyProperties(tab._icon);	
		tab._icon=undefined; // HTMLImageElement
		
		var textTitle=tab._textTitle;
		if (textTitle) {
			tab._textTitle=undefined;
			
			textTitle.onclick=null;
			textTitle.onmouseover=null;
			textTitle.onmouseout=null;
			textTitle._tab=undefined;

			f_core.VerifyProperties(textTitle);		
		}

		var textLink=tab._textLink;
		if (textLink) {
			tab._textLink=undefined;
			
			textLink.onclick=null;
			textLink.onfocus=null;
			textLink.onkeydown=null;
			textLink.onkeypress=null;
			textLink._tab=undefined;

			f_core.VerifyProperties(textLink);		
		}

		// tab._id=undefined; // string
		// tab._imageURL=undefined; // string
		// tab._disabledImageURL=undefined; // string
		// tab._hoverImageURL=undefined; // string
		// tab._selectedImageURL=undefined; // string
		// tab._disabled=undefined; // boolean
				
		this.f_super(arguments, tab);
	},
	/**
	 * @method hidden
	 */
	f_setTabImageURL: function(_tab, imageURL) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabImageURL: L'objet n'est pas un onglet ! ("+_tab+")");
		tab._imageURL=imageURL;

		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_updateCardStyle(tab);
	},
	/**
	 * @method hidden
	 */
	f_setTabDisabledImageURL: function(_tab, imageURL) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabDisabledImageURL: L'objet n'est pas un onglet ! ("+ _tab +")");
		tab._disabledImageURL=imageURL;

		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_updateCardStyle(tab);
	},
	/**
	 * @method hidden
	 */
	f_setTabHoverImageURL: function(_tab, imageURL) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabHoverImageURL: L'objet n'est pas un onglet ! ("+ _tab+")");
		tab._hoverImageURL=imageURL;

		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_updateCardStyle(tab);
	},
	/**
	 * @method hidden
	 */
	f_setTabSelectedImageURL: function(_tab, imageURL) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabSelectedImageURL: L'objet n'est pas un onglet ! ("+ _tab+")");
		tab._selectedImageURL=imageURL;

		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_updateCardStyle(tab);
	},
	/**
	 * @method hidden
	 */
	f_setTabText: function(_tab, text) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabText: L'objet n'est pas un onglet ! ("+ _tab+")");
		// @TODO !
	},
	/**
	 * @method hidden
	 */
	f_setTabDisabled: function(_tab, disabled) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setTabDisabled: L'objet n'est pas un onglet ! ("+ _tab+")");
		
		tab._disabled=disabled;
		tab._textLink.disabled=disabled;

		if (!this.fa_componentUpdated) {
			return;
		}
		
		var update=true;

		if (disabled) {
			if (this._overTab==tab) {
				this._overTab=null;
			}
			
			if (this._selectedCard==tab) {
				var found=false;
				for(var i=0;i<this._cards.length;i++) {
					var t=this._cards[i];
						
					if (this._selectTab(t, false, null)==false) {
						continue;
					}
					
					update=false;
					found=true;
					break;
				}
				
				if (!found) {
					this._selectedCard=null;
				}
			}
			
		} else if (!this._selectedCard) {
			if (this._selectTab(tab, false, null)) {
				update=false;
			}
		}
		
		if (update) {
			this.f_updateCardStyle(tab);
		}
	},
	/**
	 * @method hidden
	 */
	f_setCardFocus: function(_tab, evt) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setCardFocus: L'objet n'est pas un onglet ! ("+ _tab+")");
		
		f_core.SetFocus(tab._textLink);
	},
	/**
	 * @method hidden
	 */
	f_performTabAccessKey: function(_tab, evt) {
		var tab=_tab._vcard;
		f_core.Assert(tab, "f_tabbedPane.f_setCardFocus: L'objet n'est pas un onglet ! ("+ _tab+")");
	
		this._selectTab(tab, true, evt);
	},
	/**
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (!this._selectedCard) {
			return;
		}
		
		var component=this._selectedCard._textLink;
		if (!component) {
			return;
		}
		try {
			component.focus();
			
		} catch (x) {
			f_core.Error(f_tabbedPane, "f_setFocus: Error while setting focus to '"+component.id+"'.", x);
		}
	}
}
 
new f_class("f_tabbedPane", null, __statics, __members, f_cardBox);
