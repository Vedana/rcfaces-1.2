/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/11/09 19:09:09  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProcessContext {

    String getNamingSeparator();

    FacesContext getFacesContext();
/*
    void setChangeFacesContext(FacesContext facesContext);
*/
    boolean getDebugMode();

    boolean getProfilerMode();

    boolean isDesignerMode();

    Locale getUserLocale();

    String getAbsolutePath(String uri, boolean containsContextPath);

    String getRelativePath(String uri);

    void changeBaseHREF(String base);

    String getBaseHREF();
}
