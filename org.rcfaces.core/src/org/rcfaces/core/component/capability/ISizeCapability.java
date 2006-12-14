/*
 * $Id$
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
public interface ISizeCapability {

    /**
     * Returns a string value (as specified by CSS) for the width of the component.
     * @return width
     */
    String getWidth();

    /**
     * Sets a string value (as specified by CSS) for the width of the component.
     * @param width width
     */
    void setWidth(String width);

    /**
     * Returns a string value (as specified by CSS) for the height of the component.
     * @return height
     */
    String getHeight();

    /**
     * Sets a string value (as specified by CSS) for the height of the component.
     * @param height height
     */
    void setHeight(String height);
}
