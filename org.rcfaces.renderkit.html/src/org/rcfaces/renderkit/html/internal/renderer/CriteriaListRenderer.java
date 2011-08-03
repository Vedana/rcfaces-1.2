/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CriteriaListComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.ICssWriter;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CriteriaListRenderer extends AbstractCssRenderer {
	private static final String REVISION = "$Revision$";

	protected String getJavaScriptClassName() {
		return JavaScriptClasses.CRITERIA_LIST;
	}

	protected void encodeBegin(IComponentWriter writer) throws WriterException {
		super.encodeBegin(writer);

		FacesContext facesContext = writer.getComponentRenderContext()
				.getFacesContext();

		CriteriaListComponent pagerComponent = (CriteriaListComponent) writer
				.getComponentRenderContext().getComponent();

		IHtmlWriter htmlWriter = (IHtmlWriter) writer;

		htmlWriter.startElement(IHtmlWriter.DIV);
		if (pagerComponent.getWidth(facesContext) == null
				&& pagerComponent.getHeight(facesContext) == null) {
			htmlWriter.writeStyle().writeDisplay(ICssWriter.INLINE);
		}

		writeHtmlAttributes(htmlWriter);
		writeJavaScriptAttributes(htmlWriter);
		writeCssAttributes(htmlWriter);

		String forValue = pagerComponent.getFor(facesContext);
		if (forValue == null) {
			throw new WriterException("'for' attribute must be specified !",
					null, pagerComponent);
		}

		htmlWriter.writeAttribute("v:for", forValue);

		String criteriaFormat = pagerComponent.getCriteriaFormat(facesContext);
		if (criteriaFormat != null) {
			criteriaFormat = ParamUtils.formatMessage(pagerComponent,
					criteriaFormat);

			htmlWriter.writeAttribute("v:criteriaFormat", criteriaFormat);
		}

		String noPagedMessage = pagerComponent
				.getNoCriteriaMessage(facesContext);
		if (noPagedMessage != null) {
			noPagedMessage = ParamUtils.formatMessage(pagerComponent,
					noPagedMessage);

			htmlWriter.writeAttribute("v:noCriteriaMessage", noPagedMessage);
		}

		htmlWriter.endElement(IHtmlWriter.DIV);

		htmlWriter.getJavaScriptEnableMode().enableOnInit();
	}
}
