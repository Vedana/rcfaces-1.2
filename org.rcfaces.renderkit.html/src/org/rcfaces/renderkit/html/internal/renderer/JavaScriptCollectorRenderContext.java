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

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.lang.OrderedSet;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.AbstractHtmlWriter;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.AbstractJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptComponentRenderer;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptEnableModeImpl;

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
        // Ce sont des lazys mais ils n'ont pas besoin d'être initialisés

        // components.add(writer.getComponentRenderContext().getComponentClientId());
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
        if (LOG.isDebugEnabled()) {
            LOG.debug("Push child " + javaScriptRenderContext);
        }

        if (RcfacesContext.isJSF1_2()) {
            // Nous sommes en asyncRenderMode= tree
            return;
        }

        flushComponents(htmlWriter, true);
    }

    public void popChild(IJavaScriptRenderContext javaScriptRenderContext,
            IHtmlWriter htmlWriter) throws WriterException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Pop child " + javaScriptRenderContext);
        }

        if (parent != null && RcfacesContext.isJSF1_2()) {
            // Nous sommes en asyncRenderMode= tree

            ((JavaScriptCollectorRenderContext) parent).components
                    .addAll(components);

            String currentId = htmlWriter.getComponentRenderContext()
                    .getComponentClientId();
            ((JavaScriptCollectorRenderContext) parent).components
                    .add(new ComponentId(currentId,
                            ((JavaScriptEnableModeImpl) htmlWriter
                                    .getJavaScriptEnableMode()).getMode(),
                            null, null));

            return;
        }

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
                    JavaScriptEnableModeImpl js = (JavaScriptEnableModeImpl) writer
                            .getJavaScriptEnableMode();

                    int mode = js.getMode();
                    String accessKey = null;

                    if ((mode & JavaScriptEnableModeImpl.ONACCESSKEY) > 0) {
                        UIComponent component = getWriter()
                                .getComponentRenderContext().getComponent();
                        if (component instanceof IAccessKeyCapability) {
                            accessKey = ((IAccessKeyCapability) component)
                                    .getAccessKey();
                        }
                    }

                    String subComponents[] = null;
                    if ((mode & JavaScriptEnableModeImpl.ONFOCUS) > 0) {
                        subComponents = ((AbstractHtmlWriter) getWriter())
                                .listSubFocusableComponents();
                    }

                    components.add(new ComponentId(
                            writer.getComponentRenderContext()
                                    .getComponentClientId(), js.getMode(),
                            accessKey, subComponents));

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

    public void releaseComponentJavaScript(IJavaScriptWriter jsWriter,
            boolean sendComplete, AbstractHtmlRenderer htmlComponentRenderer)
            throws WriterException {

        if (sendComplete) {
            if (LOG_INTERMEDIATE_PROFILING.isTraceEnabled()) {
                jsWriter.writeCall("f_core", "Profile").writeln(
                        "false,\"javascript.completeComponent\");");
            }

            jsWriter.writeMethodCall("f_completeComponent").writeln(");");

            if (LOG_INTERMEDIATE_PROFILING.isTraceEnabled()) {
                jsWriter.writeCall("f_core", "Profile").writeln(
                        "true,\"javascript.completeComponent\");");
            }
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

            if (LOG_INTERMEDIATE_PROFILING.isInfoEnabled()) {
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
        List accessIds = new ArrayList(16);
        List messageIds = new ArrayList(16);
        List focusIds = new ArrayList();
        List submitIds = new ArrayList(16);
        List hoverIds = new ArrayList(16);

        for (Iterator it = components.iterator(); it.hasNext();) {
            Object object = it.next();

            if (object instanceof ComponentId) {
                initializeIds.add(object);

                if (logIntermediateProfiling) {
                    if (jsWriter == null) {
                        jsWriter = InitRenderer.openScriptTag(htmlWriter);
                    }

                    writeInitIds(jsWriter, initializeIds, beginRender,
                            focusIds, hoverIds, messageIds, accessIds,
                            submitIds);

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

                writeInitIds(jsWriter, initializeIds, beginRender, focusIds,
                        hoverIds, messageIds, accessIds, submitIds);

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

            writeInitIds(jsWriter, initializeIds, beginRender, focusIds,
                    hoverIds, messageIds, accessIds, submitIds);
        }

        if (accessIds.isEmpty() == false || messageIds.isEmpty() == false
                || focusIds.isEmpty() == false || submitIds.isEmpty() == false
                || hoverIds.isEmpty() == false) {
            if (jsWriter == null) {
                jsWriter = InitRenderer.openScriptTag(htmlWriter);
            }

            String cameliaClassLoader = jsWriter.getJavaScriptRenderContext()
                    .convertSymbol("f_classLoader", "_rcfacesClassLoader");

            if (accessIds.isEmpty() == false) {
                boolean first = true;
                jsWriter.writeCall(cameliaClassLoader, "f_initOnAccessIds");
                for (Iterator it = accessIds.iterator(); it.hasNext();) {

                    if (first) {
                        first = false;

                    } else {
                        jsWriter.write(',');
                    }

                    jsWriter.writeString((String) it.next()).write(',')
                            .writeString((String) it.next());
                }
                jsWriter.writeln(");");
            }

            if (messageIds.isEmpty() == false) {
                boolean first = true;
                jsWriter.writeCall(cameliaClassLoader, "f_initOnMessage");
                for (Iterator it = messageIds.iterator(); it.hasNext();) {

                    if (first) {
                        first = false;

                    } else {
                        jsWriter.write(',');
                    }

                    jsWriter.writeString((String) it.next());
                }
                jsWriter.writeln(");");
            }

            if (focusIds.isEmpty() == false) {
                jsWriter.writeCall(cameliaClassLoader, "f_initOnFocusIds");

                IObjectLiteralWriter objWriter = jsWriter
                        .writeObjectLiteral(false);
                for (Iterator it = focusIds.iterator(); it.hasNext();) {

                    objWriter.writeProperty((String) it.next()).writeInt(1);
                }
                objWriter.end();
                jsWriter.writeln(");");
            }

            if (submitIds.isEmpty() == false) {
                jsWriter.writeCall(cameliaClassLoader, "f_initOnSubmitIds");

                IObjectLiteralWriter objWriter = jsWriter
                        .writeObjectLiteral(false);
                for (Iterator it = submitIds.iterator(); it.hasNext();) {
                    objWriter.writeProperty((String) it.next()).writeInt(1);
                }
                objWriter.end();
                jsWriter.writeln(");");
            }

            if (hoverIds.isEmpty() == false) {
                jsWriter.writeCall(cameliaClassLoader, "f_initOnOverIds");

                IObjectLiteralWriter objWriter = jsWriter
                        .writeObjectLiteral(false);
                for (Iterator it = hoverIds.iterator(); it.hasNext();) {
                    objWriter.writeProperty((String) it.next()).writeInt(1);
                }
                objWriter.end();
                jsWriter.writeln(");");
            }

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
            List initializeIds, boolean beginRender, List focusIds,
            List hoverIds, List messagesIds, List accessIds, List submitIds)
            throws WriterException {

        String currendId = null;
        if (beginRender) {
            currendId = jsWriter.getComponentRenderContext()
                    .getComponentClientId();
        }

        String cameliaClassLoader = jsWriter.getJavaScriptRenderContext()
                .convertSymbol("f_classLoader", "_rcfacesClassLoader");

        ComponentId currendIdDetected = null;

        List others = null;

        boolean first = true;
        for (Iterator it = initializeIds.iterator(); it.hasNext();) {
            ComponentId componentId = (ComponentId) it.next();

            String clientId = componentId.clientId;

            if (clientId.equals(currendId)) {
                currendIdDetected = componentId;
                continue;
            }

            if (componentId.mode > 0
                    && (componentId.mode & JavaScriptEnableModeImpl.ONINIT) == 0) {
                if (others == null) {
                    others = new ArrayList(initializeIds.size());
                }
                others.add(componentId);
                continue;
            }

            if (first) {
                first = false;
                jsWriter.writeCall(cameliaClassLoader, "f_initIds");

            } else {
                jsWriter.write(',');
            }

            jsWriter.writeString(clientId);
        }

        if (first == false) {
            jsWriter.writeln(");");
        }

        if (others != null) {
            for (Iterator it = others.iterator(); it.hasNext();) {
                ComponentId componentId = (ComponentId) it.next();

                if ((componentId.mode & JavaScriptEnableModeImpl.ONACCESSKEY) == 0) {
                    continue;
                }

                accessIds.add(componentId.clientId);
                accessIds.add(componentId.accessKey);
            }

            for (Iterator it = others.iterator(); it.hasNext();) {
                ComponentId componentId = (ComponentId) it.next();

                if ((componentId.mode & JavaScriptEnableModeImpl.ONSUBMIT) == 0) {
                    continue;
                }

                submitIds.add(componentId.clientId);
            }

            for (Iterator it = others.iterator(); it.hasNext();) {
                ComponentId componentId = (ComponentId) it.next();

                if ((componentId.mode & JavaScriptEnableModeImpl.ONFOCUS) == 0) {
                    continue;
                }

                focusIds.add(componentId.clientId);
            }

            for (Iterator it = others.iterator(); it.hasNext();) {
                ComponentId componentId = (ComponentId) it.next();

                if ((componentId.mode & JavaScriptEnableModeImpl.ONMESSAGE) == 0) {
                    continue;
                }

                messagesIds.add(componentId.clientId);
            }

            for (Iterator it = others.iterator(); it.hasNext();) {
                ComponentId componentId = (ComponentId) it.next();

                if ((componentId.mode & JavaScriptEnableModeImpl.ONOVER) == 0) {
                    continue;
                }

                hoverIds.add(componentId.clientId);
            }
        }

        initializeIds.clear();
        if (currendIdDetected != null) {
            initializeIds.add(currendIdDetected);
        }
    }

    public boolean isCollectorMode() {
        return true;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ComponentId {
        final int mode;

        final String clientId;

        final String accessKey;

        final String subClientIds[];

        public ComponentId(String clientId, int mode, String accessKey,
                String subClientIds[]) {
            this.mode = mode;
            this.clientId = clientId;
            this.accessKey = accessKey;
            this.subClientIds = subClientIds;
        }

        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((clientId == null) ? 0 : clientId.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final ComponentId other = (ComponentId) obj;
            if (clientId == null) {
                if (other.clientId != null)
                    return false;
            } else if (!clientId.equals(other.clientId))
                return false;
            return true;
        }

    }
}
