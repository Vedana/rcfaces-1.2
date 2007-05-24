/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A string that indicates the (composed) key used to execute an action from the
 * keyboard.
 * <p>
 * [Shift|Alt|Ctrl] + &lt;Any key&gt;
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAcceleratorKeyCapability {

    /**
     * Returns a string that indicates the (composed) key used to execute an
     * action from the keyboard.
     * 
     * @return [Shift|Alt|Ctrl] + &lt;Any key&gt;
     */
    String getAcceleratorKey();

    /**
     * Sets a string that indicates the (composed) key used to execute an action
     * from the keyboard.
     * 
     * @param acceleratorKey
     *            [Shift|Alt|Ctrl] + &lt;Any key&gt;
     */
    void setAcceleratorKey(String acceleratorKey);
}
