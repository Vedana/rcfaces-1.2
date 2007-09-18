/*
 * $Id$
 */
 
/**
 * Divers Add'ons afin de pouvoir gérer le multiWindow.
 * 
 * @class f_classLoader
 */

function f_multiWindowClassLoader(win) {
	f_classLoader.call(this, win); // super(win)
	
	this._kclass=f_multiWindowClassLoader;	
	this._parent=window._rcfacesClassLoader;
	
	this._installCoreClasses();
	this.f_requiresBundle(this._parent._mainBundleName);
}

f_multiWindowClassLoader.prototype=new f_classLoader();

/**
 *
 * @method hidden
 * @param Window childWindow
 * @return f_classLoader
 */ 
f_classLoader.prototype.f_newWindowClassLoader=function(childWindow) {
	return new f_multiWindowClassLoader(childWindow);
}

/**
 * @method hidden
 * @param String... bundleNames
 * @return void
 */
f_multiWindowClassLoader.prototype.f_requiresBundle=function(bundleNames) {

	var parent=this._parent;

	for(var i=0;i<arguments.length;i++) {
		var bundleName=arguments[i];
		
		var parentBundle=parent._bundles[bundleName];
		if (!parentBundle) {
			alert("Unknown parent bundle ! ("+bundleName+")");
			continue;
		}
		
		var classes=parentBundle.f_listClasses();
		
		var newClasses=new Array;
		for(var j=0;j<classes.length;j++) {
			newClasses=this._cloneClass(classes[j]);
		}
		
		var bundle=new f_bundle(this._window, bundleName, newClasses);		
	}
}

f_multiWindowClassLoader.prototype._cloneClass=function(clazz) {

	switch(clazz._name) {
	case "Array":
		var newArray=this._window.Array.prototype;
		f_classLoader._CopyProperties(newArray.prototype, this._parent._window.Array,
			"f_removeElement",
			"f_removeElements",
			"f_addElement",
			"f_addElements",
			"f_contains");

		f_classLoader._CopyProperties(newArray, this._parent._window.Array, "f_getName");
		return newArray;
		
	case "f_core":
		var newCore=new Object();
		f_classLoader._CopyProperties(newCore, this._parent._window.f_core);
		return newCore;
	}

	if (clazz instanceof f_aspect) {
		var parentAspects=clazz._parents;
		
		var aspects=new Array;
		for(var i=0;i<parentAspects;i++) {
			aspects.push(this.f_getAspect(parentAspects[i]._name));
		}
	
		var atts= {
			members: clazz._members,
			statics: clazz._staticMembers,
			extend: aspects,
			_classLoader: this
		}
		
		return new f_aspect(clazz._name, atts);			
	}

	var parentAspects=clazz._aspects;
	
	var aspects=new Array;
	for(var i=0;i<parentAspects;i++) {
		aspects.push(this.f_getAspect(parentAspects[i]._name));
	}
	
	var parent;
	var parentParent=clazz._parent;
	if (parentParent) {
		parent=this.f_getClass(parentParent._name, parentParent._lookId);
	}
	
	var atts= {
		members: clazz._members,
		statics: clazz._staticMembers,
		extend: parent,
		aspects: aspects,
		_systemClass: clazz._systemClass,
		_classLoader: this
	}
	
	var newClass=new f_class(clazz._name, clazz._lookId, atts);
	newClass._kmethods=clazz._kmethods;
	newClass._initialized=true;
	
	return newClass;
}

