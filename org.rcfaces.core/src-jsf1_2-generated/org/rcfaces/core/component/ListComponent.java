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
import org.rcfaces.core.component.capability.IMultipleSelectCapability;

/**
 * <p>The list Component is based on the standard HTML tag &lt;SELECT&gt;.</p>
 * <p>The list Component has the following capabilities :
 * <ul>
 * <li>IMultipleSelectCapability</li>
 * <li>IDoubleClickEventCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ListComponent.html">list</a> renderer is linked to the <a href="/jsdoc/index.html?f_list.html">f_list</a> javascript class. f_list extends f_abstractList, fa_selectionProvider<String[]></p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee" " width="50%">Description</td>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td bgcolor="#ffffff" width="33%">f_list</td>
 * <td width="50%">Defines styles for the wrapper SELECT element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class ListComponent extends ComboComponent implements 
	IMultipleSelectCapability,
	IDoubleClickEventCapability {

	private static final Log LOG = LogFactory.getLog(ListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.list";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ComboComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"multipleSelect","doubleClickListener","rowNumber"}));
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
