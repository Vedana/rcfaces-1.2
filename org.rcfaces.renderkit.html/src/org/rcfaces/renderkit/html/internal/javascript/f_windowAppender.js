/* 
 * $Id$
 */

/**
 * f_windowAppender
 *
 * @class hidden f_windowAppender extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static={
	/**
	 * @method hidden static
	 * @return void
	 */
	Initializer: function() {	

		var parentWindow=window.parent;
		for(;parentWindow.parent && parentWindow.parent!=parentWindow;) {
			parentWindow=parentWindow.parent;
		}
		if (!parentWindow || !parentWindow._Camelia_windowAppend) {
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
			
			uri+="frameSetAppender/window.html";
			
			if (!parentWindow) {
				parentWindow=window;
			}
			
			window.open(uri, "CAMELIA_WINDOW_LOG", "toolbar=no,scrollbars=yes,location=no,toolbar=no,directories=no,status=no,menubar=non,copyhistory=no");
			return;
		}
		var callback=parentWindow._Camelia_windowAppend;

		callback.call(window, "newPage");

		var instance=this.f_newInstance(callback);
		
		f_windowAppender._callback=callback;
	}
}
var __prototype = {
	f_windowAppender: function(callback) {
		this.f_super(arguments);

		f_log.AddAppenders(this);
		
	},
	f_finalize: function() {
		this.f_super(arguments);
	},
	f_doAppend: function(event) {
		var callback=f_windowAppender._callback;
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

var f_windowAppender=new f_class("f_windowAppender", null, __static, __prototype);
