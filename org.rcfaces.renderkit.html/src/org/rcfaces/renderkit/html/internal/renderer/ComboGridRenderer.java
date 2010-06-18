/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboGridComponent;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.ISubInputClientIdRenderer;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboGridRenderer extends KeyEntryRenderer implements
        ISubInputClientIdRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ComboGridRenderer.class);

    private static final int ARROW_IMAGE_WIDTH = 16;

    private static final int ARROW_IMAGE_HEIGHT = 16;

    private static final String BUTTON_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "button";

    private static final String INPUT_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "input";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO_GRID;
    }

    protected void encodeGrid(IHtmlWriter htmlWriter) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ComboGridComponent comboGridComponent = (ComboGridComponent) componentRenderContext
                .getComponent();

        AbstractGridRenderContext gridRenderContext = getGridRenderContext(componentRenderContext);

        Map formatValues = new HashMap();

        String valueFormat = comboGridComponent.getValueFormat(facesContext);
        if (valueFormat != null) {
            formatValues.put("valueFormat", valueFormat);
        }

        String valueFormatLabel = comboGridComponent
                .getValueFormatLabel(facesContext);
        if (valueFormatLabel != null) {
            formatValues.put("valueFormatLabel", valueFormatLabel);
        }

        Map formattedValues = null;
        String formattedValue = null;
        String formattedValueLabel = null;
        String convertedSelectedValue = null;
        Object selectedValue = comboGridComponent
                .getSelectedValue(facesContext);
        String valueColumnId = comboGridComponent
                .getValueColumnId(facesContext);

        if (selectedValue != null) {
            UIComponent converterComponent = getColumn(comboGridComponent,
                    valueColumnId);

            convertedSelectedValue = ValuesTools.convertValueToString(
                    selectedValue, converterComponent, facesContext);

            if (convertedSelectedValue != null
                    && convertedSelectedValue.length() > 0) {

                formattedValues = formatValue(facesContext, comboGridComponent,
                        convertedSelectedValue, formatValues);
                if (formattedValues != null) {
                    formattedValue = (String) formattedValues
                            .get("valueFormat");
                    formattedValueLabel = (String) formattedValues
                            .get("valueFormatLabel");
                }

                if (formattedValue == null) {
                    componentRenderContext.setAttribute(INPUT_ERRORED_PROPERTY,
                            Boolean.TRUE);

                    formattedValue = convertedSelectedValue;
                }
            }
        }

        boolean disabled = comboGridComponent.isDisabled(facesContext);
        boolean readOnly = comboGridComponent.isReadOnly(facesContext);
        boolean editable = comboGridComponent.isEditable(facesContext);

        ICssStyleClasses cssStyleClasses = getCssStyleClasses(htmlWriter);

        if (disabled) {
            cssStyleClasses.addSuffix("_disabled");

        } else if (readOnly) {
            cssStyleClasses.addSuffix("_readOnly");
        }

        if (componentRenderContext.containsAttribute(INPUT_ERRORED_PROPERTY)) {
            cssStyleClasses.addSuffix("_errored");
        }

        String width = comboGridComponent.getWidth(facesContext);

        int colWidth = -1;
        if (width != null) {
            int totalWidth = computeSize(width, 0, 2);

            colWidth = totalWidth - ARROW_IMAGE_WIDTH - 4;
        }

        htmlWriter.startElement(IHtmlWriter.TABLE);
        htmlWriter.writeCellPadding(0);
        htmlWriter.writeCellSpacing(0);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter, cssStyleClasses, CSS_ALL_MASK);

        if (valueFormat != null) {
            htmlWriter.writeAttribute("v:valueFormat", valueFormat);
        }

        if (valueFormatLabel != null) {
            htmlWriter.writeAttribute("v:valueFormatLabel", valueFormatLabel);
        }

        int rows = gridRenderContext.getRows();
        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }
        if (gridRenderContext.isPaged() == false) {
            htmlWriter.writeAttribute("v:paged", false);
        }
        if (editable == false) {
            htmlWriter.writeAttribute("v:editable", false);
        }
        if (readOnly) {
            htmlWriter.writeAttribute("v:readOnly", true);
        }
        if (disabled) {
            htmlWriter.writeAttribute("v:disabled", true);
        }

        int maxTextLength = comboGridComponent.getMaxTextLength(facesContext);
        if (maxTextLength > 0) {
            htmlWriter.writeAttribute("v:maxTextLength", maxTextLength);
        }

        String rowStyleClasses[] = gridRenderContext.getRowStyleClasses();

        if (rowStyleClasses != null) {
            StringAppender sa = new StringAppender(rowStyleClasses.length * 32);

            for (int i = 0; i < rowStyleClasses.length; i++) {
                String token = rowStyleClasses[i];

                if (sa.length() > 0) {
                    sa.append(',');
                }
                sa.append(token);
            }

            htmlWriter.writeAttribute("v:rowStyleClass", sa.toString());
        }

        int suggestionDelayMs = comboGridComponent
                .getSuggestionDelayMs(facesContext);
        if (suggestionDelayMs > 0) {
            htmlWriter.writeAttribute("v:suggestionDelayMs", suggestionDelayMs);
        }

        int suggestionMinChars = comboGridComponent
                .getSuggestionMinChars(facesContext);
        if (suggestionMinChars > 0) {
            htmlWriter.writeAttribute("v:suggestionMinChars",
                    suggestionMinChars);
        }

        String noValueFormatLabel = comboGridComponent
                .getNoValueFormatLabel(facesContext);
        if (noValueFormatLabel != null) {
            htmlWriter.writeAttribute("v:noValueFormatLabel",
                    noValueFormatLabel);
        }

        String ac = comboGridComponent.getForLabel(facesContext);

        IRenderContext renderContext = componentRenderContext
                .getRenderContext();

        String forId = renderContext.computeBrotherComponentClientId(
                comboGridComponent, ac);

        if (forId != null) {
            htmlWriter.writeAttribute("v:forLabel", forId);

            UIComponent label = facesContext.getViewRoot().findComponent(forId);
            if (null != label && label instanceof TextComponent) {
                if (formattedValueLabel != null) {
                    ((TextComponent) label).setValue(formattedValueLabel);
                } else if (noValueFormatLabel != null) {
                    ((TextComponent) label).setValue(noValueFormatLabel);
                }
            }
        }

        String pagerStyleClass = comboGridComponent
                .getPagerStyleClass(facesContext);
        if (pagerStyleClass != null) {
            htmlWriter.writeAttribute("v:pagerStyleClass", pagerStyleClass);
        }

        String pagerLookId = comboGridComponent.getPagerLookId(facesContext);
        if (pagerLookId != null) {
            htmlWriter.writeAttribute("v:pagerLookId", pagerLookId);
        }

        String popupStyleClass = comboGridComponent
                .getPopupStyleClass(facesContext);
        if (popupStyleClass != null) {
            htmlWriter.writeAttribute("v:popupStyleClass", popupStyleClass);
        }

        String gridStyleClass = comboGridComponent
                .getGridStyleClass(facesContext);
        if (gridStyleClass != null) {
            htmlWriter.writeAttribute("v:gridStyleClass", gridStyleClass);
        }

        String gridLookId = comboGridComponent.getGridLookId(facesContext);
        if (gridLookId != null) {
            htmlWriter.writeAttribute("v:gridLookId", gridLookId);
        }

        boolean searchField = comboGridComponent
                .isSearchFieldVisible(facesContext);
        if (searchField == false) {
            htmlWriter.writeAttribute("v:searchFieldVisible", searchField);
        }

        boolean forceValidation = comboGridComponent
                .isForceValidation(facesContext);
        if (forceValidation == true) {
            htmlWriter.writeAttribute("v:forceValidation", forceValidation);
        }

        if (valueColumnId != null) {
            htmlWriter.writeAttribute("v:valueColumnId", valueColumnId);
        }

        String labelColumnId = comboGridComponent
                .getLabelColumnId(facesContext);
        if (labelColumnId != null) {
            htmlWriter.writeAttribute("v:labelColumnId", labelColumnId);
        }

        boolean headerVisible = gridRenderContext.isHeaderVisible();
        if (headerVisible == true && comboGridComponent.isHeaderVisibleSetted()) {
            htmlWriter.writeAttribute("v:headerVisible", true);
        }

        Object dataModel = gridRenderContext.getDataModel();
        if (dataModel instanceof IFiltredModel) {
            htmlWriter.writeAttribute("v:filtred", true);

            IFilterProperties filterMap = gridRenderContext.getFiltersMap();
            if (filterMap != null && filterMap.isEmpty() == false) {
                String filterExpression = HtmlTools.encodeFilterExpression(
                        filterMap, componentRenderContext.getRenderContext()
                                .getProcessContext(), componentRenderContext
                                .getComponent());
                htmlWriter.writeAttribute("v:filterExpression",
                        filterExpression);
            }
        }

        int popupWidth = gridRenderContext.getGridWidth();
        if (popupWidth > 0) {
            htmlWriter.writeAttribute("v:popupWidth", popupWidth);
        }

        int popupHeight = gridRenderContext.getGridHeight();
        if (popupHeight > 0) {
            htmlWriter.writeAttribute("v:popupHeight", popupHeight);
        }

        // if (comboGridComponent instanceof IEmptyMessageCapability) {
        String emptyMessage = ((IEmptyMessageCapability) comboGridComponent)
                .getEmptyMessage();
        if (emptyMessage != null) {
            emptyMessage = ParamUtils.formatMessage(comboGridComponent,
                    emptyMessage);
            htmlWriter.writeAttribute("v:emptyMessage", emptyMessage);
        }
        // }

        // if (comboGridComponent instanceof IEmptyDataMessageCapability) {
        String emptyDataMessage = ((IEmptyDataMessageCapability) comboGridComponent)
                .getEmptyDataMessage();
        if (emptyDataMessage != null) {
            emptyDataMessage = ParamUtils.formatMessage(comboGridComponent,
                    emptyDataMessage);

            htmlWriter.writeAttribute("v:emptyDataMessage", emptyDataMessage);
        }

        if (convertedSelectedValue != null
                && convertedSelectedValue.length() > 0) {

            htmlWriter
                    .writeAttribute("v:selectedValue", convertedSelectedValue);

            if (componentRenderContext
                    .containsAttribute(INPUT_ERRORED_PROPERTY)) {
                // La clef est inconnue !
                htmlWriter.writeAttribute("v:invalidKey", true);
            }
        }

        writePagerMessage(htmlWriter, comboGridComponent);

        htmlWriter.startElement(IHtmlWriter.COL);
        if (colWidth > 0) {
            htmlWriter.writeWidth(colWidth);
        } else {
            htmlWriter.writeWidth("1*");
        }
        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.COL);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.endElement(IHtmlWriter.COL);

        htmlWriter.startElement(IHtmlWriter.TBODY);

        htmlWriter.startElement(IHtmlWriter.TR);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeClass(getMainStyleClassName() + "_inputCell");

        writeInputSubComponent(htmlWriter, formattedValue, colWidth);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.startElement(IHtmlWriter.TD);
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeClass(getMainStyleClassName() + "_buttonCell");

        writeImageSubComponent(htmlWriter);

        htmlWriter.endElement(IHtmlWriter.TD);

        htmlWriter.endElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.TBODY);
        htmlWriter.endElement(IHtmlWriter.TABLE);

        htmlWriter.getJavaScriptEnableMode().enableOnInit();

        htmlWriter.endComponent();
    }

    protected void writeImageSubComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        htmlWriter.startElement(IHtmlWriter.IMG);
        htmlWriter.writeId(componentRenderContext.getComponentClientId()
                + BUTTON_ID_SUFFIX);
        htmlWriter.writeClass(getMainStyleClassName() + "_button");
        htmlWriter.writeWidth(ARROW_IMAGE_WIDTH);
        htmlWriter.writeHeight(ARROW_IMAGE_HEIGHT);

        String url = componentRenderContext.getHtmlRenderContext()
                .getHtmlProcessContext()
                .getStyleSheetURI(BLANK_IMAGE_URL, true);

        htmlWriter.writeSrc(url);

        htmlWriter.endElement(IHtmlWriter.IMG);
    }

    protected void writeInputSubComponent(IHtmlWriter htmlWriter,
            String formattedValue, int colWidth) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        ComboGridComponent comboGridComponent = (ComboGridComponent) componentRenderContext
                .getComponent();

        htmlWriter.startElement(IHtmlWriter.INPUT);
        if (colWidth > 0) {
            htmlWriter.writeStyle().writeWidthPx(colWidth - 4);
        }

        htmlWriter.writeType(IHtmlWriter.TEXT_INPUT_TYPE);

        htmlWriter.writeId(componentRenderContext.getComponentClientId()
                + INPUT_ID_SUFFIX);

        StringAppender sa = new StringAppender(128);
        sa.append(getMainStyleClassName());
        sa.append("_input");

        String emptyMessage = null;

        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (componentRenderContext.containsAttribute(INPUT_ERRORED_PROPERTY)) {
            sa.append(' ').append(getMainStyleClassName()).append(
                    "_input_errored");

        } else if ((formattedValue == null || formattedValue.length() == 0)) {
            emptyMessage = comboGridComponent.getEmptyMessage(facesContext);

            if (emptyMessage != null) {
                sa.append(' ').append(getMainStyleClassName()).append(
                        "_input_empty_message");

                htmlWriter.writeAttribute("v:emptyMessage", true);
            }
        }

        htmlWriter.writeClass(sa.toString());

        writeInputAttributes(htmlWriter);

        Integer tabIndex = comboGridComponent
                .getTabIndex(componentRenderContext.getFacesContext());
        if (tabIndex != null) {
            htmlWriter.writeTabIndex(tabIndex.intValue());
        }

        if (formattedValue != null) {
            htmlWriter.writeValue(formattedValue);

        } else if (emptyMessage != null) {
            htmlWriter.writeValue(emptyMessage);
        }

        htmlWriter.endElement(IHtmlWriter.INPUT);
    }

    protected void writeInputAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ComboGridComponent comboGridComponent = (ComboGridComponent) componentRenderContext
                .getComponent();

        boolean disabled = comboGridComponent.isDisabled(facesContext);
        boolean readOnly = comboGridComponent.isReadOnly(facesContext);
        boolean editable = comboGridComponent.isEditable(facesContext);

        if (disabled) {
            htmlWriter.writeDisabled();
        }
        if (readOnly || editable == false) {
            htmlWriter.writeReadOnly();
        }
    }

    protected IHtmlWriter writeIdAttribute(IHtmlWriter htmlWriter)
            throws WriterException {
        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        if (isDataGridRenderer(htmlWriter) == false) {
            return super.writeIdAttribute(htmlWriter);
        }

        StringAppender id = new StringAppender(componentRenderContext
                .getComponentClientId(), 16);

        String separator = componentRenderContext.getRenderContext()
                .getProcessContext().getNamingSeparator();
        if (separator != null) {
            id.append(separator);
        } else {
            id.append(NamingContainer.SEPARATOR_CHAR);
        }

        id.append("grid");

        htmlWriter.writeId(id.toString());

        return htmlWriter;
    }

    protected void encodeJsHeader(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        String htmlContent = (String) jsWriter.getComponentRenderContext()
                .removeAttribute(GRID_HTML_CONTENT);

        if (htmlContent != null) {
            jsWriter.writeMethodCall("f_setGridInnerHTML").writeString(
                    htmlContent).writeln(");");
        }
    }

    public AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext) {
        AbstractGridRenderContext tableContext = new ComboGridRenderContext(
                componentRenderContext);

        return tableContext;
    }

    public DataGridRenderContext createTableContext(
            IProcessContext processContext,
            IScriptRenderContext scriptRenderContext, IGridComponent dg,
            int rowIndex, int forcedRows, ISortedComponent sortedComponents[],
            String filterExpression, String showAdditionals,
            String hideAdditionals) {
        DataGridRenderContext tableContext = new ComboGridRenderContext(
                processContext, scriptRenderContext, dg, rowIndex, forcedRows,
                sortedComponents, filterExpression, showAdditionals,
                hideAdditionals);

        return tableContext;
    }

    public String computeSubInputClientId(IRenderContext renderContext,
            UIComponent component, String clientId) {
        return clientId + INPUT_ID_SUFFIX;
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, GENERATE_CELL_IMAGES
                | GENERATE_CELL_TEXT | GENERATE_CELL_WIDTH);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public class ComboGridRenderContext extends DataGridRenderContext {

        private static final String REVISION = "$Revision$";

        public ComboGridRenderContext(IProcessContext processContext,
                IScriptRenderContext scriptRenderContext, IGridComponent dg,
                int rowIndex, int forcedRows,
                ISortedComponent[] sortedComponents, String filterExpression,
                String showAdditionals, String hideAdditionals) {
            super(processContext, scriptRenderContext, dg, rowIndex,
                    forcedRows, sortedComponents, filterExpression,
                    showAdditionals, hideAdditionals);
        }

        public ComboGridRenderContext(
                IHtmlComponentRenderContext componentRenderContext) {
            super(componentRenderContext);
        }

        protected void computeGridSize(ISizeCapability sizeCapability) {
            this.gridWidth = ((ComboGridComponent) gridComponent)
                    .getPopupWidth();
            this.gridHeight = ((ComboGridComponent) gridComponent)
                    .getPopupHeight();
        }

    }
}
