package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Collection;

/**
 * <p>The lineBreak Component is the &lt;BR&gt; HTML equivalent.</p>
 * <p>It is used often when simple HTML is not desirable : for example if a part of a page is loaded via AJAX it might be easier to have only a jsf tree memory represantation.</p>
 * <p>The lineBreak Component has the following capability :
 * <ul>
 * <li>Visibility</li>
 * </ul>
 * </p>
 */
public class LineBreakComponent extends CameliaBaseComponent implements 
	IStyleClassCapability,
	IVisibilityCapability,
	IHiddenModeCapability {

	private static final Log LOG = LogFactory.getLog(LineBreakComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.lineBreak";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaBaseComponent.BEHAVIOR_EVENT_NAMES;

	public LineBreakComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LineBreakComponent(String componentId) {
		this();
		setId(componentId);
	}

	public void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	public Boolean getVisibleState(FacesContext facesContext) {


			if (isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	/**
	 * See {@link #getStyleClass() getStyleClass()} for more details
	 */
	public java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.STYLE_CLASS);
	}

	/**
	 * Returns <code>true</code> if the attribute "styleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isStyleClassSetted() {
		return getStateHelper().get(Properties.STYLE_CLASS)!=null;
	}

	public void setStyleClass(java.lang.String styleClass) {
		getStateHelper().put(Properties.STYLE_CLASS, styleClass);
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.VISIBLE, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return getStateHelper().get(Properties.VISIBLE)!=null;
	}

	public void setVisible(boolean visible) {
		getStateHelper().put(Properties.VISIBLE, visible);
	}

	public Boolean getVisibleState() {


			return getVisibleState(null);
		
	}

	public int getHiddenMode() {
		return getHiddenMode(null);
	}

	/**
	 * See {@link #getHiddenMode() getHiddenMode()} for more details
	 */
	public int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.HIDDEN_MODE, IHiddenModeCapability.DEFAULT_HIDDEN_MODE);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return getStateHelper().get(Properties.HIDDEN_MODE)!=null;
	}

	public void setHiddenMode(int hiddenMode) {
		getStateHelper().put(Properties.HIDDEN_MODE, hiddenMode);
	}

}
