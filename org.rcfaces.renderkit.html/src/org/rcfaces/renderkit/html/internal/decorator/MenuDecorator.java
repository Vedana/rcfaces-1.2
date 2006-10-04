/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.8  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.7  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.6  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.5  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2006/03/02 15:31:55  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
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
 * Revision 1.1  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.MenuItemComponent;
import org.rcfaces.core.component.MenuSeparatorComponent;
import org.rcfaces.core.component.capability.ICheckedCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.KeyTools;
import org.rcfaces.core.model.IAcceleratorKeySelectItem;
import org.rcfaces.core.model.IAccessKeySelectItem;
import org.rcfaces.core.model.ICheckSelectItem;
import org.rcfaces.core.model.IGroupSelectItem;
import org.rcfaces.core.model.IImagesSelectItem;
import org.rcfaces.core.model.IStyledSelectItem;
import org.rcfaces.core.model.IVisibleSelectItem;
import org.rcfaces.core.model.SeparatorSelectItem;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.EventsRenderer;
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
        if (selectItem instanceof IVisibleSelectItem) {
            IVisibleSelectItem vs = (IVisibleSelectItem) selectItem;

            if (vs.isVisible() == false) {
                return;
            }
        }

        MenuContext menuContext = getMenuContext();

        String managerVarId = menuContext.getManagerVarId();
        String parentVarId = menuContext.peekVarId();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();
        // String varId = "m" + vid;
        menuContext.pushVarId(varId);

        String sid = menuContext.getComponentClientId(component);

        int style = IStyledSelectItem.AS_PUSH_BUTTON;
        if (selectItem instanceof IStyledSelectItem) {
            style = ((IStyledSelectItem) selectItem).getStyle();

        } else if (selectItem instanceof IGroupSelectItem) {
            style = IStyledSelectItem.AS_RADIO_BUTTON;

        } else if (selectItem instanceof ICheckSelectItem) {
            style = IStyledSelectItem.AS_CHECK_BOX;
        }

        String groupName = null;
        if (style == IStyledSelectItem.AS_RADIO_BUTTON) {
            groupName = ((IGroupSelectItem) selectItem).getGroupName();
            if (groupName != null) {
                groupName = javaScriptWriter.allocateString(groupName);
            }
        }

        IComponentRenderContext componentContext = javaScriptWriter
                .getComponentRenderContext();

        javaScriptWriter.write("var ").write(varId).write('=');

        String cmd;
        switch (style) {
        case IStyledSelectItem.AS_RADIO_BUTTON:
            cmd = "f_appendRadioItem";
            break;

        case IStyledSelectItem.AS_CHECK_BOX:
            cmd = "f_appendCheckItem";
            break;

        default:
            cmd = "f_appendItem";
        }

        javaScriptWriter.writeCall(managerVarId, cmd);
        javaScriptWriter.write(parentVarId).write(',').writeString(sid);

        int pred = 0;

        if (style == IStyledSelectItem.AS_RADIO_BUTTON) {
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

        if (style == IStyledSelectItem.AS_CHECK_BOX
                || style == IStyledSelectItem.AS_RADIO_BUTTON) {
            if (((ICheckSelectItem) selectItem).isChecked()) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
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

        if (selectItem instanceof IVisibleSelectItem) {
            boolean visible = ((IVisibleSelectItem) selectItem).isVisible();
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

        if (selectItem instanceof IAcceleratorKeySelectItem) {
            String acceleratorKey = ((IAcceleratorKeySelectItem) selectItem)
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

            List l = null;
            for (int i = 0; i < facesListeners.length; i++) {
                FacesListener facesListener = facesListeners[i];

                if ((facesListener instanceof IScriptListener) == false) {
                    continue;
                }

                IScriptListener scriptListener = (IScriptListener) facesListener;

                if (IHtmlRenderContext.JAVASCRIPT_TYPE.equals(scriptListener
                        .getScriptType()) == false) {
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

        if (selectItem instanceof IImagesSelectItem) {
            IImagesSelectItem iim = (IImagesSelectItem) selectItem;

            String imageURL = iim.getImageURL();
            String disabledImageURL = iim.getDisabledImageURL();
            String hoverImageURL = iim.getHoverImageURL();
            String selectedImageURL = iim.getSelectedImageURL();

            String originalImageURL = imageURL;
            if (imageURL != null) {
                imageURL = AbstractCameliaRenderer.rewriteURL(componentContext,
                        IURLRewritingProvider.IMAGE_URL_TYPE, "imageURL",
                        imageURL, null, null);
            }
            if (disabledImageURL != null) {
                disabledImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "disabledImageURL", disabledImageURL, originalImageURL, null);
            }
            if (hoverImageURL != null) {
                hoverImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "hoverImageURL", hoverImageURL, originalImageURL, null);
            }
            if (selectedImageURL != null) {
                selectedImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "selectedImageURL", selectedImageURL, originalImageURL, null);
            }

            if (imageURL != null || disabledImageURL != null
                    || hoverImageURL != null || selectedImageURL != null) {

                if (imageURL != null) {
                    imageURL = javaScriptWriter.allocateString(imageURL);
                }
                if (disabledImageURL != null) {
                    disabledImageURL = javaScriptWriter
                            .allocateString(disabledImageURL);
                }
                if (hoverImageURL != null) {
                    hoverImageURL = javaScriptWriter
                            .allocateString(hoverImageURL);
                }
                if (selectedImageURL != null) {
                    selectedImageURL = javaScriptWriter
                            .allocateString(selectedImageURL);
                }

                javaScriptWriter.writeCall(managerVarId, "_setItemImages")
                        .write(varId);
                pred = 0;

                if (imageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').write(imageURL);
                } else {
                    pred++;
                }

                if (disabledImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').write(disabledImageURL);
                } else {
                    pred++;
                }
                if (hoverImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').write(hoverImageURL);
                } else {
                    pred++;
                }

                if (selectedImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').write(selectedImageURL);
                } else {
                    pred++;
                }

                javaScriptWriter.writeln(");");
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

        javaScriptWriter.writeCall(managerVarId, "f_appendSeparatorItem").write(
                parentVarId).writeln(");");
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

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getComponentRenderContext();

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

        // MenuBarComponent menuBarComponent = (MenuBarComponent) component;

        Map childrenClientIds = null;
        // Map childrenClientItemValues = null;

        String disabledItems = componentData.getStringProperty(DISABLED_ITEMS);
        if (disabledItems != null && disabledItems.length() > 0) {
            if (childrenClientIds == null) {
                childrenClientIds = mapChildrenClientId(null, context,
                        component);
            }

            List l = listChildren(childrenClientIds, disabledItems,
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

            List l = listChildren(childrenClientIds, enabledItems,
                    IDisabledCapability.class);

            for (Iterator it = l.iterator(); it.hasNext();) {
                IDisabledCapability disabledCapability = (IDisabledCapability) it
                        .next();

                disabledCapability.setDisabled(false);
            }
        }

        String checkedItems = componentData.getStringProperty(CHECKED_ITEMS);
        if (checkedItems != null && checkedItems.length() > 0) {
            if (childrenClientIds == null) {
                childrenClientIds = mapChildrenClientId(null, context,
                        component);
            }

            List l = listChildren(childrenClientIds, checkedItems,
                    ICheckedCapability.class);

            if (l.isEmpty() == false) {
                for (Iterator it = l.iterator(); it.hasNext();) {
                    ICheckedCapability checkedCapability = (ICheckedCapability) it
                            .next();

                    checkedCapability.setChecked(true);
                }
            }
        }

        String uncheckedItems = componentData
                .getStringProperty(UNCHECKED_ITEMS);
        if (uncheckedItems != null && uncheckedItems.length() > 0) {
            if (childrenClientIds == null) {
                childrenClientIds = mapChildrenClientId(null, context,
                        component);
            }

            List l = listChildren(childrenClientIds, uncheckedItems,
                    ICheckedCapability.class);

            if (l.isEmpty() == false) {
                for (Iterator it = l.iterator(); it.hasNext();) {
                    ICheckedCapability checkedCapability = (ICheckedCapability) it
                            .next();

                    checkedCapability.setChecked(false);
                }
            }
        }
    }

}
