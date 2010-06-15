package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

import org.rcfaces.core.component.SchedulerColumnComponent;
import org.rcfaces.core.component.SchedulerComponent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.lang.Time;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

public class SchedulerRenderer extends AbstractCssRenderer {

	public static final int DEFAULT_WIDTH = 548;
	public static final int DEFAULT_COLUMN_WIDTH = 95;
	public static final int DEFAULT_HEIGHT = 460;
	public static final int DEFAULT_NB_COLUMN = 5;

	public static final int NORTH_HEIGHT = 20;
	public static final int EAST_WIDTH = 0;
	public static final int SOUTH_HEIGHT = 0;
	public static final int WEST_WIDTH = 71;

	private static final String NORTH_WEST = "_north_west";
	private static final String NORTH = "_north";
	private static final String NORTH_EAST = "_north_east";
	private static final String EAST = "_east";
	private static final String SOUTH_EAST = "_south_east";
	private static final String SOUTH = "_south";
	private static final String SOUTH_WEST = "_south_west";
	private static final String WEST = "_west";
	private static final String CENTER = "_center";

	private static final String COLUMN_HEARDER = "_column_header";
	private static final String ROW_HEARDER = "_row_header";

	private static final String PERIODS_ID_SUFFIX = ""
			+ UINamingContainer.SEPARATOR_CHAR
			+ UINamingContainer.SEPARATOR_CHAR + "periods";

	protected String getJavaScriptClassName() {
		return JavaScriptClasses.SCHEDULER;
	}

	protected void encodeBegin(IComponentWriter writer) throws WriterException {
		super.encodeBegin(writer);
		IComponentRenderContext componentContext = writer
				.getComponentRenderContext();

		FacesContext facesContext = componentContext.getFacesContext();

		SchedulerComponent schedulerComponent = (SchedulerComponent) componentContext
				.getComponent();

		List schedulerColumnList = schedulerComponent.getSchedulerColumn();
		if (schedulerColumnList == null) {
			schedulerColumnList = new ArrayList(DEFAULT_NB_COLUMN);
		}

		IHtmlWriter htmlWriter = (IHtmlWriter) writer;

		htmlWriter.startElement(IHtmlWriter.DIV);

		writeCoreAttributes(htmlWriter);
		writeJavaScriptAttributes(htmlWriter);
		writeCssAttributes(htmlWriter);
		Date dateBegin = schedulerComponent.getDateBegin(facesContext);

		Calendar componentCalendar = CalendarTools.getCalendar(componentContext
				.getRenderContext().getProcessContext(), schedulerComponent,
				false);
		if (dateBegin != null) {
			htmlWriter.writeAttribute("v:dateBegin", convertDate(
					componentCalendar, dateBegin, false));
		}

		htmlWriter.writeAttribute("v:columnNumber", String
				.valueOf(schedulerColumnList.size()));

		String width = schedulerComponent.getWidth(facesContext);
		double columnWidth = 0;
		if (width == null) {
			columnWidth = DEFAULT_COLUMN_WIDTH;
			if (schedulerColumnList.size() > 0) {
				width = String.valueOf(schedulerColumnList.size() * columnWidth
						+ getWestWidth());
			} else {
				width = String.valueOf(DEFAULT_WIDTH);
			}
		}
		if ((columnWidth > 0) == false) {
			columnWidth = (Integer.parseInt(width) - getWestWidth())
					/ (double) schedulerColumnList.size();
		}

		String newWidth = AbstractCssRenderer.computeSizeInPixel(width, 0, 0);
		if (null != newWidth) {
			htmlWriter.writeStyle().writeWidth(newWidth);
		}
		htmlWriter.writeAttribute("v:columnWidth", String.valueOf(columnWidth));
		String height = schedulerComponent.getHeight(facesContext);
		if (height == null) {
			height = String.valueOf(DEFAULT_HEIGHT);
		}
		if (null != height) {
			String newHeight = AbstractCssRenderer.computeSizeInPixel(height,
					0, 0);
			if (null != newHeight) {
				htmlWriter.writeStyle().writeHeight(newHeight);
			}
		}

		Time hbt = schedulerComponent.getHourBegin();
		Time het = schedulerComponent.getHourEnd();

		int hourBegin = hbt.getHours() * 60 + hbt.getMinutes();
		int hourEnd = het.getHours() * 60 + het.getMinutes();

		htmlWriter.writeAttribute("v:minutesDayBegin", String
				.valueOf(hourBegin));
		htmlWriter.writeAttribute("v:minutesDayEnd", String.valueOf(hourEnd));

		int b = hourBegin % 30;
		hourBegin -= b;
		int e = hourEnd % 30;
		if (e > 0) {
			hourEnd += 30 - e;
		}

		int eastHeight = Integer.parseInt(height) - getNorthHeight();
		double minPerPx = (double) eastHeight / (double) (hourEnd - hourBegin);

		htmlWriter.writeAttribute("v:minPerPx", String.valueOf(minPerPx));

		createNorthWest(schedulerComponent, htmlWriter, facesContext);
		createNorth(schedulerComponent, htmlWriter, facesContext,
				schedulerColumnList, columnWidth);
		createNorthEast(schedulerComponent, htmlWriter, facesContext);
		createEast(schedulerComponent, htmlWriter, facesContext);
		createSouthEast(schedulerComponent, htmlWriter, facesContext);
		createSouth(schedulerComponent, htmlWriter, facesContext);
		createSouthWest(schedulerComponent, htmlWriter, facesContext);

		createWest(schedulerComponent, htmlWriter, facesContext, eastHeight,
				hourBegin, hourEnd, minPerPx);

		createCenter(schedulerComponent, htmlWriter, facesContext, eastHeight,
				hourBegin, hourEnd, minPerPx,
				(schedulerColumnList.size() * columnWidth), schedulerColumnList
						.size(), columnWidth);

		htmlWriter.getJavaScriptEnableMode().enableOnInit();
	}

	protected void createCenter(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext, int eastHeight,
			int hourBegin, int hourEnd, double minPerPx, double d,
			int nbColumn, double columnWidth) throws WriterException {

		htmlWriter.startElement(IHtmlWriter.DIV);
		htmlWriter.writeStyle().writeTopPx(getNorthHeight());
		htmlWriter.writeStyle().writeLeftPx(getWestWidth());
		htmlWriter.writeStyle().writeWidthPx((int) d);
		htmlWriter.writeStyle().writeHeightPx(eastHeight);
		htmlWriter.writeClass(getCenterClassName(htmlWriter));

		int demiBegin = hourBegin % 60;
		int offsetY = 0;

		if (demiBegin == 30) {
			offsetY = (int) (0.5 * 60 * minPerPx);
			for (int j = 0; j < nbColumn; j++) {

				String styleEnd = "";
				if (j == nbColumn - 1) {
					styleEnd = " f_scheduler_cell_last_column";
				}

				htmlWriter.startElement(IHtmlWriter.DIV);
				htmlWriter.writeClass("f_scheduler_cell_first" + styleEnd);
				htmlWriter.writeStyle().writeWidthPx((int) columnWidth);
				htmlWriter.writeStyle().writeHeightPx(((int) (30 * minPerPx)));
				htmlWriter.writeStyle().writeLeftPx((int) (j * columnWidth));

				htmlWriter.endElement(IHtmlWriter.DIV);
			}
			hourBegin += 30;
		}

		int demiEnd = hourEnd % 60;

		int nbHour = (hourEnd - hourBegin) / 60;

		int i = 0;
		for (; i < nbHour * 2; i++) {
			for (int j = 0; j < nbColumn; j++) {

				htmlWriter.startElement(IHtmlWriter.DIV);
				String styleEnd = "";
				if (j == nbColumn - 1) {
					styleEnd = " f_scheduler_cell_last_column";
				}

				if (i == 0 && demiBegin < 30) {
					htmlWriter.writeClass("f_scheduler_cell_first" + styleEnd);
				} else if (i == nbHour * 2 - 1 && demiEnd == 0) {
					htmlWriter.writeClass("f_scheduler_cell_odd_last"
							+ styleEnd);
				} else if (i % 2 > 0) {
					htmlWriter.writeClass("f_scheduler_cell_odd" + styleEnd);
				} else {
					htmlWriter.writeClass("f_scheduler_cell_even" + styleEnd);
				}

				htmlWriter.writeStyle().writeWidthPx((int) columnWidth);
				htmlWriter
						.writeStyle()
						.writeHeightPx(
								((int) ((i + 1) * 30 * minPerPx) - ((int) (i * 30 * minPerPx))));

				htmlWriter.writeStyle().writeTopPx(
						((int) (offsetY + i * 30 * minPerPx)));
				htmlWriter.writeStyle().writeLeftPx((int) (j * columnWidth));

				htmlWriter.endElement(IHtmlWriter.DIV);

			}
		}

		if (demiEnd == 30) {
			for (int j = 0; j < nbColumn; j++) {
				String styleEnd = "";
				if (j == nbColumn - 1) {
					styleEnd = " f_scheduler_cell_last_column";
				}

				htmlWriter.startElement(IHtmlWriter.DIV);
				htmlWriter.writeClass("f_scheduler_cell_even_last" + styleEnd);
				htmlWriter.writeStyle().writeWidthPx((int) columnWidth);
				htmlWriter.writeStyle().writeHeightPx(((int) (30 * minPerPx)));
				htmlWriter.writeStyle().writeLeftPx((int) (j * columnWidth));
				htmlWriter.writeStyle().writeTopPx(
						((int) (offsetY + i * 30 * minPerPx)));
				htmlWriter.endElement(IHtmlWriter.DIV);
			}
		}
		htmlWriter.endElement(IHtmlWriter.DIV);

		// div period
		htmlWriter.startElement(IHtmlWriter.DIV);
		htmlWriter.writeStyle().writeTopPx(getNorthHeight());
		htmlWriter.writeStyle().writeLeftPx(getWestWidth());
		htmlWriter.writeStyle().writeWidthPx((int) d);
		htmlWriter.writeStyle().writeHeightPx(eastHeight);
		htmlWriter.writeClass(getCenterClassName(htmlWriter) + "_periods");
		htmlWriter.writeId(getPeriodsId(htmlWriter));

		htmlWriter.endElement(IHtmlWriter.DIV);
	}

	protected void createNorthWest(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext)
			throws WriterException {
	}

	protected void createWest(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext, int eastHeight,
			int hourBegin, int hourEnd, double minPerPx) throws WriterException {
		htmlWriter.startElement(IHtmlWriter.DIV);
		htmlWriter.writeStyle().writeTopPx(getNorthHeight());
		htmlWriter.writeStyle().writeWidthPx(getWestWidth());
		htmlWriter.writeStyle().writeHeightPx(eastHeight);
		htmlWriter.writeClass(getWestClassName(htmlWriter));

		int demiBegin = hourBegin % 60;
		int offsetY = 0;
		if (demiBegin == 30) {
			offsetY = (int) (0.5 * 60 * minPerPx);
			htmlWriter.startElement(IHtmlWriter.DIV);
			htmlWriter.writeStyle().writeWidthPx(getWestWidth());
			htmlWriter.writeStyle().writeHeightPx((int) (30 * minPerPx));
			htmlWriter.writeClass(getRowHeaderClassName(htmlWriter));

			htmlWriter.startElement(IHtmlWriter.LABEL);
			htmlWriter.writeText(String.valueOf(hourBegin / 60) + "h30");
			htmlWriter.endElement(IHtmlWriter.LABEL);

			htmlWriter.endElement(IHtmlWriter.DIV);

			hourBegin += 30;
		}

		int nbHour = (hourEnd - hourBegin) / 60;

		int demiEnd = hourEnd % 60;

		int i = 0;
		for (i = 0; i < nbHour; i++) {
			htmlWriter.startElement(IHtmlWriter.DIV);
			htmlWriter.writeStyle().writeTopPx(
					(int) (offsetY + i * 60 * minPerPx));
			htmlWriter.writeStyle().writeWidthPx(getWestWidth());
			htmlWriter
					.writeStyle()
					.writeHeightPx(
							((int) ((i + 1) * 60 * minPerPx) - ((int) (i * 60 * minPerPx))));

			if (demiEnd == 0 && i == nbHour - 1) {

				htmlWriter.writeClass(getRowHeaderClassName(htmlWriter)
						+ "_last");
			} else {
				htmlWriter.writeClass(getRowHeaderClassName(htmlWriter));
			}

			htmlWriter.startElement(IHtmlWriter.LABEL);
			htmlWriter.writeText(String.valueOf(hourBegin / 60 + i) + "h00");
			htmlWriter.endElement(IHtmlWriter.LABEL);
			htmlWriter.endElement(IHtmlWriter.DIV);
		}

		if (demiEnd == 30) {

			htmlWriter.startElement(IHtmlWriter.DIV);
			htmlWriter.writeStyle().writeTopPx(
					(int) (offsetY + i * 60 * minPerPx));
			htmlWriter.writeClass(getRowHeaderClassName(htmlWriter) + "_last");
			htmlWriter.writeStyle().writeWidthPx(getWestWidth());
			htmlWriter.writeStyle().writeHeightPx((int) (30 * minPerPx));
			htmlWriter.writeClass(getRowHeaderClassName(htmlWriter));

			htmlWriter.startElement(IHtmlWriter.LABEL);
			htmlWriter.writeText(String.valueOf(hourEnd / 60) + "h00");
			htmlWriter.endElement(IHtmlWriter.LABEL);

			htmlWriter.endElement(IHtmlWriter.DIV);
		}

		htmlWriter.endElement(IHtmlWriter.DIV);

	}

	protected void createSouthWest(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext) {

	}

	protected void createSouth(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext) {

	}

	protected void createSouthEast(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext) {

	}

	protected void createEast(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext) {

	}

	protected void createNorthEast(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext) {

	}

	protected void createNorth(SchedulerComponent schedulerComponent,
			IHtmlWriter htmlWriter, FacesContext facesContext,
			List schedulerColumnList, double columnWidth)
			throws WriterException {

		htmlWriter.startElement(IHtmlWriter.DIV);
		htmlWriter.writeStyle().writeLeftPx(getWestWidth());
		htmlWriter.writeStyle().writeWidthPx(
				(int) (columnWidth * schedulerColumnList.size()));
		htmlWriter.writeStyle().writeHeightPx(getNorthHeight());
		
		
		htmlWriter.writeClass(getNorthClassName(htmlWriter));

		for (int i = 0; i < schedulerColumnList.size(); i++) {
			htmlWriter.startElement(IHtmlWriter.DIV);
			
			 String styleClass =  getColumnHeaderClassName(htmlWriter);
			 if (i == schedulerColumnList.size() - 1) {
				 styleClass += "_last";
			} 
				 
			String secondStyle	= ((SchedulerColumnComponent) schedulerColumnList
						.get(i)).getStyleClass(facesContext);
			if (secondStyle != null){
				styleClass += " "+secondStyle;
			}
			
			htmlWriter.writeClass(styleClass);
			htmlWriter.writeStyle().writeWidthPx((int) columnWidth);
			htmlWriter.writeStyle().writeHeightPx(getNorthHeight());
			htmlWriter.writeStyle().writeLeftPx((int) (columnWidth * i));
			htmlWriter.startElement(IHtmlWriter.LABEL);
			String label = ((SchedulerColumnComponent) schedulerColumnList
					.get(i)).getText();
			// Date dateBegin = schedulerComponent.getDateBegin(facesContext);
			// GregorianCalendar calendar = new GregorianCalendar();
			// calendar.setTime(dateBegin);
			// if (label.contains("#day")){
			// label = calendar.get(Calendar.d)
			// }
			htmlWriter.writeText(label);
			htmlWriter.endElement(IHtmlWriter.LABEL);

			htmlWriter.endElement(IHtmlWriter.DIV);
		}
		htmlWriter.endElement(IHtmlWriter.DIV);
	}

	protected void encodeEnd(IComponentWriter writer) throws WriterException {
		IHtmlWriter htmlWriter = (IHtmlWriter) writer;

		htmlWriter.endElement(IHtmlWriter.DIV);
		super.encodeEnd(writer);
	}

	protected void encodeJavaScript(IJavaScriptWriter writer)
			throws WriterException {
		super.encodeJavaScript(writer);

		FacesContext facesContext = writer.getComponentRenderContext()
				.getFacesContext();

		SchedulerComponent schedulerComponent = (SchedulerComponent) writer
				.getComponentRenderContext().getComponent();

		DataModel periods = (DataModel) schedulerComponent
				.getPeriods(facesContext);

		String var = schedulerComponent.getVar(facesContext);

		Map requestMap = facesContext.getExternalContext().getRequestMap();

		Object oldValue = requestMap.get(var);
		try {
			for (int index = 0;; index++) {
				periods.setRowIndex(index);

				if (periods.isRowAvailable() == false) {
					break;
				}

				Object periodData = periods.getRowData();

				requestMap.put(var, periodData);

				String periodLabel = schedulerComponent
						.getPeriodLabel(facesContext);

				String perdiodToolTip = schedulerComponent
						.getPeriodToolTip(facesContext);
				Object periodValue = schedulerComponent
						.getPeriodValue(facesContext);

				Calendar componentCalendar = CalendarTools.getCalendar(writer
						.getComponentRenderContext().getRenderContext()
						.getProcessContext(), schedulerComponent, false);

				Date periodBegin = schedulerComponent
						.getPeriodBegin(facesContext);
				Date periodEnd = schedulerComponent.getPeriodEnd(facesContext);

				String periodStyle = schedulerComponent.getPeriodStyle(); 
				
				boolean selectable = schedulerComponent.isPeriodSelectable(facesContext);
				
				writer.writeMethodCall("f_addPeriod");
				IObjectLiteralWriter objectLiteralWriter = writer
						.writeObjectLiteral(false);

				objectLiteralWriter.writeSymbol("_value").writeString(
						ValuesTools.valueToString(periodValue, null,
								schedulerComponent, facesContext));
				if (periodLabel != null) {
					objectLiteralWriter.writeSymbol("_label").writeString(
							periodLabel);
				}
				if (perdiodToolTip != null) {
					objectLiteralWriter.writeSymbol("_toolTip").writeString(
							perdiodToolTip);
				}
				if (periodStyle!= null) {
					objectLiteralWriter.writeSymbol("_periodStyle").writeString(
							periodStyle);
				}
				
				objectLiteralWriter.writeSymbol("_begin").writeString(
						convertDate(componentCalendar, periodBegin, false));
				componentCalendar.setTime(periodBegin);
				objectLiteralWriter.writeSymbol("_minutesBegin").writeInt(
						componentCalendar.get(Calendar.HOUR_OF_DAY) * 60
								+ componentCalendar.get(Calendar.MINUTE));
				objectLiteralWriter.writeSymbol("_end").writeString(
						convertDate(componentCalendar, periodEnd, false));
				componentCalendar.setTime(periodEnd);
				objectLiteralWriter.writeSymbol("_minutesEnd").writeInt(
						componentCalendar.get(Calendar.HOUR_OF_DAY) * 60
								+ componentCalendar.get(Calendar.MINUTE));
				objectLiteralWriter.writeSymbol("_selectable").writeBoolean(selectable);
				
				objectLiteralWriter.end();

				writer.writeln(");");
			}

		} finally {
			periods.setRowIndex(-1);
			requestMap.put(var, oldValue);
		}
	}

	protected int getNorthHeight() {
		return NORTH_HEIGHT;
	}

	protected int getEastWidth() {
		return EAST_WIDTH;
	}

	protected int getSouthHeight() {
		return SOUTH_HEIGHT;
	}

	protected int getWestWidth() {
		return WEST_WIDTH;
	}

	protected String getNorthClassName(IHtmlWriter htmlWriter) {
		return getMainStyleClassName() + NORTH;
	}

	protected String getWestClassName(IHtmlWriter htmlWriter) {
		return getMainStyleClassName() + WEST;
	}

	protected String getColumnHeaderClassName(IHtmlWriter htmlWriter) {
		return getMainStyleClassName() + COLUMN_HEARDER;
	}

	protected String getRowHeaderClassName(IHtmlWriter htmlWriter) {
		return getMainStyleClassName() + ROW_HEARDER;
	}

	protected String getCenterClassName(IHtmlWriter htmlWriter) {
		return getMainStyleClassName() + CENTER;
	}

	private String getPeriodsId(IHtmlWriter htmlWriter) {
		return htmlWriter.getComponentRenderContext().getComponentClientId()
				+ PERIODS_ID_SUFFIX;
	}

	private static String convertDate(Calendar calendar, Date date,
			boolean onlyDate) {
		StringAppender sb = new StringAppender(10);

		HtmlTools.formatDate(date, sb, calendar, onlyDate);

		return sb.toString();
	}

}