/*
 * $Id$
 */


/**
 * class f_bundle
 *
 * @class public f_bundle extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
function f_bundle(name)  {
	this._name=name;

	var names=new Array;
	var classes=new Array;
	this._classes=classes;
	
	for(var i=1;i<arguments.length;i++) {
		var clazz=arguments[i];
		classes.push(clazz);
		
		f_core.Assert(clazz.f_getName, "The class '"+clazz+"' ["+i+"] has not defined f_getName method ! ("+name+")");
		
		names.push(clazz.f_getName());
	}
	
	f_core.Info("f_bundle", "Bundle '"+name+"' declares classes: "+names);

	var classLoader=window._classLoader;
	f_core.Assert(classLoader, "Bundle '"+name+"' can not get window classloader !");
	
	this._classLoader=classLoader;
	
	classLoader._declareBundle(this);
}

f_bundle.prototype = {
	f_finalize: function() {
		this._classLoader=undefined; // f_classLoader
		this._classes=undefined; // f_class[]
	},
	
	/**
	 * @method public
	 * @return f_class[]
	 */
	f_listClasses: function() {
		return this._classes;
	},
	
	/**
	 * @method public
	 * @return String
	 */
	f_getName: function() {
		return this._name;
	}
}

f_bundle.f_getName=function() {
	return "f_bundle";
}


