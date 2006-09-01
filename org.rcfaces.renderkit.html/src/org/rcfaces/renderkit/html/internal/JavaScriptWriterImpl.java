/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.17  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.16  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.15  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.14  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.13  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.12  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.11  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.10  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.9  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.8  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.7  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.6  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.5  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.4  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.3  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.2  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public final class JavaScriptWriterImpl extends AbstractJavaScriptWriter {
    private static final String REVISION = "$Revision$";

    private static final String SCRIPT_END = "</SCRIPT>";

    private IHtmlWriter writer;

    private boolean start = false;

    private int initialized = 0;

    private boolean initializing = false;

    private String varId = null;

    private Set requestedModules = null;

    protected IJavaScriptComponent javaScriptComponent;

    private boolean useMetaContentScriptType;

    private boolean useScriptCData;

    private IJavaScriptRenderContext javascriptRenderContext;

    public IHtmlWriter getWriter() {
        return writer;
    }

    public final FacesContext getFacesContext() {
        return getComponentRenderContext().getFacesContext();
    }

    public final IHtmlComponentRenderContext getComponentRenderContext() {
        return (IHtmlComponentRenderContext) writer.getComponentRenderContext();
    }

    public final IJavaScriptRenderContext getJavaScriptRenderContext() {
        return javascriptRenderContext;
    }

    public void setWriter(IHtmlWriter writer,
            IJavaScriptComponent javaScriptComponent,
            IJavaScriptRenderContext javascriptRenderContext,
            boolean useMetaContentScriptType, boolean useScriptCData)
            throws WriterException {
        this.writer = writer;
        this.javaScriptComponent = javaScriptComponent;
        this.useMetaContentScriptType = useMetaContentScriptType;
        this.useScriptCData = useScriptCData;
        this.javascriptRenderContext = javascriptRenderContext;
        start = false;

        if (javascriptRenderContext != null
                && javascriptRenderContext.isRequiresPending()) {
            // Ben non, on peut pas utiliser l'attribut requires car il ne faut
            // pas �tre dans un bloc Javascript
            // pour utiliser l'include
            isInitialized(false);
        }
    }

    /*
     * @see com.vedana.adonis.factory.Writer#write(char)
     */
    public IJavaScriptWriter write(char c) throws WriterException {
        isInitialized(true);

        writer.write(c);

        return this;
    }

    /*
     * @see com.vedana.adonis.factory.Writer#write(String)
     */
    public IJavaScriptWriter write(String string) throws WriterException {
        isInitialized(true);

        writer.write(string);

        return this;
    }

    public IJavaScriptWriter ensureInitialization() throws WriterException {
        isInitialized(true);

        return this;
    }

    public void end() throws WriterException {
        /*
         * if (start == false) { return; }
         */
        writeFooter();
        start = false;
    }

    private boolean isInitialized(boolean full) throws WriterException {
        if (start) {
            return true;
        }

        writeHeader(full);

        return start;
    }

    protected void writeHeader(boolean full) throws WriterException {
        if (initializing || initialized == 2) {
            writeScriptStart();
            return;
        }

        if (initialized == 0) {
            try {
                initializing = true;
                initialized = 1;

                if (requestedModules != null
                        && requestedModules.isEmpty() == false) {
                    writeRequestedModule();
                }

                if (javaScriptComponent != null) {
                    javaScriptComponent.initializeJavaScript(this);
                }

                writeJavaScriptDependencies();

                if (full == false) {
                    return;
                }

            } finally {
                initializing = false;
            }
        }

        try {
            initializing = true;
            initialized = 2;

            if (javaScriptComponent != null) {
                javaScriptComponent.initializeJavaScriptComponent(this);
            }

        } finally {
            initializing = false;
        }

        if (full && start == false) {
            writeScriptStart();
        }
    }

    protected void writeScriptStart() throws WriterException {
        start = true;

        write("<SCRIPT");

        if (useMetaContentScriptType == false) {
            write(" type=\"");
            write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            write('\"');
        }

        write(">");
        if (useScriptCData) {
            write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
        }
        writeln();
    }

    protected void writeScriptEnd() throws WriterException {
        if (useScriptCData) {
            writeln(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
        }
        write(SCRIPT_END);
        start = false;
    }

    public void writeJavaScriptDependencies() throws WriterException {
        if (javascriptRenderContext.isRequiresPending() == false) {
            return;
        }

        writeSymbol("_classLoader").write('.').writeSymbol("requiresBundle")
                .write("(document");

        IRepository.IFile filesToRequire[] = javascriptRenderContext
                .popRequiredFiles();

        Locale locale = javascriptRenderContext.getUserLocale();
        for (int i = 0; i < filesToRequire.length; i++) {
            IRepository.IFile file = filesToRequire[i];

            // if (i>0) {
            write(',');
            // }
            write('\"').write(file.getURI(locale)).write('\"');
        }
        writeln(");");

        // On ferme et rouvre pour pouvoir prendre en compte les document.write
        // du FClass.Require
        writeScriptEnd();
    }

    private void writeInclude(String baseURI, String src)
            throws WriterException {
    }

    private void writeRequestedModule() throws WriterException {

    }

    protected void writeFooter() throws WriterException {
        if (javaScriptComponent != null) {
            javaScriptComponent.releaseJavaScript(this);
            javaScriptComponent = null;
        }

        if (start) {
            writeScriptEnd();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#isClosed()
     */
    public boolean isOpened() {
        return start;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#getComponentVarId()
     */
    public String getComponentVarName() {
        if (varId != null) {
            return varId;
        }

        throw new FacesException("Var is not initialized yet !");
    }

    public void setComponentVarName(String varName) {
        this.varId = varName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter#writeRaw(char[],
     *      int, int)
     */
    public IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
            throws WriterException {
        writer.write(dst, pos, length);

        return this;
    }

    public String allocateString(String string) throws WriterException {
        if (string == null) {
            return null;
        }

        boolean ret[] = new boolean[1];

        String varId = javascriptRenderContext.allocateString(string, ret);
        if (ret[0] == false) {
            return varId;
        }

        write("var ").write(varId).write("=").writeString(string).writeln(";");

        return varId;
    }

    public void addRequestedModule(String moduleName) {
        if (start) {
            throw new FacesException("Can not add requested module !");
        }

        if (requestedModules == null) {
            requestedModules = new HashSet(4);
        }

        requestedModules.add(moduleName);
    }

    protected final String convertSymbol(String symbol) {
        return javascriptRenderContext.convertSymbol(symbol);
    }
}
