package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.PagerComponent;
import org.rcfaces.core.internal.component.Properties;

public class PagerTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PagerTag.class);

	private String forValue;
	private String manyResultsMessage;
	private String message;
	private String oneResultMessage;
	private String zeroResultMessage;
	private String noPagedMessage;
	public String getComponentType() {
		return PagerComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forValue;
	}

	public final void setFor(String forValue) {
		this.forValue = forValue;
	}

	public final String getManyResultsMessage() {
		return manyResultsMessage;
	}

	public final void setManyResultsMessage(String manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final String getOneResultMessage() {
		return oneResultMessage;
	}

	public final void setOneResultMessage(String oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public final String getZeroResultMessage() {
		return zeroResultMessage;
	}

	public final void setZeroResultMessage(String zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public final String getNoPagedMessage() {
		return noPagedMessage;
	}

	public final void setNoPagedMessage(String noPagedMessage) {
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
		super.setProperties(uiComponent);

		if ((uiComponent instanceof PagerComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PagerComponent'.");
		}

		PagerComponent component = (PagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (forValue != null) {
			if (isValueReference(forValue)) {
				ValueBinding vb = application.createValueBinding(forValue);
				component.setValueBinding(Properties.FOR, vb);

			} else {
				component.setFor(forValue);
			}
		}

		if (manyResultsMessage != null) {
			if (isValueReference(manyResultsMessage)) {
				ValueBinding vb = application.createValueBinding(manyResultsMessage);
				component.setValueBinding(Properties.MANY_RESULTS_MESSAGE, vb);

			} else {
				component.setManyResultsMessage(manyResultsMessage);
			}
		}

		if (message != null) {
			if (isValueReference(message)) {
				ValueBinding vb = application.createValueBinding(message);
				component.setValueBinding(Properties.MESSAGE, vb);

			} else {
				component.setMessage(message);
			}
		}

		if (oneResultMessage != null) {
			if (isValueReference(oneResultMessage)) {
				ValueBinding vb = application.createValueBinding(oneResultMessage);
				component.setValueBinding(Properties.ONE_RESULT_MESSAGE, vb);

			} else {
				component.setOneResultMessage(oneResultMessage);
			}
		}

		if (zeroResultMessage != null) {
			if (isValueReference(zeroResultMessage)) {
				ValueBinding vb = application.createValueBinding(zeroResultMessage);
				component.setValueBinding(Properties.ZERO_RESULT_MESSAGE, vb);

			} else {
				component.setZeroResultMessage(zeroResultMessage);
			}
		}

		if (noPagedMessage != null) {
			if (isValueReference(noPagedMessage)) {
				ValueBinding vb = application.createValueBinding(noPagedMessage);
				component.setValueBinding(Properties.NO_PAGED_MESSAGE, vb);

			} else {
				component.setNoPagedMessage(noPagedMessage);
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
