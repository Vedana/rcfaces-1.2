/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.1  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 */
package org.rcfaces.core.internal.tools;

import java.util.Collection;
import java.util.Collections;
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
public final class FilterExpressionTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(FilterExpressionTools.class);

    private static final boolean TEST_MAP_CONTENT = true;

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    /**
     * 
     */
    public static final IFilterProperties EMPTY = new IFilterProperties() {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -3817846186098661680L;

        public boolean containsKey(String propertyName) {
            return false;
        }

        public Object put(String filterName, Object value) {
            throw new UnsupportedOperationException(
                    "Not supported for EMPTY filtersMap !");
        }

        public Object remove(String filterName) {
            throw new UnsupportedOperationException(
                    "Not supported for EMPTY filtersMap !");
        }

        public String[] listNames() {
            return STRING_EMPTY_ARRAY;
        }

        public void clear() {
            throw new UnsupportedOperationException(
                    "Not supported for EMPTY filtersMap !");
        }

        public boolean isEmpty() {
            return true;
        }

        public int size() {
            return 0;
        }

        public void putAll(Map map) {
            throw new UnsupportedOperationException(
                    "Not supported for EMPTY filtersMap !");
        }

        public Map toMap() {
            return Collections.EMPTY_MAP;
        }

        public boolean isTransient() {
            return false;
        }

        public void restoreState(FacesContext context, Object state) {
        }

        public Object saveState(FacesContext context) {
            return null;
        }

        public void setTransient(boolean newTransientValue) {
        }

        public Object getProperty(String name) {
            return null;
        }

        public String getStringProperty(String name) {
            return null;
        }

        public String getStringProperty(String name, String defaultValue) {
            return defaultValue;
        }

        public boolean getBoolProperty(String name, boolean defaultValue) {
            return defaultValue;
        }

        public Boolean getBooleanProperty(String name) {
            return null;
        }

        public int getIntProperty(String name, int defaultValue) {
            return defaultValue;
        }

        public Number getNumberProperty(String name) {
            return null;
        }

        public int hashCode() {
            return 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            return true;
        }

    };

    public static IFilterProperties create(Map map) {
        return new FilterParametersMap(map);
    }

    // C'est un StateHolder, le constructeur doit etre public !
    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class FilterParametersMap extends AbstractProperties
            implements IFilterProperties {

        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = -6566852140453141630L;

        private final Map map;

        FilterParametersMap(Map map) {
            this.map = map;
        }

        public FilterParametersMap() {
            this.map = new HashMap();
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

        public Object getProperty(String name) {
            return map.get(name);
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

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            final FilterParametersMap other = (FilterParametersMap) obj;
            if (map == null) {
                if (other.map != null) {
                    return false;
                }

            } else if (!map.equals(other.map)) {
                return false;
            }

            return true;
        }

    }

    static {
        if (TEST_MAP_CONTENT) {
            LOG.info("TEST_MAP_CONTENT=" + TEST_MAP_CONTENT);
        }
    }
}
