/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlContentAccessorFactory extends ContentAccessorFactory {
    private static final String REVISION = "$Revision$";

    public static IContentAccessor createFromStyleSheet(
            IHtmlRenderContext htmlRenderContext, String contentURL,
            IContentType contentType) {

        if (htmlRenderContext == null) {
            htmlRenderContext = HtmlRenderContext.getRenderContext(null);
        }

        String imageURL = htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetURI(contentURL, false);

        return createAccessor(imageURL, contentType, null, null);
    }
}
