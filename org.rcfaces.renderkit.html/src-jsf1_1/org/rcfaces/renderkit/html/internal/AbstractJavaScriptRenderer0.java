/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptRenderer0 extends AbstractHtmlRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractJavaScriptRenderer0.class);

    protected boolean hasComponentAction(UIComponent component) {
        if ((component instanceof UICommand) == false) {
            return false;
        }

        MethodBinding action = ((UICommand) component).getAction();
        return action != null;
    }
}
