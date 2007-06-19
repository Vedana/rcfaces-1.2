/* 
 * $Id$
 *
 */

/**
 * f_classLoader
 *
 * @class public final f_classLoader extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
function f_classLoader(window, parentClassLoader) {
	this._window=window;
	this._parent=parentClassLoader;			

	this._objectPool=new Array;
	this._componentPool=new Array;
	this._systemComponentPool=new Array;
	this._classes=new Object;
	this._aspects=new Object;
	this._bundles=new Object;
}

f_classLoader.prototype = {
	/**
	 * @method public final
	 * @return Window
	 */
	f_getWindow: function() {
		return this._window;
	},
	
	/**
	 * @method public final
	 * @return Document
	 */
	f_getDocument: function() {
		return this._window.document;
	},
	
	/**
	 * @method public final
	 * @return f_classLoader
	 */
	f_getParent: function() {
		return this._parent;
	},
	
	/**
	 * @method public final
	 * @param String className Name of class.
	 * @param optional String lookId Look id.
	 * @return f_class
	 */
	f_getClass: function(className, lookId) {
		var claz; 
		for(;;) {
			var name=f_classLoader._MakeClassName(className, lookId);
			
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
	},
	
	/**
	 * @method public final
	 * @param String aspectName Name of aspect.
	 * @return f_aspect
	 */
	f_getAspect: function(aspectName) {
		var aspect=this._aspects[aspectName];
		if (aspect) {
			return aspect;
		}
	
		var parentClassloader=this._parent;
		if (!parentClassloader) {
			return null;
		}
		
		return parentClassloader.f_getAspect(aspectName);
	},
	/**
	 * @method hidden
	 * @param f_class claz
	 * @param optional Window win
	 * @return void
	 */
	f_declareClass: function(claz, win) {
		if (win && win!=window) {
			// On ne traite pas les déclarations de classes si c'est pas notre fenetre !
			return;
		}
		if (!win) {
			win=window;
		}
	
		var key=f_classLoader._MakeClassName(claz._name, claz._look);
		
		f_core.Assert(typeof(key)=="string", "f_classLoader.f_declareClass: Invalid className '"+key+"'.");
		
		f_core.Assert(!this._classes[key], "f_classLoader.f_declareClass: Class '"+key+"' is already declared.");
	
		this._classes[key] = claz;
	
		f_core.Debug(f_classLoader, "f_declareClass: Registering class "+claz._name+((claz._look)?" (lookId="+claz._look+")":"")+".");
	
		win[claz._name]=claz;
		f_classLoader._InitializeStaticMembers(claz);
	
		var parentClassloader=this._parent;
		if (parentClassloader) {
			parentClassloader.f_declareClass(claz, win);
		}
	},
	
	/**
	 * @method hidden
	 * @param f_aspect aspect
	 * @param optional Window win
	 * @return void
	 */
	f_declareAspect: function(aspect, win) {
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
		win[name]=aspect;

		f_classLoader._InitializeStaticMembers(aspect);
		
		var parentClassloader=this._parent;
		if (parentClassloader) {
			parentClassloader.f_declareAspect(aspect, win);
		}
	},
	
	_onExit: function() {
		// Cleanup objects
	
		f_core.Profile(false, "f_classLoader.onExit");
		
		this._exiting=true;
		
		this._visibleListeners=undefined; // List<f_component>
	
		// Vide le pool des objets AVANT !
		var pool=this._componentPool;
		f_core.Assert(pool, "f_classLoader._onExit: Invalid Objects componentPool !");
		this._componentPool=undefined;
	
		f_core.Debug("f_classLoader", "Clean "+pool.length+" components store into component pool !");
		f_class.Clean(pool);
		
		f_core.Profile(null, "f_classLoader.onExit.clean(components)");
		
		// Vide le pool des composants
		pool=this._objectPool;
		f_core.Assert(pool, "f_classLoader._onExit: Invalid Objects pool !");
		this._objectPool=undefined;
	
		f_core.Debug("f_classLoader","Clean "+pool.length+" objects store into component pool !");
		f_class.Clean(pool);
	
		// Vide le pool SYSTEM des composants
		pool=this._systemComponentPool;
		f_core.Assert(pool, "f_classLoader._onExit: Invalid Objects pool !");
		this._systemComponentPool=undefined;
	
		f_core.Debug("f_classLoader","Clean "+pool.length+" system objects store into component pool !");
		f_class.Clean(pool);
	
		f_core.Profile(null, "f_classLoader.onExit.clean(objects)");
	
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
			
		f_core.Profile(null, "f_classLoader.onExit.clean(classes)");
			
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
		
		f_core.Profile(true, "f_classLoader.onExit");
		
		this._parent=undefined;
		
		this._window._cameliaClassLoader=undefined;
		this._window._changeContext=undefined;
	
		//this._documentCompleted=undefined; // boolean
		//this._lazyIndex=undefined; // number
		
		// this._exiting=undefined; // boolean
	
		this._window=undefined;
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_onDocumentComplete: function() {
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
			if (!obj) {
				continue;
			}
			
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
	},
	
	_newInstance: function(object, systemClass) {
		f_core.Assert(typeof(object)=="object", "Object parameter must be an object ! ("+typeof(object)+")");
	
		if (this._exiting && !systemClass) {
			throw "This classloader is exiting ... [newInstance: "+((object._kclass)?("className="+object._kclass._name):"")+",tagName="+object.tagName+"]";
		}
	
		var pool;
		if (systemClass) {
			pool=this._systemComponentPool;
			f_core.Debug("f_classLoader", "Add SYSTEM component '"+object.id+"' of class '"+object._kclass._name+"' into component pool.");
	
		} else if (object.tagName) {
			pool=this._componentPool;
			f_core.Debug("f_classLoader", "Add component '"+object.id+"' of class '"+object._kclass._name+"' into component pool.");
			
		} else {
			pool=this._objectPool;
	//		f_core.Debug("f_classLoader", "Add object of class '"+object._kclass._name+"' into object pool.");
		}	
		
		f_core.Assert(pool, "Pool must be defined !");
		pool.push(object);
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_requiresBundle: function(doc) {
		if (this._exiting) {
			throw "This classloader is exiting ... [requiresBundle]";
		}
	
		f_core.Assert(doc && doc.nodeType==f_core.DOCUMENT_NODE, "Document parameter is not a valid document node !");
	
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
	},
	
	_loadBundle: function(doc, bundleName) {
	
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
	},
	
	// Initialize les objets "lazy" qui utilisent le tag v:init pour être identifiés.
	/**
	 * @method hidden
	 * @return void
	 */
	f_initializeObjects: function() {
		if (this._interactiveMode) {
			return;
		}
	
		if (this._exiting) {
			throw "This classloader is exiting ... [initializeObjects]";
		}
	
		var root=this._window.document.body;
		
		var lazys=root.getElementsByTagName("v:init");
		
		f_core.Debug(f_classLoader, lazys.length+" lazy object(s) found !"+
			((this._lazyIndex)?"(Current index="+this._lazyIndex+")":""));
		
		if (!lazys.length) {
			return;
		}
	
		var index=this._lazyIndex;
		if (index===undefined) {
			index=0;
		}
		
		var components=new Array;
		
		var evaluations=new Object;
		
		for(;index<lazys.length;) {
			var component=lazys[index++];
			
			this._lazyIndex=index;
			
			var requires=f_core.GetAttribute(component, "requiresBundle");
			if (requires) {
				var args=requires.split(";");
				
				args.unshift(this, this._window.document);
				this.f_requiresBundle.apply(args);
			}
			
			var rid=f_core.GetAttribute(component, "rid");
			if (rid) {
				components.push(rid, component);
				continue;
			}
			
			var clz=f_core.GetAttribute(component, "v:class");
			if (clz) {
				components.push(component, component);
				continue;
			}
			
			var fct=f_core.GetAttribute(component, "v:function");
			if (fct) {
				var evaluatedFunction=evaluations[fct];
				if (!evaluatedFunction) {
					try {
						evaluatedFunction=eval(fct)
	
					} catch (x) {
						f_core.Error(f_classLoader, "f_initializeObjects: Failed to evaluate function '"+fct+"'.", x);					
						continue;
					}

					if (typeof(evaluatedFunction)!="function") {
						f_core.Error(f_classLoader, "f_initializeObjects: Invalid type of function '"+fct+"': "+evaluatedFunction);
						continue;
					}
					
					evaluations[fct]=evaluatedFunction;
				}
								
				components.push(evaluatedFunction, component);
				continue;
			}
			
			// C'est donc le frere !
			var prev=component.previousSibling;
			for(;prev;prev=prev.previousSibling) {
				if (prev.nodeType!=f_core.ELEMENT_NODE || !prev.tagName) {
					continue;
				}
				
				if (prev.tagName.toLowerCase()=="script") {
					// C'est le cas du premier tag INIT !
					// ou de requires JS !
					continue;
				}
				
				var clz=f_core.GetAttribute(prev, "v:class");
				if (!clz) {
					f_core.Warn(f_classLoader, "f_initializeObjects: Lazy detection: Unknown previous sibling type '"+prev.tagName+"#"+prev.id+"'.");
					continue;
				}
	
				break;
			}
			
			if (prev) {				
				components.push(prev, prev);
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
	
			f_core.Error(f_classLoader, "f_initializeObjects: Unknown lazy component path='"+path+"'.");
		}
		
		for(var i=0;i<components.length;) {
			var obj=components[i++];
			var node=components[i++];
		
			var o;
			if (typeof(obj)=="function") {
				try {
					o = obj.call(this, node);
				
				} catch (x) {
					f_core.Error(f_classLoader, "f_initializeObjects: Failed to initialize object by function '"+obj+"'.", x);
					continue;
				}
	
			} else {
				try {
					o=this.f_init(obj);
					
				} catch (x) {
					f_core.Error(f_classLoader, "f_initializeObjects: Failed to initialize object '"+obj.id+"'.", x);
					continue;
				}
			}
									
			if (!o) {
				continue;
			}
			
			var clientDatas=f_core.ParseDataAttribute(node);
			if (clientDatas) {
				o._clientDatas=clientDatas;
			}
				
			var completeComponent=o.f_completeComponent;
			if (typeof(completeComponent)=="function") {
				try {
					completeComponent.call(o);
		
				} catch (x) {
					f_core.Error(f_classLoader, "f_initializeObjects: f_completeComponent throws exception for component '"+o.id+"'.", x);
				}
			}
		}
	},
	/**
	 * @method hidden
	 * @param Object obj Object or String
	 * @param boolean ignoreNotFound
	 * @return Object
	 */
	f_init: function(obj, ignoreNotFound) {
		f_core.Assert(obj && (obj.nodeType || typeof(obj)=="string"), "f_classLoader.f_init: Invalid obj parameter ("+obj+")");
		
		if (typeof(obj)=="string") {
			var id=obj;
			
			var doc=this._window.document;
	
			var obj=doc.getElementById(id);
			if (!obj) {
				var names=doc.getElementsByName(id);
				if (!names || !names.length) {
					if (ignoreNotFound) {
						return null;
					}
					throw new Error("Component not found by Id/name '"+id+"'.");
				}
				
				f_core.Assert(names.length!=1, "f_classLoader._init: Too many components associated to name '"+id+"'.");
				
				var obj=names[0];
				if (f_core.DebugMode) {
					if (obj.id) {
						f_core.Assert(obj, "f_classLoader._init: Component found by name ('"+id+"') has already an ID ('+obj.id+') !");
			
					} else if (f_core.GetAttribute(obj, "v:class")==null) {
						f_core.Assert(obj, "f_classLoader._init: Component found by name ('"+id+"') is not a RCFaces Component !");
					}
				}

				if (obj.id!=id) {
					// On l'a trouvé par le NAME, on essaye de changer l'ID !					
					
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
	
			f_core.Debug(f_classLoader, "_init: Class is not defined for component '"+obj.id+"'.");
			return obj;
		}
	
		var look = f_core.GetAttribute(obj, "v:lookid");
	
		var cls=this.f_getClass(claz, look);
		f_core.Assert(cls, "f_classLoader._init: Class '"+claz+"' lookId '"+look+"' not found !");
	
		return f_class.Init(obj, cls, f_classLoader._EMPTY_ARGUMENTS);
	},
	
	/**
	 * @method private
	 */
	_destroy: function(objs) {
		if (this._exiting) {
			return;
		}
		
		var toClean=new Array;
		for(var i=0;i<objs.length;i++) {
			var obj=objs[i];
		
			var pool=(obj.tagName)?this._componentPool:this._objectPool;
			f_core.Assert(pool, "f_classLoader._destroy: Invalid Objects pool for object "+obj);
		
			for (var j=0;j<pool.length;j++) {
				if (pool[j]!=obj) {
					continue;
				}
				
				pool.splice(j, 1);
		
				toClean.push(obj);
				obj=undefined;
		
				f_core.Debug(f_classLoader, "_destroy: Object '"+obj+"' has been removed from the pool !");
				
				break;
			}
	
			if (obj) {
				f_core.Warn(f_classLoader, "_destroy: Object '"+obj+"' is not found into pool, and can not be destroyed !");
			}
		}	
				
		if (toClean.length) {
			f_class.Clean(toClean);
		}
	},

	/**
	 * @method hidden
	 * @param HTMLElement[] components
	 * @return String
	 */
	f_serializeComponents: function(components) {
		var serial = "";
	
		try {
			this._serializing=true;
		
			for (var i=0; i<components.length; i++) {
				var obj = components[i];
				var objectId=obj.id;
				
				if (!obj || !objectId) {
					continue;
				}
				
				var f = obj.f_serialize0;
				if (!f) {
					continue;
				}
							
				f_core.Assert(typeof(f)=="function", "f_classLoader.f_serializeComponents: Field f_serialize0 is not a method for object '"+objectId+"'.");
				
				var ser;
				try {
					ser = f.call(obj);
					
				} catch (x) {
					f_core.Error(f_classLoader, "f_serializeComponents: Serialization of object '"+objectId+"' throws exception.", x);
					continue;
				}
				
				f_core.Assert(ser!==undefined, "f_classLoader.f_serializeComponents: Serialization of object '"+objectId+"' returns undefined !");
				
				if (!ser) {
					continue;
				}
				
				if (serial) {
					serial += ",";
				}
				
				serial += objectId + "=[" + ser + "]";
			}
			
		} finally {
			this._serializing=undefined;
		}
		
		return serial;
	},
	
	/**
	 * @method hidden
	 * @param HTMLFormElement form
	 * @return String
	 */
	f_serialize: function(form) {

		var components = this._componentPool;
		
		var serial=this.f_serializeComponents(components);
	
		f_core.Debug(f_classLoader, "f_serialize: Serialized form '"+form.id+"' => '"+serial+"'.");
		
		f_core.SetInputHidden(form, f_core.SERIALIZED_DATA, serial);
	},
	
	/**
	 * @method hidden
	 */
	addVisibleComponentListener: function(component) {
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
	},
		
	/**
	 * @method hidden
	 */
	fireVisibleEvent: function(componentSource) {
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
	},
	
	/**
	 * @method hidden
	 * @param optional boolean serializeState
	 * @return String serialized state
	 */
	f_garbageObjects: function(serializeState) {
		var componentPool=this._componentPool;
		f_core.Assert(componentPool, "f_classLoader.f_garbageObjects: Invalid Objects pool !");
	
		if (this._exiting) {
			throw "This classloader is exiting ... [garbageObjects]";
		}
		
		var toClean;
		for (var i=0;i<componentPool.length;) {
			var obj=componentPool[i];
			
			var p=obj;
			for(;p;p=p.parentNode) {
				if (p.nodeType==f_core.DOCUMENT_NODE) {
					break;
				}
			}
			
			if (p) {
				i++;
				continue;
			}
			
			f_core.Debug(f_classLoader, "f_garbageObjects: Mark object '"+obj+"' to garbage");
			
			if (!toClean) {
				toClean=new Array;
			}
			
			toClean.push(obj);
			
			componentPool.splice(i, 1);
		}
	
		if (!toClean) {						
			f_core.Debug(f_classLoader, "f_garbageObjects: no object garbaged.");
			return;
		}
		
		var serializedForm=null;
		
		if (serializeState) {
			serializedForm=this.f_serializeComponents(toClean);
		}
		
		f_class.Clean(toClean);
		
		f_core.Debug(f_classLoader, "f_garbageObjects: "+toClean.length+" object(s) garbaged.");
		
		return serializedForm;
	},
	
	/**
	 * @method hidden
	 */
	_declareBundle: function(bundle, win) {
		if (win && win!=window) {
			// On ne traite pas les déclarations de bundle si c'est pas notre fenetre !
			return;
		}
		if (!win) {
			win=window;
		}

		var name=bundle.f_getName();

		f_core.Profile(false, "f_classLoader.loadBundle("+name+")");

		f_core.Assert(!this._bundles[name], "Bundle '"+name+"' is alreay declared !");

		this._bundles[name]=bundle;

		if (!this._mainBundleName) {
			this._mainBundleName=name;
		}

		var parentClassloader=this._parent;
		if (parentClassloader) {
			parentClassloader._declareBundle(bundle, win);
		}

		f_core.Profile(true, "f_classLoader.loadBundle("+name+")");
	},

	toString: function() {
		if (!this._window) {
			return "[ClassLoader]";
		}
		return "[ClassLoader '"+this._window.document.title+"']";
	}
}


/**
 * @field private static final String
 */
f_classLoader._LOOK="~";

/**
 * @field private static final Array
 */
f_classLoader._EMPTY_ARGUMENTS=[];

/**
 * @method private static String
 */
f_classLoader._MakeClassName=function(claz,look) {
	if (!look) {
		return claz;
	}
	
	return claz+f_class._LOOK+look;
}

/**
 * @method static static final
 */
f_classLoader._InitializeStaticMembers=function(claz) {
	// Attention: Code pour Classes et Aspects
	
	
	var staticMembers=claz._staticMembers;
	if (staticMembers) {
	/*
		if (staticMembers instanceof _remapContext) {
			staticMembers=this._classLoader._remapContext(staticMembers);
			claz._staticMembers=staticMembers;
		}
	*/
		for(var memberName in staticMembers) {				
			var member=staticMembers[memberName];
			
			/*			
			f_core.Assert(
				typeof(member)=="number" || 
				typeof(member)=="string" || 
				member===null ||
				member===false ||
				member===true ||
				memberName=="_EVENTS" || // Ok c'est pas joli, mais bon ...
				memberName=="_ACCENTS_MAPPER" ||
				memberName=="_CALLBACKS" ||
				typeof(member)=="function", "Static member '"+memberName+"' is not litteral or function for aspect/class '"+claz._name+"' !");
			*/
					
			claz[memberName]=member;
		}
	}
			
	var staticInitializer=claz.Initializer;
	if (staticInitializer) {
		f_core.Assert(typeof(staticInitializer)=="function", "f_classLoader._InitializeStaticMembers: Invalid 'Initializer' field, it must be a function ! value="+staticInitializer);
		try {	
			staticInitializer.call(claz);
			
		} catch (x) {
			f_core.Error(f_classLoader, "_InitializeStaticMembers: Initializer of aspect/class '"+claz._name+"' throws exception.", x);
		}
	}
}

/**
 * @method hidden static
 * @param Object... object
 * @return void
 */
f_classLoader.Destroy=function(object1, object2) {

	var lastClassLoader=undefined;
	var toDestroy=undefined;
	
	for(var i=0;i<arguments.length;i++) {
		var object=arguments[i];
		
		f_core.Assert(typeof(object)=="object", "f_classLoader.Destroy: Invalid object type: "+object);
		
		var klass=object._kclass;
		if (!klass) {
			var finalizer=object.f_finalize;
			
			if (finalizer===undefined) {
				// Object sans finalizer !
				continue;
			}
			
			f_core.Assert(typeof(finalizer)=="function", "f_classLoader.Destroy: finalizer field must be a function. ("+finalizer+")");

			finalizer.call(object);
			continue;
		}
		
		var classLoader=klass._classLoader;
		f_core.Assert(classLoader, "f_classLoader.Destroy: Classloader is not defined for '"+object+"'.");
		
		// On regroupe par classLoader ...
		if (!lastClassLoader || (lastClassLoader && lastClassLoader!=classLoader)) {
			if (lastClassLoader) {
				lastClassLoader._destroy(toDestroy);
			}
			
			lastClassLoader=classLoader;
			toDestroy=new Array;
		}
		
		toDestroy.push(object);
	}
	
	if (lastClassLoader) {
		lastClassLoader._destroy(toDestroy);
	}
}

/**
 * @method hidden static
 * @param Object parameters
 * @param HTMLElement component
 * @return void
 */
f_classLoader.SerializeInputs=function(parameters, component) {
	f_core.Assert(parameters && typeof(parameters)=="object", "f_classLoader.SerializeInputs: Invalid parameters parameter '"+parameters+"'.");
	f_core.Assert(component && (component.nodeType==f_core.ELEMENT_NODE || component.nodeType==f_core.DOCUMENT_NODE), "f_classLoader.SerializeInputs: Invalid parameters parameter '"+parameters+"'.");

	var inputs=f_core.GetElementsByTagName(component, "input");
	for(var i=0;i<inputs.length;i++) {
		var input=inputs[i];
		var inputName=input.name;
		if (!inputName) {
			continue;
		}

		var value="";
		
		switch(input.type.toLowerCase()) {
		case "checkbox":
		case "radio":
			if (!input.checked) {
				break;
			}
			// On continue ...
		
		case "text":
		case "password":
		case "hidden":
			 value=input.value;
			 break;
		}
		
		parameters[inputName]=value;
	}
	
	
	var selects=f_core.GetElementsByTagName(component, "select");
	for(var i=0;i<selects.length;i++) {
		var select=selects[i];
		var selectName=select.name;
		if (!selectName) {
			continue;
		}
		
		parameters[selectName]="x";
	}
}

/**
 * @method hidden static
 * @param optional Window win
 * @return f_classLoader
 */
f_classLoader.Get=function(win) {
	if (!win) {
		win=window;
	}
	return win._cameliaClassLoader;
}

f_classLoader.f_getName=function() {
	return "f_classLoader";
}

window._cameliaClassLoader=new f_classLoader(window);
