/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ISelectionCardinalityCapability extends ICardinality {

    int getSelectionCardinality();

    void setSelectionCardinality(int selectionCardinality);
}
