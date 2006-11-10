/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

public interface IHorizontalTextPositionCapability {
    
    int RIGHT_POSITION = 0x00;

    int LEFT_POSITION = 0x10;

    int DEFAULT_POSITION = RIGHT_POSITION;

    int getTextPosition();

    void setTextPosition(int position);
}
