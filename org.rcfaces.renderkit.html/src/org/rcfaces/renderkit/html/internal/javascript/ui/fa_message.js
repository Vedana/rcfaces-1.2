/*
 * $Id$
 */

/**
 * Aspect Message
 *
 * @aspect fa_message
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	fa_message: function() {

		var messageContext=f_messageContext.Get(this);
		
		var messages=messageContext.f_listMessages(this);
		
		messageContext.f_addMessageListener(this);
		
		f_core.Debug(fa_message, "fa_message: message detected for component id='"+this.id+"' = "+messages);
		if (messages.length) {
			this.f_performMessageChanges(messageContext);
		}
	},
	
	/**
	 * @method protected
	 * @return boolean
	 */
	f_hasSeverityClassName: function() {

		var classSpecified=this._classSpecified;
		
		if (classSpecified!==undefined) {
			return classSpecified;
		}
		
		this._fatalStyleClass=f_core.GetAttribute(this, "v:fatalStyleClass");
		this._errorStyleClass=f_core.GetAttribute(this, "v:errorStyleClass");
		this._warnStyleClass=f_core.GetAttribute(this, "v:warnStyleClass");
		this._infoStyleClass=f_core.GetAttribute(this, "v:infoStyleClass");
		
		classSpecified=this._fatalStyleClass || this._errorStyleClass || this._warnStyleClass || this._infoStyleClass;
		this._classSpecified=classSpecified;

		if (classSpecified) {
			return true;
		}
		
		var styleClass=this.f_getMainStyleClass();
		
		this._fatalStyleClass=styleClass+"_fatal";
		this._errorStyleClass=styleClass+"_error";
		this._warnStyleClass=styleClass+"_warn";
		this._infoStyleClass=styleClass+"_info";

		return false;
	},
	
	/*
	f_finalize: function() {
		// this._classSpecified=undefined; // boolean
		
		// this._fatalStyleClass=undefined;  // string
		// this._errorStyleClass=undefined; // string
		// this._warnStyleClass=undefined; // string
		// this._infoStyleClass=undefined; // string
	},
	*/

	/**
	 * @method public 
	 * @return String
	 */
	f_getFatalStyleClass: function() {
		this.f_hasSeverityClassName();

		return this._fatalStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getErrorStyleClass: function() {
		this.f_hasSeverityClassName();

		return this._errorStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getWarnStyleClass: function() {
		this.f_hasSeverityClassName();
		
		return this._warnStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getInfoStyleClass: function() {
		this.f_hasSeverityClassName();
		
		return this._infoStyleClass;
	},
	/**
	 * @method protected final
	 * @param number severity
	 * @return String style class name.
	 */
	f_getStyleClassFromSeverity: function(severity) {
		f_core.Assert(typeof(severity)=="number", "Invalid severity parameter ('"+severity+"')");
		
		var className=null;
		
		switch(severity) {
		case f_messageObject.SEVERITY_FATAL:
			className=this.f_getFatalStyleClass();
			if (className) {
				return className;
			}

		case f_messageObject.SEVERITY_ERROR:
			className=this.f_getErrorStyleClass();
			if (className) {
				return className;
			}

		case f_messageObject.SEVERITY_WARN:
			className=this.f_getWarnStyleClass();
			if (className) {
				return className;
			}

		case f_messageObject.SEVERITY_INFO:
			className=this.f_getInfoStyleClass();
			if (className) {
				return className;
			}
		}
		
		return null;
	},
	
	/**
	 * @method protected abstract
	 */
	f_performMessageChanges: f_class.ABSTRACT	
}

new f_aspect("fa_message", {
	members: __members
});
