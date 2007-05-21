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
		this.f_super(arguments);
		
		_viewURL=undefined // string
	},
	*/

	/**
	 *  <p>Return the viewURL URL.</p>
	 *
	 * @method public 
	 * @return string viewURL
	 */
	f_getViewURL: function() {
		return this._viewURL;
	},
	
	/**
	 *  <p>Sets the viewURL URL.</p>
	 *
	 * @method public 
	 * @param string viewURL
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
		return this.f_getViewURL();
	},

	/**
	 * @method public
	 * @param optionnal string viewURL : the url to be shown in the iframe
	 * @return void
	 */
	f_openURL: function(viewURL) {
     	f_core.Debug(f_viewDialog, "f_openURL: entering ("+viewURL+")");
		f_core.Assert(arguments.length==0 || viewURL || typeof(viewURL)=="string", "f_viewDialog.f_openURL: first parameter is optionnal but must be a string");
		
		if (viewURL) {
			this.f_setViewURL(viewURL);
		}

		this.f_openDialog(null, this.f_getViewURL());
		

	},

	/**
	 * @method public
	 * @param boolean aSuivre : true if we're to draw the next Dialog
	 * @return void
	 */
	f_close: function(aSuivre) {
     	f_core.Debug(f_viewDialog, "f_close: entering ("+aSuivre+")");

		if (aSuivre) {
			f_dialog.ShowNextDialogStored();
			return;
		}
		//delete the iFrame
		this.f_delModIFrame();

	},

	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		var ts = this.f_super(arguments);
		ts = ts + "\n[f_viewDialog viewURL='"+this._viewURL+"']";
		return ts;
	}
}

new f_class("f_viewDialog", null, __static, __prototype, f_dialog);
