package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClosableCapability;
import org.rcfaces.core.component.capability.ICloseEventCapability;
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
	IClosableCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabbedPane";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CardBoxComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"closable","closeListener"}));
	}

	public TabbedPaneComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabbedPaneComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final ITabIterator listTabs() {


				return TabbedPaneTools.listTabs(this);				
			
	}

	public final TabComponent getSelectedTab() {


				return (TabComponent)getSelectedCard();
			
	}

	public final TabComponent getSelectedTab(FacesContext facesContext) {


				return (TabComponent)getSelectedCard(facesContext);
			
	}

	public final void select(TabComponent tab) {


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

	public final boolean isClosable() {
		return isClosable(null);
	}

	/**
	 * See {@link #isClosable() isClosable()} for more details
	 */
	public final boolean isClosable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLOSABLE, false, facesContext);
	}

	public final void setClosable(boolean closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	/**
	 * See {@link #setClosable(boolean) setClosable(boolean)} for more details
	 */
	public final void setClosable(ValueBinding closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
