/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.2  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.internal.listener;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.rcfaces.core.event.IServiceEventListener;
import org.rcfaces.core.event.ServiceEvent;
import org.rcfaces.core.internal.service.IEventReturnValue;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceEventActionListener extends AbstractActionListener
        implements IServiceEventListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { ServiceEvent.class };

    public ServiceEventActionListener() {
    }

    public ServiceEventActionListener(String expression) {
        super(expression);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.component.listener.IChangeListener#processChange(org.rcfaces.core.component.listener.ChangeEvent)
     */
    public void processServiceEvent(ServiceEvent event)
            throws AbortProcessingException {
        process(event);
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

    protected Class[] listParameterClasses() {
        return actionParameters;
    }
}