package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;

public abstract class FileItemComponent extends CameliaItemComponent {

	private static final Log LOG = LogFactory.getLog(FileItemComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"charSet","src"}));
	}


	public void setSrc(String src) {


				setItemValue(src);
			
	}

	public String getSrc() {


				return (String)getItemValue();
			
	}

	public String getCharSet() {
		return getCharSet(null);
	}

	public String getCharSet(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.CHAR_SET, facesContext);
		return s;
	}

	public void setCharSet(String charSet) {
		engine.setProperty(Properties.CHAR_SET, charSet);
	}

	/**
	 * Returns <code>true</code> if the attribute "charSet" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCharSetSetted() {
		return engine.isPropertySetted(Properties.CHAR_SET);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.SRC.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueExpression(name, binding);
	}
}
