/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.renderkit.html.internal.service;

import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletResponse;

import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.service.log.LogService;
import org.rcfaces.renderkit.html.internal.Constants;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class LogHtmlService extends LogService {
    private static final String REVISION = "$Revision$";

    private static final String SERVICE_ID = Constants.getPackagePrefix()
            + ".Log";

    public void initialize(FacesContext facesContext) {
    }

    public static LogService getInstance(FacesContext facesContext) {

        IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
                facesContext).getServicesRegistry();
        if (serviceRegistry == null) {
            // Designer mode
            return null;
        }

        return (LogService) serviceRegistry.getService(facesContext,
                RenderKitFactory.HTML_BASIC_RENDER_KIT, SERVICE_ID);
    }

    public void service(FacesContext facesContext, String commandId) {
        Map parameters = facesContext.getExternalContext()
                .getRequestParameterMap();

        UIViewRoot viewRoot = facesContext.getViewRoot();

        ILogger logger = getLogger();

        for (int i = 0;; i++) {
            String name = (String) parameters.get("name" + i);
            if (name == null) {
                break;
            }
            String message = (String) parameters.get("message" + i);

            ClientException clientException = null;
            String exception = (String) parameters.get("exception" + i);
            if (exception != null) {
                clientException = new ClientException(viewRoot.getId(),
                        exception);
            }

            int iLevel = -1;
            String level = (String) parameters.get("level" + i);
            if (level != null) {
                iLevel = Integer.parseInt(level);
            }

            long lDate = 0;
            String date = (String) parameters.get("date" + i);
            if (date != null) {
                lDate = Long.parseLong(date);
            }

            logger.logException(facesContext, viewRoot, name, lDate, message,
                    iLevel, clientException);
        }

        ServletResponse response = (ServletResponse) facesContext
                .getExternalContext().getResponse();

        AbstractHtmlService.setNoCache(response);

        facesContext.responseComplete();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class ClientException extends Exception {
        private static final String REVISION = "$Revision$";

        private static final long serialVersionUID = 6346621862119596920L;

        private final String viewId;

        ClientException(String viewId, String exception) {
            super(exception);

            this.viewId = viewId;
        }

        public final String getViewId() {
            return viewId;
        }
    }
}
