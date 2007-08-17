/*
 * $Id$
 */

/**
 * 
 * @class public f_dataGridPopup extends f_dataGrid
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field private static final number
	 */
	_TITLE_HEIGHT: 19,
	
	/**
	 * @method hidden static
	 */
	CopyProperties: function(attributes, element, attributesName) {
		for(var i=2;i<arguments.length;i++) {
			var name=arguments[i];
			
			try {
				var value=element.getAttribute(name);
				if (!value) {
					continue;
				}
				
				attributes[name]=value;
				
			} catch(x) {
				// IE peut envoyer une exception en fonction du composant !
			}
		}
	},
	/**
	 *  @method hidden static
	 */
	Create: function(parent, dataGridPopup, width, height, styleClass) {
		
		var columns=dataGridPopup._columns;
		
		var properties= { 
			id: dataGridPopup.id+fa_namingContainer.GetSeparator()+"popup", 
			"v:nc": "true", 
			role: "wairole:grid", 
			"class": "f_grid", 
			"v:asyncRender": "true", 
			"v:filtred": "true"
		};
		
		if (width) {
			properties.style="width:"+width+"px";
			if (height) {
				properties.style+=";height:"+height+"px";
			}
		}
		
		if (styleClass) {
			properties["v:styleClass"]=styleClass;
		}
		
		f_dataGridPopup.CopyProperties(properties, dataGridPopup, "v:rows", "v:rowStyleClass", "v:paged", "v:headerVisible");
		
		var divDataGrid=f_core.CreateElement(parent, "div", properties);
		
		var headerVisible=f_core.GetBooleanAttribute(divDataGrid, "v:headerVisible", true);

		var vh=height;
		
		var totalSize=0;						
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];
			
			var w=column._width;
			var wi=parseInt(w);
			if (w==wi || w==wi+"px") {
				totalSize+=wi;
				continue;
			}
					
			totalSize=-1;
			break;
		}
		
		if (!headerVisible) {
			var divDataTitle=f_core.CreateElement(divDataGrid, "div", { 
				"class": "f_grid_dataTitle_scroll",
				style: "width:"+width+"px;height:"+f_dataGridPopup._TITLE_HEIGHT+"px"
			});
			
			properties={ 
				"class": "f_grid_fttitle",
				cellPadding: 0,
				cellSpacing: 0
			};
			if (totalSize>=0) {
				properties.style="width:"+totalSize+"px";
			}
			
			var tableTTitle=f_core.CreateElement(divDataTitle, "table", properties);

			var colGroup=f_core.CreateElement(tableTTitle, "colgroup");
		
			for(var i=0;i<columns.length;i++) {
				var column=columns[i];

				properties={};
				var cw=column._width;
				if (cw) {
					if (cw==parseInt(cw)) {
						cw+="px";
					}
					properties.style="width:"+cw;
				}
				
				f_core.CreateElement(colGroup, "col", properties);
			}
	
			var thead=f_core.CreateElement(tableTTitle, "thead");
			
			var tr=f_core.CreateElement(thead, "tr", { "class": "f_grid_title"});
			
			for(var i=0;i<columns.length;i++) {
				var column=columns[i];
				var th=f_core.CreateElement(tr, "th", { "class": "f_grid_tcell"});
				
				var align=column._align;
				if (!align) {
					align="left";
				}
				
				var divStext=f_core.CreateElement(th, "div", { "class": "f_grid_stext"});
				var divTtext=f_core.CreateElement(divStext, "div", { "class": "f_grid_ttext", align: align});
				//f_core.CreateElement(divTtext, "img", { "class": "f_grid_ttext", align: "left"});
				
				if (column._text) {
					f_core.SetTextNode(divTtext, column._text)
				}
			}
			
			f_core.CreateElement(tableTTitle, "tbody");
			
			vh-=f_dataGridPopup._TITLE_HEIGHT;
		}
				
		var divDataBody=f_core.CreateElement(divDataGrid, "div", { 
			"class": "f_grid_dataBody_scroll",
			style: "width:"+width+"px;height:"+vh+"px"
		});
		
			
		properties={ 
			"class": "f_grid_table",
			cellPadding: 0,
			cellSpacing: 0
		};
		if (totalSize>=0) {
			properties.style="width:"+totalSize+"px";
		}
		var tableBody=f_core.CreateElement(divDataBody, "table", properties);
		
		var colGroup=f_core.CreateElement(tableBody, "colgroup");
		
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];

			properties={};
			var cw=column._width;
			if (cw) {
				if (cw==parseInt(cw)) {
					cw+="px";
				}
				properties.style="width:"+cw;
			}
				
			f_core.CreateElement(colGroup, "col", properties);
		}
		
		f_core.CreateElement(tableBody, "tbody");
		
		fa_selectionManager.SetSelectionCardinality(divDataGrid, fa_cardinality.OPTIONAL_CARDINALITY, true);

		var dataGrid=f_class.Init(divDataGrid, f_dataGridPopup, [dataGridPopup]);
		
		dataGrid.f_setColumns2.apply(dataGrid, columns);
		
		dataGrid.f_completeComponent();		
				
		return dataGrid;
	}
}

var __members = {

	f_dataGridPopup: function(popupParent) {
		this.f_super(arguments);

		this._gridUpdadeServiceId="popupGrid.update";
		this._serviceGridId=popupParent.id;
		this._ignoreFocus=true;
		this._focus=true;
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateNewPage: function() {
		this.f_super(arguments);
		
		var autoSelect=this._autoSelect;
		if (!autoSelect) {
			return;
		}
		this._autoSelect=undefined;
		
		this.f_performAutoSelection(autoSelect);
	},
	/**
	 * @method hidden
	 * @param number autoSelect
	 * @return void
	 */
	f_performAutoSelection: function(autoSelect) {
		f_core.Assert(typeof(autoSelect)=="number", "f_dataGridPopup.f_performAutoSelection: Invalid autoSelect parameter ("+autoSelect+")");		
		
		var rows=this.fa_listVisibleElements();

		f_core.Debug(f_dataGridPopup, "f_performAutoSelection: change selection="+autoSelect+" rows.lengh="+rows.length);

		if (!rows.length) {
			return;
		}

		var selection;
		
		if (autoSelect>0) {
			selection=this.fa_getElementValue(rows[0]);

		} else if (autoSelect<0) {
			selection=this.fa_getElementValue(rows[rows.length-1]);
		}
		
		f_core.Debug(f_dataGridPopup, "f_performAutoSelection: Selection="+selection);
		
		if (selection!==undefined) {
			this.f_setSelection([ selection ], true);				
		}
	},
	/**
	 * @method hidden
	 * @param number autoSelect
	 * @return void
	 */
	f_setAutoSelection: function(autoSelect) {
		this._autoSelect=autoSelect;
	},
	f_performKeyDown: function(evt) {
		var code=evt.keyCode;
		switch(code) {
		case f_key.VK_SPACE:
		case f_key.VK_HOME:
		case f_key.VK_END:
			return true;
		}	
		
		return this.f_super(arguments, evt);
	}
}
 
new f_class("f_dataGridPopup", null, __statics, __members, f_dataGrid);
