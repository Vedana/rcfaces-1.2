/*
 * $Id$
 */

/**
 * 
 *
 * @class public final f_error extends Object
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */

var __static = {


	/**
	 * @field public static final number
	 */
	ERROR_MIME_TYPE: "x-camelia/error",

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
		
		if (component.f_isActionListEmpty(f_event.ERROR)) {
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
			return;
		}

		return component.f_fireEvent(f_event.ERROR, null, param, messageCode, null, message);
	}
}

new f_class("f_error", null, __static);
