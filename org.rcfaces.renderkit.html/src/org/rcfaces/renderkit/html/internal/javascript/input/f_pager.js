/*
 * $Id$
 */

/**
 * @class public f_pager extends f_component, fa_pager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
	
	/**
	 * @method private static
	 */
	_AddSpan: function(container, spanClass, text) {		
		return f_core.CreateElement(container, "span", {className: "f_pager_value f_pager_value_"+spanClass, textNode: text });
	},	

	/**
	 * @method private static
	 */
	_AddText: function(container, text) {
		f_core.AppendChild(container, container.ownerDocument.createTextNode(text));	
	},
	
	/**
	 * @method private static
	 */
	_AddButton: function(dataPager, container, buttonClass, text, tooltip, index, parameters) {
		if (parameters) {
			if (parameters["label"]) {
				text=parameters["label"];
			}
			if (parameters["tooltip"]) {
				tooltip=parameters["tooltip"];
			}
		}

		var button;
		
		var doc=dataPager.ownerDocument;
		
		var suffix="";
		if (index===undefined || index<0) {
			button=doc.createElement("span");
			suffix+="_disabled";
			
		} else {
			button=doc.createElement("a");
			button._index=index;
			button.href=f_core.JAVASCRIPT_VOID;
			button.onclick=f_pager._PositionSelect;
			button.onkeydown=f_pager._PositionKey;
			button.f_link=dataPager;
		}
		
		var cls="f_pager_button f_pager_button_"+buttonClass;
		
		if (suffix) {
			cls+=" f_pager_button"+suffix+" f_pager_button_"+buttonClass+suffix;
		}
		
		if (button.className!=cls) {
			button.className=cls;
		}
		if (tooltip) {
			button.title=tooltip;
		}
		
		f_core.AppendChild(button, doc.createTextNode(text));
		
		f_core.AppendChild(container, button);
		
		var buttons=dataPager._buttons;
		if (!buttons) {
			buttons=new Array;
			dataPager._buttons=buttons;
		}
		button.id=dataPager.id+"::"+buttons.length;
		buttons.push(button);
		
		return button;
	},
	
	/**
	 * @method private static
	 */
	_SearchButtons: function(list, parent) {
		var children=parent.childNodes;
		if (!children || !children.length) {
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
	 * @context object:dataPager
	 */
	_PositionKey: function(evt) {
		var dataPager=this.f_link;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (dataPager.f_getEventLocked(evt)) {
			return false;
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
			return f_core.CancelJsEvent(evt);
		}
		return true;
	},
	
	/**
	 * @method private static
	 * @context object:dataPager
	 */
	_PositionSelect: function(evt) {
		var dataPager=this.f_link;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (dataPager.f_getEventLocked(evt)) {
			return false;
		}

		var v_index=f_core.GetAttribute(this, "v:index");		
		if (typeof(v_index)!="number") {
			v_index=this._index;
		
			if (typeof(v_index)!="number") {
				return false;
			}
		}
		
		dataPager.f_changePosition(v_index);
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method hidden static
	 */
	Create: function(parent, refComponent, forId, styleClass) {
		
		var properties={
			id: refComponent.id+":pager",
			className: "f_pager",
			"v:for": forId
		};
		
		f_dataGridPopup.CopyProperties(properties, refComponent, 
			"v:message", "v:zeroResultMessage", "v:oneResultMessage", "v:manyResultMessage", "v:manyResultMessage2");

		
		if (styleClass) {
			properties["className"]+=" "+styleClass;
		}		
		
		var pager=f_core.CreateElement(parent, "div", properties);

		f_class.Init(pager, f_pager, [parent]);
		
		pager.f_completeComponent();		
		
		return pager;		
	}
}
 
var __members = {

	f_pager: function() {
		this.f_super(arguments);
		
		this._for=f_core.GetAttribute(this, "v:for");
		
		var zeroMessage;
		var oneMessage;
		var manyMessage;
		var manyMessage2;

		var message=f_core.GetAttribute(this, "v:message");
		if (message) {
			zeroMessage=f_core.GetAttribute(this, "v:zeroResultMessage");
			oneMessage=f_core.GetAttribute(this, "v:oneResultMessage");
			manyMessage=f_core.GetAttribute(this, "v:manyResultMessage");
			manyMessage2=f_core.GetAttribute(this, "v:manyResultMessage2");
		
		} else {
			var resourceBundle=f_resourceBundle.Get(f_pager);
			
			message=resourceBundle.f_get("MESSAGE");			
			zeroMessage=resourceBundle.f_get("ZERO_RESULT_MESSAGE");			
			oneMessage=resourceBundle.f_get("ONE_RESULT_MESSAGE");			
			manyMessage=resourceBundle.f_get("MANY_RESULTS_MESSAGE");			
			manyMessage2=resourceBundle.f_get("MANY_RESULTS_MESSAGE2");			
		}
		
		this._message=message;
		this._zeroMessage=(zeroMessage!==undefined)?zeroMessage:message;
		this._oneMessage=(oneMessage!==undefined)?oneMessage:message;
		this._manyMessage=(manyMessage!==undefined)?manyMessage:message;
		this._manyMessage2=(manyMessage2!==undefined)?manyMessage2:this._manyMessage;
		
		var noPagedMessage=f_core.GetAttribute(this, "v:noPagedMessage", null);
		if (noPagedMessage===null) {
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
		f_core.Debug(f_pager, "ManyMessage2='"+this._manyMessage2+"'");
*/
		if (this._for) {
			fa_pagedComponent.RegisterPager(this._for, this);

		} else  {
			f_core.Error(f_pager, "f_pager: 'for' attribute is not defined !");
		}
	},
	f_finalize: function() {

		fa_pagedComponent.UnregisterPager(this);

		this._destroyButtons();
		
		this._pagedComponent=undefined; // f_pagedComponent
		// this._for=undefined; // string
		// this._message=undefined; // string
		// this._zeroMessage=undefined; // string
		// this._oneMessage=undefined; // string
		// this._manyMessage=undefined; // string
		// this._manyMessage2=undefined; // string
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
	/**
	 * @method protected
	 * @return HTMLElement
	 */
	f_getFocusableElement: function() {
		var buttons=this._buttons;
		if (!buttons) {
			return;
		}
		
		return buttons[0];
	},
	
	/* ****************************************************************** */
	fa_pagedComponentInitialized: function(dataComponent) {
		this._pagedComponent=dataComponent;
		
		var component=this;
		
		var oldVisibility=this.style.visibility;
		if (!oldVisibility) {
			oldVisibility="inherit";
		}
		
		try {
			this.style.visibility="hidden";
			
			var children=this.childNodes;
			if (children) {
				this._destroyButtons();
	
				while (this.hasChildNodes()) {
			    	this.removeChild(this.lastChild);
				}
			}
		
			var fragment=component.ownerDocument.createDocumentFragment();
		
			var resourceBundle;
			var message;
	
			var rows=dataComponent.f_getRows();
			var rowCount=dataComponent.f_getRowCount();
			var first=dataComponent.f_getFirst();
			var maxRows=dataComponent.f_getMaxRows();
			var paged=dataComponent.f_isPaged();
	
			if (this._noPagedMessage && (rows<1 || !paged)) {
				message=this._noPagedMessage;
	
			} else {
				if (rowCount<0) {
					if (first+rows<maxRows) {
						message=this._manyMessage2;
						
					} else {
						message=this._manyMessage;
					}
					
				} else if (rowCount==0) {
					message=this._zeroMessage;
		
				} else if (rowCount==1) {
					message=this._oneMessage;
				}
			}
			
			if (message==undefined) {
				message=this._message;
			}
			
			f_core.Debug(f_pager, "fa_pagedComponentInitialized: Format message '"+message+"' rows="+rows+" rowCount="+rowCount+" first="+first+" maxRows="+maxRows);
			
			var span=null;
			for(var i=0;i<message.length;) {
				var c=message.charAt(i++);
				if (c=="{") {
					var end=message.indexOf("}", i);
					var varName=message.substring(i, end).toLowerCase();		
					i=end+1;
					
					if (span && span.length) {
						this._appendSpan(fragment, span.join(""));
						span=null;
					}
					
					if (!resourceBundle) {
						resourceBundle=f_resourceBundle.Get(f_pager);
					}
					
					var parameters=undefined;
					var pvar=varName.indexOf(':');
					if (pvar>=0) {
						var parameter=varName.substring(pvar+1);
						varName=varName.substring(0, pvar);
						
						parameters=new Object();
						
						var ss=parameter.split(';');
						for(var j=0;j<ss.length;j++) {
							var s=ss[j];
							var p="";
							var ep=s.indexOf('=');
							if (ep>=0) {
								p=s.substring(ep+1);
								s=s.substring(0, ep);
							}
							
							parameters[s]=p;
						}
					}
					
					switch(varName) {
					case "first":
					case "position":
						this.f_appendFirstValue(fragment, first+1, "first");
						
						break;
						
					case "pageposition":
						if (rows>0) {
							this.f_appendFirstValue(fragment, Math.floor(first/rows)+1, "pagePosition");
						}
						
						break;
						
					case "last":
						var last=first+rows;
						if (rowCount>0 && last>=rowCount) {
							last=rowCount;
						} else if (maxRows>0 && last>=maxRows) {
							last=maxRows;
						}
						this.f_appendLastValue(fragment, last, "last");
						break;
						
					case "rowcount":
						if (rowCount>=0) {
							this.f_appendRowCountValue(fragment, rowCount, "rowCount", parameters);
						}
						break;
						
					case "pagecount":
						if (rowCount>=0 && rows>0) {
							this.f_appendRowCountValue(fragment, Math.floor(((rowCount-1)/rows)+1), "pageCount", parameters);
						}
						break;
						
					case "bfirst":
						this.f_appendFirstButton(fragment, first, "first", resourceBundle, parameters);
						break;
	
					case "bprev":
						this.f_appendPrevButton(fragment, first, rows, "prev", resourceBundle, parameters);
						break;
						
					case "bnext":		
						this.f_appendNextButton(fragment, first, rows, rowCount, maxRows, "next", resourceBundle, parameters);
						break;
					
					case "blast":
						this.f_appendLastButton(fragment, first, rows, rowCount, maxRows, "last", resourceBundle, parameters);
						break;
					
					case "bpages":
						this.f_appendPagesButtons(fragment, first, rows, rowCount, maxRows,"goto", resourceBundle, parameters);
						break;
						
					default:
						f_core.Error(f_pager, "Unknown pager message button '"+varName+"'.");
					}
					
					continue;	
				} else if (c=="\'") {
					if (!span) {
						span=new Array;
					}
					for(var j=i;;) {
						var end=message.indexOf("'", j);
						if (end<0) {
							span.push(message.substring(j));
							i=message.length;
							break;
						}
								
						if (message.charAt(end+1)=="\'") {
							span.push(message.substring(j, end), "'");
							j=end+2;
							continue;
						}
						
						span.push(message.substring(j, end));
						i=end+1;
						break;
					}
					continue;
				}
				
				if (!span) {
					span=new Array;
				}
				span.push(c);
			}
			
			if (span && span.length) {
				this._appendSpan(fragment, span.join(""));
			}
			
			
			
		} finally {
		
			if (fragment) {
				f_core.AppendChild(component, fragment);
			}

			if (oldVisibility) {
				this.style.visibility=oldVisibility;
			}
		}
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
			
			f_core.AppendChild(component, component.ownerDocument.createElement("br"));
			
			idx=next+1;
		}
	},
	/**
	 * @method protected
	 */
	f_appendRowCountValue: function(component, rowCount, cls, parameters) {
		f_pager._AddSpan(component, cls, rowCount);
	},
	/**
	 * @method protected
	 */
	f_appendFirstValue: function(component, first, cls, parameters) {
		f_pager._AddSpan(component, cls, first);
	},
	/**
	 * @method protected
	 */
	f_appendLastValue: function(component, last, cls, parameters) {
		f_pager._AddSpan(component, cls, last);
	},
	/**
	 * @method protected
	 */
	f_appendFirstButton: function(component, first, cls, resourceBundle, parameters) {
		f_pager._AddButton(this, 
			component, 
			cls,
			resourceBundle.f_get("FIRST"), 
			resourceBundle.f_get("FIRST_TOOLTIP"), 
			(first>0)?0:-1, 
			parameters);
	},
	/**
	 * @method protected
	 */
	f_appendPrevButton: function(component, first, rows, cls, resourceBundle, parameters) {
		var idx = first - rows;
		if (idx < 0) {
			idx = 0;
		}

		f_pager._AddButton(this, 
			component, 
			cls, 
			resourceBundle.f_get("PREVIOUS"), 
			resourceBundle.f_get("PREVIOUS_TOOLTIP"), 
			(first>0)?idx:-1, 
			parameters);
	},
	/**
	 * @method protected
	 */
	f_appendNextButton: function(component, first, rows, rowCount, maxRows, cls, resourceBundle, parameters) {			
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
			nextIndex, parameters);
	},
	/**
	 * @method protected
	 */
	f_appendLastButton: function(component, first, rows, rowCount, maxRows, cls, resourceBundle, parameters) {
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
			lastIndex, 
			parameters);
	},
	
	/**
	 * @method protected
	 */
	f_appendPagesButtons: function(component, first, rows, rowCount, maxRows, cls, resourceBundle, parameters) {
		
		var maxPage=3 * 2 + 1;		
		var sep=null;
		
		if (parameters) {
			if (parameters["separator"]) {
				sep=parameters["separator"];
			}
			if (parameters["pages"]) {
				maxPage=parseInt(parameters["pages"], 10);
			}
		}

		var selectedPage = Math.floor(first / rows);
		var nbPage;
		if (rowCount<0) {				
			nbPage = Math.floor((maxRows+rows-1) / rows)+1;
		} else {
			nbPage = Math.floor((rowCount+rows-1) / rows);
		}
		
		var showPage = nbPage;
		if (showPage > maxPage) {
			showPage = maxPage;
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
		if (tag.tagName.toLowerCase()=="a") {
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

new f_class("f_pager", {
	extend: f_component,
	aspects: [ fa_pager ],
	statics: __statics,
	members: __members
});
