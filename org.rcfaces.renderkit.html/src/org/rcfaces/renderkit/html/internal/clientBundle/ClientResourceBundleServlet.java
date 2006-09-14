/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.clientBundle;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ServletTools;
import org.rcfaces.core.internal.webapp.IRepository;
import org.rcfaces.core.internal.webapp.RepositoryServlet;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientResourceBundleServlet extends RepositoryServlet {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 3676267778105553820L;

    private static final Log LOG = LogFactory
            .getLog(ClientResourceBundleServlet.class);

    private static final String BUNDLE_REPOSITORY_PROPERTY = "org.rcfaces.renderkit.html.internal.clientBundle.BUNDLE_REPOSITORY";

    private String clientBundleURI;

    public void init(ServletConfig config) throws ServletException {

        if (clientBundleURI == null) {
            clientBundleURI = ServletTools.computeResourceURI(config
                    .getServletContext(), null, getClass());
        }

        if (clientBundleURI == null) {
            LOG.error("Servlet '" + config.getServletName()
                    + "' is disabled because its URL can not be determined !");
            return;
        }

        LOG.info("ClientResourceBundle uri is '" + clientBundleURI + "'.");

        super.init(config);
    }

    protected boolean getVersionSupport() {
        return Constants.CLIENT_BUNDLE_VERSIONED_SUPPORT;
    }

    public static IClientBundleRepository getBundleRepository(
            FacesContext facesContext) {
        IClientBundleRepository bundleRepository = (IClientBundleRepository) facesContext
                .getExternalContext().getApplicationMap().get(
                        BUNDLE_REPOSITORY_PROPERTY);

        if (bundleRepository == null) {
            throw new FacesException(
                    "Bundle client repository is not initialized !");
        }

        return bundleRepository;
    }

    protected String getContentType(Record record) {
        String contentType = IHtmlRenderContext.JAVASCRIPT_TYPE;

        String charset = record.getCharset();
        if (charset == null) {
            return contentType;
        }

        return contentType + "; charset=" + charset;
    }

    protected String getParameterPrefix() {
        return Constants.getPackagePrefix();
    }

    protected IRepository initializeRepository(ServletConfig config) {
        IRepository bundleRepository = new ClientBundleRepository(config
                .getServletContext(), clientBundleURI, null);

        config.getServletContext().setAttribute(BUNDLE_REPOSITORY_PROPERTY,
                bundleRepository);

        return bundleRepository;
    }

    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (clientBundleURI == null) {
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        super.service(request, response);
    }

}
