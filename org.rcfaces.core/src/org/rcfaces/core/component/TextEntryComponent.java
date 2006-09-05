package org.rcfaces.core.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAutoTabCapability;
import org.rcfaces.core.component.capability.IClientValidationCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.IFocusStyleClassCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IValueChangeEventCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.AbstractInputComponent;
import org.rcfaces.core.internal.component.IDataMapAccessor;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.tools.MenuTools;

public class TextEntryComponent extends AbstractInputComponent implements
        IRequiredCapability, IAutoTabCapability, ITextCapability,
        IEmptyMessageCapability, IReadOnlyCapability,
        IValueChangeEventCapability, IMenuCapability,
        IFocusStyleClassCapability, IClientValidationCapability,
        ISelectionEventCapability, IValidationParameters {

    public static final String COMPONENT_TYPE = "org.rcfaces.core.textEntry";

    public TextEntryComponent() {
        setRendererType(COMPONENT_TYPE);
    }

    public TextEntryComponent(String componentId) {
        this();
        setId(componentId);
    }

    public final boolean isClientSideValidationParameter(String name) {

        return isClientSideValidationParameter(name, null);

    }

    public final Map getValidationParametersMap() {

        return getValidationParametersMap(null);

    }

    public final String setValidationParameter(String name, String value,
            boolean client) {

        return (String) setValidationParameterData(name, value, client);

    }

    public final String getValidationParameter(String name) {

        return getValidationParameter(name, null);

    }

    public final String removeValidationParameter(String name) {

        FacesContext facesContext = getFacesContext();

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", false);
        if (dataMapAccessor == null) {
            return null;
        }

        IDataMapAccessor clientMapAccessor = engine.getDataMapAccessor(
                facesContext, "ClientValidationParameter", false);
        if (clientMapAccessor != null) {
            clientMapAccessor.removeData(name, facesContext);
        }

        return (String) dataMapAccessor.removeData(name, facesContext);

    }

    public final void setValidationParameter(String name, ValueBinding value,
            boolean client) {

        setValidationParameterData(name, value, client);

    }

    public final Map getClientValidationParametersMap() {

        return getClientValidationParametersMap(null);

    }

    public final int getValidationParametersCount() {

        return getValidationParametersCount(null);

    }

    public final String getValidationParameter(String name,
            FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = getFacesContext();
        }

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", false);
        if (dataMapAccessor == null) {
            return null;
        }

        return (String) dataMapAccessor.getData(name, facesContext);

    }

    public final int getValidationParametersCount(FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = getFacesContext();
        }
        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", false);
        if (dataMapAccessor == null) {
            return 0;
        }

        return dataMapAccessor.getDataCount();

    }

    public final Map getValidationParametersMap(FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = getFacesContext();
        }

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", false);
        if (dataMapAccessor == null) {
            return Collections.EMPTY_MAP;
        }

        return dataMapAccessor.getDataMap(facesContext);

    }

    public final Map getClientValidationParametersMap(FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = getFacesContext();
        }

        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", false);
        if (dataMapAccessor == null) {
            return Collections.EMPTY_MAP;
        }

        Map map = dataMapAccessor.getDataMap(facesContext);
        if (map.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        IDataMapAccessor clientMapAccessor = engine.getDataMapAccessor(
                facesContext, "ClientValidationParameter", false);
        if (clientMapAccessor == null) {
            if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
                map = Collections.unmodifiableMap(map);
            }
            return map;
        }

        Map client = clientMapAccessor.getDataMap(facesContext);
        if (client == null || client.isEmpty()) {

            if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
                map = Collections.unmodifiableMap(map);
            }
            return map;
        }

        Map fmap = new HashMap(map);
        if (map.keySet().removeAll(client.keySet()) == false) {
            if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
                map = Collections.unmodifiableMap(map);
            }
            return map;
        }

        if (fmap.isEmpty()) {
            return Collections.EMPTY_MAP;
        }

        if (Constants.READ_ONLY_COLLECTION_LOCK_ENABLED) {
            fmap = Collections.unmodifiableMap(fmap);
        }

        return fmap;

    }

    private final Object setValidationParameterData(String name, Object value,
            boolean client) {

        FacesContext facesContext = getFacesContext();
        IDataMapAccessor dataMapAccessor = engine.getDataMapAccessor(
                facesContext, "ValidationParameter", true);
        if (client) {
            // On retire la limitation au niveau client si besoin !
            IDataMapAccessor clientMapAccessor = engine.getDataMapAccessor(
                    facesContext, "ClientValidationParameter", false);
            if (clientMapAccessor != null) {
                clientMapAccessor.removeData(name, facesContext);
            }
        } else {
            IDataMapAccessor clientMapAccessor = engine.getDataMapAccessor(
                    facesContext, "ClientValidationParameter", true);
            clientMapAccessor.setData(name, Boolean.FALSE, facesContext);
        }

        return dataMapAccessor.setData(name, value, facesContext);

    }

    public final boolean isClientSideValidationParameter(String name,
            FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = getFacesContext();
        }

        IDataMapAccessor clientMapAccessor = engine.getDataMapAccessor(
                facesContext, "ClientValidationParameter", false);
        if (clientMapAccessor == null) {
            return false;
        }
        return (clientMapAccessor.getData(name, facesContext) == null);

    }

    public final boolean isAutoTab() {
        return isAutoTab(null);
    }

    public final boolean isAutoTab(javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.AUTO_TAB, false, facesContext);
    }

    public final void setAutoTab(boolean autoTab) {
        engine.setProperty(Properties.AUTO_TAB, autoTab);
    }

    public final void setAutoTab(ValueBinding autoTab) {
        engine.setProperty(Properties.AUTO_TAB, autoTab);
    }

    public final java.lang.String getText() {
        return getText(null);
    }

    public final java.lang.String getText(
            javax.faces.context.FacesContext facesContext) {
        return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this,
                facesContext);
    }

    public final void setText(java.lang.String text) {
        setValue(text);
    }

    public final void setText(ValueBinding text) {
        setValue(text);
    }

    public final java.lang.String getEmptyMessage() {
        return getEmptyMessage(null);
    }

    public final java.lang.String getEmptyMessage(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.EMPTY_MESSAGE, facesContext);
    }

    public final void setEmptyMessage(java.lang.String emptyMessage) {
        engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
    }

    public final void setEmptyMessage(ValueBinding emptyMessage) {
        engine.setProperty(Properties.EMPTY_MESSAGE, emptyMessage);
    }

    public final boolean isReadOnly() {
        return isReadOnly(null);
    }

    public final boolean isReadOnly(
            javax.faces.context.FacesContext facesContext) {
        return engine
                .getBoolProperty(Properties.READ_ONLY, false, facesContext);
    }

    public final void setReadOnly(boolean readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public final void setReadOnly(ValueBinding readOnly) {
        engine.setProperty(Properties.READ_ONLY, readOnly);
    }

    public final void addValueChangeListener(
            javax.faces.event.ValueChangeListener listener) {
        addFacesListener(listener);
    }

    public final void removeValueChangeListener(
            javax.faces.event.ValueChangeListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listValueChangeListeners() {
        return getFacesListeners(javax.faces.event.ValueChangeListener.class);
    }

    public final IMenuComponent getMenu(String menuId) {

        return MenuTools.getMenu(this, menuId);

    }

    public final IMenuComponent getMenu() {

        return MenuTools.getMenu(this);

    }

    public final IMenuIterator listMenus() {

        return MenuTools.listMenus(this);

    }

    public final java.lang.String getFocusStyleClass() {
        return getFocusStyleClass(null);
    }

    public final java.lang.String getFocusStyleClass(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.FOCUS_STYLE_CLASS,
                facesContext);
    }

    public final void setFocusStyleClass(java.lang.String focusStyleClass) {
        engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
    }

    public final void setFocusStyleClass(ValueBinding focusStyleClass) {
        engine.setProperty(Properties.FOCUS_STYLE_CLASS, focusStyleClass);
    }

    public final java.lang.String getClientValidator() {
        return getClientValidator(null);
    }

    public final java.lang.String getClientValidator(
            javax.faces.context.FacesContext facesContext) {
        return engine.getStringProperty(Properties.CLIENT_VALIDATOR,
                facesContext);
    }

    public final void setClientValidator(java.lang.String clientValidator) {
        engine.setProperty(Properties.CLIENT_VALIDATOR, clientValidator);
    }

    public final void setClientValidator(ValueBinding clientValidator) {
        engine.setProperty(Properties.CLIENT_VALIDATOR, clientValidator);
    }

    public final void addSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        addFacesListener(listener);
    }

    public final void removeSelectionListener(
            org.rcfaces.core.event.ISelectionListener listener) {
        removeFacesListener(listener);
    }

    public final javax.faces.event.FacesListener[] listSelectionListeners() {
        return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
    }

    public final int getMaxTextLength() {
        return getMaxTextLength(null);
    }

    public final int getMaxTextLength(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.MAX_TEXT_LENGTH, 0,
                facesContext);
    }

    public final void setMaxTextLength(int maxTextLength) {
        engine.setProperty(Properties.MAX_TEXT_LENGTH, maxTextLength);
    }

    public final void setMaxTextLength(ValueBinding maxTextLength) {
        engine.setProperty(Properties.MAX_TEXT_LENGTH, maxTextLength);
    }

    public final boolean isMaxTextLengthSetted() {
        return engine.isPropertySetted(Properties.MAX_TEXT_LENGTH);
    }

    public final int getColumnNumber() {
        return getColumnNumber(null);
    }

    public final int getColumnNumber(
            javax.faces.context.FacesContext facesContext) {
        return engine.getIntProperty(Properties.COLUMN_NUMBER, 0, facesContext);
    }

    public final void setColumnNumber(int columnNumber) {
        engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
    }

    public final void setColumnNumber(ValueBinding columnNumber) {
        engine.setProperty(Properties.COLUMN_NUMBER, columnNumber);
    }

    public final boolean isColumnNumberSetted() {
        return engine.isPropertySetted(Properties.COLUMN_NUMBER);
    }

    public final boolean isAutoCompletion() {
        return isAutoCompletion(null);
    }

    public final boolean isAutoCompletion(
            javax.faces.context.FacesContext facesContext) {
        return engine.getBoolProperty(Properties.AUTO_COMPLETION, true,
                facesContext);
    }

    public final void setAutoCompletion(boolean autoCompletion) {
        engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
    }

    public final void setAutoCompletion(ValueBinding autoCompletion) {
        engine.setProperty(Properties.AUTO_COMPLETION, autoCompletion);
    }

    public final boolean isAutoCompletionSetted() {
        return engine.isPropertySetted(Properties.AUTO_COMPLETION);
    }

    public void release() {
        super.release();
    }
}
