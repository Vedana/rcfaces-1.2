/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import org.rcfaces.core.component.iterator.IAdditionalInformationIterator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAdditionalInformationComponent {
    /**
     * 
     */
    void showAdditionalInformation(Object rowValue);

    /**
     * Show all additional informations.
     */
    void showAllAdditionalInformations();

    /**
     * 
     */
    void hideAdditionalInformation(Object rowValue);

    /**
     * Hide all shown additional informations.
     */
    void hideAllAdditionalInformations();

    IAdditionalInformationIterator listAdditionalInformations();
}
