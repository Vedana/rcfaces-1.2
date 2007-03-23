/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.component.EditableValueHolder;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IEditableValueHolder extends EditableValueHolder {
    boolean isSubmittedValueSet();
}
