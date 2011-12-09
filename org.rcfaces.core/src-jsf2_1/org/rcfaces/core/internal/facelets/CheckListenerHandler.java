/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.CheckListenerTag;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CheckListenerHandler extends AbstractListenerHandler {
    private static final String REVISION = "$Revision$";

    public CheckListenerHandler(TagConfig config) {
        super(config);
    }

    protected void addListener(Object listener, UIComponent component) {
        CheckListenerTag.addCheckListener(listener, component);
    }

    protected String getListenerName() {
        return "check";
    }

}