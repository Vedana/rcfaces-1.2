package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.component.TextEntryComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * <p>The textAreaEntry Component is based on the standard HTML tag &lt;TEXTAREA&gt; and is a <a href="/comps/textEntryComponent.html">textEntry Component</a>.</p>
 * <p>The textAreaEntry Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text &amp; font</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
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

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public final int getRowNumber() {
		return getRowNumber(null);
	}

	/**
	 * Returns an int value specifying the number of rows to be displayed.
	 * @return number of rows
	 */
	public final int getRowNumber(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ROW_NUMBER, 0, facesContext);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public final void setRowNumber(int rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Sets an int value specifying the number of rows to be displayed.
	 * @param rowNumber number of rows
	 */
	public final void setRowNumber(ValueBinding rowNumber) {
		engine.setProperty(Properties.ROW_NUMBER, rowNumber);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowNumber" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowNumberSetted() {
		return engine.isPropertySetted(Properties.ROW_NUMBER);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
