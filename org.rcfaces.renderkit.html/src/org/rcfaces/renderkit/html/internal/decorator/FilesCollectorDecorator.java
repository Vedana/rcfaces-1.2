/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.FileItemComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.item.BasicSelectItem;
import org.rcfaces.core.item.FileItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FilesCollectorDecorator extends AbstractSelectItemsDecorator {

    private static final String REVISION = "$Revision$";

    private static final String STRING_EMPTY_ARRAY[] = new String[0];

    private List sources;

    public FilesCollectorDecorator(UIComponent component) {
        super(component, null);
    }

    protected SelectItemsContext createHtmlContext() {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        return new SelectItemsContext(this, componentRenderContext,
                getComponent(), null);
    }

    protected SelectItemsContext createJavaScriptContext()
            throws WriterException {
        return null;
    }

    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {

        if (isVisible == false || selectItem.isDisabled()) {
            return SKIP_NODE;
        }

        Object value = selectItem.getValue();
        if (value instanceof String) {
            String src = ((String) value).trim();

            if (src.length() > 0) {
                if (sources == null) {
                    sources = new ArrayList(8);
                }
                if (sources.indexOf(src) < 0) {
                    sources.add(src);
                }
            }
        }

        return EVAL_NODE;
    }

    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException {
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof FileItemComponent) {
            return new FileItem(component);
        }

        return new BasicSelectItem(component);
    }

    public String[] listSources() {
        if (sources == null || sources.isEmpty()) {
            return STRING_EMPTY_ARRAY;
        }

        return (String[]) sources.toArray(new String[sources.size()]);
    }

}
