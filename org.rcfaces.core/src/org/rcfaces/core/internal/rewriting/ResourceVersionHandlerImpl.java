/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/20 17:55:19  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 */
package org.rcfaces.core.internal.rewriting;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.config.RcfacesContextImpl;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.provider.IProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ResourceVersionHandlerImpl extends AbstractURLRewritingProvider
        implements IResourceVersionHandler {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ResourceVersionHandlerImpl.class);

    private static final String APPLICATION_VERSION_URI_PARAMETER = Constants
            .getPackagePrefix()
            + ".APPLICATION_VERSION";

    private static final String HASHCODE_KEYWORD = "hashcode";

    private static final String NOW_KEYWORD = "now";

    private static final int HASHCODE_CACHE_INITIAL_SIZE = 128;

    private final String prefixURI;

    private final String version;

    private Map hashCodeByURL = null;

    public ResourceVersionHandlerImpl(IProvider parent) {
        super(parent);

        FacesContext facesContext = FacesContext.getCurrentInstance();

        ExternalContext externalContext = facesContext.getExternalContext();

        String applicationVersionURI = ApplicationVersionServlet
                .getApplicationVersionURI(externalContext.getApplicationMap());
        if (applicationVersionURI == null) {
            LOG
                    .debug("Can not find application version pattern ! (Servlet can be not defined)");
            this.prefixURI = null;
            this.version = null;
            return;
        }

        String version = externalContext
                .getInitParameter(APPLICATION_VERSION_URI_PARAMETER);
        if (version == null || "none".equalsIgnoreCase(version)) {
            this.prefixURI = null;
            this.version = null;
            LOG.info("Disable application version rewriting engine.");
            return;
        }
        if (HASHCODE_KEYWORD.equalsIgnoreCase(version)) {
            version = HASHCODE_KEYWORD;

            prefixURI = applicationVersionURI;
            hashCodeByURL = new HashMap(HASHCODE_CACHE_INITIAL_SIZE);

            LOG.info("Use resource hashcode as the resource version.");

        } else {
            if (NOW_KEYWORD.equalsIgnoreCase(version)) {
                version = "0." + System.currentTimeMillis();

                LOG.info("Set resource version to a random value: '" + version
                        + "'.");

            } else {
                LOG.info("Set resource version to specified value: '" + version
                        + "'.");
            }

            prefixURI = applicationVersionURI + "/" + version;
            LOG.info("Set resource version url to '" + prefixURI + "'.");
        }

        this.version = version;

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);
        ((RcfacesContextImpl) rcfacesContext).setResourceVersionHandler(this);
    }

    public String computeURL(FacesContext facesContext, UIComponent component,
            int type, String attributeName, String url, String rootURL,
            IURLRewritingInformation rewritingInformation) {

        if (prefixURI == null || (url==null || url.length() < 1)) {
            return url;
        }

        if (rewritingInformation != null && rewritingInformation.isVersioned()) {
            return url;
        }

        StringAppender sa = new StringAppender(256);

        ExternalContext externalContext = facesContext.getExternalContext();
        String requestContextPath = externalContext.getRequestContextPath();
        if (requestContextPath.equals("/") == false) {
            sa.append(requestContextPath);
        }

        IProcessContext extContext = AbstractProcessContext
                .getProcessContext(facesContext);

        String absolutePath = extContext.getAbsolutePath(url, false);

        sa.append(prefixURI);

        if (hashCodeByURL != null) {
            String etag = getResourceVersion(facesContext, absolutePath, null);

            sa.append('/');
            sa.append(etag);
        }

        sa.append(absolutePath);
        return sa.toString();
    }

    public String getResourceVersion(FacesContext facesContext,
            String absolutePath, URL url) {
        if (hashCodeByURL == null) {
            return version;
        }

        ResourceHashCode tag;
        synchronized (hashCodeByURL) {
            tag = (ResourceHashCode) hashCodeByURL.get(absolutePath);
            if (tag == null) {
                tag = new ResourceHashCode(absolutePath, url);
                hashCodeByURL.put(absolutePath, tag);
            }
        }

        return tag.getHashCode(facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ResourceHashCode {

        private static final String REVISION = "$Revision$";

        private static final String NULL_HASH_CODE = "ERR";

        private final String absolutePath;

        private final URL url;

        private String etag;

        public ResourceHashCode(String absolutePath, URL url) {
            this.absolutePath = absolutePath;
            this.url = url;
        }

        public synchronized String getHashCode(FacesContext facesContext) {
            if (etag == null) {
                etag = HashCodeTools.computeURLFormat(facesContext, absolutePath,
                        url, Constants.VERSIONED_URI_HASHCODE_MAX_SIZE);
                if (etag == null) {
                    etag = NULL_HASH_CODE;
                }
            }
            return etag;
        }

    }
}
