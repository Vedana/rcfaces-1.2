package org.rcfaces.renderkit.html.component;

import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.internal.component.CameliaBaseComponent;
import java.util.Set;
import java.util.HashSet;

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

	public final String getSrc() {
		return getSrc(null);
	}

	public final String getSrc(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC, facesContext);
	}

	public final void setSrc(String src) {
		engine.setProperty(Properties.SRC, src);
	}

	public final void setSrc(ValueBinding src) {
		engine.setProperty(Properties.SRC, src);
	}

	public final boolean isSrcSetted() {
		return engine.isPropertySetted(Properties.SRC);
	}

	public final String getSrcCharSet() {
		return getSrcCharSet(null);
	}

	public final String getSrcCharSet(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.SRC_CHAR_SET, facesContext);
	}

	public final void setSrcCharSet(String srcCharSet) {
		engine.setProperty(Properties.SRC_CHAR_SET, srcCharSet);
	}

	public final void setSrcCharSet(ValueBinding srcCharSet) {
		engine.setProperty(Properties.SRC_CHAR_SET, srcCharSet);
	}

	public final boolean isSrcCharSetSetted() {
		return engine.isPropertySetted(Properties.SRC_CHAR_SET);
	}

	public final String getRequiredFiles() {
		return getRequiredFiles(null);
	}

	public final String getRequiredFiles(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.REQUIRED_FILES, facesContext);
	}

	public final void setRequiredFiles(String requiredFiles) {
		engine.setProperty(Properties.REQUIRED_FILES, requiredFiles);
	}

	public final void setRequiredFiles(ValueBinding requiredFiles) {
		engine.setProperty(Properties.REQUIRED_FILES, requiredFiles);
	}

	public final boolean isRequiredFilesSetted() {
		return engine.isPropertySetted(Properties.REQUIRED_FILES);
	}

	public final String getRequiredClasses() {
		return getRequiredClasses(null);
	}

	public final String getRequiredClasses(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.REQUIRED_CLASSES, facesContext);
	}

	public final void setRequiredClasses(String requiredClasses) {
		engine.setProperty(Properties.REQUIRED_CLASSES, requiredClasses);
	}

	public final void setRequiredClasses(ValueBinding requiredClasses) {
		engine.setProperty(Properties.REQUIRED_CLASSES, requiredClasses);
	}

	public final boolean isRequiredClassesSetted() {
		return engine.isPropertySetted(Properties.REQUIRED_CLASSES);
	}

	public void release() {
		super.release();
	}
	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
