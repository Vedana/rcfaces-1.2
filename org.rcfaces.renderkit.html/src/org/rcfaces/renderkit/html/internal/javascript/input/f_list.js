/*
 * $Id$
 */

/**
 * 
 * @class f_list extends f_combo
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {

	/**
	 * Return an array of values or indexes or an empty array.
	 * 
	 * @method public
	 * @param hidden boolean byIndex Returns index 
	 * @return Object[]
	 */
	f_getValue: function(byIndex) {
		var items = this.options;
		var sel = new Array;
		for (var i=0; i<items.length; i++) {
			var item = items[i];
			if (!item.selected) {
				continue;
			}
			
			sel.push(byIndex?i:item.value);
		}
		return sel;
	},
	/**
	 * 
	 * @method public
	 * @param Object[] val Values to select.
	 * @param hidden boolean byIndex <code>True</code> if the array of values is an array of indexes.
	 * @return void
	 */
	f_setValue: function(val, byIndex) {
		if (!val.length) {
			return;
		}
		var items = this.options;
		if (!items.length) {
			return;
		}
		if (byIndex) {
			for (var i=0; i<val.length; i++) {
				var idx = val[i];
				if (idx<0 || idx>=items.length) {
					continue;
				}
				items[idx].selected = true;
			}
			return;
		}
		// Build a value based array
		var hash = new Array;
		for (var i=0; i<items.length; i++) {
			var o = items[i];
			hash[o.value] = o;
		}
		// Select options from given array of values
		for (var i=0; i<val.length; i++) {
			var prop = val[i];
			if (hash[prop]) {
				hash[prop].selected = true;
			}
		}
	},
	/**
	 * @method public
	 * @return number[] Returns indexes of the selected values.
	 */
	f_getSelectedIndexes: function() {
		return this.f_getValue(true);
	},
	/**
	 * @method public
	 * @param number[] val Indexes of values to select.
	 * @return void
	 */
	f_setSelectedIndexes: function(val) {
		this.f_setValue(val,true);
	},
	/**
	 * @method public
	 * @return boolean
	 */
	f_isMultiple: function() {
		return (this.multiple == true);
	},
	/**
	 * @method public
	 * @param boolean set
	 * @return void
	 */
	f_setMultiple: function(set) {
		if (set == this.multiple) {
			 return;
		}
		this.multiple = set;
		this.f_setProperty(f_prop.MULTIPLE,set);
	}
}

new f_class("f_list", {
	extend: f_combo,
	members: __members
});