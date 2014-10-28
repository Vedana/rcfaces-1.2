/*
 * $Id$
 */

/**
 * Focus manager class.
 * 
 * @class public f_focusSectionPopup extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field private static
	 */
	_PopupSection : undefined,

	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer : function() {
		f_focusSectionPopup._PopupSection = undefined; // Element
	},

	/**
	 * @method private static
	 * @param HtmlElement
	 *            element
	 * @return Object
	 */
	_ListHElementsHierarchy : function(element) {
		var prevs = [];
		var curLevel = 10;

		for (var c = element; c;) {
			if (c.nodeType == f_core.ELEMENT_NODE) {
				var tname = c.tagName;
				if (tname && tname.charAt(0).toUpperCase() == "H") {
					var lvl = parseInt(tname.substring(1), 10);

					if (lvl && curLevel > lvl) {
						curLevel = lvl;
						prevs[lvl - 1] = c;

						if (curLevel === 1) {
							break;
						}
					}
				}
			}
			if (c.previousSibling) {
				c = c.previousSibling;
				for (; c.lastChild;) {
					c = c.lastChild;
				}
				continue;
			}
			c = c.parentNode;
			if (!c || c.nodeType != f_core.ELEMENT_NODE) {
				break;
			}
		}

		return prevs;
	},
	/**
	 * @method public static
	 * @param f_event
	 *            event
	 * @param hidden
	 *            HTMLElement fromComponent
	 * @return Boolean
	 */
	ShowFocusSection : function(event) {
		if (f_focusSectionPopup._PopupSection
				&& f_focusSectionPopup._PopupSection.parentNode) {
			return true;
		}

		var focusComponent = f_focusManager.ComputeFocusComponent(event);
		if (!focusComponent) {
			return true;
		}

		var doc = focusComponent.ownerDocument;

		var body = doc.body;
		if (!body) {
			return true;
		}

		var prevs = f_focusSectionPopup._ListHElementsHierarchy(focusComponent);

		var popup = f_core.CreateElement(body, "div", {
			id : "f_focusSelection",
			classname : "f_focusSection"
		});
		f_focusSectionPopup._PopupSection = popup;

		var span = f_core.CreateElement(popup, "div", {
			id : "f_focusSelection::span",
			className : "f_focusSection_title",
			textNode : "Arborescence de la page",
			role : "description"
		});

		var ul = f_core.CreateElement(popup, "ul", {
			role : "tree"
		});
		var rootUL = ul;

		var tree = f_focusSectionPopup._BuildHeadingsTree(body);
		tree._element = ul;

		var currentNode = null;
		var firstNode = null;

		var nodeIdx = 0;
		var stack = tree._children.concat([]);
		for (; stack.length;) {
			var n = stack.shift();
			n._nodeIdx = nodeIdx++;

			var li = f_core.CreateElement(n._parent._element, "li", {
				role : "presentation"
			});

			var txt = n._txt;
			if (!txt) {
				txt = "Titre non spécifié";
			}

			var s = "";
			for (var sn = n; sn._parent; sn = sn._parent) {
				s = String(sn._parent._children.indexOf(sn) + 1)
						+ (s ? "." : "") + s;
			}

			var h = f_core.CreateElement(li, "a", {
				href : f_core.CreateJavaScriptVoid0(),
				id : "f_focusSelection::node" + n._nodeIdx,
				role : "treeitem",
				"aria-labelledby" : "f_section_l" + n._nodeIdx,
				tabIndex : -1,
				_node : n
			});
			f_core.CreateElement(h, "LABEL", {
				className : "f_section_l" + n._level,
				textNode : "Section " + s + ": ",
				role : "presentation"
			});
			f_core.CreateElement(h, "LABEL", {
				id : "f_section_l" + n._nodeIdx,
				className : "f_section_l" + n._level,
				textNode : txt
			/* "Section " + s + ": " + */
			});

			if (!n._focusables.length) {
				fa_audioDescription.AppendAudioDescription(h,
						"Aucun champ saisissable", "focus");
				h.className = "f_focusSelection_noFocus";
			}

			if (n._children.length) {
				h.setAttribute("aria-expanded", true);

				var ul = f_core.CreateElement(li, "ul", {
					role : "group"
				});
				n._element = ul;

				stack.unshift.apply(stack, n._children);
			}

			if (!firstNode) {
				firstNode = h;
			}

			if (!currentNode && prevs.indexOf(n._component) >= 0) {
				currentNode = h;
			}
		}

		if (!currentNode) {
			currentNode = firstNode;
		}

		if (!currentNode) {
			return;
		}

		currentNode.tabIndex = 0;

		var oldFocus = null;
		var oldTarget = null;
		var oldTargetOutline = null;
		var setFocusDate = Date.now();
		f_core.SetFocus(currentNode, true);

		var clbks = {
			close : function(evt) {
				if (oldTarget) {
					if (oldTargetOutline !== undefined) {
						oldTarget.style.outline = oldTargetOutline;
						oldTargetOutline = undefined;
					}
					oldTarget = undefined;
				}

				f_focusSectionPopup._PopupSection = undefined;

				f_popup.UnregisterWindowClick(popup);

				popup.parentNode.removeChild(popup);
			},

			/**
			 * @method public
			 */
			exit : function(evt) {
				f_core.Debug(f_focusSectionPopup, "_OpenPopup.exit: evt: "
						+ evt);

				var target = evt.target || evt.srcElement;

				if (false) {
					console
							.log("Event=", evt, " target=", target,
									" setFocusDate=", setFocusDate, " now=",
									Date.now());
				}

				if (setFocusDate && setFocusDate + 800 > Date.now()) {
					setFocusDate = 0;
					if (false) {
						console.log("Cancel focus date");
					}
					oldFocus = target;
					return false;
				}

				if (target.nodeType === f_core.ELEMENT_NODE) {
					for (; target; target = target.parentNode) {
						if (target.id === popup.id) {
							return false;
						}
					}
				}

				clbks.close();
			},
			/**
			 * @method public
			 */
			keyDown : function(evt) {
				f_core.Debug(f_focusSectionPopup,
						"_OpenPopup.keyDown: popup keyDown: " + evt.keyCode);

				if (!evt.ctrlKey) {
					// return clbks.close(evt);
				}

				switch (evt.keyCode) {
					case f_key.VK_DOWN :
					case f_key.VK_UP :
					case f_key.VK_LEFT :
					case f_key.VK_RIGHT :
					case f_key.VK_RETURN :
					case f_key.VK_TAB :
					case f_key.VK_ESCAPE :
						return true;
				}

				return false;
			},
			/**
			 * @method public
			 */
			keyUp : function(evt) {
				f_core.Debug(f_focusSectionPopup,
						"_OpenPopup.keyUp: popup keyUp: " + evt.keyCode);

				if (!evt.ctrlKey) {
					// return clbks.close(evt);
				}

				switch (evt.keyCode) {
					case f_key.VK_DOWN :
					case f_key.VK_UP :
					case f_key.VK_LEFT :
					case f_key.VK_RIGHT :
					case f_key.VK_RETURN :
					case f_key.VK_TAB :
					case f_key.VK_ESCAPE :
						return true;
				}

				return false;
			},
			/**
			 * @method public
			 */
			keyPress : function(evt) {
				f_core.Debug(f_focusSectionPopup,
						"_OpenPopup.keyPress: popup keyPress: " + evt.keyCode);

				if (oldTarget) {
					if (oldTargetOutline !== undefined) {
						oldTarget.style.outline = oldTargetOutline;
						oldTargetOutline = undefined;
					}
					oldTarget = undefined;
				}

				var nextNode;

				switch (evt.keyCode) {

					case f_key.VK_RIGHT :
						// Un fils ?
						var nodes = currentNode.nextSibling
								&& currentNode.nextSibling
										.getElementsByTagName("a");
						if (nodes && nodes.length) {
							nextNode = nodes[0];
							break;
						}

						// Un frere
						var nextNode = currentNode.parentNode.nextSibling;
						if (nextNode && nextNode.tagName === "LI") {
							nextNode = nextNode.firstChild;
							break;
						}

						if (nextNode) {
							nextNode = nextNode.nextSibling;
							if (nextNode) {
								nextNode = nextNode.firstChild;
								break;
							}
						}
						// On cherche un parent
						// A LI UL
						nextNode = currentNode.parentNode.parentNode;
						for (;;) {
							if (nextNode.parentNode.nextSibling) {
								nextNode = nextNode.parentNode.nextSibling.firstChild;
								break;
							}

							nextNode = nextNode.parentNode.parentNode;

							if (nextNode.tagName === "UL") {
								continue;
							}

							nextNode = rootUL.firstChild.firstChild;
							break;
						}

						break;

					case f_key.VK_DOWN :
						var nextNode = currentNode.parentNode.nextSibling;
						if (nextNode && nextNode.tagName === "LI") {
							nextNode = nextNode.firstChild;
							break;
						}

						if (nextNode) {
							nextNode = nextNode.nextSibling;
							if (nextNode) {
								nextNode = nextNode.firstChild;
								break;
							}
						}

						nextNode = currentNode.parentNode.parentNode.firstChild.firstChild;

						if (nextNode === currentNode) {
							for (;;) {
								if (nextNode.parentNode.nextSibling) {
									nextNode = nextNode.parentNode.nextSibling.firstChild;
									break;
								}

								nextNode = nextNode.parentNode.parentNode.previousSibling;

								if (nextNode.tagName === "A") {
									continue;
								}

								nextNode = rootUL.firstChild.firstChild;
								break;
							}
						}
						break;

					case f_key.VK_LEFT :
						nextNode = currentNode.parentNode.parentNode.parentNode;
						if (nextNode.tagName === "LI") {
							nextNode = nextNode.firstChild;
							if (nextNode) {
								break;
							}
						}

					case f_key.VK_UP :
						nextNode = currentNode.parentNode.previousSibling;
						if (nextNode && nextNode.tagName === 'LI') {
							nextNode = nextNode.firstChild;
							break;
						}
						if (nextNode) {
							nextNode = nextNode.previousSibling.firstChild;
							break;
						}

						nextNode = currentNode.parentNode.parentNode.lastChild.firstChild;

						if (nextNode && nextNode !== currentNode) {
							break;
						}

						nextNode = currentNode.parentNode.parentNode.parentNode;
						if (nextNode.tagName === "LI") {
							nextNode = nextNode.firstChild;
						} else {
							nextNode = null;
						}

						// On va a gauche
						break;

					case f_key.VK_RETURN :
					case f_key.VK_TAB :
						var focs = undefined;
						if (currentNode && currentNode._node) {
							focs = currentNode._node._focusables;
						}

						if (false) {
							console.log("Focus=", focs, " currentNode=",
									currentNode);
						}

						if (focs && focs.length) {
							var elt = doc.getElementById(focs[0]);

							if (false) {
								console.log("Set focus to ", elt);
							}
							if (elt) {
								f_core.SetFocus(elt, true);
							}
						}
						return clbks.close(evt);

					case f_key.VK_ESCAPE :
						if (oldFocus) {
							f_core.SetFocus(oldFocus, true);
						}
						return clbks.close(evt);
				}

				if (true) {
					console
							.log("NextNode=", nextNode, " curNode=",
									currentNode);
				}

				if (nextNode) {
					if (currentNode) {
						currentNode.tabIndex = -1;
					}
					nextNode.tabIndex = 0;

					f_core.SetFocus(nextNode, true);
					currentNode = nextNode;

					var fs = nextNode._node._focusables;
					if (fs && fs.length) {
						oldTarget = doc.getElementById(fs[0]);
						if (oldTarget) {
							oldTargetOutline = oldTarget.style.outline;
							oldTarget.style.outline = "red 3px solid";
						}
					}
				}

				return f_core.CancelJsEvent(evt);
			}
		};

		if (f_popup.RegisterWindowClick(clbks, popup, popup) == false) {
			f_core.Debug(f_focusSectionPopup,
					"_OpenPopup: Register refused to open the popup of focusManagerSection='"
							+ popup + "'.");
			return true;
		}

		return false;
	},
	/**
	 * @method private static
	 * @param Element
	 *            from
	 * @return Object
	 */
	_BuildHeadingsTree : function(from, listRef, listPos) {
		var root = {
			_children : [],
			_focusables : [],
			_parent : null,
			_level : 0
		};

		var focusableTags = new RegExp(f_core._FOCUSABLE_TAGS, "i");

		var levels = [root];

		for (var c = from; c;) {
			var visible = false;
			if (c.nodeType == f_core.ELEMENT_NODE) {
				visible = true;

				var vis = f_core.GetCurrentStyleProperty(c, "visibility");
				if (vis && vis.toLowerCase() == "hidden") {
					visible = false;
				} else {
					vis = f_core.GetCurrentStyleProperty(c, "display");
					if (vis && vis.toLowerCase() == "none") {
						visible = false;
					}
				}
			}

			if (visible) {
				var tagName = c.tagName.toUpperCase();
				if (tagName.charAt(0) == "H") {
					var level = parseInt(tagName.substring(1), 10);

					for (; level < levels.length;) {
						var lv = levels.pop();
						if (!lv._children.length && !lv._focusables.length) {
							levels[levels.length - 1]._children.pop();
						}
					}

					for (; level >= levels.length;) {
						var n = {
							_children : [],
							_focusables : [],
							_parent : levels[levels.length - 1],
							_level : level,
							_component : c
						};
						levels[levels.length - 1]._children.push(n);
						levels.push(n);

						if (listRef) {
							var lidx = listRef.indexOf(c);
							if (lidx >= 0) {
								listPos[lidx] = n;
							}
						}
					}

					var txt = f_core.GetTextNode(c, true);
					levels[levels.length - 1]._txt = txt;
				}

				if ((typeof (c.tabIndex) == "number" && c.tabIndex >= 0)
						|| (focusableTags.test(tagName) && (!c.tabIndex || c.tabIndex >= 0))
						&& !c.disabled
						&& (!c.f_isDisabled || !c.f_isDisabled())) {
					var id = c.id;
					if (!id) {
						id = f_core.AllocateAnoIdentifier();
						c.id = id;
					}

					var registerFocus = true;

					if (tagName != "INPUT" || !c.type
							|| c.type.toUpperCase() != "HIDDEN") {

						var idx = id.lastIndexOf("::");
						if (idx > 0) {
							id = id.substring(0, idx);

							var parentElt = document.getElementById(id);
							if (parentElt
									&& (parentElt.disabled || (parentElt.f_isDisabled && parentElt
											.f_isDisabled()))) {
								registerFocus = false;
							}
						}
					}

					if (registerFocus) {
						var focusables = levels[levels.length - 1]._focusables;
						if (focusables.indexOf(id) < 0) {
							focusables.push(id);
						}
					}
				}

				if (c.firstChild) {
					c = c.firstChild;
					continue;
				}
			}

			if (c.nextSibling) {
				c = c.nextSibling;
				continue;
			}

			if (c == from) {
				break;
			}

			for (;;) {
				var p = c.parentNode;
				if (!p || p.nodeType != f_core.ELEMENT_NODE || p == from) {
					c = null;
					break;
				}

				if (p.nextSibling) {
					c = p.nextSibling;
					break;
				}
				c = p;
			}
		}

		for (; levels.length;) {
			var lv = levels.pop();
			if (!lv._children.length && !lv._focusables.length) {
				levels[levels.length - 1]._children.pop();
			}
		}

		return root;
	},
	/**
	 * @method public static
	 * @param f_event
	 *            event
	 * @return void
	 */
	FocusNextH1 : function(event) {
		f_focusSectionPopup._FocusNext(event, function(focusElement, levels,
				pos, tree) {
			var p0 = pos[0];
			if (!p0) {
				return;
			}
			var children = p0._parent._children;
			var idx = children.indexOf(p0);
			idx = (idx + 1) % children.length;

			return children[idx];
		});
	},
	/**
	 * @method public static
	 * @param f_event
	 *            event
	 * @return void
	 */
	FocusPreviousH1 : function(event) {
		f_focusSectionPopup._FocusNext(event, function(focusElement, levels,
				pos, tree) {
			var p0 = pos[0];
			if (!p0) {
				return;
			}
			var children = p0._parent._children;
			var idx = children.indexOf(p0);
			idx = (idx - 1 + children.length) % children.length;

			return children[idx];
		});
	},
	/**
	 * @private static
	 * @param Object
	 *            px
	 * @return Object
	 */
	_SearchFirstChild : function(px) {
		for (; px;) {
			if (px._focusables.length) {
				return px;
			}

			px = px._children[0];
		}

		return null;
	},
	/**
	 * @method public static
	 * @param f_event
	 *            event
	 * @return void
	 */
	FocusNext : function(event) {
		f_focusSectionPopup._FocusNext(event, function(focusElement, levels,
				pos, tree) {
			var px = pos[pos.length - 1];
			if (!px) {
				return;
			}
			if (px._children && px._children.length) {
				var ret = f_focusSectionPopup
						._SearchFirstChild(px._children[0]);
				if (ret) {
					return ret;
				}
			}

			var children = px._parent._children;
			var idx = children.indexOf(px);
			if (children[idx + 1]) {
				var ret = f_focusSectionPopup
						._SearchFirstChild(children[idx + 1]);
				if (ret) {
					return ret;
				}
			}

			for (;;) {
				px = px._parent;
				if (!px || !px._parent) {
					break;
				}

				children = px._parent._children;
				var idx = children.indexOf(px);
				var ret = f_focusSectionPopup
						._SearchFirstChild(children[idx + 1]);
				if (ret) {
					return ret;
				}
			}

			var ret = f_focusSectionPopup._SearchFirstChild(tree._children[0]);
			if (ret) {
				return ret;
			}

			return null;
		});
	},
	/**
	 * @method private static
	 * @param Object
	 *            px
	 * @return Object
	 */
	_SearchChildLast : function(px) {
		for (; px._children.length;) {
			px = px._children[px._children.length - 1];
		}

		return px;
	},
	/**
	 * @method public static
	 * @param f_event
	 *            event
	 * @return void
	 */
	FocusPrevious : function(event) {
		f_focusSectionPopup._FocusNext(event, function(focusElement, levels,
				pos, tree) {
			var px;
			if (pos.length) {
				px = pos[pos.length - 1];
				if (!px) {
					return;
				}

				for (; px._parent;) {
					var children = px._parent._children;
					var idx = children.indexOf(px);
					if (children[idx - 1]) {
						return f_focusSectionPopup
								._SearchChildLast(children[idx - 1]);
					}

					px = px._parent;

					if (px._focusables.length) {
						return px;
					}
				}
			}
			px = tree;
			for (;;) {
				if (!px._children || !px._children.length) {
					return px;
				}

				px = px._children[px._children.length - 1];
			}
		});
	},

	/**
	 * @method private static
	 * @param f_event
	 *            event
	 * @param Function
	 *            func
	 * @return Boolean
	 */
	_FocusNext : function(event, func) {
		// On ne peut pas utiliser le f_getFocusComponent()
		// Car pour les containers qui contiennent des Hx on ne peut plus voir
		// les Hx
		var focusComponent = f_focusManager.Get()._getActiveElement();
		if (false) {
			console.log("FocusComponent", focusComponent);
		}
		if (!focusComponent) {
			return false;
		}

		var levels = f_focusSectionPopup
				._ListHElementsHierarchy(focusComponent);

		if (false) {
			console.log("Levels", levels);
		}
		if (!levels) {
			return;
		}

		var doc = focusComponent.ownerDocument;
		if (false) {
			console.log("doc", doc);
		}
		if (!doc) {
			return;
		}

		var pos = [];

		var tree = f_focusSectionPopup
				._BuildHeadingsTree(doc.body, levels, pos);
		if (!tree) {
			if (false) {
				console.log("Tree", tree);
			}
			return;
		}

		var node = func.call(f_focusSectionPopup, focusComponent, levels, pos,
				tree);
		if (false) {
			console.log("NextNode", node);
		}
		if (!node) {
			return;
		}

		var focs = node._focusables;

		if (false) {
			console.log("Focs", focs);
		}
		if (!focs || !focs.length) {
			return;
		}

		var id = focs[0];
		var idx = id.lastIndexOf("::");
		if (idx > 0) {
			id = id.substring(0, idx);
			var component = f_core.GetElementByClientId(id);

			while (!component || idx > 0) {
				idx = id.lastIndexOf(".");
				if (idx < 0) {
					break;
				}
				id = id.substring(0, idx);
				component = f_core.GetElementByClientId(id);
			}
		}
		var elt = f_core.GetElementByClientId(id);
		if (elt) {
			if (false) {
				console.log("SETFOCUS ", elt);
			}
			f_core.SetFocus(elt, true);
		}
	}
};

var __members = {

	f_focusSectionPopup : function() {
		this.f_super(arguments);

	}
};

new f_class("f_focusSectionPopup", {
	extend : f_object,
	statics : __statics,
	members : __members
});
