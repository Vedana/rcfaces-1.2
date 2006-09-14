/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.2  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.SuggestTextEntryComponent;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.model.IFilterProperties;
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

    protected IHtmlWriter writeInputAttributes(IHtmlWriter writer)
            throws WriterException {
        writer = super.writeInputAttributes(writer);

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        SuggestTextEntryComponent suggestTextEntryComponent = (SuggestTextEntryComponent) writer
                .getComponentRenderContext().getComponent();

        int maxResultNumber = suggestTextEntryComponent
                .getMaxResultNumber(facesContext);
        if (maxResultNumber > 0) {
            writer.writeAttribute("v:maxResultNumber", String
                    .valueOf(maxResultNumber));
        }

        int suggestionDelayMs = suggestTextEntryComponent
                .getSuggestionDelayMs(facesContext);
        if (suggestionDelayMs > 0) {
            writer.writeAttribute("v:suggestionDelayMs", String
                    .valueOf(suggestionDelayMs));
        }

        int suggestionMinChars = suggestTextEntryComponent
                .getSuggestionMinChars(facesContext);
        if (suggestionMinChars > 0) {
            writer.writeAttribute("v:suggestionMinChars", String
                    .valueOf(suggestionMinChars));
        }

        boolean caseSensitive = suggestTextEntryComponent
                .isCaseSensitive(facesContext);
        if (caseSensitive) {
            writer.writeAttribute("v:caseSensitive", "true");
        }

        boolean forceProposal = suggestTextEntryComponent
                .isForceProposal(facesContext);
        if (forceProposal) {
            writer.writeAttribute("v:forceProposal", "true");
        }

        Object suggestionValue = suggestTextEntryComponent
                .getSuggestionValue(facesContext);
        if (suggestionValue != null) {
            String value = ValuesTools.convertValueToString(suggestionValue,
                    suggestTextEntryComponent, facesContext);
            writer.writeAttribute("v:suggestionValue", value);
        }

        return writer;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        ((IHtmlWriter) writer).enableJavaScript();

        super.encodeEnd(writer);
    }

    protected void encodeJavaScript(IJavaScriptWriter writer)
            throws WriterException {
        super.encodeJavaScript(writer);

        FacesContext facesContext = writer.getFacesContext();

        SuggestTextEntryComponent suggestTextEntryComponent = (SuggestTextEntryComponent) writer
                .getComponentRenderContext().getComponent();

        IFilterProperties filterProperties = suggestTextEntryComponent
                .getFilterProperties(facesContext);

        int maxResultNumber = suggestTextEntryComponent
                .getMaxResultNumber(facesContext);

        encodeFilteredItems(writer, suggestTextEntryComponent, filterProperties,
                maxResultNumber, false);
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
