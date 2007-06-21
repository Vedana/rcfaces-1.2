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
	 * @field private static final number
	 */
	_BUTTONS_UPDATE_TIMER: 1000,
	
	/**
	 * @field private static Array[]
	 */
	_TextEditors: undefined,
	
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
	},
	/**
	 * @method hidden static
	 * @param String textEditorId
	 * @param f_textEditorButton button
	 * @return void
	 */
	RegisterTextEditorButton: function(textEditorId, button) {
		f_core.Debug(f_textEditor, "RegisterTextEditorButton: register button '"+button.id+"' for editor '"+textEditorId+"'.");
		
		var textEditors=f_textEditor._TextEditors;
		if (!textEditors) {
			textEditors=new Object;
			f_textEditor._TextEditors=textEditors;
		}
		
		var buttons=textEditors[textEditorId];
		if (!buttons) {
			buttons=new Array;
			textEditors[textEditorId]=buttons;
		}
		
		buttons.push(button);
	},
	/**
	 * @method public static
	 * @return void
	 */
	Finalizer: function() {
		f_textEditor._TextEditors=undefined;
	},
	
	/**
	 * @method hidden static
	 * @param String textEditorId
	 * @param f_textEditorButton button
	 * @return void
	 */
	PerformCommand: function(textEditorId, button, parameter) {
		f_core.Assert(typeof(textEditorId)=="string", "f_textEditor.PerformCommand: Invalid textEditorId parameter ("+textEditorId+")");
		f_core.Assert(button && button.f_getType, "f_textEditor.PerformCommand: Invalid button parameter ("+button+")");
		
		var textEditors=f_textEditor._TextEditors;
		if (!textEditors) {
			f_core.Debug(f_textEditor, "PerformCommand: No registred text editors.");
			return;
		}

		var buttons=textEditors[textEditorId];
		if (!buttons) {
			f_core.Debug(f_textEditor, "PerformCommand: No buttons for textEditor '"+textEditorId+"'.");
			return;
		}
		
		var textEditor=buttons._textEditor;
		if (textEditor===undefined) {
			textEditor=f_core.GetElementByClientId(textEditorId);
			buttons._textEditor=textEditor;
		}
		
		if (!textEditor) {
			f_core.Error(f_textEditor, "PerformCommand: Can not find textEditor '"+textEditorId+"'.");
			return;
		}
		
		textEditor._performButtonCommand(button.f_getType(), parameter);
	},
	_OnFocus: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var win;
		if (!this.nodeType) {
			// Le this est la window !
			win=f_core.GetWindow(evt.srcElement);
			
		} else {
			win=f_core.GetWindow(this);
		}
		
		var textEditor=win.frameElement;
		
		return textEditor._onFocus(evt);
	},
	_OnBlur: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var win;
		if (!this.nodeType) {
			// Le this est la window !
			win=f_core.GetWindow(evt.srcElement);
			
		} else {
			win=f_core.GetWindow(this);
		}
		
		var textEditor=win.frameElement;
		
		return textEditor._onBlur(evt);
	}
}

var __prototype = {
	f_textEditor: function() {
		this.f_super(arguments);
	},
	f_finalize: function() {
		this.onload=null; // function
		
		var contentDocument=this._contentDocument;
		if (contentDocument) {
			this._contentDocument=undefined;
			
			contentDocument.designMode="off";

			f_core.RemoveEventListener(contentDocument, "focus", f_textEditor._OnFocus);
			f_core.RemoveEventListener(contentDocument, "blur", f_textEditor._OnBlur);			
		}

		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			
			window.clearInterval(timerId);
		}
		
		this._contentWindow=undefined; // Window
//		this._mimeType=undefined; // String
		// this._focused=undefined; // boolean
		
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
		
		f_core.AddEventListener(contentDocument, "focus", f_textEditor._OnFocus);
		f_core.AddEventListener(contentDocument, "blur", f_textEditor._OnBlur);
		
		contentDocument.designMode="on";
		
		this.f_updateButtons();
	},
	_onFocus: function(jsEvent) {
		f_core.Debug(f_textEditor, "_onFocus: Get focus");
		
		if (this._focused) {
			return;
		}
		this._focused=true;
		
		var self=this;
		this._timerId=window.setInterval(function() {
			try {
				self.f_updateButtons();

			} catch (x) {
				f_core.Debug(f_textEditor, "_onFocus.timer: Exception into updateButtons method.", x);				
			}
			
		}, f_textEditor._BUTTONS_UPDATE_TIMER);
		
	},
	_onBlur: function(jsEvent) {
		f_core.Debug(f_textEditor, "_onFocus: Lost focus");		
		
		if (!this._focused) {
			return;
		}
		this._focused=undefined;
		
		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			
			window.clearInterval(timerId);
		}
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
		
		command=command.charAt(0).toUpperCase()+command.substring(1);
		
		contentDocument.execCommand(command, false, param);
	},
	_queryCommandState: function(command, param) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return null;
		}
		
	//	command=command.charAt(0).toUpperCase()+command.substring(1);
	
//		f_core.Debug(f_textEditor, "_queryCommandState: Query command: '"+command+"' parameter='"+param+"'.");
		
		var ret=contentDocument.queryCommandState(command, false, param);

//		f_core.Debug(f_textEditor, "_queryCommandState: Query command: '"+command+"' => "+ret);
		
		return ret;
	},
	_queryCommandEnabled: function(command) {
		var contentDocument=this._contentDocument;
		if (!contentDocument) {
			return null;
		}
		
	//	command=command.charAt(0).toUpperCase()+command.substring(1);
	
//		f_core.Debug(f_textEditor, "_queryCommandEnabled: Query command: '"+command+"'.");
		
		var ret= contentDocument.queryCommandEnabled(command);

//		f_core.Debug(f_textEditor, "_queryCommandEnabled: Query command: '"+command+"' => "+ret);
		
		return ret;
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
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_updateButtons: function() {
		var textEditors=f_textEditor._TextEditors;
		if (!textEditors) {
			f_core.Debug(f_textEditor, "f_updateButtons: No registred text editors.");
			return;
		}

		var buttons=textEditors[this.id];
		if (!buttons) {
			f_core.Debug(f_textEditor, "f_updateButtons: No buttons for textEditor '"+this.id+"'.");
			return;
		}
		
		for(var i=0;i<buttons.length;i++) {
			var type=buttons[i].f_getType();
			
			switch(type) {
			case "bold":
			case "italic":
			case "underline":
			case "subscript":
			case "superscript":
			case "justifyleft":
			case "justifycenter":
			case "justifyright":
			case "justifyfull":
			case "strikethrough":
				buttons[i].f_setSelected(this._queryCommandState(type));
				break;
				
			case "undo":
			case "redo":
			case "indent":
			case "outdent":
			case "copy":
			case "cut":
			case "paste":
			case "decreasefontsize":
			case "increasefontsize":
			case "insertorderedlist":
			case "insertunorderedlist":
				buttons[i].f_setDisabled(!this._queryCommandEnabled(type));
				break;
			}
		}
	},
	_performButtonCommand: function(buttonType, param) {
		f_core.Assert(typeof(buttonType)=="string", "f_textEditor._performButtonCommand: Invalid buttonType parameter ("+buttonType+")");
		
		var update=false;
		
		switch(buttonType) {
		case "bold":
		case "italic":
		case "underline":
		case "subscript":
		case "superscript":
		case "justifyleft":
		case "justifycenter":
		case "justifyright":
		case "justifyfull":
		case "strikethrough":
		case "undo":
		case "redo":
		case "indent":
		case "outdent":
		case "copy":
		case "cut":
		case "paste":
		case "decreasefontsize":
		case "increasefontsize":
		case "insertorderedlist":
		case "insertunorderedlist":
			update=true;
			this._execCommand(buttonType);
			break;		
		}
		
		if (update) {
			this.f_updateButtons();
		}
		
		var contentWindow=this._contentWindow;
		if (contentWindow) {
			contentWindow.focus();
		}
	}
}

new f_class("f_textEditor", null, __static, __prototype, f_component);
