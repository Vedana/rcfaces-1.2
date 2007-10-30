/*
 * $Id$
 */

/**
 * <p><strong>f_shellManager</strong> represents shell manager.
 *
 * @class public f_shellManager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
	/**
	 * @method public static
	 * @return f_shellManager
	 */
	Get: function() {
		var shellManager=f_shellManager._singleton;
		
		if (shellManager) {
			return shellManager;
		}
		
		shellManager=new f_shellManager();
		f_shellManager._singleton=shellManager;		
		
		return shellManager;
	},
	DocumentComplete: function() {
		f_shellManager._documentComplete=true;	
		
		var shellManager=f_shellManager._singleton;
		
		if (shellManager) {
	     	shellManager.f_showNextShell();
		}			
	},
    /**
     * <p>For IE 6 only : Hide selects that get over the Div</p>
     *
     * @method protected static
	 * @return void
     */
    HideSelect: function() {
		var tags=f_core.GetElementsByTagName(document, "select");

		for (var i=0;i<tags.length;i++) {
			var tag=tags[i];
			
			var old=tag._visibility_old;
			if (old === undefined) {
				old=tag.style.visibility;
				if (!old) {
					old="inherit";
				}
				tag._visibility_old=old;
			}

			tag.style.visibility="hidden";
		}		
    },

    /**
     * <p>For IE 6 only : Show selects that get over the Div</p>
     *
     * @method protected static
	 * @return void
     */
    ShowSelect: function() {
		var tags=f_core.GetElementsByTagName(document, "select");

		for (var i=0;i<tags.length;i++) {
			var tag=tags[i];
			if (tag._visibility_old) {
				tag.style.visibility=tag._visibility_old;
				tag._visibility_old=undefined;
			}
		}
    },
	/**
     *
     * @method private static
     * @param Event evt
     * @return boolean
     * @context event:evt
     */
	_OnFocus: function(evt) {
     	if (window._rcfacesExiting) {
     		// On sait jamais, nous sommes peut etre dans un context foireux ...
     		return true;
     	}
     	
     	f_core.Debug(f_shellManager, "_OnFocus: entering on "+this.tagName+"#"+this.id+"."+this.className);
 
 		var shellManager=f_shellManager.Get();
 
 		var shell=shellManager.f_getTopShell();
 		if (!shell) {
 			// Plus de frame visibles ... (on peut être en cours de fermeture ...)
			return true;
 		}
     	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
     	 			
		var target;
		if (evt.target) {
			target = evt.target;
			
		} else if (evt.srcElement) {
			target = evt.srcElement;
		}
		
		if (!target) {
    		f_core.Info(f_shellManager, "_OnFocus: No target identified");
			return true;
		}

		var shellDecorator=shellManager.f_getShellDecorator(shell);
		if (shellDecorator.f_isIntoShell(target)) {
			return true;
		}
     	
     	shellDecorator.f_setFocus();
     	
     	return f_core.CancelJsEvent(evt);
     },
	 
	/**
     * @method public static
     * @return Object size (width, height)
     */
    GetScreenSize: function() {
 		var viewSize=f_core.GetViewSize();
 		var docSize=f_core.GetDocumentSize();
 		
 		var size= { 
 			width: (viewSize.width>docSize.width)?viewSize.width:docSize.width, 
 			height: (viewSize.height>docSize.height)?viewSize.height:docSize.height
 		};
 		
 		//document.title="viewSize:"+viewSize.width+","+viewSize.height+"  docSize="+docSize.width+","+docSize.height+"  size="+size.width+","+size.height;
 		
 		return size;
	},
	/**
	 * @method public static
	 * @param HTMLElement component
	 * @return f_shell
	 */
	GetShell: function(component) {
		f_core.Assert(component, "f_shellManager.GetShell: Invalid component parameter '"+component+"'.");
		
		for(;component;) {
			if (component._shell) {
				return component._shell;
			}
			
			var parent=component.parentNode;
			if (parent && parent.nodeType!=f_core.DOCUMENT_NODE) {
				component=parent;
				continue;
			}
			
			var win=f_core.GetWindow(component);
			if (!win) {
				break;
			}
			
			component=win.frameElement;
		}

		return null;
	},
	Finalizer: function() {
		var shellManager=f_shellManager._singleton;
		if (shellManager) {
			f_shellManager._singleton=undefined; // f_shellManager
				
			if (shellManager._modalStyleInstalled) {
				shellManager._modalStyleInstalled=undefined;
				
				f_core.RemoveEventListener(document, "focus", f_shellManager._OnFocus);
			}
		}

		f_shellManager._documentComplete=undefined; //boolean
	}
}

var __members = {
	f_shellManager: function() {
		this._shells=new Array;
		this._shellDecorators=new Object;
	},
	f_finalize: function() {
		this._shells=undefined; // List<Shell>
		this._waitingShells=undefined; // List<Shell>
		
		var backgroundElement=this._backgroundElement;
		if (backgroundElement) {
			this._backgroundElement=undefined; // HtmlDivElement

			backgroundElement._shellManager=undefined; // f_shellManager	
		}
		
		this._removeResizeCallback();

		// Sécurité ! (fuite mémoire IE et Firefox)
		if (this._modalStyleInstalled) {
			// Il y a une exception ...
			// this._modalStyleInstalled=undefined; // boolean
			 		
			this.f_uninstallModalStyle();
		}
	},
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @return void
	 */
	f_pushShell: function(shell) {
		f_core.Debug(f_shellManager, "f_pushShell: push shell '"+shell._id+"'.");

		this._shells.f_addElement(shell);

		if (!this._backgroundElement && shell.f_getBackgroundMode()) {
			this._hideScreen();
		}
		
		if (shell.f_getStyle() & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE)) {
			this.f_installModalStyle();
		}
		
		this.f_getShellDecorator(shell).f_showShell();
	},
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @return void
	 */
	f_popShell: function(shell) {
		f_core.Debug(f_shellManager, "f_pushShell: pop shell '"+shell._id+"'.");

		shell.f_setStatus(f_shell.DESTROYING_STATUS);			

		var shells=this._shells;

		if (!shells.f_removeElement(shell)) {
			return;
		}
		
		// On reste en modale ?
		if (!shells.length  ||  
			!(shells[shells.length-1].f_getStyle() & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE))) {
			this.f_uninstallModalStyle();
		}

		if (this._backgroundElement) {
			for(var i=0;i<shells.length;i++) {
				if (shells[i].f_getBackgroundMode()) {
					return;
				}
			}		
						
			this._showScreen();
		}
	},
	/**
	 * @method public
	 * @return f_shell
	 */
	f_getTopShell: function() {
		
		var shells=this._shells;
		for(var i=shells.length;i>0;) {
			var shell=shells[--i];
			
			if (shell.f_getStatus()==f_shell.OPENED_STATUS) {
				return shell;
			}
		}
		
		return null;
	},
	/**
	 * @method private
	 * @return void
	 */
	_hideScreen: function() {		
		var backgroundMode;
		
		var shells=this._shells;
		for(var i=0;i<shells.length;i++) {
			backgroundMode=shells[i].f_getBackgroundMode();
			if (backgroundMode) {
				break;
			}
		}		
		
		if (!backgroundMode) {
			return;
		}
		
		if (f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6)) {
			f_shellManager.HideSelect();
		}
				
		// Creation de la div recouvrant la page
		var div = document.createElement("div");
		this._backgroundElement=div;
		div._shellManager=this;
		
		div.className="f_shellManager_background f_shellManager_background_"+backgroundMode;
		
		this._removeResizeCallback();
		
		var self=this;
		this._onResizeCB=function() {
			//get the greying div
			var div = self._backgroundElement;
			if (!div) {
				// On ne sait jamais ...
				return;
			}
	
			// Get the document' size
			var size=f_shellManager.GetScreenSize();
			
			if (f_core.IsGecko()) {
				size.width-=1; //f_core.ComputeBorderLength(div.ownerDocument.body, "left", "right")+1;
				size.height-=1; //f_core.ComputeBorderLength(div.ownerDocument.body, "top", "bottom")+2;
			}
					
			//Modify the size
			div.style.width=size.width+"px";
			div.style.height=size.height+"px";
		};
		
		//Resize Handler
		f_core.AddResizeEventListener(document.body, this._onResizeCB);

		this._onResizeCB();

		//Hide Selects
		if (f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6)) {
			f_shellManager.HideSelect();
		}
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);
	},
	/**
	 * @method private
	 * @return void
	 */
	_showScreen: function() {
		var backgroundElement=this._backgroundElement;
		if (!backgroundElement) {
			return;
		}
				
		this._backgroundElement=undefined;
		backgroundElement._shellManager=undefined;
		
		backgroundElement.parentNode.removeChild(backgroundElement);

		this._removeResizeCallback();
		
		if (f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6)) {
			f_shellManager.ShowSelect();
		}
	},
	/**
	 * @method private
	 * @return void
	 */
	_removeResizeCallback: function() {
		var onResizeCB=this._onResizeCB; // function
		if (!onResizeCB) {
			return;
		}
		this._onResizeCB=undefined;
		
		f_core.RemoveResizeEventListener(document.body, onResizeCB);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_installModalStyle: function(shell) {		

     	f_core.Debug(f_shellManager, "f_installModalStyle: Install modal hooks");
		
		if (this._modalStyleInstalled) {
			return;
		}
		this._modalStyleInstalled=true;
		
		f_core.AddEventListener(document, "focus", f_shellManager._OnFocus);
	},

	/**
	 * @method protected
	 * @return void
	 */
	f_uninstallModalStyle: function() {
     	f_core.Debug(f_shellManager, "f_uninstallModalStyle: Uninstall modal hooks");
	
		if (!this._modalStyleInstalled) {
			return;
		}
	
		this._modalStyleInstalled=undefined;
			
		f_core.RemoveEventListener(document, "focus", f_shellManager._OnFocus);
	},
	/**
	 * @method public
	 * @param f_shell shell
	 * @return f_shellDecorator
	 */
	f_getShellDecorator: function(shell) {
		var shellDecorators=this._shellDecorators;
		
		var shellDecorator=shellDecorators[shell.f_getId()];
		
		if (shellDecorator) {
			return shellDecorator;
		}
				
		shellDecorator=this.f_newShellDecorator(shell);

		f_core.Debug(f_shellManager, "f_getShellDecorator: create new shell decorator: "+shellDecorator);

		shellDecorators[shell.f_getId()]=shellDecorator;
		
		return shellDecorator;
	},
	/**
	 * @method protected
	 * @param f_shell shell
	 * @return f_shellDecorator
	 */
	f_newShellDecorator: function(shell) {
		return f_shellDecorator.f_newInstance(shell);
	},
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @return void
	 */
	f_openShell: function(shell) {
		if (!f_shellManager._documentComplete) {
			f_core.Debug(f_shellManager, "f_openShell: document is not complete, push shell into pipe !");
			
			var waitingShells=this._waitingShells;
			if (!waitingShells) {
				waitingShells=new Array;
				this._waitingShells=waitingShells;				
			}
			
			waitingShells.push(shell);
			return;
		}

		shell.f_preConstruct();
				
		f_core.Debug(f_shellManager, "f_openShell: create decoration");
		
		var self=this;
		this.f_getShellDecorator(shell).f_createDecoration(function(shellDecorator, shell) {
			f_core.Debug(f_shellManager, "f_openShell: creation started ...");
			
			shell.f_setStatus(f_shell.OPENING_STATUS);

			shell.f_postConstruct();

			shell.f_fillBody(shellDecorator.f_getShellBody());
			
			shell.f_prepareOpening();
			
			shell.f_setStatus(f_shell.OPENED_STATUS);
			
			self.f_pushShell(shell);
		});		
		
	},
	/**
	 * @method hidden
	 * @param optional f_shell shell
	 * @param optional boolean showNextShell
	 * @return void
	 */
	f_closeShell: function(shell, showNextShell) {
		if (shell.f_getStatus() == f_shell.CREATED_STATUS) {
			shell.f_setStatus(f_shell.DESTROYING_STATUS); // Directement ...
		}

		if (shell.f_getStatus() == f_shell.OPENING_STATUS || shell.f_getStatus() == f_shell.OPENED_STATUS) {
			shell.f_setStatus(f_shell.CLOSING_STATUS);
		}

		if (shell.f_getStatus()==f_shell.CLOSING_STATUS) {
			this.f_getShellDecorator(shell).f_hideShell();

			shell.f_preDestruction(); // C'est le preDestruction qui positionne le status ABOUT_TO_CLOSE ...
		}

		if (shell.f_getStatus()==f_shell.ABOUT_TO_CLOSE_STATUS) {
			this.f_getShellDecorator(shell).f_destroyDecoration();
			// C'est le Shell decorator qui positionne CLOSED_STATUS
		}
		
		if (shell.f_getStatus()==f_shell.CLOSED_STATUS) {
			this.f_popShell(shell);
			
			// C'est le Shell decorator qui positionne DESTROYING_STATUS
		}
		
		if (shell.f_getStatus()==f_shell.DESTROYING_STATUS) {
			shell.f_postDestruction();
		}
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_showNextShell: function() {
    	var waitingShells=this._waitingShells;
    	
    	if (!waitingShells) {
    		return;
    	}
    	
    	for(;waitingShells.length;) {		    		
    		var maxPriority=-1;
    		var waitingShell=null;
    		
    		for(var i=0;i<waitingShells.length;i++) {
    			var ws=waitingShells[i];
    			
    			if (!waitingShell || ws._priority>waitingShell._priority) {
    				waitingShell=ws;
    			}
    		}
    	
    		if (!waitingShell) {
    			return;
    		}
    		
    		waitingShells.f_removeElement(waitingShell);
    	
    		this.f_openShell(waitingShell);
    		
    		// Nous sommes en mode modal ?
    		// On arrete alors !

			if (waitingShell.f_getStyle() & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE)) {
				return; 
	    	}			
    	}			    
	},
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @param String key
	 * @param optional any value
	 * @return void
	 */
	f_setShellDecoration: function(shell, key, value) {
		this.f_getShellDecorator(shell).f_setDecorationValue(key, value);
	},
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @param number x
	 * @param number y
	 * @param number width
	 * @param number height
	 * @return void
	 */
	f_setShellBounds: function(shell, x, y, width, height) {
		this.f_getShellDecorator(shell).f_setShellBounds(shell, x, y, width, height);
	},
	/**
	 * @method public
	 * @return void
	 */
	f_clearPendingShells: function() {
    	this._waitingShells=undefined;
	}
}

new f_class("f_shellManager", {
	statics: __statics,
	members: __members
});
