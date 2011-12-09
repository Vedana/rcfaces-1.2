/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAnchoredPositionCapability {
    int getLeftPosition();

    void setLeftPosition(int leftPosition);

    int getRightPosition();

    void setRightPosition(int rightPosition);

    int getTopPosition();

    void setTopPosition(int topPosition);

    int getBottomPosition();

    void setBottomPosition(int bottomPosition);

}
