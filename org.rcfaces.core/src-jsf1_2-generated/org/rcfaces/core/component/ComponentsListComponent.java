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
import org.rcfaces.core.component.capability.IHeadingZoneCapability;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.IMenuComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.capability.IHeadingLevelCapability;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.IMenuCapability;

/**
 * <p>The componentslist component Shows the components for each data with pagination.</p>
 * <p>The componentslist Component has the following capabilities :
 * <ul>
 * <li>IMenuCapability</li>
 * <li>IBorderCapability</li>
 * <li>IBorderTypeCapability</li>
 * <li>IScrollableCapability</li>
 * <li>IShowValueCapability </li>
 * </ul>
 * </p>
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/ComponentsListComponent.html"> componentsList </a> renderer is link to the <a href="/jsdocs/index.html?f_componentsGrid.html" target="_blank">f_componentslist</a> javascript class. f_componentslist extends f_component, fa_pagedComponent</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_componentslist</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper DIV element. Contains a Table</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_componentslist_cell</td>
 * <td id="ermvsh" width="50%">Defines styles for the wrapper TD element for the row.</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class ComponentsListComponent extends AbstractDataComponent implements 
	IMenuCapability,
	IBorderCapability,
	IBorderTypeCapability,
	IScrollableCapability,
	IShowValueCapability,
	IHeadingZoneCapability,
	IHeadingLevelCapability {

	private static final Log LOG = LogFactory.getLog(ComponentsListComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.componentsList";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractDataComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"headingLevel","showValue","columnStyleClass","verticalScrollPosition","borderType","horizontalScrollPosition","rowStyleClass","rowCountVar","border","rowIndexVar","headingZone","columnNumber"}));
	}

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
		return engine.getBoolProperty(Properties.BORDER, true, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "border" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderSetted() {
		return engine.isPropertySetted(Properties.BORDER);
	}

	public void setBorder(boolean border) {
		engine.setProperty(Properties.BORDER, border);
	}

	public java.lang.String getBorderType() {
		return getBorderType(null);
	}

	/**
	 * See {@link #getBorderType() getBorderType()} for more details
	 */
	public java.lang.String getBorderType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.BORDER_TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "borderType" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isBorderTypeSetted() {
		return engine.isPropertySetted(Properties.BORDER_TYPE);
	}

	public void setBorderType(java.lang.String borderType) {
		engine.setProperty(Properties.BORDER_TYPE, borderType);
	}

	public int getHorizontalScrollPosition() {
		return getHorizontalScrollPosition(null);
	}

	/**
	 * See {@link #getHorizontalScrollPosition() getHorizontalScrollPosition()} for more details
	 */
	public int getHorizontalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.HORIZONTAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "horizontalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHorizontalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.HORIZONTAL_SCROLL_POSITION);
	}

	public void setHorizontalScrollPosition(int horizontalScrollPosition) {
		engine.setProperty(Properties.HORIZONTAL_SCROLL_POSITION, horizontalScrollPosition);
	}

	public int getVerticalScrollPosition() {
		return getVerticalScrollPosition(null);
	}

	/**
	 * See {@link #getVerticalScrollPosition() getVerticalScrollPosition()} for more details
	 */
	public int getVerticalScrollPosition(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.VERTICAL_SCROLL_POSITION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "verticalScrollPosition" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isVerticalScrollPositionSetted() {
		return engine.isPropertySetted(Properties.VERTICAL_SCROLL_POSITION);
	}

	public void setVerticalScrollPosition(int verticalScrollPosition) {
		engine.setProperty(Properties.VERTICAL_SCROLL_POSITION, verticalScrollPosition);
	}

	public java.lang.Object getShowValue() {
		return getShowValue(null);
	}

	/**
	 * See {@link #getShowValue() getShowValue()} for more details
	 */
	public java.lang.Object getShowValue(javax.faces.context.FacesContext facesContext) {
		return engine.getProperty(Properties.SHOW_VALUE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "showValue" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isShowValueSetted() {
		return engine.isPropertySetted(Properties.SHOW_VALUE);
	}

	public void setShowValue(java.lang.Object showValue) {
		engine.setProperty(Properties.SHOW_VALUE, showValue);
	}

	public boolean isHeadingZone() {
		return isHeadingZone(null);
	}

	/**
	 * See {@link #isHeadingZone() isHeadingZone()} for more details
	 */
	public boolean isHeadingZone(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.HEADING_ZONE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingZone" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingZoneSetted() {
		return engine.isPropertySetted(Properties.HEADING_ZONE);
	}

	public void setHeadingZone(boolean headingZone) {
		engine.setProperty(Properties.HEADING_ZONE, headingZone);
	}

	public java.lang.String getHeadingLevel() {
		return getHeadingLevel(null);
	}

	/**
	 * See {@link #getHeadingLevel() getHeadingLevel()} for more details
	 */
	public java.lang.String getHeadingLevel(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.HEADING_LEVEL, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "headingLevel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isHeadingLevelSetted() {
		return engine.isPropertySetted(Properties.HEADING_LEVEL);
	}

	public void setHeadingLevel(java.lang.String headingLevel) {
		engine.setProperty(Properties.HEADING_LEVEL, headingLevel);
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
		String s = engine.getStringProperty(Properties.ROW_COUNT_VAR, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the number of rows.
	 * @param rowCountVar variable name
	 */
	public void setRowCountVar(String rowCountVar) {
		engine.setProperty(Properties.ROW_COUNT_VAR, rowCountVar);
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
		return engine.isPropertySetted(Properties.ROW_COUNT_VAR);
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
		String s = engine.getStringProperty(Properties.ROW_INDEX_VAR, facesContext);
		return s;
	}

	/**
	 * Sets a string value specifying the name of the variable receiving the index of the current row.
	 * @param rowIndexVar variable name
	 */
	public void setRowIndexVar(String rowIndexVar) {
		engine.setProperty(Properties.ROW_INDEX_VAR, rowIndexVar);
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
		return engine.isPropertySetted(Properties.ROW_INDEX_VAR);
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
		return engine.getIntProperty(Properties.COLUMN_NUMBER, 0, facesContext);
	}

	/**
	 * Sets an int value holding the width of the component in characters : x characters translates into y pixels width.
	 * @param columnNumber width in characters
	 */
	public void setColumnNumber(int columnNumber) {
		engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
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
		return engine.isPropertySetted(Properties.COLUMN_NUMBER);
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
		String s = engine.getStringProperty(Properties.ROW_STYLE_CLASS, facesContext);
		return s;
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when the row is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param rowStyleClass list of CSS style classes
	 */
	public void setRowStyleClass(String rowStyleClass) {
		engine.setProperty(Properties.ROW_STYLE_CLASS, rowStyleClass);
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
		return engine.isPropertySetted(Properties.ROW_STYLE_CLASS);
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
		String s = engine.getStringProperty(Properties.COLUMN_STYLE_CLASS, facesContext);
		return s;
	}

	/**
	 * Sets a space-separated list of CSS style class(es) to be applied when this column is rendered. This value will be passed through as the "class" attribute on generated markup.
	 * @param columnStyleClass list of CSS style classes
	 */
	public void setColumnStyleClass(String columnStyleClass) {
		engine.setProperty(Properties.COLUMN_STYLE_CLASS, columnStyleClass);
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
		return engine.isPropertySetted(Properties.COLUMN_STYLE_CLASS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
