/*
 * $Id$
 */

/**
 * 
 *
 * @class public final f_error extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field public static final String
	 */
	APPLICATION_ERROR_MIME_TYPE: "x-camelia/error",

	/**
	 * @field hidden static final String
	 */
	ERROR_CODE_PROPERTY_NAME: "X-Camelia-Error-Code",

	/**
	 * @field public static final number
	 */
	ERROR_TYPE_MASK: 0xFF000000,

	/**
	 * @field public static final number
	 */
	HTTP_ERROR: 0x1000000,
		
	/**
	 * @field public static final number
	 */
	SERVICE_ERROR: 0x2000000,
	
	/**
	 * @field public static final number
	 */
    INVALID_PARAMETER_SERVICE_ERROR: 0x2000001,

	/**
	 * @field public static final number
	 */
    SESSION_EXPIRED_SERVICE_ERROR: 0x2000002,

	/**
	 * @field public static final number
	 */
    RESPONSE_TYPE_SERVICE_ERROR: 0x2000003,

	/**
	 * @field public static final number
	 */
    INVALID_RESPONSE_SERVICE_ERROR: 0x2000004,
	
	/**
	 * @field public static final number
	 */
	RESPONSE_EVALUATION_SERVICE_ERROR: 0x2000005,

	/**
	 * @field public static final number
	 */
	INVALID_SERVICE_RESPONSE_ERROR: 0x2000006,

	/**
	 * @field public static final number
	 */
	INTERNAL_SERVICE_RESPONSE_ERROR: 0x2000007,
		
	/**
	 * @field public static final number
	 */
	ASYNC_RENDER_ERROR: 0x3000000,

	/**
	 * @field public static final number
	 */
    RESPONSE_TYPE_ASYNC_RENDER_ERROR: 0x3000001,

	/**
	 * @field public static final number
	 */
    INVALID_RESPONSE_ASYNC_RENDER_ERROR: 0x3000002,
	
	/**
	 * @field public static final number
	 */
	RESPONSE_EVALUATION_ASYNC_RENDER_ERROR: 0x3000004,

	/**
	 * @field public static final number
	 */
	APPLICATION_ERROR: 0x0000000,

	/**
	 * @method hidden static
	 * @param f_component component
	 * @param number messageCode
	 * @param String message
	 * @param any param
	 * @return boolean
	 */
	PerformErrorEvent: function(component, messageCode, message, param) {

		if (f_core.IsDebugEnabled(f_error)) {
			var code=(messageCode)?("[0x"+messageCode.toString(16)+"]"):"";

			alert("ERROR: "+code+": "+message);
		}
		
		if (!component.f_isActionListEmpty(f_event.ERROR)) {
			return component.f_fireEvent(f_event.ERROR, null, param, messageCode, null, message);
		}
		
		var listeners=f_error._Listeners;
		if (listeners && listeners.length) {
			var ret=true;

			for(var i=0;i<listeners.length;i++) {
				var listener=listeners[i];
				
				try {
					if (listener.call(window, component, messageCode, message, param)===false) {
						ret=false;
					}
					
				} catch (x) {
					f_core.Error(f_error, "PerformErrorEvent: Exception when calling '"+listener+"' (component="+component+", messageCode='"+messageCode+"' message='"+message+"' param='"+param+"')", x);
				}
			}
			
			return ret;
		}
		
		// Pas d'évènements !
		
		var x=undefined;
		if (param instanceof Error) {
			x=param;
		}
	
		if (messageCode) {
			messageCode="[0x"+messageCode.toString(16)+"]";
			
		} else {
			messageCode="";
		}	
	
		f_core.Error(component.f_getClass(), "Error"+messageCode+"  "+message, x);
	},
	
	
	/**
	 * @method hidden static
	 * @param function errorListener
	 * @return void
	 */
	RegisterErrorListener: function(errorListener) {
		f_core.Assert(typeof(errorListener)=="function", "f_error.RegisterErrorListener: Invalid errorListener '"+errorListener+"'.");
		
		var listeners=f_error._Listeners;
		if (!listeners) {
			listeners=new Array;
			
			f_error._Listeners=listeners;			
		}
		
		listeners.f_addElement(errorListener);
	},
	
	/**
	 * @method hidden static
	 * @param function errorListener
	 * @return void
	 */
	UnregisterErrorListener: function(errorListener) {
		f_core.Assert(typeof(errorListener)=="function", "f_error.UnregisterErrorListener: Invalid errorListener '"+errorListener+"'.");
		
		var listeners=f_error._Listeners;
		if (!listeners) {
			return;
		}
		
		listeners.f_removeElement(errorListener);				
	},
	Finalizer: function() {
		f_error._Listeners=undefined;
	},
	/**
	 * @method hidden static
	 * @param f_httpRequest request
	 * @return number
	 */
	ComputeApplicationErrorCode: function(request) {
		var code=f_error.APPLICATION_ERROR;
		
		var pcode=request.f_getResponseHeader(f_error.ERROR_CODE_PROPERTY_NAME);
		if (pcode) {
			code |= parseInt(pcode) & ~f_error.ERROR_TYPE_MASK;
		}
		
		return code;
	}
}

new f_class("f_error", {
	statics: __statics,
	_systemClass: true
});
