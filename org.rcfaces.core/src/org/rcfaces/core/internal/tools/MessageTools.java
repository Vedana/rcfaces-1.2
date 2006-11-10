/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class MessageTools {
    private static final String REVISION = "$Revision$";

    private static final Iterator EMPTY_ITERATOR = Collections.EMPTY_LIST
            .iterator();

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

        return EMPTY_ITERATOR;
    }
}
