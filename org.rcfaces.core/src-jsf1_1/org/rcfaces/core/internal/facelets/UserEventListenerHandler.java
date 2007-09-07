/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.UserEventListenerTag;

import com.sun.facelets.tag.TagConfig;

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

    protected void addListener(Object listener, UIComponent component) {
        UserEventListenerTag.addUserEventListener(listener, component);
    }

    protected String getListenerName() {
        return "userEvent";
    }

}