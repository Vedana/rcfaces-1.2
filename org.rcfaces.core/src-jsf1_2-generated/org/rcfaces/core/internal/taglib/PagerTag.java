package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.PagerComponent;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class PagerTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PagerTag.class);

	private ValueExpression forValue;
	private ValueExpression manyResultsMessage;
	private ValueExpression message;
	private ValueExpression oneResultMessage;
	private ValueExpression zeroResultMessage;
	private ValueExpression noPagedMessage;
	public String getComponentType() {
		return PagerComponent.COMPONENT_TYPE;
	}

	public void setFor(ValueExpression forValue) {
		this.forValue = forValue;
	}

	public void setManyResultsMessage(ValueExpression manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
	}

	public void setMessage(ValueExpression message) {
		this.message = message;
	}

	public void setOneResultMessage(ValueExpression oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public void setZeroResultMessage(ValueExpression zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public void setNoPagedMessage(ValueExpression noPagedMessage) {
		this.noPagedMessage = noPagedMessage;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (PagerComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  forValue='"+forValue+"'");
			LOG.debug("  manyResultsMessage='"+manyResultsMessage+"'");
			LOG.debug("  message='"+message+"'");
			LOG.debug("  oneResultMessage='"+oneResultMessage+"'");
			LOG.debug("  zeroResultMessage='"+zeroResultMessage+"'");
			LOG.debug("  noPagedMessage='"+noPagedMessage+"'");
		}
		if ((uiComponent instanceof PagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PagerComponent'.");
		}

		super.setProperties(uiComponent);

		PagerComponent component = (PagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (forValue != null) {
			if (forValue.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR, forValue);

			} else {
				component.setFor(forValue.getExpressionString());
			}
		}

		if (manyResultsMessage != null) {
			if (manyResultsMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);

			} else {
				component.setManyResultsMessage(manyResultsMessage.getExpressionString());
			}
		}

		if (message != null) {
			if (message.isLiteralText()==false) {
				component.setValueExpression(Properties.MESSAGE, message);

			} else {
				component.setMessage(message.getExpressionString());
			}
		}

		if (oneResultMessage != null) {
			if (oneResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ONE_RESULT_MESSAGE, oneResultMessage);

			} else {
				component.setOneResultMessage(oneResultMessage.getExpressionString());
			}
		}

		if (zeroResultMessage != null) {
			if (zeroResultMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);

			} else {
				component.setZeroResultMessage(zeroResultMessage.getExpressionString());
			}
		}

		if (noPagedMessage != null) {
			if (noPagedMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.NO_PAGED_MESSAGE, noPagedMessage);

			} else {
				component.setNoPagedMessage(noPagedMessage.getExpressionString());
			}
		}
	}

	public void release() {
		forValue = null;
		manyResultsMessage = null;
		message = null;
		oneResultMessage = null;
		zeroResultMessage = null;
		noPagedMessage = null;

		super.release();
	}

}
