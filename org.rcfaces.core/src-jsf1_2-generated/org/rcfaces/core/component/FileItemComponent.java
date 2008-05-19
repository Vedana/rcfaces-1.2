package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public abstract class FileItemComponent extends CameliaItemComponent {

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaItemComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"src"}));
	}


	public void setSrc(String src) {


			setItemValue(src);
			
	}

	public String getSrc() {


			return (String)getItemValue();
			
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
