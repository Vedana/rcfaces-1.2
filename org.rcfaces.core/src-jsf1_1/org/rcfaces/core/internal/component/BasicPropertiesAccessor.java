/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.model.ICommitableObject;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicPropertiesAccessor extends AbstractPropertiesAccessor {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesAccessor.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    private static final Set PRIMITIVE_CLASSES = new HashSet(8);
    static {
        PRIMITIVE_CLASSES.add(String.class);
        PRIMITIVE_CLASSES.add(Long.class);
        PRIMITIVE_CLASSES.add(Integer.class);
        PRIMITIVE_CLASSES.add(Short.class);
        PRIMITIVE_CLASSES.add(Byte.class);
        PRIMITIVE_CLASSES.add(Boolean.class);
        PRIMITIVE_CLASSES.add(Double.class);
        PRIMITIVE_CLASSES.add(Float.class);
        PRIMITIVE_CLASSES.add(Character.class);
        PRIMITIVE_CLASSES.add(Date.class);
        PRIMITIVE_CLASSES.add(Time.class);
    }

    private Map properties;

    void putAll(FacesContext context, Set entries, Object undefinedValue) {
        for (Iterator it = entries.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
            Object value = entry.getValue();

            if (value == undefinedValue) {
                value = null;

            } else if (value instanceof ICommitableObject) {
                ICommitableObject commitableObject = (ICommitableObject) value;

                if (commitableObject.isCommited() == false) {
                    if (debugEnabled) {
                        LOG.debug("Commit object '" + commitableObject + "'.");
                    }

                    commitableObject.commit();
                }
            }

            setProperty(context, key, value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.components.IPropertiesAccessor#getProperty(java.lang.String)
     */

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
            if ((previousValue instanceof ValueBinding) == false) {
                return properties.remove(propertyName);
            }

            if (context == null) {
                context = FacesContext.getCurrentInstance();
            }

            ValueBinding valueBinding = (ValueBinding) previousValue;

            Object old = valueBinding.getValue(context);

            valueBinding.setValue(context, value);

            return old;
        }

        if (properties == null) {
            properties = createMap(8);

            properties.put(propertyName, value);
            return null;
        }

        Object previousValue = properties.get(propertyName);
        if ((previousValue instanceof ValueBinding) == false) {
            properties.put(propertyName, value);
            return previousValue;
        }

        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        ValueBinding valueBinding = (ValueBinding) previousValue;

        Object old = valueBinding.getValue(context);

        valueBinding.setValue(context, value);

        return old;
    }

    public void setProperty(FacesContext context, String propertyName,
            ValueBinding value) {
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

        if ((previousValue instanceof ValueBinding) == false) {
            return previousValue;
        }

        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        ValueBinding valueBinding = (ValueBinding) previousValue;

        Object old = valueBinding.getValue(context);

        return old;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.components.IPropertiesAccessor#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }

        Object rets[] = new Object[properties.size() * 2];
        int i = 0;
        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

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

            if (isPrimitive(value)) {
                rets[i++] = value;
                continue;
            }

            rets[i++] = UIComponentBase.saveAttachedState(context, value);
        }

        return rets;
    }

    static final boolean isPrimitive(Object value) {
        if (value == null) {
            return true;
        }

        return PRIMITIVE_CLASSES.contains(value.getClass());
    }

    public void release() {
        properties = null;
    }

    public IDeltaPropertiesAccessor restoreState(FacesContext context,
            Object object) {
        if (object == null) {
            return null;
        }

        if ((object instanceof Object[]) == false) {
            throw new FacesException(
                    "Bad serialized format ! (not an objects array !)");
        }

        Object datas[] = (Object[]) object;

        if (((datas[0] instanceof String) == false) && datas.length == 3) {
            BasicDeltaPropertiesAccessor deltaPropertiesAccessor = new BasicDeltaPropertiesAccessor(
                    this);

            return deltaPropertiesAccessor.restoreState(context, object);
        }

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

            if (isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            setProperty(context, (String) key, value);
        }

        return null;
    }

    public boolean isPropertySetted(String propertyName) {
        if (properties == null) {
            return false;
        }
        return properties.containsKey(propertyName);
    }

    public IDeltaPropertiesAccessor createDeltaPropertiesAccessor() {
        return new BasicDeltaPropertiesAccessor(this);
    }

    public Set keySet() {
        if (properties == null || properties.isEmpty()) {
            return Collections.EMPTY_SET;
        }

        return properties.keySet();
    }

    public int size() {
        if (properties == null) {
            return 0;
        }

        return properties.size();
    }

    public String toString() {
        if (properties == null) {
            return "[EMPTY]";
        }

        Set keys = keySet();

        String s = "{";

        boolean first = true;
        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = (String) it.next();

            if (first) {
                first = false;
            } else {
                s += ",";
            }
            s += key + "=";

            Object obj = getProperty(key);
            if (obj instanceof ValueBinding) {
                s += "'" + ((ValueBinding) obj).getExpressionString() + "'";

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