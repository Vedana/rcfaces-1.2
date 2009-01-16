/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentTools0 {

    private static final Log LOG = LogFactory.getLog(ComponentTools0.class);

    protected static void broadcastActionCommand(FacesContext facesContext,
            UICommand component) {
        MethodBinding binding;
        String outcome = null;

        binding = component.getAction();
        if (binding != null) {
            try {
                Object invokeResult = binding.invoke(facesContext, null);

                if (invokeResult != null) {
                    outcome = invokeResult.toString();
                }
                // else, default to null, as assigned above.
            } catch (MethodNotFoundException e) {
                LOG.error(e);

                throw new FacesException(binding.getExpressionString() + ": "
                        + e.getMessage(), e);
            } catch (EvaluationException e) {
                LOG.error(e);

                throw new FacesException(binding.getExpressionString() + ": "
                        + e.getMessage(), e);
            }
        }

        Application application = facesContext.getApplication();

        // Retrieve the NavigationHandler instance..

        NavigationHandler navHandler = application.getNavigationHandler();

        // Invoke nav handling..

        navHandler.handleNavigation(facesContext, (null != binding) ? binding
                .getExpressionString() : null, outcome);

        // Trigger a switch to Render Response if needed
        facesContext.renderResponse();

    }

}
