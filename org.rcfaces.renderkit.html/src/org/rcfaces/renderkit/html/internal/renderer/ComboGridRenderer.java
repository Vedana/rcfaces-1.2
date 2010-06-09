/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboGridComponent;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IEmptyMessageCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.core.lang.FilterPropertiesMap;
import org.rcfaces.core.model.IComponentRefModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.ISubInputClientIdRenderer;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboGridRenderer extends DataGridRenderer implements
        ISubInputClientIdRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ComboGridRenderer.class);

    private static final String GRID_HTML_CONTENT = "org.rcfaces.renderkit.html.GRID_HTML_CONTENT";

    private static final int ARROW_IMAGE_WIDTH = 16;

    private static final int ARROW_IMAGE_HEIGHT = 16;

    private static final String BUTTON_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "button";

    private static final String INPUT_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "input";

    private static final String INPUT_ERRORED_PROPERTY = "org.rcfaces.html.COMBO_GRID_ERRORED";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO_GRID;
    }

    protected void encodeGrid(IHtmlWriter htmlWriter) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        ComboGridComponent comboGridComponent = (ComboGridComponent) componentRenderContext
                .getComponent();

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

        AbstractGridRenderContext gridRenderContext = getGridRenderContext(componentRenderContext);

        Map formatValues = new HashMap();

        String valueFormat = comboGridComponent.getValueFormat(facesContext);
        if (valueFormat != null) {
            htmlWriter.writeAttribute("v:valueFormat", valueFormat);
            formatValues.put("valueFormat", valueFormat);
        }

        String valueFormatLabel = comboGridComponent
                .getValueFormatLabel(facesContext);
        if (valueFormatLabel != null) {
            htmlWriter.writeAttribute("v:valueFormatLabel", valueFormatLabel);
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

    protected boolean needAjaxJavaScriptClasses(IHtmlWriter writer,
            IGridComponent dataGridComponent) {
        return true;
    }

    private Map formatValue(FacesContext facesContext,
            ComboGridComponent comboGridComponent,
            String convertedSelectedValue, Map formatValues) {

        DataModel dataModel = comboGridComponent.getDataModelValue();

        if (dataModel instanceof IComponentRefModel) {
            ((IComponentRefModel) dataModel).setComponent(comboGridComponent);
        }

        if ((dataModel instanceof IFiltredModel) == false) {
            if (true) {
                LOG
                        .error("Model doest not implement IFiltredModel, returns *not found*");
                return null;
            }

            if (LOG.isInfoEnabled()) {
                LOG
                        .info("Search a row value in a not filtred DataModel ! (comboGridComponent="
                                + comboGridComponent.getId() + ")");
            }

            String var = comboGridComponent.getVar(facesContext);
            if (var == null) {
                throw new FacesException("Var attribute is null !");
            }

            Map requestMap = facesContext.getExternalContext().getRequestMap();
            Object oldValue = requestMap.get(var);

            try {
                for (int rowIndex = 0;; rowIndex++) {
                    dataModel.setRowIndex(rowIndex);

                    if (dataModel.isRowAvailable() == false) {
                        break;
                    }

                    Object rowData = dataModel.getRowData();

                    // @XXX TODO Est-ce la bonne clef ?
                    if (true) {
                        continue;
                    }

                    // Oui !
                    return formatValue(facesContext, comboGridComponent,
                            rowData, formatValues);
                }

            } finally {
                requestMap.put(var, oldValue);
                dataModel.setRowIndex(-1);
            }

            return null;
        }

        IFilterProperties filterProperties = comboGridComponent
                .getFilterProperties(facesContext);
        if (filterProperties == null) {
            filterProperties = new FilterPropertiesMap();
        } else {
            filterProperties = new FilterPropertiesMap(filterProperties);
        }

        filterProperties.put("key", convertedSelectedValue);
        filterProperties.put("text", convertedSelectedValue);

        ((IFiltredModel) dataModel).setFilter(filterProperties);

        try {
            dataModel.setRowIndex(0);

            boolean available = dataModel.isRowAvailable();

            if (LOG.isDebugEnabled()) {
                LOG.debug("formatValue index=0 available=" + available);
            }

            if (available == false) {
                return null;
            }

            Object rowData = dataModel.getRowData();

            String var = comboGridComponent.getVar(facesContext);

            if (LOG.isDebugEnabled()) {
                LOG.debug("formatValue rowData='" + rowData + "' var='" + var
                        + "'");
            }

            if (var == null) {
                throw new FacesException("Var attribute is null !");
            }

            Map requestMap = facesContext.getExternalContext().getRequestMap();
            Object oldValue = requestMap.put(var, rowData);

            try {
                return formatValue(facesContext, comboGridComponent, rowData,
                        formatValues);

            } finally {
                requestMap.put(var, oldValue);
            }

        } finally {
            dataModel.setRowIndex(-1);
        }
    }

    private Map formatValue(FacesContext facesContext,
            ComboGridComponent comboGridComponent, Object rowData,
            Map formatValues) {

        Map columnValues = new HashMap();

        IColumnIterator it = comboGridComponent.listColumns();
        for (int idx = 0; it.hasNext(); idx++) {
            UIColumn column = it.next();
            if ((column instanceof ValueHolder) == false) {
                continue;
            }

            String columnId = column.getId();
            if (columnId == null) {
                continue;
            }

            Object value = ((ValueHolder) column).getValue();
            String svalue = ValuesTools.convertValueToString(value, column,
                    facesContext);

            columnValues.put(columnId, svalue);
            columnValues.put(String.valueOf(idx), svalue);
        }

        Map results = new HashMap();
        Iterator iterator = formatValues.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String valueFormat = (String) entry.getValue();
            if (valueFormat == null) {
                String labelColumnId = comboGridComponent.getLabelColumnId();
                if (labelColumnId != null) {
                    valueFormat = "{" + labelColumnId + "}";
                } else {
                    valueFormat = "{0}";
                }
            }
            results.put(key, formatMessage(valueFormat, columnValues));
        }
        return results;

    }

    private String formatMessage(String message, Map parameters) {
        StringAppender ret = new StringAppender(message.length()
                + parameters.size() * 8);

        int pos = 0;
        for (; pos < message.length();) {
            int idx = message.indexOf('{', pos);
            int idx2 = message.indexOf('\'', pos);

            if (idx2 < 0 && idx < 0) {
                ret.append(message, pos, message.length() - pos);

                return ret.toString();
            }

            if (idx2 < 0 || (idx >= 0 && idx < idx2)) {
                idx2 = message.indexOf('}', idx);
                if (idx2 < 0) {
                    throw new FacesException("Invalid expression \"" + message
                            + "\".");
                }

                ret.append(message, pos, idx - pos);

                String p = message.substring(idx + 1, idx2);

                if (p.length() > 0 && Character.isDigit(p.charAt(0))) {
                    int num = Integer.parseInt(p);
                    if (num >= 0 && num < parameters.size()) {
                        ret
                                .append((String) parameters.get(String
                                        .valueOf(num)));
                    }

                } else if (parameters.containsKey(p)) {
                    ret.append((String) parameters.get(p));
                }

                pos = idx2 + 1;
                continue;
            }

            ret.append(message, pos, idx2 - pos);

            idx = message.indexOf('\'', idx2 + 1);
            if (idx < 0) {
                throw new Error("Invalid expression \"" + parameters + "\".");
            }
            pos = idx + 1;

            if (idx == idx2 + 1) {
                ret.append('\'');

            } else {
                ret.append(message, idx2 + 1, idx - idx2 - 1);

                if (message.charAt(pos) == '\'') {
                    ret.append('\'');
                }
            }
        }

        return ret.toString();
    }

    protected UIColumn getColumn(ComboGridComponent comboGridComponent,
            String valueColumnId) {

        if (valueColumnId == null) {
            return null;
        }

        IColumnIterator columnIterator = comboGridComponent.listColumns();
        for (; columnIterator.hasNext();) {
            UIColumn column = columnIterator.next();
            if (valueColumnId.equals(column.getId()) == false) {
                continue;
            }

            return column;
        }

        return null;
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

    protected void encodeJsBodyRows(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext tableContext) {
        // On génère rien
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

    protected void encodeBodyTableEnd(IHtmlWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        // On ferme pas les DIV
    }

    public String getComponentStyleClassName(IHtmlWriter htmlWriter) {
        if (isDataGridRenderer(htmlWriter)) {
            return super.getComponentStyleClassName(htmlWriter);
        }

        return getJavaScriptClassName();
    }

    protected boolean isDataGridRenderer(IHtmlWriter htmlWriter) {
        return Boolean.TRUE.equals(htmlWriter.getComponentRenderContext()
                .getAttribute(GRID_HTML_CONTENT));
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

    protected UIColumn getRowValueColumn(IGridComponent dg) {
        ComboGridComponent dataGridComponent = (ComboGridComponent) dg;

        String valueColumnId = dataGridComponent.getValueColumnId();
        if (valueColumnId != null) {
            for (IColumnIterator it = dg.listColumns(); it.hasNext();) {
                UIColumn column = it.next();
                if (valueColumnId.equals(column.getId()) == false) {
                    continue;
                }

                return column;
            }

            throw new FacesException("Can not find column '" + valueColumnId
                    + "'.");
        }

        return null;
    }

    protected void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        encodeJsColumns(htmlWriter, gridRenderContext, GENERATE_CELL_IMAGES
                | GENERATE_CELL_TEXT | GENERATE_CELL_WIDTH);
    }

    public void encodeRowByKey(IJavaScriptWriter jsWriter,
            ComboGridRenderContext tableContext) throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();
        ComboGridComponent comboGridComponent = (ComboGridComponent) tableContext
                .getGridComponent();

        DataModel dataModel = tableContext.getDataModel();

        if (dataModel instanceof IComponentRefModel) {
            ((IComponentRefModel) dataModel).setComponent(comboGridComponent);
        }

        IFilterProperties filtersMap = tableContext.getFiltersMap();
        if (filtersMap != null) {
            if (dataModel instanceof IFiltredModel) {
                IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

                filtredDataModel.setFilter(filtersMap);
                tableContext.updateRowCount();

            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
                tableContext.updateRowCount();
            }

        } else if (dataModel instanceof IFiltredModel) {
            IFiltredModel filtredDataModel = (IFiltredModel) dataModel;

            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
            tableContext.updateRowCount();
        }

        Map columnValues = new HashMap();
        List colValues = new ArrayList();

        String rowId = null;

        comboGridComponent.setRowIndex(0);
        try {
            if (comboGridComponent.isRowAvailable() == false) {
                // No result
                jsWriter.writeMethodCall("fa_valueSelected").write(");");
                return;
            }

            UIColumn rowValueColumn = tableContext.getRowValueColumn();

            if (rowValueColumn != null) {
                Object value = ((ValueHolder) rowValueColumn).getValue();

                rowId = convertValue(facesContext, rowValueColumn, value);
            }

            IColumnIterator it = comboGridComponent.listColumns();
            for (int idx = 0; it.hasNext(); idx++) {
                UIColumn column = it.next();
                if ((column instanceof ValueHolder) == false) {
                    continue;
                }

                String columnId = column.getId();
                if (columnId == null) {
                    continue;
                }

                Object value = ((ValueHolder) column).getValue();
                String svalue = ValuesTools.convertValueToString(value, column,
                        facesContext);

                columnValues.put(columnId, svalue);
                columnValues.put(String.valueOf(idx), svalue);
                colValues.add(columnId);
            }

        } finally {
            comboGridComponent.setRowIndex(-1);
        }

        String valueFormat = comboGridComponent.getValueFormat(facesContext);
        if (valueFormat == null) {
            String labelColumnId = comboGridComponent.getLabelColumnId();
            if (labelColumnId != null) {
                valueFormat = "{" + labelColumnId + "}";
            } else {
                valueFormat = "{0}";
            }
        }

        jsWriter.writeMethodCall("fa_valueSelected").writeString(rowId).write(
                ',').writeString(formatMessage(valueFormat, columnValues))
                .write(',');

        IObjectLiteralWriter objWriter = jsWriter.writeObjectLiteral(false);
        for (Iterator it = colValues.iterator(); it.hasNext();) {
            String colId = (String) it.next();

            objWriter.writeProperty(colId).writeString(
                    (String) columnValues.get(colId));
        }

        objWriter.end().writeln(");");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        ComboGridComponent comboGridComponent = (ComboGridComponent) component;

        FacesContext facesContext = context.getFacesContext();

        Object convertedSelectedValue = null;
        String selectedValue = componentData.getStringProperty("selected");
        if (selectedValue != null) {
            UIComponent converterComponent = getColumn(comboGridComponent,
                    comboGridComponent.getValueColumnId(facesContext));

            convertedSelectedValue = ValuesTools.convertStringToValue(
                    facesContext, converterComponent, selectedValue, false);
        }

        Object old = comboGridComponent.getSelectedValue(facesContext);

        if (convertedSelectedValue != old
                && (old == null || old.equals(convertedSelectedValue) == false)) {
            comboGridComponent.setSelectedValue(convertedSelectedValue);

            component.queueEvent(new PropertyChangeEvent(component,
                    Properties.SELECTED_VALUE, old, convertedSelectedValue));
        }
    }

    public String computeSubInputClientId(IRenderContext renderContext,
            UIComponent component, String clientId) {
        return clientId + INPUT_ID_SUFFIX;
    }

    protected void addUnlockProperties(Set unlockedProperties) {
        super.addUnlockProperties(unlockedProperties);

        unlockedProperties.add("selectedValue");
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
