/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.2  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.22  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.21  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS dï¿½tectï¿½s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.20  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.19  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.18  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.17  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.16  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.15  2006/01/03 15:21:39  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.14  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.13  2005/11/08 12:16:27  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cotï¿½ client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.12  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propriï¿½tï¿½s des composants
 *
 * Revision 1.11  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalitï¿½s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.10  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * Rï¿½ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.9  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Intï¿½gration des corrections de Didier
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.rewriting.AbstractURLRewritingProvider;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.provider.IURLRewritingProvider;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractCameliaRenderer extends Renderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public final void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {

        super.encodeBegin(context, component);

        IRenderContext renderContext = getRenderContext(context);

        String id = renderContext.getComponentId(context, component);

        renderContext.pushComponent(context, component, id);

        IComponentWriter writer = renderContext.getComponentWriter(context);
        try {
            encodeBegin(writer);

        } catch (RuntimeException e) {
            throw new WriterException("RuntimeException", e, component);
        }

        writer.flush();
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
    }

    protected abstract IRenderContext getRenderContext(FacesContext context);

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(context);

        IComponentWriter writer = renderContext.getComponentWriter(context);

        renderContext.encodeEnd(context, component);
        try {
            encodeEnd(writer);

        } catch (RuntimeException e) {
            throw new WriterException("RuntimeException", e, component);
        }

        writer.flush();

        super.encodeEnd(context, component);

        renderContext.popComponent(component);

    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
    }

    protected abstract IRequestContext getRequestContext(FacesContext context);

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public final void decode(FacesContext context, UIComponent component) {

        IRequestContext requestContext = getRequestContext(context);

        String id = requestContext.getComponentId(component);

        IComponentData componentData = requestContext.getComponentData(
                component, id);
        decode(requestContext, component, componentData);

        /*
         * String clientId = component.getClientId(context);
         * 
         * Map requestParameterMap = context.getExternalContext()
         * .getRequestParameterMap();
         * 
         * String value = (String) requestParameterMap.get(clientId); if (value ==
         * null) { if (requestParameterMap.get(clientId + ".x") == null &&
         * requestParameterMap.get(clientId + ".y") == null) { return; } }
         * 
         * String type = (String) component.getAttributes().get("type"); if
         * ((type != null) && (type.toLowerCase().equals("reset"))) { return; }
         * ActionEvent actionEvent = new ActionEvent(component);
         * component.queueEvent(actionEvent);
         * 
         * if (log.isDebugEnabled()) { log.debug("This command resulted in form
         * submission " + " ActionEvent queued " + actionEvent); } if
         * (log.isTraceEnabled()) { log.trace("End decoding component " +
         * component.getId()); }
         */
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
    }

    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#decodeChildren(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decodeChildren(FacesContext context, UIComponent component) {
        Iterator kids = component.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            decodeChild(context, component, kid);
        }
    }

    public void decodeChild(FacesContext context, UIComponent parent,
            UIComponent child) {
        child.processDecodes(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#decodeEnd(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     */
    public void decodeEnd(FacesContext context, UIComponent component) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRendererExtension#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return false;
    }

    protected String convertValue(FacesContext facesContext,
            UIComponent component, Object value) {
        if (component instanceof ValueHolder) {
            ValueHolder valueHolder = (ValueHolder) component;

            return ValuesTools.valueToString(valueHolder, facesContext);
        }

        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        return ValuesTools.convertStringToValue(context, component,
                submittedValue);
    }

    public static final String rewriteURL(
            IComponentRenderContext componentRenderContext, int type,
            String attributeName, String url, String rootURL) {
        if (Constants.URL_REWRITING_SUPPORT == false) {
            return url;
        }

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IURLRewritingProvider urlRewritingProvider;
        IRenderContext renderContext = componentRenderContext
                .getRenderContext();
        if (renderContext != null) {
            urlRewritingProvider = renderContext.getURLRewritingProvider();
        } else {
            urlRewritingProvider = AbstractURLRewritingProvider
                    .getInstance(facesContext.getExternalContext());
        }

        if (urlRewritingProvider == null) {
            return url;
        }

        return urlRewritingProvider.computeURL(facesContext,
                componentRenderContext.getComponent(), type, attributeName,
                url, rootURL, null);
    }
}