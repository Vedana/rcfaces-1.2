/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.6  2006/04/27 13:49:45  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.5  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
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
 * Revision 1.3  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.2  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.1  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
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

import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicDeltaPropertiesAccessor extends AbstractPropertiesAccessor
        implements IDeltaPropertiesAccessor {
    private static final String REVISION = "$Revision$";

    private static final int PROPERTY_INITIAL_SIZE = 8;

    private static final boolean CLONE_VALUE = true;

    private static final Object UNDEFINED = new Object();

    private final BasicPropertiesAccessor accessor;

    private Map properties;

    public BasicDeltaPropertiesAccessor(BasicPropertiesAccessor accessor) {
        this.accessor = accessor;
    }

    public void commitProperties(FacesContext context) {
        if (properties == null || properties.isEmpty()) {
            return;
        }

        accessor.putAll(context, properties.entrySet(), UNDEFINED);
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

        return accessor.isPropertySetted(propertyName);
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

        Object value = accessor.getProperty(propertyName);

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

        Object initialValue = accessor.getProperty(propertyName);

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

    public Object removeProperty(FacesContext facesContext, String propertyName) {

        Object initialValue = accessor.getProperty(propertyName);
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

        // On positionne donc un UNDEFINED dans le delta !
        Object old = properties.put(propertyName, UNDEFINED);
        if (old == UNDEFINED) {
            // L'ancien est deja UNDEFINED => retourne null;
            return null;
        }

        return old;
    }

    public Object saveState(FacesContext context) {
        Object rets2[] = new Object[3];
        rets2[0] = Boolean.TRUE;

        rets2[1] = accessor.saveState(context);

        if (properties == null) {
            return rets2;
        }

        Object rets[] = new Object[properties.size() * 2];
        int i = 0;
        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            rets[i++] = entry.getKey();

            Object value = entry.getValue();
            if (value == UNDEFINED || value == null) {
                continue;
            }

            if (BasicPropertiesAccessor.isPrimitive(value) == false) {
                rets[i++] = UIComponentBase.saveAttachedState(context, value);

                continue;
            }

            rets[i++] = value;
        }

        rets2[2] = rets;

        return rets2;
    }

    public IDeltaPropertiesAccessor restoreState(FacesContext context,
            Object state) {
        if ((state instanceof Object[]) == false) {
            throw new FacesException(
                    "Bad serialized format ! (not an objects array !)");
        }

        Object params[] = (Object[]) state;
        if (params.length != 3) {
            throw new FacesException("Bad format ! (Bad object array length)");
        }

        accessor.restoreState(context, params[1]);

        Object datas[] = (Object[]) params[2];

        if (datas.length == 0) {
            return this;
        }

        properties = new HashMap(datas.length / 2);

        for (int i = 0; i < datas.length;) {
            String key = (String) datas[i++];
            Object value = datas[i++];

            if (BasicPropertiesAccessor.isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            setProperty(context, key, value);
        }

        return this;
    }

    public void release() {
        properties = null;
    }

    public IDeltaPropertiesAccessor createDeltaPropertiesAccessor() {
        throw new UnsupportedOperationException(
                "Can not create a Delta of a delta properties !");
    }

    public Set keySet() {
        Set l = accessor.keySet();
        if (properties == null || properties.isEmpty()) {
            // Pas de propriétés locales !
            return l;
        }

        Set v = new HashSet(l.size() + properties.size());
        v.addAll(l);

        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
            Object value = entry.getValue();

            if (value == UNDEFINED) {
                v.remove(key);
                continue;
            }

            v.add(key);
        }

        return v;
    }

    public int size() {
        if (properties == null || properties.isEmpty()) {
            return accessor.size();
        }

        Set l = accessor.keySet();

        int cnt = l.size();
        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();

            String key = (String) entry.getKey();
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

}
