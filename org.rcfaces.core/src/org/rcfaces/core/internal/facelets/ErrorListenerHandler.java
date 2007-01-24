/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.ErrorListenerTag;

import com.sun.facelets.tag.TagConfig;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ErrorListenerHandler extends AbstractListenerHandler {
    private static final String REVISION = "$Revision$";

    public ErrorListenerHandler(TagConfig config) {
        super(config);
    }

    protected void addListener(Object listener, UIComponent component) {
        ErrorListenerTag.addErrorListener(listener, component);
    }

    protected String getListenerName() {
        return "error";
    }

}