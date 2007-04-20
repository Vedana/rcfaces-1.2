/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.component.Properties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class OrderTools {

    public static UIComponent[] getOrderedChildren(FacesContext facesContext,
            UIComponent component, IComponentEngine engine, Class childClass) {

        return ComponentTools.listChildren(facesContext, component, engine,
                childClass, Properties.ORDERED_CHILDREN);
    }

    public static UIComponent getFirstOrderedChild(FacesContext facesContext,
            UIComponent component, IComponentEngine engine, Class classOfChild) {
        UIComponent children[] = getOrderedChildren(facesContext, component,
                engine, classOfChild);
        if (children == null || children.length < 1) {
            return null;
        }

        return children[0];
    }

    public static void setOrderedChildren(FacesContext facesContext,
            UIComponent component, IComponentEngine engine, Class classOfChild,
            UIComponent[] children) {

        ComponentTools.setChildren(component, engine, classOfChild, children,
                Properties.ORDERED_CHILDREN);
    }
}
