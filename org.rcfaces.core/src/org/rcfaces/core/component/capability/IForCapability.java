/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A component's id that express the link between the two components.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IForCapability {

    /**
     * Returns a component's id that express the link between the two
     * components.
     * 
     * @return id for a linked component
     */
    String getFor();

    /**
     * Sets a component's id that express the link between the two components.
     * 
     * @param forValue
     *            id for a linked component
     */
    void setFor(String forValue);
}
