/*
 * $Id$
 */


/**
 * class f_bundle
 *
 * @class hidden f_bundle extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	f_bundle: function(name)  {
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
	
		var classLoader=f_classLoader.Get();
		f_core.Assert(classLoader, "Bundle '"+name+"' can not get window classloader !");
		
		this._classLoader=classLoader;
		
		classLoader._declareBundle(this);
	},
	
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

new f_class("f_bundle", {
	members: __members
});


