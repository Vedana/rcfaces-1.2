/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.facelets.FaceletViewHandler;
import org.rcfaces.core.internal.util.ClassLocator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncModeTools {

    private static final Log LOG = LogFactory.getLog(AsyncModeTools.class);

    private static final String FACELETS_CLASS_NAME = "com.sun.facelets.Facelet";

    private static final String FACELETS_PROCESSOR_PROPERTY = "org.rcfaces.core.internal.tools.FACELETS_PROCESSOR";

    public static boolean isTagProcessor(FacesContext facesContext) {
        return isFaceletsProcessor(facesContext) == false;
    }

    private static boolean isFaceletsProcessor(FacesContext context) {
        if (Constants.FACELETS_SUPPORT == false) {
            return false;
        }

        if (context == null) {
            context = FacesContext.getCurrentInstance();
        }

        Map applicationMap = context.getExternalContext().getApplicationMap();

        Boolean b;
        synchronized (AsyncModeTools.class) {
            b = (Boolean) applicationMap.get(FACELETS_PROCESSOR_PROPERTY);
            if (b == null) {
                b = Boolean.valueOf(searchFaceletsProcessor(context));

                applicationMap.put(FACELETS_PROCESSOR_PROPERTY, b);
            }
        }

        return b.booleanValue();
    }

    private static boolean searchFaceletsProcessor(FacesContext context) {
        Class cls;

        try {
            cls = ClassLocator.load(FACELETS_CLASS_NAME, ComponentTools.class,
                    context);

        } catch (ClassNotFoundException ex) {

            LOG.debug("Class '" + FACELETS_CLASS_NAME + "' is not found !", ex);
            return false;
        }

        LOG.debug("Class '" + cls.getName() + "' detected");

        return FaceletViewHandler.isFaceletProcessor(context);
    }

    public static int getEnableValue(FacesContext context) {
        if (isFaceletsProcessor(context)) {
            return IAsyncRenderModeCapability.TREE_ASYNC_RENDER_MODE;
        }

        // Tag mode ?
        return Constants.ENABLE_ASYNC_MODE_VALUE;
    }

}
