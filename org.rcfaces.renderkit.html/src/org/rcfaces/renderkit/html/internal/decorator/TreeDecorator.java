/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
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
 * Revision 1.6  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.5  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.SelectItemMappers;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.model.IImagesSelectItem;
import org.rcfaces.core.provider.IURLRewritingProvider;
import org.rcfaces.renderkit.html.internal.HtmlValuesTools;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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

        javaScriptWriter.writeCall(null, "_setDefaultImages");
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
            writeFullStates(javaScriptWriter, "_setCheckStates",
                    treeRenderContext.getCheckValues());
        }

        if (treeRenderContext.isSelectable()
                && treeRenderContext.writeSelectionFullState()) {
            writeFullStates(javaScriptWriter, "_setSelectionStates",
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
                .getComponentRenderContext();

        jsWriter.writeCall(null, jsCommand).write('[');
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
                    javaScriptWriter.writeCall(null, "_setInteractiveParent")
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

        javaScriptWriter.write('=').writeCall(null, "_appendNode").write(
                parentVarId).write(',');

        int pred = 0;

        String text = selectItem.getLabel();
        if (text != null) {
            javaScriptWriter.writeString(text);

        } else {
            javaScriptWriter.writeNull();
        }

        Object selectItemValue = selectItem.getValue();

        String value = convertItemValue(javaScriptWriter
                .getComponentRenderContext(), selectItemValue);
        if (value != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeBoolean(false);
            }
            javaScriptWriter.write(',').writeString(value);

        } else {
            pred++;
        }

        String toolTip = selectItem.getDescription();
        if (toolTip != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeBoolean(false);
            }
            javaScriptWriter.write(',').writeString(toolTip);

        } else {
            pred++;
        }

        if (selectItem.isDisabled()) { // DISABLED
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeBoolean(false);
            }

            javaScriptWriter.write(',').writeBoolean(true);

        } else {
            pred++;
        }

        if (treeRenderContext.isUserExpandable()) {
            if (treeRenderContext.isValueExpanded(selectItem, selectItemValue)) { // Expand
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeBoolean(false);
                }

                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
        }

        if (treeRenderContext.writeSelectionFullState() == false) {
            if (treeRenderContext.isValueSelected(selectItem, selectItemValue)) { // SELECTION
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeBoolean(false);
                }

                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
        }

        if (treeRenderContext.writeCheckFullState() == false) {
            if (treeRenderContext.isValueChecked(selectItem, selectItemValue)) { // CHECK
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeBoolean(false);
                }

                javaScriptWriter.write(',').writeBoolean(true);

            } else {
                pred++;
            }
        }

        javaScriptWriter.writeln(");");

        if (selectItem instanceof IImagesSelectItem) {
            IImagesSelectItem imagesSelectItem = (IImagesSelectItem) selectItem;
            String varImageURL = null;
            String varExpandedImageURL = null;
            String varSelectedImageURL = null;
            String varDisabledImageURL = null;
            String varHoverImageURL = null;

            String imageURL = imagesSelectItem.getImageURL();
            String expandedImageURL = imagesSelectItem.getExpandedImageURL();
            String selectedImageURL = imagesSelectItem.getSelectedImageURL();
            String disabledImageURL = imagesSelectItem.getDisabledImageURL();
            String hoverImageURL = null;

            IComponentRenderContext componentContext = javaScriptWriter
                    .getComponentRenderContext();

            String originalImageURL = imageURL;
            if (imageURL != null) {
                imageURL = AbstractCameliaRenderer.rewriteURL(componentContext,
                        IURLRewritingProvider.IMAGE_URL_TYPE, "imageURL",
                        imageURL, null);
            }
            if (disabledImageURL != null) {
                disabledImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "disabledImageURL", disabledImageURL, originalImageURL);
            }
            if (expandedImageURL != null) {
                expandedImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "expandedImageURL", expandedImageURL, originalImageURL);
            }
            if (selectedImageURL != null) {
                selectedImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "selectedImageURL", selectedImageURL, selectedImageURL);
            }
            if (hoverImageURL != null) {
                hoverImageURL = AbstractCameliaRenderer.rewriteURL(
                        componentContext, IURLRewritingProvider.IMAGE_URL_TYPE,
                        "hoverImageURL", hoverImageURL, originalImageURL);
            }

            if (imageURL != null) {
                varImageURL = javaScriptWriter.allocateString(imageURL);
            }

            if (expandedImageURL != null) {
                varExpandedImageURL = javaScriptWriter
                        .allocateString(expandedImageURL);
            }

            if (selectedImageURL != null) {
                varSelectedImageURL = javaScriptWriter
                        .allocateString(selectedImageURL);
            }

            if (disabledImageURL != null) {
                varDisabledImageURL = javaScriptWriter
                        .allocateString(disabledImageURL);
            }

            if (hoverImageURL != null) {
                varHoverImageURL = javaScriptWriter
                        .allocateString(hoverImageURL);
            }

            if (varImageURL != null || varExpandedImageURL != null
                    || varSelectedImageURL != null
                    || varDisabledImageURL != null || varHoverImageURL != null) {
                javaScriptWriter.writeCall(null, "_setImages").write(varId);
                pred = 0;

                if (varImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeBoolean(false);
                    }
                    javaScriptWriter.write(',').write(varImageURL);
                } else {
                    pred++;
                }

                if (varExpandedImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeBoolean(false);
                    }
                    javaScriptWriter.write(',').write(varExpandedImageURL);
                } else {
                    pred++;
                }

                if (varSelectedImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeBoolean(false);
                    }
                    javaScriptWriter.write(',').write(varSelectedImageURL);
                } else {
                    pred++;
                }

                if (varDisabledImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeBoolean(false);
                    }
                    javaScriptWriter.write(',').write(varDisabledImageURL);
                } else {
                    pred++;
                }

                if (varHoverImageURL != null) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeBoolean(false);
                    }
                    javaScriptWriter.write(',').write(varHoverImageURL);
                } else {
                    pred++;
                }

                javaScriptWriter.writeln(");");
            }
        }

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
                .getComponentRenderContext();
        TreeComponent treeComponent = (TreeComponent) componentRenderContext
                .getComponent();

        return createContext(componentRenderContext, treeComponent, this, 0,
                true);
    }

    private SelectItemsContext createContext(
            IComponentRenderContext componentRenderContext,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, boolean sendFullStates) {
        return new TreeRenderContext(nodeRenderer, componentRenderContext,
                treeComponent, depth, sendFullStates);
    }

    public void encodeNodes(IJavaScriptWriter javaScriptWriter,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth) throws WriterException {

        this.javaScriptWriter = javaScriptWriter;
        try {
            SelectItemsContext selectItemsContext = createContext(
                    javaScriptWriter.getComponentRenderContext(),
                    treeComponent, nodeRenderer, depth, false);

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
                .getComponentRenderContext().getRenderContext();

        String interactiveComponentClientId = htmlRenderContext
                .getCurrentInteractiveRenderComponentClientId();

        if (interactiveComponentClientId != null) {
            // Pas de donn�es si nous sommes dans un scope interactif !
            javaScriptWriter.writeCall(null, "_setInteractiveShow").write('"')
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
                    checkedValues, uncheckedValues)) {

                if (values.isEmpty()) {
                    tree.setSubmittedValue(OBJECT_EMPTY_ARRAY);

                } else {
                    tree.setSubmittedValue(values.toArray());
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
                            tree.setSubmittedValue(null);

                        } else {
                            tree.setSubmittedValue(values.iterator().next());
                        }

                    } else if (values.isEmpty()) {
                        tree.setSubmittedValue(OBJECT_EMPTY_ARRAY);

                    } else {
                        tree.setSubmittedValue(values.toArray());
                    }
                }
            } else {
                // Object v = tree.getSelectionValues(facesContext);

                // Set values = ValuesTools.valueToSet(v, true);

                // On recommence à ZERO !
                Set values = new HashSet();

                HtmlValuesTools.updateValues(facesContext, tree, true, values,
                        selectedValues, deselectedValues);

                if (values.isEmpty()) {
                    tree.setSelectedValues(OBJECT_EMPTY_ARRAY);

                } else {
                    tree.setSelectedValues(values.toArray());
                }
            }
        }

        String expandedValues = componentData
                .getStringProperty("expandedItems");
        String collapsedValues = componentData
                .getStringProperty("collapsedItems");
        if (collapsedValues != null || expandedValues != null) {
            Object v = tree.getExpansionValues(facesContext);

            Set values = ValuesTools.valueToSet(v, true);

            if (HtmlValuesTools.updateValues(facesContext, tree, true, values,
                    expandedValues, collapsedValues)) {

                if (values.isEmpty()) {
                    tree.setExpansionValues(OBJECT_EMPTY_ARRAY);

                } else {
                    tree.setExpansionValues(values.toArray());
                }
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

}
