/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.image;

import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.css.parser.DefaultDocumentHandler;
import org.apache.batik.css.parser.Parser;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.IGeneratedResourceInformation;
import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;
import org.rcfaces.core.internal.images.AbstractImageResourceAdapter;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.item.ISelectItemGroup;
import org.rcfaces.renderkit.svg.image.SVGImageGenerationInformation;
import org.rcfaces.renderkit.svg.item.IGroupItem;
import org.rcfaces.renderkit.svg.item.INodeItem;
import org.rcfaces.renderkit.svg.item.IPathItem;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.LexicalUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGImageResourceAdapter extends AbstractImageResourceAdapter {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(SVGImageResourceAdapter.class);

    private static final String DEFAULT_URL = "file:///rcfaces.svg";

    private final DocumentLoader documentLoader;

    public SVGImageResourceAdapter() {

        AdapterImageTranscoder transcoder = new AdapterImageTranscoder();

        documentLoader = new DocumentLoader(transcoder.getUserAgent());
    }

    public BufferedImage adaptContent(FacesContext facesContext,
            InputStream inputStream,
            IGenerationResourceInformation generationInformation,
            IGeneratedResourceInformation generatedInformation) {

        AdapterImageTranscoder transcoder = new AdapterImageTranscoder();
        AdapterTranscoderOuput output = new AdapterTranscoderOuput();

        String url = (String) generationInformation
                .getAttribute(IGenerationResourceInformation.SOURCE_URL);
        if (url == null) {
            url = DEFAULT_URL;
        }

        Document document;
        try {
            document = documentLoader.loadDocument(url, inputStream);

        } catch (IOException e) {
            throw new FacesException(e);
        }

        SVGImageGenerationInformation svgImageGenerationInformation = (SVGImageGenerationInformation) generationInformation;

        int imageWidth = svgImageGenerationInformation.getImageWidth();
        int imageHeight = svgImageGenerationInformation.getImageHeight();

        INodeItem items[] = svgImageGenerationInformation.getNodes();

        if (imageWidth > 0 || imageHeight > 0
                || (items != null && items.length > 0)) {
            document = (Document) document.cloneNode(true);

            if (items != null && items.length > 0) {
                applyItems(document, generationInformation, items);
            }

            if (imageWidth > 0) {
                transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH,
                        new Float(imageWidth));
            }

            if (imageHeight > 0) {
                transcoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT,
                        new Float(imageHeight));
            }
        }

        float pixelUnit = svgImageGenerationInformation.getPixelUnitToMillimeter();
        if (pixelUnit > 0) {
            transcoder.addTranscodingHint(
                    SVGAbstractTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER,
                    new Float(pixelUnit));
        }

        String fontName = svgImageGenerationInformation.getDefaultFontFamily();
        if (fontName != null) {
            transcoder.addTranscodingHint(
                    SVGAbstractTranscoder.KEY_DEFAULT_FONT_FAMILY, fontName);
        }

        TranscoderInput input = new TranscoderInput(document);

        try {
            transcoder.transcode(input, output);

        } catch (TranscoderException e) {
            throw new FacesException(e);
        }

        return output.getBufferedImage();
    }

    private void applyItems(Document document,
            IGenerationResourceInformation generationInformation, Object[] items) {

        for (int i = 0; i < items.length; i++) {
            Object item = items[i];

            if (item instanceof INodeItem) {
                INodeItem nodeItem = (INodeItem) item;

                String targetId = nodeItem.getTargetId();

                if (targetId == null) {
                    LOG.error("TargetId is null !");
                    continue;
                }

                Element element = document.getElementById(targetId);

                if (element == null) {
                    LOG.error("Can not locate '" + targetId + "'.");
                    continue;
                }

                if ((element instanceof SVGElement) == false) {
                    LOG.error("Invalid type of element for target=" + targetId
                            + "'.");
                    continue;
                }

                SVGElement svgElement = (SVGElement) element;

                alterElement(svgElement, nodeItem);

            }

            if (item instanceof SelectItemGroup) {
                SelectItem selectItems[] = ((SelectItemGroup) item)
                        .getSelectItems();

                applyItems(document, generationInformation, selectItems);

            } else if (item instanceof ISelectItemGroup) {
                SelectItem selectItems[] = ((ISelectItemGroup) item)
                        .getSelectItems();

                applyItems(document, generationInformation, selectItems);

            }
        }

    }

    private void alterElement(SVGElement svgElement, INodeItem nodeItem) {

        CssValues cssValues = new CssValues(svgElement);
        try {
            if (nodeItem.isRendered() == false) {
                cssValues.setValue("display", "none", false);

                // On arrete la !
                return;
            }

            if (nodeItem instanceof IGroupItem) {
                updateProperties(cssValues, nodeItem, IGroupItem.class);
            }

            if (nodeItem instanceof IPathItem) {
                updateProperties(cssValues, nodeItem, IPathItem.class);
            }

        } finally {
            if (cssValues.isModified()) {
                cssValues.updateStyle(svgElement);
            }
        }
    }

    private void updateProperties(CssValues cssValues, Object item,
            Class itemClass) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(itemClass);

        } catch (IntrospectionException ex) {
            LOG.error("Can not update path item '" + item + "'", ex);
            return;
        }

        PropertyDescriptor pds[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < pds.length; i++) {
            PropertyDescriptor pd = pds[i];

            Method m = pd.getReadMethod();
            if (m == null) {
                continue;
            }

            Object value;
            try {
                value = m.invoke(item, null);

            } catch (Exception ex) {
                LOG.error("Can not read property '" + pd.getName()
                        + "' from path item '" + item + "'", ex);
                continue;
            }

            if (value == null) {
                continue;
            }

            cssValues.setValue(pd.getName(), value.toString(), false);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class CssValues {
        private static final String REVISION = "$Revision$";

        private final Map properties = new LinkedHashMap();

        private boolean modified = false;

        public CssValues(SVGElement element) {
            this(element.getAttribute("style"));
        }

        public CssValues(String style) {
            Parser cssParser = new Parser();
            cssParser.setDocumentHandler(new DefaultDocumentHandler() {

                public void property(String propertyName,
                        LexicalUnit propertyValue, boolean important)
                        throws CSSException {

                    properties.put(propertyName, new CssValue(propertyName,
                            propertyValue.toString(), important));
                }
            });

            try {
                cssParser.parseStyleDeclaration(style);

            } catch (Exception ex) {
                LOG.error("Can not parse css value '" + style + "'.", ex);
                return;
            }
        }

        public void setValue(String name, String value, boolean important) {
            modified = true;

            CssValue cssValue = (CssValue) properties.get(name);
            if (cssValue != null) {
                cssValue.setValue(value);
                if (important) {
                    cssValue.setImportant(true);
                }

                return;
            }

            cssValue = new CssValue(name, value, important);
            properties.put(name, cssValue);

        }

        public final boolean isModified() {
            return modified;
        }

        public void updateStyle(SVGElement svgElement) {
            StringAppender sa = new StringAppender(properties.size() * 32);
            for (Iterator it = properties.values().iterator(); it.hasNext();) {
                CssValue cssValue = (CssValue) it.next();

                if (sa.length() > 0) {
                    sa.append("; ");
                }
                cssValue.append(sa);
            }

            svgElement.setAttribute("style", sa.toString());
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class CssValue {
        private static final String REVISION = "$Revision$";

        private static final String IMPORTANT_LABEL = "!important";

        private final String name;

        private String value;

        private boolean important;

        public CssValue(String name, String value, boolean important) {
            this.name = name;
            this.value = value;
            this.important = important;
        }

        public void setImportant(boolean important) {
            this.important = important;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void append(StringAppender sa) {

            sa.ensure(name.length() + 2 + value.length()
                    + (important ? (IMPORTANT_LABEL.length() + 1) : 0));
            sa.append(name);
            sa.append(": ");
            sa.append(value);

            if (important) {
                sa.append(' ');
                sa.append(IMPORTANT_LABEL);
            }
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class AdapterTranscoderOuput extends TranscoderOutput {
        private static final String REVISION = "$Revision$";

        private BufferedImage bufferedImage;

        public AdapterTranscoderOuput() {
        }

        public final BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        public final void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class AdapterImageTranscoder extends ImageTranscoder {
        private static final String REVISION = "$Revision$";

        public BufferedImage createImage(int width, int height) {
            BufferedImage img = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);

            return img;
        }

        public void writeImage(BufferedImage img, TranscoderOutput output)
                throws TranscoderException {

            ((AdapterTranscoderOuput) output).setBufferedImage(img);
        }

        public UserAgent getUserAgent() {
            return userAgent;
        }
    }
}
