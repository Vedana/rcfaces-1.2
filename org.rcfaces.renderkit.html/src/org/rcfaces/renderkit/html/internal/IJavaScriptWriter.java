/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

public interface IJavaScriptWriter {

    IJavaScriptRenderContext getJavaScriptRenderContext();

    String getComponentVarName();

    void setComponentVarName(String varName);

    /*
     * Peut ne pas être implementée !
     */
    IHtmlComponentRenderContext getHtmlComponentRenderContext();

    IHtmlRenderContext getHtmlRenderContext();

    /*
     * Peut ne pas �tre implement�e !
     */
    IHtmlWriter getWriter();

    FacesContext getFacesContext();

    IJavaScriptWriter ensureInitialization() throws WriterException;

    IJavaScriptWriter write(String string) throws WriterException;

    IJavaScriptWriter writeln(String string) throws WriterException;

    IJavaScriptWriter writeln() throws WriterException;

    IJavaScriptWriter write(char c) throws WriterException;

    IJavaScriptWriter writeInt(long value) throws WriterException;

    IJavaScriptWriter writeDouble(double value) throws WriterException;

    IJavaScriptWriter writeNumber(Number value) throws WriterException;

    IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
            throws WriterException;

    IJavaScriptWriter writeSymbol(String symbol) throws WriterException;

    IJavaScriptWriter writeCall(String object, String symbol)
            throws WriterException;

    IJavaScriptWriter writeMethodCall(String symbol) throws WriterException;

    IJavaScriptWriter writeConstructor(String symbol) throws WriterException;

    IJavaScriptWriter writeString(String s) throws WriterException;

    void addRequestedModule(String moduleName);

    String allocateString(String text) throws WriterException;

    IJavaScriptWriter writeBoolean(boolean value) throws WriterException;

    IJavaScriptWriter writeNull() throws WriterException;

    IObjectLiteralWriter writeObjectLiteral(boolean writeNullIfEmpty) throws WriterException;

    void end() throws WriterException;

    boolean isOpened();
}
