/*
 * $Id$
 * 
 */

package org.rcfaces.core.internal.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class WriterException extends IOException {

    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 473005368009076248L;

    private final UIComponent component;

    public WriterException(String message, Throwable throwable,
            UIComponent component) {
        super(message);

        initCause(throwable);

        this.component = component;
    }

    public UIComponent getUIComponent() {
        return component;
    }
}
