/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.internal.images.operation;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BrithnessOperation extends ContrastBrithnessOperation {
    private static final String REVISION = "$Revision$";

    protected String getDefaultPropertyName() {
        return getOffsetPropertyName();
    }

    public String getName() {
        return "Brithness operation";
    }
}
