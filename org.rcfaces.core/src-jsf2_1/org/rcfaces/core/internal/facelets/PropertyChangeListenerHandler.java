/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.PropertyChangeListenerTag;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeListenerHandler extends AbstractListenerHandler {
    private static final String REVISION = "$Revision$";

    public PropertyChangeListenerHandler(TagConfig config) {
        super(config);
    }

    @Override
    protected void addListener(Object listener, UIComponent component) {
        PropertyChangeListenerTag
                .addPropertyChangeListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "propertyChange";
    }

}