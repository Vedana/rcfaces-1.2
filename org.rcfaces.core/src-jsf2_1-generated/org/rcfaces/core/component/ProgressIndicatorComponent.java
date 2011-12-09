package org.rcfaces.core.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.TextComponent;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.AbstractBasicComponent;
import org.rcfaces.core.internal.Constants;
import javax.el.ValueExpression;
import org.rcfaces.core.component.ProgressBarComponent;
import javax.faces.FacesException;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.internal.component.IInitializationState;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

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

	private static final Log LOG = LogFactory.getLog(ProgressIndicatorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.progressIndicator";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractBasicComponent.BEHAVIOR_EVENT_NAMES;

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
		return (Boolean)getStateHelper().eval(Properties.INDETERMINATE, false);
	}

	/**
	 * Sets a boolean value indicating wether the value is not determinated.
	 * @param indeterminate true if not determinated
	 */
	public void setIndeterminate(boolean indeterminate) {
		 getStateHelper().put(Properties.INDETERMINATE, indeterminate);
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
		return getStateHelper().get(Properties.INDETERMINATE)!=null;
	}

}
