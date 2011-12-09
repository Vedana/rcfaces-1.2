package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ITypedComponentCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IFontCapability;

/**
 * <p>The text Component is a placeholder for displaying text.</p>
 * <p>The text Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; justification</li>
 * <li>Margin</li>
 * <li>Help</li>
 * <li>Visibility</li>
 * <li>Events Handling</li>
 * <li>Association with another component</li>
 * </ul>
 * </p>
 */
public class TextComponent extends AbstractOutputComponent implements 
	ITextCapability,
	ITextDirectionCapability,
	IFontCapability,
	ITextAlignmentCapability,
	IAccessKeyCapability,
	IForCapability,
	ITypedComponentCapability {

	private static final Log LOG = LogFactory.getLog(TextComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.text";

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractOutputComponent.BEHAVIOR_EVENT_NAMES;

	public TextComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public TextComponent(String componentId) {
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
		return org.rcfaces.core.internal.tools.ValuesTools.valueToString(this, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "text" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextSetted() {
		return getStateHelper().get(Properties.TEXT)!=null;
	}

	public void setText(java.lang.String text) {
		setValue(text);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return (Integer)getStateHelper().eval(Properties.TEXT_DIRECTION, 0);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return getStateHelper().get(Properties.TEXT_DIRECTION)!=null;
	}

	public void setTextDirection(int textDirection) {
		getStateHelper().put(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_BOLD);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return getStateHelper().get(Properties.FONT_BOLD)!=null;
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		getStateHelper().put(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_ITALIC);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return getStateHelper().get(Properties.FONT_ITALIC)!=null;
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		getStateHelper().put(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_NAME);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return getStateHelper().get(Properties.FONT_NAME)!=null;
	}

	public void setFontName(java.lang.String fontName) {
		getStateHelper().put(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FONT_SIZE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return getStateHelper().get(Properties.FONT_SIZE)!=null;
	}

	public void setFontSize(java.lang.String fontSize) {
		getStateHelper().put(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return (Boolean)getStateHelper().eval(Properties.FONT_UNDERLINE);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return getStateHelper().get(Properties.FONT_UNDERLINE)!=null;
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		getStateHelper().put(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TEXT_ALIGNMENT);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return getStateHelper().get(Properties.TEXT_ALIGNMENT)!=null;
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		getStateHelper().put(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.ACCESS_KEY);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return getStateHelper().get(Properties.ACCESS_KEY)!=null;
	}

	public void setAccessKey(java.lang.String accessKey) {
		getStateHelper().put(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.FOR);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return getStateHelper().get(Properties.FOR)!=null;
	}

	public void setFor(java.lang.String forValue) {
		getStateHelper().put(Properties.FOR, forValue);
	}

	public java.lang.String getType() {
		return getType(null);
	}

	/**
	 * See {@link #getType() getType()} for more details
	 */
	public java.lang.String getType(javax.faces.context.FacesContext facesContext) {
		return (String)getStateHelper().eval(Properties.TYPE);
	}

	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTypeSetted() {
		return getStateHelper().get(Properties.TYPE)!=null;
	}

	public void setType(java.lang.String type) {
		getStateHelper().put(Properties.TYPE, type);
	}

}
