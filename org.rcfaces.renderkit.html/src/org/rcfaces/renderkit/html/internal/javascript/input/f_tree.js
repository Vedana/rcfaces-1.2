/*
 * $Id$
 */

/**
 * f_tree
 *
 * @class f_tree extends f_component, fa_readOnly, fa_disabled, fa_immediate, fa_subMenu, fa_selectionManager, fa_checkManager, fa_itemClientDatas, fa_scrollPositions
 * @author olivier Oeuillot
 * @version $REVISION: $
 */
 
var __static = {
	
	/**
	 * @field private static final String
	 */
	_BLANK_NODE_IMAGE_URL: "/blank.gif",
	
	/**
	 * @field private static final String
	 */
	_NODE_MENU_ID: "#node",
	
	/**
	 * @field private static final String
	 */
	_BODY_MENU_ID: "#body",

	/**
	 * @field private static final number
	 */
	_SEARCH_KEY_DELAY: 400,

	/**
	 * @field private static final number
	 */
	_COMMAND_IMAGE_WIDTH: 16,
	
	/**
	 * @field private static final number
	 */
	_COMMAND_IMAGE_HEIGHT: 16,

	/**
	 * @method private static 
	 */
	_NodeLabel_mouseOver: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false) || li._labelOver) {
			return false;
		}
			
		li._labelOver=true;
		
		tree.fa_updateElementStyle(li);
	},
	/**
	 * @method private static 
	 */
	_NodeLabel_mouseOut: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!li._labelOver) {
			return;
		}
		
		li._labelOver=undefined;
		
		tree.fa_updateElementStyle(li);
	},
	/**
	 * @method private static 
	 */
	_DivNode_mouseOver: function(evt) {
		var li=this._node;
		var tree=li._tree;

		f_core.Assert(tree && tree.tagName, "f_tree._DivNode_mouseOver: Invalid tree this=("+this.id+"/"+this.tagname+") li=("+li.id+"/"+li.tagName+")");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (!tree || tree.f_getEventLocked(evt, false) || li._over) {
			return false;
		}
			
		li._over=true;
		
		tree.fa_updateElementStyle(li);
	},
	/**
	 * @method private static 
	 */
	_DivNode_mouseOut: function(evt) {
		var li=this._node;
		var tree=li._tree;

		f_core.Assert(tree && tree.tagName, "f_tree._DivNode_mouseOut: Invalid tree this=("+this.id+"/"+this.tagname+") li=("+li.id+"/"+li.tagName+")");

		if (!tree || !li._over) {
			return;
		}
		
		li._over=undefined;
		
		tree.fa_updateElementStyle(li);
	},
	/**
	 * @method private static 
	 */
	_DivNode_dblClick: function(evt) {
		var li=this._node;
		var tree=li._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt)) {
			return false;
		}
	
		var node=li._node;

		tree.f_fireEvent(f_event.DBLCLICK, evt, node, node._value);

		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_DivNode_mouseDown: function(evt) {
		var li=this._node;
		var tree=li._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
		if (tree.f_getEventLocked(evt)) {
			return false;
		}

		if (!tree._focus) {
			tree.f_setFocus();
		}
		
		var selection=fa_selectionManager.ComputeMouseSelection(evt);
		
		tree.f_moveCursor(li, true, evt, selection);
					
		if (f_core.IsPopupButton(evt) && !tree.fa_isElementDisabled(li)) {		
			var menu=tree.f_getSubMenuById(f_tree._NODE_MENU_ID);
			if (menu) {
				menu.f_open(evt, {
					position: f_popup.MOUSE_POSITION
				});
			}
		}

		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_BodyMouseDown: function(evt) {
		var tree=this;
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt)) {
			return false;
		}
		
		if (tree.f_isDisabled()) {
			return f_core.CancelJsEvent(evt);
		}
		
		var sub=f_core.IsPopupButton(evt);
		if (!sub) {
			return f_core.CancelJsEvent(evt);
		}
		
		var menuId=f_tree._BODY_MENU_ID;
		
		// S'il y a une seule selection, on bascule en popup de ligne !
		/* finalement non, car sous File explorer ce n'est pas le cas !  (c'est le cas d'Eclipse)
		if (tree._currentSelection.length) {
			menuId=f_tree._NODE_MENU_ID;	
		}
		*/
		
		var menu=tree.f_getSubMenuById(menuId);
		if (menu) {
			menu.f_open(evt, {
				position: f_popup.MOUSE_POSITION
			});
		}
			
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_Command_mouseDown: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt)) {
			return false;
		}
		
		var node=li._node;

		if (!tree._focus) {
			tree.f_setFocus();
		}

		if (tree._userExpandable) {
			if (node._opened) {
				tree._closeNode(node, evt, li);
	
			} else {
				tree._openNode(node, evt, li);
			}
		}
		
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static 
	 */
	_Link_bodyOnfocus: function(evt) {
		var tree=this._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false)) {
			return false;
		}
		
		tree.f_setFocus();
	},
	/**
	 * @method private static 
	 */
	_Link_onfocus: function(evt) {
		var tree=this._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}		

		if (tree.f_getEventLocked(evt, false)) {
			return false;
		}

		if (tree._focus) {
			return true;
		}
		
		tree._focus=true;
		
		if (tree._selectable) {
			if (!tree._cursor) {
				var currentSelection=tree._currentSelection;
				if (currentSelection.length) {
					tree._cursor=currentSelection[0];
					tree._initCursorValue=undefined;
				}
				
				if (!tree._cursor) {
					var li=f_core.GetFirstElementByTagName(tree, "li");
					if (li) {
						tree._cursor=li;
						tree._initCursorValue=undefined;
					}
				}
			}
		
			tree._updateSelectedNodes();
			
		} else if (!tree._cursor) {
			var li=f_core.GetFirstElementByTagName(tree, "li");
			if (li) {
				tree._cursor=li;
				tree._initCursorValue=undefined;
				
				tree.fa_updateElementStyle(li);
			}
		}

		tree.f_onFocus(evt);

		return true;		
	},
	/**
	 * @method private static 
	 */
	_Link_onblur: function(evt) {
		var tree=this._tree;
		
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		// On verouille pas l'accés !
		//if (tree.f_getEventLocked(evt, false)) {
		// return false;
		//}
		
		if (!tree._focus) {
			return true;
		}
		
		tree._focus=undefined;
	
		if (tree._selectable) {
			tree._updateSelectedNodes();
		}

		if (tree._cfocus) {
			tree._cfocus.style.top=tree.scrollTop+"px";
		}

		tree.f_onBlur(evt);

		return true;
	},
	/**
	 * @method private static 
	 */
	_Link_onkeydown: function(evt) {
		var tree=this._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, true)) {
			return false;
		}
		
		if (!tree._focus) {
			return true;
		}

		return tree.f_onKeyDown(evt);
	},
	/**
	 * @method private static 
	 */
	_Link_onkeyup: function(evt) {
		var tree=this._tree;

		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false)) {
			return false;
		}

		if (!tree._focus) {
			return true;
		}

		return tree.f_onKeyUp(evt);
	},
	/**
	 * @method private static 
	 */
	_Link_onkeypress: function(evt) {
		var tree=this._tree;

		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false)) {
			return false;
		}

		if (!tree._focus) {
			return true;
		}

		return tree.f_onKeyPress(evt);
	},
	/**
	 * @method private static 
	 */
	_NodeInput_mouseClick: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt)) {
			return false;
		}

		evt.cancelBubble = true;

		if (li!=tree._cursor) {
			tree.f_moveCursor(li, true, evt);
		}
		
		var checked;
		if (this.type=="radio") {
			checked=true;
		} else {
			checked=!tree.fa_isElementChecked(li);
		}
	
		tree.fa_performElementCheck(li, true, evt, checked);
		
		if (f_core.IsGecko()) {
			if (tree.fa_isElementChecked(li)!=checked) {
				return false;
			}
		}
 
		return true;
	}
}

var __prototype = {
	
	f_tree: function() {
		this.f_super(arguments);
		
		this._nodesList=new Array();

		this._interactive=f_core.GetBooleanAttribute(this, "v:asyncRender", false);

		this._hideRootExpandSign=f_core.GetBooleanAttribute(this, "v:hideRootExpandSign", false);
				
		this._userExpandable=f_core.GetBooleanAttribute(this, "v:userExpandable", true);
		
		this._images=f_core.GetBooleanAttribute(this, "v:images");
		
		this._preloadedLevelDepth=f_core.GetNumberAttribute(this, "v:preloadedLevelDepth");
		
		this._initCursorValue=f_core.GetAttribute(this, "v:cursorValue");
		
		var styleSheetBase=f_env.GetStyleSheetBase();

		this._blankNodeImageURL=styleSheetBase+f_tree._BLANK_NODE_IMAGE_URL;
		f_imageRepository.PrepareImage(this._blankNodeImageURL);
		
		if (f_core.IsInternetExplorer()) {
			if (!this.tabIndex) {
				this.tabIndex=0;
			}
			
			this.hideFocus=true;
			this.onfocus=f_tree._Link_onfocus;
			this.onblur=f_tree._Link_onblur;
			this.onkeydown=f_tree._Link_onkeydown;
			this.onkeyup=f_tree._Link_onkeyup;
			this.onkeypress=f_tree._Link_onkeypress;
			this._tree=this;
			
		} else {
			var focus=document.createElement("a");
			this._cfocus=focus;

			focus.className="f_tree_focus";
			focus.onfocus=f_tree._Link_onfocus;
			focus.onblur=f_tree._Link_onblur;
			focus.onkeydown=f_tree._Link_onkeydown;
			focus.onkeypress=f_tree._Link_onkeypress;
			focus.onkeyup=f_tree._Link_onkeyup;
			focus.href=f_core.JAVASCRIPT_VOID;
			focus._tree=this;
			focus.f_link=this;
	
			// Gestion du focus lors du click dans le TREE !
			this.onfocus=f_tree._Link_bodyOnfocus;
			this.onblur=f_tree._Link_onblur;
			this._tree=this;

			var tabIndex=this.tabIndex;
			if (tabIndex<0) {
				tabIndex=0;
			}
			focus.tabIndex=0;
			this.tabIndex=-1;

			this.appendChild(focus);
		}
		
		this.onmousedown=f_tree._BodyMouseDown;
		this.onmouseup=f_core.CancelJsEventHandler;
		this.onclick=f_core.CancelJsEventHandler;
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._performKeyDown);
	},
	f_finalize: function() {
//		this._preloadedLevelDepth=undefined;  // number
//		this._userExpandable=undefined; // boolean
//		this._images=undefined;  // boolean 
		this._tree=undefined;
//		this._hideRootExpandSign=undefined; // boolean
		
		this._cursor=undefined; // HtmlLIElement
		
//		this._lastKeyDate=undefined; // number
//		this._lastKey=undefined; // char

//		this._focus=undefined;   // boolean
		
		this._nodes=undefined;  
		this._container=undefined; // object
// 		this._opened=undefined;  // boolean
//		this._interactive=undefined; // boolean

		var cfocus=this._cfocus;
		if (cfocus) {
			this._cfocus=undefined;
			
			cfocus.onfocus=null;
			cfocus.onblur=null;
			cfocus.onkeydown=null;
			cfocus.onkeyup=null;
			cfocus.onkeypress=null;
			
			cfocus._tree=undefined;
			cfocus.f_link=undefined;
		}

		this.onfocus=null;
		this.onblur=null;
		this.onkeydown=null;
		this.onkeypress=null;
		this.onkeyup=null;
		this.onmousedown=null;
		this.onmouseup=null;
		this.onclick=null;
				
//		this._blankNodeImageURL=undefined; // string

//		this._defaultImageURL=undefined; // string
//		this._defaultExpandedImageURL=undefined; // string
//		this._defaultSelectedImageURL=undefined; // string
//		this._defaultDisabledImageURL=undefined; // string
		
//		this._defaultLeafImageURL=undefined; // string
//		this._defaultExpandedLeafImageURL=undefined; // string
//		this._defaultSelectedLeafImageURL=undefined; // string
//		this._defaultDisabledLeafImageURL=undefined; // string

		var lis=this._nodesList;
		this._nodesList=undefined;

		f_core.Debug(f_tree, "Remove LIs: "+lis.length);
		for(var i=0;i<lis.length;i++) {
			var li=lis[i];
			
			if (li._menuBar) {
			// @TODO  A Voir car cela survient !
//				alert("???? li._menuBar ???");
				continue;
			}
			
			this._nodeFinalizer(li);
		}

//		this._collapsedValues=undefined;
//		this._expandedValues=undefined;

//		this._disabledValues=undefined;
//		this._enabledValues=undefined;

		var wns=this._waitingNodes;
		if (wns) {
			this._waitingNodes=undefined;
			
			for(var i=0;i<wns.length;i++) {
				var wn=wns[i];
				
				wn._id=undefined;
				wn._li=undefined;
				wn._image=undefined;
				wn._label=undefined;
				
				f_core.VerifyProperties(wn);
			}
		}

		this.f_super(arguments);
	},
	_nodeFinalizer: function(li, deepFinalizer) {
	
		if (deepFinalizer) {
			this._nodesList.f_removeElement(li);
		
			var ul=li._nodes;
			if (ul) {
				var children=ul.childNodes;				
				for(var i=0;i<children;i++) {
					var child=children[i];
					
					this._nodeFinalizer(child, true);
				}
			}
		}		
	
		li._node=undefined;
		li._nodes=undefined;
//		li._depth=undefined; // number
//		li._className=undefined; // string
		li._tree=undefined; // f_tree
// 		li.title=null; // string
//		li._over=undefined; // boolean
//		li._clientDatas=undefined; // Map<String, String>
		
		var divNode=li._divNode;
		if (divNode) {
			li._divNode=undefined;

			divNode.onmouseover=null;
			divNode.onmouseout=null;
			divNode.onmousedown=null;
			divNode.onmouseup=null;
			divNode.onclick=null;
			divNode.ondblclick=null;

			divNode._node=undefined;
			
			f_core.VerifyProperties(divNode);			
		} else {
			if (f_core.IsDebugEnabled(f_tree)) {
				f_core.Debug(f_tree, "No div node ? "+li);
			}
		}
		
		var command=li._command;
		if (command) {
			li._command=undefined;
			
			command._node=undefined;
			command.onmousedown=null;
			command.onmouseup=null;
			command.onclick=null;
			
			f_core.VerifyProperties(command);			
		}
		
		var input=li._input;
		if (input) {
			li._input=undefined;

			input._node=undefined;
			input.onclick=null;
			// input.tabIndex=undefined; // string
			
			f_core.VerifyProperties(input);			
		}
		
		var image=li._image;
		if (image) {
			li._image=undefined;

			image._node=undefined;
			
			f_core.VerifyProperties(image);			
		}
		
		var span=li._span;
		if (span) {
			li._span=undefined;

			span.onmouseover=null;
			span.onmouseout=null;
			span._node=undefined;
		
			f_core.VerifyProperties(span);			
		}
		
		var label=li._label;
		if (label) {
			li._label=undefined;

			label._node=undefined;

			f_core.VerifyProperties(label);			
		}

		f_core.VerifyProperties(li);			
	},
	f_update: function() {
		this.fa_initializeScrollBars();

		var nodes=this._nodes;
		if (nodes) {
			this._constructTree(this, nodes, 0);
		}
		
		this.f_super(arguments);		
			
		if (!this.f_isVisible()) {
			this.f_getClass().f_getClassLoader().addVisibleComponentListener(this);
		}		
	},	
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
			return;
		}

		return this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
			return;
		}

		return this.f_super(arguments, type, target);
	},
	f_documentComplete: function() {
		this.f_super(arguments);

		if (!this.f_isVisible()) {
			return;
		}
		
		this.f_performComponentVisible();
	},

	/* ***************************************************************************** */

	/**
	 * @method hidden
	 * @return void
	 */
	f_performComponentVisible: function() {
		if (this._interactiveShow) {
			// Appel si un onglet etait en Ajax et il charge la liste !
			this.f_setFirst(this._first, this._currentCursor);			
		}
	},
	/**
	 * @method private
	 * @return void
	 */
	_constructTree: function(container, nodes, depth) {
		for(var i=0;i<nodes.length;i++) {
			var node=nodes[i];
			
			var li=document.createElement("li");

			li._node=node;
			li._depth=depth;
			li._tree=this;
			li._className="f_tree_parent";
			li.className=li._className;
			if (node._tooltip) {
				li.title=node._tooltip;
			}
			
			this._nodesList.push(li);
			
			container.appendChild(li); // Evite les fuites memoires

			var divNode=document.createElement("div");
			li._divNode=divNode;
			divNode._node=li;
			divNode.className="f_tree_depth"+depth;
			
			divNode.role="treeitem";
			divNode.onmouseover=f_tree._DivNode_mouseOver;
			divNode.onmouseout=f_tree._DivNode_mouseOut;
			divNode.onmousedown=f_tree._DivNode_mouseDown;
			divNode.onmouseup=f_core.CancelJsEventHandler;
			divNode.onclick=f_core.CancelJsEventHandler;
			divNode.ondblclick=f_tree._DivNode_dblClick;

			li.appendChild(divNode);
			
			var d=depth;
			if (this._userExpandable) {
				if (depth>0 || !this._hideRootExpandSign) {
					var command=document.createElement("img");
					command.align="center";
					command.width=f_tree._COMMAND_IMAGE_WIDTH;
					command.height=f_tree._COMMAND_IMAGE_HEIGHT;
					command.src=this._blankNodeImageURL;
					command._node=li;
		
					divNode.appendChild(command);
					li._command=command;
									
					command.onmousedown=f_tree._Command_mouseDown;
					command.onmouseup=f_core.CancelJsEventHandler;
					command.onclick=f_core.CancelJsEventHandler;
					
					this._updateCommandStyle(li);					
				}
				if (depth==1 && this._hideRootExpandSign) {
					d=0;
				}
			}
			
			divNode.style.paddingLeft=(d*f_tree._COMMAND_IMAGE_WIDTH)+"px";
			
			if (this._checkable) {
				var input=document.createElement("input");
				li._input=input;
				input._node=li;
				input.className="f_tree_check";
				input.tabIndex=-1;
				input.onclick=f_tree._NodeInput_mouseClick;
		
				var nodeIdx=(this._nodeIdx)?(this._nodeIdx++):1;
				this._nodeIdx=nodeIdx;
			
				input.id=container.id+"::"+nodeIdx;
	
				if (this._checkCardinality==fa_cardinality.ONE_CARDINALITY) {
					input.type="radio";
					input.value="CHECKED_"+nodeIdx;
					input.name=this.id+"::radio";
					
				} else {
					input.type="checkbox";
					input.value="CHECKED";
					input.name=input.id;
				}

				divNode.appendChild(input);
			}

			var span=document.createElement("span");
			li._span=span;
			divNode.appendChild(span);
			
			span._node=li;
			span.onmouseover=f_tree._NodeLabel_mouseOver;
			span.onmouseout=f_tree._NodeLabel_mouseOut;
			
			if (this._images) {
				var image=document.createElement("img");
				image.align="center";
				image.className="f_tree_image";
				image._node=li;

				span.appendChild(image);
				li._image=image;
			}
			
			var label=document.createElement("label");

			span.appendChild(label);
			li._label=label;

			label.className="f_tree_label";
			label._node=li;
			
			if (node._label) {
				label.appendChild(document.createTextNode(node._label));
			}
			
			if (this._selectable) {
				this.f_updateElementSelection(li, li._node._selected);
			}
			if (this._checkable) {	
				this.fa_updateElementCheck(li, li._node._checked);
			}
				
			var initCursorValue=this._initCursorValue;
			if (!this._cursor && node._value==initCursorValue) {
				this._cursor=li;
				this._initCursorValue=undefined;
			}			
			
			this.fa_updateElementStyle(li);
			
			if (node._container) {
				// On peut etre un container sans posseder (encore) d'enfants.
				
				var ul=document.createElement("ul");
				ul.role="treegroup";				
				ul.style.display="none";
				ul.className="f_tree_parent";

				li.appendChild(ul);
				
				li._nodes=ul;
				
			}
			
			if (node._nodes) {
				// f_core.Debug(f_tree, "constructTree: children: opened="+node._opened+" userExp="+this._userExpandable+" depth="+depth);
				
				if (node._opened || !this._userExpandable) {
					this._constructTree(li._nodes, node._nodes, depth+1);
					
					li._nodes.style.display="list-item";
				}
			}
		}
	},
	/**
	 * Close a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @param optional hidden Event evt Javascript event
	 * @return boolean <code>true</code> if success.
	 */
	f_closeNode: function(value, evt) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this._closeNode(li._node, evt, li);
	},
	/**
	 * @method protected
	 * @param Object node TreeNode
	 * @param optional Event evt Javascript event
	 * @param hidden li 
	 * @return boolean <code>true</code> if success.
	 */
	_closeNode: function(node, evt, li) {
		if (!node._opened || !node._container) {
			return false;
		}
		node._opened=false;

		if (!this._collapsedValues) {
			this._collapsedValues=new Array;
		}

		if (!this._expandedValues || !this._expandedValues.f_removeElement(node._value)) {
			this._collapsedValues.f_addElement(node._value);
		}
		
		var ul=li._nodes;
	
		ul.style.display="none";
			
		this.fa_updateElementStyle(li);
		this._updateCommandStyle(li);
		
		return true;
	},
	/**
	 * Open a node.
	 * 
	 * @method public
	 * @param any value Value of the node, or the node object
	 * @param optional hidden Event evt Javascript event
	 * @return boolean <code>true</code> if success.
	 */
	f_openNode: function(value, evt) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this._openNode(li._node, evt, li);
	},
	/**
	 * @method protected
	 * @param Object node 
	 * @param optional Event evt Javascript event.
	 * @param hidden li
	 * @return boolean <code>true</code> if success ...
	 */
	_openNode: function(node, evt, li) {
		if (!node._container) {
			f_core.Info(f_tree, "Node is not a container !");
			return false;
		}
		if (node._opened) {
			f_core.Info(f_tree, "Node is already opened !");
			return false;
		}
		node._opened=true;

		if (!this._expandedValues) {
			this._expandedValues=new Array;
		}

		if (!this._collapsedValues || !this._collapsedValues.f_removeElement(node._value)) {
			this._expandedValues.f_addElement(node._value);
		}

		if (!li) {
			li=this._searchComponentByNodeOrValue(node);
		}

		var ul=li._nodes;

		if (node._interactive) {
			// Noeuds à charger dynamiquement ....
			node._interactive=undefined;
			
			if (!ul) {
				ul=document.createElement("ul");
				ul.className="f_tree_parent";
				ul.role="treegroup";
			
				li.appendChild(ul);
			
				li._nodes=ul;
			} else {
				ul.style.display="list-item";
			}
							
			var waitingNode=this._newWaitingNode(li._depth);
			ul.appendChild(waitingNode);
	
			if (!this._waitingNodes) {
				this._waitingNodes=new Array;
			}
			waitingNode._id=this._waitingNodes.length;
			waitingNode._li=li;

			this._waitingNodes.push(waitingNode);
		
			this._callServer(waitingNode);
		
			return;
		}
		
		if (!ul.hasChildNodes()) {
			// Il faut créer les composants ...
			
			this._constructTree(ul, node._nodes, li._depth+1);
		}		
		
		ul.style.display="list-item";

		this.fa_updateElementStyle(li);
		this._updateCommandStyle(li);
		
		return true;
	},
	_reloadTree: function() {
		var ul=this;
	
		var waitingNode=this._newWaitingNode(0);
		ul.appendChild(waitingNode);

		if (!this._waitingNodes) {
			this._waitingNodes=new Array;
		}
		waitingNode._id=this._waitingNodes.length;
		waitingNode._li=this;

		this._waitingNodes.push(waitingNode);
	
		this._callServer(waitingNode);
	
		return;
	},
	_callServer: function(waitingNode) {
		var error=false;
		var params="";
		
		if  (waitingNode._li!=this) {
			for(var c=waitingNode._li;;) {
				var u=c.parentNode;
				if (u.tagName.toLowerCase()!="ul") {
					error=true;
					break;
				}
				
				var lis=u.childNodes;
				var liIdx=0;
				for(var i=0;i<lis.length;i++) {
					var li=lis[i];
					if (li.tagName.toLowerCase()!="li") {
						continue;
					}
					
					if (li==c) {
						if (params) {
							params=","+params;
						}
						
						params=liIdx+params;
						liIdx=-1;
						break;
					}
					
					liIdx++;
				}
				if (liIdx>=0) {
					error=true;
					f_core.Error(f_tree, "LI not found ?");
					break;
				}
				
				c=u.parentNode;
				if (c.tagName.toLowerCase()!="li") {
					break;
				}
			}
			if (!params.length) {
				waitingNode.parentNode.removeChild(waitingNode);
				return;
			}
		}
		if (error) {
			waitingNode.parentNode.removeChild(waitingNode);
			return;
		}
		
		var url=f_env.GetViewURI();
		var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var tree=this;

		request.f_setListener({
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			var label=waitingNode._label;
				if (label) {
					label.innerHTML="ERREUR !";
					label.className="f_waiting_error";
				}
				
				var image=waitingNode._image;
				if (image) {
					image.src=f_waiting.GetWaitingErrorImageURL();
				}
				
				f_tree.Info(f_tree, "Bad status: "+request.f_getStatus());
				
		 		tree.f_performErrorEvent(request, f_error.HTTP_ERROR, text);
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
				if (waitingNode._label) {
					f_core.SetTextNode(waitingNode._label, f_waiting.GetReceivingMessage());
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
					tree.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
					return;
				}

				var responseContentType=request.f_getResponseContentType();
				if (responseContentType.indexOf(f_error.ERROR_MIME_TYPE)>=0) {
			 		tree.f_performErrorEvent(request, f_error.APPLICATION_ERROR, content);
					return;
				}
				
				if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
		 			tree.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);

					return;
				}

	 			var ret=request.f_getResponse();
				try {
					//alert("ret="+ret);
					eval(ret);

				} catch(x) {				
				 	tree.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
				}
	
				var event=new f_event(tree, f_event.CHANGE);
				try {
					tree.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
	 		}			
		});

//		alert("Params="+params);

		request.f_setRequestHeader("X-Camelia", "tree.request");
		
		var params = {
			treeId: this.id,
			waitingId: waitingNode._id,
			node: params 
		};
		request.f_doFormRequest(params);
		
	},
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	_newWaitingNode: function(parentDepth) {
		var li=document.createElement("li");

		li.className="f_tree_parent";
		
		var divNode=document.createElement("div");
		li.appendChild(divNode);
		divNode.className="f_tree_depth"+(parentDepth+1);
		divNode.style.paddingLeft=(parentDepth*f_tree._COMMAND_IMAGE_WIDTH)+"px";
		
		var command=document.createElement("img");
		divNode.appendChild(command);

		command.align="center";
		command.width=f_tree._COMMAND_IMAGE_WIDTH;
		command.height=f_tree._COMMAND_IMAGE_HEIGHT;
		command.src=this._blankNodeImageURL;

		var span=document.createElement("span");
		divNode.appendChild(span);
		
		var image=document.createElement("img");
		span.appendChild(image);

		image.align="center";
		image.width=f_waiting.WAIT_IMAGE_WIDTH;
		image.height=f_waiting.WAIT_IMAGE_HEIGHT;
		image.src=f_waiting.GetWaitingImageURL();
		image.className="f_tree_image";
		li._image=image;
			
		var label=document.createElement("label");
		span.appendChild(label);
		li._label=label;

		label.className="f_tree_label";

		var txt=f_waiting.GetLoadingMessage();
		label.appendChild(document.createTextNode(txt));

		return li;		
	},
	/**
	 * Show a node.
	 * 
	 * @method public
	 * @param any value Value of the node, or the node object
	 * @return boolean <code>true</code> if the node was found.
	 */
	f_showNode: function(value) {
		var item=this._searchComponentByNodeOrValue(value);
		if (!item) {
			return false;
		}
		
		this.fa_showElement(item);
		
		return true;
	},
	fa_showElement: function(item) {
		f_core.Assert(item && item.tagName, "Item parameter must be a LI tag ! ("+item+")");
		
		//f_core.Debug(f_tree, "Show="+item._label.innerHTML);
		if (item.offsetTop-this.scrollTop<0) {
			this.scrollTop=item.offsetTop;
			//	f_core.Debug(f_tree, "Show=UP");
			return;
		}	
		
		if (item.offsetTop+item._label.offsetHeight-this.scrollTop>this.clientHeight) {			
			this.scrollTop=item.offsetTop+item.offsetHeight-this.clientHeight;

			// f_core.Debug(f_tree, "Show=DOWN itemO="+item.offsetTop+" laH="+item._label.offsetHeight+", stop="+this.scrollTop+", ch="+this.clientHeight);
			return;
		}
	},
	fa_updateElementStyle: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "Invalid LI parameter ("+li+")");
		
		var node=li._node;
	
		var suffixLabel="";
		var suffixDivNode="";
	
		if (node._disabled) {
			if (!node._container) {
				suffixDivNode+="_leaf"
			}	
			
			suffixLabel+="_disabled";
			suffixDivNode+="_disabled";
		
		} else {
			if (node._opened) {
				suffixDivNode+="_opened"
				
			} else if (!node._container) {
				suffixDivNode+="_leaf"
			}	
		
			if (li._over) {
				suffixDivNode+="_hover";
			}

			if (node._selected) {
				suffixLabel+="_selected";
			}				
			if (this._focus && li==this._cursor) {
				suffixLabel+="_focus";
			}
			if (li._labelOver) {
				suffixLabel+="_hover";
			}
		}
	
		var divNode=li._divNode;
		var divNodeClassName="f_tree_depth f_tree_depth"+li._depth;
		if (suffixDivNode) {
			divNodeClassName+=" f_tree_depth"+suffixDivNode+" f_tree_depth"+li._depth+suffixDivNode;
		}
		
		var labelClassName="f_tree_node";
		if (suffixLabel) {
			labelClassName+=" f_tree_node"+suffixLabel;
		}
		
		if (divNode.className!=divNodeClassName) {
			divNode.className=divNodeClassName;
		}
	
		var liImage=li._image;	
		if (liImage) {
			var imageURL=this._searchNodeImageURL(node);
			
			if (liImage.src!=imageURL) {
				liImage.src=imageURL;
			}
		}
		
		if (node._styleClass) {
			labelClassName+=" "+node._styleClass;
			if (suffixLabel) {
				labelClassName+=" "+node._styleClass+suffixLabel;
			}
		}
		
		var span=li._span;
		if (span.className!=labelClassName) {
			span.className=labelClassName;
		}
		
		labelClassName="f_tree_label";
		if (this._cursor==li && this._focus) {
			labelClassName+=" "+labelClassName+"_cursor";
		}

		var label=li._label;
		if (label.className!=labelClassName) {
			label.className=labelClassName;
		}
		
		var input=li._input;
		if (input) {
			if (node._cheked!=input.checked) {
				input.checked=node._checked;
				
				if (f_core.IsInternetExplorer()) {
					// Il se peut que le composant ne soit jamais affiché 
					// auquel cas il faut utiliser le defaultChecked !
					input.defaultChecked=node._checked;
				}
			}
			if (input.disabled!=node._disabled) {
				input.disabled=node._disabled;
			}
		}
	},
	_updateCommandStyle: function(li) {
		var command=li._command;
		if (!command) {
			return;
		}

		var node=li._node;
		
		
		var suffix="";
		if (node._container) {
			if (!node._opened) {
				suffix+="_opened";
			} else {
				suffix+="_closed";
			}
		}
		
		if (node._selected) {
			suffix+="_selected";
		}

		var className="f_tree_command";
		if (suffix) {
			className+=" "+className+suffix;
		}

		if (className!=command.className) {
			command.className=className;
		}
	},
	_searchNodeImageURL: function(node) {
		var imageURL;
		
		if (node._disabled) {
			imageURL=node._disabledImageURL;
			if (imageURL) { 
				return imageURL;
			}
		}
		
		if (node._opened) {
			imageURL=node._expandedImageURL;
			if (imageURL) { 
				return imageURL;
			}
		}
		
		if (node._selected) {
			imageURL=node._selectedImageURL;
			if (imageURL) { 
				return imageURL;
			}
		}
		
		if (node._imageURL) {	
			return node._imageURL;
		}

		if (!node._container) {
			if (node._disabled) {
				imageURL=this._defaultDisabledLeafImageURL;
				if (imageURL) { 
					return imageURL;
				}
			}

// ????? C'est possible ca ????  (leaf expanded ???)
			if (node._opened) {
				imageURL=this._defaultExpandedLeafImageURL;
				if (imageURL) { 
					return imageURL;
				}
			}
			
			if (node._selected) {
				imageURL=this._defaultSelectedLeafImageURL;
				if (imageURL) { 
					return imageURL;
				}
			}
			
			if (this.f_leafImageURL) {	
				return this._defaultLeafImageURL;
			}
		}

		if (node._opened) {
			imageURL=this._defaultExpandedImageURL;
			if (imageURL) { 
				return imageURL;
			}
		}
		
		if (node._selected) {
			imageURL=this._defaultSelectedImageURL;
			if (imageURL) { 
				return imageURL;
			}
		}

		if (this._defaultImageURL) {	
			return this._defaultImageURL;
		}
		
		return this._blankNodeImageURL;
	},
	/** 
	 * @method hidden
	 */
	f_appendNode2: function(parent, properties) {
		
		var node=this.f_appendNode(parent, properties._label, properties._value, properties._description, properties._disabled);

		if (properties._expanded) {
			node._opened=true;
		}

		if (properties._selected) {
			node._selected=true;
		}

		if (properties._checked) {
			node._checked=true;
		}
		
		if (properties._imageURL) {
			this.f_setItemImages(node, 
				properties._imageURL, 
				properties._disabledImageURL, 
				properties._hoverImageURL, 
				properties._selectedImageURL);
		}
		
		if (properties._clientDatas) {
			this.f_setItemClientDatas(node, properties._clientDatas);
		}
		
		if (properties._styleClass) {
			node._styleClass=properties._styleClass;
		}
		
		return node;
	},
	/** 
	 * @method public
	 */
	f_appendNode: function(parent, label, value, tooltip, disabled) {
//		f_core.Assert(!parent || !parent.tagName, "Bad type of parent ! "+parent);
	
		var node=new Object;
		
		node._label=label;
		if (value) {
			node._value=value;
		}
		if (tooltip) {
			node._tooltip=tooltip;
		}
		if (disabled) {
			node._disabled=disabled;
		}
		
		this._checkNodeAttributes(arguments, 5, node);

		if (node._opened===undefined && !this._userExpandable) {
			node._opened=true;
		}
		
		if (!parent._nodes) {
			parent._nodes=new Array;
			parent._container=true;
		}
		
		node._parentTreeNode=parent;
		
		parent._nodes.push(node);
		
		return node;
	},
	_checkNodeAttributes: function(args, atts, node) {
		if (atts>=args.length) {
			return;
		}
		
		if (this._userExpandable) {
			node._opened=(args[atts++])?true:false;
			
			if (atts>=args.length) {
				return;
			}
		}

		if (this._selectable && !this._selectionFullState) {
			if (args[atts++]) {
				node._selected=true;
			}
			
			if (atts>=args.length) {
				return;
			}
		}

		if (this._checkable && !this._checkFullState) {
			node._checked=(args[atts++])?true:false;
			
			if (atts>=args.length) {
				return;
			}
		}
	},
	/**
	 * @method hidden
	 */
	f_setItemImages: function() {
		var atts=0;
		var node=arguments[atts++];
		
		var imageURL=arguments[atts++];
		if (imageURL) {
			this.f_setNodeImageURL(node, imageURL);
		}

		if (atts>=arguments.length) {
			return;
		}

		var expandedImageURL=arguments[atts++];
		if (expandedImageURL) {
			this.f_setExpandedNodeImageURL(node, expandedImageURL);
		}

		if (atts>=arguments.length) {
			return;
		}

		var selectedImageURL=arguments[atts++];
		if (selectedImageURL) {
			this.f_setSelectedNodeImageURL(node, selectedImageURL);
		}

		if (atts>=arguments.length) {
			return;
		}

		var disabledImageURL=arguments[atts++];
		if (disabledImageURL) {
			this.f_setDisabledNodeImageURL(node, disabledImageURL);
		}
	},
	f_setNodeImageURL: function(node, imageURL) {
		node._imageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	f_setExpandedNodeImageURL: function(node, imageURL) {
		node._expandedImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	f_setSelectedNodeImageURL: function(node, imageURL) {
		node._selectedImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	f_setDisabledNodeImageURL: function(node, imageURL) {
		node._disabledImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	/**
	 * @method private
	 * @param f_event cevt
	 * @return boolean
	 */
	_performKeyDown: function(cevt) {
		var evt=cevt.f_getJsEvent();
	
		var cancel=false;
		
		var selection=fa_selectionManager.ComputeKeySelection(evt);
		
		var cardinality=this._selectionCardinality;
		
		var scroll=(cardinality==fa_cardinality.OPTIONAL_CARDINALITY || 
						cardinality==fa_cardinality.ONE_CARDINALITY);
		
		var code=evt.keyCode;
		switch(code) {
		case f_key.VK_DOWN: // FLECHE VERS LE BAS
			if (evt.ctrlKey && scroll) {
				// Scroll la zone vers le bas sans bouger le curseur !
			} else {
				this._nextTreeNode(evt, selection);
			}
			cancel=true;
			break;
						
		case f_key.VK_UP: // FLECHE VERS LE HAUT
			if (evt.ctrlKey && scroll) {
				// Scroll la zone vers le haut sans bouger le curseur !
			} else {
				this._previousTreeNode(evt, selection);
			}
			cancel=true;
			break;
			
		case f_key.VK_PAGE_DOWN:
			this._nextPageTreeNode(evt, selection);
			cancel=true;
			break;
			
		case f_key.VK_PAGE_UP:
			this._previousPageTreeNode(evt, selection);
			cancel=true;
			break;
			
		case f_key.VK_HOME:
			this._firstTreeNode(evt, selection);
			cancel=true;
			break;
		
		case f_key.VK_END: 
			this._lastTreeNode(evt, selection);
			cancel=true;
			break;
			
		case f_key.VK_RIGHT:
		case f_key.VK_ADD: // FLECHE VERS LA DROITE
			this._openTreeNode(evt, selection);
			cancel=true;
			break;
			
		case f_key.VK_LEFT:
		case f_key.VK_SUBTRACT: // FLECHE VERS LA GAUCHE
			this._closeTreeNode(evt, selection);
			cancel=true;
			break;

		case f_key.VK_MULTIPLY: // FLECHE VERS LA GAUCHE
			this._expandAllTreeNode(evt);
			cancel=true;
			break;

		case f_key.VK_SPACE:
			if (this._checkable) {
				this.fa_performElementCheck(this._cursor, true, evt);
				cancel=true;
				break;
			}
				
			// Continue comme une selection ....
			
		case f_key.VK_RETURN:
		case f_key.VK_ENTER:
						
			if (this._cursor && this._selectable) {
				this._performElementSelection(this._cursor, true, evt, selection);
			}
			cancel=true;
			break;

		case f_key.VK_CONTEXTMENU:
			this._openContextMenu(evt);
			cancel=true;
			break;

		default:
			if (f_key.IsLetterOrDigit(code)) {
				this._searchTreeNode(code, evt);
				
				// Dans tous les cas !
				cancel=true;
				
			} else {
				// Rien on laisse faire !			
			}
		}

		if (cancel) {
			return f_core.CancelJsEvent(evt);		
		}
		
		return true;
	},
	_openContextMenu: function(evt) {
		var cursorLi=this._cursor;
		if (!cursorLi && !tree.fa_isElementDisabled(cursorLi)) {
			return;
		}
		
		var menu=this.f_getSubMenuById(f_tree._NODE_MENU_ID);
		if (menu) {
			menu.f_open(evt, {
				component: cursorLi._span,
				position: f_popup.MIDDLE_COMPONENT
			});
		}
	},
	_nextTreeNode: function(evt, selection) {	
		var cursorLi=this._cursor;
		if (!cursorLi) {
			this._firstTreeNode(evt, selection);
			return;
		}
		
		var lis=this.fa_listVisibleElements();
		
		var i=0;
		for(;i<lis.length;i++) {
			var l=lis[i];
			
			if (l!=cursorLi) {
				continue;
			}
			
			i++;
			break;
		}
		
		if (i>=lis.length) {
			return;
		}

		this.f_moveCursor(lis[i], true, evt, selection);
	},
	_lastTreeNode: function(evt, selection) {
		var lis=this.fa_listVisibleElements();
		if (!lis || !lis.length) {
			return;
		}
		
		this.f_moveCursor(lis[lis.length-1], true, evt, selection);
	},
	_nextPageTreeNode: function(evt, selection) {		
		var cursorLi=this._cursor;
		if (!cursorLi) {
			this._firstTreeNode(evt, selection);
			return;
		}

		var lis=this.fa_listVisibleElements();
		
		var parent=this;
		
		var i=0;
		var last=null;
		for(;i<lis.length;i++) {
			var li=lis[i];
			
			if (li.offsetTop+li._span.offsetHeight/2-parent.scrollTop>parent.clientHeight) {
				break;
			}
			
			last=li;
		}
		
		if (last==null) {
			return;
		}
		
		if (last==cursorLi) {
			var next=i+Math.floor(parent.scrollHeight/last._span.offsetHeight);
			if (next>=lis.length) {
				next=lis.length-1;
			}
			
			last=lis[next];
			if (last==cursorLi) {
				return;
			}		
		}

		this.f_moveCursor(last, true, evt, selection);
	},
	_firstTreeNode: function(evt, selection) {		
		var lis=this.fa_listVisibleElements();
		if (!lis || !lis.length) {
			return;
		}

		this.f_moveCursor(lis[0], true, evt, selection);
	},
	_previousTreeNode: function(evt, selection) {
		var cursorLi=this._cursor;
		if (!cursorLi) {
			this._firstTreeNode(evt, selection);
			return;
		}

		var lis=this.fa_listVisibleElements();
		
		var i=0;
		for(;i<lis.length;i++) {
			var l=lis[i];
			
			if (l!=cursorLi) {
				continue;
			}
			
			i--;
			break;
		}		
		
		if (i<0) {
			return;
		}
		
		this.f_moveCursor(lis[i], true, evt, selection);
	},
	
	_previousPageTreeNode: function(evt, selection) {		
		var cursorLi=this._cursor;
		if (!cursorLi) {
			this._firstTreeNode(evt, selection);
			return;
		}
		
		var lis=this.fa_listVisibleElements();
		
		var parent=this;
		
		var i=0;
		var last=null;
		for(;i<lis.length;i++) {
			var li=lis[i];
			
			if (li.offsetTop+li._span.offsetHeight/2-parent.scrollTop>0) {
				last=li;
				// On le voit !
				break;
			}		
		}
		
		if (last==null) {
			return;
		}
		
		if (last==cursorLi) {
			var next=i-Math.floor(parent.scrollHeight/last._span.offsetHeight);
			if (next<0) {
				next=0;
			}
			
			last=lis[next];
			if (last==cursorLi) {
				return;
			}		
		}

		this.f_moveCursor(last, true, evt, selection);
	},
	fa_listVisibleElements: function(container, list) {
		if (container===undefined) {
			container=this;
			list=new Array;
		}
		
		var children=container.childNodes;
		
		for(var i=0;i<children.length;i++) {
			var li=children[i];
			var node=li._node;
			
			if (!node) {
				continue;
			}
			
			list.push(li);
			
			if (this._userExpandable && !node._opened) {
				continue;
			}
			
			var ul=li.getElementsByTagName("ul");
			if (ul.length) {
				this.fa_listVisibleElements(ul[0], list);
			}
		}
		
		return list;		
	},
	_openTreeNode: function(evt, selection) {
		var cursorLi=this._cursor;
		if (!cursorLi) {
			return;
		}

		var cursorNode=cursorLi._node;
		
		if (cursorNode._container && cursorNode._opened) {
			var nodes=cursorNode._nodes;
			if (!nodes && !nodes.length) {
				return;
			}
			
			var childLi=this._searchComponentByNodeOrValue(nodes[0]);
	
			this.f_moveCursor(childLi, true, evt, selection);
			return;
		}
		
		this.fa_showElement(cursorLi);
		this.f_openNode(cursorLi._node, evt, cursorLi);
	},
	_closeTreeNode: function(evt, selection) {
		var cursorLi=this._cursor;
		if (!cursorLi) {
			return;
		}

		var cursorNode=cursorLi._node;
		
		if (cursorNode._container && cursorNode._opened) {
			this._closeNode(cursorNode, evt, cursorLi);
			this.fa_showElement(cursorLi);
			return;
		}

		// Retourne au parent !
		var parentNode=cursorNode._parentTreeNode;
		if (!parentNode || parentNode==this) {
			return;
		}

		var parentLi=this._searchComponentByNodeOrValue(parentNode);

		this.f_moveCursor(parentLi, true, evt, selection);
	},
	_expandAllTreeNode: function(evt) {
		var cursorLi=this._cursor;
		if (!cursorLi) {
			return;
		}

		var cursorNode=cursorLi._node;
		if (!cursorNode._container) {
			return;
		}

		this.fa_showElement(cursorLi);

		var listLI=new Array;
		listLI.push(cursorLi);
		
		for(var i=0;i<listLI.length;i++) {
			var li=listLI[i]
			var node=li._node;
			
			if (!node._container) {
				continue;
			}
			
			if (!node._opened) {
				this.f_openNode(node, evt);
			}
			
			var ul=f_core.GetFirstElementByTagName(li, "ul");
			if (!ul) {
				continue;
			}

			this.fa_listVisibleElements(ul, listLI);
		}
	},
	_searchTreeNode: function(code, evt) {
		var lis=this.fa_listVisibleElements();
		
		var pos=0;

		var cursorLi=this._cursor;
		if (cursorLi) {
			var i;
			for(i=0;i<lis.length;i++) {
				var l=lis[i];
				if (l!=cursorLi) {
					continue;
				}
				
				i++;
				break;
			}
			
			if (i<lis.length) {
				pos=i;
			}
		}
		
		var key=String.fromCharCode(code).toUpperCase();
	
		var now=new Date().getTime();
		if (this._lastKeyDate!==undefined) {
			var dt=now-this._lastKeyDate;
			f_core.Debug(f_tree, "_searchTreeNode: Delay key down "+dt+"ms");
			if (dt<f_tree._SEARCH_KEY_DELAY) {
				key=this._lastKey+key;
			}
		}
		
		this._lastKeyDate=now;
		this._lastKey=key;
		
		var kl=key.length;
		
		for(;kl>0;kl--) {
			for(var i=0;i<lis.length;i++) {
				var li=lis[pos++];
				if (pos>=lis.length) {
					pos=0;
				}
				
				var label=li._node._label;
				
				if (!label) {
					continue;
				}
				
				if (label.substring(0, kl).toUpperCase()!=key.substring(0, kl)) {
					continue;
				}
				
				this.f_moveCursor(li, true, evt);
				return true;
			}
		}
		
		return false;
	},
	_updateSelectedNodes: function() {
		var cursorLi=this._cursor;
		
		var currentSelection=this._currentSelection;
		for(var i=0;i<currentSelection.length;i++) {
			var li=currentSelection[i];
			if (cursorLi==li) {
				cursorLi=undefined;
			}
			
			this.fa_updateElementStyle(li);
		}
		
		if (cursorLi) {
			this.fa_updateElementStyle(cursorLi);
		}
	},
	f_setFocus: function() {

		f_core.Debug(f_tree, "f_setFocus: Set focus on tree '"+this.id+"' cfocus="+this._cfocus);

		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}

		if (this._cfocus) {
			this._cfocus.focus();
			return;
		}
		
		if (!this.focus) {
			return;
		}
		
		this.focus();
	},
	/**
	 * @method private
	 */
	_searchComponentByNodeOrValue: function(nodeOrValue) {
		f_core.Assert(nodeOrValue, "f_tree._searchComponentByNodeOrValue: Value parameter is null !");

		var lis=this.getElementsByTagName("li");
		for(var i=0;i<lis.length;i++) {
			var li=lis[i];
			
			var n=li._node;
			if (!n || (n!=nodeOrValue && n._value!=nodeOrValue)) {
				continue;
			}
			
			return li;
		}
		
		throw new Error("Can not find node with value '"+nodeOrValue+"'.");
	},
	_setDefaultImages: function() {
		var i=0;

		this._images=true;

		var url=arguments[i++];
		if (url) this._defaultImageURL=url;

		url=arguments[i++];
		if (url) this._defaultExpandedImageURL=url;

		url=arguments[i++];
		if (url) this._defaultSelectedImageURL=url;

		url=arguments[i++];
		if (url) this._defaultDisabledImageURL=url;

		url=arguments[i++];
		if (url) this._defaultLeafImageURL=url;

		url=arguments[i++];
		if (url) this._defaultExpandedLeafImageURL=url;
		
		url=arguments[i++];
		if (url) this._defaultSelectedLeafImageURL=url;		

		url=arguments[i++];
		if (url) this._defaultDisabledLeafImageURL=url;
	},
	/**
	 * Select a node
	 *
	 * @method public
	 * @param any value Value of the node.
	 * @param optional boolean append Append mode.
	 * @param optional boolean show Node must be show after the selection.
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return boolean <code>true</code> if success.
	 */
	f_select: function(value, append, show, jsEvent) {
		var li=this._searchComponentByNodeOrValue(value);
		
		var selection=(append)?fa_selectionManager.APPEND_SELECTION:0;
		
		return this._performElementSelection(li, show, jsEvent, selection);
	},
	/**
	 * Check a node.
	 * 
	 * @method public
	 * @param any value Value of the node
	 * @param optional boolean show Node must be show after the selection.
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return boolean <code>true</code> if success !
	 */
	f_check: function(value, show, jsEvent) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this.fa_performElementCheck(li, true, jsEvent, true);
	},
	/**
	 * Uncheck a node.
	 * 
	 * @method public
	 * @param any value Value of the node
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return boolean <code>true</code> if success.
	 */
	f_uncheck: function(value, jsEvent) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this.fa_performElementCheck(li, false, jsEvent, false);
	},
	/**
	 * Returns the check state of a node.
	 * 
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return boolean <code>true</code> if the node is checked.
	 */
	f_getChecked: function(value) {
		var li=this._searchComponentByNodeOrValue(value);

		return this.fa_isElementChecked(li);
	},
	/**
	 * Returns the expand state of a node.
	 * 
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return boolean <code>true</code> if the node is expanded. (open)
	 */
	f_isOpened: function(value) {
		var li=this._searchComponentByNodeOrValue(value);

		return (li._node._opened)?true:false;
	},
	/**
	 * Returns the selection state of a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return boolean <code>true</code> if the node is selected.
	 */
	f_isSelected: function(value) {
		var li=this._searchComponentByNodeOrValue(value);

		return this.fa_isElementSelected(li);
	},
	/**
	 * Returns the disable state of a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return boolean
	 */
	f_isNodeDisabled: function(value) {
		var li=this._searchComponentByNodeOrValue(value);

		return this.fa_isElementDisabled(li);
	},
	/**
	 * Disable or enable a tree node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @param optional boolean disabled State to set.
	 * @return void
	 */
	f_setNodeDisabled: function(value, disabled) {
		var li=this._searchComponentByNodeOrValue(value);

		disabled=(disabled!==false)?true:false;
		li._node._disabled=disabled;
		this.fa_updateElementStyle(li);
		
		// C'est pas forcement la bonne value !
		value=li._node._value;
		
		var disabledValues=this._disabledValues;
		var enabledValues=this._enabledValues;
		
		if (disabled) {
			if (enabledValues.f_removeElement(value)) {
				return;
			}
			
			disabledValues.push(value);
			return;
		}
		
		if (disabledValues.f_removeElement(value)) {
			return;
		}
		
		enabledValues.push(value);
	},
	/**
	 * Returns the value of each children of a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return Object[] value of each children nodes.
	 */
	f_listChildrenValues: function(value) {
		var parentNode;
		if (value===undefined) {
			parentNode=this;

		} else {
			parentNode=this._searchComponentByNodeOrValue(value);
		}
	
		var ret=new Array;
		var children=parentNode._nodes;
		if (!children) {
			return ret;
		}
		
		for(var i=0;i<children.length;i++) {
			var child=children[i];
			
			ret.push(child._value);
		}
		
		return ret;
	},
	/**
	 * Returns the label of a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return String
	 */
	f_getNodeLabel: function(value) {
		var li=this._searchComponentByNodeOrValue(value);

		return li._node._label;
	},
	/**
	 * Returns the value of a node.
	 * 
	 * @method public
	 * @param Object node Node object.
	 * @return any Value of the node.
	 */
	f_getNodeValue: function(node) {
		f_core.Assert(node._parentTreeNode, "f_getNodeLabel: Node parameter is invalid !");

		return node._value;
	},
	/**
	 * Call a callback for each loaded node.
	 *
	 * @method public 
	 * @param Function callBack Callback called for each node !
	 * @return any
	 */
	f_forEachNode: function(callBack, parent) {
		if (!parent) {
			parent=this;
		}
		
		var nodes=parent._nodes;
		if (!nodes) {
			return null;
		}

		for(var i=0;i<nodes.length;i++) {
			var node=nodes[i];
		
			var ret=callBack.call(this, node);
			if (ret) {
				return ret;
			}
			
			if (!node._nodes) {
				continue;
			}
			
			ret=this.f_forEachNode(callBack, node);
			if (ret) {
				return ret;
			}				
		}

		return null;
	},
	/**
	 * Search a node by a specified value.
	 *
	 * @method public
	 * @param any value Value of the node.
	 * @param hidden boolean throwException
	 * @return Object found node.
	 */
	f_getNodeByValue: function(value, throwException) {
		var ret=this.f_forEachNode(function(node) {
			if (node._value==value) {
				return node;
			}
			
			return null;
		});
		if (ret) {
			return ret;
		}
		
		if (!throwException) {
			return null;
		}
		
		throw new Error("Can not find a node with value '"+value+"'.");
	},
	fa_updateDisabled: function() {
		if (!this.fa_componentUpdated) {
			return;
		}
	},
	fa_updateReadOnly: function() {
	},
	/** 
	 * @method hidden
	 * @param Object node
	 * @return void
	 */
	f_setInteractiveParent: function(node) {
		node._container=true;
		node._interactive=true;
	},
	f_serialize: function() {
		if (this._userExpandable) {			
			var expandedValues=this._expandedValues;
			if (expandedValues) {
				this.f_setProperty(f_prop.EXPANDED_ITEMS, expandedValues, true);
			}
	
			var collapsedValues=this._collapsedValues;
			if (collapsedValues) {
				this.f_setProperty(f_prop.COLLAPSED_ITEMS, collapsedValues, true);
			}
		}
		
		var disabledValues=this._disabledValues;
		if (disabledValues) {
			this.f_setProperty(f_prop.DISABLED_ITEMS, disabledValues, true);
		}
	
		var enabledValues=this._enabledValues;
		if (enabledValues) {
			this.f_setProperty(f_prop.ENABLED_ITEMS, enabledValues, true);
		}
	
		var cursor=this._cursor;
		var cursorValue=null;
		if (cursor) {
			cursorValue=this.fa_getElementValue(cursor);
		}
		this.f_setProperty(f_prop.CURSOR, cursorValue);
		
		this.f_setProperty(f_prop.HORZSCROLLPOS, this.scrollLeft);
		this.f_setProperty(f_prop.VERTSCROLLPOS, this.scrollTop);
		
		return this.f_super(arguments);
	},
	/** 
	 * @method hidden
	 * @param String waitingId Identifier of waiting process
	 * @return Object
	 */
	f_getWaitingNode: function(waitingId) {
		var waiting=this._waitingNodes[waitingId];
		f_core.Assert(waiting, "f_tree.f_getWaitingNode: Can not find waiting #"+waitingId);

		var li=waiting._li;
		if (li==this) {
			return this;
		}

		return li._node;
	},
	/** 
	 * @method hidden
	 * @param String waitingId Identifier of waiting process
	 * @return void
	 */
	f_clearWaiting: function(waitingId) {
		var waiting=this._waitingNodes[waitingId];
		f_core.Assert(waiting, "f_tree.f_clearWaiting: Can not find waiting #"+waitingId);

		f_core.Debug(f_tree, "_clearWaiting id='"+waitingId+"'.");

		var li=waiting._li;
		f_core.Assert(li, "Waiting node is already cleared !");
		
		waiting._li=undefined;
		waiting._image=undefined;
		waiting._label=undefined;
		
		waiting.parentNode.removeChild(waiting);

		if (li==this) {			
			f_core.Debug(f_tree, "f_clearWaiting: reconstruct tree.");

			var nodes=this._nodes;
			if (nodes) {
				this._constructTree(this, nodes, 0);
			}
			
			return;
		}			

		var node=li._node;
		f_core.Debug(f_tree, "f_clearWaiting: construct node '"+node._value+"'.");
	
		if (node._nodes && node._opened) {
			var ul=li._nodes;
			
			this._constructTree(ul, node._nodes, li._depth+1);
			
			ul.style.display="list-item";
	
			this.fa_updateElementStyle(li);
			this._updateCommandStyle(li);			
		}
	},
	/**
	 * Refresh the structure of the tree.
	 * 
	 * @method public
	 * @param optional any value Value of the node, or the node object.
	 * @return void
	 */
	f_refreshContent: function(value) {
		if (value===undefined) {		
			var children=this._nodes;
			
			var ul=this;

			var lis=ul.childNodes;
			for(var i=0;i<lis.length;) {
				var li=lis[i];
				if (li.tagName.toLowerCase()!="li") {
					i++;
					continue;
				}
				
				ul.removeChild(li);
			}
			
			this._nodes=new Array;
			for(var i=0;i<children;i++) {
				var child=children[i];
				
				this._nodeFinalizer(child, true);
			}

			this._reloadTree();
			return;
		}
			
		var li=this._searchComponentByNodeOrValue(value);
		var node=li._node;
	
		var opened=this.f_isOpened(node);
		if (opened) {
			this._closeNode(node, null, li);
		}
				
		f_core.Debug(f_tree, "f_refreshContent: Refreshed node open state="+opened);
		
		node._nodes=undefined;
		this.f_setInteractiveParent(node);
		
		var ul=li._nodes;
		if (ul) {
			li._nodes=undefined;
			
			li.removeChild(ul);
	
			var children=ul.childNodes;
			
			for(var i=0;i<children;i++) {
				var child=children[i];
				
				this._nodeFinalizer(child, true);
			}
		}
				
		if (opened) {
			this._openNode(node, null, li);
		}
	},
	fa_componentCaptureMenuEvent: function() {
		return null;
	},

	fa_getElementItem: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_getElementItem: Invalid element parameter ! ("+li+")");

		return li._node;
	},


	fa_getElementValue: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_getElementValue: Invalid element parameter ! ("+li+")");

		return li._node._value;
	},

	fa_isElementDisabled: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_isElementDisabled: Invalid element parameter ! ("+li+")");
		
		return li._node._disabled;
	},

	fa_isElementSelected: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_isElementSelected: Invalid element parameter ! ("+li+")");
		
		return li._node._selected;
	},
	
	fa_setElementSelected: function(li, selected) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_setElementSelected: Invalid element parameter ! ("+li+")");
		
		li._node._selected=selected;
	},

	fa_isElementChecked: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_isElementChecked: Invalid element parameter ! ("+li+")");
		
		return li._node._checked;
	},
	
	fa_setElementChecked: function(li, checked) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_setElementChecked: Invalid element parameter ! ("+li+")");
		
		li._node._checked=checked;
	},
	fa_getScrolledComponent: function() {
		return this;
	},
	fa_getScrolledHorizontalTitle: function() {
		return null;
	},
	fa_getScrolledVerticalTitle: function() {
		return null;
	},
	/**
	 * @method public
	 * @param Object nodeOrValue
	 * @return String
	 */
	f_getItemStyleClass: function(nodeOrValue) {
		var li=this._searchComponentByNodeOrValue(value);

		return li._node._styleClass;
	},
	/**
	 * @method public
	 * @param Object nodeOrValue
	 * @param String styleClass
	 * @return void
	 */
	f_setItemStyleClass: function(nodeOrValue, styleClass) {
		var li=this._searchComponentByNodeOrValue(nodeOrValue);

		if (li._node._styleClass==styleClass) {
			return;
		}
		
		li._node._styleClass=styleClass;

		if (!this.fa_componentUpdated) {
			return;
		}

		this.fa_updateElementStyle(li);
	}	
}

new f_class("f_tree", null, __static, __prototype, f_component, fa_readOnly, fa_disabled, fa_immediate, fa_subMenu, fa_selectionManager, fa_checkManager, fa_itemClientDatas, fa_scrollPositions);
