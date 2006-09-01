/*
 * $Id$
 */
 
/** 
 * f_component class
 *
 * @class f_component extends f_eventTarget, HTMLElement, fa_serializable, fa_clientData
 * @author Joel Merlin & Olivier Oeuillot
 * @version $Revision$
 */
 
var __static = {
	
	/**
	 * @field public static final string 
	 */
	HIDDEN_MODE_IGNORE: "ignore",
	/**
	 * @field public static final string 
	 */
	HIDDEN_MODE_SERVER: "server",
	/**
	 * @field public static final string 
	 */
	HIDDEN_MODE_PHANTOM: "phantom",
	
	/**
	 * @method static hidden
	 * @return HTMLElement Underline zone component
	 */
	AddLabelWithAccessKey: function(parent, label, accessKey, removeText) {
		if (removeText) {			
			while(parent.firstChild) {
				parent.removeChild(parent.firstChild);
			}
		}
	
		if (!accessKey || accessKey.length!=1) {
			parent.appendChild(document.createTextNode(label));
			
			return null;
		}
		accessKey=accessKey.toUpperCase();
		
		var lab=label.toUpperCase();
		
		var idx=lab.indexOf(accessKey);
		
		if (idx<0) {				
			parent.appendChild(document.createTextNode(label));
	
			return null;
		}
		
		if (idx>0) {
			parent.appendChild(document.createTextNode(label.substring(0, idx)));
		}

		var sub=document.createElement("U");
		sub.className="f_accessKey";
		parent.appendChild(sub);
		sub.appendChild(document.createTextNode(label.substring(idx, idx+1)));				
		
		if (idx+1<lab.length) {
			parent.appendChild(document.createTextNode(label.substring(idx+1, lab.length)));
		}
		
		return sub;
	},
	
	/**
	 * @method hidden static final
	 */
	GetDefaultHiddenMode: function() {
		return f_component.DEFAULT_HIDDEN_MODE;
	}
}

var __prototype = {
	/**
	 * @method hidden
	 * @return void
	 */
	f_component: function() {
		this.f_super(arguments);
		this._componentUpdated = false;
		
		var accessKey=f_core.GetAttribute(this, "v:accessKey");
		if (accessKey) {
			this._accessKey=accessKey;
			
		} else {
			accessKey=this.accessKey;
		}
		
		if (accessKey) {
			f_key.AddKeyHandler(null, accessKey, this, this.f_performAccessKey);
		}
		
		var helpURL=f_core.GetAttribute(this, "v:helpURL");
		if (helpURL) {
			this.f_setHelpURL(helpURL);
		}
		var helpMessage=f_core.GetAttribute(this, "v:helpMessage");
		if (helpMessage) {
			this.f_setHelpMessage(helpMessage);
		}
		var hiddenMode=f_core.GetAttribute(this, "v:hiddenMode");
		if (hiddenMode) {
			this.f_setHiddenMode(hiddenMode);
		}
	},
	/*
	f_finalize: function() {
		this._hiddenMode = undefined; // string 
		this._helpMessageSet=undefined; // boolean
		this._helpMessage = undefined; // string
		this._helpURLSet=undefined; // boolean
		this._helpURL=undefined; // string
		this._accessKey=undefined; // string
		this._componentUpdated = undefined; // boolean
		this._oldDisplay = undefined; // string

		this.f_super(arguments);		
	},
	*/

	/**
	 * Returns the idenfiant of the component.
	 * 
	 * @method public
	 * @return string Identifier
	 */
	f_getId: function() {
		return this.id;
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getX: function() {
		return this.style.left;
	},
	/**
	 * @method public
	 * @param number x
	 * @return void
	 */
	f_setX: function(x) {
		f_core.Assert(typeof(x)=="number", "x parameter must be a number ! ("+x+")");
		
		this.style.left = x;
		this.f_setProperty(f_prop.X,x);
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getY: function() {
		return this.style.top;
	},
	/**
	 * @method public
	 * @param number y
	 * @return void
	 */
	f_setY: function(y) {
		f_core.Assert(typeof(y)=="number", "y parameter must be a number ! ("+y+")");

		this.style.top = y;
		this.f_setProperty(f_prop.Y,y);
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getWidth: function() {
		return this.style.width;
	},
	/**
	 * @method public
	 * @param number w
	 * @return void
	 */
	f_setWidth: function(w) {
		f_core.Assert(typeof(w)=="number", "w parameter must be a number ! ("+w+")");

		this.style.width = w;
		this.f_setProperty(f_prop.WIDTH,w);
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getHeight: function() {
		return this.style.height;
	},
	/**
	 * @method public
	 * @param number h
	 * @return void
	 */
	f_setHeight: function(h) {
		f_core.Assert(typeof(h)=="number", "h parameter must be a number ! ("+h+")");

		this.style.height = h;
		this.f_setProperty(f_prop.HEIGHT,h);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getBackgroundColor: function() {
		return this.style.backgroundColor;
	},
	/**
	 * @method public
	 * @param string color
	 * @return void
	 */
	f_setBackgroundColor: function(color) {
		f_core.Assert(color===null || typeof(color)=="string", "Background color parameter must be a string ! ("+color+")");

		this.style.backgroundColor = color;
		this.f_setProperty(f_prop.BACKGROUND,color);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getForegroundColor: function() {
		return this.style.color;
	},
	/**
	 * @method public
	 * @param string color
	 * @return void
	 */
	f_setForegroundColor: function(color) {
		f_core.Assert(color===null || typeof(color)=="string", "Foreground color parameter must be a string ! ("+color+")");

		this.style.color = color;
		this.f_setProperty(f_prop.FOREGROUND,color);
	},
	/**
	 * Returns the receiver's tool tip text, or <code>null</code> if it has not been set.
	 * 
	 * @method public
	 * @return string the receiver's tool tip text
	 */
	f_getToolTipText: function() {
		return this.title;
	},
	/**
	 * Sets the receiver's tool tip text to the argument, 
	 * which may be <code>null</code> indicating that no tool tip text should be shown.
	 
	 * @method public
	 * @param string title the new tool tip text (or <code>null</code>)
	 * @return void
	 */
	f_setToolTipText: function(title) {
		f_core.Assert(title===null || typeof(title)=="string", "Title parameter must be a string ! ("+title+")");

		this.title = title;
		this.f_setProperty(f_prop.TOOLTIP,title);
	},
	/**
	 * Returns <code>true</code> if the receiver is visible, and <code>false</code> otherwise.
	 * <br>
	 * If one of the receiver's ancestors is not visible or some other condition makes the receiver not visible,
	 * this method may still indicate that it is considered visible even though it may not actually be showing.
	 *
	 * @method public final
	 * @return boolean the receiver's visibility state
	 */
	f_getVisible: function() {
		if (this._visible===undefined) {
			var hiddenMode=this.f_getHiddenMode();
			if (hiddenMode==f_component.HIDDEN_MODE_PHANTOM) {
				this._visible=(this.style.visibility!="hidden");

			} else {
				this._visible=(this.style.display!="none");
			}
		}

		return this._visible;
	},
	/**
	 * Returns <code>true</code> if the receiver is visible and all ancestors up to and including the receiver's nearest ancestor document are visible.
	 * Otherwise, <code>false</code> is returned.
	 *
	 * @method public final
	 * @return boolean the receiver's visibility state
	 */
	f_isVisible: function() {
		if (!this.f_getVisible()) {
			return false;
		}
		
		return f_core.IsComponentVisible(this);
	},
	/**
	 * Marks the receiver as visible if the argument is true, and marks it invisible otherwise.
	 * <br>
	 * If one of the receiver's ancestors is not visible or some other condition makes the receiver not visible, 
	 * marking it visible may not actually cause it to be displayed.
	 *
	 * @method public
	 * @param boolean visible the new visibility state
	 * @return void
	 */
	f_setVisible: function(visible) {
		f_core.Assert(typeof(visible)=="boolean", "Visible parameter must be a boolean ! ("+visible+")");

		if (this._visible==visible) {
			return;
		}
		
		this._visible=visible;
		
		this._updateVisibility(this, visible);

		this._kclass._classLoader.fireVisibleEvent(this);

		this.f_setProperty(f_prop.VISIBLE,visible);		
	},
	/**
	 * @method private
	 * @return void
	 */
	_updateVisibility: function(component, visible) {
		var hiddenMode=this.f_getHiddenMode();
		if (hiddenMode==f_component.HIDDEN_MODE_PHANTOM) {
			if (!visible) {
				component.style.visibility="hidden";
				return;
			}
			
			component.style.visibility="inherit";
			return;
		}
			
		// Mode IGNORE et SERVEUR
		if (!visible) {
			if (component.style.display != "none") {
				component._oldDisplay = component.style.display;
				component.style.display="none";
			}
			return;
		}
			
		if (component._oldDisplay) {
			component.style.display = component._oldDisplay;
			return;
		}
		
		component.style.display = f_core.GetDefaultDisplayMode(component);
		component._oldDisplay = component.style.display;
	},
	/**
	 * @method public
	 * @param string mode
	 * @return void
	 */
	f_setHiddenMode: function(mode) {
		f_core.Assert(mode===null || typeof(mode)=="string", "Hidden mode parameter must be a string ! ("+mode+")");

		this._hiddenMode = mode;
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getHiddenMode: function() {
		if (!this._hiddenMode) {
			this._hiddenMode = f_component.GetDefaultHiddenMode();
		}
		return this._hiddenMode;
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getHelpURL: function() {
		return this.f_helpURL;
	},
	/**
	 * @method public
	 * @param string url
	 * @return void
	 */
	f_setHelpURL: function(url) {
		f_core.Assert(url===null || typeof(url)=="string", "Help URL parameter must be a string ! ("+url+")");

		this._helpURL = url;

		if (this._helpURLSet) {
			return;
		}
		this._helpURLSet=true;
		
		var f_help=this.f_getClassLoader().getClass("f_help");
		if (!f_help) {
			return;
		}

		f_help.Install();
		this.f_addEventListener(f_event.FOCUS, f_help._OnFocus);
		this.f_addEventListener(f_event.BLUR, f_help._OnBlur);
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getHelpMessage: function() {
		return this._helpMessage;
	},
	/**
	 * @method public
	 * @param string msg
	 * @return void
	 */
	f_setHelpMessage: function(msg) {
		f_core.Assert(msg===null || typeof(msg)=="string", "Message parameter must be a string ! ("+msg+")");

		this._helpMessage = msg;

		if (this._helpMessageSet) {
			return;
		}
		this._helpMessageSet=true;

		var f_help=this.f_getClassLoader().getClass("f_help");
		if (!f_help) {
			return;
		}
		
		f_help.SetHelpMessageZone();
		this.f_addEventListener(f_event.MOUSEOVER,f_help._OnShowHelpMessage);
		this.f_addEventListener(f_event.MOUSEOUT,f_help._OnHideHelpMessage);
		this.f_addEventListener(f_event.FOCUS,f_help._OnShowHelpMessage);
		this.f_addEventListener(f_event.BLUR,f_help._OnHideHelpMessage);
	},
	/**
	 * @method protected
	 * @param f_event evt
	 * @return void
	 */
	f_performAccessKey: f_key.DefaultAccessKey,
	/**
	 * @method public
	 * @return void
	 */
	f_setFocus: function() {
		f_core.Assert(false, "Focus method not implemented !");
	},
	/**
	 * @method public
	 * @param optional boolean scroll Scroll into view to show the component.
	 *		(<code>true</code> align on top, <code>false</code> align on bottom)
	 * @return boolean if the component can be shown.
	 */	 
	f_show: function(scroll) {
		if (!this.f_parentShow()) {
			return false;
		}
		
		if (scroll!==undefined) {
			this.scrollIntoView(scroll);
		}
		
		return true;
	},
	/**
	 * @method protected
	 * @return void
	 */	 
	f_parentShow: function() {
		for(var comp=this;;) {
			var parent=f_core.GetParentComponent(comp);
			if (!parent) {
				return true;
			}
			
			if (parent.f_parentShow) {
				return parent.f_parentShow();
			}
			
			comp=parent;
		}
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getAccessKey: function() {
		return this._accessKey;
	},
	/**
	 * @method public
	 * @return HTMLElement
	 */
	f_getParent: function() {
		return this.parentNode;
	},
	/**
	 *
	 *
	 * @method protected
	 * @return void
	 */
	f_documentComplete: function() {
	},
	/**
	 *
	 *
	 * @method protected
	 * @return void
	 */
	f_update: function(set) {
		this._componentUpdated = (set===undefined)? true:set;		
	},
	/**
	 * @method hidden
	 */
	_completeComponent: function() {
	 	try {
	 		this.f_update(true);
	 		
	 	} catch (x) {
	 		f_core.Error(f_component, "Call of f_update throws exception !", x);
	 	}

		if (!this._hasInitListeners) {
			return;
		}
		this._hasInitListeners=undefined;
		
		this.f_onInitEvent();
	},
	f_serialize: function() {
		f_core.Assert(this._componentUpdated, "Method _componentUpdated not called for component '"+this.id+"/"+this._kclass+"'.");
	},
	
	/**
     * <p>Search for and return the {@link f_component} with an <code>id</code>
     * that matches the specified search expression (if any), according to the
     * algorithm described below.</p>
     *
     * <p>Component identifiers are required to be unique within the scope of
     * the closest ancestor {@link fa_namingContainer} that encloses this
     * component (which might be this component itself).  If there are no
     * {@link fa_namingContainer} components in the ancestry of this component,
     * the root component in the tree is treated as if it were a
     * {@link fa_namingContainer}, whether or not its class actually implements
     * the {@link fa_namingContainer} interface.</p>
     *
     * <p>A <em>search expression</em> consists of either an
     * identifier (which is matched exactly against the <code>id</code>
     * property of a {@link f_component}, or a series of such identifiers
     * linked by the {@link fa_namingContainer#SeparatorChar} character value.
     * The search algorithm operates as follows:</p>
     * <ul>
     * <li>Identify the {@link f_component} that will be the base for searching,
     *     by stopping as soon as one of the following conditions is met:
     *     <ul>
     *     <li>If the search expression begins with the the separator character
     *         (called an "absolute" search expression),
     *         the base will be the root {@link f_component} of the component
     *         tree.  The leading separator character will be stripped off,
     *         and the remainder of the search expression will be treated as
     *         a "relative" search expression as described below.</li>
     *     <li>Otherwise, if this {@link f_component} is a
     *         {@link fa_namingContainer} it will serve as the basis.</li>
     *     <li>Otherwise, search up the parents of this component.  If
     *         a {@link fa_namingContainer} is encountered, it will be the base.
     *         </li>
     *     <li>Otherwise (if no {@link fa_namingContainer} is encountered)
     *         the root {@link f_component} will be the base.</li>
     *     </ul></li>
     * <li>The search expression (possibly modified in the previous step) is now
     *     a "relative" search expression that will be used to locate the
     *     component (if any) that has an <code>id</code> that matches, within
     *     the scope of the base component.  The match is performed as follows:
     *     <ul>
     *     <li>If the search expression is a simple identifier, this value is
     *         compared to the <code>id</code> property, and then recursively
     *         through the facets and children of the base {@link f_component}
     *         (except that if a descendant {@link fa_namingContainer} is found,
     *         its own facets and children are not searched).</li>
     *     <li>If the search expression includes more than one identifier
     *         separated by the separator character, the first identifier is
     *         used to locate a {@link fa_namingContainer} by the rules in the
     *         previous bullet point.  Then, the <code>findComponent()</code>
     *         method of this {@link fa_namingContainer} will be called, passing
     *         the remainder of the search expression.</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @method public 
     *
 	 * @param string id Identifier of component.
	 * @param optional string id2 Identifier of the child of the found component. (optional)
      *
     * @return f_component the found {@link f_component}, or <code>null</code>
     *  if the component was not found.
     *
     * @exception Error if an intermediate identifier
     *  in a search expression identifies a {@link f_component} that is
     *  not a {@link f_namingContainer}
     */
	f_findComponent: function(id, id2) {
		return fa_namingContainer.FindComponents(this, arguments);
	}
}

var f_component=new f_class("f_component", null, __static, __prototype, f_eventTarget, fa_serializable, fa_clientData);
