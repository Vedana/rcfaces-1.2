/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit.tools;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractTreeRendererProcessor implements IComponentTreeRenderProcessor {
    private static final String REVISION = "$Revision$";

    protected final FacesContext facesContext;

    protected AbstractTreeRendererProcessor(FacesContext facesContext) {
        this.facesContext = facesContext;
    }
}
