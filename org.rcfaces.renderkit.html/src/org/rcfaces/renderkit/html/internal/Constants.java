/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class Constants {
    private static final String REVISION = "$Revision$";

    private static final String CONSTANT_PREFIX;
    static {
        String name = Constants.class.getPackage().getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    public static final String getPackagePrefix() {
        return CONSTANT_PREFIX;
    }
}
