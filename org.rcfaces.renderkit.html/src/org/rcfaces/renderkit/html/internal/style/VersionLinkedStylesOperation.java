/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.io.IOException;
import java.util.Map;

import org.rcfaces.core.internal.content.IOperationContentLoader;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.AbstractStyleOperation;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class VersionLinkedStylesOperation extends AbstractStyleOperation
        implements ICssOperation {
    private static final String REVISION = "$Revision$";

    public VersionLinkedStylesOperation() {
        setName("Versioning linked styles");
    }

    public String filter(Map applicationParameters,
            IResourceLoaderFactory resourceLoaderFactory, ICssParser cssParser,
            String styleSheetURL, String styleSheetContent,
            IParserContext mergeContext,
            IOperationContentLoader operationContentLoader) throws IOException {
        return cssParser.normalizeBuffer(applicationParameters,
                resourceLoaderFactory, styleSheetURL, styleSheetContent,
                mergeContext, operationContentLoader, false);
    }
}
