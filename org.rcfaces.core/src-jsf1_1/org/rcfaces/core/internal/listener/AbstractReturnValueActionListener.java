/*
 * $Id$
 */
package org.rcfaces.core.internal.listener;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.FacesEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.service.IEventReturnValue;

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

    protected void processReturn(FacesContext facesContext,
            MethodBinding binding, FacesEvent event, Object ret) {
        // Pas de traitement de retour !

        if (ret == null) {
            return;
        }

        if (event instanceof IEventReturnValue) {
            ((IEventReturnValue) event).setReturnValue(ret);
        }
    }

}
