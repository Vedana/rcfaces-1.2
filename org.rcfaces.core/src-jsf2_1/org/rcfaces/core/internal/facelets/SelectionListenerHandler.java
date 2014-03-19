/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.TagConfig;

import org.rcfaces.core.internal.taglib.SelectionListenerTag;

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

    @Override
    protected void addListener(Object listener, UIComponent component) {
        SelectionListenerTag.addSelectionListener(listener, component);
    }

    @Override
    protected String getListenerName() {
        return "selection";
    }

}