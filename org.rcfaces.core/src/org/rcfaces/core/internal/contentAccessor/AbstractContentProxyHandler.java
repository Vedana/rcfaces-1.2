/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractContentProxyHandler implements
        IContentProxyHandler {
    private static final String REVISION = "$Revision$";

    public boolean equals(Object obj) {
        if (obj == null
                || (obj instanceof AbstractContentProxyHandler) == false) {
            return false;
        }

        return getId().equals(((AbstractContentProxyHandler) obj).getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public String toString() {
        return "[AbstractContentProxyHandler '" + getId() + "']";
    }

}
