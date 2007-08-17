/*
 * $Id$
 */

/**
 * <p><strong>f_shell</strong> represents popup modal window.
 *
 * @class public final f_shell extends f_object, fa_eventTarget
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
	
	/**
	 * Style constant for resize area trim
	 * 
	 * @field public static final number
	 */
	RESIZE_STYLE: 1<<4,
	
	/**
	 * Style constant for title area trim
	 * 
	 * @field public static final number
	 */
	TITLE_STYLE: 1<<5,
	
	/**
	 * Style constant for close box trim
	 * 
	 * @field public static final number
	 */
	CLOSE_STYLE: 1<<6,
	
	/**
	 * Style constant for minimize box trim
	 * 
	 * @field public static final number
	 */
	MIN_STYLE: 1<<7,
	
	/**
	 * Style constant for horizontal scrollbar behavior
	 * 
	 * @field public static final number
	 */
	H_SCROLL_STYLE: 1<<8,
	
	/**
	 * Style constant for vertical scrollbar behavior
	 * 
	 * @field public static final number
	 */
	V_SCROLL_STYLE: 1<<9,
	
	/**
	 * Style constant for maximize box trim
	 * 
	 * @field public static final number
	 */
	MAX_STYLE: 1<<10,
	
	/**
	 * Style constant for modeless behavior
	 * 
	 * @field public static final number
	 */
	MODELESS_STYLE: 0,
	
	/**
	 * Style constant for primary modal behavior
	 * 
	 * @field public static final number
	 */
	PRIMARY_MODAL_STYLE: 1<<15,
	
	/**
	 * Style constant for application modal behavior
	 * 
	 * @field public static final number
	 */
	APPLICATION_MODAL_STYLE: 1<<16,
	
	
    /**
     * @field private static Object
     */
	_ObjIFrame: undefined,
	
    /**
     * @field private static boolean
     */
	_IE6: undefined,
	
    /**
     * @field private static boolean
     */
	_IE: undefined,

    /**
     * @field private static boolean
     */
	_DocComplete: undefined,
	
    /**
     * @field private static function
     */
	_OnDocComplete: undefined,
	
     /**
     * @method protected static
     * @return boolean
     */
    IsDocComplete: function() {
	    f_core.Debug(f_shell, "IsDocComplete: entering");
		return f_shell._DocComplete;
    },
     /**
     * @method protected static
     * @param function functionToExecute
     * @return void
     */
    ExecuteOnDocComplete: function(functionToExecute) {
	    f_core.Debug(f_shell, "ExecuteOnDocComplete: entering");
	    f_core.Assert(typeof(functionToExecute) == "function", "f_shell.ExecuteOnDocComplete bad parameter "+functionToExecute);
		if (f_shell._DocComplete) {
		    f_core.Debug(f_shell, "ExecuteOnDocComplete: executing");
		    // Add timeout to empty the stack and allow IE to draw an iframe from an IMG !!!
			window.setTimeout(functionToExecute,0);
			
		} else if (!f_shell._OnDocComplete) {
		    f_core.Debug(f_shell, "ExecuteOnDocComplete: delaying");
			f_shell._OnDocComplete = functionToExecute;
			
		} else {
			f_core.Debug(f_shell, "ExecuteOnDocComplete: ignored new function");
		}
    },
    /**
     *
     * @method protected static
	 * @return void
     */
     DocumentComplete: function() {
     	f_core.Debug(f_shell, "DocumentComplete: entering");
		f_shell.HideSelect();
     	
     	f_shell._DocComplete=true;
     	
     	var onDocComplete=f_shell._OnDocComplete;
     	if (onDocComplete) {
     		f_shell._OnDocComplete=undefined;

     		onDocComplete.call(f_shell);
     	}
	},
	
     /**
     * Class Constructor (called in the head ...
     * @method protected static
	 * @return void
     */
    Initializer: function() {
    	f_shell._IE6 = f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6); 
    	f_shell._IE = f_core.IsInternetExplorer(); 
	 },
	/**
     * Class Destructor (called in the head ...
     * @method protected static
	 * @return void
     */
    Finalizer: function() {
    	var objIframe=f_shell._ObjIFrame;
    	if (objIframe) {
	    	f_shell._ObjIFrame = undefined; // Object
	    	
     		var iframe=objIframe._iframe;
    		if (iframe) {
    			objIframe._iframe=undefined; // HTMLIFrameElement
    			
	    		iframe.onload=null;
	    		iframe.onreadystatechange=null;
	    	}
    	}
    	// f_shell._IE6 = undefined; // boolean
    	// f_shell._IE = undefined; // boolean
    	// f_shell._DocComplete = undefined; // boolean
    	f_shell._OnDocComplete = undefined; // function
	 },
	 
	/**
     * @method public static
     * @return Object size (width, height)
     */
    GetOwnDocumentSize: function() {
 		var viewSize=f_core.GetViewSize();
 		var viewDocSize=f_core.GetDocumentSize();
 		if (viewSize.height>viewDocSize.height) {
 			return viewSize;
 		}
 		return viewDocSize;
	},
     /**
     * <p>For IE 6 only : Hide selects that get over the Div</p>
     *
     * @method protected static
	 * @return void
     */
    HideSelect: function() {
		if (!f_shell._IE6) {
			return;
		}
		
		var tags=f_core.GetElementsByTagName(document,"select");

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
		if (!f_shell._IE6) {
			return;
		}
		
		var tags=f_core.GetElementsByTagName(document,"select");

		for (var i=0;i<tags.length;i++) {
			var tag=tags[i];
			if (tag._visibility_old != undefined) {
				tag.style.visibility=tag._visibility_old;
				tag._visibility_old=undefined;
			}
		}
    },

	/**
	 *  <p>delete the modal iFrame. 
	 *  </p>
	 *
	 * @method protected static
	 * @return void
	 */
	DelModIFrame: function() {
     	f_core.Debug(f_shell, "DelModIFrame: entering");
     	
     	var objIFrame=f_shell._ObjIFrame;
		if (objIFrame) {
			f_shell._ObjIFrame = undefined;

			var shell=objIFrame._iframe._modalShell;
			objIFrame._iframe._modalShell = undefined;

			shell.f_uninstallModalStyle();
			

			//Detach
			document.body.removeChild(objIFrame._div);
			objIFrame._div = undefined; // HTMLDivElement
			
			shell.f_deleteIframe();				

			objIFrame._iframe = undefined; // HTMLIFrameElement

			objIFrame._lastValidFocus=undefined; //HTMLElement
			// Return from Modal ...
		}
		
		// remove focus hooks if they exist
		f_core.RemoveEventListener(document, "focus", f_shell._OnFocus, document);
		
		//Show Selects
		f_shell.ShowSelect();
	},

	/**
	 *  <p>onload for the iframe object. 
	 *  </p>
	 *
	 * @method private static
	 * @return void
	 */
	_OnIframeLoad: function() {
     	f_core.Debug(f_shell, "_OnIframeLoad: entering with this="+this);
     	
	    this.onreadystatechange=null;
		this.onload=null;
	    
     	var inst=this._modalShell;
     	if (!inst) {
	     	f_core.Debug(f_shell, "_OnIframeLoad: Hack de la mort pour IE ...");
	     	inst=f_shell.GetInstance();
     	}
     	
     	var callBack=inst.f_getIFrameDrawingCallBack();
     	if (typeof(callBack) == "function") {
	     	callBack.call(inst);
	    } else {
	    	f_core.Debug(f_shell, "_OnIframeLoad: the callBack specified is not a function "+callBack+"\n trying url");
    		inst.f_drawIframeWithUrl();
	    }
 	},
	
	/**
	 *  <p>onload for the iframe object. 
	 *  </p>
	 *
	 * @method private static
	 */
	_OnIframeRS: function() {
     	f_core.Debug(f_shell, "_OnIframeRS: entering with this="+this+" readyState = "+this.readyState);
		if (this.readyState == "interactive") {
			f_shell._OnIframeLoad.call(this);
		}
 	},
	
    /**
     * <p>Resize Callback called on the div</p>
     *
     * @method protected static
     * @return void
     */
	_OnResize: function(evt) {
		var shell=this._shell;
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (shell.f_getEventLocked(evt, false)) {
			return false;
		}
		
		shell._resize();
		
		return true;
    },
    
	/**
	 *  <p>get the iFrame from the iframe object. 
	 *  </p>
	 *
	 * @method protected static
	 * @return HTMLIframeElement iframe
	 */
	GetIframe: function() {
     	f_core.Debug(f_shell, "GetIframe: entering");
		if (f_shell._ObjIFrame) {
			return f_shell._ObjIFrame._iframe;
		}
		return undefined;
	},

	/**
	 *  <p>get the current instance from the iframe object. 
	 *  </p>
	 *
	 * @method protected static
	 * @return f_shell
	 */
	GetInstance: function() {
     	f_core.Debug(f_shell, "GetInstance: entering");
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._iframe) {
			return f_shell._ObjIFrame._iframe._modalShell;
		}
		return undefined;
	},

	/**
	 *  <p>get the current instance from the parent iframe 
	 *  </p>
	 *
	 * @method public static
	 * @param optional Window win
	 * @return f_shell
	 */
	GetParentShell: function(win) {
     	f_core.Assert(!win || win.document, "f_shell.GetParentShell: bad parameter type "+win);
     	f_core.Debug(f_shell, "GetParentShell: entering");
     	
     	if (!win) {
     		win = window;
     	}
		var iframe = win.frameElement;
		if (iframe) {
			return iframe._modalShell;
		}
		return null;
	},

	/**
	 *  <p>clean the current instance from the iframe object. 
	 *  </p>
	 *
	 * @method protected static
	 * @return void
	 */
	CleanInstance: function() {
     	f_core.Debug(f_shell, "ClearInstance: entering");
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._iframe) {
			f_shell._ObjIFrame._iframe._modalShell = undefined;
		}
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._div) {
			f_shell._ObjIFrame._div._shell = undefined;
		}
	},

    /**
     *
     * @method private static
     * @param Event evt
     * @return boolean
     */
     _OnFocus: function(evt) {
     	if (window._rcfacesExiting) {
     		// On sait jamais, nous sommes peut etre dans un context foireux ...
     		return;
     	}
     	f_core.Debug(f_shell, "_OnFocus: entering on "+this.outerHTML);
 
 		var iframe=f_shell.GetIframe();
 		if (!iframe) {
 			// On a un gros probleme ...
     		f_core.Error(f_shell, "_OnFocus: No frame opened ?");
			return;
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
    		f_core.Info(f_shell, "_OnFocus: No target identified");
			return true;
		}

		var targetDocument=target;
		switch(target.nodeType) {
		case f_core.DOCUMENT_NODE:
			break;
			
		case f_core.ELEMENT_NODE:
			targetDocument=target.ownerDocument;
			break;

		default:
			// Qu'est que c'est ????
			f_core.Debug(f_shell, "_OnFocus: type de noeud "+target.nodeType);
			targetDocument=document; // On bloque donc
		}
 		
 		var frameDocument = iframe.contentWindow.document;

     	if (targetDocument==frameDocument) {
     		// C'est dans notre frame
     
     		// Pour l'instant ce n'est pas possible ... car la callback n'est pas installee
      		
     		iframe._lastValidFocus=target;
     		f_core.Debug(f_shell, "_OnFocus: Focus on our frame !");
     		return true;
     	}
     	
     	iframe.focus();
		iframe._modalShell.f_setFocus();
//		var self=iframe._modalShell;
//  		window.setTimeout(function() {
//  			f_core.Debug(f_shell, "anonymous timer _OnFocus: entering");
//  			self.f_getIframe().focus();
//  			self.f_setFocus();
// 		}, 50);
     	
     	return f_core.CancelJsEvent(evt);

     }
}

var __members = {

	/**
	 * @field private number
	 */
	_style: undefined,

	/**
	 * @field private String
	 */
	_div: undefined,
	
	/**
	 * @field private String
	 */
	_iframe: undefined,
	
	/**
	 * @field private String
	 */
	_cssClassBase: undefined,
	
	/**
	 * @field private String
	 */
	_backgroundMode: undefined,
	
	/**
	 * @field private String
	 */
	_imageURL: undefined,

	/**
	 * @field private number
	 */
	_height: undefined,
	/**
	 * @field private number
	 */
	_width: undefined,
	
	/**
	 * @field private boolean
	 */
	_iframeDraw: false,
	
	/**
	 * @field private function
	 */
	_drawingFunction: undefined,
	
	/**
	 * <p>Construct a new <code>f_shell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param number style the style of control to construct
	 */
	f_shell: function(style) {
		f_core.Assert(style===undefined || typeof(style)=="number", "f_shell.f_shell: Invalid style parameter ("+style+")");
		this.f_super(arguments);
		
		this._style=(style)?style:0;
		this._cssClassBase="f_shell";
		this._backgroundMode="greyed";
	},


	/**
	 * <p>Destruct a <code>f_shell</code>.</p>
	 *
	 * @method public
	 * @return void
	 */
	f_finalize: function() {
		
		// this._cssClassBase=undefined; // string
		// this._backgroundMode=undefined; //string
		// this._imageURL=undefined; //string
		// this._style=undefined; //number
		this._div=undefined; //HTMLElement
		this._iframe=undefined; //HTMLElement
		//this._height=undefined; // number
		//this._width=undefined; // number
		//this._iframeDraw: undefined; // boolean
		this._drawingFunction=null; //function

		this.f_super(arguments);
	},

	/**
	 *  <p>Return the style of the shell.</p>
	 *
	 * @method public 
	 * @return number The style
	 */
	f_getStyle: function() {
		return this._style;
	},

	/**
	 *  <p>Return the greying Div.</p>
	 *
	 * @method public 
	 * @return object Div
	 */
	f_getDiv: function() {
		return this._div;
	},
	
	/**
	 *  <p>Sets greying div.</p>
	 *
	 * @method public 
	 * @param object div
	 * @return void
	 */
	f_setDiv: function(div) {
    	f_core.Assert((typeof(div)=="object"), "f_shell.f_setDiv: Invalid parameter '"+div+"'.");
		this._div = div;
	},
	
	/**
	 *  <p>Return the iframe.</p>
	 *
	 * @method public 
	 * @return object iframe
	 */
	f_getIframe: function() {
		return this._iframe;
	},
	
	/**
	 *  <p>Sets the IFrame.</p>
	 *
	 * @method public 
	 * @param object iframe
	 * @return void
	 */
	f_setIframe: function(iframe) {
    	f_core.Assert(typeof(iframe)=="object", "f_shell.f_setIframe: Invalid parameter '"+iframe+"'.");
		this._iframe = iframe;
	},
	
	/**
	 *  <p>Return the Css base class name.</p>
	 *
	 * @method public 
	 * @return String a base name for the style classes
	 */
	f_getCssClassBase: function() {
		return this._cssClassBase;
	},
	
	/**
	 *  <p>Sets the Css base class name.</p>
	 *
	 * @method public 
	 * @param String cssClassBase a base name for the style classes
	 * @return void
	 */
	f_setCssClassBase: function(cssClassBase) {
    	f_core.Assert(typeof(cssClassBase)=="string", "f_shell.f_setCssClassBase: Invalid parameter '"+cssClassBase+"'.");
    	
		this._cssClassBase = cssClassBase;
	},
	
	/**
	 *  <p>Return the background mode.</p>
	 *
	 * @method public 
	 * @return String background mode : transparent, greyed, opaque
	 */
	f_getBackgroundMode: function() {
		return this._backgroundMode;
	},
	
	/**
	 *  <p>Sets the background mode.</p>
	 *
	 * @method public 
	 * @param String backgroundMode background mode : transparent, greyed, opaque
	 * @return void
	 */
	f_setBackgroundMode: function(backgroundMode) {
    	f_core.Assert(typeof(backgroundMode)=="string", "f_shell.f_setBackgroundMode: Invalid parameter '"+backgroundMode+"'.");
		this._backgroundMode = backgroundMode;
	},
	
	/**
	 *  <p>Gets the image URL.</p>
	 *
	 * @method public 
	 * @return String imageURL 
	 */
	f_getImageURL: function() {
		return this._imageURL;
	},
	
	/**
	 *  <p>Gets the image resolved URL.</p>
	 *
	 * @method public 
	 * @return String imageURL 
	 */
	f_getImageResolvedURL: function() {
		if (!this._imageURL) {
			return null;
		}
		
		return f_env.ResolveContentUrl(window, this._imageURL);
	},
	/**
	 *  <p>Sets the image URL.</p>
	 *
	 * @method public 
	 * @param String imageURL  (or <code>null</code>)
	 * @return void
	 */
	f_setImageURL: function(imageURL) {
    	f_core.Assert(imageURL===null || typeof(imageURL)=="string", "f_shell.f_setImageURL: Invalid parameter '"+imageURL+"'.");
    	
		this._imageURL = imageURL;
	},

	/**
	 *  <p>Return the height.</p>
	 *
	 * @method public 
	 * @return String height
	 */
	f_getHeight: function() {
		return this._height;
	},
	/**
	 *  <p>Sets Height.</p>
	 *
	 * @method public 
	 * @param number height
	 * @return void
	 */
	f_setHeight: function(height) {
    	f_core.Assert(typeof(height)=="number", "f_shell.f_setHeight: Invalid height parameter '"+height+"'.");

		this._height = height;
	},
	
	/**
	 *  <p>Return the width.</p>
	 *
	 * @method public 
	 * @return number width
	 */
	f_getWidth: function() {
		return this._width;
	},
	/**
	 *  <p>Sets width.</p>
	 *
	 * @method public 
	 * @param number width
	 * @return void
	 */
	f_setWidth: function(width) {
    	f_core.Assert(typeof(width)=="number", "f_shell.f_setWidth: Invalid width parameter '"+width+"'.");

		this._width = width;
	},
	

	/**
	 *  <p>Resize the greying div 
	 *  </p>
	 *
	 * @method private
	 * @return void
	 */
	_resize: function() {
     	f_core.Debug(f_shell, "_resize: entering");

		// Get the document' size
		var size=f_shell.GetOwnDocumentSize();
		
		//get the greying div
		var div = this.f_getDiv();
		
		//Modify the size
		div.style.width=size.width+"px";
		div.style.height=size.height+"px";

	},

	/**
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 * @return void
	 */
	f_drawContent: function(drawingFunction) {
     	f_core.Debug(f_shell, "f_drawContent: entering");
		f_core.Assert(typeof(drawingFunction) == "function", "f_shell.f_drawContent: bad parameter type: drawingFunction is not a function "+drawingFunction);

		try {
			if (!this._iframeDraw) {
				f_core.Debug(f_shell, "f_drawContent: not ready yet");
		     	this._drawingFunction=drawingFunction;
				return;
			}

			f_core.Debug(f_shell, "f_drawContent: ready call");
			drawingFunction.call(this);
			f_core.Debug(f_shell, "f_drawContent: setFocus");
			this.f_setFocus(true);

		} catch (e) {
	     	f_core.Error(f_shell, "f_drawContent: catch error", e);
		}

	},

	
	/**
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 * @return void
	 */
	f_fillModIFrame: function() {
     	f_core.Debug(f_shell, "f_fillModIFrame: entering");

		try {
			if (!f_shell._ObjIFrame) {
		     	f_core.Debug(f_shell, "_fillModIFrame : exit No iFrame");
				return;
			}
			f_shell.HideSelect();
			
			var iframe = this.f_getIframe();
			
			var iframeDocument = iframe.contentWindow.document;
	
			f_core.CopyStyleSheets(iframeDocument, document);
			
			var base = iframeDocument.body;
			var cssBaseName = iframe._modalShell.f_getCssClassBase();
			if (!cssBaseName) {
				cssBaseName = "f_shell";
			}
			//style
			base.className = cssBaseName+"_global";
			
			if (this._drawingFunction) {
		     	f_core.Debug(f_shell, "f_fillModIFrame: call drawing funtion ");
				this._drawingFunction.call(this);
				this._drawingFunction=null;
				
		     	f_core.Debug(f_shell, "f_fillModIFrame: setFocus");
				this.f_setFocus(true);
			} else {
		     	f_core.Debug(f_shell, "f_fillModIFrame: no drawing funtion ");
				this._iframeDraw=true;
			}

		} catch (e) {
	     	f_core.Error(f_shell, "_fillModIFrame: catch error", e);
		}

	},

	/**
	 *  <p>returns the callback to use to draw the iFrame 
	 *  </p>
	 *
	 * @method protected
	 * @return Function 
	 */
	f_drawIframeWithUrl: function() {
    	f_core.Debug(f_shell, "f_drawIframeWithUrl: entering");
    	var srcUrl=this.f_getIFrameUrl();
    	f_core.Debug(f_shell, "f_drawIframeWithUrl: url = "+srcUrl);
    	if (srcUrl && typeof(srcUrl) == "string") {
    		var iframe=this.f_getIframe();
    		iframe.src=srcUrl;
    		return;
    	}
    	f_core.Debug(f_shell, "f_drawIframeWithUrl: bad url = "+srcUrl);
    	
	},
	/**
	 *  <p>returns the callback to use to draw the iFrame 
	 *  </p>
	 *
	 * @method protected
	 * @return Function 
	 */
	f_getIFrameDrawingCallBack: function() {
		return this.f_fillModIFrame;
	},

	/**
	 *  <p>returns the url to show in the iFrame 
	 *  </p>
	 *
	 * @method protected
	 * @return String 
	 */
	f_getIFrameUrl: function() {
		return "";
	},

	/**
	 *  <p>decorate the div
	 *  </p>
	 *
	 * @method protected
	 * @param HTMLElement div
	 * @return void
	 */
	f_decorateDiv: function(div) {
     	f_core.Debug(f_shell, "f_decorateDiv: entering");
     	f_core.Assert(div.nodeType==f_core.ELEMENT_NODE, "f_shell.f_decorateDiv: bad parameter type "+div);

		// Recuperation de la taille de la fenetre
		var size=f_shell.GetOwnDocumentSize();

		div.className = this.f_getCssClassBase()+"_background_"+this.f_getBackgroundMode();
		div.style.position="absolute";
		div.style.top=0;
		div.style.left=0;
		div.style.width=size.width+"px";
		div.style.height=size.height+"px";

		//Associate this to the div
		div._shell = this;

	},

	/**
	 *  <p>decorate the iframe
	 *  </p>
	 *
	 * @method protected
	 * @param HTMLElement iframe
	 * @return void
	 */
	f_decorateIframe: function(iframe) {
     	f_core.Debug(f_shell, "f_decorateIframe: entering");
     	f_core.Assert(iframe.nodeType==f_core.ELEMENT_NODE, "f_shell.f_decorateIframe: bad parameter type "+iframe);

		iframe.className = this.f_getCssClassBase()+"_frame";
		
		// calculate iframe size and position
		var viewSize=f_core.GetViewSize();
		if (!this._height) {
			this._height=100;
		}
		var y=0;
		if (viewSize.height > this._height) {
			y = Math.round((viewSize.height - this._height)/2);
		} else {
			this._height = viewSize.height;
		}
		if (!this._width) {
			this._width=100;
		}
		var x=0;
		if (viewSize.width > this._width) {
			x = Math.round((viewSize.width - this._width)/2);
		} else {
			this._width = viewSize.width;
		}
		// Def pos and size
		iframe.style.top = y+"px";
		iframe.style.left = x+"px";
		iframe.style.height = this._height+"px";
		iframe.style.width = this._width+"px";
		
		iframe._modalShell = this;

	},

	/**
	 *  <p>draw a modal iFrame with a greying div around. 
	 *  </p>
	 *
	 * @method protected
	 * @return Object _div holds the gray div and _iframe holds the iframe
	 */
	f_drawModIFrame: function() {
     	f_core.Debug(f_shell, "f_drawModIFrame: entering");

		//var cssClassBase = this.f_getCssClassBase();

		if (f_shell._ObjIFrame) {
	     	f_core.Debug(f_shell, "f_drawModIFrame: exit : already done !");
	     	if (!this._div) {
		     	this.f_setDiv(f_shell._ObjIFrame._div);
		     }
	     	if (!this._iframe && f_shell._ObjIFrame._iframe) {
		     	this.f_setIframe(f_shell._ObjIFrame._iframe);
		     }
			return;
		}

		// Creation de la div recouvrant la page
		var div = document.createElement("div");
		
		this.f_decorateDiv(div);

		//Resize Handler
		f_core.AddResizeEventListener(div, f_shell._OnResize);

		this.f_setDiv(div);

		//Hide Selects
		f_shell.HideSelect();
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);

		f_shell._ObjIFrame = { 
			_div: div, 
			_iframe: undefined
		};
		
		// Creation de l'iFrame
//		var iframe = this.f_constructIframe(document);
	},
	
	/**
	 *  <p>construct the iframe. 
	 *  </p>
	 *
	 * @method public
	 * @return HTMLElement iframe
	 */
	f_constructIframe: function() {
     	f_core.Debug(f_shell, "f_constructIframe: entering");
		var doc = this.f_deleteIframe();
		
		if (!doc) {
     		f_core.Debug(f_shell, "f_constructIframe: pas de doc");
			if (this._div) {
	    	 	f_core.Debug(f_shell, "f_constructIframe: doc from div");
				doc = this._div.ownerDocument;
			} 
			if (!doc) {
	     		f_core.Debug(f_shell, "f_constructIframe: doc = document");
				doc = document;
			}
		}
		
		this._iframeDraw=false;
		
		// Creation de l'iFrame
		var iframe = doc.createElement("iframe");
		iframe.id = "shell_"+iframe.uniqueID;
		iframe.name = "shell_"+iframe.uniqueID+"_name";

		iframe.frameBorder = 0;
		if (!f_shell._IE6) {
			iframe.allowTransparency = true;
		}
		
		this.f_decorateIframe(iframe);
		
    	var srcUrl=this.f_getIFrameUrl();
    	f_core.Debug(f_shell, "f_constructIframe: url = "+srcUrl);
    	if (srcUrl && typeof(srcUrl) == "string") {
			iframe.src=srcUrl;
    	} else {
    		srcUrl = undefined;
			iframe.src="about:blank";
		}
		
		this.f_setIframe(iframe);

		//Attach
		doc.body.insertBefore(iframe, doc.body.firstChild);

		f_shell._ObjIFrame = { 
			_div: this.f_getDiv(), 
			_iframe: iframe
		};

		if (!srcUrl) {
			if (f_shell._IE) {
				f_core.Debug(f_shell, "f_constructIframe: IE use onreadystatechange ");
				iframe.onreadystatechange=f_shell._OnIframeRS;
			} else {
				iframe.onload=f_shell._OnIframeLoad;
			}
		}
		
		this.f_installModalStyle();
		
		return iframe;
	},

	/**
	 *  <p>delete the iframe. 
	 * and return its document
	 *  </p>
	 *
	 * @method public
	 * @return HTMLElement document
	 */
	f_deleteIframe: function() {
     	f_core.Debug(f_shell, "f_deleteIframe: entering");
		var iframe = this.f_getIframe();
		if (!iframe) {
	     	f_core.Debug(f_shell, "f_deleteIframe: no instance iframe : getting it ...");
			iframe = f_shell.GetIframe();
			if (!iframe) {
				return undefined;
			}
		}
		var doc = iframe.ownerDocument;

		iframe.onload=null;
	    iframe.onreadystatechange=null;

	    this.f_uninstallModalStyle();

     	f_core.Debug(f_shell, "f_deleteIframe: before removing the iframe from "+doc+" "+doc.body);
     	try {
			doc.body.removeChild(iframe);
		} catch (e) {
			f_core.Assert("f_shell.f_deleteIframe : iframe is not a child of body\n"+e);
		}
     	f_core.Debug(f_shell, "f_deleteIframe: after removing the ifram");
	    
		return doc;
	},
	
	/**
	 *  <p>delete the modal iframe and div. 
	 *  </p>
	 *
	 * @method public
	 * @return void
	 */
	f_delModIFrame: function() {
     	f_core.Debug(f_shell, "f_delModIFrame: entering");

     	this.f_uninstallModalStyle();

		f_shell.DelModIFrame();     	

	},
	
	/**
	 * @method protected
	 * @return void
	 */
	f_installModalStyle: function() {
		f_core.Debug(f_shell, "f_installModalStyle");
		var style=this.f_getStyle();
		if (!(style & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE))) {
			return;
		}

     	f_core.Debug(f_shell, "f_installModalStyle: Install modal hooks");
		
		f_core.AddEventListener(document, "focus", f_shell._OnFocus);
	},

	/**
	 * @method protected
	 * @return void
	 */
	f_uninstallModalStyle: function() {
		var style=this.f_getStyle();
		if (!(style & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE))) {
			return;
		}

     	f_core.Debug(f_shell, "f_uninstallModalStyle: Uninstall modal hooks");
		
		f_core.RemoveEventListener(document, "focus", f_shell._OnFocus);
	},
	
	/**
	 * @method protected
	 * @param boolean firstTime
	 * @return void
	 */
	f_setFocus: function(firstTime) {
		f_core.Debug(f_shell, "f_setFocus: entering with firstTime = "+firstTime);
		if (!f_shell.IsDocComplete()) {
			f_core.Debug(f_shell, "f_setFocus: Doc is not complete yet");
			return;
		}
		var iframe=this._iframe;
		var frameDoc=iframe.contentWindow.document;
		
		var nextFocusable=null;
		if (firstTime) {
			var inputs=f_core.GetElementsByTagName(frameDoc, "input");
			
			for(var i=0;i<inputs.length;i++) {
				var input=inputs[i];
			    		
				if (!input.type) {
					continue;
				}
				
				if (input.type.toLowerCase()!="submit") {
					continue;
				}
				
				nextFocusable=input;
				break;
			}
		}

  		if (!nextFocusable) {
  			if (frameDoc.body) {
	   			nextFocusable=f_core.GetNextFocusableComponent(frameDoc.body);
	   		}
  		}
  		
   		if (nextFocusable) {
     		f_core.Debug(f_shell, "f_setFocus: Set focus on "+nextFocusable.outerHTML);

	     	f_core.SetFocus(nextFocusable, false);
   		}
	},

	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		return "[f_shell cssClassBase='"+this._cssClassBase+"' backgroundMode='"+this._backgroundMode+"']";
	}
}

new f_class("f_shell", null, __statics, __members, f_object, fa_eventTarget);
