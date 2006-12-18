package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

public class CssStyleComponent extends CameliaBaseComponent implements 
	ITextCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.html.cssStyle";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"text","srcCharSet","src"}));
	}

	public CssStyleComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CssStyleComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final java.lang.String getText() {
		return getText(null);
	}

	public final java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	public final void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	public final void setText(ValueBinding text) {
		engine.setProperty(Properties.TEXT, text);
	}

	/**
	 * Returns an url value pointing to the file to be included.
	 * @return url
	 */
	public final String getSrc() {
		return getSrc(null);
	}

	/**
	 * Returns an url value pointing to the file to be included.
	 * @return url
	 */
	public final String getSrc(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC, facesContext);
	}

	/**
	 * Sets an url value pointing to the file to be included.
	 * @param src url
	 */
	public final void setSrc(String src) {
		engine.setProperty(Properties.SRC, src);
	}

	/**
	 * Sets an url value pointing to the file to be included.
	 * @param src url
	 */
	public final void setSrc(ValueBinding src) {
		engine.setProperty(Properties.SRC, src);
	}

	/**
	 * Returns <code>true</code> if the attribute "src" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSrcSetted() {
		return engine.isPropertySetted(Properties.SRC);
	}

	/**
	 * Returns a string value sprecifying the charset associated to the "src" files.
	 * @return charset
	 */
	public final String getSrcCharSet() {
		return getSrcCharSet(null);
	}

	/**
	 * Returns a string value sprecifying the charset associated to the "src" files.
	 * @return charset
	 */
	public final String getSrcCharSet(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC_CHAR_SET, facesContext);
	}

	/**
	 * Sets a string value sprecifying the charset associated to the "src" files.
	 * @param srcCharSet charset
	 */
	public final void setSrcCharSet(String srcCharSet) {
		engine.setProperty(Properties.SRC_CHAR_SET, srcCharSet);
	}

	/**
	 * Sets a string value sprecifying the charset associated to the "src" files.
	 * @param srcCharSet charset
	 */
	public final void setSrcCharSet(ValueBinding srcCharSet) {
		engine.setProperty(Properties.SRC_CHAR_SET, srcCharSet);
	}

	/**
	 * Returns <code>true</code> if the attribute "srcCharSet" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isSrcCharSetSetted() {
		return engine.isPropertySetted(Properties.SRC_CHAR_SET);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
