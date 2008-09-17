/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.util.StateHolderTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractInformation implements StateHolder {

    private Map attributes;

    private boolean transientState;

    public final Object getAttribute(String attributeName) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(attributeName);
    }

    public final Object setAttribute(String attributeName, Object attributeValue) {
        if (attributes == null) {
            attributes = new HashMap();
        }

        return attributes.put(attributeName, attributeValue);
    }

    public final Map getAttributes() {
        if (attributes == null) {
            return Collections.EMPTY_MAP;
        }
        return attributes;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attributes == null) ? 0 : attributes.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AbstractInformation other = (AbstractInformation) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        return true;
    }

    public void restoreState(FacesContext context, Object state) {
        Object states[] = (Object[]) state;

        attributes = new HashMap(states.length / 2);

        for (int i = 0; i < states.length;) {
            Object key = states[i];

            Object value = states[i];
            if (StateHolderTools.isPrimitive(value) == false) {
                value = UIComponentBase.restoreAttachedState(context, value);
            }

            attributes.put(key, value);
        }
    }

    public Object saveState(FacesContext context) {
        Object ret[] = new Object[attributes.size() * 2];

        int i = 0;
        for (Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Entry) it.next();

            ret[i++] = entry.getKey();

            Object value = entry.getValue();

            if (StateHolderTools.isPrimitive(value)) {
                ret[i++] = value;
                continue;
            }

            ret[i++] = UIComponentBase.saveAttachedState(context, value);
        }

        return ret;
    }

    public boolean isTransient() {
        return transientState;
    }

    public void setTransient(boolean transientState) {
        this.transientState = transientState;
    }
}
