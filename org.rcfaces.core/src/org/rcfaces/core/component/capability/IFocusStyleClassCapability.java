/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFocusStyleClassCapability {

    /**
     * Specify the style class of the component.
     * @param cssClass style class 
     */
    void setFocusStyleClass(String cssClass);

    /**
     * Returns the style class of the component.
     * 
     * @return style class
     */
    String getFocusStyleClass();

}
