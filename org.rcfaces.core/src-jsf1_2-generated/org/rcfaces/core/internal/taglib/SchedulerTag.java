package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.core.component.SchedulerComponent;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class SchedulerTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(SchedulerTag.class);

	private ValueExpression width;
	private ValueExpression height;
	private ValueExpression horizontalScrollPosition;
	private ValueExpression verticalScrollPosition;
	private ValueExpression literalLocale;
	private ValueExpression literalTimeZone;
	private ValueExpression styleClass;
	private ValueExpression tabIndex;
	private ValueExpression immediate;
	private ValueExpression selectionListeners;
	private ValueExpression dateBegin;
	private ValueExpression hourBegin;
	private ValueExpression hourEnd;
	private ValueExpression primaryTick;
	private ValueExpression secondaryTick;
	private ValueExpression showPrimaryTickLabel;
	private ValueExpression showSecondaryTickLabel;
	private ValueExpression periods;
	private ValueExpression periodBegin;
	private ValueExpression periodEnd;
	private ValueExpression periodLabel;
	private ValueExpression periodStyle;
	private ValueExpression periodSelectable;
	private ValueExpression periodToolTip;
	private ValueExpression periodValue;
	private ValueExpression var;
	public String getComponentType() {
		return SchedulerComponent.COMPONENT_TYPE;
	}

	public final void setWidth(ValueExpression width) {
		this.width = width;
	}

	public final void setHeight(ValueExpression height) {
		this.height = height;
	}

	public final void setHorizontalScrollPosition(ValueExpression horizontalScrollPosition) {
		this.horizontalScrollPosition = horizontalScrollPosition;
	}

	public final void setVerticalScrollPosition(ValueExpression verticalScrollPosition) {
		this.verticalScrollPosition = verticalScrollPosition;
	}

	public final void setLiteralLocale(ValueExpression literalLocale) {
		this.literalLocale = literalLocale;
	}

	public final void setLiteralTimeZone(ValueExpression literalTimeZone) {
		this.literalTimeZone = literalTimeZone;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setTabIndex(ValueExpression tabIndex) {
		this.tabIndex = tabIndex;
	}

	public final void setImmediate(ValueExpression immediate) {
		this.immediate = immediate;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setDateBegin(ValueExpression dateBegin) {
		this.dateBegin = dateBegin;
	}

	public final void setHourBegin(ValueExpression hourBegin) {
		this.hourBegin = hourBegin;
	}

	public final void setHourEnd(ValueExpression hourEnd) {
		this.hourEnd = hourEnd;
	}

	public final void setPrimaryTick(ValueExpression primaryTick) {
		this.primaryTick = primaryTick;
	}

	public final void setSecondaryTick(ValueExpression secondaryTick) {
		this.secondaryTick = secondaryTick;
	}

	public final void setShowPrimaryTickLabel(ValueExpression showPrimaryTickLabel) {
		this.showPrimaryTickLabel = showPrimaryTickLabel;
	}

	public final void setShowSecondaryTickLabel(ValueExpression showSecondaryTickLabel) {
		this.showSecondaryTickLabel = showSecondaryTickLabel;
	}

	public final void setPeriods(ValueExpression periods) {
		this.periods = periods;
	}

	public final void setPeriodBegin(ValueExpression periodBegin) {
		this.periodBegin = periodBegin;
	}

	public final void setPeriodEnd(ValueExpression periodEnd) {
		this.periodEnd = periodEnd;
	}

	public final void setPeriodLabel(ValueExpression periodLabel) {
		this.periodLabel = periodLabel;
	}

	public final void setPeriodStyle(ValueExpression periodStyle) {
		this.periodStyle = periodStyle;
	}

	public final void setPeriodSelectable(ValueExpression periodSelectable) {
		this.periodSelectable = periodSelectable;
	}

	public final void setPeriodToolTip(ValueExpression periodToolTip) {
		this.periodToolTip = periodToolTip;
	}

	public final void setPeriodValue(ValueExpression periodValue) {
		this.periodValue = periodValue;
	}

	public final void setVar(ValueExpression var) {
		this.var = var;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (SchedulerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  width='"+width+"'");
			LOG.debug("  height='"+height+"'");
			LOG.debug("  horizontalScrollPosition='"+horizontalScrollPosition+"'");
			LOG.debug("  verticalScrollPosition='"+verticalScrollPosition+"'");
			LOG.debug("  literalLocale='"+literalLocale+"'");
			LOG.debug("  literalTimeZone='"+literalTimeZone+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  tabIndex='"+tabIndex+"'");
			LOG.debug("  immediate='"+immediate+"'");
			LOG.debug("  dateBegin='"+dateBegin+"'");
			LOG.debug("  hourBegin='"+hourBegin+"'");
			LOG.debug("  hourEnd='"+hourEnd+"'");
			LOG.debug("  primaryTick='"+primaryTick+"'");
			LOG.debug("  secondaryTick='"+secondaryTick+"'");
			LOG.debug("  showPrimaryTickLabel='"+showPrimaryTickLabel+"'");
			LOG.debug("  showSecondaryTickLabel='"+showSecondaryTickLabel+"'");
			LOG.debug("  periods='"+periods+"'");
			LOG.debug("  periodBegin='"+periodBegin+"'");
			LOG.debug("  periodEnd='"+periodEnd+"'");
			LOG.debug("  periodLabel='"+periodLabel+"'");
			LOG.debug("  periodStyle='"+periodStyle+"'");
			LOG.debug("  periodSelectable='"+periodSelectable+"'");
			LOG.debug("  periodToolTip='"+periodToolTip+"'");
			LOG.debug("  periodValue='"+periodValue+"'");
			LOG.debug("  var='"+var+"'");
		}
		if ((uiComponent instanceof SchedulerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'SchedulerComponent'.");
		}

		super.setProperties(uiComponent);

		SchedulerComponent component = (SchedulerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (width != null) {
			if (width.isLiteralText()==false) {
				component.setValueExpression(Properties.WIDTH, width);

			} else {
				component.setWidth(width.getExpressionString());
			}
		}

		if (height != null) {
			if (height.isLiteralText()==false) {
				component.setValueExpression(Properties.HEIGHT, height);

			} else {
				component.setHeight(height.getExpressionString());
			}
		}

		if (horizontalScrollPosition != null) {
			if (horizontalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);

			} else {
				component.setHorizontalScrollPosition(getInt(horizontalScrollPosition.getExpressionString()));
			}
		}

		if (verticalScrollPosition != null) {
			if (verticalScrollPosition.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);

			} else {
				component.setVerticalScrollPosition(getInt(verticalScrollPosition.getExpressionString()));
			}
		}

		if (literalLocale != null) {
			if (literalLocale.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_LOCALE, literalLocale);

			} else {
				component.setLiteralLocale(literalLocale.getExpressionString());
			}
		}

		if (literalTimeZone != null) {
			if (literalTimeZone.isLiteralText()==false) {
				component.setValueExpression(Properties.LITERAL_TIME_ZONE, literalTimeZone);

			} else {
				component.setLiteralTimeZone(literalTimeZone.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (tabIndex != null) {
			if (tabIndex.isLiteralText()==false) {
				component.setValueExpression(Properties.TAB_INDEX, tabIndex);

			} else {
				component.setTabIndex(getInteger(tabIndex.getExpressionString()));
			}
		}

		if (immediate != null) {
			if (immediate.isLiteralText()==false) {
				component.setValueExpression(Properties.IMMEDIATE, immediate);

			} else {
				component.setImmediate(getBool(immediate.getExpressionString()));
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (dateBegin != null) {
			if (dateBegin.isLiteralText()==false) {
				component.setValueExpression(Properties.DATE_BEGIN, dateBegin);

			} else {
				component.setDateBegin(dateBegin.getExpressionString());
			}
		}

		if (hourBegin != null) {
			if (hourBegin.isLiteralText()==false) {
				component.setValueExpression(Properties.HOUR_BEGIN, hourBegin);

			} else {
				component.setHourBegin(hourBegin.getExpressionString());
			}
		}

		if (hourEnd != null) {
			if (hourEnd.isLiteralText()==false) {
				component.setValueExpression(Properties.HOUR_END, hourEnd);

			} else {
				component.setHourEnd(hourEnd.getExpressionString());
			}
		}

		if (primaryTick != null) {
			if (primaryTick.isLiteralText()==false) {
				component.setValueExpression(Properties.PRIMARY_TICK, primaryTick);

			} else {
				component.setPrimaryTick(getInt(primaryTick.getExpressionString()));
			}
		}

		if (secondaryTick != null) {
			if (secondaryTick.isLiteralText()==false) {
				component.setValueExpression(Properties.SECONDARY_TICK, secondaryTick);

			} else {
				component.setSecondaryTick(getInt(secondaryTick.getExpressionString()));
			}
		}

		if (showPrimaryTickLabel != null) {
			if (showPrimaryTickLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_PRIMARY_TICK_LABEL, showPrimaryTickLabel);

			} else {
				component.setShowPrimaryTickLabel(getBool(showPrimaryTickLabel.getExpressionString()));
			}
		}

		if (showSecondaryTickLabel != null) {
			if (showSecondaryTickLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_SECONDARY_TICK_LABEL, showSecondaryTickLabel);

			} else {
				component.setShowSecondaryTickLabel(getBool(showSecondaryTickLabel.getExpressionString()));
			}
		}

		if (periods != null) {
			if (periods.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIODS, periods);

			} else {
				component.setPeriods(periods.getExpressionString());
			}
		}

		if (periodBegin != null) {
			if (periodBegin.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_BEGIN, periodBegin);

			} else {
				component.setPeriodBegin(periodBegin.getExpressionString());
			}
		}

		if (periodEnd != null) {
			if (periodEnd.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_END, periodEnd);

			} else {
				component.setPeriodEnd(periodEnd.getExpressionString());
			}
		}

		if (periodLabel != null) {
			if (periodLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_LABEL, periodLabel);

			} else {
				component.setPeriodLabel(periodLabel.getExpressionString());
			}
		}

		if (periodStyle != null) {
			if (periodStyle.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_STYLE, periodStyle);

			} else {
				component.setPeriodStyle(periodStyle.getExpressionString());
			}
		}

		if (periodSelectable != null) {
			if (periodSelectable.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_SELECTABLE, periodSelectable);

			} else {
				component.setPeriodSelectable(getBool(periodSelectable.getExpressionString()));
			}
		}

		if (periodToolTip != null) {
			if (periodToolTip.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_TOOL_TIP, periodToolTip);

			} else {
				component.setPeriodToolTip(periodToolTip.getExpressionString());
			}
		}

		if (periodValue != null) {
			if (periodValue.isLiteralText()==false) {
				component.setValueExpression(Properties.PERIOD_VALUE, periodValue);

			} else {
				component.setPeriodValue(periodValue.getExpressionString());
			}
		}

		if (var != null) {
			if (var.isLiteralText()==false) {
				component.setValueExpression(Properties.VAR, var);

			} else {
				component.setVar(var.getExpressionString());
			}
		}
	}

	public void release() {
		width = null;
		height = null;
		horizontalScrollPosition = null;
		verticalScrollPosition = null;
		literalLocale = null;
		literalTimeZone = null;
		styleClass = null;
		tabIndex = null;
		immediate = null;
		selectionListeners = null;
		dateBegin = null;
		hourBegin = null;
		hourEnd = null;
		primaryTick = null;
		secondaryTick = null;
		showPrimaryTickLabel = null;
		showSecondaryTickLabel = null;
		periods = null;
		periodBegin = null;
		periodEnd = null;
		periodLabel = null;
		periodStyle = null;
		periodSelectable = null;
		periodToolTip = null;
		periodValue = null;
		var = null;

		super.release();
	}

}
