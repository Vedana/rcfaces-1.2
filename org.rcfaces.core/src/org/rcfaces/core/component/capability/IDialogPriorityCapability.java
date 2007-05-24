/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A int indicating the priority for this component. Permits to show a higher
 * priority dialog before a lower priority one.
 * 
 * @author Fred (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDialogPriorityCapability {

    int MAX_PRIORITY = 1000;

    int getDialogPriority();

    void setDialogPriority(int priority);
}
