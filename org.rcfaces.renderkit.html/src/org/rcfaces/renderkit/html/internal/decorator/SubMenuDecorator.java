/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.Map;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.EventsRenderer;
import org.rcfaces.renderkit.html.internal.util.ListenerTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SubMenuDecorator extends MenuDecorator {
    private static final String REVISION = "$Revision$";

    protected final String menuId;

    protected String menuVarName;

    private boolean independantMenu;

    private boolean removeAllWhenShown;

    public SubMenuDecorator(UIComponent component, String menuId,
            boolean independantMenu, boolean removeAllWhenShown) {
        super(component);

        this.independantMenu = independantMenu;
        this.menuId = menuId;
        this.removeAllWhenShown = removeAllWhenShown;
    }

    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {
        SelectItemsJsContext context = (SelectItemsJsContext) super
                .createJavaScriptContext();

        menuVarName = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        String id = context.getComponentClientId(getComponent());

        javaScriptWriter.write("var ").write(menuVarName).write("=")
                .writeMethodCall("_newSubMenu").writeString(menuId);

        int pred = 0;

        if (independantMenu) {
            javaScriptWriter.write(',').writeString(id);
        } else {
            pred++;
        }

        if (removeAllWhenShown) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }

            javaScriptWriter.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        javaScriptWriter.writeln(");");

        context.pushVarId(menuVarName);

        context.setManagerComponentId(menuVarName);

        return context;
    }

    protected void encodeComponentsBegin() throws WriterException {
        if (javaScriptWriter != null) {
            if (independantMenu) {
                SelectItemsJsContext context = (SelectItemsJsContext) getContext();

                Map listenersByType = ListenerTools.getListenersByType(
                        ListenerTools.JAVASCRIPT_NAME_SPACE, component);

                EventsRenderer.encodeEventListeners(javaScriptWriter, context
                        .peekVarId(), listenersByType, null);
            }

        }
        super.encodeComponentsBegin();
    }
}
