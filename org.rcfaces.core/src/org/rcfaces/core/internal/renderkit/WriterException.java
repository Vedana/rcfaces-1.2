/*
 * $Id$
 * 
 */

package org.rcfaces.core.internal.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;

/**
 * Probleme de g�n�ration de l'aspect graphique d'un composant.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WriterException extends IOException {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 473005368009076248L;

    private final UIComponent component;

    private final Throwable throwable;

    public WriterException(String message, Throwable throwable,
            UIComponent component) {
        super(message);

        this.throwable = throwable;
        this.component = component;
    }

    public UIComponent getUIComponent() {
        return component;
    }

    public final Throwable getRootCause() {
        return throwable;
    }
}
