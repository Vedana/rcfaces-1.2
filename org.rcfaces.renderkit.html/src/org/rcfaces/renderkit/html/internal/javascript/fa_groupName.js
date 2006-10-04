/*
 * $Id$
 */
 
/**
 * Aspect GroupName
 *
 * @aspect hidden fa_groupName
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @method hidden static
	 */
	GlobalScope: function() {
		return window;
	},
	
	Finalizer: function() {
		window._Groups=undefined;
	}
}
var __prototype = {
	f_finalize: function() {
		var scope=this.fa_getRadioScope();
		if (scope) {
			scope._Groups=undefined; // Map<string,List<any>>
		}
	},
	_delFromGroup: function(groupName, obj) {
		var g = this._listGroup(groupName);
		if (!g) {
			return null;
		}
		g.f_removeElement(obj);
	},
	_addToGroup: function(groupName, obj) {
		var g = this._listGroup(groupName, true);
		g.f_addElement(obj);
	},
	_changeGroup: function(groupName, newGroupName, obj) {
		if (groupName) {
			this._delFromGroup(groupName, obj);
		}
		if (newGroupName) {
			this._addToGroup(newGroupName, obj);
		}
	},
	_findIntoGroup: function(groupName, fct) {
		var g = this._listGroup(groupName);
		if (!g) {
			return null;
		}
		
		for(var i=0;i<g.length;i++) {
			var ret=fct.call(this, g[i]);
			
			if (ret) {
				return ret;
			}
		}
		return false;
	},
	_listGroup: function(groupName, create) {
		f_core.Assert(groupName, "You must specify a groupName !");
	
		var scope=this.fa_getRadioScope();
		if (!scope) {
			scope=window;
		}
		
		var wg=scope._Groups;
		if (!wg) {
			if (!create) {
				return null;
			}

			wg=new Object;
			scope._Groups=wg;
		}
		
		var groupKey=this.f_getClass().f_getName();
		var groups=wg[groupKey];
		if (!groups) {
			if (!create) {
				return null;
			}
			groups=new Object;
			wg[groupKey]=groups;
		}
		
		var group=groups[groupName];
		if (!create) {
			return group;
		}
		
		if (!group) {
			group=new Array;
			groups[groupName]=group;
		}

		return group;
	},
	
	/**
	 * @method protected abstract
	 */
	fa_getRadioScope: f_class.ABSTRACT
}
var fa_groupName=new f_aspect("fa_groupName", __static, __prototype);
