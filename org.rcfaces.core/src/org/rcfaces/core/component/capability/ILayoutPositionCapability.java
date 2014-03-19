/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILayoutPositionCapability {
    Number getLeft();

    void setLeft(Number left);

    Number getRight();

    void setRight(Number right);

    Number getTop();

    void setTop(Number top);

    Number getBottom();

    void setBottom(Number bottom);

    Number getHorizontalCenter();

    void setHorizontalCenter(Number horizontalCenter);

    Number getVerticalCenter();

    void setVerticalCenter(Number verticalCenter);
}
