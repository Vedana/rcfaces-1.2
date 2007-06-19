/*
 * $Id$
 */

/**
 * f_textEditor class
 *
 * @class f_textEditor extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field private static final String[]
	 */
	_TEXT_STYLES: ["font-weight", "font-size", "font-family", "font-style", "text-decoration", "color", "background-color"],
	
	/**
	 * @method hidden static
	 */
	_OnLoad: function(textEditor) {
		try {
			f_textEditor.f_getClassLoader().f_init(textEditor);
			
			textEditor._onLoad();
			
		} catch (x) {			
			f_core.Error(f_textEditor, "_OnLoad: load exception on textEditor "+textEditor.id, x);
		}
	}
}

var __prototype = {
	f_textEditor: function() {
		this.f_super(arguments);
	},
	f_finalize: function() {
		this.onload=null; // function
		
		this._contentDocument=undefined; // Document
		this._contentWindow=undefined; // Window
//		this._mimeType=undefined; // String
		
		// this._autoTab=undefined;  // boolean
		// this._requiredInstalled=undefined; // boolean
		
		this.f_super(arguments);
	},
	_onLoad: function() {
		f_core.Debug(f_textEditor, "_onLoad: Initialize textEditor");
		
		var contentWindow=this.contentWindow;
		this._contentWindow=contentWindow;

		var contentDocument;
		if (f_core.IsInternetExplorer()) {
			contentDocument=contentWindow.document;
			
		} else {
			contentDocument=this.contentDocument;
		}
		
		this._contentDocument=contentDocument;
		
		this._mimeType=f_core.GetAttribute(this, "v:mimeType");
		
		var text=f_core.GetAttribute(this, "v:text");
		if (text) {
			this.f_setText(text);
		}
		
		contentDocument.designMode="on";
	},
	f_update: function() {
		
		this.f_super(arguments);
	},
	f_serialize: function() {
		var contentDocument=this._contentDocument;	
		if (contentDocument) {
			this.f_setProperty(f_prop.TEXT, this.f_getText());
		}
		
		this.f_super(arguments);
	},
	f_getText: function() {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return null;
		}
		
		switch(this._mimeType) {
		case "text/plain":
			return f_core.GetTextNode(contentDocument.body);
			
		default:
			return contentDocument.body.innerHTML;
		}		
	},
	/**
	 * @method public
	 * @param String text
	 * @return void
	 */
	f_setText: function(text) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return;
		}

		switch(this._mimeType) {
		case "text/plain":
			f_core.SetTextNode(contentDocument.body, text);
			return;
			
		default:
			contentDocument.body.innerHTML=text;				
		}
	},
	/**
	 * @method public
	 * @param optional number range
	 * @return void
	 */
	f_toggleBold: function(range) {
		this._execCommand("Bold", range);
	},
	/**
	 * @method public
	 * @param optional number range
	 * @return void
	 */
	f_toggleItalic: function(range) {
		this._execCommand("Italic", range);
	},
	/**
	 * @method public
	 * @param optional number range
	 * @return void
	 */
	f_toggleUnderline: function(range) {
		this._execCommand("Underline", range);
	},
	/**
	 * @method private
	 * @param String command
	 * @param optional number range
	 * @param optional param
	 * @return void
	 */
	_execCommand: function(command, range, param) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return;
		}
		
		if (typeof(range)=="number") {
			f_core.SelectText(contentDocument.body, range, range);

		} else if ((range instanceof Array) && range.length>1) {
			f_core.SelectText(contentDocument.body, range[0], range[1]);			
		}
		
		contentDocument.execCommand(command, false, param);
	},
	_queryCommand: function(command, param) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return null;
		}
		
		return contentDocument.queryCommand(command, false, param);
	},
	/**
	 * @method public
	 * @return Object
	 */
	f_computeStyle0: function(position) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return null;
		}
		
		var element=null;
		var style=null;	

		if (f_core.IsInternetExplorer()) {
			element=contentDocument.body.createTextRange().parentElement;

			if (element) {
				style=component.currentStyle;
			}

		} else if (contentDocument.createRange) {
			var selection=this._contentWindow.getSelection();
			
			if (selection && selection.rangeCount) {
				var range=selection.getRangeAt(0);
				
				var element=range.startContainer;
				for(;element.nodeType==3;element=element.parentNode);
				
				alert("Element= "+element.tagName+" "+element.offsetWidth);
				
				var computedStyle=contentDocument.defaultView.getComputedStyle(element, '');

				style=new Object;				
				var styles=f_textEditor._TEXT_STYLES;
				for(var i=0;i<styles.length;i++) {
					var styleName=styles[i];
					style[styleName]=computedStyle.getPropertyValue(styleName);
				}
			}
			
			selection.dettach();
		}
		
		f_core.Debug(f_textEditor, "f_computeStyle: element="+element.id+" style="+style);
		
		return style;
	},
	/**
	 * @method public
	 * @return Object
	 */
	f_computeStyle: function(position) {
		var style= {
			bold: this._queryCommandState("Bold", position),
			italic: this._queryCommandState("Italic", position),
			underline: this._queryCommandState("Underline", position)
		}
		
		return style;
	}
}

new f_class("f_textEditor", null, __static, __prototype, f_component);
