package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.PagerComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class PagerTag extends AbstractBasicTag implements Tag {


	private static final Log LOG=LogFactory.getLog(PagerTag.class);

	private String forVal;
	private String message;
	private String zeroResultMessage;
	private String oneResultMessage;
	private String manyResultsMessage;
	private String noPagedMessage;
	public String getComponentType() {
		return PagerComponent.COMPONENT_TYPE;
	}

	public final String getFor() {
		return forVal;
	}

	public final void setFor(String forVal) {
		this.forVal = forVal;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

	public final String getZeroResultMessage() {
		return zeroResultMessage;
	}

	public final void setZeroResultMessage(String zeroResultMessage) {
		this.zeroResultMessage = zeroResultMessage;
	}

	public final String getOneResultMessage() {
		return oneResultMessage;
	}

	public final void setOneResultMessage(String oneResultMessage) {
		this.oneResultMessage = oneResultMessage;
	}

	public final String getManyResultsMessage() {
		return manyResultsMessage;
	}

	public final void setManyResultsMessage(String manyResultsMessage) {
		this.manyResultsMessage = manyResultsMessage;
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
			LOG.debug("  forVal='"+forVal+"'");
			LOG.debug("  message='"+message+"'");
			LOG.debug("  zeroResultMessage='"+zeroResultMessage+"'");
			LOG.debug("  oneResultMessage='"+oneResultMessage+"'");
			LOG.debug("  manyResultsMessage='"+manyResultsMessage+"'");
			LOG.debug("  noPagedMessage='"+noPagedMessage+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof PagerComponent)==false) {
			throw new IllegalStateException("Component specified by tag is not instanceof of 'PagerComponent'.");
		}

		PagerComponent component = (PagerComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (forVal != null) {
			if (isValueReference(forVal)) {
				ValueBinding vb = application.createValueBinding(forVal);
				component.setFor(vb);
			} else {
				component.setFor(forVal);
			}
		}

		if (message != null) {
			if (isValueReference(message)) {
				ValueBinding vb = application.createValueBinding(message);
				component.setMessage(vb);
			} else {
				component.setMessage(message);
			}
		}

		if (zeroResultMessage != null) {
			if (isValueReference(zeroResultMessage)) {
				ValueBinding vb = application.createValueBinding(zeroResultMessage);
				component.setZeroResultMessage(vb);
			} else {
				component.setZeroResultMessage(zeroResultMessage);
			}
		}

		if (oneResultMessage != null) {
			if (isValueReference(oneResultMessage)) {
				ValueBinding vb = application.createValueBinding(oneResultMessage);
				component.setOneResultMessage(vb);
			} else {
				component.setOneResultMessage(oneResultMessage);
			}
		}

		if (manyResultsMessage != null) {
			if (isValueReference(manyResultsMessage)) {
				ValueBinding vb = application.createValueBinding(manyResultsMessage);
				component.setManyResultsMessage(vb);
			} else {
				component.setManyResultsMessage(manyResultsMessage);
			}
		}

		if (noPagedMessage != null) {
			if (isValueReference(noPagedMessage)) {
				ValueBinding vb = application.createValueBinding(noPagedMessage);
				component.setNoPagedMessage(vb);
			} else {
				component.setNoPagedMessage(noPagedMessage);
			}
		}
	}

	public void release() {
		forVal = null;
		message = null;
		zeroResultMessage = null;
		oneResultMessage = null;
		manyResultsMessage = null;
		noPagedMessage = null;

		super.release();
	}

}
