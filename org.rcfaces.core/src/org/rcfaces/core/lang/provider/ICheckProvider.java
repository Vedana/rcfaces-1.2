/*
 * $Id$
 */
package org.rcfaces.core.lang.provider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckProvider {
    Object getCheckedValues();

    void setCheckedValues(Object checkedValues);

    int getCheckedValuesCount();

    Object getFirstCheckedValue();
}
