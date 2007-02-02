/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.DateItemComponent;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.item.DateItem;
import org.rcfaces.core.item.BasicSelectItem;
import org.rcfaces.core.item.IClientDataItem;
import org.rcfaces.core.item.IStyleClassItem;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.AbstractCalendarRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CalendarDecorator extends AbstractSelectItemsDecorator {
    private static final String REVISION = "$Revision$";

    private final int maxResultNumber;

    private final boolean onlyDay;

    private Calendar calendar;

    private int count = 0;

    public CalendarDecorator(UIComponent component, boolean onlyDay,
            IFilterProperties filterProperties, int maxResultNumber) {
        super(component, filterProperties);

        this.onlyDay = onlyDay;

        this.maxResultNumber = maxResultNumber;
    }

    protected SelectItemsContext createHtmlContext() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext() {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        return new SelectItemsJsContext(this, componentRenderContext, null,
                null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#encodeNodeBegin(org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer.Context,
     *      javax.faces.component.UIComponent, javax.faces.model.SelectItem,
     *      boolean)
     */
    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) throws WriterException {

        count++;

        if (maxResultNumber > 0 && count > maxResultNumber) {
            return SKIP_NODE;
        }

        Object selectItemValue = selectItem.getValue();
        if (selectItemValue == null) {
            return EVAL_NODE;
        }

        javaScriptWriter.writeMethodCall("f_appendDateItem");

        if (calendar == null) {
            calendar = CalendarTools.getCalendar(getComponentRenderContext()
                    .getRenderContext().getProcessContext(), getComponent(),
                    false);
        }

        StringAppender sb = new StringAppender(32);
        if (selectItemValue instanceof Date) {
            AbstractCalendarRenderer.appendDate(calendar,
                    (Date) selectItemValue, sb, onlyDay);

        } else if (selectItemValue instanceof Date[]) {
            AbstractCalendarRenderer.appendDates(calendar,
                    (Date[]) selectItemValue, sb, onlyDay);

        } else if (selectItemValue instanceof Date[][]) {
            AbstractCalendarRenderer.appendPeriods(calendar,
                    (Date[][]) selectItemValue, sb, onlyDay);
        } else {
            throw new FacesException("Invalid value for date '"
                    + selectItemValue + "'.");
        }

        javaScriptWriter.writeString(sb.toString());

        int pred = 0;

        String text = selectItem.getLabel();
        if (text != null) {
            javaScriptWriter.write(',').writeString(text);

        } else {
            pred++;
        }

        if (selectItem.isDisabled()) {
            for (; pred > 0; pred--) {
                javaScriptWriter.write(',').writeNull();
            }

            javaScriptWriter.write(',').writeBoolean(true);

        } else {
            pred++;
        }

        if (selectItem instanceof IStyleClassItem) {
            IStyleClassItem dateSelectItem = (IStyleClassItem) selectItem;

            String layer = dateSelectItem.getStyleClass();
            if (layer != null) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }

                javaScriptWriter.write(',').writeString(layer);

            } else {
                pred++;
            }
        }

        if (selectItem instanceof IClientDataItem) {
            IClientDataItem clientDataItem = (IClientDataItem) selectItem;

            if (clientDataItem.isClientDataEmpty() == false) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }

                Map map = clientDataItem.getClientDataMap();

                for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();

                    String key = (String) entry.getKey();
                    Object data = entry.getValue();
                    if (data != null) {
                        data = String.valueOf(data);
                    }

                    javaScriptWriter.write(',').writeString(key).write(',')
                            .writeString((String) data);
                }

            } else {
                pred++;
            }
        }

        javaScriptWriter.writeln(");");

        return EVAL_NODE;
    }

    /*
     * protected void encodeComponentsEnd() throws WriterException { if
     * (javaScriptWriter == null) { return; }
     * 
     * int rowCount = getSelectItemCount(); if (rowCount < 1 && service ==
     * false) { // Nous sommes dans un rendu HTML ! return; }
     * 
     * javaScriptWriter.writeCall(null, "f_setRowCount").write(rowCount)
     * .writeln(");");
     * 
     * super.encodeComponentsEnd(); }
     */
    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) {
    }

    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        if (component instanceof IFilterCapability) {
            IFilterCapability filterCapability = (IFilterCapability) component;

            String filterExpression = componentData
                    .getStringProperty("filterExpression");
            if (filterExpression != null) {
                if (filterExpression.length() < 1) {
                    filterExpression = null;
                }

                filterCapability.setFilterProperties(HtmlTools
                        .decodeFilterExpression(context.getProcessContext(),
                                component, filterExpression));
            }
        }
    }

    protected int getMaxResultNumber() {
        return maxResultNumber;
    }

    protected SelectItem createSelectItem(UISelectItem component) {
        if (component instanceof DateItemComponent) {
            return new DateItem(component);
        }

        return new BasicSelectItem(component);
    }
}
