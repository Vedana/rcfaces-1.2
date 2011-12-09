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
import java.util.Collection;

public class TextEditorToolFolderComponent extends ToolFolderComponent implements 
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(TextEditorToolFolderComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.textEditorToolFolder";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=ToolFolderComponent.BEHAVIOR_EVENT_NAMES;

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
		return (String)getStateHelper().eval(Properties.ITEM_TYPES);
	}

	public void setItemTypes(String itemTypes) {
		 getStateHelper().put(Properties.ITEM_TYPES, itemTypes);
	}

	/**
	 * Returns <code>true</code> if the attribute "itemTypes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isItemTypesSetted() {
		return getStateHelper().get(Properties.ITEM_TYPES)!=null;
	}

	public String getFontSizes() {
		return getFontSizes(null);
	}

	public String getFontSizes(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_SIZES);
	}

	public void setFontSizes(String fontSizes) {
		 getStateHelper().put(Properties.FONT_SIZES, fontSizes);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSizes" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontSizesSetted() {
		return getStateHelper().get(Properties.FONT_SIZES)!=null;
	}

	public String getFontNames() {
		return getFontNames(null);
	}

	public String getFontNames(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_NAMES);
	}

	public void setFontNames(String fontNames) {
		 getStateHelper().put(Properties.FONT_NAMES, fontNames);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontNames" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isFontNamesSetted() {
		return getStateHelper().get(Properties.FONT_NAMES)!=null;
	}

	public String getFor() {
		return getFor(null);
	}

	public String getFor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR);
	}

	public void setFor(String forVal) {
		 getStateHelper().put(Properties.FOR, forVal);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

}
