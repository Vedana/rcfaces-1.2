/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.component.capability;

import java.util.Locale;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ILocalizedAttributesCapability {
    Locale getAttributesLocale();

    void setAttributesLocale(Locale locale);
}
