/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A string value specifying the position of the text in the component :
 * <ul>
 * <li> left </li>
 * <li> right </li>
 * <li> top </li>
 * <li> bottom </li>
 * </ul>
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITextPositionCapability extends
        IHorizontalTextPositionCapability {

    int TOP_POSITION = 0x20;

    int BOTTOM_POSITION = 0x40;
}
