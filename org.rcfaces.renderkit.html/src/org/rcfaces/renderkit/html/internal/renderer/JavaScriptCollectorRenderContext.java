/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.lang.OrderedSet;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptComponentRenderer;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptCollectorRenderContext extends
        AbstractJavaScriptRenderContext {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(JavaScriptCollectorRenderContext.class);

    private static final Log LOG_INTERMEDIATE_PROFILING = LogFactory
            .getLog("org.rcfaces.html.javascript.LOG_INTERMEDIATE_PROFILING");

    private static final Object DISABLE_VINIT_SEARCH_PROPERTY = "camelia.jsCollector.disable.vinit";

    public final Set components = new OrderedSet();

    public JavaScriptCollectorRenderContext(FacesContext facesContext) {
        super(facesContext);
    }

    protected JavaScriptCollectorRenderContext(
            JavaScriptCollectorRenderContext parent) {
        super(parent);
    }

    public IJavaScriptRenderContext createChild() {
        return new JavaScriptCollectorRenderContext(this);
    }

    public void declareLazyJavaScriptRenderer(IComponentWriter writer) {
        components.add(writer.getComponentRenderContext()
                .getComponentClientId());
    }

    public boolean isJavaScriptRendererDeclaredLazy(IComponentWriter writer) {
        return false;
    }

    public void initializeJavaScriptComponent(IJavaScriptWriter writer)
            throws WriterException {
        writeJsInitComponent(writer);

        IHtmlComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        if (componentRenderContext.hasClientDatas(true)) {
            encodeClientData(writer);
        }
    }

    public void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException {
        // Rien

    }

    public void pushChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) throws WriterException {
        flushComponents(htmlWriter, true);
    }

    public void popChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) throws WriterException {
        flushComponents(htmlWriter, false);
    }

    protected IJavaScriptWriter createJavaScriptWriter(
            final IHtmlWriter writer,
            final IJavaScriptComponentRenderer javaScriptComponent)
            throws WriterException {

        final CharArrayWriter bufferedWriter = new CharArrayWriter(8000);
        components.add(bufferedWriter);

        IJavaScriptWriter javaScriptWriter = new AbstractJavaScriptWriter() {
            private static final String REVISION = "$Revision$";

            private String componentVarName;

            private boolean initialized;

            protected String convertSymbol(String className, String memberName) {
                return JavaScriptCollectorRenderContext.this.convertSymbol(
                        className, memberName);
            }

            public String allocateString(String string) throws WriterException {
                if (string == null) {
                    return null;
                }

                boolean ret[] = new boolean[1];

                String varId = JavaScriptCollectorRenderContext.this
                        .allocateString(string, ret);
                if (ret[0] == false) {
                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("String '" + string
                                        + "' is already setted to var '"
                                        + varId + "'.");
                    }

                    return varId;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Allocate string '" + string + "' to var '"
                            + varId + "'.");
                }

                write("var ").write(varId).write("=").writeString(string)
                        .writeln(";");

                return varId;
            }

            public void end() throws WriterException {

                if (initialized && javaScriptComponent != null) {
                    javaScriptComponent.releaseJavaScript(this);
                }

                bufferedWriter.close();
                if (bufferedWriter.size() == 0) {
                    components.remove(bufferedWriter);
                }

                if (initialized == false) {
                    components.add(writer.getComponentRenderContext()
                            .getComponentClientId());
                    return;
                }
            }

            public IJavaScriptWriter ensureInitialization()
                    throws WriterException {
                return this;
            }

            public String getComponentVarName() {
                if (componentVarName != null) {
                    return componentVarName;
                }

                componentVarName = getJavaScriptRenderContext()
                        .allocateVarName();

                return componentVarName;
            }

            public void setComponentVarName(String varName) {
                this.componentVarName = varName;
            }

            public FacesContext getFacesContext() {
                return writer.getComponentRenderContext().getFacesContext();
            }

            public IHtmlComponentRenderContext getHtmlComponentRenderContext() {
                return writer.getHtmlComponentRenderContext();
            }

            public IJavaScriptRenderContext getJavaScriptRenderContext() {
                return JavaScriptCollectorRenderContext.this;
            }

            public IHtmlWriter getWriter() {
                return writer;
            }

            public boolean isOpened() {
                return true;
            }

            public IJavaScriptWriter write(String string)
                    throws WriterException {
                isInitialized();

                char ch[] = string.toCharArray();

                bufferedWriter.write(ch, 0, ch.length);
                return this;
            }

            public IJavaScriptWriter write(char c) throws WriterException {
                isInitialized();
                bufferedWriter.write(c);

                return this;
            }

            public IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
                    throws WriterException {
                isInitialized();

                bufferedWriter.write(dst, pos, length);

                return this;
            }

            public IComponentRenderContext getComponentRenderContext() {
                return writer.getComponentRenderContext();
            }

            public String getResponseCharacterEncoding() {
                return "UTF-8";
            }

            protected void isInitialized() throws WriterException {
                if (initialized) {
                    return;
                }
                initialized = true;

                javaScriptComponent.initializeJavaScriptComponent(this);
            }
        };

        return javaScriptWriter;
    }

    public void releaseComponentJavaScript(IJavaScriptWriter writer,
            boolean sendComplete, AbstractHtmlRenderer htmlComponentRenderer)
            throws WriterException {

        if (sendComplete) {
            writer.writeMethodCall("f_completeComponent").writeln(");");
        }
    }

    public boolean isRequiresPending() {
        return false;
    }

    private void flushComponents(IHtmlWriter htmlWriter, boolean beginRender)
            throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        IRepository.IFile filesToRequire[] = computeFilesToRequire();

        if (filesToRequire.length > 0) {
            IJavaScriptWriter jsWriter = InitRenderer.openScriptTag(htmlWriter);

            initializeJavaScript(jsWriter, getRepository());

            String cameliaClassLoader = convertSymbol("f_classLoader",
                    "_rcfacesClassLoader");

            jsWriter.writeCall(cameliaClassLoader, "f_requiresBundle");

            if (LOG.isDebugEnabled()) {
                LOG.debug("Write javascript dependencies: "
                        + Arrays.asList(filesToRequire));
            }

            Locale locale = getUserLocale();
            for (int i = 0; i < filesToRequire.length; i++) {
                IRepository.IFile file = filesToRequire[i];

                if (i > 0) {
                    jsWriter.write(',');
                }
                jsWriter.writeString(file.getURI(locale));
            }
            jsWriter.writeln(");");

            jsWriter.end();
        }

        IJavaScriptWriter jsWriter = null;
        if (isJavaScriptInitialized(facesContext) == false) {
            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            initializeJavaScript(jsWriter, getRepository());
        }

        boolean isProfilerOn = isProfilerOn(htmlWriter);
        int profilerId = 0;
        boolean logProfiling = false;
        boolean logIntermediateProfiling = false;

        if (isProfilerOn) {
            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            if (LOG_INTERMEDIATE_PROFILING.isDebugEnabled()) {
                logProfiling = true;

                jsWriter.writeCall("f_core", "Profile").writeln(
                        "false,\"javascriptCollector\");");
            }
            if (LOG_INTERMEDIATE_PROFILING.isTraceEnabled()) {
                logIntermediateProfiling = true;
            }
        }

        ExternalContext externalContext = facesContext.getExternalContext();
        Map requestMap = externalContext.getRequestMap();

        if (requestMap.put(DISABLE_VINIT_SEARCH_PROPERTY, Boolean.TRUE) == null) {
            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            jsWriter.writeln("window._rcfacesDisableInitSearch=true");
        }

        List initializeIds = new ArrayList(16);

        for (Iterator it = components.iterator(); it.hasNext();) {
            Object object = it.next();

            if (object instanceof String) {
                initializeIds.add(object);

                if (logIntermediateProfiling) {
                    if (jsWriter == null) {
                        jsWriter = InitRenderer.openScriptTag(htmlWriter);
                    }

                    writeInitIds(jsWriter, initializeIds, beginRender);

                    jsWriter.writeCall("f_core", "Profile").writeln(
                            "null,\"javascriptCollector.initIds(#"
                                    + (profilerId++) + ")\");");
                }

                continue;
            }

            if (initializeIds.isEmpty() == false) {
                if (jsWriter == null) {
                    jsWriter = InitRenderer.openScriptTag(htmlWriter);
                }

                writeInitIds(jsWriter, initializeIds, beginRender);

                if (logIntermediateProfiling) {
                    jsWriter.writeCall("f_core", "Profile").writeln(
                            "null,\"javascriptCollector.initIds(#"
                                    + (profilerId++) + ")\");");
                }
            }

            CharArrayWriter writer = (CharArrayWriter) object;
            String buffer = writer.toString().trim();
            if (buffer.length() < 1) {
                continue;
            }

            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            jsWriter.writeln(buffer);

            if (logIntermediateProfiling) {
                jsWriter.writeCall("f_core", "Profile").writeln(
                        "null,\"javascriptCollector.buffer(#" + (profilerId++)
                                + ")\");");
            }
        }

        if (initializeIds.isEmpty() == false) {
            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            writeInitIds(jsWriter, initializeIds, beginRender);
        }

        if (logProfiling) {
            if (jsWriter != null) {
                jsWriter.writeCall("f_core", "Profile").writeln(
                        "true,\"javascriptCollector\");");
            }
        }

        if (jsWriter != null) {
            jsWriter.end();
        }

        components.clear();
    }

    protected boolean isProfilerOn(IHtmlWriter writer) {
        return Boolean.FALSE.equals(writer.getHtmlComponentRenderContext()
                .getHtmlRenderContext().getProcessContext().getProfilerMode()) == false;
    }

    private static void writeInitIds(IJavaScriptWriter jsWriter,
            List initializeIds, boolean beginRender) throws WriterException {

        String currendId = null;
        if (beginRender) {
            currendId = jsWriter.getComponentRenderContext()
                    .getComponentClientId();
        }
        boolean currendIdDetected = false;

        boolean first = true;
        for (Iterator it = initializeIds.iterator(); it.hasNext();) {

            String clientId = (String) it.next();

            if (clientId.equals(currendId)) {
                currendIdDetected = true;
                continue;
            }

            if (first) {
                first = false;

                String cameliaClassLoader = jsWriter
                        .getJavaScriptRenderContext().convertSymbol(
                                "f_classLoader", "_rcfacesClassLoader");

                jsWriter.writeCall(cameliaClassLoader, "f_initIds");

            } else {
                jsWriter.write(',');
            }

            jsWriter.writeString(clientId);

        }

        if (first == false) {
            jsWriter.writeln(");");
        }

        initializeIds.clear();
        if (currendIdDetected) {
            initializeIds.add(currendId);
        }
    }

    public boolean isCollectorMode() {
        return true;
    }
}
