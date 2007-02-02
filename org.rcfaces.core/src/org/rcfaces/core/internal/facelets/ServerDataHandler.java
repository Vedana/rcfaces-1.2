/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.manager.IServerDataManager;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyValueBinding;
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
public class ServerDataHandler extends TagHandler {
    private static final String REVISION = "$Revision$";

    private final TagAttribute name;

    private final TagAttribute value;

    public ServerDataHandler(TagConfig config) {
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

        IServerDataManager serverDataCapability = (IServerDataManager) parent;

        String nameValue = name.getValue(ctx);
        if (value.isLiteral()) {
            serverDataCapability.setServerData(nameValue, value.getValue());
            return;
        }

        serverDataCapability.setServerData(nameValue, new LegacyValueBinding(
                value.getValueExpression(ctx, Object.class)));
    }
}
