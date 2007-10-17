/* 
 * $Id$
 *
 */

/**
 * f_classLoader
 *
 * @class public final f_classLoader
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
/**
 * @context window:window
 */
function f_classLoader(win) {
	if (!arguments.length) {
		// Constructeur vide pour l'héritage
		return;
	}
	
	f_core.Assert(win, "f_classLoader.f_classLoader: Invalid window parameter ("+win+")");
	
	this._window=win;
	f_classLoader._window=win;

	this._objectPool=new Array;
	this._componentPool=new Array;
	this._systemComponentPool=new Array;
	this._classes=new Object;
	this._aspects=new Object;
	this._bundles=new Object;
	this._serializedStates=new Object;
	this._kclass=f_classLoader;
}

f_classLoader.prototype = {
	
	/**
	 * @field hidden final Window
	 */
	_window: undefined,
	
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

		return null;
	},
	/**
	 * @method hidden
	 * @param f_class claz
	 * @return void
	 * @context object:this
	 */
	f_declareClass: function(claz) {
		var key=f_classLoader._MakeClassName(claz._name, claz._lookId);
		
		f_core.Assert(typeof(key)=="string", "f_classLoader.f_declareClass: Invalid className '"+key+"'.");
		
		f_core.Assert(!this._classes[key], "f_classLoader.f_declareClass: Class '"+key+"' is already declared.");
	
		this._classes[key] = claz;
	
		f_core.Debug(f_classLoader, "f_declareClass: Registering class "+claz._name+((claz._lookId)?" (lookId="+claz._lookId+")":"")+".");
	
		this._window[claz._name]=claz;
		f_classLoader._InitializeStaticMembers(claz);
	},
	
	/**
	 * @method hidden
	 * @param f_aspect aspect
	 * @return void
	 */
	f_declareAspect: function(aspect) {
	
		var name=aspect._name;
		
		f_core.Assert(aspect instanceof f_aspect, "f_classLoader.f_declareAspect: Aspect parameter must be an aspect ! ("+aspect+")");
		f_core.Assert(!this._aspects[name], "f_classLoader.f_declareAspectAspect: Aspect '"+name+"' is already known.");
	
		f_core.Debug(f_classLoader, "f_declareAspect: Registering aspect "+name+".");
		
		this._aspects[name] = aspect;
		this._window[name] = aspect;

		f_classLoader._InitializeStaticMembers(aspect);
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_onExit: function() {
		// Cleanup objects
	
		f_core.Profile(false, "f_classLoader.onExit");
		
		this._exiting=true;
		
		this._visibleListeners=undefined; // List<f_component>
	
		// Vide le pool des objets AVANT !
		var pool=this._componentPool;
		f_core.Assert(pool, "f_classLoader.f_onExit: Invalid Objects componentPool !");
		this._componentPool=undefined;
	
		f_core.Debug("f_classLoader", "f_onExit: Clean "+pool.length+" components store into component pool !");
		f_class.Clean(pool);
		
		f_core.Profile(null, "f_classLoader.onExit.clean(components)");
		
		// Vide le pool des composants
		pool=this._objectPool;
		f_core.Assert(pool, "f_classLoader.f_onExit: Invalid Objects pool !");
		this._objectPool=undefined;
	
		f_core.Debug("f_classLoader","f_onExit: Clean "+pool.length+" objects store into component pool !");
		f_class.Clean(pool);
	
		// Vide le pool SYSTEM des composants
		pool=this._systemComponentPool;
		f_core.Assert(pool, "f_classLoader.f_onExit: Invalid Objects pool !");
		this._systemComponentPool=undefined;
	
		f_core.Debug("f_classLoader", "f_onExit: Clean "+pool.length+" system objects store into component pool !");
		f_class.Clean(pool);
	
		f_core.Profile(null, "f_classLoader.onExit.clean(objects)");
	
		this._mainBundleName=undefined;
		this._bundles=undefined;
		
		// Il semble que la destruction des classes ne soient pas 
		// obligatoire (fuite mémoire)
		var classes=this._classes;
		f_core.Assert(classes, "f_classLoader.f_onExit: Invalid Classes pool !");
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
		
			f_core.Assert(typeof(staticFinalizer)=="function", "f_classLoader.f_onExit: Type of Finalizer callback of class '"+cls._name+"' is not a function  ! ("+staticFinalizer+")");
			
			try {
				staticFinalizer.call(cls);
				
			} catch (x) {
				f_core.Error("f_classLoader", "f_onExit: Call of method Finalizer of class '"+cls._name+"' throws exception.", x);
			}
		}
			
		f_core.Profile(null, "f_classLoader.onExit.clean(classes)");
			
		var aspects=this._aspects;
		f_core.Assert(aspects, "f_classLoader.f_onExit: Invalid Aspects pool !");
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
	
			f_core.Assert(typeof(staticFinalizer)=="function", "f_classLoader.f_onExit: Type of Finalizer callback of aspect '"+aspect._name+"' is not a function ! ("+staticFinalizer+")");
			
			try {
				staticFinalizer.call(aspect);
				
			} catch (x) {
				f_core.Error("f_classLoader", "f_onExit: Call of method Finalizer of aspect '"+aspect._name+"' throws exception.", x);
			}
		}		

		var win=this._window;
		this._window=undefined;

		for (var claz in classes) {
			win[claz._name]=undefined;
		}
		for (var aspect in aspects) {
			win[aspect._name]=undefined;
		}
		
		f_core.Profile(true, "f_classLoader.onExit");
		
		win._rcfacesClassLoader=undefined;
	
		//this._documentCompleted=undefined; // boolean
		//this._lazyIndex=undefined; // number
		
		// this._exiting=undefined; // boolean
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_onDocumentComplete: function() {
		f_core.Assert(!this._documentCompleted, "f_classLoader.f_onDocumentComplete: Document has been already completed !");
		this._documentCompleted=true;
		
		var classes=this._classes;
		f_core.Debug(f_classLoader, "f_onDocumentComplete: Calling static DocumentComplete methods ...");
		
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
			
			f_core.Assert(typeof(fct)=="function", "f_classLoader.f_onDocumentComplete: Type of DocumentComplete callback is not a function ! ("+fct+")");
			
			nb++;
			try {
				fct.call(claz);
	
			} catch (x) {			
				f_core.Error(f_classLoader, "f_onDocumentComplete: Exception during DocumentComplete for class "+claz._name, x);
			}
		}
		f_core.Debug(f_classLoader, "f_onDocumentComplete: "+nb+" static DocumentComplete method(s) called.");
	
		nb=0;
	
		var componentPool = this._componentPool;
		f_core.Debug(f_classLoader, "f_onDocumentComplete: Calling f_documentComplete methods ... ("+componentPool.length+" objects)");
		for (var i=0; i<componentPool.length; i++) {
			var obj = componentPool[i];
			if (!obj) {
				continue;
			}
			
			var fct=obj.f_documentComplete;
			if (!fct) {
				continue;
			}
				
			f_core.Assert(typeof(fct)=="function", "f_classLoader.f_onDocumentComplete: Type of f_documentComplete callback is not a function ! ("+fct+")");
			
			nb++;
			try {
				fct.call(obj);
				
			} catch (x) {
				f_core.Error(f_classLoader, "f_onDocumentComplete: Exception during documentComplete event for component "+obj.id+"/"+obj.tagName, x);
			}
		}	
	
		f_core.Debug(f_classLoader, "f_onDocumentComplete: "+nb+" f_documentComplete method(s) called.");
	},
	
	_newInstance: function(object, systemClass) {
		f_core.Assert(typeof(object)=="object", "f_classLoader._newInstance: Object parameter must be an object ! ("+typeof(object)+")");
	
		if (this._exiting && !systemClass) {
			throw "This classloader is exiting ... [newInstance: "+((object._kclass)?("className="+object._kclass._name):"")+",tagName="+object.tagName+"]";
		}
	
		var pool;
		if (systemClass) {
			pool=this._systemComponentPool;
			f_core.Debug(f_classLoader, "_newInstance: Add SYSTEM component '"+object.id+"' of class '"+object._kclass._name+"' into component pool.");
	
		} else if (object.tagName) {
			pool=this._componentPool;
			f_core.Debug(f_classLoader, "_newInstance: Add component '"+object.id+"' of class '"+object._kclass._name+"' into component pool.");
			
		} else {
			pool=this._objectPool;
	//		f_core.Debug(f_classLoader, "_newInstance: Add object of class '"+object._kclass._name+"' into object pool.");
		}	
		
		f_core.Assert(pool, "f_classLoader._newInstance: Pool must be defined !");
		pool.push(object);
	},
	
	/**
	 * @method hidden
	 * @param String... bundleNames
	 * @return void
	 */
	f_requiresBundle: function(bundleNames) {
		if (this._exiting) {
			throw "This classloader is exiting ... [requiresBundle]";
		}
			
		for(var i=0;i<arguments.length;i++) {
			var bundleName=arguments[i];
		
			this._loadBundle(bundleName);
		}	
	},
	/**
	 * @method private
	 * @param String bundleName
	 * @return boolean
	 */
	_loadBundle: function(bundleName) {
	
		if (this._interactiveMode) {
			return this._asyncLoadBundle(bundleName);
		}
	
		var bundles=this._bundles;
		if (bundles[bundleName]) {
			f_core.Debug(f_classLoader, "_loadBundle: Bundle already loaded '"+bundleName+"'.");
	
			return true;
		}
		
		var url=f_env.ComputeJavaScriptURI(bundleName);
	
		f_core.Info(f_classLoader, "_loadBundle: Load '"+bundleName+"' located at url '"+url+"'.");

		f_core.Profile(null, "f_classLoader.requestBundle("+bundleName+")");
	
		document.write("<SCRIPT type=\"text/javascript\" charset=\"UTF-8\" src=\""+url+"\"></SCRIPT>");
		
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
	
			obj=doc.getElementById(id);
			if (!obj) {
				var names=doc.getElementsByName(id);
				if (!names || !names.length) {
					if (ignoreNotFound) {
						return null;
					}
					throw new Error("Component not found by Id/name '"+id+"'.");
				}
				
				f_core.Assert(names.length!=1, "f_classLoader._init: Too many components associated to name '"+id+"'.");
				
				obj=names[0];
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
		
		// On evite l'empilement des destroys, on les ramene au premier appel !

		var localClean;
		/* var otherDocumentClean; */
		
		for(var i=0;i<objs.length;i++) {
			var obj=objs[i];
		
			var pool=(obj.tagName)?this._componentPool:this._objectPool;
			f_core.Assert(pool, "f_classLoader._destroy: Invalid Objects pool for object "+obj);
		
			for (var j=0;j<pool.length;j++) {
				if (pool[j]!=obj) {
					continue;
				}
				
				pool.splice(j, 1);
				/*
				if (obj.tagName && obj.ownerDocument!=document) {
					// Cas d'une frame !
					if (!otherDocumentClean) {
						otherDocumentClean=new Array;
					}
					
					otherDocumentClean.push(obj);
				} else {
				*/
					if (!localClean) {
						localClean=new Array;
					}
			
					localClean.push(obj);
				/*
				}
				*/
						
				f_core.Debug(f_classLoader, "_destroy: Object '"+obj+"' "+obj.tagName+"#"+obj.id+"."+obj.className+"' has been removed from the pool !");

				obj=undefined;
				
				break;
			}
	
			if (obj) {
				f_core.Warn(f_classLoader, "_destroy: Object '"+obj+"' "+obj.tagName+"#"+obj.id+"."+obj.className+"' is not found into pool, and can not be destroyed !");
			}
		}	
		
		/*
		if (otherDocumentClean) {
			f_class.Clean(otherDocumentClean);
		}
		*/
		
		if (!localClean) {
			// Il n'y a rien a detruire !
			return;
		}
		
		
		f_class.Clean(localClean);
		
		/*
		var toClean=this._toClean;
		if (toClean) {
			// nous sommes dans un empilement d'appels
			
			// On empile nos nouveaux objets AU DEBUT de la liste
			
			toClean.unshift.apply(toClean, localClean);
			return;
		}
		
		for(;toClean.length;) {
			// On ne peut les retirer que 1 par 1 !
			// Car si c'est un element complexe, il peut en ajouter d'autres !
			
			f_class.Clean(toClean.splice(0, 1));
		}
		
		this._toClean=undefined;
		*/
	},

	/**
	 * @method private
	 * @param HTMLElement[] components
	 * @return void
	 */
	f_serializeComponents: function(components) {
		var serializedStates=this._serializedStates;
	
		try {
			this._serializing=true;
		
			for (var i=0; i<components.length; i++) {
				var component = components[i];
				var componentId=component.id;
				
				if (!component || !componentId) {
					continue;
				}
				
				var f = component.f_serialize0;
				if (!f) {
					continue;
				}
							
				f_core.Assert(typeof(f)=="function", "f_classLoader.f_serializeComponents: Field f_serialize0 is not a method for object '"+componentId+"'.");
				
				var ser;
				try {
					ser = f.call(component);
					
				} catch (x) {
					f_core.Error(f_classLoader, "f_serializeComponents: Serialization of object '"+componentId+"' throws exception.", x);
					continue;
				}
				
				f_core.Assert(ser!==undefined, "f_classLoader.f_serializeComponents: Serialization of object '"+componentId+"' returns undefined !");
				
				if (!ser) {
					delete serializedStates[componentId];
					continue;
				}
				
				serializedStates[componentId]=ser;
			}
			
		} finally {
			this._serializing=undefined;
		}
	},
	/**
	 * @method hidden
	 * @return String
	 */
	f_getSerializedState: function() {
		var serial="";
		
		var serializedStates=this._serializedStates;
	
		for(var id in serializedStates) {
			if (serial) {
				serial+=",";
			}
			serial+=id+"=["+serializedStates[id]+"]";
		}		
		
		return serial;		
	},
	/**
	 * @method hidden
	 * @param HTMLFormElement form
	 * @return void
	 */
	f_serialize: function(form) {
		
		this.f_serializeComponents(this._componentPool);
		
		var serial=this.f_getSerializedState();
	
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
		
		var garbageMark=this._garbageMark;
		if (garbageMark===undefined) {
			garbageMark=new Date().getTime();
		}

		var keepGarbageMark=++garbageMark;
		var clearGarbageMark=++garbageMark;
		this._garbageMark=garbageMark;
		
		var list=new Array;
		var toClean;
		for (var i=0;i<componentPool.length;) {
			var obj=componentPool[i];
			
			var p=obj;
			var gm;
			var parentNode;
			for(;;) {
				if (p.nodeType==f_core.DOCUMENT_NODE) {
					break;
				}

				gm=p._rcfacesGarbageMark;

				if (gm==keepGarbageMark) {
					// Cool !
					break;
					
				} else if (gm==clearGarbageMark) {
					p=null;
					break;
				}

				list.push(p); // On marque

				parentNode=p.parentNode;
				if (parentNode) {
					p=parentNode;
					
					continue;
				}
				
				parentNode=p._parentNode;
				if (parentNode) {
					p=parentNode;

					continue;
				}
				
				p=null;
				break;
			}
			
			var setMark=(p)?keepGarbageMark:clearGarbageMark;
			for(;list.length;) {
				list.shift()._rcfacesGarbageMark=setMark;
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

			if (serializeState) {
				return this.f_getSerializedState();
			}
			return undefined;
		}
		
		var serializedForm=null;
		
		if (serializeState) {
			this.f_serializeComponents(toClean);

			serializedForm=this.f_getSerializedState();
		}
		
		f_class.Clean(toClean);
		
		f_core.Debug(f_classLoader, "f_garbageObjects: "+toClean.length+" object(s) garbaged.");
		
		return serializedForm;
	},
	
	/**
	 * @method hidden
	 * @param f_bundle bundle
	 * @return void
	 */
	_declareBundle: function(bundle) {
		var name=bundle.f_getName();
		
		f_core.Profile(false, "f_classLoader.loadBundle("+name+")");

		f_core.Assert(!this._bundles[name], "f_classLoader._declareBundle: Bundle '"+name+"' is alreay declared !");

		this._bundles[name]=bundle;

		if (!this._mainBundleName) {
			this._mainBundleName=name;
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
 * @field hidden static final boolean
 */
f_classLoader.MULTI_WINDOW_CLASSLOADER=false;

/**
 * @method private static
 * @param String claz
 * @param optional String lookId 
 * @return String
 */
f_classLoader._MakeClassName=function(claz, lookId) {
	if (!lookId) {
		return claz;
	}
	
	return claz+f_class._LOOK+lookId;
}

/**
 * @method private static final
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
 * @method private static
 * @param Object parameters
 * @param HTMLElement component
 * @return Object
 */
f_classLoader._SerializeInputs=function(component) {
	f_core.Assert(component && (component.nodeType==f_core.ELEMENT_NODE || component.nodeType==f_core.DOCUMENT_NODE), "f_classLoader.SerializeInputs: Invalid component parameter '"+component+"'.");
	
	var form=f_core.GetParentForm(component);
	var serializedInputs=form._serializedInputs;
	if (!serializedInputs) {
		serializedInputs=new Object;
		form._serializedInputs=serializedInputs;
	}

	var inputs=f_core.GetElementsByTagName(component, "input");
	for(var i=0;i<inputs.length;i++) {
		var input=inputs[i];
		
		if (input._dontSerialize) {
			continue;
		}
		
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
		
		serializedInputs[inputName]=value;
	}
	
	var selects=f_core.GetElementsByTagName(component, "select");
	for(var i=0;i<selects.length;i++) {
		var select=selects[i];
		var selectName=select.name;
		if (!selectName) {
			continue;
		}
		
		serializedInputs[selectName]=select.value;
	}
	
	return serializedInputs;
}

/**
 * @method hidden static
 * @param Object parameters
 * @param HTMLElement component
 * @param boolean updateInputs
 * @return void
 */
f_classLoader.SerializeInputsIntoParam=function(parameters, component, updateInputs) {
	f_core.Assert(parameters && typeof(parameters)=="object", "f_classLoader.SerializeInputsIntoParam: Invalid parameters parameter '"+parameters+"'.");
	f_core.Assert(component===undefined || (component.nodeType==f_core.ELEMENT_NODE || component.nodeType==f_core.DOCUMENT_NODE), "f_classLoader.SerializeInputsIntoParam: Invalid component parameter '"+component+"'.");
	
	var serializedInputs;
	if (updateInputs) {
		serializedInputs=f_classLoader._SerializeInputs(component);

	} else {
		var form=f_core.GetParentForm(component);
		serializedInputs=form._serializedInputs
	}
	
	if (!serializedInputs) {
		return;
	}
	
	for(var name in serializedInputs) {
		parameters[name]=serializedInputs[name];
	}	
}

/**
 * @method hidden static
 * @param Object parameters
 * @param HTMLElement component
 * @return void
 */
f_classLoader.SerializeInputsIntoForm=function(form) {
	f_core.Assert(form && form.nodeType==f_core.ELEMENT_NODE && form.tagName.toLowerCase()=="form", "f_classLoader.SerializeInputsIntoParam: Invalid form parameter '"+form+"'.");
	
	var serializedInputs=form._serializedInputs;
	if (!serializedInputs) {
		// Aucune sérialisation donc on laisse tomber la recherche de doublons d'INPUT		
		return;
	}
	
	for(var name in serializedInputs) {
		var value=serializedInputs[name];
		var elements=document.getElementsByName(name);
		
		f_core.Debug(f_classLoader, "SerializeInputsIntoForm: elements.length="+elements.length+" for name '"+name+"'.");
		
		switch(elements.length) {
		case 0:
			// Aucun élément
			var input = form.ownerDocument.createElement("input");
			input.type = "hidden";
			input.value = value;
			input.name = name;
			input._serializedInput=true;
		
			form.appendChild(input);

			f_core.Debug(f_classLoader, "SerializeInputsIntoForm: add input name='"+name+"' value='"+value+"'");
			break;
		
		case 1:
			// Un seul élément, c'est un faux ?
			var input=elements[0];
			if (input._serializedInput) {
				// C'est un faux !
				// On change juste la valeur
				
				input.value=value;
				f_core.Debug(f_classLoader, "SerializeInputsIntoForm: change input value for name='"+name+"', new value='"+value+"'");
				break;
			}
			// C'est pas un faux !
			// Bizarre comme config, on laisse tomber ...
			break;
		
		default:
			// Plusieurs champs avec le même nom
			
			// Y a t-il un vrai input ?
			var realValueComponent=null;
			for(var i=0;i<elements.length;i++) {
				var input=elements[i];
				if (input._serializedInput) {
					continue;
				}
				
				realValueComponent=input;
				break;
			}
			
			if (!realValueComponent) {
				// Que des faux, on garde le premier faux
				// et on efface le reste ...
				
				realValueComponent=elements[0];
				realValueComponent.value=value;
			}
				
			// On effaces tous les faux sauf le realValueComponent
			for(var i=0;i<elements.length;i++) {
				var input=elements[i];
				if (!input._serializedInput || input==realValueComponent) {
					continue;
				}

				input.parentNode.removeChild(input);
			}
			
			break;
		}
	}		
}


/**
 * @method hidden static
 * @param optional Window win
 * @return f_classLoader
 * @context window:window
 */
f_classLoader.Get=function(win) {
	if (!win) {
		win=window;
	}
	return win._rcfacesClassLoader;
}

/**
 * @method public static
 * @return String
 */
f_classLoader.f_getName=function() {
	return "f_classLoader";
}

window._rcfacesClassLoader=new f_classLoader(window);
