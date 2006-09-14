/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractProperties implements IProperties {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentData#getBoolProperty(java.lang.String,
     *      boolean)
     */
    public final boolean getBoolProperty(String name, boolean defaultValue) {
        Boolean b = getBooleanProperty(name);
        if (b == null) {
            return defaultValue;
        }

        return b.booleanValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentData#getBooleanProperty(java.lang.String)
     */
    public Boolean getBooleanProperty(String name) {
        Object s = getProperty(name);
        if (s == null) {
            return null;
        }

        if (s instanceof Boolean) {
            return (Boolean) s;
        }

        if (s instanceof String) {
            return Boolean.valueOf((String) s);
        }

        return null;
    }

    public Number getNumberProperty(String name) {
        Object s = getProperty(name);
        if (s == null) {
            return null;
        }

        if (s instanceof Number) {
            return (Number) s;
        }

        if (s instanceof String) {
            String ss = (String) s;

            if (ss.indexOf('.') >= 0 || ss.indexOf('E') >= 0) {
                return Double.valueOf(ss);
            }

            return Long.valueOf(ss);
        }

        return null;
    }

    public String getStringProperty(String name) {
        Object s = getProperty(name);
        if (s == null) {
            return null;
        }

        return String.valueOf(s);
    }

    public String getStringProperty(String name, String defaultValue) {
        String s = getStringProperty(name);
        if (s == null) {
            return defaultValue;
        }

        return s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IComponentData#getIntProperty(java.lang.String,
     *      int)
     */
    public int getIntProperty(String name, int defaultValue) {
        Number i = getNumberProperty(name);
        if (i == null) {
            return defaultValue;
        }

        return i.intValue();
    }

    public abstract Object getProperty(String name);
}
