package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IMenuCapability;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IBorderCapability;

public class ComponentsGridComponent extends AbstractDataComponent implements 
	IMenuCapability,
	IBorderCapability,
	IBorderTypeCapability,
	IScrollableCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsGrid";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractDataComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"rowCountVar","verticalScrollPosition","var","value","rows","horizontalScrollPosition","columnStyleClass","first","border","borderType","rowStyleClass","rowIndexVar"}));
	}

	public ComponentsGridComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public ComponentsGridComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final void setRows(ValueBinding rows) {


			super.setValueBinding("rows", rows);
		
	}

	public final void setFirst(ValueBinding first) {


				super.setValueBinding("first", first);
			
	}

	public final void setVar(ValueBinding var) {


				super.setValueBinding("var", var);
			
	}

	public final void setValue(ValueBinding value) {


				super.setValueBinding("value", value);
//				clearCachedValue();
			
	}

	public final IMenuComponent getMenu(String menuId) {


		return MenuTools.getMenu(this, menuId);
		
	}

	public final IMenuComponent getMenu() {


		return MenuTools.getMenu(this);
		
	}

	public final IMenuIterator listMenus() {


		return MenuTools.listMenus(this);
		
	}

	public final boolean isBorder() {
		return isBorder(null);
	}

	public final boolean isBorder(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	public final void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final void setBorder(ValueBinding border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public final java.lang.String getBorderType() {
		return getBorderType(null);
	}

	public final java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	public final void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final void setBorderType(ValueBinding borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public final java.lang.String getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	public final java.lang.String getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HORIZONTAL_SCROLL_POSITION, facesContext);
	}

	public final void setHorizontalScrollPosition(java.lang.String horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public final void setHorizontalScrollPosition(ValueBinding horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public final java.lang.String getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	public final java.lang.String getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_SCROLL_POSITION, facesContext);
	}

	public final void setVerticalScrollPosition(java.lang.String verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public final void setVerticalScrollPosition(ValueBinding verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public final String getRowCountVar() {
		return getRowCountVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the number of rows.
	 * @return variable name
	 */
	public final String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public final void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public final void setRowCountVar(ValueBinding rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowCountVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public final String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	/**
	 * Returns a string value specifying the name of the variable receiving the index of the current row.
	 * @return variable name
	 */
	public final String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public final void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public final void setRowIndexVar(ValueBinding rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowIndexVar" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getRowStyleClass() {
		return getRowStyleClass(null);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getRowStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_STYLE_CLASS, facesContext);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param rowStyleClass list of CSS style classes
	 */
	public final void setRowStyleClass(String rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param rowStyleClass list of CSS style classes
	 */
	public final void setRowStyleClass(ValueBinding rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowStyleClassSetted() {
		return engine.isPropertySetted(Properties.ROW_STYLE_CLASS);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getColumnStyleClass() {
		return getColumnStyleClass(null);
	}

	/**
	 * Returns a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @return list of CSS style classes
	 */
	public final String getColumnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLUMN_STYLE_CLASS, facesContext);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param columnStyleClass list of CSS style classes
	 */
	public final void setColumnStyleClass(String columnStyleClass) {
		engine.setProperty(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param columnStyleClass list of CSS style classes
	 */
	public final void setColumnStyleClass(ValueBinding columnStyleClass) {
		engine.setProperty(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
	}

	/**
	 * Returns <code>true</code> if the attribute "columnStyleClass" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isColumnStyleClassSetted() {
		return engine.isPropertySetted(Properties.COLUMN_STYLE_CLASS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
