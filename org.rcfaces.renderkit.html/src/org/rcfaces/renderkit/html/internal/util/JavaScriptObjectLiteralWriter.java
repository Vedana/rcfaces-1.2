/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptObjectLiteralWriter implements IObjectLiteralWriter {

    private final IJavaScriptWriter parent;

    private final boolean writeNullIfEmpty;

    private boolean firstProperty = true;

    public JavaScriptObjectLiteralWriter(IJavaScriptWriter parent,
            boolean writeNullIfEmpty) {
        this.parent = parent;
        this.writeNullIfEmpty = writeNullIfEmpty;
    }

    public IJavaScriptWriter getParent() {
        return parent;
    }

    public IJavaScriptWriter writeProperty(String propertyName)
            throws WriterException {

        if (firstProperty) {
            firstProperty = false;
            parent.write('{');

        } else {
            parent.write(',');
        }

        parent.write(propertyName);

        parent.write(':');

        return parent;
    }

    public IJavaScriptWriter writeSymbol(String symbol) throws WriterException {

        String convertedSymbol = parent.getJavaScriptRenderContext()
                .convertSymbol(null, symbol);

        return writeProperty(convertedSymbol);
    }

    public IJavaScriptWriter end() throws WriterException {
        if (firstProperty) {
            if (writeNullIfEmpty == false) {
                return parent.write("{}");
            }

            return parent.writeNull();
        }

        return parent.write('}');
    }
}
