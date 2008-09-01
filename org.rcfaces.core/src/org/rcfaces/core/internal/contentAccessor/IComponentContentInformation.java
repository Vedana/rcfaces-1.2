/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentContentInformation {
    UIComponent getComponent();

    void setComponent(UIComponent component);

    String getComponentClientId();

    void setComponentClientId(String componentClientId);

    void setComponent(UIComponent component, String componentClientId);
}
