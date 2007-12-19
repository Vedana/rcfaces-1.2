/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptEnableMode {

    void enableOnInit();

    void enableOnAccessKey();

    void enableOnFocus();

    void enableOnSubmit();

    void enableOnMessage();

    void enableOnOver();

    boolean isOnInitEnabled();

}
