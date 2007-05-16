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
     * @field private static Array
     */
	_Dialogs: undefined,
    /**
     * @field private static boolean
     */
	_DocComplete: undefined,
	
     /**
     * @method protected static
     * @return boolean
     */
    IsDocComplete: function() {
	    f_core.Debug(f_dialog, "IsDocComplete: entering");
		return f_dialog._DocComplete;
    },

     /**
     * @method protected static
     * @return number
     */
    GetNumDialogs: function() {
	    f_core.Debug(f_dialog, "GetNumDialogs: entering");
	    if (f_dialog._Dialogs) {
	    	return f_dialog._Dialogs.length;
	    }
	    return 0;
    },

     /**
     * @method protected static
     * @param f_dialog dialog
     * @param function functionOpen
     * @param string viewURL
     * @param number priority
     * @return void
     */
    AddDialog: function(dialog, functionOpen, viewURL, priority) {
	    f_core.Debug(f_dialog, "AddDialog: entering");
		if (!f_dialog._Dialogs) {
			f_dialog._Dialogs = new Array();
		}
		f_dialog._Dialogs.push({
			_dialog: dialog,
			_function: functionOpen,
			_url: viewURL,
			_priority: priority
		});
    },

    /**
     * <p>Call the next stored dialog</p>
     *
     * @method protected static
     * @return void
     */
    ShowNextDialogStored: function() {
	    f_core.Debug(f_dialog, "ShowNextDialogStored: entering");
    	var dialogs = f_dialog._Dialogs;

    	if (dialogs && dialogs.length) {
		    f_core.Debug(f_dialog, "ShowNextDialogStored: "+dialogs.length+" Items");

			var maxPriority = 0;
			var maxIndex = 0;
			
			// Search for the highest priority
			for (var i=0; i<dialogs.length; i++) {
		     	var dialog = dialogs[i];
				if (dialog._priority > maxPriority) {
					maxPriority = dialog._priority;
					maxIndex = i;
				}
			}
			
			var dial = dialogs[maxIndex];
			dialogs[maxIndex] = dialogs[0];
	     	dialogs.shift();
    		var dialogInst = dial._dialog;
			var functionToCall = dial._function;
			var url = dial._url;
			var iframe = dialogInst.f_getIframe();

			dialogInst.f_decorateIframe(iframe);
			dialogInst.f_decorateDiv(dialogInst.f_getDiv());
			
			if (typeof(functionToCall) == "function") {
			    f_core.Debug(f_dialog, "ShowNextDialogStored: before calling ");

				functionToCall.call(dialogInst, iframe.contentWindow.document.body);
				
			    f_core.Debug(f_dialog, "ShowNextDialogStored: after calling ");
			} else {
				f_core.Debug(f_dialog, "ShowNextDialogStored: setting an url : "+url);

				if (!url || typeof(url) != "string") {
					url = "about:blank"
				}
				iframe.src = url;
			}

			iframe._modalShell.f_setFocus(true);

			return;
		}
		
		f_shell.DelModIFrame();
    },
    
     /**
     * Class Destructor (called in the head ...
     * @method public static
     */
    Finalizer: function() {
    	f_dialog._Dialogs = undefined; // List<Object>
    	// f_dialog._DocComplete = undefined; // boolean
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
	    	
	    	var iframe = f_shell.GetIframe();
	    	
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
    },

    /**
     *
     * @method public static
     */
     DocumentComplete: function() {
     	f_core.Debug(f_dialog, "DocumentComplete: entering");
     	
     	f_dialog._DocComplete=true;
     	f_dialog.ShowNextDialogStored();
     }
    
    
}

var __prototype = {

	/**
	 * @field private String
	 */
	_priority: undefined,

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
	 * <p>Destruct a new <code>f_dialog</code>.</p>
	 *
	 * @method public
	 *
	f_finalize: function() {
		this.f_super(arguments);
		//this._priority=undefined; // int
	},
	*/

	/**
	 *  <p>Return the priority.</p>
	 *
	 * @method public 
	 * @return int priority
	 */
	f_getPriority: function() {
		return this._priority;
	},
	/**
	 *  <p>Sets the priority.</p>
	 *
	 * @method public 
	 * @param int priority
	 * @return void
	 */
	f_setPriority: function(priority) {
    	f_core.Assert(typeof(priority)=="number", "f_dialog.f_setPriority: Invalid priority parameter '"+priority+"'."+typeof(priority));

		this._priority = priority;
	},
	
	/**
	 *  <p>draw a dialog. 
	 * Should not be called directly
	 *  </p>
	 *
	 * @method protected 
	 * @param fFunction callback The callback function to be called when the messageBox is closed
	 * @param string viewURL the url to show
	 * @return void
	 */
	f_openDialog: function(drawingFunction, viewURL) {
     	f_core.Debug(f_dialog, "f_open: entering ");
		
		// Create a blocking Div
		this.f_drawModIFrame();

		f_dialog.AddDialog(this, drawingFunction, viewURL, this.f_getPriority());

		if (f_dialog.IsDocComplete) {
//			this.f_drawContent(f_dialog.ShowNextDialogStored);
		}

	},

	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		var ts = this.f_super(arguments);
		ts = ts + "\n[f_dialog priority='"+this._priority+"']";
		return ts;
	}
}

new f_class("f_dialog", null, __static, __prototype, f_shell);
