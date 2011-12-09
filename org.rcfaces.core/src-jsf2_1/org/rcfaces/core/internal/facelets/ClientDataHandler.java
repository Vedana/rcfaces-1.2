/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandler;

import org.rcfaces.core.internal.manager.IClientDataManager;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientDataHandler extends TagHandler {
    private static final String REVISION = "$Revision$";

    private final TagAttribute name;

    private final TagAttribute value;

    public ClientDataHandler(TagConfig config) {
        super(config);
        this.name = this.getRequiredAttribute("name");
        this.value = this.getRequiredAttribute("value");
    }

    public void apply(FaceletContext ctx, UIComponent parent) {
        if (parent == null) {
            throw new TagException(this.tag, "Parent UIComponent was null");
        }

        // only process if the parent is new to the tree
        if (parent.getParent() != null) {
            return;
        }

        IClientDataManager clientDataCapability = (IClientDataManager) parent;

        String nameValue = name.getValue(ctx);
        if (value.isLiteral()) {
            clientDataCapability.setClientData(nameValue, value.getValue());
            return;
        }

        clientDataCapability.setClientData(nameValue,
                value.getValueExpression(ctx, String.class));
    }

}
