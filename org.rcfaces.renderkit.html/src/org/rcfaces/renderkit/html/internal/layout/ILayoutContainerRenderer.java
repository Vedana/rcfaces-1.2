/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.layout;

import java.util.List;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILayoutContainerRenderer {
    Size computeInternalSize(String zoneId, Size size);

    List<UIComponent> getChildren(String zoneId);
}
