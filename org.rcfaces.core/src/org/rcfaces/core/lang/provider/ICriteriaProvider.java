/*
 * $Id$
 */
package org.rcfaces.core.lang.provider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICriteriaProvider {
	Object getCriteriaValues();

	void setCriteriaValues(Object criteriaValues);

	int getCriteriaValuesCount();

	Object getFirstCriteriaValue();

	Object[] listCriteriaValues();
}
