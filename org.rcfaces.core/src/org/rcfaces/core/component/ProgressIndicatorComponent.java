package org.rcfaces.core.component;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.Constants;
import javax.faces.el.ValueBinding;
import javax.faces.FacesException;
import org.rcfaces.core.component.ProgressBarComponent;
import java.util.Arrays;
import javax.faces.component.NamingContainer;
import java.util.Set;
import java.util.HashSet;
import org.rcfaces.core.component.AbstractBasicComponent;

public class ProgressIndicatorComponent extends AbstractBasicComponent implements 
	NamingContainer {

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressIndicator";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"indeterminate"}));
	}

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
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
