package org.rcfaces.renderkit.html.internal.service;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.capability.ICriteriaConfiguration;
import org.rcfaces.core.internal.capability.ICriteriaContainer;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.service.IServicesRegistry;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.core.internal.webapp.ConfiguredHttpServlet;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.model.ICriteriaSelectedResult;
import org.rcfaces.core.model.ISelectedCriteria;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.HtmlTools;
import org.rcfaces.renderkit.html.internal.HtmlTools.ILocalizedComponent;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.IObjectLiteralWriter;
import org.rcfaces.renderkit.html.internal.util.JavaScriptResponseWriter;

/**
 * 
 * @author Olivier Oeuillot
 */
public class CriteriaGridService extends AbstractHtmlService {
	private static final String REVISION = "$Revision$";

	private static final String SERVICE_ID = Constants.getPackagePrefix()
			+ ".CriteriaGrid";

	private static final Log LOG = LogFactory.getLog(CriteriaGridService.class);

	private static final int DEFAULT_BUFFER_SIZE = 4096;

	private static final int INITIAL_SIZE = 8000;

	private static final String CRITERIA_SERVICE_VERSION = "1.0.0";

	public CriteriaGridService() {
	}

	public static CriteriaGridService getInstance(FacesContext facesContext) {

		IServicesRegistry serviceRegistry = RcfacesContext.getInstance(
				facesContext).getServicesRegistry();
		if (serviceRegistry == null) {
			// Designer mode
			return null;
		}

		return (CriteriaGridService) serviceRegistry.getService(facesContext,
				RenderKitFactory.HTML_BASIC_RENDER_KIT, SERVICE_ID);
	}

	public void service(FacesContext facesContext, String commandId) {
		Map parameters = facesContext.getExternalContext()
				.getRequestParameterMap();

		UIViewRoot viewRoot = facesContext.getViewRoot();

		String componentId = (String) parameters.get("gridId");
		if (componentId == null) {
			sendJsError(facesContext, null, INVALID_PARAMETER_SERVICE_ERROR,
					"Can not find 'componentId' parameter.", null);
			return;
		}

		if (viewRoot.getChildCount() == 0) {
			sendJsError(facesContext, componentId,
					SESSION_EXPIRED_SERVICE_ERROR, "No view !", null);
			return;
		}

		String tokenId = (String) parameters.get("tokenId");

		ILocalizedComponent localizedComponent = HtmlTools.localizeComponent(
				facesContext, componentId);
		if (localizedComponent == null) {
			// Cas special: la session a du expir�e ....

			sendJsError(facesContext, componentId,
					INVALID_PARAMETER_SERVICE_ERROR,
					"Component is not found !", null);

			return;
		}

		try {

			UIComponent component = localizedComponent.getComponent();

			if ((component instanceof ICriteriaManagerCapability) == false) {
				sendJsError(
						facesContext,
						componentId,
						INVALID_PARAMETER_SERVICE_ERROR,
						"Component can not be filtred by criteria. (not an ICriteriaManagerCapability)",
						null);
				return;
			}

			ICriteriaManagerCapability keyLabelComponent = (ICriteriaManagerCapability) component;

			ServletResponse response = (ServletResponse) facesContext
					.getExternalContext().getResponse();

			setNoCache(response);
			response.setContentType(IHtmlRenderContext.JAVASCRIPT_TYPE
					+ "; charset=" + RESPONSE_CHARSET);

			setCameliaResponse(response, CRITERIA_SERVICE_VERSION);

			boolean useGzip = canUseGzip(facesContext);

			PrintWriter printWriter = null;
			try {

				if (useGzip == false) {
					printWriter = response.getWriter();

				} else {
					ConfiguredHttpServlet.setGzipContentEncoding(
							(HttpServletResponse) response, true);

					OutputStream outputStream = response.getOutputStream();

					GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
							outputStream, DEFAULT_BUFFER_SIZE);

					Writer writer = new OutputStreamWriter(gzipOutputStream,
							RESPONSE_CHARSET);

					printWriter = new PrintWriter(writer, false);
				}

				
				ISelectedCriteria[] selectedCriteria = null;
				String criteria_s = (String) parameters.get("selectedCriteria");
				selectedCriteria = DataGridService.computeCriteriaConfigs((IGridComponent) component, criteria_s);
				
				writeJs(facesContext, printWriter, keyLabelComponent,
						componentId, selectedCriteria, tokenId);

			} catch (IOException ex) {

				throw new FacesException(
						"Can not write criteria javascript result !", ex);

			} catch (RuntimeException ex) {
				LOG.error("Catch runtime exception !", ex);

				throw ex;

			} finally {
				if (printWriter != null) {
					printWriter.close();
				}
			}

		} finally {
			localizedComponent.end();
		}

		facesContext.responseComplete();
	}

	private void writeJs(FacesContext facesContext, PrintWriter printWriter,
			ICriteriaManagerCapability component, String componentId,
			ISelectedCriteria[] selectedCriteria, String tokenId)
			throws IOException {

		ICriteriaSelectedResult result = component
				.processSelectedCriteria(selectedCriteria);

		CharArrayWriter cw = null;
		PrintWriter pw = printWriter;
		if (LOG.isTraceEnabled()) {
			cw = new CharArrayWriter(2000);
			pw = new PrintWriter(cw);
		}

		IJavaScriptWriter jsWriter = new JavaScriptResponseWriter(facesContext,
				pw, RESPONSE_CHARSET, (UIComponent) component, componentId);

		String varId = jsWriter.getComponentVarName();

		jsWriter.write("var ").write(varId).write('=')
				.writeCall("f_core", "GetElementByClientId")
				.writeString(componentId).writeln(");");

		// component.setFilterProperties(filterProperties);

		boolean first = true;
		jsWriter.writeMethodCall("_processSelectedCriteriaResult");

		jsWriter.writeString(tokenId).write(',')
				.writeInt(result.getResultCount()).write(',');
		
		ISelectedCriteria[] resultCriteria = result.listSelectedCriteria();
		if (resultCriteria != null && resultCriteria.length > 0) {
			for (int i = 0; i < resultCriteria.length; i++) {
				ISelectedCriteria rc = resultCriteria[i];
	
				if (first == false) {
					jsWriter.write(',');
				}
	
				writeSelectedCriteria(jsWriter, rc, result);
			}
		}else {
			 ICriteriaContainer[] criteriaContainers = component.listCriteriaContainers();
			 IObjectLiteralWriter ow = jsWriter.writeObjectLiteral(true);
			 //for (int i = 0; i < criteriaContainers.length; i++) {
				 //ICriteriaContainer container = criteriaContainers[i];
				 //ICriteriaConfiguration configuration = container.getCriteriaConfiguration();

					

					ow.writeSymbol("_id").writeString("unId");
					
					CriteriaItem[] cis = new CriteriaItem[4];//result.getAvailableCriteriaItems(configuration);
					
					/*
					 * Bouchon
					 */
					CriteriaItem item = new CriteriaItem();
					item.setLabel("label1");
					item.setValue("value1");
					
					CriteriaItem item2 = new CriteriaItem();
					item2.setLabel("label2");
					item2.setValue("value2");
					CriteriaItem item3 = new CriteriaItem();
					item3.setLabel("label3");
					item3.setValue("value3");
					CriteriaItem item4 = new CriteriaItem();
					item4.setLabel("label4");
					item4.setValue("value4");
					
					cis[0] = item;
					cis[1] = item2;
					cis[2] = item3;
					cis[3] = item4;
					
					/*
					 * Fin bouchon
					 */
					//UIComponent refComponent = (UIComponent) configuration;
					Converter converter = null;//configuration.getCriteriaConverter();

					IJavaScriptWriter ijs = ow.writeSymbol("_items").write('[');
					for (int j = 0; j < cis.length; j++) {
						CriteriaItem ci = cis[j];

						if (j > 0) {
							ijs.write(',');
						}

						IObjectLiteralWriter itemsW = ijs.writeObjectLiteral(true);
						itemsW.writeSymbol("_label").writeString(ci.getLabel());

						Object itemValue = ci.getValue();

						String itemConvertedValue = ValuesTools
								.convertValueToString(itemValue, converter, null,
										jsWriter.getFacesContext());

						itemsW.writeSymbol("_value").writeString(itemConvertedValue);

						itemsW.end();
					}

					ijs.write(']');

					ow.end();
			//}
		}
		
		jsWriter.writeln(");");

		if (LOG.isTraceEnabled()) {
			pw.flush();

			LOG.trace(cw.toString());

			printWriter.write(cw.toCharArray());
		}
	}

	private void writeSelectedCriteria(IJavaScriptWriter jsWriter,
			ISelectedCriteria resultCriteria, ICriteriaSelectedResult result)
			throws WriterException {

		ICriteriaConfiguration configuration = resultCriteria.getConfig();

		IObjectLiteralWriter ow = jsWriter.writeObjectLiteral(true);

		ow.writeSymbol("_id").writeString(
				resultCriteria.getConfig().getCriteriaContainer().getId());

		CriteriaItem[] cis = new CriteriaItem[4];//result.getAvailableCriteriaItems(configuration);
		
		/*
		 * Bouchon
		 */
		CriteriaItem item = new CriteriaItem();
		item.setLabel("label1");
		item.setValue("value1");
		
		CriteriaItem item2 = new CriteriaItem();
		item.setLabel("label2");
		item.setValue("value2");
		CriteriaItem item3 = new CriteriaItem();
		item.setLabel("label3");
		item.setValue("value3");
		CriteriaItem item4 = new CriteriaItem();
		item.setLabel("label4");
		item.setValue("value4");
		
		cis[0] = item;
		cis[1] = item2;
		cis[2] = item3;
		cis[3] = item4;
		
		/*
		 * Fin bouchon
		 */
		

		UIComponent refComponent = (UIComponent) configuration;
		Converter converter = resultCriteria.getConfig().getCriteriaConverter();

		IJavaScriptWriter ijs = ow.writeSymbol("_items").write('[');
		for (int j = 0; j < cis.length; j++) {
			CriteriaItem ci = cis[j];

			if (j > 0) {
				ijs.write(',');
			}

			IObjectLiteralWriter itemsW = ijs.writeObjectLiteral(true);
			itemsW.writeSymbol("_label").writeString(ci.getLabel());

			Object itemValue = ci.getValue();

			String itemConvertedValue = ValuesTools
					.convertValueToString(itemValue, converter, refComponent,
							jsWriter.getFacesContext());

			itemsW.writeSymbol("_value").writeString(itemConvertedValue);

			itemsW.end();
		}

		ijs.write(']');

		ow.end();

	}

}
