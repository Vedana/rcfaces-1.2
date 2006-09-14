/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.2  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentRenderContext implements
        IComponentRenderContext {
    private static final String REVISION = "$Revision$";

    private final UIComponent component;

    private final String componentId;

    private FacesContext facesContext;

    private Map attributes;

    protected AbstractComponentRenderContext(FacesContext facesContext,
            UIComponent component, String componentId) {
        this.facesContext = facesContext;
        this.component = component;
        this.componentId = componentId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentRenderContext#getFacesContext()
     */
    public final FacesContext getFacesContext() {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }
        return facesContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentRenderContext#getComponent()
     */
    public final UIComponent getComponent() {
        return component;
    }

    public final String getComponentId() {
        return componentId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentRenderContext#getAttribute(java.lang.String)
     */
    public Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(key);
    }

    public boolean containsAttribute(String key) {
        if (attributes == null) {
            return false;
        }

        return attributes.containsKey(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentRenderContext#setAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public Object setAttribute(String key, Object value) {
        if (attributes == null) {
            attributes = new HashMap();
        }

        return attributes.put(key, value);
    }

    public Object removeAttribute(String key) {
        if (attributes == null) {
            return null;
        }

        return attributes.remove(key);
    }

}
