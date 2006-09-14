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
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 * Revision 1.2  2003/01/24 12:07:04  oeuillot
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