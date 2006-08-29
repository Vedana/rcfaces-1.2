/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.core.preference;

import java.io.Serializable;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IComponentPreference extends StateHolder, Serializable {
    void loadPreference(FacesContext facesContext, UIComponent component);

    void savePreference(FacesContext facesContext, UIComponent component);
}
