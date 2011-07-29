package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.DataModel;

import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import org.rcfaces.core.internal.capability.ICriteriaContainer;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.model.IComponentRefModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.ISelectedCriteria;

/**
 * 
 * @author Olivier Oeuillot
 */
public class GridCriteriaSelectedResult extends AbstractCriteriaSelectedResult {

	public GridCriteriaSelectedResult(IGridComponent gridComponent,
			ISelectedCriteria[] configs) {
		super((ICriteriaManagerCapability) gridComponent, configs);
	}

	@Override
	protected void fillDatas(List<Object> result,
			Map<ICriteriaConfiguration, CriteriaItem[]> criteriaItemsByContainer) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		IGridComponent gridComponent = (IGridComponent) getCriteriaManager();

		DataModel dataModel = gridComponent.getDataModelValue();

		IComponentRefModel componentRefModel = (IComponentRefModel) AbstractCameliaRenderer
				.getAdapter(IComponentRefModel.class, dataModel, gridComponent);

		if (componentRefModel != null) {
			componentRefModel.setComponent((UIComponent) gridComponent);
		}

		if (gridComponent instanceof IFilterCapability) {
			IFilterProperties filtersMap = ((IFilterCapability) gridComponent)
					.getFilterProperties();

			IFiltredModel filtredDataModel = (IFiltredModel) AbstractCameliaRenderer
					.getAdapter(IFiltredModel.class, dataModel, gridComponent);
			if (filtersMap != null) {
				if (filtredDataModel != null) {
					filtredDataModel.setFilter(filtersMap);
				}
			}
		}

		ISelectedCriteria[] selectedCriteria = listSelectedCriteria();

		Set<ICriteriaContainer> criteriaContainers = new HashSet<ICriteriaContainer>(
				Arrays.asList(getCriteriaManager().listCriteriaContainers()));

		Set<Object>[] selectedValues = new Set[selectedCriteria.length];
		Set<Object>[] possibleValues = new Set[selectedCriteria.length];
		for (int i = 0; i < possibleValues.length; i++) {
			possibleValues[i] = new HashSet<Object>();
			selectedValues[i] = selectedCriteria[i].listSelectedValues();

			criteriaContainers.remove(selectedCriteria[i].getConfig()
					.getCriteriaContainer());
		}

		ICriteriaConfiguration[] notSelectedContainers = new ICriteriaConfiguration[criteriaContainers
				.size()];
		int j = 0;
		for (Iterator<ICriteriaContainer> it = criteriaContainers.iterator(); it
				.hasNext();) {
			ICriteriaContainer cc = it.next();

			notSelectedContainers[j++] = cc.getCriteriaConfiguration();
		}

		try {
			next_row: for (int idx = 0;; idx++) {
				gridComponent.setRowIndex(idx);

				if (gridComponent.isRowAvailable() == false) {
					break;
				}

				for (int i = 0; i < selectedCriteria.length; i++) {
					ISelectedCriteria sc = selectedCriteria[i];

					Object dataValue = getDataValue(facesContext,
							gridComponent, sc.getConfig());

					if (selectedValues[i].contains(dataValue) == false) {
						continue next_row;
					}

					possibleValues[i].add(dataValue);
				}

				for (int i = 0; i < notSelectedContainers.length; i++) {
					Object dataValue = getDataValue(facesContext,
							gridComponent, notSelectedContainers[i]);

					possibleValues[i].add(dataValue);
				}

				result.add(gridComponent.getRowData());
			}

		} finally {
			gridComponent.setRowIndex(-1);
		}

		for (int i = 0; i < selectedCriteria.length; i++) {
			ISelectedCriteria sc = selectedCriteria[i];

			Converter labelConverter = sc.getConfig().getLabelConverter();
			UIComponent component = (UIComponent) sc.getConfig();

			Set<Object> values = possibleValues[i];
			List<CriteriaItem> criteriaItems = new ArrayList<CriteriaItem>(
					values.size());

			for (Iterator<Object> it = values.iterator(); it.hasNext();) {
				Object criteriaValue = it.next();

				String criteriaLabel = ValuesTools.convertValueToString(
						criteriaValue, labelConverter, component, facesContext);

				CriteriaItem criteriaItem = new CriteriaItem();
				criteriaItem.setLabel(criteriaLabel);
				criteriaItem.setValue(criteriaValue);
			}

			Collections.sort(criteriaItems, new Comparator<CriteriaItem>() {

				public int compare(CriteriaItem o1, CriteriaItem o2) {

					return o1.getLabel().compareTo(o2.getLabel());
				}
			});

			CriteriaItem[] criteriaItemsArray = criteriaItems
					.toArray(new CriteriaItem[criteriaItems.size()]);

			criteriaItemsByContainer.put(sc.getConfig(), criteriaItemsArray);
		}
	}

	private Object getDataValue(FacesContext facesContext,
			IGridComponent gridComponent, ICriteriaConfiguration config) {

		Object dataValue = null;
		if (config.isCriteriaValueSetted()) {
			return config.getCriteriaValue();
		}

		ICriteriaContainer container = config.getCriteriaContainer();
		if (container instanceof ValueHolder) {
			dataValue = ((ValueHolder) container).getValue();
		}

		return dataValue;
	}
}
