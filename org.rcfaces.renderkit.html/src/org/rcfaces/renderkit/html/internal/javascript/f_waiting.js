/*
 * $Id$
 */

/**
 * 
 * @class hidden f_waiting extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	
	/**
	 * @field private static final number
	 */
	_STEP: 0.3,
	
	/**
	 * @field private static final number
	 */
	_FIRST_STEP: 3,
	
	/**
	 * @field private static final number
	 */
	_STEP_MS: 100,

	/**
	 * @field private static final String
	 */
	_WAITING_IMAGE_URL: "/waiting/waiting2.gif",

	/**
	 * @field private static final String
	 */
	_WAITING_ERROR_IMAGE_URL: "/waiting/error.gif",
	
	/**
	 * @field hidden static final number
	 */
	WIDTH: 100,

	/**
	 * @field hidden static final number
	 */
	HEIGHT: 24,
	
	/**
	 * @field public static final number
	 */
	LEFT: 1,

	/**
	 * @field public static final number
	 */
	CENTER: 2,

	/**
	 * @field public static final number
	 */
	RIGHT: 3,


	/**
	 * @field public static final number
	 */
	TOP: 4,

	/**
	 * @field public static final number
	 */
	MIDDLE: 8,

	/**
	 * @field public static final number
	 */
	BOTTOM: 12,

	/**
	 * @field private static final number
	 */
	_DEFAULT_ALIGNMENT: 10,
	
	/**
	 * @field public static final number
	 */
	WAIT_IMAGE_WIDTH: 16,

	/**
	 * @field public static final number
	 */
	WAIT_IMAGE_HEIGHT: 16,
	
	/**
	 * @method public static
	 */
	Create: function(parentElement, lookId, inlineMode, message, alignment, marginH, marginV) {
	
		if (alignment===undefined) {
			alignment=f_waiting._DEFAULT_ALIGNMENT;
		}
	
		f_core.Assert(parentElement.tagName, "parent is not a DOM element !");
		var node=document.createElement("DIV");
		
		var className="f_waiting";
		node.className=className+((inlineMode)?" f_waiting_inline":" f_waiting_absolute");
		node.setAttribute("v:class", className);
		if (lookId) {
			node.setAttribute("v:lookId", lookId);
		}

		node._parentElement=parentElement;
		
		if (!message) {
			message=f_waiting.GetLoadingMessage();
		}
		
		node.appendChild(document.createTextNode(message));
		
		if (parentElement.tagName.toUpperCase()=="SELECT") {
			parentElement=parentElement.parentNode;
		}

		parentElement.appendChild(node);

		if (typeof(marginH)=="number") {
			node._marginH=marginH;
		}
		
		if (typeof(marginV)=="number") {
			node._marginV=marginV;
		}
		
		if (typeof(alignment)=="number") {
			node._alignment=alignment;
		}
		
		return f_waiting._classLoader._init(node);
	},
	/**
	 * @method hidden static
	 * @return String
	 */
	GetLoadingMessage: function() {
		return f_resourceBundle.Get(f_waiting).f_get("LOADING_MESSAGE");
	},
	/**
	 * @method hidden static
	 * @return String
	 */
	GetReceivingMessage: function() {
		return f_resourceBundle.Get(f_waiting).f_get("RECEIVING_MESSAGE");
	},
	/**
	 * @method private static
	 * @param HTMLElement node
	 * @return void
	 */
	_Layout: function(node) {
		var parentElement=node._parentElement;
		if (!parentElement || !parentElement.tagName) {	
			f_core.Info(f_waiting, "Unknown type of parent: '"+parentElement+"' for node '"+node+"'.");
			return;
		}
		
		var x=0;
		if (node._marginH) {
			x+=node._marginH;
		}
		
		var y=0;
		if (node._marginV) {
			y+=node._marginV;
		}
		
		if (node._alignment) {
			var ha=(node._alignment & 3);
			var va=(node._alignment & 12);

			if (ha>0) {
				if (ha==2) {
					// CENTER
					x=(parentElement.offsetWidth-f_waiting.WIDTH)/2;
					
				} else if (ha==3) {
					// RIGHT
					x=parentElement.offsetWidth-x;
					if (x>=f_waiting.WIDTH) {
						x-=f_waiting.WIDTH;
					}
				}		
			}
			
			if (va>0) {
				if (va==8) {
					// MIDDLE
					y=(parentElement.offsetHeight-f_waiting.HEIGHT)/2;
					
				} else if (va==12) {
					// BOTTOM
					y=parentElement.offsetHeight-y;
					if (y>=f_waiting.HEIGHT) {
						y-=f_waiting.HEIGHT;
					}
				}
			}
		}

		if (x<0) {
			x=0;
		}
		
		if (y<0) {
			y=0;
		}
		
		for(var p=node._parentElement;p;p=p.offsetParent) {
			var pos=f_core.GetCurrentStyleProperty(p, "position");

			if (pos=="absolute" || pos=="relative") {
				break;
			}	

			// Le parent peut pas positionner l'enfant !
			x+=p.offsetLeft;
			y+=p.offsetTop;
			
		}
				
		node.style.left=x+"px";
		node.style.top=y+"px";
//		node.style.display="block";
//		document.title="x="+x+"/y="+y;
	},
	/**
	 * @method private static
	 * @return void
	 */
	_TimeOut: function(waiting) {
		var cur=waiting._opacity;
		var next=waiting._next;

		if (cur==next) {
			if (next==0 && waiting.style.display!="none") {
				waiting.style.display="none";
			}
		
			window.clearInterval(waiting._timerId);
			waiting._timerId=undefined;
			return;
		}
		
		if (waiting._waitStep>0) {
			waiting._waitStep--;
			return;
		}
		
		var oldCur=cur;
		
		if (cur>next) {
			cur-=f_waiting._STEP;
			if (cur<next) {
				cur=next;
			}
		} else {
			cur+=f_waiting._STEP;
			if (cur>next) {
				cur=next;
			}
		}
		waiting._opacity=cur;
		
		if (waiting.style.opacity!==undefined) {
			// CSS 3  on peut toujours réver !
			waiting.style.opacity = cur;

		} else if (f_core.IsInternetExplorer()) {
			waiting.style.filter = "alpha(opacity="+Math.floor(cur*100)+")";

		} else if (f_core.IsGecko()) {
			waiting.style.MozOpacity = cur;

		} else {
			// On arrete tout de suite le petit jeu !
			waiting._opacity=next;
			if (next>0) {
				waiting.style.display="block";
			} else {
				waiting.style.display="none";
			}
			return;
		}
		
		if (oldCur==0) {
			waiting.style.display="block";
		}
	},
	/**
	 * @method hidden static 
	 * @return String
	 */
	 GetWaitingImageURL: function() {
		return f_waiting._GetImageURL(f_waiting._WAITING_IMAGE_URL);
	 },
	/**
	 * @method hidden static 
	 * @return String
	 */
	 GetWaitingErrorImageURL: function() {
		return f_waiting._GetImageURL(f_waiting._WAITING_ERROR_IMAGE_URL);
	 },
	/**
	 * @method private static 
	 * @return String
	 */
	 _GetImageURL: function(imageURL) {
		var styleSheetBase=f_env.GetStyleSheetBase();

		imageURL=styleSheetBase+imageURL;

		f_imageRepository.PrepareImage(imageURL);
		
		return imageURL;
	 }
} 

var __prototype = {

	/*
	f_waiting: function() {
		this.f_super(arguments);
	},
	*/
	f_finalize: function() {
		var timerId=this._timerId; // Quel type ?
		if (timerId) {
			this._timerId=undefined;

			this._kclass._classLoader._window.clearInterval(timerId);
		}
		
		// this._opacity=undefined; // number
		// this._next=undefined; // number
		// this._marginH=undefined; // number
		// this._marginV=undefined; // number
		// this._alignment=undefined; // number 
		// this._text=undefined; // string
		// this._waitStep=undefined; // number
		this._parentElement=undefined; // HtmlElement
		// this._parentElementOldCursor=undefined; // string

		this.f_super(arguments);
	},
	
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return this._text;
	},
	
	/**
	 * @method public
	 * @param String text
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(typeof(text)=="string", "f_waiting.f_setText: invalid text parameter ("+text+")");
		this._text=text;
		text=f_core.EncodeHtml(text);
		
		if (f_core.IsInternetExplorer()) {
			// Le font aliasing de IE fait que la fonte à l'aspect du GRAS !
			
			text="<span style='background-color: white'>&nbsp;"+text+"</span>";
		}
		this.innerHTML=text;
	},
	
	/**
	 * @method public
	 * @return void
	 */
	f_close: function() {
		var parent=this.parentNode;
		f_core.Assert(parent && parent.tagName, "No parent ! Already closed ?");
		
		parent.removeChild(this);
	},
	
	/**
	 * @method public
	 * @return void
	 */
	f_show: function() {
		if (!this._parentElementOldCursor) {
			var cursor=this._parentElement.style.cursor;
			if (!cursor) {
				cursor="auto";
			}
			this._parentElementOldCursor=cursor;
		}
		this._parentElement.style.cursor="wait";
			
		this._installTimer(1);
	},
	
	/**
	 * @method public
	 * @param optional boolean immediately
	 * @return void
	 */
	f_hide: function(immediately) {
		this._installTimer(0, immediately);
		
		if (this._parentElementOldCursor) {
			if (this._parentElement.style.cursor=="wait") {
				this._parentElement.style.cursor=this._parentElementOldCursor;
			}
			this._parentElementOldCursor=undefined;
		}
	},
	
	/**
	 * @method private
	 * @param number level
	 * @param optional boolean immediately
	 * @return void
	 */
	_installTimer: function(level, immediately) {
		if (level<0) {
			level=0;
			
		} else if (level>1) {
			level=1;
		}
		
		if (typeof(this._opacity)!="number") {
			this._opacity=0;
		}
		
		if (this._next==level) {
			return;
		}
		this._next=level;
	
		f_waiting._Layout(this);

		if (this._timerId) {	
			return;
		}

		if (!immediately) {
			this._waitStep=f_waiting._FIRST_STEP;
		}
		
		var waiting=this;
		this._timerId=window.setInterval(function() {
			f_waiting._TimeOut.call(f_waiting, waiting);
			
		}, f_waiting._STEP_MS);
	}
}
var f_waiting=new f_class("f_waiting", null, __static, __prototype, f_object);
