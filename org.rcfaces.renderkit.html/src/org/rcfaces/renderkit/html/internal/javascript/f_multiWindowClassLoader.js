/*
 * $Id$
 */
 
/**
 * Divers Add'ons afin de pouvoir gérer le multiWindow.
 * 
 * @class f_multiWindowClassLoader
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

function f_multiWindowClassLoader(win) {
	f_classLoader.call(this, win); // super(win)
	
	this._kclass=f_multiWindowClassLoader;	
	this._parent=window._rcfacesClassLoader;
	this._location=win.location.toString();
	
	win._rcfacesClassLoader=this;
	
	//this._installCoreClasses();
	this.f_requiresBundle(this._parent._mainBundleName);
}

f_multiWindowClassLoader.prototype=new f_classLoader();
	
f_multiWindowClassLoader.f_getName=function() {
	return "f_multiWindowClassLoader";
}

if (window._RCFACES_LEVEL3) {
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
		f_core.Profile(false, "f_core.multiWindow.f_requiresBundles");
		
		var cnt=0;
		try {
		
			var parent=this._parent;
		
			for(var i=0;i<arguments.length;i++) {
				var bundleName=arguments[i];
				
				var parentBundle=parent._bundles[bundleName];
				if (!parentBundle) {
					f_core.Debug(f_multiWindowClassLoader, "f_requiresBundle: delagate to parent bundle '"+bundleName+"'.");
					f_classLoader.prototype.f_requiresBundle.call(this, bundleName);
					continue;
				}
				
				var classes=parentBundle.f_listClasses();
				
				for(var j=0;j<classes.length;j++) {
					var clazz=classes[j];
		
					this._cloneClass(clazz);
				}
				
				cnt+=classes.length;
			}
			
		} finally {
			f_core.Profile(true, "f_core.multiWindow.f_requiresBundles("+cnt+" classes)");	
		}
	}
	
	f_multiWindowClassLoader.prototype._cloneClass=function(clazz) {
	
		var className=clazz.f_getName();
	
		switch(className) {
		case "Array":
			var newArray=this._window.Array;
			var parentArray=this._parent._window.Array;
			
			newArray.prototype.f_removeElement=parentArray.prototype.f_removeElement;
			newArray.prototype.f_removeElements=parentArray.prototype.f_removeElements;
			newArray.prototype.f_addElement=parentArray.prototype.f_addElement;
			newArray.prototype.f_addElements=parentArray.prototype.f_addElements;
			newArray.prototype.f_contains=parentArray.prototype.f_contains;
			return newArray;
			
		case "f_core":
			this._window._rcfacesGW=this._parent._window._rcfacesGW;
				
			var newCore=new Object();
			this._window.f_core=newCore;
			
			var kmethods=this._parent._window.f_core._kmethods;
					
			for(var memberName in kmethods) {
				newCore[memberName]=kmethods[memberName];
			}
			
			newCore._multiWindowCore=true;
			this._window.f_core._InitLibrary(this._window);
			
			return newCore;
			
		case "f_classLoader":
		case "f_multiWindowClassLoader":
		case "f_aspect":
		case "f_class":
		
			var parentClassLoader=this._parent._window[className];
			var newClassLoader=function() {
				parentClassLoader.apply(this, arguments);		
			}
			
			newClassLoader.prototype=parentClassLoader.prototype;
			for(var i in parentClassLoader) {
				newClassLoader[i]=parentClassLoader[i];
			}
			
			newClassLoader._window=this._window;
			this._window[className]=newClassLoader;
			
			if (className=="f_class" || className=="f_aspect") {
				newClassLoader._classLoader=this;
			}
			
			return newClassLoader;
		}
	
		if (clazz instanceof f_aspect) {
			var parentAspects=clazz._parents;
			
			var aspects;
			if (parentAspects) {
				aspects=new Array;
				for(var i=0;i<parentAspects.length;i++) {
					aspects.push(this._aspects[parentAspects[i]._name]);
				}
			}
				
			var atts= {
				members: clazz._members,
				statics: clazz._staticMembers,
				extend: aspects,
				_classLoader: this
			}
			
			return new this._window.f_aspect(className, atts);			
		}
	
		f_class.InitializeClass(clazz)
	
		var parentAspects=clazz._aspects;
		
		var aspects;
		if (parentAspects) {
			aspects=new Array;
			for(var i=0;i<parentAspects.length;i++) {
				aspects.push(this._aspects[parentAspects[i]._name]);
			}
		}
			
		var parent;
		var parentParent=clazz._parent;
		if (parentParent) {
			var key=(parentParent._lookId)?(parentParent._name+f_class._LOOK+parentParent._lookId):parentParent._name;
			
			parent=this._classes[key];
		}
	
		var atts= {
			extend: parent,
			aspects: aspects,
			_classLoader: this
		}
		
		var lookId=clazz._lookId;
		if (lookId) {
			atts.lookId=lookId;
		}
	
		var members=clazz._members;
		if (members) {
			atts.members=members;
		}
		
		var staticMembers=clazz._staticMembers;
		if (staticMembers) {
			atts.statics=staticMembers;
		}
		
		if (clazz._systemClass) {
			atts._systemClass=true; 
		}
	
		if (clazz._nativeClass) {
			atts._nativeClass=true; 
		}
			
		var newClass=new this._window.f_class(className, atts);
		
		if (!newClass._nativeClass) {		
			newClass._kmethods=clazz._kmethods;
			newClass._constructor=clazz._constructor;
			newClass._initialized=true;
		}
			
		if (className=="f_resourceBundle") {
			// On recopie les bundles !
			
			this._window.f_resourceBundle.SetResources(this._parent._window.f_resourceBundle.CopyResources());
		}
		
		return newClass;
	}
	
	
	f_multiWindowClassLoader.prototype.toString=function() {
		if (!this._window) {
			return "[MultiWindowClassLoader]";
		}
		return "[MultiWindowClassLoader '"+this._window.location+"']";
	}
}