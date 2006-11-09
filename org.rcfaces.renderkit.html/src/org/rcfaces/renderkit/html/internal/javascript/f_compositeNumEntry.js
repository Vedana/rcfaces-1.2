/*
 * $Id$
 */

/**
 * 
 * @class public f_compositeNumEntry extends f_component, fa_compositeNumEntry, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype={

	f_compositeNumEntry: function() {
		this.f_super(arguments);
		
		var validatorParams=f_core.GetAttribute(this, "v:clientValidatorParams");
		if (validatorParams) {
			this._validatorParams=f_core.ParseParameters(validatorParams);
		}
		
		if (window.f_messageContext) {
			f_core.AddCheckListener(this, this);	
		}
	},
	/*
	f_finalize: function() {

		// this._validatorParams=undefined; // Map<String, String>
					
		this.f_super(arguments);
	},
	*/
	f_performCheckValue: function() {		
	},

	/**
	 * @method protected
	 * @return void
	 */
	f_addErrorMessage: function(clazz, errorMessage) {
		var summary=null;
		var detail=null;
		var severity=f_messageObject.SEVERITY_ERROR;
		
		var validatorParams=this._validatorParams;
		if (validatorParams) {
			summary=validatorParams[errorMessage+".summary"];
			if (summary) {
				detail=validatorParams[errorMessage+".detail"];
			} else {
				summary=validatorParams[errorMessage];
			}			
		}
		
		if (!summary) {
			var resourceBundle=f_resourceBundle.Get(clazz);
			
			summary=resourceBundle.f_get(errorMessage.toUpperCase().replace(/\./g, "_")+"_SUMMARY");
		}

		var message=new f_messageObject(severity, summary, detail);
		messageContext.f_addMessageObject(this, message);
	}
}
 
var f_compositeNumEntry=new f_class("f_compositeNumEntry", null, null, __prototype, f_component, fa_compositeNumEntry, fa_required);
