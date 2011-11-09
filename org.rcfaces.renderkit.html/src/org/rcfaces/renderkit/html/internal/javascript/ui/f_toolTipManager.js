/**
 * Class Tooltip Manager
 * 
 * @class public f_toolTipManager extends f_object
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field private static
	 */
	_Instance : undefined,

	/**
	 * @field private final static
	 */
	_DEFAULT_DELAY : 600,
	/**
	 * @field private static
	 */
	_Delay : undefined,

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
	_HidePopup: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(f_toolTipManager, "_HidePopup: event="+evt);

		var instance = f_toolTipManager.Get();
		
		try {
			return instance._hidePopupEvent(evt);

		} catch (x) {
			f_core.Error(f_toolTipManager, "_HidePopup: exception", x);
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

	f_toolTipManager : function() {
		this.f_super(arguments);

		var delay = undefined;

		if (this.nodeType == f_core.ELEMENT_NODE) {
			delay = f_core.GetAttribute(this, "v:delay");
		}

		if (delay>0) {
			f_toolTipManager._DELAY = delay;
			
		} else {
			f_toolTipManager._DELAY = f_toolTipManager._DEFAULT_DELAY;
		}

		f_core.AddEventListener(document.body, "mouseover",
				f_toolTipManager._ElementOver, document.body);
		
		f_core.AddEventListener(document.body, "mouseout",
				f_toolTipManager._ElementOut, document.body);

		// Touches
		
		f_core.AddEventListener(document.body, "keydown",
				f_toolTipManager._HidePopup, document.body);
		
		// Focus
		
		f_core.AddEventListener(document.body, "focus",
				f_toolTipManager._HidePopup, document.body);
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
		var tooltipContainer = this._getTooltipContainerForElement(element);

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
			this.f_hidePopup(currentTooltip);
			currentTooltip.f_clear();
			
			this._currentTooltip = undefined;
		}
		
		if (!tooltipContainer) {
			// Pas de container de tooltip, rien à faire			
			return true;
		}

		var tooltip = tooltipContainer.fa_getTooltipForElement(element);
		if (!tooltip) {
			return true;
		}
		
		var self=this;
		this._currentTooltip = tooltip;
		this._timerId = window.setTimeout(function() {
			self.f_showTooltip(tooltip);
			self=undefined;
			tooltip=undefined;
			
		}, f_toolTipManager._DELAY);
		
		return true;
	},
	
	/**
	 * @method protected
	 * @param f_toolTip tooltip
	 * @return void
	 */
	f_showTooltip: function(tooltip) {
		if(!tooltip) {
			return;
		}
		
		var tooltipContainer = tooltip.f_getElementContainer();
		if (!tooltipContainer) {
			return;
		}
		
		tooltip._x = evt.layerX;
		tooltipContainer.fa_setTooltipVisible(tooltip, true, true);

		f_core.Debug(f_toolTipManager, "f_showTooltip: tooltipContainer="
				+ tooltipContainer.id + " tooltip=" + tooltip.id);
	},
	
	/**
	 * @method protected
	 * @param f_toolTip tooltip
	 * @return void
	 */
	f_hideTooltip: function(tooltip) {
		if(!tooltip) {
			return;
		}
		
		var tooltipContainer = tooltip.f_getElementContainer();
		if (!tooltipContainer) {
			return;
		}
		
		tooltip._x = evt.layerX;
		tooltipContainer.fa_setTooltipVisible(tooltip, false, true);

		f_core.Debug(f_toolTipManager, "f_hideTooltip: tooltipContainer="
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
	_hidePopupEvent: function(evt) {
		var currentTooltip = this._currentTooltip;
		
		if (!currentTooltip) {
			return;
		}

		this._currentTooltip = undefined;
		
		this.f_hidePopup(currentTooltip);
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
	_getTooltipContainerForElement : function(source) {
		
		var classLoader = this.f_getClass().f_getClassLoader();

		var parentTooltip = null;
		
		var comp=source;
		for (; comp ; comp=comp.parentNode) {

			if (comp.alt || comp.title) {
				// Risque d'afficher un tooltip ... on laisse tomber TOUT !
				return false;
			}
			
			classLoader.f_init(comp, true, true);
			
			if (comp.fa_getTooltipForElement && !parentTooltip) {
				// Il faut rechercher jusqu'à la racine pour rechercher un ALT ou un TITLE
				parentTooltip=comp;
			}
		}
		
		return parentTooltip;
	}
};

new f_class("f_toolTipManager", {
	extend : f_object,
	statics : __statics,
	members : __members
});
