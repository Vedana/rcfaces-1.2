package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IClosableCapability;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.iterator.ITabIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.TabbedPaneTools;

/**
 * <p>The tabbedPane Component provides a way to show more information on a single page. It can download the data asynchronously via AJAX.</p>
 * <p>The tabbedPane Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class TabbedPaneComponent extends CardBoxComponent implements 
	ICloseEventCapability,
	IClosableCapability,
	IShowValueCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabbedPane";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CardBoxComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"showValue","closable","closeListener"}));
	}

	public TabbedPaneComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabbedPaneComponent(String componentId) {
		this();
		setId(componentId);
	}

	public ITabIterator listTabs() {


				return TabbedPaneTools.listTabs(this);				
			
	}

	public TabComponent getSelectedTab() {


				return (TabComponent)getSelectedCard();
			
	}

	public TabComponent getSelectedTab(FacesContext facesContext) {


				return (TabComponent)getSelectedCard(facesContext);
			
	}

	public void select(TabComponent tab) {


				super.select(tab);
			
	}

	public final void addCloseListener(org.rcfaces.core.event.ICloseListener listener) {
		addFacesListener(listener);
	}

	public final void removeCloseListener(org.rcfaces.core.event.ICloseListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCloseListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICloseListener.class);
	}

	public boolean isClosable() {
		return isClosable(null);
	}

	/**
	 * See {@link #isClosable() isClosable()} for more details
	 */
	public boolean isClosable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLOSABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "closable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClosableSetted() {
		return engine.isPropertySetted(Properties.CLOSABLE);
	}

	public void setClosable(boolean closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	public java.lang.Object getShowValue() {
		return getShowValue(null);
	}

	/**
	 * See {@link #getShowValue() getShowValue()} for more details
	 */
	public java.lang.Object getShowValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.SHOW_VALUE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "showValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowValueSetted() {
		return engine.isPropertySetted(Properties.SHOW_VALUE);
	}

	public void setShowValue(java.lang.Object showValue) {
		engine.setProperty(Properties.SHOW_VALUE, showValue);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
