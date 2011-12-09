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
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractPropertiesAccessor.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

    protected Map createMap(int size) {
        return new HashMap(size);
    }

    public IDeltaPropertiesAccessor createDeltaPropertiesAccessor() {
        return new BasicDeltaPropertiesAccessor(this);
    }

    public void putAll(FacesContext context, Set entries, Object undefinedValue) {
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
}
