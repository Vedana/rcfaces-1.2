/*
 * $Id$
 */

/**
 * Class fsvg_dataSVG
 * 
 * @class fsvg_dataSVG extends fsvg_svg, fa_items, fa_gridToolTipContainer
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @method private static
	 * @param Event
	 *            event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnClick : function(evt) {
		var svg = this._svg;
		var link = this;
		var item = link;

		if (link._item) {
			item = link._item;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		f_core.Debug(fsvg_svg, "_ItemOnClick: perform event " + evt);

		if (svg.f_getEventLocked(evt)) {
			return false;
		}

		return svg._onClick(evt, item, link);
	},

	/**
	 * @method private static
	 * @param Event
	 *            event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnFocus : function(evt) {
		var svg = this._svg;
		var link = this;
		var item = link;

		if (link._item) {
			item = link._item;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		f_core.Debug(fsvg_svg, "_ItemOnFocus: perform event " + evt);

		if (svg.f_getEventLocked(evt)) {
			return false;
		}

		return svg._onFocus(evt, item, link);
	},

	/**
	 * @method private static
	 * @param Event
	 *            event
	 * @return Boolean
	 * @context object:svg
	 */
	_ItemOnBlur : function(evt) {
		var svg = this._svg;
		var link = this;
		var item = link;

		if (link._item) {
			item = link._item;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		f_core.Debug(fsvg_svg, "_ItemOnBlur: perform event " + evt);

		if (svg.f_getEventLocked(evt, false)) {
			return false;
		}

		return svg._onBlur(evt, item, link);
	}

}

var __members = {
	fsvg_dataSVG : function() {
		this.f_super(arguments);

	},
	f_finalize : function() {
		var links = this._links;
		if (links) {
			this._links = undefined;

			for (var i = 0; i < links.length; i++) {
				this._clearLink(links[i]);
			}
		}

		if (this._callbacksInstalled) {
			this._callbacksInstalled=undefined;

			var svgDocument = this.f_getSVGDocument();
			if (svgDocument) {
				try {
					f_key.UninstallDomEvent(svgDocument);
				} catch (x) {
					// Y a pas forcement de keyManager de dispo !
				}
				try {
					f_toolTipManager.UninstallOverCallbacks(svgDocument,
							svgDocument.rootElement);
				} catch (x) {				
					// Y a pas forcement de tooltipManager de dispo !
				}
			}
		}
		
		this.f_super(arguments);
	},
	f_updateSVG: function(svgDocument) {
		this.f_super(arguments, svgDocument);

		if (!this._callbacksInstalled) {
			this._callbacksInstalled=undefined;
			
			try {
				f_toolTipManager.InstallOverCallbacks(svgDocument,
						svgDocument.rootElement);
			} catch (x) {
				// Y a pas forcement de tooltipManager de dispo !
			}
	
			try {
				f_key.InstallDomEvent(svgDocument);
			} catch (x) {
				// Y a pas forcement de keyManager de dispo !
			}
		}
	},
	/**
	 * @method protected
	 * @param SVGElement
	 *            link
	 * @return void
	 */
	_clearLink : function(link) {
		link._svg = undefined;
		link._item = undefined;
		link.onclick = null;
		link.onfocus = null;
		link.onblur = null;
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

		c._rowIndex = modifs._rowIndex;

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
			var link = c;

			if (!c.tagName || c.tagName.toUpperCase() != "A") {
				var ah = c.ownerDocument.createElementNS(fsvg_svg.SVG_XMLNS,
						"a");

				var parentNode = c.parentNode;
				parentNode.insertBefore(ah, c);
				parentNode.removeChild(c);
				ah.appendChild(c);

				if (modifs._tooltipText) {
					var th = c.ownerDocument.createElementNS(
							fsvg_svg.SVG_XMLNS, "title");

					f_core.SetTextNode(th, modifs._tooltipText);

					ah.appendChild(th);
				}

				// Non il peut y avoir un tooltip riche !
				if (modifs._audioDescription && !modifs._toolTipId) {
					ah.setAttributeNS(fsvg_svg.SVG_XMLNS, "title",
							modifs._audioDescription);
				}

				ah._item = c;
				link = ah;
			}

			link._svg = this;
			link.onclick = fsvg_dataSVG._ItemOnClick;
			link.onfocus = fsvg_dataSVG._ItemOnFocus;
			link.onblur = fsvg_dataSVG._ItemOnBlur;
			link.tabIndex = 0;
			link.className.baseVal = "fsvg_link";

			if (!link.id) {
				link.id = f_core.AllocateAnoIdentifier();
			}
			link.setAttributeNS(fsvg_svg.XLINK_XMLNS, "href", f_core
					.CreateJavaScriptVoid0());
			
			var hasTooltipMessage="";
			if (modifs._toolTipId) {
				hasTooltipMessage=" Une infobulle est disponible.";
				link._toolTipId = modifs._toolTipId;

				if (modifs._toolTipContent !== undefined) {
					link._toolTipContent = modifs._toolTipContent;
				}
			}

			if (modifs._audioDescription !== undefined) {
				link.setAttribute("aria-label", modifs._audioDescription+hasTooltipMessage);

			} else if (modifs._tooltipText) {
				link.setAttribute("aria-label", modifs._tooltipText+hasTooltipMessage);
			} else {
				link.setAttribute("aria-label", hasTooltipMessage);
			}

			if (!this._links) {
				this._links = [];
			}

			this._links.push(link);

		} else {
			if (modifs._selectable === false) {
				c.tabIndex = -1;
			}

			if (modifs._tooltipText !== undefined) {
				var th = c.ownerDocument.createElementNS(fsvg_svg.SVG_XMLNS,
						"title");

				f_core.SetTextNode(th, modifs._tooltipText);

				c.append(th);
			}
			if (modifs._audioDescription !== undefined) {
				c.setAttribute("aria-label", modifs._audioDescription);
			}
		}
		if (modifs._value !== undefined) {
			c._value = modifs._value;
		} else {
			c._value = id;
		}

		if (modifs._toolTipId !== undefined) {
			c._toolTipId = modifs._toolTipId;

			if (modifs._toolTipContent !== undefined) {
				c._toolTipContent = modifs._toolTipContent;
			}
		}

	},
	/**
	 * @method protected
	 * @param Event
	 *            jsEvent
	 * @param Object
	 *            item
	 * @param SVGElement
	 *            link
	 * @return Boolean
	 */
	_onClick : function(jsEvent, item, link) {

		var event = new f_event(this, f_event.SELECTION, jsEvent, item,
				item._value);
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
	 * @param Event
	 *            jsEvent
	 * @param Object
	 *            item
	 * @param SVGElement
	 *            link
	 * @return Boolean
	 */
	_onFocus : function(jsEvent, item, link) {
		link.className.baseVal = "fsvg_link fsvg_link_focus";
		
		fsvg_cursor.Show(link);
	},
	/**
	 * @method protected
	 * @param Event
	 *            jsEvent
	 * @param Object
	 *            item
	 * @param SVGElement
	 *            link
	 * @return Boolean
	 */
	_onBlur : function(jsEvent, item, link) {
		link.className.baseVal = "fsvg_link";
		
		fsvg_cursor.Hide(link);
	},
	fa_setToolTipVisible : function(tooltip, show, jsEvent) {

		if (show) {
			var item = tooltip.f_getElementItem();

			if (!tooltip.f_isContentSpecified()) {
				this.f_showToolTip(tooltip, jsEvent,
						f_toolTip.MOUSE_POSITION);
				return;
			}

			tooltip.f_show(tooltip.f_getStateId(), jsEvent,
					f_toolTip.MOUSE_POSITION);

		} else {
			tooltip.f_hide(tooltip.f_getStateId());
		}
	},

	/**
	 * @method protected
	 * @param Element
	 *            elementItem
	 * @param String tooltipId
	 * @return Object
	 */
	_computeTooltipRowContext: function(elementItem, tooltipId) {	
		if (elementItem._item) {
			elementItem=elementItem._item;
		}
		return {
			_row: elementItem,
			_rowValue: elementItem._value,
			_rowIndex: elementItem._rowIndex,
			_tooltipId: tooltipId
		};
	}
}

new f_class("fsvg_dataSVG", {
	extend : fsvg_svg,
	aspects : [ fa_items, fa_gridToolTipContainer ],
	members : __members,
	statics : __statics
});
