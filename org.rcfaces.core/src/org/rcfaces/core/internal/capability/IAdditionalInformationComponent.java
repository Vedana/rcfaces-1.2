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
     * Show the additional information associated to the item at the given
     * zero-relative index in the receiver. If the item at the index was already
     * selected, it remains selected. Indices that are out of range are ignored.
     * 
     * @param index
     *            the index of the additional information to show
     */
    void showAdditionalInformation(int index);

    /**
     * Show the additional information associated to the item at the given
     * zero-relative index in the receiver. If the item at the index was already
     * selected, it remains selected. Indices that are out of range are ignored.
     * 
     * @param index
     *            the indexes of additional informations to show
     */
    void showAdditionalInformation(int indexes[]);

    /**
     * Show all additional informations.
     */
    void showAllAdditionalInformations();

    /**
     * 
     */
    void hideAdditionalInformation(Object rowValue);

    /**
     * Hide the additional information associated to the item at the given
     * zero-relative index in the receiver. If the item at the index was already
     * selected, it remains selected. Indices that are out of range are ignored.
     * 
     * @param index
     *            the index of the additional information to hide
     */
    void hideAdditionalInformation(int index);

    /**
     * Hide the additional information associated to the item at the given
     * zero-relative index in the receiver. If the item at the index was already
     * selected, it remains selected. Indices that are out of range are ignored.
     * 
     * @param indexes
     *            the indexes of additional informations to hide
     */
    void hideAdditionalInformation(int indexes[]);

    /**
     * Hide all shown additional informations.
     */
    void hideAllAdditionalInformations();

    IAdditionalInformationIterator listAdditionalInformations();
}
