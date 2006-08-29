/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.internal.images;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.codec.URLFormCodec;
import org.rcfaces.core.internal.images.operation.BrithnessOperation;
import org.rcfaces.core.internal.images.operation.ContrastBrithnessOperation;
import org.rcfaces.core.internal.images.operation.ContrastOperation;
import org.rcfaces.core.internal.images.operation.DisableOperation;
import org.rcfaces.core.internal.images.operation.GrayOperation;
import org.rcfaces.core.internal.images.operation.HoverOperation;
import org.rcfaces.core.internal.images.operation.ResizeOperation;
import org.rcfaces.core.internal.images.operation.ScaleOperation;
import org.rcfaces.core.internal.images.operation.SelectedOperation;
import org.rcfaces.core.internal.images.operation.SetSizeOperation;
import org.rcfaces.core.provider.IProvider;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageFiltersRepositoryImpl extends ImageFiltersRepository {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ImageFiltersRepositoryImpl.class);

    private static final String URL_ENCODER_CHARSET = "UTF-8";

    private static final String IMAGE_REPOSITORY_SERVLET_URL = "/image-filters";

    private final Map operationsById = new HashMap(32);

    private final Map validContentTypes = new HashMap(8);

    private FileNameMap fileNameMap;

    /*
     * private static final Map CONTENT_TYPE_BY_EXTENSION = new HashMap(10); {
     * CONTENT_TYPE_BY_EXTENSION.put("gif", "image/gif");
     * CONTENT_TYPE_BY_EXTENSION.put("jpg", "image/jpeg");
     * CONTENT_TYPE_BY_EXTENSION.put("jpeg", "image/jpg");
     * CONTENT_TYPE_BY_EXTENSION.put("png", "image/png"); }
     */

    public ImageFiltersRepositoryImpl(IProvider provider) {
        super(provider);

        fileNameMap = URLConnection.getFileNameMap();

        operationsById.put("disabled", new DisableOperation());
        operationsById.put("gray", new GrayOperation());
        operationsById.put("contrast", new ContrastOperation());
        operationsById.put("brithness", new BrithnessOperation());
        operationsById.put("colorRescale", new ContrastBrithnessOperation());
        operationsById.put("hover", new HoverOperation());
        operationsById.put("selected", new SelectedOperation());
        operationsById.put("scale", new ScaleOperation());
        operationsById.put("resize", new ResizeOperation());
        operationsById.put("setSize", new SetSizeOperation());
        // operationsById.put("icon", new IEIconOperation());

        for (Iterator it = operationsById.values().iterator(); it.hasNext();) {
            IImageOperation imageOperation = (IImageOperation) it.next();

            imageOperation.configure(Collections.EMPTY_MAP);
        }
    }

    public IImageOperation getImageOperation(String operationId) {
        IImageOperation imageOperation = (IImageOperation) operationsById
                .get(operationId);

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("Operation id='" + operationId + "' => "
                            + imageOperation);
        }

        return imageOperation;
    }

    public String formatImageURL(FacesContext facesContext, String filter,
            String url, boolean mainURL, ImageInformation imageInformation) {

        boolean addFilterServletPath = true;

        String contentType = getContentType(url);

        if (imageInformation != null) {
            imageInformation.setMimeType(contentType);
        }

        if (isValidContenType(contentType) == false) {
            LOG.info("Not supported content type '" + contentType
                    + "' for url '" + url + "'.");

            if (mainURL == false) {
                return null;
            }

            addFilterServletPath = false;
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        StringAppender sb = new StringAppender(256);
        sb.append(externalContext.getRequestContextPath());

        if (addFilterServletPath) {
            sb.append(IMAGE_REPOSITORY_SERVLET_URL);
            sb.append('/');

            sb.append(filter);
        }

        String servletPath = externalContext.getRequestServletPath();
        if (servletPath != null) {
            int idx = servletPath.lastIndexOf('/');
            if (idx > 0) {
                sb.append(servletPath.substring(0, idx));
            }
        }

        sb.append('/');
        URLFormCodec.appendURL(sb, url);

        String ret = sb.toString();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Format image '" + url + "' with filter '" + filter
                    + "' returns url '" + ret + "'.");
        }

        return ret;
    }

    public String getContentType(String url) {
        int idx = url.lastIndexOf('/');
        if (idx >= 0) {
            url = url.substring(idx + 1);
        }

        return fileNameMap.getContentTypeFor(url);
    }

    public boolean isValidContenType(String contentType) {
        Boolean valid;

        synchronized (validContentTypes) {
            valid = (Boolean) validContentTypes.get(contentType);

            if (valid == null) {
                Iterator it = ImageIO.getImageWritersByMIMEType(contentType);
                valid = (it.hasNext()) ? Boolean.TRUE : Boolean.FALSE;

                validContentTypes.put(contentType, valid);
            }
        }

        return valid.booleanValue();
    }
}
