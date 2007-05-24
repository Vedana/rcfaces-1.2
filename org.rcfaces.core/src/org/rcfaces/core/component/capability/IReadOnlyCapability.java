/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IReadOnlyCapability {

    /**
     * Returns a boolean value indicating that this component will prohibit
     * changes by the user. The element may receive focus unless it has also
     * been disabled.
     * 
     * @return readOnly boolean property
     */
    boolean isReadOnly();

    /**
     * Sets a boolean value indicating that this component will prohibit changes
     * by the user. The element may receive focus unless it has also been
     * disabled.
     * 
     * @param readOnly
     *            readOnly boolean property
     */
    void setReadOnly(boolean readOnly);
}
