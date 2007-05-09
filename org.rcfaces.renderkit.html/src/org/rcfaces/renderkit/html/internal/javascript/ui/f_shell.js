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
			
			document.body.removeChild(objIFrame._iframe);
			objIFrame._iframe = undefined; // HTMLIFrameElement

			objIFrame._lastValidFocus=undefined; //HTMLElement
			// Return from Modal ...
		}

		//Show Selects
		f_shell.ShowSelect();
	},

	/**
	 *  <p>onload for the iframe object. 
	 *  </p>
	 *
	 * @method private static
	 */
	_OnIframeLoad: function() {
     	f_core.Debug(f_shell, "_OnIframeLoad: entering with this="+this);
     	
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
     * <p>Resize Callback called on the div</p>
     *
     * @method protected static
     */
	_OnResize: function() {
		var shell=this._shell;
		if (shell.f_getEventLocked(false)) {
			return false;
		}
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
		
		shell._resize();
		
		return true;
		
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
		if (f_shell._ObjIFrame && f_shell._ObjIFrame._div) {
			f_shell._ObjIFrame._div._shell = undefined;
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
     	if (!window.f_shell) {
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

		var targetDocument=target;
		switch(target.nodeType) {
		case f_core.DOCUMENT_NODE:
			break;
			
		case f_core.ELEMENT_NODE:
			targetDocument=target.ownerDocument;
			break;

		default:
			// Qu'est que c'est ????
			targetDocument=document; // On bloque donc
		}
 		
 		var frameDocument = iframe.contentWindow.document;
 		     	
     	if (targetDocument==frameDocument) {
     		// C'est dans notre frame
     
     		// Pour l'instant ce n'est pas possible ... car la callback n'est pas installée
      		
     		iframe._lastValidFocus=target;
     		f_core.Debug(f_shell, "_OnFocus: Focus on our frame !");
     		return true;
     	}
  
  		iframe._modalShell.f_setFocus();
     	
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
	 * @field private number
	 */
	_height: undefined,
	/**
	 * @field private number
	 */
	_width: undefined,
	
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


	f_finalize: function() {
		
		// this._cssClassBase=undefined; // string
		// this._backgroundMode=undefined; //string
		// this._imageURL=undefined; //string
		// this._style=undefined; //number
		this._div=undefined; //HTMLElement
		this._iframe=undefined; //HTMLElement
		//this._height=undefined; // number
		//this._width=undefined; // number

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
	f_fillModIFrame: function() {
     	f_core.Debug(f_shell, "f_fillModIFrame: entering");

		try {
			if (!f_shell._ObjIFrame) {
		     	f_core.Debug(f_shell, "_fillModIFrame : exit No iFrame");
				return;
			}
			f_shell.HideSelect();
			
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
	 *  <p>draw a modal iFrame with a greying div around. 
	 *  </p>
	 *
	 * @method protected
	 * @return Object _div holds the gray div and _iframe holds the iframe
	 */
	f_drawModIFrame: function() {
     	f_core.Debug(f_shell, "f_drawModIFrame: entering");

		if (f_shell._ObjIFrame) {
	     	f_core.Debug(f_shell, "f_drawModIFrame: exit : already done !");
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

		//Associate this to the div
		div._shell = this;

		this.f_setDiv(div);

		//Resize Handler
		f_core.AddResizeEventListener(div, f_shell._OnResize);
		
		//Hide Selects
		f_shell.HideSelect();
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);
		
		// Creation de l'iFrame
		var iframe = document.createElement("iframe");

		//Hide Selects
		// Twice is too much (Fred)
//		f_shell.HideSelect();
		
		iframe.className = cssClassBase+"_frame";
		iframe.frameBorder = 0;
		if (!f_shell._IE6) {
			iframe.allowTransparency = true;
		}
		
		// calculate iframe size and position
		var viewSize=f_core.GetViewSize();
		if (!this._height) {
			this._height=100;
		}
		var y=0;
		if (viewSize.height > this._height) {
			y = (viewSize.height - this._height)/2;
		} else {
			this._height = viewSize.height;
		}
		if (!this._width) {
			this._width=100;
		}
		var x=0;
		if (viewSize.width > this._width) {
			x = (viewSize.width - this._width)/2;
		} else {
			this._width = viewSize.width;
		}
		// Def pos and size
		iframe.style.top = y;
		iframe.style.left = x;
		iframe.style.height = this._height+"px";
		iframe.style.width = this._width+"px";
		
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
		
		f_core.Debug(f_shell, "f_drawModIFrame: callback ");
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
		
		f_core.AddEventListener(document, "focus", f_shell._OnFocus, document);
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
		
		f_core.RemoveEventListener(document, "focus", f_shell._OnFocus, document);
	},
	
	f_setFocus: function(firstTime) {
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
   			nextFocusable=f_core.GetNextFocusableComponent(frameDoc.body);
  		}
  		
   		if (nextFocusable) {
     		f_core.Debug(f_shell, "f_setFocus: Set focus on "+nextFocusable.id);

	     	f_core.SetFocus(nextFocusable, false);
   		}
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_shell cssClassBase='"+this._cssClassBase+"' backgroundMode='"+this._backgroundMode+"']";
	}
}

new f_class("f_shell", null, __static, __prototype, f_object, fa_eventTarget);
