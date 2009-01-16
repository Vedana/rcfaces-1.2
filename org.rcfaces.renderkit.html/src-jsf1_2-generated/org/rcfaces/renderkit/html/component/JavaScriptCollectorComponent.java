package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class JavaScriptCollectorComponent extends CameliaBaseComponent {

	private static final Log LOG = LogFactory.getLog(JavaScriptCollectorComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.html.javaScriptCollector";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"mergeScripts"}));
	}

	public JavaScriptCollectorComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public JavaScriptCollectorComponent(String componentId) {
		this();
		setId(componentId);
	}

	public boolean isMergeScripts() {
		return isMergeScripts(null);
	}

	public boolean isMergeScripts(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.MERGE_SCRIPTS, false, facesContext);
	}

	public void setMergeScripts(boolean mergeScripts) {
		engine.setProperty(Properties.MERGE_SCRIPTS, mergeScripts);
	}

	/**
	 * Returns <code>true</code> if the attribute "mergeScripts" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isMergeScriptsSetted() {
		return engine.isPropertySetted(Properties.MERGE_SCRIPTS);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
