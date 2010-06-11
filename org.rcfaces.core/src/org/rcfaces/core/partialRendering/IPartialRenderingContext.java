/*
 * $Id$
 */
package org.rcfaces.core.partialRendering;

import java.util.Map;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPartialRenderingContext {
    void register(UIComponent component);

    void update(UIComponent component, String property, Map parameters);
}
