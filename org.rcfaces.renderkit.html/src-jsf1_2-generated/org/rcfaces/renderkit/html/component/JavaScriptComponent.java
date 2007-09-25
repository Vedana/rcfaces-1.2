package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

/**
 * Specifies includes and required javascript classes.
 */
public class JavaScriptComponent extends CameliaBaseComponent implements 
	ITextCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.html.javaScript";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaBaseComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"text","srcCharSet","requiredFiles","src","requiredClasses"}));
	}

	public JavaScriptComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public JavaScriptComponent(String componentId) {
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

	/**
	 * Returns a string value specifying a list of javascript files to include.
	 * @return list of javascript files
	 */
	public String getRequiredFiles() {
		return getRequiredFiles(null);
	}

	/**
	 * Returns a string value specifying a list of javascript files to include.
	 * @return list of javascript files
	 */
	public String getRequiredFiles(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.REQUIRED_FILES, facesContext);
	}

	/**
	 * Sets a string value specifying a list of javascript files to include.
	 * @param requiredFiles list of javascript files
	 */
	public void setRequiredFiles(String requiredFiles) {
		engine.setProperty(Properties.REQUIRED_FILES, requiredFiles);
	}

	/**
	 * Sets a string value specifying a list of javascript files to include.
	 * @param requiredFiles list of javascript files
	 */
	/**
	 * Returns <code>true</code> if the attribute "requiredFiles" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRequiredFilesSetted() {
		return engine.isPropertySetted(Properties.REQUIRED_FILES);
	}

	/**
	 * Returns a string value specifying a list of javascript classes to include.
	 * @return list of javascript classes
	 */
	public String getRequiredClasses() {
		return getRequiredClasses(null);
	}

	/**
	 * Returns a string value specifying a list of javascript classes to include.
	 * @return list of javascript classes
	 */
	public String getRequiredClasses(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.REQUIRED_CLASSES, facesContext);
	}

	/**
	 * Sets a string value specifying a list of javascript classes to include.
	 * @param requiredClasses list of javascript classes
	 */
	public void setRequiredClasses(String requiredClasses) {
		engine.setProperty(Properties.REQUIRED_CLASSES, requiredClasses);
	}

	/**
	 * Sets a string value specifying a list of javascript classes to include.
	 * @param requiredClasses list of javascript classes
	 */
	/**
	 * Returns <code>true</code> if the attribute "requiredClasses" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isRequiredClassesSetted() {
		return engine.isPropertySetted(Properties.REQUIRED_CLASSES);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
