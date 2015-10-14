package org.rcfaces.core.component;

import org.rcfaces.core.component.capability.IErrorTextCapability;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.capability.ITypedComponentCapability;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.IForCapability;
import org.rcfaces.core.component.capability.IAudioDescriptionCapability;
import org.rcfaces.core.component.capability.ITextDirectionCapability;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractOutputComponent;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.IFontCapability;

/**
 * <p>The text Component is a placeholder for displaying text.</p>
 * <p>The text Component has the following capabilities :
 * <ul>
 * <li>ITextCapability</li>
 * <li>ITextDirectionCapability</li>
 * <li>IFontCapability</li>
 * <li>ITextAlignmentCapability</li>
 * <li>IAccessKeyCapability</li>
 * <li>IForCapability</li>
 * <li>ITypedComponentCapability </li>
 * </ul>
 * </p>
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/TextComponent.html">text</a> renderer is linked to the <a href="/jsdocs/index.html?f_text.html" target="_blank">f_text</a> javascript class. f_text extends f_component</p>
 * 
 * <p> Table of component style classes: </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody>
 * 
 * <tr style="text-align:left">
 * <th  width="33%">Style Name</th>
 * <th width="50%">Description</th>
 * </tr>
 * 
 * <tr  style="text-align:left">
 * <td width="33%">f_text</td>
 * <td width="50%">Defines styles for the wrapper element</td>
 * </tr>
 * 
 * </tbody>
 * </table>
 */
public class TextComponent extends AbstractOutputComponent implements 
	ITextCapability,
	IAudioDescriptionCapability,
	ITextDirectionCapability,
	IFontCapability,
	ITextAlignmentCapability,
	IAccessKeyCapability,
	IForCapability,
	ITypedComponentCapability,
	IErrorTextCapability {

	private static final Log LOG = LogFactory.getLog(TextComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.core.text";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractOutputComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"errorText","accessKey","fontName","fontItalic","text","fontUnderline","for","textAlignment","fontBold","type","fontSize","audioDescription","textDirection"}));
	}
	protected static final String CAMELIA_VALUE_ALIAS="text";

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
		return engine.isPropertySetted(Properties.TEXT);
	}

	public void setText(java.lang.String text) {
		setValue(text);
	}

	public java.lang.String getAudioDescription() {
		return getAudioDescription(null);
	}

	/**
	 * See {@link #getAudioDescription() getAudioDescription()} for more details
	 */
	public java.lang.String getAudioDescription(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.AUDIO_DESCRIPTION, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "audioDescription" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAudioDescriptionSetted() {
		return engine.isPropertySetted(Properties.AUDIO_DESCRIPTION);
	}

	public void setAudioDescription(java.lang.String audioDescription) {
		engine.setProperty(Properties.AUDIO_DESCRIPTION, audioDescription);
	}

	public int getTextDirection() {
		return getTextDirection(null);
	}

	/**
	 * See {@link #getTextDirection() getTextDirection()} for more details
	 */
	public int getTextDirection(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.TEXT_DIRECTION,0, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textDirection" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextDirectionSetted() {
		return engine.isPropertySetted(Properties.TEXT_DIRECTION);
	}

	public void setTextDirection(int textDirection) {
		engine.setProperty(Properties.TEXT_DIRECTION, textDirection);
	}

	public java.lang.Boolean getFontBold() {
		return getFontBold(null);
	}

	/**
	 * See {@link #getFontBold() getFontBold()} for more details
	 */
	public java.lang.Boolean getFontBold(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_BOLD, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontBold" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontBoldSetted() {
		return engine.isPropertySetted(Properties.FONT_BOLD);
	}

	public void setFontBold(java.lang.Boolean fontBold) {
		engine.setProperty(Properties.FONT_BOLD, fontBold);
	}

	public java.lang.Boolean getFontItalic() {
		return getFontItalic(null);
	}

	/**
	 * See {@link #getFontItalic() getFontItalic()} for more details
	 */
	public java.lang.Boolean getFontItalic(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_ITALIC, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontItalic" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontItalicSetted() {
		return engine.isPropertySetted(Properties.FONT_ITALIC);
	}

	public void setFontItalic(java.lang.Boolean fontItalic) {
		engine.setProperty(Properties.FONT_ITALIC, fontItalic);
	}

	public java.lang.String getFontName() {
		return getFontName(null);
	}

	/**
	 * See {@link #getFontName() getFontName()} for more details
	 */
	public java.lang.String getFontName(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_NAME, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontName" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontNameSetted() {
		return engine.isPropertySetted(Properties.FONT_NAME);
	}

	public void setFontName(java.lang.String fontName) {
		engine.setProperty(Properties.FONT_NAME, fontName);
	}

	public java.lang.String getFontSize() {
		return getFontSize(null);
	}

	/**
	 * See {@link #getFontSize() getFontSize()} for more details
	 */
	public java.lang.String getFontSize(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FONT_SIZE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontSize" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontSizeSetted() {
		return engine.isPropertySetted(Properties.FONT_SIZE);
	}

	public void setFontSize(java.lang.String fontSize) {
		engine.setProperty(Properties.FONT_SIZE, fontSize);
	}

	public java.lang.Boolean getFontUnderline() {
		return getFontUnderline(null);
	}

	/**
	 * See {@link #getFontUnderline() getFontUnderline()} for more details
	 */
	public java.lang.Boolean getFontUnderline(javax.faces.context.FacesContext facesContext) {
		return engine.getBooleanProperty(Properties.FONT_UNDERLINE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "fontUnderline" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isFontUnderlineSetted() {
		return engine.isPropertySetted(Properties.FONT_UNDERLINE);
	}

	public void setFontUnderline(java.lang.Boolean fontUnderline) {
		engine.setProperty(Properties.FONT_UNDERLINE, fontUnderline);
	}

	public java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	/**
	 * See {@link #getTextAlignment() getTextAlignment()} for more details
	 */
	public java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "textAlignment" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTextAlignmentSetted() {
		return engine.isPropertySetted(Properties.TEXT_ALIGNMENT);
	}

	public void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public java.lang.String getAccessKey() {
		return getAccessKey(null);
	}

	/**
	 * See {@link #getAccessKey() getAccessKey()} for more details
	 */
	public java.lang.String getAccessKey(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ACCESS_KEY, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "accessKey" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isAccessKeySetted() {
		return engine.isPropertySetted(Properties.ACCESS_KEY);
	}

	public void setAccessKey(java.lang.String accessKey) {
		engine.setProperty(Properties.ACCESS_KEY, accessKey);
	}

	public java.lang.String getFor() {
		return getFor(null);
	}

	/**
	 * See {@link #getFor() getFor()} for more details
	 */
	public java.lang.String getFor(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.FOR, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "for" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isForSetted() {
		return engine.isPropertySetted(Properties.FOR);
	}

	public void setFor(java.lang.String forValue) {
		engine.setProperty(Properties.FOR, forValue);
	}

	public java.lang.String getType() {
		return getType(null);
	}

	/**
	 * See {@link #getType() getType()} for more details
	 */
	public java.lang.String getType(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TYPE, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "type" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isTypeSetted() {
		return engine.isPropertySetted(Properties.TYPE);
	}

	public void setType(java.lang.String type) {
		engine.setProperty(Properties.TYPE, type);
	}

	public java.lang.String getErrorText() {
		return getErrorText(null);
	}

	/**
	 * See {@link #getErrorText() getErrorText()} for more details
	 */
	public java.lang.String getErrorText(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ERROR_TEXT, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "errorText" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isErrorTextSetted() {
		return engine.isPropertySetted(Properties.ERROR_TEXT);
	}

	public void setErrorText(java.lang.String errorText) {
		engine.setProperty(Properties.ERROR_TEXT, errorText);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return CAMELIA_VALUE_ALIAS;
	}
}
