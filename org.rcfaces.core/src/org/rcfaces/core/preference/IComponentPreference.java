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
public interface IComponentPreference extends StateHolder, Serializable {
    void loadPreference(FacesContext facesContext, UIComponent component);

    void savePreference(FacesContext facesContext, UIComponent component);
}
