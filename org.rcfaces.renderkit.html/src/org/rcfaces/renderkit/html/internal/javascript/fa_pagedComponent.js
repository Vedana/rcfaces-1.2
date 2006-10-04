/*
 * $Id$
 */
 
/**
 * Aspect PagedComponent
 *
 * @aspect fa_pagedComponent extends fa_filterProperties
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	/** 
	 * @field private static
	 */
	_DataPagers: undefined,
	 
	/**
	 * @method hidden static final 
	 * @param String componentId
	 * @param fa_pager pager
	 * @return void
	 */
	RegisterPager: function(componentId, pager) {
	
		var dgp=fa_pagedComponent._DataPagers;
		if (!dgp) {
			dgp=new Object;
			
			fa_pagedComponent._DataPagers=dgp;
		}
		
		componentId=fa_namingContainer.ComputeComponentId(pager, componentId);
		
		var lst=dgp[componentId];
		if (!lst) {
			lst=new Array;
			dgp[componentId]=lst;
		}
		
		lst.push(pager);

		var dp=pager.ownerDocument.getElementById(componentId);
		// Le dataGrid n'existe pas forcement lors de son enregistrement !		
		if (dp && f_class.IsObjectInitialized(dp)) {

			f_core.Debug(fa_pagedComponent, "Register fa_pager ("+pager.id+"/"+pager+") to component '"+componentId+"': Initialize now ! ");
			try {		
				pager.fa_pagedComponentInitialized(dp);
		
			} catch (x) {
				f_core.Error(fa_pagedComponent, "Call of fa_pagedComponentInitialized() throws an exception ! (pager="+pager.id+")", x);
			}
							
			return;
		}

		f_core.Debug(fa_pagedComponent, "Register fa_pager ("+pager.id+"/"+pager+") to component '"+componentId+"': Waiting initialization ! ");
	},
	Finalizer: function() {
		fa_pagedComponent._DataPagers=undefined;
	}
}
 
var __prototype = {
	fa_pagedComponent: function() {
		if (f_core.GetAttribute(this, "v:asyncRender")) {
			this._interactive=true;
		}
		
		if (f_core.GetAttribute(this, "v:interactiveShow")) {
			this._interactiveShow=true;
		}
		
		var rows=f_core.GetAttribute(this, "v:rows");
		this._rows=(rows)?parseInt(rows):0;
		
		var first=f_core.GetAttribute(this, "v:first");
		this._first=(first)?parseInt(first):0;

		this._rowCount=f_core.GetAttribute(this, "v:rowCount");
		if (!this._rowCount) {
			this._rowCount=-1;
			this._maxRows=this._first+this._rows;
			
		} else {
			this._rowCount=parseInt(this._rowCount);
		}
	},
	/*
	f_finalize: function() {
		// this._rows=undefined;  // number
		// this._maxRows=undefined; // number
		// this._rowCount=undefined; // number
		// this._first=undefined; // number
		// this._interactive=undefined; // boolean

		// this._interactiveShow=undefined; // boolean
	},
	*/
	
	/**
	 * @method protected
	 */
	f_performPagedComponentInitialized: function() {
		var dps=fa_pagedComponent._DataPagers;

		if (!dps) {
			f_core.Debug(fa_pagedComponent, "Perform page component initialized:  NO pagers !");

			return;
		}
		
		var lst=dps[this.id];
		f_core.Debug(fa_pagedComponent, "Perform page component initialized ("+this.id+") list="+lst);
		
		if (!lst) {
			return;
		}
		
		for(var i=0;i<lst.length;i++) {
			var p=lst[i];
			if (!p) {
				continue;
			}
			
			try {
				p.fa_pagedComponentInitialized(this);

			} catch (x) {
				f_core.Error(fa_pagedComponent, "Call of fa_pagedComponentInitialized() throws an exception ! (pager="+pager.id+")", x);
				
				lst[i]=undefined;
			}
		}
	},

	/**
	 * @method hidden
	 */
	f_setInteractiveShow: function(interactiveComponentId) {		
		this._interactiveShow=true;
	},
	
	/**
	 * Returns index of first row.
	 *
	 * @method public
	 * @return number
	 */
	f_getFirst: function() {
		return this._first;
	},
	/**
	 * Returns number of row.
	 *
	 * @method public
	 * @return number
	 */
	f_getMaxRows: function() {
		return this._maxRows;
	},
	/**
	 * List rows.
	 *
	 * @method public
	 * @return Object[]
	 */
	f_getRows: function() {
		return this._rows;
	},
	/**
	 * Returns number of dowloaded rows.
	 *
	 * @method public
	 * @return number
	 */
	f_getRowCount: function() {
		return this._rowCount;
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setFirst: f_class.ABSTRACT
}

var fa_pagedComponent=new f_aspect("fa_pagedComponent", __static, __prototype, fa_filterProperties);


