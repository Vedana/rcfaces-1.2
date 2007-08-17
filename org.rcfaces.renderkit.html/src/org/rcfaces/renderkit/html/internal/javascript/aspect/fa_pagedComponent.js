/*
 * $Id$
 */
 
/**
 * Aspect PagedComponent
 *
 * @aspect public abstract fa_pagedComponent extends fa_filterProperties
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
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

			f_core.Debug(fa_pagedComponent, "RegisterPager: Register fa_pager ("+pager.id+"/"+pager+") to component '"+componentId+"': Initialize now ! ");
			try {		
				pager.fa_pagedComponentInitialized(dp);
		
			} catch (x) {
				f_core.Error(fa_pagedComponent, "RegisterPager: Call of fa_pagedComponentInitialized() throws an exception ! (pager="+pager.id+")", x);
			}
							
			return;
		}

		f_core.Debug(fa_pagedComponent, "RegisterPager: Register fa_pager ("+pager.id+"/"+pager+") to component '"+componentId+"': Waiting initialization ! ");
	},
	/**
	 * @method hidden static final 
	 * @param fa_pager pager
	 * @return void
	 */
	UnregisterPager: function(pager) {	
		var dgp=fa_pagedComponent._DataPagers;
		if (!dgp) {
			return;
		}
		
		for(var componentId in dgp) {
			f_core.RemoveElement(dgp[componentId], pager);
		}
	},
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		fa_pagedComponent._DataPagers=undefined;
	}
}
 
var __members = {
	fa_pagedComponent: function() {
		this._interactive=f_core.GetBooleanAttribute(this, "v:asyncRender");
		
		this._interactiveShow=f_core.GetBooleanAttribute(this, "v:interactiveShow");
		
		this._rows=f_core.GetNumberAttribute(this, "v:rows", 0); // Nombre ligne a afficher
		
		this._first=f_core.GetNumberAttribute(this, "v:first", 0);  // La premiere ligne

		this._paged=f_core.GetBooleanAttribute(this, "v:paged", true);

		this._rowCount=f_core.GetNumberAttribute(this, "v:rowCount", -1); // Nombre ligne au total
		if (this._rowCount<0) {
			this._maxRows=this._first+this._rows;
		}
	},
	/*
	f_finalize: function() {
		// this._rows=undefined;  // number
		// this._maxRows=undefined; // number
		// this._rowCount=undefined; // number
		// this._first=undefined; // number
		// this._interactive=undefined; // boolean
		// this._paged=undefined; // boolean

		// this._interactiveShow=undefined; // boolean
	},
	*/
	
	/**
	 * @method protected
	 */
	f_performPagedComponentInitialized: function() {
		var dps=fa_pagedComponent._DataPagers;

		if (!dps) {
			f_core.Debug(fa_pagedComponent, "f_performPagedComponentInitialized: Perform page component initialized:  NO pagers !");

			return;
		}
		
		var lst=dps[this.id];
		f_core.Debug(fa_pagedComponent, "f_performPagedComponentInitialized: Perform page component initialized ("+this.id+") list="+lst);
		
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
				f_core.Error(fa_pagedComponent, "f_performPagedComponentInitialized: Call of fa_pagedComponentInitialized() throws an exception ! (pager="+p.id+")", x);
				
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
	 * Returns <code>true</code> if the component is paged
	 *
	 * @method public
	 * @return boolean <code>true</code> if the component is paged.
	 */
	f_isPaged: function() {
		return this._paged;
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setFirst: f_class.ABSTRACT
}

new f_aspect("fa_pagedComponent", {
	extend: [ fa_filterProperties ],
	statics: __statics,
	members: __members
});


