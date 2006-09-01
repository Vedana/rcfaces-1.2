/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.core.internal.config;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.provider.IURLRewritingProvider;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractURLRewritingProvider implements
        IURLRewritingProvider {

    private static final Log LOG = LogFactory
            .getLog(AbstractURLRewritingProvider.class);

    private static final String URL_REWRITING_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".URL_REWRITING_SUPPORT";

    public static final boolean isURLRewritingEnabled(
            ExternalContext externalContext) {
        if (Constants.URL_REWRITING_SUPPORT == false) {
            return false;
        }

        if (externalContext == null) {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
        }

        Map applicationMap = externalContext.getInitParameterMap();

        boolean enable = "false".equalsIgnoreCase((String) applicationMap
                .get(URL_REWRITING_SUPPORT_PARAMETER)) == false;

        if (LOG.isDebugEnabled()) {
            LOG.debug("URL rewriting support specified by parameter is "
                    + enable);
        }

        return enable;
    }

    public static IURLRewritingProvider getInstance(
            ExternalContext externalContext) {
        if (Constants.URL_REWRITING_SUPPORT == false) {
            return null;
        }

        if (externalContext == null) {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
        }

        if (isURLRewritingEnabled(externalContext) == false) {
            return null;
        }

        IProvidersRegistry providersRegistry = RcfacesContext.getInstance(
                externalContext).getProvidersRegistry();

        IURLRewritingProvider urlRewritingProvider = (IURLRewritingProvider) providersRegistry
                .getProvider(IURLRewritingProvider.URL_REWRITING_PROVIDER_ID);

        if (urlRewritingProvider == null) {
            LOG.error("Can not find url rewritring provider. id="
                    + IURLRewritingProvider.URL_REWRITING_PROVIDER_ID);

            return null;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("URL rewriting support = " + urlRewritingProvider);
        }

        return urlRewritingProvider;
    }
}
