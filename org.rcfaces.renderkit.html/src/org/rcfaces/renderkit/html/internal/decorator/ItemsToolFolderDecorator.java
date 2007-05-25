/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
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
import org.rcfaces.core.component.capability.IShowDropDownMarkCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.event.SelectionEvent;
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
import org.rcfaces.core.item.IClientDataItem;
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
import org.rcfaces.renderkit.html.internal.IHtmlRequestContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;

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

    private static final String COMPONENT_ID_TO_VALUE_PROPERTY = "org.rcfaces.html.ITEMS_CID";

    private final String borderType;

    private final List menuDecoratorStack = new ArrayList(8);

    private final boolean showDropDownMark;

    private final List itemsId = new ArrayList(8);

    private final Map itemIdToClientId = new HashMap(8);

    private IToolBarImageAccessors toolBarImageAccessors;

    private ToolBarComponent toolBar;

    public ItemsToolFolderDecorator(UIComponent component) {
        super(component, null);

        borderType = ((ItemsToolFolderComponent) component).getBorderType();

        showDropDownMark = ((IShowDropDownMarkCapability) component)
                .isShowDropDownMark();
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

    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (javaScriptWriter != null) {
            return encodeToolItemPopupBegin(component, selectItem, hasChild,
                    isVisible);
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

    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (javaScriptWriter != null) {
            // pas de Javascript

            int depth = getContext().getDepth();

            if (hasChild || depth > 1) {
                encodeToolItemPopupEnd(component, selectItem, hasChild,
                        isVisible);
            }
            return;
        }

    }

    protected int encodeToolItemPopupBegin(UIComponent component,
            SelectItem selectItem, boolean hasChild, boolean isVisible)
            throws WriterException {

        ItemsMenuDecorator menuDecorator = null;

        if (LOG.isDebugEnabled()) {
            LOG.debug("Encode node BEGIN value='" + selectItem.getValue()
                    + "' hasChild=" + hasChild + " isVisible=" + isVisible
                    + "  detph=" + getContext().getDepth(), null);
        }

        if (getContext().getDepth() == 1) {
            String itemId = nextItemId();
            if (itemId == null) {
                return SKIP_NODE;
            }

            int inputType = 0;

            if (SeparatorSelectItem.isSeparator(selectItem)) {
                inputType = IInputTypeCapability.AS_SEPARATOR;

            } else if (selectItem instanceof IInputTypeItem) {
                inputType = ((IInputTypeItem) selectItem).getInputType();
            }

            if (inputType == 0) {
                if (hasChild) {
                    inputType = IInputTypeCapability.AS_DROP_DOWN_MENU;

                } else {
                    inputType = DEFAULT_INPUT_TYPE;
                }
            }

            String selectItemVarName = javaScriptWriter
                    .getJavaScriptRenderContext().allocateVarName();

            javaScriptWriter.write(selectItemVarName).write('=')
                    .writeMethodCall("f_appendToolItem2").writeString(itemId)
                    .write(',');

            IObjectLiteralWriter objectLiteralWriter = javaScriptWriter
                    .writeObjectLiteral(false);

            Object siValue = selectItem.getValue();
            String selectItemValue = convertItemValue(javaScriptWriter
                    .getHtmlComponentRenderContext(), siValue);

            if (selectItemValue == null) {
                throw new FacesException(
                        "Item of a toolbar must have a value. itemsToolFolderId="
                                + component.getId());
            }

            String itemCliendId = (String) itemIdToClientId.get(itemId);
            if (itemCliendId == null) {
                itemCliendId = itemId;
            }

            Map componentIdToValue = (Map) getComponent().getAttributes().get(
                    COMPONENT_ID_TO_VALUE_PROPERTY);
            if (componentIdToValue == null) {
                componentIdToValue = new HashMap(8);

                getComponent().getAttributes().put(
                        COMPONENT_ID_TO_VALUE_PROPERTY, componentIdToValue);
            }

            componentIdToValue.put(itemCliendId, selectItemValue);

            if (inputType == IInputTypeCapability.AS_SEPARATOR) {
                objectLiteralWriter.writeSymbol("_inputType").writeInt(
                        inputType);

            } else {
                objectLiteralWriter.writeSymbol("_value").writeString(
                        selectItemValue);

                if (inputType != DEFAULT_INPUT_TYPE) {
                    objectLiteralWriter.writeSymbol("_inputType").writeInt(
                            inputType);
                }
            }

            if (selectItem.getLabel() != null) {
                objectLiteralWriter.writeSymbol("_label").writeString(
                        selectItem.getLabel());
            }

            if (selectItem.getDescription() != null) {
                objectLiteralWriter.writeSymbol("_description").writeString(
                        selectItem.getDescription());
            }

            if (selectItem.isDisabled()) {
                objectLiteralWriter.writeSymbol("_disabled").writeBoolean(true);
            }

            /*
             * if (selectItem instanceof IStyleClassItem) { String styleClass =
             * ((IStyleClassItem) selectItem) .getStyleClass(); if (styleClass !=
             * null) { javaScriptWriter.write(',').writeSymbol("_styleClass")
             * .write(':').writeString(styleClass); } }
             */

            if (selectItem instanceof IVisibleItem) {
                if (((IVisibleItem) selectItem).isVisible() == false) {
                    objectLiteralWriter.writeSymbol("_visible").writeBoolean(
                            false);
                }
            }

            if (selectItem instanceof IClientDataItem) {
                writeItemClientDatas((IClientDataItem) selectItem,
                        javaScriptWriter, null, null, objectLiteralWriter);
            }

            /*
             * if (selectItem instanceof IMenuPopupIdCapability) { String
             * menuPopupId = ((IMenuPopupIdCapability) selectItem)
             * .getMenuPopupId(); if (menuPopupId != null) {
             * javaScriptWriter.write(',').writeSymbol("_menuPopupId")
             * .write(':').writeString(menuPopupId); } }
             */

            objectLiteralWriter.end().writeln(");");

            if (hasChild == false) {
                return SKIP_NODE;
            }

            String componentVarName = javaScriptWriter
                    .getJavaScriptRenderContext().allocateVarName();

            javaScriptWriter.write("var ").write(componentVarName).write('=')
                    .writeMethodCall("f_getItemComponent").write(
                            selectItemVarName).writeln(");");

            menuDecorator = pushMenuDecorator(componentVarName, itemId,
                    javaScriptWriter);

        } else {
            menuDecorator = peekMenuDecorator();

            menuDecorator.encodeNodeBegin(component, selectItem, hasChild,
                    isVisible);
        }

        return EVAL_NODE;
    }

    protected void encodeToolItemPopupEnd(UIComponent component,
            SelectItem selectItem, boolean hasChild, boolean isVisible)
            throws WriterException {

        ItemsMenuDecorator menuDecorator = peekMenuDecorator();

        if (LOG.isDebugEnabled()) {
            LOG.debug("Encode node end value='" + selectItem.getValue()
                    + "' hasChild=" + hasChild + " isVisible=" + isVisible
                    + "  detph=" + getContext().getDepth(), null);
        }

        if (getContext().getDepth() == 1) {
            popupMenuDecorator();

        } else {
            menuDecorator.encodeNodeEnd(component, selectItem, hasChild,
                    isVisible);
        }
    }

    private void encodeToolItemSeparator(UIComponent component,
            SelectItem selectItem) throws WriterException {

        IContentAccessor separatorImageURL = toolBarImageAccessors
                .getSeparatorImageAccessor();

        writer.startElement(IHtmlWriter.TD);

        StringAppender sa = new StringAppender("f_toolBar_itemSeparator", 32);
        if (separatorImageURL == null) {
            sa.append(" f_toolBar_autoSeparator");
        }
        writer.writeClass(sa.toString());

        writer.writeAttribute("v:separator", "true");

        allocateItemSeparator();

        if (separatorImageURL != null) {

            FacesContext facesContext = getComponentRenderContext()
                    .getFacesContext();

            String imageURL = separatorImageURL.resolveURL(facesContext, null,
                    null);
            if (imageURL != null) {
                writer.startElement(IHtmlWriter.IMG);
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

                writer.endElement(IHtmlWriter.IMG);
            }
        }

        writer.endElement(IHtmlWriter.TD);
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

        String itemId = allocateItemId();

        int style = 0;
        if (selectItem instanceof IInputTypeItem) {
            style = ((IInputTypeItem) selectItem).getInputType();

        } else if (selectItem instanceof IGroupSelectItem) {
            style = IInputTypeCapability.AS_RADIO_BUTTON;

        } else if (selectItem instanceof ICheckSelectItem) {
            style = IInputTypeCapability.AS_CHECK_BUTTON;
        }

        if (style == 0) {
            if (hasChild) {
                style = IInputTypeCapability.AS_DROP_DOWN_MENU;

            } else {
                style = DEFAULT_INPUT_TYPE;
            }
        }

        UIComponent itemComponent = null;

        switch (style) {
        case IInputTypeCapability.AS_RADIO_BUTTON:
            itemComponent = new ImageRadioButtonComponent(itemId);
            break;

        case IInputTypeCapability.AS_CHECK_BUTTON:
            itemComponent = new ImageCheckButtonComponent(itemId);
            break;

        case IInputTypeCapability.AS_RESET_BUTTON:
            itemComponent = new ImageResetButtonComponent(itemId);
            break;

        case IInputTypeCapability.AS_SUBMIT_BUTTON:
            itemComponent = new ImageSubmitButtonComponent(itemId);
            break;

        case IInputTypeCapability.AS_DROP_DOWN_MENU:
            itemComponent = new ImageComboComponent(itemId);
            break;

        default:
            itemComponent = new ImageButtonComponent(itemId);
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

        if (selectItem instanceof IStyleClassCapability) {
            IStyleClassCapability lookIdItem = (IStyleClassCapability) selectItem;

            String cssClass = lookIdItem.getStyleClass();
            if (cssClass != null
                    && (itemComponent instanceof IStyleClassCapability)) {
                ((IStyleClassCapability) itemComponent).setStyleClass(cssClass);
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

        if (itemComponent instanceof IShowDropDownMarkCapability) {
            ((IShowDropDownMarkCapability) itemComponent)
                    .setShowDropDownMark(showDropDownMark);
        }

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        Renderer renderer = getRenderer(facesContext, itemComponent);
        if (renderer == null) {
            LOG.error("No renderer for component '" + itemComponent + "' ?");
            return;
        }

        writer.startElement(IHtmlWriter.TD);
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

            String itemClientId = itemComponent.getClientId(facesContext);

            itemIdToClientId.put(itemId, itemClientId);

        } finally {
            children.remove(itemComponent);
        }

        writer.endElement(IHtmlWriter.TD);
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

        Map childrenClientIds = mapChildrenClientId(null, context, component);

        String disabledItems = componentData.getStringProperty(DISABLED_ITEMS);
        if (disabledItems != null && disabledItems.length() > 0) {
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
            List l = listComponents(childrenClientIds, enabledItems,
                    IDisabledCapability.class);

            for (Iterator it = l.iterator(); it.hasNext();) {
                IDisabledCapability disabledCapability = (IDisabledCapability) it
                        .next();

                disabledCapability.setDisabled(false);
            }
        }

        if (componentData.isEventComponent() == false) {
            // Si il n'y a pas d'evenement Camelia, on regarde les evenements
            // HTML !

            String eventComponentId = ((IHtmlRequestContext) context)
                    .getEventComponentId();

            Map componentIdToValue = (Map) getComponent().getAttributes().get(
                    COMPONENT_ID_TO_VALUE_PROPERTY);
            if (componentIdToValue != null) {
                for (Iterator it = componentIdToValue.entrySet().iterator(); it
                        .hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();

                    String clientId = (String) entry.getKey();

                    if (clientId.equals(eventComponentId) == false) {
                        continue;
                    }

                    String selectItemValue = (String) entry.getValue();

                    Object value = convertToItemValue(
                            context.getFacesContext(), component,
                            selectItemValue);

                    ActionEvent actionEvent = new SelectionEvent(component,
                            selectItemValue, value, null, 0);

                    actionEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                    component.queueEvent(actionEvent);

                    break;
                }
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

    public String nextItemId() {
        return (String) itemsId.remove(0);
    }

    public String allocateItemId() {
        String id = "_item" + itemsId.size();

        itemsId.add(id);

        return id;
    }

    public void allocateItemSeparator() {

        itemsId.add(null);
    }

    public ItemsMenuDecorator pushMenuDecorator(String selectItemVarName,
            String selectItemComponentId, IJavaScriptWriter javascriptWriter)
            throws WriterException {

        ItemsMenuDecorator itemsMenuDecorator = new ItemsMenuDecorator(
                javascriptWriter.getComponentRenderContext().getComponent(),
                selectItemComponentId, selectItemVarName);

        itemsMenuDecorator.initializeItemContext(javascriptWriter);

        menuDecoratorStack.add(itemsMenuDecorator);

        return itemsMenuDecorator;
    }

    public void popupMenuDecorator() {

        ItemsMenuDecorator itemsMenuDecorator = (ItemsMenuDecorator) menuDecoratorStack
                .remove(menuDecoratorStack.size() - 1);

        itemsMenuDecorator.finalizeItemContext();
    }

    public ItemsMenuDecorator peekMenuDecorator() {
        return (ItemsMenuDecorator) menuDecoratorStack.get(menuDecoratorStack
                .size() - 1);
    }
}
