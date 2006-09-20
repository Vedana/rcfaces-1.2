/*
 * $Id$
 */

/**
 * @class public f_dataPager extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	
	/**
	 * @method private static
	 */
	_AddSpan: function(container, spanClass, text) {
		var span=document.createElement("SPAN");
		span.className=spanClass;
		span.appendChild(document.createTextNode(text));
		
		container.appendChild(span);
		
		return span;
	},
	
	/**
	 * @method private static
	 */
	_AddText: function(container, text) {
		container.appendChild(document.createTextNode(text));	
	},
	
	/**
	 * @method private static
	 */
	_AddButton: function(dataPager, container, buttonClass, text, tooltip, index) {
		var button;
		
		if (index===undefined || index<0) {
			button=document.createElement("SPAN");
			
		} else {
			button=document.createElement("A");
			button._index=index;
			button.href=f_core.JAVASCRIPT_VOID;
			button.onclick=f_dataPager._PositionSelect;
			button.onkeydown=f_dataPager._PositionKey;
			button.f_link=dataPager;
		}
		button.className=buttonClass;
		if (tooltip) {
			button.title=tooltip;
		}
		
		button.appendChild(document.createTextNode(text));
		
		container.appendChild(button);
		
		var buttons=dataPager._buttons;
		if (!buttons) {
			buttons=new Array;
			dataPager._buttons=buttons;
		}
		button.id=dataPager+"__"+buttons.length;
		buttons.push(button);
		
		return button;
	},
	
	/**
	 * @method private static
	 */
	_SearchButtons: function(list, parent) {
		var children=parent.childNodes;
		if (!children || children.length<1) {
			return;
		}
		for(var i=0;i<children.length;i++) {
			var child=children[i];
			
			if (!child.tagName) {
				continue;
			}
			var index=f_core.GetAttribute(child, "v:index");
			if (!index) {
				index=child._index;
			}
			if (index) {
				list.push(child);
				continue;
			}
			
			f_dataPager._SearchButtons(list, child);
		}
		
		return list;
	},
	
	/**
	 * @method private static
	 */
	_PositionKey: function(evt) {
		var dataPager=this.f_link;
		if (dataPager.f_getEventLocked()) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}
		var code=evt.keyCode;
		var cancel=false;
		
		switch(code) {
		case f_key.VK_RIGHT: 
			var buttons=dataPager._buttons;
			for(var i=0;i<buttons.length;i++) {
				var button=buttons[i];
				
				if (button!=this) {
					continue;
				}
				
				for(var j=0;j<buttons.length;j++) {
					i=(i+1) % buttons.length;
					
					var but=buttons[i];
					if (!but.f_link) {
						continue;
					}

					f_core.SetFocus(but);
					break;
				}
				break;
			}		
			
			cancel=true;
			break
						
		case f_key.VK_LEFT: 
			var buttons=dataPager._buttons;
			for(var i=0;i<buttons.length;i++) {
				var button=buttons[i];
				
				if (button!=this) {
					continue;
				}
				
				for(var j=0;j<buttons.length;j++) {
					i=(i-1+buttons.length) % buttons.length;
					
					var but=buttons[i];
					
					if (!but.f_link) {
						continue;
					}

					f_core.SetFocus(but);
					break;
				}
				break;
			}
			
			cancel=true;
			break;
		}
		
		if (cancel) {
			return f_core.CancelEvent(evt);
		}
		return true;
	},
	
	/**
	 * @method private static
	 */
	_PositionSelect: function(evt) {
		var dataPager=this.f_link;
		if (dataPager.f_getEventLocked()) {
			return false;
		}
		if (!evt) {
			evt = window.event;
		}

		var v_index=f_core.GetAttribute(this, "v:index");		
		if (typeof(v_index)!="number") {
			v_index=this._index;
		
			if (typeof(v_index)!="number") {
				return false;
			}
		}
		
		dataPager.f_changePosition(v_index);
		
		return f_core.CancelEvent(evt);
	}
}
 
var __prototype = {

	f_dataPager: function() {
		this.f_super(arguments);
		
		this._for=f_core.GetAttribute(this, "v:for");
	},
	f_finalize: function() {
		this._destroyButtons();
		
		this._dataComponent=undefined;
		this._for=undefined;
		
		this.f_super(arguments);
	},
	_destroyButtons: function() {
		var buttons=this._buttons;
		if (!buttons) {
			return;
		}

		this._buttons=undefined;

		for(var i=0;i<buttons.length;i++) {
			var button=buttons[i];
			
			button.onclick=null;
			button.onkeydown=null;
			button.f_link=undefined;
			button._index=undefined;
			f_core.VerifyProperties(button);			
		}			
	},
	f_update: function() {
		
		this.f_super(arguments);

		if (this._for) {
			fa_pagedComponent.RegisterPager(this._for, this);
		}
	},
	
	/* ****************************************************************** */
	
	/**
	 * @method hidden
	 */
	f_dataComponentInitialized: function(dataComponent) {
		this._dataComponent=dataComponent;
		
		var children=this.childNodes;
		if (children) {
			this._destroyButtons();

			while (this.hasChildNodes()) {
		    	this.removeChild(this.lastChild);
			}
		}
		
		var resourceBundle=f_resourceBundle.Get(f_dataPager);
		
		var rows=dataComponent.f_getRows();
		if (rows<1) {
			f_dataPager._AddSpan(this, "f_dataPager_title_found", resourceBundle.f_get("NO_PAGE"));
			return;
		}
		
		/*			
			<span class="f_dataPager_title_found">73 entr&eacute;es</span>, 
		*/
		
		var cls=this.className+"_title_found";
		
		var rowCount=dataComponent.f_getRowCount();
		var first=dataComponent.f_getFirst();
		var maxRows=dataComponent.f_getMaxRows();

		f_core.Debug("f_dataPager", "Update pager values :  rowCount="+rowCount+" first="+first+" maxRows="+maxRows);

		if (rowCount<0) {
			f_dataPager._AddSpan(this, cls, resourceBundle.f_get("UNKNOWN_ENTRY_COUNT"));

		} else if (rowCount==0) {
			f_dataPager._AddSpan(this, cls, resourceBundle.f_get("NO_RESULT"));
			return;
			
		} else if (rowCount==1) {
			f_dataPager._AddSpan(this, cls, resourceBundle.f_get("ONE_RESULT"));

		} else {
			f_dataPager._AddSpan(this, cls, resourceBundle.f_format("N_RESULTS", rowCount));
		}
		
		f_dataPager._AddText(this, ", ");
		
		if (rowCount<0) {
			f_dataPager._AddSpan(this, cls, resourceBundle.f_format("SUMMARY_NO_END", first+1));
			
		} else {
			var last=first+rows;
			if (last>=rowCount) {
				last=rowCount;
			}
			f_dataPager._AddSpan(this, cls, resourceBundle.f_format("SUMMARY", first+1, last));
		}
		
		this.appendChild(document.createElement("BR"));

		f_dataPager._AddText(this, "[");

		f_dataPager._AddButton(this, 
			this, 
			this.className+"_pfirst", 
			resourceBundle.f_get("FIRST"), 
			resourceBundle.f_get("FIRST_TOOLTIP"), 
			(first>0)?0:-1);

		f_dataPager._AddText(this, "/");
		var idx = first - rows;
		if (idx < 0) {
			idx = 0;
		}

		f_dataPager._AddButton(this, 
			this, 
			this.className+"_pprev", 
			resourceBundle.f_get("PREVIOUS"), 
			resourceBundle.f_get("PREVIOUS_TOOLTIP"), 
			(first>0)?idx:-1);

		f_dataPager._AddText(this, "]  ");
		
		cls=this.className+"_pgoto";
		
		var selectedPage = Math.floor(first / rows);
		var nbPage;
		if (rowCount<0) {				
			nbPage = Math.floor((maxRows+rows-1) / rows)+1;
		} else {
			nbPage = Math.floor((rowCount+rows-1) / rows);
		}
		
		var showPage = nbPage;
		if (showPage > 3 * 2 + 1) {
			showPage = 3 * 2 + 1;
		}
		
		var pageOffset = 0;
		if (showPage < nbPage) {
			pageOffset = selectedPage - Math.floor(showPage / 2);
			if (pageOffset + showPage > nbPage) {
				pageOffset = nbPage - showPage;
			}
			
			if (pageOffset < 0) {
				pageOffset = 0;
			}
		}
		
	
		
		for (var i = 0; i < showPage; i++) {
			if (i > 0) {
				f_dataPager._AddText(this, ", ");
			}

			var pi = pageOffset + i;
			
			var tooltipKey="INDEX_TOOLTIP";
			var label=(pi+1);
			if (rowCount<0 && pi+1==nbPage) {
				label="...";
				tooltipKey="UNKNOWN_INDEX_TOOLTIP";
			}

			var tooltipIndex=resourceBundle.f_format(tooltipKey, pi+1);
	
			f_dataPager._AddButton(this, 
				this, 
				cls, 
				label, 
				tooltipIndex,
				(pi == selectedPage)?-1:(pi * rows));
		}

		f_dataPager._AddText(this, " [");
					
		idx = first + rows;

		var nextIndex=-1;
		var lastIndex=-1;
		
		if (rowCount>=0) {			
			if (idx + rows > rowCount) {
				idx = (rowCount - ((rowCount+rows-1) % rows))-1;
				if (idx < 0) {
					idx = 0;
				}
			}
		
			if (idx>first) {
				nextIndex=idx;
				lastIndex=(rowCount - ((rowCount+rows-1) % rows))-1;
			}
		} else {
			if (idx+rows<=maxRows) {
				lastIndex=(maxRows - ((maxRows+rows-1) % rows))-1;
			}
			
			nextIndex=idx;
		}
							
		f_dataPager._AddButton(this, 
			this, 
			this.className+"_pnext", 
			resourceBundle.f_get("NEXT"), 
			resourceBundle.f_get("NEXT_TOOLTIP"), 
			nextIndex);

		f_dataPager._AddText(this, "/");

		f_dataPager._AddButton(this, 
			this, 
			this.className+"_plast", 
			resourceBundle.f_get("LAST"), 
			resourceBundle.f_get("LAST_TOOLTIP"), 
			lastIndex);

		f_dataPager._AddText(this, "]");
		
	},
	/**
	 * @method hidden ????
	 * Pas utilisé !
	f_dataComponentUpdated: function(dataComponent) {
		this.f_dataComponentInitialized(dataComponent);
	},
	*/
	/**
	 * @method private
	 */
	_f_installButtons: function(tag) {
		if (tag==null) {
			return;
		}
		if (tag.tagName=="A") {
			tag.href=f_core.JAVASCRIPT_VOID;
			
			tag.onclick=f_dataPager._PositionSelect;
			tag.f_link=this;
		}
	},
	/**
	 * @method hidden
	 */
	f_changePosition: function(index) {
		this._dataComponent.f_setFirst(index);
	}
}

var f_dataPager=new f_class("f_dataPager", null, __static, __prototype, f_component);
