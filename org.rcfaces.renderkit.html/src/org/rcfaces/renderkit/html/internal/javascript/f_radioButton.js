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
		
		var input=this._input;
		if (input.name) {
			this._addToGroup(input.name, this);
		}
	},
	/**
	 * @method public 
	 * @return string
	 */
	f_getGroupName: function() {
		var input=this._input;
		if (!input) {
			return null;
		}
		return input.name;
	},
	/**
	 * @method public 
	 * @param string group
	 * @return void
	 */
	f_setGroupName: function(group) {
		var input=this._input;
		if (!input || !group) {
			return;
		}
		
		this._changeGroup(this.f_getGroupName(), group, this);
		input.name = group;
	
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
		var input=this._input;
		if (!input) {
			return false;
		}
		
		return (input.checked==true);
	},
	/**
	 * @method public 
	 * @param boolean set
	 * @return void
	 */
	f_setSelected: function(set) {
		var input=this._input;
		
		if (!input || input.checked==set) {
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
				
		input.checked = true;
	},
	_a_updateRequired: function() {
	},
	_a_getRadioScope: fa_groupName.GlobalScope
}

var f_radioButton=new f_class("f_radioButton", null, null, __prototype, f_checkButton, fa_groupName, fa_required);
