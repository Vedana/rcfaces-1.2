/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectionProvider {
    Object getSelection();

    void setSelection(Object selection);
}
