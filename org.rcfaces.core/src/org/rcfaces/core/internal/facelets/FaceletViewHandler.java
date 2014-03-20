/*
 * $Id: FaceletViewHandler.java,v 1.3 2007/09/13 08:38:13 oeuillot Exp $
 */
package org.rcfaces.core.internal.facelets;

import java.io.IOException;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: oeuillot $)
 * @version $Revision: 1.3 $ $Date: 2007/09/13 08:38:13 $
 */
public class FaceletViewHandler extends com.sun.facelets.FaceletViewHandler {
    private static final String REVISION = "$Revision: 1.3 $";

    private static final Log LOG = LogFactory.getLog(FaceletViewHandler.class);

    public FaceletViewHandler(ViewHandler parent) {
        super(parent);
    }

    public void buildFaceletViewRoot(FacesContext facesContext,
            UIViewRoot viewRoot) throws IOException {
        buildView(facesContext, viewRoot);
    }

    public static boolean isFaceletProcessor(FacesContext context) {
        if (context.getApplication().getViewHandler() instanceof FaceletViewHandler) {
            LOG.debug("Facelet processor detected");
            return true;
        }

        LOG.debug("Facelet processor not detected");
        return false;
    }
}
