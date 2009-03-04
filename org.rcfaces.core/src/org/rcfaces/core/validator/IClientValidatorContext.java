/*
 * $Id$
 */
package org.rcfaces.core.validator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidatorContext {

    FacesContext getFacesContext();

    IComponentRenderContext getComponentRenderContext();

    IParameter[] getParameters();

    void setInputValue(String input);

    void setOutputValue(String output);

    String getInput();

    String getOutputValue();

    void setLastError(String summary, String detail,
            FacesMessage.Severity severity);

    String getLastErrorSummary();

    String getLastErrorDetail();

    FacesMessage.Severity getLastErrorSeverity();

    boolean containsAttribute(String key);

    Object getAttribute(String key);

    Object setAttribute(String key, Object value);

    Object removeAttribute(String key);

}
