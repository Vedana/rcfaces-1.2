/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
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
package org.rcfaces.core.internal.rewriting;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.config.IProvidersRegistry;
import org.rcfaces.core.provider.IProvider;
import org.rcfaces.core.provider.IURLRewritingProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractURLRewritingProvider implements
        IURLRewritingProvider {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractURLRewritingProvider.class);

    private static final String URL_REWRITING_SUPPORT_PARAMETER = Constants
            .getPackagePrefix()
            + ".URL_REWRITING_SUPPORT";

    private static final String URL_REWRITING_SUPPORT_PROPERTY = "org.rcfaces.core.internal.config.URL_REWRITING_SUPPORT";

    protected final IURLRewritingProvider parent;

    protected AbstractURLRewritingProvider(IProvider parent) {
        this.parent = (IURLRewritingProvider) parent;
    }

    public void configureRules(Digester digester) {
    }

    public static final boolean isURLRewritingEnabled(
            ExternalContext externalContext) {
        if (Constants.URL_REWRITING_SUPPORT == false) {
            return false;
        }

        if (externalContext == null) {
            externalContext = FacesContext.getCurrentInstance()
                    .getExternalContext();
        }

        Map applicationMap = externalContext.getApplicationMap();
        Boolean b = (Boolean) applicationMap
                .get(URL_REWRITING_SUPPORT_PROPERTY);
        if (b != null) {
            return b.booleanValue();
        }

        Map initApplicationMap = externalContext.getInitParameterMap();

        boolean enable = "false".equalsIgnoreCase((String) initApplicationMap
                .get(URL_REWRITING_SUPPORT_PARAMETER)) == false;

        if (LOG.isDebugEnabled()) {
            LOG
                    .debug("URL rewriting support (maybe specified by parameter '"
                            + URL_REWRITING_SUPPORT_PARAMETER
                            + "') is "
                            + enable + ".");
        }

        applicationMap.put(URL_REWRITING_SUPPORT_PROPERTY, Boolean
                .valueOf(enable));

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
            if (LOG.isDebugEnabled()) {
                LOG.debug("Can not find url rewritring provider. id="
                        + IURLRewritingProvider.URL_REWRITING_PROVIDER_ID);
            }

            return null;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("URL rewriting support = " + urlRewritingProvider);
        }

        return urlRewritingProvider;
    }
}
