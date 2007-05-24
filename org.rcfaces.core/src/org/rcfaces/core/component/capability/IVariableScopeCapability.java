/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

import javax.faces.el.ValueBinding;

/**
 * A couple string-binding specifying the name of a variable representing a
 * shortcut to a binding. ex: if scopeVar "bat" is associated to scopeValue
 * "bean.attribute1.attribute2" then the use of "bat.attribute3" will be
 * equivalente to "bean.attribute1.attribute2.attribute3"
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IVariableScopeCapability {

    /**
     * Returns a string value specifying the name of a variable representing a
     * shortcut to a binding.
     * 
     * @return variable name
     */
    String getScopeVar();

    /**
     * Sets a string value specifying the name of a variable representing a
     * shortcut to a binding.
     * 
     * @param scopeVar
     *            variable name
     */
    void setScopeVar(String scopeVar);

    /**
     * Returns a value binding associated to a variable representing a shortcut.
     * 
     * @return value binding
     */
    ValueBinding getScopeValue();

    /**
     * Sets a value binding associated to a variable representing a shortcut.
     * 
     * @param valueBinding
     *            value binding
     */
    void setScopeValue(ValueBinding valueBinding);
}
