/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.internal.util.StateHolderTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicDeltaPropertiesAccessor extends AbstractPropertiesAccessor
        implements IDeltaPropertiesAccessor {

    private static final Log LOG = LogFactory
            .getLog(BasicDeltaPropertiesAccessor.class);

    private static final int PROPERTY_INITIAL_SIZE = 8;

    private static final boolean CLONE_VALUE = true;

    private static final Object UNDEFINED = new Object();

    private final IPropertiesAccessor parent;

    private Map<String, Object> properties;

    private static IUnproxifyValueExpression unproxifyValueExpression = null;

    static {
        try {
            ClassLocator.load("org.apache.jasper.el.JspValueExpression",
                    BasicDeltaPropertiesAccessor.class, null);

            Class< ? > cl = ClassLocator
                    .load("org.rcfaces.core.internal.jasper.JasperUnproxifyValueExpression",
                            BasicDeltaPropertiesAccessor.class, null);
            unproxifyValueExpression = (IUnproxifyValueExpression) cl
                    .newInstance();

        } catch (ClassNotFoundException ex) {
            LOG.error(ex.getMessage());
        } catch (Throwable ex) {
            LOG.error(ex.getMessage());
        }
    }

    public BasicDeltaPropertiesAccessor(IPropertiesAccessor parent) {
        this.parent = parent;
    }

    public void commitProperties(FacesContext context) {
        if (hasModifiedProperties() == false) {
            return;
        }

        parent.putAll(context, properties.entrySet(), UNDEFINED);
    }

    public boolean isPropertySetted(String propertyName) {
        if (properties != null) {
            Object value = properties.get(propertyName);
            if (value != null) {
                if (value == UNDEFINED) {
                    return false;
                }

                return true;
            }
        }

        return parent.isPropertySetted(propertyName);
    }

    public Object getProperty(String propertyName) {
        if (properties != null && properties.isEmpty() == false) {
            Object ret = properties.get(propertyName);
            if (ret != null) {
                if (ret == UNDEFINED) {
                    return null;
                }

                return ret;
            }
        }

        Object value = parent.getProperty(propertyName);

        if (CLONE_VALUE && value != null) {
            Object original = value;

            if (value instanceof Map) {
                value = new HashMap((Map) value);

            } else if (value instanceof List) {
                value = new ArrayList((List) value);

            } else if (value instanceof Set) {
                value = new HashSet((Set) value);
            }

            if (original != value) {
                setProperty(null, propertyName, value);
            }
        }

        return value;
    }

    public Object setProperty(FacesContext facesContext, String propertyName,
            Object value) {

        Object initialValue = parent.getProperty(propertyName);

        // La propriété est identique au model ?
        if (initialValue == value
                || (initialValue != null && initialValue.equals(value))) {

            if (properties != null) {
                return properties.remove(propertyName);
            }

            // On remet l'ancienne valeur !
            return initialValue;
        }

        // On doit gérer le NULL !
        if (value == null) {
            value = UNDEFINED;
        }

        if (properties == null) {
            properties = createMap(PROPERTY_INITIAL_SIZE);
        }

        Object old = properties.put(propertyName, value);
        if (old == UNDEFINED) {
            return initialValue;
        }

        return old;
    }

    public void setProperty(FacesContext facesContext, String propertyName,
            ValueExpression valueExpression) {

        Object initialValue = parent.getProperty(propertyName);

        if (unproxifyValueExpression != null) {
            valueExpression = unproxifyValueExpression.process(valueExpression);
        }

        if (initialValue != null && initialValue.equals(valueExpression)) {
            return;
        }

        LOG.error("Can not change a ValueExpression property '" + propertyName
                + "' valueExpression=" + valueExpression + " original='"
                + initialValue + "'.");

        throw new FacesException(
                "Can not set a ValueExpression while a delta phase.");
    }

    public Object removeProperty(FacesContext facesContext, String propertyName) {

        Object initialValue = parent.getProperty(propertyName);
        if (initialValue != null) {
            // Un model existe !

            if (properties == null) {
                properties = createMap(PROPERTY_INITIAL_SIZE);
            }

            // On positionne donc un UNDEFINED dans le delta !
            Object old = properties.put(propertyName, UNDEFINED);

            if (old == UNDEFINED) {
                // L'ancien est deja UNDEFINED => retourne null;
                return null;
            }

            if (old != null) {
                // Il y a avait deja une valeur dans le delta
                return old;
            }

            // Sinon on retourne la valeur initiale
            return initialValue;
        }

        // Pas de valeur initiale !

        if (properties == null) {
            return null;
        }

        // On positionne donc un UNDEFINED dans le delta !
        Object old = properties.put(propertyName, UNDEFINED);
        if (old == UNDEFINED) {
            // L'ancien est deja UNDEFINED => retourne null;
            return null;
        }

        return old;
    }

    public void clearProperties(FacesContext facesContext) {

        Set<String> names = keySet();
        if (names.isEmpty()) {
            return;
        }

        if (properties == null) {
            properties = createMap(PROPERTY_INITIAL_SIZE);
        }
        for (String propertyName : names) {
            properties.put(propertyName, UNDEFINED);
        }
    }

    public Object saveState(FacesContext context) {
        if (hasModifiedProperties() == false) {
            return null;
        }

        Object rets[] = new Object[properties.size() * 2];
        int i = 0;
        for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();

            rets[i++] = entry.getKey();

            Object value = entry.getValue();
            if (value == UNDEFINED || value == null) {
                i++;
                continue;
            }

            if (StateHolderTools.isPrimitive(value) == false) {
                rets[i++] = UIComponentBase.saveAttachedState(context, value);

                continue;
            }

            rets[i++] = value;
        }

        return rets;
    }

    public void restoreState(FacesContext context, Object state) {
        if (state == null) {
            return;
        }

        if ((state instanceof Object[]) == false) {
            throw new FacesException(
                    "Bad serialized format ! (not an objects array !)");
        }

        Object datas[] = (Object[]) state;

        properties = createMap(datas.length / 2);

        for (int i = 0; i < datas.length;) {
            String key = (String) datas[i++];
            Object value = datas[i++];

            if (StateHolderTools.isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            setProperty(context, key, value);
        }
    }

    public void release() {
        properties = null;
    }

    public Set<String> keySet() {
        Set<String> l = parent.keySet();
        if (properties == null || properties.isEmpty()) {
            // Pas de propriétés locales !
            return l;
        }

        Set<String> v = new HashSet<String>(l.size() + properties.size());
        v.addAll(l);

        for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();

            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == UNDEFINED) {
                v.remove(key);
                continue;
            }

            v.add(key);
        }

        return v;
    }

    public boolean hasModifiedProperties() {
        return properties != null && properties.size() > 0;
    }

    public int size() {
        if (hasModifiedProperties() == false) {
            return parent.size();
        }

        Set<String> l = parent.keySet();

        int cnt = l.size();
        for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();

            String key = entry.getKey();
            Object value = entry.getValue();

            boolean contains = l.contains(key);
            if (contains) {
                if (value == UNDEFINED) {
                    // Valeur connue mais mise à NULL
                    cnt--;
                }

                continue;
            }

            if (value == UNDEFINED) {
                // Valeur à NULL, mais inconnue
                continue;
            }

            // Valeur ajoutée
            cnt++;
        }

        return cnt;
    }

    public IPropertiesAccessor copyOriginalState(FacesContext facesContext) {
        throw new IllegalStateException("No copy original state");
    }

    @Override
    public String toString() {
        if (hasModifiedProperties() == false) {
            return "{Delta: NONE}";
        }

        Set<String> keys = keySet();

        String s = "{Delta: ";

        boolean first = true;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();

            if (first) {
                first = false;
            } else {
                s += ",";
            }

            if (properties.containsKey(key)) {
                s += "(D)";
            }
            s += key + "=";

            Object obj = getProperty(key);
            if (obj instanceof ValueExpression) {
                s += "'" + ((ValueExpression) obj).getExpressionString() + "'";

            } else if (obj != null) {
                s += "'" + obj + "'";

            } else {
                s += obj;
            }
        }

        s += "}";

        return s;
    }
}
