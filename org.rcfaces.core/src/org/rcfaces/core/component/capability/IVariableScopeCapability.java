/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.el.ValueBinding;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IVariableScopeCapability {
    String getScopeVar();

    void setScopeVar(String scopeVar);

    ValueBinding getScopeValue();

    void setScopeValue(ValueBinding valueBinding);
}
