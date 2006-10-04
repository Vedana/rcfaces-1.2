/* 
 * $Id$
 */
 
 /* 
  * !!!!!!
  * 
  * Attention, a cause de la déclaration de methodes STATIQUES dans un objet JavaScript,
  * et de l'utilisation d'un optimiseur de code, 
  * Il ne faut SURTOUT PAS UTILISER de with dans le code des méthode de f_class !
  */

/**
 * f_class package
 *
 * @class public f_class
 * @author Olivier Oeuillot (latest modification by $Author$) & Joel Merlin
 * @version $Revision$ $Date$
 */
function f_class(className, lookId, staticMembers, members, parentClass) {
	// Constructeur vide: on ne fait rien !
	if (arguments.length==0) {
		return;
	}
	if (arguments[0] instanceof f_classLoader) {
		this._newMultiWindow(arguments);		
		return;
	}
	
	if (!parentClass) {
		parentClass=window.f_object;
		// Pour certaines classes de base (f_env, ...)
		// f_object ne peut ne pas être encore déclaré !
	}

	this._classLoader=window._classLoader;
	
	this._name = className;
	this._look = lookId;
	this._staticMembers = staticMembers;
	this._members = members;
	this._parent = parentClass;
	
	if (arguments.length>5) {
		// Aspects
		var aspects=f_core.PushArguments(null, arguments, 5);
		this._aspects=aspects;
		
		if (f_core.IsDebugEnabled("f_class")) {
			for(var i=0;i<aspects.length;i++) {
				var aspect=aspects[i];
				
				f_core.Assert(aspect instanceof f_aspect, "Not an aspect ("+aspect+") for className '"+className+"' lookId='"+lookId+"' ?");
			}
		}
	}
	
	this._classLoader._declareClass(this);
}
/**
 * @method public final
 * @return string
 */
f_class.prototype.f_getName=function() {
	return this._name;
}
/**
 * @method public final
 * @return string
 */
f_class.prototype.f_getLookId=function() {
	return this._look;
}
/**
 * Returns super class of this class.
 *
 * @method public final
 * @return f_class
 */
f_class.prototype.f_getParent=function() {
	return this._parent;
}
/**
 * @method public final
 * @return Object
 */
f_class.prototype.f_newInstance=function() {
	var obj = new Object;
	
	return f_class.Init(obj, this, arguments);
}
/**
 * Returns the classes loader of this class.
 * 
 * @method public final
 * @return f_classLoader
 */
f_class.prototype.f_getClassLoader=function() {
	return this._classLoader;
}
/**
 * @method hidden final
 * @return f_classLoader
 */
f_class.prototype.f_localize=function(staticMembers, instanceMembers) {
	if (staticMembers) {
		var sms=this._staticMembers;
		for(var memberName in staticMembers) {
			sms[memberName] = staticMembers[memberName];
		}
	}
	
	if (instanceMembers) {
		var ims=this._instanceMembers;
		for(var memberName in instanceMembers) {
			ims[memberName] = instanceMembers[memberName];
		}
	}
}

f_class.f_getName=function() {
	return "f_class";
}

/**
 * @method public final
 * @return string
 */
f_class.prototype.toString=function() {
	return "[f_class "+this._name+"]";
}

var __static = {

	/**
	 * @field private static final string
	 */
	_LOOK: "~",
	
	/**
	 * @field hidden static final string
	 */
	ABSTRACT: "f_abstract",
	
	/**
	 * @field hidden static final string
	 */
	OPTIONAL_ABSTRACT: "f_optionalAbstract",
	
	/**
	 * @field hidden static final string
	 */
	BEFORE_ASPECT: "before",

	/**
	 * @field hidden static final string
	 */
	AFTER_ASPECT: "after",

	/**
	 * @field hidden static final string
	 */
	THROWING_ASPECT: "throwing",
	
	/**
	 * @method hidden static final string
	 */
	MakeClassName: function(claz,look) {
		if (!look) {
			return claz;
		}
		
		return claz+f_class._LOOK+look;
	},
	/**
	 * @method private static final string
	 */
	_Call: function(obj,m,a) {
		if (!a || a.length<1) {
			return m.call(obj);
		}
		return m.apply(obj, a);
	},
	/**
	 * @method private static final string
	 */
	_Super: function(caller) {
		f_core.Assert(caller && caller.callee, "f_class._Super: First parameter must be an arugments object !");
		
		var callee=caller.callee;

		var ksuper=callee._ksuper;
		if (!ksuper) {
			var name=callee._kname;
			var cls=callee._kclass;
			f_core.Assert(cls instanceof f_class, "_Super: Can not find class of object '"+caller+"'\n["+caller.callee+"'\nclass='"+cls+"'\nname='"+name+"' !");
	
			var p = cls._parent;
			f_core.Assert(p instanceof f_class, "_Super: No parent class ! (className='"+cls._name+", method='"+name+"')");
		
			if (callee._constructor) {
				for (;p && !ksuper;p = p._parent) {
					ksuper=p._constructor;
				}
			
			} else {
				for (;p && !ksuper;p = p._parent) {
					ksuper=p._kmethods[name];
				}
			}

			if (!ksuper) {
				throw new Error("Core._Super: No super method ! (className='"+cls._name+", method='"+name+"')");
			}

			callee._ksuper=ksuper;
		}
		
		var nargs=arguments.length;
		switch(nargs) {
		case 1:
			return ksuper.call(this);

		case 2:
			return ksuper.call(this, arguments[1]);
		}
		
		var a = f_core.PushArguments(null, arguments, 1);
	
		return ksuper.apply(this, a);
	},
	/**
	 * @method hidden static final
	 */
	InitializeStaticMembers: function(claz) {
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
			f_core.Assert(typeof(staticInitializer)=="function", "Invalid 'Initializer' field, it must be a function ! value="+staticInitializer);
			try {	
				staticInitializer.call(claz);
				
			} catch (x) {
				f_core.Error(f_class, "Initializer of aspect/class '"+claz._name+"' throws exception.", x);
			}
		}
	},
	/**
	 * @method hidden static final
	 */
	InitializeClass: function(claz) {
		if (claz._initialized) {
			return;
		}
		
		claz._initialized=true;
		
		var methods=new Object;
				
		var parent=claz._parent;
		if (parent) {
			f_class.InitializeClass(parent);
			
			var pms=parent._kmethods;
			for(var member in pms) {
				methods[member]=pms[member];
			}
		}
		
		// Les methodes
		var members=claz._members;
		if (members) {
			f_class._InitializeClassMembers(claz, methods);
		}
		methods.f_super = f_class._Super;
		
		var aspects=claz._aspects;
		if (aspects) {
			for(var i=0;i<aspects.length;i++) {
				f_class._InstallAspects(claz, aspects[i], methods);
			}
		}
		
		claz._kmethods = methods;
	},
	/**
	 * @method private static final
	 */
	_InitializeClassMembers: function(claz, methods) {
		var members=claz._members;
/*
		if (members instanceof _remapContext) {
			members=this._classLoader._remapContext(members);
			claz._members=members;
		}
	*/
		var className=claz._name;
		for (var memberName in members) {
			var member=members[memberName];

			var type=typeof(member);

			f_core.Assert(type!="undefined", "InitializeClassMembers: Type undefined for "+claz._name+"."+memberName);
			if (type=="undefined") {
				continue;
			}		

			f_core.Assert(type!="object", "InitializeClassMembers: Type Object for "+claz._name+"."+memberName);
		
			if (type=="function") {
				member._kname = memberName;
				member._kclass = claz;

				if (memberName==className) {
					claz._constructor=member;
					member._constructor=true;
					
					// Pas dans les methodes !
					continue;
				}
			}
			
			methods[memberName] = member;
		}
	},
	/**
	 * @method private static final
	 */
	_ManageAspects: function(callerArguments, callerThis) {
		var obj=callerArguments.callee;
			
		var params;
		if (callerArguments.length>0) {
			params = new Array;
			for (var i=0;i<callerArguments.length;i++) { 
				params.push(callerArguments[i]);
			}
		}
		
		var before=obj._kbefore;
		if (before) {
			for(var i=0;i<before.length;i++) {
				f_class._Call(callerThis, before[i], params);
			}
		}
		
		var ret=undefined;
		if (obj._kmethod) {
			try {
				f_class._Call(callerThis, obj._kmethod, params);
				
			} catch (x) {
				if (obj._kthrowing) {
					var throwing=obj._kthrowing;
					for(var i=0;i<throwing.length;i++) {
						throwing[i].call(callerThis, x, obj._kmethod, params);
					}					
				}
				
				throw x;
			}
		}
		
		var after=obj._kafter;
		if (after) {
			for(var i=0;i<after.length;i++) {
				f_class._Call(callerThis, after[i], params);
			}
		}
		
		return ret;
	},
	/**
	 * @method private static final
	 */
	_InstallAspects: function(claz, aspect, methods, abstracts) {
	
		// Gestion des aspects parents !
		var parents=aspect._parents;
		if (parents) {
			for(var i=0;i<parents.length;i++) {
				var p=parents[i];
				
				f_class._InstallAspects(claz, p, methods, abstracts);
			}
		}
	
		var members=aspect._members;
		aspect._initialized=true;
/*
		if (members instanceof _remapContext) {
			members=this._classLoader._remapContext(members);
			aspect._members=members;
		}
	*/	
		var constructor;
		var aspectName=aspect._name;
		for(var memberName in members) {		
			var member=members[memberName];			
					
			if (memberName==aspectName) {
				memberName=claz._name;
				constructor=true;
			} else {
				constructor=false;
			}

			if (typeof(member)=="object") {
				for(var mname in member) {
					if (mname==f_class.BEFORE_ASPECT || mname==f_class.AFTER_ASPECT || mname==f_class.THROWING_ASPECT) {
						var m2=member[mname];
						
						if (typeof(m2)=="function") {
							if (memberName=="__all__") {
								for(var i=0;i<methods.length;i++) {
									f_class._InstallAspectMethod(claz, methods, methods[i], mname, m2, constructor);
								}
							
								continue;
							}
								
							f_class._InstallAspectMethod(claz, methods, memberName, mname, m2, constructor);
							continue;
						}
						
						f_core.Assert(false, "Aspect: Bad function type '"+typeof(m2)+"' for member '"+mname+"' of aspect '"+aspect._name+"'.");
						continue;
					}
					
					f_core.Assert(false, "Aspect: Bad keyword '"+mname+"' defined in aspect '"+aspect._name+"'.");
				}
				
				continue;
			}

			if (member===f_class.ABSTRACT || member==f_class.OPTIONAL_ABSTRACT) {
				// méthode abstraite !
				if (abstracts) {
					abstracts.push(memberName);
				}
				continue;
			}

			if (typeof(member)=="function") {
				var type=null;

				if (memberName=="f_finalize") {
					type=f_class.BEFORE_ASPECT;

				} else if (memberName==claz._name) {
					type=f_class.AFTER_ASPECT;
				}
				
				if (type) {
					f_class._InstallAspectMethod(claz, methods, memberName, type, member, constructor);
					continue;
				}
			}
			
			if (methods[memberName]) {
				f_core.Assert(false, "Aspect: Already defined member '"+memberName+"' of aspect '"+aspect._name+"'.");
				continue;
			}
			
			methods[memberName]=member;
		}
	},
	/**
	 * @method private static final
	 */
	_CreateAspectMethod: function() {
		return function() {
			return f_class._ManageAspects(arguments, this);
		};
	},
	/**
	 * @method private static final
	 */
	_InstallAspectMethod: function(claz, methods, memberName, type, member, constructor) {
		var old=(constructor)?(claz._constructor):methods[memberName];
		
		// Il faut refaire une function a chaque niveau de classe !
		if (!old || !old._kmethod || old._kclass!=claz) {
			var f=f_class._CreateAspectMethod();
			f._kmethod=old;
			f._kname=memberName;
			f._kclass=claz;
			
			if (constructor) {
				claz._constructor=f;
			}
			
			old=f;
			methods[memberName]=f;
		}
		
		var l;
		switch(type) {
		case f_class.BEFORE_ASPECT:
			l=old._kbefore;
			if (!l) {
				l=old._kbefore=new Array;
			}
			break;
			
		case f_class.AFTER_ASPECT:
			l=old._kafter;
			if (!l) {
				l=old._kafter=new Array;
			}
			break;
			
		case f_class.THROWING_ASPECT:
			l=old._kthrowing;
			if (!l) {
				l=old._kthrowing=new Array;
			}
		}
		
		if (l) {
			l.push(member);
		}
	},
	/**
	 * @method private static final
	 */
	_Inherit: function(obj) {
		var cls=obj._kclass;
		f_core.Assert(cls, "f_class._Inherit: Class of object '"+obj+"' is null !");
		
		f_class.InitializeClass(cls)
		
		var methods=cls._kmethods;
		for (var fname in methods) {
			obj[fname] = methods[fname];
		}
		
//		alert("Super '"+cls._name+"' "+cls._ksupmethods);
//		obj.f_super=cls._ksupmethods;
	},
	/**
	 * @method hidden static final
	 */
	Init: function(obj, cls, args) {
		if (obj._kclass) {
			return obj;
		}
					
		// f_core.Profile("f_class.init.enter("+obj.id+" / "+cls._name+")");
					
		obj._kclass = cls;
		
		f_class._Inherit(obj);
		
		var constructor=undefined;
		for (var kls=cls;kls && !constructor;kls = kls._parent) {
			constructor=kls._constructor;
		}				
		
		f_core.Assert(typeof(constructor)=="function", "No constructor for class '"+cls._name+"'.");

		try {
			f_class._Call(obj, constructor, args);
		
		} catch (ex) {
			f_core.Error(f_class, "Call of constructor of '"+cls._name+"' throws exception ! (id='"+obj.id+"')", ex);
		
			throw ex;
		}
							
		cls._classLoader._newInstance(obj);
		
		// f_core.Profile("f_class.init.exit("+obj.id+" / "+cls._name+")");
		
		return obj;
	},
	/**
	 * @method hidden static final
	 */
	Clean: function(objs) {
		f_core.Assert(objs instanceof Array,"f_class.Clean: Invalid array of objects ("+objs+")");

		for(var i=0;i<objs.length;i++) {
			var obj=objs[i];
			if (!obj) {
				continue;
			}
			
			var cls=obj._kclass;
			f_core.Assert(cls instanceof f_class,"f_class.Clean: Not a class object ? ("+cls+")");
			
			try {
				obj.f_finalize();
				
			} catch (x) {
				f_core.Error(f_class, "Call of method f_finalize of class '"+cls+"' throws exception.", x);
			}
	
			// Desinherit		
			var methods=cls._kmethods;
			for (var fname in methods) {
				obj[fname] = undefined;
			}
			
			obj.f_super = undefined;
	
			obj._kclass = undefined;
			
			if (obj.tagName) {
				// Un composant du DOM !
				f_core.VerifyProperties(obj);
			}
		}
	},
	/**
	 * @method hidden static final 
	 */
	IsObjectInitialized: function(object) {
		f_core.Assert(object, "Object is NULL");

		return (object._kclass)?true:false;
	},
	_remapContext: function() {
	}
}
for(var p in __static) {
	f_class[p]=__static[p];
}

