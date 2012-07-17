/*
 * $Id$
 */

/**
 * f_tree
 *
 * @class f_tree extends f_component, fa_readOnly, fa_disabled, fa_immediate, fa_subMenu, fa_selectionManager<String[]>, fa_checkManager, fa_itemClientDatas, fa_scrollPositions, fa_overStyleClass, fa_filterProperties, fa_droppable, fa_draggable, fa_autoScroll, fa_autoOpen, fa_tabIndex
 * @author olivier Oeuillot
 * @version $REVISION: $
 */
 
var __statics = {
	/**
	 * @field private static final Number
	 */
	_DND_AUTO_OPEN_NODE_MS: 500,
	
	/**
	 * @field private static final String
	 */
	_NODE_MENU_ID: "#node",
	
	/**
	 * @field private static final String
	 */
	_BODY_MENU_ID: "#body",

	/**
	 * @field private static final Number
	 */
	_SEARCH_KEY_DELAY: 400,

	/**
	 * @field private static final Number
	 */
	_COMMAND_IMAGE_WIDTH: 16,
	
	/**
	 * @field private static final Number
	 */
	_COMMAND_IMAGE_HEIGHT: 16,

	/**
	 * @method private static
	 * @param Event evt 
	 * @return Boolean
	 * @context object:tree
	 */
	_NodeLabel_mouseOver: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false) || li._labelOver) {
			// On bloque pas !!!
			return true;
		}
			
		li._labelOver=true;
		
		tree.fa_updateElementStyle(li);
		
		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_NodeLabel_mouseOut: function(evt) {
		var li=this._node;
		var tree=li._tree;

		if (!li._labelOver) {
			return true;
		}
		
		li._labelOver=undefined;
		
		tree.fa_updateElementStyle(li);
		
		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_DivNode_mouseOver: function(evt) {
		var li=this._node;
		var tree=li._tree;

		f_core.Assert(tree && tree.tagName, "f_tree._DivNode_mouseOver: Invalid tree this=("+this.id+"/"+this.tagname+") li=("+li.id+"/"+li.tagName+")");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		if (!tree || tree.f_getEventLocked(evt, false) || li._over) {
			// On bloque pas !
			return true;
		}
			
		li._over=true;
		
		tree.fa_updateElementStyle(li);
		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_DivNode_mouseOut: function(evt) {
		var li=this._node;
		var tree=li._tree;

		f_core.Assert(tree && tree.tagName, "f_tree._DivNode_mouseOut: Invalid tree this=("+this.id+"/"+this.tagname+") li=("+li.id+"/"+li.tagName+")");

		if (!tree || !li._over) {
			return true;
		}
		
		li._over=undefined;
		
		tree.fa_updateElementStyle(li);
		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
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
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
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

		var selection=fa_selectionManager.ComputeMouseSelection(evt);
		
		tree.f_moveCursor(li, true, evt, selection, fa_selectionManager.BEGIN_PHASE);
					
		if (f_core.IsPopupButton(evt) && !tree.fa_isElementDisabled(li)) {		
			var menu=tree.f_getSubMenuById(f_tree._NODE_MENU_ID);
			if (menu) {
				if(menu.f_closeAllpopups) {
					menu.f_closeAllpopups();
				}
			}
			
		} else if (tree._dragAndDropEngine){
			tree._dragNode(evt);
		}

		return f_core.CancelJsEvent(evt);
	},
	
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_DivNode_mouseUp: function(evt) {
		var li=this._node;
		var tree=li._tree;
		
		if(tree._cursor && tree._cursor != li){
			li=tree._cursor;
		}
		
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
		
		tree.f_moveCursor(li, true, evt, selection, fa_selectionManager.END_PHASE);
		
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
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
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
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
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
		
		if(tree._cursor){
			var oldCursor = tree._cursor;
			tree._cursor = li;
			tree.fa_updateElementStyle(oldCursor); 
		} else {
			tree._cursor = li;
		}

		if (!tree._focus) {
			tree.f_setFocus();
		}

		if (tree._userExpandable) {
			if (node._opened) {
				tree._userCloseNode(node, evt, li);
	
			} else {
				//
				tree._userOpenNode(node, evt, li);
			}
		}
		 
		return f_core.CancelJsEvent(evt);
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_Link_bodyOnfocus: function(evt) {
		f_core.Debug(f_tree, "_Link_bodyOnfocus: focus body ");
		
		var tree=this._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}

		if (tree.f_getEventLocked(evt, false)) {
			return false;
		}
		
		tree.f_setFocus();
		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_Link_onfocus: function(evt) {
		var tree=this._tree;
		if (!tree && this._node) {
			tree=this._node._tree;
		}

		f_core.Debug(f_tree, "_Link_onfocus: on focus ");
		
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
		
		if (tree.f_isSelectable()) {
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
		
		if (tree._cursor) {
			if (!tree._lastFocusDesactivate || new Date().getTime()-tree._lastFocusDesactivate>300) {
				tree.fa_showElement(tree._cursor, true);
			}
		}

		tree.f_fireEvent(f_event.FOCUS, evt);

		return true;		
	},
	/**
	 * @method private static
	 * @param Event evt
	 * @return Boolean 
	 * @context object:tree
	 */
	_Link_onblur: function(evt) {
		var tree=this._tree;
		if (!tree && this._node) {
			tree=this._node._tree;
		}
		
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
	
		if (tree.f_isSelectable()) {
			tree._updateSelectedNodes();
		}

		if (tree._cfocus) {
			tree._cfocus.style.top=tree.scrollTop+"px";
		}

		tree.f_fireEvent(f_event.BLUR, evt);

		return true;
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
	 */
	_Link_onkeydown: function(evt) {
		var tree=this._tree;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
				
		var showAlert=!f_key.IsModifierKey(evt.keyCode);

		if (tree.f_getEventLocked(evt, showAlert)) {
			return false;
		}
		
		if (!tree._focus) {
			return true;
		}

		return tree.f_fireEvent(f_event.KEYDOWN, evt);
	},
	/**
	 * @method private static 
	 * @param Event evt
	 * @return Boolean
	 * @context object:tree
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

		return tree.f_fireEvent(f_event.KEYUP, evt);
	},
	/**
	 * @method private static 
	 * @context object:tree
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

		return tree.f_fireEvent(f_event.KEYPRESS, evt);
	},
	/**
	 * @method private static 
	 * @context object:tree
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
};

var __members = {
	
	f_tree: function() {
		this.f_super(arguments);
		
		this._filtred = true;
		
		this._nodesList=new Array();

		this._interactive=f_core.GetBooleanAttributeNS(this,"asyncRender", false);

		this._hideRootExpandSign=f_core.GetBooleanAttributeNS(this,"hideRootExpandSign", false);
				
		this._userExpandable=f_core.GetBooleanAttributeNS(this,"userExpandable", true);
		
		this._images=f_core.GetBooleanAttributeNS(this,"images");
		
		this._preloadedLevelDepth=f_core.GetNumberAttributeNS(this,"preloadedLevelDepth");
		
		this._initCursorValue=f_core.GetAttributeNS(this,"cursorValue");

		this._showValue=f_core.GetAttributeNS(this,"showValue");
		
		this._blankNodeImageURL=f_env.GetBlankImageURL();
		
		if(this.f_isDroppable()) {
			this.f_addEventListener(f_event.DRAG, this._treeDropListener);
		}
		
		this._body=this;
		if (this.tagName.toUpperCase()!="UL") {
			var container=this.ownerDocument.getElementById(this.id+"::body");
			if (container) {
				this._body=container;
			}
		}

		// Gestion du focus lors du click dans le TREE !
		this.onfocus=f_tree._Link_bodyOnfocus;
		this.onblur=f_tree._Link_onblur;
		this._tree=this;

		// =2 la marque contient le focus   =1 c'est le label qui devient un lien   =0 ancienne méthode
		this._treeNodeFocusEnabled=(this.f_isCheckable())?2:1;

		
		if (this._treeNodeFocusEnabled) {
			this.onkeydown=f_tree._Link_onkeydown;
			this.onkeypress=f_tree._Link_onkeypress;
			this.onkeyup=f_tree._Link_onkeyup;
			
		} else {
			var focus=this.ownerDocument.getElementById(this.id+"::focus");
			this._cfocus=focus;

			focus.onfocus=f_tree._Link_onfocus;
			focus.onblur=f_tree._Link_onblur;
			focus.onkeydown=f_tree._Link_onkeydown;
			focus.onkeypress=f_tree._Link_onkeypress;
			focus.onkeyup=f_tree._Link_onkeyup;
			focus.href=f_core.CreateJavaScriptVoid0();
			focus._tree=this;

			if (f_core.IsInternetExplorer()) {
				this.hideFocus=true;
				
				var self=this;
				
				focus.onbeforeactivate=function() {
	
					var evt = f_core.GetJsEvent(this);
					
					evt.cancelBubble=true;
				};
	
				focus.onbeforedeactivate=function() {
	
					self._lastFocusDesactivate=new Date().getTime();
	
					var evt = f_core.GetJsEvent(this);
					
					var toElement=evt.toElement;
					if (toElement==self || !toElement) {
						// Necessaire pour la scrollBar !
						return true;
					}
				
					for(;toElement.parentNode;toElement=toElement.parentNode) {
						if (toElement!=self) {
							continue;
						}
						
						return f_core.CancelJsEvent(evt);
					}
					
					return true;
				};
			}
		}
		
		this.onmousedown=f_tree._BodyMouseDown;
		this.onmouseup=f_core.CancelJsEventHandler;
		this.onclick=f_core.CancelJsEventHandler;
		
		this.f_insertEventListenerFirst(f_event.KEYDOWN, this._performKeyDown);
		
		if (this.f_isDraggable()) {
			this._dragAndDropEngine= f_dragAndDropEngine.Create(this);
		}
	
		if (this.f_isDroppable()) {
			this._bodyDroppable=f_core.GetBooleanAttributeNS(this,"bodyDroppable", false);
		}
	},
	f_finalize: function() {
//		this._showValue=undefined; // String
//		this._preloadedLevelDepth=undefined;  // number
//		this._userExpandable=undefined; // boolean
//		this._images=undefined;  // boolean 
		this._tree=undefined;
//		this._hideRootExpandSign=undefined; // boolean
		this._body=undefined; // HTMLElement
		
		this._cursor=undefined; // HtmlLIElement
		this._breadCrumbsCursor=undefined; // HtmlLIElement

		// this._treeNodeFocusEnabled=undefined; // Boolean
		this._treeNodeFocus=undefined; // HtmlElement

//		this._lastKeyDate=undefined; // number
//		this._lastKey=undefined; // char

//		this._focus=undefined;   // boolean
		
		this._dragAndDropEngine=undefined;
		this._targetDragAndDropEngine=undefined;
		this._lastRemovedTitleElement=undefined;
		
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
			cfocus.onbeforeactivate=null;
			cfocus.onbeforedeactivate=null;
			
			cfocus._tree=undefined;
			
			if (cfocus!=this) {
				f_core.VerifyProperties(cfocus);
			}
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
//		this._defaultCollapsedImageURL=undefined; // string
//		this._defaultSelectedImageURL=undefined; // string
//		this._defaultDisabledImageURL=undefined; // string
		
//		this._defaultLeafImageURL=undefined; // string
//		this._defaultExpandedLeafImageURL=undefined; // string
//		this._defaultSelectedLeafImageURL=undefined; // string
//		this._defaultDisabledLeafImageURL=undefined; // string
		
		// this._commandImages=undefined; // Map<String, String>

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
	_nodeFinalizer: function(li, deepFinalizer, deselectedNodeValues) {
	
		if (deepFinalizer) {
			this._nodesList.f_removeElement(li);
		
			var ul=li._nodes;
			if (ul) {
				var children=ul.childNodes;				
				for(var i=0;i<children;i++) {
					var child=children[i];
					
					this._nodeFinalizer(child, true, deselectedNodeValues);
				}
			}
		}

		if(this._currentSelection) { //que sur le refresh
			 value = li._node._value;
			if(this._deselectElement(li,value)){
				deselectedNodeValues.push(value);
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
			input.onfocus=null;
			input.onblur=null;
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
			label.onfocus=null;
			label.onblur=null;

			f_core.VerifyProperties(label);			
		}
		li._focusComponent=undefined;

		f_core.VerifyProperties(li);
	},
	f_update: function() {
		this.f_updateScrollPosition();

		var nodes=this._nodes;
		if (nodes) {
			this._constructTree(this._body, nodes, 0);
			this.f_updateBreadCrumbs();		
			
			this._updateBodyWidth();
		}
		
		this.f_super(arguments);		
		
		/*
		if (!this.f_isVisible()) {
			this.f_getClass().f_getClassLoader().f_addVisibleComponentListener(this);
		}	
		*/	
	},	
	/**
	 * @method private
	 * @return void
	 */
	_updateBodyWidth: function() {
		
		if (!this.style.width) {
			return;
		}
		
		var self=this;
		window.setTimeout(function() {
			var tree=self;
			self=null;
			
			if (!tree._body) {
				return;
			}
			
			//alert("Scroll="+this.scrollWidth);
			var width=tree.offsetWidth;
			width-=f_core.ComputeContentBoxBorderLength(tree, "left", "right");
			
			tree._body.style.width=width+"px";
		}, 50);
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateScrollPosition: function() {
		var showValue=this._showValue;
		if (showValue) {
			this.f_showNode(showValue);			
			return;
		}
		
		this.fa_initializeScrollBars();
	},
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
		case f_event.EXPAND:	
			return;
		}

		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION:
		case f_event.DBLCLICK:
		case f_event.KEYDOWN:
		case f_event.KEYPRESS:
		case f_event.KEYUP:
		case f_event.EXPAND:
			return;
		}

		this.f_super(arguments, type, target);
	},
	/**
	 * @method protected
	 * @return void
	 */
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
			// ???? this.f_setFirst(this._first, this._currentCursor);			
		}
		
		this.f_updateScrollPosition();		
	},
	/**
	 * @method private
	 * @return void
	 */
	_constructTree: function(container, nodes, depth, domFragment) {
		
		var doc=this.ownerDocument;
		
		var fragment=domFragment;
		if (!fragment) {
			fragment = doc.createDocumentFragment();
		}
		
		if (!this._nodeIdx) {
			this._nodeIdx=1;
		}
		
		for(var i=0;i<nodes.length;i++) {
			var node=nodes[i];
			node._depth=depth;
			
			var li=doc.createElement("li");
			f_core.AppendChild(fragment, li); // Evite les fuites memoires

			li._node=node;
			li._depth=depth;
			li._tree=this;
			li._className="f_tree_parent";
			li.setAttribute("role", "presentation");
			li.className=li._className;
			
			this._nodesList.push(li);
			
			// On prefere la performance aux fuites mémoires ! on le met à la fin
			// f_core.AppendChild(container, li); // Evite les fuites memoires

			var nodeIdx=this._nodeIdx++;

			var divNode=f_core.CreateElement(li, "div", {
				id: this.id+"::node"+nodeIdx,
				className: "f_tree_depth"+depth,
				_node: li,
				onmouseover: f_tree._DivNode_mouseOver,
				onmouseout: f_tree._DivNode_mouseOut,
				onmousedown: f_tree._DivNode_mouseDown,
				onmouseup: f_tree._DivNode_mouseUp,
				onclick: f_core.CancelJsEventHandler,
				ondblclick: f_tree._DivNode_dblClick
			});
			
			if (this._treeNodeFocusEnabled==0) {
				divNode.setAttribute("role", "treeitem");
			} else {
				divNode.setAttribute("role", "presentation");				
			}

			
			li._divNode=divNode;
			
			var focusComponent=divNode;
			
			var d=depth;
			if (this._userExpandable) {
				if (depth>0 || !this._hideRootExpandSign) {
					
					li._command=f_core.CreateElement(divNode, "img", {
						align: "center",
						width: f_tree._COMMAND_IMAGE_WIDTH,
						height: f_tree._COMMAND_IMAGE_HEIGHT,
						src: this._blankNodeImageURL,
						_node: li,
						onmousedown: f_tree._Command_mouseDown,
						onmouseup: f_core.CancelJsEventHandler,
						onclick: f_core.CancelJsEventHandler
					});
					
					this._updateCommandStyle(li);
					
				}
				if (depth==1 && this._hideRootExpandSign) {
					d=0;
				}
			}
			
			divNode.style.paddingLeft=(d*f_tree._COMMAND_IMAGE_WIDTH)+"px";
			
			if (this.f_isCheckable()) {
				var input=doc.createElement("input");
				li._input=input;
				input._node=li;

				input.id=this.id+"::input"+nodeIdx;
				input.className="f_tree_check";				
				input.onclick=f_tree._NodeInput_mouseClick;
		
				if (this._checkCardinality==fa_cardinality.ONE_CARDINALITY) {
					input.type="radio";
					input.value="CHECKED_"+nodeIdx;
					input.name=this.id+"::radio";
					
				} else {
					input.type="checkbox";
					input.value="CHECKED";
					input.name=input.id;
				}
				
				
				if (this._treeNodeFocusEnabled==2) {
					input.onfocus=f_tree._Link_onfocus;
					input.onblur=f_tree._Link_onblur;
					input.tabIndex=-1;
					input.setAttribute("role", "treeitem");


					if (!this._treeNodeFocus) {
						this._treeNodeFocus=input;
						input.tabIndex=this.fa_getTabIndex();
					}
				}
				
				focusComponent=input;

				f_core.AppendChild(divNode, input);
			}

			var span=f_core.CreateElement(divNode, "div", {
				_node: li,
				role: "presentation",
				onmouseover: f_tree._NodeLabel_mouseOver,
				onmouseout: f_tree._NodeLabel_mouseOut				
			});
			li._span=span;			
			
			if (this._images) {
				li._image=f_core.CreateElement(span, "img", {
					align: "center",
					className: "f_tree_image",
					_node: li
				});
			}
			
			var linkType=(this._treeNodeFocusEnabled==1)?"a":"label";
			var label=f_core.CreateElement(span, linkType, {
				className: "f_tree_label",
				id: this.id+"::node"+nodeIdx+"::label",
				textNode: (node._label)?node._label:null,
				tabIndex: -1,
				href: f_core.CreateJavaScriptVoid0(),
				_node: li
			});
			li._label=label;
			
			if (this._treeNodeFocusEnabled==1) {				
				label.onfocus=f_tree._Link_onfocus;
				label.onblur=f_tree._Link_onblur;
				label.setAttribute("role", "treeitem");

				focusComponent=label;
				
				if (!this._treeNodeFocus) {
					this._treeNodeFocus=label;
					label.tabIndex=this.fa_getTabIndex();
				}
				
			} else {
				fa_aria.SetElementAriaLabelledBy(focusComponent, label.id);
			}	

			li._focusComponent=focusComponent;
			fa_aria.SetElementAriaSetsize(focusComponent, nodes.length);
			fa_aria.SetElementAriaPosinset(focusComponent, i+1);
//			fa_aria.SetElementAriaLevel(focusComponent, depth +1); // Pas nécessaire
	
			if (this.f_isSelectable()) {
				this.f_updateElementSelection(li, li._node._selected);
			}
			if (this.f_isCheckable()) {	
				this.fa_updateElementCheck(li, li._node._checked);
			}
				
			var initCursorValue=this._initCursorValue;
			if (!this._cursor && node._value==initCursorValue) {
				this._cursor=li;
				this._initCursorValue=undefined;
			}			
			
			this.fa_updateElementStyle(li, true);
			
			if (node._container) {
				// On peut etre un container sans posseder (encore) d'enfants.
				
				var ul=doc.createElement("ul");
				ul.setAttribute("role", "group");
				ul.style.display="none";
				ul.className="f_tree_parent";

				f_core.AppendChild(li, ul);
				
				li._nodes=ul;
			}
			
			if (node._nodes) {
				// f_core.Debug(f_tree, "constructTree: children: opened="+node._opened+" userExp="+this._userExpandable+" depth="+depth);
				
				if (node._opened || !this._userExpandable) {
					this._constructTree(li._nodes, node._nodes, depth+1, li._nodes);
					
					li._nodes.style.display="list-item";
				}
			}
		}
		
		// Si c'est la racine, on retire les erreurs
		var waitingNodes=this._waitingNodes;
		if (container==this._body && waitingNodes) {
			for ( var j = 0; j < waitingNodes.length; j++) {
				var waintingNode = waitingNodes[j];
				
				if (waintingNode._li) {
					this.f_clearWaiting(waitingNode._id);
				}
			}
		}

		if (!domFragment) {
			container.appendChild(fragment);
		}
		
		if (this._cursor) {
 			this.fa_showElement(this._cursor, true); 
		}
	},
	/**
	 * Close a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @param optional hidden Event evt Javascript event
	 * @return Boolean <code>true</code> if success.
	 */
	f_closeNode: function(value, evt) {
		var li=this._searchComponentByNodeOrValue(value);
		
		if (li === undefined) {
			return false;
		}	
		
		return this._closeNode(li._node, evt, li);
	},
	
	/**
	 * @method protected
	 * @param Object node 
	 * @param optional Event evt Javascript event.
	 * @param hidden Object li
	 * @return Boolean <code>true</code> if success ...
	 */
	_userCloseNode: function(node, evt, li) {
		var item = li;
		var itemValue = (item==this)?undefined:this.fa_getElementValue(item);
		if (this.f_fireEvent(f_event.EXPAND, evt, item, itemValue, this, 0)===false){
			return false;
		}
		return this._closeNode(node, evt, li);
	},
	
	/**
	 * @method protected
	 * @param Object node TreeNode
	 * @param optional Event evt Javascript event
	 * @param hidden Object li 
	 * @return Boolean <code>true</code> if success.
	 */
	_closeNode: function(node, evt, li) {
		if (!node._opened || !node._container) {
			return false;
		}
		node._opened=false;
		li._divNode.removeAttribute(fa_aria.AriaExpanded);
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
	 * @return Boolean <code>true</code> if success.
	 */
	f_openNode: function(value, evt) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this._openNode(li._node, evt, li);
	},
	
	

	/**
	 * @method protected
	 * @param Object node 
	 * @param optional Event evt Javascript event.
	 * @param hidden Object li
	 * @return Boolean <code>true</code> if success ...
	 */
	_userOpenNode: function(node, evt, li) {
		var item = li;
		var itemValue = (item==this)?undefined:this.fa_getElementValue(item);
		if (this.f_fireEvent(f_event.EXPAND, evt, item, itemValue, this, 1)===false){
			return false;
		}
		return this._openNode(node, evt, li);
	},
	/**
	 * @method protected
	 * @param Object node 
	 * @param optional Event evt Javascript event.
	 * @param hidden Object li
	 * @return Boolean <code>true</code> if success ...
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
				ul=this.ownerDocument.createElement("ul");
				ul.className="f_tree_parent";
				ul.setAttribute("role", "treegroup");
			
				f_core.AppendChild(li, ul);
			
				li._nodes=ul;
			} else {
				ul.style.display="list-item";
			}
							
			var waitingNode=this._newWaitingNode(li._depth, ul);
	
			if (!this._waitingNodes) {
				this._waitingNodes=new Array;
			}
			waitingNode._id=this._waitingNodes.length;
			waitingNode._li=li;

			this._waitingNodes.push(waitingNode);
		
			this._callServer(waitingNode);
		
			return true;
		}
		
		if (!ul.hasChildNodes()) {
			// Il faut créer les composants ...
			
			this._constructTree(ul, node._nodes, li._depth+1);
			
			this._updateBodyWidth();
		}		
		
		ul.style.display="list-item";

		this.fa_updateElementStyle(li);
		this._updateCommandStyle(li);
		
		return true;
	},
	/**
	 * @method private
	 * @return void
	 */
	_reloadTree: function() {
		var ul=this;
	
		var waitingNode=this._newWaitingNode(0, ul);

		if (!this._waitingNodes) {
			this._waitingNodes=new Array;
		}
		waitingNode._id=this._waitingNodes.length;
		waitingNode._li=this;

		this._waitingNodes.push(waitingNode);
	
		this._callServer(waitingNode);
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
				tree._showError(waitingNode);
				
				f_core.Info(f_tree, "_callServer.onError: Bad status: "+status);
				
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
					tree._showError(waitingNode);
					tree.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
					return;
				}

				var responseContentType=request.f_getResponseContentType().toLowerCase();
				if (responseContentType.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE)>=0) {
					var code=f_error.ComputeApplicationErrorCode(request);
				
					tree._showError(waitingNode);
			 		tree.f_performErrorEvent(request, code, content);
					return;
				}
				
				if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
					tree._showError(waitingNode);
		 			tree.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);

					return;
				}
				
				var item = waitingNode._li;
				var itemValue = (item==tree)?undefined:tree.fa_getElementValue(item);
				
	 			var ret=request.f_getResponse();
				try {
					//alert("ret="+ret);
					f_core.WindowScopeEval(ret);

				} catch(x) {				
					tree._showError(waitingNode);
				 	tree.f_performErrorEvent(x, f_error.RESPONSE_EVALUATION_SERVICE_ERROR, "Evaluation exception");
				}
	
				var event=new f_event(tree, f_event.LOAD, null, item, itemValue, tree);
				try {
					tree.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
	 		}			
		});

//		alert("Params="+params);

		request.f_setRequestHeader("X-Camelia", "tree.request");
		
		var requestParams = {
			treeId: this.id,
			waitingId: waitingNode._id,
			node: params 
		};
		
		var filterExpression=this.fa_getSerializedPropertiesExpression();
		if (filterExpression) {
			requestParams.filterExpression=filterExpression;
		}

		request.f_doFormRequest(requestParams);
		
	},
	
	/**
	 * @method private
	 */
	_showError: function(waitingNode) {
		var label=waitingNode._label;
		if (label) {
			label.innerHTML="ERREUR !";
			label.className="f_waiting_error";
		}
		
		var image=waitingNode._image;
		if (image) {
			image.src=f_waiting.GetWaitingErrorImageURL();
		}
	},
	
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * @method private
	 * @param Number parentDepth
	 * @param HTMLElement container
	 * @return void
	 */
	_newWaitingNode: function(parentDepth, container) {
		var doc=this.ownerDocument;
		
		var fragment= doc.createDocumentFragment();
				
		var li=doc.createElement("li");
		li.className="f_tree_parent";
		li.setAttribute("role", "presentation");
		f_core.AppendChild(fragment, li);

		
		var divNode=doc.createElement("div");
		f_core.AppendChild(li, divNode);
		divNode.className="f_tree_depth"+(parentDepth+1)+" f_tree_waiting";
		divNode.style.paddingLeft=(parentDepth*f_tree._COMMAND_IMAGE_WIDTH)+"px";
		divNode.setAttribute("role", "presentation");
		
		var command=doc.createElement("img");
		f_core.AppendChild(divNode, command);

		command.align="center";
		command.width=f_tree._COMMAND_IMAGE_WIDTH;
		command.height=f_tree._COMMAND_IMAGE_HEIGHT;
		command.src=this._blankNodeImageURL;

		var span=doc.createElement("span");
		f_core.AppendChild(divNode, span);
		
		var image=doc.createElement("img");
		f_core.AppendChild(span, image);

		image.align="center";
		image.width=f_waiting.WAIT_IMAGE_WIDTH;
		image.height=f_waiting.WAIT_IMAGE_HEIGHT;
		image.src=f_waiting.GetWaitingImageURL();
		image.className="f_tree_image";
		li._image=image;
			
		var label=doc.createElement("label");
		f_core.AppendChild(span, label);
		li._label=label;

		label.className="f_tree_label";

		var txt=f_waiting.GetLoadingMessage();
		f_core.AppendChild(label, doc.createTextNode(txt));

		f_core.AppendChild(container, fragment);
		
		return li;
	},
	/**
	 * Show a node.
	 * 
	 * @method public
	 * @param any value Value of the node, or the node object
	 * @return Boolean <code>true</code> if the node was found.
	 */
	f_showNode: function(value) {
		var item=this._searchComponentByNodeOrValue(value);
		if (!item) {
			return false;
		}
		
		this.fa_showElement(item);
		
		return true;
	},
	fa_showElement: function(item, giveFocus) {
		f_core.Assert(item && item.tagName, "f_tree.fa_showElement: Item parameter must be a LI tag ! ("+item+")");
		
		
		if (giveFocus && this._treeNodeFocusEnabled) {
			
			var cfocus=this._treeNodeFocus;
			if (cfocus) {				
				if (cfocus==item._focusComponent) {
					return;
				}
					
				cfocus.tabIndex=-1;
			}
			
			cfocus=item._focusComponent;
			this._treeNodeFocus=cfocus;
			cfocus.tabIndex=this.fa_getTabIndex();
	
			f_core.Debug(f_tree, "fa_showElement: give focus to "+cfocus);

			f_core.SetFocus(cfocus, true);
				
			return;
		}
		
		if (item.offsetTop-this.scrollTop<0) {
			this.scrollTop=item.offsetTop;

		} else if (item.offsetTop+item._label.offsetHeight-this.scrollTop>this.clientHeight) {			
			var itemHeight = item.offsetHeight; 
			if (itemHeight == 0){ // possible sur certain arbre
				if (item.nextSibling) {
					itemHeight = (item.nextSibling.offsetTop - item.offsetTop)*2 ;
				} else if (item.parentNode){
					var parent = item.parentNode;
					while (parent.offsetTop != 0){
						parent = parent.parentNode;
					}
					itemHeight = parent.offsetTop+parent.offsetHeight;
				}
			}
			this.scrollTop=item.offsetTop+itemHeight-this.clientHeight;
		}
		
		var itemNode=item.firstChild; // Div du noeud
		var firstChild=itemNode.firstChild;
		var lastChild=itemNode.lastChild;
		
		if (firstChild.offsetLeft-this.scrollLeft<0) {
			this.scrollLeft=firstChild.offsetLeft;

		} else if (lastChild.offsetLeft+lastChild.offsetWidth-this.scrollLeft>this.clientWidth) {			
			this.scrollLeft=firstChild.offsetLeft;
		}		
		
		f_core.ShowComponent(item._span);
	},
	fa_updateElementStyle: function(li, constructMode) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li", "f_tree.fa_updateElementStyle: Invalid LI parameter ("+li+")");
		
		var node=li._node;
		if (!node) {
			return;
		}
	
		var suffixLabel="";
		var suffixDivNode="";
		var cursor=this._cursor;
	
		if (this._cfocus && !cursor) {
			fa_aria.SetElementAriaActiveDescendant(this._cfocus, null);
		}
		
		if (this._treeNodeFocusEnabled && !node._selected) {
			fa_aria.SetElementAriaSelected(li._focusComponent, undefined);
		}
	
		if (node._disabled) {
			if (!node._container) {
				suffixDivNode+="_leaf";
			}	
			
			suffixLabel+="_disabled";
			suffixDivNode+="_disabled";
			
			if (this._cfocus && li==cursor){
				fa_aria.SetElementAriaActiveDescendant(this._cfocus, li._divNode.id);
			}
			
		} else {
			if (node._opened) {
				suffixDivNode+="_opened";
				
				fa_aria.SetElementAriaExpanded(li._focusComponent, true);
				
			} else if (node._container && !node._opened) {
				fa_aria.SetElementAriaExpanded(li._focusComponent, false);
				
			} else if (!node._container) {
				suffixDivNode+="_leaf";
			}	
		
			if (li._dndOver) {
				suffixDivNode+="_dndOver";
				
			} else if (li._over) {
				suffixDivNode+="_hover";
			}

			if (node._selected) {
				suffixLabel+="_selected";
				
				if (this._focus) {
					suffixLabel+="_focus";
					
					if (this._cfocus) {
						fa_aria.SetElementAriaActiveDescendant(this._cfocus, li._divNode.id);
					}
				}
				
				if (this._treeNodeFocusEnabled) {
					fa_aria.SetElementAriaSelected(li._focusComponent, true);
				}
		
			} else if (this._focus && li==cursor) {
				suffixLabel+="_focus";
				
				if (this._cfocus) {
					fa_aria.SetElementAriaActiveDescendant(this._cfocus, li._divNode.id);
				}
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

			divNodeClassName+=" f_tree_depth"+li._depth+suffixLabel;			
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
		if (cursor==li && this._focus) {
			labelClassName+=" "+labelClassName+"_cursor";
		}
		
		if (!constructMode && this._breadCrumbsCursor!=cursor) {
			this.f_updateBreadCrumbs();						
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
		
		var command=li._command;
		if (command && this._hasCommandImages(li, command, node)) {
			this._updateCommandImage(li, command, node, {
				disabled: node._disabled,
				container: !!node._container,
				root: node._parentTreeNode==this,
				leaf: !node._container,
				cursor:  (this._focus && li==cursor),
				opened: node._opened,
				closed: (node.container && !node._opened),
				selected: node._selected,
				dndOver: li._dndOver,
				labelOver: li._labelOver,
				over: li._over,
				depth: li._depth
			});
		}
	},
	/**
	 * @method protected
	 * @param HTMLElement li Graphical component
	 * @param HTMLImageElement command Command component
	 * @param Object node Logical treenode
	 * @return Boolean
	 */
	_hasCommandImages: function(li, command, node) {
		if (this._hasCommandImage!==undefined) {
			return this._hasCommandImage;
		}
		
		var url=f_core.GetAttributeNS(this, "cmdNode");
		if (!url) {
			this._hasCommandImage=false;
			return false;
		}
		var images=new Object();
		this._commandImages=images;
		
		images._cmdNodeImageURL=url;
		
		images._cmdNodeOpenedImageURL=f_core.GetAttributeNS(this, "cmdNodeOpened");
		images._cmdNodeDisabledImageURL=f_core.GetAttributeNS(this, "cmdNodeDisabled");

		images._cmdRootImageURL=f_core.GetAttributeNS(this, "cmdRoot");
		images._cmdRootOpenedImageURL=f_core.GetAttributeNS(this, "cmdRootOpened");
		images._cmdRootDisabledImageURL=f_core.GetAttributeNS(this, "cmdRootDisabled");

		images._cmdLeafImageURL=f_core.GetAttributeNS(this, "cmdLeaf");
		images._cmdLeafDisabledImageURL=f_core.GetAttributeNS(this, "cmdLeafDisabled");

		this._hasCommandImage=true;
		return true;
	},
	/**
	 * @method hidden
	 * @param Object properties
	 * @return void
	 */
	_setCommandImagesURL: function(images) {
		this._hasCommandImage=true;
		this._commandImages=images;
	},
	/**
	 * @method protected
	 * @param HTMLElement li Graphical component
	 * @param HTMLImageElement command Command component
	 * @param Object node Logical treenode
	 */
	_updateCommandImage: function(li, command, node, properties) {
		if (!this._hasCommandImage) {
			return;
		}
		
		var images=this._commandImages;
		
		var url=images._cmdNodeImageURL;
		
		if (properties.opened && images._cmdNodeOpenedImageURL) {
			url=images._cmdNodeOpenedImageURL;
		}
		if (properties.disabled && images._cmdNodeDisabledImageURL) {
			url=images._cmdNodeDisabledImageURL;
		}
		
		if (properties.leaf) {
			if (images._cmdLeafImageURL) {
				url=images._cmdLeafImageURL;
			}
			if (properties.disabled && images._cmdLeafDisabledImageURL) {
				url=images._cmdLeafDisabledImageURL;
			}	
		}
		if (properties.root) {
			if (images._cmdRootImageURL) {
				url=images._cmdRootImageURL;
			}
			if (properties.opened && images._cmdRootOpenedImageURL) {
				url=images._cmdRootOpenedImageURL;
			}
			if (properties.disabled && images._cmdRootDisabledImageURL) {
				url=images._cmdRootDisabledImageURL;
			}
		}
		
		if (command.src!=url) {
			command.src=url;
		}
	},
	/**
	 * @method protected
	 * @param HTMLElement li Graphical component
	 * @return void
	 */
	_updateCommandStyle: function(li) {
		var command=li._command;
		if (!command) {
			return;
		}

		var node=li._node;		
		
		var suffix="";
		var alt_title="";
		if (node._container) {
			if (!node._opened) {
				suffix+="_opened";
				alt_title=f_resourceBundle.Get(f_tree).f_get("OPEN_NODE");
				
			} else {
				suffix+="_closed";
				alt_title=f_resourceBundle.Get(f_tree).f_get("CLOSE_NODE");
			}
		} else {
			suffix+="_leaf";
		}
		
		if (alt_title && li._node._label) {
			alt_title+=": "+li._node._label;
		}
		//command.title = alt_title; // OO: C'est une image, il faut positionner le ALT
		command.alt = alt_title; 
		
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
	/**
	 * @method private
	 * @param Object node
	 * @return String
	 */
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
			
			if (this._defaultLeafImageURL) {	
				return this._defaultLeafImageURL;
			}
		}

		if (node._opened) {
			imageURL=this._defaultExpandedImageURL;
			if (imageURL) { 
				return imageURL;
			}
		} else {
			imageURL=this._defaultCollapsedImageURL;
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
	 * @param Object nodeValue node value
	 * @param Object nodeInfo refreshed node Object 
	 * @return void
	 */
	f_refreshNode: function(nodeValue, nodeInfo) {
		var li=this._searchComponentByNodeOrValue(nodeValue);
		if (!li) {
			return;
		}
		var node=li._node;
		if (!node) {
			return;
		}
		if (nodeInfo._description) {
			node._tooltip=nodeInfo._description;
		}
		if (nodeInfo._expanded !== undefined) {
			node._opened=nodeInfo._expanded;
		}
		this._setImages(nodeInfo, node);
		delete nodeInfo._imageURL;
		delete nodeInfo._disabledImageURL;
		delete nodeInfo._selectedImageURL;
		delete nodeInfo._expandedImageURL;

		var clientDatas=nodeInfo._clientDatas;
		if (clientDatas) {
			this.f_setItemClientDatas(node, clientDatas);
			delete nodeInfo._clientDatas;
		}
		
		if (nodeInfo._hasChild !== undefined) {
			node._container = nodeInfo._hasChild;
			this._updateCommandStyle(li);
			delete nodeInfo._hasChild;
		}
		
		for (var attr in nodeInfo) {
			node[attr] = nodeInfo[attr];
		}

	},
	/** 
	 * @method private
	 * @param Object srcObj  object
	 * @param Object node  node
	 * @return void
	 */
	_setImages: function(imagesInfo, node) {
		var imageURL=imagesInfo._imageURL;
		var disabledImageURL=imagesInfo._disabledImageURL;
		var selectedImageURL=imagesInfo._selectedImageURL;
		var expandedImageURL=imagesInfo._expandedImageURL;
		
		if (imageURL || disabledImageURL || selectedImageURL || expandedImageURL) {
			this._images=true; // C'est peut-etre trop tard ?  en Ajax ! ?
			
			if (imageURL) {
				this.f_setNodeImageURL(node, imageURL);
			}

			// Le hover ???
			// var hoverImageURL=node._hoverImageURL;
			// if (hoverImageURL) {
			//	  this.f_setHoverNodeImageURL(node, hoverImageURL);
			// }

			
			if (expandedImageURL) {
				this.f_setExpandedNodeImageURL(node, expandedImageURL);
			}
				
			if (selectedImageURL) {
				this.f_setSelectedNodeImageURL(node, selectedImageURL);
			}
				
			if (disabledImageURL) {
				this.f_setDisabledNodeImageURL(node, disabledImageURL);				
			}
		}
	},
	/** 
	 * @method hidden
	 * @param Object parent Parent node
	 * @param Object node  New node
	 * @return Object New node
	 */
	f_appendNode2: function(parent, node) {
		
		node._tooltip=node._description;
		node._opened=node._expanded;

		if (node._opened===undefined && !this._userExpandable) {
			node._opened=true;
		}
		
		if (!parent._nodes) {
			parent._nodes=new Array;
			parent._container=true;
		}
		
		node._parentTreeNode=parent;
		
		parent._nodes.push(node);
		
		this._setImages(node, node);
		
		var clientDatas=node._clientDatas;
		if (clientDatas) {
			this.f_setItemClientDatas(node, clientDatas);
		}
		
		return node;
	},
	/** 
	 * @method public
	 * @param Object parent Parent node. (or the tree object itself if the node is the root)
	 * @param String label
	 * @param String value
	 * @param optional String tooltip
	 * @return optional Boolean disabled
	 * @return Object The created node.
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
	/**
	 * @method private
	 * @param Array args
	 * @param Number atts
	 * @param Object node
	 * @return void
	 */
	_checkNodeAttributes: function(args, atts, node) {
		if (atts>=args.length) {
			return;
		}
		
		if (this._userExpandable) {
			node._opened=!!args[atts++];
			
			if (atts>=args.length) {
				return;
			}
		}

		if (this.f_isSelectable() && !this._selectionFullState) {
			if (args[atts++]) {
				node._selected=true;
			}
			
			if (atts>=args.length) {
				return;
			}
		}

		if (this.f_isCheckable() && !this._checkFullState) {
			node._checked=!!args[atts++];
			
			if (atts>=args.length) {
				return;
			}
		}
	},
	/**
	 * @method hidden
	 */
	f_setItemImages: function(node) {
		var atts=1;
		
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
	/**
	 * @method public
	 * @param Object node
	 * @param String imageURL
	 * @return void
	 */
	f_setNodeImageURL: function(node, imageURL) {
		node._imageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	/**
	 * @method public
	 * @param Object node
	 * @param String imageURL
	 * @return void
	 */
	f_setExpandedNodeImageURL: function(node, imageURL) {
		node._expandedImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	/**
	 * @method public
	 * @param Object node
	 * @param String imageURL
	 * @return void
	 */
	f_setSelectedNodeImageURL: function(node, imageURL) {
		node._selectedImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	/**
	 * @method public
	 * @param Object node
	 * @param String imageURL
	 * @return void
	 */
	f_setDisabledNodeImageURL: function(node, imageURL) {
		node._disabledImageURL=imageURL;
		if (imageURL) {
			f_imageRepository.PrepareImage(imageURL);
		}
	},
	/**
	 * @method private
	 * @param Object node
	 * @return Object node
	 */
	_getParentNode: function(node) {
		if (node) {
			if (node._parentTreeNode) {
				return node._parentTreeNode;
			}

			return this._getParentNode(node._node);
		}
		return node;
	},
	
	/**
	 * @method public
	 * @param Object node
	 * @return Object node
	 */
	f_getParentNode: function(node) {
		return this._getParentNode(node);
	},
	
	
	/**
	 * @method public
	 * @param any value Value of the node, or the node object
	 * @return Boolean <code>true</code> if the node was found.
	 */
	f_revealAndSelectNode: function(value) {
		var item=this._searchComponentByNodeOrValue(value);
		if (!item) {
			return false;
		}
		var node = item._node;
		if (this.f_revealNode(node)) {
			return this.f_select(node);
		}
		return false;
	},
	/**
	 * @method public
	 * @param any value Value of the node, or the node object
	 * @return Boolean <code>true</code> if the node was found.
	 */
	f_revealNode: function(value) {
		var item=this._searchComponentByNodeOrValue(value);
		if (!item) {
			return false;
		}
		
		var parents = [];
		var parent = this._getParentNode(item);
		// tant qu'on est pas au niveau racine et que le noeud n'est pas ouvert
		while (parent != this && !this.f_isOpened(parent)) {
			parents.push(parent);
			parent = this._getParentNode(parent);
		}
		while (parents.length > 0) {
			parent = parents.pop();
			this.f_openNode(parent);
		}
		
		this.fa_showElement(item);
		
		return true;
	},
	/**
	 * @method private
	 * @param f_event cevt
	 * @return Boolean
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
			if (this.f_isCheckable()) {
				 if (evt && evt.target && evt.target.tagName.toLowerCase()=="input") {
					 // L'input gere de lui-meme le click ! donc on fait pas le job 2x
					 cancel = true;
					 break;
				 }

				this.fa_performElementCheck(this._cursor, true, evt);
				cancel=true;
				break;
			}
				
			// Continue comme une selection ....
			
		case f_key.VK_RETURN:
		case f_key.VK_ENTER:
						
			if (this._cursor && this.f_isSelectable()) {
				this.f_performElementSelection(this._cursor, true, evt, selection);
			}
			cancel=true;
			break;

		case f_key.VK_CONTEXTMENU:
			this._openContextMenu(evt);
			cancel=true;
			break;

		default:
			if (f_key.IsLetterOrDigit(code)) {
				this._searchTreeNode(code, evt, selection);
				
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
	/**
	 * @method private
	 * @param Event evt
	 * @param Number selection
	 * @eturn void
	 */
	_lastTreeNode: function(evt, selection) {
		var lis=this.fa_listVisibleElements();
		if (!lis || !lis.length) {
			return;
		}
		
		this.f_moveCursor(lis[lis.length-1], true, evt, selection);
	},
	/**
	 * @method private
	 * @param Event evt
	 * @param Number selection
	 * @eturn void
	 */
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
	/**
	 * @method private
	 * @param Event evt
	 * @param Number selection
	 * @eturn void
	 */
	_firstTreeNode: function(evt, selection) {		
		var lis=this.fa_listVisibleElements();
		if (!lis || !lis.length) {
			return;
		}

		this.f_moveCursor(lis[0], true, evt, selection);
	},
	/**
	 * @method private
	 * @param Event evt
	 * @param Number selection
	 * @eturn void
	 */
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
	/**
	 * @method private
	 * @param Event evt
	 * @param Number selection
	 * @eturn void
	 */
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
	
	fa_listVisibleElements: function(ordered) {
		return this.f_listVisibleElementsInTree();
	},
	
	f_listVisibleElementsInTree: function(container, list) {
		if (container===undefined) {
			container=this._body;
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
				this.f_listVisibleElementsInTree(ul[0], list);
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
			if(!cursorNode._nodes){
				return;
			}
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
			var li=listLI[i];
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

			this.f_listVisibleElementsInTree(ul, listLI);
		}
	},
	/**
	 * @method private
	 * @param Number code Keycode
	 * @param Event evt
	 * @param Boolean selection
	 * @return Boolean Success
	 */
	_searchTreeNode: function(code, evt, selection) {
		var key=String.fromCharCode(code).toUpperCase();
	
		var now=new Date().getTime();
		if (this._lastKeyDate!==undefined) {
			var dt=now-this._lastKeyDate;
			f_core.Debug(f_tree, "_searchTreeNode: Delay key down "+dt+"ms");
			if (dt<f_tree._SEARCH_KEY_DELAY) {
				var nkey=this._lastKey+key;
				
				if (this._searchTreeNodeByText(nkey, false, evt, selection)) {
					this._lastKeyDate=now;
					this._lastKey=nkey;
					return true;
				}
			}
		}
		
		this._lastKeyDate=now;
		this._lastKey=key;
		
		return this._searchTreeNodeByText(key, true, evt, selection);
	},
	/**
	 * @method private
	 * @param String key
	 * @param Boolean next
	 * @param Event evt
	 * @param Boolean selection
	 * @return Boolean Success
	 */
	_searchTreeNodeByText: function(key, next, evt, selection) {
		var lis=this.fa_listVisibleElements();
		
		var pos=undefined;

		var cursorLi=this._cursor;
		if (cursorLi) {
			for(var i=0;i<lis.length;i++) {
				var l=lis[i];
				if (l!=cursorLi) {
					continue;
				}
				
				pos=i;
				break;
			}
		}
		
		if (pos===undefined) {
			pos=0;
			
		} else if (next) {
			pos++;
		}

		var kl=key.length;
		for(var i=0;i<lis.length;i++) {
			if (pos>=lis.length) {
				pos=0;
			}
			
			var li=lis[pos++];
			
			if (li._node._disabled) {
				continue;
			}
			
			var text=li._node._label;
			
			if (!text || text.length<kl) {
				continue;
			}
			
			if (text.substring(0, kl).toUpperCase()!=key) {
				continue;
			}
			
			this.f_moveCursor(li, true, evt, selection);
			return true;
		}
		
		return false;
	},
	/**
	 * @method private
	 * @return void
	 */
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
	/**
	 * @method public
	 * @param function callback
	 * @param optional any value The value of a node or an element object (Use cursor value if not specified)
	 * @return Boolean
	 */
	f_mapHierarchicalValues: function(callback, value) {
		f_core.Assert(typeof(callback)=="function", "f_tree.f_mapHierarchicalValues: Invalid callback parameter '"+callback+"'.");
		
		if (value===undefined) {
			value=this._cursor;
			if (value===undefined) {
				return undefined;
			}
		}
		
		var cache=new Object;
		var li=value;		
		if (!li || !li._node) {
			li=this._searchComponentByNodeOrValue(li, cache);
			if (!li) {
				return true;
			}
		}
				
		for (;li;) {
			if (callback.call(this, li._node._value, li)===false) {
				return false;
			}
				
			var parentNode=li._node._parentTreeNode;
			if (!parentNode || parentNode==this) {
				break;
			}
			
			li=this._searchComponentByNodeOrValue(parentNode, cache);
		}
		
		return true;
	},
	/**
	 * @method public
	 * @param optional any value The value of a node or an element object (Use cursor value if not specified)
	 * @return String[] Returns Hierarchical values which are opened.
	 */
	f_getHierachicalValues: function(value) {
		var values=new Array;		
		
		this.f_mapHierarchicalValues(function(value, element) {
			values.unshift(value);
		}, value);
		
		return values;
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateBreadCrumbs: function() {
		this._breadCrumbsCursor=this._cursor;

		var ids=new Array;
		var values=new Array;
		var texts=new Array;
		
		var exp=/\|/g;
		
		this.f_mapHierarchicalValues(function(value, element) {
			ids.unshift(element._divNode.id.replace(exp, " "));			
			texts.unshift(element._node._label.replace(exp, " "));			
			values.unshift(value.replace(exp, " "));
		});
		
		this.setAttribute(f_core._VNS+":breadCrumbsIds", ids.join("|"));
		this.setAttribute(f_core._VNS+":breadCrumbsValues", values.join("|"));
		this.setAttribute(f_core._VNS+":breadCrumbsTexts", texts.join("|"));
	},
	/**
	 * @method protected
	 * @return HTMLElement
	 */
	f_getFocusableElement: function() {		
		if (this._cfocus) {
			return this._cfocus;
		}
		
		return this;
	},	
	f_setFocus: function() {
		f_core.Debug(f_tree, "f_setFocus: Set focus on tree '"+this.id+"' cfocus="+this._cfocus);

		if (!f_core.ForceComponentVisibility(this)) {
			return;
		}
		
		if (this._cfocus) {
			if(this._cursor){ //ff3.5.x
				this._cfocus.style.top=this.scrollTop+"px";
			}
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
	_searchComponentByNodeOrValue: function(nodeOrValue, cache, throwError) {
		f_core.Assert(nodeOrValue!==undefined, "f_tree._searchComponentByNodeOrValue: Value parameter is null ! ("+nodeOrValue+")");
		
		if (cache && typeof(nodeOrValue)=="string") {
			var found=undefined;

			if (!cache._initialized) {
				cache._initialized=true;
				
				var c=new Object;
				cache._byValue=c;
				
				
				var lis=this.getElementsByTagName("li");
				for(var i=0;i<lis.length;i++) {
					var li=lis[i];
				
					var n=li._node;
					if (!n) {
						continue;
					}
					
					var v=n._value;
					c[v]=li;
					if (v!=nodeOrValue) {
						continue;
					}
					
					found=li;
				}
			
				if (found) {
					return found;
				}
								
			} else {
				var li=cache._byValue[nodeOrValue];
				if (li) {
					return li;
				}
			}
			if (!throwError) {
				return undefined;
			}
			throw new Error("Can not find node with value '"+nodeOrValue+"'.");
		}

		var lis=this.getElementsByTagName("li");
		for(var i=0;i<lis.length;i++) {
			var li=lis[i];
			
			var n=li._node;
			if (!n || (n!=nodeOrValue && n._value!=nodeOrValue)) {
				continue;
			}
			
			return li;
		}
		
		if (!throwError) {
			return undefined;
		}
		throw new Error("Can not find node with value '"+nodeOrValue+"'.");
	},
	/**
	 * @method hidden
	 * @param String... urls
	 * @return void
	 */
	f_setDefaultImages: function(urls) {
		var i=0;

		this._images=true;

		var url=arguments[i++];
		if (url) this._defaultImageURL=url;

		url=arguments[i++];
		if (url) this._defaultExpandedImageURL=url;
		
		url=arguments[i++];
		if (url) this._defaultCollapsedImageURL=url;

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
	 * @param optional Boolean append Append mode.
	 * @param optional Boolean show Node must be show after the selection.
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return Boolean <code>true</code> if success.
	 */
	f_select: function(value, append, show, jsEvent) {
		var li=this._searchComponentByNodeOrValue(value);
		
		var selection=(append)?fa_selectionManager.APPEND_SELECTION:0;
		
		return this.f_performElementSelection(li, show, jsEvent, selection);
	},
	/**
	 * Check a node.
	 * 
	 * @method public
	 * @param any value Value of the node
	 * @param optional Boolean show Node must be show after the selection.
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return Boolean <code>true</code> if success !
	 */
	f_check: function(value, show, jsEvent) {
		var li=this._searchComponentByNodeOrValue(value);
		
		return this.fa_performElementCheck(li, show, jsEvent, true);
	},
	/**
	 * Uncheck a node.
	 * 
	 * @method public
	 * @param any value Value of the node
	 * @param optional hidden Event jsEvent Javascript event associated to this action.
	 * @return Boolean <code>true</code> if success.
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
	 * @return Boolean <code>true</code> if the node is checked.
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
	 * @return Boolean <code>true</code> if the node is expanded. (open)
	 */
	f_isOpened: function(value) {
		var li=this._searchComponentByNodeOrValue(value);
		if (!li) {
			return false;
		}
		return !!li._node._opened;
	},
	/**
	 * Returns the selection state of a node.
	 *
	 * @method public
	 * @param any value Value of the node, or the node object.
	 * @return Boolean <code>true</code> if the node is selected.
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
	 * @return Boolean
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
	 * @return optional Boolean disabled State to set.
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
		
			var ret=callBack.call(this, node, node._value);
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
		var ret=this.f_forEachNode(function(node, nodeValue) {
			if (nodeValue==value) {
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
		
		this.f_updateStyleClass();
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_updateStyleClass: function() {
		var over=this.f_isMouseOver();
		
		var suffix=null;
		if (this.f_isDisabled()) {
			suffix="_disabled";
			
		} else if (over) {
			suffix="_over";
		}
	
		var className=this.f_computeStyleClass(suffix);
		
		if (over) {
			var overStyleClass=this.f_getOverStyleClass();
			if (overStyleClass) {
				className+=" "+overStyleClass;
			}
		}
				
		if (this.className!=className) {
			this.className=className;
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
		
		this.f_super(arguments);
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

		f_core.Debug(f_tree, "f_clearWaiting: id='"+waitingId+"'.");

		var li=waiting._li;
		f_core.Assert(li, "f_tree.f_clearWaiting: Waiting node is already cleared !");
		
		waiting._li=undefined;
		waiting._image=undefined;
		waiting._label=undefined;
		
		waiting.parentNode.removeChild(waiting);

		if (li==this) {			
			f_core.Debug(f_tree, "f_clearWaiting: reconstruct tree.");

			var nodes=this._nodes;
			if (nodes) {
				this._constructTree(this._body, nodes, 0);
				
				this._updateBodyWidth();
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
			this._updateBodyWidth();
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

			var ul=this._body;
			var children=ul.childNodes;
			var lis=ul.childNodes;
			for(var i=0;i<lis.length;) {
				var li=lis[i];
				if (li.tagName.toLowerCase()!="li") {
					i++;
					continue;
				}
				
				ul.removeChild(li);
			}
			
			this._cursor=undefined;
			this._breadCrumbsCursor=undefined;
			
			this._nodes=new Array;
			var selectedValue= new Array;
			for(var i=0;i<children.length;i++) {
				var child=children[i];
				
				this._nodeFinalizer(child, true, selectedValue);
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
			
			var cursor=this._cursor;
			var breadCrumbsCursor=this._breadCrumbsCursor;
			var selectedValue= new Array;
			for(var i=0;i<children.length;i++) {
				var child=children[i];
				
				if (child==cursor) {
					this._cursor=undefined;
				}
				if (child==breadCrumbsCursor) {
					this._breadCrumbsCursor=undefined;
				}
								
				this._nodeFinalizer(child, true, selectedValue);
			}
			
			if (selectedValue.length) {
				this.fa_fireSelectionChangedEvent(null, f_event.REFRESH_DETAIL);
			}
			
		}
				
		if (opened) {
			this._openNode(node, null, li);
		}
	},
	/**
	 * @method protected
	 * @return HTMLElement
	 */
	fa_componentCaptureMenuEvent: function() {
		return null;
	},

	fa_getElementItem: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_getElementItem: Invalid element parameter ! ("+li+")");

		return li._node;
	},


	fa_getElementValue: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_getElementValue: Invalid element parameter ! ("+li+")");

		return li._node._value;
	},

	fa_isElementDisabled: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_isElementDisabled: Invalid element parameter ! ("+li+")");
		
		return li._node._disabled;
	},

	fa_isElementSelected: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_isElementSelected: Invalid element parameter ! ("+li+")");
		
		return li._node._selected;
	},
	
	fa_setElementSelected: function(li, selected) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_setElementSelected: Invalid element parameter ! ("+li+")");
		
		li._node._selected=selected;
	},

	fa_isElementChecked: function(li) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_isElementChecked: Invalid element parameter ! ("+li+")");
		
		return li._node._checked;
	},
	
	fa_setElementChecked: function(li, checked) {
		f_core.Assert(li && li.tagName.toLowerCase()=="li" && li._node, "f_tree.fa_setElementChecked: Invalid element parameter ! ("+li+")");
		
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
	f_getItemDepth: function(nodeOrValue) {
		if (nodeOrValue._depth) {
			return nodeOrValue._depth;
		}
		
		var li=this._searchComponentByNodeOrValue(nodeOrValue, undefined, false);
		if (li){
			return li._depth;
		}
			
		var node=this._searchNodeByValue(nodeOrValue);
		if (!node) {
			return -1;
		}
		
		return node._depth;
	},
	_searchNodeByValue: function(value) {
		var s = [ this ];
		for(;s.length;) {
			var n=s.shift();
			
			if (n._value==value) {
				return n;
			}
			
			if (n._nodes) {
				s.push.apply(s, n._nodes);
			}
		}
	},
	
	/**
	 * @method public
	 * @param Object nodeOrValue
	 * @return String
	 */
	f_getItemStyleClass: function(nodeOrValue) {
		var li=this._searchComponentByNodeOrValue(nodeOrValue);
		if (!li){
			return undefined;
		}
		return li._node._styleClass;
	},
	
	/**
	 * Returns label of a node
	 * 
	 * @method public
	 * @param Object nodeOrValue
	 * @return String
	 */
	f_getItemLabel: function(nodeOrValue) {
		var li=this._searchComponentByNodeOrValue(nodeOrValue);
		if (!li){
			return undefined;
		}
		return li._node._label;
	},
	/**
	 * Returns image of a node
	 * 
	 * @method public
	 * @param Object nodeOrValue
	 * @return String url of an image
	 */
	f_getItemImage: function(nodeOrValue) {
		var li=this._searchComponentByNodeOrValue(nodeOrValue);
		if (!li){
			return undefined;
		}
		return this._searchNodeImageURL(li._node);
	},

	/**
	 * Returns label of a node
	 *
	 * @method public
	 * @param any value
	 * @return String
	 * @see #f_getItemLabel(nodeOrValue)
	 */
	f_getElementLabel: function(value) {
		return this.f_getItemLabel(value);
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
	},
	f_getItemByValue: function(value) {
		var item=this._searchComponentByNodeOrValue(value);
		
		if (!item) {
			return item;
		}
		
		return item._node;
	},
	fa_updateFilterProperties : function(filterProperties) {
		
		this.f_refreshContent();
		
		return false;
	},
	
	/**
	 * @method private
	 * @param f_event event drag event
	 * @return void
	 */
	_treeDropListener: function(event) {
		var dndEvent = f_dndEvent.As(event);
		var targetComponent = dndEvent.f_getTargetComponent();
		if(!targetComponent) {
			return;
		}
		if(dndEvent.f_getSourceComponent() 
				!= targetComponent) {
			return;
		}
		switch(dndEvent.f_getStage()) {
		case f_dndEvent.DRAG_OVER_STAGE: 
			// interdit le drop dans un noeud fils
			var itemSource = dndEvent.f_getSourceItem();
			var itemTarget = dndEvent.f_getTargetItem();
			while(itemTarget._depth != 0){
				itemTarget = this._getParentNode(itemTarget);
				if(itemTarget == itemSource){
					dndEvent.f_setEffect(f_dndEvent.NONE_DND_EFFECT);
					break;
				}
			}	
			break;
		}
	},
	
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return Boolean
	 */
	_dragNode: function(jsEvent) {
		var dnd=this._dragAndDropEngine;
		if (!dnd) {
			return false;
		}
		
		var selection=new Object;
		selection._items = new Array;
		selection._itemsValue = new Array;
		var itemsDragTypes = new Array;
		var currentSelection = this._currentSelection;
		var lastEffects = undefined;
		for ( var i = 0; i < currentSelection.length; i++) {
					
			var dragTypes=undefined;
			var dragEffects=undefined;
			var node = currentSelection[i]._node;
					
			for(var n=node;n && (dragTypes===undefined || dragEffects===undefined);n=n._parentTreeNode) {
				if (dragTypes===undefined) {
					dragTypes=n._dragTypes;
				}
				if (dragEffects===undefined) {
					dragEffects=n._dragEffects;
				}
			}
			f_core.Debug(f_tree, "_dragNode: dragEffects=0x"+dragEffects+" dragTypes='"+dragTypes+"'");
			
			if (!dragEffects || !dragTypes) {
				return false;
			}
			
			if(lastEffects){
				lastEffects = dragEffects & lastEffects;
			}else {
				lastEffects = dragEffects;
			}
			
			if(itemsDragTypes.length){
				itemsDragTypes = f_dragAndDropEngine.ComputeTypes(dragTypes, itemsDragTypes);
			}else {
				itemsDragTypes = dragTypes;
			}
			
			selection._items[i] = node;
			selection._itemsValue[i] = node._value; 
		}
		
		if (!lastEffects) {
			return false;
		}
		if (!itemsDragTypes.length) {
			return false;
		}
		
		selection._dragEffects = lastEffects;
		selection._dragTypes = itemsDragTypes;
		selection._itemsElement = currentSelection;
		var ret=dnd.f_start(jsEvent, selection);

		f_core.Debug(f_tree, "_dragNode: start returns '"+ret+"'");
		
		return ret;
	},
	
	/**
	 * @method public 
	 * @return Array
	 */
	f_getDragItems : function(selection) {
		return selection._items;
	},
	
	/**
	 * @method public 
	 * @return Array
	 */
	f_getDragItemsValue : function(selection) {
		return selection._itemsValue;
	},
	
	/**
	 * @method public 
	 * @return Array
	 */
	f_getDragItemsElement : function(selection) {
		return selection._itemsElement;
	},
	
	/**
	 * @method public 
	 * @return Array
	 */
	f_getDragTypes : function(selection) {
		return selection._dragTypes;
	},
	
	/**
	 * @method public 
	 * @return Number
	 */
	f_getDragEffects : function(selection) {
		return selection._dragEffects;
	},

	
	/**
	 * @method private
	 * @param HTMLElement element
	 * @return Object
	 */
	_findNodeFromElement: function(element) {
		
		var node=null;
		var nodeElement=null;
		
		for(;element;element=element.parentNode) {
			if (element._body) {
				// Racine de l'arbre
				node=this;
				nodeElement=this;
				break;
			}
			
			var li=element._node;
			if (!li) {
				continue;
					}
					
			if (!element._tree) { // On est tombé sous un sous element de noeud
				nodeElement=li;
					
				li=li._node;
						
			} else {
				// On ne prend pas l'element même !		
				//nodeElement=element;	 	
				continue;
			}
					
			node=li;
			break;
		}
					
		if (!node) {
			return null;
		}

		return {
			_node: node,
			_value: node._value,
			_nodeElement: nodeElement
		};
	},
	f_queryDropInfos: function(dragAndDropEngine, jsEvent, element) {
		this._targetDragAndDropEngine=dragAndDropEngine;

		this.fa_installAutoScroll();

		var node=null;
		var found=this._findNodeFromElement(element);
		if (!found) {
			return null;
							}
							
		node=found._node;
		
		if (this._bodyDroppable!==true && node==this) {
			return null;
						}
						
		if (node._disabled) {
			return null;
		}
						
		var dropTypes=undefined;
		var dropEffects=undefined;
					
		for(var n=node;n && (dropTypes===undefined || dropEffects===undefined);n=n._parentTreeNode) {
			if (dropTypes===undefined) {
				dropTypes=n._dropTypes;
			}
			if (dropEffects===undefined) {
				dropEffects=n._dropEffects;
			}
		}
		
		if (!dropTypes || !dropEffects) {
			return null;
		}		

		return {
			item: node,
			itemValue: found._value,
			targetItemElement: found._nodeElement,
			dropTypes: dropTypes,
			dropEffects: dropEffects		
		};
	},
	f_overDropInfos: function(dragAndDropEngine, infos) {
		var node=infos.item;
		var element=infos.targetItemElement;

		if (node._tooltip) {
			this._lastRemovedTitleElement=element;
			element._divNode.removeAttribute("title");
		}
				
		element._dndOver=true;		
		this.fa_updateElementStyle(element);			
	},
	f_outDropInfos: function(dragAndDropEngine, infos) {
		this._lastRemovedTitleElement=undefined;
			
		var node=infos.item;
		var element=infos.targetItemElement;
		
		if (node._tooltip) {
			element._divNode.title=node._tooltip;
		}
		
		element._dndOver=false;		
		this.fa_updateElementStyle(element);			
	},
	f_releaseDropInfos: function() {
		this._targetDragAndDropEngine=undefined;
		
		this.fa_uninstallAutoScroll();
	},
	fa_getLastMousePosition: function() {
		return this._targetDragAndDropEngine.f_getLastMousePosition();
	},

	fa_autoScrollPerformed: function() {
		if (this._targetDragAndDropEngine) {
			this._targetDragAndDropEngine.f_updateMousePosition();
		}
	},
	fa_getScrollableContainer: function() {
		return this;
	},
	fa_findAutoOpenElement: function(htmlElement) {
		if (!this._userExpandable) {
			return null;
		}

		var found= this._findNodeFromElement(htmlElement);
		if (!found) {
			f_core.Debug(f_tree, "fa_findAutoOpenElement: can not found any component");

			return null;
		}
		
		var node=found._node;

		f_core.Debug(f_tree, "fa_findAutoOpenElement: node="+node+" value='"+node._value+"' container='"+node._container+"' open='"+node._opened+"'.");

		if (node._container && !node._opened && found._value!==undefined) {
			return found;
		}
		
		return null;
	},
	fa_performAutoOpenElement: function(element) {
		this.f_openNode(element._value);		
	},
	fa_isSameAutoOpenElement: function(elt1, elt2) {
		return elt1._value===elt2._value;
	}
};

new f_class("f_tree", {
	extend: f_component,
	aspects: [ fa_readOnly, fa_disabled, fa_immediate, fa_subMenu, fa_selectionManager, fa_checkManager, fa_itemClientDatas, fa_scrollPositions, fa_overStyleClass, fa_filterProperties, fa_droppable, fa_draggable, fa_autoScroll, fa_autoOpen, fa_tabIndex ],
	members: __members,
	statics: __statics
});