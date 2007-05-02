/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.IAutoFilterCapability;
import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IForegroundBackgroundColorCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IMenuCapability;
import org.rcfaces.core.component.capability.IOrderCapability;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.IRequiredCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ISortedChildrenCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IToolTipCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;
import org.rcfaces.core.component.capability.IWidthCapability;
import org.rcfaces.core.component.capability.IWidthRangeCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.component.Properties;
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
import org.rcfaces.core.internal.tools.ArrayIndexesModel;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.core.lang.provider.ICheckProvider;
import org.rcfaces.core.lang.provider.ISelectionProvider;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.preference.IComponentPreference;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IAccessibilityRoles;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;

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

    private static final String TABLE_CONTEXT = "camelia.table.context";

    private static final String SCROLLBARS_PROPERTY = "camelia.scrollbars";

    protected static final boolean ENABLE_SERVER_REQUEST = true;

    protected static final int[] EMPTY_INDEXES = new int[0];

    protected static final String NULL_VALUE = "*$& NULL VALUE *$& O.O.";

    protected static final boolean ALLOCATE_ROW_STRINGS = true;

    private static final int TEXT_RIGHT_PADDING = 4;

    private static final int SORT_PADDING = 10;

    private static final String GRID_STYLE_CLASS = "f_grid";

    protected static final int GENERATE_CELL_STYLE_CLASS = 0x0001;

    protected static final int GENERATE_CELL_IMAGES = 0x0002;

    public String getComponentStyleClassName() {
        return GRID_STYLE_CLASS;
    }

    protected final AbstractGridRenderContext getGridRenderContext(
            IComponentWriter componentWriter) {
        return (AbstractGridRenderContext) componentWriter
                .getComponentRenderContext().getAttribute(TABLE_CONTEXT);
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        IGridComponent dataGridComponent = (IGridComponent) writer
                .getComponentRenderContext().getComponent();

        IJavaScriptRenderContext javaScriptRenderContext = writer
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

        if (dataGridComponent instanceof IMenuCapability) {
            IMenuIterator menuIterator = ((IMenuCapability) dataGridComponent)
                    .listMenus();
            if (menuIterator.hasNext()) {
                javaScriptRenderContext.appendRequiredClasses(classes,
                        JavaScriptClasses.GRID, "menu");
            }
        }

        if (dataGridComponent.getRows() > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.GRID, "ajax");
        }
    }

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        UIComponent dataGridComponent = componentRenderContext.getComponent();

        if (dataGridComponent instanceof IBorderCapability) {
            if (((IBorderCapability) dataGridComponent).isBorder() == false) {
                cssWriter.writeBorderStyle("none");
            }
        }
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        IGridComponent dg = (IGridComponent) componentRenderContext
                .getComponent();

        if (dg instanceof IPreferenceCapability) {
            IPreferenceCapability preferenceCapability = (IPreferenceCapability) dg;

            IComponentPreference preference = preferenceCapability
                    .getPreference();
            if (preference != null) {
                preference.loadPreference(facesContext, (UIComponent) dg);
            }
        }

        AbstractGridRenderContext gridRenderContext = createTableContext((IHtmlComponentRenderContext) writer
                .getComponentRenderContext());
        componentRenderContext.setAttribute(TABLE_CONTEXT, gridRenderContext);

        boolean scrollBars = false;

        String height = ((ISizeCapability) dg).getHeight();
        String width = ((ISizeCapability) dg).getWidth();
        int rows = gridRenderContext.getRows();
        if (height != null || width != null) {
            scrollBars = true;
            /*
             * writer.setComponentRenderAttribute(SCROLLBARS_PROPERTY,
             * Boolean.TRUE);
             */
        }

        boolean resizable = false;
        int totalResize = 0;
        {
            boolean widthNotSpecified = false;

            UIColumn dccs[] = gridRenderContext.listColumns();
            for (int i = 0; i < dccs.length; i++) {
                if (gridRenderContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                    continue;
                }

                UIColumn dc = dccs[i];
                String dw = null;

                if (dc instanceof IWidthCapability) {
                    dw = ((IWidthCapability) dc).getWidth();
                }

                if (dw == null) {
                    widthNotSpecified = true;

                } else {
                    try {
                        int idw = Integer.parseInt(dw);
                        if (idw < 0) {
                            idw = 0;
                        }

                        totalResize += idw;

                    } catch (NumberFormatException ex) {
                        LOG.error("Bad width of column #" + i + ": " + dw, ex);
                    }
                }

                if (dc instanceof IResizableCapability) {
                    resizable |= ((IResizableCapability) dc).isResizable();
                }
            }

            if (resizable && (scrollBars == false || widthNotSpecified)) {
                resizable = false;
            }
        }
        gridRenderContext.setResizable(resizable, totalResize);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(IHtmlWriter.DIV, (UIComponent) dg);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (gridRenderContext.isSelectable()) {
            htmlWriter.writeAttribute("v:selectionCardinality",
                    gridRenderContext.getSelectionCardinality());

            if (gridRenderContext.isClientSelectionFullState()) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", "true");
            }
        }
        if (gridRenderContext.isCheckable()) {
            htmlWriter.writeAttribute("v:checkCardinality", gridRenderContext
                    .getCheckCardinality());

            if (gridRenderContext.getCheckCardinality() == ICardinality.ONEMANY_CARDINALITY) {
                int checkedCount = ((ICheckProvider) dg)
                        .getCheckedValuesCount();

                if (checkedCount > 0) {
                    htmlWriter.writeAttribute("v:checkedCount", checkedCount);
                }
            }

            if (gridRenderContext.isClientCheckFullState()) {
                htmlWriter.writeAttribute("v:clientCheckFullState", "true");
            }
        }

        if (dg instanceof IReadOnlyCapability) {
            if (((IReadOnlyCapability) dg).isReadOnly()) {
                htmlWriter.writeAttribute("v:readOnly", "true");
            }
        }

        if (gridRenderContext.isDisabled()) {
            htmlWriter.writeAttribute("v:disabled", "true");
        }

        if (dg instanceof IRequiredCapability) {
            if (((IRequiredCapability) dg).isRequired()) {
                htmlWriter.writeAttribute("v:required", "true");
            }
        }

        if (resizable) {
            htmlWriter.writeAttribute("v:resizable", "true");
        }

        Object dataModel = gridRenderContext.getDataModel();
        if (dataModel instanceof IFiltredModel) {
            htmlWriter.writeAttribute("v:filtred", "true");

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

        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }

        int rowCount = gridRenderContext.getRowCount();
        if (rowCount >= 0) {
            htmlWriter.writeAttribute("v:rowCount", rowCount);
        }

        int first = gridRenderContext.getFirst();
        if (first > 0) {
            htmlWriter.writeAttribute("v:first", first);
        }
        if (gridRenderContext.isPaged() == false) {
            htmlWriter.writeAttribute("v:paged", "false");
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

        writeGridComponentAttributes(htmlWriter, gridRenderContext, dg);

        int dataGridPixelWidth = getPixelSize(((IWidthCapability) dg)
                .getWidth(), -1);

        boolean headerVisible = true; // dg.isHeaderVisible(facesContext);

        int tableWidth = 0;
        if (scrollBars) {
            String w = null;
            // dataGridPixelWidth-=2;
            if (dataGridPixelWidth > 0) {
                w = String.valueOf(dataGridPixelWidth) + "px";
            }

            if (headerVisible) {
                htmlWriter.startElement(IHtmlWriter.DIV);
                htmlWriter.writeClass(getDataTitleScroll(htmlWriter));
                if (w != null) {
                    htmlWriter.writeStyle().writeWidth(w);
                }
            }

            tableWidth = encodeFixedHeader(htmlWriter, gridRenderContext,
                    dataGridPixelWidth);
            htmlWriter.endElement(IHtmlWriter.DIV);

            htmlWriter.startElement(IHtmlWriter.DIV);
            htmlWriter.writeClass(getDataBodyScrollClassName(htmlWriter));

            ICssWriter cssWriter = htmlWriter.writeStyle(32);

            if (height != null) {
                cssWriter.writeHeight(computeSizeInPixel(height, -1,
                        -getTitleHeight()));
            }
            if (w != null) {
                cssWriter.writeWidth(w);
            }
        }

        htmlWriter.startElement(IHtmlWriter.TABLE);
        htmlWriter.writeClass(getDataTableClassName(htmlWriter,
                gridRenderContext.isDisabled()));

        // Pas de support CSS de border-spacing pour IE: on est oblig� de le
        // cod� en HTML ...
        htmlWriter.writeCellSpacing(0);

        if (tableWidth > 0) {
            int ttw = getPixelSize(((IWidthCapability) dg).getWidth(), -1);
            if (ttw > 0 && ttw > tableWidth) {
                tableWidth = -1;
            }
        }

        if (resizable) {
            // 2 htmlWriter.writeAttribute("width", totalResize);
            htmlWriter.writeStyle().writeWidth(totalResize + "px");

        } else if (tableWidth > 0) {
            htmlWriter.writeWidth(tableWidth);

        } else {
            htmlWriter.writeWidth("100%");
        }

        gridRenderContext.setHasScrollBars(scrollBars);

        encodeHeader(htmlWriter, gridRenderContext);

        htmlWriter.startElement(IHtmlWriter.TBODY);

        encodeBodyBegin(htmlWriter, gridRenderContext);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        AbstractGridRenderContext tableContext = getGridRenderContext(writer);

        encodeBodyEnd((IHtmlWriter) writer, tableContext);

        super.encodeEnd(writer);
    }

    protected void writeGridComponentAttributes(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, IGridComponent dg)
            throws WriterException {

    }

    protected String getWAIRole() {
        return IAccessibilityRoles.GRID;
    }

    protected String getDataBodyScrollClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + "_dataBody_scroll";
    }

    protected String getDataTableClassName(IHtmlWriter htmlWriter,
            boolean disabled) {
        String className = GRID_STYLE_CLASS + TABLE;
        if (disabled) {
            className += " " + className + "_disabled";
        }

        return className;
    }

    protected String getDataTitleScroll(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + "_dataTitle_scroll";
    }

    protected int getTitleHeight() {
        return 19;
    }

    protected int encodeFixedHeader(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, int dataGridWidth)
            throws WriterException {

        htmlWriter.startElement(IHtmlWriter.TABLE);
        htmlWriter.writeClass(getFixedHeaderTableClassName(htmlWriter));
        htmlWriter.writeCellPadding(0);
        htmlWriter.writeCellSpacing(0);
        if (tableContext.isResizable()) {
            // OO2: htmlWriter.writeAttribute("width",
            // tableContext.getResizeTotalSize());
            htmlWriter.writeStyle().writeWidth(
                    tableContext.getResizeTotalSize() + "px");
        }

        int tableWidth = 0;

        UIColumn columns[] = tableContext.listColumns();

        for (int i = 0; i < columns.length; i++) {
            if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            htmlWriter.startElement(IHtmlWriter.COL);

            htmlWriter.endElement(IHtmlWriter.COL);
        }
        // htmlWriter.startElement(IHtmlWriter.COL");

        htmlWriter.startElement(IHtmlWriter.THEAD);
        htmlWriter.startElement(IHtmlWriter.TR);
        htmlWriter.writeClass(getTitleRowClassName(htmlWriter));

        boolean first = true;
        for (int i = 0; i < columns.length; i++) {
            if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            UIColumn column = columns[i];

            encodeFixedTitleCol(htmlWriter, tableContext, column, first);
            first = false;

            if (tableWidth < 0) {
                continue;
            }

            if ((column instanceof IWidthCapability) == false) {
                tableWidth = -1; // On sait plus !
                continue;
            }

            int wp = getPixelSize(((IWidthCapability) column).getWidth(),
                    dataGridWidth);
            if (wp <= 0) {
                continue;
            }

            tableWidth += wp;
        }

        // Fin des titres ....
        htmlWriter.endElement(IHtmlWriter.TR);
        htmlWriter.endElement(IHtmlWriter.THEAD);
        htmlWriter.startElement(IHtmlWriter.TBODY);
        htmlWriter.endElement(IHtmlWriter.TBODY);
        htmlWriter.endElement(IHtmlWriter.TABLE);

        if (tableWidth < 0) {
            return -1;
        }

        return tableWidth;
    }

    protected String getTitleRowClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + TITLE_ROW;
    }

    protected String getFixedHeaderTableClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + "_fttitle";
    }

    private void encodeFixedTitleCol(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, UIColumn column,
            boolean first) throws WriterException {

        htmlWriter.startElement(IHtmlWriter.TH);

        String className = getTitleCellClassName(htmlWriter, column, first,
                tableContext.isDisabled());
        htmlWriter.writeClass(className);

        if (column instanceof IToolTipCapability) {
            String toolTip = ((IToolTipCapability) column).getToolTipText();
            if (toolTip != null) {
                toolTip = ParamUtils.formatMessage(column, toolTip);

                htmlWriter.writeTitle(toolTip);
            }
        }

        String width = null;
        if (column instanceof IWidthCapability) {
            width = ((IWidthCapability) column).getWidth();
            if (width != null) {
                htmlWriter.writeWidth(width);
            }
        }

        encodeTitleText(htmlWriter, tableContext, column, width);

        htmlWriter.endElement(IHtmlWriter.TH);
    }

    protected String getTitleCellClassName(IHtmlWriter htmlWriter,
            UIColumn column, boolean firstColumn, boolean disabled) {
        String mainClassName = GRID_STYLE_CLASS + TITLE_CELL;

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

    protected void encodeTitleText(IHtmlWriter htmlWriter,
            AbstractGridRenderContext tableContext, UIColumn column,
            String width) throws WriterException {
        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement(IHtmlWriter.DIV);
        htmlWriter.writeClass(getTitleDivContainerClassName(htmlWriter));

        if (width != null) {
            String widthRightPadding = computeSizeInPixel(width, -1,
                    -TEXT_RIGHT_PADDING);
            htmlWriter.writeStyle().writeWidth(widthRightPadding);
        }

        htmlWriter.startElement(IHtmlWriter.DIV);
        htmlWriter.writeClass(getTitleDivTextClassName(htmlWriter));

        String halign = null;
        if (column instanceof IAlignmentCapability) {
            halign = ((IAlignmentCapability) column).getAlignment();
        }
        if (halign == null) {
            halign = "left";
        }

        htmlWriter.writeAlign(halign);

        if (width != null) { // SORTER
            String widthRightPadding = computeSizeInPixel(width, -1,
                    -TEXT_RIGHT_PADDING);
            htmlWriter.writeStyle().writeWidth(widthRightPadding);
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

        htmlWriter.endElement(IHtmlWriter.DIV);

        htmlWriter.endElement(IHtmlWriter.DIV);
    }

    protected String getTitleDivTextClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + TITLE_TTEXT;
    }

    protected String getTitleDivContainerClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + TITLE_STEXT;
    }

    protected String getTitleImageClassName(IHtmlWriter htmlWriter) {
        return GRID_STYLE_CLASS + TITLE_IMAGE;
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
            AbstractGridRenderContext tableContext) throws WriterException {

        UIColumn columns[] = tableContext.listColumns();

        UIColumn dcs[] = new UIColumn[columns.length];

        int is = 0;
        for (int i = 0; i < columns.length; i++) {
            UIColumn dc = columns[i];

            if (tableContext.getColumnState(i) != AbstractGridRenderContext.VISIBLE) {
                continue;
            }

            dcs[is++] = dc;
            encodeTitleCol(htmlWriter, dc);
        }

        if (tableContext.isHasScrollBars() == false) {
            htmlWriter.startElement(IHtmlWriter.THEAD);
            htmlWriter.startElement(IHtmlWriter.TR);
            htmlWriter.writeClass(getTitleRowClassName(htmlWriter));

            for (int i = 0; i < is; i++) {
                encodeTitleText(htmlWriter, i, tableContext, dcs[i]);
            }

            htmlWriter.endElement(IHtmlWriter.TR);
            htmlWriter.endElement(IHtmlWriter.THEAD);
        }
    }

    private void encodeTitleCol(IHtmlWriter htmlWriter, UIColumn column)
            throws WriterException {
        htmlWriter.startElement(IHtmlWriter.COL);

        if (column instanceof IWidthCapability) {
            String width = ((IWidthCapability) column).getWidth();
            if (width != null) {
                // 2: htmlWriter.writeAttribute("width", width);
                htmlWriter.writeStyle().writeWidth(width + "px");
            }
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

    protected void encodeTitleText(IHtmlWriter htmlWriter, int number,
            AbstractGridRenderContext tableContext, UIColumn column)
            throws WriterException {
        htmlWriter.startElement(IHtmlWriter.TH);
        String thClassName = getTitleCellClassName(htmlWriter, column,
                number == 0, tableContext.isDisabled());
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

        encodeTitleText(htmlWriter, tableContext, column, null);

        htmlWriter.endElement(IHtmlWriter.TH);
    }

    protected void encodeBodyBegin(IHtmlWriter htmlWriter,
            AbstractGridRenderContext data) throws WriterException {

        htmlWriter.enableJavaScript();
    }

    protected void encodeBodyEnd(IHtmlWriter writer,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
    }

    protected void encoreBodyTableEnd(IHtmlWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) throws WriterException {
        htmlWriter.endElement(IHtmlWriter.TBODY);

        htmlWriter.endElement(IHtmlWriter.TABLE);

        if (gridRenderContext.isHasScrollBars()) {
            htmlWriter.endElement(IHtmlWriter.DIV);
        }

        htmlWriter.endElement(IHtmlWriter.DIV);
    }

    protected void encodeJsHeader(IJavaScriptWriter htmlWriter,
            AbstractGridRenderContext gridRenderContext) {
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
                objectWriter.writeSymbol("_hiddenMode").writeNull();

                objectWriter.end();
                continue;

            } else if (rowState == AbstractGridRenderContext.CLIENT_HIDDEN) {
                objectWriter.writeSymbol("_hiddenMode").writeBoolean(false);
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
                    objectWriter.writeSymbol("_horizontalAlign").write(halign);
                }
            }

            if (imageURLs != null) {
                String imageURL = imageURLs[i];
                if (imageURL != null) {
                    objectWriter.writeSymbol("_imageURL").write(imageURL);
                }
            }

            if (disabledImageURLs != null) {
                String disabledImageURL = disabledImageURLs[i];
                if (disabledImageURL != null) {
                    objectWriter.writeSymbol("_disabledImageURL").write(
                            disabledImageURL);
                }
            }

            if (selectedImageURLs != null) {
                String selectedImageURL = selectedImageURLs[i];
                if (selectedImageURL != null) {
                    objectWriter.writeSymbol("_selectedImageURL").write(
                            selectedImageURL);
                }
            }

            if (hoverImageURLs != null) {
                String hoverImageURL = hoverImageURLs[i];
                if (hoverImageURL != null) {
                    objectWriter.writeSymbol("_hoverImageURL").write(
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

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        IGridComponent gridComponent = (IGridComponent) component;

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

        if (gridComponent instanceof IPreferenceCapability) {
            IPreferenceCapability preferenceCapability = (IPreferenceCapability) gridComponent;

            IComponentPreference preference = preferenceCapability
                    .getPreference();
            if (preference != null) {
                preference.savePreference(facesContext,
                        (UIComponent) preferenceCapability);
            }
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
                        + ".");
            }
        }

        for (; idx < ret.length;) {
            ret[idx++] = -1;
        }

        return ret;
    }

    protected void setCheckedIndexes(FacesContext facesContext,
            ICheckProvider dg, int[] indexes, int uindexes[], boolean all) {
        Object m = dg.getCheckedValues();
        if (m != null && (m instanceof IIndexesModel) == false) {
            throw new FacesException(
                    "'CheckedValues' attribute must contain an IIndexesModel if you do not specify 'rowValueColumnId' attribute !");
        }

        IIndexesModel model = (IIndexesModel) m;
        if (model == null) {
            model = new ArrayIndexesModel();

            dg.setCheckedValues(model);
        }

        if (all) {
            model.clearIndexes();

        } else {
            for (int i = 0; i < uindexes.length; i++) {
                model.removeIndex(uindexes[i]);
            }
        }

        for (int i = 0; i < indexes.length; i++) {
            model.addIndex(indexes[i]);
        }

        model.commitChanges();
    }

    protected void setSelectedIndexes(FacesContext facesContext,
            ISelectionProvider dg, int[] indexes, int dindexes[],
            boolean deselectAll) {

        Object m = dg.getSelectedValues();
        if (m != null && (m instanceof IIndexesModel) == false) {
            throw new FacesException(
                    "'SelectedValues' attribute must contain an IIndexesModel if you do not specify 'rowValueColumnId' attribute !");
        }

        IIndexesModel model = (IIndexesModel) m;
        if (model == null) {
            model = new ArrayIndexesModel();

            dg.setSelectedValues(model);
        }

        if (deselectAll) {
            model.clearIndexes();

        } else {
            for (int i = 0; i < dindexes.length; i++) {
                model.removeIndex(dindexes[i]);
            }
        }

        for (int i = 0; i < indexes.length; i++) {
            model.addIndex(indexes[i]);
        }

        model.commitChanges();
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
}
