/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.12  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.11  2006/01/03 15:21:40  oeuillot
 * Refonte du systeme de menuPopup !
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
 * Revision 1.8  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 * Revision 1.7  2004/08/31 15:30:33  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/31 08:29:47  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.AbstractReleasable;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractRequestContext extends AbstractReleasable
        implements IRequestContext {
    private static final String REVISION = "$Revision$";

    protected static final String EMPTY_PROPERTIES[] = new String[0];

    private final Map componentDatas = new HashMap(32);

    private IComponentData emptyComponentData;

    private FacesContext facesContext;

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
        this.componentDatas.clear(); // On ne sait jamais ....
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRequestContext#getFacesContext()
     */
    public final FacesContext getFacesContext() {
        return facesContext;
    }

    protected final void putComponentData(String key, Object data) {
        componentDatas.put(key, data);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IRequestContext#getComponentData(javax.faces.component.UIComponent)
     */
    public final IComponentData getComponentData(UIComponent component,
            String componentId) {
        Object data = componentDatas.get(componentId);

        if (data == null) {
            return emptyComponentData();
        }

        if (data instanceof IComponentData) {
            return (IComponentData) data;
        }

        data = getComponentData(component, componentId, data);
        if (data == null) {
            return emptyComponentData();
        }

        componentDatas.put(componentId, data);

        return (IComponentData) data;
    }

    protected IComponentData getComponentData(UIComponent component,
            String key, Object data) {
        return emptyComponentData();
    }

    protected IComponentData emptyComponentData() {
        if (emptyComponentData != null) {
            return emptyComponentData;
        }

        emptyComponentData = createEmptyComponentData();

        return emptyComponentData;
    }

    protected abstract IComponentData createEmptyComponentData();

    protected final String getKey(UIComponent component) {
        return component.getClientId(facesContext);
    }

    protected static abstract class AbstractComponentData extends
            AbstractProperties implements IComponentData {
        private static final String REVISION = "$Revision$";
    }

    public String getComponentId(UIComponent component) {
        return component.getClientId(getFacesContext());
    }

    public void release() {
        componentDatas.clear();

        super.release();
    }
}