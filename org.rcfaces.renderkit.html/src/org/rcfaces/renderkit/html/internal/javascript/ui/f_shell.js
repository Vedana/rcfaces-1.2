/*
 * $Id$
 */

/**
 * <p><strong>f_shell</strong> represents popup modal window.
 *
 * @class public final f_shell extends f_object
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	
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
     * Class Constructor (called in the head ...
     * @method public static
     */
    Initializer: function() {
    	f_shell._IE6 = f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6); 
	 },
     /**
     * Class Destructor (called in the head ...
     * @method public static
     */
    Finalizer: function() {
    	f_shell._ObjIFrame = undefined; // Object
    	// f_shell._IE6 = undefined; // boolean
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
     */
    _HideSelect: function() {
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
     */
    _ShowSelect: function() {
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
	 */
	_DelModIFrame: function() {
     	f_core.Debug(f_shell, "_DelModIFrame: entering");
     	
     	var objIFrame=f_shell._ObjIFrame;
		if (objIFrame) {
			f_shell._ObjIFrame = undefined;

			var shell=objIFrame._iframe._modalShell;
			objIFrame._iframe._modalShell = undefined;

			shell.f_uninstallModalStyle();
			

			//Detach
			document.body.removeChild(objIFrame._div);
			objIFrame._div = undefined;
			
			document.body.removeChild(objIFrame._iframe);
			objIFrame._iframe = undefined;

			// Return from Modal ...
		}

		//Show Selects
		f_shell._ShowSelect();
	},

	/**
	 *  <p>onload for the iframe object. 
	 *  </p>
	 *
	 * @method private static
	 */
	_OnIframeLoad: function() {
     	f_core.Debug(f_shell, "_OnIframeLoad: entering with this"+this);
     	
     	var inst=this._modalShell;
     	if (!inst) {
	     	f_core.Debug(f_shell, "_OnIframeLoad: Hack de la mort pour IE ...");
	     	inst=f_shell.GetInstance();
     	}
     	
     	var callBack=inst.f_getIFrameDrawingCallBack();
     	if (typeof(callBack) == "function") {
	     	callBack.call(inst);
	    } else {
	    	f_core.Debug(f_shell, "_OnIframeLoad: the callBack specified is not a function "+callBack);
	    }
     	this.onload=null;
	},

	/**
	 *  <p>get the iFrame from the iframe object. 
	 *  </p>
	 *
	 * @method protected static
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
	 */
	GetInstance: function() {
     	f_core.Debug(f_shell, "GetInstance: entering");
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._iframe) {
			return f_shell._ObjIFrame._iframe._modalShell;
		}
		return undefined;
	},

	/**
	 *  <p>clean the current instance from the iframe object. 
	 *  </p>
	 *
	 * @method protected static
	 */
	CleanInstance: function() {
     	f_core.Debug(f_shell, "ClearInstance: entering");
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._iframe) {
			f_shell._ObjIFrame._iframe._modalShell = undefined;
		}
	},

    /**
     *
     * @method public static
     */
     DocumentComplete: function() {
     	f_core.Debug(f_shell, "DocumentComplete: entering;");
     },
     
     
    /**
     *
     * @method private static
     * @param Event evt
     * @return boolean
     */
     _OnFocus: function(evt) {
     	if (!window.f_core) {
     		// On sait jamais, nous sommes peut etre dans un context foireux ...
     		return;
     	}
 
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

		switch(target.nodeType) {
		case f_core.DOCUMENT_NODE:
			break;
			
		case f_core.ELEMENT_NODE:
			target=target.ownerDocument;
			break;

		default:
			// Qu'est que c'est ????
			target=document; // On bloque donc
		}
 		
 		var frameDocument = iframe.contentWindow.document;
 		     	
     	if (target==frameDocument) {
     		// C'est dans notre frame
     		
     		f_core.Debug(f_shell, "_OnFocus: Focus on our frame !");
     		return true;
     	}
 
   		var nextFocusable=f_core.GetNextFocusableComponent(frameDocument.body);
   		if (nextFocusable) {
     		f_core.Debug(f_shell, "_OnFocus: Set focus on "+nextFocusable.id);

	     	f_core.SetFocus(nextFocusable, true);	     	
   		}
     	
     	return f_core.CancelJsEvent(evt);
     }
}

var __prototype = {

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
	 * <p>Construct a new <code>f_shell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param number style the style of control to construct
	 */
	f_shell: function(style) {
		this.f_super(arguments);
		
		this._style=style;
		this._cssClassBase="f_shell";
		this._backgroundMode="greyed";
	},


	f_finalize: function() {
		
		// this._cssClassBase=undefined; // string
		// this._backgroundMode=undefined; //string
		// this._imageURL=undefined; //string
		// this._style=undefined; //number
		this._div=undefined; //HTMLElement
		this._iframe=undefined; //HTMLElement

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
	 */
	f_setImageURL: function(imageURL) {
    	f_core.Assert(imageURL===null || typeof(imageURL)=="string", "f_shell.f_setImageURL: Invalid parameter '"+imageURL+"'.");
    	
		this._imageURL = imageURL;
	},

	/**
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 */
	_fillModIFrame: function() {
     	f_core.Debug(f_shell, "_fillModIFrame: entering");

		try {
			if (!f_shell._ObjIFrame) {
		     	f_core.Debug(f_shell, "_fillModIFrame : exit No iFrame");
				return;
			}
			f_shell._HideSelect();
			
//			var iframe = f_shell._ObjIFrame._iframe;
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
	f_getIFrameDrawingCallBack: function() {
		return this._fillModIFrame;
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
	 *  <p>draw a modal iFrame with a greying div around. 
	 *  </p>
	 *
	 * @method private
	 * @return Object _div holds the gray div and _iframe holds the iframe
	 */
	_drawModIFrame: function() {
     	f_core.Debug(f_shell, "_drawModIFrame: entering");

		if (f_shell._ObjIFrame) {
	     	f_core.Debug(f_shell, "_drawModIFrame: exit : already done !");
	     	if (!this._iframe) {
		     	this._iframe=f_shell._ObjIFrame._iframe;
		    }
	     	if (!this._div) {
		     	this._div=f_shell._ObjIFrame._div;
		     }
//	     	var div = f_shell._ObjIFrame._div;
//	     	div.className = this._cssClassBase+"_background_"+this._backgroundMode;
//			var iframe = f_shell._ObjIFrame._iframe;
//	     	iframe.className = this._cssClassBase+"_frame";
			return;
		}
		
		var cssClassBase = this._cssClassBase;

//		var callback = this.f_getIFrameDrawingCallBack();
		var url = this.f_getIFrameUrl();
		
		// Recuperation de la taille de la fenetre
		var size=f_shell.GetOwnDocumentSize();
		
		// Creation de la div recouvrant la page
		var div = document.createElement("div");
		div.className = cssClassBase+"_background_"+this._backgroundMode;
		div.style.top=0;
		div.style.left=0;
		div.style.width=size.width+"px";
		div.style.height=size.height+"px";
		
		//Hide Selects
		f_shell._HideSelect();
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);
		
		// Creation de l'iFrame
		var iframe = document.createElement("iframe");

		//Hide Selects
		f_shell._HideSelect();
		
		iframe.className = cssClassBase+"_frame";
		iframe.frameBorder = 0;
		if (!f_shell._IE6) {
			iframe.allowTransparency = true;
		}
		
		iframe._modalShell = this;
		
		if (!url || typeof(url) != "string" || url == "") {
			iframe.src="about:blank";
		} else {
			iframe.src=url;
		}

		f_shell._ObjIFrame = { 
			_div: div, 
			_iframe: iframe
		};
		this.f_setDiv(div);
		this.f_setIframe(iframe);

		//Attach
		document.body.insertBefore(iframe, document.body.firstChild);
		
		f_core.Debug(f_shell, "_drawModIFrame: callback ");
		iframe.onload=f_shell._OnIframeLoad;
		
		this.f_installModalStyle();
	},


	/**
	 * @method protected
	 * @return void
	 */
	f_installModalStyle: function() {
		var style=this.f_getStyle();
		if (!(style & (f_shell.PRIMARY_MODAL_STYLE | f_shell.APPLICATION_MODAL_STYLE))) {
			return;
		}

     	f_core.Debug(f_shell, "f_installModalStyle: Install modal hooks");
		
		f_core.AddEventListener(document, "focus", this._OnFocus, true);
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
		
		f_core.RemoveEventListener(document, "focus", this._OnFocus, true);
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_shell cssClassBase='"+this._cssClassBase+"' backgroundMode='"+this._backgroundMode+"']";
	}
}

new f_class("f_shell", null, __static, __prototype, f_object);
