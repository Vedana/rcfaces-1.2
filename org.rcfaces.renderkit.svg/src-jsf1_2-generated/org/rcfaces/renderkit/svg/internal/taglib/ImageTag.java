package org.rcfaces.renderkit.svg.internal.taglib;

import javax.faces.application.Application;
import org.rcfaces.renderkit.svg.component.ImageComponent;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.taglib.CameliaTag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.taglib.AbstractOutputTag;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class ImageTag extends AbstractOutputTag implements Tag {


	private static final Log LOG=LogFactory.getLog(ImageTag.class);

	private ValueExpression imageURL;
	private ValueExpression imageHeight;
	private ValueExpression imageWidth;
	private ValueExpression alternateText;
	private ValueExpression fontBold;
	private ValueExpression fontItalic;
	private ValueExpression fontName;
	private ValueExpression fontSize;
	private ValueExpression fontUnderline;
	private ValueExpression filterProperties;
	private ValueExpression pixelUnitToMillimeter;
	private ValueExpression curveFlatness;
	private ValueExpression distanceTolerance;
	public String getComponentType() {
		return ImageComponent.COMPONENT_TYPE;
	}

	public void setImageURL(ValueExpression imageURL) {
		this.imageURL = imageURL;
	}

	public void setImageHeight(ValueExpression imageHeight) {
		this.imageHeight = imageHeight;
	}

	public void setImageWidth(ValueExpression imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setAlternateText(ValueExpression alternateText) {
		this.alternateText = alternateText;
	}

	public void setFontBold(ValueExpression fontBold) {
		this.fontBold = fontBold;
	}

	public void setFontItalic(ValueExpression fontItalic) {
		this.fontItalic = fontItalic;
	}

	public void setFontName(ValueExpression fontName) {
		this.fontName = fontName;
	}

	public void setFontSize(ValueExpression fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontUnderline(ValueExpression fontUnderline) {
		this.fontUnderline = fontUnderline;
	}

	public void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public void setPixelUnitToMillimeter(ValueExpression pixelUnitToMillimeter) {
		this.pixelUnitToMillimeter = pixelUnitToMillimeter;
	}

	public void setCurveFlatness(ValueExpression curveFlatness) {
		this.curveFlatness = curveFlatness;
	}

	public void setDistanceTolerance(ValueExpression distanceTolerance) {
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

		if (imageURL != null) {
			if (imageURL.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_URL, imageURL);

			} else {
				component.setImageURL(imageURL.getExpressionString());
			}
		}

		if (imageHeight != null) {
			if (imageHeight.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_HEIGHT, imageHeight);

			} else {
				component.setImageHeight(getInt(imageHeight.getExpressionString()));
			}
		}

		if (imageWidth != null) {
			if (imageWidth.isLiteralText()==false) {
				component.setValueExpression(Properties.IMAGE_WIDTH, imageWidth);

			} else {
				component.setImageWidth(getInt(imageWidth.getExpressionString()));
			}
		}

		if (alternateText != null) {
			if (alternateText.isLiteralText()==false) {
				component.setValueExpression(Properties.ALTERNATE_TEXT, alternateText);

			} else {
				component.setAlternateText(alternateText.getExpressionString());
			}
		}

		if (fontBold != null) {
			if (fontBold.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_BOLD, fontBold);

			} else {
				component.setFontBold(getBoolean(fontBold.getExpressionString()));
			}
		}

		if (fontItalic != null) {
			if (fontItalic.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_ITALIC, fontItalic);

			} else {
				component.setFontItalic(getBoolean(fontItalic.getExpressionString()));
			}
		}

		if (fontName != null) {
			if (fontName.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_NAME, fontName);

			} else {
				component.setFontName(fontName.getExpressionString());
			}
		}

		if (fontSize != null) {
			if (fontSize.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_SIZE, fontSize);

			} else {
				component.setFontSize(fontSize.getExpressionString());
			}
		}

		if (fontUnderline != null) {
			if (fontUnderline.isLiteralText()==false) {
				component.setValueExpression(Properties.FONT_UNDERLINE, fontUnderline);

			} else {
				component.setFontUnderline(getBoolean(fontUnderline.getExpressionString()));
			}
		}

		if (filterProperties != null) {
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (pixelUnitToMillimeter != null) {
			if (pixelUnitToMillimeter.isLiteralText()==false) {
				component.setValueExpression(Properties.PIXEL_UNIT_TO_MILLIMETER, pixelUnitToMillimeter);

			} else {
				component.setPixelUnitToMillimeter(getDouble(pixelUnitToMillimeter.getExpressionString()));
			}
		}

		if (curveFlatness != null) {
			if (curveFlatness.isLiteralText()==false) {
				component.setValueExpression(Properties.CURVE_FLATNESS, curveFlatness);

			} else {
				component.setCurveFlatness(getDouble(curveFlatness.getExpressionString()));
			}
		}

		if (distanceTolerance != null) {
			if (distanceTolerance.isLiteralText()==false) {
				component.setValueExpression(Properties.DISTANCE_TOLERANCE, distanceTolerance);

			} else {
				component.setDistanceTolerance(getDouble(distanceTolerance.getExpressionString()));
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
