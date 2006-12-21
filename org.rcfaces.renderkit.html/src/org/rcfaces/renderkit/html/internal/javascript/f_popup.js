/*
 * $Id$
 */

/**
 *
 * @class hidden f_popup extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	
	/**
	 * @field hidden static final String
	 */
	NO_CONTEXT_POPUP: "noContextPopup",
	
	/**
	 * @field hidden static
	 */
	Callbacks: undefined,
	
	/**
	 * @field hidden static
	 */
	Popup: undefined,
	
	/**
	 * @field hidden static
	 */
	Component: undefined,
	
	/**
	 * @field private static
	 */
	_OldContextMenu: undefined,
	
	/**
	 * @field private static
	 */
	_Installed: undefined,
	
	/**
	 * @field private static
	 */
	_ClearCallback: undefined,
	
	/**
	 * @field private static
	 */
	_PopupKey: undefined,
	
	/**
	 * @field private static boolean
	 */
	_LockPopupEvents: undefined,
	
	/**
	 * @method hidden static
	 * @return void
	 */
	Finalizer: function() {
		f_popup.Callbacks=undefined; // Map of functions
		f_popup.Popup=undefined; // HTMLComponent
		f_popup.Component=undefined;  // f_component
		f_popup._OldContextMenu=undefined; // function
		// f_popup._Installed=undefined; // boolean
		f_popup._ClearCallback=undefined; // function
		// f_popup._PopupKey=undefined; // string
	},
	/**
	 * @method hidden static
	 */
	Ie_enablePopup: function() {
		return f_core.IsInternetExplorer();
	},
	/**
	 * @method hidden static
	 */
	Ie_GetCurrentPopupByKey: function(document, popupKey) {
		var popup=document._iePopup;

		if (!popup || f_popup._PopupKey!=popupKey) {
			return null;
		}
		
		return popup;
	},
	/**
	 * @method hidden static
	 */
	Ie_GetPopup: function(document, popupKey, clearCallback) {		
		var popup=document._iePopup;
		
		if (!popup) {
			popup=f_popup.Ie_CreatePopup(document);
			document._iePopup=popup;
		}
		
		if (typeof(f_popup._ClearCallback)=="function") {
			f_popup._ClearCallback.call(popup);
		}

		f_popup._ClearCallback=clearCallback;
		f_popup._PopupKey=popupKey;

		return popup;
	},
	/**
	 * @method hidden static
	 */
	Ie_CreatePopup: function(document) {
		var popup=document.parentWindow.createPopup();
				
		var pdocument=popup.document;
		
		var bases=document.getElementsByTagName("BASE");
		if (bases.length) {
			var base=bases[bases.length-1];			
			
			var pbase=pdocument.createElement(base.tagName);
			pbase.href=base.href;

			pdocument.appendChild(pbase);
		}
		
		// Le BASE a terminer .... ?
	
		var links=document.styleSheets;
		for(var i=0;i<links.length;i++) {
			var link=links[i];

			pdocument.createStyleSheet(link.href);
		}
		
		return popup;
	},
	/**
	 * @method hidden static
	 */
	RegisterWindowClick: function(callbacks, component, popup) {

		f_core.Assert(component, "f_popup: Component parameter is null !");
		f_core.Assert(callbacks, "f_popup: Callback parameter is null !");

		if (!f_popup._OldContextMenu) {
			var oldContextMenu=document.body.oncontextmenu;
			if (!oldContextMenu) {
				oldContextMenu=f_popup.NO_CONTEXT_POPUP;
			}
			f_popup._OldContextMenu=oldContextMenu;

			document.body.oncontextmenu=f_core.CancelEventHandler;
		}
		
		if (!f_popup._LockPopupEvents) {
			f_popup._LockPopupEvents=true;
		
			f_event.EnterEventLock(f_event.POPUP_LOCK);
		}
		
		f_core.Debug("f_popup", "Register popup on "+component.id);

		var oldComponent=f_popup.Component;
		if (oldComponent) {
			alert("Already old component !");
			// On clot le precedant
			f_popup.Component=undefined;
			
			if (f_popup.Callbacks) {
				f_popup.Callbacks.exit.call(oldComponent);
				f_popup.Callbacks=undefined;
			}
			f_popup.Popup=undefined;
			
			return false;
		}
		
		f_popup.Callbacks=callbacks;
		f_popup.Component=component;
		f_popup.Popup=popup;

		// Dans le cas  IE pas de Register Window click
		if (f_popup.Ie_enablePopup()) {
			return true;
		}
		
		if (f_popup._Installed) {
			return true;
		}
		f_popup._Installed=true;
		
		document.addEventListener("mousedown", f_popup._OnMouseDown, true);		
		document.addEventListener("click", f_popup._OnClick, true);		
		document.addEventListener("dblclick", f_popup._OnClick, true);		
//		document.addEventListener("blur", f_popup._OnBlur, true);
		document.addEventListener("focus", f_popup._OnFocus, true);
		document.addEventListener("keydown", f_popup._OnKeyDown, true);
		document.addEventListener("keyup", f_popup._OnKeyUp, true);
		document.addEventListener("keypress", f_popup._OnKeyPress, true);
		return true;
	},
	/**
	 * @method hidden static
	 */
	UnlockPopupEvent: function() {	
		if (!f_popup._LockPopupEvents) {
			return;
		}
		
		f_popup._LockPopupEvents=undefined;
		
		f_event.ExitEventLock(f_event.POPUP_LOCK);
	},
	/**
	 * @method hidden static
	 */
	UnregisterWindowClick: function(component) {	
		var document=component.ownerDocument;
		
		f_popup.UnlockPopupEvent();

		var contextMenu=f_popup._OldContextMenu;
		if (contextMenu) {
			f_popup._OldContextMenu=undefined;

			if (contextMenu==f_popup.NO_CONTEXT_POPUP) {
				contextMenu=null; // null pour IE

			} else if (!contextMenu) {
				contextMenu=null;
			}
			
			document.body.oncontextmenu=contextMenu;
		}

		f_core.Debug("f_popup", "Unregister popup on "+component.id);
					
		f_popup.Callbacks=undefined;
		f_popup.Component=undefined;
		f_popup.Popup=undefined;

		// Dans le cas  IE pas de Register Window click
		if (f_popup.Ie_enablePopup()) {
			return;
		}
		
		if (!f_popup._Installed) {
			return;
		}
		
		f_popup._Installed=undefined;		
		document.removeEventListener("mousedown", f_popup._OnMouseDown, true);
		document.removeEventListener("click", f_popup._OnClick, true);
		document.removeEventListener("dblclick", f_popup._OnClick, true);
//		document.removeEventListener("blur", f_popup._OnBlur, true);
		document.removeEventListener("focus", f_popup._OnFocus, true);
		document.removeEventListener("keydown", f_popup._OnKeyDown, true);
		document.removeEventListener("keyup", f_popup._OnKeyUp, true);
		document.removeEventListener("keypress", f_popup._OnKeyPress, true);
	},
	/**
	 * @method private static
	 */
	_OnMouseDown: function(evt) {	
		f_core.Debug("f_popup", "OnMouseDown on "+this+" target="+evt.target+"/"+evt.target.className);

		if (!f_popup.Component) {
			return;
		}
		
		// Si la target n'est pas dans une popup on ferme !
		
		var found=f_popup._IsChildOfDocument(evt.target);
		f_core.Debug("f_popup", "OnMouseDown search parent="+found);

		if (found) {
			return true;
		}
	
		f_core.Debug("f_popup", "OnMouseDown outside: close the popup !");
		f_popup.Callbacks.exit.call(f_popup.Component, evt);
		f_popup.Callbacks=undefined;
		
		return true;
	},
	/**
	 * @method private static
	 */
	_IsChildOfDocument: function(target) {
		var popupDocument=f_popup.Popup;
		
		//f_core.Debug("f_popup", "Search parent target='"+target+"' document='"+popupDocument+"'.");
		
		for(;target;target=target.parentNode) {
		
			// f_core.Debug("f_popup", "Test child '"+target+"' popupParent='"+target._popupParent+"'");
		
			if (target==popupDocument) {
				return true;
			}

			if (target._menuBar==popupDocument || target._popupParent) {
				return true;
			}
		}
		
		return false;
	},
	/**
	 * @method private static
	 */
	_OnClick: function(evt) {	
		f_core.Debug("f_popup", "OnClick on "+this+" target="+evt.target+"/"+evt.target.className);

		if (!f_popup.Component) {
			return;
		}

		if (f_core.IsPopupButton(evt)) {
			return f_core.CancelEvent(evt);
		}
		
		return true;
	},
	/**
	 * @method private static
	 */
	_OnBlur: function(evt) {	
		f_core.Debug("f_popup", "OnBlur on "+this+" target="+evt.target+"/"+evt.target.className);

/*
		if (!f_popup.Component) {
			return;
		}
		try {
			f_popup.Callbacks.exit.call(f_popup.Component, evt);
			
		} catch (x) {
			f_core.Error(f_popup, "exit callback throws exception", x);
		}
		*/	
		return true;
	},
	/**
	 * @method private static
	 */
	_OnFocus: function(evt) {	
		f_core.Debug("f_popup", "OnFocus on "+this+" target="+evt.target+"/"+evt.target.className);

		if (!f_popup.Component) {
			return;
		}
		
		var found=f_popup._IsChildOfDocument(evt.target);
		f_core.Debug("f_popup", "OnFocus search parent="+found);

		if (found) {
			return true;
		}
	
		try {
			f_popup.Callbacks.exit.call(f_popup.Component, evt);
			
		} catch (x) {
			f_core.Error(f_popup, "Exit callback throws exception", x);

		} finally {
			f_popup.Callbacks=undefined;
		}
			
		return true;
	},
	/**
	 * @method private static
	 */
	_OnKeyDown: function(evt) {	
	
		var component=f_popup.Component;
		if (!component) {
			f_core.Debug("f_popup", "OnKeyDown["+evt.keyCode+"] on "+this+" no component");

			return true;
		}
	
		var target=evt.target;
		f_core.Debug("f_popup", "OnKeyDown["+evt.keyCode+"] on "+this+" component:"+component+" target:"+target);
		
		var callbacks=f_popup.Callbacks;
		if (evt.altKey) { // ?
			try {
				if (callbacks.exit.call(component, evt)===true) {
					return true;
				}
				
			} catch (x) {
				f_core.Error(f_popup, "Exit callback throws exception", x);
				
			} finally {
				f_popup.Callbacks=undefined;
			}

			return f_core.CancelEvent(evt);
		}
		
		try {
			if (callbacks.keyDown) {
				if (callbacks.keyDown.call(component, evt, f_popup.Popup)===false) {
					return f_core.CancelEvent(evt);
				}
				
				return true;
			}			
		} catch (x) {
			f_core.Error(f_popup, "KeyDown callback throws exception", x);
		}
			
		return f_core.CancelEvent(evt);
	},
	/**
	 * @method private static
	 */
	_OnKeyUp: function(evt) {	
		var component=f_popup.Component;
		if (!component) {
			f_core.Debug("f_popup", "OnKeyUp["+evt.keyCode+"] on "+this+" no component");

			return true;
		}
	
		var target=evt.target;
		f_core.Debug("f_popup", "OnKeyUp["+evt.keyCode+"] on "+this+" component:"+component+" target:"+target);
		
		var callbacks=f_popup.Callbacks;
		try {
			if (callbacks.keyUp) {
				if (callbacks.keyUp.call(component, evt, f_popup.Popup)===false) {
					return f_core.CancelEvent(evt);
				}
			}
			
		} catch (x) {
			f_core.Error(f_popup, "KeyUp callback throws exception", x);
		}
		
		return true;
	},
	/**
	 * @method private static
	 */
	_OnKeyPress: function(evt) {	
		var component=f_popup.Component;
		if (!component) {
			f_core.Debug("f_popup", "OnKeyPress["+evt.keyCode+"] on "+this+" no component");

			return true;
		}
	
		var target=evt.target;
		f_core.Debug("f_popup", "OnKeyPress["+evt.keyCode+"] on "+this+" component:"+component+" target:"+target);
		
		var callbacks=f_popup.Callbacks;
		try {
			if (callbacks.keyPress) {
				if (callbacks.keyPress.call(component, evt, f_popup.Popup)===false) {
					return f_core.CancelEvent(evt);
				}
			}
			
		} catch (x) {
			f_core.Error(f_popup, "KeyPress callback throws exception", x);
		}
		
		return true;
	},
	/**
	 * ???
	 * @method hidden static 
	 */	
	VerifyMouseDown: function(component, jsEvent) {
		if (f_core.IsGecko()) {
			
			// On a un probleme ! 
			// Les evenements clicks sont traités par notre composant !
			var target=jsEvent.target;
			
			// Il y a des sous-composants dans le menu 
			// l'evenement peut provenir de l'un d'eux ... 
			// dans le doute on recherche dans les parents
			for(;target;) {
				if (target._menuBar || target._popupParent) {
					// On laisse tomber ...
					
					return false;
				}
				target=target.parentNode;
			}
			
			return true;
		}

		if (f_core.IsInternetExplorer()) {
			var target=jsEvent.srcElement;
			
			if (target.ownerDocument!=component.ownerDocument) {
				return false;
			}
			
			return true;
		}
		
		return true;
	},
	/* ******************************************************************* */
	/* Popup IE */
	Ie_openPopup: function(popup, component, componentPosition, popupX, popupY, popupWidth) {
		var popupDocument=popup.document;
	
		popupDocument._component=component;
		popupDocument.hideFocus=true;

		var pbody=popupDocument.body;
		pbody.onunload=f_popup._Ie_unload;
//		pbody.onblur=f_popup._Ie_unload;
		
		popup.show(0, 0, 0, 0);
		var popupW = pbody.firstChild.offsetWidth;
		var popupH = pbody.firstChild.offsetHeight;
			
		if (popupWidth) {
			popupW=popupWidth;
		}

		f_core.Debug(f_popup, "Open popup '"+popup.id+"' for component '"+component.id+"', popup x="+popupX+" y="+popupY+" w="+popupW+" h="+popupH+" componentPosition="+componentPosition.id+"/"+componentPosition.tagName);

		popup.show(popupX, popupY, popupW, popupH, componentPosition);		
	},
	/**
	 * @method private static
	 */
	_Ie_unload: function(evt) {		
		var doc=this.document;
		var body=doc.body;

		var component=doc._component;
		doc._component=undefined;
		
		body.onunload=null;
				
		f_core.Debug(f_popup, "Unload popup '"+this.id+"' for component '"+component.id+"'.");		
			
		if (f_popup.Callbacks) {
			f_popup.Callbacks.exit.call(f_popup.Component, evt);
			f_popup.Callbacks=undefined;
		}
	},
	Ie_closePopup: function(popup) {
		if (!popup.isOpen) {
			return;
		}

		popup.hide();
	}	
}

var f_popup=new f_class("f_popup", null, __static);
