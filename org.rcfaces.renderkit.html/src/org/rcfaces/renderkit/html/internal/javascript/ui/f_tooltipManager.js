/**
 * Class Tooltip Manager
 * 
 * @class public f_tooltipManager extends f_object
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

		try {
			var instance = f_tooltipManager._Instance;

			var current = f_tooltipManager._Current;
			if (current) {
				if (!f_tooltipManager._ElementOut(evt)) {
					return;
				}
			}

			var element = instance._getElementAtPosition(evt);
			var tooltipContainer = instance
					._getTooltipContainerForElement(element);

			if (!tooltipContainer) {
				f_tooltipManager._Current = undefined;
				return;
			}

			if (tooltipContainer.fa_getTooltipForElement) {
				tooltip = tooltipContainer.fa_getTooltipForElement(element);
			}

			if (tooltip) {

				f_tooltipManager._Current = tooltip._elementContainer;
				f_tooltipManager._timer = setTimeout(function() {
					
					if(!tooltip) {
						return;
					}
					
					tooltip._x = +evt.layerX;
					tooltipContainer.fa_setTooltipVisible(tooltip, true, true);

					f_core.Debug(f_tooltipManager, "Show toolTipcontainer="
							+ tooltipContainer.id + " tooltip" + tooltip.id);

				}, f_tooltipManager._DELAY);

			}

			return;

		} catch (x) {
			f_core.Error(f_tooltipManager, "_ElementMove: exception", x);
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

		try {

			if (!f_tooltipManager._Current) {
				return;
			}

			if (f_tooltipManager._timer) {
				clearTimeout(f_tooltipManager._timer);
			}

			var instance = f_tooltipManager._Instance;

			var current = f_tooltipManager._Current;

			if (current) {
				var element = instance._getElementAtPosition(evt);
				var tooltipContainer = undefined;
				
				if (element) {
					// if (instance._isTooltipChild(element, tooltipId)) {
					// return false;
					// }
										
					tooltipContainer = instance._getTooltipContainerForElement(element);
					
					if (tooltipContainer) {
						if (current === element) {
							return false;
						}
					} else {
						tooltipContainer = instance._getTooltipContainerForElement(current);
					}
				}
				
				var tooltip = tooltipContainer.fa_getTooltipForElement(current);
				tooltip.f_setVisible(false);

				f_core.Debug(f_tooltipManager, "Hide component = "
						+ current.id + "tooltip = " + tooltip.id);
				f_tooltipManager._Current = undefined;
			}

		} catch (x) {
			f_core.Error(f_tooltipManager, "_ElementOut: exception", x);
		}

		return;

	},

	/**
	 * @method public static
	 * @param optional
	 *            Number delay
	 * @return f_tooltipManager
	 */
	Get : function(delay) {
		var instance = f_tooltipManager._Instance;
		if (!instance) {
			instance = f_tooltipManager.f_newInstance(delay);
			f_tooltipManager._Instance = instance;
		}

		return instance;
	},

	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer : function() {
		f_tooltipManager._Instance = undefined; // f_tooltipManager
	}

};

var __members = {

	f_tooltipManager : function() {
		this.f_super(arguments);

		var delay = undefined;

		if (this.nodeType == f_core.ELEMENT_NODE) {
			delay = f_core.GetAttribute(this, "v:delay");
		}

		if (!delay && arguments.length > 0) {
			delay = arguments[0];
		}

		if (delay) {
			f_tooltipManager._DELAY = delay;
		} else {
			f_tooltipManager._DELAY = f_tooltipManager._DEFAULT_DELAY;
		}

		f_core.AddEventListener(document.body, "mouseover",
				f_tooltipManager._ElementOver, document.body);
		f_core.AddEventListener(document.body, "mouseout",
				f_tooltipManager._ElementOut, document.body);

	},

	f_finalize : function() {

		this.f_super(arguments);
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
	_getTooltipContainerForElement : function(element) {
		var parent = element;
		while (parent) {
			if (parent.id) {
				var component = f_core.GetElementByClientId(parent.id);

				if (component.fa_getTooltipForElement) {
					return component;
				}
			}

			parent = parent.parentNode;
		}
		return undefined;
	},

	/**
	 * @method private
	 * @param String
	 *            elementId
	 * @param String
	 *            tooltipId
	 * @return Boolean
	 */
	_isTooltipChild : function(elementId, tooltipId) {
		if (elementId) {
			var component = f_core.GetElementByClientId(elementId);

			var parent = component;
			while (parent.parentNode) {

				var parent = parent.parentNode;
				if (parent.id && parent.id.indexOf(tooltipId) > 0) {
					return true;
				}
			}
		}
		return false;
	}

};

new f_class("f_tooltipManager", {
	extend : f_object,
	statics : __statics,
	members : __members
});
