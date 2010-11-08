/*
 * $Id$
 */

/**
 *
 * @class hidden f_serverClientStorage extends f_clientStorage
 * @version $Revision$ $Date$
 */

var __statics = {
		
	/**
	 * @field private static final Number
	 */
	_STORAGE_MAX_SIZE: -1,
	
	/**
	 * @method hidden static
	 * @return boolean
	 */
	IsSupported: function() {
		return true;
	}
}

var __members = {
	f_getStorageType: function() {
		return f_clientStorage.SERVER_STORAGE_TYPE;
	},
	f_getStorageMaxSize: function() {
		return f_serverClientStorage._STORAGE_MAX_SIZE;
	},
	f_get: function(name) {
		f_core.Assert(typeof(name)=="string", "Invalid name parameter ("+name+")");

			
		return null;
	},
	f_set: function(name, value) {
		f_core.Assert(typeof(name)=="string", "Invalid name parameter ("+name+")");
		f_core.Assert(value===null || typeof(value)=="string", "Invalid value parameter ("+value+")");
	
				
		return null;
	}
}

new f_class("f_serverClientStorage", null, __statics, __members, f_clientStorage);
