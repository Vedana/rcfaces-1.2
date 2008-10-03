package org.rcfaces.renderkit.svg.internal.image;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.UserAgent;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.rcfaces.renderkit.svg.item.INodeItem;
import org.w3c.dom.Element;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class AdapterImageTranscoder extends ImageTranscoder {
    private static final String REVISION = "$Revision$";

    private Map selectables;

    private Map selectableShapes;

    private GraphicsNode rootGraphicsNode;

    private BridgeContext bridgeContext;

    private CanvasGraphicsNode canvasGraphicsNode;

    private AffineTransform globalTransform;

    public BufferedImage createImage(int width, int height) {
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        return img;
    }

    public void setDefaultFontFamily(String fontName) {
        addTranscodingHint(SVGAbstractTranscoder.KEY_DEFAULT_FONT_FAMILY,
                fontName);
    }

    public void setPixelUnitToMillimeter(float pixelUnit) {
        addTranscodingHint(SVGAbstractTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER,
                new Float(pixelUnit));
    }

    public void setImageHeight(int imageHeight) {
        addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, new Float(
                imageHeight));
    }

    public void setImageWidth(int imageWidth) {
        addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, new Float(
                imageWidth));
    }

    public void setSelectables(Map selectables) {
        this.selectables = selectables;

        addTranscodingHint(SVGAbstractTranscoder.KEY_EXECUTE_ONLOAD,
                Boolean.TRUE);
    }

    public void writeImage(BufferedImage img, TranscoderOutput output)
            throws TranscoderException {

        ((AdapterTranscoderOuput) output).setBufferedImage(img);
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public void transcode(TranscoderInput input, TranscoderOutput output)
            throws TranscoderException {

        super.transcode(input, output);

        globalTransform = canvasGraphicsNode.getGlobalTransform();
    }

    protected CanvasGraphicsNode getCanvasGraphicsNode(GraphicsNode gn) {
        // Gros hack ... mais a t-on le choix ?

        canvasGraphicsNode = super.getCanvasGraphicsNode(gn);

        if (selectables != null && selectables.isEmpty() == false) {
            selectableShapes = new HashMap(selectables.size());

            searchSelectableGraphicsNode(gn);
        }

        return canvasGraphicsNode;
    }

    protected BridgeContext createBridgeContext(String svgVersion) {
        BridgeContext bridgeContext = super.createBridgeContext(svgVersion);

        this.bridgeContext = bridgeContext;

        return bridgeContext;
    }

    private void searchSelectableGraphicsNode(GraphicsNode root) {

        List l = new ArrayList(64);

        l.add(root);

        for (; l.isEmpty() == false;) {
            GraphicsNode node = (GraphicsNode) l.remove(l.size() - 1);

            Element element = bridgeContext.getElement(node);

            if (element != null) {
                String id = element.getAttribute("id");
                if (id != null) {
                    INodeItem item = (INodeItem) selectables.get(id);
                    if (item != null) {
                        Shape shape = node.getOutline();
                        if (shape != null) {
                            selectableShapes.put(item, new ShapeValue(shape,
                                    item));
                        }
                    }
                }
            }

            if (node instanceof CompositeGraphicsNode) {
                CompositeGraphicsNode compositeGraphicsNode = (CompositeGraphicsNode) node;

                List children = compositeGraphicsNode.getChildren();

                l.addAll(children);
            }
        }
    }

    public final AffineTransform getGlobalTransform() {
        return globalTransform;
    }

    public final Map getSelectableShapes() {
        if (selectableShapes == null) {
            return Collections.EMPTY_MAP;
        }
        return selectableShapes;
    }
}