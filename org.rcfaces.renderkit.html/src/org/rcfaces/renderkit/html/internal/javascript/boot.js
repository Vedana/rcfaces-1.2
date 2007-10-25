/*
 * $Id$
 */

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$ 
 */
 
if (window.f_core) {
	var m="PANIC: Vedana Faces Library is already loaded !";
	alert(m);
	throw new Error(m);
} 

// For profiling ....
if (!window._rcfacesInitLibraryDate) {
	window._rcfacesInitLibraryDate=new Date();
}

var __SYMBOL=function(x) { return x };

if (window._RCFACES_LEVEL3) {
	var _rcfacesGW=function(thiz, evt) {
		var win;
		if (evt) {
			if (evt.view) { // Firefox
				win=evt.view;

			} else if (evt.target) { // Firefox
				if (evt.target.nodeType==9) {					
					win=evt.target.defaultView;
					
				} else {
					win=evt.target.ownerDocument.defaultView;
				}

			} else if (evt.fromElement) { // IE
				win=evt.fromElement.ownerDocument.parentWindow;		

			} else if (evt.toElement) { // IE
				win=evt.toElement.ownerDocument.parentWindow;
			}
		}
		
		if (!win) {
			if (thiz.parentWindow) {
				// IE !
				win=thiz.parentWindow;
				
			} else if (thiz.defaultView) {
				win=thiz.defaultView;
				
			} else if (thiz.ownerDocument) {
				win=thiz.ownerDocument.parentWindow;
	
			} else if (thiz.frames) {
				win=thiz;			
			}
			
			if (!win) {
				throw new Error("RCFaces: Unknown this object type !");
			}
		}
		
		for(var w=win;w;w=w.opener) {
			try {
				if (w._rcfacesClassLoader) {
					return w;
				}
			} catch(x) {
			}			
		}
		
		for(var w=win.top;w;w=w.top) {
			try {
				if (w._rcfacesClassLoader) {
					return w;
				}
			} catch(x) {
			}			
			if (w.top==w) {
				break;
			}
		}
		
		throw new Error("RCFaces: Can not identify the rcfaces window !");
	}
}	
