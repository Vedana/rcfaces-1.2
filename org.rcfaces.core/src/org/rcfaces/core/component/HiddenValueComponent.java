package org.rcfaces.core.component;

import java.util.Collections;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.IPropertyChangeEventCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.CameliaInputComponent;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.manager.IClientDataManager;
import org.rcfaces.core.internal.manager.IServerDataManager;
import org.rcfaces.core.internal.tools.ComponentTools;

public class HiddenValueComponent extends CameliaInputComponent implements
        IClientDataCapability, IServerDataCapability,
        IPropertyChangeEventCapability, IImmediateCapability,
        IServerDataManager, IClientDataManager {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.hiddenValue";

    public HiddenValueComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public HiddenValueComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final void setClientData(String name, ValueBinding value) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", true);

        dataMapAccessor.setData(name, value, null);

    }

    public final void setServerData(String name, ValueBinding value) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", true);

        dataMapAccessor.setData(name, value, null);

    }

    public final void setImmediate(ValueBinding immediate) {

        setValueBinding("immediate", immediate);

    }

    public final String getClientData(String name, FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return (String) dataMapAccessor.getData(name, facesContext);

    }

    public final Object getServerData(String name, FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return dataMapAccessor.getData(name, facesContext);

    }

    public final Map getClientDataMap(FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "clientData", false);
        if (dataMapAccessor == null) {
            return Collections.EMPTY_MAP;
        }

        return dataMapAccessor.getDataMap(facesContext);

    }

    public final Map getServerDataMap(FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "serverData", false);
        if (dataMapAccessor == null) {
            return Collections.EMPTY_MAP;
        }

        Map map = dataMapAccessor.getDataMap(facesContext);
        if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
            if (map.isEmpty()) {
                return Collections.EMPTY_MAP;
            }
            map = Collections.unmodifiableMap(map);
        }
        return map;

    }

    public final String[] listClientDataKeys(FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", false);
        if (dataMapAccessor == null) {
            return ComponentTools.STRING_EMPTY_ARRAY;
        }

        return dataMapAccessor.listDataKeys(facesContext);

    }

    public final String[] listServerDataKeys(FacesContext facesContext) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", false);
        if (dataMapAccessor == null) {
            return ComponentTools.STRING_EMPTY_ARRAY;
        }

        return dataMapAccessor.listDataKeys(facesContext);

    }

    public final Map getClientDataMap() {

        return getClientDataMap(null);

    }

    public final int getClientDataCount() {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", false);
        if (dataMapAccessor == null) {
            return 0;
        }

        return dataMapAccessor.getDataCount();

    }

    public final String getClientData(String name) {

        return getClientData(name, null);

    }

    public final String[] listClientDataKeys() {

        return listClientDataKeys(null);

    }

    public final String removeClientData(String name) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return (String) dataMapAccessor.removeData(name, null);

    }

    public final String setClientData(String name, String value) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "clientData", true);

        return (String) dataMapAccessor.setData(name, value, null);

    }

    public final Object getServerData(String name) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return dataMapAccessor.getData(name, null);

    }

    public final Object removeServerData(String name) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return dataMapAccessor.removeData(name, null);

    }

    public final Map getServerDataMap() {

        return getServerDataMap(null);

    }

    public final int getServerDataCount() {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", false);
        if (dataMapAccessor == null) {
            return 0;
        }

        return dataMapAccessor.getDataCount();

    }

    public final Object setServerData(String name, Object value) {

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(null,
                "serverData", true);

        return dataMapAccessor.setData(name, value, null);

    }

    public final String[] listServerDataKeys() {

        return listServerDataKeys(null);

    }

    public final void addPropertyChangeListener(
            org.rcfaces.core.event.IPropertyChangeListener listener) {
        addFacesListener(listener);
    }

    public final void removePropertyChangeListener(
            org.rcfaces.core.event.IPropertyChangeListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listPropertyChangeListeners() {
        return getFacesListeners(org.rcfaces.core.event.IPropertyChangeListener.class);
    }

    public void release() {
        super.release();
    }
}
