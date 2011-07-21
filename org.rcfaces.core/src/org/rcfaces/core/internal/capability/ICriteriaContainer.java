package org.rcfaces.core.internal.capability;

import org.rcfaces.core.component.capability.ICriteriaManagerCapability;

/**
 * 
 * @author Oeuillot
 * 
 */
public interface ICriteriaContainer {
	ICriteriaManagerCapability getCriteriaManager();

	ICriteriaConfiguration getCriteriaConfiguration();

	Object getValue();
}
