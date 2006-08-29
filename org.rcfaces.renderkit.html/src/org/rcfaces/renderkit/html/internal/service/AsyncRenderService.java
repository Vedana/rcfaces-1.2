/*
 * $Id$
 * 
 * $Log$
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.internal.service.AbstractAsyncRenderService;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.webapp.ExpirationHttpServlet;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class AsyncRenderService extends AbstractAsyncRenderService {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AsyncRenderService.class);

    private static int BUFFER_ID = 0;

    private static final String INTERACTIVE_KEY = "vfc.itrv";

    private static final String INTERACTIVE_RENDERER_PARAMETER = Constants
            .getPackagePrefix()
            + ".INTERACTIVE_RENDER";

    private static final int MINIMUM_GZIP_SIZE = 64;

    private transient boolean interactiveRender;

    private transient boolean useGzip;

    public void initialize(FacesContext facesContext) {
        useGzip = "true".equalsIgnoreCase(facesContext.getExternalContext()
                .getInitParameter(ExpirationHttpServlet.USE_GZIP_PARAMETER));

        interactiveRender = ("false".equalsIgnoreCase(facesContext
                .getExternalContext().getInitParameter(
                        INTERACTIVE_RENDERER_PARAMETER)) == false);

        if (interactiveRender) {
            LOG.info("Enable interactive render.");

        } else {
            LOG.info("Disable interactive render.");
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
            content.sendBuffer(facesContext, response);

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

    public void setContent(UIComponent component, BodyContent writer) {
        Map map = component.getAttributes();

        map.put(INTERACTIVE_KEY,
                new InteractiveBuffer(writer, canUseGzip(null)));
    }

    protected final boolean canUseGzip(FacesContext facesContext) {
        if (useGzip == false) {
            return false;
        }

        if (facesContext == null) {
            return true;
        }

        // On verifie que le browser le supporte
        return ExpirationHttpServlet.hasGzipSupport(facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static final class InteractiveBuffer implements Externalizable {

        // Pour debug !
        private transient int id;

        private transient final String content;

        private transient final byte gzipped[];

        private transient final boolean useGzip;

        public InteractiveBuffer(BodyContent bodyContent, boolean useGzip) {
            this.useGzip = useGzip;

            int length = bodyContent.getBufferSize()
                    - bodyContent.getRemaining();

            if (LOG.isTraceEnabled()) {
                this.id = (BUFFER_ID++);

                LOG.trace("New interactiveBuffer[" + id + "] (useGzip="
                        + useGzip + ") :\n" + bodyContent.getString());
            }

            if (length < MINIMUM_GZIP_SIZE || useGzip == false) {
                this.content = bodyContent.getString();
                this.gzipped = null;
                return;
            }

            String content = null;
            byte gzipped[] = null;
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream(length);
                OutputStream gzip = new GZIPOutputStream(bout, length);
                Writer writer = new OutputStreamWriter(gzip,
                        AbstractHtmlService.RESPONSE_CHARSET);

                bodyContent.writeOut(writer);

                writer.close(); // ferme bout !

                gzipped = bout.toByteArray();

                if (gzipped.length < length) {
                    content = null;

                    LOG.info("Compression: original=" + length + " z="
                            + gzipped.length + " ("
                            + (gzipped.length * 100 / length) + " %)");
                } else {
                    LOG
                            .info("Compression: bad performance, cancel compression ! ("
                                    + (gzipped.length * 100 / length) + " %)");
                    gzipped = null;
                    content = bodyContent.getString();
                }

            } catch (IOException ex) {
                LOG.error("Can GZIP buffer !", ex);
            }

            this.content = content;
            this.gzipped = gzipped;
        }

        public void readExternal(ObjectInput in) {
            // Rien !
        }

        public void writeExternal(ObjectOutput out) {
            // Rien !
        }

        public void sendBuffer(FacesContext facesContext,
                ServletResponse response) throws IOException {

            boolean useGzip = this.useGzip;
            if (useGzip) {
                useGzip = ExpirationHttpServlet.hasGzipSupport(facesContext);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace("Send interactiveBuffer[" + id + "] (gzip support="
                        + useGzip + ")");
            }

            if (gzipped != null) {
                if (useGzip) {
                    ExpirationHttpServlet
                            .setGzipContentEncoding((HttpServletResponse) response);

                    OutputStream out = response.getOutputStream();
                    out.write(gzipped);

                    return;
                }

                // Ca peut toujours arriver !

                OutputStream out = response.getOutputStream();

                ByteArrayInputStream bin = new ByteArrayInputStream(gzipped);
                GZIPInputStream gzip = new GZIPInputStream(bin, gzipped.length);
                try {
                    byte bout[] = new byte[4096];
                    for (;;) {
                        int ret = gzip.read(bout);
                        if (ret < 1) {
                            break;
                        }

                        out.write(bout, 0, ret);
                    }

                } finally {
                    gzip.close();
                }

                return;
            }

            PrintWriter pw = response.getWriter();
            pw.write(content);
        }
    }

    /*
     * public static void main(String args[]) throws Exception { for (int i = 0;
     * i < 64; i++) { ByteArrayOutputStream out = new ByteArrayOutputStream();
     * GZIPOutputStream g = new GZIPOutputStream(out);
     * 
     * for (int j = 0; j <= i; j++) { g.write(j); } g.close(); out.close();
     * 
     * System.out.println("#" + i + " " + out.toByteArray().length); } }
     */
}
