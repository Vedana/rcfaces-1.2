/*
 * $Id$
 */

/**
 * <p><strong>f_shellDecorator</strong> represents shell decorator.
 *
 * @class public f_shellDecorator
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field hidden static final String
	 */
	TITLE_DECORATOR: "title",
	
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleButton_onmousedown: function(evt) {
		var button=this;
		var shellDecorator=button._shellDecorator;
		
		if (this._selected) {
			return;
		}
		
		this._selected=true;
		
		shellDecorator._updateTitleButton(button);
	},
	
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleButton_onmouseup: function(evt) {
		var button=this;
		var shellDecorator=button._shellDecorator;
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
		
		if (!this._selected) {
			return;
		}
		
		this._selected=false;
		
		shellDecorator._updateTitleButton(button);
		shellDecorator._performTitleButton(button, evt);		
	},
	
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleButton_onmouseover: function(evt) {
		var button=this;
		var shellDecorator=button._shellDecorator;
		
		if (this._over) {
			return;
		}
		
		this._over=true;
		
		shellDecorator._updateTitleButton(button);
	},
	
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleButton_onmouseout: function(evt) {
		var button=this;
		var shellDecorator=button._shellDecorator;
		
		if (!this._over) {
			return;
		}
		
		this._over=false;
		this._selected=false;
		
		shellDecorator._updateTitleButton(button);
		
	}
}

var __members = {
	
	/**
	 * @field private final f_shell
	 */
	_shell: undefined,
	
	f_shellDecorator: function(shell) {
		this.f_super(arguments);
		
		this._shell=shell;
		this._decorationValues=new Object;
	},
	f_finalize: function() {		
		this._shell=undefined; // f_shell
		
		var iframe=this._iframe;
		if (iframe) {	
			this._iframe=undefined; // HtmlIFrame
			
			iframe._shell=undefined;
			
			f_core.VerifyProperties(iframe);
		}
		
		this._decorationValues=undefined; // Map<String,any>
		this._shellBody=undefined; //HtmlElement
		this._title=undefined; // HtmlElement
		// this._blankImageURL=undefined; // String
		
		var buttons=this._buttons;
		if (buttons) {
			this.buttons=undefined;
			
			for(var name in buttons) {
				var button=buttons[name];
				
				this.f_clearButton(button);
			}
		}
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @param HTMLElement button
	 * @return void
	 */
	f_clearButton: function(button) {
//		button._over=undefined; // boolean
//		button._selected=undefined; // boolean
//		button._className=undefined; // String
//		button._eventName=undefined; // String
//		button._name=undefined; // String
		button._shellDecorator=undefined; // f_shellDecorator		
		button.onmousedown=null;
		button.onmouseup=null;
		button.onmouseover=null;
		button.onmouseout=null;		
	},
	/**
	 * @method public
	 * @param optional number width
	 * @param optional number height
	 * @return Object
	 */
	f_computeTrim: function(width, height) {
		var shell=this._shell;
		
		if (width===undefined) {
			width=shell.f_getWidth();
		}
		
		if (height===undefined) {
			height=shell.f_getHeight();
		}
		
//		width+=2; // border body_cell
//		height+=2; // border body_cell
		
//		width+=2; // border
//		height+=2; // border
		
		if (shell._style & (f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE)) {
			height+=20; // Le titre
			
			height+=1; // Border title
		}
		
		if (shell._style & f_shell.RESIZE_STYLE) {
			width+=2; // Les bords
			height+=2;
		}		
		
		return { width: width, height: height };
	},
	
	f_createDecoration: function(functionWhenReady) {
		f_core.Assert(!this._iframe, "f_shellDecorator.f_createDecoration: Invalid state, iframe is not null ! ("+this._iframe+")");

		f_core.Debug(f_shellDecorator, "f_createDecoration: create new decoration");

		var iframe = document.createElement("iframe");
		this._iframe=iframe;

		iframe.id = "iframe_"+this._shell.f_getId();
		iframe.name = iframe.id+"_name";
		iframe._shell=this._shell;

		iframe.frameBorder = 0;
		iframe.scrolling="no";
		
		var className="f_shellDecorator_frame";
		if (this._shell._style & f_shell.TRANSPARENT) {
			if (f_core.IsInternetExplorer(f_core.INTERNET_EXPLORER_6)) {
				iframe.allowTransparency = true;
			}
			className+=" "+className+"_transparent";

		} else {
			className+=" "+className+"_border";			
		}
		
		iframe.className=className;
				
		var self=this;
		
		if (f_core.IsInternetExplorer()) {
			f_core.Debug(f_shell, "f_constructIFrame: IE use onreadystatechange ");
			iframe.onreadystatechange=function() {
				f_core.Debug(f_shellDecorator, "f_createDecoration.readyStateChange: decoration created: "+this+" state="+this.readyState);

				if (this.readyState != "interactive") {
					return;
				}	
				
				this.onreadystatechange=null;
				
				self.f_decorateFrame(this);
				
				functionWhenReady.call(window, self, self._shell);
			};
			
		} else {
			f_core.Debug(f_shell, "f_constructIFrame: Firefox use onload ");
			iframe.onload=function() {
				f_core.Debug(f_shellDecorator, "f_createDecoration.onLoad: decoration created: "+this);
	
				this.onload=null;
				
				self.f_decorateFrame(this);
				
				functionWhenReady.call(window, self, self._shell);
			};
		}

		iframe.src="about:blank";
		
		document.body.insertBefore(iframe, document.body.firstChild);
		
		iframe.src="about:blank";
		f_core.Debug(f_shellDecorator, "f_createDecoration: wait decoration creation");
	},
	
	f_destroyDecoration: function() {
		f_core.Assert(this._iframe, "f_shellDecorator.f_destroyDecoration: Invalid state, iframe is null !");
		
		var iframe=this._iframe;
		this._iframe=undefined;
		
		iframe.parentNode.removeChild(iframe);

		this._shell.f_setStatus(f_shell.CLOSED_STATUS);
	},
	
	f_getShellBody: function() {
		f_core.Assert(this._iframe, "f_shellDecorator.f_getShellBody: Invalid state, iframe is null !");

		return this._shellBody;
	},
	f_setDecorationValue: function(key, value) {
		this._decorationValues[key]=value;

		if (!this._iframe) {
			return;
		}
		
		switch(key) {
		case f_shellDecorator.TITLE_DECORATOR:
			if (this._title) {
				f_core.SetTextNode(this._title, value);
			}
			break;
		}	
	},
	f_decorateFrame: function(iframe) {
		f_core.Debug(f_shellDecorator, "f_decorateFrame: decorate frame="+iframe.contentWindow+" document="+iframe.contentWindow.document);
		
		var style=this._shell._style;
		var decoration=style & (f_shell.COPY_STYLESHEET | f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE | f_shell.RESIZE_STYLE);
		
		var shellDocument=iframe.contentWindow.document;
		
		var body=shellDocument.body;
		body.topmargin=0;
		body.leftmargin=0;
		body.marginheight=0;
		body.marginwidth=0;
	
		var className="f_shellDecorator_body";
		if (style && f_shell.TRANSPARENT) {
			className+=" "+className+"_transparent";
		}
		
		var shellStyleClass=this._shell.f_getStyleClass();
		if (shellStyleClass) {
			className+=" "+shellStyleClass;
		}
		body.className=className;	

		if (style & f_shell.COPY_STYLESHEET) {
			// Copie le BASE href
			
			var head=shellDocument.head;
			if (!head) {
				var root=shellDocument.documentElement;
				
				var head=f_core.GetFirstElementByTagName(root, "head");		
				if (!head) {
					head=f_core.CreateElement(root, "head");					
				}
			}
						
			var baseHref=document.baseURI || document.URL;
			
			if (!f_core.GetFirstElementByTagName(head, "base")) {
				f_core.CreateElement(head, "base", {
					href: baseHref
				});
			}
		}
		
		f_core.CopyStyleSheets(shellDocument, document);
		
		if (!decoration) {
			this._shellBody=shellDocument.body;
			return;
		}
					
		var table=shellDocument.createElement("table");
		table.cellPadding=0;
		table.cellSpacing=0;
		table.style.width="100%";
		table.style.height="100%";
		
		if (style & f_shell.TRANSPARENT) { 
			table.className="f_shellDecorator_background_tranparent";
		} else {
			table.className="f_shellDecorator_background";
		}

		shellDocument.body.appendChild(table);		

		var tbody=shellDocument.createElement("tbody");
		table.appendChild(tbody);
	
		if (style & (f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE)) {
			
			var tr=shellDocument.createElement("tr");
			tr.className="f_shellDecorator_title";
			tbody.appendChild(tr);

			var td=shellDocument.createElement("td");
			td.className="f_shellDecorator_title_cell";
			tr.appendChild(td);
			
			this.f_createTitle(td);
		}
		
		var tr=shellDocument.createElement("tr");
		tr.className="f_shellDecorator_body";
		tbody.appendChild(tr);			
		
		var td=shellDocument.createElement("td");
		td.className="f_shellDecorator_body_cell";
		tr.appendChild(td);
		
		this._shellBody=td;
	},
	f_showShell: function() {

		if (!this._iframe) {
			return;
		}

		this._iframe.style.visibility="visible";		
	},
	f_hideShell: function() {

		if (!this._iframe) {
			return;
		}

		this._iframe.style.visibility="hidden";		
	},
	/**
	 * @method protected
	 * @param HTMLElement td
	 * @return void
	 */
	f_createTitle: function(parent) {
			
		var style=this._shell._style;
		if (style & f_shell.CLOSE_STYLE) {
			var tooltip=f_resourceBundle.Get(f_shell).f_get("CLOSE_TITLE_BUTTON_TOOLTIP");
			
			this.f_addTitleButton(parent, "close", "f_shellDecorator_close", tooltip, f_shell.CLOSE_BUTTON_EVENT);
		}
	
		var title=this._decorationValues[f_shellDecorator.TITLE_DECORATOR];
		if (title) {
			this._title=f_core.CreateElement(parent, "div", {
				className: "f_shellDecorator_title_text",
				textNode: title
			});
		}		
	},
	/**
	 * @method protected
	 * @param String name
	 * @param String className
	 * @return void
	 */
	f_addTitleButton: function(parent, name, className, tooltip, eventName) {

		var blankImageURL=this._blankImageURL;
		if (!blankImageURL) {
			blankImageURL=f_env.GetBlankImageURL();
			
			this._blankImageURL=blankImageURL;
		}

		var img=f_core.CreateElement(parent, "img", {
			className: className,
			src: blankImageURL,
			title: tooltip
		});
		
		img.onmousedown=f_shellDecorator._TitleButton_onmousedown;
		img.onmouseup=f_shellDecorator._TitleButton_onmouseup;
		img.onmouseover=f_shellDecorator._TitleButton_onmouseover;
		img.onmouseout=f_shellDecorator._TitleButton_onmouseout;
		img._shellDecorator=this;
		img._className=className;
		img._eventName=eventName;
		img._name=name;
		
		if (!this._buttons) {
			this._buttons=new Object;
		}
		
		this._buttons[name]=className;
	},
	f_setShellBounds: function(shell, x, y, width, height) {
		
		var iframe=this._iframe;
		if (!iframe) {
			return;
		}
		
		// Def pos and size
		iframe.style.top = y+"px";
		iframe.style.left = x+"px";
		iframe.style.height = height+"px";
		iframe.style.width = width+"px";
		
		var table=iframe.contentWindow.document.body; //.firstChild;
		if (table) {
			table.style.height = height+"px";
			table.style.width = width+"px";			
		}
	},
	/**
	 * @method hidden
	 * @param Object target
	 * @return boolean
	 */
	f_isIntoShell: function(target) {

		var iframe=this._iframe;
		if (!iframe) {
			return false;
		}
		
		var targetDocument=null;
		
		switch(target.nodeType) {
		case f_core.DOCUMENT_NODE:
			targetDocument=target;
			break;
			
		case f_core.ELEMENT_NODE:
			if (target.tagName.toLowerCase()=="iframe") {
				targetDocument=target.contentWindow.document;
				break;
			}

			targetDocument=target.ownerDocument;
			break;

		default:
			// Qu'est que c'est ????
			
			targetDocument=target.document;
		}

		if (!targetDocument) {
			f_core.Debug(f_shellManager, "f_isIntoShell: type de noeud "+target.nodeType);
			return false;			
		}
 		
 		var frameDocument = iframe.contentWindow.document;

     	if (targetDocument==frameDocument) {
 			f_core.Debug(f_shellManager, "f_isIntoShell: Same document ("+targetDocument.location+"/"+frameDocument.location+")");
     		return true;
     	}
     	
		f_core.Debug(f_shellManager, "f_isIntoShell: Different document ("+targetDocument.location+"/"+frameDocument.location+")");
     	return false;
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_setFocus: function() {
		var iframe=this._iframe;
		if (!iframe) {
			return;
		}

		iframe.contentWindow.focus();
		this._shell.f_setFocus();
	},
	/**
	 * @method private
	 * @param Object button
	 * @return void
	 */
	_updateTitleButton: function(button) {
		var className=button._className;
		
		if (button._selected) {
			className+=" "+className+"_selected";
			if (button._over) {
				className+="_over";
			}
			
		} else if (button._over) {
			className+=" "+className+"_over";		
		}
		
		if (button.className!=className) {
			button.className=className;
		}
	},
	/**
	 * @method private
	 * @param Object button
	 * @param Event jsEvent
	 * @return boolean
	 */
	_performTitleButton: function(button, jsEvent) {
		var shell=this._shell;
	
		if (button._eventName) {	
			var event=new f_event(shell, button._eventName, jsEvent, button, button._name);
			try {
				if (shell.f_fireEvent(event)===false) {
					return false;
				}
				
			} finally {
				f_classLoader.Destroy(event);
			}
		}
		
		switch(button._name) {
		case "close":
			shell.f_close();
			break;
		}		
	}
}


new f_class("f_shellDecorator", {
	extend: f_object,
	statics: __statics,
	members: __members
});
