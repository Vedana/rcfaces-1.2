/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentAccessorRegistry {
    IContentAccessorHandler[] listContentAccessorHandlers(IContentType type);
}
