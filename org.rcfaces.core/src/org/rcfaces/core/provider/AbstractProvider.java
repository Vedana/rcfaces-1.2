/*
 * $Id$
 * 
 */
package org.rcfaces.core.provider;

import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractProvider implements IProvider {
    private static final Log LOG = LogFactory.getLog(AbstractProvider.class);

    public void configureRules(Digester digester) {
    }

    public void startup(FacesContext facesContext) {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj instanceof AbstractProvider) == false) {
            return false;
        }

        return getId().equals(((AbstractProvider) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "[AbstractProvider '" + getId() + "']";
    }
}
