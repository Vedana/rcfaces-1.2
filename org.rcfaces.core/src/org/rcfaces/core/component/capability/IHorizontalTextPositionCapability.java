/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A string value specifying the position of the text in the component :
 * <ul>
 * <li>left</li>
 * <li>right</li>
 * </ul>
 * 
 * @author Fred
 * 
 */
public interface IHorizontalTextPositionCapability {

    int RIGHT_POSITION = 0x08;

    int LEFT_POSITION = 0x10;

    int DEFAULT_POSITION = RIGHT_POSITION;

    int UNKNOWN_POSITION = 0;

    /**
     * Returns an int value specifying the position of the text in the
     * component.
     * 
     * @return 0x08:right|0x10:left
     */
    int getTextPosition();

    /**
     * Sets an int value specifying the position of the text in the component.
     * 
     * @param position
     *            0x00:right|0x10:left
     */
    void setTextPosition(int position);
}
