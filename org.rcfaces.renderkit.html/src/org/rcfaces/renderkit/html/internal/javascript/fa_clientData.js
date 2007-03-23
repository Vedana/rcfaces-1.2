/*
 * $Id$
 */
 
/**
 * Aspect ClientData
 *
 * @aspect fa_clientData
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field private static final String
	 */
	_REMOVED_PROPERTY: "removed",
	
	/**
	 * @field private static final String
	 */
	_CHANGED_PROPERTY: "changed",

	/**
	 * @method hidden static final
	 * @param f_component component
	 * @param optional HTMLElement Node which defines v_data attribute.
	 * @return Object
	 */
	InitializeDataAttribute: function(component, node) {
		if (!node) {
			node=component;
		}
		
		var clientData=new Object;
		component._clientDatas=clientData;
		
		var att=f_core.GetAttribute(node, "v:data");
		if (!att) {
			return clientData;
		}
		
		var ds=att.split(",");
		for(var i=0;i<ds.length;i++) {
			var d=ds[i];
			var vname=d;
			var value="";
			
			var p=d.indexOf("=");
			if (p>=0) {
				vname=d.substring(0, p).replace(/\+/g, " ");
				vname=decodeURIComponent(vname);
				
				value=d.substring(p+1);
				value=value.replace(/\+/g," ");
				value=decodeURIComponent(value);
			}
								
			clientData[vname]=value;
		}

		return clientData;
	}
}
var __prototype = {
/*
	f_finalize: function() {
		this._clientDatas=undefined;  // Map<string, string>
		this._modifiedDatas=undefined;  // Map<string, string>			 
		this._newDatas=undefined; // Map<string, string>
	},
	*/
	
	/**
	 * 
	 * @method public
	 * @param String name Name of property
	 * @return String
	 */
	f_getClientData: function(name) {
		f_core.Assert(typeof(name)=="string", "Name of clientData must be a string !");

		var clientData=this._clientDatas;
		if (clientData===undefined) {
			clientData=fa_clientData.InitializeDataAttribute(this);
		}
		
		return clientData[name];
	},
	/**
	 * 
	 * @method public
	 * @return Object 
	 */
	f_getClientDataSet: function() {
		if (this._clientDatas===undefined) {
			this.f_getClientData("");
		}
		
		var clientData=this._clientDatas;

		var obj=new Object();
		for(var name in clientData) {
			obj[name]=clientData[name];
		}		
		
		return obj;
	},
	/**
	 * 
	 * @method public
	 * @param String name1
	 * @param optional String value1
	 * @param optional String... name2
	 * @return void
	 */
	f_setClientData: function(name1, value1, name2) {
		if (this._clientDatas===undefined) {
			this.f_getClientData("");
		}
		
		var data=this._clientDatas;
		
		if (!this.fa_componentUpdated) {
			for (var i=0;i<arguments.length;) {
				var name=arguments[i++];
				var data=arguments[i++];

				data[name]=value;
			}
			return;
		}
		
		
		var modifiedData=this._modifiedDatas;
		var newData=this._newDatas;
		
		if (!modifiedData) {
			modifiedData=new Object;
			this._modifiedDatas=modifiedData;

			newData=new Object;
			this._newDatas=newData;
		}
		
		for (var i=0;i<arguments.length;) {
			var name=arguments[i++];
			f_core.Assert(typeof(name)=="string", "Name of clientData must be a string !");

			var value;
			
			if (i<arguments.length) {
				value=arguments[i++];
				
				f_core.Assert(typeof(value)=="string" || !value, "Value of clientData must be a string or null !");
			}
			
			if (!data[name] && !modifiedData[name] && !newData[name]) {
				// C'est un nouveau !
				newData[name]=true;
			}
			
			if (!value) {
				delete data[name];
				
				if (newData[name]) {
					// C'est un nouveau ... on efface seulement la propriété "modifié" !
					delete modifiedData[name];
					continue;
				}
					
				modifiedData[name]=fa_clientData._REMOVED_PROPERTY;
				continue;
			}
			
			modifiedData[name]=fa_clientData._CHANGED_PROPERTY;
			data[name] = value;
		}
	},
	f_serialize: {
		after: function() {
			var modifiedData=this._modifiedDatas;
			if (!modifiedData) {
				return;
			}
			
			var v=new Array;
			
			var data=this._clientDatas;
			for(var name in modifiedData) {				
				var type=modifiedData[name];
				if (type==fa_clientData._REMOVED_PROPERTY) {
					v.push("R", name);
					continue;
				}

				var value=data[name];
				v.push("S", name, value);
			}
			
			if (v.length<1) {
				return;
			}
			
			this.f_setProperty(f_prop.DATA, v, true);
		}
	}
}

new f_aspect("fa_clientData", __static, __prototype);
