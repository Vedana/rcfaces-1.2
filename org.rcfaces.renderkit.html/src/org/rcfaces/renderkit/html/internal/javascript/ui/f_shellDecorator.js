/*
 * $Id$
 */

/**
 * <p><strong>f_shellManager</strong> represents shell manager.
 *
 * @class public f_shellManager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field hidden static final String
	 */
	TITLE_DECORATOR: "title"
}

var __members = {
	f_shellDecorator: function(shell) {
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
		
		this.f_super(arguments);
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
		
		width+=2; // border body_cell
		height+=2; // border body_cell
		
		width+=2; // border
		height+=2; // border
		
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
		
		document.body.insertBefore(iframe, document.body.firstChild);
		
		iframe.src="about:blank";
		f_core.Debug(f_shellDecorator, "f_createDecoration: wait decoration creation");
	},
	
	f_destroyDecoration: function() {
		f_core.Assert(this._iframe, "f_shellDecorator.f_destroyDecoration: Invalid state, iframe is null !");
		
		var iframe=this._iframe;
		this._iframe=undefined;
		
		iframe.parentNode.removeChild(iframe);
	},
	
	f_getShellBody: function() {
		f_core.Assert(this._iframe, "f_shellDecorator.f_getShellBody: Invalid state, iframe is null !");

		return this._shellBody; //_iframe.contentWindow.document.body;
	},
	f_setDecorationValue: function(key, value) {
		this._decorationValues[key]=value;

		if (!this._iframe) {
			return;
		}
		
		switch(key) {
		case "title":
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
			
			var title=this._decorationValues[f_shellDecorator.TITLE_DECORATOR];
			if (title) {
				f_core.SetTextNode(td, title);
			}
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
			return;
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
	}
}


new f_class("f_shellDecorator", {
	extend: f_object,
	statics: __statics,
	members: __members
});
