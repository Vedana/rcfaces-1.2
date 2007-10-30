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
	f_deleteFromGroup: function(groupName, obj) {
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
			this.f_deleteFromGroup(groupName, obj);
		}
		if (newGroupName) {
			this.f_addToGroup(newGroupName, obj);
		}
	},
	/**
	 * @method protected
	 * @param String groupName
	 * @param function fct
	 * @return any
	 */
	f_findIntoGroup: function(groupName, fct) {
		if (!groupName) {
			return null;
		} 
	
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
	 * @param String groupName
	 * @param boolean create
	 * @return Array
	 */
	f_listGroup: function(groupName, create) {
		f_core.Assert(typeof(groupName)=="string", "fa_groupName.f_listGroup: Invalid groupName parameter ("+groupName+") !");
	
		var scope=this.fa_getRadioScope();
		if (!scope) {
			scope=fa_groupName;
		}
		
		var wg=scope._Groups;
		if (!wg) {
			wg=new Object;
			scope._Groups=wg;
		}
		
		var groupKey=this.f_getClass().f_getName();
		var groups=wg[groupKey];
		if (!groups) {
			groups=new Object;
			wg[groupKey]=groups;
		}
		
		var group=groups[groupName];		
		if (group) {
			return group;
		}
		
		group=new Array;
		groups[groupName]=group;

		if (!this.fa_isRadioElementName()) {
			// Les composants ont été enregistré à la construction
			return group;
		}

		f_core.Debug(fa_groupName, "f_listGroup: Search elements by name '"+groupName+"' ...");

		var elements=document.getElementsByName(groupName);
		for(var i=0;i<elements.length;i++) {
			var element=elements[i];
			
			var elementId=element.id;

			var inputSuffixPos=elementId.indexOf(f_checkButton._INPUT_ID_SUFFIX);
			if (inputSuffixPos>0) {
				elementId=elementId.substring(0, inputSuffixPos);
			}
			
			element=f_core.GetElementByClientId(elementId);

			f_core.Debug(fa_groupName, "f_listGroup: Found element id='"+element.id+"' mainId='"+elementId+"' element='"+element+"'.");

			if (!element) {
				continue;
			}
			
			group.push(element);			
		}

		return group;
	},
	
	/**
	 * @method protected abstract
	 * @return String
	 */
	fa_getRadioScope: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return boolean
	 */
	fa_isRadioElementName: f_class.ABSTRACT
}

new f_aspect("fa_groupName", {
	statics: __statics,
	members: __members
});
