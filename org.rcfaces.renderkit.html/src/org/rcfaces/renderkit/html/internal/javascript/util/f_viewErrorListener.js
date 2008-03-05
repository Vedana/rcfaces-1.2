/*
 * $Id$
 */

/**
 *
 * @class hidden f_viewErrorListener extends f_object, fa_eventTarget
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field private static final
	 */
	_EVENTS: {
			error: f_event.ERROR,
			propertyChange: f_event.PROPERTY_CHANGE,
			user: f_event.USER
	}
}


var __members = {
	f_viewErrorListener: function() {
		this.f_super(arguments);
		
		var self=this;
		f_error.RegisterErrorListener(function(component, messageCode, message, param) {
			self.f_fireEvent(f_event.ERROR, null,  { 
				component: component, 
				message: message,
				param: param
			}, messageCode); 
		});
		
		if (this.nodeType) {
			var events=f_core.GetAttribute(this, "v:events");
			if (events) {
				this.f_initEventAtts(f_viewErrorListener._EVENTS, events);
			}
		}
	}
}

new f_class("f_viewErrorListener", {
	extend: f_object,
	aspects: [ fa_eventTarget ],
	members: __members,
	statics: __statics
});
