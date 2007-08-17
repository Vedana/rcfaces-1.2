/*
 * $Id$
 */
 
/**
 * Aspect GroupName
 *
 * @aspect public abstract fa_groupName
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
	/**
	 * @method hidden static
	 */
	GlobalScope: function() {
		return fa_groupName;
	},
	
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		fa_groupName._Groups=undefined;
	}
}
var __members = {
	f_finalize: function() {
		var scope=this.fa_getRadioScope();
		if (scope) {
			scope._Groups=undefined; // Map<string,List<any>>
		}
	},
	/**
	 * @method protected
	 * @return any
	 */
	f_delFromGroup: function(groupName, obj) {
		var g = this.f_listGroup(groupName);
		if (!g) {
			return null;
		}
		return g.f_removeElement(obj);
	},
	/**
	 * @method protected
	 * @param String groupName
	 * @param any obj
	 * @return void
	 */
	f_addToGroup: function(groupName, obj) {
		var g = this.f_listGroup(groupName, true);
		g.f_addElement(obj);
	},
	/**
	 * @method protected
	 */
	f_changeGroup: function(groupName, newGroupName, obj) {
		if (groupName) {
			this.f_delFromGroup(groupName, obj);
		}
		if (newGroupName) {
			this.f_addToGroup(newGroupName, obj);
		}
	},
	/**
	 * @method protected
	 */
	f_findIntoGroup: function(groupName, fct) {
		var g = this.f_listGroup(groupName);
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
	/**
	 * @method protected
	 */
	f_listGroup: function(groupName, create) {
		f_core.Assert(groupName, "You must specify a groupName !");
	
		var scope=this.fa_getRadioScope();
		if (!scope) {
			scope=fa_groupName;
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

new f_aspect("fa_groupName", {
	statics: __statics,
	members: __members
});
