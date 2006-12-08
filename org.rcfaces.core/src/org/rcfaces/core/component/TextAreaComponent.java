package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextEntryComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class TextAreaComponent extends TextEntryComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.textArea";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(TextEntryComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"rowNumber"}));
	}

	public TextAreaComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextAreaComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final int getRowNumber() {
		return getRowNumber(null);
	}

	public final int getRowNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ROW_NUMBER, 0, facesContext);
	}

	public final void setRowNumber(int rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	public final void setRowNumber(ValueBinding rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	public final boolean isRowNumberSetted() {
		return engine.isPropertySetted(Properties.ROW_NUMBER);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
