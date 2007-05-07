/*
 * $Id$
 */

/**
 * <p><strong>f_waitingShell</strong> represents popup modal window.
 *
 * @class public final f_waitingShell extends f_shell
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
}

var __prototype = {

	/**
	 * <p>Construct a new <code>f_waitingShell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_waitingShell: function() {
		this.f_super(arguments);
		
		this.f_setCssClassBase("f_waitingShell");
		this.f_setBackgroundMode("greyed");
		this.f_setImageURL("images/3MA_processingbar.gif")
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
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 */
	_fillModIFrame: function() {
     	f_core.Debug(f_shell, "_FillModIFrame: entering");

		this.f_super(arguments);

		//Hide Selects
		f_shell._HideSelect();

		var iframe=this.f_getIframe();
		var docBase = iframe.contentWindow.document;
		var base = docBase.body;
		var cssClassBase = this.f_getCssClassBase();
		if (!cssClassBase) {
			cssClassBase="f_waitingShell";
		}

		var url=this.f_getImageResolvedURL();
		if (url) {
			var img = docBase.createElement("img");
			img.className=cssClassBase+"_image"
			img.src=url;
			base.appendChild(img);
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
	 * @method public
	 * @return 
	 */
	f_show: function() {
     	f_core.Debug(f_waitingShell, "f_show: entering ");
		
		// Create a blocking Div
		this._drawModIFrame();

	},

	/**
	 * @method public
	 * @return 
	 */
	f_hide: function() {
     	f_core.Debug(f_waitingShell, "f_hide : entering ");
		
		// Hide the blocking Div
		f_shell._DelModIFrame();
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_waitingShell]";
	}
}

new f_class("f_waitingShell", null, __static, __prototype, f_shell);
