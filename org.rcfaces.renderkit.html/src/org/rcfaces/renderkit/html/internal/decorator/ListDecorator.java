/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.HtmlTools;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListDecorator extends ComboDecorator {
    private static final String REVISION = "$Revision$";

    public ListDecorator(UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {
        super(component, filterProperties, jsVersion);
    }

    protected void decodeList(FacesContext facesContext, UIInput component, IComponentData componentData) {
        String value = componentData.getStringProperty("value");
        if (value != null) {
            if (value.length() < 1) {
                component.setSubmittedValue(null);
                return;
            }

            StringTokenizer st = new StringTokenizer(value,
                    HtmlTools.LIST_SEPARATORS);
            Set s = new HashSet(st.countTokens());
            for (; st.hasMoreTokens();) {
                s.add(st.nextToken());
            }

            component.setSubmittedValue(s.toArray());
            return;
        }

        String values[] = componentData.getComponentParameters();

        component.setSubmittedValue(values);
    }

}
