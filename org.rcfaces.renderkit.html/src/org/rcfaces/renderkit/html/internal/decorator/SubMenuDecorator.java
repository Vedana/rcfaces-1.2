/*
 * $Id$
 * 
 * $Log$
 * Revision 1.5  2006/11/10 14:00:03  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
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

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.Constants;
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

    private String suffixMenuId;

    private boolean removeAllWhenShown;

    private int itemImageWidth;

    private int itemImageHeight;

    public SubMenuDecorator(UIComponent component, String menuId,
            String suffixMenuId, boolean removeAllWhenShown,
            int itemImageWidth, int itemImageHeight) {
        super(component);

        this.suffixMenuId = suffixMenuId;
        this.menuId = menuId;
        this.removeAllWhenShown = removeAllWhenShown;
        this.itemImageWidth = itemImageWidth;
        this.itemImageHeight = itemImageHeight;
    }

    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {
        SelectItemsJsContext context = (SelectItemsJsContext) super
                .createJavaScriptContext();

        menuVarName = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        javaScriptWriter.write("var ").write(menuVarName).write("=")
                .writeMethodCall("f_newSubMenu").writeString(menuId);

        int pred = 0;

        String id = context.getComponentClientId(getComponent());

        if (suffixMenuId != null) {

            if (Constants.CLIENT_NAMING_SEPARATOR_SUPPORT) {
                IProcessContext processContext = getComponentRenderContext()
                        .getRenderContext().getProcessContext();

                String namingSeparator = processContext.getNamingSeparator();
                if (namingSeparator != null) {
                    id += namingSeparator + suffixMenuId;

                } else {
                    id += NamingContainer.SEPARATOR_CHAR + suffixMenuId;
                }
            } else {
                id += NamingContainer.SEPARATOR_CHAR + suffixMenuId;
            }
        }

        if (id != null) {
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

        if (itemImageWidth >= 0) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }

            javaScriptWriter.write(',').writeInt(itemImageWidth);
        } else {
            pred++;
        }

        if (itemImageHeight >= 0) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }

            javaScriptWriter.write(',').writeInt(itemImageHeight);
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
            if (suffixMenuId == null) {
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
