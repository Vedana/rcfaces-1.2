/*
 * $Id$
 */

/**
 * <p><strong>f_waitingShell</strong> represents popup modal window.
 *
 * @class public final f_waitingShell extends f_dialog
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
var __statics = {
 	
	/**
	 * @field private static final
	 */
	_DEFAULT_FEATURES: {
		priority: 0,
		width: 150,
		height: 150,
		styleClass: "f_waitingShell",
		backgroundMode: f_shell.GREYED_BACKGROUND_MODE
	},
 
    /**
     * @method public static
     * @return void
     */
    WaitForSubmit: function() {
 		var waitingShell=f_waitingShell.f_newInstance();
 		
 		waitingShell.f_open();
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
		this.f_super(arguments, f_shell.TRANSPARENT);
		
		if (this.nodeType==f_core.ELEMENT_NODE) {
			var imageURL=f_core.GetAttribute(this, "v:imageURL");
			if (imageURL) {
				this.f_setImageURL(imageURL);
			}

			var text=f_core.GetAttribute(this, "v:text");
			if (text) {
				this.f_setText(text);
			}

			var visible=f_core.GetBooleanAttribute(this, "v:visible", true);
			if (visible) {
				this.f_installShowOnSubmit();
			}
		}
	},
	/*
	f_finalize: function() {
		this.f_super(arguments);
		//this._imageURL=undefined; // String
		//this._text=undefined; // String
	},
	*/

	/**
	 * @method public
	 * @return void
	 */
	f_installShowOnSubmit: function() {
		var self=this;
		var submitCb=function() {
			self.f_open();
		}
		f_core.AddPostSubmitListener(submitCb);
	},
	/**
	 *  <p>Fill a modal iFrame. 
	 *  </p>
	 *
	 * @method protected
	 * @return void
	 */
	f_fillBody: function(base) {
		this.f_super(arguments, base);

		var tr=f_core.CreateElement(base, "table", {
			cellPadding: 0,
			cellSpacing: 0
		}, "tbody", null, "tr", null);

		var url=this.f_getImageResolvedURL();
		if (url) {
			f_core.CreateElement(tr, "td", {
				align: "center",
				valign: "middle"
			}, "img", {
				className: "f_waitingShell_image",
				src: url
			});
		}
		
		var text=this._text;
		if (text) {
			f_core.CreateElement(tr, "td", {
				align: "center",
				valign: "middle"
			}, "span", {
				className: "f_waitingShell_text",
				textNode: text
			});
		}
	},

	/**
	 *  <p>Gets the image URL.</p>
	 *
	 * @method public 
	 * @return String imageURL 
	 */
	f_getImageURL: function() {
		return this._imageURL;
	},
	
	/**
	 *  <p>Gets the image resolved URL.</p>
	 *
	 * @method public 
	 * @return String imageURL 
	 */
	f_getImageResolvedURL: function() {
		if (!this._imageURL) {
			return null;
		}
		
		return f_env.ResolveContentUrl(this._imageURL);
	},
	/**
	 *  <p>Sets the image URL.</p>
	 *
	 * @method public 
	 * @param String imageURL  (or <code>null</code>)
	 * @return void
	 */
	f_setImageURL: function(imageURL) {
    	f_core.Assert(imageURL===null || typeof(imageURL)=="string", "f_shell.f_setImageURL: Invalid parameter '"+imageURL+"'.");
    	
		this._imageURL = imageURL;
	},
	/**
	 * @method public
	 * @param String text
	 * @return void
	 */
	f_setText: function(text) {
		this._text=text;
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
	 * @return String
	 */
	toString: function() {
		return "[f_waitingShell]";
	}
}

new f_class("f_waitingShell", {
	extend: f_dialog,
	statics: __statics,
	members: __members
});
