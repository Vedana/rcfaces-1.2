package org.rcfaces.core.internal.tools;

import java.util.Set;

import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import org.rcfaces.core.model.ISelectedCriteria;

/**
 * 
 * @author Olivier Oeuillot
 * 
 */
public class BasicSelectedCriteria implements ISelectedCriteria {

	private final ICriteriaConfiguration config;
	private final Set<Object> criteriaValues;

	public BasicSelectedCriteria(ICriteriaConfiguration config,
			Set<Object> values) {
		this.config = config;
		this.criteriaValues = values;
	}

	public ICriteriaConfiguration getConfig() {
		return config;
	}

	public Set<Object> listSelectedValues() {
		return criteriaValues;
	}

}