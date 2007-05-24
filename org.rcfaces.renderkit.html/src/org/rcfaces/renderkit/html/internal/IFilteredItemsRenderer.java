/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFilteredItemsRenderer {
    void encodeFilteredItems(IJavaScriptWriter writer,
            IFilterCapability component, IFilterProperties filterProperties,
            int maxResultNumber) throws WriterException;

}
