/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.21  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.20  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.19  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.18  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.17  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.16  2006/01/03 15:21:39  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.15  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.14  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.13  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.12  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.11  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.10  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.9  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.7  2004/09/01 13:47:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.AbstractReleasable;
import org.rcfaces.core.internal.rewriting.AbstractURLRewritingProvider;
import org.rcfaces.core.provider.IURLRewritingProvider;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractRenderContext extends AbstractReleasable
        implements IRenderContext {
    private static final String REVISION = "$Revision$";

    private static final int COMPONENT_DEPTH = 16;

    private final List stack = new ArrayList(COMPONENT_DEPTH * 3);

    private Map attributes;

    private IURLRewritingProvider urlRewritingProvider;

    protected AbstractRenderContext() {
    }

    protected void initialize(FacesContext facesContext) {
        urlRewritingProvider = AbstractURLRewritingProvider
                .getInstance(facesContext.getExternalContext());
    }

    public String getComponentId(FacesContext facesContext,
            UIComponent component) {
        return component.getClientId(facesContext);
    }

    public String computeBrotherComponentId(FacesContext facesContext,
            UIComponent brotherComponent, String componentId) {

        if (componentId.length() > 0
                && componentId.charAt(0) == NamingContainer.SEPARATOR_CHAR) {
            return componentId;
        }

        String brotherId = getComponentId(facesContext, brotherComponent);

        if (brotherId == null) {
            return null;
        }

        int idx = brotherId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx < 0) {
            return componentId;
        }

        brotherId = brotherId.substring(0, idx + 1);

        return brotherId + componentId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#pushComponent(javax.faces.component.UIComponent)
     */
    public void pushComponent(FacesContext facesContext, UIComponent component,
            String componentId) {
        stack.add(component);
        stack.add(componentId);
        stack.add(Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#popComponent(javax.faces.component.UIComponent)
     */
    public void popComponent(UIComponent component) {

        int level = getStackLevel();
        stack.remove(level);
        stack.remove(level);
        Object componentContextAttributes = stack.remove(level);
        if (componentContextAttributes != Boolean.FALSE) {
            releaseMap((Map) componentContextAttributes);
        }

        /*
         * On ne fait pas ca ... Car il y a peut etre d'autres composants
         * "freres" Camelia
         * 
         * if (getStackLevel() < 0) { release(); }
         */
    }

    public void release() {
        if (attributes != null) {
            releaseComponentAttributes(attributes);
            releaseMap(attributes);
            attributes = null;
        }

        super.release();
    }

    protected void releaseComponentAttributes(Map map) {
    }

    private void releaseMap(Map map) {

    }

    protected int getStackLevel() {
        return stack.size() - 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#getComponentContext()
     */
    public final Object getComponentContextAttribute(String key) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            return null;
        }

        Map map = (Map) object;

        return map.get(key);
    }

    public final boolean containsComponentContextAttribute(String key) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            return false;
        }

        Map map = (Map) object;

        return map.containsKey(key);
    }

    public final Object setComponentContextAttribute(String key, Object value) {
        int componentContextLevel = getStackLevel() + 2;

        Object object = stack.get(componentContextLevel);
        if (object == Boolean.FALSE) {
            object = new HashMap();
            stack.set(componentContextLevel, object);
        }

        Map map = (Map) object;

        return map.put(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#getAttribute(java.lang.String)
     */
    public final Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRenderContext#setAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public final Object setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap();
        }

        return attributes.put(key, value);
    }

    public final UIComponent getComponent() {

        int level = getStackLevel();

        return (UIComponent) stack.get(level);
    }

    public final String getComponentId() {

        int level = getStackLevel();

        return (String) stack.get(level + 1);
    }

    public final IComponentWriter getComponentWriter(FacesContext facesContext) {
        return createWriter(facesContext, getComponent());
    }

    protected abstract IComponentWriter createWriter(FacesContext facesContext,
            UIComponent component);

    public void encodeEnd(FacesContext facesContext, UIComponent component) {
    }

    public IURLRewritingProvider getURLRewritingProvider() {
        return urlRewritingProvider;
    }

}