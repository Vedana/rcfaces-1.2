/*
 * $Id$
 */
package org.rcfaces.core.lang;

import org.rcfaces.core.internal.contentAccessor.ContentFamilies.ContentTypeImpl;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IContentFamily {

    public static final IContentFamily IMAGE = new ContentTypeImpl("image");

    public static final IContentFamily HELP = new ContentTypeImpl("help");

    public static final IContentFamily SCRIPT = new ContentTypeImpl("script");

    public static final IContentFamily STYLE = new ContentTypeImpl("style");

    public static final IContentFamily USER = new ContentTypeImpl("user");

    int getOrdinal();
}
