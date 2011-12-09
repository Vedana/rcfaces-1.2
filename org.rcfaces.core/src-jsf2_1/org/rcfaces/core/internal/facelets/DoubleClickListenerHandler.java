/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.DoubleClickListenerTag;

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

    @Override
    protected void addListener(Object listener, UIComponent component) {
        DoubleClickListenerTag.addDoubleClickListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "doubleClick";
    }

}