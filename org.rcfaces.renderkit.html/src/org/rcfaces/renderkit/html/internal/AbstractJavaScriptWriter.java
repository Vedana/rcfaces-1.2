/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.FacesException;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.codec.JavascriptCodec;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptWriter implements IJavaScriptWriter {
    private static final String REVISION = "$Revision$";

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
        write(convertSymbol(object));
        write('.');

        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeMethodCall(String symbol)
            throws WriterException {
        isInitialized();

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

    protected abstract String convertSymbol(String symbol);

}
