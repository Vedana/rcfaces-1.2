/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITextDirectionCapability {

    int LEFT_TO_RIGHT_TEXT_DIRECTION = 0;

    int RIGHT_LEFT_TEXT_DIRECTION = 1;

    int DEFAULT_TEXT_DIRECTION = LEFT_TO_RIGHT_TEXT_DIRECTION;

    int getTextDirection();

    void setTextDirection(int textDirection);
}
