/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentType {

    IContentType IMAGE = new ContentTypeImpl("image");

    IContentType HELP = new ContentTypeImpl("help");

    IContentType SCRIPT = new ContentTypeImpl("script");

    IContentType STYLE = new ContentTypeImpl("style");

    IContentType USER = new ContentTypeImpl("user");
}
