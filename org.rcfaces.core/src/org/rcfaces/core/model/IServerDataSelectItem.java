/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IServerDataSelectItem {
    Map getServerDataMap();

    boolean isServerDataEmpty();
}
