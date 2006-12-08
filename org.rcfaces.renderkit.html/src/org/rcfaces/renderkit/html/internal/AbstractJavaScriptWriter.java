/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.FacesException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.codec.JavascriptCodec;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptWriter implements IJavaScriptWriter {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractJavaScriptWriter.class);

    private static final String LF = "\n";

    public IJavaScriptWriter writeSymbol(String symbol) throws WriterException {
        int idx = symbol.indexOf('.');
        if (idx >= 0) {
            write(convertSymbol(symbol.substring(0, idx)));
            write('.');

            symbol = symbol.substring(idx + 1);
        }

        write(convertSymbol(symbol));

        return this;
    }

    public IJavaScriptWriter writeCall(String object, String symbol)
            throws WriterException {
        if (object == null) {
            throw new FacesException("Can not call a method without object !");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Write call object='" + object + "' symbol='" + symbol
                    + "'.");
        }

        write(convertSymbol(object));
        write('.');

        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeMethodCall(String symbol)
            throws WriterException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Write method call symbol='" + symbol + "'.");
        }

        isInitialized();

        if (LOG.isDebugEnabled()) {
            LOG.debug("  STEP2: Write method call symbol='" + symbol
                    + "' (componentVarName='" + getComponentVarName() + "')");
        }

        String componentVarName = getComponentVarName();
        if (componentVarName == null) {
            throw new FacesException("Component var name is not defined !");
        }
        write(convertSymbol(componentVarName));
        write('.');
        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    protected abstract void isInitialized() throws WriterException;

    public IJavaScriptWriter writeConstructor(String symbol)
            throws WriterException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Write constructor symbol='" + symbol + "'.");
        }

        write("new ");
        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeBoolean(boolean value) throws WriterException {
        if (value) {
            return write("true");
        }

        return write("false");
    }

    public IJavaScriptWriter writeNull() throws WriterException {
        return write("null");
    }

    public IJavaScriptWriter writeln(String string) throws WriterException {
        return write(string).writeln();
    }

    public IJavaScriptWriter writeln() throws WriterException {
        return write(LF);
    }

    public IJavaScriptWriter writeInt(int value) throws WriterException {
        return write(String.valueOf(value));
    }

    public IJavaScriptWriter writeString(String s) throws WriterException {
        if (s == null) {
            return writeNull();
        }

        int l = s.length();
        if (l < 1) {
            return write("\"\"");
        }

        char sep = '\"';
        if (s.indexOf(sep) >= 0 && s.indexOf('\'') < 0) {
            sep = '\'';
        }

        write(sep);
        return JavascriptCodec.writeJavaScript(this, s, sep).write(sep);
    }

    public IHtmlRenderContext getHtmlRenderContext() {
        return getHtmlComponentRenderContext().getHtmlRenderContext();
    }

    protected abstract String convertSymbol(String symbol);

}
