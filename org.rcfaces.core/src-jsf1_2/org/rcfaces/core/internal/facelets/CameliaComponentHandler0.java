/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools.IListenerType;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaComponentHandler0 extends ComponentHandler {
    private static final String REVISION = "$Revision$";

    public CameliaComponentHandler0(ComponentConfig config) {
        super(config);
    }

    protected static void actionApplyMetaData(FaceletContext ctx,
            UIComponent instance, String expression,
            IListenerType defaultListenerType) {

        ListenersTools1_2.parseAction(ctx.getFacesContext(), instance,
                defaultListenerType, expression);
    }
}
