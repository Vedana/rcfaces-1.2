/*
 * $Id: ClientDataHandler.java,v 1.1.12.1 2014/02/27 13:12:02 jbmeslin Exp $
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.manager.IClientDataManager;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: jbmeslin $)
 * @version $Revision: 1.1.12.1 $ $Date: 2014/02/27 13:12:02 $
 */
public class ClientDataHandler extends TagHandler {
    

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
        if (ComponentSupport.isNew(parent) == false) {
            return;
        }

        IClientDataManager clientDataCapability = (IClientDataManager) parent;

        String nameValue = name.getValue(ctx);
        if (value.isLiteral()) {
            clientDataCapability.setClientData(nameValue, value.getValue());
            return;
        }

        clientDataCapability.setClientData(nameValue, value.getValueExpression(
                ctx, String.class));
    }

}
