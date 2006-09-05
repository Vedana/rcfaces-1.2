/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 */
package org.rcfaces.core.internal.rewriting;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.codec.StringAppender;
import org.rcfaces.core.internal.renderkit.AbstractProcessContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.provider.IProvider;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ApplicationVersionURLRewritingProvider extends
        AbstractURLRewritingProvider {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ApplicationVersionURLRewritingProvider.class);

    private static final String APPLICATION_VERSION_URI_PARAMETER = Constants
            .getPackagePrefix()
            + ".APPLICATION_VERSION";

    private final String prefixURI;

    public ApplicationVersionURLRewritingProvider(IProvider parent) {
        super(parent);

        FacesContext facesContext = FacesContext.getCurrentInstance();

        ExternalContext externalContext = facesContext.getExternalContext();

        String version = externalContext
                .getInitParameter(APPLICATION_VERSION_URI_PARAMETER);
        if (version == null || "none".equalsIgnoreCase(version)) {
            prefixURI = null;
            LOG.info("Disable application version rewriting engine.");
            return;
        }

        String applicationVersionURI = ApplicationVersionServlet
                .getApplicationVersionURI(externalContext.getApplicationMap());
        if (applicationVersionURI == null) {
            LOG.error("Can not find application version pattern !");
            prefixURI = null;
            return;
        }

        prefixURI = applicationVersionURI + "/" + version;
        LOG.info("Set application version url to '" + prefixURI + "'.");
    }

    public String computeURL(FacesContext facesContext, UIComponent component,
            int type, String attributeName, String attributeValue,
            String rootURL, IURLRewritingInformation rewritingInformation) {

        if (prefixURI == null || attributeValue.length() < 1) {
            return attributeValue;
        }

        if (attributeValue.charAt(0) == '/') {
            // C'est en dur !
            int idx = attributeValue.indexOf('/', 1);

            return attributeValue.substring(0, idx) + prefixURI
                    + attributeValue.substring(idx);
        }

        StringAppender sa = new StringAppender(256);

        ExternalContext externalContext = facesContext.getExternalContext();
        String requestContextPath = externalContext.getRequestContextPath();
        if (requestContextPath.equals("/") == false) {
            sa.append(requestContextPath);
        }
        sa.append(prefixURI);

        IProcessContext extContext = AbstractProcessContext
                .getProcessContext(externalContext);

        String absolutePath = extContext.getAbsolutePath(attributeValue, false);

        sa.append(absolutePath);
        return sa.toString();
    }
}
