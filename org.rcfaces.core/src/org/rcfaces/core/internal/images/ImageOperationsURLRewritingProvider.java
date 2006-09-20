/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/20 17:55:19  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/06/19 17:22:19  oeuillot
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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.rcfaces.core.image.IImageOperation;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.config.IProvidersRegistry;
import org.rcfaces.core.internal.rewriting.AbstractURLRewritingProvider;
import org.rcfaces.core.provider.IProvider;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.core.provider.ImageURLRewritingInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ImageOperationsURLRewritingProvider extends
        AbstractURLRewritingProvider {
    private static final String REVISION = "$Revision$";

    public static final String URL_REWRITING_SEPARATOR = "::";

    public abstract IImageOperation getImageOperation(String operationId);

    public ImageOperationsURLRewritingProvider(IProvider parent) {
        super(parent);
    }

    protected abstract String formatImageURL(FacesContext facesContext,
            String filter, String url, boolean mainURL,
            ImageURLRewritingInformation imageInformation);

    public final String computeURL(FacesContext facesContext,
            UIComponent component, int type, String attributeName, String url,
            String rootURL, IURLRewritingInformation rewritingInformation) {

        if (type != IURLRewritingProvider.IMAGE_URL_TYPE) {
            if (parent == null) {
                return url;
            }

            return parent.computeURL(facesContext, component, type,
                    attributeName, url, rootURL, rewritingInformation);
        }

        int idx = url.indexOf(URL_REWRITING_SEPARATOR);
        if (isProviderEnabled() == false) {
            if (idx >= 0) {
                return null;
            }

            // Pas de filtre ... donc on continue
            
            if (parent == null) {
                return url;
            }

            return parent.computeURL(facesContext, component, type,
                    attributeName, url, rootURL, rewritingInformation);
        }

        ImageURLRewritingInformation imageInformation = null;
        if (rewritingInformation instanceof ImageURLRewritingInformation) {
            imageInformation = (ImageURLRewritingInformation) rewritingInformation;
        }

        if (idx < 0) {
            if (parent == null) {

                if (imageInformation != null) {
                    imageInformation.setOriginalImageURL(url);
                    imageInformation.setRootImageURL(rootURL);

                    imageInformation.setContentType(getContentType(url));
                }
                return url;
            }

            return parent.computeURL(facesContext, component, type,
                    attributeName, url, rootURL, rewritingInformation);
        }

        if (imageInformation != null) {
            imageInformation.setOriginalImageURL(url);
            imageInformation.setRootImageURL(rootURL);
        }

        boolean mainURL = false;
        String filter;
        if (idx == url.length() - 2) { // Filtre tout seul !
            if (rootURL == null) {
                throw new FacesException("Can not get main image of '" + url
                        + "'.");
            }

            filter = url.substring(0, idx);
            url = rootURL;

        } else {
            filter = url.substring(0, idx);
            url = url.substring(idx + URL_REWRITING_SEPARATOR.length());
            mainURL = true;
        }

        if (imageInformation == null) {
            imageInformation = new ImageURLRewritingInformation();
        }

        String ret = formatImageURL(facesContext, filter, url, mainURL,
                imageInformation);

        if (parent == null) {
            return ret;
        }

        return parent.computeURL(facesContext, component, type, attributeName,
                ret, rootURL, imageInformation);
    }

    public static ImageOperationsURLRewritingProvider getInstance(
            ServletContext servletContext, ServletRequest request,
            ServletResponse response) {
        RcfacesContext cameliaContext = RcfacesContext.getInstance(
                servletContext, request, response);

        IProvidersRegistry providersRegistry = cameliaContext
                .getProvidersRegistry();

        ImageOperationsURLRewritingProvider imageFiltersRepository = (ImageOperationsURLRewritingProvider) providersRegistry
                .getProvider(IURLRewritingProvider.URL_REWRITING_PROVIDER_ID);

        return imageFiltersRepository;
    }

    public abstract boolean isProviderEnabled();

    public abstract String getContentType(String url);

    public abstract boolean isValidContenType(String contentType);

}
