package org.rcfaces.core.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import org.rcfaces.core.component.ProgressBarComponent;
import java.util.HashSet;
import org.rcfaces.core.internal.component.IInitializationState;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.AbstractBasicComponent;

/**
 * <p>The progressIndicator Component indicates the evolution of a task. It uses the <a href="/comps/progressBarComponent.html">porgressBar Component</a> and add text information to it.</p>
 * <p>The progressIndicator Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Margin &amp; border</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * </ul>
 * </p>
 */
public class ProgressIndicatorComponent extends AbstractBasicComponent implements 
	NamingContainer {

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressIndicator";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractBasicComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"indeterminate"}));
	}

	public ProgressIndicatorComponent() {
		setRendererType(COMPONENT_TYPE);
		if (Constants.TEMPLATE_ENGINE_SUPPORT==false) {
			throw new FacesException("Template Support must be enabled to use this component !");
		}
	}

	public ProgressIndicatorComponent(String componentId) {
		this();
		setId(componentId);
	}

	protected boolean isTemplateComponent(IInitializationState state) {
		return true;
	}

	protected void constructTemplate(IInitializationState state) {
		super.constructTemplate(state);

				ProgressBarComponent progressBar=new ProgressBarComponent("progressBar");
				getChildren().add(progressBar);

				TextComponent textComponent=new TextComponent("label");
				getChildren().add(textComponent);
			
	}

	/**
	 * Returns a boolean value indicating wether the value is not determinated.
	 * @return true if not determinated
	 */
	public boolean isIndeterminate() {
		return isIndeterminate(null);
	}

	/**
	 * Returns a boolean value indicating wether the value is not determinated.
	 * @return true if not determinated
	 */
	public boolean isIndeterminate(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.INDETERMINATE, false, facesContext);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	public void setIndeterminate(boolean indeterminate) {
		engine.setProperty(Properties.INDETERMINATE, indeterminate);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	/**
	 * Returns <code>true</code> if the attribute "indeterminate" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isIndeterminateSetted() {
		return engine.isPropertySetted(Properties.INDETERMINATE);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
