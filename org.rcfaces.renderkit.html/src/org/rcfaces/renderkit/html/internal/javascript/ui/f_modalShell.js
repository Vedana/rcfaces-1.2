/*
 * $Id$
 */

/**
 * <p><strong>f_modalShell</strong> represents popup modal window.
 *
 * @class public final f_modalShell extends f_object
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
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
    	f_modalShell._IE6 = f_core.IsInternetExplorer('iexplorer.6');
	 },
     /**
     * Class Destructor (called in the head ...
     * @method public static
     */
    Finalizer: function() {
    	_ObjIFrame = undefined;
    	f_modalShell._IE6 = undefined;
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
     * @method public static
     * @param object myWindow a window with the right location
     * @param string url for the content
     * @return string fully formed url
     */
    ResolveContentUrl: function(myWindow, url) {
    	f_core.Assert(typeof(myWindow)=="object", "f_modalShell.ResolveContentUrl: Invalid parameter window '"+myWindow+"'."+typeof(myWindow));
    	f_core.Assert(myWindow.location, "f_modalShell.ResolveContentUrl: Invalid parameter window has no location '"+myWindow+"'.");
    	f_core.Assert((typeof(url)=="string"), "f_modalShell.ResolveContentUrl: Invalid parameter url '"+url+"'.");
	    f_core.Debug(f_modalShell, "ResolveContentUrl : entering with ("+myWindow+", "+url+")");
	    
    	// Check for protocol in url
    	var pos=url.indexOf(":");
    	if (pos>0 && pos<url.indexOf("/")) {
		    f_core.Debug(f_modalShell, "ResolveContentUrl : url already formed");
    		return url;
    	}
    	// check for window protocol
    	var windowUrl=myWindow.location.toString();
    	pos=windowUrl.indexOf("//");
    	if (pos<0) {
		    f_core.Debug(f_modalShell, "ResolveContentUrl : window.location does not have a protocol ...");
    		return url;
    	}
    	// extract protocol://domain:port from windowUrl into base and rest into remain
    	pos=windowUrl.indexOf("/",pos+2);
    	var base=windowUrl;
    	var remain="";
    	if (pos>=0) {
	    	base=windowUrl.substring(0,pos);
	    	remain=windowUrl.substring(pos+1);
    	}
    	// If url absolute
    	if (url.indexOf("/")==0) {
    		return base+url;
    	}
    	//if no remain
    	if (remain.length==0) {
    		return base+"/"+url;
    	}
    	// extract the last par of remain (page)
    	var remains=remain.split("/");
    	if (remain.charAt(remain.length-1) != "/") {
    		remains.pop();
    	}
    	// del .. from url
    	var adds=url.split("/");
    	while (adds.length>0 && remains.length>0 && adds[0]=="..") {
    		adds.shift();
    		remains.pop();
    	}
    	//reconstruct complete url
    	return base+"/"+remains.concat(adds).join("/");
	},
    /**
     * <p>For IE 6 only : Hide selects that get over the Div</p>
     *
     * @method protected static
     */
    _HideSelect: function() {
		if (f_modalShell._IE6) {
			var tags=f_core.GetElementsByTagName(document,'select');
	
			for (var i=0;i<tags.length;i++) {
				var tag=tags[i];
				if (tag._visibility_old === undefined) {
					tag._visibility_old=tag.style.visibility;
					tag.style.visibility='hidden';
				}
			}
		}
		
    },

    /**
     * <p>For IE 6 only : Show selects that get over the Div</p>
     *
     * @method protected static
     */
    _ShowSelect: function() {
		if (f_modalShell._IE6) {
			var tags=f_core.GetElementsByTagName(document,'select');
	
			for (var i=0;i<tags.length;i++) {
				var tag=tags[i];
				if (tag._visibility_old != undefined) {
					tag.style.visibility=tag._visibility_old;
					tag._visibility_old=undefined;
				}
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
     	f_core.Debug(f_modalShell, "_DelModIFrame : entering");
		if (f_modalShell._ObjIFrame) {
			//Detach
			document.body.removeChild(f_modalShell._ObjIFrame._div);
			f_modalShell._ObjIFrame._iframe._modalShell = undefined;
			document.body.removeChild(f_modalShell._ObjIFrame._iframe);
			f_modalShell._ObjIFrame._iframe = undefined;
			f_modalShell._ObjIFrame._div = undefined;
			f_modalShell._ObjIFrame = undefined;

			// Return from Modal ...
		}

		//Show Selects
		f_modalShell._ShowSelect();
		
	},

	/**
	 *  <p>onload for the iframe object. 
	 *  </p>
	 *
	 * @method private static
	 */
	_OnIframeLoad: function() {
     	f_core.Debug(f_modalShell, "_OnIframeLoad: entering with this"+this);
     	
     	var inst=this._modalShell;
     	if (!inst) {
	     	f_core.Debug(f_modalShell, "_OnIframeLoad: Hack de la mort pour IE ...");
	     	inst=f_modalShell.GetInstance();
     	}
     	var callBack=inst.f_getIFrameDrawingCallBack();
     	if (typeof(callBack) == "function") {
	     	callBack.call(inst);
	    } else {
	    	f_core.Debug(f_modalShell, "_OnIframeLoad: the callBack specified is not a function "+callBack);
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
     	f_core.Debug(f_modalShell, "GetIframe : entering");
		if (f_modalShell._ObjIFrame) {
			return f_modalShell._ObjIFrame._iframe;
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
     	f_core.Debug(f_modalShell, "GetInstance : entering");
		if (f_modalShell._ObjIFrame && f_modalShell._ObjIFrame._iframe) {
			return f_modalShell._ObjIFrame._iframe._modalShell;
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
     	f_core.Debug(f_modalShell, "GetInstance : entering");
		if (f_modalShell._ObjIFrame && f_modalShell._ObjIFrame._iframe) {
			f_modalShell._ObjIFrame._iframe._modalShell = undefined;
		}
	},

    /**
     *
     * @method public static
     */
     DocumentComplete: function() {
     	f_core.Debug(f_modalShell, "DocumentComplete: entering;");
     }
}

var __prototype = {

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
	 * <p>Construct a new <code>f_modalShell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_modalShell: function() {
		this._cssClassBase="f_modalShell";
		this._backgroundMode="greyed";
	},


	f_finalize: function() {
		this._cssClassBase=undefined; // string
		this._backgroundMode=undefined; //string
		this._imageURL=undefined; //string
		this._div=undefined; //HTMLElement
		this._iframe=undefined; //HTMLElement
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
    	f_core.Assert((typeof(div)=="object"), "f_modalShell.f_setDiv: Invalid parameter '"+div+"'.");
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
    	f_core.Assert((typeof(iframe)=="object"), "f_modalShell.f_setIframe: Invalid parameter '"+iframe+"'.");
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
    	f_core.Assert((typeof(cssClassBase)=="string"), "f_modalShell.f_setCssClassBase: Invalid parameter '"+cssClassBase+"'.");
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
    	f_core.Assert((typeof(backgroundMode)=="string"), "f_modalShell.f_setBackgroundMode: Invalid parameter '"+backgroundMode+"'.");
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
		if (this._imageURL) {
			return f_modalShell.ResolveContentUrl(window, this._imageURL);
		}
		return this._imageURL;
	},
	/**
	 *  <p>Sets the image URL.</p>
	 *
	 * @method public 
	 * @param String imageURL 
	 */
	f_setImageURL: function(imageURL) {
    	f_core.Assert((typeof(imageURL)=="string"), "f_modalShell.f_setImageURL: Invalid parameter '"+imageURL+"'.");
		this._imageURL = imageURL;
	},

	/**
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 */
	_fillModIFrame: function() {
     	f_core.Debug(f_modalShell, "_FillModIFrame : entering");

		try {
			if (!f_modalShell._ObjIFrame) {
		     	f_core.Debug(f_modalShell, "_FillModIFrame : exit No iFrame");
				return;
			}
			f_modalShell._HideSelect();
			
//			var iframe = f_modalShell._ObjIFrame._iframe;
			var iframe = this.f_getIframe();
			
			var iframeDocument = iframe.contentWindow.document;
	
			f_core.CopyStyleSheets(iframeDocument, document);
			
			var base = iframeDocument.body;
			var cssBaseName = iframe._modalShell.f_getCssClassBase();
			if (!cssBaseName) {
				cssBaseName = "f_modalShell";
			}
			//style
			base.className = cssBaseName+"_global";
		} catch (e) {
	     	f_core.Error(f_modalShell, "_FillModIFrame : catch error", e);
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
     	f_core.Debug(f_modalShell, "_drawModIFrame : entering");

		if (f_modalShell._ObjIFrame) {
	     	f_core.Debug(f_modalShell, "_drawModIFrame : exit : already done !");
	     	if (!this._iframe) {
		     	this._iframe=f_modalShell._ObjIFrame._iframe;
		    }
	     	if (!this._div) {
		     	this._div=f_modalShell._ObjIFrame._div;
		     }
//	     	var div = f_modalShell._ObjIFrame._div;
//	     	div.className = this._cssClassBase+"_background_"+this._backgroundMode;
//			var iframe = f_modalShell._ObjIFrame._iframe;
//	     	iframe.className = this._cssClassBase+"_frame";
			return;
		}
		
		var cssClassBase = this._cssClassBase;

//		var callback = this.f_getIFrameDrawingCallBack();
		var url = this.f_getIFrameUrl();
		
		// Recuperation de la taille de la fenetre
		var size=f_modalShell.GetOwnDocumentSize();
		
		// Creation de la div recouvrant la page
		var div = document.createElement("div");
		div.className = cssClassBase+"_background_"+this._backgroundMode;
		div.style.top=0;
		div.style.left=0;
		div.style.width=size.width+"px";
		div.style.height=size.height+"px";
		
		//Hide Selects
		f_modalShell._HideSelect();
		
		//Attach
		document.body.insertBefore(div, document.body.firstChild);
		
		// Creation de l'iFrame
		var iframe = document.createElement("iframe");

		//Hide Selects
		f_modalShell._HideSelect();
		
		iframe.className = cssClassBase+"_frame";
		iframe.frameBorder = 0;
		if (!f_modalShell._IE6) {
			iframe.allowTransparency = true;
		}
		
		iframe._modalShell = this;
		
		if (!url || typeof(url) != "string" || url == "") {
			iframe.src="about:blank";
		} else {
			iframe.src=url;
		}

		f_modalShell._ObjIFrame = { 
			_div: div, 
			_iframe: iframe
		};
		this.f_setDiv(div);
		this.f_setIframe(iframe);

		//Attach
		document.body.insertBefore(iframe, document.body.firstChild);
		
		f_core.Debug(f_modalShell, "_drawModIFrame : callback ");
		iframe.onload=f_modalShell._OnIframeLoad;

	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_modalShell cssClassBase='"+this._cssClassBase+"' backgroundMode='"+this._backgroundMode+"']";
	}
}

new f_class("f_modalShell", null, __static, __prototype, f_object);
