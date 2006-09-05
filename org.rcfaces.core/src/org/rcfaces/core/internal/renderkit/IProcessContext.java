/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante gï¿½nï¿½rale de spï¿½cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

import javax.faces.context.ExternalContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IProcessContext {

    char getNamingSeparatorChar();

    ExternalContext getExternalContext();

    boolean getDebugMode();

    boolean getProfilerMode();

    Locale getUserLocale();

    String getAbsolutePath(String uri, boolean containsContextPath);

    String getRelativePath(String uri);

    void changeBaseHREF(String base);

    String getBaseHREF();
}
