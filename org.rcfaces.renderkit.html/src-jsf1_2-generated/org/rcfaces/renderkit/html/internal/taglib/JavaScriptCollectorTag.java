package org.rcfaces.renderkit.html.internal.taglib;

import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import org.apache.commons.logging.LogFactory;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;
import javax.faces.application.Application;
import javax.el.ValueExpression;
import org.rcfaces.renderkit.html.component.JavaScriptCollectorComponent;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.tools.ListenersTools1_2;

public class JavaScriptCollectorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptCollectorTag.class);

	private ValueExpression mergeScripts;
	public String getComponentType() {
		return JavaScriptCollectorComponent.COMPONENT_TYPE;
	}

	public final void setMergeScripts(ValueExpression mergeScripts) {
		this.mergeScripts = mergeScripts;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (JavaScriptCollectorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  mergeScripts='"+mergeScripts+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof JavaScriptCollectorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'JavaScriptCollectorComponent'.");
		}

		JavaScriptCollectorComponent component = (JavaScriptCollectorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (mergeScripts != null) {
			if (mergeScripts.isLiteralText()==false) {
				component.setValueExpression(Properties.MERGE_SCRIPTS, mergeScripts);

			} else {
				component.setMergeScripts(getBool(mergeScripts.getExpressionString()));
			}
		}
	}

	public void release() {
		mergeScripts = null;

		super.release();
	}

}
