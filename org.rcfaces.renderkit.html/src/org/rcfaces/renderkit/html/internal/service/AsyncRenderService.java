/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.CharArrayWriter;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.lang.ByteBufferInputStream;
import org.rcfaces.core.internal.lang.ByteBufferOutputStream;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.tools.ComponentTreeRenderProcessorFactory;
import org.rcfaces.core.internal.renderkit.tools.IComponentTreeRenderProcessor;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.ExtendedHttpServlet;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncRenderService extends AbstractAsyncRenderService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AsyncRenderService.class);

    private static int BUFFER_ID = 0;

    private static final String INTERACTIVE_KEY = "vfc.itrv";

    private static final String INTERACTIVE_RENDERER_PARAMETER = Constants
            .getPackagePrefix()
            + ".INTERACTIVE_RENDER";

    // private static final Integer TREE_ASYNC_MODE = new
    // Integer(IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE);

    private static final Integer BUFFER_ASYNC_RENDER_MODE = new Integer(
            IAsyncRenderModeCapability.BUFFER_ASYNC_RENDER_MODE);

    private static final int TREE_BUFFER_INITIAL_SIZE = 8000;

    private transient boolean interactiveRender;

    private transient boolean useGzip;

    public void initialize(FacesContext facesContext) {
        String interactiveRendererParameter = facesContext.getExternalContext()
                .getInitParameter(INTERACTIVE_RENDERER_PARAMETER);

        if ("false".equalsIgnoreCase(interactiveRendererParameter)) {
            LOG.info("Disable interactive render.");
            interactiveRender = false;

        } else if ("true".equalsIgnoreCase(interactiveRendererParameter)) {
            LOG.info("Enable interactive render.");
            interactiveRender = true;

        } else {
            interactiveRender = Constants.INTERACTIVE_RENDER_DEFAULT_VALUE;

            LOG.info("Use default interactive render value ("
                    + interactiveRender + ").");
        }

        if (interactiveRender) {
            String useGZIPParameter = facesContext.getExternalContext()
                    .getInitParameter(ConfiguredHttpServlet.USE_GZIP_PARAMETER);

            if ("true".equalsIgnoreCase(useGZIPParameter)) {
                LOG.info("Enable interactive render GZIP.");
                useGzip = true;

            } else if ("false".equalsIgnoreCase(useGZIPParameter)) {
                LOG.info("Disable interactive render GZIP.");
                useGzip = false;

            } else {
                useGzip = Constants.INTERACTIVE_RENDER_GZIP_DEFAULT_VALUE;

                LOG.info("Use default interactive render GZIP value ("
                        + useGzip + ").");

            }
        }

    }

    public void service(FacesContext facesContext, String commandId) {
        try {
            Map parameters = facesContext.getExternalContext()
                    .getRequestParameterMap();

            String componentId = (String) parameters.get("id");
            if (componentId == null) {
                AbstractHtmlService.sendJsError(facesContext,
                        "Can not find 'id' parameter.");
                return;
            }

            UIViewRoot viewRoot = facesContext.getViewRoot();

            componentId = HtmlTools.computeComponentId(facesContext,
                    componentId);
            UIComponent component = ComponentTools.getForComponent(
                    facesContext, componentId, viewRoot);
            if (component == null) {
                AbstractHtmlService.sendJsError(facesContext,
                        "Can not find component '" + componentId + "'.");

                return;
            }

            if ((component instanceof IAsyncRenderComponent) == false) {
                AbstractHtmlService.sendJsError(facesContext,
                        "Can not find AsyncRenderComponent (id='" + componentId
                                + "').");
                return;
            }

            Object object = component.getAttributes().remove(INTERACTIVE_KEY);
            if (object instanceof InteractiveBuffer) {
                InteractiveBuffer interactiveBuffer = (InteractiveBuffer) object;

                ServletResponse response = (ServletResponse) facesContext
                        .getExternalContext().getResponse();

                AbstractHtmlService.setNoCache(response);
                response.setContentType(IHtmlRenderContext.HTML_TYPE
                        + "; charset=" + AbstractHtmlService.RESPONSE_CHARSET);

                try {
                    interactiveBuffer.sendBuffer(facesContext);

                } catch (IOException ex) {
                    throw new FacesException(
                            "Can not write async content of component id='"
                                    + componentId + "'.", ex);
                }

            } else if (object instanceof InteractiveContext) {
                InteractiveContext interactiveContext = (InteractiveContext) object;

                ServletResponse response = (ServletResponse) facesContext
                        .getExternalContext().getResponse();

                AbstractHtmlService.setNoCache(response);
                response.setContentType(IHtmlRenderContext.HTML_TYPE
                        + "; charset=" + AbstractHtmlService.RESPONSE_CHARSET);

                try {
                    InteractiveBuffer interactiveBuffer = interactiveContext
                            .renderTree(facesContext, component, componentId);

                    interactiveBuffer.sendBuffer(facesContext);

                } catch (IOException ex) {
                    throw new FacesException(
                            "Can not write async content of component id='"
                                    + componentId + "'.", ex);
                }

            } else {
                AbstractHtmlService.sendJsError(facesContext,
                        "No content for component id='" + componentId + "'.");

                return;
            }

        } catch (RuntimeException ex) {
            LOG.error("Catch runtime exception !", ex);

            throw ex;
        }

        facesContext.responseComplete();
    }

    public static void setAsyncRenderer(
            IHtmlComponentRenderContext htmlComponentRenderContext,
            int asyncRender) {
        UIComponent component = htmlComponentRenderContext.getComponent();
        Map map = component.getAttributes();

        if (asyncRender == IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
            if (map.remove(INTERACTIVE_KEY) != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Disable asyncRender for component '"
                            + component.getId() + "'.");
                }
            }
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Enable asyncRender for component '" + component.getId()
                    + "'.");
        }

        if (asyncRender == IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE) {
            map.put(INTERACTIVE_KEY, new InteractiveContext(
                    htmlComponentRenderContext.getHtmlRenderContext()));
            return;
        }
        map.put(INTERACTIVE_KEY, BUFFER_ASYNC_RENDER_MODE);
    }

    public boolean isAsyncRendererEnabled(FacesContext facesContext,
            UIComponent component) {
        Map map = component.getAttributes();

        return map.containsKey(INTERACTIVE_KEY);
    }

    public boolean isAsyncRenderEnable() {
        return interactiveRender;
    }

    public void setContent(FacesContext facesContext, UIComponent component,
            BodyContent writer) {
        Map map = component.getAttributes();

        Object key = map.get(INTERACTIVE_KEY);
        if (key instanceof InteractiveContext) {
            return;
        }

        map.put(INTERACTIVE_KEY, new InteractiveBuffer(facesContext, writer));
    }

    protected final boolean canUseGzip(FacesContext facesContext) {
        if (useGzip == false) {
            return false;
        }

        if (facesContext == null) {
            return true;
        }

        // On verifie que le browser le supporte
        return ConfiguredHttpServlet.hasGzipSupport(facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static abstract class AbstractInteractive implements StateHolder {

        private transient boolean transientState;

        public boolean isTransient() {
            return transientState;
        }

        public void setTransient(boolean newTransientValue) {
            this.transientState = newTransientValue;
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class InteractiveContext extends AbstractInteractive
            implements Serializable {

        private static final long serialVersionUID = -2686265776202582083L;

        private Object contextState;

        private String contentType;

        public InteractiveContext() {
        }

        public InteractiveContext(IHtmlRenderContext htmlRenderContext) {
            contextState = htmlRenderContext.saveRenderContextState();
            contentType = htmlRenderContext.getFacesContext()
                    .getResponseWriter().getContentType();
        }

        private InteractiveBuffer renderTree(FacesContext facesContext,
                UIComponent component, String componentId)
                throws WriterException {

            Writer buffer = new CharArrayWriter(TREE_BUFFER_INITIAL_SIZE);

            ResponseWriter newWriter = facesContext.getRenderKit()
                    .createResponseWriter(buffer, contentType,
                            AbstractHtmlService.RESPONSE_CHARSET);

            facesContext.setResponseWriter(newWriter);

            IComponentTreeRenderProcessor processor = ComponentTreeRenderProcessorFactory
                    .get(facesContext);

            HtmlRenderContext.restoreRenderContext(facesContext, contextState,
                    true);
            processor.encodeChildrenRecursive(component, componentId);

            String myBuffer = buffer.toString();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Render tree render mode : buffer=" + myBuffer
                        + " bytes");
            }

            return new InteractiveBuffer(facesContext, myBuffer);
        }

        public void restoreState(FacesContext context, Object state) {
            Object states[] = (Object[]) state;

            this.contextState = states[0];
            this.contentType = (String) states[1];
        }

        public Object saveState(FacesContext context) {
            return new Object[] { contextState, contentType };
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class InteractiveBuffer extends AbstractInteractive
            implements Externalizable {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -8520292309943559285L;

        private static final byte[] NO_GZIPPED_CONTENT = new byte[0];

        private static final int GZIP_MINIMUM_SIZE = 128;

        // Pour debug !
        private transient int id;

        private String content;

        private byte gzippedContent[];

        private boolean hasSaveStateFieldMarker;

        public InteractiveBuffer() {
            // Pour la déserialisation !
            if (LOG.isTraceEnabled()) {
                this.id = (BUFFER_ID++);
            }
        }

        public InteractiveBuffer(FacesContext facesContext,
                BodyContent bodyContent) {
            this(facesContext, bodyContent.getString());
        }

        public InteractiveBuffer(FacesContext facesContext, String content) {
            this();

            if (LOG.isTraceEnabled()) {
                LOG.trace("New interactiveBuffer[" + id + "]=\n" + content);
            }

            this.content = content;

            IComponentTreeRenderProcessor componentTreeRenderProcessor = ComponentTreeRenderProcessorFactory
                    .get(facesContext);

            this.hasSaveStateFieldMarker = componentTreeRenderProcessor
                    .hasSaveStateFieldMarker(content);

            /*
             * String content = null; byte gzipped[] = null; try {
             * BufferOutputStream bout = new BufferOutputStream(length);
             * OutputStream gzip = new GZIPOutputStream(bout, length); Writer
             * writer = new OutputStreamWriter(gzip,
             * AbstractHtmlService.RESPONSE_CHARSET);
             * 
             * bodyContent.writeOut(writer);
             * 
             * writer.close(); // ferme bout !
             * 
             * gzipped = bout.toByteArray();
             * 
             * if (gzipped.length < length) { content = null;
             * 
             * if (LOG.isInfoEnabled()) { LOG.info("Compression: original=" +
             * length + " z=" + gzipped.length + " (" + (gzipped.length * 100 /
             * length) + " %)"); } } else { if (LOG.isInfoEnabled()) { LOG
             * .info("Compression: bad performance, cancel compression ! (" +
             * (gzipped.length * 100 / length) + " %)"); } gzipped = null;
             * content = bodyContent.getString(); } } catch (IOException ex) {
             * LOG.error("Can GZIP buffer !", ex); }
             * 
             * this.content = content; this.gzipped = gzipped;
             */
        }

        public void sendBuffer(FacesContext facesContext) throws IOException {

            ServletResponse response = (ServletResponse) facesContext
                    .getExternalContext().getResponse();

            OutputStream responseStream = response.getOutputStream();
            OutputStream outputStream = responseStream;

            boolean useGzip = ConfiguredHttpServlet
                    .hasGzipSupport(facesContext);

            if (LOG.isTraceEnabled()) {
                LOG.trace("Send interactiveBuffer[" + id + "] (Use gzip: "
                        + useGzip + ")");
            }

            if (hasSaveStateFieldMarker == false) {
                if (gzippedContent != null && useGzip) {

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("Send an already gzipped interactive buffer. (no state field marker)");
                    }
                    ExtendedHttpServlet
                            .setGzipContentEncoding((HttpServletResponse) response);

                    outputStream.write(gzippedContent);
                    return;
                }
            }

            String content = this.content;
            if (content == null) {
                // Content est necessaire !

                InputStream ins = new ByteBufferInputStream(gzippedContent);
                GZIPInputStream gin = new GZIPInputStream(ins,
                        gzippedContent.length);

                Reader reader = new InputStreamReader(gin, "UTF-8");

                StringAppender sa = new StringAppender(
                        gzippedContent.length * 2);
                char buf[] = new char[4096];
                for (;;) {
                    int ret = reader.read(buf);
                    if (ret < 1) {
                        break;
                    }
                    sa.append(buf, 0, ret);
                }

                reader.close();

                content = sa.toString();
            }

            ByteBufferOutputStream bos = null;
            if (useGzip && content.length() > GZIP_MINIMUM_SIZE) {
                ConfiguredHttpServlet
                        .setGzipContentEncoding((HttpServletResponse) response);

                if (hasSaveStateFieldMarker == false) {
                    bos = new ByteBufferOutputStream(content.length());

                    outputStream = new GZIPOutputStream(bos, content.length());

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("GZip content of interactive buffer and keep result. (no state field marker)");
                    }
                } else {
                    outputStream = new GZIPOutputStream(outputStream, content
                            .length());

                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("GZip content of interactive buffer. (state field marker presn");
                    }
                }
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Dont't use GZip to send content.");
                }
            }

            Writer writer = new OutputStreamWriter(outputStream,
                    AbstractHtmlService.RESPONSE_CHARSET);

            if (hasSaveStateFieldMarker == false) {
                writer.write(content);

            } else {
                ComponentTreeRenderProcessorFactory.get(facesContext)
                        .writeFilteredContent(writer, content);
            }

            writer.close();

            outputStream.close();

            if (bos != null) {
                gzippedContent = bos.toByteArray();

                responseStream.write(gzippedContent);
            }
        }

        public void restoreState(FacesContext context, Object state) {
            Object states[] = (Object[]) state;
            hasSaveStateFieldMarker = (states[1] != null);

            if (states[0] instanceof byte[]) {
                gzippedContent = (byte[]) states[0];
                return;
            }

            content = (String) states[0];
            gzippedContent = NO_GZIPPED_CONTENT;
        }

        public Object saveState(FacesContext context) {
            if (gzippedContent == null) {
                // Test le GZIP

                if (LOG.isDebugEnabled()) {
                    LOG
                            .debug("saveState of interactive buffer (not already gzipped)");
                }

                int length = content.length();
                gzippedContent = NO_GZIPPED_CONTENT;
                if (length >= Constants.INTERACTIVE_RENDER_MINIMUM_GZIP_BUFFER_SIZE) {
                    try {
                        ByteBufferOutputStream bos = new ByteBufferOutputStream(
                                content.length());
                        GZIPOutputStream out = new GZIPOutputStream(bos, 4096);

                        out.write(content.getBytes("UTF-8"));

                        out.close();

                        byte gzipped[] = bos.toByteArray();
                        if (gzipped.length < length * 2) {
                            gzippedContent = gzipped;
                        }

                        if (LOG.isDebugEnabled()) {
                            if (gzippedContent.length > 0) {
                                LOG.debug("Compression: original=" + length
                                        + " z=" + gzipped.length + " ("
                                        + (gzipped.length * 100 / length)
                                        + " %)");

                            } else {
                                LOG
                                        .debug("Compression: bad performance, ignore compression ! ("
                                                + (gzipped.length * 100 / length)
                                                + " %)");

                            }
                        }
                    } catch (IOException ex) {
                        LOG.error("Can not compress async render buffer.", ex);
                    }
                } else {
                    if (LOG.isDebugEnabled()) {
                        LOG
                                .debug("Buffer is too small to try compression ! ("
                                        + length
                                        + "<"
                                        + Constants.INTERACTIVE_RENDER_MINIMUM_GZIP_BUFFER_SIZE
                                        + ")");

                    }
                }
            }

            Boolean hasObject = (hasSaveStateFieldMarker) ? Boolean.TRUE : null;

            if (gzippedContent.length > 0) {
                return new Object[] { gzippedContent, hasObject };
            }

            return new Object[] { content, hasObject };
        }

        public void readExternal(ObjectInput in) throws ClassNotFoundException,
                IOException {
            if (LOG.isDebugEnabled()) {
                LOG.debug("readExternal of interactive buffer.");
            }

            Object external = in.readObject();

            restoreState(null, external);
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            if (LOG.isDebugEnabled()) {
                LOG.debug("writeExternal of interactive buffer.");
            }

            Object external = saveState(null);

            out.writeObject(external);
        }
    }
}
