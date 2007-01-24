package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.ToolBarTools;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.iterator.IToolFolderIterator;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IInitEventCapability;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractBasicComponent;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolBarComponent extends AbstractBasicComponent implements 
	IInitEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolBar";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"initListener"}));
	}

	public ToolBarComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ToolBarComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final IToolFolderIterator listToolFolders() {


		return ToolBarTools.listToolFolders(this);
		
	}

	public final void addInitListener(org.rcfaces.core.event.IInitListener listener) {
		addFacesListener(listener);
	}

	public final void removeInitListener(org.rcfaces.core.event.IInitListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listInitListeners() {
		return getFacesListeners(org.rcfaces.core.event.IInitListener.class);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
