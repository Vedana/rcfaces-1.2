/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import java.util.Map;

import javax.faces.el.ValueBinding;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValidationParameters {
    void setValidationParameter(String name, ValueBinding value, boolean client);

    String setValidationParameter(String name, String value, boolean clientSide);

    String removeValidationParameter(String name);

    String getValidationParameter(String name);

    boolean isClientSideValidationParameter(String name);

    int getValidationParametersCount();

    Map getValidationParametersMap();

    Map getClientValidationParametersMap();
}
