/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentAccessorRegistry {
    IContentAccessorHandler[] listContentAccessorHandlers(IContentFamily type);
}
