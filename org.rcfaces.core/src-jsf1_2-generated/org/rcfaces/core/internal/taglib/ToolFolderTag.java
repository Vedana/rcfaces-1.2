package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolFolderComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ToolFolderTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ToolFolderTag.class);

	private ValueExpression initListeners;
	private ValueExpression mouseOutListeners;
	private ValueExpression mouseOverListeners;
	private ValueExpression doubleClickListeners;
	private ValueExpression verticalAlignment;
	private ValueExpression borderType;
	public String getComponentType() {
		return ToolFolderComponent.COMPONENT_TYPE;
	}

	public final void setInitListener(ValueExpression initListeners) {
		this.initListeners = initListeners;
	}

	public final void setMouseOutListener(ValueExpression mouseOutListeners) {
		this.mouseOutListeners = mouseOutListeners;
	}

	public final void setMouseOverListener(ValueExpression mouseOverListeners) {
		this.mouseOverListeners = mouseOverListeners;
	}

	public final void setDoubleClickListener(ValueExpression doubleClickListeners) {
		this.doubleClickListeners = doubleClickListeners;
	}

	public final void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public final void setBorderType(ValueExpression borderType) {
		this.borderType = borderType;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ToolFolderComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  borderType='"+borderType+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof ToolFolderComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ToolFolderComponent'.");
		}

		ToolFolderComponent component = (ToolFolderComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (initListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.INIT_LISTENER_TYPE, initListeners);
		}

		if (mouseOutListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OUT_LISTENER_TYPE, mouseOutListeners);
		}

		if (mouseOverListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.MOUSE_OVER_LISTENER_TYPE, mouseOverListeners);
		}

		if (doubleClickListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.DOUBLE_CLICK_LISTENER_TYPE, doubleClickListeners);
		}

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
		}

		if (borderType != null) {
			if (borderType.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER_TYPE, borderType);

			} else {
				component.setBorderType(borderType.getExpressionString());
			}
		}
	}

	public void release() {
		initListeners = null;
		mouseOutListeners = null;
		mouseOverListeners = null;
		doubleClickListeners = null;
		verticalAlignment = null;
		borderType = null;

		super.release();
	}

}
