/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 */
package org.rcfaces.core.internal.component;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IDataMapAccessor {

    Object getData(String name, FacesContext facesContext);

    Object setData(String name, Object data, FacesContext facesContext);

    void setData(String name, ValueBinding value, FacesContext facesContext);

    Object removeData(String name, FacesContext facesContext);

    int getDataCount();

    String[] listDataKeys(FacesContext facesContext);

    Map getDataMap(FacesContext facesContext);
}
