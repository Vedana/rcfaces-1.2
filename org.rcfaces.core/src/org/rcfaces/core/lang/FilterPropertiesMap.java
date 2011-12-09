package org.rcfaces.core.lang;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.AbstractProperties;
import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class FilterPropertiesMap extends AbstractProperties implements
        IFilterProperties {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(FilterPropertiesMap.class);

    static final boolean TEST_MAP_CONTENT = false;

    private static final long serialVersionUID = -6566852140453141630L;

    private final Map map;

    public FilterPropertiesMap(Map map) {
        this.map = new HashMap(map);
    }

    public FilterPropertiesMap(IFilterProperties filterProperties) {
        this.map = copyMap(filterProperties);
    }

    public FilterPropertiesMap() {
        this.map = new HashMap();
    }

    public void clear() {
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Object put(Serializable key, Object value) {
        return map.put(key.toString(), value);
    }

    public void putAll(Map t) {
        if (TEST_MAP_CONTENT) {
            for (Iterator it = t.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                Object key = entry.getKey();
                if (key != null && (key instanceof String) == false) {
                    throw new FacesException("Key is not a String !");
                }

                Object value = entry.getValue();
                if (value != null && (value instanceof String) == false) {
                    throw new FacesException("Value is not a String !");
                }
            }
        }

        map.putAll(t);
    }

    public Object remove(Serializable key) {
        return map.remove(key.toString());
    }

    public int size() {
        return map.size();
    }

    public boolean containsKey(Serializable propertyName) {
        return map.containsKey(propertyName.toString());
    }

    public String[] listNames() {
        Collection c = map.keySet();

        return (String[]) c.toArray(new String[c.size()]);
    }

    public Map toMap() {
        return new HashMap(map);
    }

    public Object saveState(FacesContext context) {
        if (map.isEmpty()) {
            return null;
        }

        Object ret[] = new Object[map.size() * 2];

        int idx = 0;
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Entry) it.next();

            ret[idx++] = entry.getKey();
            ret[idx++] = entry.getValue();
        }

        return ret;
    }

    public void restoreState(FacesContext context, Object state) {
        if (state == null) {
            return;
        }

        Object p[] = (Object[]) state;

        for (int i = 0; i < p.length;) {
            map.put(p[i++], p[i++]);
        }
    }

    public boolean isTransient() {
        return false;
    }

    public void setTransient(boolean newTransientValue) {
    }

    public Object getProperty(Serializable name) {
        return map.get(name.toString());
    }

    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final FilterPropertiesMap other = (FilterPropertiesMap) obj;
        if (map == null) {
            if (other.map != null) {
                return false;
            }

        } else if (!map.equals(other.map)) {
            return false;
        }

        return true;
    }

    private Map copyMap(IFilterProperties filterProperties) {
        if (filterProperties instanceof FilterPropertiesMap) {
            // Pas besoin de copie !
            return filterProperties.toMap();
        }

        return new HashMap(filterProperties.toMap());
    }

    static {
        if (TEST_MAP_CONTENT) {
            LOG.info("TEST_MAP_CONTENT=" + TEST_MAP_CONTENT);
        }
    }

}