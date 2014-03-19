/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.taglib.SelectionListenerTag;

import com.sun.facelets.tag.TagConfig;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SelectionListenerHandler extends AbstractListenerHandler {

    public SelectionListenerHandler(TagConfig config) {
        super(config);
    }

    @Override
    protected void addListener(Object listener, UIComponent component) {
        SelectionListenerTag.addSelectionListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "selection";
    }

}