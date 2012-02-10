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

/**
 * <p>The externalBox Component is a container based on the standard HTML tag &lt;IFrame&gt;.  .</p>
 * <p>The externalBox Component has the following capabilities :
 * <ul>
 * <li>ILoadEventCapability</li>
 * <li>IOverStyleClassCapability</li>
 * </ul>
 * </p>
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ExternalBoxComponent.html">externalBox</a> renderer is link to the <a href="/jsdocs/index.html?f_externalBox.html" target="_blank">f_externalBox</a> javascript class. f_externalBox extends f_component</p>
 * 
 * <p>Table of component style classes : </p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_externalBox</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper Frame element</td>
 * </tr>
 * 
 * 
 * </tbody>
 * </table>
 */
public class ExternalBoxComponent extends AbstractOutputComponent implements 
	ILoadEventCapability,
	IOverStyleClassCapability {

	private static final Log LOG = LogFactory.getLog(ExternalBoxComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.externalBox";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"scrolling","overStyleClass","contentURL","loadListener"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="contentURL";

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
		return engine.getStringProperty(Properties.OVER_STYLE_CLASS, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "overStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isOverStyleClassSetted() {
		return engine.isPropertySetted(Properties.OVER_STYLE_CLASS);
	}

	public void setOverStyleClass(java.lang.String overStyleClass) {
		engine.setProperty(Properties.OVER_STYLE_CLASS, overStyleClass);
	}

	public String getScrolling() {
		return getScrolling(null);
	}

	public String getScrolling(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.SCROLLING, facesContext);


return s;
	}

	public void setScrolling(String scrolling) {
		engine.setProperty(Properties.SCROLLING, scrolling);
	}

	/**
	 * Returns <code>true</code> if the attribute "scrolling" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isScrollingSetted() {
		return engine.isPropertySetted(Properties.SCROLLING);
	}

	public String getContentURL() {
		return getContentURL(null);
	}

	public String getContentURL(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.CONTENT_URL, facesContext);


return s;
	}

	public void setContentURL(String contentURL) {
		engine.setProperty(Properties.CONTENT_URL, contentURL);
	}

	/**
	 * Returns <code>true</code> if the attribute "contentURL" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isContentURLSetted() {
		return engine.isPropertySetted(Properties.CONTENT_URL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
