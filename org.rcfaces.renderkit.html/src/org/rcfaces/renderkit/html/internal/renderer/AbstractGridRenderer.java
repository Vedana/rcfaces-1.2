/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AdditionalInformationComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.IAdditionalInformationCardinalityCapability;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.IAutoFilterCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.capability.IClientFullStateCapability;
import org.rcfaces.core.component.capability.IDragAndDropEffects;
import org.rcfaces.core.component.capability.IDraggableCapability;
import org.rcfaces.core.component.capability.IDroppableCapability;
import org.rcfaces.core.component.capability.IEmptyDataMessageCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.IPreferencesCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import org.rcfaces.core.component.capability.IShowValueCapability;
import org.rcfaces.core.component.capability.ISortManagerCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITabIndexCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.capability.IWidthCapability;
import org.rcfaces.core.component.capability.IWidthRangeCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.capability.IAdditionalInformationComponent;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import org.rcfaces.core.internal.capability.ICheckRangeComponent;
import org.rcfaces.core.internal.capability.IDroppableGridComponent;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.capability.IPreferencesSettings;
import org.rcfaces.core.internal.capability.ISelectionRangeComponent;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.component.UIData2;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.listener.IServerActionListener;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IEventData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.internal.tools.GridServerSort;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.core.lang.provider.ICheckProvider;
import org.rcfaces.core.model.IComponentRefModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.core.preference.GridPreferences;
import org.rcfaces.core.preference.IComponentPreferences;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlRenderContext;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractGridRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractGridRenderer.class);

    protected static final String SORT_SERVER_COMMAND = "f_grid.Sort_Server";

    private static final String TABLE = "_table";

    private static final String TITLE_ROW = "_title";

    private static final String TITLE_CELL = "_tcell";

    private static final String TITLE_IMAGE = "_timage";

    private static final String TITLE_TTEXT = "_ttext";

    private static final String TITLE_STEXT = "_stext";

    private static final String TABLE_BODY = "_table_tbody";

    private static final String TABLE_CONTEXT = "camelia.table.context";

    private static final String SCROLLBARS_PROPERTY = "camelia.scrollbars";

    protected static final boolean ENABLE_SERVER_REQUEST = true;

    protected static final int[] EMPTY_INDEXES = new int[0];

    protected static final String NULL_VALUE = "*$& NULL VALUE *$& O.O.";

    protected static final boolean ALLOCATE_ROW_STRINGS = true;

    private static final int TEXT_RIGHT_PADDING = 4;

    private static final int TEXT_LEFT_PADDING = 4;

    private static final int SORT_PADDING = 18;

    private static final String GRID_MAIN_STYLE_CLASS = "f_grid";

    protected static final int GENERATE_CELL_STYLE_CLASS = 0x0001;

    protected static final int GENERATE_CELL_IMAGES = 0x0002;

    protected static final int GENERATE_CELL_TEXT = 0x0004;

    protected static final int GENERATE_CELL_WIDTH = 0x0008;

    private static final String ADDITIONAL_INFORMATIONS_RENDER_CONTEXT_STATE = "org.rcfaces.html.AI_CONTEXT";

    private static final String DATA_BODY_SCROLL_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "dataBody_scroll";

    private static final String DATA_TITLE_SCROLL_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "dataTitle_scroll";

    private static final String EMPTY_DATA_MESSAGE_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "emptyDataMessage";

    private static final String DATA_TABLE_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "dataTable";

    private static final String FIXED_HEADER_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "fixedHeader";

    private static final String FIXED_FAKE_COLUMN_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "fakeCol";

    private static final String TITLE_TTEXT_ID_SUFFIX = ""
            + UINamingContainer.SEPARATOR_CHAR
            + UINamingContainer.SEPARATOR_CHAR + "text";

    protected static final int GRID_BODY_BORDER_SIZE_IN_PIXEL = 2;

    private static final int GRID_HEADER_SCROLLBAR_WIDTH = 16;

    protected static final String GRID_WRAP_CLASSNAME = "f_grid_wrap";

    /*
     * private static final String FIXED_FAKE_CELL_ID_SUFFIX = "" +
     * UINamingContainer.SEPARATOR_CHAR + UINamingContainer.SEPARATOR_CHAR +
     * "fakeCell";
     */

    public String getComponentStyleClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS;
    }

    protected final AbstractGridRenderContext getGridRenderContext(
            IHtmlComponentRenderContext componentRenderContext) {
        AbstractGridRenderContext gridRenderContext = (AbstractGridRenderContext) componentRenderContext
                .getAttribute(TABLE_CONTEXT);

        if (gridRenderContext != null) {
            return gridRenderContext;
        }

        gridRenderContext = createTableContext(componentRenderContext);
        componentRenderContext.setAttribute(TABLE_CONTEXT, gridRenderContext);

        return gridRenderContext;
    }

    public void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            IJavaScriptRenderContext javaScriptRenderContext) {
        super.addRequiredJavaScriptClassNames(writer, javaScriptRenderContext);

        IGridComponent dataGridComponent = (IGridComponent) writer
                .getComponentRenderContext().getComponent();

        AbstractGridRenderContext gridRenderContext = getGridRenderContext(writer
                .getHtmlComponentRenderContext());

        if (dataGridComponent instanceof IMenuCapability) {
            IMenuIterator menuIterator = ((IMenuCapability) dataGridComponent)
                    .listMenus();
            if (menuIterator.hasNext()) {
                javaScriptRenderContext.appendRequiredClass(
                        JavaScriptClasses.GRID, "menu");
            }
        }

        boolean ajax = needAjaxJavaScriptClasses(writer, dataGridComponent);

        if (ajax == false) {
            // On a des colonnes triables coté serveur ?
            int columnCount = gridRenderContext.getColumnCount();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Object sort = gridRenderContext.getSortCommand(columnIndex);
                if (sort == null) {
                    continue;
                }

                if (sort instanceof IServerActionListener) {
                    ajax = true;
                    break;
                }

                if (gridRenderContext.getSortClientSide(columnIndex) == false) {
                    ajax = true;
                    break;
                }
                if (sort instanceof String) {
                    if (((String) sort).trim().equals(SORT_SERVER_COMMAND)) {
                        ajax = true;
                        break;
                    }
                }
            }
        }

        if (gridRenderContext.hasAdditionalInformations()) {
            ajax = true;
            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.GRID,
                    "additional");

            if (needAdditionalInformationContextState()) {
                IHtmlRenderContext htmlRenderContext = writer
                        .getHtmlComponentRenderContext().getHtmlRenderContext();

                Object state = htmlRenderContext.saveState(htmlRenderContext
                        .getFacesContext());

                if (state != null) {
                    String contentType = htmlRenderContext.getFacesContext()
                            .getResponseWriter().getContentType();

                    ((UIComponent) dataGridComponent).getAttributes().put(
                            ADDITIONAL_INFORMATIONS_RENDER_CONTEXT_STATE,
                            new Object[] { state, contentType });
                }
            }
        }

        if (ajax) {
            addAjaxRequiredClasses(javaScriptRenderContext);
        }

        if (dataGridComponent instanceof ISortManagerCapability) {
            String sortManager = ((ISortManagerCapability) dataGridComponent)
                    .getSortManager();
            if (sortManager != null && sortManager.indexOf('(') < 0) {
                if ("dialog".equals(sortManager)) {
                    javaScriptRenderContext.appendRequiredClass(
                            "f_columnSortDialog", null);
                }
            }
        }

        if (((dataGridComponent instanceof IDraggableCapability) && ((IDraggableCapability) dataGridComponent)
                .isDraggable())
                || ((dataGridComponent instanceof IDroppableCapability) && ((IDroppableCapability) dataGridComponent)
                        .isDroppable())) {
            javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.GRID,
                    "dnd");
        }
    }

    protected void addAjaxRequiredClasses(
            IJavaScriptRenderContext javaScriptRenderContext) {
        javaScriptRenderContext.appendRequiredClass(JavaScriptClasses.GRID,
                "ajax");
    }

    protected boolean needAjaxJavaScriptClasses(IHtmlWriter writer,
            IGridComponent dataGridComponent) {
        if (dataGridComponent.getRows() > 0) {
            return true;
        }

        DataModel dataModel = dataGridComponent.getDataModelValue();
        IFiltredModel filtredModel = (IFiltredModel) getDataModelAdapter(IFiltredModel.class, dataModel);
        if (filtredModel != null) {
            return true;
        }

        return false;
    }

    public Object[] getAdditionalInformationsRenderContextState(
            IGridComponent component) {
        return (Object[]) ((UIComponent) component).getAttributes().get(
                ADDITIONAL_INFORMATIONS_RENDER_CONTEXT_STATE);
    }

    protected boolean needAdditionalInformationContextState() {
        return false;
    }

    protected String encodeAdditionalInformation(IJavaScriptWriter jsWriter,
            AdditionalInformationComponent additionalInformationComponent)
            throws WriterException {

        CharArrayWriter writer = new CharArrayWriter(8000);

        encodeAdditionalInformation(jsWriter.getFacesContext(), writer,
                (IGridComponent) jsWriter.getComponentRenderContext()
                        .getComponent(), additionalInformationComponent,
                jsWriter.getResponseCharacterEncoding());

        writer.close();

        return writer.toString();
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {
        // Pas de rendu des enfants !
        // super.encodeChildren(facesContext, component);
    }

    protected void encodeAdditionalInformation(FacesContext facesContext,
            Writer writer, IGridComponent component,
            AdditionalInformationComponent additionalInformationComponent,
            String responseCharacterEncoding) throws WriterException {

        ResponseWriter oldResponseWriter = facesContext.getResponseWriter();
        ResponseWriter newResponseWriter;
        ResponseStream oldResponseStream = null;
        if (oldResponseWriter == null) {
            // Appel AJAX pour un TRI par exemple ... (ou changement de page)

            oldResponseStream = facesContext.getResponseStream();

            Object states[] = getAdditionalInformationsRenderContextState(component);

            newResponseWriter = facesContext.getRenderKit()
                    .createResponseWriter(writer, (String) states[1],
                            responseCharacterEncoding);

            HtmlRenderContext.restoreRenderContext(facesContext, states[0],
                    true);

        } else {
            newResponseWriter = oldResponseWriter.cloneWithWriter(writer);
        }

        facesContext.setResponseWriter(newResponseWriter);
        try {
            ComponentTools.encodeRecursive(facesContext,
                    additionalInformationComponent);

        } finally {
            if (oldResponseWriter != null) {
                facesContext.setResponseWriter(oldResponseWriter);
            }

            if (oldResponseStream != null) {
                facesContext.setResponseStream(oldResponseStream);
            }
        }
    }

    public void renderAdditionalInformation(
            IHtmlRenderContext htmlRenderContext, Writer writer,
            IGridComponent gridComponent, String responseCharacterEncoding,
            String rowValue2, String rowIndex) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) htmlRenderContext
                .getComponentWriter();

        // On prepare le DataModel !
        AbstractGridRenderContext tableContext = getGridRenderContext(htmlWriter
                .getHtmlComponentRenderContext());
        DataModel dataModel = tableContext.getDataModel();
        
        IComponentRefModel componentRefModel = (IComponentRefModel) 
        	getDataModelAdapter(IComponentRefModel.class, dataModel);
        
        if (componentRefModel != null) {
        	componentRefModel.setComponent((UIComponent) gridComponent);
        }

        IFilterProperties filtersMap = tableContext.getFiltersMap();
        IFiltredModel filtredDataModel = (IFiltredModel)
        	getDataModelAdapter(IFiltredModel.class, dataModel);
        
        if (filtersMap != null) {
            if (filtredDataModel != null) {
                filtredDataModel.setFilter(filtersMap);
            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
            }

        } else if (filtredDataModel != null) {
            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
        }

        int translatedRowIndex = Integer.parseInt(rowIndex);

        ISortedComponent sortedComponents[] = tableContext
                .listSortedComponents();
        ISortedDataModel sortedDataModel = (ISortedDataModel)
        
        	getDataModelAdapter(ISortedDataModel.class, dataModel);
        if (sortedComponents != null && sortedComponents.length > 0) {
            if (sortedDataModel != null) {
            	sortedDataModel.setSortParameters(
                        (UIComponent) gridComponent, sortedComponents);
            } else {
                // Il faut faire le tri à la main !

                int sortTranslations[] = GridServerSort
                        .computeSortedTranslation(
                                htmlRenderContext.getFacesContext(),
                                gridComponent, dataModel, sortedComponents);

                if (sortTranslations != null) {
                    translatedRowIndex = sortTranslations[translatedRowIndex];
                }
            }
        } else if (sortedDataModel != null) {
	        // Reset des parametres de tri !
	    	sortedDataModel.setSortParameters(
	                (UIComponent) gridComponent, null);
        }
        

        gridComponent.setRowIndex(translatedRowIndex);
        try {
            if (gridComponent.isRowAvailable() == false) {
                return;
            }

            AdditionalInformationComponent additionalInformationComponents[] = tableContext
                    .listAdditionalInformations();

            AdditionalInformationComponent additionalInformationComponent = null;

            for (int i = 0; i < additionalInformationComponents.length; i++) {
                AdditionalInformationComponent add = additionalInformationComponents[i];

                if (add.isRendered() == false) {
                    continue;
                }

                additionalInformationComponent = add;
                break;
            }

            if (additionalInformationComponent == null) {
                return;
            }

            encodeAdditionalInformation(htmlRenderContext.getFacesContext(),
                    writer, gridComponent, additionalInformationComponent,
                    responseCharacterEncoding);

        } finally {
            gridComponent.setRowIndex(-1);
        }
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        UIComponent dataGridComponent = componentRenderContext.getComponent();

        if (dataGridComponent instanceof IBorderCapability) {
            if (((IBorderCapability) dataGridComponent).isBorder() == false) {
                cssWriter.writeBorderStyle(ICssWriter.NONE);
            }
        }
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        encodeGrid((IHtmlWriter) writer);
    }

    protected void encodeGrid(IHtmlWriter htmlWriter) throws WriterException {

        IHtmlComponentRenderContext componentRenderContext = htmlWriter
                .getHtmlComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IGridComponent gridComponent = (IGridComponent) componentRenderContext
                .getComponent();

        if (gridComponent instanceof IPreferencesCapability) {
            IPreferencesCapability preferenceCapability = (IPreferencesCapability) gridComponent;

            IComponentPreferences preference = preferenceCapability
                    .getPreferences();
            if (preference != null) {
                preference.loadPreferences(facesContext,
                        (UIComponent) gridComponent);
            }
        }

        AbstractGridRenderContext gridRenderContext = getGridRenderContext(componentRenderContext);

        htmlWriter.startElement(IHtmlWriter.DIV, (UIComponent) gridComponent);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter, getCssStyleClasses(htmlWriter),
                ~CSS_SIZE_MASK);

        if (gridRenderContext.hasAdditionalInformations()) {
            int cardinality = gridRenderContext
                    .getAdditionalInformationCardinality();
            if (cardinality == 0) {
                cardinality = IAdditionalInformationCardinalityCapability.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:additionalInformationCardinality",
                    cardinality);

            int cfs = gridRenderContext.getClientAdditionalFullState();
            if (cfs != IClientFullStateCapability.DEFAULT_CLIENT_FULL_STATE) {
                htmlWriter.writeAttribute("v:clientAdditionalFullState", cfs);
            }
        }

        if (gridRenderContext.isSelectable()) {
            htmlWriter.writeAttribute("v:selectionCardinality",
                    gridRenderContext.getSelectionCardinality());

            int cfs = gridRenderContext.getClientSelectionFullState();
            if (cfs != IClientFullStateCapability.DEFAULT_CLIENT_FULL_STATE) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", cfs);
            }
        }

        if (gridRenderContext.isCheckable()) {
            htmlWriter.writeAttribute("v:checkCardinality",
                    gridRenderContext.getCheckCardinality());

            if (gridRenderContext.getCheckCardinality() == ICardinality.ONEMANY_CARDINALITY) {
                int checkedCount = ((ICheckProvider) gridComponent)
                        .getCheckedValuesCount();

                if (checkedCount > 0) {
                    htmlWriter.writeAttribute("v:checkedCount", checkedCount);
                }
            }

            int cfs = gridRenderContext.getClientCheckFullState();
            if (cfs != IClientFullStateCapability.DEFAULT_CLIENT_FULL_STATE) {
                htmlWriter.writeAttribute("v:clientCheckFullState", cfs);
            }
        }

        if (gridComponent instanceof IReadOnlyCapability) {
            if (((IReadOnlyCapability) gridComponent).isReadOnly()) {
                htmlWriter.writeAttribute("v:readOnly", true);
            }
        }

        String emptyDataMessage = null;

        if (gridComponent instanceof IEmptyDataMessageCapability) {
            emptyDataMessage = ((IEmptyDataMessageCapability) gridComponent)
                    .getEmptyDataMessage();
            if (emptyDataMessage != null) {
                emptyDataMessage = ParamUtils.formatMessage(
                        (UIComponent) gridComponent, emptyDataMessage);

                htmlWriter.writeAttribute("v:emptyDataMessage",
                        emptyDataMessage);
            }
        }

        if (gridRenderContext.isDisabled()) {
            htmlWriter.writeAttribute("v:disabled", true);
        }

        if (gridComponent instanceof IRequiredCapability) {
            if (((IRequiredCapability) gridComponent).isRequired()) {
                htmlWriter.writeAttribute("v:required", true);
            }
        }
        
        boolean wheelSelection = gridRenderContext.isWheelSelection();
        if (wheelSelection == false) {
        	htmlWriter.writeAttribute("v:wheelSelection", wheelSelection);
        }

        String alertLoadingMessage = gridRenderContext.getAlertLoadingMessage();
        if (alertLoadingMessage != null) {
        	htmlWriter.writeAttribute("v:alertLoadingMessage", alertLoadingMessage);
        }

        boolean headerVisible = gridRenderContext.isHeaderVisible();
        if (headerVisible == false) {
            htmlWriter.writeAttribute("v:headerVisible", false);
        }

        String sortManager = gridRenderContext.getSortManager();
        if (sortManager != null) {
            htmlWriter.writeAttribute("v:sortManager", sortManager);
        }

        DataModel dataModel = gridRenderContext.getDataModel();
        IFiltredModel filtredDataModel = (IFiltredModel) getDataModelAdapter(IFiltredModel.class, dataModel);
        if (filtredDataModel != null) {
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

        int rows = gridRenderContext.getRows();
        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }

        int rowCount = gridRenderContext.getRowCount();
        if (rowCount >= 0) {
            htmlWriter.writeAttribute("v:rowCount", rowCount);
        }

        int first = gridRenderContext.getFirst();
        if (first > 0) {
            if (rowCount < 0 || first <= rowCount) {
                htmlWriter.writeAttribute("v:first", first);
            } else {
                // reset First to 0 if rowCount is smaller than first
                gridRenderContext.resetFirst();
            }
        }

        if (gridRenderContext.isPaged() == false) {
            htmlWriter.writeAttribute("v:paged", false);
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

        if (getSortPadding() != SORT_PADDING) {
            htmlWriter.writeAttribute("v:sortPadding", getSortPadding());
        }

        if (gridRenderContext.isDraggable()) {
            IDraggableCapability draggableCapability = (IDraggableCapability) gridComponent;

            int dragEffects = draggableCapability.getDragEffects();

            if (dragEffects <= IDragAndDropEffects.UNKNOWN_DND_EFFECT) {
                dragEffects = IDragAndDropEffects.DEFAULT_DND_EFFECT;
            }
            htmlWriter.writeAttribute("v:dragEffects", dragEffects);

            String dragTypes[] = draggableCapability.getDragTypes();
            if (dragTypes != null && dragTypes.length > 0) {
                htmlWriter.writeAttribute("v:dragTypes",
                        HtmlTools.serializeDnDTypes(dragTypes));
            } else {
                htmlWriter.writeAttribute("v:dragTypes", "x-RCFaces/row");
            }
        }

        if (gridRenderContext.isDroppable()) {
            IDroppableCapability droppableCapability = (IDroppableCapability) gridComponent;

            int dropEffects = droppableCapability.getDropEffects();

            if (dropEffects <= IDragAndDropEffects.UNKNOWN_DND_EFFECT) {
                dropEffects = IDragAndDropEffects.DEFAULT_DND_EFFECT;
            }
            htmlWriter.writeAttribute("v:dropEffects", dropEffects);

            String dropTypes[] = droppableCapability.getDropTypes();
            if (dropTypes != null && dropTypes.length > 0) {
                htmlWriter.writeAttribute("v:dropTypes",
                        HtmlTools.serializeDnDTypes(dropTypes));
            } else {
                htmlWriter.writeAttribute("v:dropTypes", "x-RCFaces/row");
            }

            if (gridComponent instanceof IDroppableGridComponent) {
                if (((IDroppableGridComponent) gridComponent).isBodyDroppable()) {
                    htmlWriter.writeAttribute("v:bodyDroppable", true);
                }
            }
        }

        writeGridComponentAttributes(htmlWriter, gridRenderContext,
                gridComponent);

        int gridHeight = gridRenderContext.getGridHeight();
        int gridWidth = gridRenderContext.getGridWidth();
        if (gridWidth <= 0) {
            gridWidth = gridRenderContext.getColumnWidthTotalSize();

            if (gridWidth > 0) {
                // Border DIV // Border TABLE
                gridWidth += GRID_BODY_BORDER_SIZE_IN_PIXEL
                        + GRID_BODY_BORDER_SIZE_IN_PIXEL;

                if (gridRenderContext.hasScrollBars() && headerVisible) {
                    gridWidth += GRID_HEADER_SCROLLBAR_WIDTH;
                }
            }
        }

        if (gridHeight > 0 || gridWidth > 0) {
            /*
             * writer.setComponentRenderAttribute(SCROLLBARS_PROPERTY,
             * Boolean.TRUE);
             */

            ICssWriter cssWriter = htmlWriter.writeStyle();
            if (gridWidth > 0) {
                cssWriter.writeWidthPx(gridWidth);
            }
            if (gridHeight > 0) {
                cssWriter.writeHeightPx(gridHeight);
            }
        }

        boolean resizable = gridRenderContext.isResizable();
        int totalResize = gridRenderContext.getColumnWidthTotalSize();

        if (resizable) {
            htmlWriter.writeAttribute("v:resizable", true);
        }

        if (gridRenderContext.hasScrollBars() == false) {
            htmlWriter.writeAttribute("v:sb", false);
        }

        if (serverTitleGeneration() == false) {
            htmlWriter
                    .writeAttribute("v:sb", gridRenderContext.hasScrollBars());
            return;
        }

        if (gridRenderContext.hasScrollBars() == false
                || headerVisible == false) {
            htmlWriter.writeStyle().writeOverflow(ICssWriter.AUTO);
        }

        if (emptyDataMessage != null) {
            htmlWriter.startElement(IHtmlWriter.DIV);
            htmlWriter.writeId(getEmptyDataMessageId(htmlWriter));
            htmlWriter.writeClass(getEmptyDataMessageClassName(htmlWriter));

            htmlWriter.writeText(emptyDataMessage);

            htmlWriter.endElement(IHtmlWriter.DIV);
        }

        int w = gridWidth - GRID_BODY_BORDER_SIZE_IN_PIXEL;
        // boolean mainComponentScrollable = true;
        if (gridRenderContext.hasScrollBars()) {

            if (headerVisible) {
                htmlWriter.startElement(IHtmlWriter.DIV);
                htmlWriter.writeId(getDataTitleScrollId(htmlWriter));
                htmlWriter.writeClass(getDataTitleScrollClassName(htmlWriter));
                if (w > 0) {
                    htmlWriter.writeStyle().writeWidthPx(w);
                }

                encodeFixedHeader(htmlWriter, gridRenderContext, gridWidth,
                        true);
                htmlWriter.endElement(IHtmlWriter.DIV);

                // Finalement le BODY aussi n'a pas de DIV !

                htmlWriter.startElement(IHtmlWriter.DIV);
                htmlWriter.writeId(getDataBodyScrollId(htmlWriter));
                htmlWriter.writeClass(getDataBodyScrollClassName(htmlWriter));

                ICssWriter cssWriter = htmlWriter.writeStyle(32);

                if (gridHeight > 0) {
                    int gh = gridHeight - 2; // Border !
                    if (headerVisible) {
                        gh -= getTitleHeight();
                    }

                    cssWriter.writeHeightPx(gh);
                }
                if (w > 0) {
                    cssWriter.writeWidthPx(w);
                }

                // mainComponentScrollable = false;
            }
        } else {
            // Pas de scroll vertical ...
            if (headerVisible) {
                htmlWriter.startElement(IHtmlWriter.DIV);
                htmlWriter.writeId(getDataTitleScrollId(htmlWriter));
                htmlWriter.writeClass(getDataTitleScrollClassName(htmlWriter));
                if (w > 0) {
                    htmlWriter.writeStyle().writeWidthPx(w);
                }
                encodeFixedHeader(htmlWriter, gridRenderContext, gridWidth,
                        false);
                htmlWriter.endElement(IHtmlWriter.DIV);
            }
        }

        htmlWriter.startElement(IHtmlWriter.TABLE);
        htmlWriter.writeId(getDataTableId(htmlWriter));
        htmlWriter.writeClass(getDataTableClassName(htmlWriter,
                gridRenderContext.isDisabled()));

        // Pas de support CSS de border-spacing pour IE: on est oblig� de le
        // cod� en HTML ...
        htmlWriter.writeCellSpacing(0);

        int tableWidth = gridRenderContext.getColumnWidthTotalSize();
        if (tableWidth > 0) {
            if (gridWidth > 0 && gridWidth > tableWidth) {
                // tableWidth = -1;
            }
        }

        if (resizable) {
            // 2 htmlWriter.writeAttribute("width", totalResize);
            htmlWriter.writeStyle().writeWidthPx(totalResize);

        } else if (tableWidth > 0) {
            htmlWriter.writeWidth(tableWidth);

        } else {
            htmlWriter.writeWidth("100%");
        }

        encodeHeader(htmlWriter, gridRenderContext);

        htmlWriter.startElement(IHtmlWriter.TBODY);
        htmlWriter.writeClass(getTitleTableBodyClassName(htmlWriter));

        encodeBodyBegin(htmlWriter, gridRenderContext);
    }

    protected boolean serverTitleGeneration() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        AbstractGridRenderContext tableContext = getGridRenderContext(((IHtmlWriter) writer)
                .getHtmlComponentRenderContext());

        encodeBodyEnd((IHtmlWriter) writer, tableContext);

        super.encodeEnd(writer);
    }

    protected void writeGridComponentAttributes(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, IGridComponent dg)
            throws WriterException {
        if (dg instanceof ITabIndexCapability) {
            ITabIndexCapability tic = (ITabIndexCapability) dg;
            Integer tabIndex = tic.getTabIndex();
            if (tabIndex != null) {
                htmlWriter.writeAttribute("v:tabIndex", tabIndex.intValue());
            }
        }

    }

    protected String getWAIRole() {
        return IAccessibilityRoles.GRID;
    }

    protected String getDataBodyScrollClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + "_dataBody_scroll";
    }

    protected String getDataBodyScrollId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + DATA_BODY_SCROLL_ID_SUFFIX;
    }

    protected String getDataTableId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + DATA_TABLE_ID_SUFFIX;
    }

    protected String getDataTableClassName(IHtmlWriter htmlWriter,
            boolean disabled) {
        String className = GRID_MAIN_STYLE_CLASS + TABLE;
        if (disabled) {
            className += " " + className + "_disabled";
        }

        return className;
    }

    protected String getEmptyDataMessageClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + "_empty_data_message";
    }

    protected String getEmptyDataMessageId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + EMPTY_DATA_MESSAGE_ID_SUFFIX;
    }

    protected String getDataTitleScrollClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + "_dataTitle_scroll";
    }

    protected String getDataTitleScrollId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + DATA_TITLE_SCROLL_ID_SUFFIX;
    }

    protected int getTitleHeight() {
        return 20;
    }

    protected void encodeFixedHeader(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, int dataGridWidth,
            boolean fixedHeader) throws WriterException {

        htmlWriter.startElement(IHtmlWriter.UL);
        htmlWriter.writeId(getFixedHeaderTableId(htmlWriter));

        if (fixedHeader) {
            htmlWriter.writeClass(getFixedHeaderTableClassName(htmlWriter));
        } else {
            htmlWriter.writeClass(getTitleRowClassName(htmlWriter));
        }

        UIColumn columns[] = tableContext.listColumns();

        /*
         * htmlWriter.writeCellPadding(0); htmlWriter.writeCellSpacing(0); if
         * (tableContext.isResizable()) { // OO2:
         * htmlWriter.writeAttribute("width", //
         * tableContext.getResizeTotalSize());
         * htmlWriter.writeStyle().writeWidth( tableContext.getTotalSize() +
         * "px"); }
         * 
         * for (int i = 0; i < columns.length; i++) { if
         * (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE)
         * { continue; }
         * 
         * UIColumn column = columns[i];
         * 
         * htmlWriter.startElement(IHtmlWriter.COL); String width = null; if
         * (column instanceof IWidthCapability) { width = ((IWidthCapability)
         * column).getWidth(); if (width != null) {
         * htmlWriter.writeStyle().writeWidth(getSize(width)); } }
         * 
         * htmlWriter.endElement(IHtmlWriter.COL); }
         * 
         * htmlWriter.startElement(IHtmlWriter.COL); // Fake col !
         * htmlWriter.writeId(getFakeColumnClientId(htmlWriter));
         * htmlWriter.endElement(IHtmlWriter.COL);
         * 
         * htmlWriter.startElement(IHtmlWriter.THEAD);
         * htmlWriter.startElement(IHtmlWriter.TR);
         * htmlWriter.writeClass(getTitleRowClassName(htmlWriter));
         */
        boolean first = true;
        int columnHeaderIndex = 0;
        for (int i = 0; i < columns.length; i++) {
            if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            UIColumn column = columns[i];

            encodeFixedTitleCol(htmlWriter, tableContext, column, first, i,
                    ++columnHeaderIndex);
            first = false;
        }

        if (fixedHeader) {
            htmlWriter.startElement(IHtmlWriter.LI); // Fake TD
            // htmlWriter.writeId(getFakeHeadCellClientId(htmlWriter));
            htmlWriter.writeClass("f_grid_tcell f_grid_tcell_right");
            htmlWriter.write("&nbsp;");
            htmlWriter.endElement(IHtmlWriter.LI);
        }

        /*
         * htmlWriter.startElement(IHtmlWriter.TH); // Fake TD //
         * htmlWriter.writeId(getFakeHeadCellClientId(htmlWriter));
         * htmlWriter.writeClass("f_grid_tcell"); htmlWriter.write("&nbsp;");
         * htmlWriter.endElement(IHtmlWriter.TH);
         * 
         * // Fin des titres .... htmlWriter.endElement(IHtmlWriter.TR);
         * htmlWriter.endElement(IHtmlWriter.THEAD);
         * htmlWriter.startElement(IHtmlWriter.TBODY);
         * 
         * htmlWriter.endElement(IHtmlWriter.TBODY);
         * htmlWriter.endElement(IHtmlWriter.TABLE);
         */

        htmlWriter.endElement(IHtmlWriter.UL);
    }

    /*
     * private String getFakeHeadCellClientId(IHtmlWriter htmlWriter) { return
     * htmlWriter.getComponentRenderContext().getComponentClientId() +
     * FIXED_FAKE_CELL_ID_SUFFIX; }
     */

    private String getFakeColumnClientId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + FIXED_FAKE_COLUMN_ID_SUFFIX;
    }

    private String getTitleTableBodyClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + TABLE_BODY;
    }

    protected String getTitleRowClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + TITLE_ROW;
    }

    protected String getFixedHeaderTableClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + "_fttitle";
    }

    protected String getFixedHeaderTableId(IHtmlWriter htmlWriter) {
        return htmlWriter.getComponentRenderContext().getComponentClientId()
                + FIXED_HEADER_ID_SUFFIX;
    }

    private void encodeFixedTitleCol(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, UIColumn column,
            boolean first, int columnIndex, int columnHeaderIndex)
            throws WriterException {

        /*
         * htmlWriter.startElement(IHtmlWriter.LI);
         * 
         * htmlWriter.writeId(column.getClientId(htmlWriter
         * .getComponentRenderContext().getFacesContext()));
         * 
         * String className = getTitleCellClassName(htmlWriter, column, first,
         * tableContext.isDisabled()); htmlWriter.writeClass(className);
         * 
         * if (column instanceof IToolTipCapability) { String toolTip =
         * ((IToolTipCapability) column).getToolTipText(); if (toolTip != null)
         * { toolTip = ParamUtils.formatMessage(column, toolTip);
         * 
         * htmlWriter.writeTitle(toolTip); } }
         */

        encodeTitleCell(htmlWriter, tableContext, column, columnIndex,
                columnHeaderIndex);

        /*
         * htmlWriter.endElement(IHtmlWriter.LI);
         */
    }

    protected String getTitleCellClassName(IHtmlWriter htmlWriter,
            UIColumn column, boolean firstColumn, boolean disabled) {
        String mainClassName = GRID_MAIN_STYLE_CLASS + TITLE_CELL;

        StringAppender sa = null;

        if (column instanceof IStyleClassCapability) {
            String cl = ((IStyleClassCapability) column).getStyleClass();

            if (cl != null) {
                if (sa == null) {
                    sa = new StringAppender(mainClassName);
                }
                sa.append(' ').append(cl);
            }
        }

        /*
         * Ca sert à qq chose ? if (firstColumn) { if (sa == null) { sa = new
         * StringAppender(mainClassName); } sa.append('
         * ').append(mainClassName).append("_left"); }
         */

        if (disabled) {
            if (sa == null) {
                sa = new StringAppender(mainClassName);
            }
            sa.append(' ').append(mainClassName).append("_disabled");
        }

        if (sa != null) {
            return sa.toString();
        }

        return mainClassName;
    }

    protected void encodeTitleCellBody(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, UIColumn column,
            int columnIndex) throws WriterException {
        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement(IHtmlWriter.DIV);
        htmlWriter.writeClass(getTitleDivContainerClassName(htmlWriter));

        int width = tableContext.getColumnWidthInPixel(columnIndex);

        if (width > 0) {
            htmlWriter.writeStyle().writeWidthPx(
                    width - getTextRightPadding() - getTextLeftPadding());

        } else {
            String literalWidth = tableContext.getColumnWidth(columnIndex);
            if (literalWidth != null) {
                htmlWriter.writeStyle().writeWidth(literalWidth);
            }
        }

        String columnTagName = IHtmlWriter.DIV;

        boolean command = false;
        if (tableContext.getSortCommand(columnIndex) != null) {
            columnTagName = IHtmlWriter.A;
            command = true;
        }
        htmlWriter.startElement(columnTagName);

        boolean sorted = false;

        if (command) {
            if (columnTagName == IHtmlWriter.A) {
                htmlWriter.writeHRef(IHtmlWriter.JAVASCRIPT_VOID);
            }

            UIComponent component = htmlWriter.getComponentRenderContext()
                    .getComponent();
            if (component instanceof ITabIndexCapability) {
                Integer tabIndex = ((ITabIndexCapability) component)
                        .getTabIndex();

                if (tabIndex != null) {
                    htmlWriter.writeTabIndex(tabIndex.intValue());
                }
            }

            if (column instanceof IOrderCapability) {
                String altText = null;

                ISortedComponent sortedComponents[] = tableContext
                        .listSortedComponents();
                for (int i = 0; i < sortedComponents.length; i++) {
                    if (sortedComponents[i].getComponent() != column) {
                        continue;
                    }

                    if (((IOrderCapability) column).isAscending()) {
                        altText = getResourceBundleValue(htmlWriter,
                                "f_grid.DESCENDING_SORT");
                    } else {
                        altText = getResourceBundleValue(htmlWriter,
                                "f_grid.ASCENDING_SORT");
                    }

                    sorted = true;
                    break;
                }

                if (altText == null) {
                    altText = getResourceBundleValue(htmlWriter,
                            "f_grid.NO_SORT");
                }

                htmlWriter.writeAlt(altText);
            }

        }

        htmlWriter.writeClass(getTitleDivTextClassName(htmlWriter, column));
        htmlWriter.writeId(getTitleDivTextId(htmlWriter, column));

        String halign = null;
        if (column instanceof IAlignmentCapability) {
            halign = ((IAlignmentCapability) column).getAlignment();
        }
        if (halign == null) {
            halign = "left";
        }

        htmlWriter.writeAlign(halign);

        if (width > 0) { // SORTER

            int w = width - getTextRightPadding() - getTextLeftPadding();

            if (sorted) {
                w -= getSortPadding();
            }

            if (w < 0) {
                w = 0;
            }

            htmlWriter.writeStyle().writeWidthPx(w);
        }

        if (column instanceof IImageAccessorsCapability) {
            IContentAccessors contentAccessors = ((IImageAccessorsCapability) column)
                    .getImageAccessors(facesContext);
            if (contentAccessors instanceof IImageAccessors) {
                IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;

                IContentAccessor imageAccessor = imageAccessors
                        .getImageAccessor();
                if (imageAccessor != null) {
                    String imageURL = imageAccessor.resolveURL(facesContext,
                            null, null);

                    if (imageURL != null) {
                        htmlWriter.startElement(IHtmlWriter.IMG);
                        htmlWriter
                                .writeClass(getTitleImageClassName(htmlWriter));

                        String disabledImageURL = null;

                        if (tableContext.isDisabled()) {
                            if (imageAccessors instanceof IStatesImageAccessors) {
                                IStatesImageAccessors is = (IStatesImageAccessors) imageAccessors;

                                IContentAccessor disabledImageContentAccessor = is
                                        .getDisabledImageAccessor();
                                if (disabledImageContentAccessor != null) {
                                    disabledImageURL = disabledImageContentAccessor
                                            .resolveURL(facesContext, null,
                                                    null);
                                }
                            }
                        }

                        if (disabledImageURL != null) {
                            htmlWriter.writeSrc(disabledImageURL);

                        } else {
                            htmlWriter.writeSrc(imageURL);
                        }

                        int imageWidth = ((IImageSizeCapability) column)
                                .getImageWidth();
                        if (imageWidth > 0) {
                            htmlWriter.writeWidth(imageWidth);
                        }

                        int imageHeight = ((IImageSizeCapability) column)
                                .getImageHeight();
                        if (imageHeight > 0) {
                            htmlWriter.writeHeight(imageHeight);
                        }

                        htmlWriter.endElement(IHtmlWriter.IMG);
                    }
                }
            }
        }

        String text = null;
        if (column instanceof ITextCapability) {
            text = ((ITextCapability) column).getText();
        }
        if (text != null && text.trim().length() > 0) {
            htmlWriter.writeText(text);

        } else {
            htmlWriter.write(' ');
            htmlWriter.writeText(ISgmlWriter.NBSP);
            htmlWriter.write(' ');
        }

        htmlWriter.endElement(columnTagName);

        htmlWriter.endElement(IHtmlWriter.DIV);
    }
    
    protected int getSortPadding() {
        return SORT_PADDING;
    }

    private int getTextLeftPadding() {
        return TEXT_LEFT_PADDING;
    }

    private int getTextRightPadding() {
        return TEXT_RIGHT_PADDING;
    }

    protected String getTitleDivTextClassName(IHtmlWriter htmlWriter,
            UIColumn column) {
        return GRID_MAIN_STYLE_CLASS + TITLE_TTEXT;
    }

    protected String getTitleDivTextId(IHtmlWriter htmlWriter, UIColumn column) {
        return column.getClientId(htmlWriter.getComponentRenderContext()
                .getFacesContext()) + TITLE_TTEXT_ID_SUFFIX;
    }

    protected String getTitleDivContainerClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + TITLE_STEXT;
    }

    protected String getTitleImageClassName(IHtmlWriter htmlWriter) {
        return GRID_MAIN_STYLE_CLASS + TITLE_IMAGE;
    }

    protected void encodeJavaScript(IJavaScriptWriter htmlWriter)
            throws WriterException {
        super.encodeJavaScript(htmlWriter);

        AbstractGridRenderContext tableContext = (AbstractGridRenderContext) htmlWriter
                .getHtmlComponentRenderContext().setAttribute(TABLE_CONTEXT,
                        null);

        encodeJsHeader(htmlWriter, tableContext);
        encodeJsBody(htmlWriter, tableContext);
        encodeJsFooter(htmlWriter, tableContext);
    }

    public abstract AbstractGridRenderContext createTableContext(
            IHtmlComponentRenderContext componentRenderContext);

    protected void encodeHeader(IHtmlWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        // Le tableau n'a pas de taille et les colonnes ne sont pas retaillable

        UIColumn columns[] = gridRenderContext.listColumns();

        UIColumn dcs[] = new UIColumn[columns.length];

        int is = 0;
        for (int i = 0; i < columns.length; i++) {
            UIColumn dc = columns[i];

            if (gridRenderContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            dcs[is++] = dc;
            encodeTitleCol(htmlWriter, dc, gridRenderContext, i);
        }
    }

    private void encodeTitleCol(IHtmlWriter htmlWriter, UIColumn column,
            AbstractGridRenderContext gridRenderContext, int columnIndex)
            throws WriterException {
        htmlWriter.startElement(IHtmlWriter.COL);

        String width = gridRenderContext.getColumnWidth(columnIndex);
        if (width != null) {
            // 2: htmlWriter.writeAttribute("width", width);
            htmlWriter.writeStyle().writeWidth(getSize(width));
        }

        String halign = null;
        if (column instanceof IAlignmentCapability) {
            halign = ((IAlignmentCapability) column).getAlignment();
        }
        if (halign == null) {
            halign = "left";
        }
        htmlWriter.writeAlign(halign);

        if (column instanceof IVerticalAlignmentCapability) {
            String valign = ((IVerticalAlignmentCapability) column)
                    .getVerticalAlignment();
            if (valign != null) {
                htmlWriter.writeVAlign(valign);
            }
        }

        if (column instanceof IForegroundBackgroundColorCapability) {
            String foregroundColor = ((IForegroundBackgroundColorCapability) column)
                    .getForegroundColor();
            String backgroundColor = ((IForegroundBackgroundColorCapability) column)
                    .getBackgroundColor();

            if (foregroundColor != null || backgroundColor != null) {
                ICssWriter cssWriter = htmlWriter.writeStyle(128);
                if (foregroundColor != null) {
                    cssWriter.writeColor(foregroundColor);
                }
                if (backgroundColor != null) {
                    cssWriter.writeBackgroundColor(backgroundColor);
                }
            }
        }

        htmlWriter.endElement(IHtmlWriter.COL);
    }

    protected void encodeTitleCell(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, UIColumn column,
            int columnIndex, int columnHeaderIndex) throws WriterException {
        htmlWriter.startElement(IHtmlWriter.LI);

        htmlWriter.writeRole(IAccessibilityRoles.COLUMNHEADER);
        // au propre
        htmlWriter.writeId(getDataTableId(htmlWriter) + ":columnHeader:"
                + columnHeaderIndex);

        String thClassName = getTitleCellClassName(htmlWriter, column,
                columnIndex == 0, tableContext.isDisabled());
        htmlWriter.writeClass(thClassName);

        String halign = null;
        if (column instanceof IAlignmentCapability) {
            halign = ((IAlignmentCapability) column).getAlignment();
        }
        if (halign == null) {
            halign = "left";
        }
        htmlWriter.writeAlign(halign);

        if (column instanceof IToolTipCapability) {
            String toolTip = ((IToolTipCapability) column).getToolTipText();

            if (toolTip != null) {
                toolTip = ParamUtils.formatMessage(column, toolTip);

                htmlWriter.writeTitle(toolTip);
            }
        }

        int width = tableContext.getColumnWidthInPixel(columnIndex);
        String literalWidth = tableContext.getColumnWidth(columnIndex);
        if (width > 0) {
            htmlWriter.writeStyle().writeWidthPx(width);

        } else if (literalWidth != null) {
            htmlWriter.writeStyle().writeWidth(literalWidth);
        }

        encodeTitleCellBody(htmlWriter, tableContext, column, columnIndex);

        htmlWriter.endElement(IHtmlWriter.LI);
    }

    protected void encodeBodyBegin(IHtmlWriter htmlWriter,
            AbstractGridRenderContext data) throws WriterException {

        htmlWriter.getJavaScriptEnableMode().enableOnInit();
    }

    protected void encodeBodyEnd(IHtmlWriter writer,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
    }

    protected void encodeBodyTableEnd(IHtmlWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        if (serverTitleGeneration()) {
            htmlWriter.endElement(IHtmlWriter.TBODY);

            htmlWriter.endElement(IHtmlWriter.TABLE);

            if (gridRenderContext.hasScrollBars()
                    && gridRenderContext.isHeaderVisible()) {
                htmlWriter.endElement(IHtmlWriter.DIV);
            }
        }

        htmlWriter.endElement(IHtmlWriter.DIV);
    }

    protected void encodeJsHeader(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
    }

    protected void encodeJsBody(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {

        encodeJsColumns(htmlWriter, gridRenderContext);

        /* Si le tableau n'est pas visible ! */

        if (ENABLE_SERVER_REQUEST) {
            String interactiveComponentClientId = htmlWriter
                    .getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getCurrentInteractiveRenderComponentClientId();

            if (interactiveComponentClientId != null) {
                // Pas de donn�es si nous sommes dans un scope interactif !
                htmlWriter.writeMethodCall("f_setInteractiveShow").write('"')
                        .write(interactiveComponentClientId).writeln("\");");
                return;
            }
        }
    }

    protected abstract void encodeJsColumns(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException;

    protected void encodeJsColumns(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext gridRenderContext, int generationMask)
            throws WriterException {

        String defaultCellImageURLs[] = null;
        String defaultCellStyleClasses[][] = null;
        String defaultCellToolTipTexts[] = null;
        String defaultCellHorizontalAligments[] = null;
        String imageURLs[] = null;
        String disabledImageURLs[] = null;
        String hoverImageURLs[] = null;
        String selectedImageURLs[] = null;
        String columnStyleClasses[] = null;

        if ((generationMask & GENERATE_CELL_IMAGES) > 0) {
            defaultCellImageURLs = gridRenderContext.getDefaultCellImageURLs();
            if (defaultCellImageURLs != null) {
                defaultCellImageURLs = allocateStrings(jsWriter,
                        defaultCellImageURLs, null);
            }

            imageURLs = gridRenderContext.getCellTitleImageURLs();
            if (imageURLs != null) {
                imageURLs = allocateStrings(jsWriter, imageURLs, null);
            }

            disabledImageURLs = gridRenderContext
                    .getCellTitleDisabledImageURLs();
            if (disabledImageURLs != null) {
                disabledImageURLs = allocateStrings(jsWriter,
                        disabledImageURLs, null);
            }

            hoverImageURLs = gridRenderContext.getCellTitleHoverImageURLs();
            if (hoverImageURLs != null) {
                hoverImageURLs = allocateStrings(jsWriter, hoverImageURLs, null);
            }

            selectedImageURLs = gridRenderContext
                    .getCellTitleSelectedImageURLs();
            if (selectedImageURLs != null) {
                selectedImageURLs = allocateStrings(jsWriter,
                        selectedImageURLs, null);
            }
        }

        columnStyleClasses = gridRenderContext.getColumnStyleClasses();
        if (columnStyleClasses != null) {
            columnStyleClasses = allocateStrings(jsWriter, columnStyleClasses,
                    null);
        }

        defaultCellStyleClasses = gridRenderContext
                .getDefaultCellStyleClasses();
        if (defaultCellStyleClasses != null) {
            String s[][] = new String[defaultCellStyleClasses.length][];
            for (int i = 0; i < defaultCellStyleClasses.length; i++) {
                s[i] = allocateStrings(jsWriter, defaultCellStyleClasses[i],
                        null);
            }

            defaultCellStyleClasses = s;
        }

        defaultCellToolTipTexts = gridRenderContext
                .getDefaultCellToolTipTexts();
        if (defaultCellToolTipTexts != null) {
            defaultCellToolTipTexts = allocateStrings(jsWriter,
                    defaultCellToolTipTexts, null);
        }

        defaultCellHorizontalAligments = gridRenderContext
                .getDefaultCellHorizontalAlignments();
        if (defaultCellHorizontalAligments != null) {
            defaultCellHorizontalAligments = allocateStrings(jsWriter,
                    defaultCellHorizontalAligments, null);
        }

        UIColumn columns[] = gridRenderContext.listColumns();

        int autoFilterIndex = 0;
        jsWriter.writeMethodCall("f_setColumns2");
        for (int i = 0; i < columns.length; i++) {
            UIColumn columnComponent = columns[i];

            if (i > 0) {
                jsWriter.write(',');
            }

            IObjectLiteralWriter objectWriter = jsWriter
                    .writeObjectLiteral(true);

            String columnId = gridRenderContext.getColumnId(i);
            if (columnId != null) {
                objectWriter.writeSymbol("_id").writeString(columnId);
            }

            int rowState = gridRenderContext.getColumnState(i);
            if (rowState == AbstractGridRenderContext.SERVER_HIDDEN) {
                objectWriter.writeSymbol("_visibility").writeNull();

                objectWriter.end();
                continue;

            } else if (rowState == AbstractGridRenderContext.CLIENT_HIDDEN) {
                objectWriter.writeSymbol("_visibility").writeBoolean(false);
            }

            if (columnStyleClasses != null) {
                String styleClass = columnStyleClasses[i];
                if (styleClass != null) {
                    objectWriter.writeSymbol("_styleClass").write(styleClass);
                }
            }

            if (defaultCellImageURLs != null) {
                String url = defaultCellImageURLs[i];
                if (url != null) {
                    objectWriter.writeSymbol("_defaultCellImageURL").write(url);
                }
            }

            if (columnComponent instanceof ICellImageSettings) {
                if (((ICellImageSettings) columnComponent)
                        .isCellImageURLSetted()) {
                    objectWriter.writeSymbol("_cellImage").writeBoolean(true);
                }
            }

            if ((generationMask & GENERATE_CELL_STYLE_CLASS) > 0) {
                if (columnComponent instanceof ICellStyleClassSettings) {
                    if (((ICellStyleClassSettings) columnComponent)
                            .isCellStyleClassSetted()) {
                        objectWriter.writeSymbol("_cellStyleClassSetted")
                                .writeBoolean(true);
                    }
                }
            }

            if (defaultCellStyleClasses != null) {
                String scs[] = defaultCellStyleClasses[i];
                if (scs != null) {
                    objectWriter.writeSymbol("_cellStyleClasses").write('[');

                    for (int j = 0; j < scs.length; j++) {
                        if (j > 0) {
                            jsWriter.write(',');
                        }
                        jsWriter.write(scs[j]);
                    }

                    jsWriter.write(']');
                }
            }

            if (columnComponent instanceof IAutoFilterCapability) {
                if (((IAutoFilterCapability) columnComponent).isAutoFilter()) {
                    objectWriter.writeSymbol("_autoFilter").writeInt(
                            autoFilterIndex++);
                }
            }

            if (defaultCellToolTipTexts != null) {
                String tooltip = defaultCellToolTipTexts[i];
                if (tooltip != null) {
                    objectWriter.writeSymbol("_cellToolTipText").write(tooltip);
                }
            }

            if (defaultCellHorizontalAligments != null) {
                String halign = defaultCellHorizontalAligments[i];
                if (halign != null) {
                    objectWriter.writeSymbol("_align").write(halign);
                }
            }

            if (imageURLs != null) {
                String imageURL = imageURLs[i];
                if (imageURL != null) {
                    objectWriter.writeSymbol("_titleImageURL").write(imageURL);
                }
            }

            if (disabledImageURLs != null) {
                String disabledImageURL = disabledImageURLs[i];
                if (disabledImageURL != null) {
                    objectWriter.writeSymbol("_titleDisabledImageURL").write(
                            disabledImageURL);
                }
            }

            if (selectedImageURLs != null) {
                String selectedImageURL = selectedImageURLs[i];
                if (selectedImageURL != null) {
                    objectWriter.writeSymbol("_titleSelectedImageURL").write(
                            selectedImageURL);
                }
            }

            if (hoverImageURLs != null) {
                String hoverImageURL = hoverImageURLs[i];
                if (hoverImageURL != null) {
                    objectWriter.writeSymbol("_titleHoverImageURL").write(
                            hoverImageURL);
                }
            }

            if (gridRenderContext.isResizable()) {
                if (columnComponent instanceof IResizableCapability) {
                    if (((IResizableCapability) columnComponent).isResizable()) {
                        objectWriter.writeSymbol("_resizable").writeBoolean(
                                true);

                        if (columnComponent instanceof IWidthRangeCapability) {
                            int min = ((IWidthRangeCapability) columnComponent)
                                    .getMinWidth();
                            if (min > 0) {
                                objectWriter.writeSymbol("_minWidth").writeInt(
                                        min);
                            }

                            int max = ((IWidthRangeCapability) columnComponent)
                                    .getMaxWidth();
                            if (max > 0) {
                                objectWriter.writeSymbol("_maxWidth").writeInt(
                                        max);
                            }
                        }
                    }
                }
            }

            if ((generationMask & GENERATE_CELL_WIDTH) > 0) {
                if (columnComponent instanceof IWidthCapability) {
                    String width = ((IWidthCapability) columnComponent)
                            .getWidth();
                    if (width != null) {
                        objectWriter.writeSymbol("_width").writeString(width);
                    }
                }
            }

            if ((generationMask & GENERATE_CELL_TEXT) > 0) {
                if (columnComponent instanceof ITextCapability) {
                    String text = ((ITextCapability) columnComponent).getText();
                    if (text != null) {
                        objectWriter.writeSymbol("_text").writeString(text);
                    }
                }
                if (columnComponent instanceof IToolTipCapability) {
                    String toolTip = ((IToolTipCapability) columnComponent)
                            .getToolTipText();
                    if (toolTip != null) {
                        objectWriter.writeSymbol("_toolTip").writeString(
                                toolTip);
                    }
                }
            }

            writeGridColumnProperties(objectWriter, gridRenderContext,
                    columnComponent, i);

            objectWriter.end();
        }
        jsWriter.writeln(");");

        ISortedComponent sortedComponents[] = gridRenderContext
                .listSortedComponents();
        if (sortedComponents != null && sortedComponents.length > 0) {
            jsWriter.writeMethodCall("f_enableSorters");
            int pred = 0;
            for (int j = 0; j < sortedComponents.length; j++) {
                ISortedComponent sortedComponent = sortedComponents[j];

                if (j > 0) {
                    for (; pred > 0; pred--) {
                        jsWriter.write(',').writeNull();
                    }

                    jsWriter.write(',');
                }
                jsWriter.writeInt(sortedComponent.getIndex());

                if (sortedComponent.isAscending()) {
                    jsWriter.write(',').writeBoolean(true);

                } else {
                    pred++;
                }
            }
            jsWriter.writeln(");");
        }
    }

    protected void writeGridColumnProperties(IObjectLiteralWriter objectWriter,
            AbstractGridRenderContext tableContext, UIColumn columnComponent,
            int columnIndex) throws WriterException {

        Map listenersByType = ListenerTools.getListenersByType(
                ListenerTools.ATTRIBUTE_NAME_SPACE, columnComponent);

        if (listenersByType.isEmpty() == false) {
            StringAppender sa = new StringAppender(128);

            listenersByType.remove(ListenerTools.ATTRIBUTE_NAME_SPACE
                    .getSortEventName());

            appendAttributeEventForm(sa, objectWriter.getParent().getWriter(),
                    listenersByType);

            if (sa.length() > 0) {
                IJavaScriptWriter jsWriter = objectWriter
                        .writeSymbol("_events");

                jsWriter.writeString(sa.toString());
            }
        }

        Object sort = tableContext.getSortCommand(columnIndex);
        if (sort != null) {
            String command = null;

            if (sort instanceof String) {
                if (tableContext.getSortClientSide(columnIndex) == false) {
                    command = tableContext
                            .translateJavascriptMethod(SORT_SERVER_COMMAND);

                } else {
                    command = ((String) sort).trim();
                }

            } else if (sort instanceof IScriptListener) {
                IScriptListener scriptListener = (IScriptListener) sort;

                command = scriptListener.getCommand();

            } else if (sort instanceof IServerActionListener) {
                // Le tri se fait coté serveur !

                command = tableContext
                        .translateJavascriptMethod(SORT_SERVER_COMMAND);
            }

            if (command != null) {
                IJavaScriptWriter jsWriter = objectWriter
                        .writeSymbol("_sorter");

                if (Constants.VERIFY_SORT_COMMAND) {
                    String delimiters = " (),;:";
                    StringTokenizer st = new StringTokenizer(command,
                            delimiters, true);
                    if (st.countTokens() > 1) {
                        throw new FacesException(
                                "The comparator must be a function name ! ('"
                                        + command + "')");
                    }
                }

                jsWriter.write(command);
            }
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private interface IBooleanStateCallback {
        boolean test(AbstractGridRenderContext tableContext, int index);
    }

    private static final IBooleanStateCallback CELL_STYLE_CLASS = new IBooleanStateCallback() {
        private static final String REVISION = "$Revision$";

        public boolean test(AbstractGridRenderContext tableContext, int index) {
            return tableContext.isCellStyleClass(index);
        }
    };

    private static final IBooleanStateCallback CELL_TOOLTIP_TEXT = new IBooleanStateCallback() {
        private static final String REVISION = "$Revision$";

        public boolean test(AbstractGridRenderContext tableContext, int index) {
            return tableContext.isCellToolTipText(index);
        }
    };

    private void writeBooleanArray(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext tableContext, int cnt,
            IBooleanStateCallback callback) throws WriterException {
        int pred = 0;
        boolean first = true;
        for (int i = 0; i < cnt; i++) {

            if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            if (pred > 0) {
                if (first) {
                    pred--;
                    htmlWriter.writeBoolean(false).write(',');
                    first = false;
                }

                for (; pred > 0; pred--) {
                    htmlWriter.write(',').writeBoolean(false);
                }
            }

            if (first) {
                first = false;

            } else {
                htmlWriter.write(',');
            }

            htmlWriter.writeBoolean(callback.test(tableContext, i));
        }
    }

    protected final String[] allocateStrings(IJavaScriptWriter htmlWriter,
            String[] values, String ret[]) throws WriterException {

        if (values == null) {
            return null;
        }

        if (ret == null) {
            ret = new String[values.length];
        }

        if (values == null) {
            return ret;
        }

        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                continue;
            }

            ret[i] = htmlWriter.allocateString(values[i]);
        }

        return ret;
    }

    protected void encodeJsFooter(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext data) {
    }

    public boolean getDecodesChildren() {
        return true;
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected void addUnlockProperties(Set unlockedProperties) {
        super.addUnlockProperties(unlockedProperties);

        unlockedProperties.add("serializedIndexes");
        unlockedProperties.add("first");
        unlockedProperties.add("sortIndex");
        unlockedProperties.add("columnWidths");
        unlockedProperties.add("filterExpression");
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        IGridComponent gridComponent = (IGridComponent) component;

        if (component instanceof UIData2) {
            ((UIData2) component).addDecodedIndexes(0, 0);

            String serializedIndexes = componentData
                    .getStringProperty("serializedIndexes");
            if (serializedIndexes != null) {
                for (StringTokenizer st = new StringTokenizer(
                        serializedIndexes, ","); st.hasMoreTokens();) {

                    int first = Integer.parseInt(st.nextToken());
                    int rows = Integer.parseInt(st.nextToken());

                    ((UIData2) component).addDecodedIndexes(first, rows);
                }
            }
        }

        Number first = componentData.getNumberProperty("first");
        if (first != null) {
            int old = gridComponent.getFirst();

            int f = first.intValue();
            if (old != f) {
                gridComponent.setFirst(f);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.FIRST, new Integer(old), first));
            }
        }

        if (gridComponent instanceof ISortedChildrenCapability) {
            ISortedChildrenCapability sortedChildrenCapability = (ISortedChildrenCapability) gridComponent;

            String sortIndex = componentData.getStringProperty("sortIndex");
            if (sortIndex != null) {
                UIColumn columns[] = gridComponent.listColumns().toArray();

                List sortedColumns = new ArrayList(columns.length);
                StringTokenizer st1 = new StringTokenizer(sortIndex, ",");

                for (; st1.hasMoreTokens();) {
                    String tok1 = st1.nextToken();
                    String tok2 = st1.nextToken();

                    int idx = Integer.parseInt(tok1);
                    boolean order = "true".equalsIgnoreCase(tok2);

                    UIColumn dataColumn = columns[idx];

                    sortedColumns.add(dataColumn);

                    if ((dataColumn instanceof IOrderCapability) == false) {
                        continue;
                    }

                    IOrderCapability orderCapability = (IOrderCapability) dataColumn;

                    if (orderCapability.isAscending() == order) {
                        continue;
                    }

                    orderCapability.setAscending(order);

                    dataColumn.queueEvent(new PropertyChangeEvent(dataColumn,
                            Properties.ASCENDING, Boolean.valueOf(!order),
                            Boolean.valueOf(order)));
                }

                UIComponent old[] = sortedChildrenCapability
                        .getSortedChildren();

                UIComponent news[] = (UIComponent[]) sortedColumns
                        .toArray(new UIComponent[sortedColumns.size()]);

                sortedChildrenCapability.setSortedChildren(news);

                if (isEquals(old, news) == false) {
                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.SORTED_CHILDREN, old, news));
                }
            }
        }

        String columnWidths = componentData.getStringProperty("columnWidths");
        if (columnWidths != null) {
            StringTokenizer st = new StringTokenizer(columnWidths, ",");
            IColumnIterator it = gridComponent.listColumns();

            for (; st.hasMoreTokens();) {
                String width = st.nextToken();

                for (; it.hasNext();) {
                    UIColumn col = it.next();

                    if ((col instanceof IResizableCapability) == false) {
                        continue;
                    }

                    if (((IResizableCapability) col).isResizable() == false) {
                        continue;
                    }

                    if ((col instanceof IWidthCapability) == false) {
                        continue;
                    }

                    String old = ((IWidthCapability) col).getWidth();
                    if (isEquals(old, width)) {
                        break;
                    }

                    ((IWidthCapability) col).setWidth(width);

                    col.queueEvent(new PropertyChangeEvent(col,
                            Properties.WIDTH, old, width));
                    break;
                }
            }
        }

        if (gridComponent instanceof IFilterCapability) {
            IFilterCapability filterCapability = (IFilterCapability) gridComponent;

            String filterExpression = componentData
                    .getStringProperty("filterExpression");
            if (filterExpression != null) {
                if (filterExpression.length() < 1) {
                    filterExpression = null;
                }

                IFilterProperties fexp = HtmlTools.decodeFilterExpression(
                        context.getProcessContext(), component,
                        filterExpression);

                IFilterProperties old = filterCapability.getFilterProperties();
                if (isEquals(fexp, old) == false) {
                    filterCapability.setFilterProperties(fexp);

                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.FILTER_PROPERTIES, old, fexp));
                }
            }
        }

        if (gridComponent instanceof IPreferencesCapability) {
            IPreferencesCapability preferenceCapability = (IPreferencesCapability) gridComponent;

            IComponentPreferences preferences = preferenceCapability
                    .getPreferences();

            if (preferences == null
                    && (gridComponent instanceof IPreferencesSettings)) {
                if (((IPreferencesSettings) gridComponent)
                        .isPreferencesSetted()) {

                    preferences = new GridPreferences();

                    preferenceCapability.setPreferences(preferences);
                }
            }
            if (preferences != null) {
                preferences.savePreferences(facesContext,
                        (UIComponent) preferenceCapability);
            }
        }

        if (gridComponent instanceof IShowValueCapability) {
            ((IShowValueCapability) gridComponent).setShowValue(null);
        }
    }

    protected void decodeEvent(IRequestContext context, UIComponent component,
            IEventData eventData) {

        if (eventData != null
                && JavaScriptClasses.EVENT_VALUE_CHANGE.equals(eventData
                        .getEventName())) {

            // Ok on change de page ...
            return;
        }

        super.decodeEvent(context, component, eventData);
    }

    protected static final int[] parseIndexes(String indexes) {
        if (indexes == null) {
            return EMPTY_INDEXES;
        }
        StringTokenizer st = new StringTokenizer(indexes,
                HtmlTools.LIST_SEPARATORS);

        int cnt = st.countTokens();
        if (cnt < 1) {
            return EMPTY_INDEXES;
        }

        int ret[] = new int[cnt];

        int idx = 0;
        for (; st.hasMoreTokens();) {
            String s_index = st.nextToken();
            try {
                int index = Integer.parseInt(s_index);
                ret[idx++] = index;

            } catch (NumberFormatException ex) {
                throw new FacesException("Can not parse index '" + s_index
                        + ".", ex);
            }
        }

        for (; idx < ret.length;) {
            ret[idx++] = -1;
        }

        return ret;
    }

    protected void setCheckedIndexes(FacesContext facesContext,
            ICheckRangeComponent checkComponent, int[] indexes, int uindexes[],
            boolean uncheckAll) {

        if (uncheckAll) {
            checkComponent.uncheckAll();

        } else if (uindexes.length > 0) {
            checkComponent.uncheck(uindexes);
        }

        if (indexes.length > 0) {
            checkComponent.check(indexes);
        }
    }

    protected void setAdditionalIndexes(FacesContext facesContext,
            IAdditionalInformationComponent additionalInformationComponent,
            int[] indexes, int uindexes[], boolean all) {

        if (all) {
            additionalInformationComponent.hideAllAdditionalInformations();

        } else if (uindexes.length > 0) {
            additionalInformationComponent.hideAdditionalInformation(uindexes);
        }

        if (indexes.length > 0) {
            additionalInformationComponent.showAdditionalInformation(indexes);
        }
    }

    protected void setSelectedIndexes(FacesContext facesContext,
            ISelectionRangeComponent selectionComponent, int[] indexes,
            int dindexes[], boolean deselectAll) {

        if (deselectAll) {
            selectionComponent.deselectAll();

        } else if (dindexes.length > 0) {
            selectionComponent.deselect(dindexes);
        }

        if (indexes.length > 0) {
            selectionComponent.select(indexes);
        }
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = null;

        IGridComponent gridComponent = (IGridComponent) component;

        if (gridComponent instanceof IMenuCapability) {
            IMenuIterator menuIterator = ((IMenuCapability) gridComponent)
                    .listMenus();

            for (; menuIterator.hasNext();) {
                MenuComponent menuComponent = menuIterator.next();

                IComponentDecorator menuDecorator = new SubMenuDecorator(
                        menuComponent, menuComponent.getMenuId(), null,
                        menuComponent.isRemoveAllWhenShown(facesContext),
                        getItemImageWidth(menuComponent),
                        getItemImageHeight(menuComponent));

                if (decorator == null) {
                    decorator = menuDecorator;
                    continue;
                }

                menuDecorator.addChildDecorator(decorator);
                decorator = menuDecorator;
            }
        }

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    protected void writeFullStates(IJavaScriptWriter jsWriter,
            AbstractGridRenderContext context, String jsCommand, Set objects)
            throws WriterException {
    }

    protected void writeFullStates(IJavaScriptWriter jsWriter,
            String jsCommand, int[] indexes) throws WriterException {

        if (indexes == null || indexes.length < 1) {
            return;
        }

        jsWriter.writeMethodCall(jsCommand).write('[');
        for (int i = 0; i < indexes.length; i++) {
            if (i > 0) {
                jsWriter.write(',');
            }

            jsWriter.writeInt(indexes[i]);
        }

        jsWriter.writeln("]);");
    }

    protected IHtmlWriter writeTabIndex(IHtmlWriter writer,
            ITabIndexCapability tabIndexCapability) throws WriterException {
        // Do nothing : check the v:tabIndex attribute
        return writer;
    }
}
