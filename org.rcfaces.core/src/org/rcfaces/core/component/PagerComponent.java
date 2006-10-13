package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.AbstractBasicComponent;

public class PagerComponent extends AbstractBasicComponent implements 
	IForCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.pager";


	public PagerComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public PagerComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getFor() {
		return getFor(null);
	}

	public final java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public final void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final void setFor(ValueBinding forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public final String getMessage() {
		return getMessage(null);
	}

	public final String getMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MESSAGE, facesContext);
	}

	public final void setMessage(String message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	public final void setMessage(ValueBinding message) {
		engine.setProperty(Properties.MESSAGE, message);
	}

	public final boolean isMessageSetted() {
		return engine.isPropertySetted(Properties.MESSAGE);
	}

	public final String getZeroResultMessage() {
		return getZeroResultMessage(null);
	}

	public final String getZeroResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ZERO_RESULT_MESSAGE, facesContext);
	}

	public final void setZeroResultMessage(String zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
	}

	public final void setZeroResultMessage(ValueBinding zeroResultMessage) {
		engine.setProperty(Properties.ZERO_RESULT_MESSAGE, zeroResultMessage);
	}

	public final boolean isZeroResultMessageSetted() {
		return engine.isPropertySetted(Properties.ZERO_RESULT_MESSAGE);
	}

	public final String getOneResultMessage() {
		return getOneResultMessage(null);
	}

	public final String getOneResultMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ONE_RESULT_MESSAGE, facesContext);
	}

	public final void setOneResultMessage(String oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public final void setOneResultMessage(ValueBinding oneResultMessage) {
		engine.setProperty(Properties.ONE_RESULT_MESSAGE, oneResultMessage);
	}

	public final boolean isOneResultMessageSetted() {
		return engine.isPropertySetted(Properties.ONE_RESULT_MESSAGE);
	}

	public final String getManyResultsMessage() {
		return getManyResultsMessage(null);
	}

	public final String getManyResultsMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.MANY_RESULTS_MESSAGE, facesContext);
	}

	public final void setManyResultsMessage(String manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public final void setManyResultsMessage(ValueBinding manyResultsMessage) {
		engine.setProperty(Properties.MANY_RESULTS_MESSAGE, manyResultsMessage);
	}

	public final boolean isManyResultsMessageSetted() {
		return engine.isPropertySetted(Properties.MANY_RESULTS_MESSAGE);
	}

	public final String getNoPagedMessage() {
		return getNoPagedMessage(null);
	}

	public final String getNoPagedMessage(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.NO_PAGED_MESSAGE, facesContext);
	}

	public final void setNoPagedMessage(String noPagedMessage) {
		engine.setProperty(Properties.NO_PAGED_MESSAGE, noPagedMessage);
	}

	public final void setNoPagedMessage(ValueBinding noPagedMessage) {
		engine.setProperty(Properties.NO_PAGED_MESSAGE, noPagedMessage);
	}

	public final boolean isNoPagedMessageSetted() {
		return engine.isPropertySetted(Properties.NO_PAGED_MESSAGE);
	}

	public void release() {
		super.release();
	}
}
