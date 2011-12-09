package org.rcfaces.core.component;

import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.IMenuComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IMenuCapability;
import java.util.Collection;

/**
 * Shows the components for each data with pagination.
 */
public class ComponentsListComponent extends AbstractDataComponent implements 
	IMenuCapability,
	IBorderCapability,
	IBorderTypeCapability,
	IScrollableCapability,
	IShowValueCapability {

	private static final Log LOG = LogFactory.getLog(ComponentsListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsList";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractDataComponent.BEHAVIOR_EVENT_NAMES;

	public ComponentsListComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComponentsListComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public boolean isBorder() {
		return isBorder(null);
	}

	/**
	 * See {@link #isBorder() isBorder()} for more details
	 */
	public boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.BORDER, true);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return getStateHelper().get(Properties.BORDER)!=null;
	}

	public void setBorder(boolean border) {
		getStateHelper().put(Properties.BORDER, border);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.BORDER_TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return getStateHelper().get(Properties.BORDER_TYPE)!=null;
	}

	public void setBorderType(java.lang.String borderType) {
		getStateHelper().put(Properties.BORDER_TYPE, borderType);
	}

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.HORIZONTAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return getStateHelper().get(Properties.HORIZONTAL_SCROLL_POSITION)!=null;
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		getStateHelper().put(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.VERTICAL_SCROLL_POSITION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return getStateHelper().get(Properties.VERTICAL_SCROLL_POSITION)!=null;
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		getStateHelper().put(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public java.lang.Object getShowValue() {
		return getShowValue(null);
	}

	/**
	 * See {@link #getShowValue() getShowValue()} for more details
	 */
	public java.lang.Object getShowValue(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.SHOW_VALUE);
	}

	/**
	 * Returns <code>true</code> if the attribute "showValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowValueSetted() {
		return getStateHelper().get(Properties.SHOW_VALUE)!=null;
	}

	public void setShowValue(java.lang.Object showValue) {
		getStateHelper().put(Properties.SHOW_VALUE, showValue);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar() {
		return getRowCountVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
		 getStateHelper().put(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowCountVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowCountVarSetted() {
		return getStateHelper().get(Properties.ROW_COUNT_VAR)!=null;
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_INDEX_VAR);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
		 getStateHelper().put(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowIndexVarSetted() {
		return getStateHelper().get(Properties.ROW_INDEX_VAR)!=null;
	}

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public int getColumnNumber() {
		return getColumnNumber(null);
	}

	/**
	 * Returns an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @return width in characters
	 */
	public int getColumnNumber(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.COLUMN_NUMBER, 0);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	public void setColumnNumber(int columnNumber) {
		 getStateHelper().put(Properties.COLUMN_NUMBER, columnNumber);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	/**
	 * Returns <code>true</code> if the attribute "columnNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isColumnNumberSetted() {
		return getStateHelper().get(Properties.COLUMN_NUMBER)!=null;
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public String getRowStyleClass() {
		return getRowStyleClass(null);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public String getRowStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ROW_STYLE_CLASS);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param rowStyleClass list of CSS style classes
	 */
	public void setRowStyleClass(String rowStyleClass) {
		 getStateHelper().put(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param rowStyleClass list of CSS style classes
	 */
	/**
	 * Returns <code>true</code> if the attribute "rowStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRowStyleClassSetted() {
		return getStateHelper().get(Properties.ROW_STYLE_CLASS)!=null;
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public String getColumnStyleClass() {
		return getColumnStyleClass(null);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public String getColumnStyleClass(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.COLUMN_STYLE_CLASS);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param columnStyleClass list of CSS style classes
	 */
	public void setColumnStyleClass(String columnStyleClass) {
		 getStateHelper().put(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param columnStyleClass list of CSS style classes
	 */
	/**
	 * Returns <code>true</code> if the attribute "columnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isColumnStyleClassSetted() {
		return getStateHelper().get(Properties.COLUMN_STYLE_CLASS)!=null;
	}

}
