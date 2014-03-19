/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.util;

import org.rcfaces.core.internal.contentAccessor.ContentFamilies.ContentTypeImpl;
import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISVGContentFamily extends IContentFamily {

    public static final IContentFamily SVG = new ContentTypeImpl("svg");
}
