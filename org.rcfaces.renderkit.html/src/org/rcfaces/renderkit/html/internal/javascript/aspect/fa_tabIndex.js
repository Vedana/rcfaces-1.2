
/**
 * TabIndex value.
 *
 * @aspect abstract fa_tabIndex
 * @author jb.meslin
 * @version $Revision$ $Date$
 */
var __members = {
/*
	f_finalize: function() {
		// this._tabIndex=undefined;  // boolean
	},
	*/
	/**
	 * Returns the current tabIndex.
	 *
	 * @method public
	 * @return Number .
	 */
	fa_getTabIndex: function() {
		if (this._tabIndex===undefined) {
		  	this._tabIndex=f_core.GetNumberAttribute(this, "v:tabIndex", undefined);
		  	if (this._tabIndex===undefined) {
		  		var input = this.f_getInput();
		  		if (input) {
			  		this._tabIndex=f_core.GetNumberAttribute(input, "tabIndex", 0);
		  		}
		  	}
		}
		return this._tabIndex;
	},
	/**
	 * .
	 *
	 * @method public
	 * @param Number
	 * @return void
	 */
	fa_setTabIndex: function(set) {		
		var input = this.f_getInput();
		if (input) {
			this.f_setProperty("tabIndex",set);
			this._tabIndex = set;
			this.f_getInput().tabIndex = set;
		}
	},
	
	/**
	 * 
	 * @method protected final
	 * @return HTMLElement
	 */
	f_getInput: f_class.ABSTRACT
}

new f_aspect("fa_tabIndex", {
	members: __members
});	
