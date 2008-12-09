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
    String SOURCE_URL = "org.rcfaces.generation.SOURCE_URL";

    String SOURCE_KEY = "org.rcfaces.generation.SOURCE_KEY";

    Object getAttribute(String attributeName);

    Object setAttribute(String attributeName, Object value);

    UIComponent getComponent();

    String getComponentClientId();

    IFilterProperties getFilterProperties();

    boolean isProcessAtRequest();
}
