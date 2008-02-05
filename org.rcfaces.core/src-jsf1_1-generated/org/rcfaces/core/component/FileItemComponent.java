package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.component.CameliaItemComponent;
import javax.faces.el.ValueBinding;
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

	public void setValueBinding(String name, ValueBinding binding) {
		if (Properties.SRC.equals(name)) {
			name=Properties.ITEM_VALUE;
		}
		super.setValueBinding(name, binding);
	}
}
