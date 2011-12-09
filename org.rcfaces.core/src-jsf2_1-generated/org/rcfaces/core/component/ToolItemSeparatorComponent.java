package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.AbstractSeparatorComponent;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.ToolFolderComponent;
import java.util.Collection;
import org.rcfaces.core.component.capability.IAlternateTextCapability;

/**
 * <b>EXPERIMENTAL</b>
 */
public class ToolItemSeparatorComponent extends AbstractSeparatorComponent implements 
	IAlternateTextCapability {

	private static final Log LOG = LogFactory.getLog(ToolItemSeparatorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.toolItemSeparator";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractSeparatorComponent.BEHAVIOR_EVENT_NAMES;

	public ToolItemSeparatorComponent() {
		setRendererType(null);
	}

	public ToolItemSeparatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public ToolFolderComponent getToolFolder() {


		return (ToolFolderComponent)getParent();
		
	}

	public java.lang.String getAlternateText() {
		return getAlternateText(null);
	}

	/**
	 * See {@link #getAlternateText() getAlternateText()} for more details
	 */
	public java.lang.String getAlternateText(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ALTERNATE_TEXT);
	}

	/**
	 * Returns <code>true</code> if the attribute "alternateText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAlternateTextSetted() {
		return getStateHelper().get(Properties.ALTERNATE_TEXT)!=null;
	}

	public void setAlternateText(java.lang.String alternateText) {
		getStateHelper().put(Properties.ALTERNATE_TEXT, alternateText);
	}

}
