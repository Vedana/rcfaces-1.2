/*
 * $Id$
 */

/**
 * <p><strong>f_viewDialog</strong> represents popup modal view.
 *
 * @class public final f_viewDialog extends f_dialog
 * @author Fred Lefevere-Laoide Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {

	/**
	 * @field private String
	 */
	_viewURL: undefined,
	

	/**
	 * @field private HtmlIFrame
	 */
	_iframe: undefined,

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

			this.f_setStyleClass(f_core.GetAttribute(this, "v:styleClass", "f_viewDialog"));
			this.f_setBackgroundMode(f_core.GetAttribute(this, "v:backgroundMode", f_shell.GREYED_BACKGROUND_MODE));

			var title=f_core.GetAttribute(this, "v:text");
			if (title) {
				this.f_setTitle(title);
			}

			this.f_setPriority(f_core.GetNumberAttribute(this, "v:dialogPriority", 0));
			
			if (f_core.GetBooleanAttribute(this, "v:visible", true)) {
				this.f_open();
			}
			
		} else {
			this.f_setCssClassBase("f_viewDialog");
			this.f_setBackgroundMode(f_shell.GREYED_BACKGROUND_MODE);
			this.f_setWidth(500);
			this.f_setHeight(400);
			this.f_setPriority(0);
		}
		
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {
		// this._viewURL=undefined // string
		this._iframe=undefined; // HtmlIFrame

		this.f_super(arguments);		
	},

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
		
		if (this._iframe) {
			this._iframe.src=this.f_getIFrameUrl();
		}
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
		if (!url) {
			return "about:blank";
		}
		
		var pos=url.indexOf("?");
		if (pos<0) {
			return url+"?vedvar=1";
		} 
		
		return url.substring(0,pos+1)+"vedvar=1&"+url.substring(pos+1);
	},
	
	f_fillBody: function(base) {
		var iframe=f_core.CreateElement(base, "iframe");
		this._iframe=iframe;
		
		iframe.className="f_viewDialog_frame";
		iframe.frameBorder=0;
		iframe.style.width=this.f_getWidth();
		iframe.style.height=this.f_getHeight();
		
		iframe.src=this.f_getIFrameUrl();
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_viewDialog viewURL='"+this._viewURL+"']";
	}
}

new f_class("f_viewDialog", {
	extend: f_dialog,
	members: __members
});
