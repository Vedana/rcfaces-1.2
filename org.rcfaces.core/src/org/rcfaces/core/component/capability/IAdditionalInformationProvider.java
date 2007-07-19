/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdditionalInformationProvider {
    Object getAdditionalInformationValues();

    void setAdditionalInformationValues(Object additionalInformationValues);

    int getAdditionalInformationValuesCount();

    Object getFirstAdditionalInformationValue();

    Object[] listAdditionalInformationValues();
}
