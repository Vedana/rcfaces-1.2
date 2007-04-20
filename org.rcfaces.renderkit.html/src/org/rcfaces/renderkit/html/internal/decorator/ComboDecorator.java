/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IValueLockedCapability;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.item.IClientDataItem;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboDecorator extends AbstractSelectItemsDecorator {
    private static final String REVISION = "$Revision$";

    private final boolean jsVersion;

    private final Converter converter;

    private int selectionCount = 0;

    public ComboDecorator(UIComponent component,
            IFilterProperties filterProperties, boolean jsVersion) {
        super(component, filterProperties);

        this.jsVersion = jsVersion;

        if (component instanceof ValueHolder) {
            converter = ((ValueHolder) component).getConverter();

        } else {
            converter = null;
        }
    }

    protected SelectItemsContext createHtmlContext() {
        if (jsVersion) {
            return null;
        }

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        UIInput input = (UIInput) getComponent();

        Object selectionValue = input.getValue();

        return new SelectItemsContext(this, componentRenderContext, input,
                selectionValue);
    }

    protected SelectItemsContext createJavaScriptContext() {
        if (jsVersion == false) {
            return null;
        }

        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getHtmlComponentRenderContext();

        UIInput input = (UIInput) getComponent();

        Object selectionValue = input.getValue();

        return new SelectItemsJsContext(this, componentRenderContext, input,
                selectionValue);
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

        IComponentRenderContext componentRenderContext = getComponentRenderContext();

        Object selectItemValue = selectItem.getValue();
        String value = convertItemValue(componentRenderContext, selectItemValue);
        if (value == null) {
            return EVAL_NODE;
        }

        if (hasChildren) {
            if (getContext().getDepth() > 1) {
                throw new WriterException(
                        "Optgroup does not support more 1 level !", null,
                        component);
            }
        }

        String text = selectItem.getLabel();

        if (jsVersion == false) {
            if (hasChildren) {
                writer.startElement(IHtmlWriter.OPTGROUP);

                if (text != null) {
                    writer.writeLabel(text);
                }

                return EVAL_NODE;
            }

            writer.startElement(IHtmlWriter.OPTION);

            writer.writeValue(value);

            if (getContext().isValueSelected(selectItem, selectItemValue)) {
                writer.writeSelected();
                selectionCount++;
            }

            if (selectItem.isDisabled()) {
                writer.writeDisabled();
            }

            String description = selectItem.getDescription();
            if (selectItem.getDescription() != null) {
                writer.writeTitle(description);
            }

            if (selectItem instanceof IClientDataItem) {
                IClientDataItem clientDataSelectItem = (IClientDataItem) selectItem;

                if (clientDataSelectItem.isClientDataEmpty() == false) {
                    Map map = clientDataSelectItem.getClientDataMap();

                    HtmlTools.writeClientData(writer, map);
                }
            }

            if (text != null) {
                // Le Label ne marche pas sous Gecko ! (argggggh !)
                writer.writeText(text);
            }

            writer.endElement(IHtmlWriter.OPTION);
        } else {
            SelectItemsJsContext selectItemsJsContext = (SelectItemsJsContext) selectItemsContext;
            if (hasChildren) {
                String varId = javaScriptWriter.getJavaScriptRenderContext()
                        .allocateVarName();

                javaScriptWriter.write(varId).write('=');
                javaScriptWriter.writeMethodCall("f_appendItem").writeNull()
                        .write(',').write(selectItem.getLabel());

                int pred = 2; // value,selected
                if (selectItem.isDisabled()) {
                    for (; pred > 0; pred--) {
                        javaScriptWriter.write(',').writeNull();
                    }

                    javaScriptWriter.write(',').writeBoolean(true);

                    selectionCount++;
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

                if (selectItem instanceof IClientDataItem) {
                    IClientDataItem clientDataSelectItem = (IClientDataItem) selectItem;

                    Map map = clientDataSelectItem.getClientDataMap();

                    if (map.isEmpty() == false) {
                        for (; pred > 0; pred--) {
                            javaScriptWriter.write(',').writeNull();
                        }

                        for (Iterator it = map.entrySet().iterator(); it
                                .hasNext();) {
                            Map.Entry entry = (Map.Entry) it.next();

                            String dataKey = (String) entry.getKey();
                            if (dataKey == null || dataKey.length() < 1) {
                                continue;
                            }

                            Object dataValue = entry.getValue();
                            if (dataValue != null) {
                                dataValue = String.valueOf(dataValue);
                            }

                            javaScriptWriter.write(',').writeString(dataKey)
                                    .write(',').writeString((String) dataValue);

                        }
                    }
                }

                javaScriptWriter.writeln(");");

                selectItemsJsContext.pushVarId(varId);

                return EVAL_NODE;
            }

            String parentVarId = null;
            if (selectItemsJsContext.getDepth() > 1) {
                parentVarId = selectItemsJsContext.peekVarId();
            }

            javaScriptWriter.writeMethodCall("f_appendItem").write(parentVarId)
                    .write(',');

            if (text == null) {
                javaScriptWriter.writeNull();

            } else {
                javaScriptWriter.writeString(text);
            }

            int pred = 0;
            if (value != null) {
                javaScriptWriter.write(',').writeString(value);
            } else {
                pred++;
            }

            if (getContext().isValueSelected(selectItem, selectItemValue)) {
                for (; pred > 0; pred--) {
                    javaScriptWriter.write(',').writeNull();
                }

                javaScriptWriter.write(',').writeBoolean(true);
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

            javaScriptWriter.writeln(");");
        }

        return EVAL_NODE;
    }

    public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) throws WriterException {

        if (hasChildren) {
            if (jsVersion == false) {
                writer.endElement(IHtmlWriter.OPTGROUP);

            } else {
                ((SelectItemsJsContext) selectItemsContext).popVarId();
            }
        }
    }

    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        UIInput input = (UIInput) component;

        decodeList(context.getFacesContext(), input, componentData);

        if (input instanceof IFilterCapability) {
            IFilterCapability filterCapability = (IFilterCapability) input;

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

    protected void decodeList(FacesContext facesContext, UIInput input,
            IComponentData componentData) {

        if (input instanceof IValueLockedCapability) {
            if (((IValueLockedCapability) input).isValueLocked()) {
                return;
            }
        }

        String value = componentData.getStringProperty("selectedItems");
        if (value != null) {
            StringTokenizer st = new StringTokenizer(value,
                    HtmlTools.LIST_SEPARATORS);
            if (st.hasMoreTokens() == false) {
                input.setSubmittedValue(null);
                return;
            }

            input.setSubmittedValue(st.nextToken());
            return;
        }

        value = componentData.getComponentParameter();

        input.setSubmittedValue(value);
    }

    protected Converter getConverter() {
        return converter;
    }

}
