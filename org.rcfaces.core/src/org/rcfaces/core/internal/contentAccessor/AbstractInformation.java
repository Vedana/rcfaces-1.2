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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.util.StateHolderTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractInformation implements StateHolder,
        IResourceKeyParticipant {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(AbstractInformation.class);

    private static final int INITIAL_KEY_SIZE = 4096;

    private Map attributes;

    private boolean transientState;

    private String cachedParticipeKey = null;

    private long hashCode = 0;

    public final Object getAttribute(String attributeName) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(attributeName);
    }

    public final Object setAttribute(String attributeName, Object attributeValue) {
        cachedParticipeKey = null;

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
        return computeCachedParticipeKey().hashCode();
    }

    private String computeCachedParticipeKey() {
        if (cachedParticipeKey != null) {
            return cachedParticipeKey;
        }

        StringAppender sa = new StringAppender(INITIAL_KEY_SIZE);
        participeKey(sa);

        cachedParticipeKey = sa.toString();

        return cachedParticipeKey;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AbstractInformation other = (AbstractInformation) obj;

        return computeCachedParticipeKey().equals(
                other.computeCachedParticipeKey());
    }

    public void restoreState(FacesContext context, Object state) {
        cachedParticipeKey = null;

        Object states[] = (Object[]) state;

        attributes = new HashMap(states.length / 2);

        for (int i = 0; i < states.length;) {
            String key = (String) states[i++];

            Object value = states[i++];
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

    protected void appendToKey(StringAppender sa, String propertyName,
            Object value) {
    }

    public void participeKey(StringAppender sa) {
        for (Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Entry) it.next();

            String key = (String) entry.getKey();

            sa.append(IResourceKeyParticipant.RESOURCE_KEY_SEPARATOR).append(
                    key);

            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            if ((value instanceof String) || (value instanceof Number)
                    || (value instanceof Boolean)) {
                sa.append(IResourceKeyParticipant.RESOURCE_KEY_SEPARATOR)
                        .append(String.valueOf(value));
                continue;
            }

            appendToKey(sa, key, value);
        }
    }

}
