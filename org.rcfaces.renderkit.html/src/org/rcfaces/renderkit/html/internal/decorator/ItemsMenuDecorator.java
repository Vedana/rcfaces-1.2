/*
 * $Id$
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
public class ItemsMenuDecorator extends MenuDecorator {
    private static final String REVISION = "$Revision$";

    protected final String menuId;

    protected String menuVarName;

    private String suffixMenuId;

    private boolean removeAllWhenShown;

    private int itemImageWidth;

    private int itemImageHeight;

    public ItemsMenuDecorator(UIComponent component, String menuId,
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
