package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

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
	IVisibilityCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.lineBreak";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"styleClass","visible","hiddenMode","rendered"}));
	}

	public LineBreakComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public LineBreakComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, null, hiddenMode)).intValue());
		
	}

	public final Boolean getVisibleState(FacesContext facesContext) {


				if (engine.isPropertySetted(Properties.VISIBLE)==false) {
					return null;
				}
				
				return Boolean.valueOf(isVisible(facesContext));
			
	}

	public final java.lang.String getStyleClass() {
		return getStyleClass(null);
	}

	public final java.lang.String getStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.STYLE_CLASS, facesContext);
	}

	public final void setStyleClass(java.lang.String styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final void setStyleClass(ValueBinding styleClass) {
		engine.setProperty(Properties.STYLE_CLASS, styleClass);
	}

	public final int getHiddenMode() {
		return getHiddenMode(null);
	}

	public final int getHiddenMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
	}

	public final void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final void setHiddenMode(ValueBinding hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public final boolean isVisible() {
		return isVisible(null);
	}

	public final boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, false, facesContext);
	}

	public final void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final void setVisible(ValueBinding visible) {
		engine.setProperty(Properties.VISIBLE, visible);
	}

	public final Boolean getVisibleState() {


				return getVisibleState(null);
			
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
