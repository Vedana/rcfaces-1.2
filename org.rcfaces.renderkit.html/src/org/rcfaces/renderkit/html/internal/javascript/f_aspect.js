/*
 * $Id$
 */
 
/**
 *
 * Moteur des aspects !
 *
 * @class public f_aspect
 * @author Olivier Oeuillot
 * @version $Revision$
 */
function f_aspect(aspectName, staticMembers, members) {
	// Constructeur vide: on ne fait rien !
	if (arguments.length==0) {
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

	if (arguments.length>3) {
		var parents=new Array;		
		this._parents=parents;
		
		f_core.PushArguments(parents, arguments, 3);
		
		if (f_core.IsDebugEnabled("f_aspect")) {
			for(var i=0;i<parents.length;i++) {
				var parent=parents[i];
				f_core.Assert(parent instanceof f_aspect, "Parent of aspect must be an aspect. (parent="+parent+").");
			}
		}		
	}
	
	this._classLoader._declareAspect(this);
}
f_aspect.prototype.f_getName=function() {
	return this._name;
}
f_aspect.prototype.f_getClassLoader=function() {
	return this._classLoader;
}
f_aspect.prototype.toString=function() {
	return "[Aspect "+this._name+"]";
}
f_aspect.f_getName=function() {
	return "f_aspect";
}

