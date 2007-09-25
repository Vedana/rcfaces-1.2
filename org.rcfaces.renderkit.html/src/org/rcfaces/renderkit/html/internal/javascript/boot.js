/*
 * $Id$
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

var _rcfacesGetWindow=function(th, evt) {
	if (evt) {
		if (evt.view) {
			// Firefox
			return evt.view;
		}
		
		if (evt.target) {
			// Firefox
			return evt.target.ownerDocument.defaultView;
		}
		
		// IE		
		if (evt.fromElement) {
			return evt.fromElement.ownerDocument.parentWindow;		
		}
		
		if (evt.toElement) {
			return evt.toElement.ownerDocument.parentWindow;
		}
	}
	
	// IE !	
	
	if (th.nodeType==9) {
		// component=document
		return th.parentWindow;
	}
	
	if (th.ownerDocument) {
		return th.ownerDocument.parentWindow;
	}
	
	// Component est une window ?
	return th;
}

