package org.rcfaces.core.component.capability;

import org.rcfaces.core.model.ICriteriaConfig;
import org.rcfaces.core.model.ICriteriaConfigResult;

/**
 * 
 * @author Oeuillot Olivier
 * 
 */
public interface ICriteriaManagerCapability {
	ICriteriaContainer[] listCriteriaContainers();

	void setCriteriaContainers(ICriteriaContainer[] containers);

	ICriteriaConfigResult processCriteriaConfig();

	ICriteriaConfigResult processCriteriaConfig(ICriteriaConfig[] configs);
}
