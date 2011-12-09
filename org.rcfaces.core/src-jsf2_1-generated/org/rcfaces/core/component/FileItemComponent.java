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
import java.util.Collection;

public abstract class FileItemComponent extends CameliaItemComponent {

	private static final Log LOG = LogFactory.getLog(FileItemComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=CameliaItemComponent.BEHAVIOR_EVENT_NAMES;


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
		return (String)getStateHelper().eval(Properties.CHAR_SET);
	}

	public void setCharSet(String charSet) {
		 getStateHelper().put(Properties.CHAR_SET, charSet);
	}

	/**
	 * Returns <code>true</code> if the attribute "charSet" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isCharSetSetted() {
		return getStateHelper().get(Properties.CHAR_SET)!=null;
	}


	public void setValueExpression(String name, ValueExpression binding) {
		if (Properties.SRC.toString().equals(name)) {
			name=Properties.ITEM_VALUE.toString();
		}
		super.setValueExpression(name, binding);
	}
}
