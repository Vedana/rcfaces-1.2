/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.manager;

import java.util.Map;

import javax.el.ValueExpression;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IValidationParameters {
    void setValidationParameter(String name, ValueExpression value,
            boolean client);

    String setValidationParameter(String name, String value, boolean clientSide);

    String removeValidationParameter(String name);

    String getValidationParameter(String name);

    boolean isClientSideValidationParameter(String name);

    int getValidationParametersCount();

    Map<String, String> getValidationParametersMap();

    Map<String, String> getClientValidationParametersMap();
}
