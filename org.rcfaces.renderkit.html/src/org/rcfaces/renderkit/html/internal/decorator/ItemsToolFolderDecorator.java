/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ImageButtonComponent;
import org.rcfaces.core.component.ImageCheckButtonComponent;
import org.rcfaces.core.component.ImageComboComponent;
import org.rcfaces.core.component.ImageRadioButtonComponent;
import org.rcfaces.core.component.ImageResetButtonComponent;
import org.rcfaces.core.component.ImageSubmitButtonComponent;
import org.rcfaces.core.component.ItemsToolFolderComponent;
import org.rcfaces.core.component.ToolBarComponent;
import org.rcfaces.core.component.ToolItemComponent;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IExpandImageCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IInputTypeCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.component.IToolBarImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.item.IAccessKeyItem;
import org.rcfaces.core.item.IBorderTypeItem;
import org.rcfaces.core.item.ICheckSelectItem;
import org.rcfaces.core.item.IGroupSelectItem;
import org.rcfaces.core.item.IImageSizeItem;
import org.rcfaces.core.item.IImagesItem;
import org.rcfaces.core.item.IInputTypeItem;
import org.rcfaces.core.item.ILookAndFeelItem;
import org.rcfaces.core.item.ITextPositionItem;
import org.rcfaces.core.item.IToolItem;
import org.rcfaces.core.item.IVisibleItem;
import org.rcfaces.core.item.SeparatorSelectItem;
import org.rcfaces.core.item.ToolItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ItemsToolFolderDecorator extends AbstractSelectItemsDecorator {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ItemsToolFolderDecorator.class);

    private static final String DISABLED_ITEMS = "disabledItems";

    private static final String ENABLED_ITEMS = "enabledItems";

    private static final int DEFAULT_INPUT_TYPE = IInputTypeCapability.AS_PUSH_BUTTON;

    private static final int DEFAULT_ITEM_SEPARATOR_WIDTH = 2;

    private final String borderType;

    private final List itemComponentId = new ArrayList(8);

    private IToolBarImageAccessors toolBarImageAccessors;

    private ToolBarComponent toolBar;

    public ItemsToolFolderDecorator(UIComponent component) {
        super(component, null);

        borderType = ((ItemsToolFolderComponent) component).getBorderType();
    }

    protected void preEncodeContainer() throws WriterException {
        writer.enableJavaScript();

        FacesContext facesContext = getComponentRenderContext()
                .getFacesContext();

        ItemsToolFolderComponent itemsToolFolderComponent = (ItemsToolFolderComponent) getComponent();

        toolBar = itemsToolFolderComponent.getToolBar();

        toolBarImageAccessors = (IToolBarImageAccessors) toolBar
                .getImageAccessors(facesContext);

        super.preEncodeContainer();
    }

    protected void encodeComponentsEnd() throws WriterException {
        if (javaScriptWriter == null) {
            super.encodeComponentsEnd();
            return;
        }

        if (itemComponentId.isEmpty()) {
            super.encodeComponentsEnd();
            return;
        }

        List hiddenItems = null;

        javaScriptWriter.writeMethodCall("f_appendToolItems");

        int cnt = 0;

        for (Iterator it = itemComponentId.iterator(); it.hasNext(); cnt++) {
            SelectItem selectItem = (SelectItem) it.next();
            String componentId = (String) it.next();

            int inputType = IInputTypeCapability.AS_PUSH_BUTTON;

            if (SeparatorSelectItem.isSeparator(selectItem)) {
                inputType = IInputTypeCapability.AS_SEPARATOR;

            } else if (selectItem instanceof IInputTypeItem) {
                inputType = ((IInputTypeItem) selectItem).getInputType();
            }

            if (inputType == 0) {
                inputType = DEFAULT_INPUT_TYPE;
            }

            if (cnt > 0) {
                javaScriptWriter.write(',');
            }

            javaScriptWriter.writeString(componentId).write(',').writeInt(
                    inputType);

            String selectItemValue = null;
            Object si = selectItem.getValue();

            if (si != null) {
                selectItemValue = convertItemValue(javaScriptWriter
                        .getHtmlComponentRenderContext(), selectItem.getValue());
            }

            if (selectItemValue == null) {
                throw new FacesException(
                        "Item of a toolbar must have a value. itemsToolFolderId="
                                + component.getId());
            }

            javaScriptWriter.write(',').writeString(selectItemValue);
            javaScriptWriter.write(',').writeBoolean(selectItem.isDisabled());

            if (selectItem instanceof IVisibleItem) {
                if (((IVisibleItem) selectItem).isVisible() == false) {
                    if (hiddenItems == null) {
                        hiddenItems = new ArrayList();
                    }

                    hiddenItems.add(selectItemValue);
                }
            }
        }

        javaScriptWriter.writeln(");");

        if (hiddenItems != null) {
            javaScriptWriter.writeMethodCall("f_hideToolItems");

            int idx = 0;
            for (Iterator it = hiddenItems.iterator(); it.hasNext(); idx++) {
                String itemValue = (String) it.next();

                if (idx > 0) {
                    javaScriptWriter.write(',');
                }

                javaScriptWriter.writeString(itemValue);
            }

            javaScriptWriter.write(");");
        }

        super.encodeComponentsEnd();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeTreeNodeBegin(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.TreeContext,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem)
     */
    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (javaScriptWriter != null) {
            // pas de Javascript
            return SKIP_NODE;
        }

        if (SeparatorSelectItem.isSeparator(selectItem)) {
            encodeToolItemSeparator(component, selectItem);
            return EVAL_NODE;
        }

        if (getContext().getDepth() == 1) {
            encodeToolItemBegin(component, selectItem, hasChild);
            return EVAL_NODE;
        }

        // encodeToolItemBegin(component, selectItem, hasChild);

        // C'est un popup menu ?

        return EVAL_NODE;
    }

    private void encodeToolItemSeparator(UIComponent component,
            SelectItem selectItem) throws WriterException {

        IContentAccessor separatorImageURL = toolBarImageAccessors
                .getSeparatorImageAccessor();

        writer.startElement("TD");

        StringAppender sa = new StringAppender("f_toolBar_itemSeparator", 32);
        if (separatorImageURL == null) {
            sa.append(" f_toolBar_autoSeparator");
        }
        writer.writeClass(sa.toString());

        writer.writeAttribute("v:separator", "true");

        itemComponentId.add(selectItem);
        itemComponentId.add(null);

        if (separatorImageURL != null) {

            FacesContext facesContext = getComponentRenderContext()
                    .getFacesContext();

            String imageURL = separatorImageURL.resolveURL(facesContext, null,
                    null);
            if (imageURL != null) {
                writer.startElement("IMG");
                writer.writeClass("f_toolBar_imgSeparator");

                int imageWidth = toolBar.getSeparatorImageWidth(facesContext);
                if (imageWidth > 0) {
                    writer.writeWidth(imageWidth);
                }

                int imageHeight = toolBar.getSeparatorImageHeight(facesContext);
                if (imageHeight > 0) {
                    writer.writeHeight(imageHeight);
                }

                writer.writeSrc(imageURL);

                String tooltip = getSeparatorImageAlt(selectItem);
                if (tooltip != null) {
                    writer.writeAlt(tooltip);
                }

                writer.endElement("IMG");
            }
        }

        writer.endElement("TD");
    }

    protected String getSeparatorImageAlt(SelectItem selectItem) {
        return null;
    }

    protected int getToolItemSeparatorWidth(SelectItem selectItem) {
        return DEFAULT_ITEM_SEPARATOR_WIDTH;
    }

    private void encodeToolItemBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild) throws WriterException {

        ToolBarContext toolBarContext = getToolBarContext();

        Object selectItemValue = selectItem.getValue();

        String componentId = "_item" + itemComponentId.size();
        itemComponentId.add(selectItem);
        itemComponentId.add(componentId);

        int style = IInputTypeCapability.AS_PUSH_BUTTON;
        if (selectItem instanceof IInputTypeItem) {
            style = ((IInputTypeItem) selectItem).getInputType();

        } else if (selectItem instanceof IGroupSelectItem) {
            style = IInputTypeCapability.AS_RADIO_BUTTON;

        } else if (selectItem instanceof ICheckSelectItem) {
            style = IInputTypeCapability.AS_CHECK_BUTTON;
        }

        UIComponent itemComponent = null;

        switch (style) {
        case IInputTypeCapability.AS_RADIO_BUTTON:
            itemComponent = new ImageRadioButtonComponent(componentId);
            break;

        case IInputTypeCapability.AS_CHECK_BUTTON:
            itemComponent = new ImageCheckButtonComponent(componentId);
            break;

        case IInputTypeCapability.AS_RESET_BUTTON:
            itemComponent = new ImageResetButtonComponent(componentId);
            break;

        case IInputTypeCapability.AS_SUBMIT_BUTTON:
            itemComponent = new ImageSubmitButtonComponent(componentId);
            break;

        case IInputTypeCapability.AS_DROP_DOWN_MENU:
            itemComponent = new ImageComboComponent(componentId);
            break;

        default:
            itemComponent = new ImageButtonComponent(componentId);
        }

        if (borderType != null
                && (itemComponent instanceof IBorderTypeCapability)) {
            ((IBorderTypeCapability) itemComponent).setBorderType(borderType);
        }

        if (itemComponent instanceof ISelectedCapability) {
            if (toolBarContext.isValueSelected(selectItem, selectItemValue)) {
                ((ISelectedCapability) itemComponent).setSelected(true);
            }
        }

        String label = selectItem.getLabel();
        if (label != null && (itemComponent instanceof ITextCapability)) {
            ((ITextCapability) itemComponent).setText(label);

            if (itemComponent instanceof ITextPositionCapability) {
                int textPosition = 0;

                if (selectItem instanceof ITextPositionItem) {
                    textPosition = ((ITextPositionItem) selectItem)
                            .getTextPosition();
                }

                if (textPosition == 0) {
                    textPosition = getDefaultTextPosition(selectItem);
                }

                if (textPosition > 0) {
                    ((ITextPositionCapability) itemComponent)
                            .setTextPosition(textPosition);
                }
            }
        }

        if (selectItem instanceof IVisibleItem) {
            if (((IVisibleItem) selectItem).isVisible() == false) {
                if (itemComponent instanceof IVisibilityCapability) {
                    IVisibilityCapability visibilityCapability = (IVisibilityCapability) itemComponent;

                    ItemsToolFolderComponent itemsToolFolderComponent = (ItemsToolFolderComponent) getComponent();

                    if (visibilityCapability instanceof IHiddenModeCapability) {
                        int hiddenMode = IHiddenModeCapability.IGNORE_HIDDEN_MODE;
                        if (itemsToolFolderComponent.isItemHiddenModeSetted()) {
                            hiddenMode = itemsToolFolderComponent
                                    .getItemHiddenMode(getComponentRenderContext()
                                            .getFacesContext());
                        }

                        if (hiddenMode == 0) {
                            hiddenMode = IHiddenModeCapability.IGNORE_HIDDEN_MODE;
                        }

                        ((IHiddenModeCapability) visibilityCapability)
                                .setHiddenMode(hiddenMode);
                    }

                    visibilityCapability.setVisible(false);
                }
            }
        }

        if (selectItem instanceof IAccessKeyItem) {
            String accessKey = ((IAccessKeyItem) selectItem).getAccessKey();

            if (accessKey != null
                    && (itemComponent instanceof IAccessKeyCapability)) {
                ((IAccessKeyCapability) itemComponent).setAccessKey(accessKey);
            }
        }

        String description = selectItem.getDescription();
        if (description != null
                && (itemComponent instanceof IToolTipCapability)) {
            ((IToolTipCapability) itemComponent).setToolTipText(description);
        }

        boolean disabled = selectItem.isDisabled();
        if (disabled && (itemComponent instanceof IDisabledCapability)) {
            ((IDisabledCapability) itemComponent).setDisabled(true);
        }

        if (selectItem instanceof ILookAndFeelItem) {
            ILookAndFeelItem lookIdItem = (ILookAndFeelItem) selectItem;

            String lookId = lookIdItem.getLookId();
            if (lookId != null
                    && (itemComponent instanceof ILookAndFeelCapability)) {
                ((ILookAndFeelCapability) itemComponent).setLookId(lookId);
            }
        }

        if (selectItem instanceof IAccessKeyItem) {
            IAccessKeyItem accessKeyItem = (IAccessKeyItem) selectItem;

            String accessKey = accessKeyItem.getAccessKey();
            if (accessKey != null
                    && (itemComponent instanceof IAccessKeyCapability)) {

                ((IAccessKeyCapability) itemComponent).setAccessKey(accessKey);
            }
        }

        if (selectItem instanceof IBorderTypeItem) {
            IBorderTypeItem borderTypeItem = (IBorderTypeItem) selectItem;

            String borderType = borderTypeItem.getBorderType();
            if (borderType != null
                    && (itemComponent instanceof IBorderTypeCapability)) {

                ((IBorderTypeCapability) itemComponent)
                        .setBorderType(borderType);
            }
        }

        if (selectItem instanceof IImagesItem) {
            IImagesItem imagesSelectItem = (IImagesItem) selectItem;

            if (itemComponent instanceof IImageCapability) {
                String imageURL = imagesSelectItem.getImageURL();
                if (imageURL != null) {
                    ((IImageCapability) itemComponent).setImageURL(imageURL);
                }
            }
            if (itemComponent instanceof IStatesImageCapability) {
                IStatesImageCapability is = (IStatesImageCapability) itemComponent;

                String disabledImageURL = imagesSelectItem
                        .getDisabledImageURL();
                if (disabledImageURL != null) {
                    is.setDisabledImageURL(disabledImageURL);
                }

                String hoverImageURL = imagesSelectItem.getHoverImageURL();
                if (hoverImageURL != null) {
                    is.setHoverImageURL(hoverImageURL);
                }

                String selectedImageURL = imagesSelectItem
                        .getSelectedImageURL();
                if (selectedImageURL != null) {
                    is.setSelectedImageURL(selectedImageURL);
                }

                String expandImageURL = imagesSelectItem.getExpandedImageURL();
                if (expandImageURL != null
                        && (itemComponent instanceof IExpandImageCapability)) {
                    ((IExpandImageCapability) itemComponent)
                            .setExpandedImageURL(expandImageURL);
                }
            }
        }

        if (itemComponent instanceof IImageSizeCapability) {
            IImageSizeCapability isc = (IImageSizeCapability) itemComponent;

            int width = 0;
            int height = 0;

            if (selectItem instanceof IImageSizeItem) {
                IImageSizeItem ss = (IImageSizeItem) selectItem;

                width = ss.getImageWidth();
                height = ss.getImageHeight();
            }

            if (width < 1 || height < 1) {
                width = getToolItemImageDefaultWidth(selectItem);
                height = getToolItemImageDefaultHeight(selectItem);
            }

            if (width > 0) {
                isc.setImageWidth(width);
            }
            if (height > 0) {
                isc.setImageHeight(height);
            }
        }

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        Renderer renderer = getRenderer(facesContext, itemComponent);
        if (renderer == null) {
            LOG.error("No renderer for component '" + itemComponent + "' ?");
            return;
        }

        writer.startElement("TD");
        writer.writeClass("f_toolBar_item");

        List children = component.getChildren();

        try {
            children.add(itemComponent);

            try {
                renderer.encodeBegin(facesContext, itemComponent);

                renderer.encodeEnd(facesContext, itemComponent);

            } catch (IOException ex) {
                throw new WriterException(ex.getMessage(), ex.getCause(),
                        component);
            }

        } finally {
            children.remove(itemComponent);
        }

        writer.endElement("TD");
    }

    private int getDefaultTextPosition(SelectItem selectItem) {
        UIComponent component = getComponent();

        if (component instanceof ITextPositionCapability) {
            return ((ITextPositionCapability) component).getTextPosition();
        }

        return 0;
    }

    protected int getToolItemImageDefaultHeight(SelectItem selectItem) {
        return -1;
    }

    protected int getToolItemImageDefaultWidth(SelectItem selectItem) {
        return -1;
    }

    private Renderer getRenderer(FacesContext facesContext,
            UIComponent itemComponent) {
        String rendererType = itemComponent.getRendererType();
        if (rendererType == null) {
            return null;
        }

        RenderKitFactory rkFactory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = rkFactory.getRenderKit(facesContext, facesContext
                .getViewRoot().getRenderKitId());

        return renderKit.getRenderer(itemComponent.getFamily(), rendererType);

    }

    protected final ToolBarContext getToolBarContext() {
        return (ToolBarContext) getContext();
    }

    protected void encodeJsToolItemSeparator(UIComponent component)
            throws WriterException {

        String parentVarId = getToolBarContext().peekVarId();

        javaScriptWriter.writeMethodCall("f_appendSeparatorItem").write(
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

        if (writer == null) {
            // Rendu Javascriupt !

            return;
        }
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof ToolItemComponent) {
            return new ToolItem((ToolItemComponent) component);
        }

        return super.createSelectItem(component);
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
        IComponentRenderContext componentRenderContext = writer
                .getHtmlComponentRenderContext();

        ItemsToolFolderComponent itemsToolFolderComponent = (ItemsToolFolderComponent) componentRenderContext
                .getComponent();

        return new ToolBarContext(this, componentRenderContext,
                itemsToolFolderComponent.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext() {

        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        ItemsToolFolderComponent itemsToolFolderComponent = (ItemsToolFolderComponent) componentRenderContext
                .getComponent();

        return new ToolBarContext(this, componentRenderContext,
                itemsToolFolderComponent.getValue());
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

        // ItemsToolFolderComponent itemsToolFolderComponent =
        // (ItemsToolFolderComponent) component;

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
    }

    protected SelectItem convertToSelectItem(Object value) {
        if (value instanceof IToolItem) {
            IToolItem toolItem = (IToolItem) value;

            return new ToolItem(toolItem);
        }

        return super.convertToSelectItem(value);
    }

}
