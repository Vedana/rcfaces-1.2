/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IAccessKeySelectItem;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class MenuBarDecorator extends MenuDecorator {

    private static final String REVISION = "$Revision$";

    public MenuBarDecorator(UIComponent component) {
        super(component);
    }

    protected void encodeJsMenuItemBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        if (getContext().getDepth() == 1) {
            encodeJsMenuBarItemBegin(component, selectItem, hasChild);
            return;
        }

        super.encodeJsMenuItemBegin(component, selectItem, hasChild);
    }

    protected void encodeJsMenuBarItemBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        IComponentRenderContext componentContext = javaScriptWriter
                .getComponentRenderContext();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        MenuContext menuContext = getMenuContext();

        menuContext.pushVarId(varId);

        String sid = menuContext.getComponentClientId(component); // menuContext.getMenuBarItemId();

        javaScriptWriter.write("var ").write(varId).write('=').writeCall(null,
                "_declareBarItem").writeString(sid);

        int pred = 0;

        String txt = selectItem.getLabel();
        if (txt != null) {
            javaScriptWriter.write(',').writeString(txt);
        } else {
            pred++;
        }

        String value = convertItemValue(componentContext, selectItem.getValue());
        if (value != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeString(value);

        } else {
            pred++;
        }

        if (selectItem instanceof IAccessKeySelectItem) {
            String key = ((IAccessKeySelectItem) selectItem).getAccessKey();
            if (key != null && key.length() > 0) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeString(key);
            } else {
                pred++;
            }
        } else {
            pred++;
        }

        boolean disabled = selectItem.isDisabled();
        if (disabled) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        javaScriptWriter.writeln(");");

        if (hasChild == false) {
            return;
        }

        encodeJsMenuPopupBegin(sid);
    }

    protected void encodeJsMenuItemEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild) {

        if (getContext().getDepth() == 1) {
            encodeJsMenuBarItemEnd(component, selectItem, hasChild);
            return;
        }

        super.encodeJsMenuItemEnd(component, selectItem, hasChild);
    }

    protected void encodeJsMenuBarItemEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild) {

        // IWriter writer = context.getWriter();

        if (hasChild) {
            encodeJsMenuPopupEnd();
        }

        getMenuContext().popVarId();
    }

}
