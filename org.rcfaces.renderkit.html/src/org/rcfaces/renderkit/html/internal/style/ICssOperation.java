/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.io.IOException;
import java.util.Map;

import org.rcfaces.core.internal.content.IOperationContentLoader;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.IStyleOperation;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssOperation extends IStyleOperation {

    String filter(Map applicationParameters,
            IResourceLoaderFactory resourceLoaderFactory, ICssParser cssParser,
            String styleSheetURL, String styleSheetContent,
            IParserContext mergeContext,
            IOperationContentLoader operationContentLoader) throws IOException;
}
