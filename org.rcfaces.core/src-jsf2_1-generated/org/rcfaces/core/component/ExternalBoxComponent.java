package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IOverStyleClassCapability;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import java.util.Collection;

public class ExternalBoxComponent extends AbstractOutputComponent implements 
	ILoadEventCapability,
	IOverStyleClassCapability {

	private static final Log LOG = LogFactory.getLog(ExternalBoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.externalBox";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractOutputComponent.BEHAVIOR_EVENT_NAMES;

	public ExternalBoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ExternalBoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void addLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		addFacesListener(listener);
	}

	public final void removeLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listLoadListeners() {
		return getFacesListeners(org.rcfaces.core.event.ILoadListener.class);
	}

	public java.lang.String getOverStyleClass() {
		return getOverStyleClass(null);
	}

	/**
	 * See {@link #getOverStyleClass() getOverStyleClass()} for more details
	 */
	public java.lang.String getOverStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.OVER_STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "overStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverStyleClassSetted() {
		return getStateHelper().get(Properties.OVER_STYLE_CLASS)!=null;
	}

	public void setOverStyleClass(java.lang.String overStyleClass) {
		getStateHelper().put(Properties.OVER_STYLE_CLASS, overStyleClass);
	}

	public String getScrolling() {
		return getScrolling(null);
	}

	public String getScrolling(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.SCROLLING);
	}

	public void setScrolling(String scrolling) {
		 getStateHelper().put(Properties.SCROLLING, scrolling);
	}

	/**
	 * Returns <code>true</code> if the attribute "scrolling" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isScrollingSetted() {
		return getStateHelper().get(Properties.SCROLLING)!=null;
	}

	public String getContentURL() {
		return getContentURL(null);
	}

	public String getContentURL(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.CONTENT_URL);
	}

	public void setContentURL(String contentURL) {
		 getStateHelper().put(Properties.CONTENT_URL, contentURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "contentURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isContentURLSetted() {
		return getStateHelper().get(Properties.CONTENT_URL)!=null;
	}

}
