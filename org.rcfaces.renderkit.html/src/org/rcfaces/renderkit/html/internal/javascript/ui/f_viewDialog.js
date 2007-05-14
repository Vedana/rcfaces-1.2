/*
 * $Id$
 */

/**
 * <p><strong>f_viewDialog</strong> represents popup modal view.
 *
 * @class public final f_viewDialog extends f_dialog
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
}

var __prototype = {

	/**
	 * @field private String
	 */
	_src: undefined,

	/**
	 * <p>Construct a new <code>f_viewDialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_viewDialog: function() {
		this.f_super(arguments, f_shell.PRIMARY_MODAL_STYLE);
		
		if (this.nodeType==f_core.ELEMENT_NODE) {
			this.f_setHeight(f_core.GetNumberAttribute(this, "v:height", 500));
			this.f_setWidth(f_core.GetNumberAttribute(this, "v:width", 400));
			
			this.f_setSrc(f_core.GetAttribute(this, "v:src", "about:blank"));

			this.f_setCssClassBase(f_core.GetAttribute(this, "v:cssClassBase", "f_viewDialog"));
			this.f_setBackgroundMode(f_core.GetAttribute(this, "v:backgroundMode", "greyed"));
			
			if (f_core.GetBooleanAttribute(this, "v:visible", true)) {
				this.f_open();
			}
		} else {
			this.f_setCssClassBase("f_viewDialog");
			this.f_setBackgroundMode("greyed");
			this.f_setWidth(500);
			this.f_setHeight(400);
		}
		
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 *
	f_finalize: function() {
		this.f_super(arguments);
		
		_src=undefined // string
	},
	*/

	/**
	 *  <p>Return the src URL.</p>
	 *
	 * @method public 
	 * @return string src
	 */
	f_getSrc: function() {
		return this._src;
	},
	
	/**
	 *  <p>Sets the src URL.</p>
	 *
	 * @method public 
	 * @param string src
	 * @return void
	 */
	f_setSrc: function(src) {
    	f_core.Assert((typeof(src)=="string"), "f_shell.f_setSrc: Invalid parameter '"+src+"'.");
		this._src = f_env.ResolveContentUrl(window, src);
	},
	
	/**
	 *  <p>returns the callback to use to draw the iFrame 
	 *  </p>
	 *
	 * @method protected
	 * @return Function 
	 */
	f_getIFrameDrawingCallBack: function() {
		return null;
	},

	/**
	 *  <p>returns the url to show in the iFrame 
	 *  </p>
	 *
	 * @method protected
	 * @return String 
	 */
	f_getIFrameUrl: function() {
		return this.f_getSrc();
	},

	/**
	 * @method public
	 * @param optionnal string src : the url to be shown in the iframe
	 * @return void
	 */
	f_open: function(src) {
     	f_core.Debug(f_viewDialog, "f_open: entering ("+src+")");
		f_core.Assert(arguments.length==0 || src || typeof(src)=="string", "f_viewDialog.f_open: first parameter is optionnal but must be a string");
		
		if (src) {
			this.f_setSrc(src);
		}

		// Create a blocking Div
		this.f_drawModIFrame();

	},

	/**
	 * @method public
	 * @return void
	 */
	f_close: function() {
     	f_core.Debug(f_viewDialog, "f_close: entering ");

		// Create a blocking Div
		this.f_drawModIFrame();

	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_viewDialog]";
	}
}

new f_class("f_viewDialog", null, __static, __prototype, f_dialog);
