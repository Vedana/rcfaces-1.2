/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.5  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2006/02/03 11:37:33  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
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

import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.ValueHolder;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IClientDataSelectItem;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.HtmlTools;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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

        return new SelectItemsContext(this, componentRenderContext, input,
                input.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.SelectItemsRenderer#createContext(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected SelectItemsContext createJavaScriptContext() {
        if (jsVersion == false) {
            return null;
        }

        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getComponentRenderContext();

        UIInput input = (UIInput) getComponent();

        return new SelectItemsJsContext(this, componentRenderContext, input,
                input.getValue());
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
                writer.startElement("OPTGROUP");

                if (text != null) {
                    writer.writeAttribute("label", text);
                }

                return EVAL_NODE;
            }

            writer.startElement("OPTION");

            writer.writeAttribute("value", value);

            if (getContext().isValueSelected(selectItem, selectItemValue)) {
                writer.writeAttribute("selected");
                selectionCount++;
            }

            if (selectItem.isDisabled()) {
                writer.writeAttribute("disabled");
            }

            String description = selectItem.getDescription();
            if (selectItem.getDescription() != null) {
                writer.writeAttribute("title", description);
            }

            if (selectItem instanceof IClientDataSelectItem) {
                IClientDataSelectItem clientDataSelectItem = (IClientDataSelectItem) selectItem;

                if (clientDataSelectItem.isClientDataEmpty() == false) {
                    Map map = clientDataSelectItem.getClientDataMap();

                    HtmlTools.writeClientData(writer, map);
                }
            }

            if (text != null) {
                // Le Label ne marche pas sous Gecko ! (argggggh !)
                writer.writeText(text);
            }

            writer.endElement("OPTION");
        } else {
            SelectItemsJsContext selectItemsJsContext = (SelectItemsJsContext) selectItemsContext;
            if (hasChildren) {
                String varId = javaScriptWriter.getJavaScriptRenderContext()
                        .allocateVarName();

                javaScriptWriter.write(varId).write('=');
                javaScriptWriter.writeCall(null, "f_appendItem").writeNull()
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

                if (selectItem instanceof IClientDataSelectItem) {
                    IClientDataSelectItem clientDataSelectItem = (IClientDataSelectItem) selectItem;

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

            javaScriptWriter.writeCall(null, "f_appendItem").write(parentVarId)
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
                writer.endElement("OPTGROUP");

            } else {
                ((SelectItemsJsContext) selectItemsContext).popVarId();
            }
        }
    }

    public void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        UIInput input = (UIInput) component;

        decodeList(input, componentData);

        if (input instanceof IFilterCapability) {
            IFilterCapability filterCapability = (IFilterCapability) input;

            String filterExpression = componentData
                    .getStringProperty("filterExpression");
            if (filterExpression != null) {
                if (filterExpression.length() < 1) {
                    filterExpression = null;
                }

                filterCapability.setFilterProperties(HtmlTools
                        .decodeFilterExpression(component, filterExpression));
            }
        }
    }

    protected void decodeList(UIInput input, IComponentData componentData) {
        String value = componentData.getStringProperty("value");
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
