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

    private static final Log LOG = LogFactory
            .getLog(BasicPropertiesManager.class);

    private static final boolean debugEnabled = LOG.isDebugEnabled();

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
        if (debugEnabled) {
            LOG.debug("Commit manager deltaProperties="
                    + deltaPropertiesAccessor);
        }

        if (deltaPropertiesAccessor == null) {
            return;
        }

        deltaPropertiesAccessor.commitProperties(context);
        deltaPropertiesAccessor.release();
        deltaPropertiesAccessor = null;

        if (commitedPropertiesAccessor != null
                && commitedPropertiesAccessor.hasModifiedProperties() == false) {

            if (debugEnabled) {
                LOG.debug("Release commited properties ="
                        + commitedPropertiesAccessor);
            }

            commitedPropertiesAccessor.release();
            commitedPropertiesAccessor = null;
        }
    }

    public IPropertiesManager copyOriginalState(FacesContext facesContext) {
        BasicPropertiesManager copy = new BasicPropertiesManager();
        copy.setCameliaFactory(factory);

        if (copy.originalPropertiesAccessor != null) {
            copy.originalPropertiesAccessor = originalPropertiesAccessor
                    .copyOriginalProperties(facesContext);
        }

        return copy;
    }

    public IPropertiesAccessor getPropertiesAccessor(boolean enableDelta,
            boolean forceDelta) {

        if (debugEnabled) {
            LOG.debug("getPropertiesAccessor(enableDelta=" + enableDelta
                    + ", forceDelta=" + forceDelta + ") => ");
        }

        if (enableDelta) {
            if (forceDelta) {
                if (deltaPropertiesAccessor == null) {
                    if (originalPropertiesAccessor == null) {
                        originalPropertiesAccessor = createPropertiesAccessor();

                        if (debugEnabled) {
                            LOG.debug("  create originalPropertiesAccessor="
                                    + originalPropertiesAccessor);
                        }
                    }

                    if (COMMITED_PROPERTIES_ENABLED) {
                        if (commitedPropertiesAccessor == null) {
                            commitedPropertiesAccessor = originalPropertiesAccessor
                                    .createDeltaPropertiesAccessor();

                            if (debugEnabled) {
                                LOG.debug("  create commitedPropertiesAccessor="
                                        + commitedPropertiesAccessor);
                            }
                        }
                    }

                    if (commitedPropertiesAccessor != null) {
                        deltaPropertiesAccessor = commitedPropertiesAccessor
                                .createDeltaPropertiesAccessor();

                        if (debugEnabled) {
                            LOG.debug("  create deltaPropertiesAccessor from commited ="
                                    + deltaPropertiesAccessor);
                        }

                    } else {
                        deltaPropertiesAccessor = originalPropertiesAccessor
                                .createDeltaPropertiesAccessor();

                        if (debugEnabled) {
                            LOG.debug("  create deltaPropertiesAccessor from original ="
                                    + deltaPropertiesAccessor);
                        }
                    }
                }

                if (debugEnabled) {
                    LOG.debug("  returns deltaPropertiesAccessor="
                            + deltaPropertiesAccessor);
                }

                return deltaPropertiesAccessor;
            }
        }

        // On peut avoir des deltas ... même si on ne peut plus en créer
        if (deltaPropertiesAccessor != null) {
            if (debugEnabled) {
                LOG.debug("  returns deltaPropertiesAccessor="
                        + deltaPropertiesAccessor);
            }

            return deltaPropertiesAccessor;
        }

        if (commitedPropertiesAccessor != null) {
            if (debugEnabled) {
                LOG.debug("  returns commitedPropertiesAccessor="
                        + commitedPropertiesAccessor);
            }

            return commitedPropertiesAccessor;
        }

        if (originalPropertiesAccessor != null || forceDelta == false) {
            if (debugEnabled) {
                LOG.debug("  returns originalPropertiesAccessor="
                        + originalPropertiesAccessor);
            }

            return originalPropertiesAccessor;
        }

        originalPropertiesAccessor = createPropertiesAccessor();

        if (debugEnabled) {
            LOG.debug("  create and returns originalPropertiesAccessor="
                    + originalPropertiesAccessor);
        }

        return originalPropertiesAccessor;

    }

    @SuppressWarnings("unused")
    public void restoreManagerState(FacesContext context, Object state) {
        if (state == null) {
            return;
        }

        Object states[] = (Object[]) state;

        if (debugEnabled) {
            if (states != null) {
                LOG.debug("Restore manager state [0]='" + states[0] + "' [1]='"
                        + states[1] + "' [2]='" + states[2] + "'.");
            } else {
                LOG.debug("Restore manager state states=" + states);

            }
        }

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
            if (debugEnabled) {
                LOG.debug("Save manager state returns null.");
            }

            return null;
        }

        Object states[] = new Object[3];

        if (originalPropertiesAccessor != null) {
            states[0] = originalPropertiesAccessor.saveState(context);

            if (commitedPropertiesAccessor != null
                    && commitedPropertiesAccessor.hasModifiedProperties()) {
                states[1] = commitedPropertiesAccessor.saveState(context);
            }

            if (deltaPropertiesAccessor != null
                    && deltaPropertiesAccessor.hasModifiedProperties()) {
                states[2] = deltaPropertiesAccessor.saveState(context);
            }
        }

        if (debugEnabled) {
            LOG.debug("Save manager state [0]='" + states[0] + "' [1]='"
                    + states[1] + "' [2]='" + states[2] + "'.");

        }

        return states;
    }

    protected IPropertiesAccessor createPropertiesAccessor() {
        return this;
    }

    @Override
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
