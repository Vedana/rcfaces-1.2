/* 
 * $Id$
 *
 */

/**
 * f_classLoader
 *
 * @class public f_classLoader
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
function f_classLoader(window, parentClassLoader) {
	this._window=window;
	this._parent=parentClassLoader;			

	this._objectPool=new Array;
	this._componentPool=new Array;
	this._classes=new Object;
	this._aspects=new Object;
	this._bundles=new Object;
}
/**
 * @method public final
 * @return Window
 */
f_classLoader.prototype.f_getWindow=function() {
	return this._window;
}
/**
 * @method public final
 * @return Document
 */
f_classLoader.prototype.f_getDocument=function() {
	return this._window.document;
}
/**
 * @method public final
 * @return f_classLoader
 */
f_classLoader.prototype.f_getParent=function() {
	return this._parent;
}

/**
 * @method public final
 * @param string className Name of class.
 * @param string lookId Look id.
 * @return f_class
 */
f_classLoader.prototype.f_getClass=function(className, lookId) {
	var claz; 
	for(;;) {
		var name=f_class.MakeClassName(className, lookId);
		
		claz=this._classes[name];
		if (claz) {
			break;
		}
		
		var parentClassloader=this._parent;
		if (parentClassloader) {
			claz= parentClassloader.f_getClass(className, lookId);
			if (claz) {
				// C'est déjà initializé par le parent !
				return claz;
			}
		}

		if (!lookId) {
			return null;
		}
		
		lookId=undefined;
	}
	
	f_class.InitializeClass(claz);
	
	return claz;
}

f_classLoader.prototype.f_getAspect=function(aspectName) {
	var aspect=this._aspects[aspectName];

	var parentClassloader=this._parent;
	if (!parentClassloader) {
		return null;
	}
	
	return parentClassloader.f_getAspect(aspectName);
}

f_classLoader.prototype._declareClass=function(claz, win) {
	if (win && win!=window) {
		// On ne traite pas les déclarations de classes si c'est pas notre fenetre !
		return;
	}
	if (!win) {
		win=window;
	}

	var key=f_class.MakeClassName(claz._name, claz._look);
	
	f_core.Assert(!this._classes[key], "Class '"+key+"' is already declared.");

	this._classes[key] = claz;

	f_core.Debug("f_classLoader", "Registering class "+claz._name+((claz._look)?" (lookId="+claz._look+")":"")+".");

	win[claz._name]=claz;
	f_class.InitializeStaticMembers(claz);

	var parentClassloader=this._parent;
	if (parentClassloader) {
		parentClassloader._declareClass(claz, win);
	}
}

f_classLoader.prototype._declareAspect=function(aspect, win) {
	if (win && win!=window) {
		// On ne traite pas les déclarations de classes si c'est pas notre fenetre !
		return;
	}
	if (!win) {
		win=window;
	}

	var name=aspect._name;
	
	f_core.Assert(aspect instanceof f_aspect, "Aspect parameter must be an aspect ! ("+aspect+")");
	f_core.Assert(!this._aspects[name],"Aspect '"+name+"' is already known.");

	f_core.Debug("f_classLoader", "Registering aspect "+name+".");
	
	this._aspects[name] = aspect;

	f_class.InitializeStaticMembers(aspect);

	var parentClassloader=this._parent;
	if (parentClassloader) {
		parentClassloader._declareAspect(aspect, win);
	}
}

f_classLoader.prototype._onExit=function() {
	// Cleanup objects

	f_core.Profile("f_classLoader.onExit.enter");
	
	this._exiting=true;
	
	this._visibleListeners=undefined;

	// Vide le pool des objets AVANT !
	var pool=this._componentPool;
	f_core.Assert(pool, "f_classLoader._onExit: Invalid Objects componentPool !");
	this._componentPool=undefined;

	f_core.Debug("f_classLoader", "Clean "+pool.length+" components store into component pool !");
	f_class.Clean.apply(f_class, pool);
	
	f_core.Profile("f_classLoader.onExit.clean(components)");
	
	// Vide le pool des composants
	var pool=this._objectPool;
	f_core.Assert(pool, "f_classLoader._onExit: Invalid Objects pool !");
	this._objectPool=undefined;

	f_core.Debug("f_classLoader","Clean "+pool.length+" objects store into component pool !");
	f_class.Clean.apply(f_class, pool);

	f_core.Profile("f_classLoader.onExit.clean(objects)");

	this._mainBundleName=undefined;
	this._bundles=undefined;
	
	// Il semble que la destruction des classes ne soient pas 
	// obligatoire (fuite mémoire)
	var classes=this._classes;
	f_core.Assert(classes, "f_classLoader._onExit: Invalid Classes pool !");
	this._classes=undefined;

	for (var claz in classes) {
		var cls = classes[claz];

		if (!cls._initialized) {
			continue;
		}

		var staticMembers=cls._staticMembers;
		if (!staticMembers) {
			continue;
		}

		var staticFinalizer=staticMembers.Finalizer;
		if (!staticFinalizer) {
			continue;
		}
	
		f_core.Assert(typeof(staticFinalizer)=="function", "Type of Finalizer callback of class '"+cls._name+"' is not a function  ! ("+staticFinalizer+")");
		
		try {
			staticFinalizer.call(cls);
			
		} catch (x) {
			f_core.Error("f_classLoader", "Call of method Finalizer of class '"+cls._name+"' throws exception.", x);
		}
	}
		
	f_core.Profile("f_classLoader.onExit.clean(classes)");
		
	var aspects=this._aspects;
	f_core.Assert(aspects, "f_classLoader._onExit: Invalid Aspects pool !");
	this._aspects=undefined;
	
	for (var name in aspects) {
		var aspect = aspects[name];

		if (!aspect._initialized) {
			continue;
		}

		var staticMembers=aspect._staticMembers;
		if (!staticMembers) {
			continue;
		}

		var staticFinalizer=staticMembers.Finalizer;
		if (!staticFinalizer) {
			continue;
		}

		f_core.Assert(typeof(staticFinalizer)=="function", "Type of Finalizer callback of aspect '"+aspect._name+"' is not a function ! ("+staticFinalizer+")");
		
		try {
			staticFinalizer.call(aspect);
			
		} catch (x) {
			f_core.Error("f_classLoader", "Call of method Finalizer of aspect '"+aspect._name+"' throws exception.", x);
		}
	}	
	
	f_core.Profile("f_classLoader.onExit.clean(aspects)");
	
	this._parent=undefined;
	
	this._window._classLoader=undefined;
	this._window._changeContext=undefined;

	//this._documentCompleted=undefined; // boolean
	//this._lazyIndex=undefined; // number
	
	// this._exiting=undefined; // boolean

	this._window=undefined;
}

f_classLoader.prototype._onDocumentComplete=function() {
	f_core.Assert(!this._documentCompleted, "Document has been already completed !");
	this._documentCompleted=true;
	
	var classes=this._classes;
	f_core.Debug("f_classLoader", "Calling static DocumentComplete methods ...");
	
	var nb=0;
	for(var i in classes) {
		var claz=classes[i];
		
		var staticMembers=claz._staticMembers;
		if (!staticMembers) {
			continue;
		}
		
		var fct=staticMembers.DocumentComplete;
		if (!fct) {
			continue;
		}
		
		f_core.Assert(typeof(fct)=="function", "Type of DocumentComplete callback is not a function ! ("+fct+")");
		
		nb++;
		try {
			fct.call(claz);

		} catch (x) {			
			f_core.Error("f_classLoader", "Exception during DocumentComplete for class "+claz._name, x);
		}
	}
	f_core.Debug("f_classLoader", nb+" static DocumentComplete method(s) called.");

	
	nb=0;

	var componentPool = this._componentPool;
	f_core.Debug("f_classLoader", "Calling f_documentComplete methods ... ("+componentPool.length+" objects)");
	for (var i=0; i<componentPool.length; i++) {
		var obj = componentPool[i];
		
		var fct=obj.f_documentComplete;
		if (!fct) {
			continue;
		}
			
		f_core.Assert(typeof(fct)=="function", "Type of f_documentComplete callback is not a function ! ("+fct+")");
		
		try {
			fct.call(obj);
			
		} catch (x) {
			f_core.Error("f_classLoader", "Exception during documentComplete event for component "+obj.id+"/"+obj.tagName, x);
		}
	}	

	f_core.Debug("f_classLoader", nb+" f_documentComplete method(s) called.");
}

f_classLoader.prototype._newInstance=function(object) {
	f_core.Assert(typeof(object)=="object", "Object parameter must be an object ! ("+typeof(object)+")");

	if (this._exiting) {
		throw "This classloader is exiting ... [newInstance]";
	}

	var pool;
	if (object.tagName) {
		pool=this._componentPool;
		f_core.Debug("f_classLoader", "Add component '"+object.id+"' of class '"+object._kclass._name+"' into component pool.");
		
	} else {
		pool=this._objectPool;
//		f_core.Debug("f_classLoader", "Add object of class '"+object._kclass._name+"' into object pool.");
	}	
	
	f_core.Assert(pool, "Pool must be defined !");
	pool.push(object);
}
/**
 * @method hidden static
 * @return void
 */
f_classLoader.prototype.requiresBundle=function(doc) {
	if (this._exiting) {
		throw "This classloader is exiting ... [requiresBundle]";
	}

	f_core.Assert(doc && doc.nodeType==9, "Document parameter is not a valid document node !");

	var parentClassloader=this._parent;
	
	// #0=document
	for(var i=1;i<arguments.length;i++) {
		var bundleName=arguments[i];

		if (parentClassloader) {		
			if (parentClassloader._loadBundle(doc, bundleName)) {
				continue;
			}
			
			f_core.Debug("f_classLoader", "Parent classloader can not find bundle '"+bundleName+"'.");
		}
	
		this._loadBundle(doc, bundleName);
	}	
}

f_classLoader.prototype._loadBundle=function(doc, bundleName) {

	var interactiveMode=this._interactiveMode;	
	if (interactiveMode) {
		return interactiveMode.addRequireBundle(bundleName);
	}

	var bundles=this._bundles;
	if (bundles[bundleName]) {
		f_core.Debug("f_classLoader", "Bundle already loaded '"+bundleName+"'.");

		return true;
	}
	
	var url=f_env.ComputeJavaScriptURI(bundleName);

	f_core.Info("f_classLoader", "Load '"+bundleName+"' located at url '"+url+"'.");

	doc.write("<SCRIPT type=\"text/javascript\" charset=\"UTF-8\" src=\""+url+"\"></SCRIPT>");
	
	return true;
}

// Initialize les objets "lazy" qui utilisent le tag v:init pour être identifiés.
f_classLoader.prototype._initializeObjects = function() {
	if (this._interactiveMode) {
		return;
	}

	if (this._exiting) {
		throw "This classloader is exiting ... [initializeObjects]";
	}

	var root=this._window.document.body;
	
	var lazys=root.getElementsByTagName("v:init");
	
	f_core.Debug("f_classLoader", lazys.length+" lazy object(s) found !"+
		((this._lazyIndex)?"(Current index="+this._lazyIndex+")":""));
	
	if (lazys.length==0) {
		return;
	}

	var index=this._lazyIndex;
	if (index===undefined) {
		index=0;
	}
	
	for(;index<lazys.length;) {
		var component=lazys[index++];
		
		this._lazyIndex=index;
		
		var requires=f_core.GetAttribute(component, "requiresBundle");
		if (requires) {
			var args=requires.split(";");
			
			args.unshift(this, this._window.document);
			this.requiresBundle.apply(args);
		}
		
		var rid=f_core.GetAttribute(component, "rid");
		if (rid) {
			this._vinit(rid, component);
			continue;
		}
		
		var clz=f_core.GetAttribute(component, "v:class");
		if (clz) {
			this._vinit(component);
			continue;
		}
		
		// C'est donc le frere !
		var prev=component.previousSibling;
		for(;prev;prev=prev.previousSibling) {
			if (prev.nodeType!=1 || !prev.tagName) {
				continue;
			}
			
			if (prev.tagName.toUpperCase()=="SCRIPT") {
				// C'est le cas du premier tag INIT !
				// ou de requires JS !
				continue;
			}
			
			var clz=f_core.GetAttribute(prev, "v:class");
			if (!clz) {
				f_core.Warn("f_classLoader", "Lazy detection: Unknown previous sibling type '"+prev.tagName+"#"+prev.id+"'.");
				continue;
			}

			break;
		}
		
		if (prev) {				
			this._vinit(prev);
			continue;
		}
		
		var path="";
		for(var p=component;p;p=p.parentNode) {
			if (path.length>0) {
				path=" > "+path;
			}
			
			if (!p.tagName) {
				continue;
			}
			path=p.tagName+((p.id)?("#"+p.id):"")+path;
		}

		f_core.Error("f_classLoader", "Unknown lazy component path='"+path+"'.");
	}
}
f_classLoader.prototype._vinit = function(obj, node) {	
	var o;
	try {
		o=this._init(obj);
		
	} catch (x) {
		f_core.Error("f_classLoader", "Failed to initialize object '"+obj.id+"'.", x);
		return;
	}
				
	if (!o) {
		return;
	}
	
	if (!node) {
		node=obj;
	}
	
	var clientData=f_core.GetAttribute(node, "v:data");
	if (clientData) {
		fa_clientData.InitializeDataAttribute(o, node);
	}
		
	var update=o._completeComponent;
	if (update && typeof(update)=="function") {
		try {
			update.call(o);

		} catch (x) {
			f_core.Error("f_classLoader", "_completeComponent throws exception for component '"+o.id+"'.", x);
		}
	}
	
	return o;
},
f_classLoader.prototype._init = function(obj, ignoreNotFound) {
	f_core.Assert(obj, "f_classLoader._init: Obj parameter is invalid ("+obj+")");
	
	if (typeof(obj)=="string") {
		var id=obj;
		
		var document=this._window.document;

		var obj=document.getElementById(id);
		if (!obj) {
			var names=document.getElementsByName(id);
			if (!names || names.length==0) {
				if (ignoreNotFound) {
					return null;
				}
				throw new Error("Component not found by Id/name '"+id+"'.");
			}
			
			f_core.Assert(names.length!=1, "Too many components associated to name '"+id+"'.");
			
			var obj=names[0];
			if (f_core.Debug_Mode) {
				if (obj.id) {
					f_core.Assert(obj, "Component found by name ('"+id+"') has already an ID ('+obj.id+') !");
		
				} else if (f_core.GetAttribute(obj, "v:class")==null) {
					f_core.Assert(obj, "Component found by name ('"+id+"') is not a RCFaces Component !");
				}
			}
						
			if (obj.id!=id) {
				// ATTENTION - Sous IE le changement de la propriété ID est Read-only !
				try {
					obj.id=id;
				} catch (x) {
				}
			}
		}
	}
	
	if (obj._kclass) {
		// Deja initialisé !
		return obj;
	}
			
	var claz = f_core.GetAttribute(obj, "v:class");
	if (!claz) {
		// La classe n'est pas définie ... c'est peut etre une form !

		f_core.Debug("f_classLoader", "Class is not defined for component '"+obj.id+"'.");
		return obj;
	}

	var look = f_core.GetAttribute(obj, "v:lookid");

	var cls=this.f_getClass(claz, look);
	f_core.Assert(cls instanceof f_class, "Class '"+claz+"' lookId '"+look+"' not found !");

	return f_class.Init(obj, cls);
}

/**
 * @method private
 */
f_classLoader.prototype._destroy = function(obj) {
	if (this._exiting) {
		return;
	}
	
	var pool=(obj.tagName)?this._componentPool:this._objectPool;
	f_core.Assert(pool, "f_classLoader._destroy: Invalid Objects pool for object "+obj);

	for (var i=0;i<pool.length;i++) {
		if (pool[i]!=obj) {
			continue;
		}
		
		f_class.Clean(obj);
		pool.splice(i, 1);

		f_core.Debug("f_classLoader", "Object '"+obj+"' has been removed from the pool !");
		return;
	}
	
	f_core.Warn("f_classLoader", "Object '"+obj+"' is not found into pool, and can not be destroyed !");
}

/**
 * @method hidden
 */
f_classLoader.prototype.serialize=function(form) {
	var serial = "";

	try {
		this._serializing=true;
	
		var a = this._componentPool;
		for (var i=0; i<a.length; i++) {
			var obj = a[i];
			if (!obj.id) {
				continue;
			}
			
			var f = obj._serialize0;
			if (!f) {
				continue;
			}
						
			f_core.Assert(typeof(f)=="function", "Field _serialize0 is not a method !");
			
			var ser;
			try {
				ser = f.call(obj);
				
			} catch (x) {
				f_core.Error("f_classLoader", "Serialization of object '"+obj.id+"' throws exception.", x);
				continue;
			}
			
			f_core.Assert(ser!==undefined, "Serialization of object returns undefined !");
			
			if (!ser) {
				continue;
			}
			
			if (serial) {
				serial += ",";
			}
			
			serial += (obj.id + "=[" + ser + "]");
		}
		
	} finally {
		this._serializing=undefined;
	}

	f_core.Debug("f_classLoader", "Serialized form of component '"+form.id+"'='"+serial+"'.");
	
	if (!serial) {
		return;
	}
	
	f_core.SetInputHidden(form, f_core.SERIALIZED_DATA, serial);
}

/**
 * @method hidden
 */
f_classLoader.prototype.addVisibleComponentListener=function(component) {
	f_core.Assert(component.f_performComponentVisible, "Callback 'f_performComponentVisible' not found !");

	if (this._exiting) {
		throw "This classloader is exiting ... [addVisibleComponentListener]";
	}
	
	if (f_core.IsComponentVisible(component)) {
		return false;
	}
	
	var visibleListeners=this._visibleListeners;

	if (!visibleListeners) {
		visibleListeners=new Array;
		this._visibleListeners=visibleListeners;
	}
	
	visibleListeners.f_addElement(component);
	
	return true;
}
	
/**
 * @method hidden
 */
f_classLoader.prototype.fireVisibleEvent=function(componentSource) {
	f_core.Debug("f_classLoader", "Fire visible event for '"+componentSource.id+"'.");
	var components=this._visibleListeners;
	if (!components) {
		return;
	}
	
	for(var i=0;i<components.length;) {
		var component=components[i];
		
		if (!f_core.IsComponentVisible(component)) {
			i++;
			continue;
		}
		
		components.splice(i, 1);
	
		f_core.Debug("f_classLoader", "New visible registred component: '"+component.id+"', call callback.");
		
		var fct=component.f_performComponentVisible;
		if (fct===undefined) {
			continue;
		}
		
		f_core.Assert(typeof(fct)=="function", "f_performComponentVisible of component '"+component.id+"' is not a function !");
		
		try {
			fct.call(component, componentSource);
			
		} catch (x) {
			f_core.Error("f_classLoader", "Call of method f_performComponentVisible of component '"+component.id+"' throws exception.", x);
		}
	}
}

/**
 * @method hidden
 */
f_classLoader.prototype.garbageObjects=function() {
	var componentPool=this._componentPool;
	f_core.Assert(componentPool, "f_classLoader._garbageObjects: Invalid Objects pool !");

	if (this._exiting) {
		throw "This classloader is exiting ... [garbageObjects]";
	}
	
	var toClean;
	for (var i=0;i<componentPool.length;) {
		var obj=componentPool[i];
		
		var p=obj;
		for(;p;p=p.parentNode) {
			if (p.nodeType==9) {
				break;
			}
		}
		
		if (p) {
			i++;
			continue;
		}
		
		if (!toClean) {
			toClean=new Array;
		}
		
		f_core.Debug("f_classLoader", "Garbage object: "+obj);
		
		toClean.push(obj);
		
		componentPool.splice(i, 1);
	}

	if (toClean) {		
		f_class.Clean.apply(f_class, toClean);
	}
	
	f_core.Debug("f_classLoader", ((toClean)?toClean.length:0)+" objects garbaged.");
}

/**
 * @method hidden
 */
f_classLoader.prototype._declareBundle=function(bundle, win) {
	if (win && win!=window) {
		// On ne traite pas les déclarations de bundle si c'est pas notre fenetre !
		return;
	}
	if (!win) {
		win=window;
	}
	
	var name=bundle.f_getName();

	f_core.Profile("f_classLoader.loadBundle("+name+")");
	
	f_core.Assert(!this._bundles[name], "Bundle '"+name+"' is alreay declared !");
	
	this._bundles[name]=bundle;
	
	if (!this._mainBundleName) {
		this._mainBundleName=name;
	}
	
	var parentClassloader=this._parent;
	if (parentClassloader) {
		parentClassloader._declareBundle(bundle, win);
	}
}

f_classLoader.prototype.toString=function() {
	if (!this._window) {
		return "[ClassLoader]";
	}
	return "[ClassLoader '"+this._window.document.title+"']";
}


/**
 * @method hidden static
 * @param Object object
 * @param optional Object object2
 * @return void;
 */
f_classLoader.Destroy=function(object, objectN) {
	for(var i=0;i<arguments.length;i++) {
		object=arguments[i];
		
		f_core.Assert(typeof(object)=="object", "f_classLoader.Destroy: Invalid object: "+object);
		
		var klass=object._kclass;
		if (!klass) {
			var finalizer=object.f_finalize;
			if (typeof(finalizer)=="function") {
				finalizer.call(object);
			}
			continue;
		}
		
		var classLoader=klass._classLoader;
		f_core.Assert(classLoader, "Classloader is not defined for '"+object+"'.");
		
		classLoader._destroy(object);
	}
}



f_classLoader.f_getName=function() {
	return "f_classLoader";
}

window._classLoader=new f_classLoader(window);
