/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.DoubleClickListenerTag;

import com.sun.facelets.tag.TagConfig;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DoubleClickListenerHandler extends AbstractListenerHandler {
    private static final String REVISION = "$Revision$";

    public DoubleClickListenerHandler(TagConfig config) {
        super(config);
    }

    protected void addListener(Object listener, UIComponent component) {
        DoubleClickListenerTag.addDoubleClickListener(listener, component);
    }

    protected String getListenerName() {
        return "doubleClick";
    }

}