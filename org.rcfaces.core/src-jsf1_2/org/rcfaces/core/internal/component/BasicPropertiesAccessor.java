/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.util.StateHolderTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicPropertiesAccessor extends AbstractPropertiesAccessor {

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesAccessor.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    private Map<String, Object> properties;

    public Object getProperty(String propertyName) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }

        Object value = properties.get(propertyName);

        return value;
    }

    public Object setProperty(FacesContext context, String propertyName,
            Object value) {
        if (value == null) {
            if (properties == null) {
                return null;
            }

            Object previousValue = properties.get(propertyName);
            if ((previousValue instanceof ValueExpression) == false) {
                return properties.remove(propertyName);
            }

            if (context == null) {
                context = FacesContext.getCurrentInstance();
            }

            ValueExpression valueBinding = (ValueExpression) previousValue;

            Object old = valueBinding.getValue(context.getELContext());

            if (debugEnabled) {
                LOG.debug("Set value '" + value + "' to '"
                        + valueBinding.getExpressionString() + " old='" + old
                        + "'");
            }

            try {
                valueBinding.setValue(context.getELContext(), value);

            } catch (RuntimeException ex) {
                LOG.error(
                        "Set value '" + value + "' to '"
                                + valueBinding.getExpressionString() + " old='"
                                + old + "'", ex);

                throw ex;
            }

            return old;
        }

        if (properties == null) {
            properties = createMap(8);

            properties.put(propertyName, value);
            return null;
        }

        Object previousValue = properties.get(propertyName);
        if ((previousValue instanceof ValueExpression) == false) {
            properties.put(propertyName, value);
            return previousValue;
        }

        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        ValueExpression valueBinding = (ValueExpression) previousValue;

        Object old = valueBinding.getValue(context.getELContext());

        if (debugEnabled) {
            LOG.debug("Set value '" + value + "' to '"
                    + valueBinding.getExpressionString() + " old='" + old + "'");
        }

        try {
            valueBinding.setValue(context.getELContext(), value);

        } catch (RuntimeException ex) {
            LOG.error(
                    "Set value '" + value + "' to '"
                            + valueBinding.getExpressionString() + " old='"
                            + old + "'", ex);

            throw ex;
        }

        return old;
    }

    public void setProperty(FacesContext context, String propertyName,
            ValueExpression value) {
        if (value == null) {
            if (properties == null) {
                return;
            }

            properties.remove(propertyName);
            return;
        }

        if (properties == null) {
            properties = createMap(8);
        }

        properties.put(propertyName, value);
    }

    public Object removeProperty(FacesContext context, String propertyName) {
        if (properties == null) {
            return null;
        }

        Object previousValue = properties.remove(propertyName);

        if ((previousValue instanceof ValueExpression) == false) {
            return previousValue;
        }

        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        ValueExpression valueBinding = (ValueExpression) previousValue;

        Object old = valueBinding.getValue(context.getELContext());

        return old;
    }

    public void clearProperties(FacesContext context) {
        if (properties == null) {
            return;
        }

        properties.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.internal.components.IPropertiesAccessor#saveState(javax
     * .faces.context.FacesContext)
     */
    public Object saveState(FacesContext context) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }

        Object rets[] = new Object[properties.size() * 2];
        int i = 0;
        for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                .iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
                    .next();

            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            Object key = entry.getKey();
            if (Constants.COMPACTED_PROPERTY_NAME) {
                // La clef est forcement un string ....
                // Par contre, on peut la transformer en Integer !
                Object k = PropertiesRepository.getKey((String) key);
                if (k != null) {
                    key = k;
                }
            }

            rets[i++] = key;

            if (StateHolderTools.isPrimitive(value)) {
                rets[i++] = value;
                continue;
            }

            rets[i++] = UIComponentBase.saveAttachedState(context, value);
        }

        return rets;
    }

    public void release() {
        properties = null;
    }

    public void restoreState(FacesContext context, Object object) {
        if (object == null) {
            return;
        }

        if ((object instanceof Object[]) == false) {
            throw new FacesException(
                    "Bad serialized format ! (not an objects array !)");
        }

        Object datas[] = (Object[]) object;

        properties = createMap(datas.length / 2);

        for (int i = 0; i < datas.length;) {
            Object key = datas[i++];
            if (Constants.COMPACTED_PROPERTY_NAME) {
                if ((key instanceof String) == false) {
                    key = PropertiesRepository.getPropertyFromKey(key);

                    if (key == null) {
                        throw new FacesException("Unknown key '" + key + "' !");
                    }
                }
            }

            Object value = datas[i++];
            if (value == null) {
                // ??? Ca ne doit jamais arriver ...
                LOG.error("Invalid format for restoreState (key=" + key + ").");
                continue;
            }

            if (StateHolderTools.isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            setProperty(context, (String) key, value);
        }
    }

    public boolean isPropertySetted(String propertyName) {
        if (properties == null) {
            return false;
        }
        return properties.containsKey(propertyName);
    }

    public Set<String> keySet() {
        if (properties == null || properties.isEmpty()) {
            return Collections.emptySet();
        }

        return properties.keySet();
    }

    public int size() {
        if (properties == null) {
            return 0;
        }

        return properties.size();
    }

    @Override
    public String toString() {
        if (properties == null) {
            return "[EMPTY]";
        }

        Set<String> keys = keySet();

        String s = "{";

        boolean first = true;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();

            if (first) {
                first = false;
            } else {
                s += ",";
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