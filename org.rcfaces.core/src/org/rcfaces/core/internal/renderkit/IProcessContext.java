/*
 * $Id$
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
