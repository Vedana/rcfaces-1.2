/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdditionalProvider {
    Object getAdditionalValues();

    void setAdditionalValues(Object additionalValues);

    int getAdditionalValuesCount();

    Object getFirstAdditionalValue();

    Object[] listAdditionalValues();
}
