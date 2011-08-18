package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import org.rcfaces.core.internal.capability.ICriteriaContainer;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.model.ICriteriaSelectedResult;
import org.rcfaces.core.model.ISelectedCriteria;

/**
 * 
 * @author Olivier Oeuillot
 * 
 */
public class CriteriaTools extends CollectionTools {

	private static final ISelectedCriteria[] SELECTED_CRITERIA_EMPTY_ARRAY = new ISelectedCriteria[0];

	private static final ICriteriaContainer[] CRITERIA_CONTAINER_EMPTY_ARRAY = new ICriteriaContainer[0];

	public static ICriteriaContainer[] getSelectedCriteriaColumns(
			FacesContext facesContext, UIComponent component,
			IComponentEngine engine, String propertiesName) {
		UIComponent[] childs = ComponentTools.listChildren(facesContext,
				component, engine, ICriteriaContainer.class, propertiesName);

		ICriteriaContainer[] criteriaContainers = new ICriteriaContainer[childs.length];
		System.arraycopy(childs, 0, criteriaContainers, 0, childs.length);

		return criteriaContainers;
	}

	public static void setSelectedCriteriaColumns(FacesContext facesContext,
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
			ICriteriaConfiguration configuration, ISelectedCriteria[] configs) {
		ICriteriaContainer container = configuration.getCriteriaContainer();

		ICriteriaSelectedResult result = container.getCriteriaManager()
				.processSelectedCriteria(configs);

		return result.getAvailableCriteriaItems(configuration);
	}

	public static ICriteriaSelectedResult processCriteriaConfig(
			ICriteriaManagerCapability manager, ISelectedCriteria[] configs) {

		if (manager instanceof IGridComponent) {
			ICriteriaSelectedResult result = new GridCriteriaSelectedResult(
					(IGridComponent) manager, configs);
			return result;
		}

		throw new UnsupportedOperationException("Not implemented");
	}

	public static ISelectedCriteria[] listSelectedCriteria(
			ICriteriaManagerCapability manager) {

		ICriteriaContainer[] criteriaContainers = manager
				.listCriteriaContainers();

		if (criteriaContainers == null || criteriaContainers.length == 0) {
			return SELECTED_CRITERIA_EMPTY_ARRAY;
		}

		List<ISelectedCriteria> configs = new ArrayList<ISelectedCriteria>();

		for (ICriteriaContainer container : criteriaContainers) {
			ICriteriaConfiguration configuration = container
					.getCriteriaConfiguration();
			if (configuration == null) {
				continue;
			}

			Object[] values = configuration.listSelectedValues();

			if (values == null || values.length == 0) {
				continue;
			}

			Set<Object> set = new HashSet<Object>(Arrays.asList(values));

			ISelectedCriteria selectedCriteria = new BasicSelectedCriteria(
					configuration, set);
			configs.add(selectedCriteria);
		}

		return (ISelectedCriteria[]) configs
				.toArray(new ISelectedCriteria[configs.size()]);
	}

	public static ICriteriaManagerCapability getCriteriaManager(
			ICriteriaContainer container) {

		for (UIComponent component = ((UIComponent) container).getParent(); component != null; component = component
				.getParent()) {

			if (component instanceof ICriteriaManagerCapability) {
				return (ICriteriaManagerCapability) component;
			}
		}

		return null;
	}

	public static ICriteriaConfiguration[] listCriteriaConfigurations(
			ICriteriaContainer container) {

		List<ICriteriaConfiguration> configurations = new ArrayList<ICriteriaConfiguration>();

		for (UIComponent component : ((UIComponent) container).getChildren()) {
			if (component instanceof ICriteriaConfiguration) {
				configurations.add((ICriteriaConfiguration) component);
			}
		}

		return (ICriteriaConfiguration[]) configurations
				.toArray(new ICriteriaConfiguration[configurations.size()]);
	}

	public static ICriteriaConfiguration getFirstCriteriaConfiguration(
			ICriteriaContainer container) {

		for (UIComponent component : ((UIComponent) container).getChildren()) {
			if (component instanceof ICriteriaConfiguration) {
				return (ICriteriaConfiguration) component;
			}
		}

		return null;
	}

	public static ICriteriaContainer getCriteriaContainer(
			ICriteriaConfiguration criteriaConfiguration) {

		for (UIComponent component = ((UIComponent) criteriaConfiguration)
				.getParent(); component != null; component = component
				.getParent()) {

			if (component instanceof ICriteriaContainer) {
				return (ICriteriaContainer) component;
			}
		}

		return null;
	}

	public static ICriteriaContainer[] listCriteriaContainers(
			FacesContext facesContext, UIComponent component) {

		List<ICriteriaContainer> list = new ArrayList<ICriteriaContainer>(
				component.getChildCount());

		for (Iterator<UIComponent> it = component.getChildren().iterator(); it
				.hasNext();) {
			UIComponent child = it.next();

			if ((child instanceof ICriteriaContainer) == false) {
				continue;
			}

			ICriteriaContainer criteriaContainer = (ICriteriaContainer) child;
			if (criteriaContainer.getCriteriaConfiguration() == null) {
				continue;
			}

			list.add(criteriaContainer);
		}

		if (list.isEmpty()) {
			return CRITERIA_CONTAINER_EMPTY_ARRAY;
		}

		return list.toArray(new ICriteriaContainer[list.size()]);
	}

	public static Object getDataValue(FacesContext facesContext,
				IGridComponent gridComponent, ICriteriaConfiguration config) {

		if (config.isCriteriaValueSetted()) {
			return config.getCriteriaValue();
		}

		ICriteriaContainer container = config.getCriteriaContainer();
		if (container instanceof ValueHolder) {
			ValueHolder valueHolder = (ValueHolder) container;

			Object dataValue = valueHolder.getValue();
			
			//dataValue = ((String) dataValue).replaceAll(",", "");
			if (dataValue != null) {
				Converter converter = valueHolder.getConverter();
				if (converter != null) {
					dataValue = converter.getAsString(facesContext,
							(UIComponent) container, dataValue);
				}
			}

			return dataValue;
		}

		return null;
	}
}
