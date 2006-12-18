package org.rcfaces.core.component;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.TabbedPaneTools;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.capability.IClosableCapability;
import org.rcfaces.core.component.iterator.ITabIterator;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.TabComponent;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import java.util.HashSet;

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

	public final boolean isClosable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLOSABLE, false, facesContext);
	}

	public final void setClosable(boolean closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	public final void setClosable(ValueBinding closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
