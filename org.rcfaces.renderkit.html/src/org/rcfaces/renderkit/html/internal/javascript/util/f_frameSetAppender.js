/* 
 * $Id$
 */

/**
 * f_frameSetAppender
 *
 * @class hidden f_frameSetAppender extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics={
	/**
	 * @method public static
	 * @return void
	 */
	Initializer: function() {	

		var parentWindow=window.parent;
		for(;parentWindow.parent && parentWindow.parent!=parentWindow;) {
			parentWindow=parentWindow.parent;
		}
//		alert("ParentWindow="+parentWindow);
		if (!parentWindow || !parentWindow._rcfacesFrameSetAppend) {
			// On doit se debrouiller à trouver l'URL du stylesheet,
			// car le stylesheet est surement pas encore initialisé au niveau du f_env.
			// par contre il doit y avoir un <LINK rel="stylesheet"  avant !
			var uri=null;
			var links=document.getElementsByTagName("LINK");
			for(var i=0;i<links.length;i++) {
				var link=links[i];
				if (link.rel!="stylesheet") {
					continue;
				}
				var idx=link.href.indexOf("rcfaces.css");
				if (idx<0) {
					continue;
				}
				
				uri=link.href.substring(0, idx);
			}
			if (!uri) {
				return;
			}
			
			uri+="frameSetAppender/base.html?url="+encodeURI(window.document.location);
			
			if (!parentWindow) {
				parentWindow=window;
			}
			
			//alert("Set location: "+parentWindow+" "+window+" "+uri);
			window.document.location=uri;
			return;
		}
		var callback=parentWindow._rcfacesFrameSetAppend;

		callback.call(window, "newPage");

		var instance=this.f_newInstance(callback);
		
		f_frameSetAppender._callback=callback;
	}
}
var __members = {
	f_frameSetAppender: function(callback) {
		this.f_super(arguments);

		f_log.AddAppenders(this);
		
	},
	f_finalize: function() {
		this.f_super(arguments);
	},
	f_doAppend: function(event) {
		var callback=f_frameSetAppender._callback;
		if (!callback) {
			return;
		}
		
		try {
			callback.call(window, "console", event);
		} catch (x)  {
			alert(x);
			callback=null;
		}
	}
}

new f_class("f_frameSetAppender", {
	extend: f_object,
	statics: __statics,
	members: __members
});
