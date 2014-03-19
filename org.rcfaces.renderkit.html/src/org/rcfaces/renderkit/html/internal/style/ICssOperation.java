/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import java.io.IOException;

import org.rcfaces.core.internal.style.IStyleOperation;
import org.rcfaces.core.internal.style.IStyleParser;
import org.rcfaces.core.internal.style.IStyleParser.IParserContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssOperation extends IStyleOperation {

    String filter(IStyleParser cssParser, String styleSheetURL,
            String styleSheetContent, IParserContext parserContext)
            throws IOException;
}
