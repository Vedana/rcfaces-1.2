package org.rcfaces.core.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.capability.IConvertValueHolder;
import org.rcfaces.core.internal.component.Properties;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractConverterCommandComponent extends
        AbstractCommandComponent implements IConvertValueHolder {

    protected static final Set CAMELIA_ATTRIBUTES = new HashSet(
            AbstractCommandComponent.CAMELIA_ATTRIBUTES);
    static {
        CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] { "converter" }));
    }

    public final void setConverter(String converterId) {

        setConverter(null, converterId);

    }

    public final void setConverter(FacesContext facesContext, String converterId) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }
        Converter converter = facesContext.getApplication().createConverter(
                converterId);
        this.setConverter(converter);

    }

    public final void setConverter(Converter converter) {

        engine.setProperty("converter", converter);

    }

    public final Converter getConverter() {

        return (Converter) engine.getProperty("converter", null);

    }

    public final Converter getConverter(FacesContext facesContext) {

        return (Converter) engine.getProperty("converter", facesContext);

    }

    public final Object getLocalValue() {

        return engine.getLocalValue(Properties.VALUE);

    }

    protected Set getCameliaFields() {
        return CAMELIA_ATTRIBUTES;
    }
}
