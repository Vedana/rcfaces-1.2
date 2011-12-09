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
    private static final String REVISION = "$Revision$";

    public SelectionListenerHandler(TagConfig config) {
        super(config);
    }

    protected void addListener(Object listener, UIComponent component) {
        SelectionListenerTag.addSelectionListener(listener, component);
    }

    protected String getListenerName() {
        return "selection";
    }

}