/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.model.ICommitableObject;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractPropertiesAccessor implements IPropertiesAccessor {
    private static final Log LOG = LogFactory
            .getLog(AbstractPropertiesAccessor.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    protected Map<String, Object> createMap(int size) {
        return new HashMap<String, Object>(size);
    }

    public IDeltaPropertiesAccessor createDeltaPropertiesAccessor() {
        return new BasicDeltaPropertiesAccessor(this);
    }

    public IPropertiesAccessor copyOriginalProperties(FacesContext facesContext) {
        BasicPropertiesAccessor bpa = new BasicPropertiesAccessor();

        Object state = this.saveState(facesContext);

        bpa.restoreState(facesContext, state);

        return bpa;
    }

    public void putAll(FacesContext context,
            Set<Map.Entry<String, Object>> entries, Object undefinedValue) {
        for (Iterator<Map.Entry<String, Object>> it = entries.iterator(); it
                .hasNext();) {
            Map.Entry<String, Object> entry = it.next();

            String key = entry.getKey();
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
}
