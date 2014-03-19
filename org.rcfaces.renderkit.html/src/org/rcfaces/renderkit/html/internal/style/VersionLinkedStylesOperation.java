/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.io.IOException;

import org.rcfaces.core.internal.style.AbstractStyleOperation;
import org.rcfaces.core.internal.style.IStyleParser;
import org.rcfaces.core.internal.style.IStyleParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class VersionLinkedStylesOperation extends AbstractStyleOperation
        implements ICssOperation {

    public VersionLinkedStylesOperation() {
        setName("Versioning linked styles");
    }

    public String filter(IStyleParser cssParser,
            String styleSheetURL, String styleSheetContent,
            IParserContext parserContext) throws IOException {
        return cssParser.normalizeBuffer(styleSheetURL,
                styleSheetContent, parserContext);
    }
}
