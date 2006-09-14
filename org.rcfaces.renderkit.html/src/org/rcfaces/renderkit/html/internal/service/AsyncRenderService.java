/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.3  2006/09/05 08:57:13  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.1  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/10/28 14:41:49  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.2  2005/10/05 14:34:18  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.renderkit.html.internal.service;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.application.StateManager.SerializedView;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.lang.ByteBufferInputStream;
import org.rcfaces.core.internal.lang.ByteBufferOutputStream;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.StateFieldMarkerTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.internal.webapp.ExtendedHttpServlet;
import org.rcfaces.renderkit.html.internal.Constants;
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
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        String componentId = (String) parameters.get("id");
        if (componentId == null) {
            AbstractHtmlService.sendJsError(facesContext,
                    "Can not find 'id' parameter.");
            return;
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();

        UIComponent component = ComponentTools.getForComponent(facesContext,
                componentId, viewRoot);
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
        if ((object instanceof InteractiveBuffer) == false) {
            AbstractHtmlService.sendJsError(facesContext,
                    "Object is not ready ! (id='" + componentId + "').");
            return;
        }

        InteractiveBuffer content = (InteractiveBuffer) object;
        if (content == null) {
            AbstractHtmlService.sendJsError(facesContext,
                    "No content for component id='" + componentId + "'.");
            return;
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        AbstractHtmlService.setNoCache(response);
        response.setContentType(IHtmlRenderContext.HTML_TYPE + "; charset="
                + AbstractHtmlService.RESPONSE_CHARSET);

        try {
            content.sendBuffer(facesContext);

        } catch (IOException ex) {
            throw new FacesException(
                    "Can not write async content of component id='"
                            + componentId + "'.", ex);
        }

        facesContext.responseComplete();
    }

    public static void setAsyncRenderer(UIComponent component,
            boolean enableAsyncRender) {
        Map map = component.getAttributes();
        if (enableAsyncRender == false) {
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

        map.put(INTERACTIVE_KEY, Boolean.TRUE);
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
    public static final class InteractiveBuffer implements StateHolder,
            Externalizable {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -8520292309943559285L;

        private static final byte[] NO_GZIPPED_CONTENT = new byte[0];

        // Pour debug !
        private transient int id;

        private transient boolean transientValue;

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
            this();

            if (LOG.isTraceEnabled()) {
                LOG.trace("New interactiveBuffer[" + id + "]=\n"
                        + bodyContent.getString());
            }

            this.content = bodyContent.getString();

            String saveStateFieldMarker = StateFieldMarkerTools
                    .getStateFieldMarker(facesContext);
            if (saveStateFieldMarker != null
                    && saveStateFieldMarker.length() > 0) {

                if (content.indexOf(saveStateFieldMarker) >= 0) {
                    this.hasSaveStateFieldMarker = true;
                }
            }

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
            if (useGzip) {
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
                String saveStateFieldMarker = StateFieldMarkerTools
                        .getStateFieldMarker(facesContext);
                if (saveStateFieldMarker == null) {
                    throw new FacesException(
                            "Save state field marker is null !");
                }

                StateManager stateManager = facesContext.getApplication()
                        .getStateManager();

                SerializedView serializedView = stateManager
                        .saveSerializedView(facesContext);

                String saveValue = StateFieldMarkerTools.getStateValue(
                        facesContext, serializedView);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Save value=" + saveValue);
                }

                for (int start = 0;;) {
                    int offset = content.indexOf(saveStateFieldMarker, start);
                    if (offset < 0) {
                        if (start < content.length()) {
                            writer.write(content.substring(start));
                        }
                        break;
                    }
                    if (offset > start) {
                        writer.write(content.substring(start, offset));
                    }
                    writer.write(saveValue);
                    start = offset + saveStateFieldMarker.length();
                }
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

        public boolean isTransient() {
            return transientValue;
        }

        public void setTransient(boolean newTransientValue) {
            transientValue = newTransientValue;
        }

        public void readExternal(ObjectInput in) {
            // Pas de serialization !
            if (LOG.isDebugEnabled()) {
                LOG.debug("readExternal of interactive buffer.");
            }
        }

        public void writeExternal(ObjectOutput out) {
            // Pas de serialization !
            if (LOG.isDebugEnabled()) {
                LOG.debug("writeExternal of interactive buffer.");
            }

        }
    }
}
