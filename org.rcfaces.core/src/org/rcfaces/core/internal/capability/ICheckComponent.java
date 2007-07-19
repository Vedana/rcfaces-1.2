/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckComponent {

    /**
     * 
     */
    void check(Object rowValue);

    /**
     * Checks all of the items in the receiver. If the receiver is
     * single-checked, do nothing.
     */
    void checkAll();

    void uncheck(Object rowValue);

    /**
     * Uncheks all checked items in the receiver.
     */
    void uncheckAll();

}
