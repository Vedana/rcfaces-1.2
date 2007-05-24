/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A string that represents the alignement of the data in the component.
 * <ul>
 * <li> left </li>
 * <li> right </li>
 * <li> center </li>
 * </ul>
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAlignmentCapability {

    /**
     * Returns a string that represents the alignement of the data in the
     * component.
     * 
     * @return right|left|center
     */
    String getAlignment();

    /**
     * Sets a string that represents the alignement of the data in the
     * component.
     * 
     * @param textAlignment
     *            right|left|center
     */
    void setAlignment(String textAlignment);
}