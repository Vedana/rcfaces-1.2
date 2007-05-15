/*
 * $Id$
 */
package org.rcfaces.core.lang.provider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpansionProvider {
    Object getExpandedValues();

    void setExpandedValues(Object expandedValues);

    int getExpandedValuesCount();

    Object[] listExpandedValues();

}
