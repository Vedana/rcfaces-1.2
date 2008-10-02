package org.rcfaces.renderkit.svg.component;

import java.lang.String;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import java.util.Arrays;
import org.rcfaces.renderkit.svg.component.Properties;
import java.util.Set;
import java.util.HashSet;

public abstract class NodeComponent extends CameliaItemComponent {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"targetId","rendered"}));
	}


	public void setTargetId(String src) {


			setItemValue(src);
			
	}

	public String getTargetId() {


			return (String)getItemValue();
			
	}

	public boolean isRendered() {
		return isRendered(null);
	}

	public boolean isRendered(javax.faces.context.FacesContext facesContext) {
		return engine.getBoolProperty(Properties.RENDERED, true, facesContext);
	}

	public void setRendered(boolean rendered) {
		engine.setProperty(Properties.RENDERED, rendered);
	}

	/**
	 * Returns <code>true</code> if the attribute "rendered" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRenderedSetted() {
		return engine.isPropertySetted(Properties.RENDERED);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.TARGET_ID.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueExpression(name, binding);
	}
}
