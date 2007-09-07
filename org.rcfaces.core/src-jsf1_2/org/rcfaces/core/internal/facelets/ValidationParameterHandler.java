/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.manager.IValidationParameters;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ValidationParameterHandler extends TagHandler {
    private static final String REVISION = "$Revision$";

    private final TagAttribute name;

    private final TagAttribute value;

    private final TagAttribute clientSide;

    public ValidationParameterHandler(TagConfig config) {
        super(config);
        this.name = this.getRequiredAttribute("name");
        this.value = this.getRequiredAttribute("value");
        this.clientSide = this.getAttribute("clientSide");
    }

    public void apply(FaceletContext ctx, UIComponent parent) {
        if (parent == null) {
            throw new TagException(this.tag, "Parent UIComponent was null");
        }

        // only process if the parent is new to the tree
        if (ComponentSupport.isNew(parent) == false) {
            return;
        }

        boolean clientSide = value.getBoolean(ctx);

        IValidationParameters clientDataCapability = (IValidationParameters) parent;

        String nameValue = name.getValue(ctx);
        if (value.isLiteral()) {
            clientDataCapability.setValidationParameter(nameValue, value
                    .getValue(), clientSide);
            return;
        }

        clientDataCapability.setValidationParameter(nameValue, value
                .getValueExpression(ctx, String.class), clientSide);
    }

}
