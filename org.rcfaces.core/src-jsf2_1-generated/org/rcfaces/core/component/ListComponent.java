package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.ComboComponent;
import org.rcfaces.core.component.capability.IDoubleClickEventCapability;
import java.util.Collection;
import org.rcfaces.core.component.capability.IMultipleSelectCapability;

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

	private static final Log LOG = LogFactory.getLog(ListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.list";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ComboComponent.BEHAVIOR_EVENT_NAMES;

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
		return (Boolean)getStateHelper().eval(Properties.MULTIPLE_SELECT, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "multipleSelect" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isMultipleSelectSetted() {
		return getStateHelper().get(Properties.MULTIPLE_SELECT)!=null;
	}

	public void setMultipleSelect(boolean multipleSelect) {
		getStateHelper().put(Properties.MULTIPLE_SELECT, multipleSelect);
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
		return (Integer)getStateHelper().eval(Properties.ROW_NUMBER, 0);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public void setRowNumber(int rowNumber) {
		 getStateHelper().put(Properties.ROW_NUMBER, rowNumber);
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
		return getStateHelper().get(Properties.ROW_NUMBER)!=null;
	}

}
