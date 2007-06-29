/*
 * $Id$
 */

/**
 * Aspect Message
 *
 * @aspect fa_messageText extends fa_message
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	
	/*
	f_finalize: function() {
		// this._showSummary=undefined; // boolean
		// this._showDetail=undefined; // boolean
	},
	*/

	/**
	 * @method public 
	 * @return boolean
	 */
	f_isShowSummary: function() {
		if (this._showSummary===undefined) {
			this._showSummary=f_core.GetBooleanAttribute(this, "v:showSummary", false);
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
			this._showDetail=f_core.GetBooleanAttribute(this, "v:showDetail", false);
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
	 * @method protected abstract
	 * @return void
	 */
	fa_updateMessages: f_class.ABSTRACT
}

new f_aspect("fa_messageText", null, __members, fa_message);
