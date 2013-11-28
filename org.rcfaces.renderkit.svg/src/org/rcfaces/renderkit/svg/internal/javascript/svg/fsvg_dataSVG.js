/*
 * $Id$
 */

/**
 * Class fsvg_dataSVG
 * 
 * @class fsvg_dataSVG extends fsvg_svg, fa_items, fa_toolTipContainer
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	
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
	fsvg_dataSVG: function() {
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
		
		var svgDocument=this.f_getSVGDocument();
		if (svgDocument) {			
			f_toolTipManager.UninstallOverCallbacks(svgDocument, svgDocument.rootElement);
		}
		
		this.f_super(arguments);
	},
	f_update: function() {
		
		var svgDocument=this.f_getSVGDocument();
		if (svgDocument) {
			f_toolTipManager.InstallOverCallbacks(svgDocument, svgDocument.rootElement);
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
		
		c._rowIndex=modifs._rowIndex;

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
			link.onclick = fsvg_dataSVG._ItemOnClick;
			link.onfocus = fsvg_dataSVG._ItemOnFocus;
			link.onblur = fsvg_dataSVG._ItemOnBlur;
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
		if (modifs._value !== undefined) {
			c._value=modifs._value;
		}
		if (modifs._toolTipId !== undefined) {
			c._toolTipId=modifs._toolTipId;
			
			if (modifs._toolTipContent !== undefined) {
				c._toolTipContent=modifs._toolTipContent;
			}
		}

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
	},
	/**
	 * @method protected
	 * @param f_toolTip
	 *            tooltip
	 * @param optional
	 *            Event jsEvent
	 * @param optional
	 *            Number toolTipPosition
	 * @return void
	 */
	f_showToolTip : function(tooltip, jsEvent, toolTipPosition) {
		f_core
				.Debug(f_grid, "f_showToolTip: show tooltip '" + tooltip.id
						+ "'");
		var toolTipManager = f_toolTipManager.Get();

		var self = this;
		toolTipManager
				.f_appendCommand(function() {
					f_core.Debug(f_grid, "f_showToolTip: show tooltip '"
							+ tooltip.id + "' COMMAND START");

					if (self._loadToolTip(tooltip, jsEvent, toolTipPosition) !== false) {
						return;
					}

					toolTipManager.f_processNextCommand();
				});
	},

	/**
	 * @method private
	 * @param f_toolTip
	 *            tooltip
	 * @param optional
	 *            Event jsEvent
	 * @param optional
	 *            Number toolTipPosition
	 * @return Boolean
	 */
	_loadToolTip : function(tooltip, jsEvent, toolTipPosition) {
		var tooltipId = tooltip.f_getId();
		if (!tooltipId) {
			return false;
		}

		var elementItem = tooltip.f_getElementItem();
		if (!elementItem) {
			// Pas d'item ?
			// Le temps que le nextCommand soit appelé, la popup est fermée
			return false;
		}

		var row = elementItem;

		if (!row || row._rowIndex === undefined || row._rowIndex < 0) {
			return false;// a revoir
		}

		var component = tooltip;

		var request = new f_httpRequest(component,
				f_httpRequest.TEXT_HTML_MIME_TYPE);

		var toolTipManager = f_toolTipManager.Get();

		var toolTipStateId = tooltip.f_getStateId();

		tooltip.f_setInteractiveRenderer(false); // Le contenu est géré par
		// NOTRE appel ajax !
		tooltip.f_cleanContent();

		var self = this;
		request
				.f_setListener({
					onInit : function(request) {
						tooltip.f_asyncShowWaiting();

						component.f_show(toolTipStateId, jsEvent,
								toolTipPosition);
					},
					/**
					 * @method public
					 */
					onError : function(request, status, text) {

						if (toolTipManager.f_processNextCommand()) {
							return;
						}

						if (tooltip.f_getStateId() != toolTipStateId) {
							// Reset du tooltip !
							return;
						}
						tooltip.f_asyncHideWaiting(true);

						component.f_show(toolTipStateId);

						f_core.Info(f_grid,
								"f_showToolTip.onError: Bad status: " + status);

						self.f_performErrorEvent(request, f_error.HTTP_ERROR,
								text);
					},
					/**
					 * @method public
					 */
					onProgress : function(request, content, length, contentType) {
						if (tooltip.f_getStateId() != toolTipStateId) {
							// Reset du tooltip !
							return;
						}

						tooltip.f_asyncShowMessageWaiting(f_waiting
								.GetReceivingMessage());
					},
					/**
					 * @method public
					 */
					onLoad : function(request, content, contentType) {
						if (toolTipManager.f_processNextCommand()) {
							return;
						}

						if (tooltip.f_getStateId() != toolTipStateId) {
							// Reset du tooltip !
							return;
						}

						try {
							tooltip.f_asyncHideWaiting(true);
							tooltip.f_asyncDestroyWaiting();

							if (component._removeStyleHeight) {
								component._removeStyleHeight = null;
								if (component.style.height == f_waiting.HEIGHT
										+ "px") {
									component.style.height = "auto";
								}
							}

							if (request.f_getStatus() != f_httpRequest.OK_STATUS) {
								component
										.f_performErrorEvent(
												request,
												f_error.INVALID_RESPONSE_ASYNC_RENDER_ERROR,
												"Bad http response status ! ("
														+ request
																.f_getStatusText()
														+ ")");
								return;
							}

							var cameliaServiceVersion = request
									.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
							if (!cameliaServiceVersion) {
								component.f_performErrorEvent(request,
										f_error.INVALID_SERVICE_RESPONSE_ERROR,
										"Not a service response !");
								return;
							}

							var ret = request.f_getResponse();
							// alert("Ret="+ret);

							var responseContentType = request
									.f_getResponseContentType().toLowerCase();
							if (responseContentType
									.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE) >= 0) {
								var code = f_error
										.ComputeApplicationErrorCode(request);

								component.f_performErrorEvent(request, code,
										content);
								return;
							}

							if (responseContentType
									.indexOf(f_httpRequest.TEXT_HTML_MIME_TYPE) >= 0) {

								try {
									tooltip.f_setContent(ret);

								} catch (x) {
									component
											.f_performAsyncErrorEvent(
													x,
													f_error.RESPONSE_EVALUATION_ASYNC_RENDER_ERROR,
													"Evaluation exception");
								}
								return;
							}

							component.f_performAsyncErrorEvent(request,
									f_error.RESPONSE_TYPE_ASYNC_RENDER_ERROR,
									"Unsupported content type: "
											+ responseContentType);

						} finally {

						}
					}
				});

		request.f_setRequestHeader("X-Camelia", "grid.toolTip");

		var params = {
			gridId : this.id,
			rowValue : row._value,
			rowIndex : row._rowIndex,
			toolTipId : tooltipId
		};

		request.f_doFormRequest(params);

		return true;
	},
	fa_setToolTipVisible : function(tooltip, show, jsEvent) {
		
		if (show) {
			tooltip.f_show(tooltip.f_getStateId(), jsEvent, f_toolTip.MOUSE_POSITION);

		} else {
			tooltip.f_hide(tooltip.f_getStateId());
		}
	}
}

new f_class("fsvg_dataSVG", {
	extend : fsvg_svg,
	aspects : [ fa_items, fa_toolTipContainer ],
	members : __members,
	statics : __statics
});
