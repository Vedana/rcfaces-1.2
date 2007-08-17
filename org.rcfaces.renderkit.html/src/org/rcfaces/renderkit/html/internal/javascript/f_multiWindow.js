/*
 * $Id$
 */
 
/**
 * Divers Add'ons afin de pouvoir gérer le multiWindow.
 * 
 * @TODO traiter les functions en chaines de caracteres ...
 * @class f_classLoader
 */
 
/* f_class ***************************************************************************/
if (f_core.IsGecko()) {
	// le cas de Gecko, on peut utiliser windows.eval(x) .

	/**
	 * @method static hidden
	 */
	f_class._ChangeContext = function(win, mwMethod) {
		if (typeof(mwMethod)=="function") {
			mwMethod=mwMethod.toSource();
			
		} else if (mwMethod instanceof f_class._functionSource) {
			mwMethod=mwMethod._source;
			
		} else {
			f_core.Assert(false, "Unknown method type "+mwMethod+".");
		}
		return win.eval(mwMethod);
	}
} else {
	// Autre cas, on passe par une méthode de changement de context !

	/**
	 * @method static hidden
	 */
	f_class._ChangeContext = function(win, mwMethod) {
		var changeContext=win._rcfacesChangeContext;
		if (typeof(mwMethod)=="function") {
			mwMethod=mwMethod.valueOf();
			
		} else if (mwMethod instanceof f_class._functionSource) {
			mwMethod=mwMethod._source;
			
		} else {
			f_core.Assert(false, "Unknown method type "+mwMethod+".");
		}
	
	 	return changeContext(mwMethod);
	}
}
 
/* f_classLoader ***************************************************************************/

/**
 * @method static hidden
 */
f_classLoader.prototype._newWindow=function(clparent, changeContext) {
	var win=this;
	
	if (f_core.IsInternetExplorer()) {
		win._rcfacesChangeContext=changeContext;
	}

	var cl=new f_classLoader(win, clparent);
	win._rcfacesClassLoader=cl;
	
	cl.f_requiresBundle(win.document, clparent._mainBundleName);
}

_classLoader._mwDeclareBundle=f_classLoader.prototype._declareBundle;
f_classLoader.prototype._declareBundle=function(bundle, win) {
	if (!win || win==window) {
		return this._mwDeclareBundle(bundle);
	}
	
	this._addMwBundle(bundle);
}
	
f_classLoader.prototype._addMwBundle=function(bundle) {
	// OK c'est une window enfant !
	var name=bundle._name;
	
	var mwBundles=this._mwBundles;
	if (!mwBundles) {
		mwBundles=new Array;
		this._mwBundles=mwBundles;
	}
	
	var bd=mwBundles[name];
	if (bd) {
		alert("Already defined ! "+name);
		return;
	}
	
	var classes=bundle.f_listClasses();
	
	var list=new Object;
	this._mwBundles[name]=list;
		
	for(var i=0;i<classes.length;i++) {
		var cl=classes[i];
		var name=cl.f_getName();
		
		var mwcl=new Object;
		list[name]=mwcl;
		
		if (!cl._classLoader) {
			mwcl._object=true;
		
			mwcl._staticConstants=new Object;
			mwcl._staticMembers=this._mwToSource(cl, mwcl._staticConstants);
//	alert("["+name+"] No classLoader="+mwcl._staticMembers);
			
			var proto=cl.prototype;
			if (proto) {
				mwcl._members=this._mwToSource(proto);
				alert("["+name+"] PROTO="+mwcl._members);
			}
			
			continue;
		}
		
		if (cl instanceof f_aspect) {
			mwcl._aspect=true;
		}
		
		var staticMembers=cl._staticMembers;
		if (staticMembers) {
			mwcl._staticConstants=new Object;
			mwcl._staticMembers=this._mwToSource(staticMembers, mwcl._staticConstants);
//			alert("["+name+"] SM="+mwcl._staticMembers);
		}

		var members=cl._members;
		if (members) {
			mwcl._membersConstants=new Object;
			mwcl._members=this._mwToSource(members, mwcl._membersConstants);
//			alert("["+name+"] M="+mwcl._members);
		}
	}
	
	return list;
}
_classLoader._mwToSource=function(members, constants) {
	var s;
	for(var i in members) {
		var member=members[i];
		
		switch(typeof(member)) {
		case "number":
		case "boolean":
		case "undefined":
		case "string":
			if (!constants) {
				alert("No constant ? "+i);
			}
			constants[i]=member;
			break;
			
		default:
			if (member==null) {
				alert("No constant ? "+i);
				constants[i]=null;
				break;
			}
			if (member instanceof RegExp) {
				alert("No constant ? "+i);
				constants[i]=member;
				break;
			}
		
			if (!s) {
				s="{";
			}
			s+=i+":"+member.toSource()+",\n";
		}		
	}
	
	if (s) {
		s+="}";
	}
	
	return s;
}

_classLoader._mwLoadBundle=f_classLoader.prototype._loadBundle;
f_classLoader.prototype._loadBundle=function(doc, bundleName) {
	if (!doc || doc==document) {
		return this._mwLoadBundle(doc, bundleName);
	}
	
	// OK c'est une window enfant !

	var bd=null;

	var mwBundles=this._mwBundles;
	if (mwBundles) {
		bd=mwBundles[bundleName];
	}

	if (!bd) {
		var parentBD=this._bundles[bundleName];
		if (parentBD) {
			bd=this._addMwBundle(parentBD);
		}
	}

	if (!bd) {
		alert("Can not find bundle "+bundleName+"=>"+bd);
		return;
	}
	
	var win;
	if (doc.defaultView) { // DOM Level 2
		win=doc.defaultView;

	} else {
		win=doc.parentWindow;
	}
	
	// Notre méthode de changement de contexte !
	var changeContext=win._rcfacesChangeContext;
	if (!changeContext) {
		changeContext=function(source) {
			return win.eval(source);
		}
	}
	
	for(var cls in bd) {
		var cl=bd[cls];
		
		if (cl._object) {
			// Un objet du style f_core !
			
			// On construit l'objet.
			var newObject=changeContext("new Object");
			var cts=cl._staticConstants;
			if (cts) {
				for(var name in cts) {
					newObject[name]=cts[name];
				}
			}
			
			// On spécifie les 
			var mts=cl._staticMembers;
			if (mts) {
				var newMts=changeContext(mts);
				
				for(var name in newMts) {
					newObject[name]=newMts[name];
				}				
			}

			win[cls]=newObject;

			var protos=cl._members;
			if (protos) {
				changeContext(cls+".prototype="+protos);
			}
			
			continue;
		}
	}
}
