package org.rcfaces.core.internal.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
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
	protected void fillDatas(List<?> result,
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

		Set<Object>[] possibleValues = new Set[selectedCriteria.length];

		next_row: for (int idx = 0;; idx++) {
			gridComponent.setRowIndex(idx);

			if (gridComponent.isRowAvailable() == false) {
				break;
			}

			for (int i = 0; i < selectedCriteria.length; i++) {
				ISelectedCriteria sc = selectedCriteria[i];

				if (acceptCriteria(facesContext, gridComponent, sc) == false) {
					continue next_row;
				}

			}
		}
	}

	private boolean acceptCriteria(FacesContext facesContext,
			IGridComponent gridComponent, ISelectedCriteria sc) {
		// TODO Auto-generated method stub
		return false;
	}
}
