/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class JavaScriptWriterImpl extends AbstractJavaScriptWriter {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptWriterImpl.class);

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

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Initialize Writer componentId='"
                            + writer.getComponentRenderContext()
                                    .getComponentId()
                            + "' requiresPending="
                            + (javascriptRenderContext != null && javascriptRenderContext
                                    .isRequiresPending()) + ".");
        }

        if (javascriptRenderContext != null
                && javascriptRenderContext.isRequiresPending()) {
            // Ben non, on peut pas utiliser l'attribut requires car il ne faut
            // pas être dans un bloc Javascript
            // pour utiliser l'include

            isInitialized(false);
        }
    }

    public IJavaScriptWriter write(char c) throws WriterException {
        isInitialized(true);

        writer.write(c);

        return this;
    }

    public IJavaScriptWriter write(String string) throws WriterException {
        isInitialized(true);

        writer.write(string);

        return this;
    }

    public IJavaScriptWriter ensureInitialization() throws WriterException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Ensure initialization");
        }

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

    protected void isInitialized() throws WriterException {
        isInitialized(true);
    }

    protected void writeHeader(boolean full) throws WriterException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Write header full=" + full + " initializing="
                    + initializing + " init state=" + initialized);
        }

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
        if (LOG.isTraceEnabled()) {
            LOG.trace("Start script");
        }
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
        if (LOG.isTraceEnabled()) {
            LOG.trace("End script");
        }

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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Write javascript dependencies: "
                    + Arrays.asList(filesToRequire));
        }

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
        if (LOG.isDebugEnabled()) {
            IComponentRenderContext componentRenderContext = getComponentRenderContext();
            if (componentRenderContext != null) {
                LOG.debug("Set component (id='"
                        + componentRenderContext.getComponentId()
                        + "') var name to '" + varName + "'.");
            } else {
                LOG.debug("Set component (id='?') var name to '" + varName
                        + "'.");
            }
        }

        this.varId = varName;
    }

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
            if (LOG.isDebugEnabled()) {
                LOG.debug("String '" + string + "' is already setted to var '"
                        + varId + "'.");
            }

            return varId;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Allocate string '" + string + "' to var '" + varId
                    + "'.");
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
        String converted = javascriptRenderContext.convertSymbol(symbol);

        if (LOG.isTraceEnabled()) {
            if (symbol.equals(converted) == false) {
                LOG.trace("Convert symbol '" + symbol + "' to '" + converted
                        + "'.");
            }
        }

        return converted;
    }
}
