/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.TreeNodeComponent;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.capability.ICheckedValuesCapability;
import org.rcfaces.core.component.capability.IMenuPopupIdCapability;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.SelectItemMappers;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.item.IClientDataItem;
import org.rcfaces.core.item.IImagesItem;
import org.rcfaces.core.item.IStyleClassItem;
import org.rcfaces.core.item.ITreeNode;
import org.rcfaces.core.item.TreeNode;
import org.rcfaces.renderkit.html.internal.HtmlValuesTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeDecorator extends AbstractSelectItemsDecorator {

    private static final String REVISION = "$Revision$";

    private static final String IMAGES_PROPERTY = "treeRender.images";

    private static final Object[] OBJECT_EMPTY_ARRAY = new Object[0];

    private final Converter converter;

    public TreeDecorator(TreeComponent component) {
        super(component, null);

        converter = component.getConverter();
    }

    protected void preEncodeContainer() throws WriterException {

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        writer.enableJavaScript();

        mapSelectItems(componentContext, SelectItemMappers.SEARCH_IMAGE_MAPPER);

        super.preEncodeContainer();
    }

    protected void postEncodeContainer() throws WriterException {
        super.postEncodeContainer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractSelectItemsRenderer#encodeComponentsBegin(org.rcfaces.core.internal.renderkit.html.SelectItemsContext)
     */
    protected void encodeComponentsBegin() throws WriterException {
        super.encodeComponentsBegin();

        TreeRenderContext treeRenderContext = (TreeRenderContext) getContext();

        TreeComponent treeComponent = (TreeComponent) treeRenderContext
                .getRootComponent();

        FacesContext facesContext = javaScriptWriter.getFacesContext();

        boolean write = false;
        String urls[] = new String[8];

        String defaultImageURL = treeComponent.getDefaultImageURL(facesContext);
        if (defaultImageURL != null) {
            write = true;
            urls[0] = javaScriptWriter.allocateString(defaultImageURL);
        }

        String defaultExpandedImageURL = treeComponent
                .getDefaultExpandedImageURL(facesContext);
        if (defaultExpandedImageURL != null) {
            write = true;
            urls[1] = javaScriptWriter.allocateString(defaultExpandedImageURL);
        }

        String defaultSelectedImageURL = treeComponent
                .getDefaultSelectedImageURL(facesContext);
        if (defaultSelectedImageURL != null) {
            write = true;
            urls[2] = javaScriptWriter.allocateString(defaultSelectedImageURL);
        }

        String defaultDisabledImageURL = treeComponent
                .getDefaultDisabledImageURL(facesContext);
        if (defaultDisabledImageURL != null) {
            write = true;
            urls[3] = javaScriptWriter.allocateString(defaultDisabledImageURL);
        }

        String defaultLeafImageURL = treeComponent
                .getDefaultLeafImageURL(facesContext);
        if (defaultLeafImageURL != null) {
            write = true;
            urls[4] = javaScriptWriter.allocateString(defaultLeafImageURL);
        }

        String defaultExpandedLeafImageURL = treeComponent
                .getDefaultExpandedLeafImageURL(facesContext);
        if (defaultExpandedLeafImageURL != null) {
            write = true;
            urls[5] = javaScriptWriter
                    .allocateString(defaultExpandedLeafImageURL);
        }

        String defaultSelectedLeafImageURL = treeComponent
                .getDefaultSelectedLeafImageURL(facesContext);
        if (defaultSelectedLeafImageURL != null) {
            write = true;
            urls[6] = javaScriptWriter
                    .allocateString(defaultSelectedLeafImageURL);
        }

        String defaultDisabledLeafImageURL = treeComponent
                .getDefaultDisabledLeafImageURL(facesContext);
        if (defaultDisabledLeafImageURL != null) {
            write = true;
            urls[7] = javaScriptWriter
                    .allocateString(defaultDisabledLeafImageURL);
        }

        if (write == false) {
            return;
        }

        javaScriptWriter.writeMethodCall("_setDefaultImages");
        int pred = 0;
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            if (url == null) {
                pred++;
                continue;
            }

            if (i - pred > 0) {
                javaScriptWriter.write(',');
            }

            for (; pred > 0; pred--) {
                javaScriptWriter.writeNull().write(',');
            }

            javaScriptWriter.write(url);
        }

        javaScriptWriter.writeln(");");
    }

    protected void encodeComponentsEnd() throws WriterException {

        TreeRenderContext treeRenderContext = (TreeRenderContext) getContext();

        if (treeRenderContext.isCheckable()
                && treeRenderContext.writeCheckFullState()) {
            writeFullStates(javaScriptWriter, "f_setCheckStates",
                    treeRenderContext.getCheckValues());
        }

        if (treeRenderContext.isSelectable()
                && treeRenderContext.writeSelectionFullState()) {
            writeFullStates(javaScriptWriter, "f_setSelectionStates",
                    treeRenderContext.getSelectionValues());
        }

        super.encodeComponentsEnd();
    }

    private void writeFullStates(IJavaScriptWriter jsWriter, String jsCommand,
            Set objects) throws WriterException {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        jsWriter.writeMethodCall(jsCommand).write('[');
        int i = 0;
        for (Iterator it = objects.iterator(); it.hasNext();) {
            Object value = it.next();

            String convert = convertItemValue(componentRenderContext, value);

            if (convert == null) {
                continue;
            }

            if (i > 0) {
                jsWriter.write(',');
            }

            jsWriter.writeString(convert);

            i++;
        }

        jsWriter.writeln("]);");

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeTreeNodeBegin(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.TreeContext,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem)
     */
    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        TreeRenderContext treeRenderContext = (TreeRenderContext) getContext();

        String parentVarId = null;

        if (treeRenderContext.countVarId() > 0) {
            parentVarId = treeRenderContext.peekVarId();
        }

        /*
         * System.out.println("isv=" + isVisible + "/" + selectItem.getLabel() +
         * "/" + selectItem.getValue());
         */
        if (isVisible == false) {
            int preloadLevelDepth = treeRenderContext.getPreloadedLevelDepth();
            if (preloadLevelDepth > 0
                    && preloadLevelDepth < treeRenderContext.getDepth()) {
                if (treeRenderContext.isFirstInteractiveChild(parentVarId)) {
                    javaScriptWriter.writeMethodCall("f_setInteractiveParent")
                            .write(parentVarId).writeln(");");
                }

                return SKIP_NODE;
            }
        }

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        treeRenderContext.pushVarId(varId);

        javaScriptWriter.write("var ").write(varId);

        if (parentVarId == null) {
            parentVarId = javaScriptWriter.getComponentVarName();
        }

        javaScriptWriter.write('=').writeMethodCall("f_appendNode2").write(
                parentVarId).write(',');

        IObjectLiteralWriter objectLiteralWriter = javaScriptWriter
                .writeObjectLiteral(false);

        Object selectItemValue = selectItem.getValue();

        String value = convertItemValue(javaScriptWriter
                .getHtmlComponentRenderContext(), selectItemValue);
        if (value != null) {
            objectLiteralWriter.writeSymbol("_value").writeString(value);
        }

        String text = selectItem.getLabel();
        if (text != null) {
            objectLiteralWriter.writeSymbol("_label").writeString(text);
        }

        String toolTip = selectItem.getDescription();
        if (toolTip != null) {
            objectLiteralWriter.writeSymbol("_description")
                    .writeString(toolTip);
        }

        if (selectItem.isDisabled()) {
            objectLiteralWriter.writeSymbol("_disabled").writeBoolean(true);
        }

        if (treeRenderContext.isUserExpandable()) {
            if (treeRenderContext.isValueExpanded(selectItem, selectItemValue)) { // Expand
                objectLiteralWriter.writeSymbol("_expanded").writeBoolean(true);
            }
        }

        if (treeRenderContext.writeSelectionFullState() == false) {
            if (treeRenderContext.isValueSelected(selectItem, selectItemValue)) { // SELECTION
                objectLiteralWriter.writeSymbol("_selected").writeBoolean(true);
            }
        }

        if (treeRenderContext.writeCheckFullState() == false) {
            if (treeRenderContext.isValueChecked(selectItem, selectItemValue)) { // CHECK
                objectLiteralWriter.writeSymbol("_checked").writeBoolean(true);
            }
        }

        if (selectItem instanceof IStyleClassItem) {
            String styleClass = ((IStyleClassItem) selectItem).getStyleClass();
            if (styleClass != null) {
                objectLiteralWriter.writeSymbol("_styleClass").writeString(
                        styleClass);
            }
        }

        if (selectItem instanceof IImagesItem) {
            writeSelectItemImages((IImagesItem) selectItem, javaScriptWriter,
                    null, null, false, objectLiteralWriter);
        }

        if (selectItem instanceof IClientDataItem) {
            writeItemClientDatas((IClientDataItem) selectItem,
                    javaScriptWriter, null, null, objectLiteralWriter);
        }

        if (selectItem instanceof IMenuPopupIdCapability) {
            String menuPopupId = ((IMenuPopupIdCapability) selectItem)
                    .getMenuPopupId();
            if (menuPopupId != null) {
                objectLiteralWriter.writeSymbol("_menuPopupId").writeString(
                        menuPopupId);
            }
        }

        objectLiteralWriter.end().writeln(");");

        return EVAL_NODE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeTreeNodeEnd(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.TreeContext,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem)
     */
    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) {

        TreeRenderContext treeRenderContext = (TreeRenderContext) getContext();

        if (isVisible == false) {
            int preloadLevelDepth = treeRenderContext.getPreloadedLevelDepth();
            if (preloadLevelDepth > 0
                    && preloadLevelDepth < treeRenderContext.getDepth()) {
                return;
            }
        }
        treeRenderContext.popVarId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext() {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();
        TreeComponent treeComponent = (TreeComponent) componentRenderContext
                .getComponent();

        return createContext(componentRenderContext, treeComponent, this, 0,
                true, null);
    }

    private SelectItemsContext createContext(
            IComponentRenderContext componentRenderContext,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, boolean sendFullStates, String containerVarId) {
        return new TreeRenderContext(nodeRenderer, componentRenderContext,
                treeComponent, depth, sendFullStates, containerVarId);
    }

    public void encodeNodes(IJavaScriptWriter javaScriptWriter,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, String containerVarId) throws WriterException {

        this.javaScriptWriter = javaScriptWriter;
        try {
            SelectItemsContext selectItemsContext = createContext(
                    javaScriptWriter.getHtmlComponentRenderContext(),
                    treeComponent, nodeRenderer, depth, false, containerVarId);

            this.selectItemsContext = selectItemsContext;

            super.encodeNodes(treeComponent);

        } finally {
            this.javaScriptWriter = null;
            this.selectItemsContext = null;
        }
    }

    protected void encodeNodes(TreeComponent treeComponent)
            throws WriterException {

        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) javaScriptWriter
                .getHtmlComponentRenderContext().getRenderContext();

        String interactiveComponentClientId = htmlRenderContext
                .getCurrentInteractiveRenderComponentClientId();

        if (interactiveComponentClientId != null) {
            // Pas de donn�es si nous sommes dans un scope interactif !
            javaScriptWriter.writeMethodCall("_setInteractiveShow").write('"')
                    .write(interactiveComponentClientId).writeln("\");");
            return;
        }

        super.encodeNodes(treeComponent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.IWriter)
     */

    protected IHtmlRenderContext getHtmlRenderContext(IHtmlWriter writer) {
        return (IHtmlRenderContext) writer.getComponentRenderContext()
                .getRenderContext();
    }

    protected SelectItemsContext createHtmlContext() {
        return null;
    }

    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TreeComponent tree = (TreeComponent) component;

        boolean checkable = tree.isCheckable(facesContext);

        String checkedValues = componentData.getStringProperty("checkedItems");
        String uncheckedValues = componentData
                .getStringProperty("uncheckedItems");
        if (checkable && (checkedValues != null || uncheckedValues != null)) {
            Object v = tree.getCheckedValues(facesContext);
            if (v == null) {
                // Le CHECK est sur la value !
                v = HtmlValuesTools.convertValuesToSet(facesContext, tree, tree
                        .getValue());

            } else {
                // On retire la checkValue pour n'utiliser que la value !
                tree.setCheckedValues(null);
            }

            Set values = ValuesTools.valueToSet(v, true);

            if (HtmlValuesTools.updateValues(facesContext, tree, false, values,
                    checkedValues, uncheckedValues)
                    && tree.isValueLocked(facesContext) == false) {

                if (values.isEmpty()) {
                    ValuesTools.setValue(tree, OBJECT_EMPTY_ARRAY);

                } else {
                    ValuesTools.setValue(tree, values.toArray());
                }
            }
        }

        String selectedValues = componentData
                .getStringProperty("selectedItems");
        String deselectedValues = componentData
                .getStringProperty("deselectedItems");
        if (selectedValues != null || deselectedValues != null) {
            if (checkable == false) {
                // La selection est sur la value !
                Object v = tree.getSelectedValues(facesContext);
                if (v != null) {
                    // On retire la selectionValue pour n'utiliser que la value
                    // !
                    tree.setSelectedValues(null);
                }

                // Set values = ValuesTools.valueToSet(v, true);

                // On recommence à ZERO !
                Set values = new HashSet();

                if (HtmlValuesTools.updateValues(facesContext, tree, false,
                        values, selectedValues, deselectedValues)) {

                    int selectionCardinality = tree
                            .getSelectionCardinality(facesContext);
                    if (selectionCardinality == ICardinality.ONE_CARDINALITY
                            || selectionCardinality == ICardinality.OPTIONAL_CARDINALITY) {
                        // On prend le premier seulement !

                        if (values.isEmpty()) {
                            ValuesTools.setValue(tree, null);

                        } else {
                            ValuesTools
                                    .setValue(tree, values.iterator().next());
                        }

                    } else if (values.isEmpty()) {
                        ValuesTools.setValue(tree, OBJECT_EMPTY_ARRAY);

                    } else {
                        ValuesTools.setValue(tree, values.toArray());
                    }
                }
            } else {
                // Object v = tree.getSelectionValues(facesContext);

                // Set values = ValuesTools.valueToSet(v, true);

                // On recommence à ZERO !
                Set values = new HashSet();

                HtmlValuesTools.updateValues(facesContext, tree, true, values,
                        selectedValues, deselectedValues);

                Class cls = tree.getSelectedValuesType(facesContext);
                if (cls == null) {
                    cls = Object[].class;
                }

                tree.setSelectedValues(ValuesTools.adaptValues(cls, values));
            }
        }

        String expandedValues = componentData
                .getStringProperty("expandedItems");
        String collapsedValues = componentData
                .getStringProperty("collapsedItems");
        if (collapsedValues != null || expandedValues != null) {
            Object v = tree.getExpandedValues(facesContext);

            Set values = ValuesTools.valueToSet(v, true);

            if (HtmlValuesTools.updateValues(facesContext, tree, true, values,
                    expandedValues, collapsedValues)) {

                Class cls = tree.getExpandedValuesType(facesContext);
                if (cls == null && v != null) {
                    cls = v.getClass();
                }

                tree.setExpandedValues(ValuesTools.adaptValues(cls, values));
            }
        }
    }

    protected Converter getConverter() {
        return converter;
    }

    private Set convertSelection(Object selection) {
        if (selection instanceof Object[]) {
            return new HashSet(Arrays.asList((Object[]) selection));
        }

        if (selection instanceof Collection) {
            return new HashSet((Collection) selection);
        }

        throw new FacesException(
                "Bad type of value for attribute selectedValues/checkedValues !");
    }

    protected SelectItem convertToSelectItem(Object value) {
        if (value instanceof ITreeNode) {
            ITreeNode treeNode = (ITreeNode) value;

            return new TreeNode(treeNode);
        }

        return super.convertToSelectItem(value);
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof TreeNodeComponent) {
            return new TreeNode((TreeNodeComponent) component);
        }

        return super.createSelectItem(component);
    }
}
