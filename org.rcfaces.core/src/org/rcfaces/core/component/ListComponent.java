package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;
import org.rcfaces.core.internal.component.Properties;

/**
 * <p>The list Component is based on the standard HTML tag &lt;SELECT&gt;.</p>
 * <p>The list Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text ,&amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class ListComponent extends ComboComponent implements 
	IMultipleSelectCapability,
	IDoubleClickEventCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.list";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ComboComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"rowNumber","doubleClickListener","multipleSelect"}));
	}

	public ListComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ListComponent(String componentId) {
		this();
		setId(componentId);
	}

	public boolean isMultipleSelect() {
		return isMultipleSelect(null);
	}

	/**
	 * See {@link #isMultipleSelect() isMultipleSelect()} for more details
	 */
	public boolean isMultipleSelect(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.MULTIPLE_SELECT, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "multipleSelect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMultipleSelectSetted() {
		return engine.isPropertySetted(Properties.MULTIPLE_SELECT);
	}

	public void setMultipleSelect(boolean multipleSelect) {
		engine.setProperty(Properties.MULTIPLE_SELECT, multipleSelect);
	}

	public final void addDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		addFacesListener(listener);
	}

	public final void removeDoubleClickListener(org.rcfaces.core.event.IDoubleClickListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listDoubleClickListeners() {
		return getFacesListeners(org.rcfaces.core.event.IDoubleClickListener.class);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public int getRowNumber() {
		return getRowNumber(null);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public int getRowNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ROW_NUMBER, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public void setRowNumber(int rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowNumberSetted() {
		return engine.isPropertySetted(Properties.ROW_NUMBER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
