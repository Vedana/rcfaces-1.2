/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.AbstractReleasable;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
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
