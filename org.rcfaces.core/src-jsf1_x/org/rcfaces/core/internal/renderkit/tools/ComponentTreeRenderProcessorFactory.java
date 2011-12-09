/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit.tools;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.facelets.FaceletViewHandler;
import org.rcfaces.core.internal.facelets.FaceletsTreeRendererProcessor;
import org.rcfaces.core.internal.tools.AsyncModeTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentTreeRenderProcessorFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ComponentTreeRenderProcessorFactory.class);

    private final FacesContext facesContext;

    private ComponentTreeRenderProcessorFactory(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public static IComponentTreeRenderProcessor get(FacesContext facesContext) {
        if (Constants.FACELETS_SUPPORT) {
            if (AsyncModeTools.isTagProcessor(facesContext) == false) {
                if (facesContext.getApplication().getViewHandler() instanceof FaceletViewHandler) {
                    return new FaceletsTreeRendererProcessor(facesContext);
                }
            }
        }

        return new DefaultTreeRendererProcessor(facesContext);
    }

}
