/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.9  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.8  2006/04/27 13:49:45  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.7  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.6  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.5  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.4  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
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

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicPropertiesAccessor extends AbstractPropertiesAccessor {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesAccessor.class);

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

            rets[i++] = entry.getKey();

            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            if (isPrimitive(value) == false) {
                rets[i++] = UIComponentBase.saveAttachedState(context, value);

                continue;
            }

            rets[i++] = value;
        }

        return rets;
    }

    static final boolean isPrimitive(Object value) {
        if (value == null) {
            return true;
        }

        return PRIMITIVE_CLASSES.contains(value.getClass());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.components.IPropertiesAccessor#release()
     */
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
            String key = (String) datas[i++];
            Object value = datas[i++];
            if (value == null) {
                // ??? Ca ne doit jamais arriver ...
                LOG.error("Invalid format for restoreState (key=" + key + ").");
                continue;
            }

            if (isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            setProperty(context, key, value);
        }

        return null;
    }

    public boolean isPropertySetted(String propertyName) {
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

}