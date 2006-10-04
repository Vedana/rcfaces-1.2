package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IScrollableCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.internal.tools.MenuTools;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.AbstractDataComponent;
import org.rcfaces.core.component.capability.IBorderCapability;

public class ComponentsGridComponent extends AbstractDataComponent implements 
	IMenuCapability,
	IBorderCapability,
	IBorderTypeCapability,
	IScrollableCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsGrid";


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

	public final String getRowCountVar() {
		return getRowCountVar(null);
	}

	public final String getRowCountVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
	}

	public final void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	public final void setRowCountVar(ValueBinding rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
	}

	public final boolean isRowCountVarSetted() {
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
	}

	public final String getRowIndexVar() {
		return getRowIndexVar(null);
	}

	public final String getRowIndexVar(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
	}

	public final void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	public final void setRowIndexVar(ValueBinding rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
	}

	public final boolean isRowIndexVarSetted() {
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
	}

	public final String getRowStyleClass() {
		return getRowStyleClass(null);
	}

	public final String getRowStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_STYLE_CLASS, facesContext);
	}

	public final void setRowStyleClass(String rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	public final void setRowStyleClass(ValueBinding rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
	}

	public final boolean isRowStyleClassSetted() {
		return engine.isPropertySetted(Properties.ROW_STYLE_CLASS);
	}

	public final String getColumnStyleClass() {
		return getColumnStyleClass(null);
	}

	public final String getColumnStyleClass(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.COLUMN_STYLE_CLASS, facesContext);
	}

	public final void setColumnStyleClass(String columnStyleClass) {
		engine.setProperty(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
	}

	public final void setColumnStyleClass(ValueBinding columnStyleClass) {
		engine.setProperty(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
	}

	public final boolean isColumnStyleClassSetted() {
		return engine.isPropertySetted(Properties.COLUMN_STYLE_CLASS);
	}

	public void release() {
		super.release();
	}
}
