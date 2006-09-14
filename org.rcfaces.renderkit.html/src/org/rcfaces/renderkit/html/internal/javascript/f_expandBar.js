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
	 * @field private static final string
	 */
	_COLLAPSED_IMAGE_URL: "/expandBar/arrow_collapsed.gif",

	/**
	 * @field private static final string
	 */
	_EXPANDED_IMAGE_URL: "/expandBar/arrow_expanded.gif",

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
		
		var groupName=f_core.GetAttribute(this, "v:groupName");
		if (groupName) {
			this._groupName=groupName;
			this._addToGroup(groupName, this);
		}

		var head=f_core.GetFirstElementByTagName(this, "LI");
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
	
		var button=f_core.GetFirstElementByTagName(this, "INPUT");
		if (button) {
			this._button=button;
			button.f_link=this;
		}

		var body=f_core.GetFirstElementByTagName(this, "DIV");
		if (body) {
			this._body=body;
			body._oldDisplay=body.style.display;
			if (body.parentNode.style.display=="none") {
				body.style.display="none";
			}
		}
	
	//	this._returnOnSelect=false;
		this.f_addEventListener(f_event.SELECTION, this._onSelect);
	},
	f_finalize: function() {
		var effect=this._effect;
		if (effect) {
			// On force la destruction !
			f_classLoader.Destroy(effect);
			
			this._effect=undefined;
		}
	
		this._body=undefined;
		this._groupName=undefined;
		
		var head=this._head;
		if (head) {
			this._head=undefined;
			
			head.onmouseover=null;
			head.onmouseout=null;
			head._link=undefined;
			head._className=undefined;
			
			f_core.VerifyProperties(head);
		}

		var text=this._text;
		if (text) {
			this._text=undefined;			
			text._link=undefined;
			text.onclick=null;

			f_core.VerifyProperties(text);
		}

		var button=this._button;
		if (button) {
			this._button=undefined;
			
			button.onclick=null;
			button.f_link=undefined;
			
			f_core.VerifyProperties(button);
		}
		
		this.f_super(arguments);
	},
	f_setDomEvent: function(type, target) {
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
	_a_updateCollapsed: function(set) {
	
		var body=this._body;
		
		if (!body) {	
			return;
		}
		
		var button=this._button;
		if (button) {
			var imageURL=f_env.GetStyleSheetBase()+ this.f_getButtonImageURL(set);
		
			button.src=imageURL;	
		}
		
		var effect=this._effect;
		if (effect===undefined) {
			effect = f_core.GetAttribute(this, "v:effect");
			if (effect) {
				effect=f_core.CreateEffectByName(effect, body, function(value) {
					var display=body._oldDisplay;
					if (value==0) {
						display="none";
					}
					
					var parent=body.parentNode;
					if (display==parent.style.display) {
						return;
					}
					
					parent.style.display=display;
				});
			}
			if (!effect) {
				effect=false;
			}
			this._effect=effect;
		}
		
		if (effect) {
			effect.f_performEffect(set);

		} else if (set) {
			body.style.display="none";
			
		} else  {
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
	
			this._findIntoGroup(this._groupName, unselect);
		}
	},
	/**
	 * @method protected
	 */
	f_getButtonImageURL: function(collapsed) {
		if (collapsed) {
			return f_expandBar._COLLAPSED_IMAGE_URL;
		}
		
		return f_expandBar._EXPANDED_IMAGE_URL;
	},
	/**
	 * Returns the group name of this expandBar, or <code>null</code> if it is not defined !
	 *
	 * @method public
	 * @return string The group name.
	 */
	f_getGroupName: function() {
		return this._groupName;
	},
	_a_getRadioScope: fa_groupName.GlobalScope	
}
var f_expandBar=new f_class("f_expandBar", null, __static, __prototype, f_component, fa_disabled, fa_readOnly, fa_collapsed, fa_groupName);
