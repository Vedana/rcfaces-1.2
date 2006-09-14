package org.rcfaces.core.component;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClosableCapability;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.component.iterator.ITabIterator;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.TabbedPaneTools;

public class TabbedPaneComponent extends CardBoxComponent implements 
	ICloseEventCapability,
	IClosableCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabbedPane";


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

	public void release() {
		super.release();
	}
}
