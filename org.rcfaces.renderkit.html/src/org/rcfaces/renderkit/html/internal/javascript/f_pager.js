/*
 * $Id$
 */

/**
 * @class public f_pager extends f_component, fa_pager
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
			buttonClass+="_disabled";
			
		} else {
			button=document.createElement("A");
			button._index=index;
			button.href=f_core.JAVASCRIPT_VOID;
			button.onclick=f_pager._PositionSelect;
			button.onkeydown=f_pager._PositionKey;
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
			
			f_pager._SearchButtons(list, child);
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

	f_pager: function() {
		this.f_super(arguments);
		
		this._for=f_core.GetAttribute(this, "v:for");
		
		var zeroMessage;
		var oneMessage;
		var manyMessage;

		var message=f_core.GetAttribute(this, "v:message");
		if (message) {
			zeroMessage=f_core.GetAttribute(this, "v:zeroResultMessage");
			oneMessage=f_core.GetAttribute(this, "v:oneResultMessage");
			manyMessage=f_core.GetAttribute(this, "v:manyResultMessage");
		
		} else {
			var resourceBundle=f_resourceBundle.Get(f_pager);
			
			message=resourceBundle.f_get("MESSAGE");			
			zeroMessage=resourceBundle.f_get("ZERO_RESULT_MESSAGE");			
			oneMessage=resourceBundle.f_get("ONE_RESULT_MESSAGE");			
			manyMessage=resourceBundle.f_get("MANY_RESULTS_MESSAGE");			
		}
		
		this._message=message;
		this._zeroMessage=(zeroMessage!==undefined)?zeroMessage:message;
		this._oneMessage=(oneMessage!==undefined)?oneMessage:message;
		this._manyMessage=(manyMessage!==undefined)?manyMessage:message;
		
		var noPagedMessage=f_core.GetAttribute(this, "v:noPagedMessage");
		if (!noPagedMessage) {
			var resourceBundle=f_resourceBundle.Get(f_pager);
			
			noPagedMessage=resourceBundle.f_get("NO_PAGED_MESSAGE");			
		}
		this._noPagedMessage=noPagedMessage;
		
/*
		f_core.Debug(f_pager, "Message='"+this._message+"'");
		f_core.Debug(f_pager, "ZeroMessage='"+this._zeroMessage+"'");
		f_core.Debug(f_pager, "OneMessage='"+this._oneMessage+"'");
		f_core.Debug(f_pager, "ManyMessage='"+this._manyMessage+"'");
		f_core.Debug(f_pager, "NoPagedMessage='"+this._noPagedMessage+"'");
*/
		if (this._for) {
			fa_pagedComponent.RegisterPager(this._for, this);

		} else  {
			f_core.Error(f_pager, "'for' attribute is not defined !");
		}
	},
	f_finalize: function() {
		this._destroyButtons();
		
		this._pagedComponent=undefined; // f_pagedComponent
		// this._for=undefined; // string
		// this._message=undefined; // string
		// this._zeroMessage=undefined; // string
		// this._oneMessage=undefined; // string
		// this._manyMessage=undefined; // string
		// this._noPagedMessage=undefined; // string
		
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
			// button._index=undefined; // number
			
			f_core.VerifyProperties(button);			
		}			
	},
	
	/* ****************************************************************** */
	
	/*
	fa_pagedComponentInitialized0: function(dataComponent) {
		this._pagedComponent=dataComponent;
			
		var className=this.className;
		
		var children=this.childNodes;
		if (children) {
			this._destroyButtons();

			while (this.hasChildNodes()) {
		    	this.removeChild(this.lastChild);
			}
		}
		
		var resourceBundle=f_resourceBundle.Get(f_pager);
		
		var rows=dataComponent.f_getRows();
		if (rows<1) {
			f_pager._AddSpan(this, "f_pager_title_found", resourceBundle.f_get("NO_PAGE"));
			return;
		}
		
		var cls=className+"_title_found";
		
		var rowCount=dataComponent.f_getRowCount();
		var first=dataComponent.f_getFirst();
		var maxRows=dataComponent.f_getMaxRows();

		f_core.Debug("f_pager", "Update pager values :  rowCount="+rowCount+" first="+first+" maxRows="+maxRows);

		if (rowCount<0) {
			f_pager._AddSpan(this, cls, resourceBundle.f_get("UNKNOWN_ENTRY_COUNT"));

		} else if (rowCount==0) {
			f_pager._AddSpan(this, cls, resourceBundle.f_get("NO_RESULT"));
			return;
			
		} else if (rowCount==1) {
			f_pager._AddSpan(this, cls, resourceBundle.f_get("ONE_RESULT"));

		} else {
			f_pager._AddSpan(this, cls, resourceBundle.f_format("N_RESULTS", rowCount));
		}
		
		f_pager._AddText(this, ", ");
		
		if (rowCount<0) {
			f_pager._AddSpan(this, cls, resourceBundle.f_format("SUMMARY_NO_END", first+1));
			
		} else {
			var last=first+rows;
			if (last>=rowCount) {
				last=rowCount;
			}
			f_pager._AddSpan(this, cls, resourceBundle.f_format("SUMMARY", first+1, last));
		}
		
		this.appendChild(document.createElement("BR"));

		f_pager._AddText(this, "[");

		this.f_appendFirstButton(this, first, className+"_pfirst", resourceBundle);

		f_pager._AddText(this, "/");

		this.f_appendPrevButton(this, first, rows, className+"_pprev", resourceBundle);

		f_pager._AddText(this, "]  ");
		
		this.f_appendPagesButtons(this, first, rows, rowCount, maxRows,className+"_pgoto", resourceBundle, parameter);
	
		f_pager._AddText(this, "  [");
	
		this.f_appendNextButton(this, first, rows, rowCount, maxRows, className+"_pnext", resourceBundle);

		f_pager._AddText(this, "/");

		this.f_appendLastButton(this, first, rows, rowCount, maxRows, className+"_plast", resourceBundle);

		f_pager._AddText(this, "]");
	},
	*/
	fa_pagedComponentInitialized: function(dataComponent) {
		this._pagedComponent=dataComponent;
		
		var component=this;
		var className=this.className;
		
		var children=this.childNodes;
		if (children) {
			this._destroyButtons();

			while (this.hasChildNodes()) {
		    	this.removeChild(this.lastChild);
			}
		}
	
		var className=this.className;
		var resourceBundle;
		var message;

		var rows=dataComponent.f_getRows();
		var rowCount=dataComponent.f_getRowCount();
		var first=dataComponent.f_getFirst();
		var maxRows=dataComponent.f_getMaxRows();

		if (rows<1) {
			message=this._noPagedMessage;

		} else {
			if (rowCount<0) {
				message=this._manyMessage;
				
			} else if (rowCount==0) {
				message=this._zeroMessage;
	
			} else if (rowCount==1) {
				message=this._oneMessage;
			}
		}
		
		if (message==undefined) {
			message=this._message;
		}
		
		f_core.Debug(f_pager, "Format message '"+message+"' rows="+rows+" rowCount="+rowCount+" first="+first+" maxRows="+maxRows);
		
		var span="";
		for(var i=0;i<message.length;) {
			var c=message.charAt(i++);
			if (c=="{") {
				var end=message.indexOf("}", i);
				var varName=message.substring(i, end).toLowerCase();		
				i=end+1;
				
				if (span) {
					this._appendSpan(this, span);
					span="";
				}
				
				if (!resourceBundle) {
					resourceBundle=f_resourceBundle.Get(f_pager);
				}
				
				var parameter=undefined;
				var pvar=varName.indexOf(':');
				if (pvar>=0) {
					parameter=varName.substring(pvar+1);
					varName=varName.substring(0, pvar);
				}
				
				switch(varName) {
				case "first":
				case "position":
					this.f_appendFirstValue(component, first+1, className+"_vfirst");
					
					break;
					
				case "pageposition":
					if (rows>0) {
						this.f_appendFirstValue(component, Math.floor(first/rows)+1, className+"_vpageposition");
					}
					
					break;
					
				case "last":
					var last=first+rows;
					if (rowCount>0 && last>=rowCount) {
						last=rowCount;
					} else if (maxRows>0 && last>=maxRows) {
						last=maxRows;
					}
					this.f_appendLastValue(component, last, className+"_vlast");
					break;
					
				case "rowcount":
					if (rowCount>=0) {
						this.f_appendRowCountValue(component, rowCount, className+"_vrowCount");
					}
					break;
					
				case "pagecount":
					if (rowCount>=0 && rows>0) {
						this.f_appendRowCountValue(component, Math.floor(((rowCount-1)/rows)+1), className+"_vpageCount");
					}
					break;
					
				case "bfirst":
					this.f_appendFirstButton(component, first, className+"_pfirst", resourceBundle);
					break;

				case "bprev":
					this.f_appendPrevButton(component, first, rows, className+"_pprev", resourceBundle);
					break;
					
				case "bnext":		
					this.f_appendNextButton(component, first, rows, rowCount, maxRows, className+"_pnext", resourceBundle);
					break;
				
				case "blast":
					this.f_appendLastButton(component, first, rows, rowCount, maxRows, className+"_plast", resourceBundle);
					break;
				
				case "bpages":
					this.f_appendPagesButtons(component, first, rows, rowCount, maxRows,className+"_pgoto", resourceBundle, parameter);
					break;
					
				default:
					f_core.Error(f_pager, "Unknown pager message button '"+varName+"'.");
				}
				
				continue;	
			} else if (c=="\'") {
				for(var j=i;;) {
					var end=message.indexOf("'", j);
					if (end<0) {
						span+=message.substring(j);
						i=message.length;
						break;
					}
							
					if (message[end+1]=="\'") {
						span+=message.substring(j, end)+"'";
						j=end+2;
						continue;
					}
					
					span+=message.substring(j, end);
					i=end+1;
					break;
				}
				continue;
			}
			
			span+=c;
		}
		
		this._appendSpan(component, span);
	},
	/**
	 * @method private
	 */
	_appendSpan: function(component, message) {
		if (!message) {
			return;
		}
		var idx=0;
		for(;;) {
			var next=message.indexOf('\n', idx);
			if (next<0) {
				f_pager._AddText(component, message.substring(idx));
				break;
			}
			
			if (idx+1<next) {
				f_pager._AddText(component, message.substring(idx, next));
			}
			
			component.appendChild(document.createElement("BR"));
			
			idx=next+1;
		}
	},
	/**
	 * @method protected
	 */
	f_appendRowCountValue: function(component, rowCount, cls) {
		f_pager._AddSpan(component, cls, rowCount);
	},
	/**
	 * @method protected
	 */
	f_appendFirstValue: function(component, first, cls) {
		f_pager._AddSpan(component, cls, first);
	},
	/**
	 * @method protected
	 */
	f_appendLastValue: function(component, last, cls) {
		f_pager._AddSpan(component, cls, last);
	},
	/**
	 * @method protected
	 */
	f_appendFirstButton: function(component, first, cls, resourceBundle) {
		f_pager._AddButton(this, 
			component, 
			cls, 
			resourceBundle.f_get("FIRST"), 
			resourceBundle.f_get("FIRST_TOOLTIP"), 
			(first>0)?0:-1);
	},
	/**
	 * @method protected
	 */
	f_appendPrevButton: function(component, first, rows, cls, resourceBundle) {
		var idx = first - rows;
		if (idx < 0) {
			idx = 0;
		}

		f_pager._AddButton(this, 
			component, 
			cls, 
			resourceBundle.f_get("PREVIOUS"), 
			resourceBundle.f_get("PREVIOUS_TOOLTIP"), 
			(first>0)?idx:-1);
	},
	/**
	 * @method protected
	 */
	f_appendNextButton: function(component, first, rows, rowCount, maxRows, cls, resourceBundle) {			
		var idx = first + rows;

		var nextIndex=-1;
		
		if (rowCount>=0) {			
			if (idx + rows > rowCount) {
				idx = (rowCount - ((rowCount+rows-1) % rows))-1;
				if (idx < 0) {
					idx = 0;
				}
			}
		
			if (idx>first) {
				nextIndex=idx;
			}
		} else {
			nextIndex=idx;
		}
							
		f_pager._AddButton(this, 
			component, 
			cls, 
			resourceBundle.f_get("NEXT"), 
			resourceBundle.f_get("NEXT_TOOLTIP"), 
			nextIndex);
	},
	/**
	 * @method protected
	 */
	f_appendLastButton: function(component, first, rows, rowCount, maxRows, cls, resourceBundle) {
		var idx = first + rows;

		var lastIndex=-1;
		
		if (rowCount>=0) {			
			if (idx + rows > rowCount) {
				idx = (rowCount - ((rowCount+rows-1) % rows))-1;
				if (idx < 0) {
					idx = 0;
				}
			}
		
			if (idx>first) {
				lastIndex=(rowCount - ((rowCount+rows-1) % rows))-1;
			}
		} else if (idx==maxRows) {
			lastIndex=maxRows;
			
		} else if (idx<maxRows) {
			lastIndex=(maxRows - ((maxRows+rows-1) % rows))-1;
		}
							
		f_pager._AddButton(this, 
			component, 
			cls, 
			resourceBundle.f_get("LAST"), 
			resourceBundle.f_get("LAST_TOOLTIP"), 
			lastIndex);
	},
	
	/**
	 * @method protected
	 */
	f_appendPagesButtons: function(component, first, rows, rowCount, maxRows, cls, resourceBundle, separator) {
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
		
		var sep=null;
		
		if (separator) {
			var ss=separator.split(';');
			for(var i=0;i<ss.length;i++) {
				var s=ss[i];
				var p="";
				var ep=s.indexOf('=');
				if (ep>=0) {
					p=s.substring(ep+1);
					s=s.substring(0, ep);
				}
				
				switch(s) {
				case "separator":
					sep=p;
					break;
				}
			}
		}
		
		if (sep===null) {
			sep=", ";
		}
		
		for (var i = 0; i < showPage; i++) {
			if (i > 0) {
				f_pager._AddText(component, sep);
			}

			var pi = pageOffset + i;
			
			var tooltipKey="INDEX_TOOLTIP";
			var label=(pi+1);
			if (rowCount<0 && pi+1==nbPage) {
				label="...";
				tooltipKey="UNKNOWN_INDEX_TOOLTIP";
			}

			var tooltipIndex=resourceBundle.f_format(tooltipKey, pi+1);
	
			f_pager._AddButton(this, 
				component, 
				cls, 
				label, 
				tooltipIndex,
				(pi == selectedPage)?-1:(pi * rows));
		}
		
	},
	/**
	 * @method hidden ????
	 * Pas utilisé !
	f_pagedComponentUpdated: function(dataComponent) {
		this.fa_pagedComponentInitialized(dataComponent);
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
			
			tag.onclick=f_pager._PositionSelect;
			tag.f_link=this;
		}
	},
	/**
	 * @method hidden
	 */
	f_changePosition: function(index) {
		this._pagedComponent.f_setFirst(index);
	}
}

var f_pager=new f_class("f_pager", null, __static, __prototype, f_component, fa_pager);
