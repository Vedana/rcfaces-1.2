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
var __statics = {
}

var __members = {

	/**
	 * @field private String
	 */
	_viewURL: undefined,

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
			
			this.f_setViewURL(f_core.GetAttribute(this, "v:viewURL", "about:blank"));

			this.f_setCssClassBase(f_core.GetAttribute(this, "v:cssClassBase", "f_viewDialog"));
			this.f_setBackgroundMode(f_core.GetAttribute(this, "v:backgroundMode", "greyed"));

			this.f_setPriority(f_core.GetNumberAttribute(this, "v:dialogPriority", 0));
			
			if (f_core.GetBooleanAttribute(this, "v:visible", true)) {
				this.f_openURL();
			}
		} else {
			this.f_setCssClassBase("f_viewDialog");
			this.f_setBackgroundMode("greyed");
			this.f_setWidth(500);
			this.f_setHeight(400);
			this.f_setPriority(0);
		}
		
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 *
	f_finalize: function() {
		_viewURL=undefined // string

		this.f_super(arguments);		
	},
	*/

	/**
	 *  <p>Return the viewURL URL.</p>
	 *
	 * @method public 
	 * @return String viewURL
	 */
	f_getViewURL: function() {
		return this._viewURL;
	},
	
	/**
	 *  <p>Sets the viewURL URL.</p>
	 *
	 * @method public 
	 * @param String viewURL
	 * @return void
	 */
	f_setViewURL: function(viewURL) {
    	f_core.Assert((typeof(viewURL)=="string"), "f_shell.f_setViewURL: Invalid parameter '"+viewURL+"'.");
    	
		this._viewURL = f_env.ResolveContentUrl(window, viewURL);
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
		var url=this.f_getViewURL();
		if (url) {
			var pos=url.indexOf("?");
			if (pos<0) {
				url=url+"?vedvar=1";
			} else {
				url=url.substring(0,pos+1)+"vedvar=1&"+url.substring(pos+1);
			}
		}
		return url;
	},

	/**
	 * @method public
	 * @param optionnal String viewURL the url to be shown in the iframe
	 * @return void
	 */
	f_openURL: function(viewURL) {
		f_core.Assert(arguments.length==0 || viewURL || typeof(viewURL)=="string", "f_viewDialog.f_openURL: first parameter is optionnal but must be a string");		
     	f_core.Debug(f_viewDialog, "f_openURL: entering ("+viewURL+")");

		if (viewURL) {
			this.f_setViewURL(viewURL);
		}

		this.f_openDialog(null, this.f_getViewURL());
	},

	/**
	 * @method public
	 * @param boolean showNext <code>true</code> if we're to draw the next Dialog
	 * @return void
	 */
	f_close: function(showNext) {
     	f_core.Debug(f_viewDialog, "f_close: entering ("+aSuivre+")");

		if (showNext) {
			f_dialog.ShowNextDialogStored();
			return;
		}
		
		//delete the iFrame
		this.f_deleteIframe();
		this.f_delModIFrame();
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_viewDialog shell="+this.f_super(arguments)+" viewURL='"+this._viewURL+"']";
	}
}

new f_class("f_viewDialog", null, __statics, __members, f_dialog);
