/*
 * $Id$
 */

/**
 * <p><strong>f_dialog</strong> represents popup modal window.
 *
 * @class public final f_dialog extends f_shell
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
    /**
     * @field private static Object
     */
	_ObjMove: undefined,
     /**
     * Class Destructor (called in the head ...
     * @method public static
     * @return void
     */
    Finalizer: function() {
    	f_dialog._ObjMove = undefined; // Object
	},
    /**
     * <p>On Mouse Down Handler</p>
     *
     * @method private static
     * @param Event evt
     * @return void
     */
    _OnMouseDown: function(evt) {
		f_core.Debug(f_dialog, "_OnMouseDown: entering ("+evt+")");
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
//		document.captureEvents(Event.MOUSEMOVE);
		
		// memorize the position
		f_dialog._ObjMove = {
			_deltaX: evt.clientX,
			_deltaX: evt.clientY
		};
		
		f_core.Debug(f_dialog, "_OnMouseDown: exiting ("+evt+")");
		return f_core.CancelJsEvent(evt);
    },
    
    /**
     * <p>On Mouse Up Handler</p>
     *
     * @method private static
     * @return void
     */
    _OnMouseUp: function() {
	    f_core.Debug(f_dialog, "OnMouseUp: entering");
		f_dialog._ObjMove = undefined;
    },
    
    /**
     * <p>On Mouse Move Handler</p>
     *
     * @method private static
     * @return boolean
     */
    _OnMouseMove: function(evt) {
	    var objMove = f_dialog._ObjMove;
	    if (objMove) {
		    f_core.Debug(f_dialog, "_OnMouseMove : entering");
	    
			if (!evt) {
				evt = f_core.GetJsEvent(this);
			}
		    f_core.Debug(f_dialog, "_OnMouseMove: evt "+evt+" : "+evt.clientX+", "+evt.clientY);
	    	
	    	var iframe = f_shell._ObjIFrame._iframe;
	    	
			// memorize the position
			var posX = evt.clientX;
			var posY = evt.clientY;
		    f_core.Debug(f_dialog, "_OnMouseMove: iframe before "+iframe.style.left+", "+iframe.style.top);
			iframe.style.left = iframe.style.left + posX - objMove._deltaX;
			iframe.style.top = iframe.style.top + posY - objMove._deltaY;
		    f_core.Debug(f_dialog, "_OnMouseMove: iframe after "+iframe.style.left+", "+iframe.style.top);
			objMove = {
				_deltaX: posX,
				_deltaX: posY
			};
	    	//if (evt.clientX >= 0 && evt.clientY >= 0) {
			//}
			return f_core.CancelJsEvent(evt);
	    }
    }
    
}

var __prototype = {

	/**
	 * <p>Construct a new <code>f_dialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param number style the style of control to construct
	 */
	f_dialog: function(style) {
		this.f_super(arguments, style);
		
		this.f_setCssClassBase("f_dialog");
		this.f_setBackgroundMode("greyed");
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 *
	f_finalize: function() {
		this.f_super(arguments);
	},
	*/


	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_dialog]";
	}
}

new f_class("f_dialog", null, __static, __prototype, f_shell);
