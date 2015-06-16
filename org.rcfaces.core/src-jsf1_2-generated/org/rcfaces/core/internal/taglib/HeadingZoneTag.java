package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.HeadingZoneComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class HeadingZoneTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(HeadingZoneTag.class);

	private ValueExpression headingZone;
	private ValueExpression headingLevel;
	private ValueExpression caption;
	public String getComponentType() {
		return HeadingZoneComponent.COMPONENT_TYPE;
	}

	public void setHeadingZone(ValueExpression headingZone) {
		this.headingZone = headingZone;
	}

	public void setHeadingLevel(ValueExpression headingLevel) {
		this.headingLevel = headingLevel;
	}

	public void setCaption(ValueExpression caption) {
		this.caption = caption;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (HeadingZoneComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  headingZone='"+headingZone+"'");
			LOG.debug("  headingLevel='"+headingLevel+"'");
			LOG.debug("  caption='"+caption+"'");
		}
		if ((uiComponent instanceof HeadingZoneComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'HeadingZoneComponent'.");
		}

		super.setProperties(uiComponent);

		HeadingZoneComponent component = (HeadingZoneComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (headingZone != null) {
			if (headingZone.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_ZONE, headingZone);

			} else {
				component.setHeadingZone(getBool(headingZone.getExpressionString()));
			}
		}

		if (headingLevel != null) {
			if (headingLevel.isLiteralText()==false) {
				component.setValueExpression(Properties.HEADING_LEVEL, headingLevel);

			} else {
				component.setHeadingLevel(headingLevel.getExpressionString());
			}
		}

		if (caption != null) {
			if (caption.isLiteralText()==false) {
				component.setValueExpression(Properties.CAPTION, caption);

			} else {
				component.setCaption(caption.getExpressionString());
			}
		}
	}

	public void release() {
		headingZone = null;
		headingLevel = null;
		caption = null;

		super.release();
	}

}
