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
		
	},
	f_finalize: function() {
		this.f_destroyDataGrid();
		this.f_destroyPager();
		
		this.f_super(arguments);		
	}
	f_addColumn: function(id, properties) {
		
	},
	f_getDataGrid: function(doc) {
		var dataGrid=this._dataGrid;
		if (dataGrid && dataGrid.parentNode && dataGrid.ownerDocument==doc) {
			return dataGrid;
		}
		
		if (dataGrid) {
			this.f_destroyDataGrid(dataGrid);
		}
		var pager=this._pager;
		if (pager) {
			this.f_destroyPager(dataGrid);	
		}
		
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
