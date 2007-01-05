/*
 * $Id$
 */
 
/*
 * class f_imageRadioButton
 *
 * @class f_imageRadioButton extends f_imageCheckButton, fa_groupName, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

/*
	f_finalize: function() {
		this._groupName=undefined; // string
			
		this.f_super(arguments);
	},
	*/
	/**
	 * @method protected
	 */
	_onSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled() || this.f_isSelected()) {
			return false;
		}
		
		this.f_setSelected(true);
		
		return false;
	},
	/**
	 * @method protected
	 */
	f_parseAttributes: function() {
    	var groupName=f_core.GetAttribute(this, "v:groupName");
    	
 		if (groupName) {
			this.f_setGroupName(groupName);
		}

		this.f_super(arguments);
	},
	/**
	 * Set selected state.
	 * 
	 * @method public
	 * @param boolean set
	 * @return void
	 */
	f_setSelected: function(set) {
		if (set!==false) {
			set=true;
		}
		
		if (set) {
			var selected=this.f_getSelectedInGroup();
			if (selected) {
				selected.f_setSelected(false);
			}		
		}

		this.f_super(arguments, set);
	},	
	/**
	 * Returns the group name of the button
	 * 
	 * @method public
	 * @return String
	 */
	f_getGroupName: function() {
		return this._groupName;
	},
	/**
	 * Set the group name of the button.
	 *
	 * @method hiden
	 * @param String group
	 * @return void
	 */
	f_setGroupName: function(group) {
		this.f_changeGroup(this._groupName, group, this);
		this._groupName = group;
		this.f_setProperty(f_prop.GROUPNAME, group);
	},
	/**
	 * Returns the selected button of the same group of this button.
	 *
	 * @method public
	 * @return f_imageRadioButton
	 */
	f_getSelectedInGroup: function() {
		function search(item) {
			return item.f_isSelected.call(item)?item:null;
		}

		return this.f_findIntoGroup(this.f_getGroupName(), search);
	},
	/**
	 * List all buttons of same group.
	 * 
	 * @method public
	 * @return f_imageRadioButton[]
	 */
	f_listAllInGroup: function() {
		return this.f_listGroup(this.f_getGroupName());
	},
	fa_updateRequired: function() {
	},
	fa_getRadioScope: fa_groupName.GlobalScope
}

var f_imageRadioButton=new f_class("f_imageRadioButton", null, null, __prototype, f_imageCheckButton, fa_groupName, fa_required);
