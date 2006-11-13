/*
 * $Id$
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
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.IRepository.IFile;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.HtmlProcessContextImpl;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlProcessContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.javascript.IJavaScriptRepository;
import org.rcfaces.renderkit.html.internal.javascript.JavaScriptRepositoryServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptTag extends BodyTagSupport implements Tag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -5062201682788903876L;

    private static final Log LOG = LogFactory.getLog(JavaScriptTag.class);

    private String src;

    private boolean initialize;

    private String requiredFiles;

    private String requiredClasses;

    private IHtmlProcessContext htmlProcessContext;

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
        htmlProcessContext = null;

        super.release();
    }

    public int doStartTag() {
        if (src != null) {
            return SKIP_BODY;
        }

        return EVAL_BODY_INCLUDE;
    }

    static boolean addRequires(FacesContext facesContext, JspWriter writer,
            IHtmlProcessContext htmlProcessContext, String requiredFiles,
            String requiredClasses) throws IOException {

        IJavaScriptRepository repository = JavaScriptRepositoryServlet
                .getRepository(facesContext);
        if (repository == null) {
            LOG.error("JavaScript repository is not created yet !");
            return false;
        }

        List files = new ArrayList(32);

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

        IFile fs[] = repository
                .computeFiles(files,
                        IHierarchicalRepository.FILE_COLLECTION_TYPE,
                        repositoryContext);
        if (fs.length < 1) {
            return false;
        }

        openScriptTag(writer, htmlProcessContext);

        Map symbols = JavaScriptRepositoryServlet.getSymbols(facesContext);

        IJavaScriptWriter jsWriter = new JavaScriptWriterImpl(facesContext,
                symbols, writer);

        JavaScriptRenderContext.initializeJavaScript(jsWriter, repository,
                htmlProcessContext);

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

        htmlProcessContext = HtmlProcessContextImpl
                .getHtmlProcessContext(facesContext);

        boolean opened = false;
        try {
            if (requiredFiles != null || requiredClasses != null) {
                opened = addRequires(facesContext, writer, htmlProcessContext,
                        requiredFiles, requiredClasses);
            }

        } catch (IOException ex) {
            throw new JspException(ex);
        }

        BodyContent bodyContent = getBodyContent();
        try {
            if (src != null || bodyContent != null) {
                boolean useScriptCData = htmlProcessContext.useScriptCData();

                if (opened && src != null) {
                    closeScriptTag(writer, htmlProcessContext);
                    opened = false;
                }

                boolean needScriptCData = false;
                if (opened == false) {
                    boolean useMetaScript = htmlProcessContext
                            .useMetaContentScriptType()
                            && InitializeTag.isMetaDataInitialized(pageContext);

                    writer.print("<SCRIPT");
                    if (useMetaScript == false) {
                        writer.write(" type=\"");
                        writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
                        writer.write('"');
                    }

                    if (src != null) {
                        IContentAccessor contentAccessor = ContentAccessorFactory
                                .createFromWebResource(facesContext, src,
                                        IContentType.SCRIPT);

                        src = contentAccessor.resolveURL(facesContext, null,
                                null);

                        if (src != null) {
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
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
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

        protected void isInitialized() {
        }

    }

    static void openScriptTag(JspWriter writer,
            IHtmlProcessContext htmlProcessContext) throws IOException {

        writer.print("<SCRIPT");
        if (htmlProcessContext.useMetaContentScriptType() == false) {
            writer.write(" type=\"");
            writer.write(IHtmlRenderContext.JAVASCRIPT_TYPE);
            writer.write('"');
        }
        writer.write('>');

        boolean useScriptCData = htmlProcessContext.useScriptCData();

        if (useScriptCData) {
            writer.write(IHtmlRenderContext.JAVASCRIPT_CDATA_BEGIN);
        }

        writer.println();
    }

    static void closeScriptTag(JspWriter writer,
            IHtmlProcessContext htmlProcessContext) throws IOException {

        if (htmlProcessContext.useScriptCData()) {
            writer.println(IHtmlRenderContext.JAVASCRIPT_CDATA_END);
        }

        writer.print("</SCRIPT>");
    }
}
