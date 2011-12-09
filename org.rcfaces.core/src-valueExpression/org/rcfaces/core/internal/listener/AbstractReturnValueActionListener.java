/*
 * $Id$
 */
package org.rcfaces.core.internal.listener;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.service.IApplicationExceptionCapability;
import org.rcfaces.core.internal.service.IEventReturnValue;
import org.rcfaces.core.lang.ApplicationException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractReturnValueActionListener extends
        AbstractActionListener {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractReturnValueActionListener.class);

    public AbstractReturnValueActionListener() {
    }

    public AbstractReturnValueActionListener(String expression) {
        super(expression);
    }

    public AbstractReturnValueActionListener(String expression,
            boolean partialRendering) {
        super(expression, partialRendering);
    }

    protected void processReturn(FacesContext facesContext,
            MethodExpression binding, FacesEvent event, Object ret) {
        // Pas de traitement de retour !

        if (LOG.isDebugEnabled()) {
            LOG.debug("Process return value '" + ret + "'.");
        }

        if (ret == null) {
            return;
        }

        if (event instanceof IEventReturnValue) {
            ((IEventReturnValue) event).setReturnValue(ret);
        }
    }

    protected Exception processException(ELException ex, FacesEvent event) {
        Throwable cause = ex.getCause();

        if (event instanceof IApplicationExceptionCapability) {
            if (cause instanceof ApplicationException) {
                ((IApplicationExceptionCapability) event)
                        .setApplicationException((ApplicationException) cause);

                return null;
            }
        }

        return super.processException(ex, event);
    }

}