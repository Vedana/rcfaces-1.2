/*
 * $Id$
 */

/**
 * class f_expandBar
 *
 * @class public f_expandBar extends f_component, fa_disabled, fa_readOnly, fa_collapsed, fa_groupName
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {

	/**
	 * @field private static final String
	 */
	_HEAD_CLASSNAME: "f_expandBar_head",

	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:expandBar
	 */
	_OnHeadOver: function() {
		var expandBar=this._link;
		
		var cls=f_expandBar._HEAD_CLASSNAME;
		this.className=cls+" "+cls+"_over";
		
		return true;
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:expandBar
	 */
	_OnHeadOut: function() {
		var expandBar=this._link;

		this.className=f_expandBar._HEAD_CLASSNAME;
		
		return true;
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return boolean
	 * @context object:expandBar
	 */
	_OnHeadClick: function(evt) {
		var expandBar=this._link;
		
		expandBar.f_onSelect(evt);
		
		return true;
	}
}

var __members = {
	f_expandBar: function() {
		this.f_super(arguments);
		
		var groupName=f_core.GetAttribute(this, "v:groupName");
		if (groupName) {
			this._groupName=groupName;
			this.f_addToGroup(groupName, this);
		}

		var txt=null;
		var lis=this.getElementsByTagName("li");
		var head=lis[0];
		if (head) {
			this._head=head;
			head._link=this;
			
			head.onmouseover=f_expandBar._OnHeadOver;
			head.onmouseout=f_expandBar._OnHeadOut;
		
			var text=f_core.GetFirstElementByTagName(head, "label");
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
			
			this._content=f_core.GetFirstElementByTagName(body, "div");
		}
			
		this._normalText=f_core.GetAttribute(this, "v:text", txt);
		this._collapsedText=f_core.GetAttribute(this, "v:collapsedText", txt);
	
		this.f_insertEventListenerFirst(f_event.SELECTION, this._onSelect);
	},
	f_finalize: function() {
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
		
		button=f_core.GetFirstElementByTagName(this, "input");
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
	 * @method boolean
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
			return;
		}
				
		var cmp=this._button;
		if (!cmp) {
			cmp=this;
		}
		
		f_core.Debug(f_expandBar, "f_setFocus: focus component '"+cmp+"' for expandBar '"+this.id+"'.");
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

		var suffix="";
		if (set) {
			suffix+="_collapsed";
		}	
		
		var cls=this.f_computeStyleClass(suffix);
		if (cls!=this.className) {
			this.className=cls;
		}
		
		var effect=this.f_getEffect();
		
		var collapsedText=this._collapsedText;
		if (collapsedText && this._text) {
			if (!set) {
				collapsedText=this._normalText;
			}
			
			f_core.Debug(f_expandBar, "fa_updateCollapsed: Change text to '"+collapsedText+"'.");
			
			f_core.SetTextNode(this._text, collapsedText, this._accessKey);
		}
		
		f_core.Debug(f_expandBar, "fa_updateCollapsed: Call effect '"+effect+"'.");
		
		var suffix="";
		if (effect) {
			effect.f_performEffect(set);
			suffix=false;

		} else if (set) {
			suffix+="_collapsed";
		}
		
		if (suffix!==false) {
			var contentClassName="f_expandBar_content";
			var bodyClassName="f_expandBar_body";
			if (suffix) {
				contentClassName+=" "+contentClassName+"_collapsed";
				bodyClassName+=" "+bodyClassName+"_collapsed";
			}
			
			if (content.className!=contentClassName) {
				content.className=contentClassName;
			}
			if (body.className!=bodyClassName) {
				body.className=bodyClassName;
			}
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
				
				var suffix="";
				if (value==0) {
					display="none";
					suffix+="_collapsed";
					
				} else if (value!=1) {
					suffix+="_effect";				
				}
				
				var contentClassName="f_expandBar_content";
				var bodyClassName="f_expandBar_body";
				if (suffix) {
					contentClassName+=" "+contentClassName+suffix;
					bodyClassName+=" "+bodyClassName+suffix;
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
		f_core.Assert(typeof(text)=="string", "f_expandBar.f_setText: Invalid text parameter ! ('"+text+"')");

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
		f_core.Assert(typeof(text)=="string", "f_expandBar.f_setCollapsedText: Invalid text parameter ! ('"+text+"')");

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
	 * @return boolean
	 */	 
	f_parentShow: function() {
		this.f_setCollapsed(false);
		
		return this.f_super(arguments);		
	}
}
new f_class("f_expandBar", {
	extend: f_component,
	aspects: [ fa_disabled, fa_readOnly, fa_collapsed, fa_groupName ],
	statics: __statics,
	members: __members
});
