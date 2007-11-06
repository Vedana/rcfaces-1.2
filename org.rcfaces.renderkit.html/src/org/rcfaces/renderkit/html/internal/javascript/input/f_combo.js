/* 
 * $Id$
 */


/**
 * 
 * @class f_combo extends f_input, fa_required, fa_filterProperties, fa_commands, fa_selectionProvider, fa_immediate
 * @author Joel Merlin
 * @author Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
var __statics = {

	/**
	 * @field private static final number
	 */
	_MIN_WIDTH: 128,
	
	/**
	 * @method private static final
	 * @param Event evt
	 * @return boolean
	 * @context object:combo
	 */
	_OnChange: function(evt) {
		var combo=this;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		return combo.f_fireEvent(f_event.SELECTION, evt, null, combo.f_getValue(), combo);
	}
}

var __members = {

	/*
	f_combo: function() {
		this.f_super(arguments);
	},
	*/

	f_finalize: function() {
//		this._loading=undefined; // boolean
		this._waiting=undefined; // f_waiting
//		this._oldWidth=undefined; // string

		// Pour l'instant il n'y a pas de variables attachées aux OPTIONs
	
		this.f_super(arguments);
	},
	f_update: function() {
		if (f_core.GetBooleanAttribute(this, "v:noSelection")) {
			this.selectedIndex = -1;
		}
		
		this.f_super(arguments);
	},
	f_initializeInput: function() {
		return this;
	},
	/**
	 * @method public 
	 * @param String value
	 * @return Object The item object or <code>null</code> if the item is not found.
	 */
	f_getItemByValue: function(value) {
		if (value.nodeType==f_core.ELEMENT_NODE && value.tagName.toLowerCase()=="option") {
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
	 * @return String Value, or index of the item.
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
	 * @param String val Value associated to an item. (if val is a number, val specifies the index of the item)
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
	 * @method public
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
		this.f_setValue(idx, true);
	},
	/**
	 * Returns the value of item specified by an index.
	 *
	 * @method public
	 * @param number idx Index of the item.
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
	 * Remove specified items
	 * 
	 * @method public
	 * @param String... value
	 * @return number Number of removed items.
	 */
	f_clear: function(value) {		
		var cnt=0;
		var input=this.f_getInput();
		
		for(var j=0;j<arguments.length;j++) {
			value=arguments[j];
			
			var items = input.options; // On recharge à chaque fois !
			
			for (var i=0; i<items.length; i++) {
				var item=items[i];
				
				if (items.value != value) {
					continue;
				}
				
				input.removeChild(item);

				cnt++;
				break;
			}
		}
		
		return cnt;
	},
	/**
	 * Remove specified items.
	 * 
	 * @method public
	 * @param any[] values List of values whose specified items.
	 * @return number Number of removed items.
	 */
	f_clearArray: function(values) {
		f_core.Assert(values instanceof Array, "f_combo.f_clearArray: Invalid values parameter '"+values+"'.");

		return this.f_clear.apply(this, values);
	},
	/**
	 * Remove all items.
	 * 
	 * @method public
	 * @return number Number of removed rows.
	 */
	f_clearAll: function() {
		var input=this.f_getInput();
		var items=input.items;
		var cnt=items.length;
		if (!cnt) {
			return 0;
		}
		
		// items risque d'etre modifié au fur & à mesure des removeChilds
		for(var i=cnt-1;i>=0;i--) {
			input.removeChild(items[i]);
		}
		
		return cnt;
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

		this.f_super(arguments);
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			this.onchange = f_combo._OnChange;
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
	
	fa_updateFilterProperties: function() {
		this.f_appendCommand(function(combo) {			
			combo._callServer();
		});
	},
	/**
	 * @method private
	 */
	_callServer: function() {
		f_class.IsClassDefined("f_httpRequest", true);
		
		var w=f_core.GetCurrentStyleProperty(this, "width");
		if (f_core.IsGecko()) {
			if (!this.style.width) {
				w=(this.offsetWidth+2)+"px";
			}
		}
		
		// f_core.Info(f_combo, "Width="+w);
		
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
		this.className=this.f_computeStyleClass("_loading");
		
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
		var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var combo=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 		 	var waiting=combo._waiting;
	 			if (!waiting && !combo.childNodes.length) {
		 			waiting=combo.ownerDocument.createElement("option");
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
	 			f_core.Info(f_combo, "f_callServer.onError: Bad status: "+status);
	 			
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
				
				combo.className=this.f_computeStyleClass();
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

					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						combo.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}
				
					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						combo.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
		
					var responseContentType=request.f_getResponseContentType().toLowerCase();
					
					if (responseContentType.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE)>=0) {
						var code=f_error.ComputeApplicationErrorCode(request);
				
				 		combo.f_performErrorEvent(request, code, content);
						return;
					}
					
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
			 			combo.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);
						return;
					}
		
					var ret=request.f_getResponse();
					try {
						f_core.WindowScopeEval(ret);
						
					} catch (x) {
						f_core.Error(f_combo, "_callServer.onLoad: Can not eval response '"+ret+"'.", x);
					}

				} finally {
					combo._loading=undefined;	
					combo.className=combo.f_computeStyleClass();
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
	 * @method protected
	 * @overided
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * Append an item.
	 *
	 * @method public
	 * @param boolean parent
	 * @param String label
	 * @param String value
	 * @param optional boolean selected
	 * @param optional boolean disabled
	 * @param optional String description
	 * @return Object New item.
	 */
	f_appendItem: function(parent, label, value, selected, disabled, description) {
		if (parent) {
			var optgroup=this.ownerDocument.createElement("optgroup");
			if (disabled) {
				optgroup.disabled=true;
			}
			
			// Pas de SetTextNode: ca marche pas !
			optgroup.innerHTML=f_core.EncodeHtml(label);
			
			this.appendChild(optgroup);
			return optgroup;
		}
		
		var option=this.ownerDocument.createElement("option");
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

		clientDatas=f_core.ParseDataAttribute(item);
		item._clientDatas=clientDatas;
		
		return clientDatas;
	},
	/**
	 * @method hidden
	 */
	fa_cancelFilterRequest: function() {
	}
}


new f_class("f_combo", {
	extend: f_input,
	aspects: [ fa_required, fa_filterProperties, fa_commands, fa_selectionProvider, fa_immediate ],
	members: __members,
	statics: __statics
});
