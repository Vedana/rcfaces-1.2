package org.rcfaces.core.internal.capability;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.lang.provider.ISelectionProvider;

/**
 * 
 * @author Oeuillot
 * 
 */
public interface ICriteriaConfiguration extends ISelectionProvider,
		ISelectionCardinalityCapability {
	ICriteriaContainer getCriteriaContainer();

	Converter getCriteriaConverter();

	boolean isCriteriaValueSetted();

	Object getCriteriaValue();

	int getCriteriaCardinality();
}
