/*
 * $Id$
 */

/**
 *
 * @class public f_clientStorage extends f_object
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field public static final number
	 */
	COOKIE_STORAGE_TYPE: 1,
	
	/**
	 * @field public static final number
	 */
	FLASH_STORAGE_TYPE: 2,
	
	/**
	 * @field public static final number
	 */
	USER_DATA_STORAGE_TYPE: 4,
	
	/**
	 * @field public static final number
	 */
	DOM_STORAGE_TYPE: 8,
	
	/**
	 * @field public static final number
	 */
	SERVER_STORAGE_STYPE: 16,
	
	/**
	 * @field private static f_clientStorage
	 */
	_Storage: undefined,
	
	/**
	 * @field private static f_clientStorage[]
	 */
	_Storages: undefined,

	/**
	 * @method public static
	 * @param optional number... types
	 * @return f_clientStorage
	 */
	Get: function(types) {
		var storages=f_clientStorage._Storages;
		if (!storages) {
			storages=new Object;
			f_clientStorage._Storages=storages;
		}
		
		// On accepte aucun parametre !
		for(var i=0;i<arguments.length || i==0;i++) {
			var type=arguments[i]; // Le premier parametre peut-etre Undefined !
			
			if (!type) {
				var storage=f_clientStorage._Storage;
				if (storage) {
					return storage;
				}
	
				type=-1;
	
			} else {
				var storage=storages[type];
				if (storage) {
					return storage;
				}
			}
			
			var storage=f_clientStorage._SearchStorage(storages, type);
					
			if (!storage) {
				continue;
			}
			
			if (!type) {
				f_clientStorage._Storage=storage;
			}
	
			storages[storage.f_getStorageType()]=storage;
			return storage;
		}
		
		return null;
	},
	_SearchStorage: function(storages, type) {
		if ((type & f_clientStorage.DOM_STORAGE_TYPE) && f_class.IsClassDefined("f_domClientStorage"))  {
			if (storages) {
				var storage=storages[f_clientStorage.DOM_STORAGE_TYPE];
				if (storage) {
					return storage;
				}
			}
		
			if (f_domClientStorage.IsSupported()) {
				return f_domClientStorage.f_newInstance();
			}
		}
		
		if ((type & f_clientStorage.USER_DATA_STORAGE_TYPE) && f_class.IsClassDefined("f_userDataClientStorage")) {
			if (storages) {
				var storage=storages[f_clientStorage.USER_DATA_STORAGE_TYPE];
				if (storage) {
					return storage;
				}
			}

			if (f_userDataClientStorage.IsSupported()) {
				return f_userDataClientStorage.f_newInstance();
			}
		}
		
		if ((type & f_clientStorage.FLASH_STORAGE_TYPE) && f_class.IsClassDefined("f_flashClientStorage")) {
			if (storages) {
				var storage=storages[f_clientStorage.FLASH_STORAGE_TYPE];
				if (storage) {
					return storage;
				}
			}
			
			if (f_flashClientStorage.IsSupported()) {
				return f_flashClientStorage.f_newInstance();
			}
		}
		
		if ((type & f_clientStorage.COOKIE_STORAGE_TYPE) && f_class.IsClassDefined("f_cookieClientStorage")) {
			if (storages) {
				var storage=storages[f_clientStorage.COOKIE_STORAGE_TYPE];
				if (storage) {
					return storage;
				}
			}
			
			if (f_cookieClientStorage.IsSupported()) {
				return f_cookieClientStorage.f_newInstance();
			}
		}

		
		if ((type & f_clientStorage.SERVER_STORAGE_TYPE) && f_class.IsClassDefined("f_serverClientStorage")) {
			if (storages) {
				var storage=storages[f_clientStorage.SERVER_STORAGE_TYPE];
				if (storage) {
					return storage;
				}
			}
			
			if (f_serverClientStorage.IsSupported()) {
				return f_serverClientStorage.f_newInstance();
			}
		}
		
		return null;
	},
	Finalizer: function() {
		f_clientStorage._Storage=undefined;
		f_clientStorage._Storages=undefined;
	}
}

var __members = {
	/**
	 * @method public abstract
	 * @return number
	 * @see #f_clientStorage.COOKIE_STORAGE_TYPE
	 * @see #f_clientStorage.FLASH_STORAGE_TYPE
	 * @see #f_clientStorage.USER_DATA_STORAGE_TYPE
	 * @see #f_clientStorage.DOM_STORAGE_TYPE
	 */
	f_getStorageType: f_class.ABSTRACT,
	
	/**
	 * @method public abstract
	 * @param String name Name of the property
	 * @return String value of the property.
	 */
	f_get: f_class.ABSTRACT,
	
	/**
	 * @method public abstract
	 * @param String name Name of the property
	 * @param optional String value Value of the property.
	 * @return String Old value of the property.
	 */
	f_set: f_class.ABSTRACT,
	
	/**
	 * @method public abstract
	 * @return number Size or (-1 if unknown)
	 */
	f_getStorageMaxSize: f_class.ABSTRACT
}


new f_class("f_clientStorage", {
	extend: f_object,
	statics: __statics,
	members: __members
});
