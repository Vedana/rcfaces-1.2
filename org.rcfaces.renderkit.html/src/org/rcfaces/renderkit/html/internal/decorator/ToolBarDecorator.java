/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.6  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IAccessKeySelectItem;
import org.rcfaces.core.model.ICheckSelectItem;
import org.rcfaces.core.model.IGroupSelectItem;
import org.rcfaces.core.model.IImagesSelectItem;
import org.rcfaces.core.model.IStyledSelectItem;
import org.rcfaces.core.model.IVisibleSelectItem;
import org.rcfaces.core.model.SeparatorSelectItem;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.HtmlTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ToolBarDecorator extends AbstractSelectItemsDecorator {
    private static final String REVISION = "$Revision$";

    private static final String DISABLED_ITEMS = "disabledItems";

    private static final String ENABLED_ITEMS = "enabledItems";

    public ToolBarDecorator(UIComponent component) {
        super(component, null);
    }

    protected void preEncodeContainer() throws WriterException {
        writer.enableJavaScript();

        super.preEncodeContainer();
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
            encodeJsToolItemSeparator(component);
            return EVAL_NODE;
        }

        if (getContext().getDepth() == 0) {
            encodeJsToolFolderBegin(component, selectItem, hasChild);
            return EVAL_NODE;
        }

        encodeJsToolItemBegin(component, selectItem, hasChild);

        return EVAL_NODE;
    }

    private void encodeJsToolFolderBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        if ((selectItem instanceof SelectItemGroup) == false) {
            throw new FacesException(
                    "Bad ToolItem type. (depth=1, Must be a selectItemGroup !)",
                    null);
        }

        ToolBarContext toolBarContext = getToolBarContext();

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        toolBarContext.pushVarId(varId);

        String sid = toolBarContext.getComponentClientId(component); // menuContext.getMenuBarItemId();

        javaScriptWriter.write("var ").write(varId).write('=').writeMethodCall(
                "_declareToolFolder").writeString(sid);

        int pred = 0;

        String value = convertItemValue(componentContext, selectItem.getValue());
        if (value != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').writeString(value);

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
    }

    private void encodeJsToolItemBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        if (selectItem instanceof SelectItemGroup) {
            throw new FacesException(
                    "Bad ToolItem type. (depth>1, Must be a selectItemGroup !)",
                    null);
        }

        if (selectItem instanceof IVisibleSelectItem) {
            IVisibleSelectItem vs = (IVisibleSelectItem) selectItem;

            if (vs.isVisible() == false) {
                return;
            }
        }

        ToolBarContext toolBarContext = getToolBarContext();

        String parentVarId = toolBarContext.peekVarId();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();
        toolBarContext.pushVarId(varId);

        String sid = toolBarContext.getComponentClientId(component);

        int style = IStyledSelectItem.AS_PUSH_BUTTON;
        if (selectItem instanceof IStyledSelectItem) {
            style = ((IStyledSelectItem) selectItem).getStyle();

        } else if (selectItem instanceof IGroupSelectItem) {
            style = IStyledSelectItem.AS_RADIO_BUTTON;

        } else if (selectItem instanceof ICheckSelectItem) {
            style = IStyledSelectItem.AS_CHECK_BOX;
        }

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

        javaScriptWriter.writeMethodCall(cmd);
        javaScriptWriter.write(parentVarId).write(',').writeString(sid);

        int pred = 0;

        if (style == IStyledSelectItem.AS_RADIO_BUTTON) {
            String groupName = ((IGroupSelectItem) selectItem).getGroupName();

            if (groupName != null) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeString(groupName);
            } else {
                pred++;
            }
        } else {
            pred++;
        }

        String txt = selectItem.getLabel();
        if (txt != null) {
            javaScriptWriter.write(',').writeString(txt);
        } else {
            pred++;
        }

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

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

        if (style == IStyledSelectItem.AS_CHECK_BOX
                || style == IStyledSelectItem.AS_RADIO_BUTTON) {
            if (toolBarContext.isValueSelected(selectItem, selectItemValue)) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
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

        javaScriptWriter.writeln(");");

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

                javaScriptWriter.writeMethodCall("_setItemImages").write(varId);
                pred = 0;

                if (imageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').writeString(imageURL);
                } else {
                    pred++;
                }

                if (disabledImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').writeString(disabledImageURL);
                } else {
                    pred++;
                }
                if (hoverImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').writeString(hoverImageURL);
                } else {
                    pred++;
                }

                if (selectedImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }
                    javaScriptWriter.write(',').writeString(selectedImageURL);
                } else {
                    pred++;
                }

                javaScriptWriter.writeln(");");
            }
        }
    }

    protected final ToolBarContext getToolBarContext() {
        return (ToolBarContext) getContext();
    }

    protected void encodeJsToolItemSeparator(UIComponent component)
            throws WriterException {

        String parentVarId = getToolBarContext().peekVarId();

        javaScriptWriter.writeMethodCall("_appendSeparatorItem").write(
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

        // System.out.println("DEPTH="+context.getDepth()+"/"+selectItem.getLabel());

        if (getContext().getDepth() == 0) {
            encodeJsToolFolderEnd(component, selectItem, hasChild);
            return;
        }

        encodeJsToolItemEnd(component, selectItem, hasChild);
    }

    protected void encodeJsToolItemEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        getToolBarContext().popVarId();
    }

    protected void encodeJsToolFolderEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        getToolBarContext().popVarId();
    }

    protected SelectItem getUnknownComponent(UIComponent component) {
        if (component instanceof ToolItemSeparatorComponent) {
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
    protected SelectItemsContext createJavaScriptContext() {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getComponentRenderContext();

        ToolBarComponent toolBarComponent = (ToolBarComponent) componentRenderContext
                .getComponent();

        return new ToolBarContext(this, componentRenderContext,
                toolBarComponent.getValue());
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

        ToolBarComponent toolBarComponent = (ToolBarComponent) component;

        Map childrenClientIds = null;

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

        String value = componentData.getStringProperty("value");
        if (value == null) {
            toolBarComponent.setSubmittedValue(null);
        } else {
            StringTokenizer st = new StringTokenizer(value,
                    HtmlTools.LIST_SEPARATORS);

            Object values[] = new Object[st.countTokens()];

            for (int i = 0; st.hasMoreTokens(); i++) {
                String token = st.nextToken();

                Object v = token; // On doit convertir ....

                values[i] = v;
            }

            toolBarComponent.setSubmittedValue(values);
        }
    }

}
