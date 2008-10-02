/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.item;

import org.rcfaces.core.item.ISelectItemGroup;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface INodeItem extends ISelectItemGroup {
    String getTargetId();

    boolean isRendered();
}
