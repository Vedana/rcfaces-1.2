/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import org.rcfaces.core.component.capability.IAnchoredPositionCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAnchoredPositionSettings extends IAnchoredPositionCapability {

    boolean isLeftPositionSetted();

    boolean isRightPositionSetted();

    boolean isTopPositionSetted();

    boolean isBottomPositionSetted();
}
