/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IProperties {

    boolean containsKey(String name);

    Object getProperty(String name);

    String getStringProperty(String name);

    String getStringProperty(String name, String defaultValue);

    boolean getBoolProperty(String name, boolean defaultValue);

    Boolean getBooleanProperty(String name);

    int getIntProperty(String name, int defaultValue);

    Number getNumberProperty(String name);

}
