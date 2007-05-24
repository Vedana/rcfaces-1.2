/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Experimental : Do not use !
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClosableCapability {

    /**
     * Experimental : Do not use !
     * 
     * @return <code>true</code> is the component can be closed.
     */
    boolean isClosable();

    /**
     * Experimental : Do not use !
     * 
     * @param closable
     */
    void setClosable(boolean closable);
}
