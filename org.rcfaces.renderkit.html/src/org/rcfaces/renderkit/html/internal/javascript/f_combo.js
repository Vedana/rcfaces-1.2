/* $Id$
 *
 * $Log$
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:26  oeuillot
 * Renommage  en rcfaces
 *
 */


/**
 * 
 * @class f_combo extends f_input, fa_required, fa_filterProperties, fa_commands, fa_selectionProvider
 * @author  Joel Merlin & Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __static = {

	/**
	 * @field private static final number
	 */
	_MIN_WIDTH: 128
}

var __prototype = {

	f_combo: function() {
		this.f_super(arguments);
		
		this._className=this.className;
	},

	f_finalize: function() {
//		this._loading=undefined; // boolean
		this._waiting=undefined; // f_waiting
//		this._oldWidth=undefined; // string
//		this._className=undefined; // string

		// Pour l'instant il n'y a pas de variables attachées aux OPTIONs
	
		this.f_super(arguments);
	},
	f_update: function() {
		if (f_core.GetAttribute(this, "v:noSelection")) {
			this.selectedIndex = -1;
		}
		
		this.f_super(arguments);
	},
	f_initializeInput: function() {
		return this;
	},
	/**
	 * @method public 
	 * @param string value
	 * @return Object The item object or <code>null</code> if the item is not found.
	 */
	f_getItemByValue: function(value) {
		if (value.nodeType==1 && value.tagName=="OPTION") {
			return value;
		}
		
		var items = this.options;
		for (var i=0; i<items.length; i++) {
			var item = items[i];
			if (item.value!=value) {
				continue;
			}
			
			return item;
		}
		return null;
	},
	/**
	 * @method public 
	 * @param hidden boolean byIndex Returns the index of the item if setted to <code>true</code>.
	 * @return string Value, or index of the item.
	 */
	f_getValue: function(byIndex) {
		var items = this.options;
		for (var i=0; i<items.length; i++) {
			var item = items[i];
			if (!item.selected) {
				continue;
			}
			
			return (byIndex? i:item.value);
		}
		return (byIndex? -1:null);
	},
	/**
	 * @method public 
	 * @param string val Value associated to an item. (if val is a number, val specifies the index of the item)
	 * @param hidden boolean byIndex Select the item by index instead of value.
	 * @param hidden boolean deselectOther Deselect not specified items.
	 * @return boolean True is success. (Item has been found !)
	 */
	f_setValue: function(val, byIndex, deselectOther) {
		var items = this.options;
		
		if (byIndex) {
			f_core.Assert(typeof(val)=="number", "f_combo.f_setValue: val parameter is not a number !");
			f_core.Assert(val>=0 && val<items.length, "f_combo.f_setValue: Number is out of bounds 0<="+val+"<"+items.length+" !");

			if (deselectOther) {
				for (var i=0; i<items.length; i++) {
					var item = items[i];
					if (!item.selected || i==byIndex) {
						continue;
					}
					
					item.selected=false;
				}
			}
		
			items[val].selected = true;
			return true;
		}
		
		var ret=false;
		
		for (var i=0; i<items.length; i++) {
			var item = items[i];
			if (item.value != val) {
				if (deselectOther && item.selected) {
					item.selected=false;
				}
				continue;
			}
			
			item.selected = true;
			if (!deselectOther) {
				return true;
			}
			ret=true;
		}
		
		if (!ret) {
			// Rien n'a été selectionné !
			this.selectedIndex = -1;
		}
		return ret;
	},
	/**
	 * Clear the selection.
	 *
	 * @methid public
	 * @return void
	 */
	f_clearSelection: function() {
		// Grosse astuce (this) pour tout deselectionner !
		this.f_setValue(this, false, true);
	},
	/**
	 * Returns the index of the first selected value.
	 *
	 * @method public
	 * @return number Index of the first selected value.
	 */
	f_getSelectedIndex: function() {
		return this.f_getValue(true);
	},
	/**
	 * Specify selection by the index of the selected value.
	 *
	 * @method public
	 * @param number idx Index of the first selected value.
	 * @return void
	 */
	f_setSelectedIndex: function(idx) {
		return this.f_setValue(idx, true);
	},
	/**
	 * Returns the value of item specified by an index.
	 *
	 * @method public
	 * @param number Index of the item.
	 * @return String Value of the item.
	 */
	f_getValueFromIndex: function(idx) {
		f_core.Assert(typeof(idx)=="number", "f_combo.f_getValueFromIndex: Invalid idx parameter.");
		f_core.Assert(idx<0, "f_combo.f_getValueFromIndex: Index parameter is out of range (0<="+idx+"<"+items.length+").");

		var items = this.options;
		
		if (idx<0 || idx>=items.length) {
			return null;
		}
		
		return items[idx].value;
	},
	/**
	 * Returns the index of item specified by its value.
	 *
	 * @method public
	 * @param String val Value of the item
	 * @return number Index of the item.
	 */
	f_getIndexFromValue: function(val) {
		var items = this.options;
		for (var i=0; i<items.length; i++) {
			if (items[i].value == val) {
				return i;
			}
		}
		return -1;
	},
	f_serialize: function() {
		// On sait jamais ... dés fois que !
		var waiting=this._waiting;
		if (waiting) {
			// Il faut interdire la modification !
			this._waiting=undefined;
			this.removeChild(waiting);
		}

		if (this.f_isDisabled()) {
			var sel = this.f_getValue();
			this.f_setProperty(f_prop.SELECTED_ITEMS, sel, sel instanceof Array);
		}

		return this.f_super(arguments);
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			this.onchange = this.f_onSelect;
			return;
		}
		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			this.onchange = null;
			return;
		}
		this.f_super(arguments, type, target);
	},
	
	_a_updateFilterProperties: function() {
		this.f_appendCommand(function(combo) {			
			combo._callServer(0);
		});
	},
	/**
	 * @method private
	 */
	_callServer: function(firstIndex, cursorIndex) {
		if (!window.f_httpRequest) {
			f_core.Error(f_combo, "Class f_httpRequest is not defined !");
			return;
		}
		
		var w=f_core.GetCurrentStyleProperty(this, "width");
		if (f_core.IsGecko()) {
			if (!this.style.width) {
				w=(this.offsetWidth+2)+"px";
			}
		}
		
		f_core.Info(f_combo, "Width="+w);
		
		if (!w || w=="auto") {
			if (!this._oldWidth) {
				this._oldWidth="auto";
				
				this._oldWidth=w;
//				alert("old="+oldWidth);
			}
						
			w=this.offsetWidth;
			if (w<f_combo._MIN_WIDTH) {
				w=f_combo._MIN_WIDTH;
			}
			this.style.width=w+"px";
		}
		this.className=this._className+"_loading";
		
		// Effaces les items !
		while (this.hasChildNodes()) {
			this.removeChild(this.lastChild);
		}
 	
		var params=new Object;
		params.componentId=this.id;
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}
	
		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var combo=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 		 	var waiting=combo._waiting;
	 			if (!waiting && combo.childNodes.length<1) {
		 			waiting=combo.ownerDocument.createElement("OPTION");
		 			waiting.disabled=true;
		 			
		 			combo.appendChild(waiting);
		 			if (combo.size>1) {
		 				// Pas de selection si il y a plusieurs elements affichés dans la liste
		 				combo.selectedIndex=-1;
		 			} else {
			 			combo.selectedIndex=0;
			 		}
		 					 			
		 			combo._waiting=waiting;
		 		}
		 		
		 		if (waiting) {
					// pas de f_core.SetTextNode  : ca marche pas !
		 			waiting.innerHTML=f_core.EncodeHtml(f_waiting.GetLoadingMessage());
		 			waiting.disabled=true;
		 		}
	 		},	 		
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_combo, "Bad status: "+request.f_getStatus());
	 			
				if (combo.f_processNextCommand()) {
					return;
				}
	 		
				combo._loading=false;		
				
				var waiting=combo._waiting;
				if (waiting) {
					combo._waiting=undefined;
					combo.removeChild(waiting);
				}
				if (combo._oldWidth) {
					combo.style.width=combo._oldWidth;
					combo._oldWidth=undefined;
				}
				
				combo.className=combo._className;
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 			var waiting=combo._waiting;
				if (waiting) {
					// pas de f_core.SetTextNode  : ca marche pas !
					waiting.innerHTML=f_core.EncodeHtml(f_waiting.GetReceivingMessage());
					waiting.disabled=true;
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (combo.f_processNextCommand()) {
					return;
				}
	 			
	 			var waiting=combo._waiting;
				combo._waiting=undefined;
				
				try {
					if (waiting) {
						combo.removeChild(waiting);
					}
					if (combo._oldWidth) {
						combo.style.width=combo._oldWidth;
						combo._oldWidth=undefined;
					}
					combo.className=combo._className;

					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_combo, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
	
					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
						f_core.Error(f_combo, "Unsupported content type: "+responseContentType);
						return;
					}

					var ret=request.f_getResponse();
					try {
						eval(ret);
						
					} catch (x) {
						f_core.Error(f_combo, "Can not eval response '"+ret+"'.", x);
					}

				} finally {
					combo._loading=undefined;	
				}
				
				/* A voir ! @TODO
				var event=new f_event(combo, f_event.CHANGE);
				try {
					combo.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
				*/
	 		}
		});

		this._loading=true;
		request.f_setRequestHeader("X-Camelia", "items.request");
		request.f_doFormRequest(params);
	},
	/**
	 * Append an item.
	 *
	 * @method public
	 * @param boolean parent
	 * @param string label
	 * @param string value
	 * @param optional boolean selected
	 * @param optional boolean disabled
	 * @param optional string description
	 */
	f_appendItem: function(parent, label, value, selected, disabled, description) {
		if (parent) {
			var optgroup=this.ownerDocument.createElement("OPTGROUP");
			if (disabled) {
				optgroup.disabled=true;
			}
			
			// Pas de SetTextNode: ca marche pas !
			optgroup.innerHTML=f_core.EncodeHtml(label);
			
			this.appendChild(optgroup);
			return optgroup;
		}
		
		var option=this.ownerDocument.createElement("OPTION");
		option.value=value;
		if (disabled) {
			option.disabled=true;
		}
		if (selected) {
			option.selected=true;
		}
		
		if (description) {
			option.title=description;
		}
		if (arguments.length>5) {
			var values=new Object;
			
			option._clientDatas=values;
			
			for(var i=6;i<arguments.length;i+=2) {
				values[arguments[i]]=arguments[i+1];
			}
		}
		
		// Pas de SetTextNode: ca marche pas !
		option.innerHTML=f_core.EncodeHtml(label);
		
		this.appendChild(option);
		
		return option;
	},
	f_getSelection: function() {
		return this.f_getValue();
	},
	f_setSelection: function(selection) {
		this.f_setValue(selection, false, true);
	},
	/**
	 * Returns the disabled state of an item 
	 * @method public
	 * @param String itemValue Value of the item or the item object.
	 * @return boolean Disable state.
	 */
	f_isItemDisabled: function(itemValue) {
		var item=this.f_getItemByValue(itemValue);
		if (!item) {
			return null;
		}
		
		return item.disabled;
	},
	/**
	 * Returns the description of an item 
	 * @method public
	 * @param String itemValue Value of the item or the item object.
	 * @return String Description of the item.
	 */
	f_getItemToolTip: function(itemValue) {
		var item=this.f_getItemByValue(itemValue);
		if (!item) {
			return null;
		}
		
		return item.title;
	},
	/**
	 * Returns the label of an item 
	 * @method public
	 * @param String itemValue Value of the item or the item object.
	 * @return String Label of the item.
	 */
	f_getItemLabel: function(itemValue) {
		var item=this.f_getItemByValue(itemValue);
		if (!item) {
			return null;
		}
		
		return item.innerHTML;
	},
	/**
	 * Returns a value of a property.
	 * 
	 * @method public
	 * @param String itemValue Value of the item or the item object.
	 * @param String name Property name.
	 * @return String Value associated to the specified property.
	 */
	f_getItemClientData: function(itemValue, name) {
		var set=this.f_getItemClientSet(itemValue);
		if (!set) {
			return null;
		}
		
		return set[name];
	},
	/**
	 * Returns all the values associated to an item specified by its value.
	 * 
	 * @method public
	 * @param String itemValue Value of the item or the item object.
	 * @return String Value associated to the specified property.
	 */
	f_getItemClientSet: function(itemValue) {
		var item=this.f_getItemByValue(itemValue);
		if (!item) {
			return null;
		}
		
		var clientDatas=item._clientDatas;
		if (clientDatas) {
			return clientDatas;
		}

		var att=f_core.GetAttribute(item, "v:data");
		if (!att) {
			clientDatas=new Object;
			item._clientDatas=clientDatas;
			return clientDatas;
		}
		
		return fa_clientData.InitializeDataAttribute(item);
	}
}

var f_combo=new f_class("f_combo", null, __static, __prototype, f_input, fa_required, fa_filterProperties, fa_commands, fa_selectionProvider);
