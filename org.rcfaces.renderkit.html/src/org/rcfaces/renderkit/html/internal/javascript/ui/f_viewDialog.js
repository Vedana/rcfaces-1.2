/*
 * $Id$
 */

/**
 * <p><strong>f_viewDialog</strong> represents popup modal view.
 *
 * @class public f_viewDialog extends f_dialog
 * @author Fred Lefevere-Laoide (latest mdialogodification by $Author$)
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */

var __statics = {
	
	/**
	 * @field private static final
	 */
	_DEFAULT_FEATURES: {
		width: 500,
		height: 400,
		priority: 0,
		styleClass: "f_viewDialog",
		backgroundMode: f_shell.GREYED_BACKGROUND_MODE
	}
	
}

var __members = {

	/**
	 * @field private String
	 */
	_viewURL: undefined,
	
	/**
	 * @field private HTMLIFrameElement
	 */
	_iframe: undefined,

	/**
	 * <p>Construct a new <code>f_viewDialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_viewDialog: function(style) {
		this.f_super(arguments, style | f_shell.PRIMARY_MODAL_STYLE);
		
		if (this.nodeType==f_core.ELEMENT_NODE) {
			
			this._parameters=f_core.ParseDataAttribute(this, "v:parameter");
			
			this.f_setViewURL(f_core.GetAttribute(this, "v:viewURL", "about:blank"));

			if (f_core.GetBooleanAttribute(this, "v:visible", true)) {
				this.f_open();
			}
		}		
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {
		// this._viewURL=undefined // string
		var iframe=this._iframe;
		if (iframe) {	
			this._iframe=undefined; // HtmlIFrame
			
			this.f_finalizeIframe(iframe);
		}
		this._parameters=undefined;
		this.f_super(arguments);		
	},
	/**
	 * @method protected
	 * @return Object
	 */
	f_getDefaultFeatures: function() {
		return f_viewDialog._DEFAULT_FEATURES;
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
    	
		this._viewURL = f_env.ResolveContentUrl(viewURL);
		
		if (this._iframe && this.f_getStatus()==f_shell.OPENED_STATUS) { 
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
		
		var param = this._parameters;
		if (param){
			var ds="";
			var first=true;
			
			for(var key in param) {
				if (first) {
					first=false;
				} else {
					ds+=",";
				}
				
				ds+=encodeURIComponent(key)+"="+encodeURIComponent(param[key]);
			}
			
			ds="__rcfaces_utf8="+encodeURIComponent(ds);

			var pos=url.indexOf("?");
			if (pos<0) {
				url+="?"+ds;
			}  else {
				url+="&"+ds;
			}
		}
		
		var requestId="__rcfaces_var="+f_core.AllocateRequestKey();
		var pos=url.indexOf("?");
		if (pos<0) {
			url+="?"+requestId;
			
		}  else {
			url=url.substring(0, pos+1)+requestId+"&"+url.substring(pos+1);
		}
		
		return url;
	},
	
	f_fillBody: function(base) {
		var iframe=f_core.CreateElement(base, "iframe");
		this._iframe=iframe;
		
		iframe.className="f_viewDialog_frame";
		iframe.frameBorder=0;
		iframe.style.width=this.f_getWidth();
		iframe.style.height=this.f_getHeight();
		
		var self=this;
		if (f_core.IsInternetExplorer()) {
			f_core.Debug(f_viewDialog, "f_fillBody: IE use onreadystatechange ");
			iframe.onreadystatechange=function() {
				if (window._rcfacesExiting) {
					return false;
				}

				f_core.Debug(f_viewDialog, "f_fillBody: on ready state change: "+this+" state="+this.readyState);

				if (this.readyState != "interactive") {
					return;
				}	
				
				this.onreadystatechange=null;
				
				try {
					self.f_performFrameReady(this, this.contentWindow.document);

				} catch (x) {					
					f_core.Error(f_viewDialog, "f_fillBody: f_performFrameReady throws exception.", x);
				}
			};
			
		} else {
			f_core.Debug(f_viewDialog, "f_fillBody: Firefox use onload ");
			iframe.onload=function() {
				if (window._rcfacesExiting) {
					return false;
				}

				f_core.Debug(f_viewDialog, "f_fillBody: on ready state change: "+this+" state="+this.readyState);
	
				this.onload=null;
				
				try {
					self.f_performFrameReady(this, this.contentWindow.document);

				} catch (x) {					
					f_core.Error(f_viewDialog, "f_fillBody: f_performFrameReady throws exception.", x);
				}
			};
		}
		
		iframe.src=this.f_getIFrameUrl();
	},
	
	/**
	 * @method protected
	 * @param HtmlIFrameElement iframe
	 * @return void
	 */
	f_finalizeIframe: function(iframe) {
		iframe.onreadystatechange=null;
		iframe.onload=null;
		
		f_core.VerifyProperties(iframe);		
	},
	/**
	 * @method protected
	 * @param HtmlIFrameElement iframe
	 * @param Document doc
	 * @return void
	 */
	f_performFrameReady: function(iframe, doc) {
		f_core.Debug(f_frameShellDecorator, "f_performFrameReady: frame '"+iframe+"' is ready !");
	},
	
	/* Plus nécessaire par la redéfinition de  _OnExit 
	f_preDestruction: function() {
		if (window._RCFACES_LEVEL3) {
			var iframe=this._iframe;
			if (f_core.IsInternetExplorer() && iframe) {
				
				// Pour IE, probleme de synchronisme de fermeture !
				
				// Pas de super, car on passe en asynchrone !
				iframe.parentNode.removeChild(iframe);
		
				var self=this;
				var callee=arguments.callee;
				window.setTimeout(function() {
					self.f_super({callee: callee, length: 0 });
					
					self=null;
					callee=null;
				}, 10);
				
				return;
			}
		}
		this.f_super(arguments);
	},
	*/
	f_postDestruction: function() {
		this._iframe=undefined; // HtmlIFrame
		
		this.f_super(arguments);
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
	members: __members,
	statics: __statics
});
