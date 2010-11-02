package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TabbedPaneTag extends CardBoxTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TabbedPaneTag.class);

	private ValueExpression closeListeners;
	private ValueExpression closable;
	private ValueExpression preSelectionListeners;
	private ValueExpression showValue;
	public String getComponentType() {
		return TabbedPaneComponent.COMPONENT_TYPE;
	}

	public final void setCloseListener(ValueExpression closeListeners) {
		this.closeListeners = closeListeners;
	}

	public final void setClosable(ValueExpression closable) {
		this.closable = closable;
	}

	public final void setPreSelectionListener(ValueExpression preSelectionListeners) {
		this.preSelectionListeners = preSelectionListeners;
	}

	public final void setShowValue(ValueExpression showValue) {
		this.showValue = showValue;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TabbedPaneComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  closable='"+closable+"'");
			LOG.debug("  showValue='"+showValue+"'");
		}
		if ((uiComponent instanceof TabbedPaneComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TabbedPaneComponent'.");
		}

		super.setProperties(uiComponent);

		TabbedPaneComponent component = (TabbedPaneComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (closeListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.CLOSE_LISTENER_TYPE, closeListeners);
		}

		if (closable != null) {
			if (closable.isLiteralText()==false) {
				component.setValueExpression(Properties.CLOSABLE, closable);

			} else {
				component.setClosable(getBool(closable.getExpressionString()));
			}
		}

		if (preSelectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.PRE_SELECTION_LISTENER_TYPE, preSelectionListeners);
		}

		if (showValue != null) {
			if (showValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SHOW_VALUE, showValue);

			} else {
				component.setShowValue(showValue.getExpressionString());
			}
		}
	}

	public void release() {
		closeListeners = null;
		closable = null;
		preSelectionListeners = null;
		showValue = null;

		super.release();
	}

}
