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
 * Ajout de la JavaDoc
 * Ajout de TablePaneTools
 * Refonte du systeme de selection des Tabs
 *
 * Revision 1.1  2002/09/09 07:28:49  oeuillot
 * Nouveau nom pour  com.vedana.adonis.factory
 *
 * Revision 1.4  2002/09/05 09:25:00  oeuillot
 * Ajout de checkButton et radioButton
 * Met a jour qq tags CVS
 *
 */

package org.rcfaces.core.internal.renderkit;

/**
 * Probleme d'analyse de l'aspect graphique d'un composant.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ParserException extends Exception {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -8711696478442649846L;

    public ParserException(String message, Throwable throwable) {
        super(message, throwable);
    }
}