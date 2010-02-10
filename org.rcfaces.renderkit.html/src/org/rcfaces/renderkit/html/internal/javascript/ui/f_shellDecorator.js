/*
 * $Id$
 */

/**
 * <p><strong>f_shellDecorator</strong> represents shell decorator.
 *
 * @class public abstract f_shellDecorator extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
		
	/**
	 * @field protected static final Number
	 */
	_DEFAULT_TITLE_HEIGHT: 20,
	
	/**
	 * @field hidden static final String
	 */
	TITLE_DECORATOR: "title",
	
	/**
	 * @field hidden static final String
	 */
	INSTANCE_DECORATOR: "instance",
	
	/**
	 * @field private static number
	 */
	_ShellIdentifier: 0,
	
	/**
	 * @field private static Object
	 */
	_FrameShells: undefined,
	
	
	
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
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleMove_onmousedown: function(evt) {
		var shellDecorator=this._shellDecorator;
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
/* ????
		if (dataGrid.f_getEventLocked(evt)) {
			return false;
		}
*/
	
		var iframe=shellDecorator._iframe;
		if (!iframe) {
			return false;
		}

		var shellDocument=iframe.contentWindow.document;
		
		f_shellDecorator._decoratorDragged=shellDecorator;

	 	f_core.CancelJsEvent(evt);

		var eventPos=f_core.GetJsEventPosition(evt, shellDocument);
		var cursorPos=f_core.GetAbsolutePosition(shellDecorator._titleMoveButton);
		
		shellDecorator._dragDeltaX=eventPos.x-cursorPos.x;
		shellDecorator._dragDeltaY=eventPos.y-cursorPos.y;
		shellDecorator._dragOrigin=eventPos;
		
		f_core.AddEventListener(shellDocument, "mousemove", f_shellDecorator._TitleMove_dragMove, shellDocument.body);
		f_core.AddEventListener(shellDocument, "mouseup",   f_shellDecorator._TitleMove_dragStop, shellDocument.body);
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleMove_dragMove: function(evt) {
		var shellDecorator=f_shellDecorator._decoratorDragged;

		var iframe=shellDecorator._iframe;
		if (!iframe) {
			return false;
		}	

		var shellDocument=iframe.contentWindow.document;

		var eventPos=f_core.GetJsEventPosition(evt, shellDocument);
		var cursorPos=f_core.GetAbsolutePosition(shellDecorator._titleMoveButton);
		
		var deltaX=eventPos.x-cursorPos.x-shellDecorator._dragDeltaX;
		var deltaY=eventPos.y-cursorPos.y-shellDecorator._dragDeltaY;
		
		var x=parseInt(iframe.style.left)+deltaX;
		var y=parseInt(iframe.style.top)+deltaY;
		
		var w=iframe._initialWidth;
		var h=iframe._initialHeight;
		
		var screenSize=f_shellManager.GetScreenSize(iframe.ownerDocument);
		if (f_core.IsGecko()) {
			screenSize.width-=f_core.ComputeBorderLength(iframe, "left", "right");
			screenSize.height-=f_core.ComputeBorderLength(iframe, "top", "bottom");
		}
		
		if (x+w>=screenSize.width) {
			w=screenSize.width-x-1;
			if (w<1) {
				w=1;
			}
		}
		if (w!=parseInt(iframe.style.width)) {
			iframe.style.width=w+"px";
		}

		if (y+h>=screenSize.height) {
			h=screenSize.height-y-1;
			if (h<1) {
				h=1;
			}
		}			
		if (h!=parseInt(iframe.style.height)) {
			iframe.style.height=h+"px";
		}
		
		iframe.style.left=x+"px";
		iframe.style.top=y+"px";
	},
	/**
 	 * @method private static
	 * @param Event evt
	 * @return void
	 * @context object:shellDecorator
	 */
	_TitleMove_dragStop: function(evt) {
		var shellDecorator=f_shellDecorator._decoratorDragged;
		f_shellDecorator._decoratorDragged=undefined; // f_shellDecorator
		
		var iframe=shellDecorator._iframe;
		if (!iframe) {
			return;
		}	

		var shellDocument=iframe.contentWindow.document;

		f_core.RemoveEventListener(shellDocument, "mousemove", f_shellDecorator._TitleMove_dragMove, shellDocument.body);
		f_core.RemoveEventListener(shellDocument, "mouseup",   f_shellDecorator._TitleMove_dragStop, shellDocument.body);
		
	},
	/**
	 * @method hidden static 
	 * @param number shellIdentifier
	 * @return f_shell
	 */
	GetShellFromIdentifier: function(shellIdentifier) {
		var frameShells=f_shellDecorator._FrameShells;
		if (!frameShells) {
			return null;
		}
		return frameShells[shellIdentifier];
	},
	Finalizer: function() {
		f_shellDecorator._decoratorDragged=undefined; // f_shellDecorator
		f_shellDecorator._FrameShells=undefined; // Map<id, shell>
	}
}

var __members = {
	
	/**
	 * @field protected final f_shell
	 */
	_shell: undefined,
	
	f_shellDecorator: function(shell) {
		this.f_super(arguments);
		
		this._shell=shell;
		this._decorationValues=new Object;
	},
	f_finalize: function() {		
		this._shell=undefined; // f_shell
		
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
		
		var titleMoveButton=this._titleMoveButton;
		if (titleMoveButton) {
			this._titleMoveButton=undefined;
					
			try {
				titleMoveButton._shellDecorator=undefined;

				titleMoveButton.onmousedown=null;
			} catch (x) {
				// Le composant peut ne plus être rattaché !
			}
		}
		
		this.f_super(arguments);
	},
	
	/**
	 * @method public abstract
	 * @return String
	 */
	 f_getId: f_class.ABSTRACT,
	
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
		
		var style=shell.f_getStyle();
		
		if (style & (f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE)) {
			height+=(this.f_getTitleHeight()+this.f_getBottomHeight()); // Le titre
			
			height+=1; // Border title
		}
		
		if (style & f_shell.RESIZE_STYLE) {
			width+=2; // Les bords
			height+=2;
		}
		
		return { width: width, height: height };
	},
	
	/**
	 * @method hidden abstract
	 * @param Function functionWhenReady
	 * @return void
	 */	 
	f_createDecoration: f_class.ABSTRACT,
	
	/**
	 * @method hidden
	 * @return void
	 */	 
	f_destroyDecoration: function() {
		this._shell.f_setStatus(f_shell.CLOSED_STATUS);
	},

	/**
	 * @method hidden
	 * @return HTMLElement
	 */
	f_getShellBody: function() {
		return this._shellBody;
	},
	/**
	 * @method hidden
	 * @param String key
	 * @param optional any value
	 * @return void
	 */
	f_setDecorationValue: function(key, value) {
		this._decorationValues[key]=value;
		
		switch(key) {
		case f_shellDecorator.TITLE_DECORATOR:
			if (this._title) {
				f_core.SetTextNode(this._title, value);
			}
			break;
		}	
	},
	/**
	 * @method public
	 * @reutrn void
	 */
	f_showShell: f_class.ABSTRACT,
	
	/**
	 * @method public
	 * @reutrn void
	 */
	f_hideShell: f_class.ABSTRACT,

	/**
	 * @method protected
	 * @param HTMLElement body of container
	 * @return HTMLElement
	 */
	f_decorateShell: function(body) {

		var style=this._shell.f_getStyle();		
		var decoration=style & (f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE | f_shell.RESIZE_STYLE);
		
		if (!decoration) {
			this._shellBody=body;
			return body;
		}
		
		var tbody=f_core.CreateElement(body, "table", {
			cellPadding: 0,
			cellSpacing: 0,
			cssWidth: "100%",
			cssHeight: "100%",
			className: (style & f_shell.TRANSPARENT)?"f_shellDecorator_background_tranparent":"f_shellDecorator_background"
		}, "tbody");
	
		if (style & (f_shell.TITLE_STYLE | f_shell.CLOSE_STYLE)) {
			var td=f_core.CreateElement(tbody, "tr", {
				className: "f_shellDecorator_title"	

			}, "td", {
				className: "f_shellDecorator_title_cell"
			});
			
			this.f_createTitle(td);
		}
		
		var td=f_core.CreateElement(tbody, "tr", {
			className: "f_shellDecorator_body"	

		}, "td", {
			className: "f_shellDecorator_body_cell"
		});
		
		this._shellBody=td;
		
		return td;
	},

	/**
	 * @method protected
	 * @param HTMLElement td
	 * @return void
	 */
	f_createTitle: function(parent) {
			
		var style=this._shell.f_getStyle();
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
		
		if (style & f_shell.MOVE_STYLE) {
			this._titleMoveButton=parent;
			
			parent._shellDecorator=this;
			parent.onmousedown=f_shellDecorator._TitleMove_onmousedown;
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
	/**
	 * @method hidden
	 * @param f_shell shell
	 * @param number x
	 * @param number y
	 * @param number width
	 * @param number height
	 * @return void
	 */
	f_setShellBounds: f_class.ABSTRACT,
	/**
	 * @method hidden
	 * @param Object target
	 * @return boolean
	 */
	f_isIntoShell: f_class.ABSTRACT,
	/**
	 * @method hidden
	 * @return void
	 */
	f_setFocus: function() {
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
		
		return true;
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_performViewResizeEvent: f_class.ABSTRACT,
	
	/**
	 * @method protected
	 * @param String shellIdentifier
	 * @param f_shell shell
	 * @return String
	 */
	f_registerShell: function(shell) {
		var shellIdentifier="rcfaces_shell_"+(f_shellDecorator._ShellIdentifier++);

		var frameShells=f_shellDecorator._FrameShells;
		if (!frameShells) {
			frameShells=new Object;
			f_shellDecorator._FrameShells=frameShells;
		}
		frameShells[shellIdentifier]=shell;
		
		return shellIdentifier;
	},
	
	/**
	 * @method protected
	 * @return Number
	 */
	f_getTitleHeight : function(){
		return f_shellDecorator._DEFAULT_TITLE_HEIGHT;
	},
	
	/**
	 * @method protected
	 * @return Number
	 */
	f_getBottomHeight : function(){
		return  0;
	}
}


new f_class("f_shellDecorator", {
	extend: f_object,
	statics: __statics,
	members: __members
});
