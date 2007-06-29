/*
 * $Id$
 */

/**
 * 
 * @class public f_flashClientStorage extends f_clientStorage
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	
	/**
	 * @field private static final number
	 */
	_STORAGE_MAX_SIZE: 102400,
	
	/**
	 * @method hidden static
	 */
	IsSupported: function() {			
		return f_flashObject.RequiresVersion(6);
	}
}

var __members = {
	f_getStorageType: function() {
		return f_clientStorage.FLASH_STORAGE_TYPE;
	},
	f_getStorageMaxSize: function() {
		return f_flashClientStorage._STORAGE_MAX_SIZE;
	},
	f_get: function(name) {
		f_core.Assert(typeof(name)=="string", "Invalid name parameter ("+name+")");

	},
	f_set: function(name, value) {
		f_core.Assert(typeof(name)=="string", "Invalid name parameter ("+name+")");
		f_core.Assert(value===null || typeof(value)=="string", "Invalid value parameter ("+value+")");
	
	}
}

new f_class("f_flashClientStorage", {
	extend: f_clientStorage,
	statics: __statics, 
	members: __members
});