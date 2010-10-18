package org.rcfaces.core.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.component.ToolFolderComponent;

public class TextEditorToolFolderComponent extends ToolFolderComponent implements 
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(TextEditorToolFolderComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEditorToolFolder";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ToolFolderComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"fontSizes","for","itemTypes","fontNames"}));
	}

	public TextEditorToolFolderComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextEditorToolFolderComponent(String componentId) {
		this();
		setId(componentId);
	}

	public String getItemTypes() {
		return getItemTypes(null);
	}

	public String getItemTypes(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ITEM_TYPES, facesContext);
	}

	public void setItemTypes(String itemTypes) {
		engine.setProperty(Properties.ITEM_TYPES, itemTypes);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemTypesSetted() {
		return engine.isPropertySetted(Properties.ITEM_TYPES);
	}

	public String getFontSizes() {
		return getFontSizes(null);
	}

	public String getFontSizes(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZES, facesContext);
	}

	public void setFontSizes(String fontSizes) {
		engine.setProperty(Properties.FONT_SIZES, fontSizes);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSizes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontSizesSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZES);
	}

	public String getFontNames() {
		return getFontNames(null);
	}

	public String getFontNames(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAMES, facesContext);
	}

	public void setFontNames(String fontNames) {
		engine.setProperty(Properties.FONT_NAMES, fontNames);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontNames" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontNamesSetted() {
		return engine.isPropertySetted(Properties.FONT_NAMES);
	}

	public String getFor() {
		return getFor(null);
	}

	public String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	public void setFor(String forVal) {
		engine.setProperty(Properties.FOR, forVal);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
