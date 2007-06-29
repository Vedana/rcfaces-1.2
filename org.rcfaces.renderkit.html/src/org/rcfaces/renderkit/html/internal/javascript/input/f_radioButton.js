/*
 * $Id$
 */

/** 
 * Class f_radioButton
 *
 * @class public f_radioButton extends f_checkButton, fa_groupName, fa_required
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {

	f_radioButton: function() {
		this.f_super(arguments);
		
		var input=this._input;
		
		var name=input.name;
		if (name) {
			this.f_addToGroup(name, this);
		}
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getGroupName: function() {
		var input=this._input;
		if (!input) {
			return null;
		}
		return input.name;
	},
	/*
	 * @method public 
	 * @param String group
	 * @return void
	 *
	f_setGroupName: function(group) {
		var input=this._input;
		if (!input || !group) {
			return;
		}
		
		this.f_changeGroup(this.f_getGroupName(), group, this);
		input.name = group;
	
		this.f_setProperty(f_prop.GROUPNAME,group);
	},
	*/
	
	/**
	 * @method public 
	 * @return f_radioButton
	 */
	f_getSelectedInGroup: function() {
		function search(item) {
			return item.f_isSelected()?item:false;
		}

		return this.f_findIntoGroup(this.f_getGroupName(), search);
	},
	/**
	 * Returns the value of the selected button of the group.
	 * 
	 * @method public
	 * @return String
	 */
	f_getValue: function() {
		var button=this.f_getSelectedInGroup();
		if (!button) {
			return;
		}
		if (!button.f_getRadioValue) {
			return null;
		}
		return button.f_getRadioValue();
	},
	/**
	 * Select the radio button by its RadioValue.
	 * @method public
	 * @param String value radioValue of the button 
	 * @return boolean <code>true</code> if the button has been found.
	 */
	f_setValue: function(value) {
		function search(item) {
			if (!item.f_getRadioValue) {
				return false;
			}
			
			if (value!=item.f_getRadioValue()) {
				return false;
			}
			
			item.f_setSelected(true);
			return true;
		}

		return this.f_findIntoGroup(this.f_getGroupName(), search);		
	},
	/**
	 * @method public 
	 * @return f_radioButton[]
	 */
	f_listAllInGroup: function() {
		return this.f_listGroup(this.f_getGroupName());
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
	
			this.f_findIntoGroup(this.f_getGroupName(), unselect);
		}
				
		input.checked = true;
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getRadioValue: function() {
		var input=this._input;
		
		if (!input) {
			return;
		}
		
		return input.name;
	},
	fa_updateRequired: function() {
	},
	fa_getRadioScope: fa_groupName.GlobalScope
}

new f_class("f_radioButton", {
	extend: f_checkButton,
	aspects: [ fa_groupName, fa_required],
	members: __members
});
