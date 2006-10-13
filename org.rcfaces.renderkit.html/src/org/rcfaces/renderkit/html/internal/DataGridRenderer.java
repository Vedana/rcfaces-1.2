/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.34  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.33  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.32  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.31  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.30  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.29  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.28  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.27  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.26  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.25  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.24  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.23  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.22  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.21  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.20  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.19  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.18  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.17  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.16  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.15  2005/08/24 15:39:33  oeuillot
 * Colonne retaillable + debug de composants
 *
 * Revision 1.14  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.13  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.12  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.11  2005/01/06 18:18:14  oeuillot
 * Ajout du tri des tables
 *
 * Revision 1.10  2005/01/04 13:02:51  oeuillot
 * Amelioration des tables. (Ajout des tris, scrollbars ...)
 *
 * Revision 1.9  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.8  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.7  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.6  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.5  2004/09/29 20:49:39  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.DataColumnComponent;
import org.rcfaces.core.component.DataGridComponent;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.iterator.IDataColumnIterator;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
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
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.DataGridServerSort;
import org.rcfaces.core.internal.tools.FilterExpressionTools;
import org.rcfaces.core.internal.tools.FilteredDataModel;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredDataModel;
import org.rcfaces.core.model.IIndexesModel;
import org.rcfaces.core.model.IRangeDataModel;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.core.model.ISortedDataModel;
import org.rcfaces.core.model.ITransactionalDataModel;
import org.rcfaces.core.preference.IComponentPreference;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;
import org.rcfaces.renderkit.html.internal.service.DataGridService;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DataGridRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(DataGridRenderer.class);

    private static final String TABLE = "_table";

    private static final String TITLE_ROW = "_title";

    private static final String TITLE_CELL = "_tcell";

    private static final String TITLE_IMAGE = "_timage";

    private static final String TITLE_TTEXT = "_ttext";

    private static final String TITLE_STEXT = "_stext";

    private static final String TABLE_CONTEXT = "camelia.table.context";

    private static final String SCROLLBARS_PROPERTY = "camelia.scrollbars";

    private static final boolean ENABLE_SERVER_REQUEST = true;

    private static final int[] EMPTY_INDEXES = new int[0];

    private static final String NULL_VALUE = "*$& NULL VALUE *$& O.O.";

    private static final boolean ALLOCATE_ROW_STRINGS = true;

    private static final Map SORT_ALIASES = new HashMap(8);

    static {
        SORT_ALIASES.put(ISortEventCapability.SORT_INTEGER,
                "f_dataGrid.Sort_Integer");
        SORT_ALIASES.put(ISortEventCapability.SORT_NUMBER,
                "f_dataGrid.Sort_Number");
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA,
                "f_dataGrid.Sort_Alpha");
        SORT_ALIASES.put(ISortEventCapability.SORT_ALPHA_IGNORE_CASE,
                "f_dataGrid.Sort_AlphaIgnoreCase");
        SORT_ALIASES
                .put(ISortEventCapability.SORT_TIME, "f_dataGrid.Sort_Time");
        SORT_ALIASES
                .put(ISortEventCapability.SORT_DATE, "f_dataGrid.Sort_Date");
        SORT_ALIASES.put(ISortEventCapability.SORT_SERVER,
                "f_dataGrid.Sort_Server");
    }

    private static final int TEXT_RIGHT_PADDING = 4;

    private static final int SORT_PADDING = 10;

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATA_GRID;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter writer,
            Set classes) {
        super.addRequiredJavaScriptClassNames(writer, classes);

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        DataGridComponent dataGridComponent = (DataGridComponent) writer
                .getComponentRenderContext().getComponent();

        IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                writer).getJavaScriptRenderContext();

        IMenuIterator menuIterator = dataGridComponent.listMenus();
        if (menuIterator.hasNext()) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.DATA_GRID, "menu");
        }

        if (dataGridComponent.getRows(facesContext) > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.DATA_GRID, "ajax");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer#writeCustomCss(org.rcfaces.core.internal.renderkit.IWriter,
     *      org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer.CssWriter)
     */

    protected void writeCustomCss(IHtmlWriter writer, ICssWriter cssWriter) {
        super.writeCustomCss(writer, cssWriter);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        DataGridComponent dataGridComponent = (DataGridComponent) componentRenderContext
                .getComponent();

        if (dataGridComponent
                .isBorder(componentRenderContext.getFacesContext()) == false) {
            cssWriter.writeBorderStyle("none");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeBegin(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        DataGridComponent dg = (DataGridComponent) componentRenderContext
                .getComponent();

        IComponentPreference preference = dg.getPreference(facesContext);
        if (preference != null) {
            preference.loadPreference(facesContext, dg);
        }

        TableRenderContext tableContext = createTableContext(facesContext, dg);

        boolean scrollBars = false;

        String cssClassNameSuffix = "";
        String height = dg.getHeight(facesContext);
        String width = dg.getWidth(facesContext);
        int rows = tableContext.getRows();
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

            DataColumnComponent dccs[] = tableContext.listColumns();
            for (int i = 0; i < dccs.length; i++) {
                DataColumnComponent dc = dccs[i];

                if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                    continue;
                }

                String dw = dc.getWidth();
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

                resizable |= dc.isResizable();
            }

            if (resizable && (scrollBars == false || widthNotSpecified)) {
                resizable = false;
            }
        }
        tableContext.setResizable(resizable, totalResize);

        if (tableContext.isDisabled()) {
            cssClassNameSuffix += "_disabled";
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV", dg);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter, cssClassNameSuffix, CSS_ALL_MASK);

        String className = getStyleClassName(componentRenderContext, dg);

        if (cssClassNameSuffix != null) {
            htmlWriter.writeAttribute("v:className", className);
        }

        if (tableContext.selectable) {
            htmlWriter.writeAttribute("v:selectionCardinality",
                    tableContext.selectionCardinality);

            if (tableContext.clientSelectionFullState) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", "true");
            }
        }

        if (tableContext.checkable) {
            htmlWriter.writeAttribute("v:checkCardinality",
                    tableContext.checkCardinality);

            if (tableContext.checkCardinality == ICardinality.ONEMANY_CARDINALITY) {
                int checkedCount = dg.getCheckedRowsCount();

                if (checkedCount > 0) {
                    htmlWriter.writeAttribute("v:checkedCount", checkedCount);
                }
            }

            if (tableContext.clientCheckFullState) {
                htmlWriter.writeAttribute("v:clientCheckFullState", "true");
            }
        }

        if (dg.isReadOnly(facesContext)) {
            htmlWriter.writeAttribute("v:readOnly", "true");
        }
        if (tableContext.isDisabled()) {
            htmlWriter.writeAttribute("v:disabled", "true");
        }
        if (dg.isRequired(facesContext)) {
            htmlWriter.writeAttribute("v:required", "true");
        }

        if (resizable) {
            htmlWriter.writeAttribute("v:resizable", "true");
        }

        Object dataModel = tableContext.getDataModel();
        if (dataModel instanceof IFiltredDataModel) {
            htmlWriter.writeAttribute("v:filtred", "true");

            IFilterProperties filterMap = tableContext.getFiltersMap();
            if (filterMap != null && filterMap.isEmpty() == false) {
                String filterExpression = HtmlTools
                        .encodeFilterExpression(filterMap);
                htmlWriter.writeAttribute("v:filterExpression",
                        filterExpression);
            }
        }

        if (rows > 0) {
            htmlWriter.writeAttribute("v:rows", rows);
        }

        int rowCount = tableContext.getRowCount();
        if (rowCount >= 0) {
            htmlWriter.writeAttribute("v:rowCount", rowCount);
        }

        int first = tableContext.getFirst();
        if (first > 0) {
            htmlWriter.writeAttribute("v:first", first);
        }

        int dataGridPixelWidth = getPixelSize(dg.getWidth(), -1);

        if (ENABLE_SERVER_REQUEST) {
            DataGridService dataGridServer = DataGridService
                    .getInstance(facesContext);
            if (dataGridServer != null) {
                htmlWriter.writeAttribute("v:asyncRender", "true");
            }
        }

        boolean headerVisible = true; // dg.isHeaderVisible(facesContext);

        int tableWidth = 0;
        if (scrollBars) {
            String w = null;
            // dataGridPixelWidth-=2;
            if (dataGridPixelWidth > 0) {
                w = String.valueOf(dataGridPixelWidth) + "px";
            }

            if (headerVisible) {
                htmlWriter.startElement("DIV");
                htmlWriter.writeAttribute("class", className
                        + "_dataTitle_scroll");
                if (w != null) {
                    htmlWriter.writeAttribute("style", "width: " + w);
                }
            }

            tableWidth = encodeFixedHeader(htmlWriter, className, tableContext,
                    dataGridPixelWidth);
            htmlWriter.endElement("DIV");

            htmlWriter.startElement("DIV");
            htmlWriter.writeAttribute("class", className + "_dataBody_scroll");

            ICssWriter cssWriter = new CssWriter(htmlWriter, 32);

            if (height != null) {
                cssWriter
                        .writeHeight(computeSize(height, -1, -getTitleHeight()));
            }
            if (w != null) {
                cssWriter.writeWidth(w);
            }
            cssWriter.close();
        }

        htmlWriter.startElement("TABLE");
        String tableClassName = className + TABLE;

        if (tableContext.isDisabled()) {
            tableClassName += "_disabled";
        }

        htmlWriter.writeAttribute("class", tableClassName);
        // Pas de support CSS de border-spacing pour IE: on est oblig� de le
        // cod� en HTML ...
        htmlWriter.writeAttribute("cellspacing", "0");

        if (tableWidth > 0) {
            int ttw = getPixelSize(dg.getWidth(), -1);
            if (ttw > 0 && ttw > tableWidth) {
                tableWidth = -1;
            }
        }

        if (resizable) {
            // 2 htmlWriter.writeAttribute("width", totalResize);
            htmlWriter.writeAttribute("style", "width:" + totalResize + "px");

        } else if (tableWidth > 0) {
            htmlWriter.writeAttribute("width", tableWidth);

        } else {
            htmlWriter.writeAttribute("width", "100%");
        }

        encodeHeader(htmlWriter, tableContext, !scrollBars);

        encodeFooter(htmlWriter, tableContext);

        htmlWriter.startElement("TBODY");

        encodeBody(htmlWriter, tableContext);

        htmlWriter.endElement("TBODY");

        htmlWriter.endElement("TABLE");

        if (scrollBars) {
            htmlWriter.endElement("DIV");
        }

        htmlWriter.endElement("DIV");

        htmlWriter.enableJavaScript();
    }

    protected int getTitleHeight() {
        return 19;
    }

    protected int encodeFixedHeader(IHtmlWriter htmlWriter, String className,
            TableRenderContext tableContext, int dataGridWidth)
            throws WriterException {

        htmlWriter.startElement("TABLE");
        htmlWriter.writeAttribute("class", className + "_fttitle");
        htmlWriter.writeAttribute("cellpadding", "0");
        htmlWriter.writeAttribute("cellspacing", "0");
        if (tableContext.isResizable()) {
            // OO2: htmlWriter.writeAttribute("width",
            // tableContext.getResizeTotalSize());
            htmlWriter.writeAttribute("style", "width:"
                    + tableContext.getResizeTotalSize() + "px");
        }

        int tableWidth = 0;

        int colStatesCount = tableContext.getColumnStateCount();

        DataColumnComponent dcs[] = tableContext.listColumns();

        for (int i = 0; i < colStatesCount; i++) {
            if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                continue;
            }

            htmlWriter.startElement("COL");

            if (false) {
                String width = dcs[i].getWidth();
                if (width != null) {
                    htmlWriter.writeAttribute("style", "width:" + width + "px");
                }
            }
            htmlWriter.endElement("COL");
        }
        // htmlWriter.startElement("COL");

        htmlWriter.startElement("THEAD");
        htmlWriter.startElement("TR");
        htmlWriter.writeAttribute("class", className + TITLE_ROW);

        boolean first = true;
        for (int i = 0; i < colStatesCount; i++) {
            DataColumnComponent dc = dcs[i];

            if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                continue;
            }

            encodeFixedTitleCol(htmlWriter, className, tableContext, dc, first);
            first = false;

            if (tableWidth < 0) {
                continue;
            }

            int wp = getPixelSize(dc.getWidth(), dataGridWidth);
            if (wp <= 0) {
                continue;
            }

            tableWidth += wp;
        }

        // Fin des titres ....
        htmlWriter.endElement("TR");
        htmlWriter.endElement("THEAD");
        htmlWriter.startElement("TBODY");
        htmlWriter.endElement("TBODY");
        htmlWriter.endElement("TABLE");

        if (tableWidth < 0) {
            return -1;
        }

        return tableWidth;
    }

    private void encodeFixedTitleCol(IHtmlWriter htmlWriter, String className,
            TableRenderContext tableContext, DataColumnComponent dc,
            boolean first) throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement("TH");
        String clName = className + TITLE_CELL;
        if (first) {
            clName += "_left";
        }
        htmlWriter.writeAttribute("class", clName);

        String toolTip = dc.getToolTipText(facesContext);
        if (toolTip != null) {
            htmlWriter.writeAttribute("title", toolTip);
        }

        if (tableContext.isResizable()) {
            if (dc.isResizable(facesContext)) {
                htmlWriter.writeAttribute("v:resizable", "true");

                int min = dc.getMinWidth(facesContext);
                if (min > 0) {
                    htmlWriter.writeAttribute("v:minWidth", min);
                }

                int max = dc.getMaxWidth(facesContext);
                if (max > 0) {
                    htmlWriter.writeAttribute("v:maxWidth", max);
                }
            }
        }

        String width = dc.getWidth(facesContext);
        if (width != null) {
            htmlWriter.writeAttribute("width", width);
        }

        encodeTitleText(htmlWriter, className, tableContext, dc, width);

        htmlWriter.endElement("TH");
    }

    protected void encodeTitleText(IHtmlWriter htmlWriter, String className,
            TableRenderContext tableContext, DataColumnComponent dc,
            String width) throws WriterException {
        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        htmlWriter.startElement("DIV");
        htmlWriter.writeAttribute("class", className + TITLE_STEXT);

        if (width != null) {
            String widthRightPadding = computeSize(width, -1,
                    -TEXT_RIGHT_PADDING);
            htmlWriter.writeAttribute("style", "width: " + widthRightPadding);
        }

        htmlWriter.startElement("DIV");
        htmlWriter.writeAttribute("class", className + TITLE_TTEXT);

        String halign = dc.getAlignment(facesContext);
        if (halign != null) {
            htmlWriter.writeAttribute("align", halign);
        } else {
            htmlWriter.writeAttribute("align", "left");
        }

        if (true) { // SORTER
            String widthRightPadding = computeSize(width, -1,
                    -TEXT_RIGHT_PADDING);
            htmlWriter.writeAttribute("style", "width: " + widthRightPadding);
        }

        String text = dc.getText(facesContext);
        if (text != null && text.trim().length() > 0) {
            htmlWriter.writeText(text);

        } else {
            htmlWriter.write(' ');
            htmlWriter.writeText(ISgmlWriter.NBSP);
            htmlWriter.write(' ');
        }

        htmlWriter.endElement("DIV");

        htmlWriter.endElement("DIV");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#encodeJavaScript(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     */
    protected void encodeJavaScript(IJavaScriptWriter htmlWriter)
            throws WriterException {
        super.encodeJavaScript(htmlWriter);

        TableRenderContext tableContext = (TableRenderContext) htmlWriter
                .getComponentRenderContext().setAttribute(TABLE_CONTEXT, null);

        encodeJsHeader(htmlWriter, tableContext);
        encodeJsBody(htmlWriter, tableContext);
        encodeJsFooter(htmlWriter, tableContext);
    }

    public TableRenderContext createTableContext(FacesContext facesContext,
            DataGridComponent dg) {
        TableRenderContext tableContext = new TableRenderContext(facesContext,
                dg);

        return tableContext;
    }

    public TableRenderContext createTableContext(FacesContext facesContext,
            DataGridComponent dg, int rowIndex,
            ISortedComponent sortedComponents[], String filterExpression) {
        TableRenderContext tableContext = new TableRenderContext(facesContext,
                dg, rowIndex, sortedComponents, filterExpression);

        return tableContext;
    }

    protected void encodeHeader(IHtmlWriter htmlWriter,
            TableRenderContext tableContext, boolean title)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        int columnsStateCount = tableContext.getColumnStateCount();

        DataColumnComponent dcs[] = new DataColumnComponent[columnsStateCount];
        IDataColumnIterator it = tableContext.dataGridComponent.listColumns();
        int is = 0;
        for (int i = 0; i < columnsStateCount; i++) {
            DataColumnComponent dc = it.next();

            if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                continue;
            }

            dcs[is++] = dc;
            encodeTitleCol(htmlWriter, dc);
        }

        if (title) {
            String className = getStyleClassName(componentRenderContext,
                    componentRenderContext.getComponent());
            htmlWriter.startElement("THEAD");
            htmlWriter.startElement("TR");
            htmlWriter.writeAttribute("class", className + TITLE_ROW);

            for (int i = 0; i < is; i++) {
                encodeTitleText(htmlWriter, i, tableContext, dcs[i]);
            }

            htmlWriter.endElement("TR");
            htmlWriter.endElement("THEAD");
        }

        componentRenderContext.setAttribute(TABLE_CONTEXT, tableContext);
    }

    private void encodeTitleCol(IHtmlWriter htmlWriter, DataColumnComponent dc)
            throws WriterException {
        htmlWriter.startElement("COL");

        String width = dc.getWidth();
        if (width != null) {
            // 2: htmlWriter.writeAttribute("width", width);
            htmlWriter.writeAttribute("style", "width:" + width + "px");
        }
        String halign = dc.getAlignment();
        if (halign != null) {
            htmlWriter.writeAttribute("align", halign);
        } else {
            htmlWriter.writeAttribute("align", "left");
        }

        String valign = dc.getVerticalAlign();
        if (valign != null) {
            htmlWriter.writeAttribute("valign", valign);
        }

        String foregroundColor = dc.getForegroundColor();
        String backgroundColor = dc.getBackgroundColor();
        if (foregroundColor != null || backgroundColor != null) {
            StringAppender sb = new StringAppender(128);
            if (foregroundColor != null) {
                sb.append("color:");
                sb.append(foregroundColor);
                sb.append(';');
            }
            if (backgroundColor != null) {
                sb.append("background-color:");
                sb.append(backgroundColor);
                // sb.append(';');
            }

            htmlWriter.writeAttribute("style", sb.toString());
        }

        htmlWriter.endElement("COL");
    }

    protected void encodeTitleText(IHtmlWriter htmlWriter, int number,
            TableRenderContext tableContext, DataColumnComponent dc)
            throws WriterException {
        String className = getStyleClassName(htmlWriter
                .getComponentRenderContext(), tableContext
                .getDataGridComponent());

        htmlWriter.startElement("TH");
        String thClassName = className + TITLE_CELL;
        if (number == 0) {
            thClassName += "_left";
        }
        if (tableContext.isDisabled()) {
            thClassName += "_disabled";
        }
        htmlWriter.writeAttribute("class", thClassName);

        String halign = dc.getAlignment();
        if (halign != null) {
            htmlWriter.writeAttribute("align", halign);
        } else {
            htmlWriter.writeAttribute("align", "left");
        }

        String toolTip = dc.getToolTipText();
        if (toolTip != null) {
            htmlWriter.writeAttribute("title", toolTip);
        }

        encodeTitleText(htmlWriter, className, tableContext, dc, null);

        htmlWriter.endElement("TH");
    }

    protected void encodeBody(IHtmlWriter htmlWriter, TableRenderContext data) {
    }

    protected void encodeFooter(IHtmlWriter htmlWriter, TableRenderContext dg) {
    }

    protected void encodeJsHeader(IJavaScriptWriter htmlWriter,
            TableRenderContext data) {
    }

    protected void encodeJsBody(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext) throws WriterException {

        encodeJsColumns(htmlWriter, tableContext);

        /* Si le tableau n'est pas visible ! */

        if (ENABLE_SERVER_REQUEST) {
            String interactiveComponentClientId = getHtmlRenderContext(
                    htmlWriter.getWriter())
                    .getCurrentInteractiveRenderComponentClientId();

            if (interactiveComponentClientId != null) {
                // Pas de donn�es si nous sommes dans un scope interactif !
                htmlWriter.writeMethodCall("f_setInteractiveShow").write('"')
                        .write(interactiveComponentClientId).writeln("\");");
                return;
            }
        }

        IJavaScriptRenderContext javascriptRenderContext = ((IHtmlRenderContext) htmlWriter
                .getComponentRenderContext().getRenderContext())
                .getJavaScriptRenderContext();

        String rowVarName = javascriptRenderContext.allocateVarName();
        tableContext.setRowVarName(rowVarName);

        encodeJsTransactionalRows(htmlWriter, tableContext, true);

    }

    private void encodeJsColumns(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext) throws WriterException {

        FacesContext facesContext = htmlWriter.getFacesContext();

        int columnsStateCount = tableContext.getColumnStateCount();

        DataGridComponent dataGridComponent = tableContext
                .getDataGridComponent();

        htmlWriter.writeMethodCall("f_setColumns");
        boolean first = true;
        for (int i = 0; i < columnsStateCount; i++) {
            if (first) {
                first = false;

            } else {
                htmlWriter.write(',');
            }

            String columnId = tableContext.getColumnId(i);
            if (columnId != null) {
                htmlWriter.writeString(columnId).write(',');
            }

            int rowState = tableContext.getColumnState(i);
            if (rowState == TableRenderContext.SERVER_HIDDEN) {
                htmlWriter.writeNull();
                continue;
            }

            if (rowState == TableRenderContext.CLIENT_HIDDEN) {
                htmlWriter.writeBoolean(false);
                continue;
            }

            htmlWriter.writeBoolean(true);
        }
        htmlWriter.writeln(");");

        IDataColumnIterator it = dataGridComponent.listColumns();
        String urls[] = null;
        String clazz[] = null;
        int cnt = it.count();
        StringAppender autoFilter = null;
        DataColumnComponent rowValueColumn = tableContext.getRowValueColumn();
        for (int i = 0; it.hasNext(); i++) {
            DataColumnComponent dataColumnComponent = it.next();

            String url = dataColumnComponent
                    .getDefaultCellImageURL(facesContext);
            if (url != null && url.length() > 0) {
                if (urls == null) {
                    urls = new String[cnt];
                }
                urls[i] = htmlWriter.allocateString(url);
            }

            String claz = dataColumnComponent.getStyleClass(facesContext);
            if (claz != null && claz.length() > 0) {
                if (clazz == null) {
                    clazz = new String[cnt];
                }
                clazz[i] = claz;
            }

            if (rowValueColumn == dataColumnComponent) {
                htmlWriter.writeMethodCall("f_setRowValueColumn").write(
                        String.valueOf(i)).writeln(");");
            }

            if (dataColumnComponent.isAutoFilter(facesContext)) {
                if (autoFilter == null) {
                    autoFilter = new StringAppender(128);
                } else {
                    autoFilter.append(", ");
                }

                autoFilter.append(String.valueOf(i));
            }
        }

        if (autoFilter != null) {
            htmlWriter.writeMethodCall("f_setAutoFilters").write(
                    autoFilter.toString()).writeln(");");
        }

        if (urls != null || tableContext.hasColumnImages()) {
            htmlWriter.writeMethodCall("f_setColumnsImages");
            int pred = 0;
            first = true;
            for (int i = 0; i < cnt; i++) {
                if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                    continue;
                }

                String url = null;
                // URL des images par defaut !
                if (urls != null) {
                    url = urls[i];
                }

                boolean cellImage = tableContext.isColumnImageURL(i);
                // cellImage=false: une image par d�faut est peut etre
                // positionn�e !
                if (url == null && cellImage == false) {
                    pred++;
                    continue;
                }

                if (pred > 0) {
                    // Si il y avait des pred avant, ce sont forcement des
                    // colonnes qui n'ont pas d'image/cellule
                    if (first) {
                        pred--;
                        htmlWriter.writeBoolean(false).write(',').writeNull();
                        first = false;
                    }

                    for (; pred > 0; pred--) {
                        htmlWriter.write(',').writeBoolean(false).write(',')
                                .writeNull();
                    }
                }

                if (first) {
                    first = false;

                } else {
                    htmlWriter.write(',');
                }

                htmlWriter.writeBoolean(cellImage).write(',');

                if (url != null) {
                    htmlWriter.write(url);

                } else {
                    htmlWriter.writeNull();
                }
            }
            htmlWriter.writeln(");");
        }

        if (clazz != null) {
            List sc = new ArrayList(cnt);

            int total = 0;
            for (int i = 0; i < cnt; i++) {
                if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                    continue;
                }

                String claz = clazz[i];
                if (claz == null) {
                    sc.add(null);
                    continue;
                }

                if (claz.indexOf(',') >= 0 || claz.indexOf(';') >= 0
                        || claz.indexOf(' ') >= 0) {
                    StringTokenizer st = new StringTokenizer(claz, ",; ");
                    if (st.countTokens() > 1) {
                        String claz1 = st.nextToken();
                        claz1 = htmlWriter.allocateString(claz1);

                        String claz2 = st.nextToken();
                        claz2 = htmlWriter.allocateString(claz2);

                        sc.add(claz1);
                        sc.add(claz2);
                        total += 2;
                        continue;
                    }

                    claz = st.nextToken();
                }

                claz = htmlWriter.allocateString(claz);
                sc.add(claz);
                sc.add(Boolean.FALSE);
                total++;
            }

            if (total > 0) {
                htmlWriter.writeMethodCall("f_setColumnsStyleClass");
                int pred = 0;
                first = true;
                for (Iterator it2 = sc.iterator(); it2.hasNext();) {
                    Object tok = it2.next();

                    if (tok == null) {
                        pred++;
                        continue;
                    }

                    for (; pred > 0; pred--) {
                        if (first == false) {
                            htmlWriter.write(',');
                        } else {
                            first = false;
                        }

                        htmlWriter.writeNull();
                    }

                    if (first == false) {
                        htmlWriter.write(',');

                    } else {
                        first = false;
                    }

                    if (tok == Boolean.FALSE) {
                        htmlWriter.writeBoolean(false);
                        continue;
                    }

                    htmlWriter.write((String) tok);
                }
                htmlWriter.writeln(");");
            }
        }

        if (tableContext.hasCellStyleClass()) {
            htmlWriter.writeMethodCall("f_setColumnsCellStyle");
            writeBooleanArray(htmlWriter, tableContext, cnt, CELL_STYLE_CLASS);
            htmlWriter.writeln(");");
        }

        if (tableContext.hasCellToolTipText()) {
            htmlWriter.writeMethodCall("f_setColumnsToolTipText");
            writeBooleanArray(htmlWriter, tableContext, cnt, CELL_TOOLTIP_TEXT);
            htmlWriter.writeln(");");
        }

        if (tableContext.sortClientSide != null) {
            int pred = 0;

            htmlWriter.writeMethodCall("f_setColumnSorters");
            // int cnt = tableContext.getColumnCount();
            first = true;
            for (int i = 0; i < cnt; i++) {

                Object sort = tableContext.sortCommand[i];

                if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
                    continue;
                }

                if (sort == null) {
                    pred++;
                    continue;
                }

                if (sort instanceof String) {
                    String aliasCommand = (String) sort;

                    if (tableContext.sortClientSide[i] == false) {
                        aliasCommand = (String) SORT_ALIASES
                                .get(ISortEventCapability.SORT_SERVER);
                    }

                    for (; pred > 0; pred--) {
                        if (first == false) {
                            htmlWriter.write(',');
                        } else {
                            first = false;
                        }

                        htmlWriter.writeNull();
                    }

                    if (first == false) {
                        htmlWriter.write(',');
                    } else {
                        first = false;
                    }
                    htmlWriter.writeSymbol(aliasCommand); // .write('\"');

                    continue;
                }

                if (sort instanceof IScriptListener) {
                    IScriptListener scriptListener = (IScriptListener) sort;

                    for (; pred > 0; pred--) {
                        if (first == false) {
                            htmlWriter.write(',');
                        } else {
                            first = false;
                        }
                        htmlWriter.writeNull();
                    }

                    if (first == false) {
                        htmlWriter.write(',');
                    } else {
                        first = false;
                    }

                    EventsRenderer.encodeJavaScriptCommmand(htmlWriter,
                            scriptListener);
                    // On envoie que le premier valide !
                    continue;
                }

                if (sort instanceof IServerActionListener) {
                    // Le tri se fait cot� serveur !

                    for (; pred > 0; pred--) {
                        if (first == false) {
                            htmlWriter.write(',');
                        } else {
                            first = false;
                        }
                        htmlWriter.writeNull();
                    }

                    String aliasCommand = (String) SORT_ALIASES
                            .get(ISortEventCapability.SORT_SERVER);

                    if (first == false) {
                        htmlWriter.write(',');
                    } else {
                        first = false;
                    }

                    htmlWriter.writeString(aliasCommand);

                    continue;

                }
                // Ben y a pas de listener SCRIPT !
                pred++;
            }
            htmlWriter.writeln(");");

            ISortedComponent sortedComponents[] = tableContext
                    .listSortedComponents();
            if (sortedComponents.length > 0) {
                htmlWriter.writeMethodCall("f_enableSorters");
                pred = 0;
                for (int j = 0; j < sortedComponents.length; j++) {
                    ISortedComponent sortedComponent = sortedComponents[j];

                    if (j > 0) {
                        for (; pred > 0; pred--) {
                            htmlWriter.write(',').writeNull();
                        }

                        htmlWriter.write(',');
                    }
                    htmlWriter.writeInt(sortedComponent.getIndex());

                    if (sortedComponent.isAscending()) {
                        htmlWriter.write(',').writeBoolean(true);

                    } else {
                        pred++;
                    }
                }
                htmlWriter.writeln(");");
            }
        }
    }

    private interface IBooleanStateCallback {
        boolean test(TableRenderContext tableContext, int index);
    }

    private static final IBooleanStateCallback CELL_STYLE_CLASS = new IBooleanStateCallback() {
        private static final String REVISION = "$Revision$";

        public boolean test(TableRenderContext tableContext, int index) {
            return tableContext.isCellStyleClass(index);
        }
    };

    private static final IBooleanStateCallback CELL_TOOLTIP_TEXT = new IBooleanStateCallback() {
        private static final String REVISION = "$Revision$";

        public boolean test(TableRenderContext tableContext, int index) {
            return tableContext.isCellToolTipText(index);
        }
    };

    private void writeBooleanArray(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext, int cnt,
            IBooleanStateCallback callback) throws WriterException {
        int pred = 0;
        boolean first = true;
        for (int i = 0; i < cnt; i++) {

            if (tableContext.getColumnState(i) != TableRenderContext.VISIBLE) {
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

    public void encodeJsTransactionalRows(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext, boolean sendFullStates)
            throws WriterException {

        DataModel dataModel = tableContext.getDataModel();

        if ((dataModel instanceof ITransactionalDataModel) == false) {
            encodeJsRows(htmlWriter, tableContext, sendFullStates);
            return;
        }

        ITransactionalDataModel transactionalDataModel = (ITransactionalDataModel) dataModel;

        try {
            transactionalDataModel.enableTransactionalObjects(true);

            encodeJsRows(htmlWriter, tableContext, sendFullStates);

            return;

        } finally {
            transactionalDataModel.enableTransactionalObjects(false);
        }
    }

    private void encodeJsRows(IJavaScriptWriter jsWriter,
            TableRenderContext tableContext, boolean sendFullStates)
            throws WriterException {

        FacesContext facesContext = jsWriter.getFacesContext();

        DataGridComponent dataGridComponent = tableContext
                .getDataGridComponent();
        DataModel dataModel = tableContext.getDataModel();

        boolean filtred = false;
        int firstCount = tableContext.getRowCount();

        IFilterProperties filtersMap = tableContext.getFiltersMap();
        if (filtersMap != null) {
            if (dataModel instanceof IFiltredDataModel) {
                IFiltredDataModel filtredDataModel = (IFiltredDataModel) dataModel;

                filtredDataModel.setFilter(filtersMap);
                tableContext.updateRowCount();

                filtred = true;

            } else {
                dataModel = FilteredDataModel.filter(dataModel, filtersMap);
                tableContext.updateRowCount();
            }

        } else if (dataModel instanceof IFiltredDataModel) {
            IFiltredDataModel filtredDataModel = (IFiltredDataModel) dataModel;

            filtredDataModel.setFilter(FilterExpressionTools.EMPTY);
            tableContext.updateRowCount();

            filtred = true;
        }

        int rows = tableContext.getRows();

        boolean searchEnd = (rows > 0);
        // int firstCount = -1;
        int count = -1;

        if (searchEnd) {
            count = firstCount;
        }

        int sortTranslations[] = null;

        ISortedComponent sortedComponents[] = tableContext
                .listSortedComponents();
        if (sortedComponents != null && sortedComponents.length > 0) {
            if (dataModel instanceof ISortedDataModel) {
                // On delegue au modele, le tri !

                // Nous devons �tre OBLIGATOIREMENT en mode rowValueColumnId
                if (tableContext.getRowValueColumn() == null) {
                    throw new FacesException(
                            "Can not sort dataModel without attribute rowValueColumnId specified !");
                }

                ((ISortedDataModel) dataModel).setSortParameters(
                        dataGridComponent, sortedComponents);
            } else {
                // Il faut faire le tri à la main !

                sortTranslations = DataGridServerSort.computeSortedTranslation(
                        jsWriter.getFacesContext(), dataGridComponent,
                        dataModel, sortedComponents);
            }

            // Apres le tri, on connait peu etre la taille
            tableContext.updateRowCount();
        } else {

            if (dataModel instanceof ISortedDataModel) {
                // Reset des parametres de tri !
                ((ISortedDataModel) dataModel).setSortParameters(
                        dataGridComponent, null);
            }
        }

        int rowIndex = tableContext.getFirst();

        int selectedIndexes[] = null;
        int selectedOffset = -1;
        Set selectedObjects = null;

        if (tableContext.selectable
                && (tableContext.clientSelectionFullState == false || sendFullStates)) {

            Object selectionModel = dataGridComponent
                    .getSelectedValues(facesContext);

            if (selectionModel != null) {
                if (selectionModel instanceof IIndexesModel) {
                    selectedIndexes = ((IIndexesModel) selectionModel)
                            .listSortedIndexes();

                    if (tableContext.clientSelectionFullState) {
                        writeFullStates(jsWriter, "_setSelectionStates",
                                selectedIndexes);
                        selectedIndexes = null;

                    } else {
                        if (sortTranslations == null) {
                            // Dans le cas ou il n'y a pas de tri
                            // Les indexes sont lineaires ...

                            if (selectedIndexes != null
                                    && selectedIndexes.length > 0) {
                                // Recherche du premier RowOffset
                                for (int i = 0; i < selectedIndexes.length; i++) {
                                    if (selectedIndexes[i] >= rowIndex) {
                                        selectedOffset = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    selectedObjects = convertSelection(selectionModel);

                    if (tableContext.clientSelectionFullState) {
                        writeFullStates(jsWriter, tableContext,
                                "_setSelectionStates", selectedObjects);
                        selectedObjects = null;
                    }
                }
            }
        }

        int checkedIndexes[] = null;
        int checkedOffset = -1;
        Set checkedObjects = null;

        if (tableContext.checkable
                && (tableContext.clientCheckFullState == false || sendFullStates)) {

            Object checkModel = dataGridComponent
                    .getCheckedValues(facesContext);
            if (checkModel != null) {
                if (checkModel instanceof IIndexesModel) {
                    checkedIndexes = ((IIndexesModel) checkModel)
                            .listSortedIndexes();

                    if (tableContext.clientCheckFullState) {
                        writeFullStates(jsWriter, "_setCheckStates",
                                checkedIndexes);
                        checkedIndexes = null;

                    } else {
                        if (sortTranslations == null) {
                            // Dans le cas ou il n'y a pas de tri
                            // Les indexes sont donc lineaires ...

                            if (checkedIndexes != null
                                    && checkedIndexes.length > 0) {
                                // Recherche du premier RowOffset
                                for (int i = 0; i < checkedIndexes.length; i++) {
                                    if (checkedIndexes[i] >= rowIndex) {
                                        checkedOffset = i;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                } else {
                    checkedObjects = convertSelection(checkModel);

                    if (tableContext.clientCheckFullState) {
                        writeFullStates(jsWriter, tableContext,
                                "_setCheckStates", checkedObjects);
                        checkedObjects = null;
                    }
                }
            }
        }

        IDataColumnIterator it = dataGridComponent.listColumns();
        boolean testImageUrls[] = new boolean[it.count()];
        for (int i = 0; it.hasNext(); i++) {
            DataColumnComponent dataColumnComponent = it.next();
            testImageUrls[i] = dataColumnComponent.isCellImageURLSetted();
        }

        if (sortTranslations == null && rows > 0
                && (dataModel instanceof IRangeDataModel)) {
            // Specifie le range que si il n'y a pas de tri !

            int rangeLength = rows;
            if (searchEnd) {
                // On regardera si il y a bien une suite ...
                rangeLength++;
            }

            ((IRangeDataModel) dataModel).setRowRange(rowIndex, rangeLength);
        }

        // On recherche la taille ?
        if (searchEnd) {
            count = tableContext.getRowCount();

            // Le tri a été fait coté serveur,
            // On connait peut être le nombre d'elements !
            if (count < 0 && sortTranslations != null) {
                count = sortTranslations.length;
            }

            if (count >= 0) {
                searchEnd = false;
            }
        }

        Map varContext = facesContext.getExternalContext().getRequestMap();
        String rowCountVar = tableContext.getRowCountVar();
        if (rowCountVar != null) {
            varContext.put(rowCountVar, new Integer(count));
        }

        String rowIndexVar = tableContext.getRowIndexVar();

        try {
            boolean selected = false;
            boolean checked = false;
            String rowId = null;

            DataColumnComponent rowValueColumn = tableContext
                    .getRowValueColumn();

            for (int i = 0;; i++) {
                if (searchEnd == false) {
                    // Pas de recherche de la fin !
                    // On peut sortir tout de suite ...
                    if (rows > 0 && i >= rows) {
                        break;
                    }
                }

                int translatedRowIndex = rowIndex;

                if (rowValueColumn != null) {
                    if (sortTranslations != null) {
                        if (rowIndex >= sortTranslations.length) {
                            break;
                        }

                        translatedRowIndex = sortTranslations[rowIndex];
                    }

                } else {

                    if (sortTranslations == null) {
                        if (selectedOffset >= 0) {
                            if (selectedIndexes[selectedOffset] == rowIndex) {
                                selected = true;

                                selectedOffset++;

                            } else {
                                selected = false;
                            }
                        }
                        if (checkedOffset >= 0) {
                            if (checkedIndexes[checkedOffset] == rowIndex) {
                                checked = true;

                                checkedOffset++;

                            } else {
                                checked = false;
                            }
                        }
                    } else {
                        if (rowIndex >= sortTranslations.length) {
                            break;
                        }

                        translatedRowIndex = sortTranslations[rowIndex];

                        if (selectedIndexes != null) {
                            selected = false;

                            for (int j = 0; j < selectedIndexes.length; j++) {
                                if (selectedIndexes[j] != translatedRowIndex) {
                                    continue;
                                }

                                selected = true;
                                break;
                            }
                        }
                        if (checkedIndexes != null) {
                            checked = false;
                            for (int j = 0; j < checkedIndexes.length; j++) {
                                if (checkedIndexes[j] != translatedRowIndex) {
                                    continue;
                                }

                                checked = true;
                                break;
                            }
                        }
                    }
                }

                dataGridComponent.setRowIndex(translatedRowIndex);
                if (dataGridComponent.isRowAvailable() == false) {
                    count = rowIndex;
                    break;
                }

                if (rowIndexVar != null) {
                    varContext.put(rowIndexVar, new Integer(i));
                }

                if (rowValueColumn != null) {
                    Object value = rowValueColumn.getValue(facesContext);
                    if (value != null) {
                        rowId = convertValue(facesContext, rowValueColumn,
                                value);

                        if (checkedObjects != null) {
                            checked = checkedObjects.contains(value);
                        }
                        if (selectedObjects != null) {
                            selected = selectedObjects.contains(value);
                        }
                    }

                    if (rowId == null) {
                        throw new FacesException("Value of row #" + rowIndex
                                + " is null !");
                    }
                }

                if (searchEnd) {
                    // On teste juste la validité de la fin !
                    if (rows > 0 && i >= rows) {
                        break;
                    }
                }

                encodeJsRow(jsWriter, tableContext, i, rowId,
                        translatedRowIndex, selected, checked);

                if (sortTranslations == null) {
                    if (selectedOffset >= 0
                            && selectedOffset >= selectedIndexes.length) {
                        selectedOffset = -1;
                        selected = false;
                    }
                    if (checkedOffset >= 0
                            && checkedOffset >= checkedIndexes.length) {
                        checkedOffset = -1;
                        checked = false;
                    }
                }

                rowIndex++;
            }

        } finally {
            dataGridComponent.setRowIndex(-1);

            if (rowCountVar != null) {
                varContext.remove(rowCountVar);
            }

            if (rowIndexVar != null) {
                varContext.remove(rowIndexVar);
            }
        }

        // Le count a évolué ?
        // 2 solutions:
        // * mode page par page, nous sommes à la fin, ou il y a eu un tri
        // * en mode liste, le dataModel ne pouvait pas encore donner le nombre
        // de rows
        if (rows > 0) {
            if (count > firstCount
                    || (dataGridComponent.getFirst() == 0 && count == 0)) {
                encodeJsRowCount(jsWriter, tableContext, count);
            }

        } else if (tableContext.getRowCount() < 0) {
            encodeJsRowCount(jsWriter, tableContext, rowIndex);

        } else if (filtred) {
            if (searchEnd && count == 0) {
                encodeJsRowCount(jsWriter, tableContext, count);
            }
        }
    }

    protected void encodeJsRowCount(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext, int count) throws WriterException {
        htmlWriter.writeMethodCall("f_setRowCount").writeInt(count).writeln(
                ");");
    }

    protected void encodeJsRow(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext, int index, String rowId,
            int iRowId, boolean selected, boolean checked)
            throws WriterException {
        FacesContext facesContext = htmlWriter.getFacesContext();
        DataColumnComponent dcs[] = tableContext.listColumns();
        int columnNumber = dcs.length;

        String rowVarName = tableContext.getRowVarName();

        String values[] = null;
        if (ALLOCATE_ROW_STRINGS) {
            values = new String[columnNumber];
        }

        if (values == null) {
            if (index < 1) {
                htmlWriter.write("var ");
            }
            htmlWriter.write(rowVarName).write('=').writeMethodCall("_addRow");

            if (rowId != null) {
                htmlWriter.writeString(rowId);

            } else {
                htmlWriter.writeInt(iRowId);
            }

            if (tableContext.selectable
                    && tableContext.clientSelectionFullState == false) {
                htmlWriter.write(',').writeBoolean(selected);
            }

            if (tableContext.checkable
                    && tableContext.clientCheckFullState == false) {
                htmlWriter.write(',').writeBoolean(checked);
            }
        }

        String images[] = null;
        String cellStyleClasses[] = null;
        String cellToolTipTexts[] = null;
        int visibleColumns = 0;

        int ciIndex = 0;
        int csIndex = 0;
        int ctIndex = 0;
        for (int i = 0; i < columnNumber; i++) {
            DataColumnComponent dc = dcs[i];

            int rowState = tableContext.getColumnState(i);
            if (rowState == TableRenderContext.SERVER_HIDDEN) {
                continue;
            }

            if (rowId == null || tableContext.getRowValueColumn() != dc) {
                String svalue = null;

                Object value = dc.getValue(facesContext);
                if (value != null) {
                    svalue = convertValue(facesContext, dc, value);
                }

                if (values != null) {
                    if (svalue == null) {
                        svalue = NULL_VALUE;
                    }

                    values[i] = svalue;

                } else {
                    htmlWriter.write(',').writeString(svalue);
                }
            }

            if (rowState != TableRenderContext.VISIBLE) {
                continue;
            }

            if (tableContext.isColumnImageURL(i)) {
                String imageURL = dc.getCellImageURL(facesContext);
                if (imageURL != null) {
                    if (images == null) {
                        images = new String[columnNumber];
                    }
                    images[ciIndex] = imageURL;
                }

                ciIndex++;
            }

            if (tableContext.isCellStyleClass(i)) {
                String cs = dc.getCellStyleClass(facesContext);
                if (cs != null) {
                    if (cellStyleClasses == null) {
                        cellStyleClasses = new String[columnNumber];
                    }

                    cellStyleClasses[csIndex] = cs;
                }
                csIndex++;
            }

            if (tableContext.isCellToolTipText(i)) {
                String ct = dc.getCellToolTipText(facesContext);
                if (ct != null) {
                    if (cellToolTipTexts == null) {
                        cellToolTipTexts = new String[columnNumber];
                    }

                    cellToolTipTexts[ctIndex] = ct;
                }
                ctIndex++;
            }

            visibleColumns++;
        }

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String v = values[i];
                if (v == null || v == NULL_VALUE) {
                    continue;
                }

                values[i] = htmlWriter.allocateString(v);
            }

            if (index < 1) {
                htmlWriter.write("var ");
            }
            htmlWriter.write(rowVarName).write('=').writeMethodCall("_addRow");

            if (rowId != null) {
                htmlWriter.writeString(rowId);

            } else {
                htmlWriter.writeInt(iRowId);
            }

            if (tableContext.selectable) {
                htmlWriter.write(',').writeBoolean(selected);
            }

            if (tableContext.checkable) {
                htmlWriter.write(',').writeBoolean(checked);
            }

            for (int i = 0; i < values.length; i++) {
                String v = values[i];
                if (v == null) {
                    continue;
                }

                htmlWriter.write(',');
                if (v == NULL_VALUE) {
                    htmlWriter.writeNull();
                    continue;
                }

                htmlWriter.write(v);
            }

        }
        htmlWriter.writeln(");");

        if (images != null) {
            writeRowProperties(htmlWriter, tableContext, ciIndex, rowVarName,
                    "f_setCellImages", images);
        }
        if (cellStyleClasses != null) {
            writeRowProperties(htmlWriter, tableContext, csIndex, rowVarName,
                    "f_setCellStyleClass", cellStyleClasses);
        }
        if (cellToolTipTexts != null) {
            writeRowProperties(htmlWriter, tableContext, ctIndex, rowVarName,
                    "f_setCellToolTipText", cellToolTipTexts);
        }
    }

    protected void writeRowProperties(IJavaScriptWriter htmlWriter,
            TableRenderContext tableContext, int visibleColumns, String varId,
            String command, String values[]) throws WriterException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                continue;
            }

            values[i] = htmlWriter.allocateString(values[i]);
        }

        htmlWriter.writeMethodCall(command).write(varId);
        int pred = 0;
        for (int i = 0; i < visibleColumns; i++) {
            if (values[i] == null) {
                pred++;
                continue;
            }

            if (pred > 0) {
                for (; pred > 0; pred--) {
                    htmlWriter.write(',').writeNull();
                }
            }

            htmlWriter.write(',').write(values[i]);
        }

        htmlWriter.writeln(");");
    }

    protected void encodeJsFooter(IJavaScriptWriter htmlWriter,
            TableRenderContext data) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#getDecodesChildren()
     */
    public boolean getDecodesChildren() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#getRendersChildren()
     */
    public boolean getRendersChildren() {
        return true;
    }

    /*
     * public void encodeChildren(FacesContext context, UIComponent component) { //
     * Il faut bloquer l'encodage de l'interieur ! }
     */

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        DataGridComponent dg = (DataGridComponent) component;

        Number first = componentData.getNumberProperty("first");
        if (first != null) {
            int old = dg.getFirst();

            int f = first.intValue();
            if (old != f) {
                dg.setFirst(f);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.FIRST, new Integer(old), first));
            }
        }

        DataColumnComponent rowValueColumn = null;
        String rowValueColumnId = dg.getRowValueColumnId(facesContext);
        if (rowValueColumnId != null) {
            for (IDataColumnIterator it = dg.listColumns(); it.hasNext();) {
                DataColumnComponent column = it.next();
                if (rowValueColumnId.equals(column.getId()) == false) {
                    continue;
                }

                rowValueColumn = column;
                break;
            }
        }

        String selectedRows = componentData.getStringProperty("selectedItems");
        String deselectedRows = componentData
                .getStringProperty("deselectedItems");
        if (selectedRows != null || deselectedRows != null) {
            if (rowValueColumn != null) {
                Object selected = dg.getSelectedValues(facesContext);

                selected = updateSelection(facesContext, rowValueColumn,
                        selected, selectedRows, deselectedRows);

                dg.setSelectedValues(selected);

            } else {
                int indexes[] = parseIndexes(selectedRows);
                int dindexes[] = null;
                boolean all = false;

                if (HtmlTools.ALL_VALUE.equals(deselectedRows)) {
                    all = true;
                    dindexes = EMPTY_INDEXES;
                } else {
                    dindexes = parseIndexes(deselectedRows);
                }

                if (indexes.length > 0 || all || dindexes.length > 0) {
                    setSelectedIndexes(facesContext, dg, indexes, dindexes, all);
                }
            }
        }
        String checkedRows = componentData.getStringProperty("checkedItems");
        String uncheckedRows = componentData
                .getStringProperty("uncheckedItems");
        if (checkedRows != null || uncheckedRows != null) {
            if (rowValueColumn != null) {
                Object checked = dg.getCheckedValues(facesContext);

                checked = updateSelection(facesContext, rowValueColumn,
                        checked, checkedRows, uncheckedRows);

                dg.setCheckedValues(checked);

            } else {
                int cindexes[] = parseIndexes(checkedRows);
                int uindexes[] = null;
                boolean all = false;

                if (HtmlTools.ALL_VALUE.equals(uncheckedRows)) {
                    all = true;
                    uindexes = EMPTY_INDEXES;
                } else {
                    uindexes = parseIndexes(uncheckedRows);
                }

                if (cindexes.length > 0 || uindexes.length > 0 || all) {
                    setCheckedIndexes(facesContext, dg, cindexes, uindexes, all);
                }
            }
        }

        String sortIndex = componentData.getStringProperty("sortIndex");
        if (sortIndex != null) {
            DataColumnComponent columns[] = dg.listColumns().toArray();

            List sortedColumns = new ArrayList(columns.length);
            StringTokenizer st1 = new StringTokenizer(sortIndex, ",");

            for (; st1.hasMoreTokens();) {
                String tok1 = st1.nextToken();
                String tok2 = st1.nextToken();

                int idx = Integer.parseInt(tok1);
                boolean order = "true".equalsIgnoreCase(tok2);

                DataColumnComponent dataColumn = columns[idx];

                sortedColumns.add(dataColumn);

                if (dataColumn.isAscending(facesContext) == order) {
                    continue;
                }

                dataColumn.setAscending(order);

                dataColumn.queueEvent(new PropertyChangeEvent(dataColumn,
                        Properties.ASCENDING, Boolean.valueOf(!order), Boolean
                                .valueOf(order)));
            }

            String old = dg.getSortedColumnIds(facesContext);

            if (dg.setSortedColumns((DataColumnComponent[]) sortedColumns
                    .toArray(new DataColumnComponent[sortedColumns.size()]))) {

                String newIds = dg.getSortedColumnIds(facesContext);
                if (isEquals(old, newIds) == false) {
                    component.queueEvent(new PropertyChangeEvent(component,
                            Properties.SORTED_COLUMN_IDS, old, newIds));
                }
            }
        }

        String columnWidths = componentData.getStringProperty("columnWidths");
        if (columnWidths != null) {
            StringTokenizer st = new StringTokenizer(columnWidths, ",");
            IDataColumnIterator it = dg.listColumns();

            for (; st.hasMoreTokens();) {
                String width = st.nextToken();

                for (; it.hasNext();) {
                    DataColumnComponent col = it.next();

                    if (col.isResizable(facesContext) == false) {
                        continue;
                    }

                    String old = col.getWidth(facesContext);
                    if (isEquals(old, width)) {
                        break;
                    }

                    col.setWidth(width);

                    col.queueEvent(new PropertyChangeEvent(col,
                            Properties.WIDTH, old, width));
                    break;
                }
            }
        }

        String filterExpression = componentData
                .getStringProperty("filterExpression");
        if (filterExpression != null) {
            if (filterExpression.length() < 1) {
                filterExpression = null;
            }

            IFilterProperties fexp = HtmlTools.decodeFilterExpression(
                    component, filterExpression);

            IFilterProperties old = dg.getFilterProperties(facesContext);
            if (isEquals(fexp, old) == false) {
                dg.setFilterProperties(fexp);

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.FILTER_PROPERTIES, old, fexp));
            }

        }

        IComponentPreference preference = dg.getPreference(facesContext);
        if (preference != null) {
            preference.savePreference(facesContext, dg);
        }
    }

    private Set convertSelection(Object selection) {
        if (selection instanceof Object[]) {
            return new HashSet(Arrays.asList((Object[]) selection));
        }

        if (selection instanceof Collection) {
            return new HashSet((Collection) selection);
        }

        throw new FacesException(
                "Bad type of value for attribute selectedValues/checkedValues !");
    }

    private Object updateSelection(FacesContext facesContext,
            DataColumnComponent dataColumnComponent, Object selected,
            String selectedRows, String deselectedRows) {
        Set set;

        if (selected == null) {
            set = new HashSet(8);

        } else {
            set = convertSelection(selected);
        }

        if (HtmlTools.ALL_VALUE.equals(deselectedRows)) {
            set.clear();

        } else if (set.size() > 1 && deselectedRows != null
                && deselectedRows.length() > 0) {
            List deselect = HtmlValuesTools.parseValues(facesContext,
                    dataColumnComponent, true, false, deselectedRows);

            if (deselect.isEmpty() == false) {
                set.removeAll(deselect);
            }
        }

        if (selectedRows != null && selectedRows.length() > 0) {
            List select = HtmlValuesTools.parseValues(facesContext,
                    dataColumnComponent, true, false, selectedRows);

            if (select.isEmpty() == false) {
                set.addAll(select);
            }

        }

        return set.toArray();
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
            DataGridComponent dg, int[] indexes, int uindexes[], boolean all) {
        IIndexesModel model = (IIndexesModel) dg.getCheckedValues(facesContext);
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
            DataGridComponent dg, int[] indexes, int dindexes[],
            boolean deselectAll) {
        IIndexesModel model = (IIndexesModel) dg
                .getSelectedValues(facesContext);
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

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class TableRenderContext {

        private static final String REVISION = "$Revision$";

        public static final int SERVER_HIDDEN = 1;

        public static final int CLIENT_HIDDEN = 2;

        public static final int VISIBLE = 3;

        private final DataGridComponent dataGridComponent;

        private final boolean clientSelectionFullState;

        private final boolean clientCheckFullState;

        private final boolean checkable;

        private final int checkCardinality;

        private final boolean selectable;

        private final int selectionCardinality;

        private final boolean disabled;

        private final String rowIndexVar;

        private final String rowCountVar;

        private boolean hasCellStyleClass;

        private final boolean cellStyleClasses[];

        private boolean hasCellToolTipText;

        private final boolean cellToolTipText[];

        private final int columnStates[];

        private String rowVarName;

        private boolean hasColumnImages;

        private ISortedComponent sortedComponents[];

        private final int rows;

        private boolean resizable;

        private int first;

        private int rowCount;

        private final boolean columnImageURLs[];

        private boolean sortClientSide[];

        private Object sortCommand[];

        private String columnIds[];

        private DataModel dataModel;

        private IFilterProperties filtersMap;

        private DataColumnComponent columns[];

        private DataColumnComponent rowValueColumn;

        private int totalSize;

        private TableRenderContext(FacesContext facesContext,
                DataGridComponent dataGridComponent,
                ISortedComponent sortedComponents[]) {
            this.dataGridComponent = dataGridComponent;
            this.sortedComponents = sortedComponents;

            selectable = dataGridComponent.isSelectable(facesContext);
            if (selectable) {
                int selectionCardinality = dataGridComponent
                        .getSelectionCardinality(facesContext);
                if (selectionCardinality == 0) {
                    selectionCardinality = ICardinality.DEFAULT_CARDINALITY;
                }
                this.selectionCardinality = selectionCardinality;

                this.clientSelectionFullState = dataGridComponent
                        .isClientSelectionFullState(facesContext);
            } else {
                this.selectionCardinality = 0;
                this.clientSelectionFullState = false;
            }

            checkable = dataGridComponent.isCheckable(facesContext);
            if (checkable) {
                int checkCardinality = dataGridComponent
                        .getCheckCardinality(facesContext);
                if (checkCardinality == 0) {
                    checkCardinality = ICardinality.DEFAULT_CARDINALITY;
                }
                this.checkCardinality = checkCardinality;

                this.clientCheckFullState = dataGridComponent
                        .isClientCheckFullState(facesContext);
            } else {
                this.checkCardinality = 0;
                this.clientCheckFullState = false;
            }

            disabled = dataGridComponent.isDisabled(facesContext);

            rows = dataGridComponent.getRows();

            rowCountVar = dataGridComponent.getRowCountVar(facesContext);

            rowIndexVar = dataGridComponent.getRowIndexVar(facesContext);

            String rowValueColumnId = dataGridComponent
                    .getRowValueColumnId(facesContext);

            IDataColumnIterator itColumns = dataGridComponent.listColumns();
            List cs = new ArrayList(itColumns.count());
            for (; itColumns.hasNext();) {
                DataColumnComponent dataColumnComponent = itColumns.next();

                cs.add(dataColumnComponent);

                if (rowValueColumnId != null && rowValueColumn == null) {
                    if (rowValueColumnId.equals(dataColumnComponent.getId())) {
                        rowValueColumn = dataColumnComponent;
                    }
                }
            }

            if (rowValueColumnId != null && rowValueColumn == null) {
                throw new FacesException("Can not find column '"
                        + rowValueColumnId
                        + "' specified by 'rowValueColumnId' attribute.");
            }

            String columnOrder = dataGridComponent
                    .getColumnsOrder(facesContext);
            if (columnOrder != null) {
                StringTokenizer st = new StringTokenizer(columnOrder, ", ");
                List first = new ArrayList(cs.size());

                for (; st.hasMoreTokens();) {
                    String tok = st.nextToken();

                    for (Iterator itCs = cs.iterator(); itCs.hasNext();) {
                        DataColumnComponent dc = (DataColumnComponent) itCs
                                .next();

                        String id = dc.getId();
                        if (id == null || id.equals(tok) == false) {
                            continue;
                        }

                        first.add(dc);
                        itCs.remove();

                        break;
                    }
                }

                first.addAll(cs);

                cs = first;
            }

            columns = (DataColumnComponent[]) cs
                    .toArray(new DataColumnComponent[cs.size()]);

            columnStates = new int[columns.length];
            columnImageURLs = new boolean[columns.length];
            cellStyleClasses = new boolean[columns.length];
            cellToolTipText = new boolean[columns.length];
            columnIds = new String[columns.length];

            for (int i = 0; i < columns.length; i++) {
                DataColumnComponent dc = columns[i];

                String columnId = dc.getId();
                if (columnId != null
                        && ComponentTools.isAnonymousComponentId(columnId) == false) {
                    columnIds[i] = columnId;
                }

                Boolean v = dc.getVisible(facesContext);
                if (v != null && v.booleanValue() == false) {
                    // Pas visible du tout !

                    int hiddenMode = dc.getHiddenMode(facesContext);
                    if (IVisibilityCapability.SERVER_HIDDEN_MODE == hiddenMode) {
                        columnStates[i] = SERVER_HIDDEN; // Pas visible et
                        // limit� au serveur
                        continue;
                    }

                    columnStates[i] = CLIENT_HIDDEN; // Pas visible mais
                    // envoy�

                } else {
                    columnStates[i] = VISIBLE;
                }

                if (dc.isCellImageURLSetted()) {
                    columnImageURLs[i] = true;
                    hasColumnImages = true;
                }

                if (dc.isCellStyleClassSetted()) {
                    cellStyleClasses[i] = true;
                    hasCellStyleClass = true;
                }

                if (dc.isCellToolTipTextSetted()) {
                    cellToolTipText[i] = true;
                    hasCellToolTipText = true;
                }

                if (columnStates[i] != VISIBLE) {
                    continue;
                }

                boolean sortSetted = false;

                FacesListener facesListeners[] = dc.listSortListeners();
                if (facesListeners != null && facesListeners.length > 0) {
                    if (sortClientSide == null) {
                        sortClientSide = new boolean[columnStates.length];
                        sortCommand = new Object[columnStates.length];
                    }

                    listeners: for (int j = 0; j < facesListeners.length; j++) {
                        FacesListener facesListener = facesListeners[j];

                        if (facesListener instanceof IScriptListener) {
                            IScriptListener scriptListener = (IScriptListener) facesListener;

                            String aliasCommand = (String) SORT_ALIASES
                                    .get(scriptListener.getCommand());
                            if (aliasCommand != null) {
                                // Gestion serveur comme client !
                                sortCommand[i] = aliasCommand;
                                sortClientSide[i] = (rows == 0);
                                sortSetted = true;
                                break listeners;
                            }

                            if (IHtmlRenderContext.JAVASCRIPT_TYPE
                                    .equals(scriptListener.getScriptType())) {

                                if (rows > 0) {
                                    // Script en mode ROW !
                                    throw new FacesException(
                                            "Client-side sort not support with 'rows' mode !");
                                }

                                sortClientSide[i] = true;
                                sortCommand[i] = scriptListener;
                                sortSetted = true;
                                break listeners;
                            }
                        }

                        if (facesListener instanceof IServerActionListener) {
                            sortClientSide[i] = false;
                            sortCommand[i] = facesListener;
                            sortSetted = true;
                            break listeners;
                        }
                    }
                }

                if (sortSetted == false) {
                    Comparator comparator = dc.getSortComparator(facesContext);
                    if (comparator != null) {
                        if (sortClientSide == null) {
                            sortClientSide = new boolean[columnStates.length];
                            sortCommand = new Object[columnStates.length];
                        }

                        sortSetted = true;
                        sortClientSide[i] = false;
                        sortCommand[i] = comparator;
                    }
                }
            }

            dataModel = dataGridComponent.getDataModel(facesContext);

            // Le dataModel peut etre NULL, car dans des cas de structures
            // simples,
            // elles n'ont pas besoin de publier un model !
        }

        public String getRowIndexVar() {
            return rowIndexVar;
        }

        public String getRowCountVar() {
            return rowCountVar;
        }

        public String getRowVarName() {
            return rowVarName;
        }

        public DataColumnComponent getRowValueColumn() {
            return rowValueColumn;
        }

        public int getColumnCount() {
            return columns.length;
        }

        public void setResizable(boolean resizable, int totalSize) {
            this.resizable = resizable;
            this.totalSize = totalSize;
        }

        public final int getResizeTotalSize() {
            return totalSize;
        }

        public DataColumnComponent[] listColumns() {
            return columns;
        }

        public TableRenderContext(FacesContext facesContext,
                DataGridComponent dataGridComponent) {
            this(facesContext, dataGridComponent, dataGridComponent
                    .listSortedComponents(facesContext));

            first = dataGridComponent.getFirst();

            rowCount = -2;

            filtersMap = dataGridComponent.getFilterProperties(facesContext);
        }

        public TableRenderContext(FacesContext facesContext,
                DataGridComponent dg, int rowIndex,
                ISortedComponent sortedComponents[], String filterExpression) {
            this(facesContext, dg, sortedComponents);

            first = rowIndex;
            this.filtersMap = HtmlTools.decodeFilterExpression(dg,
                    filterExpression);
        }

        public IFilterProperties getFiltersMap() {
            return filtersMap;
        }

        public DataModel getDataModel() {
            return dataModel;
        }

        public final boolean isResizable() {
            return resizable;
        }

        public String getColumnId(int index) {
            return columnIds[index];
        }

        public void updateRowCount() {
            rowCount = -2;
        }

        public int getRowCount() {
            if (rowCount == -2) {
                rowCount = dataGridComponent.getRowCount();
            }

            return rowCount;
        }

        public final int getFirst() {
            return first;
        }

        public final ISortedComponent[] listSortedComponents() {
            return sortedComponents;
        }

        public final int getRows() {
            return rows;
        }

        public boolean hasCellStyleClass() {
            return hasCellStyleClass;
        }

        public boolean hasCellToolTipText() {
            return hasCellToolTipText;
        }

        public boolean isCellStyleClass(int index) {
            return cellStyleClasses[index];
        }

        public boolean isCellToolTipText(int index) {
            return cellToolTipText[index];
        }

        public boolean hasColumnImages() {
            return hasColumnImages;
        }

        public boolean isColumnImageURL(int index) {
            return columnImageURLs[index];
        }

        public int getColumnStateCount() {
            return columnStates.length;
        }

        public final int getColumnState(int index) {
            return columnStates[index];
        }

        public final boolean isDisabled() {
            return disabled;
        }

        public final DataGridComponent getDataGridComponent() {
            return dataGridComponent;
        }

        public void setRowVarName(String rowVarName) {
            this.rowVarName = rowVarName;
        }
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = null;

        DataGridComponent dataGridComponent = (DataGridComponent) component;

        IMenuIterator menuIterator = dataGridComponent.listMenus();
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

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    private void writeFullStates(IJavaScriptWriter jsWriter,
            TableRenderContext context, String jsCommand, Set objects)
            throws WriterException {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        FacesContext facesContext = jsWriter.getFacesContext();
        DataColumnComponent dataColumnComponent = context.getRowValueColumn();

        jsWriter.writeMethodCall(jsCommand).write('[');
        int i = 0;
        for (Iterator it = objects.iterator(); it.hasNext();) {
            Object value = it.next();

            String convert = convertValue(facesContext, dataColumnComponent,
                    value);

            if (convert == null) {
                continue;
            }

            if (i > 0) {
                jsWriter.write(',');
            }

            jsWriter.writeString(convert);

            i++;
        }

        jsWriter.writeln("]);");

    }

    private void writeFullStates(IJavaScriptWriter jsWriter, String jsCommand,
            int[] indexes) throws WriterException {

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