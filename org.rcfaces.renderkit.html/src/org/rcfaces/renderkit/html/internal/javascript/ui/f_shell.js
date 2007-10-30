/*
 * $Id$
 */

/**
 * <p><strong>f_shell</strong> represents popup window.
 *
 * @class public final f_shell extends f_object, fa_eventTarget
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
var __statics = {
	
	/**
	 * @field public static final String
	 */
	CLOSE_BUTTON_EVENT: "closeButton",
	
	/**
	 * @field public static final String
	 */
	GREYED_BACKGROUND_MODE: "greyed",
	
	/**
	 * @field public static final String
	 */
	LIGHT_GREYED_BACKGROUND_MODE: "light",
	
	/**
	 * @field public static final String
	 */
	TRANSPARENT_BACKGROUND_MODE: "transparent",
	
	/**
	 * @field public static final String
	 */
	OPAQUE_BACKGROUND_MODE: "opaque",
	
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
	 * Style constant for hide background
	 * 
	 * @field public static final number
	 */
	HIDE_SCREEN_STYLE: 1<<11,
	
	/**
	 * Copy styleSheet from the main frame
	 * 
	 * @field public static final number
	 */
	COPY_STYLESHEET: 1<<12,
	
	/**
	 * Force the body of the shell to be an IFrame
	 * 
	 * @field public static final number
	 */
	FRAME_ELEMENT: 1<<13,
	
	/**
	 * The frame can be transparent
	 * 
	 * @field public static final number
	 */
	TRANSPARENT: 1<<14,
	
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
	 * @field private static final number
	 */	
	_DEFAULT_HEIGHT: 100,
	
	/**
	 * @field private static final number
	 */	
	_DEFAULT_WIDTH: 300,
	
	/**
	 * @field private static number
	 */
	_ID: 0,
	
	/**
	 * @field public static final number
	 */
	CREATED_STATUS: 0x00,
	
	/**
	 * @field public static final number
	 */
	OPENING_STATUS: 0x10,
	
	/**
	 * @field public static final number
	 */
	OPENED_STATUS: 0x12,
	
	/**
	 * @field public static final number
	 */	
	CLOSING_STATUS: 0x20,
	
	/**
	 * @field public static final number
	 */	
	ABOUT_TO_CLOSE_STATUS: 0x21,
	
	
	/**
	 * @field public static final number
	 */
	CLOSED_STATUS: 0x24,
	
	/**
	 * @field public static final number
	 */
	DESTROYING_STATUS: 0x30,
	
	/**
	 * @field public static final number
	 */
	DESTROYED_STATUS: 0x31
}

var __members = {

	_id: undefined,

	/**
	 * @field private number
	 */
	_style: undefined,
	
	/**
	 * @field private String
	 */
	_backgroundMode: undefined,

	/**
	 * @field private number
	 */
	_height: undefined,
	
	/**
	 * @field private number
	 */
	_width: undefined,
	
	/**
	 * @field private number
	 */
	_priority: 0,
	
	
	/**
	 * @field private number
	 */
	_shellStatus: 0,
	
	/**
	 * @field hidden boolean
	 */
	_showNextShell: true,
	
	/**
	 * <p>Construct a new <code>f_shell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param optional number style the style of control to construct
	 * @param optional function drawingFunction
	 * @param optional function returnValueFunction
	 */
	f_shell: function(style, drawingFunction, returnValueFunction) {
		this.f_super(arguments);

		f_core.Assert(style===undefined || typeof(style)=="number", "f_shell.f_shell: Invalid style parameter ("+style+")");
		f_core.Assert(drawingFunction===undefined || typeof(drawingFunction) == "function", "f_shell.f_shell: bad parameter type: drawingFunction is not a function "+drawingFunction);
		
		this._id="shell_"+(f_shell._ID++);
		
		this._style=(style)?style:0;
		this._backgroundMode=(this._style & f_shell.HIDE_BACKGROUND_STYLE)?f_shell.GREYED_BACKGROUND_MODE:null;
		this._drawingFunction=drawingFunction;
		this._returnValueFunction=returnValueFunction;
		this._width=f_shell._DEFAULT_WIDTH;
		this._height=f_shell._DEFAULT_HEIGHT;
		
		this._shellManager=f_shellManager.Get();
	},


	/**
	 * <p>Destruct a <code>f_shell</code>.</p>
	 *
	 * @method public
	 * @return void
	 */
	f_finalize: function() {
		var shellStatus=this._shellStatus;
		if (shellStatus!=f_shell.CREATED_STATUS &&
			shellStatus!=f_shell.DESTROYED_STATUS) {

			if (shellStatus<f_shell.CLOSED_STATUS) {
				this.f_preDestruction();
			}

			this._shellStatus=f_shell.DESTROYING_STATUS;

			this.f_postDestruction();
			
			this._shellStatus=f_shell.DESTROYED_STATUS;			
		}
		
		// this._backgroundMode=undefined; //string
		// this._imageURL=undefined; //string
		// this._style=undefined; //number
		//this._height=undefined; // number
		//this._width=undefined; // number
		// this._styleClass=undefined; // string
		
		this._drawingFunction=undefined; // function
		this._returnValueFunction=undefined; // function

		this._shellManager=undefined; // f_shellManager

		this._shellBody=undefined; // HtmlElement

		this.f_super(arguments);
	},

	/**
	 * @method public
	 * @return String
	 */
	f_getId: function() {
		return this._id;
	},

	/**
	 *  <p>Return the priority.</p>
	 *
	 * @method public 
	 * @return number priority
	 */
	f_getPriority: function() {
		return this._priority;
	},
	
	/**
	 *  <p>Sets the priority.</p>
	 *
	 * @method public 
	 * @param number priority
	 * @return void
	 */
	f_setPriority: function(priority) {
    	f_core.Assert(typeof(priority)=="number", "f_shell.f_setPriority: Invalid priority parameter '"+priority+"'."+typeof(priority));

		this._priority = priority;
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
	 *  <p>decorate the iframe
	 *  </p>
	 *
	 * @method protected
	 * @param HTMLElement iframe
	 * @return void
	 */
	f_prepareOpening: function() {
     	f_core.Debug(f_shell, "f_prepareOpening: entering");
		
		var width=this.f_getWidth();
		if (!width || width<1) {  // Il faut traiter undefined
			width=f_shell._DEFAULT_WIDTH;
			this.f_setWidth(width);
		}

		var height=this.f_getHeight();
		if (!height || height<1) {
			height=f_shell._DEFAULT_HEIGHT;
			this.f_setHeight(height);
		}
		
		var shellDecorator=this._shellManager.f_getShellDecorator(this);
		var mySize=shellDecorator.f_computeTrim(width, height);
		
		// calculate iframe size and position
		var viewSize=f_core.GetViewSize();
	
		var x=0;
		if (viewSize.width > mySize.width) {
			x = Math.round((viewSize.width - mySize.width)/2);
			
		} else {
			mySize.width = viewSize.width;
		}
		
		var y=0;
		if (viewSize.height > mySize.height) {
			y = Math.round((viewSize.height - mySize.height)/2);
			
		} else {
			mySize.height = viewSize.height;
		}
		
		var scrolls=f_core.GetScrollOffsets();		
		x+=scrolls.x;
		y+=scrolls.y;
			
		this._shellManager.f_setShellBounds(this, x, y, mySize.width, mySize.height);
	},
	
	/**
	 *  <p>construct the iframe. 
	 *  </p>
	 *
	 * @method protected
	 * @return void
	 */
	f_fillBody: function(shellBody) {
     	f_core.Debug(f_shell, "f_fillBody: fill body in "+shellBody);		
     	
     	this._shellBody=shellBody;
	},

	/**
	 *  <p>delete the iframe. </p>
	 *
	 * @method protected
	 * @return void
	 */
	f_deleteBody: function() {
     	f_core.Debug(f_shell, "f_deleteBody: entering");
     	
     	this._shellBody=null;
	},	
	
	/**
	 * @method protected
	 * @param boolean firstTime
	 * @return void
	 */
	f_setFocus: function(firstTime) {
		f_core.Debug(f_shell, "f_setFocus: entering with firstTime = "+firstTime);
		if (!this._shellBody) {
			f_core.Debug(f_shell, "f_setFocus: Doc is not complete yet");
			return;
		}
		
		var nextFocusable=null;
		if (firstTime) {
			var inputs=f_core.GetElementsByTagName(this._shellBody, "input");
			
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
   			nextFocusable=f_core.GetNextFocusableComponent(this._shellBody);
  		}
  		
   		if (nextFocusable) {
     		f_core.Debug(f_shell, "f_setFocus: Set focus on "+nextFocusable.outerHTML);

	     	f_core.SetFocus(nextFocusable, false);
   		}
	},

	/**
	 * @method public
	 * @param function returnValueFunction
	 * @return void
	 */
	f_open: function(returnValueFunction) {
		f_core.Assert(
			this.f_getStatus()!=f_shell.OPENING_STATUS &&
			this.f_getStatus()!=f_shell.OPENED_STATUS &&
			this.f_getStatus()!=f_shell.CLOSING_STATUS, "f_open: Invalid shell state ! ("+this._status+")");
		
		this._returnValueFunction=returnValueFunction;
		
		this._shellManager.f_openShell(this);
	},	

	/**
	 * @method public
	 * @param optional function returnValueFunction
	 * @return void
	 */
	f_close: function(returnValue) {
		if (this.f_getStatus()!=f_shell.OPENED_STATUS) {
			return;
		}

		this.f_setStatus(f_shell.CLOSING_STATUS);
		
		var self=this;
		
		// On découple la destruction ... pour éviter des problèmes de sécurité !
		window.setTimeout(function() {
			self._shellManager.f_closeShell(self);
		}, 0);
	},
	f_preConstruct: function() {
	},
	f_postConstruct: function() {		
	},
	f_preDestruction: function() {
		this.f_setStatus(f_shell.ABOUT_TO_CLOSE_STATUS);
	},
	f_postDestruction: function() {
		this.f_setStatus(f_shell.DESTROYED_STATUS);

		var returnValueFunction=this._returnValueFunction;
		if (typeof(returnValueFunction)!="function") {
			return;
		}
			
		try {
			this._showNextShell=returnValueFunction.call(this, returnValue);

		} catch (x) {
			f_core.Error(f_shell, "f_shell.f_close: Exception when calling return value '"+returnValue+"'.", x);			
		}
	},
	/**
	 * @method public
	 * @param Strint title
	 * @return void
	 */
	f_setTitle: function(title) {
		f_core.Assert(title===null || typeof(title)=="string", "f_shell.f_setTitle: Invalid title parameter ('"+title+"')");

		if (title) {
			this._style|=f_shell.TITLE_STYLE;			
		}
		
		this._shellManager.f_setShellDecoration(this, f_shellDecorator.TITLE_DECORATOR, title);
	},
	/**
	 * @method protected
	 * @return f_shellManager 
	 */
	f_getShellManager: function() {
		return this._shellManager;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getStyleClass: function() {
		return this._styleClass;
	},
	/**
	 * @method public
	 * @param String styleClass
	 * @return void
	 */
	f_setStyleClass: function(styleClass) {
		f_core.Assert(styleClass===null || typeof(styleClass)=="string", "f_shell.f_setStyleClass: Invalid styleClass parameter ("+styleClass+")");

		this._styleClass=styleClass;
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getStatus: function() {
		return this._shellStatus;
	},
	/**
	 * @method hidden
	 * @param number status
	 * @return void
	 */
	f_setStatus: function(status) {
		f_core.Assert(typeof(status)=="number", "f_shell.f_setStatus: Invalid status parameter ("+status+")");

		this._shellStatus=status;
	},
	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		return "[f_shell id="+this._id+" styleClass='"+this._styleClass+"']";
	}
}

new f_class("f_shell", {
	extend: f_object,
	aspects: [ fa_eventTarget ],
	statics: __statics,
	members: __members
});
