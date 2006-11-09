/*
 * $Id$
 */

package org.rcfaces.core.internal.images;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFiltredImageAccessor extends IContentAccessor {
    String getFilter();
}