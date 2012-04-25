/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit.designer;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IComponentWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDesignerEngine {

    String MAIN_BODY = null;

    void beginChildren(UIComponent component, String facetName,
            IComponentWriter writer);

    void endChildren(UIComponent component, String facetName,
            IComponentWriter writer);
}
