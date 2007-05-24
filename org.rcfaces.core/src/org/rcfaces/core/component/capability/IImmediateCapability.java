/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImmediateCapability {

    /**
     * Returns a boolean value indicating that this component's value must be
     * converted and validated immediately (that is, during Apply Request Values
     * phase), rather than waiting until Process Validations phase.
     * 
     * @return boolean
     */
    boolean isImmediate();

    /**
     * Sets a boolean value indicating that this component's value must be
     * converted and validated immediately (that is, during Apply Request Values
     * phase), rather than waiting until Process Validations phase.
     * 
     * @param immediate
     *            boolean
     */
    void setImmediate(boolean immediate);
}
