/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/11/17 10:04:56  oeuillot
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
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public final class MessageTools {
    private static final String REVISION = "$Revision$";

    public static final Iterator listMessages(FacesContext context,
            String forComponent, UIComponent component) {

        if (forComponent == null) {
            return context.getMessages();
        }

        if (forComponent.length() < 1) {
            return context.getMessages(null);
        }

        Iterator iterator = null;

        UIComponent result = ComponentTools.getForComponent(context,
                forComponent, component);
        if (result != null) {
            iterator = context.getMessages(result.getClientId(context));
        }

        if (iterator != null) {
            return iterator;
        }

        return Collections.EMPTY_LIST.iterator();
    }
}
