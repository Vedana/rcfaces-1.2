/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.ErrorListenerTag;

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

    @Override
    protected void addListener(Object listener, UIComponent component) {
        ErrorListenerTag.addErrorListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "error";
    }

}