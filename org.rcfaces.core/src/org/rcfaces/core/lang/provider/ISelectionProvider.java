/*
 * $Id$
 */
package org.rcfaces.core.lang.provider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectionProvider {
    Object getSelectedValues();

    void setSelectedValues(Object selectedValues);

    int getSelectedValuesCount();

    Object getFirstSelectedValue();

    Object[] listSelectedValues();
}
