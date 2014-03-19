/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.el.MethodExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractJavaScriptRenderer0 extends AbstractHtmlRenderer {

    private static final Log LOG = LogFactory
            .getLog(AbstractJavaScriptRenderer0.class);

    protected boolean hasComponentAction(UIComponent component) {
        if ((component instanceof UICommand) == false) {
            return false;
        }

        MethodExpression action = ((UICommand) component).getActionExpression();
        return action != null;
    }
}
