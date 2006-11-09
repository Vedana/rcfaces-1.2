/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssRenderer {
    String getStyleClassName(UIComponent component);

    String getMainStyleClassName();

}
