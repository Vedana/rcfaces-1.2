/*
 * $Id$
 */

/**
 * class f_expandBar
 *
 * @class public f_expandBar extends f_component, fa_disabled, fa_readOnly, fa_collapsed
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {

	/**
	 * @method private static
	 * @document this.ownerDocument
	 */
	_OnHeadOver: function() {
		this.className=this._className+"_over";
	},
	/**
	 * @method private static
	 * @document this.ownerDocument
	 */
	_OnHeadOut: function() {
		this.className=this._className;
	},
	/**
	 * @method private static
	 * @document this.ownerDocument
	 */
	_OnHeadClick: function(evt) {
		var expandBar=this._link;
		
		expandBar.f_onSelect(evt);
	}
}

var __prototype = {
	f_expandBar: function() {
		this.f_super(arguments);
		
		this._className=f_core.GetAttribute(this, "v:className", this.className);
		
		var groupName=f_core.GetAttribute(this, "v:groupName");
		if (groupName) {
			this._groupName=groupName;
			this.f_addToGroup(groupName, this);
		}

		var txt=null;
		var lis=this.getElementsByTagName("LI");
		var head=lis[0];
		if (head) {
			this._head=head;
			head._link=this;
			
			head.onmouseover=f_expandBar._OnHeadOver;
			head.onmouseout=f_expandBar._OnHeadOut;
			head._className=head.className;
		
			var text=f_core.GetFirstElementByTagName(head, "LABEL");
			if (text) {
				this._text=text;
				text._link=this;
				text.onclick=f_expandBar._OnHeadClick;
				
				txt=f_core.GetTextNode(text, true);
			}
		}
		
		var body=lis[1];
		if (body) {
			this._body=body;
			
			this._content=f_core.GetFirstElementByTagName(body, "DIV");
		}
			
		this._normalText=f_core.GetAttribute(this, "v:text", txt);
		this._collapsedText=f_core.GetAttribute(this, "v:collapsedText", txt);
	
	//	this._returnOnSelect=false;
		this.f_addEventListener(f_event.SELECTION, this._onSelect);
	},
	f_finalize: function() {
		// this._className=undefined; // String
		// this._normalText=undefined; // String
		// this._collapsedText=undefined; // String
	
		var effect=this._effect;
		if (effect) {
			this._effect=undefined;
		
			// On force la destruction !
			f_classLoader.Destroy(effect);
		}
	
		this._body=undefined;
		
		var content=this._content;
		if (content) {
			this._content=undefined; // HTMLDivElement
			f_core.VerifyProperties(content);
		}
			
		// this._groupName=undefined; // String		
		
		var head=this._head;
		if (head) {
			this._head=undefined; // HtmlLiElement
			
			head.onmouseover=null;
			head.onmouseout=null;
			head._link=undefined; // f_expandBar
			// head._className=undefined;
			
			f_core.VerifyProperties(head);
		}

		var text=this._text;
		if (text) {
			this._text=undefined; // HtmlLabelElement		
			text._link=undefined; // f_expandBar
			text.onclick=null;

			f_core.VerifyProperties(text);
		}

		var button=this._button;
		if (button) {
			this._button=undefined; // HtmlInputElement
			
			button.onclick=null;
			button.f_link=undefined; // f_expandBar
			
			f_core.VerifyProperties(button);
		}
		
		this.f_super(arguments);
	},
	f_setDomEvent: function(type, target) {
		var link=this.f_getButton();
		if (link) {
			switch(type) {
			case f_event.SELECTION: 
			case f_event.BLUR: 
			case f_event.FOCUS: 
			case f_event.KEYDOWN:
			case f_event.KEYUP:
				target=link;
				break;
			}
		}
						
		this.f_super(arguments, type, target);
	},
	/**
	 * @method protected
	 */
	f_getButton: function() {
		var button=this._button;
		if (button!==undefined) {
			return button;
		}
		
		button=f_core.GetFirstElementByTagName(this, "INPUT");
		if (button) {
			button.f_link=this;
		} else {
			button=null;
		}
		
		this._button=button;
		return button;
	},
	f_clearDomEvent: function(type, target) {
		var link=this._button;
		if (link) {
			switch(type) {
			case f_event.SELECTION:
			case f_event.BLUR:
			case f_event.FOCUS:
			case f_event.KEYDOWN:
			case f_event.KEYUP:
				target=link;
				break;
			}
		}
				
		this.f_super(arguments, type, target);
	},
	/** 
	 * @method private
	 */
	_onSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		
		this.f_setCollapsed(!this.f_isCollapsed());
		
		return false;
	},	
	/**
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}
		
		if (this.f_isDisabled()) {
			return false;
		}
				
		var cmp=this._button;
		if (!cmp) {
			cmp=this;
		}
		
		f_core.Debug(f_expandBar, "f_expandBar.f_setFocus of component '"+cmp+"' for expandBar '"+this.id+"'.");
		cmp.focus();
	},
	f_performAccessKey: function(evt) {
		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}

		var ret=this.f_fireEvent(f_event.SELECTION, evt);
		
		this.f_setFocus();
		
		return ret;
	},
	fa_updateCollapsed: function(set) {
		var body=this._body;
		
		if (!body) {	
			return;
		}
		var content=this._content;
		
		this.className=this._className+(set?"_collapsed":"");
		
		var effect=this.f_getEffect();
		
		var collapsedText=this._collapsedText;
		if (collapsedText && this._text) {
			if (!set) {
				collapsedText=this._normalText;
			}
			
			f_core.Debug(f_expandBar, "Change text to '"+collapsedText+"'.");
			
			f_core.SetTextNode(this._text, collapsedText, this._accessKey);
		}
		
		f_core.Debug(f_expandBar, "Call effect '"+effect+"'.");
		
		if (effect) {
			effect.f_performEffect(set);

		} else if (set) {
			content.className="f_expandBar_content_collapsed";
			body.className="f_expandBar_body_collapsed";
			
		} else  {
			content.className="f_expandBar_content";
			body.className="f_expandBar_body";
		}
		
		if (!set && this._groupName) {
			var p=this;
			
			function unselect(item) {
				if (item==p) {
					return;
				}
				
				if (item.f_isCollapsed && item.f_isCollapsed()) {
					return;
				}
				
				item.f_setCollapsed(true);
			}
	
			this.f_findIntoGroup(this._groupName, unselect);
		}
	},
	/**
	 * @method protected
	 * @return f_effect
	 */
	f_getEffect: function() {
		var effect=this._effect;
		if (effect!==undefined) {
			return effect;
		}
		
		var content=this._content;
		var body=this._body;		
		
		effect = f_core.GetAttribute(this, "v:effect");
		if (effect) {
			effect=f_core.CreateEffectByName(effect, content, function(value) {
				var contentClassName="f_expandBar_content";
				var bodyClassName="f_expandBar_body";
				
				if (value==0) {
					display="none";
					contentClassName+="_collapsed";
					bodyClassName+="_collapsed";
					
				} else if (value!=1) {
					contentClassName+="_effect";				
				}
				
				if (content.className!=contentClassName) {
					content.className=contentClassName;
				}
				if (body.className!=bodyClassName) {
					body.className=bodyClassName;
				}				
			});
		}
		if (!effect) {
			effect=null;
		}
		this._effect=effect;
		
		return effect;
	},
	/**
	 * Returns the group name of this expandBar, or <code>null</code> if it is not defined !
	 *
	 * @method public
	 * @return String The group name.
	 */
	f_getGroupName: function() {
		return this._groupName;
	},
	fa_getRadioScope: fa_groupName.GlobalScope,
	/**
	 * @method public
	 * @param String text Text to change.
	 * @return void
	 */
	f_setText: function(text) {
		f_core.Assert(typeof(text)=="string", "Invalid text parameter ! ('"+text+"')");

		if (this._normalText == text) {
			return;
		}
		this._normalText=text;

		if (!this.f_isCollapsed() && this._collapsedText) {
			var textLabel=this._text;
			if (textLabel) {
				f_core.SetTextNode(textLabel, text, this._accessKey);
			}
		}
				
		this.f_setProperty(f_prop.TEXT, text);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		return this._normalText;
	},
	/**
	 * @method public
	 * @param String text Text to change.
	 * @return void
	 */
	f_setCollapsedText: function(text) {
		f_core.Assert(typeof(text)=="string", "Invalid text parameter ! ('"+text+"')");

		if (this._collapsedText == text) {
			return;
		}
		this._collapsedText=text;

		if (this.f_isCollapsed()) {
			var textLabel=this._text;
			if (textLabel) {
				f_core.SetTextNode(textLabel, text, this._accessKey);
			}
		}
				
		this.f_setProperty(f_prop.COLLAPSED_TEXT, text);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getCollapsedText: function() {
		return this._collapsedText;
	},
	/**
	 * @method protected
	 * @return void
	 */	 
	f_parentShow: function() {
		this.f_setCollapsed(false);
		
		return this.f_super(arguments);		
	}
}
var f_expandBar=new f_class("f_expandBar", null, __static, __prototype, f_component, fa_disabled, fa_readOnly, fa_collapsed, fa_groupName);
