/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.component.MenuSeparatorComponent;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.KeyTools;
import org.rcfaces.core.item.IAcceleratorKeyItem;
import org.rcfaces.core.item.IAccessKeyItem;
import org.rcfaces.core.item.ICheckSelectItem;
import org.rcfaces.core.item.IGroupSelectItem;
import org.rcfaces.core.item.IImagesItem;
import org.rcfaces.core.item.IInputTypeItem;
import org.rcfaces.core.item.IMenuItem;
import org.rcfaces.core.item.IVisibleItem;
import org.rcfaces.core.item.MenuItem;
import org.rcfaces.core.item.SeparatorSelectItem;
import org.rcfaces.renderkit.html.internal.EventsRenderer;
import org.rcfaces.renderkit.html.internal.HtmlValuesTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MenuDecorator extends AbstractSelectItemsDecorator {
    private static final String REVISION = "$Revision$";

    private static final String DISABLED_ITEMS = "disabledItems";

    private static final String ENABLED_ITEMS = "enabledItems";

    private static final String CHECKED_ITEMS = "checkedItems";

    private static final String UNCHECKED_ITEMS = "uncheckedItems";

    public MenuDecorator(UIComponent component) {
        super(component, null);
    }

    protected void preEncodeContainer() throws WriterException {
        writer.enableJavaScript();

        super.preEncodeContainer();
    }

    /*
     * // enableJavaScript(writer); // writeHtmlAttributes(writer); //
     * writeJavaScriptAttributes(writer); // writeCssAttributes(writer);
     */

    protected void postEncodeContainer() throws WriterException {
        super.postEncodeContainer();

        // writer.endElement("DIV");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeTreeNodeBegin(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.TreeContext,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem)
     */
    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (javaScriptWriter == null) {
            // Rendu HTML !
            /*
             * if (context.getDepth() == 0) {
             * encodeHtmlMenuBarItemBegin((MenuBarContext) context, component,
             * selectItem, hasChild); return; }
             */
            return SKIP_NODE;
        }

        if (SeparatorSelectItem.isSeparator(selectItem)) {
            encodeJsMenuItemSeparator(component);
            return EVAL_NODE;
        }

        encodeJsMenuItemBegin(component, selectItem, hasChild);

        return EVAL_NODE;
    }

    protected void encodeJsMenuItemBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        MenuContext menuContext = getMenuContext();

        String managerVarId = menuContext.getManagerVarId();
        String parentVarId = menuContext.peekVarId();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();
        // String varId = "m" + vid;
        menuContext.pushVarId(varId);

        String sid = menuContext.getComponentClientId(component);

        int style = IInputTypeCapability.AS_PUSH_BUTTON;
        if (selectItem instanceof IInputTypeItem) {
            style = ((IInputTypeItem) selectItem).getInputType();

        } else if (selectItem instanceof IGroupSelectItem) {
            style = IInputTypeCapability.AS_RADIO_BUTTON;

        } else if (selectItem instanceof ICheckSelectItem) {
            style = IInputTypeCapability.AS_CHECK_BUTTON;
        }

        String groupName = null;
        if (style == IInputTypeCapability.AS_RADIO_BUTTON) {
            groupName = ((IGroupSelectItem) selectItem).getGroupName();
            if (groupName != null) {
                groupName = javaScriptWriter.allocateString(groupName);
            }
        }

        IComponentRenderContext componentContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        javaScriptWriter.write("var ").write(varId).write('=');

        String cmd;
        switch (style) {
        case IInputTypeCapability.AS_RADIO_BUTTON:
            cmd = "f_appendRadioItem";
            break;

        case IInputTypeCapability.AS_CHECK_BUTTON:
            cmd = "f_appendCheckItem";
            break;

        default:
            cmd = "f_appendItem";
        }

        javaScriptWriter.writeCall(managerVarId, cmd);
        javaScriptWriter.write(parentVarId).write(',').writeString(sid);

        int pred = 0;

        if (style == IInputTypeCapability.AS_RADIO_BUTTON) {
            if (groupName != null) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeString(groupName);
            } else {
                pred++;
            }
        }

        String txt = selectItem.getLabel();
        if (txt != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeString(txt);
        } else {
            pred++;
        }

        Object selectItemValue = selectItem.getValue();

        String value = convertItemValue(componentContext, selectItemValue);
        if (value != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeString(value);

        } else {
            pred++;
        }

        if (style == IInputTypeCapability.AS_CHECK_BUTTON
                || style == IInputTypeCapability.AS_RADIO_BUTTON) {

            if (menuContext.isValueChecked(selectItem, selectItemValue)) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
        }

        if (selectItem instanceof IAccessKeyItem) {
            String key = ((IAccessKeyItem) selectItem).getAccessKey();
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

        String description = selectItem.getDescription();
        if (description != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeString(description);
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

        if (selectItem instanceof IVisibleItem) {
            boolean visible = ((IVisibleItem) selectItem).isVisible();
            if (visible == false) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeBoolean(false);
            } else {
                pred++;
            }
        } else {
            pred++;
        }

        if (selectItem instanceof IAcceleratorKeyItem) {
            String acceleratorKey = ((IAcceleratorKeyItem) selectItem)
                    .getAcceleratorKey();
            if (acceleratorKey != null && acceleratorKey.length() > 0) {
                KeyTools.State state = KeyTools.parseKeyBinding(acceleratorKey);

                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeString(state.format());
            } else {
                pred++;
            }
        } else {
            pred++;
        }

        javaScriptWriter.writeln(");");

        MenuItemComponent menuItemComponent = null;
        if (component instanceof MenuItemComponent) {
            menuItemComponent = (MenuItemComponent) component;

            FacesContext facesContext = javaScriptWriter.getFacesContext();
            FacesListener facesListeners[] = menuItemComponent
                    .listMenuListeners();

            IProcessContext processContext = javaScriptWriter
                    .getHtmlRenderContext().getProcessContext();
            List l = null;
            for (int i = 0; i < facesListeners.length; i++) {
                FacesListener facesListener = facesListeners[i];

                if ((facesListener instanceof IScriptListener) == false) {
                    continue;
                }

                IScriptListener scriptListener = (IScriptListener) facesListener;

                if (IHtmlRenderContext.JAVASCRIPT_TYPE.equals(scriptListener
                        .getScriptType(processContext)) == false) {
                    continue;
                }
                if (l == null) {
                    l = new ArrayList(facesListeners.length - i);
                }

                l.add(scriptListener);
            }

            boolean removeAllWhenShow = menuItemComponent
                    .isRemoveAllWhenShown(facesContext);
            if (removeAllWhenShow || l != null) {
                javaScriptWriter.writeCall(managerVarId,
                        "_setMenuItemListeners").write(varId);

                javaScriptWriter.write(',').writeBoolean(removeAllWhenShow);

                for (Iterator it = l.iterator(); it.hasNext();) {
                    IScriptListener scriptListener = (IScriptListener) it
                            .next();

                    javaScriptWriter.write(',');

                    EventsRenderer.encodeJavaScriptCommmand(javaScriptWriter,
                            scriptListener);
                }

                javaScriptWriter.writeln(");");
            }
        }

        if (selectItem instanceof IImagesItem) {
            writeSelectItemImages((IImagesItem) selectItem, javaScriptWriter,
                    managerVarId, "f_setItemImages", varId, true);
        }

        if (selectItem instanceof IVisibleItem) {
            IVisibleItem vs = (IVisibleItem) selectItem;

            if (vs.isVisible() == false) {
                javaScriptWriter.writeMethodCall("f_setItemVisible").writeln(
                        "false);");
            }
        }

        if (hasChild == false) {
            return;
        }

        encodeJsMenuPopupBegin(sid);
    }

    protected final MenuContext getMenuContext() {
        return (MenuContext) getContext();
    }

    protected void encodeJsMenuPopupBegin(String itemId) {
        // IWriter writer = context.getWriter();
    }

    protected void encodeJsMenuPopupEnd() {
        // IWriter writer = context.getWriter();

    }

    protected void encodeJsMenuItemSeparator(UIComponent component)
            throws WriterException {

        MenuContext menuContext = getMenuContext();
        String parentVarId = menuContext.peekVarId();
        String managerVarId = menuContext.getManagerVarId();

        javaScriptWriter.writeCall(managerVarId, "f_appendSeparatorItem")
                .write(parentVarId).writeln(");");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeTreeNodeEnd(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.TreeContext,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem)
     */
    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (javaScriptWriter == null) {
            // Rendu HTML !

            return;
        }

        if (SeparatorSelectItem.isSeparator(selectItem)) {
            return;
        }

        encodeJsMenuItemEnd(component, selectItem, hasChild);
    }

    protected void encodeJsMenuItemEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild) {

        // IWriter writer = context.getWriter();

        if (hasChild) {
            encodeJsMenuPopupEnd();
        }

        getMenuContext().popVarId();
    }

    protected SelectItem getUnknownComponent(UIComponent component) {
        if (component instanceof MenuSeparatorComponent) {
            return SeparatorSelectItem.SEPARATOR;
        }

        return super.getUnknownComponent(component);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected SelectItemsContext createHtmlContext() {
        return null;
        /*
         * MenuBarComponent menuBarComponent = (MenuBarComponent) writer
         * .getComponent();
         * 
         * return new MenuBarContext(this, writer, menuBarComponent.getValue());
         */
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof MenuItemComponent) {
            return new MenuItem((MenuItemComponent) component);
        }

        return super.createSelectItem(component);
    }

    protected SelectItem convertToSelectItem(Object value) {
        if (value instanceof IMenuItem) {
            IMenuItem menuItem = (IMenuItem) value;

            return new MenuItem(menuItem);
        }

        return super.convertToSelectItem(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        Object value = null;

        if (componentRenderContext instanceof ValueHolder) {
            value = ((ValueHolder) getComponent()).getValue();

        } else if (componentRenderContext instanceof UICommand) {
            value = ((UICommand) getComponent()).getValue();
        }

        return new MenuContext(this, componentRenderContext, getComponent(),
                value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        super.decode(context, component, componentData);

        Map childrenClientIds = null;

        String disabledItems = componentData.getStringProperty(DISABLED_ITEMS);
        if (disabledItems != null && disabledItems.length() > 0) {
            if (childrenClientIds == null) {
                childrenClientIds = mapChildrenClientId(null, context,
                        component);
            }

            List l = listComponents(childrenClientIds, disabledItems,
                    IDisabledCapability.class);

            for (Iterator it = l.iterator(); it.hasNext();) {
                IDisabledCapability disabledCapability = (IDisabledCapability) it
                        .next();

                disabledCapability.setDisabled(true);
            }
        }

        String enabledItems = componentData.getStringProperty(ENABLED_ITEMS);
        if (enabledItems != null && enabledItems.length() > 0) {
            if (childrenClientIds == null) {
                childrenClientIds = mapChildrenClientId(null, context,
                        component);
            }

            List l = listComponents(childrenClientIds, enabledItems,
                    IDisabledCapability.class);

            for (Iterator it = l.iterator(); it.hasNext();) {
                IDisabledCapability disabledCapability = (IDisabledCapability) it
                        .next();

                disabledCapability.setDisabled(false);
            }
        }

        if (component instanceof ICheckedValuesCapability) {
            ICheckedValuesCapability checkedValuesCapability = (ICheckedValuesCapability) component;

            Object checkedValues = checkedValuesCapability.getCheckedValues();

            boolean checkModified = false;
            Set checkValues = null;

            String checkedItems = componentData
                    .getStringProperty(CHECKED_ITEMS);
            if (checkedItems != null && checkedItems.length() > 0) {

                List values = HtmlValuesTools.parseValues(context
                        .getFacesContext(), component, true, false,
                        checkedItems);

                for (Iterator it = values.iterator(); it.hasNext();) {
                    Object value = it.next();

                    if (checkValues == null) {
                        checkValues = ValuesTools
                                .convertSelection(checkedValues);
                    }

                    if (checkValues.add(value) == false) {
                        continue;
                    }

                    checkModified = true;
                }
            }

            String uncheckedItems = componentData
                    .getStringProperty(UNCHECKED_ITEMS);
            if (uncheckedItems != null && uncheckedItems.length() > 0) {

                if (checkValues == null) {
                    checkValues = ValuesTools.convertSelection(checkedValues);
                }

                if (checkValues != null) {
                    List values = HtmlValuesTools.parseValues(context
                            .getFacesContext(), component, true, false,
                            uncheckedItems);

                    for (Iterator it = values.iterator(); it.hasNext();) {
                        Object value = it.next();

                        if (checkValues.remove(value) == false) {
                            continue;
                        }

                        checkModified = true;
                    }
                }
            }

            if (checkModified) {
                Class cls = checkedValuesCapability
                        .getCheckedValuesType(context.getFacesContext());
                if (cls == null && checkedValues != null) {
                    cls = checkedValues.getClass();
                }

                Object value = ValuesTools.adaptValues(cls, checkValues);

                checkedValuesCapability.setCheckedValues(value);
            }
        }
    }
}
