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
	     	shellManager.f_closeShell(null, true);
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
			if (tag._visibility_old === undefined) {
				tag._visibility_old=tag.style.visibility;
				tag.style.visibility="hidden";
			}
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
 			// On a un gros probleme ...
     		f_core.Error(f_shellManager, "_OnFocus: No frame opened ?");
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
	 * @method private static
	 * @return void
	 */
	_Background_onresize: function() {
		var shellManager=this._shellManager;
		
		//get the greying div
		var div = this._backgroundElement;
		if (!div) {
			return;
		}

		// Get the document' size
		var size=f_shellManager.GetOwnDocumentSize();
		
		//Modify the size
		div.style.width=size.width+"px";
		div.style.height=size.height+"px";
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
 		
 		return size;
	},
	/**
	 * @method public static
	 * @param HTMLElement component
	 * @return f_shell
	 */
	GetShell: function(component) {
		// En mode Firefox il se peut que ce ne soit pas des des windows à chaque fois !
		var win=f_core.GetWindow(component);
		
		if (win.frameElement) {
			return win.frameElement._shell;
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
			f_core.RemoveResizeEventListener(backgroundElement, f_shellManager._Background_onresize);
		}

		// Sécurité ! (fuite mémoire IE et Firefox)
		if (this._modalStyleInstalled) {
			// Il y a une exception ...
			// this._modalStyleInstalled=undefined; // boolean
			 		
			this.f_uninstallModalStyle();
		}
	},
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
	f_popShell: function(shell) {
		f_core.Debug(f_shellManager, "f_pushShell: pop shell '"+shell._id+"'.");

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
	f_getTopShell: function() {
		return this._shells[this._shells.length-1];
	},
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
		
		//Resize Handler
		f_core.AddResizeEventListener(div, f_shellManager._Background_onresize);

		f_shellManager._Background_onresize.call(div);

		//Hide Selects
		f_shellManager.HideSelect();
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);
	},
	_showScreen: function() {
		var backgroundElement=this._backgroundElement;
		if (!backgroundElement) {
			return;
		}
				
		this._backgroundElement=undefined;
		backgroundElement._shellManager=undefined;
		
		backgroundElement.parentNode.removeChild(backgroundElement);
		f_core.RemoveResizeEventListener(backgroundElement, f_shellManager._Background_onresize);
		
		if (f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6)) {
			f_shellManager.ShowSelect();
		}
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
	f_newShellDecorator: function(shell) {
		return f_shellDecorator.f_newInstance(shell);
	},
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

			shell.f_postConstruct();

			shell.f_fillBody(shellDecorator.f_getShellBody());
			
			shell.f_prepareOpening();
			
			self.f_pushShell(shell);
		});		
		
	},
	f_closeShell: function(shell, showNextShell) {
		if (shell) {
			shell.f_preDestruction();

			this.f_getShellDecorator(shell).f_destroyDecoration();

			this.f_popShell(shell);
			
			shell.f_postDestruction();
		}
		
		if (showNextShell!==false) {
	    	var waitingShells=this._waitingShells;
	    	
	    	if (waitingShells) {
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
		    			break;
		    		}
		    		
		    		waitingShells.f_removeElement(waitingShell);
		    	
		    		this.f_openShell(waitingShell);
		    		
		    		// Nous sommes en mode modal ?
		    		// On arrete alors !
		
					if (waitingShell.f_getStyle() & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE)) {
						break;		    		
			    	}			
		    	}			    
	    	}
		}
	},
	f_setShellDecoration: function(shell, key, value) {
		this.f_getShellDecorator(shell).f_setDecorationValue(key, value);
	},
	f_setShellBounds: function(shell, x, y, width, height) {
		this.f_getShellDecorator(shell).f_setShellBounds(shell, x, y, width, height);
	},
	f_clearPendingShells: function() {
    	this._waitingShells=undefined;
	}
}

new f_class("f_shellManager", {
	statics: __statics,
	members: __members
});
