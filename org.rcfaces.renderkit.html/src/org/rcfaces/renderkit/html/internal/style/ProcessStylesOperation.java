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
public class ProcessStylesOperation extends AbstractStyleOperation implements
        ICssOperation {

    public ProcessStylesOperation() {
        setName("Process styles");
    }

    public String filter(IStyleParser cssParser, String styleSheetURL,
            String styleSheetContent, IParserContext parserContext)
            throws IOException {

        parserContext.enableProcessRules();

        return cssParser.normalizeBuffer(styleSheetURL, styleSheetContent,
                parserContext);
    }
}
