package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IClosableCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.internal.tools.TabbedPaneTools;
import org.rcfaces.core.component.capability.IPreSelectionEventCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import javax.faces.context.FacesContext;
import org.rcfaces.core.component.TabComponent;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.iterator.ITabIterator;
import org.rcfaces.core.component.CardBoxComponent;
import java.util.Set;
import java.util.Arrays;

/**
 * <p>The tabbedPane Component provides a way to show more information on a single page. It can download the data asynchronously via AJAX.</p>
 * <p>The tabbedPane Component has the following capabilities :
 * <ul>
 * <li>ICloseEventCapability</li>
 * <li>IClosableCapability</li>
 * <li>IPreSelectionEventCapability</li>
 * <li>IShowValueCapability</li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/TabbedPaneComponent.html">tabbedPane</a> renderer is linked to the <a href="/jsdocs/index.html?f_tabbedPane.html" target="_blank">f_tabbedPane</a> javascript class. f_tabbedPane extends f_cardBox</p>
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
 * <td width="33%">f_tabbedPane</td>
 * <td width="50%">Defines styles for the wrapper element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_tabbedPane_title</td>
 * <td width="50%">Defines styles for the TABLE element </td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_tabbedPane_content</td>
 * <td width="50%">Defines styles for the DIV element</td>
 * </tr>
 * <tr  style="text-align:left">
 * <td width="33%">f_tabbedPane_tab</td>
 * <td width="50%">Defines styles for the DIV element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class TabbedPaneComponent extends CardBoxComponent implements 
	ICloseEventCapability,
	IClosableCapability,
	IPreSelectionEventCapability,
	IShowValueCapability {

	private static final Log LOG = LogFactory.getLog(TabbedPaneComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabbedPane";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CardBoxComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"closeListener","showValue","closable","preSelectionListener"}));
	}

	public TabbedPaneComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TabbedPaneComponent(String componentId) {
		this();
		setId(componentId);
	}

	public ITabIterator listTabs() {


				return TabbedPaneTools.listTabs(this);
			
	}

	public TabComponent getSelectedTab() {


				return (TabComponent)getSelectedCard();
			
	}

	public TabComponent getSelectedTab(FacesContext facesContext) {


				return (TabComponent)getSelectedCard(facesContext);
			
	}

	public void select(TabComponent tab) {


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

	public boolean isClosable() {
		return isClosable(null);
	}

	/**
	 * See {@link #isClosable() isClosable()} for more details
	 */
	public boolean isClosable(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.CLOSABLE, false, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "closable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClosableSetted() {
		return engine.isPropertySetted(Properties.CLOSABLE);
	}

	public void setClosable(boolean closable) {
		engine.setProperty(Properties.CLOSABLE, closable);
	}

	public final void addPreSelectionListener(org.rcfaces.core.event.IPreSelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removePreSelectionListener(org.rcfaces.core.event.IPreSelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listPreSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.IPreSelectionListener.class);
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

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
