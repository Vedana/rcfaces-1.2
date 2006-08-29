/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.2  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class MessagesRepository {
    private static final String REVISION = "$Revision$";

    private final IJavaScriptRenderContext javascriptRenderContext;

    private List messages = null;

    public MessagesRepository(IJavaScriptRenderContext javascriptRenderContext) {
        this.javascriptRenderContext = javascriptRenderContext;
    }

    public String allocateFacesMessage(FacesMessage message,
            boolean mustDeclare[]) {
        if (messages != null) {
            for (Iterator it = messages.iterator(); it.hasNext();) {
                String key = (String) it.next();
                FacesMessage fm = (FacesMessage) it.next();

                if (messageEquals(message, fm) == false) {
                    continue;
                }

                return key;
            }

        } else {
            messages = new ArrayList(8);
        }

        mustDeclare[0] = true;

        String key = javascriptRenderContext.allocateVarName();

        messages.add(key);
        messages.add(message);

        return key;
    }

    private boolean messageEquals(FacesMessage m1, FacesMessage m2) {
        if (m1 == m2) {
            return true;
        }

        Severity sev1 = m1.getSeverity();
        Severity sev2 = m2.getSeverity();
        if (sev1 != sev2) {
            if (sev1 == null || sev1.equals(sev2) == false) {
                return false;
            }
        }

        String sum1 = m1.getSummary();
        String sum2 = m2.getSummary();
        if (sum1 != sum2) {
            if (sum1 == null || sum1.equals(sum2) == false) {
                return false;
            }
        }

        String detail1 = m1.getDetail();
        String detail2 = m2.getDetail();
        if (detail1 != detail2) {
            if (detail1 == null || detail1.equals(detail2) == false) {
                return false;
            }
        }

        return true;
    }
}
