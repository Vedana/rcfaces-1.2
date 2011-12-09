/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.UserEventListenerTag;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserEventListenerHandler extends AbstractListenerHandler {
    private static final String REVISION = "$Revision$";

    public UserEventListenerHandler(TagConfig config) {
        super(config);
    }

    @Override
    protected void addListener(Object listener, UIComponent component) {
        UserEventListenerTag.addUserEventListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "userEvent";
    }

}