package org.rcfaces.renderkit.html.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.html.component.JavaScriptCollectorComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;

public class JavaScriptCollectorTag extends CameliaTag implements Tag {


	private static final Log LOG=LogFactory.getLog(JavaScriptCollectorTag.class);

	private String mergeScripts;
	public String getComponentType() {
		return JavaScriptCollectorComponent.COMPONENT_TYPE;
	}

	public final void setMergeScripts(String mergeScripts) {
		this.mergeScripts = mergeScripts;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (JavaScriptCollectorComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  mergeScripts='"+mergeScripts+"'");
		}
		if ((uiComponent instanceof JavaScriptCollectorComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'JavaScriptCollectorComponent'.");
		}

		super.setProperties(uiComponent);

		JavaScriptCollectorComponent component = (JavaScriptCollectorComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (mergeScripts != null) {
			if (isValueReference(mergeScripts)) {
				ValueBinding vb = application.createValueBinding(mergeScripts);
				component.setValueBinding(Properties.MERGE_SCRIPTS, vb);

			} else {
				component.setMergeScripts(getBool(mergeScripts));
			}
		}
	}

	public void release() {
		mergeScripts = null;

		super.release();
	}

}
