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

var __prototype = {
	fa_message: function() {
		var messageContext=f_messageContext.Get(this);
		
		this._className=f_core.GetAttribute(this, "v:styleClass");
		if (!this._className) {
			this._className=this.className;
		}
		
		messageContext.f_addMessageListener(this);
	},
	
	/*
	f_finalize: function() {
		// this._classSpecified=undefined; // boolean
		// this._className=undefined; // string
		// this._showSummary=undefined; // boolean
		// this._showDetail=undefined; // boolean
		
		// this._fatalStyleClass=undefined;  // string
		// this._errorStyleClass=undefined; // string
		// this._warnStyleClass=undefined; // string
		// this._infoStyleClass=undefined; // string
	},
	*/

	/**
	 * @method public 
	 * @return boolean
	 */
	f_isShowSummary: function() {
		if (this._showSummary===undefined) {
			var b=f_core.GetAttribute(this, "v:showSummary");			
			this._showSummary=(b)?true:false;			
		}
		
		return this._showSummary;
	},
	/* PAS DE MODIF
	 * @method public 
	 * @param boolean showSummary
	 * @return void
	 *
	f_setShowSummary: function(showSummary) {
		var old=this.f_isShowSummary();
		showSummary=(showSummary)?true:false;
		
		if (showSummary==old) {
			return;
		}
		
		this._showSummary=showSummary;
		
		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_setProperty(f_message.SHOW_SUMMARY, showSummary);
				
		this.fa_updateMessages();
	},
	*/
	/**
	 * @method public 
	 * @return boolean
	 */
	f_isShowDetail: function() {
		if (this._showDetail===undefined) {
			var b=f_core.GetAttribute(this, "v:showDetail");
			this._showDetail=(b)?true:false;
		}
		
		return this._showDetail;
	},
	/*  Pas de MODIF
	 * @method public 
	 * @param boolean showDetail
	 * @return void
	 *
	f_setShowDetail: function(showDetail) {
		f_core.Assert(typeof(showDetail)=="boolean", "Invalid showDetail parameter ('"+showDetail+"')");

		var old=this.f_isShowDetail();
		showDetail=(showDetail)?true:false;
		
		if (showDetail==old) {
			return;
		}
		
		this._showDetail=showDetail;
		
		if (!this.fa_componentUpdated) {
			return;
		}

		this.f_setProperty(f_message.SHOW_DETAIL, showDetail);
		
		this.fa_updateMessages();
	},
	*/
	/**
	 * @method public 
	 * @return String
	 */
	f_getFatalStyleClass: function() {
		if (this._fatalStyleClass===undefined) {
			this._fatalStyleClass=this._computeStyleClass("v:fatalStyleClass", "_fatal");
		}

		return this._fatalStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getErrorStyleClass: function() {
		if (this._errorStyleClass===undefined) {
			this._errorStyleClass=this._computeStyleClass("v:errorStyleClass", "_error");
		}

		return this._errorStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getWarnStyleClass: function() {
		if (this._warnStyleClass===undefined) {
			this._warnStyleClass=this._computeStyleClass("v:warnStyleClass", "_warn");
		}
		return this._warnStyleClass;
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getInfoStyleClass: function() {
		if (this._infoStyleClass===undefined) {
			this._infoStyleClass=this._computeStyleClass("v:infoStyleClass", "_info");
		}
		
		return this._infoStyleClass;
	},
	/**
	 * @method private
	 */
	_computeStyleClass: function(attributeName, suffix) {
		var style=f_core.GetAttribute(this, attributeName);
		if (style) {
			return style;
		}
	
		var classSpecified=this._classSpecified;
		
		if (classSpecified===undefined) {
			classSpecified=(f_core.GetAttribute(this, "v:fatalStyleClass") ||
				f_core.GetAttribute(this, "v:errorStyleClass") ||
				f_core.GetAttribute(this, "v:warnStyleClass") ||
				f_core.GetAttribute(this, "v:infoStyleClass"))?true:false;
			
			this._classSpecified=classSpecified;
		}
		
		if (classSpecified) {
			return null;
		}	
		
		return this._className+suffix;
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
	
	f_performMessageChanges: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateMessages: f_class.ABSTRACT
	
}

var fa_message=new f_aspect("fa_message", null, __prototype);
