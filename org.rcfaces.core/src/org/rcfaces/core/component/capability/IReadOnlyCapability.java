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
     * 
     */
    boolean isReadOnly();

    /**
     * 
     */
    void setReadOnly(boolean readOnly);
}
