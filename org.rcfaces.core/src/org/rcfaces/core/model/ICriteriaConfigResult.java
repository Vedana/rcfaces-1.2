package org.rcfaces.core.model;

import java.util.List;

import org.rcfaces.core.component.capability.ICriteriaContainer;
import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.item.CriteriaItem;

/**
 * 
 * @author Oeuillot
 * 
 */
public interface ICriteriaConfigResult {

	ICriteriaManagerCapability getCriteriaManager();

	ICriteriaConfig[] getConfig();

	CriteriaItem[] getAvailableCriteriaItems(ICriteriaContainer container);

	int getResultCount();

	List getResult();
}
