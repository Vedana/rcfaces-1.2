package org.rcfaces.core.component;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.Properties;

public class ProgressIndicatorComponent extends AbstractBasicComponent implements 
	NamingContainer {

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressIndicator";


	public ProgressIndicatorComponent() {
		setRendererType(COMPONENT_TYPE);
		if (Constants.TEMPLATE_SUPPORT==false) {
			throw new FacesException("Template Support must be enabled to use this component !");
		}
	}

	public ProgressIndicatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected boolean isTemplateComponent() {
		return true;
	}

	protected void constructTemplate() {
		super.constructTemplate();

				ProgressBarComponent progressBar=new ProgressBarComponent("progressBar");
				getChildren().add(progressBar);

				TextComponent textComponent=new TextComponent("label");
				getChildren().add(textComponent);
			
	}

	public final boolean isIndeterminate() {
		return isIndeterminate(null);
	}

	public final boolean isIndeterminate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.INDETERMINATE, false, facesContext);
	}

	public final void setIndeterminate(boolean indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
	}

	public final void setIndeterminate(ValueBinding indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
	}

	public final boolean isIndeterminateSetted() {
		return engine.isPropertySetted(Properties.INDETERMINATE);
	}

	public void release() {
		super.release();
	}
}
