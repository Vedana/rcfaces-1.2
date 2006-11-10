/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.component.IConvertValueHolder;
import org.rcfaces.core.internal.util.Convertor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValuesTools {
    private static final String REVISION = "$Revision$";

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    public static final Set valueToSet(Object value, boolean copy) {
        if (value == null) {
            if (copy) {
                return new HashSet();
            }
            return Collections.EMPTY_SET;
        }

        if (value instanceof Object[]) {
            Object array[] = (Object[]) value;
            if (array.length < 1) {
                if (copy) {
                    return new HashSet();
                }
                return Collections.EMPTY_SET;
            }

            return new HashSet(Arrays.asList(array));
        }

        if (value instanceof Set) {
            if (copy) {
                return new HashSet((Set) value);
            }
            return (Set) value;
        }

        if (value instanceof Collection) {
            Collection col = (Collection) value;
            if (col.isEmpty() && copy == false) {
                return Collections.EMPTY_SET;
            }

            return new HashSet(col);
        }

        if (copy) {
            Set set = new HashSet();
            set.add(value);

            return set;
        }

        return Collections.singleton(value);
    }

    public static final List valueToList(Object value) {
        if (value == null) {
            return Collections.EMPTY_LIST;
        }

        if (value instanceof Object[]) {
            Object array[] = (Object[]) value;
            if (array.length < 1) {
                return Collections.EMPTY_LIST;
            }

            return Arrays.asList(array);
        }

        if (value instanceof List) {
            return (List) value;
        }

        if (value instanceof Collection) {
            Collection col = (Collection) value;
            if (col.isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            return new ArrayList(col);
        }

        return Collections.singletonList(value);
    }

    protected static final Object[] convertStringsToValues(
            FacesContext context, UIComponent component, boolean testValue,
            String values[]) throws ConverterException {

        if (values == null || values.length < 1) {
            return values;
        }

        Converter converter = null;

        if (component instanceof IConvertValueHolder) {
            converter = ((IConvertValueHolder) component).getConverter(context);

        } else if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();
        }

        if (converter == null && testValue) {
            ValueBinding valueBinding = component.getValueBinding("value");

            if (valueBinding == null) {
                return values;
            }

            if (valueBinding.getValue(context) != null) {
                // On peut avoir le type !
                Class converterType = valueBinding.getType(context);
                if (converterType == null || converterType == String.class
                        || converterType == Object.class) {
                    return values;
                }

                try {
                    Application application = context.getApplication();

                    converter = application.createConverter(converterType);

                } catch (Exception e) {
                    throw new FacesException(
                            "Can not create converter for type '"
                                    + converterType + "'.", e);
                }
            }
        }

        if (converter != null) {
            Object converted[] = new Object[values.length];

            for (int i = 0; i < converted.length; i++) {
                converted[i] = converter.getAsObject(context, component,
                        values[i]);
            }

            return converted;
        }

        // throw new ConverterException("No converter !");

        return values;
    }

    public static Object convertStringToValue(FacesContext context,
            UIComponent component, Object submittedValue) {

        if ((submittedValue instanceof String) == false) {
            return submittedValue;
        }

        Converter converter = null;

        if (component instanceof IConvertValueHolder) {
            converter = ((IConvertValueHolder) component).getConverter(context);

        } else if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();
        }

        return convertStringToValue(context, component, converter,
                submittedValue, "value");
    }

    public static Object convertStringToValue(FacesContext context,
            UIComponent component, Converter converter, Object value,
            String attributeName) {

        if ((value instanceof String) == false) {
            return value;
        }

        if (converter == null) {
            ValueBinding valueBinding = component
                    .getValueBinding(attributeName);

            if (valueBinding == null) {
                return value;
            }

            if (valueBinding.getValue(context) != null) {
                Class converterType = valueBinding.getType(context);
                if (converterType == null || converterType == String.class
                        || converterType == Object.class) {
                    return value;
                }

                try {
                    Application application = context.getApplication();

                    converter = application.createConverter(converterType);

                } catch (Exception e) {
                    throw new FacesException(
                            "Can not create converter for type '"
                                    + converterType + "'.", e);
                }
            }
        }

        if (converter != null) {
            return converter.getAsObject(context, component, (String) value);
        }

        return value;
        // throw new ConverterException("No converter !");
    }

    public static String[] convertValuesToString(Object values[],
            UIComponent component, FacesContext facesContext) {

        if (values instanceof String[]) {
            return (String[]) values;
        }
        if (values.length == 0) {
            return STRING_EMPTY_ARRAY;
        }

        Converter converter = null;
        if (component instanceof IConvertValueHolder) {
            converter = ((IConvertValueHolder) component)
                    .getConverter(facesContext);

        } else if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();
        }

        if (converter == null && values != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            Application application = facesContext.getApplication();

            converter = application.createConverter(values[0].getClass());
        }

        if (converter != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            String ret[] = new String[values.length];

            for (int i = 0; i < ret.length; i++) {
                ret[i] = converter.getAsString(facesContext, component,
                        values[i]);
            }

            return ret;
        }

        String ret[] = new String[values.length];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = (String) Convertor.convert(values[i], String.class);
        }

        return ret;
    }

    public static String convertValueToString(Object value,
            UIComponent component, FacesContext facesContext) {

        if (value instanceof String) {
            return (String) value;
        }

        Converter converter = null;
        if (component instanceof IConvertValueHolder) {
            converter = ((IConvertValueHolder) component)
                    .getConverter(facesContext);

        } else if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();
        }

        return convertValueToString(value, converter, component, facesContext);
    }

    public static String convertValueToString(Object value,
            Converter converter, UIComponent component,
            FacesContext facesContext) {

        if (value instanceof String) {
            return (String) value;
        }

        if (converter == null && value != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            Application application = facesContext.getApplication();

            converter = application.createConverter(value.getClass());
        }

        if (converter != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            return converter.getAsString(facesContext, component, value);
        }

        return (String) Convertor.convert(value, String.class);
    }

    public static final boolean valueToBool(ValueHolder valueHolder) {
        Object value = valueHolder.getValue();

        if (value == null || value == Boolean.FALSE) {
            return false;
        }

        if (value == Boolean.TRUE) {
            return true;
        }

        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }

        Boolean b = (Boolean) Convertor.convert(value, Boolean.TYPE);

        if (b == null) {
            return false;
        }

        return b.booleanValue();
    }

    public static final Boolean valueToBoolean(ValueHolder valueHolder) {
        Object value = valueHolder.getValue();

        if (value == null) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return (Boolean) Convertor.convert(value, Boolean.class);
    }

    public static final int valueToInt(ValueHolder valueHolder) {
        Object value = valueHolder.getValue();

        if (value == null) {
            return 0;
        }

        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        }

        Integer i = (Integer) Convertor.convert(value, Integer.TYPE);
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }

    public static String valueToString(ValueHolder valueHolder,
            FacesContext facesContext) {
        return ValuesTools.valueToString(valueHolder.getValue(),
                (UIComponent) valueHolder, facesContext);
    }

    public static String valueToString(UICommand valueHolder,
            FacesContext facesContext) {
        return ValuesTools.valueToString(valueHolder.getValue(), valueHolder,
                facesContext);
    }

    public static String valueToString(Object value, UIComponent component,
            FacesContext facesContext) {
        if (value instanceof String) {
            return (String) value;
        }

        Converter converter = null;
        if (component instanceof IConvertValueHolder) {
            converter = ((IConvertValueHolder) component)
                    .getConverter(facesContext);

        } else if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();
        }

        return valueToString(value, converter, component, facesContext);
    }

    public static String valueToString(Object value, Converter converter,
            UIComponent component, FacesContext facesContext) {
        if (value instanceof String) {
            return (String) value;
        }

        if (converter == null && value != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            Application application = facesContext.getApplication();

            converter = application.createConverter(value.getClass());
        }

        if (converter != null) {
            if (facesContext == null) {
                facesContext = FacesContext.getCurrentInstance();
            }

            return converter.getAsString(facesContext, component, value);
        }

        return (String) Convertor.convert(value, String.class);
    }

}
