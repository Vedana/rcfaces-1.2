package org.rcfaces.core.component.capability;

import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.lang.provider.ICriteriaProvider;
import org.rcfaces.core.model.ICriteriaConfig;

/**
 * 
 * @author Oeuillot
 * 
 */
public interface ICriteriaContainer extends ICriteriaProvider {
	ICriteriaManagerCapability getCriteriaManager();

	void selectCriterion(Object criterionValue);

	void selectAllCriteria();

	void deselectCriterion(Object criterionValue);

	void deselectAllCriteria();

	CriteriaItem[] listAvailableCriteriaItems();

	CriteriaItem[] listAvailableCriteriaItems(ICriteriaConfig[] configs);
}
