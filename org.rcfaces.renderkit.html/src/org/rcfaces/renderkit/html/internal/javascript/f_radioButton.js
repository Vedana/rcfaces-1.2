/*
 * $Id$
 */

/** 
 * class f_radioButton
 *
 * @class f_radioButton extends f_checkButton, fa_groupName, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

	f_radioButton: function() {
		this.f_super(arguments);
		
		if (this._input && this._input.name) {
			this._addToGroup(this._input.name, this);
		}
	},
	/**
	 * @method public 
	 * @return string
	 */
	f_getGroupName: function() {
		if (!this._input) {
			return null;
		}
		return this._input.name;
	},
	/**
	 * @method public 
	 * @param string group
	 * @return void
	 */
	f_setGroupName: function(group) {
		if (!this._input || !group) {
			return;
		}
		
		this._changeGroup(this.f_getGroupName(), group, this);
		this._input.name = group;
	
		this.f_setProperty(f_prop.GROUPNAME,group);
	},
	/**
	 * @method public 
	 * @return f_radioButton
	 */
	f_getSelectedInGroup: function() {
		function search(item) {
			return item.f_isSelected()?item:false;
		}

		return this._findIntoGroup(this.f_getGroupName(), search);
	},
	/**
	 * @method public 
	 * @return f_radioButton[]
	 */
	f_listAllInGroup: function() {
		return this._listGroup(this.f_getGroupName());
	},
	/**
	 * @method public 
	 * @return boolean
	 */
	f_isSelected: function() {
		if (this._input) {
			return (this._input.checked==true);
		}
		return false;
	},
	/**
	 * @method public 
	 * @param boolean set
	 * @return void
	 */
	f_setSelected: function(set) {
		if (!this._input || this._input.checked==set) {
			return;
		}
		
		if (!set) {
			return;
		}

		if (f_core.IsGecko()) {
			// Pour Gecko, il faut le faire à la main !
			function unselect(item) {
				if (item._input && item._input.checked) {
					item._input.checked=false;
				}
			}
	
			this._findIntoGroup(this.f_getGroupName(), unselect);
		}
				
		this._input.checked = true;
	},
	_a_updateRequired: function() {
	},
	_a_getRadioScope: fa_groupName.GlobalScope
}

var f_radioButton=new f_class("f_radioButton", null, null, __prototype, f_checkButton, fa_groupName, fa_required);
