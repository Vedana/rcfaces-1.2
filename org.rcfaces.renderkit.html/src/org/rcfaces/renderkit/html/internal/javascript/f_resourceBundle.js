/*
 * $Id$
 */
 
/**
 * @class public f_resourceBundle extends f_object
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 */

function f_resourceBundle(name) {
	this._name=name;
}


/**
 * @field private static 
 */
f_resourceBundle._Resources=undefined;

/**
 * @field private static 
 */
f_resourceBundle._Loading=undefined;
	
/**
 * Search a value associated to a property.
 *
 * @method public
 * @param string key Key of property.
 * @param optional any params Parameters which will be formatted into the string associated to the key.
 *                 The Nth parameter will replace the '{n}' substring. (First parameter: {0}; Second parameter {1} ...)
 * @return Object
 */
f_resourceBundle.prototype.f_get=function(key, params) {

	if (arguments.length<2) {
		return this.f_getParams(key);
	}
	
	var p=new Array;
		
	f_core.PushArguments(p, arguments, 1);
	
	return this.f_getParams(key, p);
}

/**
 * Search a value associated to a property.
 *
 * @method public
 * @param string key Key of property.
 * @param optional any[] params Parameters which will be formatted into the string associated to the key.
 *                 The Nth element in the array will replace the '{n}' substring. (First element: {0}; Second element {1} ...)
 * @return Object
 */
f_resourceBundle.prototype.f_getParams=function(key, params, defaultValue) {
	f_core.Assert(typeof(key)=="string", "Key must be a string !");
 	f_core.Assert(params==null || (params instanceof Array), "Params parameter is invalid. (["+typeof(params)+"] "+params+").");
	
	var properties=this._properties;
	if (!properties) {
		if (defaultValue!==undefined) {
			return defaultValue;
		}
		f_core.Error(f_resourceBundle, "No keys for resourceBundle '"+this._name+"'.");
		return "??"+key+"??";
	}
	
	var message=properties[key];
	if (message===undefined) {
		if (defaultValue!==undefined) {
			return defaultValue;
		}
		f_core.Error(f_resourceBundle, "Unknown key '"+key+"' for resourceBundle '"+this._name+"'.");
		return "??"+key+"??";
	}
	
	f_core.Debug(f_resourceBundle, "Format '"+message+"' with '"+params+"'.");
	
	if (!params) {
		return message;
	}
	
	return f_core.FormatMessage(message, params);
}
	
f_resourceBundle.prototype._putAll=function(values) {		
	var properties=new Object();
	this._properties=properties;
	
	for(var name in values) {
		properties[name]=values[name];
	}
}

/**
 * @method public static final
 * @param string name Name of resourceBundle. (can be a f_class !)
 * @param hidden boolean create Create ResourceBundle if not found !
 * @return f_resourceBundle
 */
f_resourceBundle.Get=function(name, create) {
	f_core.Assert(name, "f_resourceBundle.Get: Name parameter is invalid: "+name);

	if ((name instanceof f_class) || (name instanceof f_aspect) || typeof(name.f_getName)=="function") {
		name=name.f_getName();
	}
	
	f_core.Assert(typeof(name)=="string", "Name of resourceBundle must be a string or f_class ! ("+name+")");

	if (!name) {
		return null;
	}

	var resources=f_resourceBundle._Resources;
	if (!resources) {
		resources=new Object();
		f_resourceBundle._Resources=resources;
	}

	var resource=resources[name];
	if (resource) {
		return resource;
	}
	
	if (!create) {
		f_core.Debug(f_resourceBundle, "Can not find resourceBundle '"+name+"'.");
		return null;
	}
	
	resource=new f_resourceBundle(name);
	resources[name]=resource;
	return resource;
}

/**
 * @method static final hidden
 * @param string name Name of resourceBundle. (can be a f_class !)
 * @param Object values
 * @return void
 */
f_resourceBundle.Define=function(name, values) {
	var resourceBundle=f_resourceBundle.Get(name, true);
	
	f_core.Debug(f_resourceBundle, "Define resourceBundle for '"+resourceBundle._name+"' with values '"+values+"'.");
	
	resourceBundle._putAll(values);
}


/**
 * @method static final hidden
 * @param string name Name of baseName of a previous request.
 * @param Object values
 * @return void
 */
f_resourceBundle.DefineLoaded=function(baseName, values) {
	f_core.Assert(f_resourceBundle._Loading, "Resource bundle base='"+name+"' is not requested !");

	var loading=f_resourceBundle._Loading;

	var bundleName=loading[baseName];
	f_core.Assert(bundleName, "Resource bundle bundleName='"+bundleName+"' is not known !");

	loading[baseName]=undefined;

	f_core.Debug(f_resourceBundle, "Loaded: baseName='"+baseName+"' bundleName='"+bundleName+"' values="+values);
		
	f_resourceBundle.Define(bundleName, values);
}

/**
 * @method static final hidden
 * @param string bunddleName Name of resourceBundle.
 * @param string baseName
 * @return void
 */
f_resourceBundle.Load=function(bundleName, baseName, url) {
	f_core.Assert(f_resourceBundle.Get(bundleName)==null, "Resource bundle '"+bundleName+"' is already defined !");

	var loading=f_resourceBundle._Loading;
	if (!loading) {
		loading=new Array;
		f_resourceBundle._Loading=loading;
	}

	f_core.Debug(f_resourceBundle, "Load bundleName='"+bundleName+"' baseName='"+baseName+"' located at url '"+url+"'.");

	loading[baseName]=bundleName;
	
	document.write("<SCRIPT type=\"text/javascript\" charset=\"UTF-8\" src=\""+url+"\"></SCRIPT>");
}

f_resourceBundle.Finalizer=function() {
	f_resourceBundle._Resources=undefined;
}

f_resourceBundle.f_getName=function() {
	return "f_resourceBundle";
}
