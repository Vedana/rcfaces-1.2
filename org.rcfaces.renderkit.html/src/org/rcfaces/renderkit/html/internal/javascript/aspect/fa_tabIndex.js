
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
		// this._tabIndex=undefined;  // Number
	},
	*/
	/**
	 * Returns the current tabIndex.
	 *
	 * @method public
	 * @return Number TabIndex or 0 if not defined
	 */
	fa_getTabIndex: function() {
		if (this._tabIndex===undefined) {
		  	this._tabIndex=f_core.GetNumberAttributeNS(this, "tabIndex", undefined);
		  	if (this._tabIndex===undefined) {
		  		var elt = this.fa_getTabIndexElement();
		  		if (elt) {
			  		this._tabIndex=f_core.GetNumberAttribute(elt, "tabIndex", 0);
		  		}
		  	}
		}
		return this._tabIndex;
	},
	/**
	 * Set the tabIndex, and record in order to synchronise to the server.
	 *
	 * @method public
	 * @param Number New tabIndex
	 * @return void
	 */
	fa_setTabIndex: function(set) {
		f_core.Asset(typeof(set)=="number", "fa_tabIndex.fa_setTabIndex: Invalid set parameter type ("+set+")");
		
		if (set===this.fa_getTabIndex()) {
			return;
		}

		this.f_setProperty("tabIndex",set);
		this._tabIndex = set;

		var elt = this.fa_getTabIndexElement();
		if (elt) {
			elt.tabIndex = set;
		}
	},
	/**
	 * Get the element that supports the tabIndex attribute
	 * Default : the input
	 *
	 * @method protected
	 * @return HTMLElement
	 */
	fa_getTabIndexElement: function() {
		return this.f_getInput();
	},
	/**
	 * 
	 * @method protected
	 * @return HTMLElement
	 */
	f_getInput: f_class.ABSTRACT
};

new f_aspect("fa_tabIndex", {
	members: __members
});	
