package org.rcfaces.renderkit.svg.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.renderkit.svg.component.DataSVGComponent;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class DataSVGTag extends SVGTag implements Tag {


	private static final Log LOG=LogFactory.getLog(DataSVGTag.class);

	private ValueExpression rowToolTipId;
	private ValueExpression var;
	private ValueExpression dataModel;
	public String getComponentType() {
		return DataSVGComponent.COMPONENT_TYPE;
	}

	public void setRowToolTipId(ValueExpression rowToolTipId) {
		this.rowToolTipId = rowToolTipId;
	}

	public void setVar(ValueExpression var) {
		this.var = var;
	}

	public void setDataModel(ValueExpression dataModel) {
		this.dataModel = dataModel;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (DataSVGComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  rowToolTipId='"+rowToolTipId+"'");
			LOG.debug("  var='"+var+"'");
			LOG.debug("  dataModel='"+dataModel+"'");
		}
		if ((uiComponent instanceof DataSVGComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'DataSVGComponent'.");
		}

		super.setProperties(uiComponent);

		DataSVGComponent component = (DataSVGComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (rowToolTipId != null) {
			if (rowToolTipId.isLiteralText()==false) {
				component.setValueExpression(Properties.ROW_TOOL_TIP_ID, rowToolTipId);

			} else {
				component.setRowToolTipId(rowToolTipId.getExpressionString());
			}
		}

		if (var != null) {
			if (var.isLiteralText()==false) {
				component.setValueExpression(Properties.VAR, var);

			} else {
				component.setVar(var.getExpressionString());
			}
		}

		if (dataModel != null) {
			if (dataModel.isLiteralText()==false) {
				component.setValueExpression(Properties.DATA_MODEL, dataModel);

			} else {
				component.setDataModel(dataModel.getExpressionString());
			}
		}
	}

	public void release() {
		rowToolTipId = null;
		var = null;
		dataModel = null;

		super.release();
	}

}
