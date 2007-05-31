/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractCompositeRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    private static final int CHAR_SIZE = 8;

    public void writeSubInput(IHtmlWriter htmlWriter, String accessKey,
            Integer tabIndex, char ch, int chLength, int length,
            String curValue, boolean disabled, boolean readOnly,
            boolean writeSize, Map attributes) throws WriterException {

        String className = getSubStyleClassName(htmlWriter, ch, chLength);

        writeSubInput(htmlWriter, className, accessKey, tabIndex, ch, length,
                curValue, disabled, readOnly, writeSize, attributes);
    }

    protected String getSubStyleClassName(IHtmlWriter htmlWriter, char ch,
            int length) {

        String cls = getMainStyleClassName();
        StringAppender sb = new StringAppender(cls.length() * 2 + 7 + 1
                + length);

        sb.append(cls);
        sb.append("_input ");

        sb.append(cls);
        sb.append('_');
        sb.append(ch, length);

        return sb.toString();
    }

    public static void writeSubInput(IHtmlWriter htmlWriter, String className,
            String accessKey, Integer tabIndex, char ch, int length,
            String curValue, boolean disabled, boolean readOnly,
            boolean writeSize, Map attributes) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        StringAppender sa = new StringAppender(componentRenderContext
                .getComponentClientId(), 8);

        String separator = componentRenderContext.getRenderContext()
                .getProcessContext().getNamingSeparator();
        if (separator != null) {
            sa.append(separator);

        } else {
            sa.append(NamingContainer.SEPARATOR_CHAR);
        }

        sa.append(ch);

        String subId = sa.toString();

        htmlWriter.startElement(IHtmlWriter.INPUT);
        htmlWriter.writeType(IHtmlWriter.TEXT_INPUT_TYPE);
        htmlWriter.writeId(subId);
        htmlWriter.writeClass(className);
        htmlWriter.writeName(subId);
        htmlWriter.writeMaxLength(length);
        htmlWriter.writeSize(length);

        UIComponent component = componentRenderContext.getComponent();
        if (component instanceof ITextDirectionCapability) {
            writeTextDirection(htmlWriter, (ITextDirectionCapability) component);
        }

        if (writeSize) {
            double em = (length * 5);

            htmlWriter.writeStyle().writeWidth(
                    ((Math.floor(em) / 10.0) + 0.4) + "em");
        }

        htmlWriter.writeAttribute("v:type", String.valueOf(ch));

        if (tabIndex != null) {
            htmlWriter.writeTabIndex(tabIndex.intValue());
        }
        if (accessKey != null) {
            htmlWriter.writeAccessKey(accessKey);
        }

        if (disabled) {
            htmlWriter.writeDisabled();
        }
        if (readOnly) {
            htmlWriter.writeReadOnly();
        }

        /*
         * if (minValue != null) { htmlWriter.writeAttribute("v:min", minValue); }
         * 
         * if (maxValue != null) { htmlWriter.writeAttribute("v:max", maxValue); }
         * 
         * if (defaultValue != null) { htmlWriter.writeAttribute("v:default",
         * defaultValue); }
         * 
         * if (separators != null && separators.length() > 0) {
         * htmlWriter.writeAttribute("v:separators", separators); }
         * 
         * if (cycle) { htmlWriter.writeAttribute("v:cycle", true); }
         * 
         * if (autoComplete != null) { htmlWriter.writeAttribute("v:auto",
         * autoComplete); }
         * 
         * if (step != null && step.length() > 0) {
         * htmlWriter.writeAttribute("v:step", step); }
         */

        if (attributes != null) {
            for (Iterator it = attributes.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                String attributeName = (String) entry.getKey();
                String attributeValue = String.valueOf(entry.getValue());

                htmlWriter.writeAttribute(attributeName, attributeValue);
            }
        }

        if (curValue != null) {
            htmlWriter.writeValue(curValue);
        }

        htmlWriter.endElement(IHtmlWriter.INPUT);
    }

    public static void writeClientValidatorParams(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        IValidationParameters validationParameters = (IValidationParameters) componentRenderContext
                .getComponent();

        int parametersCount = validationParameters
                .getValidationParametersCount();
        if (parametersCount > 0) {
            Map parameters = validationParameters.getValidationParametersMap();

            StringAppender sb = new StringAppender(parametersCount * 128);
            for (Iterator it = parameters.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Entry) it.next();

                String key = (String) entry.getKey();
                if (sb.length() > 0) {
                    sb.append(':');
                }
                if (key == null) {
                    key = "%";
                }
                EventsRenderer.appendCommand(sb, key);

                String value = (String) entry.getValue();
                if (sb.length() > 0) {
                    sb.append(':');
                }
                if (value == null) {
                    value = "%";
                }

                EventsRenderer.appendCommand(sb, value);
            }

            htmlWriter.writeAttribute("v:clientValidatorParams", sb.toString());
        }

    }
}
