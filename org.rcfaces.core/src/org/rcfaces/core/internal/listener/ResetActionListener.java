/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.2  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.AbortProcessingException;

import org.rcfaces.core.event.IResetListener;
import org.rcfaces.core.event.ResetEvent;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ResetActionListener extends AbstractActionListener implements
        IResetListener {
    private static final String REVISION = "$Revision$";

    private static final Class actionParameters[] = { ResetEvent.class };

    public ResetActionListener(String expression) {
        super(expression);
    }

    public ResetActionListener() {
    }

    public void processReset(ResetEvent event) throws AbortProcessingException {
        process(event);
    }

    protected Class[] listParameterClasses() {
        return actionParameters;
    }

}