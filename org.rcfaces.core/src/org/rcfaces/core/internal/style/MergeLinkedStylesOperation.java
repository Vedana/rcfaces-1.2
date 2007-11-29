/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import java.io.IOException;

import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.core.internal.style.CssParserFactory.ICssParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MergeLinkedStylesOperation extends AbstractStyleOperation {
    private static final String REVISION = "$Revision$";

    public MergeLinkedStylesOperation() {
        setName("Merge linked styles");
    }

    public String filter(IResourceLoaderFactory resourceLoaderFactory,
            ICssParser cssParser, String styleSheetURL,
            String styleSheetContent, IParserContext mergeContext)
            throws IOException {
        return cssParser.mergesBuffer(resourceLoaderFactory, styleSheetURL,
                styleSheetContent, mergeContext);
    }
}
