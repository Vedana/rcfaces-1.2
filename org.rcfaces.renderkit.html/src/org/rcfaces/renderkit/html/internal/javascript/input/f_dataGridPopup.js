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
		
		var dataGridPopupId=dataGridPopup.id+"::popup";
		
		var sc="f_grid";
		
		if (styleClass) {
			sc+=" "+styleClass;
		}
		
		var properties= { 
			id: dataGridPopupId,
			"v:nc": "true", 
			role: "grid", 
			className: sc, 
			"v:asyncRender": "true", 
			"v:filtred": "true",
			"v:selectionCardinality": fa_cardinality.ONE_CARDINALITY
		};
		
		if (width) {
			properties.cssWidth=width+"px";
			if (height) {
				properties.cssHeight=height+"px";
			}
		}		
		
		f_dataGridPopup.CopyProperties(properties, dataGridPopup, "v:rows", "v:rowStyleClass", "v:paged", "v:headerVisible", "v:emptyDataMessage");
		
		var divDataGrid=f_core.CreateElement(parent, "div", properties);
		
		var bw=f_core.ComputeContentBoxBorderLength(divDataGrid, "left", "right");
		var bh=f_core.ComputeContentBoxBorderLength(divDataGrid, "top", "bottom");
		
		width-=bw;
		height-=bh;
		
		var headerVisible=f_core.GetBooleanAttribute(dataGridPopup, "v:headerVisible", false);
		
		var totalSize=0;
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];

			if (column._visibility===false) {
				continue;
			}
			
			var w=column._width;
			var wi=parseInt(w);
			if (w==wi || w==wi+"px") {
				totalSize+=wi;
				continue;
			}
					
			totalSize=-1;
			break;
		}
		
		var emptyDataMessage=properties["v:emptyDataMessage"];
		if (emptyDataMessage) {
			f_core.CreateElement(divDataGrid, "div", {
				id: dataGridPopupId + f_grid._EMPTY_DATA_MESSAGE_ID_SUFFIX,
				className: "f_grid_empty_data_message",
				textNode: emptyDataMessage
			});
		}
		
		if (headerVisible) {
			properties = { 
				id: dataGridPopupId + f_grid._DATA_TITLE_SCROLL_ID_SUFFIX,
				className: "f_grid_dataTitle_scroll",
				cssHeight: f_dataGridPopup._TITLE_HEIGHT+"px"
			};
			
			var divDataTitle=f_core.CreateElement(divDataGrid, "div", properties);
			
			height-=f_dataGridPopup._TITLE_HEIGHT;
			if (f_core.IsInternetExplorer()) {
				height-=2; // ??? de l'huile ...
			}
			
			properties={ 
				id: dataGridPopupId + f_grid._FIXED_HEADER_ID_SUFFIX,
				className: "f_grid_fttitle"
			};
			
			var tableTTitle=f_core.CreateElement(divDataTitle, "ul", properties);
			
			for(var i=0;i<columns.length;i++) {
				var column=columns[i];

				if (column._visibility===false) {
					continue;
				}
				
				properties = {
					className: "f_grid_tcell"
				};
				if (column._width) {
					properties.cssWidth=column._width;
				}
				
				var th=f_core.CreateElement(tableTTitle, "li", properties);
				
				var align=column._align;
				if (!align) {
					align="left";
				}
				
				var divStext=f_core.CreateElement(th, "div", { 
					className: "f_grid_stext"
				});
				
				var divTtext=f_core.CreateElement(divStext, "div", { 
					className: "f_grid_ttext", 
					align: align
				});
				//f_core.CreateElement(divTtext, "img", { "class": "f_grid_ttext", align: "left"});
				
				if (column._text) {
					f_core.SetTextNode(divTtext, column._text)
				}
			}
		}
				
		var divDataBody=f_core.CreateElement(divDataGrid, "div", {
			 id: dataGridPopupId + f_grid._DATA_BODY_SCROLL_ID_SUFFIX,			 
			className: "f_grid_dataBody_scroll",
			cssWidth: width+"px",
			cssHeight: height+"px"
		});
		
			
		properties={ 
			id: dataGridPopupId + f_grid._DATA_TABLE_ID_SUFFIX,			 
			className: "f_grid_table",
			cellPadding: 0,
			cellSpacing: 0
		};
		if (totalSize>=0) {
			properties.cssWidth=totalSize+"px";
		}
		var tableBody=f_core.CreateElement(divDataBody, "table", properties);
		
		var colGroup=f_core.CreateElement(tableBody, "colgroup");
		
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];

			if (column._visibility===false) {
				continue;
			}

			properties={};
			var cw=column._width;
			if (cw) {
				if (cw==parseInt(cw)) {
					cw+="px";
				}
				properties.cssWidth=cw;
			}
				
			f_core.CreateElement(colGroup, "col", properties);
		}
		
		f_core.CreateElement(tableBody, "tbody");
		
		fa_selectionManager.SetSelectionCardinality(divDataGrid, fa_cardinality.OPTIONAL_CARDINALITY, true);

		var dataGrid=f_class.Init(divDataGrid, f_dataGridPopup, [dataGridPopup]);
		
		dataGrid.f_setColumns2.apply(dataGrid, columns);
		
		dataGrid.f_updateTitle();
		
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
		this._showLoadingAlert=false;
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
			
			var row=this.f_getRowByValue(selection);
			if (row!==undefined || row!==null) {
				this.f_moveCursor(row, true);
			}	
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
 
new f_class("f_dataGridPopup", {
	extend: f_dataGrid,
	statics: __statics,
	members: __members
});
