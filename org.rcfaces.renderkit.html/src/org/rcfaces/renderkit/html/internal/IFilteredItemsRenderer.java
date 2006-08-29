/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IFilteredItemsRenderer {
    void encodeFilteredItems(IJavaScriptWriter writer,
            IFilterCapability component, IFilterProperties filterProperties, int maxResultNumber)
            throws WriterException;

}
