package org.rcfaces.core.component;

import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueType;
import org.rcfaces.core.component.AbstractConverterCommandComponent;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.MenuTools;
import org.rcfaces.core.internal.tools.CollectionTools.IComponentValueTypeCapability;
import org.rcfaces.core.component.iterator.IMenuItemIterator;
import org.rcfaces.core.component.capability.ICheckEventCapability;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.capability.ICheckComponent;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.internal.tools.CheckTools;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractMenuComponent extends AbstractConverterCommandComponent implements 
	IUnlockedClientAttributesCapability,
	ISelectionEventCapability,
	ICheckEventCapability,
	ICheckedValuesCapability,
	IReadOnlyCapability,
	ICheckComponent,
	IMenuComponent,
	IComponentValueTypeCapability {

	private static final Log LOG = LogFactory.getLog(AbstractMenuComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractConverterCommandComponent.BEHAVIOR_EVENT_NAMES;


	public IComponentValueType getComponentValueType() {


				return MenuTools.MENU_VALUE_TYPE;
			
	}

	public void check(Object value) {


				CheckTools.check(null, this, value);
			
	}

	public void checkAll() {


				CheckTools.checkAll(null, this);
			
	}

	public void uncheck(Object value) {


				CheckTools.uncheck(null, this, value);
			
	}

	public void uncheckAll() {


				CheckTools.uncheckAll(null, this);
			
	}

	public IMenuItemIterator listMenuItems() {


		return MenuTools.listMenuItems(this);
		
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final void addCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		addFacesListener(listener);
	}

	public final void removeCheckListener(org.rcfaces.core.event.ICheckListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listCheckListeners() {
		return getFacesListeners(org.rcfaces.core.event.ICheckListener.class);
	}

	public java.lang.Object getCheckedValues() {
		return getCheckedValues(null);
	}

	/**
	 * See {@link #getCheckedValues() getCheckedValues()} for more details
	 */
	public java.lang.Object getCheckedValues(javax.faces.context.FacesContext facesContext) {
		return getStateHelper().eval(Properties.CHECKED_VALUES);
	}

	/**
	 * Returns <code>true</code> if the attribute "checkedValues" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isCheckedValuesSetted() {
		return getStateHelper().get(Properties.CHECKED_VALUES)!=null;
	}

	public void setCheckedValues(java.lang.Object checkedValues) {
		getStateHelper().put(Properties.CHECKED_VALUES, checkedValues);
	}

	/**
	 * Return the type of the property represented by the {@link ValueExpression}, relative to the specified {@link javax.faces.context.FacesContext}.
	 */
	public Class getCheckedValuesType(javax.faces.context.FacesContext facesContext) {
		Object valueExpression=getStateHelper().get(Properties.CHECKED_VALUES);
		if ((valueExpression instanceof ValueExpression)==false) {
			return null;
		}
		if (facesContext==null) {
			facesContext=javax.faces.context.FacesContext.getCurrentInstance();
		}
		return ((ValueExpression)valueExpression).getType(facesContext.getELContext());
	}

	public Object getFirstCheckedValue() {


			return CheckTools.getFirst(getCheckedValues(), getValue());
		
	}

	public int getCheckedValuesCount() {


			return CheckTools.getCount(getCheckedValues());
		
	}

	public Object[] listCheckedValues() {


			return CheckTools.listValues(getCheckedValues(), getValue());
		
	}

	public boolean isReadOnly() {
		return isReadOnly(null);
	}

	/**
	 * See {@link #isReadOnly() isReadOnly()} for more details
	 */
	public boolean isReadOnly(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.READ_ONLY, false);
	}

	/**
	 * Returns <code>true</code> if the attribute "readOnly" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isReadOnlySetted() {
		return getStateHelper().get(Properties.READ_ONLY)!=null;
	}

	public void setReadOnly(boolean readOnly) {
		getStateHelper().put(Properties.READ_ONLY, readOnly);
	}

	/**
	 * Returns a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @return true if content is removed when shown
	 */
	public boolean isRemoveAllWhenShown() {
		return isRemoveAllWhenShown(null);
	}

	/**
	 * Returns a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @return true if content is removed when shown
	 */
	public boolean isRemoveAllWhenShown(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.REMOVE_ALL_WHEN_SHOWN, false);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	public void setRemoveAllWhenShown(boolean removeAllWhenShown) {
		 getStateHelper().put(Properties.REMOVE_ALL_WHEN_SHOWN, removeAllWhenShown);
	}

	/**
	 * Sets a boolean value specifying wether the content of the component must be remove before the listener is called and the component displayed.
	 * @param removeAllWhenShown true if content is to be removed when shown
	 */
	/**
	 * Returns <code>true</code> if the attribute "removeAllWhenShown" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRemoveAllWhenShownSetted() {
		return getStateHelper().get(Properties.REMOVE_ALL_WHEN_SHOWN)!=null;
	}

}
