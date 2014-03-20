/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.AbstractCompositeContentAccessorHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractScriptContentAccessorHandler extends
        AbstractCompositeContentAccessorHandler implements
        IScriptContentAccessorHandler {
    

    public static IScriptContentAccessorHandler getScriptContentAccessorHandler(
            FacesContext facesContext) {
        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        return (IScriptContentAccessorHandler) rcfacesContext
                .getProvidersRegistry().getProvider(SCRIPT_CONTENT_PROVIDER_ID);
    }
}
