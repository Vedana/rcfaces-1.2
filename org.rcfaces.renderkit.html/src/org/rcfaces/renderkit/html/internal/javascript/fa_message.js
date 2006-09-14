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
		
		messageContext.f_addMessageListener(this);
	},
	
	/*
	f_finalize: function() {		
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
		
		if (!this._componentUpdated) {
			return;
		}

		this.f_setProperty(f_message.SHOW_SUMMARY, showSummary);
				
		this._a_updateMessages();
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
		var old=this.f_isShowDetail();
		showDetail=(showDetail)?true:false;
		
		if (showDetail==old) {
			return;
		}
		
		this._showDetail=showDetail;
		
		if (!this._componentUpdated) {
			return;
		}

		this.f_setProperty(f_message.SHOW_DETAIL, showDetail);
		
		this._a_updateMessages();
	},
	*/
	/**
	 * @method public 
	 * @return string
	 */
	f_getFatalStyleClass: function() {
		if (this._fatalStyleClass===undefined) {
			var style=f_core.GetAttribute(this, "v:fatalStyleClass");
			this._fatalStyleClass=(style)?style:null;
		}
		return this._fatalStyleClass;
	},
	/**
	 * @method public 
	 * @return string
	 */
	f_getErrorStyleClass: function() {
		if (this._errorStyleClass===undefined) {
			var style=f_core.GetAttribute(this, "v:errorStyleClass");
			this._errorStyleClass=(style)?style:null;
		}
		return this._errorStyleClass;
	},
	/**
	 * @method public 
	 * @return string
	 */
	f_getWarnStyleClass: function() {
		if (this._warnStyleClass===undefined) {
			var style=f_core.GetAttribute(this, "v:warnStyleClass");
			this._warnStyleClass=(style)?style:null;
		}
		return this._warnStyleClass;
	},
	/**
	 * @method public 
	 * @return string
	 */
	f_getInfoStyleClass: function() {
		if (this._infoStyleClass===undefined) {
			var style=f_core.GetAttribute(this, "v:infoStyleClass");
			this._infoStyleClass=(style)?style:null;
		}
		return this._infoStyleClass;
	},
	
	/**
	 * @method protected final
	 * @param string severity
	 * @return string style class name.
	 */
	f_getStyleClassFromSeverity: function(severity) {
		switch(severity) {
		case f_messageObject.SEVERITY_INFO:
			return this.f_getInfoStyleClass();

		case f_messageObject.SEVERITY_WARN:
			return this.f_getWarnStyleClass();

		case f_messageObject.SEVERITY_ERROR:
			return this.f_getErrorStyleClass();

		case f_messageObject.SEVERITY_FATAL:
			return this.f_getFatalStyleClass();
		}
		
		return null;
	},
	
	f_performMessageChanges: f_class.ABSTRACT,
	
	/**
	 * @method abstract
	 * @return void
	 */
	_a_updateMessages: f_class.ABSTRACT
	
}

var fa_message=new f_aspect("fa_message", null, __prototype);
