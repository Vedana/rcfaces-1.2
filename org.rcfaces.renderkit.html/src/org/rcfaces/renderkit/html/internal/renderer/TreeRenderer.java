/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.lang.reflect.Array;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.IClientFullStateCapability;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.renderkit.html.internal.AbstractSelectItemsRenderer;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ISelectItemNodeWriter;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;
import org.rcfaces.renderkit.html.internal.decorator.TreeDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    private static final String NODE_ROW_ID = "#node";

    private static final String FOCUS_ID_SUFFIX = "::focus";

    private static final String BODY_ID_SUFFIX = "::body";

    protected void encodeBeforeDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(htmlWriter, componentDecorator);

        IComponentRenderContext componentContext = htmlWriter
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentContext
                .getComponent();
        FacesContext facesContext = componentContext.getFacesContext();

        htmlWriter.startElement(IHtmlWriter.DIV);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (treeComponent.isCheckable(facesContext)) {
            int cardinality = treeComponent.getCheckCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ICheckCardinalityCapability.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:checkCardinality", cardinality);

            int ccfs = treeComponent.getClientCheckFullState(facesContext);
            if (ccfs != IClientFullStateCapability.NONE_CLIENT_FULL_STATE) {
                htmlWriter.writeAttribute("v:clientCheckFullState", ccfs);
            }
        }

        if (treeComponent.isSelectable(facesContext)) {
            int cardinality = treeComponent
                    .getSelectionCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ISelectionCardinalityCapability.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:selectionCardinality", cardinality);

            int csfs = treeComponent.getClientSelectionFullState(facesContext);
            if (csfs != IClientFullStateCapability.NONE_CLIENT_FULL_STATE) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", csfs);
            }
        }

        if (treeComponent.isExpandable(facesContext) == false) {
            htmlWriter.writeAttribute("v:userExpandable", false);
        }

        if (treeComponent.isHideRootExpandSign(facesContext)) {
            htmlWriter.writeAttribute("v:hideRootExpandSign", true);
        }

        int depthLevel = treeComponent.getPreloadedLevelDepth(facesContext);
        if (depthLevel > 0) {
            htmlWriter.writeAttribute("v:preloadedLevelDepth", depthLevel);
        }

        Object cursorValue = treeComponent.getCursorValue(facesContext);
        String clientCursorValue = null;

        if (cursorValue != null) {
            clientCursorValue = ValuesTools.convertValueToString(cursorValue,
                    treeComponent, facesContext);
        }

        if (clientCursorValue != null) {
            htmlWriter.writeAttribute("v:cursorValue", clientCursorValue);
        }

        htmlWriter.getJavaScriptEnableMode().enableOnInit();

        String overStyleClass = treeComponent.getOverStyleClass(facesContext);
        if (overStyleClass != null) {
            htmlWriter.writeAttribute("v:overStyleClass", overStyleClass);

            // htmlWriter.getJavaScriptEnableMode().enableOnOver();
        }

        if (treeComponent.isDraggable(facesContext)) {
            int dragEffects = treeComponent.getDragEffects(facesContext);

            if (dragEffects <= IDragAndDropEffects.UNKNOWN_DND_EFFECT) {
                dragEffects = IDragAndDropEffects.NONE_DND_EFFECT;
            }
            htmlWriter.writeAttribute("v:dragEffects", dragEffects);

            String dragTypes[] = treeComponent.getDragTypes(facesContext);
            if (dragTypes != null && dragTypes.length > 0) {
                StringAppender sa = new StringAppender(dragTypes.length * 32);

                for (int i = 0; i < dragTypes.length; i++) {
                    if (sa.length() > 0) {
                        sa.append(',');
                    }

                    sa.append(dragTypes[i]);
                }

                htmlWriter.writeAttribute("v:dragTypes", sa.toString());
            }
        }

        if (treeComponent.isDroppable(facesContext)) {
            int dropEffects = treeComponent.getDropEffects(facesContext);

            if (dropEffects <= IDragAndDropEffects.UNKNOWN_DND_EFFECT) {
                dropEffects = IDragAndDropEffects.NONE_DND_EFFECT;
            }
            htmlWriter.writeAttribute("v:dropEffects", dropEffects);

            String dropTypes[] = treeComponent.getDropTypes(facesContext);
            if (dropTypes != null && dropTypes.length > 0) {
                StringAppender sa = new StringAppender(dropTypes.length * 32);

                for (int i = 0; i < dropTypes.length; i++) {
                    if (sa.length() > 0) {
                        sa.append(',');
                    }

                    sa.append(dropTypes[i]);
                }

                htmlWriter.writeAttribute("v:dropTypes", sa.toString());
            }

            if (treeComponent.isBodyDroppable(facesContext)) {
                htmlWriter.writeAttribute("v:bodyDroppable", true);
            }
        }

        htmlWriter.startElement(IHtmlWriter.A);
        htmlWriter.writeId(componentContext.getComponentClientId()
                + FOCUS_ID_SUFFIX);
        htmlWriter.writeClass("f_tree_focus");

        Integer tabIndex = treeComponent.getTabIndex();
        if (tabIndex != null) {
            htmlWriter.writeTabIndex(tabIndex.intValue());
        } else {
            htmlWriter.writeTabIndex(0);
        }
        htmlWriter.writeRole(IAccessibilityRoles.TREE);
        htmlWriter.endElement(IHtmlWriter.A);

        htmlWriter.startElement(IHtmlWriter.UL);
        htmlWriter.writeId(componentContext.getComponentClientId()
                + BODY_ID_SUFFIX);
        htmlWriter.writeClass("f_tree_body");
    }

    protected String getWAIRole() {
        return null;
    }

    protected void addUnlockProperties(Set unlockedProperties) {
        super.addUnlockProperties(unlockedProperties);

        unlockedProperties.add("cursor");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TreeComponent treeComponent = (TreeComponent) component;

        String cursorValue = componentData.getStringProperty("cursor");
        if (cursorValue != null) {
            Object cursorValueObject = ValuesTools.convertStringToValue(
                    facesContext, treeComponent, cursorValue, false);

            Object oldCursorValueObject = treeComponent
                    .getCursorValue(facesContext);
            if (isEquals(oldCursorValueObject, cursorValueObject) == false) {

                treeComponent.setCursorValue(cursorValueObject);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.CURSOR_VALUE, oldCursorValueObject,
                        cursorValueObject));

            }
        }

        treeComponent.setShowValue(null);
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement(IHtmlWriter.UL);
        htmlWriter.endElement(IHtmlWriter.DIV);
    }

    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(htmlWriter,
                javaScriptRenderContext);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        TreeComponent treeComponent = (TreeComponent) htmlWriter
                .getComponentRenderContext().getComponent();
        IMenuIterator menuIterator = treeComponent.listMenus();
        if (menuIterator.hasNext()) {

            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.TREE,
                    "menu");
        }

        if (treeComponent.getPreloadedLevelDepth(facesContext) > 0) {
            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.TREE,
                    "ajax");
        }

        if (treeComponent.isDraggable(facesContext)
                || treeComponent.isDroppable(facesContext)) {
            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.TREE,
                    "dnd");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#
     * getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TREE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer#writeCustomCss
     * (org.rcfaces.core.internal.renderkit.IWriter,
     * org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer.CssWriter)
     */
    protected void writeCustomCss(IHtmlWriter htmlWriter, ICssWriter cssWriter) {
        super.writeCustomCss(htmlWriter, cssWriter);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentRenderContext
                .getComponent();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (treeComponent.getWidth(facesContext) != null
                || treeComponent.getHeight(facesContext) != null) {
            cssWriter.writeOverflow(ICssWriter.AUTO);
        }

        if (treeComponent.isBorder(facesContext) == false) {
            cssWriter.writeBorderStyle(ICssWriter.NONE);
        }

    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = new TreeDecorator(
                (TreeComponent) component);

        TreeComponent treeComponent = (TreeComponent) component;

        IComponentDecorator menuDecorators = null;
        IMenuIterator menuIterator = treeComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getItemImageWidth(menuComponent),
                    getItemImageHeight(menuComponent));

            if (menuDecorators == null) {
                menuDecorators = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(menuDecorators);
            menuDecorators = menuDecorator;
        }

        if (menuDecorators != null) {
            decorator.addChildDecorator(menuDecorators);
        }

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    public void encodeNodes(IJavaScriptWriter jsWriter,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, String containerVarId) throws WriterException {

        TreeDecorator selectItemNodeWriter = (TreeDecorator) getComponentDecorator(jsWriter
                .getHtmlComponentRenderContext());

        selectItemNodeWriter.encodeNodes(jsWriter, treeComponent, nodeRenderer,
                depth, containerVarId);
    }

    public ISelectItemNodeWriter getSelectItemNodeWriter(
            IComponentRenderContext componentRenderContext) {
        return (ISelectItemNodeWriter) getComponentDecorator(componentRenderContext);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        if (submittedValue == null
                || submittedValue.getClass().isArray() == false
                || Array.getLength(submittedValue) < 1) {
            return super.getConvertedValue(context, component, submittedValue);
        }

        Object array[] = (Object[]) submittedValue;
        Object ret[] = new Object[array.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = super.getConvertedValue(context, component, array[i]);
        }

        return ret;
    }
}