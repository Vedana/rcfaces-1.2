package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.CardBoxComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CardBoxTag extends AbstractInputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardBoxTag.class);

	private ValueExpression selectionListeners;
	private ValueExpression asyncRenderMode;
	private ValueExpression preferences;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression asyncDecodeMode;
	private ValueExpression headingZone;
	private ValueExpression headingLevel;
	public String getComponentType() {
		return CardBoxComponent.COMPONENT_TYPE;
	}

	public void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public void setAsyncRenderMode(ValueExpression asyncRenderMode) {
		this.asyncRenderMode = asyncRenderMode;
	}

	public void setPreferences(ValueExpression preferences) {
		this.preferences = preferences;
	}

	public void setScopeSaveValue(ValueExpression scopeSaveValue) {
		this.scopeSaveValue = scopeSaveValue;
	}

	public void setScopeValue(ValueExpression scopeValue) {
		this.scopeValue = scopeValue;
	}

	public void setScopeVar(ValueExpression scopeVar) {
		this.scopeVar = scopeVar;
	}

	public void setAsyncDecodeMode(ValueExpression asyncDecodeMode) {
		this.asyncDecodeMode = asyncDecodeMode;
	}

	public void setHeadingZone(ValueExpression headingZone) {
		this.headingZone = headingZone;
	}

	public void setHeadingLevel(ValueExpression headingLevel) {
		this.headingLevel = headingLevel;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardBoxComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  asyncRenderMode='"+asyncRenderMode+"'");
			LOG.debug("  preferences='"+preferences+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  asyncDecodeMode='"+asyncDecodeMode+"'");
			LOG.debug("  headingZone='"+headingZone+"'");
			LOG.debug("  headingLevel='"+headingLevel+"'");
		}
		if ((uiComponent instanceof CardBoxComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardBoxComponent'.");
		}

		super.setProperties(uiComponent);

		CardBoxComponent component = (CardBoxComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (asyncRenderMode != null) {
			if (asyncRenderMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ASYNC_RENDER_MODE, asyncRenderMode);

			} else {
				component.setAsyncRenderMode(asyncRenderMode.getExpressionString());
			}
		}

		if (preferences != null) {
				component.setValueExpression(Properties.PREFERENCES, preferences);
		}

		if (scopeSaveValue != null) {
			if (scopeSaveValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_SAVE_VALUE, scopeSaveValue);

			} else {
				component.setScopeSaveValue(getBool(scopeSaveValue.getExpressionString()));
			}
		}

		if (scopeValue != null) {
			if (scopeValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VALUE, scopeValue);

			} else {
				component.setScopeValue(scopeValue.getExpressionString());
			}
		}

		if (scopeVar != null) {
			if (scopeVar.isLiteralText()==false) {
				component.setValueExpression(Properties.SCOPE_VAR, scopeVar);

			} else {
				component.setScopeVar(scopeVar.getExpressionString());
			}
		}

		if (asyncDecodeMode != null) {
			if (asyncDecodeMode.isLiteralText()==false) {
				component.setValueExpression(Properties.ASYNC_DECODE_MODE, asyncDecodeMode);

			} else {
				component.setAsyncDecodeMode(asyncDecodeMode.getExpressionString());
			}
		}

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
	}

	public void release() {
		selectionListeners = null;
		asyncRenderMode = null;
		preferences = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		asyncDecodeMode = null;
		headingZone = null;
		headingLevel = null;

		super.release();
	}

}
