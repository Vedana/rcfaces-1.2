/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IWidthCapability {

    /**
     * Returns a string value (as specified by CSS) for the width of the
     * component.
     * 
     * @return width
     */
    String getWidth();

    /**
     * Sets a string value (as specified by CSS) for the width of the component.
     * 
     * @param width
     *            width
     */
    void setWidth(String width);

}
