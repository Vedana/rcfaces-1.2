/*
 * $Id$
 * 
 */
package org.rcfaces.core.provider;

import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractProvider implements IProvider {
    private static final String REVISION = "$Revision$";

    public void configureRules(Digester digester) {
    }

    public void startup(FacesContext facesContext) {
    }
}
