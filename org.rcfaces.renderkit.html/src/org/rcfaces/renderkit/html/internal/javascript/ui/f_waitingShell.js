/*
 * $Id$
 */

/**
 * <p><strong>f_waitingShell</strong> represents popup modal window.
 *
 * @class public final f_waitingShell extends f_shell
 * @author Fred Lefevere-Laoide Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
     /**
     * @method public static
     * @return void
     */
    WaitForSubmit: function() {
 		var waitingShell=f_waitingShell.f_newInstance();
 		waitingShell.f_show();
	}
}

var __members = {

	/**
	 * <p>Construct a new <code>f_waitingShell</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_waitingShell: function() {
		this.f_super(arguments);
		if (this.nodeType==f_core.ELEMENT_NODE) {
			this.f_setHeight(f_core.GetNumberAttribute(this, "v:height", 150));
			this.f_setWidth(f_core.GetNumberAttribute(this, "v:width", 150));
			
			var imageURL=f_core.GetAttribute(this, "v:imageURL");
			if (imageURL) {
				this.f_setImageURL(imageURL);
			}
			this.f_setCssClassBase(f_core.GetAttribute(this, "v:cssClassBase", "f_waitingShell"));
			this.f_setBackgroundMode(f_core.GetAttribute(this, "v:backgroundMode", "greyed"));
			var waitingShell=this;

			var submitCb=function() {
				waitingShell.f_show();
			}
			f_core.AddPostSubmitListener(submitCb);

		} else {
			this.f_setCssClassBase("f_waitingShell");
			this.f_setBackgroundMode("greyed");
			this.f_setWidth(150);
			this.f_setHeight(150);
		}
		
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
	 * @return void
	 */
	f_fillWaitingShell: function() {
     	f_core.Debug(f_waitingShell, "f_fillWaitingShell: entering");

		this.f_fillModIFrame(arguments);

		//Hide Selects
		f_shell.HideSelect();

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
			img.style.width=this.f_getWidth()+"px";
			img.style.height=this.f_getHeight()+"px";
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
		return this.f_fillWaitingShell;
	},

	/**
	 * @method public
	 * @return void
	 */
	f_show: function() {
     	f_core.Debug(f_waitingShell, "f_show: entering ");

		var url=this.f_getImageURL();
		if (!url) {
			this.f_setWidth(1);
			this.f_setHeight(1);
		}
		// Create a blocking Div
		this.f_drawModIFrame();
		
		//draw the image
		this.f_constructIframe();

	},

	/**
	 * @method public
	 * @return void
	 */
	f_hide: function() {
     	f_core.Debug(f_waitingShell, "f_hide : entering ");
		
		// Hide the blocking Div
		f_shell.DelModIFrame();
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_waitingShell]";
	}
}

new f_class("f_waitingShell", null, __statics, __members, f_shell);
