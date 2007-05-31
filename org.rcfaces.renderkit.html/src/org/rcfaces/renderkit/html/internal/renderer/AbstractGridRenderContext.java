/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesListener;
import javax.faces.model.DataModel;

import org.rcfaces.core.component.capability.IAlignmentCapability;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.capability.ICellImageCapability;
import org.rcfaces.core.component.capability.ICellStyleClassCapability;
import org.rcfaces.core.component.capability.ICellToolTipTextCapability;
import org.rcfaces.core.component.capability.ICheckCardinalityCapability;
import org.rcfaces.core.component.capability.ICheckableCapability;
import org.rcfaces.core.component.capability.IClientCheckFullStateCapability;
import org.rcfaces.core.component.capability.IClientSelectionFullStateCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IFilterCapability;
import org.rcfaces.core.component.capability.IHeaderVisibilityCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IOrderedChildrenCapability;
import org.rcfaces.core.component.capability.IPagedCapability;
import org.rcfaces.core.component.capability.IResizableCapability;
import org.rcfaces.core.component.capability.IRowStyleClassCapability;
import org.rcfaces.core.component.capability.ISelectableCapability;
import org.rcfaces.core.component.capability.ISelectionCardinalityCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.component.capability.ISortComparatorCapability;
import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.component.capability.IWidthCapability;
import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.capability.ICellImageSettings;
import org.rcfaces.core.internal.capability.ICellStyleClassSettings;
import org.rcfaces.core.internal.capability.ICellToolTipTextSettings;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.capability.IImageAccessorsCapability;
import org.rcfaces.core.internal.capability.ISortedComponentsCapability;
import org.rcfaces.core.internal.component.IImageAccessors;
import org.rcfaces.core.internal.component.IStatesImageAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.internal.listener.IServerActionListener;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.ISortedComponent;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlComponentRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractGridRenderContext {

    private static final String REVISION = "$Revision$";

    private static final ISortedComponent SORTED_COMPONENT_EMPTY_ARRAY[] = new ISortedComponent[0];

    public static final int SERVER_HIDDEN = 1;

    public static final int CLIENT_HIDDEN = 2;

    public static final int VISIBLE = 3;

    public final IProcessContext processContext;

    public final IGridComponent gridComponent;

    private boolean clientSelectionFullState;

    private boolean clientCheckFullState;

    private boolean checkable;

    private int checkCardinality;

    private boolean selectable;

    private int selectionCardinality;

    private boolean disabled;

    private String rowIndexVar;

    private String rowCountVar;

    private boolean cellStyleClasses[];

    private boolean hasCellStyleClass;

    private boolean hasCellToolTipText;

    private boolean cellToolTipText[];

    private int columnStates[];

    private boolean paged;

    private String rowVarName;

    private boolean hasColumnImages;

    private boolean hasTitleColumnImages;

    private ISortedComponent sortedComponents[];

    private int rows;

    private int forcedRows = -1;

    private boolean resizable;

    private int first;

    private int rowCount = -2;

    private boolean columnImageURLs[];

    private boolean sortClientSide[];

    private Object sortCommand[];

    private String columnIds[];

    protected DataModel dataModel;

    private IFilterProperties filtersMap;

    protected UIColumn columns[];

    private int totalSize;

    private boolean designerMode;

    private IScriptRenderContext scriptRenderContext;

    private boolean hasScrollBars;

    private String defaultCellImageURLs[];

    private String defaultCellStyleClasses[][];

    private String defaultCellToolTipTexts[];

    private String defaultCellHorizontalAligments[];

    private String cellTitleImageURLs[];

    private String cellTitleDisabledImageURLs[];

    private String cellTitleHoverImageURLs[];

    private String cellTitleSelectedImageURLs[];

    private String columnStyleClasses[];

    private String rowStyleClasses[];

    protected int gridWidth;

    protected int gridHeight;

    private boolean headerVisible = true;

    private AbstractGridRenderContext(IProcessContext processContext,
            IScriptRenderContext scriptRenderContext,
            IGridComponent gridComponent, ISortedComponent sortedComponents[],
            boolean checkTitleImages) {
        this.processContext = processContext;
        this.scriptRenderContext = scriptRenderContext;

        this.gridComponent = gridComponent;
        this.sortedComponents = sortedComponents;

        if (gridComponent instanceof ISizeCapability) {
            computeGridSize((ISizeCapability) gridComponent);
        }

        initialize(checkTitleImages);

    }

    protected void computeGridSize(ISizeCapability sizeCapability) {
        String width = sizeCapability.getWidth();
        if (width != null) {
            this.gridWidth = AbstractCssRenderer.computeSize(width, 0, 9);
        }

        String height = sizeCapability.getHeight();
        if (height != null) {
            this.gridHeight = AbstractCssRenderer.computeSize(height, 0, 9);
        }
    }

    protected void initialize(boolean checkTitleImages) {

        if (gridComponent instanceof IHeaderVisibilityCapability) {
            headerVisible = ((IHeaderVisibilityCapability) gridComponent)
                    .isHeaderVisible();
        }

        if (gridComponent instanceof ISelectableCapability) {
            selectable = ((ISelectableCapability) gridComponent).isSelectable();
            if (selectable) {
                int selectionCardinality = 0;
                if (gridComponent instanceof ISelectionCardinalityCapability) {
                    selectionCardinality = ((ISelectionCardinalityCapability) gridComponent)
                            .getSelectionCardinality();
                }

                if (selectionCardinality == 0) {
                    selectionCardinality = ICardinality.DEFAULT_CARDINALITY;
                }
                this.selectionCardinality = selectionCardinality;

                if (gridComponent instanceof IClientSelectionFullStateCapability) {
                    this.clientSelectionFullState = ((IClientSelectionFullStateCapability) gridComponent)
                            .isClientSelectionFullState();
                }
            }
        }

        if (gridComponent instanceof ICheckableCapability) {
            checkable = ((ICheckableCapability) gridComponent).isCheckable();
            if (checkable) {
                int checkCardinality = 0;
                if (gridComponent instanceof ICheckCardinalityCapability) {
                    checkCardinality = ((ICheckCardinalityCapability) gridComponent)
                            .getCheckCardinality();
                }
                if (checkCardinality == 0) {
                    checkCardinality = ICardinality.DEFAULT_CARDINALITY;
                }
                this.checkCardinality = checkCardinality;

                if (gridComponent instanceof IClientCheckFullStateCapability) {
                    this.clientCheckFullState = ((IClientCheckFullStateCapability) gridComponent)
                            .isClientCheckFullState();
                }
            }
        }

        if (gridComponent instanceof IDisabledCapability) {
            disabled = ((IDisabledCapability) gridComponent).isDisabled();
        }

        rows = gridComponent.getRows();
        if (rows > 0) {
            paged = true;
        }

        if (gridComponent instanceof IPagedCapability) {
            if (((IPagedCapability) gridComponent).isPagedSetted()) {
                paged = ((IPagedCapability) gridComponent).isPaged();
            }
        }

        rowCountVar = gridComponent.getRowCountVar();

        rowIndexVar = gridComponent.getRowIndexVar();

        if (gridComponent instanceof IRowStyleClassCapability) {
            String rowStyleClass = ((IRowStyleClassCapability) gridComponent)
                    .getRowStyleClass();

            if (rowStyleClass != null) {
                StringTokenizer st = new StringTokenizer(rowStyleClass, ",");

                rowStyleClasses = new String[st.countTokens()];

                for (int i = 0; i < rowStyleClasses.length; i++) {
                    String token = st.nextToken().trim();

                    rowStyleClasses[i] = token;
                }
            }
        }

        IColumnIterator itColumns = gridComponent.listColumns();

        columns = itColumns.toArray();

        if (gridComponent instanceof IOrderedChildrenCapability) {
            UIComponent orderedChildren[] = ((IOrderedChildrenCapability) gridComponent)
                    .getOrderedChildren();
            if (orderedChildren != null && orderedChildren.length > 0) {
                List cs = new ArrayList(orderedChildren.length);

                for (int i = 0; i < orderedChildren.length; i++) {
                    UIComponent c = orderedChildren[i];

                    if ((c instanceof UIColumn) == false) {
                        continue;
                    }

                    cs.add(c);
                }

                columns = (UIColumn[]) cs.toArray(new UIColumn[cs.size()]);
            }
        }

        columnStates = new int[columns.length];
        columnImageURLs = new boolean[columns.length];
        cellStyleClasses = new boolean[columns.length];
        cellToolTipText = new boolean[columns.length];
        columnIds = new String[columns.length];

        FacesContext facesContext = processContext.getFacesContext();

        boolean widthNotSpecified = false;

        for (int i = 0; i < columns.length; i++) {
            UIColumn column = columns[i];

            String columnId = column.getId();
            if (columnId != null
                    && ComponentTools.isAnonymousComponentId(columnId) == false) {
                columnIds[i] = columnId;
            }

            if (column instanceof IVisibilityCapability) {
                Boolean v = ((IVisibilityCapability) column).getVisibleState();
                if (v != null && v.booleanValue() == false) {
                    // Pas visible du tout !

                    if (column instanceof IHiddenModeCapability) {
                        int hiddenMode = ((IHiddenModeCapability) column)
                                .getHiddenMode();
                        if (IHiddenModeCapability.SERVER_HIDDEN_MODE == hiddenMode) {
                            columnStates[i] = SERVER_HIDDEN; // Pas visible
                            // et
                            // limit� au serveur
                            continue;
                        }
                    }

                    columnStates[i] = CLIENT_HIDDEN; // Pas visible mais
                    // envoy�

                } else {
                    columnStates[i] = VISIBLE;
                }
            }

            if (columnStates[i] != VISIBLE) {
                continue;
            }

            String dw = null;

            if (column instanceof IWidthCapability) {
                dw = ((IWidthCapability) column).getWidth();
            }

            if (dw == null) {
                widthNotSpecified = true;

            } else {
                int idw = AbstractCssRenderer.computeSize(dw, -1, 0);
                if (idw < 0) {
                    throw new FacesException(
                            "Width of column can not be negative (" + idw + ")");
                }

                totalSize += idw;
            }

            if (column instanceof IResizableCapability) {
                if (((IResizableCapability) column).isResizable()) {

                    resizable |= true;

                    if (dw == null) {
                        throw new FacesException(
                                "You must specify a width for a resizable column !");
                    }
                }
            }

            boolean sortSetted = false;

            if (column instanceof ISortEventCapability) {
                FacesListener facesListeners[] = ((ISortEventCapability) column)
                        .listSortListeners();
                if (facesListeners != null && facesListeners.length > 0) {
                    if (sortClientSide == null) {
                        sortClientSide = new boolean[columnStates.length];
                        sortCommand = new Object[columnStates.length];
                    }

                    listeners: for (int j = 0; j < facesListeners.length; j++) {
                        FacesListener facesListener = facesListeners[j];

                        if (facesListener instanceof IScriptListener) {
                            IScriptListener scriptListener = (IScriptListener) facesListener;

                            String aliasCommand = convertAliasCommand(scriptListener
                                    .getCommand());

                            if (aliasCommand != null) {
                                // Gestion serveur comme client !
                                sortCommand[i] = translateJavascriptMethod(aliasCommand);
                                sortClientSide[i] = (rows == 0);
                                sortSetted = true;
                                break listeners;
                            }

                            if (IHtmlRenderContext.JAVASCRIPT_TYPE
                                    .equals(scriptListener
                                            .getScriptType(processContext))) {

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
            }

            if (sortSetted == false
                    && (column instanceof ISortComparatorCapability)) {
                Comparator comparator = ((ISortComparatorCapability) column)
                        .getSortComparator();
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

            if (column instanceof ICellToolTipTextSettings) {
                if (((ICellToolTipTextSettings) column)
                        .isCellToolTipTextSetted()) {
                    cellToolTipText[i] = true;
                    hasCellToolTipText = true;
                }
            }

            if (column instanceof ICellToolTipTextCapability) {
                String ctt = ((ICellToolTipTextCapability) column)
                        .getCellDefaultToolTipText();
                if (ctt != null) {
                    if (defaultCellToolTipTexts == null) {
                        defaultCellToolTipTexts = new String[columns.length];
                    }
                    defaultCellToolTipTexts[i] = ctt;
                }
            }

            if (column instanceof ICellStyleClassSettings) {
                if (((ICellStyleClassSettings) column).isCellStyleClassSetted()) {
                    cellStyleClasses[i] = true;
                    hasCellStyleClass = true;
                }
            }

            if (column instanceof ICellStyleClassCapability) {
                String classes = ((ICellStyleClassCapability) column)
                        .getDefaultCellStyleClass();
                if (classes != null) {
                    if (defaultCellStyleClasses == null) {
                        defaultCellStyleClasses = new String[columns.length][];
                    }

                    StringTokenizer st = new StringTokenizer(classes, ",;");

                    String cs[] = new String[st.countTokens()];
                    defaultCellStyleClasses[i] = cs;

                    for (int j = 0; st.hasMoreTokens(); j++) {
                        cs[j] = st.nextToken().trim();
                    }
                }
            }

            if (column instanceof IAlignmentCapability) {
                String halign = ((IAlignmentCapability) column).getAlignment();
                if (halign != null && "left".equalsIgnoreCase(halign) == false) {
                    if (defaultCellHorizontalAligments == null) {
                        defaultCellHorizontalAligments = new String[columns.length];
                    }

                    defaultCellHorizontalAligments[i] = halign;
                }
            }

            if (column instanceof ICellImageSettings) {
                if (((ICellImageSettings) column).isCellImageURLSetted()) {
                    columnImageURLs[i] = true;
                    hasColumnImages = true;
                }
            }

            if (column instanceof ICellImageCapability) {
                String dci = ((ICellImageCapability) column)
                        .getDefaultCellImageURL();
                if (dci != null) {
                    if (defaultCellImageURLs == null) {
                        defaultCellImageURLs = new String[columns.length];
                    }
                    defaultCellImageURLs[i] = dci;
                }
            }

            if (column instanceof IStyleClassCapability) {
                String dci = ((IStyleClassCapability) column).getStyleClass();
                if (dci != null) {
                    if (columnStyleClasses == null) {
                        columnStyleClasses = new String[columns.length];
                    }
                    columnStyleClasses[i] = dci;
                }
            }

            if (column instanceof IImageAccessorsCapability) {
                IContentAccessors contentAccessors = ((IImageAccessorsCapability) column)
                        .getImageAccessors(facesContext);

                if (contentAccessors instanceof IImageAccessors) {
                    IImageAccessors imageAccessors = (IImageAccessors) contentAccessors;

                    IContentAccessor imageAccessor = imageAccessors
                            .getImageAccessor();
                    if (imageAccessor != null) {
                        String imageURL = imageAccessor.resolveURL(
                                facesContext, null, null);

                        if (imageURL != null) {
                            if (cellTitleImageURLs == null) {
                                cellTitleImageURLs = new String[columns.length];
                            }

                            cellTitleImageURLs[i] = imageURL;
                        }

                        if (imageAccessors instanceof IStatesImageAccessors) {
                            IStatesImageAccessors is = (IStatesImageAccessors) imageAccessors;

                            IContentAccessor disabledImageContentAccessor = is
                                    .getDisabledImageAccessor();
                            if (disabledImageContentAccessor != null) {
                                String disabledImageURL = disabledImageContentAccessor
                                        .resolveURL(facesContext, null, null);
                                if (disabledImageURL != null) {
                                    if (cellTitleDisabledImageURLs == null) {
                                        cellTitleDisabledImageURLs = new String[columns.length];
                                    }

                                    cellTitleDisabledImageURLs[i] = disabledImageURL;
                                }
                            }

                            IContentAccessor hoverImageContentAccessor = is
                                    .getHoverImageAccessor();
                            if (hoverImageContentAccessor != null) {
                                String hoverImageURL = hoverImageContentAccessor
                                        .resolveURL(facesContext, null, null);
                                if (hoverImageURL != null) {
                                    if (cellTitleHoverImageURLs == null) {
                                        cellTitleHoverImageURLs = new String[columns.length];
                                    }

                                    cellTitleHoverImageURLs[i] = hoverImageURL;
                                }
                            }

                            IContentAccessor selectedImageContentAccessor = is
                                    .getSelectedImageAccessor();
                            if (selectedImageContentAccessor != null) {
                                String selectedImageURL = selectedImageContentAccessor
                                        .resolveURL(facesContext, null, null);

                                if (selectedImageURL != null) {
                                    if (cellTitleSelectedImageURLs == null) {
                                        cellTitleSelectedImageURLs = new String[columns.length];
                                    }

                                    cellTitleSelectedImageURLs[i] = selectedImageURL;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (getGridHeight() > 0 || getGridWidth() > 0) {
            hasScrollBars = true;
        }

        if (resizable && (hasScrollBars == false || widthNotSpecified)) {
            resizable = false;
        }

        dataModel = gridComponent.getDataModelValue();

        // Le dataModel peut etre NULL, car dans des cas de structures
        // simples,
        // elles n'ont pas besoin de publier un model !

    }

    protected abstract String convertAliasCommand(String command);

    public boolean isDesignerMode() {
        return designerMode;
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

    public int getColumnCount() {
        return columns.length;
    }

    public UIColumn[] listColumns() {
        return columns;
    }

    protected AbstractGridRenderContext(
            IHtmlComponentRenderContext componentRenderContext) {
        this(componentRenderContext.getRenderContext().getProcessContext(),
                componentRenderContext.getRenderContext()
                        .getScriptRenderContext(),
                (IGridComponent) componentRenderContext.getComponent(),
                computeSortedComponents(componentRenderContext), true);

        designerMode = componentRenderContext.getRenderContext()
                .getProcessContext().isDesignerMode();

        first = gridComponent.getFirst();

        if (gridComponent instanceof IFilterCapability) {
            filtersMap = ((IFilterCapability) gridComponent)
                    .getFilterProperties();
        }
    }

    private static ISortedComponent[] computeSortedComponents(
            IHtmlComponentRenderContext componentRenderContext) {
        UIComponent component = componentRenderContext.getComponent();

        if (component instanceof ISortedComponentsCapability) {
            return ((ISortedComponentsCapability) component)
                    .listSortedComponents(componentRenderContext
                            .getFacesContext());
        }

        return SORTED_COMPONENT_EMPTY_ARRAY;
    }

    protected AbstractGridRenderContext(IProcessContext processContext,
            IScriptRenderContext scriptRenderContext,
            IGridComponent gridComponent, int rowIndex, int forcedRows,
            ISortedComponent sortedComponents[], String filterExpression) {
        this(processContext, scriptRenderContext, gridComponent,
                sortedComponents, false);

        this.first = rowIndex;
        this.forcedRows = forcedRows;

        if (filterExpression != null) {
            this.filtersMap = HtmlTools.decodeFilterExpression(null,
                    (UIComponent) gridComponent, filterExpression);
        }
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
            rowCount = gridComponent.getRowCount();
        }

        return rowCount;
    }

    public final int getFirst() {
        return first;
    }

    public final boolean isPaged() {
        return paged;
    }

    public final ISortedComponent[] listSortedComponents() {
        return sortedComponents;
    }

    public final int getForcedRows() {
        return forcedRows;
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

    public boolean[] getCellStyleClass() {
        return cellStyleClasses;
    }

    public boolean isCellToolTipText(int index) {
        return cellToolTipText[index];
    }

    public boolean[] getCellToolTipText() {
        return cellToolTipText;
    }

    public boolean hasTitleColumnImages() {
        return hasTitleColumnImages;
    }

    public boolean hasColumnImages() {
        return hasColumnImages;
    }

    public boolean isColumnImageURL(int index) {
        return columnImageURLs[index];
    }

    public final int getColumnState(int index) {
        return columnStates[index];
    }

    public final boolean isDisabled() {
        return disabled;
    }

    public final IGridComponent getGridComponent() {
        return gridComponent;
    }

    public void setRowVarName(String rowVarName) {
        this.rowVarName = rowVarName;
    }

    public boolean hasSortClientSide() {
        return sortClientSide != null;
    }

    public Object getSortCommand(int i) {
        if (sortCommand == null) {
            return null;
        }
        return sortCommand[i];
    }

    public boolean getSortClientSide(int i) {
        return sortClientSide[i];
    }

    public String translateJavascriptMethod(String command) {

        int idx = command.indexOf('.');
        String className = command.substring(0, idx);
        String newClassName = scriptRenderContext
                .convertSymbol(null, className);

        String memberName = command.substring(idx + 1);
        String newMemberName = scriptRenderContext.convertSymbol(className,
                memberName);

        if (className == newClassName && memberName == newMemberName) {
            return command;
        }

        StringAppender sa = new StringAppender(newClassName.length() + 1
                + newMemberName.length());

        sa.append(newClassName);
        sa.append('.');
        sa.append(newMemberName);

        return sa.toString();
    }

    public final boolean isCheckable() {
        return checkable;
    }

    public final boolean isClientCheckFullState() {
        return clientCheckFullState;
    }

    public final boolean isSelectable() {
        return selectable;
    }

    public final int getSelectionCardinality() {
        return selectionCardinality;
    }

    public final boolean isClientSelectionFullState() {
        return clientSelectionFullState;
    }

    public final int getCheckCardinality() {
        return checkCardinality;
    }

    public final boolean hasScrollBars() {
        return hasScrollBars;
    }

    public String[] getDefaultCellImageURLs() {
        return defaultCellImageURLs;
    }

    public String[][] getDefaultCellStyleClasses() {
        return defaultCellStyleClasses;
    }

    public String[] getDefaultCellToolTipTexts() {
        return defaultCellToolTipTexts;
    }

    public String[] getDefaultCellHorizontalAlignments() {
        return defaultCellHorizontalAligments;
    }

    public String[] getCellTitleImageURLs() {
        return cellTitleImageURLs;
    }

    public String[] getCellTitleDisabledImageURLs() {
        return cellTitleDisabledImageURLs;
    }

    public String[] getCellTitleHoverImageURLs() {
        return cellTitleHoverImageURLs;
    }

    public String[] getCellTitleSelectedImageURLs() {
        return cellTitleSelectedImageURLs;
    }

    public String[] getColumnStyleClasses() {
        return columnStyleClasses;
    }

    public String[] getRowStyleClasses() {
        return rowStyleClasses;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public boolean isHeaderVisible() {
        return headerVisible;
    }
}
