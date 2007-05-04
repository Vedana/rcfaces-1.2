/*
 * $Id$
 */
 
/**
 * Aspect FilterProperties
 *
 * @aspect fa_filterProperties
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {
	fa_filterProperties: function() {
		if (f_core.GetAttribute(this, "v:filtred")) {
			this._filtred=true;
		}
		
		var filterExpression=f_core.GetAttribute(this, "v:filterExpression");
		if (filterExpression) {
			this._filterProperties=f_core.DecodeObject(filterExpression);
		}
	},
	/*
	f_finalize:  function() {
		// this._filtred=undefined; // boolean
		// this._filterProperties=undefined; // Map<string, string>
	},
	*/
	
	/**
	 * Returns filter properties.
	 * 
	 * @method public
	 * @return Object
	 */
	f_getFilterProperties: function() {
		// On copie les propriétés !
		var ret=new Object;
		var properties=this._filterProperties;
		if (!properties) {
			return ret;
		}
		
		for(var name in properties) {
			ret[name]=properties[name];
		}
		
		return ret;
	},
	/**
	 * Specify some properties of a filter expression.
	 * 
	 * @method public
	 * @param String name1
	 * @param String value1
	 * @param String... name2
	 * @return void
	 */
	f_setFilterProperty: function(name1, value1, name2) {
		var properties=this._filterProperties;
		if (!properties) {
			properties=new Object;
		}
		
		for(var i=0;i<arguments.length;) {
			var name=arguments[i++];
			var value=arguments[i++];
			
			properties[name]=value;
		}
		
		this.f_setFilterProperties(properties);
	},
	/**
	 * Specify the filter expression.
	 * 
	 * @method public
	 * @param Object properties
	 * @return void
	 */
	f_setFilterProperties: function(properties) {
		f_core.Assert(this._filtred, "fa_filterProperties.f_setFilterProperties: This component does not support filter properties !");

		f_core.Assert(typeof(properties)=="object", "fa_filterProperties.f_setFilterProperties: Filter properties must be an Object or null !");
	
		var expression="";
		if (properties) {
			expression=f_core.EncodeObject(properties);
		}
	
		//if (this._filterExpression==expression) {
			// return;
			// NON: Car il peut y avoir une mise à jour !
		//}
		
		var myProps=new Object;
		if (properties) {
			for(var name in properties) {
				myProps[name]=properties[name];
			}
		}
		
		this._filterProperties=myProps;
		this.f_setProperty(f_prop.FILTER_EXPRESSION, expression);
		
		this.fa_updateFilterProperties(myProps);
	},
	
	/**
	 * @method hidden abstract
	 * @param String message
	 * @return void
	 */
	fa_cancelFilterRequest: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_updateFilterProperties: f_class.ABSTRACT
			
}

new f_aspect("fa_filterProperties", null, __prototype);

