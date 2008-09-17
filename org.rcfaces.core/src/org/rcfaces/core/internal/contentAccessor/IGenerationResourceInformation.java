/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGenerationResourceInformation extends StateHolder {
    Object getAttribute(String attributeName);

    UIComponent getComponent();

    String getComponentClientId();

    IFilterProperties getFilterProperties();

    boolean isProcessAtRequest();
}
