package org.rcfaces.core.lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    private static final Log LOG = LogFactory.getLog(FilterPropertiesMap.class);

    static final boolean TEST_MAP_CONTENT = false;

    private static final long serialVersionUID = -6566852140453141630L;

    private final Map<String, Object> map;

    public FilterPropertiesMap(Map<String, Object> map) {
        this.map = new HashMap<String, Object>(map);
    }

    public FilterPropertiesMap(IFilterProperties filterProperties) {
        this.map = copyMap(filterProperties);
    }

    public FilterPropertiesMap() {
        this.map = new HashMap<String, Object>();
    }

    public void clear() {
        map.clear();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public void putAll(Map<String, Object> t) {
        if (TEST_MAP_CONTENT) {
            for (Map.Entry<String, Object> entry : t.entrySet()) {

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

    public Object remove(String key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public boolean containsKey(String propertyName) {
        return map.containsKey(propertyName);
    }

    public String[] listNames() {
        Collection<String> c = map.keySet();

        return c.toArray(new String[c.size()]);
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>(map);
    }

    public Object saveState(FacesContext context) {
        if (map.isEmpty()) {
            return null;
        }

        Object ret[] = new Object[map.size() * 2];

        int idx = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {

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
            map.put((String) p[i++], p[i++]);
        }
    }

    public boolean isTransient() {
        return false;
    }

    public void setTransient(boolean newTransientValue) {
    }

    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((map == null) ? 0 : map.hashCode());
        return result;
    }

    @Override
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

    private Map<String, Object> copyMap(IFilterProperties filterProperties) {
        if (filterProperties instanceof FilterPropertiesMap) {
            // Pas besoin de copie !
            return filterProperties.toMap();
        }

        return new HashMap<String, Object>(filterProperties.toMap());
    }

    static {
        if (TEST_MAP_CONTENT) {
            LOG.info("TEST_MAP_CONTENT=" + TEST_MAP_CONTENT);
        }
    }

}