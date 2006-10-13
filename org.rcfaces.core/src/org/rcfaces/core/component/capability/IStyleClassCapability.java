/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStyleClassCapability {

    /**
     * Specify the style class of the component.
     */
    void setStyleClass(String cssClass);

    /**
     * Returns the style class of the component.
     */
    String getStyleClass();

}