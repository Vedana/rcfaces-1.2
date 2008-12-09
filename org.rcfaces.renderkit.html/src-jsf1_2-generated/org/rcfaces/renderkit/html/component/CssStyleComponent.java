package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

/**
 * Css style
 */
public class CssStyleComponent extends CameliaBaseComponent implements 
	ITextCapability {

	private static final Log LOG = LogFactory.getLog(CssStyleComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.html:cssStyle";

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

	public java.lang.String getText() {
		return getText(null);
	}

	/**
	 * See {@link #getText() getText()} for more details
	 */
	public java.lang.String getText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		engine.setProperty(Properties.TEXT, text);
	}

	/**
	 * Returns an url value pointing to the file to be included.
	 * @return url
	 */
	public String getSrc() {
		return getSrc(null);
	}

	/**
	 * Returns an url value pointing to the file to be included.
	 * @return url
	 */
	public String getSrc(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC, facesContext);
	}

	/**
	 * Sets an url value pointing to the file to be included.
	 * @param src url
	 */
	public void setSrc(String src) {
		engine.setProperty(Properties.SRC, src);
	}

	/**
	 * Sets an url value pointing to the file to be included.
	 * @param src url
	 */
	/**
	 * Returns <code>true</code> if the attribute "src" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSrcSetted() {
		return engine.isPropertySetted(Properties.SRC);
	}

	/**
	 * Returns a string value sprecifying the charset associated to the "src" files.
	 * @return charset
	 */
	public String getSrcCharSet() {
		return getSrcCharSet(null);
	}

	/**
	 * Returns a string value sprecifying the charset associated to the "src" files.
	 * @return charset
	 */
	public String getSrcCharSet(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC_CHAR_SET, facesContext);
	}

	/**
	 * Sets a string value sprecifying the charset associated to the "src" files.
	 * @param srcCharSet charset
	 */
	public void setSrcCharSet(String srcCharSet) {
		engine.setProperty(Properties.SRC_CHAR_SET, srcCharSet);
	}

	/**
	 * Sets a string value sprecifying the charset associated to the "src" files.
	 * @param srcCharSet charset
	 */
	/**
	 * Returns <code>true</code> if the attribute "srcCharSet" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isSrcCharSetSetted() {
		return engine.isPropertySetted(Properties.SRC_CHAR_SET);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
