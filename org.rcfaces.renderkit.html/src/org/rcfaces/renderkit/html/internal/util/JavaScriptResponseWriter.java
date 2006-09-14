/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.16  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.15  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.14  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.13  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.12  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.11  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.10  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.9  2005/11/08 12:16:27  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.8  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.7  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.5  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.4  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.3  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.1  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.AbstractHtmlComponentlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.codec.JavascriptCodec;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptResponseWriter extends
        AbstractHtmlComponentlRenderContext implements IJavaScriptWriter {
    private static final String REVISION = "$Revision$";

    private final PrintWriter out;

    private String componentVarName;

    private Map strings;

    private boolean symbolsInitialized;

    private Map symbols;

    private IJavaScriptRenderContext javaScriptRenderContext;

    private ServletContext servletContext;

    public JavaScriptResponseWriter(FacesContext facesContext, PrintWriter out,
            UIComponent component, String componentId) {
        super(facesContext, component, componentId);

        this.out = out;
    }

    public JavaScriptResponseWriter(ServletContext servletContext,
            PrintWriter out) {
        super(null, null, null);

        this.out = out;
        this.servletContext = servletContext;
    }

    public IJavaScriptRenderContext getJavaScriptRenderContext() {
        if (javaScriptRenderContext == null) {
            javaScriptRenderContext = new JavaScriptRenderContext(
                    getFacesContext());
        }
        return javaScriptRenderContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#getComponentVarName()
     */
    public String getComponentVarName() {
        if (componentVarName != null) {
            return componentVarName;
        }

        componentVarName = getJavaScriptRenderContext().allocateVarName();

        return componentVarName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#write(java.lang.String)
     */
    public IJavaScriptWriter write(String string) {
        out.print(string);

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#writeln(java.lang.String)
     */
    public IJavaScriptWriter writeln(String string) {
        out.println(string);

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#writeln()
     */
    public IJavaScriptWriter writeln() {
        out.println();

        return this;
    }

    public IJavaScriptWriter ensureInitialization() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#write(char)
     */
    public IJavaScriptWriter write(char c) {
        out.write(c);

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#write(int)
     */
    public IJavaScriptWriter writeInt(int value) {
        write(String.valueOf(value));

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#end()
     */
    public void end() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#isOpened()
     */
    public boolean isOpened() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.IReleasable#release()
     */
    public void release() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#writeRaw(char[],
     *      int, int)
     */
    public IJavaScriptWriter writeRaw(char[] dst, int pos, int length) {
        out.write(dst, pos, length);

        return this;
    }

    public String allocateString(String string) throws WriterException {
        if (strings == null) {
            strings = new HashMap(8);

        } else {
            String varId = (String) strings.get(string);
            if (varId != null) {
                return varId;
            }
        }

        String varId = getJavaScriptRenderContext().allocateVarName();
        strings.put(string, varId);

        write("var ").write(varId).write('=').writeString(string).writeln(";");

        return varId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#getComponentRenderContext()
     */
    public final IHtmlComponentRenderContext getComponentRenderContext() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentRenderContext#getRenderContext()
     */
    public IRenderContext getRenderContext() {
        // Y en a pas !
        // Par contre, il ne faut pas envoyer d'exception,
        // car le RewritingURL l'appelle !
        return null;
        // throw new FacesException("Not supported !");
    }

    public IHtmlWriter getWriter() {
        throw new FacesException("Not supported !");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#addRequestedModule(java.lang.String)
     */
    public void addRequestedModule(String moduleName) {
    }

    public IJavaScriptWriter writeSymbol(String symbol) {
        int idx = symbol.indexOf('.');
        if (idx >= 0) {
            write(convertSymbol(symbol.substring(0, idx)));
            write('.');

            symbol = symbol.substring(idx + 1);
        }

        write(convertSymbol(symbol));

        return this;
    }

    public IJavaScriptWriter writeCall(String object, String symbol) {
        if (object != null) {
            write(convertSymbol(object));
            write('.');
        }
        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeMethodCall(String symbol) {
        write(convertSymbol(getComponentVarName()));
        write('.');
        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeConstructor(String symbol) {
        write("new ");
        write(convertSymbol(symbol));
        write('(');

        return this;
    }

    public IJavaScriptWriter writeBoolean(boolean value) {
        if (value) {
            return write("true");
        }

        return write("false");
    }

    public IJavaScriptWriter writeNull() {
        return write("null");
    }

    private String convertSymbol(String symbol) {
        if (symbolsInitialized == false) {
            symbolsInitialized = true;
            symbols = getSymbolsMap();
        }

        if (symbols == null) {
            return symbol;
        }

        String s = (String) symbols.get(symbol);
        if (s != null) {
            return s;
        }

        return symbol;
    }

    protected Map getSymbolsMap() {
        if (servletContext != null) {
            return JavaScriptRepositoryServlet.getSymbols(servletContext);
        }
        return JavaScriptRepositoryServlet.getSymbols(getFacesContext());
    }

    public IJavaScriptWriter writeString(String s) throws WriterException {
        if (s == null) {
            writeNull();
            return this;
        }

        int l = s.length();
        if (l < 1) {
            write("\"\"");
            return this;
        }

        char sep = '\"';
        if (s.indexOf(sep) >= 0 && s.indexOf('\'') < 0) {
            sep = '\'';
        }

        write(sep);
        return JavascriptCodec.writeJavaScript(this, s, sep).write(sep);
    }

    public IFile[] popRequiredFiles() {
        return null;
    }

    public void setComponentVarName(String varName) {
        this.componentVarName = varName;
    }

}