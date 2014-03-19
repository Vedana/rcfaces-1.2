package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.IClosableCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICloseEventCapability;
import org.rcfaces.core.internal.tools.TabbedPaneTools;
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
import java.util.Collection;

/**
 * <p>The tabbedPane Component provides a way to show more information on a single page. It can download the data asynchronously via AJAX.</p>
 * <p>The tabbedPane Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class TabbedPaneComponent extends CardBoxComponent implements 
	ICloseEventCapability,
	IClosableCapability,
	IShowValueCapability {

	private static final Log LOG = LogFactory.getLog(TabbedPaneComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.tabbedPane";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CardBoxComponent.BEHAVIOR_EVENT_NAMES;

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
		return (Boolean)getStateHelper().eval(Properties.CLOSABLE, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "closable" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isClosableSetted() {
		return getStateHelper().get(Properties.CLOSABLE)!=null;
	}

	public void setClosable(boolean closable) {
		getStateHelper().put(Properties.CLOSABLE, closable);
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

}