/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.io.Serializable;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptEnableModeImpl implements IJavaScriptEnableMode,
        Serializable {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 8767842193854145775L;

    private static final String STRING_EMPTY_ARRAY[] = new String[0];

    public static final int ONINIT = 0x001;

    public static final int ONSUBMIT = 0x010;

    public static final int ONFOCUS = 0x100;

    public static final int ONACCESSKEY = 0x200;

    public static final int ONMESSAGE = 0x400;

    public static final int ONOVER = 0x800;

    private int mode = 0;

    JavaScriptEnableModeImpl() {
    }

    public void enableOnInit() {
        mode |= ONINIT;
    }

    public void enableOnSubmit() {
        mode |= ONSUBMIT;
    }

    public void enableOnFocus() {
        mode |= ONFOCUS;
    }

    public void enableOnAccessKey() {
        mode |= ONACCESSKEY;
    }

    public void enableOnMessage() {
        mode |= ONMESSAGE;
    }

    public void enableOnOver() {
        mode |= ONOVER;
    }

    public int getMode() {
        return mode;
    }

    public String toString() {
        String s = "[JavaScriptEnabledMode";
        if ((mode & ONINIT) > 0) {
            s += " INIT";
        }
        if ((mode & ONSUBMIT) > 0) {
            s += " SUBMIT";
        }
        if ((mode & ONFOCUS) > 0) {
            s += " FOCUS";
        }

        return s + " (" + mode + ")]";
    }

    public boolean isOnInitEnabled() {
        return (getMode() & ONINIT) > 0;
    }

}