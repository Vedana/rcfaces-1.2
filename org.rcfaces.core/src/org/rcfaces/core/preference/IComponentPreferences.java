/*
 * $Id$
 * 
 */
package org.rcfaces.core.preference;

import java.io.Serializable;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentPreferences extends StateHolder, Serializable {
    void loadPreferences(FacesContext facesContext, UIComponent component);

    void savePreferences(FacesContext facesContext, UIComponent component);
}
