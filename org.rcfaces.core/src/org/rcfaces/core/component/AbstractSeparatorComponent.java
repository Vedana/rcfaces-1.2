package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.HiddenModeConverter;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractSeparatorComponent extends CameliaBaseComponent implements 
	IVisibilityCapability,
	IHiddenModeCapability,
	ISeparatorComponent {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"visible","hiddenMode"}));
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
		return engine.getIntProperty(Properties.HIDDEN_MODE,0, facesContext);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
