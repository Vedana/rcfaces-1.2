/*
 * $Id$
 * 
 */
package org.rcfaces.core.preference;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentPreferences implements
        IComponentPreferences {

    private static final long serialVersionUID = -161366892815882804L;

    private transient boolean transientValue;

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(boolean newTransientValue) {
        this.transientValue = newTransientValue;
    }

}
