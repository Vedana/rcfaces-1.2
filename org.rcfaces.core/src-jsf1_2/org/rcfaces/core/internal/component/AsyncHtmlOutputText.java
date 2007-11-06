/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;

import org.rcfaces.core.internal.capability.IAsyncRenderComponent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AsyncHtmlOutputText extends HtmlOutputText {
    private static final String REVISION = "$Revision$";

    public boolean isTransient() {
        if (super.isTransient() == false) {
            return false;
        }

        for (UIComponent parent = getParent(); parent != null; parent = parent
                .getParent()) {
            if (parent instanceof IAsyncRenderComponent) {

                setTransient(false);
                return false;
            }
        }

        return true;
    }
}
