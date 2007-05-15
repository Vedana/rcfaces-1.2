/*
 * $Id$
 */

/**
 * 
 * @class public f_dataGridPopup extends f_daraGrid
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	/**
	 * @method private static
	 */
	_CopyProperties: function(attributes, element, attributesName) {
		for(var i=2;i<arguments.length;i++) {
			var name=arguments[i];
			
			var value=element.getAttribute(name);
			if (!value) {
				continue;
			}
			
			attributes[name]=value;
		}
	},
	/**
	 *  @method hidden static
	 */
	Create: function(parent, dataGridPopup) {
		
		var doc=parent.ownerDocument;
		var dataGridContainer=doc.createElement("table");
		dataGridContainer.cellSpacing="0";
		dataGridContainer.cellPadding="0";
		parent.appendChild(dataGridContainer);
		
		var tbody=doc.createElement("tbody");
		dataGridContainer.appendChild(tbody);
		
		var tr=doc.createElement("tr");
		tbody.appendChild(tr);
				
		var td=doc.createElement("td");
		tr.appendChild(td);
											
	//	td.innerHTML=this._dataGridInnerHtml;
		
		var width=f_core.GetNumberAttribute(dataGridPopup, "popupWidth", 320);
		var height=f_core.GetNumberAttribute(dataGridPopup, "popupHeight", 200);
		
		var columns=dataGridPopup._columns;
		
		var divAttributes= { 
			i: dataGridPopup.id+":popup", 
			"v:class": "f_dataGrid", 
			"v:nc": "true", 
			role: "wairole:grid", 
			"class": "f_grid", 
			"v:asyncRender": "true", 
			"v:filtred": "true",
			style: "width:"+width+"px;height:"+height+"px"
		};
		
		f_dataGridPopup._CopyProperties(divAttributes, dataGridPopup, "v:rows", "v:rowStyleClass", "v:paged");
		
		var divDataGrid=f_core.CreateElement(td, "div", divAttributes);
		
		var divDataTitle=f_core.CreateElement(divDataGrid, "div", { 
			"class": "f_grid_dataTitle_scroll",
			style: "width:"+width+"px"
		});
		
		var tableTTitle=f_core.CreateElement(divDataTitle, "table", { 
			"class": "f_grid_fttitle",
			cellpadding: "0",
			cellspacing: "0"
		});
		
		for(var i=0;i<columns.length;i++) {
			f_core.CreateElement(tableTTitle, "col");
		}

		var thead=f_core.CreateElement(tableTTitle, "thead");
		
		var tr=f_core.CreateElement(thead, "tr", { "class": "f_grid_title"});
		
		for(var i=0;i<columns.length;i++) {
			var column=columns[i];
			var th=f_core.CreateElement(tr, "th", { "class": "f_grid_tcell"});
			
			var divStext=f_core.CreateElement(th, "div", { "class": "f_grid_stext"});
			var divTtext=f_core.CreateElement(divStext, "div", { "class": "f_grid_ttext", align: "left"});
			//f_core.CreateElement(divTtext, "img", { "class": "f_grid_ttext", align: "left"});
			
			if (column._text) {
				f_core.SetTextNode(divTtext, column._text)
			}
		}
		
		var tbody=f_core.CreateElement(tableTTitle, "tbody");
		
		var divDataBody=f_core.CreateElement(divDataGrid, "div", { 
			"class": "f_grid_dataBody_scroll",
			style: "width:"+width+"px"
		});
		
		var tableBody=f_core.CreateElement(divDataBody, "table", { 
			"class": "f_grid_table",
			cellpadding: "0",
			width: "100%"
		});
		
		for(var i=0;i<columns.length;i++) {
			f_core.CreateElement(tableBody, "col");
		}
		
		f_core.CreateElement(tableBody, "tbody");

				
		//<div id="gridData:l1" v:class="f_dataGrid" v:nc="true" role="wairole:grid" class="f_grid" v:rows="17" v:rowCount="93" v:rowStyleClass="red,blue" v:asyncRender="true" style="width:400px">
		//   <div class="f_grid_dataTitle_scroll" style="width:400px">
		//     <table class="f_grid_fttitle" cellpadding="0" cellspacing="0">
		//       <col />
		//       <col />
		//       <thead>
		//         <tr class="f_grid_title">
		//           <th class="f_grid_tcell">
		//             <div class="f_grid_stext">
		//               <div class="f_grid_ttext" align="left">
		//                 <img class="f_grid_timage" src="/jsf/rc-av/uzIdfUhzhPHp1Dln/olivier/images/icon_print.gif" width="15" height="15" />Nom
		//               </div>
		//             </div>
		//           </th>
		//           <th class="f_grid_tcell">
		//             <div class="f_grid_stext">
		//               <div class="f_grid_ttext" align="left">Offset</div>
		//             </div>
		//           </th>
		//         </tr>
		//       </thead>
		//       <tbody>
		//       </tbody>
		//     </table>
		//   </div>
		//   <div class="f_grid_dataBody_scroll" style="width:400px">
		//     <table class="f_grid_table" cellspacing="0" width="100%">
		//       <col align="left" />
		//       <col align="left" />
		//       <tbody></tbody>
		//     </table>
		//   </div>
		//  </div>
		
		fa_selectionManager.SetSelectionCardinality(divDataGrid, fa_cardinality.OPTIONAL_CARDINALITY, true);

		var dataGrid=f_class.Init(divDataGrid, f_dataGridPopup, [td, dataGridPopup]);
		
		dataGrid.f_setColumns2.apply(dataGrid, columns);
		
		dataGrid.f_completeComponent();		
		
		return dataGrid;
	}
}

var __prototype = {

	f_dataGridPopup: function(parent, popupParent) {
		this.f_super(arguments, parent);

		this._gridUpdadeServiceId="popupGrid.update";
		this._serviceGridId=popupParent.id;
		this._ignoreFocus=true;
	},
	/**
	 * @method protected
	 */
	f_getEventLocked: function(showAlert) {
		if (showAlert!==false) {
			if (f_popup.IsChildOfDocument(this)) {
				return false;
			}			
		}
		
		if (!this.f_super(arguments, showAlert)) {
			return false;
		}
		
		if (showAlert===false) {
			if (f_popup.IsChildOfDocument(this)) {
				return false;
			}
		}
				
		return true;
	}
}
 
new f_class("f_dataGridPopup", null, __static, __prototype, f_dataGrid);
