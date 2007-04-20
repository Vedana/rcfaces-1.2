/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHeightCapability {

    /**
     * Returns a string value (as specified by CSS) for the height of the
     * component.
     * 
     * @return height
     */
    String getHeight();

    /**
     * Sets a string value (as specified by CSS) for the height of the
     * component.
     * 
     * @param height
     *            height
     */
    void setHeight(String height);
}
