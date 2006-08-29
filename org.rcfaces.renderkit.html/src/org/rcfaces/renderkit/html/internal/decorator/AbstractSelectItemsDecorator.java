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
 * Revision 1.6  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.5  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.4  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
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
import org.rcfaces.core.internal.decorator.ISelectItemMapper;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.model.BasicImagesSelectItem;
import org.rcfaces.core.model.BasicSelectItem;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFilteredCollection;
import org.rcfaces.core.model.IFilteredCollection.IFilteredIterator;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.ICssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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

    protected final String getClassName() {
        if ((renderer instanceof ICssRenderer) == false) {
            throw new FacesException("Can not compute className !");
        }

        if (className != null) {
            return className;
        }

        className = ((ICssRenderer) renderer).getStyleClassName(writer
                .getComponentRenderContext());

        return className;
    }

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

                SelectItem si = (SelectItem) uiSelectItem.getValue();
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

        if (mapper.acceptCollections()) {
            return true;
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

        return javaScriptWriter.getComponentRenderContext();
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

        if (value instanceof IFilteredCollection) {
            IFilteredCollection filteredList = (IFilteredCollection) value;

            int max = getMaxResultNumber();
            if (max > 0) {
                max -= selectItemCount;
                if (max < 1) {
                    return;
                }
            }

            Iterator it = filteredList.iterator(filterProperties, max);
            try {
                int sic = 0;

                for (; it.hasNext();) {
                    SelectItem si = (SelectItem) it.next();

                    encodeSelectItem(component, si, depth, visible);

                    sic++;
                }

                if (it instanceof IFilteredCollection.IFilteredIterator) {
                    int s = ((IFilteredCollection.IFilteredIterator) it)
                            .getSize();
                    if (s > sic) {
                        selectItemCount += s - sic;
                    }
                }

            } finally {
                if (it instanceof IFilteredIterator) {
                    ((IFilteredIterator) it).release();
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
        }

        throw new WriterException("Illegal uiSelectItems value type !", null,
                component);

    }

    protected int getMaxResultNumber() {
        return IFilteredCollection.NO_MAXIMUM_RESULT_NUMBER;
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
}
