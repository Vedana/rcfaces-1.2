/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISubmittedExternalValue {
    void setSubmittedExternalValue(Object value);

    Object getSubmittedExternalValue();

    boolean isSubmittedValueSetted();
}
