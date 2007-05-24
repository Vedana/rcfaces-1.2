/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * A string value specifying the name of a request scope attribute under which
 * the resource bundle will be exposed as a Map.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBundleVarCapability {

    /**
     * Returns a string value specifying the name of a request scope attribute
     * under which the resource bundle will be exposed as a Map.
     * 
     * @return bundle var name
     */
    String getBundleVar();

    /**
     * Sets a string value specifying the name of a request scope attribute
     * under which the resource bundle will be exposed as a Map.
     * 
     * @param bundleVar
     *            bundle var name
     */
    void setBundleVar(String bundleVar);
}
