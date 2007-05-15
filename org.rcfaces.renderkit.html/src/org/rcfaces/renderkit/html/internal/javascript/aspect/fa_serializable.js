/*
 * $Id$
 */
 
/**
 * Aspect serializable. 
 *
 * @aspect hidden fa_serializable
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {


	f_finalize: function() {
		this._properties = undefined;  // Map<string, Object>
//		this._noPropertyUpdates=undefined; // boolean
	},

	/** 
	 * @method hidden
	 */
	f_ignorePropertyChanges: function() {
		this._noPropertyUpdates=true;
	},
	/**
	 * 
	 * @method protected
	 * @return String
	 */
	f_getProperty: function(name) {
		if (!this._properties) {
			return undefined;
		}
		
		return this._properties[name];
	},
	/**
	 * 
	 * @method protected
	 * @return void
	 */
	f_setProperty: function(name, value, isList, listSep) {
		if (this.fa_componentUpdated===false || this._noPropertyUpdates) {
			f_core.Debug(fa_serializable, "f_setProperty: Ignore set property (component is not updated !)");
			return;
		}

		if (isList) {		
			if (!value || !value.length) {
				f_core.Debug(fa_serializable, "f_setProperty: No values to set for property '"+name+"'. (value='"+value+"')");
				return;
			}
			
			var values="";
			if (!listSep) {
				listSep='\x01';
			}
			
			if (value instanceof Array) {
				for (var i=0;i<value.length;i++) {
					if (values) {
						values += listSep;
					}
					values += value[i];
				}
			} else {
				for (var v in value) {
					if (values) {
						values += listSep;
					}
					values += v;
				}
			}
		
			if (!values) {
				f_core.Debug(fa_serializable, "f_setProperty: No values to set for property '"+name+"'. (value='"+value+"')");
				return;
			}
			
			value=values;
		}
		
		f_core.Assert(typeof(value)=="string" 
			|| typeof(value)=="number" 
			|| typeof(value)=="boolean" 
			|| (value instanceof Date)
			|| (f_class.IsClassDefined("f_time") && (value instanceof f_time))
			|| value===null || value===undefined, "Invalid value '"+value+"'.");
		
		var properties=this._properties;
		if (!properties) {
			if (value===undefined) {
				return;
			}
			
			properties=new Object;
			this._properties = properties;

			f_core.Debug(fa_serializable, "f_setProperty: Create property map for object '"+this.id+"'.");
		}
		
		f_core.Info(fa_serializable, "f_setProperty: Set property '"+name+"' to '"+value+"' (type='"+typeof(value)+"').");
		
		if (this._kclass._classLoader._serializing) {
			// Pas d'evenement dans ce cas !
			
			properties[name] = value;
			return;
		}
		
		var oldValue=properties[name];
		
		properties[name] = value;
		
		if (this.f_performPropertyChange) {			
			this.f_performPropertyChange(name, value, oldValue);
		}
	},
	/**
	 *
	 *
	 * @method hidden
	 * @return String Serialized form.
	 */
	f_serialize0: function() {
		if (this.f_serialize) {
			this.f_serialize();
		}
		
		var p = this._properties;
		if (!p) {	
			return null;
		}

		return f_core.EncodeObject(p, ",");
	},
	
	/**
	 * @method protected abstract optional
	 */
	fa_componentUpdated: f_class.OPTIONAL_ABSTRACT,
	
	/**
	 * @method protected abstract optional
	 */
	f_performPropertyChange: f_class.OPTIONAL_ABSTRACT,
	
	/**
	 * @method protected abstract optional
	 */
	f_serialize: f_class.OPTIONAL_ABSTRACT
}

new f_aspect("fa_serializable", null, __prototype);
