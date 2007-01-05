/*
 * $Id$
 */
 
/**
 * Aspect primary object.
 *
 * @class public f_aspect extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
function f_aspect(aspectName, staticMembers, members) {
	// Constructeur vide: on ne fait rien !
	if (!arguments.length) {
		return;
	}

	if (arguments[0] instanceof f_classLoader) {
		this._newMultiWindow(arguments);		
		return;
	}

	this._name=aspectName;
	this._members=members;
	this._staticMembers=staticMembers;
	this._classLoader=window._classLoader;

	var parents;
	if (arguments.length>3) {
		parents=f_core.PushArguments(null, arguments, 3);
		
		if (f_core.IsDebugEnabled("f_aspect")) {
			for(var i=0;i<parents.length;i++) {
				var parent=parents[i];
				f_core.Assert(parent instanceof f_aspect, "Parent of aspect must be an aspect. (parent="+parent+").");
			}
		}		
	}
	this._parents=parents;
	
	this._classLoader._declareAspect(this);
}
f_aspect.prototype = {
	/**
	 * @method public
	 * @return String
	 */
	f_getName: function() {
		return this._name;
	},
	/**
	 * @method public
	 * @return f_classLoader
	 */
	f_getClassLoader: function() {
		return this._classLoader;
	},
	/**
	 * Returns all aspects extended by this aspect.
	 *
	 * @method public final
	 * @return f_aspect[]
	 */
	f_getAspects: function() {
		if (!this._parents) {
			this._parents=new Array;
		}
		return this._parents;
	},	
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[Aspect "+this._name+"]";
	}
}

/** 
 * Returns class name.
 *
 * @method public static 
 * @return String class name.
 */
f_aspect.f_getName=function() {
	return "f_aspect";
}

