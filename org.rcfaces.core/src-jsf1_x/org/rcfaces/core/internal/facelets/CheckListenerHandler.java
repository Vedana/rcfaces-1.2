/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.CheckListenerTag;

import com.sun.facelets.tag.TagConfig;

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