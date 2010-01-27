/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.renderkit.html.component.JavaScriptItemComponent;
import org.rcfaces.renderkit.html.item.JavaScriptItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class JavaScriptFilesCollectorDecorator extends
        UserAgentVaryFilesCollectorDecorator {

    public JavaScriptFilesCollectorDecorator(UIComponent component) {
        super(component);
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof JavaScriptItemComponent) {
            return new JavaScriptItem(component);
        }

        return super.createSelectItem(component);
    }

}
