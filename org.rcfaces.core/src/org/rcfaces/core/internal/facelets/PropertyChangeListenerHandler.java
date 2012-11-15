/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.PropertyChangeListenerTag;

import com.sun.facelets.tag.TagConfig;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PropertyChangeListenerHandler extends AbstractListenerHandler {

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