package org.rcfaces.renderkit.svg.internal.taglib;

import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import javax.faces.el.ValueBinding;
import org.rcfaces.renderkit.svg.component.ImageComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_1;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.internal.taglib.AbstractOutputTag;

public class ImageTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageTag.class);

	private String imageURL;
	private String imageHeight;
	private String imageWidth;
	private String alternateText;
	private String fontBold;
	private String fontItalic;
	private String fontName;
	private String fontSize;
	private String fontUnderline;
	private String filterProperties;
	private String pixelUnitToMillimeter;
	private String curveFlatness;
	private String distanceTolerance;
	public String getComponentType() {
		return ImageComponent.COMPONENT_TYPE;
	}

	public final String getImageURL() {
		return imageURL;
	}

	public final void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public final String getImageHeight() {
		return imageHeight;
	}

	public final void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public final String getImageWidth() {
		return imageWidth;
	}

	public final void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public final String getAlternateText() {
		return alternateText;
	}

	public final void setAlternateText(String alternateText) {
		this.alternateText = alternateText;
	}

	public final String getFontBold() {
		return fontBold;
	}

	public final void setFontBold(String fontBold) {
		this.fontBold = fontBold;
	}

	public final String getFontItalic() {
		return fontItalic;
	}

	public final void setFontItalic(String fontItalic) {
		this.fontItalic = fontItalic;
	}

	public final String getFontName() {
		return fontName;
	}

	public final void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public final String getFontSize() {
		return fontSize;
	}

	public final void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public final String getFontUnderline() {
		return fontUnderline;
	}

	public final void setFontUnderline(String fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public final String getFilterProperties() {
		return filterProperties;
	}

	public final void setFilterProperties(String filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setPixelUnitToMillimeter(String pixelUnitToMillimeter) {
		this.pixelUnitToMillimeter = pixelUnitToMillimeter;
	}

	public final void setCurveFlatness(String curveFlatness) {
		this.curveFlatness = curveFlatness;
	}

	public final void setDistanceTolerance(String distanceTolerance) {
		this.distanceTolerance = distanceTolerance;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (ImageComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  imageURL='"+imageURL+"'");
			LOG.debug("  imageHeight='"+imageHeight+"'");
			LOG.debug("  imageWidth='"+imageWidth+"'");
			LOG.debug("  alternateText='"+alternateText+"'");
			LOG.debug("  fontBold='"+fontBold+"'");
			LOG.debug("  fontItalic='"+fontItalic+"'");
			LOG.debug("  fontName='"+fontName+"'");
			LOG.debug("  fontSize='"+fontSize+"'");
			LOG.debug("  fontUnderline='"+fontUnderline+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  pixelUnitToMillimeter='"+pixelUnitToMillimeter+"'");
			LOG.debug("  curveFlatness='"+curveFlatness+"'");
			LOG.debug("  distanceTolerance='"+distanceTolerance+"'");
		}
		if ((uiComponent instanceof ImageComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'ImageComponent'.");
		}

		super.setProperties(uiComponent);

		ImageComponent component = (ImageComponent) uiComponent;
		FacesContext facesContext = getFacesContext();
		Application application = facesContext.getApplication();

		if (imageURL != null) {
			if (isValueReference(imageURL)) {
				ValueBinding vb = application.createValueBinding(imageURL);
				component.setValueBinding(Properties.IMAGE_URL, vb);

			} else {
				component.setImageURL(imageURL);
			}
		}

		if (imageHeight != null) {
			if (isValueReference(imageHeight)) {
				ValueBinding vb = application.createValueBinding(imageHeight);
				component.setValueBinding(Properties.IMAGE_HEIGHT, vb);

			} else {
				component.setImageHeight(getInt(imageHeight));
			}
		}

		if (imageWidth != null) {
			if (isValueReference(imageWidth)) {
				ValueBinding vb = application.createValueBinding(imageWidth);
				component.setValueBinding(Properties.IMAGE_WIDTH, vb);

			} else {
				component.setImageWidth(getInt(imageWidth));
			}
		}

		if (alternateText != null) {
			if (isValueReference(alternateText)) {
				ValueBinding vb = application.createValueBinding(alternateText);
				component.setValueBinding(Properties.ALTERNATE_TEXT, vb);

			} else {
				component.setAlternateText(alternateText);
			}
		}

		if (fontBold != null) {
			if (isValueReference(fontBold)) {
				ValueBinding vb = application.createValueBinding(fontBold);
				component.setValueBinding(Properties.FONT_BOLD, vb);

			} else {
				component.setFontBold(getBoolean(fontBold));
			}
		}

		if (fontItalic != null) {
			if (isValueReference(fontItalic)) {
				ValueBinding vb = application.createValueBinding(fontItalic);
				component.setValueBinding(Properties.FONT_ITALIC, vb);

			} else {
				component.setFontItalic(getBoolean(fontItalic));
			}
		}

		if (fontName != null) {
			if (isValueReference(fontName)) {
				ValueBinding vb = application.createValueBinding(fontName);
				component.setValueBinding(Properties.FONT_NAME, vb);

			} else {
				component.setFontName(fontName);
			}
		}

		if (fontSize != null) {
			if (isValueReference(fontSize)) {
				ValueBinding vb = application.createValueBinding(fontSize);
				component.setValueBinding(Properties.FONT_SIZE, vb);

			} else {
				component.setFontSize(fontSize);
			}
		}

		if (fontUnderline != null) {
			if (isValueReference(fontUnderline)) {
				ValueBinding vb = application.createValueBinding(fontUnderline);
				component.setValueBinding(Properties.FONT_UNDERLINE, vb);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline));
			}
		}

		if (filterProperties != null) {
			if (isValueReference(filterProperties)) {
				ValueBinding vb = application.createValueBinding(filterProperties);
				component.setValueBinding(Properties.FILTER_PROPERTIES, vb);

			} else {
				component.setFilterProperties(filterProperties);
			}
		}

		if (pixelUnitToMillimeter != null) {
			if (isValueReference(pixelUnitToMillimeter)) {
				ValueBinding vb = application.createValueBinding(pixelUnitToMillimeter);
				component.setValueBinding(Properties.PIXEL_UNIT_TO_MILLIMETER, vb);

			} else {
				component.setPixelUnitToMillimeter(getDouble(pixelUnitToMillimeter));
			}
		}

		if (curveFlatness != null) {
			if (isValueReference(curveFlatness)) {
				ValueBinding vb = application.createValueBinding(curveFlatness);
				component.setValueBinding(Properties.CURVE_FLATNESS, vb);

			} else {
				component.setCurveFlatness(getDouble(curveFlatness));
			}
		}

		if (distanceTolerance != null) {
			if (isValueReference(distanceTolerance)) {
				ValueBinding vb = application.createValueBinding(distanceTolerance);
				component.setValueBinding(Properties.DISTANCE_TOLERANCE, vb);

			} else {
				component.setDistanceTolerance(getDouble(distanceTolerance));
			}
		}
	}

	public void release() {
		imageURL = null;
		imageHeight = null;
		imageWidth = null;
		alternateText = null;
		fontBold = null;
		fontItalic = null;
		fontName = null;
		fontSize = null;
		fontUnderline = null;
		filterProperties = null;
		pixelUnitToMillimeter = null;
		curveFlatness = null;
		distanceTolerance = null;

		super.release();
	}

}
