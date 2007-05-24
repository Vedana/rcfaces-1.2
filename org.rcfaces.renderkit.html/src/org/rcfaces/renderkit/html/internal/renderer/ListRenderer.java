/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ListComponent;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ListDecorator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListRenderer extends ComboRenderer {
    private static final String REVISION = "$Revision$";

    private static final int DEFAULT_ROW_NUMBER = 4;

    protected boolean isMultipleSelect(UIComponent comboBox) {
        ListComponent listBox = (ListComponent) comboBox;

        return listBox.isMultipleSelect();
    }

    protected int getRowNumber(UIComponent comboBox) {
        ListComponent listBox = (ListComponent) comboBox;
        int rowNumber = listBox.getRowNumber();
        if (rowNumber > 0) {
            return rowNumber;
        }

        return DEFAULT_ROW_NUMBER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.LIST;
    }

    protected IComponentDecorator createComboDecorator(
            FacesContext facesContext, UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {

        return new ListDecorator(component, filterProperties, jsVersion);
    }
}