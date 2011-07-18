package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.ICriteriaContainer;
import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.component.capability.ICriteriaValuesCapability;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.lang.provider.ICriteriaProvider;
import org.rcfaces.core.model.ICriteriaConfig;
import org.rcfaces.core.model.ICriteriaConfigResult;

/**
 * 
 * @author Olivier Oeuillot
 * 
 */
public class CriteriaTools extends CollectionTools {

	private static final IValuesAccessor CRITERIA_PROVIDER_VALUES_ACCESSOR = new IValuesAccessor() {
		public int getCount(Object selectionProvider) {
			return ((ICriteriaProvider) selectionProvider)
					.getCriteriaValuesCount();
		}

		public Object getFirst(Object selectionProvider, Object refValues) {
			return ((ICriteriaProvider) selectionProvider)
					.getFirstCriteriaValue();
		}

		public Object[] listValues(Object selectionProvider, Object refValues) {
			return convertToObjectArray(((ICriteriaProvider) selectionProvider)
					.getCriteriaValues());
		}

		public Object getAdaptedValues(Object value) {
			return value;
		}

		public void setAdaptedValues(Object selectionProvider,
				Object selectedValues) {
			((ICriteriaProvider) selectionProvider)
					.setCriteriaValues(selectedValues);
		}

		public Object getComponentValues(UIComponent component) {
			return ((ICriteriaValuesCapability) component).getCriteriaValues();
		}

		public void setComponentValues(UIComponent component, Object values) {
			((ICriteriaValuesCapability) component).setCriteriaValues(values);
		}

		public Class getComponentValuesType(FacesContext facesContext,
				UIComponent component) {
			return ((ICriteriaValuesCapability) component)
					.getCriteriaValuesType(facesContext);
		}
	};
	private static final IAllValuesProvider CRITERIA_ALL_VALUES_PROVIDER = new IAllValuesProvider() {

		public List listAllValues(UIComponent component) {

			ICriteriaContainer container = (ICriteriaContainer) component;

			ICriteriaConfigResult configResult = container.getCriteriaManager()
					.processCriteriaConfig();

			CriteriaItem[] items = configResult
					.getAvailableCriteriaItems(container);

			if (items == null || items.length == 0) {
				return Collections.emptyList();
			}

			List values = new ArrayList(items.length);
			for (int i = 0; i < items.length; i++) {
				values.add(items[i].getValue());
			}

			return values;
		}
	};

	public static void selectCriterion(FacesContext facesContext,
			ICriteriaContainer component, Object criteriaValue) {
		select((UIComponent) component, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				criteriaValue);
	}

	public static void selectAllCriteria(FacesContext facesContext,
			ICriteriaContainer component) {
		selectAll((UIComponent) component, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				CRITERIA_ALL_VALUES_PROVIDER);
	}

	public static void deselectCriterion(FacesContext facesContext,
			ICriteriaContainer component, Object criteriaValue) {
		deselect((UIComponent) component, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				criteriaValue);
	}

	public static void deselectAllCriteria(FacesContext facesContext,
			ICriteriaContainer component) {
		deselectAll((UIComponent) component, CRITERIA_PROVIDER_VALUES_ACCESSOR);
	}

	public static int getCount(Object criteriaValues) {
		IValuesAccessor valuesAccessor = getValuesAccessor(criteriaValues,
				ICriteriaProvider.class, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				true, true);
		if (valuesAccessor == null) {
			return 0;
		}
		return valuesAccessor.getCount(criteriaValues);
	}

	public static Object getFirst(Object criteriaValues) {
		IValuesAccessor valuesAccessor = getValuesAccessor(criteriaValues,
				ICriteriaProvider.class, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				true, true);
		if (valuesAccessor == null) {
			return null;
		}
		return valuesAccessor.getFirst(criteriaValues, null);
	}

	public static Object[] listValues(Object criteriaValues) {
		IValuesAccessor valuesAccessor = getValuesAccessor(criteriaValues,
				ICriteriaProvider.class, CRITERIA_PROVIDER_VALUES_ACCESSOR,
				true, true);
		if (valuesAccessor == null) {
			return EMPTY_VALUES;
		}
		return valuesAccessor.listValues(criteriaValues, null);
	}

	public static ICriteriaContainer[] getCriteriaColumns(
			FacesContext facesContext, UIComponent component,
			IComponentEngine engine, String propertiesName) {

		return (ICriteriaContainer[]) ComponentTools.listChildren(facesContext,
				component, engine, ICriteriaContainer.class, propertiesName);
	}

	public static void setCriteriaColumns(FacesContext facesContext,
			UIComponent component, IComponentEngine engine,
			ICriteriaContainer[] children, String propertiesName) {

		UIComponent[] ch2 = null;

		if (children instanceof UIComponent[]) {
			ch2 = (UIComponent[]) children;

		} else {
			ch2 = new UIComponent[children.length];
			System.arraycopy(children, 0, ch2, 0, children.length);
		}

		ComponentTools.setChildren(component, engine, ICriteriaContainer.class,
				ch2, propertiesName);

	}

	public static CriteriaItem[] listAvailableCriteriaItems(
			ICriteriaContainer container, ICriteriaConfig[] configs) {
		ICriteriaConfigResult result = container.getCriteriaManager()
				.processCriteriaConfig(configs);

		return result.getAvailableCriteriaItems(container);
	}

	public static ICriteriaConfigResult processCriteriaConfig(
			ICriteriaManagerCapability manager, ICriteriaConfig[] configs) {

		GridCriteriaConfigResult result = new GridCriteriaConfigResult(manager,
				configs);

		return result;
	}

}
