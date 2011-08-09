/*
 * $Id$
 */

/**
 * class f_imageCriteriaButton
 *
 * @class f_imageCriteriaButton extends f_imagePagerButton
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	/**
	 * @method protected
	 * 
	 * @param type
	 * @param pagedComponent
	 * @returns {Boolean}
	 */
	_isDisabled: function(type, pagedComponent) {

		var selectedCriteria = pagedComponent.fa_getSelectedCriteria();
		
		f_core.Debug(f_imageCriteriaButton, "_disabled: Update image: id="+this.id+" type="+type);
		
		if (!pagedComponent || !type || !selectedCriteria) {
			return true;
		}
		
		type=type.toLowerCase();

		switch(type) {
		case "clear":
			return selectedCriteria.length==0;
		}
		
		return true;
	},
	/**
	 * 
	 * @method protected
	 * @param pagedComponent
	 * @param type
	 */
	_processSelection: function(pagedComponent, type) {
		switch(type) {
		case "clear":
			return pagedComponent.fa_setSelectedCriteria([]);
		}		
	}
};

new f_class("f_imageCriteriaButton", {
	extend: f_imagePagerButton,
	members: __members
});
