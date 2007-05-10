/*
 * $Id$
 */

/**
 * 
 * @class public fa_dataGridPopup extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {
	
}

var __prototype = {
	
	fa_dataGridPopup: function() {
		this.f_super(arguments);
		
		this._rows=10;
		
	},
	f_finalize: function() {
		
		// this._columns=undefined; // List<Object>
		// this._rows=undefined; // number
		
		this.f_destroyDataGrid();
		this.f_destroyPager();
		
		this.f_super(arguments);		
	},
	/**
	 * @method hidden
	 */
	f_addColumn: function(properties) {
		var columns=this._columns;
		if (!columns) {
			columns=new Array;
			this._columns=columns;
		}
		
		for(var i=0;i<arguments.length;i++) {
			columns.push(arguments[i]);
		}
	},
	f_getDataGrid: function(parent) {
		var dataGrid=this._dataGrid;
		if (dataGrid && dataGrid.parentNode && dataGrid.ownerDocument==parent.ownerDocument) {
			return dataGrid;
		}
		
		if (dataGrid) {
			this.f_destroyDataGrid(dataGrid);
		}
		var pager=this._pager;
		if (pager) {
			this.f_destroyPager(dataGrid);	
		}
		
	//	dataGrid=f_dataGrid.Create(parent, this._columns, this._rows, );
		
		
		var doc=parent.ownerDocument;
		var dataGridContainer=doc.createElement("div");
		parent.appendChild(dataGrid);
		
		dataGridContainer.innerHTML=innerHTML;
				
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
		
		f_class.Init(dataGridContainer.firstChild, f_dataGrid, [parent]);
		
		return dataGrid;
	},
	f_destroyDataGrid: function() {
		var dataGrid=this._dataGrid;
		if (!dataGrid) {
			return;
		}
		this._dataGrid=undefined;
		
		var parent=dataGrid.parentNode;
		if (parent) {
			parent.removeChild(dataGrid);
		}
		
		f_classLoader.Destroy(dataGrid);
	},
	f_destroyPager: function() {
		var pager=this._pager;
		if (!pager) {
			return;
		}
		this._pager=undefined;
		
		var parent=pager.parentNode;
		if (parent) {
			parent.removeChild(pager);
		}
		
		f_classLoader.Destroy(pager);
	},
	
	f_openPopup: function() {
		
	}
}

new f_aspect("fa_dataGridPopup", null, __static, __prototype);
