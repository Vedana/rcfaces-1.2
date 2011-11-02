/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.item;

import org.rcfaces.core.internal.contentAccessor.IResourceKeyParticipant;
import org.rcfaces.core.item.IClientDataItem;
import org.rcfaces.core.item.ISelectItemGroup;
import org.rcfaces.core.item.IServerDataItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface INodeItem extends ISelectItemGroup, IClientDataItem,
        IServerDataItem, IResourceKeyParticipant {
    String getTargetId();

    boolean isRendered();

    boolean isSelectable();

    String getAlternateText();
}
