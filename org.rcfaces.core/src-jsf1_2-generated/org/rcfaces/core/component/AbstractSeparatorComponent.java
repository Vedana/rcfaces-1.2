package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.internal.converter.HiddenModeConverter;
import org.rcfaces.core.component.ISeparatorComponent;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractSeparatorComponent extends CameliaBaseComponent implements 
	IVisibilityCapability,
	IHiddenModeCapability,
	IUnlockedClientAttributesCapability,
	ISeparatorComponent {

	private static final Log LOG = LogFactory.getLog(AbstractSeparatorComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"unlockedClientAttributeNames","visible","hiddenMode"}));
	}


	public Boolean getVisibleState(FacesContext facesContext) {


			if (engine.isPropertySetted(Properties.VISIBLE)==false) {
				return null;
			}
			
			return Boolean.valueOf(isVisible(facesContext));
		
	}

	public void setHiddenMode(String hiddenMode) {


			setHiddenMode(((Integer)HiddenModeConverter.SINGLETON.getAsObject(null, this, hiddenMode)).intValue());
		
	}

	public boolean isVisible() {
		return isVisible(null);
	}

	/**
	 * See {@link #isVisible() isVisible()} for more details
	 */
	public boolean isVisible(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.VISIBLE, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "visible" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVisibleSetted() {
		return engine.isPropertySetted(Properties.VISIBLE);
	}

	public void setVisible(boolean visible) {
		engine.setProperty(Properties.VISIBLE, visible);
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
		return engine.getIntProperty(Properties.HIDDEN_MODE,IHiddenModeCapability.DEFAULT_HIDDEN_MODE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "hiddenMode" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHiddenModeSetted() {
		return engine.isPropertySetted(Properties.HIDDEN_MODE);
	}

	public void setHiddenMode(int hiddenMode) {
		engine.setProperty(Properties.HIDDEN_MODE, hiddenMode);
	}

	public java.lang.String getUnlockedClientAttributeNames() {
		return getUnlockedClientAttributeNames(null);
	}

	/**
	 * See {@link #getUnlockedClientAttributeNames() getUnlockedClientAttributeNames()} for more details
	 */
	public java.lang.String getUnlockedClientAttributeNames(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "unlockedClientAttributeNames" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isUnlockedClientAttributeNamesSetted() {
		return engine.isPropertySetted(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES);
	}

	public void setUnlockedClientAttributeNames(java.lang.String unlockedClientAttributeNames) {
		engine.setProperty(Properties.UNLOCKED_CLIENT_ATTRIBUTE_NAMES, unlockedClientAttributeNames);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
