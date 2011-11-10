/**
 * Class Tooltip Manager
 * 
 * @class public f_toolTipManager extends f_object
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field private static f_toolTipManager
	 */
	_Instance : undefined,

	/**
	 * @field private final static Number
	 */
	_DEFAULT_DELAY_MS: 600,

	/**
	 * @field private final static Number
	 */
	_DEFAULT_NEIGHBOUR_THRESHOLD_MS: 100,

	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return Boolean
	 * @context event:evt
	 */
	_ElementOver : function(evt) {
		
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(f_toolTipManager, "_ElementOver: event="+evt);
		
		var instance = f_toolTipManager.Get();

		try {
			return instance._elementOver(evt);

		} catch (x) {
			f_core.Error(f_toolTipManager, "_ElementOver: exception", x);
		}
	},

	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return Boolean
	 * @context event:evt
	 */
	_ElementOut : function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(f_toolTipManager, "_ElementOver: event="+evt);

		var instance = f_toolTipManager.Get();
		
		try {
			return instance._elementOut(evt);

		} catch (x) {
			f_core.Error(f_toolTipManager, "_ElementOut: exception", x);
		}
	},
	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return Boolean
	 * @context event:evt
	 */
	_HideToolTip: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(f_toolTipManager, "_HideToolTip: event="+evt);

		var instance = f_toolTipManager.Get();
		
		try {
			return instance._hideTooltipEvent(evt);

		} catch (x) {
			f_core.Error(f_toolTipManager, "_HideToolTip: exception", x);
		}
	},

	/**
	 * @method public static
	 * @return f_toolTipManager
	 */
	Get : function() {
		var instance = f_toolTipManager._Instance;
		if (!instance) {
			instance = f_toolTipManager.f_newInstance();
			f_toolTipManager._Instance = instance;
		}

		return instance;
	},

	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer : function() {
		f_toolTipManager._Instance = undefined; // f_toolTipManager
	}

};

var __members = {
		
	/**
	 * @field f_toolTip
	 */
	_currentTooltip: undefined,

	/**
	 * @field Number
	 */
	_timerId: undefined,

	/**
	 * @field Number
	 */
	_showDelayMs: undefined,
	
	/**
	 * @field Number
	 */
	_neighbourThresholdMs: undefined,
	
	/**
	 * @field Number
	 */
	_lastToolTipClose: undefined,
	

	f_toolTipManager : function() {
		this.f_super(arguments);

		var delay = undefined;
		var neighbourThresholdMs=undefined;

		if (this.nodeType == f_core.ELEMENT_NODE) {
			delay = f_core.GetNumberAttribute(this, "v:delay", -1);
			neighbourThresholdMs = f_core.GetNumberAttribute(this, "v:neighbourThreshold", -1);
		}

		if (delay<0 || delay===undefined) {
			delay=f_toolTipManager._DEFAULT_DELAY_MS;
		}
		if (neighbourThresholdMs<0 || neighbourThresholdMs===undefined) {
			neighbourThresholdMs=f_toolTipManager._DEFAULT_NEIGHBOUR_THRESHOLD_MS;
		}
				
		this._showDelayMs=delay;
		this._neighbourThresholdMs=neighbourThresholdMs;
			
		f_core.AddEventListener(document.body, "mouseover",
				f_toolTipManager._ElementOver, document.body);
		
		f_core.AddEventListener(document.body, "mouseout",
				f_toolTipManager._ElementOut, document.body);

		// Touches
		
		f_core.AddEventListener(document.body, "keydown",
				f_toolTipManager._HideToolTip, document.body);
		
		// Focus
		
		f_core.AddEventListener(document.body, "focus",
				f_toolTipManager._HideToolTip, document.body);
	},

	f_finalize : function() {
		this._currentTooltip= undefined;
		
		var timerId=this._timerId;
		if (timerId) {
			this._timerId = undefined;
			
			clearTimeout(timerId);
		}
		
		this.f_super(arguments);
	},
	
	/**
	 * @method private static
	 * @param Event evt
	 * @return Boolean
	 */
	_elementOver : function(evt) {

		var element = this._getElementAtPosition(evt);
		var tooltipContainer = this._getToolTipContainerForElement(element);

		var currentTooltip = this._currentTooltip;
		
		if (tooltipContainer==currentTooltip) {
			// Le même tooltip que celui qui est déjà affiché !
			return true;
		}
		
		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			
			window.clearTimeout(timerId);			
		}
		
		if (currentTooltip) {
			this.f_hideToolTip(currentTooltip);
			currentTooltip.f_clear();
			
			this._currentTooltip = undefined;
		}
		
		if (!tooltipContainer) {
			// Pas de container de tooltip, rien à faire			
			return true;
		}

		var tooltip = tooltipContainer.fa_getToolTipForElement(element);
		if (!tooltip) {
			return true;
		}
		
		var self=this;
		this._currentTooltip = tooltip;
		
		if (this._showDelayMs==0) {
			this.f_showToolTip(tooltip, evt);
			return true;
		}
		
		if (this._neighbourThresholdMs>=0 && this._lastToolTipClose>0) {
			var now=new Date().getTime();
			
			if (now<this._lastToolTipClose+this._neighbourThresholdMs) {
				this.f_showToolTip(tooltip, evt);
				return true;				
			}
		}
		
		this._timerId = window.setTimeout(function() {
			self.f_showToolTip(tooltip, evt);
			self=undefined;
			tooltip=undefined;
			
		}, this._showDelayMs);
		
		return true;
	},
	
	/**
	 * @method protected
	 * @param f_toolTip tooltip
	 * @param optional Event jsEvent
	 * @return void
	 */
	f_showToolTip: function(tooltip, jsEvent) {
		if(!tooltip) {
			return;
		}
		
		var tooltipContainer = tooltip.f_getElementContainer();
		if (!tooltipContainer) {
			return;
		}
		
		tooltipContainer.fa_setTooltipVisible(tooltip, true, true, jsEvent);

		f_core.Debug(f_toolTipManager, "f_showToolTip: tooltipContainer="
				+ tooltipContainer.id + " tooltip=" + tooltip.id);
	},
	
	/**
	 * @method protected
	 * @param f_toolTip tooltip
	 * @param optional Event jsEvent
	 * @return void
	 */
	f_hideToolTip: function(tooltip, jsEvent) {
		if (!tooltip) {
			return;
		}
		
		var tooltipContainer = tooltip.f_getElementContainer();
		if (!tooltipContainer) {
			return;
		}
		
		this._lastToolTipClose=new Date().getTime();
		
		tooltipContainer.fa_setTooltipVisible(tooltip, false, true, jsEvent);

		f_core.Debug(f_toolTipManager, "f_hideToolTip: tooltipContainer="
				+ tooltipContainer.id + " tooltip=" + tooltip.id);
		
	},
	
	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return Boolean
	 */
	_elementOut : function(evt) {
	},
	
	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return Boolean
	 */
	_hideToolTipEvent: function(evt) {
		var currentTooltip = this._currentTooltip;
		
		if (!currentTooltip) {
			return;
		}

		this._currentTooltip = undefined;
		
		this.f_hideToolTip(currentTooltip);
		currentTooltip.f_clear();
	},

	/**
	 * @method private
	 * @param Event
	 *            evt
	 * @return Element element
	 */
	_getElementAtPosition : function(evt) {

		var eventPos = f_core.GetJsEventPosition(evt);
		if (!eventPos) {
			return undefined;
		}
		var de = f_core.SearchComponentByAbsolutePosition(eventPos.x,
				eventPos.y);
		var element = (de.length) ? de[de.length - 1] : null;

		if (element) {
			return element;
		}

		return undefined;
	},

	/**
	 * @method private
	 * @param Element
	 *            element
	 * @return Any container
	 */
	_getToolTipContainerForElement : function(source) {
	
		var parentTooltip = null;
		
		var comp=source;
		for (; comp ; comp=comp.parentNode) {
			
			if (comp.nodeType==f_core.TEXT_NODE) {
				continue;
			}

			if (comp.nodeType!=f_core.ELEMENT_NODE) {
				break;
			}
			
			if (comp.alt || comp.title) {
				// Risque d'afficher un tooltip ... on laisse tomber TOUT !
				return false;
			}
			
			var state = f_classLoader.GetObjectState(comp);

			if (state==f_classLoader.UNKNOWN_STATE) {
				continue;
			}
			
			if (state == f_classLoader.LAZY_STATE) {
				var classLoader = this.f_getClass().f_getClassLoader();
				
				classLoader.f_init(comp, true, true);
			}
			
			if (comp.fa_getToolTipForElement && !parentTooltip) {
				// Il faut rechercher jusqu'à la racine pour rechercher un ALT ou un TITLE
				parentTooltip=comp;
			}
		}
		
		return parentTooltip;
	},
	
	/**
	 * @method public
	 * @param Number showDelayMs
	 * @return void
	 */
	f_setShowDelayMs: function(showDelayMs) {
		if (!showDelayMs || showDelayMs<0) {
			showDelayMs = f_toolTipManager._DEFAULT_DELAY;
		}
		
		this._showDelayMs=showDelayMs;
	},
	
	/**
	 * @method public
	 * @param Number neighbourThresholdMs
	 * @return void
	 */
	f_setNeighbourThresholdMs: function(neighbourThresholdMs) {
		if (!neighbourThresholdMs || neighbourThresholdMs<0) {
			neighbourThresholdMs = f_toolTipManager._DEFAULT_DELAY;
		}
		
		this._neighbourThresholdMs=neighbourThresholdMs;
	}
};

new f_class("f_toolTipManager", {
	extend : f_object,
	statics : __statics,
	members : __members
});
