/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.render.Renderer;

import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.contentAccessor.ContentAccessorFactory;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.decorator.ISelectItemMapper;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.model.BasicImagesSelectItem;
import org.rcfaces.core.model.BasicSelectItem;
import org.rcfaces.core.model.IAdaptable;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredCollection;
import org.rcfaces.core.model.IImagesSelectItem;
import org.rcfaces.core.model.ISelectItem;
import org.rcfaces.core.model.ISelectItemGroup;
import org.rcfaces.core.model.IFiltredCollection.IFiltredIterator;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractSelectItemsDecorator extends
        AbstractComponentDecorator implements ISelectItemNodeWriter {
    private static final String REVISION = "$Revision$";

    // private static final String CONTEXT_ATTRIBUTE =
    // "camelia.selectItems.context";

    protected static final SelectItem[] EMPTY_SELECT_ITEM_ARRAY = new SelectItem[0];

    protected final UIComponent component;

    protected final IFilterProperties filterProperties;

    protected int selectItemCount;

    protected Renderer renderer;

    protected IHtmlWriter writer;

    private String className;

    protected IJavaScriptWriter javaScriptWriter;

    protected SelectItemsContext selectItemsContext;

    protected AbstractSelectItemsDecorator(UIComponent component,
            IFilterProperties filterProperties) {
        this.component = component;
        this.filterProperties = filterProperties;
    }

    public final void encodeContainer(IHtmlWriter writer, Renderer renderer)
            throws WriterException {
        this.writer = writer;
        this.renderer = renderer;
        try {
            preEncodeContainer();

            SelectItemsContext context = createHtmlContext();
            if (context == null) {
                postEncodeContainer();
                return;
            }

            this.selectItemsContext = context;

            Iterator it = component.getChildren().iterator();
            encodeComponents(it, 0, true);

            postEncodeContainer();

        } finally {
            this.writer = null;
            this.selectItemsContext = null;
            this.renderer = null;
        }

        super.encodeContainer(writer, renderer);
    }

    public final void encodeContainerEnd(IHtmlWriter writer, Renderer renderer)
            throws WriterException {
        super.encodeContainerEnd(writer, renderer);

        this.writer = writer;
        this.renderer = renderer;
        try {
            preEncodeContainerEnd();

            postEncodeContainerEnd();

        } finally {
            this.writer = null;
            this.renderer = null;
        }
    }

    /*
     * protected final String getClassName() { if ((renderer instanceof
     * ICssRenderer) == false) { throw new FacesException("Can not compute
     * className !"); }
     * 
     * if (className != null) { return className; }
     * 
     * className = ((ICssRenderer) renderer).getStyleClassName(writer
     * .getComponentRenderContext());
     * 
     * return className; }
     */
    protected void preEncodeContainerEnd() throws WriterException {
    }

    protected void postEncodeContainerEnd() throws WriterException {
    }

    protected void postEncodeContainer() throws WriterException {
    }

    protected void preEncodeContainer() throws WriterException {
    }

    protected boolean mapSelectItems(
            IComponentRenderContext componentRenderContext,
            ISelectItemMapper mapper) {
        Iterator it = getComponent().getChildren().iterator();

        return mapSelectItems(mapper, it);
    }

    private boolean mapSelectItems(ISelectItemMapper mapper, Iterator it) {
        for (; it.hasNext();) {
            UIComponent component = (UIComponent) it.next();

            if (component instanceof UISelectItems) {
                if (mapSelectItems(mapper, (UISelectItems) component) == false) {
                    return false;
                }
                continue;
            }

            if (component instanceof UISelectItem) {
                UISelectItem uiSelectItem = (UISelectItem) component;

                Object value = uiSelectItem.getValue();
                if ((value instanceof SelectItem) == false) {
                    value = convertToSelectItem(value);
                }

                if ((value instanceof SelectItem) == false) {
                    throw new FacesException("Value is not a SelectItem ("
                            + value + ")");
                }

                SelectItem si = (SelectItem) value;
                if (si == null) {
                    si = createSelectItem(uiSelectItem);
                }

                if (mapSelectItem(mapper, si) == false) {
                    return false;
                }
                continue;
            }

            SelectItem si = getUnknownComponent(component);
            if (si == null) {
                mapper.unknownComponent(component);
                continue;
            }

            if (mapSelectItem(mapper, si) == false) {
                return false;
            }
        }

        return true;
    }

    private boolean mapSelectItems(ISelectItemMapper mapper,
            UISelectItems uiSelectItems) {
        Object value = uiSelectItems.getValue();

        if (value instanceof SelectItem) {
            SelectItem si = (SelectItem) value;

            return mapSelectItem(mapper, si);
        }

        if (value instanceof ISelectItem) {
            ISelectItem si = (ISelectItem) value;

            value = convertToSelectItem(si);
        }

        if (mapper.acceptCollections()) {
            return true;
        }

        if (value != null) {
            Class cls = value.getClass();
            if (cls.isArray()
                    && SelectItem.class
                            .isAssignableFrom(cls.getComponentType())) {
                Object sis[] = (Object[]) value;

                for (int i = 0; i < sis.length; i++) {
                    if (mapSelectItem(mapper, (SelectItem) sis[i]) == false) {
                        return false;
                    }
                }

                return true;
            }
        }

        if (value instanceof Collection) {
            Collection c = (Collection) value;
            if (c.isEmpty()) {
                return true;
            }

            for (Iterator it2 = c.iterator(); it2.hasNext();) {
                SelectItem si = (SelectItem) it2.next();

                if (mapSelectItem(mapper, si) == false) {
                    return false;
                }
            }

            return true;
        }

        if (value instanceof Map) {
            Map map = (Map) value;

            if (map.isEmpty()) {
                return true;
            }

            for (Iterator it2 = map.keySet().iterator(); it2.hasNext();) {
                String itemLabel = (String) it2.next();

                Object itemValue = map.get(itemLabel);

                SelectItem selectItem = new SelectItem(itemValue, itemLabel);

                if (mapSelectItem(mapper, selectItem) == false) {
                    return false;
                }
            }

            return true;
        }

        return true;
    }

    protected SelectItem convertToSelectItem(Object value) {
        if (value instanceof ISelectItem) {
            if (value instanceof ISelectItemGroup) {
                ISelectItemGroup selectItemGroup = (ISelectItemGroup) value;

                SelectItemGroup sig = new SelectItemGroup(selectItemGroup
                        .getLabel(), selectItemGroup.getDescription(),
                        selectItemGroup.isDisabled(), selectItemGroup
                                .getSelectItems());

                sig.setValue(selectItemGroup.getValue());

                return sig;
            }

            ISelectItem selectItem = (ISelectItem) value;

            return new SelectItem(selectItem.getValue(), selectItem.getLabel(),
                    selectItem.getDescription(), selectItem.isDisabled());
        }

        if (value instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) value;

            SelectItem selectItem = (SelectItem) adaptable.getAdapter(
                    SelectItem.class, null);
            if (selectItem != null) {
                return selectItem;
            }
        }

        if (Constants.ADAPT_SELECT_ITEMS) {
            RcfacesContext rcfacesContext = RcfacesContext
                    .getInstance(getComponentRenderContext().getFacesContext());
            SelectItem selectItem = (SelectItem) rcfacesContext
                    .getAdapterManager().getAdapter(value, SelectItem.class,
                            null);
            if (selectItem != null) {
                return selectItem;
            }
        }

        return null;
    }

    private boolean mapSelectItem(ISelectItemMapper mapper, SelectItem si) {
        if (mapper.map(si) == false) {
            return false;
        }

        if (si instanceof SelectItemGroup) {
            SelectItemGroup sig = (SelectItemGroup) si;

            SelectItem sis[] = sig.getSelectItems();
            for (int i = 0; i < sis.length; i++) {
                if (mapSelectItem(mapper, sis[i]) == false) {
                    return false;
                }
            }
        }

        return true;
    }

    public void encodeJavaScript(IJavaScriptWriter jsWriter)
            throws WriterException {

        this.javaScriptWriter = jsWriter;
        try {
            SelectItemsContext context = createJavaScriptContext();
            if (context == null) {
                return;
            }

            selectItemsContext = context;

            encodeComponentsBegin();

            encodeNodes(getComponent());

            encodeComponentsEnd();

        } finally {
            this.javaScriptWriter = null;
            this.selectItemsContext = null;
        }

        super.encodeJavaScript(jsWriter);
    }

    protected void encodeComponentsEnd() throws WriterException {
    }

    protected void encodeComponentsBegin() throws WriterException {
    }

    public final SelectItemsContext getContext() {
        return selectItemsContext;
    }

    public final UIComponent getComponent() {
        return component;
    }

    protected IComponentRenderContext getComponentRenderContext() {
        if (writer != null) {
            return writer.getComponentRenderContext();
        }

        return javaScriptWriter.getHtmlComponentRenderContext();
    }

    protected void encodeNodes(UIComponent component) throws WriterException {
        Iterator it = component.getChildren().iterator();

        encodeComponents(it, 0, true);
    }

    protected void encodeComponents(Iterator it, int depth, boolean visible)
            throws WriterException {

        for (; it.hasNext();) {
            UIComponent component = (UIComponent) it.next();

            if (component instanceof UISelectItems) {
                UISelectItems uiSelectItems = (UISelectItems) component;

                encodeUISelectItems(component, uiSelectItems.getValue(), depth,
                        visible);

                continue;
            }

            if (component instanceof UISelectItem) {
                UISelectItem uiSelectItem = (UISelectItem) component;

                Object value = uiSelectItem.getValue();
                if (value == null) {
                    value = createSelectItem(uiSelectItem);
                }

                // On sait jamais, la value peut etre un group !
                encodeUISelectItems(component, value, depth, visible);

                continue;
            }

            SelectItem closeSelectItem = getUnknownComponent(component);
            if (closeSelectItem != null) {

                selectItemCount++;

                encodeSelectItem(component, closeSelectItem, depth, visible);
                continue;
            }

        }
    }

    protected SelectItem getUnknownComponent(UIComponent component) {
        return null;
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof IImageCapability) {
            return new BasicImagesSelectItem(component);
        }

        return new BasicSelectItem(component);
    }

    private void encodeUISelectItems(UIComponent component, Object value,
            int depth, boolean visible) throws WriterException {

        if (value == null) {
            if (getComponentRenderContext().getRenderContext()
                    .getProcessContext().isDesignerMode()) {

                // En mode Designer ... on reste discret :-)
                return;
            }
            throw new WriterException("UISelectItems value is null !", null,
                    component);
        }

        if (value instanceof SelectItem) {
            SelectItem si = (SelectItem) value;

            encodeSelectItem(component, si, depth, visible);
            return;
        }

        if (value instanceof SelectItem[]) {
            SelectItem sis[] = (SelectItem[]) value;

            for (int i = 0; i < sis.length; i++) {
                SelectItem si = sis[i];

                encodeSelectItem(component, si, depth, visible);
            }

            return;
        }

        if (value instanceof IFiltredCollection) {
            IFiltredCollection filtredList = (IFiltredCollection) value;

            int max = getMaxResultNumber();
            if (max > 0) {
                max -= selectItemCount;
                if (max < 1) {
                    return;
                }
            }

            Iterator it = filtredList.iterator(filterProperties, max);
            try {
                int sic = 0;

                for (; it.hasNext();) {
                    SelectItem si = (SelectItem) it.next();

                    encodeSelectItem(component, si, depth, visible);

                    sic++;
                }

                if (it instanceof IFiltredCollection.IFiltredIterator) {
                    int s = ((IFiltredCollection.IFiltredIterator) it)
                            .getSize();
                    if (s > sic) {
                        selectItemCount += s - sic;
                    }
                }

            } finally {
                if (it instanceof IFiltredIterator) {
                    ((IFiltredIterator) it).release();
                }
            }

            return;
        }

        if (value instanceof Collection) {
            Collection l = (Collection) value;
            if (l.isEmpty()) {
                return;
            }

            for (Iterator it = l.iterator(); it.hasNext();) {
                SelectItem si = (SelectItem) it.next();

                encodeSelectItem(component, si, depth, visible);
            }

            return;
        }

        if (value instanceof Map) {
            Map map = (Map) value;

            if (map.isEmpty()) {
                return;
            }

            for (Iterator it = map.keySet().iterator(); it.hasNext();) {
                // Pas de entrySet() car le resultat pourrait ne pas �tre le
                // meme !
                String itemLabel = (String) it.next();

                Object itemValue = map.get(itemLabel);

                SelectItem selectItem = new SelectItem(itemValue, itemLabel);

                encodeSelectItem(component, selectItem, depth, visible);
            }

            return;
        }

        SelectItem convertedSelectItem = convertToSelectItem(value);
        if (convertedSelectItem != null) {
            encodeUISelectItems(component, convertedSelectItem, depth, visible);
            return;
        }

        if (getComponentRenderContext().getRenderContext().getProcessContext()
                .isDesignerMode()
                && (value instanceof String[])) {
            String vs[] = (String[]) value;
            if (vs != null) {
                SelectItem sis[] = new SelectItem[vs.length];
                for (int i = 0; i < sis.length; i++) {
                    SelectItem selectItem = new SelectItem("idx" + i, vs[i]);

                    encodeSelectItem(component, selectItem, depth, visible);
                }
            }

            return;
        }

        throw new WriterException("Illegal uiSelectItems value type ! (class='"
                + value.getClass() + "')", null, component);

    }

    protected int getMaxResultNumber() {
        return IFiltredCollection.NO_MAXIMUM_RESULT_NUMBER;
    }

    private boolean encodeSelectItem(UIComponent component,
            SelectItem selectItem, int depth, boolean visible)
            throws WriterException {

        selectItemCount++;

        /*
         * System.out.println("Item '" + selectItem.getValue() + "' depth=" +
         * depth + " visible=" + visible);
         */
        if (selectItemsContext.pushSelectItem(component, selectItem, visible) == false) {
            return false;
        }

        boolean v = visible;
        if (v) {
            if (depth > 0
                    && selectItemsContext.isValueExpanded(selectItem,
                            selectItem.getValue()) == false) {
                v = false;
            }
        }

        // On verifie si le selectItem n'est pas lui meme container d'autres
        // SelectItem !
        if (selectItem instanceof SelectItemGroup) {
            SelectItemGroup sig = (SelectItemGroup) selectItem;

            SelectItem selectItems[] = sig.getSelectItems();

            if (selectItems.length > 0) {
                for (int i = 0; i < selectItems.length; i++) {
                    SelectItem s2 = selectItems[i];

                    if (encodeSelectItem(component, s2, depth + 1, v) == false) {
                        break;
                    }
                }
            }
        }

        // On regarde maintenant les enfants du composant qui contient le
        // SelectItem
        if (component.getChildCount() > 0) {
            encodeComponents(component.getChildren().iterator(), depth + 1, v);
        }

        selectItemsContext.popSelectItem();

        return true;
    }

    protected abstract SelectItemsContext createHtmlContext();

    protected abstract SelectItemsContext createJavaScriptContext()
            throws WriterException;

    protected String convertItemValue(IComponentRenderContext componentContext,
            Object selectItemValue) {

        return ValuesTools.valueToString(selectItemValue, getConverter(),
                componentContext.getComponent(), componentContext
                        .getFacesContext());
    }

    protected final List listChildren(Map childrenClientIds, String itemIds,
            Class capability) {

        List l = null;

        StringTokenizer st = new StringTokenizer(itemIds,
                HtmlTools.LIST_SEPARATORS);
        for (; st.hasMoreTokens();) {
            String key = st.nextToken();

            UIComponent item = (UIComponent) childrenClientIds.get(key);
            if (item == null) {
                continue;
            }

            if (capability != null && capability.isInstance(item) == false) {
                continue;
            }

            if (l == null) {
                l = new ArrayList();
            }

            l.add(item);
        }

        if (l == null) {
            return Collections.EMPTY_LIST;
        }

        return l;

    }

    protected final Map mapChildrenClientId(Map map,
            IRequestContext renderContext, UIComponent container) {

        List children = container.getChildren();
        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();

            String childClientId = renderContext.getComponentId(child);
            if (childClientId == null) {
                continue;
            }

            if (map == null) {
                map = new HashMap();
            }
            map.put(childClientId, child);

            if (child.getChildCount() < 1) {
                continue;
            }

            map = mapChildrenClientId(map, renderContext, child);
        }

        return map;
    }

    protected final Map mapChildrenItemValues(Map map, FacesContext context,
            UIComponent container) {

        List children = container.getChildren();
        for (Iterator it = children.iterator(); it.hasNext();) {
            UIComponent child = (UIComponent) it.next();
            if ((child instanceof UISelectItem) == false) {
                continue;
            }

            Object value = ((UISelectItem) child).getItemValue();
            if (value == null) {
                continue;
            }

            if (map == null) {
                map = new HashMap();
            }
            map.put(value, child);

            if (child.getChildCount() < 1) {
                continue;
            }

            map = mapChildrenItemValues(map, context, child);
        }

        return map;
    }

    public void encodeNodeInit(UIComponent component, SelectItem selectItem) {
    }

    public int getSelectItemCount() {
        return selectItemCount;
    }

    protected Converter getConverter() {
        return null;
    }

    protected static void writeSelectItemImages(IImagesSelectItem iim,
            IJavaScriptWriter javaScriptWriter, String managerVarId,
            String methodName, String varId, boolean ignoreExpand)
            throws WriterException {

        FacesContext facesContext = javaScriptWriter.getFacesContext();

        String imageURL = iim.getImageURL();
        String disabledImageURL = iim.getDisabledImageURL();
        String hoverImageURL = iim.getHoverImageURL();
        String selectedImageURL = iim.getSelectedImageURL();
        String expandedImageURL = null;
        if (ignoreExpand == false) {
            expandedImageURL = iim.getExpandedImageURL();
        }

        IContentAccessor imageAccessor = null;
        if (imageURL != null) {
            imageAccessor = ContentAccessorFactory.createFromWebResource(
                    facesContext, imageURL, IContentType.IMAGE);
        }

        IContentAccessor disabledImageAccessor = null;
        if (disabledImageURL != null) {
            if (imageAccessor == null) {
                disabledImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, disabledImageURL,
                                IContentType.IMAGE);
            } else {
                disabledImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, disabledImageURL,
                                imageAccessor);
            }
        }

        IContentAccessor hoverImageAccessor = null;
        if (hoverImageURL != null) {
            if (imageAccessor == null) {
                hoverImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, hoverImageURL,
                                IContentType.IMAGE);
            } else {
                hoverImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, hoverImageURL,
                                imageAccessor);
            }
        }

        IContentAccessor selectedImageAccessor = null;
        if (selectedImageURL != null) {
            if (imageAccessor == null) {
                selectedImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, selectedImageURL,
                                IContentType.IMAGE);
            } else {
                selectedImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, selectedImageURL,
                                imageAccessor);
            }
        }

        IContentAccessor expandedImageAccessor = null;
        if (expandedImageURL != null) {
            if (imageAccessor == null) {
                expandedImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, expandedImageURL,
                                IContentType.IMAGE);
            } else {
                expandedImageAccessor = ContentAccessorFactory
                        .createFromWebResource(facesContext, expandedImageURL,
                                imageAccessor);
            }
        }

        if (imageAccessor == null && disabledImageAccessor == null
                && hoverImageAccessor == null && selectedImageAccessor == null
                && expandedImageURL == null) {
            return;
        }

        String imageVar = null;
        if (imageAccessor != null) {
            imageURL = imageAccessor.resolveURL(facesContext, null, null);
            if (imageURL != null) {
                imageVar = javaScriptWriter.allocateString(imageURL);
            }
        }

        String disabledVar = null;
        if (disabledImageAccessor != null) {
            disabledImageURL = disabledImageAccessor.resolveURL(facesContext,
                    null, null);
            if (disabledImageURL != null) {
                disabledVar = javaScriptWriter.allocateString(disabledImageURL);
            }
        }

        String hoverVar = null;
        if (hoverImageAccessor != null) {
            hoverImageURL = hoverImageAccessor.resolveURL(facesContext, null,
                    null);
            if (hoverImageURL != null) {
                hoverVar = javaScriptWriter.allocateString(hoverImageURL);
            }
        }

        String selectedVar = null;
        if (selectedImageAccessor != null) {
            selectedImageURL = selectedImageAccessor.resolveURL(facesContext,
                    null, null);
            if (selectedImageURL != null) {
                selectedVar = javaScriptWriter.allocateString(selectedImageURL);
            }
        }

        String expandVar = null;
        if (expandedImageAccessor != null) {
            expandedImageURL = expandedImageAccessor.resolveURL(facesContext,
                    null, null);
            if (expandedImageURL != null) {
                expandVar = javaScriptWriter.allocateString(expandedImageURL);
            }
        }

        if (managerVarId != null) {
            javaScriptWriter.writeCall(managerVarId, "f_setItemImages").write(
                    varId);

        } else {
            javaScriptWriter.writeMethodCall("f_setItemImages").write(varId);
        }

        int pred = 0;

        if (imageVar != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').write(imageVar);
        } else {
            pred++;
        }

        if (ignoreExpand == false) {
            if (expandVar != null) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }
                javaScriptWriter.write(',').write(expandVar);
            } else {
                pred++;
            }

        }

        if (disabledVar != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').write(disabledVar);
        } else {
            pred++;
        }
        if (hoverVar != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').write(hoverVar);
        } else {
            pred++;
        }

        if (selectedVar != null) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }
            javaScriptWriter.write(',').write(selectedVar);
        } else {
            pred++;
        }

        javaScriptWriter.writeln(");");

    }
}
