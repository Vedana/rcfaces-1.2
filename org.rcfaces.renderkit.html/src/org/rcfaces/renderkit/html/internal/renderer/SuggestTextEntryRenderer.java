/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.SuggestTextEntryComponent;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.renderkit.html.internal.IFilteredItemsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SuggestTextEntryDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SuggestTextEntryRenderer extends TextEntryRenderer implements
        IFilteredItemsRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        htmlWriter.enableJavaScript();

        super.encodeComponent(htmlWriter);
    }

    protected IHtmlWriter writeInputAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        htmlWriter = super.writeInputAttributes(htmlWriter);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        SuggestTextEntryComponent suggestTextEntryComponent = (SuggestTextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        int maxResultNumber = suggestTextEntryComponent
                .getMaxResultNumber(facesContext);
        if (maxResultNumber > 0) {
            htmlWriter.writeAttribute("v:maxResultNumber", maxResultNumber);
        }

        int suggestionDelayMs = suggestTextEntryComponent
                .getSuggestionDelayMs(facesContext);
        if (suggestionDelayMs > 0) {
            htmlWriter.writeAttribute("v:suggestionDelayMs", suggestionDelayMs);
        }

        int suggestionMinChars = suggestTextEntryComponent
                .getSuggestionMinChars(facesContext);
        if (suggestionMinChars > 0) {
            htmlWriter.writeAttribute("v:suggestionMinChars",
                    suggestionMinChars);
        }

        boolean caseSensitive = suggestTextEntryComponent
                .isCaseSensitive(facesContext);
        if (caseSensitive) {
            htmlWriter.writeAttribute("v:caseSensitive", "true");
        }

        boolean forceProposal = suggestTextEntryComponent
                .isForceProposal(facesContext);
        if (forceProposal) {
            htmlWriter.writeAttribute("v:forceProposal", "true");
        }

        Object suggestionValue = suggestTextEntryComponent
                .getSuggestionValue(facesContext);
        if (suggestionValue != null) {
            String value = ValuesTools.convertValueToString(suggestionValue,
                    suggestTextEntryComponent, facesContext);
            htmlWriter.writeAttribute("v:suggestionValue", value);
        }

        String moreResultsMessage = suggestTextEntryComponent
                .getMoreResultsMessage(facesContext);
        if (moreResultsMessage != null) {
            htmlWriter.writeAttribute("v:moreResultsMessage",
                    moreResultsMessage);
        }

        return htmlWriter;
    }

    protected void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        super.encodeJavaScript(writer);

        FacesContext facesContext = writer.getFacesContext();

        SuggestTextEntryComponent suggestTextEntryComponent = (SuggestTextEntryComponent) writer
                .getHtmlComponentRenderContext().getComponent();

        IFilterProperties filterProperties = suggestTextEntryComponent
                .getFilterProperties(facesContext);

        int maxResultNumber = suggestTextEntryComponent
                .getMaxResultNumber(facesContext);

        encodeFilteredItems(writer, suggestTextEntryComponent,
                filterProperties, maxResultNumber, false);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SUGGEST_TEXT_ENTRY;
    }

    protected IComponentDecorator createSuggestionDecorator(
            FacesContext facesContext, UIComponent component,
            Converter converter, IFilterProperties filterProperties,
            int maxResultNumber, boolean service) {

        return new SuggestTextEntryDecorator(component, converter,
                filterProperties, maxResultNumber, service);
    }

    public void encodeFilteredItems(IJavaScriptWriter writer,
            IFilterCapability component, IFilterProperties filterProperties,
            int maxResultNumber) throws WriterException {

        encodeFilteredItems(writer, component, filterProperties,
                maxResultNumber, true);
    }

    protected void encodeFilteredItems(IJavaScriptWriter writer,
            IFilterCapability component, IFilterProperties filterProperties,
            int maxResultNumber, boolean service) throws WriterException {

        FacesContext facesContext = writer.getFacesContext();

        Converter converter = null;
        if (component instanceof SuggestTextEntryComponent) {
            converter = ((SuggestTextEntryComponent) component)
                    .getSuggestionConverter(facesContext);
        }

        IComponentDecorator componentDecorator = createSuggestionDecorator(
                facesContext, (UIComponent) component, converter,
                filterProperties, maxResultNumber, service);
        if (componentDecorator == null) {
            return;
        }

        componentDecorator.encodeJavaScript(writer);
    }

    protected void decode(IRequestContext context, UIComponent element,
            IComponentData componentData) {
        super.decode(context, element, componentData);

        FacesContext facesContext = context.getFacesContext();

        SuggestTextEntryComponent suggestTextEntryComponent = (SuggestTextEntryComponent) element;

        Object oldValue = suggestTextEntryComponent
                .getSuggestionValue(facesContext);
        Object newValue = null;

        String suggestionValue = componentData
                .getStringProperty("suggestionValue");
        if (suggestionValue != null) {
            Converter converter = suggestTextEntryComponent
                    .getSuggestionConverter(facesContext);

            newValue = ValuesTools.convertStringToValue(facesContext,
                    suggestTextEntryComponent, converter, suggestionValue,
                    "suggestionValue");
        }

        if (newValue != oldValue) {
            if (newValue == null || newValue.equals(oldValue) == false) {
                suggestTextEntryComponent.setSuggestionValue(newValue);

                suggestTextEntryComponent.queueEvent(new PropertyChangeEvent(
                        suggestTextEntryComponent, Properties.SUGGESTION_VALUE,
                        oldValue, newValue));
            }
        }
    }

}
