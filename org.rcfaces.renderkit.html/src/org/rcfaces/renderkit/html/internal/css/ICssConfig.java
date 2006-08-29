/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/02/06 16:47:05  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 */
package org.rcfaces.renderkit.html.internal.css;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ICssConfig {

    String getDefaultStyleSheetURI();

    String getStyleSheetFileName();

}
