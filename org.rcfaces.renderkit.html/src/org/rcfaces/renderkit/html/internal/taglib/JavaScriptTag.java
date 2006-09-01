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
 * Revision 1.11  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.10  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.9  2006/05/16 13:58:17  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.8  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.7  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.6  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.5  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.4  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.renderkit.html.internal.taglib;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.config.AbstractURLRewritingProvider;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.HtmlRenderKit;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlExternalContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class JavaScriptTag extends BodyTagSupport {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(JavaScriptTag.class);

    private String src;

    private boolean initialize;

    private String requiredFiles;

    private String requiredClasses;

    private IHtmlExternalContext htmlRenderContext;

    public final String getSrc() {
        return src;
    }

    public final void setSrc(String src) {
        this.src = src;
    }

    public final boolean isInitialize() {
        return initialize;
    }

    public final void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }

    public final String getRequiredFiles() {
        return requiredFiles;
    }

    public final void setRequiredFiles(String requires) {
        this.requiredFiles = requires;
    }

    public final String getRequiredClasses() {
        return requiredClasses;
    }

    public final void setRequiredClasses(String requiredClasses) {
        this.requiredClasses = requiredClasses;
    }

    public void release() {
        initialize = false;
        src = null;
        requiredFiles = null;
        requiredClasses = null;
        htmlRenderContext = null;

        super.release();
    }

    public int doStartTag() {
        if (src != null) {
            return SKIP_BODY;
        }

        return EVAL_BODY_INCLUDE;
    }

    private boolean addRequires(FacesContext facesContext, JspWriter writer,
            boolean useMetaScript, String requiredFiles, String requiredClasses)
            throws IOException {

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(pageContext.getServletContext());
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return false;
        }

        List files = new ArrayList();

        if (requiredFiles != null) {
            StringTokenizer st = new StringTokenizer(requiredFiles, ", ");
            for (; st.hasMoreTokens();) {
                String requiredFile = st.nextToken();

                IRepository.IFile file = repository.getSetByName(requiredFile);
                if (file == null) {
                    file = repository.getModuleByName(requiredFile);
                    if (file == null) {
                        file = repository.getFileByName(requiredFile);
                    }
                }

                if (file == null) {
                    LOG.error("Can not find required file '" + requiredFile
                            + "' !");
                    continue;
                }

                files.add(file);
            }
        }
        if (requiredClasses != null) {
            StringTokenizer st = new StringTokenizer(requiredClasses, ", ");
            for (; st.hasMoreTokens();) {
                String className = st.nextToken();

                IJavaScriptRepository.IClass clazz = repository
                        .getClassByName(className);

                if (clazz == null) {
                    LOG.error("Can not find required class '" + className
                            + "' !");
                    continue;
                }

                files.add(clazz.getFile());
            }
        }

        if (files.isEmpty()) {
            return false;
        }

        IRepository.IContext repositoryContext;

        repositoryContext = JavaScriptRepositoryServlet
                .getContextRepository(facesContext);

        IFile fs[] = repository.computeFiles(files,
                IRepository.FILE_COLLECTION_TYPE, repositoryContext);
        if (fs.length < 1) {
            return false;
        }

        writer.print("<SCRIPT");
        if (useMetaScript == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('"');
        }
        writer.write('>');

        boolean useScriptCData = htmlRenderContext.useScriptCData();

        if (useScriptCData) {
            writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
        }

        writer.println();

        Map symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

        IJavaScriptWriter jsWriter = new JavaScriptWriterImpl(facesContext,
                symbols, writer);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository,
                htmlRenderContext);

        jsWriter.writeCall("_classLoader", "requiresBundle");
        jsWriter.write("document");

        Locale locale = repositoryContext.getLocale();
        for (int i = 0; i < fs.length; i++) {
            String src = fs[i].getURI(locale);

            jsWriter.write(", \"").write(src).write('"');
        }

        jsWriter.writeln(");");

        return true;
    }

    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        htmlRenderContext = HtmlRenderKit.getExternalContext(facesContext
                .getExternalContext());

        boolean opened = false;
        try {
            if (requiredFiles != null || requiredClasses != null) {
                boolean useMetaScript = htmlRenderContext
                        .useMetaContentScriptType()
                        && InitializeTag.isMetaDataInitialized(pageContext);

                opened = addRequires(facesContext, writer, useMetaScript,
                        requiredFiles, requiredClasses);
            }

        } catch (IOException ex) {
            throw new JspException(ex);
        }

        BodyContent bodyContent = getBodyContent();
        try {
            if (src != null || bodyContent != null) {
                boolean useScriptCData = htmlRenderContext.useScriptCData();

                if (opened && src != null) {
                    if (useScriptCData) {
                        writer.println(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
                    }

                    writer.println("</SCRIPT>");
                    opened = false;
                }

                boolean needScriptCData = false;
                if (opened == false) {
                    boolean useMetaScript = htmlRenderContext
                            .useMetaContentScriptType()
                            && InitializeTag.isMetaDataInitialized(pageContext);

                    writer.print("<SCRIPT");
                    if (useMetaScript == false) {
                        writer.write(" type=\"");
                        writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
                        writer.write('"');
                    }

                    if (src != null) {
                        src = rewriteURL(null, src);
                        src = reformatSource(src);
                        writer.write(" src=\"");
                        writer.write(src);
                        writer.write('"');

                        String javascriptCharset = IHtmlRenderContext.JAVASCRIPT_CHARSET;
                        if (javascriptCharset != null) {
                            writer.write(" charset=\"");
                            writer.write(javascriptCharset);
                            writer.write('"');
                        }
                    }

                    writer.print('>');
                    opened = true;
                    needScriptCData = useScriptCData;
                }

                if (src == null) {
                    if (needScriptCData) {
                        writer.print(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
                        writer.println();
                    }

                    bodyContent.writeOut(writer);

                    if (useScriptCData) {
                        writer.println(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
                    }
                }

            }

            if (opened) {
                writer.print("</SCRIPT>");
            }
        } catch (IOException ex) {
            throw new JspException(ex);
        }

        return super.doEndTag();
    }

    private String rewriteURL(FacesContext facesContext, String src) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        IURLRewritingProvider urlRewritingProvider = AbstractURLRewritingProvider
                .getInstance(facesContext.getExternalContext());
        if (urlRewritingProvider == null) {
            return src;
        }

        return urlRewritingProvider.computeURL(facesContext, null,
                IURLRewritingProvider.SCRIPT_URL_TYPE, null, src, null, null);
    }

    private String reformatSource(String src) {
        String applicationVersion = (String) pageContext.getServletContext()
                .getAttribute(RcfacesContext.APPLICATION_VERSION_PROPERTY);
        if (applicationVersion == null) {
            return src;
        }

        applicationVersion = applicationVersion.trim();
        if (applicationVersion.length() < 1) {
            return src;
        }

        int idx = src.lastIndexOf('.');
        int idx2 = src.lastIndexOf('/');
        if (idx < 0 || idx2 > idx) {
            return src;
        }

        return src.substring(0, idx) + "__V" + applicationVersion + "."
                + src.substring(idx + 1);

    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    static class JavaScriptWriterImpl extends AbstractJavaScriptWriter {
        private static final String REVISION = "$Revision$";

        private final Map symbols;

        private final FacesContext facesContext;

        private final Writer writer;

        public JavaScriptWriterImpl(FacesContext facesContext, Map symbols,
                Writer writer) {
            this.symbols = symbols;
            this.facesContext = facesContext;
            this.writer = writer;
        }

        protected String convertSymbol(String symbol) {
            if (symbols == null) {
                return symbol;
            }

            String compacted = (String) symbols.get(symbol);
            if (compacted != null) {
                return compacted;
            }

            return symbol;
        }

        public IJavaScriptRenderContext getJavaScriptRenderContext() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public String getComponentVarName() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public void setComponentVarName(String varName) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public IHtmlComponentRenderContext getComponentRenderContext() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public IHtmlWriter getWriter() {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public FacesContext getFacesContext() {
            return facesContext;
        }

        public IJavaScriptWriter ensureInitialization() {
            return this;
        }

        public IJavaScriptWriter write(String string) throws WriterException {
            try {
                writer.write(string);

            } catch (IOException e) {
                throw new WriterException("Can not write '" + string + "'.", e,
                        null);
            }
            return this;
        }

        public IJavaScriptWriter write(char c) throws WriterException {
            try {
                writer.write(c);
                return this;

            } catch (IOException e) {
                throw new WriterException("Can not write char '" + c + "'.", e,
                        null);
            }
        }

        public IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
                throws WriterException {
            try {
                writer.write(dst, pos, length);

            } catch (IOException e) {
                throw new WriterException("Can not write buffer.", e, null);
            }
            return null;
        }

        public void addRequestedModule(String moduleName) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public String allocateString(String text) {
            throw new UnsupportedOperationException("Not implemented !");
        }

        public void end() {
        }

        public boolean isOpened() {
            return true;
        }

    }
}
