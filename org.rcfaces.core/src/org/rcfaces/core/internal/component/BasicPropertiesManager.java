/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicPropertiesManager extends BasicPropertiesAccessor implements
        IPropertiesManager {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesManager.class);

    private static final boolean COMMITED_PROPERTIES_ENABLED = false;

    protected IFactory factory;

    protected IPropertiesAccessor originalPropertiesAccessor;

    private IDeltaPropertiesAccessor commitedPropertiesAccessor;

    private IDeltaPropertiesAccessor deltaPropertiesAccessor;

    public void setCameliaFactory(IFactory factory) {
        this.factory = factory;
    }

    public void releaseManager() {
        if (deltaPropertiesAccessor != null) {
            deltaPropertiesAccessor.release();
            deltaPropertiesAccessor = null;
        }

        if (commitedPropertiesAccessor != null) {
            commitedPropertiesAccessor.release();
            commitedPropertiesAccessor = null;
        }

        if (originalPropertiesAccessor != null) {
            originalPropertiesAccessor.release();
            originalPropertiesAccessor = null;
        }
    }

    public void commitManager(FacesContext context) {
        if (deltaPropertiesAccessor == null) {
            return;
        }

        deltaPropertiesAccessor.commitProperties(context);
        deltaPropertiesAccessor.release();
        deltaPropertiesAccessor = null;

        if (commitedPropertiesAccessor != null
                && commitedPropertiesAccessor.hasModifiedProperties() == false) {
            commitedPropertiesAccessor.release();
            commitedPropertiesAccessor = null;
        }
    }

    public IPropertiesManager copyOriginalState() {
        BasicPropertiesManager copy = new BasicPropertiesManager();
        copy.setCameliaFactory(factory);

        copy.originalPropertiesAccessor = originalPropertiesAccessor;

        return copy;
    }

    public IPropertiesAccessor getPropertiesAccessor(boolean enableDelta,
            boolean forceDelta) {

        if (enableDelta) {
            if (forceDelta) {
                if (deltaPropertiesAccessor == null) {
                    if (originalPropertiesAccessor == null) {
                        originalPropertiesAccessor = createPropertiesAccessor();
                    }

                    if (COMMITED_PROPERTIES_ENABLED) {
                        if (commitedPropertiesAccessor == null) {
                            commitedPropertiesAccessor = originalPropertiesAccessor
                                    .createDeltaPropertiesAccessor();
                        }
                    }

                    if (commitedPropertiesAccessor != null) {
                        deltaPropertiesAccessor = commitedPropertiesAccessor
                                .createDeltaPropertiesAccessor();

                    } else {
                        deltaPropertiesAccessor = originalPropertiesAccessor
                                .createDeltaPropertiesAccessor();
                    }
                }

                return deltaPropertiesAccessor;
            }

            if (deltaPropertiesAccessor != null) {
                return deltaPropertiesAccessor;
            }
        }

        if (commitedPropertiesAccessor != null) {
            return commitedPropertiesAccessor;
        }

        if (originalPropertiesAccessor != null || forceDelta == false) {
            return originalPropertiesAccessor;
        }

        originalPropertiesAccessor = createPropertiesAccessor();

        return originalPropertiesAccessor;

    }

    public void restoreManagerState(FacesContext context, Object state) {
        if (state == null) {
            return;
        }

        Object states[] = (Object[]) state;

        if (states != null && states[0] != null) {
            originalPropertiesAccessor = createPropertiesAccessor();

            originalPropertiesAccessor.restoreState(context, states[0]);

            if (COMMITED_PROPERTIES_ENABLED) {
                if (states[1] != null) {
                    commitedPropertiesAccessor = originalPropertiesAccessor
                            .createDeltaPropertiesAccessor();

                    commitedPropertiesAccessor.restoreState(context, states[1]);
                }
            }

            if (states[2] != null) {
                if (commitedPropertiesAccessor != null) {
                    deltaPropertiesAccessor = commitedPropertiesAccessor
                            .createDeltaPropertiesAccessor();

                } else {
                    deltaPropertiesAccessor = originalPropertiesAccessor
                            .createDeltaPropertiesAccessor();
                }

                deltaPropertiesAccessor.restoreState(context, states[2]);
            }
        }
    }

    public Object saveManagerState(FacesContext context) {
        if (originalPropertiesAccessor == null) {
            return null;
        }

        Object ts[] = new Object[3];

        if (originalPropertiesAccessor != null) {
            ts[0] = originalPropertiesAccessor.saveState(context);

            if (commitedPropertiesAccessor != null
                    && commitedPropertiesAccessor.hasModifiedProperties()) {
                ts[1] = commitedPropertiesAccessor.saveState(context);
            }

            if (deltaPropertiesAccessor != null
                    && deltaPropertiesAccessor.hasModifiedProperties()) {
                ts[2] = deltaPropertiesAccessor.saveState(context);
            }
        }

        return ts;
    }

    protected IPropertiesAccessor createPropertiesAccessor() {
        return this;
    }

    public String toString() {
        if (deltaPropertiesAccessor != null) {
            return "[PropertiesManager:DELTA properties="
                    + deltaPropertiesAccessor.toString() + "]";
        }

        if (commitedPropertiesAccessor != null) {
            return "[PropertiesManager:COMMITED properties="
                    + commitedPropertiesAccessor.toString() + "]";
        }

        if (originalPropertiesAccessor != null) {
            if (originalPropertiesAccessor == this) {
                return "[PropertiesManager:ORIGINAL properties="
                        + super.toString() + "]";
            }
            return "[PropertiesManager:ORIGINAL properties="
                    + originalPropertiesAccessor.toString() + "]";
        }

        return "[PropertiesManager:EMPTY " + super.toString() + "]";
    }
}
