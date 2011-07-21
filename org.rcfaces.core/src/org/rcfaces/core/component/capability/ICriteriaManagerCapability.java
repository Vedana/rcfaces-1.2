package org.rcfaces.core.component.capability;

import org.rcfaces.core.internal.capability.ICriteriaContainer;
import org.rcfaces.core.model.ISelectedCriteria;
import org.rcfaces.core.model.ICriteriaSelectedResult;

/**
 * 
 * @author Oeuillot Olivier
 * 
 */
public interface ICriteriaManagerCapability {
	ICriteriaContainer[] listCriteriaContainers();

	void setCriteriaContainers(ICriteriaContainer[] containers);

	ICriteriaSelectedResult processSelectedCriteria();

	ICriteriaSelectedResult processSelectedCriteria(ISelectedCriteria[] configs);
}
