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
	 */
	_OnHeadOver: function() {
		this.className=this._className+"_over";
	},
	/**
	 * @method private static
	 */
	_OnHeadOut: function() {
		this.className=this._className;
	},
	/**
	 * @method private static
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


		var lis=this.getElementsByTagName("LI");

		var head=lis[0];
		if (head) {
			this._head=head;
			head._link=this;
			
			head.onmouseover=f_expandBar._OnHeadOver;
			head.onmouseout=f_expandBar._OnHeadOut;
			head._className=head.className;
		}
		
		var text=f_core.GetFirstElementByTagName(this, "LABEL");
		if (text) {
			this._text=text;
			text._link=this;
			text.onclick=f_expandBar._OnHeadClick;
		}

		var body=lis[1];
		if (body) {
			this._body=body;
			
			var content=f_core.GetFirstElementByTagName(body, "DIV");
			if (content) {
				this._content=content;
				content._oldDisplay=content.style.display;
				if (body.style.display=="none") {
					content.style.display="none";
				}
			}
		}
			
		this._normalText=f_core.GetAttribute(this, "v:text");
		this._collapsedText=f_core.GetAttribute(this, "v:collapsedText");
		
		var txt=this.f_getText();
		if (this._normalText) {
			this._collapsedText=txt;
			
		} else {
			this._normalText=txt;
		}
			
	
	//	this._returnOnSelect=false;
		this.f_addEventListener(f_event.SELECTION, this._onSelect);
	},
	f_finalize: function() {
		// this._className=undefined;
		// this._normalText=undefined;
		// this._collapsedText=undefined;
	
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
			
		// this._groupName=undefined;
		
		
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
		
		if (effect) {
			effect.f_performEffect(set);

		} else if (set) {
			content.className="f_expandBar_body_collapsed";
			body.style.display="none";
			
		} else  {
			content.className="f_expandBar_body";
			body.style.display="block";
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
	 * @return f_effect;
	 */
	f_getEffect: function() {
		var effect=this._effect;
		if (effect!==undefined) {
			return effect;
		}
		
		var content=this._content;
		
		effect = f_core.GetAttribute(this, "v:effect");
		if (effect) {
			effect=f_core.CreateEffectByName(effect, content, function(value) {
				var display=content._oldDisplay;
				var className="f_expandBar_body";
				
				if (value==0) {
					display="none";
					className+="_collapsed";
					
				} else if (value!=1) {
					className+="_effect";				
				}
				
				if (content.className!=className) {
					content.className=className;
				}
				
				if (display==body.style.display) {
					return;
				}
				
				body.style.display=display;
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
		var textLabel=this._text;
		if (!textLabel) {
			return;
		}

		if (this.f_getText() == text) {
			return;
		}

		// AccessKey ?
		f_core.SetTextNode(textLabel, text, this._accessKey);
		
		this.f_setProperty(f_prop.TEXT, text);
	},
	/**
	 * @method public
	 * @return String
	 */
	f_getText: function() {
		var textLabel=this._text;
		if (!textLabel) {
			return;
		}

		// Pas de <u> de l'accessKey ?
		return f_core.GetTextNode(textLabel, true);
	}	
}
var f_expandBar=new f_class("f_expandBar", null, __static, __prototype, f_component, fa_disabled, fa_readOnly, fa_collapsed, fa_groupName);
