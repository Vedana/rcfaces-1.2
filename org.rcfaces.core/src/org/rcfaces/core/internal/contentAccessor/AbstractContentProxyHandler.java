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

    @Override
    public boolean equals(Object obj) {
        if (obj == null
                || (obj instanceof AbstractContentProxyHandler) == false) {
            return false;
        }

        return getId().equals(((AbstractContentProxyHandler) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "[AbstractContentProxyHandler '" + getId() + "']";
    }

}
