package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class CardTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(CardTag.class);

	private ValueExpression textAlignment;
	private ValueExpression verticalAlignment;
	private ValueExpression scopeSaveValue;
	private ValueExpression scopeValue;
	private ValueExpression scopeVar;
	private ValueExpression asyncDecodeMode;
	private ValueExpression loadListeners;
	public String getComponentType() {
		return CardComponent.COMPONENT_TYPE;
	}

	public void setTextAlignment(ValueExpression textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setVerticalAlignment(ValueExpression verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
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

	public void setLoadListener(ValueExpression loadListeners) {
		this.loadListeners = loadListeners;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (CardComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  textAlignment='"+textAlignment+"'");
			LOG.debug("  verticalAlignment='"+verticalAlignment+"'");
			LOG.debug("  scopeSaveValue='"+scopeSaveValue+"'");
			LOG.debug("  scopeValue='"+scopeValue+"'");
			LOG.debug("  scopeVar='"+scopeVar+"'");
			LOG.debug("  asyncDecodeMode='"+asyncDecodeMode+"'");
		}
		if ((uiComponent instanceof CardComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'CardComponent'.");
		}

		super.setProperties(uiComponent);

		CardComponent component = (CardComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (textAlignment != null) {
			if (textAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.TEXT_ALIGNMENT, textAlignment);

			} else {
				component.setTextAlignment(textAlignment.getExpressionString());
			}
		}

		if (verticalAlignment != null) {
			if (verticalAlignment.isLiteralText()==false) {
				component.setValueExpression(Properties.VERTICAL_ALIGNMENT, verticalAlignment);

			} else {
				component.setVerticalAlignment(verticalAlignment.getExpressionString());
			}
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

		if (loadListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.LOAD_LISTENER_TYPE, loadListeners);
		}
	}

	public void release() {
		textAlignment = null;
		verticalAlignment = null;
		scopeSaveValue = null;
		scopeValue = null;
		scopeVar = null;
		asyncDecodeMode = null;
		loadListeners = null;

		super.release();
	}

}
