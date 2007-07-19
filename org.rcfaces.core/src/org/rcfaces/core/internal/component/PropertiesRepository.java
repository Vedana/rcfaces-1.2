/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertiesRepository extends Properties {
    private static final String REVISION = "$Revision$";

    private static final float MAP_LOAD_FACTOR = 0.9f;

    private static Map propertyToKey;

    private static Map keyToProperty;

    public static void declareProperties(String properties[]) {

        propertyToKey = new HashMap(properties.length, MAP_LOAD_FACTOR);
        keyToProperty = new HashMap(properties.length, MAP_LOAD_FACTOR);

        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];

            Integer key = (Integer) computeKey(property);

            for (;;) {
                if (keyToProperty.put(key, property) == null) {
                    break;
                }

                // key = new Integer(key.intValue() + 1);
                throw new IllegalStateException("Duplicate key for property '"
                        + property + "'.");
            }
            propertyToKey.put(property, key);
        }
    }

    private static Object computeKey(String property) {
        return new Integer(property.hashCode());
    }

    public static Object getKey(String property) {
        return propertyToKey.get(property);
    }

    public static String getPropertyFromKey(Object key) {
        return (String) keyToProperty.get(key);
    }
}
