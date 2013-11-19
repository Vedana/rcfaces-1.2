/*
 * $Id$
 */

/**
 * Class fsvg_image.
 * 
 * @class fsvg_image extends f_image, fa_items
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field public static final String
	 */
	SVG_XMLNS : "http://www.w3.org/2000/svg",
	
	XLINK_XMLNS: "http://www.w3.org/1999/xlink",
	
	/**
	 * @method private static
	 * @param Event event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnClick: function(evt) {
		var svg=this._svg;
		var link=this;
		var item=link;
		
		if (link._item) {
			item=link._item;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(fsvg_svg, "_ItemOnClick: perform event "+evt);
		
		if (svg.f_getEventLocked(evt)) {
			return false;
		}
	
		return svg._onClick(evt, item, link);
	},
	
	/**
	 * @method private static
	 * @param Event event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnFocus: function(evt) {
		var svg=this._svg;
		var link=this;
		var item=link;
		
		if (link._item) {
			item=link._item;
		}
	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(fsvg_svg, "_ItemOnFocus: perform event "+evt);
		
		if (svg.f_getEventLocked(evt)) {
			return false;
		}
	
		return svg._onFocus(evt, item, link);
	},
	
	/**
	 * @method private static
	 * @param Event event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnBlur: function(evt) {
		var svg=this._svg;
		var link=this;
		var item=link;
		
		if (link._item) {
			item=link._item;
		}
	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(fsvg_svg, "_ItemOnBlur: perform event "+evt);
		
		if (svg.f_getEventLocked(evt)) {
			return false;
		}
	
		return svg._onBlur(evt, item, link);
	}

}

var __members = {
	fsvg_svg: function() {
		this.f_super(arguments);
		
	},
	f_finalize: function() {
		var links=this._links;
		if (links) {
			this._links=undefined;
			
			for(var i=0;i<links.length;i++) {
				this._clearLink(links[i]);
			}
		}
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @param SVGElement link
	 * @return void
	 */
	_clearLink: function(link) {
		link._svg = undefined;
		link._item= undefined;
		link.onclick = null;
		link.onfocus = null;
		link.onblur = null;
	},
	/**
	 * @method public
	 * @return Document
	 */
	f_getSVGDocument : function() {
		return this.contentDocument;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getRootElement : function(id) {
		return this.f_getSVGDocument().rootElement;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getElementById : function(id) {
		return this.f_getSVGDocument().getElementById(id);
	},
	/**
	 * @method public
	 * @return Element
	 */
	$ : function(expr) {
		return this.f_querySelector(expr);
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_querySelector : function(expr) {
		return this.f_getSVGDocument().querySelector(expr);
	},
	/**
	 * @method public
	 * @return Element[]
	 */
	f_querySelectorAll : function(expr) {
		return this.f_getSVGDocument().querySelectorAll(expr);
	},
	f_setItemColor : function(item, color) {

	},
	fa_updateItemStyle : function(item) {
		var elt = this._getItemElement(item);
		if (!elt) {
			return;
		}

		var itemStyle = item.style;

		var itemColor = item._color;
		if (itemColor !== undefined && itemStyle.color != itemColor) {
			if (itemColor) {
				itemStyle.color = itemColor;
			} else {
				// remove style
			}
		}

	},
	_getItemElement : function(item) {
		var valueToElement = this._valueToElement;
		if (!valueToElement) {
			valueToElement = new Object();
			this._valueToElement = valueToElement;
		}

		var value = this.f_getItemValue(item);
		if (value === undefined) {
			return undefined;
		}

		var element = valueToElement[value];
		if (typeof (element) != "undefined") {
			return element;
		}

		element = this.f_getElementById(value);
		valueToElement[value] = element;

		return element;
	},
	_update : function(id, modifs) {
		var c = this.f_getElementById(id);
		if (!c) {
			return;
		}

		if (modifs._color !== undefined) {
			c.style.color = modifs._color;
		}
		if (modifs._fill !== undefined) {
			c.style.fill = modifs._fill;
		}
		if (modifs._styleClass !== undefined) {
			c.className = modifs._styleClass;
		}
		if (modifs._text !== undefined) {
			f_core.SetTextNode(c, modifs._text);
		}
		if (modifs._visibility !== undefined) {
			c.style.visibility = (modifs._visibility) ? "visible" : "hidden";
		}
		if (modifs._selectable) {
			var link=c;
			
			if (!c.tagName || c.tagName.toUpperCase()!="A") {
				var ah=c.ownerDocument.createElementNS(fsvg_svg.SVG_XMLNS, "a");
				
				var parentNode=c.parentNode;
				parentNode.insertBefore(ah, c);
				parentNode.removeChild(c);
				ah.appendChild(c);
				
				if (modifs._tooltipText) {
					var th=c.ownerDocument.createElementNS(fsvg_svg.SVG_XMLNS, "title");
				
					f_core.SetTextNode(th, modifs._tooltipText);
					
					ah.appendChild(th);
				}
				
				if (modifs._audioDescription) {
					ah.setAttributeNS(fsvg_svg.SVG_XMLNS, "title", modifs._audioDescription);				
				}
				
				ah._item=c;
				link=ah;
			}
			
			link._svg = this;
			link.onclick = fsvg_svg._ItemOnClick;
			link.onfocus = fsvg_svg._ItemOnFocus;
			link.onblur = fsvg_svg._ItemOnBlur;
			link.tabIndex = 0;
			link.className.baseVal="fsvg_link";

			if (!link.id) {
				link.id=f_core.AllocateAnoIdentifier();
			}
			link.setAttributeNS(fsvg_svg.XLINK_XMLNS, "href", f_core.CreateJavaScriptVoid0());
			
			if (modifs._audioDescription !== undefined) {
				link.setAttribute("aria-label", modifs._audioDescription);

			} else if (modifs._tooltipText) {
				link.setAttribute("aria-label", modifs._tooltipText);
			}
			
			if (!this._links) {
				this._links=[];
			}
			
			this._links.push(link);
			

		} else {
			if (modifs._selectable === false) {
				c.tabIndex = -1;
			}
		
			if (modifs._tooltipText !== undefined) {
				var th=c.ownerDocument.createElementNS(fsvg_svg.SVG_XMLNS, "title");
				
				f_core.SetText(th, modifs._tooltipText);
				
				c.append(th);
			}
			if (modifs._audioDescription !== undefined) {
				c.setAttribute("aria-label", modifs._audioDescription);
			}
		}
		// Le C peut-être modifié !
		if (modifs._audioDescription) {
			c.setAttribute("aria-live", modifs._audioDescription);
		}
		if (modifs._value !== undefined) {
			c._value=modifs._value;
		}

	},
	/**
	 * @method public
	 * @param String
	 *            url URL of stylesheet
	 * @return void
	 */
	f_appendStyleSheet : function(url) {
		var doc = this.f_getSVGDocument();
		
		url=f_env.ResolveContentUrl(url, window);

		var pi = doc.createProcessingInstruction('xml-stylesheet', 'href="'
				+ url + '" type="text/css"');

		doc.insertBefore(pi, doc.firstChild);
	},
	/**
	 * @method public
	 * @param String
	 *            styles
	 * @return void
	 */
	f_appendSVGStyle : function(styles) {
		var doc = this.f_getSVGDocument();

		var pi = doc.createElementNS(fsvg_svg.SVG_XMLNS, "style");
		pi.setAttributeNS(null, "type", "text/css");

		var pt = doc.createTextNode(styles);
		pi.appendChild(pt);

		doc.rootElement.insertBefore(pi, doc.rootElement.firstChild);
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @param Object item
	 * @param SVGElement link
	 * @return Boolean
	 */
	_onClick: function(jsEvent, item, link) {
		
		var event=new f_event(this, f_event.SELECTION, jsEvent, item, item._value);
		try {
			this.f_fireEvent(event);
			
		} finally {
			f_classLoader.Destroy(event);
		}
		
		f_core.CancelJsEvent(jsEvent);
		
		return false;
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @param Object item
	 * @param SVGElement link
	 * @return Boolean
	 */
	_onFocus: function(jsEvent, item, link) {
		link.className.baseVal="fsvg_link fsvg_link_focus";
	},
	/**
	 * @method protected
	 * @param Event jsEvent
	 * @param Object item
	 * @param SVGElement link
	 * @return Boolean
	 */
	_onBlur: function(jsEvent, item, link) {
		link.className.baseVal="fsvg_link";
	}
}

new f_class("fsvg_svg", {
	extend : f_box,
	aspects : [ fa_items ],
	members : __members,
	statics : __statics
});
