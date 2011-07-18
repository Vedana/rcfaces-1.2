package org.rcfaces.core.model;

import java.util.Set;

import org.rcfaces.core.component.capability.ICriteriaContainer;

/**
 * 
 * @author Oeuillot
 */
public interface ICriteriaConfig {
	ICriteriaContainer getContainer();

	Set listCriteriaValues();
}
