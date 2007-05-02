/*
 * $Id$
 */

/**
 * <p><strong>f_messageObject</strong> represents a single validation (or
 * other) message, which is typically associated with a particular
 * component in the view.
 *
 * @class public final f_messageObject extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * <p>Message severity level indicating an informational message
     * rather than an error.</p>
     *
	 * @field public static final number
	 */
   	SEVERITY_INFO:  0,

 	/**
 	 * <p>Message severity level indicating that an error might have
     * occurred.</p>
     *
	 * @field public static final number
	 */
    SEVERITY_WARN:  1,

	/**
	 * <p>Message severity level indicating that an error has
     * occurred.</p>
     *
	 * @field public static final number
	 */
    SEVERITY_ERROR: 2,

	/**
	 * <p>Message severity level indicating that a serious error has
     * occurred.</p>
     *
	 * @field public static final number
	 */
    SEVERITY_FATAL: 3,
    
    /**
     * @method hidden static
     */
    CreateFromTag: function(tag) {
    	f_core.Assert(tag && tag.nodeType==f_core.ELEMENT_NODE, "f_messageObject.CreateFromTag: Invalid tag parameter '"+tag+"'.");
    	
		var detail=f_core.GetAttribute(tag, "v:detail");
		var summary=f_core.GetAttribute(tag, "v:summary");
		
		var severity=f_core.GetAttribute(tag, "v:severity");	
		if (typeof(severity)=="string") {			
			severity=parseInt(severity);
		}
		
		var component=null;
		
		var forClientId=f_core.GetAttribute(tag, "v:for");			
		if (forClientId) {
			component=f_core.GetElementById(forClientId);
		}
		
		var messageObject = new f_messageObject(severity, summary, detail);
		
		f_messageContext.Get(component).f_addMessageObject(component, messageObject, true);
    }
}

var __prototype = {
	/**
	 * <p>Construct a new <code>f_messageObject</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param optional number severity the severity
	 * @param optional String summary Localized summary message text
	 * @param optional String detail Localized detail message text
	 */
	f_messageObject: function(severity, summary, detail) {
	//	f_core.Assert(typeof(severity)=="number", "Bad type of severity");
	//	f_core.Assert(summary, "Bad summary"); // Summary can be null
	
		if (typeof(severity)!="number") {
			severity=f_messageObject.SEVERITY_INFO;
		}
	
		this._severity=severity;
		this._summary=summary;
		this._detail=detail;
	},

/*
	f_finalize: function() {
		this._severity=undefined; // number
		this._summary=undefined; // string
		this._detail=undefined; // string
	},
*/
	
	/**
	 *  <p>Return the severity level.</p>
	 *
	 * @method public 
	 * @return number Severity level
	 * @see #SEVERITY_INFO
	 * @see #SEVERITY_WARN
	 * @see #SEVERITY_ERROR
	 * @see #SEVERITY_FATAL
	 */
	f_getSeverity: function() {
		return this._severity;
	},
	
	/**
	 * <p>Return the localized summary text.</p>
	 *
	 * @method public 
	 * @return String The summary
	 */
	f_getSummary: function() {
		return this._summary;
	},
	
	/**
	 * <p>Return the localized detail text.  If no localized detail text has
     * been defined for this message, return the localized summary text
     * instead.</p>
     *
	 * @method public 
	 * @return String Detail
	 */
	f_getDetail: function() {
		var detail=this._detail;
		if (detail) {
			return detail;
		}
		
		return this._summary;
	},
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_messageObject severity='"+this._severity+"' summary='"+this._summary+"' detail='"+this._detail+"']";
	}
}

new f_class("f_messageObject", null, __static, __prototype);
